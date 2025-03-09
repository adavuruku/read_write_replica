package com.example.read_write_db.repo.read;


import com.example.read_write_db.model.OutboxEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Created by Sherif.Abdulraheem 3/8/2025 - 4:55 PM
 **/
@Repository
public interface OutboxEventReadRepo extends JpaRepository<OutboxEvent, UUID> {
}
