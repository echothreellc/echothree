// --------------------------------------------------------------------------------
// Copyright 2002-2023 Echo Three, LLC
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
import com.echothree.model.data.core.common.AppearanceConstants;
import com.echothree.util.server.persistence.PersistenceUtils;
import com.echothree.util.server.persistence.Session;
import com.google.common.eventbus.Subscribe;

@SentEventSubscriber
public class AppearanceModificationSubscriber {

    @Subscribe
    public void recordSentEvent(SentEvent se) {
        var event = se.getEvent();
        var entityInstance = event.getEntityInstance();
        var entityType = entityInstance.getEntityType();
        var lastEntityTypeDetail = entityType.getLastDetail();
        var entityTypeName = lastEntityTypeDetail.getEntityTypeName();
        var componentVendor = lastEntityTypeDetail.getComponentVendor();
        var componentVendorName = componentVendor.getLastDetail().getComponentVendorName();
        var eventType = event.getEventType();
        var eventTypeName = eventType.getEventTypeName();

        if(AppearanceConstants.COMPONENT_VENDOR_NAME.equals(componentVendorName)
                && AppearanceConstants.ENTITY_TYPE_NAME.equals(entityTypeName)
                && EventTypes.MODIFY.name().equals(eventTypeName)) {
            var coreControl = Session.getModelController(CoreControl.class);
            var appearance = coreControl.getAppearanceByEntityInstance(entityInstance);
            var entityAppearances = coreControl.getEntityAppearancesByAppearance(appearance);
            var createdBy = PersistenceUtils.getInstance().getBasePKFromEntityInstance(event.getCreatedBy());

            for(var entityAppearance : entityAppearances) {
                coreControl.sendEvent(entityAppearance.getEntityInstance(), EventTypes.TOUCH,
                        entityInstance, EventTypes.MODIFY, createdBy);
            }
        }
    }

}
