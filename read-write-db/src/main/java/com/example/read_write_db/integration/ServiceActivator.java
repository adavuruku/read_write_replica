package com.example.read_write_db.integration;

import com.example.read_write_db.dto.AppSettingDto;
import com.example.read_write_db.dto.ProcessedAppSettingDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * Created by Sherif.Abdulraheem 16/05/2025 - 23:06
 **/

/**
 * Service activator listen to a message from a channel and either forward
 * or just process the message
 * two examples here does both.
 *
 */
@Slf4j
@Service
public class ServiceActivator {
    @org.springframework.integration.annotation.ServiceActivator(inputChannel = "serviceActivatorInputChannel")
    public void test(AppSettingDto appSettingDto) {
        log.info("Spring Integration  [test]: " + appSettingDto.getDescription());
    }

    @org.springframework.integration.annotation.ServiceActivator(inputChannel = "serviceActivatorInputChannel", outputChannel = "serviceActivatorOutputChannel", sendTimeout = "30000")
    public String testAndSend(AppSettingDto appSettingDto) {
        log.info("Spring Integration  [testAndSend]");
        return appSettingDto.getDescription();
    }

    //using publish channel
    @org.springframework.integration.annotation.ServiceActivator(inputChannel = "publishInputChannel")
    public void testPub(AppSettingDto appSettingDto) {
        log.info("Spring Integration publishInputChannel  [test]: {} time - {} ", appSettingDto.getDescription(), LocalDateTime.now());
    }

    /**
     *
     * Note: when theres a return message youve to include a return channel
     */
    @org.springframework.integration.annotation.ServiceActivator(inputChannel = "publishInputChannel", outputChannel = "serviceActivatorOutputChannel")
    public String testAndSendPub(AppSettingDto appSettingDto) {
        log.info("Spring Integration publishInputChannel  [testAndSend ] {} ", LocalDateTime.now());
        return appSettingDto.getDescription();
    }

    @org.springframework.integration.annotation.ServiceActivator(inputChannel = "serviceActivatorOutputChannel")
    public void chan(String message) {
        log.info("Spring Integration publishInputChannel chan  [testAndSend ] {}", message);
    }


    public ProcessedAppSettingDto processItem(AppSettingDto appSettingDto) {
        log.info("Thread [itemProcessor ] - {}", Thread.currentThread().getName());
        log.info("[itemProcessor ] GroupId - {} Desc - {}", appSettingDto.getGroupId(), appSettingDto.getDescription());
        return ProcessedAppSettingDto.builder()
                .appSettingDto(appSettingDto)
                .isProcessed(true).build();
    }
}
