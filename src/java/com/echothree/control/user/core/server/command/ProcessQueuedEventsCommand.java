// --------------------------------------------------------------------------------
// Copyright 2002-2025 Echo Three, LLC
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

import com.echothree.model.control.core.server.control.EventControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.data.core.server.entity.EventSubscriber;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
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
    public ProcessQueuedEventsCommand() {
        super(COMMAND_SECURITY_DEFINITION, false);
    }

    @Override
    protected BaseResult execute() {
        var eventControl = Session.getModelController(EventControl.class);
        var remainingTime = (long) 2 * 60 * 1000; // 2 minutes
        var queuedEvents = eventControl.getQueuedEventsForUpdate();

        for(var queuedEvent : queuedEvents) {
            var startTime = System.currentTimeMillis();
            Set<EventSubscriber> eventSubscribers = new HashSet<>();
            var event = queuedEvent.getEvent();

            if(event != null) {
                // TODO: this should not be necessary, bug 444
                var eventType = event.getEventType();
                var entityInstance = event.getEntityInstance();
                var entityType = entityInstance.getEntityType();
                var eventSubscriberEventTypes = eventControl.getEventSubscriberEventTypes(eventType);
                var eventSubscriberEntityTypes = eventControl.getEventSubscriberEntityTypes(entityType, eventType);
                var eventSubscriberEntityInstances = eventControl.getEventSubscriberEntityInstances(entityInstance, eventType);

                eventSubscriberEventTypes.stream().map((eventSubscriberEventType) -> eventSubscriberEventType.getEventSubscriber()).filter((eventSubscriber) -> !eventSubscribers.contains(eventSubscriber)).map((eventSubscriber) -> {
                    eventControl.createQueuedSubscriberEvent(eventSubscriber, event);
                    return eventSubscriber;
                }).forEach((eventSubscriber) -> {
                    eventSubscribers.add(eventSubscriber);
                });

                eventSubscriberEntityTypes.stream().map((eventSubscriberEntityType) -> eventSubscriberEntityType.getEventSubscriber()).filter((eventSubscriber) -> !eventSubscribers.contains(eventSubscriber)).map((eventSubscriber) -> {
                    eventControl.createQueuedSubscriberEvent(eventSubscriber, event);
                    return eventSubscriber;
                }).forEach((eventSubscriber) -> {
                    eventSubscribers.add(eventSubscriber);
                });
                
                eventSubscriberEntityInstances.stream().map((eventSubscriberEntityInstance) -> eventSubscriberEntityInstance.getEventSubscriber()).filter((eventSubscriber) -> !eventSubscribers.contains(eventSubscriber)).map((eventSubscriber) -> {
                    eventControl.createQueuedSubscriberEvent(eventSubscriber, event);
                    return eventSubscriber;
                }).forEach((eventSubscriber) -> {
                    eventSubscribers.add(eventSubscriber);
                });

                eventControl.removeQueuedEvent(queuedEvent);

                remainingTime -= System.currentTimeMillis() - startTime;
                if(remainingTime < 0) {
                    break;
                }
            }
        }

        return null;
    }
}
