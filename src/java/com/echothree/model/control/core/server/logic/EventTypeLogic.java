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

package com.echothree.model.control.core.server.logic;

import com.echothree.control.user.core.common.spec.EventTypeUniversalSpec;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.common.exception.UnknownEventTypeNameException;
import com.echothree.model.control.core.server.control.EventControl;
import com.echothree.model.data.core.server.entity.EventType;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;
import javax.inject.Inject;

@ApplicationScoped
public class EventTypeLogic
        extends BaseLogic {

    @Inject
    EventControl eventControl;

    @Inject
    EntityInstanceLogic entityInstanceLogic;

    protected EventTypeLogic() {
        super();
    }

    public static EventTypeLogic getInstance() {
        return CDI.current().select(EventTypeLogic.class).get();
    }

    public EventType getEventTypeByName(final ExecutionErrorAccumulator eea, final String eventTypeName) {
        var eventType = eventControl.getEventTypeByName(eventTypeName);

        if(eventType == null) {
            handleExecutionError(UnknownEventTypeNameException.class, eea, ExecutionErrors.UnknownEventTypeName.name(), eventTypeName);
        }

        return eventType;
    }

    public EventType getEventTypeByUniversalSpec(final ExecutionErrorAccumulator eea,
            final EventTypeUniversalSpec universalSpec) {
        EventType eventType = null;
        var eventTypeName = universalSpec.getEventTypeName();
        var parameterCount = (eventTypeName == null ? 0 : 1) + entityInstanceLogic.countPossibleEntitySpecs(universalSpec);

        switch(parameterCount) {
            case 1 -> {
                if(eventTypeName == null) {
                    var entityInstance = entityInstanceLogic.getEntityInstance(eea, universalSpec,
                            ComponentVendors.ECHO_THREE.name(), EntityTypes.EventType.name());

                    if(!eea.hasExecutionErrors()) {
                        eventType = eventControl.getEventTypeByEntityInstance(entityInstance);
                    }
                } else {
                    eventType = getEventTypeByName(eea, eventTypeName);
                }
            }
            default ->
                    handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
        }

        return eventType;
    }

}
