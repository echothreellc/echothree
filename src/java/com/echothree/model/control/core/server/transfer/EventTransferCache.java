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

package com.echothree.model.control.core.server.transfer;

import com.echothree.model.control.core.common.transfer.EventTransfer;
import com.echothree.model.control.core.server.control.CoreControl;
import com.echothree.model.control.core.server.control.EventControl;
import com.echothree.model.data.core.server.entity.Event;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class EventTransferCache
        extends BaseCoreTransferCache<Event, EventTransfer> {

    CoreControl coreControl = Session.getModelController(CoreControl.class);
    EventControl eventControl = Session.getModelController(EventControl.class);

    /** Creates a new instance of EventTransferCache */
    public EventTransferCache() {
        super();
    }
    
    public EventTransfer getEventTransfer(UserVisit userVisit, Event event) {
        var eventTransfer = get(event);
        
        if(eventTransfer == null) {
            var entityInstanceTransferCache = coreControl.getCoreTransferCaches(userVisit).getEntityInstanceTransferCache();
            var unformattedEventTime = event.getEventTime();
            var eventTime = formatTypicalDateTime(userVisit, unformattedEventTime);
            var eventTimeSequence = event.getEventTimeSequence();
            var entityInstanceTransfer = entityInstanceTransferCache.getEntityInstanceTransfer(event.getEntityInstance(), false, false, false, false);
            var eventTypeTransfer = eventControl.getEventTypeTransfer(userVisit, event.getEventType());
            var relatedEntityInstance = event.getRelatedEntityInstance();
            var relatedEntityInstanceTransfer = relatedEntityInstance == null ? null : entityInstanceTransferCache.getEntityInstanceTransfer(relatedEntityInstance, false, false, false, false);
            var relatedEventType = event.getRelatedEventType();
            var relatedEventTypeTransfer = relatedEventType == null ? null : eventControl.getEventTypeTransfer(userVisit, relatedEventType);
            var createdBy = event.getCreatedBy();
            var createdByTransfer = createdBy == null ? null : entityInstanceTransferCache.getEntityInstanceTransfer(createdBy, false, false, false, false);

            eventTransfer = new EventTransfer(unformattedEventTime, eventTime, eventTimeSequence, entityInstanceTransfer, eventTypeTransfer,
                    relatedEntityInstanceTransfer, relatedEventTypeTransfer, createdByTransfer);
            put(userVisit, event, eventTransfer);
        }
        
        return eventTransfer;
    }
    
}
