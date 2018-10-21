// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
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

import com.echothree.control.user.communication.remote.form.GetCommunicationSourceDescriptionsForm;
import com.echothree.control.user.communication.remote.result.CommunicationResultFactory;
import com.echothree.control.user.communication.remote.result.GetCommunicationSourceDescriptionsResult;
import com.echothree.model.control.communication.server.CommunicationControl;
import com.echothree.model.data.communication.server.entity.CommunicationSource;
import com.echothree.model.data.user.remote.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.remote.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GetCommunicationSourceDescriptionsCommand
        extends BaseSimpleCommand<GetCommunicationSourceDescriptionsForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
            new FieldDefinition("CommunicationSourceName", FieldType.ENTITY_NAME, true, null, null)
        ));
    }
    
    /** Creates a new instance of GetCommunicationSourceDescriptionsCommand */
    public GetCommunicationSourceDescriptionsCommand(UserVisitPK userVisitPK, GetCommunicationSourceDescriptionsForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected BaseResult execute() {
        CommunicationControl communicationControl = (CommunicationControl)Session.getModelController(CommunicationControl.class);
        GetCommunicationSourceDescriptionsResult result = CommunicationResultFactory.getGetCommunicationSourceDescriptionsResult();
        String communicationSourceName = form.getCommunicationSourceName();
        CommunicationSource communicationSource = communicationControl.getCommunicationSourceByName(communicationSourceName);
        
        if(communicationSource != null) {
            result.setCommunicationSource(communicationControl.getCommunicationSourceTransfer(getUserVisit(), communicationSource));
            result.setCommunicationSourceDescriptions(communicationControl.getCommunicationSourceDescriptionTransfers(getUserVisit(), communicationSource));
        } else {
            addExecutionError(ExecutionErrors.UnknownCommunicationSourceName.name(), communicationSourceName);
        }
        
        return result;
    }
    
}
