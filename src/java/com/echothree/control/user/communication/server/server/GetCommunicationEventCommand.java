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

package com.echothree.control.user.communication.server.server;

import com.echothree.control.user.communication.common.form.GetCommunicationEventForm;
import com.echothree.control.user.communication.common.result.CommunicationResultFactory;
import com.echothree.model.control.communication.server.control.CommunicationControl;
import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.data.communication.server.entity.CommunicationEvent;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSingleEntityCommand;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class GetCommunicationEventCommand
        extends BaseSingleEntityCommand<CommunicationEvent, GetCommunicationEventForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("CommunicationEventName", FieldType.ENTITY_NAME, true, null, null)
        );
    }

    @Inject
    CommunicationControl communicationControl;
    
    /** Creates a new instance of GetCommunicationEventCommand */
    public GetCommunicationEventCommand() {
        super(null, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected CommunicationEvent getEntity() {
        var communicationEventName = form.getCommunicationEventName();
        var communicationEvent = communicationControl.getCommunicationEventByName(communicationEventName);

        if(communicationEvent != null) {
            sendEvent(communicationEvent.getPrimaryKey(), EventTypes.READ, null, null, getPartyPK());
        } else {
            addExecutionError(ExecutionErrors.UnknownCommunicationEventName.name(), communicationEventName);
        }

        return communicationEvent;
    }

    @Override
    protected BaseResult getResult(CommunicationEvent communicationEvent) {
        var result = CommunicationResultFactory.getGetCommunicationEventResult();

        if(communicationEvent != null) {
            result.setCommunicationEvent(communicationControl.getCommunicationEventTransfer(getUserVisit(), communicationEvent));
        }

        return result;
    }
    
}
