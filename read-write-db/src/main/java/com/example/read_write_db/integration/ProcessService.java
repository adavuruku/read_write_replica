package com.example.read_write_db.integration;

import com.example.read_write_db.dto.AppSettingDto;
import com.example.read_write_db.dto.ProcessedAppSettingDto;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by Sherif.Abdulraheem 17/05/2025 - 23:06
 **/
@Slf4j
public class ProcessService {
    public static ProcessedAppSettingDto processItem(AppSettingDto appSettingDto) {
        log.info("Thread [itemProcessor ] - {}", Thread.currentThread().getName());
        log.info("[itemProcessor ] GroupId - {} Desc - {}", appSettingDto.getGroupId(), appSettingDto.getDescription());
        return ProcessedAppSettingDto.builder()
                .appSettingDto(appSettingDto)
                .isProcessed(true).build();
    }
}
