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
import com.echothree.model.data.filter.common.FilterStepConstants;
import com.echothree.util.server.persistence.PersistenceUtils;
import com.echothree.util.server.persistence.Session;
import com.google.common.eventbus.Subscribe;

@SentEventSubscriber
public class FilterModificationSubscriber
        extends BaseEventSubscriber {

    @Subscribe
    public void receiveSentEvent(SentEvent se) {
        decodeEventAndApply(se, touchFilterIfFilterStep);
    }

    private static final Function5Arity<Event, EntityInstance, EventTypes, String, String>
            touchFilterIfFilterStep = (event, entityInstance, eventType, componentVendorName, entityTypeName) -> {
        if(FilterStepConstants.COMPONENT_VENDOR_NAME.equals(componentVendorName)
                && FilterStepConstants.ENTITY_TYPE_NAME.equals(entityTypeName)
                && (eventType == EventTypes.MODIFY || eventType == EventTypes.TOUCH)) {
            var eventControl = Session.getModelController(EventControl.class);
            var filterControl = Session.getModelController(FilterControl.class);
            var filterStep = filterControl.getFilterStepByEntityInstance(entityInstance);

            eventControl.sendEvent(filterStep.getLastDetail().getFilter().getPrimaryKey(), EventTypes.TOUCH,
                    filterStep.getPrimaryKey(), eventType,
                    PersistenceUtils.getInstance().getBasePKFromEntityInstance(event.getCreatedBy()));
        }
    };

}
