// --------------------------------------------------------------------------------
// Copyright 2002-2022 Echo Three, LLC
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

package com.echothree.model.control.filter.server.eventbus;

import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.core.server.control.CoreControl;
import com.echothree.model.control.core.server.eventbus.SentEvent;
import com.echothree.model.control.core.server.eventbus.SentEventSubscriber;
import com.echothree.model.control.filter.server.control.FilterControl;
import com.echothree.model.data.core.server.entity.Event;
import com.echothree.model.data.filter.common.FilterStepConstants;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.persistence.PersistenceUtils;
import com.echothree.util.server.persistence.Session;
import com.google.common.eventbus.Subscribe;

@SentEventSubscriber
public class FilterModificationEventRecorder {

    @Subscribe
    public void recordSentEvent(SentEvent se) {
        var event = se.getEvent();
        var entityInstance = event.getEntityInstance();
        var entityType = entityInstance.getEntityType();
        var lastEntityTypeDetail = entityType.getLastDetail();
        var entityTypeName = lastEntityTypeDetail.getEntityTypeName();
        var componentVendor = lastEntityTypeDetail.getComponentVendor();
        var componentVendorName = componentVendor.getLastDetail().getComponentVendorName();

        if(FilterStepConstants.COMPONENT_VENDOR_NAME.equals(componentVendorName)
                && FilterStepConstants.ENTITY_TYPE_NAME.equals(entityTypeName)) {
            var coreControl = Session.getModelController(CoreControl.class);
            var filterControl = Session.getModelController(FilterControl.class);
            var filterStep = filterControl.getFilterStepByEntityInstance(event.getEntityInstance());

            coreControl.sendEventUsingNames(filterStep.getLastDetail().getFilter().getPrimaryKey(),
                    EventTypes.TOUCH.name(), filterStep.getPrimaryKey(), event.getEventType().getEventTypeName(),
                    PersistenceUtils.getInstance().getBasePKFromEntityInstance(event.getCreatedBy()));
        }
    }

}
