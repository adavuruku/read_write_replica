package com.example.read_write_db.event;

import com.fasterxml.jackson.databind.JsonNode;

import java.time.Instant;

/**
 * Created by Sherif.Abdulraheem 3/8/2025 - 4:52 PM
 * <p>
 * Describes an event that should be exported via the "outbox_events" table.</p>
 **/
public interface ExportedEvent {
    /**
     * The id of the aggregate root affected by a given event.  For example, the transaction id in case of events
     * relating to a transaction, or payment (and value) legs of that transaction.
     * This is used to ensure ordering of events within an aggregate type.
     * Events pertaining to a sub-entity contained within an aggregate should use the id of the containing aggregate root.
     * This id will be used as the key for Kafka messages later on.
     * That way, all events pertaining to one aggregate root or any of its contained sub-entities will go into the same partition of that Kafka topic,
     * which ensures that consumers of that topic will consume all the events related to one and the same aggregate in the exact order as they were produced.
     */
    Long getAggregateId();

    /**
     * The type of the aggregate root affected by the event.  For example, "transaction" in case of events relating
     * to a transaction, or payment (and value) legs of that transaction.  This value will be used to route events to corresponding topics in Kafka.
     * Note also that events pertaining to a child entity contained within one such aggregate should use that same type.
     * <p>
     * Further readings:
     * <p>
     * <a href="https://inoio.de/blog/2021/05/21/kafka-topic-design-guidelines/">Should You Put Several Event Types in the Same Kafka Topic?</a>
     * <p>
     * <a href="https://www.confluent.io/blog/put-several-event-types-kafka-topic/">Kafka Topic Design Guidelines</a>
     */
    String getAggregateType();

    /**
     * The type of an event.  For example, "Transaction Submitted" or "Transaction Failed" for events that
     * belong to a given aggregate type such as "Transaction".
     * Allows consumers to trigger suitable event handlers.
     */
    String getType();

    /**
     * The application timestamp at which the event occurred.
     */
    Instant getTimestamp();

    /**
     * The actual event payload as a valid JSON string.
     */
    JsonNode getPayload();
}
