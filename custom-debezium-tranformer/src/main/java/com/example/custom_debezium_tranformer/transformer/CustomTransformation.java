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

            //unique identity of the event put in the header
            String eventId = after.getString("id");

            //event payload
            String payload = after.getString("payload");

            //aggregate_type will be use to differentiate the topic
            String eventTopic = after.getString("aggregate_type");

            //the type of event going to this topic (eventType)
            String eventType = after.getString("type");

            //message ordering key or use for partitioning
            Long aggregateId = after.getInt64("aggregate_id");


            //build the custom event headers
            Headers headers = sourceRecord.headers();
            headers.addString("eventId", eventId);
            headers.addString("eventType", eventType);
            headers.addString("eventDescription", "Scholastic CDC Events");

            /***
             * Recreate the source record with the new headers
             *
             * sourceRecord = sourceRecord.newRecord(
             *  topic, partition, keySchema, key,
             *  valueSchema, value, timestamp, header
             *  )
             *
             * Parameter	Explanation
             * topic	    The Kafka topic name (dynamically set based on eventType).
             * partition	Specify the partition. when null Kafka automatically assigns the partition (default behavior).
             * keySchema	The key format e.g Schema.STRING_SCHEMA for String, Schema.INT64 for Long. etc
             * key	        The key value . kafka will use this partitioning if partition is not specify and will be used for message ordering.
             * valueSchema	The payload schema type. When null or not explicitly defined, assuming JSON format. e.g AVRO etc
             * value	    The actual event data from the database (JSON format). must match the format specified in valueSchema
             * timestamp	the message timestamp as to when its created, is good to Keeps the original event timestamp. sourceRecord.timestamp()
             * headers	    Includes additional metadata (e.g., "eventId" header).
             */
            sourceRecord = sourceRecord.newRecord(
                    eventTopic, null, Schema.INT64_SCHEMA, aggregateId, null,
                    payload, sourceRecord.timestamp(), headers
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
