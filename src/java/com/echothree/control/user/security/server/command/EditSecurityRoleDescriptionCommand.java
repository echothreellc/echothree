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
import com.echothree.control.user.security.common.edit.SecurityRoleDescriptionEdit;
import com.echothree.control.user.security.common.form.EditSecurityRoleDescriptionForm;
import com.echothree.control.user.security.common.result.EditSecurityRoleDescriptionResult;
import com.echothree.control.user.security.common.result.SecurityResultFactory;
import com.echothree.control.user.security.common.spec.SecurityRoleDescriptionSpec;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.security.server.control.SecurityControl;
import com.echothree.model.data.security.server.entity.SecurityRole;
import com.echothree.model.data.security.server.entity.SecurityRoleDescription;
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
import javax.enterprise.context.Dependent;

@Dependent
public class EditSecurityRoleDescriptionCommand
        extends BaseAbstractEditCommand<SecurityRoleDescriptionSpec, SecurityRoleDescriptionEdit, EditSecurityRoleDescriptionResult, SecurityRoleDescription, SecurityRole> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.SecurityRole.name(), SecurityRoles.Description.name())
                        )))
                )));

        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("SecurityRoleGroupName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("SecurityRoleName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));

        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L)
                ));
    }

    /** Creates a new instance of EditSecurityRoleDescriptionCommand */
    public EditSecurityRoleDescriptionCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditSecurityRoleDescriptionResult getResult() {
        return SecurityResultFactory.getEditSecurityRoleDescriptionResult();
    }

    @Override
    public SecurityRoleDescriptionEdit getEdit() {
        return SecurityEditFactory.getSecurityRoleDescriptionEdit();
    }

    SecurityRoleGroup securityRoleGroup;
    
    @Override
    public SecurityRoleDescription getEntity(EditSecurityRoleDescriptionResult result) {
        var securityControl = Session.getModelController(SecurityControl.class);
        SecurityRoleDescription securityRoleDescription = null;
        var securityRoleGroupName = spec.getSecurityRoleGroupName();
        
        securityRoleGroup = securityControl.getSecurityRoleGroupByName(securityRoleGroupName);

        if(securityRoleGroup != null) {
            var securityRoleName = spec.getSecurityRoleName();
            var securityRole = securityControl.getSecurityRoleByName(securityRoleGroup, securityRoleName);

            if(securityRole != null) {
                var partyControl = Session.getModelController(PartyControl.class);
                var languageIsoName = spec.getLanguageIsoName();
                var language = partyControl.getLanguageByIsoName(languageIsoName);

                if(language != null) {
                    if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                        securityRoleDescription = securityControl.getSecurityRoleDescription(securityRole, language);
                    } else { // EditMode.UPDATE
                        securityRoleDescription = securityControl.getSecurityRoleDescriptionForUpdate(securityRole, language);
                    }

                    if(securityRoleDescription == null) {
                        addExecutionError(ExecutionErrors.UnknownSecurityRoleDescription.name(), securityRoleGroupName, securityRoleName, languageIsoName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownSecurityRoleName.name(), securityRoleGroupName, securityRoleName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownSecurityRoleGroupName.name(), securityRoleGroupName);
        }

        return securityRoleDescription;
    }

    @Override
    public SecurityRole getLockEntity(SecurityRoleDescription securityRoleDescription) {
        return securityRoleDescription.getSecurityRole();
    }

    @Override
    public void fillInResult(EditSecurityRoleDescriptionResult result, SecurityRoleDescription securityRoleDescription) {
        var securityControl = Session.getModelController(SecurityControl.class);

        result.setSecurityRoleDescription(securityControl.getSecurityRoleDescriptionTransfer(getUserVisit(), securityRoleDescription));
    }

    @Override
    public void doLock(SecurityRoleDescriptionEdit edit, SecurityRoleDescription securityRoleDescription) {
        edit.setDescription(securityRoleDescription.getDescription());
    }

    @Override
    public void doUpdate(SecurityRoleDescription securityRoleDescription) {
        var securityControl = Session.getModelController(SecurityControl.class);
        var securityRoleDescriptionValue = securityControl.getSecurityRoleDescriptionValue(securityRoleDescription);
        
        securityRoleDescriptionValue.setDescription(edit.getDescription());
        
        securityControl.updateSecurityRoleDescriptionFromValue(securityRoleDescriptionValue, getPartyPK());
    }

}
