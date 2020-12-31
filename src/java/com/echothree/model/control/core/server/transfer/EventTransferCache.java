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

package com.echothree.model.control.core.server.transfer;

import com.echothree.model.control.core.common.transfer.EntityInstanceTransfer;
import com.echothree.model.control.core.common.transfer.EventTransfer;
import com.echothree.model.control.core.common.transfer.EventTypeTransfer;
import com.echothree.model.control.core.server.control.CoreControl;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.core.server.entity.Event;
import com.echothree.model.data.core.server.entity.EventType;
import com.echothree.model.data.user.server.entity.UserVisit;

public class EventTransferCache
        extends BaseCoreTransferCache<Event, EventTransfer> {
    
    /** Creates a new instance of EventTransferCache */
    public EventTransferCache(UserVisit userVisit, CoreControl coreControl) {
        super(userVisit, coreControl);
    }
    
    public EventTransfer getEventTransfer(Event event) {
        EventTransfer eventTransfer = get(event);
        
        if(eventTransfer == null) {
            EntityInstanceTransferCache entityInstanceTransferCache = coreControl.getCoreTransferCaches(userVisit).getEntityInstanceTransferCache();
            Long unformattedEventTime = event.getEventTime();
            String eventTime = formatTypicalDateTime(unformattedEventTime);
            Integer eventTimeSequence = event.getEventTimeSequence();
            EntityInstanceTransfer entityInstanceTransfer = entityInstanceTransferCache.getEntityInstanceTransfer(event.getEntityInstance(), false, false, false, false, false);
            EventTypeTransfer eventTypeTransfer = coreControl.getEventTypeTransfer(userVisit, event.getEventType());
            EntityInstance relatedEntityInstance = event.getRelatedEntityInstance();
            EntityInstanceTransfer relatedEntityInstanceTransfer = relatedEntityInstance == null ? null : entityInstanceTransferCache.getEntityInstanceTransfer(relatedEntityInstance, false, false, false, false, false);
            EventType relatedEventType = event.getRelatedEventType();
            EventTypeTransfer relatedEventTypeTransfer = relatedEventType == null ? null : coreControl.getEventTypeTransfer(userVisit, relatedEventType);
            EntityInstance createdBy = event.getCreatedBy();
            EntityInstanceTransfer createdByTransfer = createdBy == null ? null : entityInstanceTransferCache.getEntityInstanceTransfer(createdBy, false, false, false, false, false);

            eventTransfer = new EventTransfer(unformattedEventTime, eventTime, eventTimeSequence, entityInstanceTransfer, eventTypeTransfer,
                    relatedEntityInstanceTransfer, relatedEventTypeTransfer, createdByTransfer);
            put(event, eventTransfer);
        }
        
        return eventTransfer;
    }
    
}
