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

package com.echothree.control.user.queue.server.command;

import com.echothree.control.user.queue.common.form.CreateQueueTypeForm;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.queue.server.control.QueueControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CreateQueueTypeCommand
        extends BaseSimpleCommand<CreateQueueTypeForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.QueueType.name(), SecurityRoles.Create.name())
                        )))
                )));
        
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("QueueTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of CreateQueueTypeCommand */
    public CreateQueueTypeCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var queueControl = Session.getModelController(QueueControl.class);
        var queueTypeName = form.getQueueTypeName();
        var queueType = queueControl.getQueueTypeByName(queueTypeName);
        
        if(queueType == null) {
            var partyPK = getPartyPK();
            var isDefault = Boolean.valueOf(form.getIsDefault());
            var sortOrder = Integer.valueOf(form.getSortOrder());
            var description = form.getDescription();
            
            queueType = queueControl.createQueueType(queueTypeName, isDefault, sortOrder, partyPK);
            
            if(description != null) {
                queueControl.createQueueTypeDescription(queueType, getPreferredLanguage(), description, partyPK);
            }
        } else {
            addExecutionError(ExecutionErrors.DuplicateQueueTypeName.name(), queueTypeName);
        }
        
        return null;
    }
    
}
