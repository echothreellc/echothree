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

package com.echothree.model.control.core.server.logic;

import com.echothree.model.control.core.common.exception.UnknownEventTypeNameException;
import com.echothree.model.control.core.server.control.EventControl;
import com.echothree.model.data.core.server.entity.EventType;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;

@ApplicationScoped
public class EventTypeLogic
        extends BaseLogic {

    protected EventTypeLogic() {
        super();
    }

    public static EventTypeLogic getInstance() {
        return CDI.current().select(EventTypeLogic.class).get();
    }

    public EventType getEventTypeByName(final ExecutionErrorAccumulator eea, final String eventTypeName) {
        var eventControl = Session.getModelController(EventControl.class);
        var eventType = eventControl.getEventTypeByName(eventTypeName);

        if(eventType == null) {
            handleExecutionError(UnknownEventTypeNameException.class, eea, ExecutionErrors.UnknownEventTypeName.name(), eventTypeName);
        }

        return eventType;
    }

}
