package com.example.read_write_db.event;

import com.example.read_write_db.model.OutboxEvent;
import com.example.read_write_db.repo.write.OutboxEvents;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;

import java.util.UUID;

/**
 * Created by Sherif.Abdulraheem 3/8/2025 - 4:55 PM
 **/
@RequiredArgsConstructor
public class CDCEventListener {
    private final OutboxEvents events;

    @EventListener
    public void handleOutboxEvent(ExportedEvent event) {

        OutboxEvent outboxEvent = events.save(of(event));

        /***
         * Delete the event once written, so that the outbox doesn't grow.
         * the CDC eventing polls the database log entry and not the table in the database
         */
        events.delete(outboxEvent);
    }

    private static OutboxEvent of(ExportedEvent exportedEvent) {
        return OutboxEvent.builder()
                .id(UUID.randomUUID())
                .aggregateId(exportedEvent.getAggregateId())
                .aggregateType(exportedEvent.getAggregateType())
                .type(exportedEvent.getType())
                .timestamp(exportedEvent.getTimestamp())
                .payload(exportedEvent.getPayload()).build();

    }
}
