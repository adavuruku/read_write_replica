package com.example.read_write_db.event;

import com.example.read_write_db.model.OutboxEvent;
import com.example.read_write_db.repo.write.OutboxEventRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

/**
 * Created by Sherif.Abdulraheem 3/8/2025 - 4:55 PM
 **/
@Service
@RequiredArgsConstructor
public class CDCEventListener {
    private final OutboxEventRepo events;

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
                .aggregateId(exportedEvent.getAggregateId())
                .aggregateType(exportedEvent.getAggregateType())
                .type(exportedEvent.getType())
                .timestamp(exportedEvent.getTimestamp())
                .payload(exportedEvent.getPayload()).build();

    }
}
