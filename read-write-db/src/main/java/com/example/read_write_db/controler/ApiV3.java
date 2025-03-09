package com.example.read_write_db.controler;

import com.example.read_write_db.dto.AppSettingDto;
import com.example.read_write_db.model.AppSetting;
import com.example.read_write_db.service.AppServiceReadWriteImp;
import com.example.read_write_db.service.EventService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * Created by Sherif.Abdulraheem 2/5/2025 - 3:19 PM
 **/
@RestController
@RequestMapping(value = "v3/api", produces = MediaType.APPLICATION_JSON_VALUE)
//@AllArgsConstructor
@Slf4j
public class ApiV3 {
    @Autowired
    private EventService eventService;

    @GetMapping("/{id}")
    public ResponseEntity<AppSetting> getAppSetting(@PathVariable Long id) {
        Optional<AppSetting> appSetting = eventService.getAppSetting(id);
        return appSetting.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<AppSetting> createApp(@RequestBody AppSettingDto appSettingDto){
        return ResponseEntity.ok(eventService.createSetting(appSettingDto));
    }


    @PostMapping("/save-user-app")
    public ResponseEntity<AppSetting> findOrSaveTransactionAutRollback(@RequestBody AppSetting appSetting){
        return ResponseEntity.ok(eventService.testSaveUserAndAppSetting(appSetting));
    }

    @PostMapping("/manual-rollback/{id}")
    public ResponseEntity<AppSetting> findOrSaveTransactionManualRollback(@PathVariable(name = "id") Long id, @RequestBody AppSetting appSetting){
        return ResponseEntity.ok(eventService.testSaveAndManualException(id, appSetting));
    }
}
