package com.example.read_write_db.repo;

import com.example.read_write_db.config.DataSourceType;
import com.example.read_write_db.config.UseDataSource;
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
    @UseDataSource(DataSourceType.READ)
    AppSetting findByGroupId(Long id);

//    @Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
    @UseDataSource(DataSourceType.READ)
    //@Transactional(propagation = Propagation.REQUIRES_NEW)
//    @Transactional( readOnly = true)
    Optional<AppSetting> findById(Long id);

//    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @UseDataSource(DataSourceType.WRITE)
//    @Transactional
    AppSetting save(AppSetting appSetting);
}
