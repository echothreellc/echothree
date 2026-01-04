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

package com.echothree.model.control.core.common.transfer;

import com.echothree.util.common.transfer.BaseTransfer;

public class EventTypeTransfer
        extends BaseTransfer {
    
    String eventTypeName;
    String description;
    
    /** Creates a new instance of EventTypeTransfer */
    public EventTypeTransfer(String eventTypeName, String description) {
        this.eventTypeName = eventTypeName;
        this.description = description;
    }
    
    public String getEventTypeName() {
        return eventTypeName;
    }
    
    public String getDescription() {
        return description;
    }
    
}
