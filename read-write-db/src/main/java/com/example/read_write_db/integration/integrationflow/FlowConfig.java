package com.example.read_write_db.integration.integrationflow;

import com.example.read_write_db.dto.AppSettingDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;

/**
 * Created by Sherif.Abdulraheem 16/05/2025 - 23:33
 * Integration flow is a fluent DSL to define an entire messaging flow, end-to-end
 * its imperative letting you compose channels, transformers, routers, filters and services.
 *
 * Unlike service activator that only describe a channel to listen and publish to.
 * @Bean
 * @ServiceActivator(inputChannel="channel")
 * public MessageHandler myhandle(){
 *     return message;
 * }
 **/

@Slf4j
@Configuration
public class FlowConfig {

    @Autowired
    MessageChannel asyncChannel;

    @Bean
    public IntegrationFlow flow() {
        return IntegrationFlow.from("publishInputChannel")
                .transform(AppSettingDto.class, AppSettingDto::getDescription)
                .handle(System.out::println)
                .get();
    }


    @Bean
    public IntegrationFlow flowChannel(MessageChannel asyncChannel) {
        return IntegrationFlow.from("publishInputChannel")
                .transform(AppSettingDto.class, AppSettingDto::getDescription)
                .channel(asyncChannel)
                .handle(message -> {
                    log.info("Thread (flowChannel): "+ Thread.currentThread().getName());
                    log.info("Processing: "+ message.getPayload());
                })
                .get();
    }

    //1. bean with filter and router using sync

    // this will execute on a thread from the pool.
    @Bean
    public IntegrationFlow flowChannelAndRouteAsync(MessageChannel asyncChannel) {
        return IntegrationFlow.from("publishInputChannel")
                .transform(AppSettingDto.class, AppSettingDto::getDescription)
                .channel(asyncChannel) //async channel to select from the pool of thread
                .route(Message.class, message -> {
                    log.info("### Async Flow Details ");
                    log.info("Thread (Async): "+ Thread.currentThread().getName()); // pool-3-thread-1
                    String payload = message.getPayload().toString();
                    return payload.contains("urgent")? "urgentChannel" : "normalChannel";
                })
                .get();
    }


    //this will execute on main thread
    @Bean
    public IntegrationFlow flowChannelAndRouteSync(MessageChannel asyncChannel) {
        return IntegrationFlow.from("publishInputChannel")
                .transform(AppSettingDto.class, AppSettingDto::getDescription)
                .route(Message.class, message -> {
                    log.info("### Sync Flow Details ");
                    log.info("Thread (Sync): "+ Thread.currentThread().getName()); // nio-thread
                    String payload = message.getPayload().toString();
                    return payload.contains("urgent")? "urgentChannel" : "normalChannel";
                })
                .get();
    }


    @Bean
    public IntegrationFlow urgentFlow() {
        return IntegrationFlow.from("urgentChannel")
                .handle(message -> {
                    log.info("### UrgentChannel Details ");
                    log.info("Thread (urgent): "+ Thread.currentThread().getName());
                    log.info("Processing : "+ message.getPayload());
                })
                .get();
    }

    @Bean
    public IntegrationFlow normalFlow() {
        return IntegrationFlow.from("normalChannel")
                .handle(message -> {
                    log.info("### NormalChannel Details ");
                    log.info("Thread (Normal): "+ Thread.currentThread().getName());
                    log.info("Processing: "+ message.getPayload());
                })
                .get();
    }

   //define flow with filter

    //bean with split and aggregator
}
