package com.example.read_write_db.service;

import com.example.read_write_db.config.DataSourceType;
import com.example.read_write_db.config.DatabaseContextHolder;
import com.example.read_write_db.dto.AppSettingDto;
import com.example.read_write_db.model.AppSetting;
import com.example.read_write_db.repo.AppSettingRepo;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
public class AppService {
    @Autowired
    private AppSettingRepo appSettingRepo;

    @Transactional
    public AppSetting createAccount(AppSettingDto appSettingDto) {

        return appSettingRepo.save(AppSetting.builder()
                .description(appSettingDto.getDescription())
                .groupId(appSettingDto.getGroupId()).build());
//        return null;
    }

    @Transactional(readOnly = true)
    public Optional<AppSetting> getAccount(Long id) {
        boolean isReadOnly = TransactionSynchronizationManager.isCurrentTransactionReadOnly();
        System.out.println("Transaction is read-only: " + isReadOnly);
        return appSettingRepo.findById(id);
//        DatabaseContextHolder.clear();// Uses read DB
    }

//    @Transactional
//    public AppSetting findOrSave(Long id, AppSetting appSetting) {
//        Optional<AppSetting> app = appSettingRepo.findById(id);
//        if(app.isEmpty()){
//            System.out.println("Not Found and Saving");
//            return appSettingRepo.save(appSetting);
//        }else {
//            System.out.println(" Found and returning");
//            return app.get();
//        }
//    }


//    @Transactional
    public AppSetting findOrSave(Long id, AppSetting appSetting) {
        boolean isReadOnly = TransactionSynchronizationManager.isCurrentTransactionReadOnly();
        System.out.println("Transaction is read-only: " + isReadOnly);
        Optional<AppSetting> app = appSettingRepo.findById(id);
        if (app.isEmpty()) {
            System.out.println("Not Found and Saving");
            AppSetting appt = appSettingRepo.save(appSetting);
            return appt;
        } else {
            System.out.println("Found and returning");
            return app.get();
        }
    }


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
}
