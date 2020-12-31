// --------------------------------------------------------------------------------
// Copyright 2002-2021 Echo Three, LLC
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

package com.echothree.control.user.core.server.command;

import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.core.server.entity.EntityType;
import com.echothree.model.data.core.server.entity.Event;
import com.echothree.model.data.core.server.entity.EventSubscriber;
import com.echothree.model.data.core.server.entity.EventSubscriberEntityInstance;
import com.echothree.model.data.core.server.entity.EventSubscriberEntityType;
import com.echothree.model.data.core.server.entity.EventSubscriberEventType;
import com.echothree.model.data.core.server.entity.EventType;
import com.echothree.model.data.core.server.entity.QueuedEvent;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ProcessQueuedEventsCommand
        extends BaseSimpleCommand {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null)
                )));
    }
    
    /** Creates a new instance of ProcessQueuedEventsCommand */
    public ProcessQueuedEventsCommand(UserVisitPK userVisitPK) {
        super(userVisitPK, COMMAND_SECURITY_DEFINITION, false);
    }

    @Override
    protected BaseResult execute() {
        var coreControl = getCoreControl();
        long remainingTime = (long) 2 * 60 * 1000; // 2 minutes
        List<QueuedEvent> queuedEvents = coreControl.getQueuedEventsForUpdate();

        for(var queuedEvent : queuedEvents) {
            long startTime = System.currentTimeMillis();
            Set<EventSubscriber> eventSubscribers = new HashSet<>();
            Event event = queuedEvent.getEvent();

            if(event != null) {
                // TODO: this should not be necessary, bug 444
                EventType eventType = event.getEventType();
                EntityInstance entityInstance = event.getEntityInstance();
                EntityType entityType = entityInstance.getEntityType();
                List<EventSubscriberEventType> eventSubscriberEventTypes = coreControl.getEventSubscriberEventTypes(eventType);
                List<EventSubscriberEntityType> eventSubscriberEntityTypes = coreControl.getEventSubscriberEntityTypes(entityType, eventType);
                List<EventSubscriberEntityInstance> eventSubscriberEntityInstances = coreControl.getEventSubscriberEntityInstances(entityInstance, eventType);

                eventSubscriberEventTypes.stream().map((eventSubscriberEventType) -> eventSubscriberEventType.getEventSubscriber()).filter((eventSubscriber) -> !eventSubscribers.contains(eventSubscriber)).map((eventSubscriber) -> {
                    coreControl.createQueuedSubscriberEvent(eventSubscriber, event);
                    return eventSubscriber;
                }).forEach((eventSubscriber) -> {
                    eventSubscribers.add(eventSubscriber);
                });

                eventSubscriberEntityTypes.stream().map((eventSubscriberEntityType) -> eventSubscriberEntityType.getEventSubscriber()).filter((eventSubscriber) -> !eventSubscribers.contains(eventSubscriber)).map((eventSubscriber) -> {
                    coreControl.createQueuedSubscriberEvent(eventSubscriber, event);
                    return eventSubscriber;
                }).forEach((eventSubscriber) -> {
                    eventSubscribers.add(eventSubscriber);
                });
                
                eventSubscriberEntityInstances.stream().map((eventSubscriberEntityInstance) -> eventSubscriberEntityInstance.getEventSubscriber()).filter((eventSubscriber) -> !eventSubscribers.contains(eventSubscriber)).map((eventSubscriber) -> {
                    coreControl.createQueuedSubscriberEvent(eventSubscriber, event);
                    return eventSubscriber;
                }).forEach((eventSubscriber) -> {
                    eventSubscribers.add(eventSubscriber);
                });

                coreControl.removeQueuedEvent(queuedEvent);

                remainingTime -= System.currentTimeMillis() - startTime;
                if(remainingTime < 0) {
                    break;
                }
            }
        }

        return null;
    }
}
