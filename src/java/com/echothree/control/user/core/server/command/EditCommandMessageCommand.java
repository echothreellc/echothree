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

import com.echothree.control.user.core.common.edit.CommandMessageEdit;
import com.echothree.control.user.core.common.edit.CoreEditFactory;
import com.echothree.control.user.core.common.form.EditCommandMessageForm;
import com.echothree.control.user.core.common.result.CoreResultFactory;
import com.echothree.control.user.core.common.result.EditCommandMessageResult;
import com.echothree.control.user.core.common.spec.CommandMessageSpec;
import com.echothree.model.control.core.server.control.CommandControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.core.server.entity.CommandMessage;
import com.echothree.model.data.core.server.entity.CommandMessageType;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
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
public class EditCommandMessageCommand
        extends BaseAbstractEditCommand<CommandMessageSpec, CommandMessageEdit, EditCommandMessageResult, CommandMessage, CommandMessage> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.CommandMessage.name(), SecurityRoles.Edit.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("CommandMessageKey", FieldType.KEY, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("CommandMessageKey", FieldType.KEY, true, null, null),
                new FieldDefinition("Translation", FieldType.STRING, false, 1L, 80L)
                ));
    }
    
    /** Creates a new instance of EditCommandMessageCommand */
    public EditCommandMessageCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditCommandMessageResult getResult() {
        return CoreResultFactory.getEditCommandMessageResult();
    }

    @Override
    public CommandMessageEdit getEdit() {
        return CoreEditFactory.getCommandMessageEdit();
    }

    CommandMessageType commandMessageType;

    @Override
    public CommandMessage getEntity(EditCommandMessageResult result) {
        var commandControl = Session.getModelController(CommandControl.class);
        CommandMessage commandMessage = null;
        var commandMessageTypeName = spec.getCommandMessageTypeName();

        commandMessageType = commandControl.getCommandMessageTypeByName(commandMessageTypeName);

        if(commandMessageType != null) {
            var commandMessageKey = spec.getCommandMessageKey();

            if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                commandMessage = commandControl.getCommandMessageByKey(commandMessageType, commandMessageKey);
            } else { // EditMode.UPDATE
                commandMessage = commandControl.getCommandMessageByKeyForUpdate(commandMessageType, commandMessageKey);
            }

            if(commandMessage != null) {
                result.setCommandMessage(commandControl.getCommandMessageTransfer(getUserVisit(), commandMessage));
            } else {
                addExecutionError(ExecutionErrors.UnknownCommandMessageKey.name(), commandMessageTypeName, commandMessageKey);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownCommandMessageTypeName.name(), commandMessageTypeName);
        }

        return commandMessage;
    }

    @Override
    public CommandMessage getLockEntity(CommandMessage commandMessage) {
        return commandMessage;
    }

    @Override
    public void fillInResult(EditCommandMessageResult result, CommandMessage commandMessage) {
        var commandControl = Session.getModelController(CommandControl.class);

        result.setCommandMessage(commandControl.getCommandMessageTransfer(getUserVisit(), commandMessage));
    }

    @Override
    public void doLock(CommandMessageEdit edit, CommandMessage commandMessage) {
        var commandControl = Session.getModelController(CommandControl.class);
        var commandMessageTranslation = commandControl.getCommandMessageTranslation(commandMessage, getPreferredLanguage());
        var commandMessageDetail = commandMessage.getLastDetail();

        edit.setCommandMessageKey(commandMessageDetail.getCommandMessageKey());

        if(commandMessageTranslation != null) {
            edit.setTranslation(commandMessageTranslation.getTranslation());
        }
    }

    @Override
    public void canUpdate(CommandMessage commandMessage) {
        var commandControl = Session.getModelController(CommandControl.class);
        var commandMessageKey = edit.getCommandMessageKey();
        var duplicateCommandMessage = commandControl.getCommandMessageByKey(commandMessageType, commandMessageKey);

        if(duplicateCommandMessage != null && !commandMessage.equals(duplicateCommandMessage)) {
            addExecutionError(ExecutionErrors.DuplicateCommandMessageKey.name(), commandMessageType.getLastDetail().getCommandMessageTypeName(), commandMessageKey);
        }
    }

    @Override
    public void doUpdate(CommandMessage commandMessage) {
        var commandControl = Session.getModelController(CommandControl.class);
        var partyPK = getPartyPK();
        var commandMessageDetailValue = commandControl.getCommandMessageDetailValueForUpdate(commandMessage);
        var commandMessageTranslation = commandControl.getCommandMessageTranslationForUpdate(commandMessage, getPreferredLanguage());
        var translation = edit.getTranslation();

        commandMessageDetailValue.setCommandMessageKey(edit.getCommandMessageKey());

        commandControl.updateCommandMessageFromValue(commandMessageDetailValue, partyPK);

        if(commandMessageTranslation == null && translation != null) {
            commandControl.createCommandMessageTranslation(commandMessage, getPreferredLanguage(), translation, partyPK);
        } else {
            if(commandMessageTranslation != null && translation == null) {
                commandControl.deleteCommandMessageTranslation(commandMessageTranslation, partyPK);
            } else {
                if(commandMessageTranslation != null && translation != null) {
                    var commandMessageTranslationValue = commandControl.getCommandMessageTranslationValue(commandMessageTranslation);

                    commandMessageTranslationValue.setTranslation(translation);
                    commandControl.updateCommandMessageTranslationFromValue(commandMessageTranslationValue, partyPK);
                }
            }
        }
    }
    
}
