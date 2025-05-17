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

package com.echothree.control.user.returnpolicy.server.command;

import com.echothree.control.user.returnpolicy.common.form.CreateReturnTypeForm;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.returnpolicy.server.control.ReturnPolicyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.sequence.server.control.SequenceControl;
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

public class CreateReturnTypeCommand
        extends BaseSimpleCommand<CreateReturnTypeForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                    new SecurityRoleDefinition(SecurityRoleGroups.ReturnType.name(), SecurityRoles.Create.name())
                    )))
                )));

        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ReturnKindName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ReturnTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ReturnSequenceName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of CreateReturnTypeCommand */
    public CreateReturnTypeCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var returnPolicyControl = Session.getModelController(ReturnPolicyControl.class);
        var returnKindName = form.getReturnKindName();
        var returnKind = returnPolicyControl.getReturnKindByName(returnKindName);
        
        if(returnKind != null) {
            var returnTypeName = form.getReturnTypeName();
            var returnType = returnPolicyControl.getReturnTypeByName(returnKind, returnTypeName);
            
            if(returnType == null) {
                var sequenceControl = Session.getModelController(SequenceControl.class);
                var returnSequenceName = form.getReturnSequenceName();
                var returnSequenceType = returnKind.getLastDetail().getReturnSequenceType();
                var returnSequence = returnSequenceName == null ? null : sequenceControl.getSequenceByName(returnSequenceType, returnSequenceName);
                
                if(returnSequenceName == null || returnSequence != null) {
                    var partyPK = getPartyPK();
                    var isDefault = Boolean.valueOf(form.getIsDefault());
                    var sortOrder = Integer.valueOf(form.getSortOrder());
                    var description = form.getDescription();
                    
                    returnType = returnPolicyControl.createReturnType(returnKind, returnTypeName, returnSequence, isDefault, sortOrder, partyPK);
                    
                    if(description != null) {
                        returnPolicyControl.createReturnTypeDescription(returnType, getPreferredLanguage(), description, partyPK);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownReturnSequenceName.name(), returnSequenceType.getLastDetail().getSequenceTypeName(), returnSequenceName);
                }
            } else {
                addExecutionError(ExecutionErrors.DuplicateReturnTypeName.name(), returnKindName, returnTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.DuplicateReturnKindName.name(), returnKindName);
        }
        
        return null;
    }
    
}
