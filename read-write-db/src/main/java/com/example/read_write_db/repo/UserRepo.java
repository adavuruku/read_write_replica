package com.example.read_write_db.repo;

import com.example.read_write_db.config.DataSourceType;
import com.example.read_write_db.config.UseDataSource;
import com.example.read_write_db.model.AppSetting;
import com.example.read_write_db.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Created by Sherif.Abdulraheem 2/5/2025 - 2:48 PM
 **/
@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    @UseDataSource(DataSourceType.WRITE)
    User save(User user);
}
