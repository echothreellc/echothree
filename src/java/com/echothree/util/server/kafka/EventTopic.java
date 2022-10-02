// --------------------------------------------------------------------------------
// Copyright 2002-2022 Echo Three, LLC
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

package com.echothree.util.server.kafka;

import com.echothree.model.data.core.server.entity.Event;
import com.echothree.util.server.string.EntityInstanceUtils;
import fish.payara.cloud.connectors.kafka.api.KafkaConnectionFactory;
import org.apache.kafka.clients.producer.ProducerRecord;

public class EventTopic {

    private static final String TOPIC = "echothree.events.text";

    private static final EventTopic instance = new EventTopic();

    public static EventTopic getInstance() {
        return instance;
    }

    KafkaConnectionFactory kafkaConnectionFactory = KafkaConnectionFactoryResource.getInstance().getKafkaConnectionFactory();
    EntityInstanceUtils entityInstanceUtils = EntityInstanceUtils.getInstance();

    protected EventTopic() {}

    public void sendEvent(Event event) {
        if(kafkaConnectionFactory != null) {
            try {
                try(var kafkaConnection = kafkaConnectionFactory.createConnection()) {
                    var eventTime = event.getEventTime();
                    var eventTimeSequence = event.getEventTimeSequence();
                    var entityInstance = event.getEntityInstance();
                    var entityRef = entityInstanceUtils.getEntityRefByEntityInstance(entityInstance);
                    var eventTypeName = event.getEventType().getEventTypeName();
                    var relatedEntityRef = entityInstanceUtils.getEntityRefByEntityInstance(event.getRelatedEntityInstance());
                    var relatedEventType = event.getRelatedEventType();
                    var relatedEventTypeName = relatedEventType == null ? null : relatedEventType.getEventTypeName();
                    var createdByEntityRef = entityInstanceUtils.getEntityRefByEntityInstance(event.getCreatedBy());

                    var value = "eventTime = " + eventTime
                            + ", eventTimeSequence = " + eventTimeSequence
                            + ", entityInstance = " + entityRef
                            + ", eventType = " + eventTypeName
                            + ", relatedEntityInstance = " + relatedEntityRef
                            + ", relatedEventType = " + relatedEventTypeName
                            + ", createdByEntityInstance = " + createdByEntityRef;

                    var future = kafkaConnection.send(new ProducerRecord<>(TOPIC, null,
                            eventTime, entityInstance.getEntityUniqueId().toString(), value));

                    future.get();
                }
            } catch(Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

}
