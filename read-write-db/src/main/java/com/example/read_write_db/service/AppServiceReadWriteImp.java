package com.example.read_write_db.service;

import com.example.read_write_db.dto.AppSettingDto;
import com.example.read_write_db.model.AppSetting;
import com.example.read_write_db.model.User;
import com.example.read_write_db.repo.read.AppSettingReadRepo;
import com.example.read_write_db.repo.read.UserReadRepo;
import com.example.read_write_db.repo.write.AppSettingRepo;
import com.example.read_write_db.repo.write.UserRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.Optional;

/**
 * Created by Sherif.Abdulraheem 2/5/2025 - 2:54 PM
 **/
@Service
//@AllArgsConstructor
//@Transactional
public class AppServiceReadWriteImp implements AppService {
    private final AppSettingRepo appSettingRepo;
    private final UserRepo userRepository;
    private final AppSettingReadRepo appSettingReadRepo;
    private final UserReadRepo userReadRepo;

    public AppServiceReadWriteImp(AppSettingRepo appSettingRepo, UserRepo userRepository,
                                  AppSettingReadRepo appSettingReadRepo, UserReadRepo userReadRepo) {
        this.appSettingRepo = appSettingRepo;
        this.userRepository = userRepository;
        this.appSettingReadRepo = appSettingReadRepo;
        this.userReadRepo = userReadRepo;
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
        boolean isReadOnly = TransactionSynchronizationManager.isCurrentTransactionReadOnly();
        System.out.println("Transaction is read-only: " + isReadOnly);

        return appSettingReadRepo.findById(id);
    }

    @Transactional
    public AppSetting findOrSave(Long id, AppSetting appSetting) {
        Optional<AppSetting> app = appSettingReadRepo.findById(id);
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
        Optional<AppSetting> app = appSettingReadRepo.findById(id);
        if(app.isEmpty()){

            System.out.println("Not Found and Saving");

            AppSetting SS = appSettingRepo.save(appSetting);
            System.out.println("Saving User");

            User user = User.builder().firstName("Peters").country("UG").build();
            userRepository.save(user);

            // TO Manually mark transaction for rollback
//            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();

            return SS;
        }else {
            System.out.println(" Found and returning");
            return app.get();
        }
    }
}
