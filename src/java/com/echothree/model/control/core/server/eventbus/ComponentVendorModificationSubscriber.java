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

package com.echothree.model.control.core.server.eventbus;

import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.core.server.control.ComponentVendorControl;
import com.echothree.model.control.core.server.control.CoreControl;
import com.echothree.model.control.core.server.control.EntityTypeControl;
import com.echothree.model.data.core.common.ComponentVendorConstants;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.core.server.entity.Event;
import com.echothree.util.server.persistence.PersistenceUtils;
import com.echothree.util.server.persistence.Session;
import com.google.common.eventbus.Subscribe;

@SentEventSubscriber
public class ComponentVendorModificationSubscriber
        extends BaseEventSubscriber {

    @Subscribe
    public void receiveSentEvent(SentEvent se) {
        decodeEventAndApply(se, touchEntityTypesIfComponentVendor);
    }

    private static final Function5Arity<Event, EntityInstance, EventTypes, String, String>
            touchEntityTypesIfComponentVendor = (event, entityInstance, eventType, componentVendorName, entityTypeName) -> {
        if(ComponentVendorConstants.COMPONENT_VENDOR_NAME.equals(componentVendorName)
                && ComponentVendorConstants.ENTITY_TYPE_NAME.equals(entityTypeName)
                && (eventType == EventTypes.MODIFY || eventType == EventTypes.TOUCH)) {
            var coreControl = Session.getModelController(CoreControl.class);
            var componentVendorControl = Session.getModelController(ComponentVendorControl.class);
            var entityTypeControl = Session.getModelController(EntityTypeControl.class);
            var componentVendor = componentVendorControl.getComponentVendorByEntityInstance(entityInstance);
            var entityTypes = entityTypeControl.getEntityTypesByComponentVendor(componentVendor);
            var createdBy = PersistenceUtils.getInstance().getBasePKFromEntityInstance(event.getCreatedBy());

            for(var entityType : entityTypes) {
                coreControl.sendEvent(entityType.getPrimaryKey(), EventTypes.TOUCH,
                        componentVendor.getPrimaryKey(), eventType,
                        createdBy);
            }
        }
    };

}
