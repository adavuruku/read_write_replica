package com.example.read_write_db.repo.write;

import com.example.read_write_db.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Sherif.Abdulraheem 2/5/2025 - 2:48 PM
 **/
@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    @Modifying
    @Transactional
    @Query("DELETE FROM User o WHERE o.createdAt < CURRENT_TIMESTAMP()")
    int deleteExpiredTokens();
}
