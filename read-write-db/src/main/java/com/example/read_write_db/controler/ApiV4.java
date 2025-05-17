package com.example.read_write_db.controler;

import com.example.read_write_db.dto.AppSettingDto;
import com.example.read_write_db.integration.gateway.AuditLogGateWay;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageChannel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Sherif.Abdulraheem 2/5/2025 - 3:19 PM
 **/
@RestController
@RequestMapping(value = "v4/api", produces = MediaType.APPLICATION_JSON_VALUE)
//@AllArgsConstructor
@Slf4j
public class ApiV4 {
    @Autowired
    private AuditLogGateWay auditLogGateWay;

    @Autowired
    MessageChannel serviceActivatorInputChannel;

    @Autowired
    MessageChannel publishInputChannel;

    @GetMapping("/gateway")
    public ResponseEntity<String> getAppSetting() {
        AppSettingDto appSettingDto = new AppSettingDto();
        appSettingDto.setDescription("Hello Gate way sample test");
        appSettingDto.setGroupId(2L);
        // will get message in inputChannel and pass to outputChannel
        // response will be available in outputChannel and that can be used for processing further
        String result = auditLogGateWay.processLog(appSettingDto);

        return ResponseEntity.ok(result);
    }

    @GetMapping("/channel")
    public void getAppSettingChannel() {
        AppSettingDto appSettingDto = new AppSettingDto();
        appSettingDto.setDescription("Hello Gate way sample test");
        appSettingDto.setGroupId(2L);
        // directly using message channel and send message
        // will directly call serviceActivatorInputChannel.send(), will not go the Messaging Gateway
        //and no response is expected. won't go to the outputChannel
        serviceActivatorInputChannel.send(MessageBuilder.withPayload(appSettingDto).build());
    }

    @GetMapping("/pub")
    public void getAppSettingPubChannel() {
        AppSettingDto appSettingDto = new AppSettingDto();
        appSettingDto.setDescription("Hello Gate way sample test");
        appSettingDto.setGroupId(2L);
        // directly using message channel and send message
        // will directly call serviceActivatorInputChannel.send(), will not go the Messaging Gateway
        //and no response is expected. won't go to the outputChannel
        publishInputChannel.send(MessageBuilder.withPayload(appSettingDto).build());
    }
}
