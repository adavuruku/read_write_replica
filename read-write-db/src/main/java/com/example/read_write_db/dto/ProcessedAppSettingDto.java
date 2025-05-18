package com.example.read_write_db.dto;

import lombok.Builder;
import lombok.Data;

/**
 * Created by Sherif.Abdulraheem 17/05/2025 - 22:51
 **/
@Data
@Builder
public class ProcessedAppSettingDto {
    AppSettingDto appSettingDto;
    boolean isProcessed;
}
