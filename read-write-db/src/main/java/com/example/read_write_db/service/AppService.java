package com.example.read_write_db.service;

import com.example.read_write_db.dto.AppSettingDto;
import com.example.read_write_db.model.AppSetting;

import java.util.Optional;

/**
 * Created by Sherif.Abdulraheem 06/02/2025 - 19:45
 **/
public interface AppService {
    Optional<AppSetting> getAppSetting(Long Id);

    AppSetting findOrSave(Long id, AppSetting appSetting);

    AppSetting createSetting(AppSettingDto appSettingDto);

    Optional<AppSetting> getAppSettingMain(Long id);
}
