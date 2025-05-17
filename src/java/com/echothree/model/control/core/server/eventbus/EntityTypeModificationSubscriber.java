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
import com.echothree.model.control.core.server.control.CoreControl;
import com.echothree.model.control.core.server.control.EntityAliasControl;
import com.echothree.model.control.core.server.control.EntityTypeControl;
import com.echothree.model.control.core.server.control.EventControl;
import com.echothree.model.data.core.common.EntityTypeConstants;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.core.server.entity.Event;
import com.echothree.util.server.persistence.PersistenceUtils;
import com.echothree.util.server.persistence.Session;
import com.google.common.eventbus.Subscribe;

@SentEventSubscriber
public class EntityTypeModificationSubscriber
        extends BaseEventSubscriber {

    @Subscribe
    public void receiveSentEventForEntityAttributes(SentEvent se) {
        decodeEventAndApply(se, touchEntityAttributesIfEntityType);
    }

    private static final Function5Arity<Event, EntityInstance, EventTypes, String, String>
            touchEntityAttributesIfEntityType = (event, entityInstance, eventType, componentVendorName, entityTypeName) -> {
        if(EntityTypeConstants.COMPONENT_VENDOR_NAME.equals(componentVendorName)
                && EntityTypeConstants.ENTITY_TYPE_NAME.equals(entityTypeName)
                && (eventType == EventTypes.MODIFY || eventType == EventTypes.TOUCH)) {
            var coreControl = Session.getModelController(CoreControl.class);
            var eventControl = Session.getModelController(EventControl.class);
            var entityTypeControl = Session.getModelController(EntityTypeControl.class);
            var entityType = entityTypeControl.getEntityTypeByEntityInstance(entityInstance);
            var entityAttributes = coreControl.getEntityAttributesByEntityType(entityType);
            var createdBy = PersistenceUtils.getInstance().getBasePKFromEntityInstance(event.getCreatedBy());

            for(var entityAttribute : entityAttributes) {
                eventControl.sendEvent(entityAttribute.getPrimaryKey(), EventTypes.TOUCH,
                        entityType.getPrimaryKey(), eventType,
                        createdBy);
            }
        }
    };

    @Subscribe
    public void receiveSentEventForEntityAliases(SentEvent se) {
        decodeEventAndApply(se, touchEntityAliasTypesIfEntityType);
    }

    private static final Function5Arity<Event, EntityInstance, EventTypes, String, String>
            touchEntityAliasTypesIfEntityType = (event, entityInstance, eventType, componentVendorName, entityTypeName) -> {
        if(EntityTypeConstants.COMPONENT_VENDOR_NAME.equals(componentVendorName)
                && EntityTypeConstants.ENTITY_TYPE_NAME.equals(entityTypeName)
                && (eventType == EventTypes.MODIFY || eventType == EventTypes.TOUCH)) {
            var entityAliasControl = Session.getModelController(EntityAliasControl.class);
            var eventControl = Session.getModelController(EventControl.class);
            var entityTypeControl = Session.getModelController(EntityTypeControl.class);
            var entityType = entityTypeControl.getEntityTypeByEntityInstance(entityInstance);
            var entityAliasTypes = entityAliasControl.getEntityAliasTypesByEntityType(entityType);
            var createdBy = PersistenceUtils.getInstance().getBasePKFromEntityInstance(event.getCreatedBy());

            for(var entityAliasType : entityAliasTypes) {
                eventControl.sendEvent(entityAliasType.getPrimaryKey(), EventTypes.TOUCH,
                        entityType.getPrimaryKey(), eventType,
                        createdBy);
            }
        }
    };

}
