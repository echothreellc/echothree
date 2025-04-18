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

import com.echothree.control.user.core.common.edit.CommandMessageTranslationEdit;
import com.echothree.control.user.core.common.edit.CoreEditFactory;
import com.echothree.control.user.core.common.form.EditCommandMessageTranslationForm;
import com.echothree.control.user.core.common.result.CoreResultFactory;
import com.echothree.control.user.core.common.result.EditCommandMessageTranslationResult;
import com.echothree.control.user.core.common.spec.CommandMessageTranslationSpec;
import com.echothree.model.control.core.server.control.CommandControl;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.data.core.server.entity.CommandMessage;
import com.echothree.model.data.core.server.entity.CommandMessageTranslation;
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

public class EditCommandMessageTranslationCommand
        extends BaseAbstractEditCommand<CommandMessageTranslationSpec, CommandMessageTranslationEdit, EditCommandMessageTranslationResult, CommandMessageTranslation, CommandMessage> {
    
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("CommandMessageTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("CommandMessageKey", FieldType.COMMAND_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Translation", FieldType.STRING, true, 1L, 512L)
                ));
    }
    
    /** Creates a new instance of EditCommandMessageTranslationCommand */
    public EditCommandMessageTranslationCommand(UserVisitPK userVisitPK, EditCommandMessageTranslationForm form) {
        super(userVisitPK, form, null, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditCommandMessageTranslationResult getResult() {
        return CoreResultFactory.getEditCommandMessageTranslationResult();
    }

    @Override
    public CommandMessageTranslationEdit getEdit() {
        return CoreEditFactory.getCommandMessageTranslationEdit();
    }

    @Override
    public CommandMessageTranslation getEntity(EditCommandMessageTranslationResult result) {
        var commandControl = Session.getModelController(CommandControl.class);
        CommandMessageTranslation commandMessageTranslation = null;
        var commandMessageTypeName = spec.getCommandMessageTypeName();
        var commandMessageType = commandControl.getCommandMessageTypeByName(commandMessageTypeName);

        if(commandMessageType != null) {
            var commandMessageKey = spec.getCommandMessageKey();
            var commandMessage = commandControl.getCommandMessageByKey(commandMessageType, commandMessageKey);

            if(commandMessage != null) {
                var partyControl = Session.getModelController(PartyControl.class);
                var languageIsoName = spec.getLanguageIsoName();
                var language = partyControl.getLanguageByIsoName(languageIsoName);

                if(language != null) {
                    if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                        commandMessageTranslation = commandControl.getCommandMessageTranslation(commandMessage, language);
                    } else { // EditMode.UPDATE
                        commandMessageTranslation = commandControl.getCommandMessageTranslationForUpdate(commandMessage, language);
                    }

                    if(commandMessageTranslation == null) {
                        addExecutionError(ExecutionErrors.UnknownCommandMessageTranslation.name(), commandMessageTypeName, commandMessageKey, languageIsoName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownCommandMessageKey.name(), commandMessageTypeName, commandMessageKey);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownCommandMessageTypeName.name(), commandMessageTypeName);
        }

        return commandMessageTranslation;
    }

    @Override
    public CommandMessage getLockEntity(CommandMessageTranslation commandMessageTranslation) {
        return commandMessageTranslation.getCommandMessage();
    }

    @Override
    public void fillInResult(EditCommandMessageTranslationResult result, CommandMessageTranslation commandMessageTranslation) {
        var commandControl = Session.getModelController(CommandControl.class);

        result.setCommandMessageTranslation(commandControl.getCommandMessageTranslationTransfer(getUserVisit(), commandMessageTranslation));
    }

    @Override
    public void doLock(CommandMessageTranslationEdit edit, CommandMessageTranslation commandMessageTranslation) {
        edit.setTranslation(commandMessageTranslation.getTranslation());
    }

    @Override
    public void doUpdate(CommandMessageTranslation commandMessageTranslation) {
        var commandControl = Session.getModelController(CommandControl.class);
        var commandMessageTranslationValue = commandControl.getCommandMessageTranslationValue(commandMessageTranslation);
        commandMessageTranslationValue.setTranslation(edit.getTranslation());

        commandControl.updateCommandMessageTranslationFromValue(commandMessageTranslationValue, getPartyPK());
    }
    
}
