package com.example.read_write_db.integration.gateway;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.channel.ExecutorChannel;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.messaging.MessageChannel;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by Sherif.Abdulraheem 16/05/2025 - 23:29
 **/
@Configuration
@EnableIntegration
public class GateWayActivatorConfig {

    /***
     * Different channel types explain
     *
     * DirectChannel -> one to one routing [only one activator can read]
     * PublishSubscribeChannel -> one to many routing [ one and more activator can read]
     * QuedChannel -> one to one routing [ message is qued and one activator can read]
     * ExecutorChannel -> one to one routing [ Async activator can read]
     */


    @Bean
    public MessageChannel serviceActivatorInputChannel(){
        return new DirectChannel();
    }

    @Bean
    public MessageChannel serviceActivatorOutputChannel(){
        return new DirectChannel();
    }

    /**
     * PublishSubscribeChannel send copy of message to all subscriber,
     * concurrently using a task executor, therefore, subscriber excecution order
     * is not guaranted
     * subscriber may receive message in order it gets to channel e.g
     * [1, 2, 3] subA rcv [1 , 2 , 3] subB rcv [1 , 2 , 3]
     * but subA might be processing in 2 and subB processing in 1
     *
     * to ensure this you can use
     * new PublishSubscribeChannel(new SyncTaskExecutor()); this wait for
     * all consumer to process before pushing next message
     */

    @Bean
    public MessageChannel publishInputChannel(){
        return new PublishSubscribeChannel();
    }


    @Bean
    public Executor taskExecutor(){
        return Executors.newFixedThreadPool(5);
    }

    /**
     * Executor channel uses a task executor for async execution of handlers
     * it channel the message to a task executor and select a thread for
     * excecution
     *
     */
    @Bean
    public MessageChannel asyncChannel(){
        return new ExecutorChannel(taskExecutor());
    }

    @Bean("splitChannel")
    public MessageChannel splitChannel(){
        return new DirectChannel();
    }

    @Bean
    public MessageChannel asyncSplitChannel(){
        return new ExecutorChannel(taskExecutor());
    }

}
