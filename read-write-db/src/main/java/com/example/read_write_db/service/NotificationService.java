package com.example.read_write_db.service;

import com.example.read_write_db.event.ExportedEvent;
import com.example.read_write_db.model.OutboxEvent;
import com.example.read_write_db.repo.write.OutboxEventRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * Created by Sherif.Abdulraheem 11/03/2025 - 23:28
 **/

@Service
@Slf4j
public class NotificationService {
    private final OutboxEventRepo outboxEvents;
    public NotificationService(OutboxEventRepo outboxEvents) {
        this.outboxEvents = outboxEvents;
    }

    @Async
    public void publishAsync(ExportedEvent exportedEvent){
        OutboxEvent outboxEventSaved = outboxEvents.save(of(exportedEvent));
        outboxEvents.deleteById(outboxEventSaved.getId());
    }

    @Transactional
    public OutboxEvent publish(ExportedEvent exportedEvent){
        OutboxEvent outboxEventSaved = outboxEvents.save(of(exportedEvent));
        outboxEvents.deleteById(outboxEventSaved.getId());
        return outboxEventSaved;
    }

    private static OutboxEvent of(ExportedEvent exportedEvent) {
        return OutboxEvent.builder()
                .aggregateId(exportedEvent.getAggregateId())
                .aggregateType(exportedEvent.getAggregateType())
                .type(exportedEvent.getType())
                .timestamp(exportedEvent.getTimestamp())
                .payload(exportedEvent.getPayload()).build();
    }

//    @Scheduled(fixedRate = 1000)
//    public void cleanOutBoc(){
//        log.info("Clean out outbox");
//        outboxEvents.deleteAll();
//    }
}
