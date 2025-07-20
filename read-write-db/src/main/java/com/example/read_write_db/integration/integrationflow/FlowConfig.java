package com.example.read_write_db.integration.integrationflow;

import com.example.read_write_db.dto.AppSettingDto;
import com.example.read_write_db.dto.AppSettingList;
import com.example.read_write_db.integration.ProcessService;
import com.example.read_write_db.integration.ServiceActivator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;

import java.util.Objects;

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

    @Autowired
    MessageChannel asyncSplitChannel;

    @Bean
    public IntegrationFlow flow() {
        return IntegrationFlow.from("publishInputChannel")
                .transform(AppSettingDto.class, AppSettingDto::getDescription)
                .handle(System.out::println)
                .get();
    }


    @Bean
    public IntegrationFlow flowChannel() {
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
    public IntegrationFlow flowChannelAndRouteAsync() {
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
    public IntegrationFlow flowChannelAndRouteSync() {
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

    @Bean
    public IntegrationFlow splitAndAggregateSync() {
        return IntegrationFlow.from("splitChannelSync")
                .enrichHeaders(h -> {
                    /**
                     * to update the message header with required information to be used in
                     *        - the correlation strategy in the aggregator.
                     *        - in the split message channel
                     *        or at any other stage of the process
                     */
                    h.header("appId", "21A4");
                    h.header("customSize", "T(payload.appSettingDtoList.size())");
                })
                .split(AppSettingList.class, AppSettingList::getAppSettingDtoList) //process each item in list synchronously
                .handle(new ServiceActivator(), "processItem") //handle each split item
                .aggregate(aggregatorSpec -> aggregatorSpec //aggregate back to a single message
                        .correlationStrategy(message -> {
                            /**
                             * define how all the split items can be grouped together to form one single output
                             * the final group will be used to determined the release strategy
                             * and the last step(outputProcessor)
                             *
                             * return message.getHeaders().get("correlationId"); //spring automatically set correlationId which is the groupId
                             * return message.getHeaders().get("sequenceSize"); //spring automatically set sequenceSize which is the total count of items in the split
                             * return message.getHeaders().get("sequenceNumber"); //spring automatically set sequenceNumber for each item that get processed (it is the position in the list)
                             */
                            log.info("### outputProcessor ");
                            log.info("Thread (outputProcessor): "+ Thread.currentThread().getName());
                            log.info("correlationId: "+ message.getHeaders().get("correlationId"));
                            log.info("sequenceSize: "+ message.getHeaders().get("sequenceSize"));
                            log.info("sequenceNumber: "+ message.getHeaders().get("sequenceNumber"));
                            log.info("custom header (appId): "+ message.getHeaders().get("appId"));
                            log.info("custom header (customSize): "+ message.getHeaders().get("customSize"));
                            try {
                                log.info("Headers {}", new ObjectMapper().writeValueAsString(message.getHeaders()));
                                log.info("Messages {}", new ObjectMapper().writeValueAsString(message.getPayload()));
                            } catch (JsonProcessingException e) {
                                throw new RuntimeException(e);
                            }
                            //we retrieve appSettingDtoList from message header and use to group the processed items by appId
                            return Long.parseLong(Objects.requireNonNull(message.getHeaders().get("sequenceSize")).toString())  == 3;
                        })
                        .releaseStrategy(group -> { //a release strategy to release the entire group
                            /**
                             * define when the group can be completed by making release strategy return true
                             * it is similar to the predicate in the filter.
                             * the strategy is evaluated for each received message
                             * if the release strategy return true, it will trigger a call to the output processor.
                             * if it return false, it will keep accumulating the messages into the group.
                             * the group will not be released, and no call will be made to the output processor
                             *
                             * Note: that release strategy is useful for long-running exchanges where the producer might send a number of messages
                             * with the same correlation and there is no way to know the total number of messages that will be sent in advance.
                             * release strategy is optional. If its not provided, the group will be release when the complete group is received
                             *
                             * spring will wait for the group to complete before making the call to the outputProcessor and the split message channel.
                             * it will wait indefinitely for the complete group if no release strategy is provided.
                             * it is good practice to provide a timeout for the aggregator if no release strategy is provided.
                             *
                             * spring has two timeouts for aggregator timeout and release timeout
                             *  aggregator timeout is the timeout for the aggregator to complete the group
                             * release timeout is the timeout for the release strategy to be called.
                             */
                            log.info("### releaseStrategy ");
                            log.info("Thread (releaseStrategy): "+ Thread.currentThread().getName());
                            return group.size() == 3;
                        })
                        .outputProcessor(group -> {
                            /**
                             * is executed after release strategy is called.(meet)
                             * it help one to return a single response/message back after processing
                             * define how the multiple message can be combined to form a single message
                             */
                            log.info("### outputProcessor ");
                            log.info("Thread (outputProcessor): "+ Thread.currentThread().getName());
                            return MessageBuilder.withPayload(group.size() + " messages received").build();
                        })
                )
                .nullChannel();
//                .get();
    }

    @Bean
    public IntegrationFlow splitAndAggregateASync() {
        return IntegrationFlow.from("splitChannel")
                .enrichHeaders(h -> {
                    /**
                     * to update the message header with required information to be used in
                     *        - the correlation strategy in the aggregator.
                     *        - in the split message channel
                     *        or at any other stage of the process
                     */
                    h.header("appId", "21A4");
                    h.header("customSize", "T(payload.appSettingDtoList.size())");
                })
                .channel(asyncSplitChannel) //this send each message item asynchronously to a thread in pool to be processed
                .split(AppSettingList.class, AppSettingList::getAppSettingDtoList) //process each item in list synchronously
                .handle(new ServiceActivator(), "processItem") //handle each split item
                .aggregate(aggregatorSpec -> aggregatorSpec //aggregate back to a single message
                        .correlationStrategy(message -> {
                            /**
                             * define how all the split items can be grouped together to form one single output
                             * the final group will be used to determined the release strategy
                             * and the last step(outputProcessor)
                             *
                             * return message.getHeaders().get("correlationId"); //spring automatically set correlationId which is the groupId
                             * return message.getHeaders().get("sequenceSize"); //spring automatically set sequenceSize which is the total count of items in the split
                             * return message.getHeaders().get("sequenceNumber"); //spring automatically set sequenceNumber for each item that get processed (it is the position in the list)
                             */
                            log.info("### outputProcessor ");
                            log.info("Thread (outputProcessor): "+ Thread.currentThread().getName());
                            log.info("correlationId: "+ message.getHeaders().get("correlationId"));
                            log.info("sequenceSize: "+ message.getHeaders().get("sequenceSize"));
                            log.info("sequenceNumber: "+ message.getHeaders().get("sequenceNumber"));
                            log.info("custom header (appId): "+ message.getHeaders().get("appId"));
                            log.info("custom header (customSize): "+ message.getHeaders().get("customSize"));
                            try {
                                log.info("Headers {}", new ObjectMapper().writeValueAsString(message.getHeaders()));
                                log.info("Messages {}", new ObjectMapper().writeValueAsString(message.getPayload()));
                            } catch (JsonProcessingException e) {
                                throw new RuntimeException(e);
                            }
                            //we retrieve appSettingDtoList from message header and use to group the processed items by appId
                            return Long.parseLong(Objects.requireNonNull(message.getHeaders().get("sequenceSize")).toString())  == 3;
                        })
                        .releaseStrategy(group -> { //a release strategy to release the entire group
                            /**
                             * define when the group can be completed by making release strategy return true
                             * it is similar to the predicate in the filter.
                             * the strategy is evaluated for each received message
                             * if the release strategy return true, it will trigger a call to the output processor.
                             * if it return false, it will keep accumulating the messages into the group.
                             * the group will not be released, and no call will be made to the output processor
                             *
                             * Note: that release strategy is useful for long-running exchanges where the producer might send a number of messages
                             * with the same correlation and there is no way to know the total number of messages that will be sent in advance.
                             * release strategy is optional. If its not provided, the group will be release when the complete group is received
                             *
                             * spring will wait for the group to complete before making the call to the outputProcessor and the split message channel.
                             * it will wait indefinitely for the complete group if no release strategy is provided.
                             * it is good practice to provide a timeout for the aggregator if no release strategy is provided.
                             *
                             * spring has two timeouts for aggregator timeout and release timeout
                             *  aggregator timeout is the timeout for the aggregator to complete the group
                             * release timeout is the timeout for the release strategy to be called.
                             */
                            log.info("### releaseStrategy ");
                            log.info("Thread (releaseStrategy): "+ Thread.currentThread().getName());
                            return group.size() == 3;
                        })
                        .outputProcessor(group -> {
                            /**
                             * is executed after release strategy is called.(meet)
                             * it help one to return a single response/message back after processing
                             * define how the multiple message can be combined to form a single message
                             */
                            log.info("### outputProcessor ");
                            log.info("Thread (outputProcessor): "+ Thread.currentThread().getName());
                            return MessageBuilder.withPayload(group.size() + " messages received").build();
                        })
                )
                .nullChannel();
//                .get();
    }
}
