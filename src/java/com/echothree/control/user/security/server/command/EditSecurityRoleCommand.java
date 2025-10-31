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

import com.echothree.control.user.security.common.edit.SecurityEditFactory;
import com.echothree.control.user.security.common.edit.SecurityRoleEdit;
import com.echothree.control.user.security.common.form.EditSecurityRoleForm;
import com.echothree.control.user.security.common.result.EditSecurityRoleResult;
import com.echothree.control.user.security.common.result.SecurityResultFactory;
import com.echothree.control.user.security.common.spec.SecurityRoleSpec;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.security.server.control.SecurityControl;
import com.echothree.model.data.security.server.entity.SecurityRole;
import com.echothree.model.data.security.server.entity.SecurityRoleGroup;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class EditSecurityRoleCommand
        extends BaseAbstractEditCommand<SecurityRoleSpec, SecurityRoleEdit, EditSecurityRoleResult, SecurityRole, SecurityRole> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.SecurityRole.name(), SecurityRoles.Edit.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("SecurityRoleGroupName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("SecurityRoleName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("SecurityRoleName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of EditSecurityRoleCommand */
    public EditSecurityRoleCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditSecurityRoleResult getResult() {
        return SecurityResultFactory.getEditSecurityRoleResult();
    }
    
    @Override
    public SecurityRoleEdit getEdit() {
        return SecurityEditFactory.getSecurityRoleEdit();
    }
    
    SecurityRoleGroup securityRoleGroup = null;
    
    @Override
    public SecurityRole getEntity(EditSecurityRoleResult result) {
        var securityControl = Session.getModelController(SecurityControl.class);
        SecurityRole securityRole = null;
        var securityRoleGroupName = spec.getSecurityRoleGroupName();
        
        securityRoleGroup = securityControl.getSecurityRoleGroupByName(securityRoleGroupName);
        
        if(securityRoleGroup != null) {
            var securityRoleName = spec.getSecurityRoleName();

            if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                securityRole = securityControl.getSecurityRoleByName(securityRoleGroup, securityRoleName);
            } else { // EditMode.UPDATE
                securityRole = securityControl.getSecurityRoleByNameForUpdate(securityRoleGroup, securityRoleName);
            }

            if(securityRole != null) {
                result.setSecurityRole(securityControl.getSecurityRoleTransfer(getUserVisit(), securityRole));
            } else {
                addExecutionError(ExecutionErrors.UnknownSecurityRoleName.name(), securityRoleGroupName, securityRoleGroupName, securityRoleName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownSecurityRoleGroupName.name(), securityRoleGroupName);
        }

        return securityRole;
    }
    
    @Override
    public SecurityRole getLockEntity(SecurityRole securityRole) {
        return securityRole;
    }
    
    @Override
    public void fillInResult(EditSecurityRoleResult result, SecurityRole securityRole) {
        var securityControl = Session.getModelController(SecurityControl.class);
        
        result.setSecurityRole(securityControl.getSecurityRoleTransfer(getUserVisit(), securityRole));
    }
    
    @Override
    public void doLock(SecurityRoleEdit edit, SecurityRole securityRole) {
        var securityControl = Session.getModelController(SecurityControl.class);
        var securityRoleDescription = securityControl.getSecurityRoleDescription(securityRole, getPreferredLanguage());
        var securityRoleDetail = securityRole.getLastDetail();

        edit.setSecurityRoleName(securityRoleDetail.getSecurityRoleName());
        edit.setIsDefault(securityRoleDetail.getIsDefault().toString());
        edit.setSortOrder(securityRoleDetail.getSortOrder().toString());

        if(securityRoleDescription != null) {
            edit.setDescription(securityRoleDescription.getDescription());
        }
    }
    
    @Override
    public void canUpdate(SecurityRole securityRole) {
        var securityControl = Session.getModelController(SecurityControl.class);
        var securityRoleName = edit.getSecurityRoleName();
        var duplicateSecurityRole = securityControl.getSecurityRoleByName(securityRoleGroup, securityRoleName);

        if(duplicateSecurityRole != null && !securityRole.equals(duplicateSecurityRole)) {
            addExecutionError(ExecutionErrors.DuplicateSecurityRoleName.name(), securityRoleGroup.getLastDetail().getSecurityRoleGroupName(), securityRoleName);
        }
    }
    
    @Override
    public void doUpdate(SecurityRole securityRole) {
        var securityControl = Session.getModelController(SecurityControl.class);
        var partyPK = getPartyPK();
        var securityRoleDetailValue = securityControl.getSecurityRoleDetailValueForUpdate(securityRole);
        var securityRoleDescription = securityControl.getSecurityRoleDescriptionForUpdate(securityRole, getPreferredLanguage());
        var description = edit.getDescription();

        securityRoleDetailValue.setSecurityRoleName(edit.getSecurityRoleName());
        securityRoleDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        securityRoleDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        securityControl.updateSecurityRoleFromValue(securityRoleDetailValue, partyPK);

        if(securityRoleDescription == null && description != null) {
            securityControl.createSecurityRoleDescription(securityRole, getPreferredLanguage(), description, partyPK);
        } else if(securityRoleDescription != null && description == null) {
            securityControl.deleteSecurityRoleDescription(securityRoleDescription, partyPK);
        } else if(securityRoleDescription != null && description != null) {
            var securityRoleDescriptionValue = securityControl.getSecurityRoleDescriptionValue(securityRoleDescription);

            securityRoleDescriptionValue.setDescription(description);
            securityControl.updateSecurityRoleDescriptionFromValue(securityRoleDescriptionValue, partyPK);
        }
    }
    
}
