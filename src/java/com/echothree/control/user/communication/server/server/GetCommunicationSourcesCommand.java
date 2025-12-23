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

import com.echothree.control.user.communication.common.form.GetCommunicationSourcesForm;
import com.echothree.control.user.communication.common.result.CommunicationResultFactory;
import com.echothree.model.control.communication.server.control.CommunicationControl;
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
public class GetCommunicationSourcesCommand
        extends BaseSimpleCommand<GetCommunicationSourcesForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
            new FieldDefinition("CommunicationSourceTypeName", FieldType.ENTITY_NAME, false, null, null)
        ));
    }
    
    /** Creates a new instance of GetCommunicationSourcesCommand */
    public GetCommunicationSourcesCommand() {
        super(null, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected BaseResult execute() {
        var result = CommunicationResultFactory.getGetCommunicationSourcesResult();
        var communicationSourceTypeName = form.getCommunicationSourceTypeName();
        var parameterCount = (communicationSourceTypeName == null ? 0 : 1);
        
        if(parameterCount < 2) {
            var communicationControl = Session.getModelController(CommunicationControl.class);
            
            if(communicationSourceTypeName != null) {
                var communicationSourceType = communicationControl.getCommunicationSourceTypeByName(communicationSourceTypeName);
                
                if(communicationSourceType != null) {
                    result.setCommunicationSources(communicationControl.getCommunicationSourceTransfersByCommunicationSourceType(getUserVisit(),
                            communicationSourceType));
                } else {
                    addExecutionError(ExecutionErrors.UnknownCommunicationSourceTypeName.name(), communicationSourceTypeName);
                }
            } else {
                result.setCommunicationSources(communicationControl.getCommunicationSourceTransfers(getUserVisit()));
            }
        } else {
            addExecutionError(ExecutionErrors.InvalidParameterCount.name());
        }
        
        return result;
    }
    
}
