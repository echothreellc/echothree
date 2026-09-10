// --------------------------------------------------------------------------------
// Copyright 2002-2026 Echo Three, LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
// --------------------------------------------------------------------------------

package com.echothree.model.control.core.server.kafka;

import com.echothree.model.control.core.server.control.EntityInstanceControl;
import com.echothree.model.data.core.server.entity.Event;
import com.echothree.util.server.persistence.Session;
import com.echothree.util.server.string.EntityInstanceUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.impl.NameBasedGenerator;
import com.google.common.net.MediaType;
import fish.payara.cloud.connectors.kafka.api.KafkaConnectionFactory;
import io.cloudevents.CloudEvent;
import io.cloudevents.core.builder.CloudEventBuilder;
import io.cloudevents.jackson.JsonFormat;
import io.cloudevents.kafka.KafkaMessageFactory;
import java.io.IOException;
import java.net.URI;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.Future;
import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

@ApplicationScoped
public class EventTopic {

    @Resource(name = "java:/KafkaConnectionFactory")
    KafkaConnectionFactory kafkaConnectionFactory;

    // Feature flags
    private static final boolean SEND_JSON_EVENTS = true;
    private static final boolean SEND_AVRO_EVENTS = false;

    // Kafka
    private static final String TOPIC_JSON = "echothree-events-json";
    private static final String TOPIC_AVRO = "echothree-events-avro";

    // CloudEvents
    private static final URI EVENT_SOURCE = URI.create("urn:echothree:events");

    // Keep the namespace stable so the same eventId always produces the same UUIDv5.
    private static final UUID NAMESPACE = Generators.nameBasedGenerator(NameBasedGenerator.NAMESPACE_URL).generate(EVENT_SOURCE.toString());
    private static final NameBasedGenerator EVENT_ID_GENERATOR = Generators.nameBasedGenerator(NAMESPACE);

    // Jackson
    // Initialized only when JSON publishing is used.
    private static class JsonSerialization {
        private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
        private static final JsonFormat CLOUD_EVENT_JSON_FORMAT = new JsonFormat();
    }

    protected EventTopic() {}

    public void sendEvent(Event event) {
        if((SEND_JSON_EVENTS || SEND_AVRO_EVENTS) && kafkaConnectionFactory != null) {
            try {
                try(var kafkaConnection = kafkaConnectionFactory.createConnection()) {
                    var entityInstanceControl = Session.getModelController(EntityInstanceControl.class);
                    var eventId = event.getPrimaryKey().getEntityId();
                    var eventTime = event.getEventTime();
                    var eventTimeSequence = event.getEventTimeSequence();
                    var entityInstance = entityInstanceControl.ensureUuidForEntityInstance(event.getEntityInstance(), false);
                    var entityRef = EntityInstanceUtils.getEntityRefByEntityInstance(entityInstance);
                    var id = entityInstance.getUuid();
                    var eventTypeName = event.getEventType().getEventTypeName();
                    var relatedEntityRef = EntityInstanceUtils.getEntityRefByEntityInstance(event.getRelatedEntityInstance());
                    var relatedEventType = event.getRelatedEventType();
                    var relatedEventTypeName = relatedEventType == null ? null : relatedEventType.getEventTypeName();
                    var createdByEntityRef = EntityInstanceUtils.getEntityRefByEntityInstance(event.getCreatedBy());

                    var eventData = new com.echothree.model.control.core.server.kafka.Event(eventId, eventTime,
                            eventTimeSequence, entityRef, id, eventTypeName, relatedEntityRef, relatedEventTypeName,
                            createdByEntityRef);
                    var cloudEvent = CloudEventBuilder.v1()
                            .withId(EVENT_ID_GENERATOR.generate(eventId.toString()).toString())
                            .withSource(EVENT_SOURCE)
                            .withType("com.echothree.model.control.core.common.EventTypes." + eventTypeName)
                            .withSubject(entityRef)
                            .withTime(Instant.ofEpochMilli(eventTime).atOffset(ZoneOffset.UTC))
                            .build();
                    var futures = new ArrayList<Future<RecordMetadata>>(2);

                    if(SEND_JSON_EVENTS) {
                        futures.add(kafkaConnection.send(createJsonRecord(eventData, cloudEvent)));
                    }

                    if(SEND_AVRO_EVENTS) {
                        futures.add(kafkaConnection.send(createAvroRecord(eventData, cloudEvent)));
                    }

                    for(var future : futures) {
                        future.get();
                    }
                }
            } catch(Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    private ProducerRecord<String, byte[]> createJsonRecord(com.echothree.model.control.core.server.kafka.Event eventData,
            CloudEvent cloudEvent) throws IOException {
        var jsonCloudEvent = CloudEventBuilder.from(cloudEvent)
                .withData(MediaType.JSON_UTF_8.toString(), JsonSerialization.OBJECT_MAPPER.writeValueAsBytes(eventData))
                .build();

        return KafkaMessageFactory.createWriter(TOPIC_JSON, null, eventData.eventTime(), eventData.entityRef())
                .writeStructured(jsonCloudEvent, JsonSerialization.CLOUD_EVENT_JSON_FORMAT);
    }

    private ProducerRecord<String, byte[]> createAvroRecord(com.echothree.model.control.core.server.kafka.Event eventData,
            CloudEvent cloudEvent) throws IOException {
        var eventAvro = com.echothree.model.avro.core.common.Event.newBuilder()
                .setEventId(eventData.eventId())
                .setEventTime(eventData.eventTime())
                .setEventTimeSequence(eventData.eventTimeSequence())
                .setEntityRef(eventData.entityRef())
                .setId(eventData.id())
                .setEventTypeName(eventData.eventTypeName())
                .setRelatedEntityRef(eventData.relatedEntityRef())
                .setRelatedEventTypeName(eventData.relatedEventTypeName())
                .setCreatedByEntityRef(eventData.createdByEntityRef())
                .build();
        // Single-object encoding includes the schema fingerprint for the generated decoder.
        var avroBuffer = eventAvro.toByteBuffer();
        var avroBytes = new byte[avroBuffer.remaining()];

        avroBuffer.get(avroBytes);

        var avroCloudEvent = CloudEventBuilder.from(cloudEvent)
                .withData("avro/binary", avroBytes)
                .build();

        return KafkaMessageFactory.createWriter(TOPIC_AVRO, null, eventData.eventTime(), eventData.entityRef())
                .writeBinary(avroCloudEvent);
    }

}
