package com.example.read_write_db.service;

import com.example.read_write_db.dto.AppSettingDto;
import com.example.read_write_db.event.CDCEventPublisher;
import com.example.read_write_db.event.type.AppSettingCreatedEvent;
import com.example.read_write_db.event.type.UserRegisteredEvent;
import com.example.read_write_db.model.AppSetting;
import com.example.read_write_db.model.User;
import com.example.read_write_db.repo.read.AppSettingReadRepo;
import com.example.read_write_db.repo.read.UserReadRepo;
import com.example.read_write_db.repo.write.AppSettingRepo;
import com.example.read_write_db.repo.write.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.Optional;

/**
 * Created by Sherif.Abdulraheem 2/5/2025 - 2:54 PM
 **/
@Service
@Slf4j
public class EventService  {
    private final AppSettingRepo appSettingRepo;
    private final UserRepo userRepository;
    private final AppSettingReadRepo appSettingReadRepo;
    private final UserReadRepo userReadRepo;
    private final CDCEventPublisher cdcEventPublisher;

    public EventService(AppSettingRepo appSettingRepo, UserRepo userRepository,
                        AppSettingReadRepo appSettingReadRepo, UserReadRepo userReadRepo,
                        CDCEventPublisher cdcEventPublisher) {
        this.appSettingRepo = appSettingRepo;
        this.userRepository = userRepository;
        this.appSettingReadRepo = appSettingReadRepo;
        this.userReadRepo = userReadRepo;
        this.cdcEventPublisher = cdcEventPublisher;
    }


    @Transactional
    public AppSetting createSetting(AppSettingDto appSettingDto) {

        AppSetting appSetting = appSettingRepo.save(AppSetting.builder()
                .description(appSettingDto.getDescription())
                .groupId(appSettingDto.getGroupId()).build());
        AppSettingCreatedEvent appSettingCreatedEvent = AppSettingCreatedEvent.of(
                null, appSetting.getDescription(), appSetting.getId()
        );

        //events are published at the end of transaction
        cdcEventPublisher.publish(appSettingCreatedEvent);
        return appSetting;
    }

    @Transactional
    public Optional<AppSetting> getAppSetting(Long id) {
        return appSettingReadRepo.findById(id);
    }

    @Transactional
    public AppSetting testSaveUserAndAppSetting(AppSetting appSetting) {

        //user cannot save because firstName is required, transaction should aut roll back
        User user = User.builder().firstName("Josh").country("UG").build();
        userRepository.save(user);

        UserRegisteredEvent userRegisteredEvent = UserRegisteredEvent.of(
                user.getFirstName(), user.getCountry(), user.getCountry(), user.getId()
        );
        AppSetting appSettingSaved = appSettingRepo.save(appSetting);

        AppSettingCreatedEvent appSettingCreatedEvent = AppSettingCreatedEvent.of(
                user.getFirstName(), appSettingSaved.getDescription(), appSettingSaved.getId()
        );

        //events are published at the end of transaction
        cdcEventPublisher.publish(userRegisteredEvent);
        cdcEventPublisher.publish(appSettingCreatedEvent);
        return appSettingSaved;
    }


    @Transactional
    public AppSetting testSaveAndManualException(Long id, AppSetting appSetting) {
        try {
            Optional<AppSetting> app = appSettingReadRepo.findById(id);
            if (app.isEmpty()) {

                log.info("Saving AppSetting");
                AppSetting appSettingSaved = appSettingRepo.save(appSetting);

                log.info("Saving User");
                User user = User.builder().firstName("John K - ".concat(appSettingSaved.getId().toString())).country("UG").build();
                userRepository.save(user);

                app = appSettingReadRepo.findById(appSettingSaved.getId());

                AppSettingCreatedEvent appSettingCreatedEvent = AppSettingCreatedEvent.of(
                        user.getFirstName(), appSettingSaved.getDescription(), appSettingSaved.getId()
                );

                //events are published at the end of transaction
                cdcEventPublisher.publish(appSettingCreatedEvent);

                if (app.isEmpty()) {
                    log.info("Rolling back all CUD transaction because {} Not found", appSettingSaved.getId());
                    // TO Manually mark transaction for rollback
                    throw new NullPointerException();
                }
                return appSettingSaved;
            } else {
                log.info(" Found and returning");
                return app.get();
            }
        }catch (Exception e){
            log.info("Message {}", e.getMessage());
            throw e;
        }
    }
}
