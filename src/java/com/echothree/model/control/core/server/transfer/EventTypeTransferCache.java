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

import com.echothree.model.control.core.common.transfer.EventTypeTransfer;
import com.echothree.model.control.core.server.control.EventControl;
import com.echothree.model.data.core.server.entity.EventType;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class EventTypeTransferCache
        extends BaseCoreTransferCache<EventType, EventTypeTransfer> {

    EventControl eventControl = Session.getModelController(EventControl.class);

    /** Creates a new instance of EventTypeTransferCache */
    public EventTypeTransferCache(UserVisit userVisit) {
        super(userVisit);
    }
    
    public EventTypeTransfer getEventTypeTransfer(EventType eventType) {
        var eventTypeTransfer = get(eventType);
        
        if(eventTypeTransfer == null) {
            var eventTypeName = eventType.getEventTypeName();
            var description = eventControl.getBestEventTypeDescription(eventType, getLanguage());
            
            eventTypeTransfer = new EventTypeTransfer(eventTypeName, description);
            put(eventType, eventTypeTransfer);
        }
        return eventTypeTransfer;
    }
    
}
