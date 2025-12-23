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

package com.echothree.model.control.core.server.eventbus;

import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.core.server.entity.Event;

public abstract class BaseEventSubscriber {

    protected void decodeEventAndApply(SentEvent se, Function5Arity<Event, EntityInstance, EventTypes, String, String> f5) {
        var event = se.getEvent();
        var entityInstance = event.getEntityInstance();
        var entityType = entityInstance.getEntityType();
        var lastEntityTypeDetail = entityType.getLastDetail();
        var entityTypeName = lastEntityTypeDetail.getEntityTypeName();
        var componentVendor = lastEntityTypeDetail.getComponentVendor();
        var componentVendorName = componentVendor.getLastDetail().getComponentVendorName();
        var eventType = EventTypes.valueOf(event.getEventType().getEventTypeName());

        f5.apply(event, entityInstance, eventType, componentVendorName, entityTypeName);
    }

}
