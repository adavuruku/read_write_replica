package com.example.read_write_db.event.type;

import com.example.read_write_db.event.ExportedEvent;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;
import java.time.LocalDateTime;

/**
 * Created by Sherif.Abdulraheem 08/03/2025 - 23:47
 **/
public class AppSettingCreatedEvent implements ExportedEvent {

    private final long id;
    private static final ObjectMapper mapper = new ObjectMapper();
    private final JsonNode payload;
    private final Instant timestamp;

    public AppSettingCreatedEvent(long id, JsonNode payload) {
        this.id = id;
        this.payload = payload;
        this.timestamp = Instant.now();
    }

    public static AppSettingCreatedEvent of(
            String firstName, String description, long id
    ) {
        ObjectNode asJson = mapper.createObjectNode()
                .put("firstName", firstName)
                .put("userId", id)
                .put("description", description);
        return new AppSettingCreatedEvent(id, asJson);
    }

    @Override
    public Long getAggregateId() {
        return id;
    }

    @Override
    public String getAggregateType() {
        return "ReadWrite";
    }

    @Override
    public String getType() {
        return "AppSettingCreated";
    }

    @Override
    public Instant getTimestamp() {
        return timestamp;
    }

    @Override
    public JsonNode getPayload() {
        return payload;
    }
}
