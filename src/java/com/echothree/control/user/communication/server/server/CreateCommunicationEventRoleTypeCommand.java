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

package com.echothree.control.user.communication.server.server;

import com.echothree.control.user.communication.common.form.CreateCommunicationEventRoleTypeForm;
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
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class CreateCommunicationEventRoleTypeCommand
        extends BaseSimpleCommand<CreateCommunicationEventRoleTypeForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
            new FieldDefinition("CommunicationEventRoleTypeName", FieldType.ENTITY_NAME, true, null, null),
            new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null)
        ));
    }
    
    /** Creates a new instance of CreateCommunicationEventRoleTypeCommand */
    public CreateCommunicationEventRoleTypeCommand() {
        super(null, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var communicationControl = Session.getModelController(CommunicationControl.class);
        var communicationEventRoleTypeName = form.getCommunicationEventRoleTypeName();
        var communicationEventRoleType = communicationControl.getCommunicationEventRoleTypeByName(communicationEventRoleTypeName);
        
        if(communicationEventRoleType == null) {
            var sortOrder = Integer.valueOf(form.getSortOrder());
            
            communicationControl.createCommunicationEventRoleType(communicationEventRoleTypeName, sortOrder);
        } else {
            addExecutionError(ExecutionErrors.DuplicateCommunicationEventRoleTypeName.name(), communicationEventRoleTypeName);
        }
        
        return null;
    }
    
}
