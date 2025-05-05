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

package com.echothree.control.user.security.server.command;

import com.echothree.control.user.security.common.form.CreateSecurityRoleGroupForm;
import com.echothree.control.user.security.common.result.SecurityResultFactory;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.security.server.control.SecurityControl;
import com.echothree.model.data.security.server.entity.SecurityRoleGroup;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CreateSecurityRoleGroupCommand
        extends BaseSimpleCommand<CreateSecurityRoleGroupForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.SecurityRoleGroup.name(), SecurityRoles.Create.name())
                        )))
                )));
        
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("SecurityRoleGroupName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ParentSecurityRoleGroupName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of CreateSecurityRoleGroupCommand */
    public CreateSecurityRoleGroupCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var result = SecurityResultFactory.getCreateSecurityRoleGroupResult();
        var securityControl = Session.getModelController(SecurityControl.class);
        var securityRoleGroupName = form.getSecurityRoleGroupName();
        var securityRoleGroup = securityControl.getSecurityRoleGroupByName(securityRoleGroupName);
        
        if(securityRoleGroup == null) {
            var parentSecurityRoleGroupName = form.getParentSecurityRoleGroupName();
            SecurityRoleGroup parentSecurityRoleGroup = null;
            
            if(parentSecurityRoleGroupName != null) {
                parentSecurityRoleGroup = securityControl.getSecurityRoleGroupByName(parentSecurityRoleGroupName);
            }
            
            if(parentSecurityRoleGroupName == null || parentSecurityRoleGroup != null) {
                var partyPK = getPartyPK();
                var isDefault = Boolean.valueOf(form.getIsDefault());
                var sortOrder = Integer.valueOf(form.getSortOrder());
                var description = form.getDescription();
                
                securityRoleGroup = securityControl.createSecurityRoleGroup(securityRoleGroupName, parentSecurityRoleGroup,
                        isDefault, sortOrder, partyPK);
                
                if(description != null) {
                    securityControl.createSecurityRoleGroupDescription(securityRoleGroup, getPreferredLanguage(),
                            description, partyPK);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownParentSecurityRoleGroupName.name(), parentSecurityRoleGroupName);
            }
        } else {
            addExecutionError(ExecutionErrors.DuplicateSecurityRoleGroupName.name(), securityRoleGroupName);
        }

        if(securityRoleGroup != null) {
            var basePK = securityRoleGroup.getPrimaryKey();

            result.setSecurityRoleGroupName(securityRoleGroup.getLastDetail().getSecurityRoleGroupName());
            result.setEntityRef(basePK.getEntityRef());
        }

        return result;
    }
    
}
