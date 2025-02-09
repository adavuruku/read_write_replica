package com.example.read_write_db.controler;

import com.example.read_write_db.dto.AppSettingDto;
import com.example.read_write_db.model.AppSetting;
import com.example.read_write_db.service.AppServiceImp;
import com.example.read_write_db.service.AppServiceReadWriteImp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * Created by Sherif.Abdulraheem 2/5/2025 - 3:19 PM
 **/
@RestController
@RequestMapping(value = "v2/api", produces = MediaType.APPLICATION_JSON_VALUE)
//@AllArgsConstructor
@Slf4j
public class ApiV2 {
    @Autowired
    private AppServiceReadWriteImp appServiceReadWriteImp;

    @GetMapping("/{id}")
    public ResponseEntity<AppSetting> getAppSetting(@PathVariable Long id) {
        log.info("controller: get app setting by id {}", id);
        Optional<AppSetting> appSetting = appServiceReadWriteImp.getAppSetting(id);
        return appSetting.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/find-save/{id}")
    public ResponseEntity<AppSetting> findOrSave(@PathVariable(name = "id") Long id, @RequestBody AppSetting appSetting){
        return ResponseEntity.ok(appServiceReadWriteImp.findOrSave(id, appSetting));
    }

    @PostMapping("/save/{id}")
    public ResponseEntity<AppSetting> findOrSaveTransaction(@PathVariable(name = "id") Long id, @RequestBody AppSetting appSetting){
        return ResponseEntity.ok(appServiceReadWriteImp.testSaveAndRoleBack(id, appSetting));
    }

    @PostMapping
    public ResponseEntity<AppSetting> createApp(@RequestBody AppSettingDto appSettingDto){
        return ResponseEntity.ok(appServiceReadWriteImp.createSetting(appSettingDto));
    }
}
