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
import com.echothree.control.user.security.common.edit.SecurityRoleGroupDescriptionEdit;
import com.echothree.control.user.security.common.form.EditSecurityRoleGroupDescriptionForm;
import com.echothree.control.user.security.common.result.EditSecurityRoleGroupDescriptionResult;
import com.echothree.control.user.security.common.result.SecurityResultFactory;
import com.echothree.control.user.security.common.spec.SecurityRoleGroupDescriptionSpec;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.security.server.control.SecurityControl;
import com.echothree.model.data.security.server.entity.SecurityRoleGroup;
import com.echothree.model.data.security.server.entity.SecurityRoleGroupDescription;
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
public class EditSecurityRoleGroupDescriptionCommand
        extends BaseAbstractEditCommand<SecurityRoleGroupDescriptionSpec, SecurityRoleGroupDescriptionEdit, EditSecurityRoleGroupDescriptionResult, SecurityRoleGroupDescription, SecurityRoleGroup> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.SecurityRoleGroup.name(), SecurityRoles.Description.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("SecurityRoleGroupName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of EditSecurityRoleGroupDescriptionCommand */
    public EditSecurityRoleGroupDescriptionCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditSecurityRoleGroupDescriptionResult getResult() {
        return SecurityResultFactory.getEditSecurityRoleGroupDescriptionResult();
    }

    @Override
    public SecurityRoleGroupDescriptionEdit getEdit() {
        return SecurityEditFactory.getSecurityRoleGroupDescriptionEdit();
    }

    @Override
    public SecurityRoleGroupDescription getEntity(EditSecurityRoleGroupDescriptionResult result) {
        var securityControl = Session.getModelController(SecurityControl.class);
        SecurityRoleGroupDescription securityRoleGroupDescription = null;
        var securityRoleGroupName = spec.getSecurityRoleGroupName();
        var securityRoleGroup = securityControl.getSecurityRoleGroupByName(securityRoleGroupName);

        if(securityRoleGroup != null) {
            var partyControl = Session.getModelController(PartyControl.class);
            var languageIsoName = spec.getLanguageIsoName();
            var language = partyControl.getLanguageByIsoName(languageIsoName);

            if(language != null) {
                if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                    securityRoleGroupDescription = securityControl.getSecurityRoleGroupDescription(securityRoleGroup, language);
                } else { // EditMode.UPDATE
                    securityRoleGroupDescription = securityControl.getSecurityRoleGroupDescriptionForUpdate(securityRoleGroup, language);
                }

                if(securityRoleGroupDescription == null) {
                    addExecutionError(ExecutionErrors.UnknownSecurityRoleGroupDescription.name(), securityRoleGroupName, languageIsoName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownSecurityRoleGroupName.name(), securityRoleGroupName);
        }

        return securityRoleGroupDescription;
    }

    @Override
    public SecurityRoleGroup getLockEntity(SecurityRoleGroupDescription securityRoleGroupDescription) {
        return securityRoleGroupDescription.getSecurityRoleGroup();
    }

    @Override
    public void fillInResult(EditSecurityRoleGroupDescriptionResult result, SecurityRoleGroupDescription securityRoleGroupDescription) {
        var securityControl = Session.getModelController(SecurityControl.class);

        result.setSecurityRoleGroupDescription(securityControl.getSecurityRoleGroupDescriptionTransfer(getUserVisit(), securityRoleGroupDescription));
    }

    @Override
    public void doLock(SecurityRoleGroupDescriptionEdit edit, SecurityRoleGroupDescription securityRoleGroupDescription) {
        edit.setDescription(securityRoleGroupDescription.getDescription());
    }

    @Override
    public void doUpdate(SecurityRoleGroupDescription securityRoleGroupDescription) {
        var securityControl = Session.getModelController(SecurityControl.class);
        var securityRoleGroupDescriptionValue = securityControl.getSecurityRoleGroupDescriptionValue(securityRoleGroupDescription);
        
        securityRoleGroupDescriptionValue.setDescription(edit.getDescription());
        
        securityControl.updateSecurityRoleGroupDescriptionFromValue(securityRoleGroupDescriptionValue, getPartyPK());
    }

    
}
