package com.example.read_write_db.repo;

import com.example.read_write_db.model.AppSetting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Created by Sherif.Abdulraheem 2/5/2025 - 2:48 PM
 **/
@Repository
public interface AppSettingRepo extends JpaRepository<AppSetting, Long> {
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    AppSetting findByGroupId(Long id);

//    @Transactional(propagation = Propagation.REQUIRES_NEW)
//    Optional<AppSetting> findById(Long id);

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    AppSetting save(AppSetting appSetting);
}
