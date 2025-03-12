package com.example.read_write_db.event.type;

import com.example.read_write_db.event.ExportedEvent;
import com.example.read_write_db.model.AppSetting;
import com.example.read_write_db.model.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;
import java.time.LocalDateTime;

/**
 * Created by Sherif.Abdulraheem 08/03/2025 - 23:47
 **/
public class UserDetailsEvent implements ExportedEvent {

    private final long id;
    private static final ObjectMapper mapper = new ObjectMapper();
    private final String payload;
    private final Instant timestamp;

    public UserDetailsEvent(long id, String payload) {
        this.id = id;
        this.payload = payload;
        this.timestamp = Instant.now();
    }

    public static UserDetailsEvent of(
            User user, AppSetting appSetting
    ) throws JsonProcessingException {

        String appSettingStr = mapper.writeValueAsString(appSetting);

        ObjectNode asJson = mapper.createObjectNode()
                .put("firstName", user.getFirstName())
                .put("country", user.getCountry())
                .put("appSetting",appSettingStr)
                .put("userId", user.getId());

        String jsonString = asJson.toString();
        return new UserDetailsEvent(user.getId(), jsonString);
    }

    @Override
    public Long getAggregateId() {
        return id;
    }

    @Override
    public String getAggregateType() {
        return "ReadWrite.events";
    }

    @Override
    public String getType() {
        return "UserDetails";
    }

    @Override
    public Instant getTimestamp() {
        return timestamp;
    }

    @Override
    public String  getPayload() {
        return payload;
    }
}
