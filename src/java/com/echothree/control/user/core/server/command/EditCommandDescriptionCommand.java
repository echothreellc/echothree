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

package com.echothree.control.user.core.server.command;

import com.echothree.control.user.core.common.edit.CommandDescriptionEdit;
import com.echothree.control.user.core.common.edit.CoreEditFactory;
import com.echothree.control.user.core.common.form.EditCommandDescriptionForm;
import com.echothree.control.user.core.common.result.CoreResultFactory;
import com.echothree.control.user.core.common.result.EditCommandDescriptionResult;
import com.echothree.control.user.core.common.spec.CommandDescriptionSpec;
import com.echothree.model.control.core.server.control.CommandControl;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.data.core.server.entity.Command;
import com.echothree.model.data.core.server.entity.CommandDescription;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class EditCommandDescriptionCommand
        extends BaseAbstractEditCommand<CommandDescriptionSpec, CommandDescriptionEdit, EditCommandDescriptionResult, CommandDescription, Command> {
    
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ComponentVendorName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("CommandName", FieldType.COMMAND_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of EditCommandDescriptionCommand */
    public EditCommandDescriptionCommand(UserVisitPK userVisitPK, EditCommandDescriptionForm form) {
        super(userVisitPK, form, null, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditCommandDescriptionResult getResult() {
        return CoreResultFactory.getEditCommandDescriptionResult();
    }

    @Override
    public CommandDescriptionEdit getEdit() {
        return CoreEditFactory.getCommandDescriptionEdit();
    }

    @Override
    public CommandDescription getEntity(EditCommandDescriptionResult result) {
        var commandControl = Session.getModelController(CommandControl.class);
        CommandDescription commandDescription = null;
        var componentVendorName = spec.getComponentVendorName();
        var componentVendor = getComponentControl().getComponentVendorByName(componentVendorName);

        if(componentVendor != null) {
            var commandName = spec.getCommandName();
            var command = commandControl.getCommandByName(componentVendor, commandName);

            if(command != null) {
                var partyControl = Session.getModelController(PartyControl.class);
                var languageIsoName = spec.getLanguageIsoName();
                var language = partyControl.getLanguageByIsoName(languageIsoName);

                if(language != null) {
                    if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                        commandDescription = commandControl.getCommandDescription(command, language);
                    } else { // EditMode.UPDATE
                        commandDescription = commandControl.getCommandDescriptionForUpdate(command, language);
                    }

                    if(commandDescription == null) {
                        addExecutionError(ExecutionErrors.UnknownCommandDescription.name(), componentVendorName, commandName, languageIsoName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownCommandName.name(), componentVendorName, commandName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownComponentVendorName.name(), componentVendorName);
        }

        return commandDescription;
    }

    @Override
    public Command getLockEntity(CommandDescription commandDescription) {
        return commandDescription.getCommand();
    }

    @Override
    public void fillInResult(EditCommandDescriptionResult result, CommandDescription commandDescription) {
        var commandControl = Session.getModelController(CommandControl.class);

        result.setCommandDescription(commandControl.getCommandDescriptionTransfer(getUserVisit(), commandDescription));
    }

    @Override
    public void doLock(CommandDescriptionEdit edit, CommandDescription commandDescription) {
        edit.setDescription(commandDescription.getDescription());
    }

    @Override
    public void doUpdate(CommandDescription commandDescription) {
        var commandControl = Session.getModelController(CommandControl.class);
        var commandDescriptionValue = commandControl.getCommandDescriptionValue(commandDescription);
        commandDescriptionValue.setDescription(edit.getDescription());

        commandControl.updateCommandDescriptionFromValue(commandDescriptionValue, getPartyPK());
    }
    
}
