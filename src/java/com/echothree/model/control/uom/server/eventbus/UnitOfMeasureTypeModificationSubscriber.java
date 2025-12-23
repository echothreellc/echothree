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

package com.echothree.model.control.uom.server.eventbus;

import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.core.server.control.EventControl;
import com.echothree.model.control.core.server.eventbus.BaseEventSubscriber;
import com.echothree.model.control.core.server.eventbus.Function5Arity;
import com.echothree.model.control.core.server.eventbus.SentEvent;
import com.echothree.model.control.core.server.eventbus.SentEventSubscriber;
import com.echothree.model.control.uom.server.control.UomControl;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.core.server.entity.Event;
import com.echothree.model.data.uom.common.UnitOfMeasureKindConstants;
import com.echothree.util.server.persistence.PersistenceUtils;
import com.echothree.util.server.persistence.Session;
import com.google.common.eventbus.Subscribe;

@SentEventSubscriber
public class UnitOfMeasureTypeModificationSubscriber
        extends BaseEventSubscriber {

    @Subscribe
    public void receiveSentEventForUnitOfMeasureKinds(SentEvent se) {
        decodeEventAndApply(se, touchUnitOfMeasureTypesIfUnitOfMeasureKind);
    }

    private static final Function5Arity<Event, EntityInstance, EventTypes, String, String>
            touchUnitOfMeasureTypesIfUnitOfMeasureKind = (event, entityInstance, eventType, componentVendorName, entityTypeName) -> {
        if(UnitOfMeasureKindConstants.COMPONENT_VENDOR_NAME.equals(componentVendorName)
                && UnitOfMeasureKindConstants.ENTITY_TYPE_NAME.equals(entityTypeName)
                && (eventType == EventTypes.MODIFY || eventType == EventTypes.TOUCH)) {
            var eventControl = Session.getModelController(EventControl.class);
            var uomControl = Session.getModelController(UomControl.class);
            var unitOfMeasureKind = uomControl.getUnitOfMeasureKindByEntityInstance(entityInstance);
            var unitOfMeasureTypes = uomControl.getUnitOfMeasureTypesByUnitOfMeasureKind(unitOfMeasureKind);

            for(var unitOfMeasureType : unitOfMeasureTypes) {
                eventControl.sendEvent(unitOfMeasureType.getPrimaryKey(), EventTypes.TOUCH,
                        unitOfMeasureKind.getPrimaryKey(), eventType,
                        PersistenceUtils.getInstance().getBasePKFromEntityInstance(event.getCreatedBy()));
            }
        }
    };

}
