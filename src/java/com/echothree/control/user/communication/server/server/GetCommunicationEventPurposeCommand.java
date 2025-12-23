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

import com.echothree.control.user.communication.common.form.GetCommunicationEventPurposeForm;
import com.echothree.control.user.communication.common.result.CommunicationResultFactory;
import com.echothree.model.control.communication.server.control.CommunicationControl;
import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.Dependent;

@Dependent
public class GetCommunicationEventPurposeCommand
        extends BaseSimpleCommand<GetCommunicationEventPurposeForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
            new FieldDefinition("CommunicationEventPurposeName", FieldType.ENTITY_NAME, true, null, null)
        ));
    }
    
    /** Creates a new instance of GetCommunicationEventPurposeCommand */
    public GetCommunicationEventPurposeCommand() {
        super(null, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected BaseResult execute() {
        var communicationControl = Session.getModelController(CommunicationControl.class);
        var result = CommunicationResultFactory.getGetCommunicationEventPurposeResult();
        var communicationEventPurposeName = form.getCommunicationEventPurposeName();
        var communicationEventPurpose = communicationControl.getCommunicationEventPurposeByName(communicationEventPurposeName);
        
        if(communicationEventPurpose != null) {
            result.setCommunicationEventPurpose(communicationControl.getCommunicationEventPurposeTransfer(getUserVisit(), communicationEventPurpose));
            
            sendEvent(communicationEventPurpose.getPrimaryKey(), EventTypes.READ, null, null, getPartyPK());
        } else {
            addExecutionError(ExecutionErrors.UnknownCommunicationEventPurposeName.name(), communicationEventPurposeName);
        }
        
        return result;
    }
    
}
