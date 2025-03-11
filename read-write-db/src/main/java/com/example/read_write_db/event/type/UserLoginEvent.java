package com.example.read_write_db.event.type;

import com.example.read_write_db.event.ExportedEvent;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Created by Sherif.Abdulraheem 08/03/2025 - 23:47
 **/
public class UserLoginEvent implements ExportedEvent {

    private final long id;
    private static final ObjectMapper mapper = new ObjectMapper();
    private final String payload;
    private final Instant timestamp;

    public UserLoginEvent(long id, String payload) {
        this.id = id;
        this.payload = payload;
        this.timestamp = Instant.now();
    }

    public static UserLoginEvent of(
            String firstName, @NotNull LocalDateTime loginDateTime, String status, long id
    ) {
        ObjectNode asJson = mapper.createObjectNode()
                .put("firstName", firstName)
                .put("loginTime", loginDateTime.toString())
                .put("status",status)
                .put("userId", id);
        String jsonString = asJson.toString();
        return new UserLoginEvent(id, jsonString);
    }

    @Override
    public Long getAggregateId() {
        return id;
    }

    @Override
    public String getAggregateType() {
        return "ReadWrite.event";
    }

    @Override
    public String getType() {
        return "UserLogin";
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
