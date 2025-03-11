package com.example.custom_debezium_tranformer.transformer;

import org.apache.kafka.common.config.ConfigDef;
import org.apache.kafka.connect.connector.ConnectRecord;
import org.apache.kafka.connect.data.Schema;
import org.apache.kafka.connect.header.Headers;
import org.apache.kafka.connect.transforms.Transformation;

import org.apache.kafka.connect.data.Struct;
import java.util.Map;

/**
 * Created by Sherif.Abdulraheem 11/03/2025 - 12:38
 **/
public class CustomTransformation<R extends ConnectRecord<R>> implements Transformation<R> {

    @Override
    public R apply(R sourceRecord) {
        Struct kstruct = (Struct) sourceRecord.value();
        String dbOperation = kstruct.getString("op");

        if("c".equalsIgnoreCase(dbOperation)) {
            Struct after = (Struct) kstruct.get("after");
            String eventId = after.getString("id");
            String payload = after.getString("payload");

            //aggregate_type will be use to differentiate the topic
            String eventTopic = after.getString("aggregate_type");

            //the type of event going to this topic (eventTopic)
            String eventType = after.getString("type").toLowerCase();

            //message ordering key or use for partitioning
            Long aggregateId = after.getInt64("aggregate_id");


            Headers headers = sourceRecord.headers();
            headers.addString("eventId", eventId);
            headers.addString("eventType", eventType);
            headers.addString("eventType", eventType);
//
//            sourceRecord = sourceRecord.newRecord( topic, partition, keySchema, key, valueSchema, value, timestamp, header)

            sourceRecord = sourceRecord.newRecord(
                    eventTopic, null, Schema.STRING_SCHEMA, aggregateId, null, payload, sourceRecord.timestamp(), headers
            );


        }
        return sourceRecord;
    }

    @Override
    public ConfigDef config() {
        return new ConfigDef();
    }

    @Override
    public void close() {

    }

    @Override
    public void configure(Map<String, ?> map) {

    }
}
