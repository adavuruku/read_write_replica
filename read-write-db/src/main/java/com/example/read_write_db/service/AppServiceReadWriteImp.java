package com.example.read_write_db.service;

import com.example.read_write_db.dto.AppSettingDto;
import com.example.read_write_db.event.CDCEventPublisher;
import com.example.read_write_db.model.AppSetting;
import com.example.read_write_db.model.User;
import com.example.read_write_db.repo.read.AppSettingReadRepo;
import com.example.read_write_db.repo.read.UserReadRepo;
import com.example.read_write_db.repo.write.AppSettingRepo;
import com.example.read_write_db.repo.write.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.Optional;

/**
 * Created by Sherif.Abdulraheem 2/5/2025 - 2:54 PM
 **/
@Service
@Slf4j
public class AppServiceReadWriteImp implements AppService {
    private final AppSettingRepo appSettingRepo;
    private final UserRepo userRepository;
    private final AppSettingReadRepo appSettingReadRepo;
    private final UserReadRepo userReadRepo;
    private final CDCEventPublisher cdcEventPublisher;

    public AppServiceReadWriteImp(AppSettingRepo appSettingRepo, UserRepo userRepository,
                                  AppSettingReadRepo appSettingReadRepo, UserReadRepo userReadRepo,
                                  CDCEventPublisher cdcEventPublisher) {
        this.appSettingRepo = appSettingRepo;
        this.userRepository = userRepository;
        this.appSettingReadRepo = appSettingReadRepo;
        this.userReadRepo = userReadRepo;
        this.cdcEventPublisher = cdcEventPublisher;
    }


    @Override
    @Transactional
    public AppSetting createSetting(AppSettingDto appSettingDto) {

        return appSettingRepo.save(AppSetting.builder()
                .description(appSettingDto.getDescription())
                .groupId(appSettingDto.getGroupId()).build());
    }

    @Override
    public Optional<AppSetting> getAppSettingMain(Long id) {
        return Optional.empty();
    }

    @Override
    @Transactional
    public Optional<AppSetting> getAppSetting(Long id) {
        return appSettingReadRepo.findById(id);
    }

    @Transactional
    public AppSetting findOrSave(Long id, AppSetting appSetting) {
        Optional<AppSetting> app = appSettingReadRepo.findById(id);
        if(app.isEmpty()){
            log.info("Not Found and Saving");
            AppSetting SS = appSettingRepo.save(appSetting);
            log.info("Saving AppSetting");
            return SS;
        }else {
            log.info(" Found and returning");
            return app.get();
        }
    }

    @Transactional
    public AppSetting testSaveAndAutomaticRollBack(Long id, AppSetting appSetting) {
        Optional<AppSetting> app = appSettingReadRepo.findById(id);
        if(app.isEmpty()){

            log.info("Not Found and Saving");
            AppSetting appSetting1 = appSettingRepo.save(appSetting);
            log.info("Saving User");

            //user cannot save because firstName is required, transaction should aut roll back
            User user = User.builder().country("UG").build();
            userRepository.save(user);

            return appSetting1;
        }else {
            log.info(" Found and returning");
            return app.get();
        }
    }

    @Transactional
    public AppSetting testReadFetchAndUpdate(Long id, AppSetting appSetting) {
        Optional<AppSetting> app = appSettingReadRepo.findById(id);
        if(app.isPresent()){

            log.info("Found and Updating AppSetting ");
            AppSetting appSetting1 = app.get();
            appSetting1.setDescription(appSetting.getDescription());
            return appSettingRepo.save(appSetting1);
        }else {
            log.info(" Found and returning");
            return app.get();
        }
    }


    @Transactional
    public AppSetting testSaveAndManualRollBack(Long id, AppSetting appSetting) {
        Optional<AppSetting> app = appSettingReadRepo.findById(id);
        if(app.isEmpty()){

            log.info("Saving AppSetting");
            AppSetting appSetting1 = appSettingRepo.save(appSetting);

            log.info("Saving User");
            User user = User.builder().firstName("Peters - ".concat(appSetting1.getId().toString())).country("UG").build();
            userRepository.save(user);

            app = appSettingReadRepo.findById(appSetting1.getId());
            if(app.isEmpty()){
                log.info("Rolling back all CUD transaction because {} Not found", appSetting1.getId());
                // TO Manually mark transaction for rollback
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            }


            return appSetting1;
        }else {
            log.info(" Found and returning");
            return app.get();
        }
    }

    @Transactional
    public AppSetting testSaveAndManualException(Long id, AppSetting appSetting) {
        try {
            Optional<AppSetting> app = appSettingReadRepo.findById(id);
            if (app.isEmpty()) {

                log.info("Saving AppSetting");
                AppSetting appSetting1 = appSettingRepo.save(appSetting);

                log.info("Saving User");
                User user = User.builder().firstName("John K - ".concat(appSetting1.getId().toString())).country("UG").build();
                userRepository.save(user);

                app = appSettingReadRepo.findById(appSetting1.getId());
                if (app.isEmpty()) {
                    log.info("Rolling back all CUD transaction because {} Not found", appSetting1.getId());
                    // TO Manually mark transaction for rollback
                    throw new NullPointerException();
                }
                return appSetting1;
            } else {
                log.info(" Found and returning");
                return app.get();
            }
        }catch (Exception e){
            log.info("Message {}", e.getMessage());
            throw e;
//            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
    }
}
