package com.example.read_write_db.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * Created by Sherif.Abdulraheem 17/05/2025 - 22:50
 **/
@Data
@AllArgsConstructor
public class AppSettingList {
    String appId;
    List<AppSettingDto> appSettingDtoList;
}
