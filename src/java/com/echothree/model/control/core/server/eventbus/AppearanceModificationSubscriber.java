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
import com.echothree.model.control.core.server.control.AppearanceControl;
import com.echothree.model.data.core.common.AppearanceConstants;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.core.server.entity.Event;
import com.echothree.util.server.persistence.PersistenceUtils;
import com.echothree.util.server.persistence.Session;
import com.google.common.eventbus.Subscribe;

@SentEventSubscriber
public class AppearanceModificationSubscriber
        extends BaseEventSubscriber {

    @Subscribe
    public void receiveSentEvent(SentEvent se) {
        decodeEventAndApply(se, touchEntityInstancesIfAppearance);
    }

    private static final Function5Arity<Event, EntityInstance, EventTypes, String, String>
            touchEntityInstancesIfAppearance = (event, entityInstance, eventType, componentVendorName, entityTypeName) -> {
        if(AppearanceConstants.COMPONENT_VENDOR_NAME.equals(componentVendorName)
                && AppearanceConstants.ENTITY_TYPE_NAME.equals(entityTypeName)
                && (eventType == EventTypes.MODIFY || eventType == EventTypes.TOUCH)) {
            var appearanceControl = Session.getModelController(AppearanceControl.class);
            var appearance = appearanceControl.getAppearanceByEntityInstance(entityInstance);
            var entityAppearances = appearanceControl.getEntityAppearancesByAppearance(appearance);
            var createdBy = PersistenceUtils.getInstance().getBasePKFromEntityInstance(event.getCreatedBy());

            for(var entityAppearance : entityAppearances) {
                appearanceControl.sendEvent(entityAppearance.getEntityInstance(), EventTypes.TOUCH,
                        entityInstance, eventType,
                        createdBy);
            }
        }
    };

}
