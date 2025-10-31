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

import com.echothree.control.user.security.common.edit.PartySecurityRoleTemplateDescriptionEdit;
import com.echothree.control.user.security.common.edit.SecurityEditFactory;
import com.echothree.control.user.security.common.form.EditPartySecurityRoleTemplateDescriptionForm;
import com.echothree.control.user.security.common.result.EditPartySecurityRoleTemplateDescriptionResult;
import com.echothree.control.user.security.common.result.SecurityResultFactory;
import com.echothree.control.user.security.common.spec.PartySecurityRoleTemplateDescriptionSpec;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.security.server.control.SecurityControl;
import com.echothree.model.data.security.server.entity.PartySecurityRoleTemplate;
import com.echothree.model.data.security.server.entity.PartySecurityRoleTemplateDescription;
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
public class EditPartySecurityRoleTemplateDescriptionCommand
        extends BaseAbstractEditCommand<PartySecurityRoleTemplateDescriptionSpec, PartySecurityRoleTemplateDescriptionEdit, EditPartySecurityRoleTemplateDescriptionResult, PartySecurityRoleTemplateDescription, PartySecurityRoleTemplate> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.PartySecurityRoleTemplate.name(), SecurityRoles.Description.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("PartySecurityRoleTemplateName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of EditPartySecurityRoleTemplateDescriptionCommand */
    public EditPartySecurityRoleTemplateDescriptionCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditPartySecurityRoleTemplateDescriptionResult getResult() {
        return SecurityResultFactory.getEditPartySecurityRoleTemplateDescriptionResult();
    }

    @Override
    public PartySecurityRoleTemplateDescriptionEdit getEdit() {
        return SecurityEditFactory.getPartySecurityRoleTemplateDescriptionEdit();
    }

    @Override
    public PartySecurityRoleTemplateDescription getEntity(EditPartySecurityRoleTemplateDescriptionResult result) {
        var securityControl = Session.getModelController(SecurityControl.class);
        PartySecurityRoleTemplateDescription partySecurityRoleTemplateDescription = null;
        var partySecurityRoleTemplateName = spec.getPartySecurityRoleTemplateName();
        var partySecurityRoleTemplate = securityControl.getPartySecurityRoleTemplateByName(partySecurityRoleTemplateName);

        if(partySecurityRoleTemplate != null) {
            var partyControl = Session.getModelController(PartyControl.class);
            var languageIsoName = spec.getLanguageIsoName();
            var language = partyControl.getLanguageByIsoName(languageIsoName);

            if(language != null) {
                if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                    partySecurityRoleTemplateDescription = securityControl.getPartySecurityRoleTemplateDescription(partySecurityRoleTemplate, language);
                } else { // EditMode.UPDATE
                    partySecurityRoleTemplateDescription = securityControl.getPartySecurityRoleTemplateDescriptionForUpdate(partySecurityRoleTemplate, language);
                }

                if(partySecurityRoleTemplateDescription == null) {
                    addExecutionError(ExecutionErrors.UnknownPartySecurityRoleTemplateDescription.name(), partySecurityRoleTemplateName, languageIsoName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownPartySecurityRoleTemplateName.name(), partySecurityRoleTemplateName);
        }

        return partySecurityRoleTemplateDescription;
    }

    @Override
    public PartySecurityRoleTemplate getLockEntity(PartySecurityRoleTemplateDescription partySecurityRoleTemplateDescription) {
        return partySecurityRoleTemplateDescription.getPartySecurityRoleTemplate();
    }

    @Override
    public void fillInResult(EditPartySecurityRoleTemplateDescriptionResult result, PartySecurityRoleTemplateDescription partySecurityRoleTemplateDescription) {
        var securityControl = Session.getModelController(SecurityControl.class);

        result.setPartySecurityRoleTemplateDescription(securityControl.getPartySecurityRoleTemplateDescriptionTransfer(getUserVisit(), partySecurityRoleTemplateDescription));
    }

    @Override
    public void doLock(PartySecurityRoleTemplateDescriptionEdit edit, PartySecurityRoleTemplateDescription partySecurityRoleTemplateDescription) {
        edit.setDescription(partySecurityRoleTemplateDescription.getDescription());
    }

    @Override
    public void doUpdate(PartySecurityRoleTemplateDescription partySecurityRoleTemplateDescription) {
        var securityControl = Session.getModelController(SecurityControl.class);
        var partySecurityRoleTemplateDescriptionValue = securityControl.getPartySecurityRoleTemplateDescriptionValue(partySecurityRoleTemplateDescription);
        
        partySecurityRoleTemplateDescriptionValue.setDescription(edit.getDescription());
        
        securityControl.updatePartySecurityRoleTemplateDescriptionFromValue(partySecurityRoleTemplateDescriptionValue, getPartyPK());
    }

    
}
