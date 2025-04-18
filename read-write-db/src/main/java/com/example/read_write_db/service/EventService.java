package com.example.read_write_db.service;

import com.example.read_write_db.dto.AppSettingDto;
import com.example.read_write_db.event.CDCEventPublisher;
import com.example.read_write_db.event.type.AppSettingCreatedEvent;
import com.example.read_write_db.event.type.UserDetailsEvent;
import com.example.read_write_db.event.type.UserRegisteredEvent;
import com.example.read_write_db.model.AppSetting;
import com.example.read_write_db.model.User;
import com.example.read_write_db.repo.read.AppSettingReadRepo;
import com.example.read_write_db.repo.write.AppSettingRepo;
import com.example.read_write_db.repo.write.UserRepo;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private final CDCEventPublisher cdcEventPublisher;
    private final CacheManager caffeineCacheManager;

    public EventService(AppSettingRepo appSettingRepo, UserRepo userRepository,
                        AppSettingReadRepo appSettingReadRepo,
                        CDCEventPublisher cdcEventPublisher,
                        @Qualifier("valkeyManager") CacheManager caffeineCacheManager) {
        this.appSettingRepo = appSettingRepo;
        this.userRepository = userRepository;
        this.appSettingReadRepo = appSettingReadRepo;
        this.cdcEventPublisher = cdcEventPublisher;
        this.caffeineCacheManager = caffeineCacheManager;
    }

    @Transactional
    public AppSetting createSetting(AppSettingDto appSettingDto) {

        AppSetting appSetting = appSettingRepo.save(AppSetting.builder()
                .description(appSettingDto.getDescription())
                .groupId(appSettingDto.getGroupId()).build());
        AppSettingCreatedEvent appSettingCreatedEvent = AppSettingCreatedEvent.of(
                null, appSetting.getDescription(), appSetting.getId()
        );
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
        cdcEventPublisher.publish(userRegisteredEvent);
        cdcEventPublisher.publish(appSettingCreatedEvent);
        return appSettingSaved;
    }


    @Transactional
    public AppSetting testSaveAndManualException(Long id, AppSetting appSetting) throws JsonProcessingException {
        try {
            Optional<AppSetting> app = appSettingReadRepo.findById(id);
            if (app.isEmpty()) {

                log.info("Saving AppSetting");
                AppSetting appSettingSaved = appSettingRepo.save(appSetting);

                log.info("Saving User");
                User user = User.builder().firstName("Hawa Obi - ".concat(appSettingSaved.getId().toString())).country("UG").build();
                userRepository.save(user);

                //prepare and publish event
                log.info("Publishing UserRegisteredEvent");
                UserRegisteredEvent userRegisteredEvent = UserRegisteredEvent.of(
                        user.getFirstName(), user.getCountry(), user.getCountry(), user.getId()
                );
                cdcEventPublisher.publish(userRegisteredEvent);

                //prepare and publish event
                log.info("Publishing AppSettingCreatedEvent");
                AppSettingCreatedEvent appSettingCreatedEvent = AppSettingCreatedEvent.of(
                        user.getFirstName(), appSettingSaved.getDescription(), appSettingSaved.getId()
                );
                cdcEventPublisher.publish(appSettingCreatedEvent);

                log.info("Publishing UserDetailsEvent");
                UserDetailsEvent userDetailsEvent = UserDetailsEvent.of(
                        user, appSettingSaved
                );
                cdcEventPublisher.publish(userDetailsEvent);

                // throw an exception to trigger transaction rollback
              // throw new NullPointerException();
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


    @Transactional
    public AppSetting scenarioException(AppSetting appSetting) throws JsonProcessingException {
        try {

            log.info("Saving User");
            User user = User.builder().firstName("Macaulay Paul").country("Italy").build();
            Optional<User> userExist = userRepository.findById(1L);

            log.info("Saving AppSetting");
            AppSetting appSettingSaved = appSettingRepo.save(appSetting);

            //prepare and publish event
            log.info("Publishing AppSettingCreatedEvent");
            AppSettingCreatedEvent appSettingCreatedEvent = AppSettingCreatedEvent.of(
                    userExist.get().getFirstName(), appSettingSaved.getDescription(), appSettingSaved.getId()
            );
            cdcEventPublisher.publish(appSettingCreatedEvent);

            log.info("Publishing UserDetailsEvent");
            UserDetailsEvent userDetailsEvent = UserDetailsEvent.of(
                    user, appSettingSaved
            );
            cdcEventPublisher.publish(userDetailsEvent);

            // throw an exception to trigger transaction rollback
             throw new NullPointerException();
//            return appSettingSaved;
        }catch (Exception e){
            log.info("Message {}", e.getMessage());
            throw e;
        }
    }

    @Cacheable(value = "user", key = "#id", condition = "#id > 20", cacheManager = "valkeyManager")
    public User testValkey(Long id) {
        try {
            Optional<User> userExist = userRepository.findById(id);
            if (userExist.isPresent()){
                return userExist.get();
            }
            throw new RuntimeException();
        }catch (Exception e){
            log.info("Message {}", e.getMessage());
            throw e;
        }
    }

//    @Scheduled(cron = "0/10 * * * * *") //every 10 seconds
    @Scheduled(cron = "0 */1 * * * ?") //
    @Transactional
    @SchedulerLock(name = "scheduleTask", lockAtMostFor = "10m", lockAtLeastFor = "1m")
    public void scheduleTask(){
        log.info("Scheduling task");
        log.info("Task scheduled");
    }
}
