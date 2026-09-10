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
import io.cloudevents.core.builder.CloudEventBuilder;
import io.cloudevents.jackson.JsonFormat;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.UUID;
import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.apache.kafka.common.header.internals.RecordHeaders;

@ApplicationScoped
public class EventTopic {

    @Resource(name = "java:/KafkaConnectionFactory")
    KafkaConnectionFactory kafkaConnectionFactory;

    // Kafka
    private static final String TOPIC = "echothree-events-json";

    // CloudEvents
    private static final URI EVENT_SOURCE = URI.create("urn:echothree:events");

    private static final Headers HEADERS_CLOUD_EVENT = new RecordHeaders()
            .add(new RecordHeader("content-type", JsonFormat.CONTENT_TYPE.getBytes(StandardCharsets.UTF_8)));

    // Keep the namespace stable so the same eventId always produces the same UUIDv5.
    private static final UUID NAMESPACE = Generators.nameBasedGenerator(NameBasedGenerator.NAMESPACE_URL).generate(EVENT_SOURCE.toString());
    private static final NameBasedGenerator EVENT_ID_GENERATOR = Generators.nameBasedGenerator(NAMESPACE);

    // Jackson
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final JsonFormat CLOUD_EVENT_JSON_FORMAT = new JsonFormat();

    protected EventTopic() {}

    public void sendEvent(Event event) {
        if(kafkaConnectionFactory != null) {
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

//                    var value = "eventId = " + eventId
//                            + ", eventTime = " + eventTime
//                            + ", eventTimeSequence = " + eventTimeSequence
//                            + ", entityRef = " + entityRef
//                            + ", id = " + id
//                            + ", eventType = " + eventTypeName
//                            + ", relatedEntityInstance = " + relatedEntityRef
//                            + ", relatedEventType = " + relatedEventTypeName
//                            + ", createdByEntityInstance = " + createdByEntityRef;

//                    var eventValue = com.echothree.model.avro.core.common.Event.newBuilder()
//                            .setEventId(eventId)
//                            .setEventTime(eventTime)
//                            .setEventTimeSequence(eventTimeSequence)
//                            .setEntityRef(entityRef)
//                            .setId(id)
//                            .setEventTypeName(eventTypeName)
//                            .setRelatedEntityRef(relatedEntityRef)
//                            .setRelatedEventTypeName(relatedEventTypeName)
//                            .setCreatedByEntityRef(createdByEntityRef)
//                            .build();

                    var eventJsonObject = new com.echothree.model.control.core.server.kafka.Event(eventId, eventTime,
                            eventTimeSequence, entityRef, id, eventTypeName, relatedEntityRef, relatedEventTypeName,
                            createdByEntityRef);
                    var cloudEvent = CloudEventBuilder.v1()
                            .withId(EVENT_ID_GENERATOR.generate(eventId.toString()).toString())
                            .withSource(EVENT_SOURCE)
                            .withType("com.echothree.event." + eventTypeName)
                            .withSubject(entityRef)
                            .withTime(Instant.ofEpochMilli(eventTime).atOffset(ZoneOffset.UTC))
                            .withData(MediaType.JSON_UTF_8.toString(), OBJECT_MAPPER.writeValueAsBytes(eventJsonObject))
                            .build();
                    var eventJson = new String(CLOUD_EVENT_JSON_FORMAT.serialize(cloudEvent), StandardCharsets.UTF_8);

                    var future = kafkaConnection.send(new ProducerRecord<>(TOPIC, null,
                            eventTime, entityRef, eventJson, HEADERS_CLOUD_EVENT));

                    future.get();
                }
            } catch(Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

}
