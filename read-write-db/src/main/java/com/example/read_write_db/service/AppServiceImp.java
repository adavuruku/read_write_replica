package com.example.read_write_db.service;

import com.example.read_write_db.config.DataSourceType;
import com.example.read_write_db.config.UseDataSource;
import com.example.read_write_db.dto.AppSettingDto;
import com.example.read_write_db.model.AppSetting;
import com.example.read_write_db.model.User;
import com.example.read_write_db.repo.write.AppSettingRepo;
import com.example.read_write_db.repo.write.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.Optional;

/**
 * Created by Sherif.Abdulraheem 2/5/2025 - 2:54 PM
 **/
@Service
//@AllArgsConstructor
//@Transactional
@Slf4j
public class AppServiceImp implements AppService {
    @Autowired
    private AppSettingRepo appSettingRepo;

    @Autowired
    private UserRepo userRepository;

    @Override
//    @Transactional
    public AppSetting createSetting(AppSettingDto appSettingDto) {

        return appSettingRepo.save(AppSetting.builder()
                .description(appSettingDto.getDescription())
                .groupId(appSettingDto.getGroupId()).build());
    }

    @Override
    public Optional<AppSetting> getAppSettingMain(Long id) {
        return getAppSetting(id);
    }


    @Override
//    @Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
    public Optional<AppSetting> getAppSetting(Long id) {
        boolean isReadOnly = TransactionSynchronizationManager.isCurrentTransactionReadOnly();
        System.out.println("Transaction is read-only: " + isReadOnly);

        return appSettingRepo.findById(id);
    }

//    @Transactional
    public AppSetting findOrSave(Long id, AppSetting appSetting) {
        Optional<AppSetting> app = appSettingRepo.findById(id);
        if(app.isEmpty()){
            System.out.println("Not Found and Saving");
            AppSetting SS = appSettingRepo.save(appSetting);
            System.out.println("Saving AppSetting");
            return SS;
        }else {
            System.out.println(" Found and returning");
            return app.get();
        }
    }

    @Transactional
    public AppSetting testSaveAndRoleBack(Long id, AppSetting appSetting) {
        Optional<AppSetting> app = appSettingRepo.findById(id);
        if(app.isEmpty()){

            System.out.println("Not Found and Saving");

            AppSetting SS = appSettingRepo.save(appSetting);
            System.out.println("Saving User");

            User user = User.builder().country("NG").build();
            userRepository.save(user);

            return SS;
        }else {
            System.out.println(" Found and returning");
            return app.get();
        }
    }

//    @Override
//    public AppSetting findOrSave(Long id, AppSetting appSetting) {
//        Optional<AppSetting> app = appSettingRepo.findById(id);
//        if (app.isEmpty()) {
//            System.out.println("Not Found and Saving");
//            return saveInWriteTransaction(appSetting);  // Call a separate method
//        } else {
//            System.out.println("Found and returning");
//            return app.get();
//        }
//    }

//    @Transactional(propagation = Propagation.REQUIRES_NEW) // 🔹 Ensures new WRITE transaction
//    public AppSetting saveInWriteTransaction(AppSetting appSetting) {
//        return appSettingRepo.save(appSetting);
//    }


//    @Override
//    public AppSetting findOrSave(Long id, AppSetting appSetting) {
//        boolean isReadOnly = TransactionSynchronizationManager.isCurrentTransactionReadOnly();
//        System.out.println("Transaction is read-only: " + isReadOnly);
//        Optional<AppSetting> app = appSettingRepo.findById(id);
//        if (app.isEmpty()) {
//            System.out.println("Not Found and Saving");
//            AppSetting appt = appSettingRepo.save(appSetting);
//            return appt;
//        } else {
//            System.out.println("Found and returning");
//            return app.get();
//        }
//    }

//    @Override
//
//    public AppSetting findOrSave(Long id, AppSetting appSetting) {
//        boolean isReadOnly = TransactionSynchronizationManager.isCurrentTransactionReadOnly();
//        System.out.println("Transaction is read-only: " + isReadOnly);
//        System.out.println("Not Found and Saving");
//        AppSetting appt = appSettingRepo.save(appSetting);
//        return appt;
//    }

//    @Transactional
//    public AppSetting findOrCreate(Long id, AppSetting appSetting) {
//        boolean isReadOnly = TransactionSynchronizationManager.isCurrentTransactionReadOnly();
//        System.out.println("Transaction is read-only: " + isReadOnly);
//        AppSetting appt = appSettingRepo.save(appSetting);
//        Optional<AppSetting> app = appSettingRepo.findById(id);
//        if (app.isEmpty()) {
//            System.out.println("Not Found and Saving");
//
//            return appt;
//        } else {
//            System.out.println("Found and returning");
//            return app.get();
//        }
//    }

//    @Transactional(readOnly = true)  // Step 1: Read from Read DB
//    public Optional<AppSetting> findById(Long id) {
//        boolean isReadOnly = TransactionSynchronizationManager.isCurrentTransactionReadOnly();
//        System.out.println("Transaction is read-only (findById): " + isReadOnly);
//        return appSettingRepo.findById(id);
//    }
//
//    @Transactional  // Step 2: Write to Write DB
//    public AppSetting save(AppSetting appSetting) {
//        boolean isReadOnly = TransactionSynchronizationManager.isCurrentTransactionReadOnly();
//        System.out.println("Transaction is read-only (save): " + isReadOnly);
//        return appSettingRepo.save(appSetting);
//    }
//
//    public AppSetting findOrSave(Long id, AppSetting appSetting) {
//        Optional<AppSetting> app = findById(id);
//        if (app.isEmpty()) {
//            System.out.println("Not Found and Saving");
//            return save(appSetting);
//        } else {
//            System.out.println("Found and returning");
//            return app.get();
//        }
//    }

    @Scheduled(cron = "${auth.token.cleanup.schedule:0/2 * * * * *}")
    public void evictExpiredToken() {
        int totalRecordDeleted = userRepository.deleteExpiredTokens();
        log.info("Total expired token deleted : {}", totalRecordDeleted);
    }
}
