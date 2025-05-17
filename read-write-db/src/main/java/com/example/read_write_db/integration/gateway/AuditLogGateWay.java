package com.example.read_write_db.integration.gateway;

/**
 * Created by Sherif.Abdulraheem 16/05/2025 - 23:39
 **/

import com.example.read_write_db.dto.AppSettingDto;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

/**
 * Gateway for publishing audit logs for this integration
 * Creates channels to produce a message containing a configuration object
 * for the purpose of creating an AuditLog in the database.
 */
@MessagingGateway
public interface AuditLogGateWay {
    /**
     * REQUEST AND REPLY CHANNEL
     * requestChannel: indicates the channel we need to write the request message into.
     * replyChannel: indicates the channel we need to read a response message from.
     *
     * Every activator listening to that channel(replyChannel) will be called (depending on channel type) and each will receive that message
     * as input. The output of each activator will be sent to the specified outputChannel.
     *
     * Note: the number of consumer listening depends on the channel type (e.g. PublishSubscribeChannel has one, but
     * RoundRobinChannel will have all of them, QueueChannel has one etc.)
     */
    @Gateway(requestChannel = "serviceActivatorInputChannel", replyChannel = "serviceActivatorOutputChannel")
    String processLog(AppSettingDto appSetting);

    /**
     * REQUEST AND REPLY CHANNEL
     * requestChannel: indicates the channel we need to write the request message into.
     *
     */
    @Gateway(requestChannel = "serviceActivatorInputChannel")
    void sendToChannelNoReply(AppSettingDto appSetting);
}
