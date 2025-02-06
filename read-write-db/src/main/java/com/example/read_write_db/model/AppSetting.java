package com.example.read_write_db.model;

import jakarta.persistence.*;
import lombok.*;

/**
 * Created by Sherif.Abdulraheem 2/5/2025 - 2:49 PM
 **/
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="app_settings")
public class AppSetting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name = "group_id")
    Long groupId;
    @Column(name = "description")
    String description;
}
