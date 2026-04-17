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

package com.echothree.model.control.filter.server.eventbus;

import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.core.server.control.EventControl;
import com.echothree.model.control.core.server.eventbus.BaseEventSubscriber;
import com.echothree.model.control.core.server.eventbus.Function5Arity;
import com.echothree.model.control.core.server.eventbus.SentEvent;
import com.echothree.model.control.core.server.eventbus.SentEventSubscriber;
import com.echothree.model.control.filter.server.control.FilterControl;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.core.server.entity.Event;
import com.echothree.model.data.filter.common.FilterAdjustmentConstants;
import com.echothree.util.server.persistence.PersistenceUtils;
import com.echothree.util.server.persistence.Session;
import com.google.common.eventbus.Subscribe;

@SentEventSubscriber
public class FilterAdjustmentModificationSubscriber
        extends BaseEventSubscriber {

    @Subscribe
    public void receiveSentFilterAdjustmentEvent(SentEvent se) {
        decodeEventAndApply(se, touchFiltersIfFilterAdjustment);
    }

    private static final Function5Arity<Event, EntityInstance, EventTypes, String, String>
            touchFiltersIfFilterAdjustment = (event, entityInstance, eventType, componentVendorName, entityTypeName) -> {
        if(FilterAdjustmentConstants.COMPONENT_VENDOR_NAME.equals(componentVendorName)
                && FilterAdjustmentConstants.ENTITY_TYPE_NAME.equals(entityTypeName)
                && (eventType == EventTypes.MODIFY || eventType == EventTypes.TOUCH)) {
            var eventControl = Session.getModelController(EventControl.class);
            var filterControl = Session.getModelController(FilterControl.class);
            var filterAdjustment = filterControl.getFilterAdjustmentByEntityInstance(entityInstance);

            var filters = filterControl.getFiltersByInitialFilterAdjustment(filterAdjustment);
            for(var filter : filters) {
                eventControl.sendEvent(filter.getLastDetail().getFilterPK(), EventTypes.TOUCH,
                        filterAdjustment.getPrimaryKey(), eventType,
                        PersistenceUtils.getInstance().getBasePKFromEntityInstance(event.getCreatedBy()));
            }

            var filterStepElements = filterControl.getFilterStepElementsByFilterAdjustment(filterAdjustment);
            for(var filterStepElement : filterStepElements) {
                eventControl.sendEvent(filterStepElement.getPrimaryKey(), EventTypes.TOUCH,
                        filterAdjustment.getPrimaryKey(), eventType,
                        PersistenceUtils.getInstance().getBasePKFromEntityInstance(event.getCreatedBy()));
            }

        }
    };

}
