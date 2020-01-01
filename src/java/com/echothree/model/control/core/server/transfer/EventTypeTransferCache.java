// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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
import com.echothree.model.control.core.server.CoreControl;
import com.echothree.model.data.core.server.entity.EventType;
import com.echothree.model.data.user.server.entity.UserVisit;

public class EventTypeTransferCache
        extends BaseCoreTransferCache<EventType, EventTypeTransfer> {
    
    /** Creates a new instance of EventTypeTransferCache */
    public EventTypeTransferCache(UserVisit userVisit, CoreControl coreControl) {
        super(userVisit, coreControl);
    }
    
    public EventTypeTransfer getEventTypeTransfer(EventType eventType) {
        EventTypeTransfer eventTypeTransfer = get(eventType);
        
        if(eventTypeTransfer == null) {
            String eventTypeName = eventType.getEventTypeName();
            String description = coreControl.getBestEventTypeDescription(eventType, getLanguage());
            
            eventTypeTransfer = new EventTypeTransfer(eventTypeName, description);
            put(eventType, eventTypeTransfer);
        }
        return eventTypeTransfer;
    }
    
}
