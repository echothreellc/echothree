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

package com.echothree.control.user.party.server.command;

import com.echothree.control.user.party.common.edit.PartyAliasTypeDescriptionEdit;
import com.echothree.control.user.party.common.edit.PartyEditFactory;
import com.echothree.control.user.party.common.form.EditPartyAliasTypeDescriptionForm;
import com.echothree.control.user.party.common.result.EditPartyAliasTypeDescriptionResult;
import com.echothree.control.user.party.common.result.PartyResultFactory;
import com.echothree.control.user.party.common.spec.PartyAliasTypeDescriptionSpec;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.party.server.entity.PartyAliasType;
import com.echothree.model.data.party.server.entity.PartyAliasTypeDescription;
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

public class EditPartyAliasTypeDescriptionCommand
        extends BaseAbstractEditCommand<PartyAliasTypeDescriptionSpec, PartyAliasTypeDescriptionEdit, EditPartyAliasTypeDescriptionResult, PartyAliasTypeDescription, PartyAliasType> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.PartyAliasType.name(), SecurityRoles.Description.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("PartyTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("PartyAliasTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of EditPartyAliasTypeDescriptionCommand */
    public EditPartyAliasTypeDescriptionCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditPartyAliasTypeDescriptionResult getResult() {
        return PartyResultFactory.getEditPartyAliasTypeDescriptionResult();
    }

    @Override
    public PartyAliasTypeDescriptionEdit getEdit() {
        return PartyEditFactory.getPartyAliasTypeDescriptionEdit();
    }

    @Override
    public PartyAliasTypeDescription getEntity(EditPartyAliasTypeDescriptionResult result) {
        var partyControl = Session.getModelController(PartyControl.class);
        PartyAliasTypeDescription partyAliasTypeDescription = null;
        var partyTypeName = spec.getPartyTypeName();
        var partyType = partyControl.getPartyTypeByName(partyTypeName);

        if(partyType != null) {
            var partyAliasTypeName = spec.getPartyAliasTypeName();
            var partyAliasType = partyControl.getPartyAliasTypeByName(partyType, partyAliasTypeName);

            if(partyAliasType != null) {
                var languageIsoName = spec.getLanguageIsoName();
                var language = partyControl.getLanguageByIsoName(languageIsoName);

                if(language != null) {
                    if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                        partyAliasTypeDescription = partyControl.getPartyAliasTypeDescription(partyAliasType, language);
                    } else { // EditMode.UPDATE
                        partyAliasTypeDescription = partyControl.getPartyAliasTypeDescriptionForUpdate(partyAliasType, language);
                    }

                    if(partyAliasTypeDescription == null) {
                        addExecutionError(ExecutionErrors.UnknownPartyAliasTypeDescription.name(), partyTypeName, partyAliasTypeName, languageIsoName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownPartyAliasTypeName.name(), partyTypeName, partyAliasTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownPartyTypeName.name(), partyTypeName);
        }

        return partyAliasTypeDescription;
    }

    @Override
    public PartyAliasType getLockEntity(PartyAliasTypeDescription partyAliasTypeDescription) {
        return partyAliasTypeDescription.getPartyAliasType();
    }

    @Override
    public void fillInResult(EditPartyAliasTypeDescriptionResult result, PartyAliasTypeDescription partyAliasTypeDescription) {
        var partyControl = Session.getModelController(PartyControl.class);

        result.setPartyAliasTypeDescription(partyControl.getPartyAliasTypeDescriptionTransfer(getUserVisit(), partyAliasTypeDescription));
    }

    @Override
    public void doLock(PartyAliasTypeDescriptionEdit edit, PartyAliasTypeDescription partyAliasTypeDescription) {
        edit.setDescription(partyAliasTypeDescription.getDescription());
    }

    @Override
    public void doUpdate(PartyAliasTypeDescription partyAliasTypeDescription) {
        var partyControl = Session.getModelController(PartyControl.class);
        var partyAliasTypeDescriptionValue = partyControl.getPartyAliasTypeDescriptionValue(partyAliasTypeDescription);
        
        partyAliasTypeDescriptionValue.setDescription(edit.getDescription());
        
        partyControl.updatePartyAliasTypeDescriptionFromValue(partyAliasTypeDescriptionValue, getPartyPK());
    }

    
}
