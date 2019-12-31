// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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

package com.echothree.control.user.core.server.command;

import com.echothree.control.user.core.common.edit.CommandMessageTypeDescriptionEdit;
import com.echothree.control.user.core.common.edit.CoreEditFactory;
import com.echothree.control.user.core.common.form.EditCommandMessageTypeDescriptionForm;
import com.echothree.control.user.core.common.result.CoreResultFactory;
import com.echothree.control.user.core.common.result.EditCommandMessageTypeDescriptionResult;
import com.echothree.control.user.core.common.spec.CommandMessageTypeDescriptionSpec;
import com.echothree.model.control.core.server.CoreControl;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.party.server.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.core.server.entity.CommandMessageType;
import com.echothree.model.data.core.server.entity.CommandMessageTypeDescription;
import com.echothree.model.data.core.server.value.CommandMessageTypeDescriptionValue;
import com.echothree.model.data.party.server.entity.Language;
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

public class EditCommandMessageTypeDescriptionCommand
        extends BaseAbstractEditCommand<CommandMessageTypeDescriptionSpec, CommandMessageTypeDescriptionEdit, EditCommandMessageTypeDescriptionResult, CommandMessageTypeDescription, CommandMessageType> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_UTILITY, null),
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.CommandMessageType.name(), SecurityRoles.Description.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("CommandMessageTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 80L)
                ));
    }
    
    /** Creates a new instance of EditCommandMessageTypeDescriptionCommand */
    public EditCommandMessageTypeDescriptionCommand(UserVisitPK userVisitPK, EditCommandMessageTypeDescriptionForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditCommandMessageTypeDescriptionResult getResult() {
        return CoreResultFactory.getEditCommandMessageTypeDescriptionResult();
    }

    @Override
    public CommandMessageTypeDescriptionEdit getEdit() {
        return CoreEditFactory.getCommandMessageTypeDescriptionEdit();
    }

    @Override
    public CommandMessageTypeDescription getEntity(EditCommandMessageTypeDescriptionResult result) {
        var coreControl = getCoreControl();
        CommandMessageTypeDescription commandMessageTypeDescription = null;
        String commandMessageTypeName = spec.getCommandMessageTypeName();
        CommandMessageType commandMessageType = coreControl.getCommandMessageTypeByName(commandMessageTypeName);

        if(commandMessageType != null) {
            var partyControl = (PartyControl)Session.getModelController(PartyControl.class);
            String languageIsoName = spec.getLanguageIsoName();
            Language language = partyControl.getLanguageByIsoName(languageIsoName);

            if(language != null) {
                if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                    commandMessageTypeDescription = coreControl.getCommandMessageTypeDescription(commandMessageType, language);
                } else { // EditMode.UPDATE
                    commandMessageTypeDescription = coreControl.getCommandMessageTypeDescriptionForUpdate(commandMessageType, language);
                }

                if(commandMessageTypeDescription == null) {
                    addExecutionError(ExecutionErrors.UnknownCommandMessageTypeDescription.name(), commandMessageTypeName, languageIsoName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownCommandMessageTypeName.name(), commandMessageTypeName);
        }

        return commandMessageTypeDescription;
    }

    @Override
    public CommandMessageType getLockEntity(CommandMessageTypeDescription commandMessageTypeDescription) {
        return commandMessageTypeDescription.getCommandMessageType();
    }

    @Override
    public void fillInResult(EditCommandMessageTypeDescriptionResult result, CommandMessageTypeDescription commandMessageTypeDescription) {
        var coreControl = getCoreControl();

        result.setCommandMessageTypeDescription(coreControl.getCommandMessageTypeDescriptionTransfer(getUserVisit(), commandMessageTypeDescription));
    }

    @Override
    public void doLock(CommandMessageTypeDescriptionEdit edit, CommandMessageTypeDescription commandMessageTypeDescription) {
        edit.setDescription(commandMessageTypeDescription.getDescription());
    }

    @Override
    public void doUpdate(CommandMessageTypeDescription commandMessageTypeDescription) {
        var coreControl = getCoreControl();
        CommandMessageTypeDescriptionValue commandMessageTypeDescriptionValue = coreControl.getCommandMessageTypeDescriptionValue(commandMessageTypeDescription);
        commandMessageTypeDescriptionValue.setDescription(edit.getDescription());

        coreControl.updateCommandMessageTypeDescriptionFromValue(commandMessageTypeDescriptionValue, getPartyPK());
    }
    
}
