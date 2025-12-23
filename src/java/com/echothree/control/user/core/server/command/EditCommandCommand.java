// --------------------------------------------------------------------------------
// Copyright 2002-2026 Echo Three, LLC
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

import com.echothree.control.user.core.common.edit.CommandEdit;
import com.echothree.control.user.core.common.edit.CoreEditFactory;
import com.echothree.control.user.core.common.form.EditCommandForm;
import com.echothree.control.user.core.common.result.CoreResultFactory;
import com.echothree.control.user.core.common.result.EditCommandResult;
import com.echothree.control.user.core.common.spec.CommandSpec;
import com.echothree.model.control.core.server.control.CommandControl;
import com.echothree.model.data.core.server.entity.Command;
import com.echothree.model.data.core.server.entity.ComponentVendor;
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
import javax.enterprise.context.Dependent;

@Dependent
public class EditCommandCommand
        extends BaseAbstractEditCommand<CommandSpec, CommandEdit, EditCommandResult, Command, Command> {
    
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ComponentVendorName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("CommandName", FieldType.COMMAND_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("CommandName", FieldType.COMMAND_NAME, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of EditCommandCommand */
    public EditCommandCommand() {
        super(null, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditCommandResult getResult() {
        return CoreResultFactory.getEditCommandResult();
    }

    @Override
    public CommandEdit getEdit() {
        return CoreEditFactory.getCommandEdit();
    }

    ComponentVendor componentVendor = null;
    
    @Override
    public Command getEntity(EditCommandResult result) {
        var commandControl = Session.getModelController(CommandControl.class);
        Command command = null;
        var componentVendorName = spec.getComponentVendorName();
        
        componentVendor = componentControl.getComponentVendorByName(componentVendorName);

        if(componentVendor != null) {
            var commandName = spec.getCommandName();

            if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                command = commandControl.getCommandByName(componentVendor, commandName);
            } else { // EditMode.UPDATE
                command = commandControl.getCommandByNameForUpdate(componentVendor, commandName);
            }

            if(command == null) {
                addExecutionError(ExecutionErrors.UnknownCommandName.name(), componentVendorName, commandName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownComponentVendorName.name(), componentVendorName);
        }

        return command;
    }

    @Override
    public Command getLockEntity(Command command) {
        return command;
    }

    @Override
    public void fillInResult(EditCommandResult result, Command command) {
        var commandControl = Session.getModelController(CommandControl.class);

        result.setCommand(commandControl.getCommandTransfer(getUserVisit(), command));
    }

    @Override
    public void doLock(CommandEdit edit, Command command) {
        var commandControl = Session.getModelController(CommandControl.class);
        var commandDescription = commandControl.getCommandDescription(command, getPreferredLanguage());
        var commandDetail = command.getLastDetail();

        edit.setCommandName(commandDetail.getCommandName());
        edit.setSortOrder(commandDetail.getSortOrder().toString());

        if(commandDescription != null) {
            edit.setDescription(commandDescription.getDescription());
        }
    }

    @Override
    public void canUpdate(Command command) {
        var commandControl = Session.getModelController(CommandControl.class);
        var commandName = edit.getCommandName();
        var duplicateCommand = commandControl.getCommandByName(componentVendor, commandName);

        if(duplicateCommand != null && !command.equals(duplicateCommand)) {
            addExecutionError(ExecutionErrors.DuplicateCommandName.name(), commandName);
        }
    }

    @Override
    public void doUpdate(Command command) {
        var commandControl = Session.getModelController(CommandControl.class);
        var partyPK = getPartyPK();
        var commandDetailValue = commandControl.getCommandDetailValueForUpdate(command);
        var commandDescription = commandControl.getCommandDescriptionForUpdate(command, getPreferredLanguage());
        var description = edit.getDescription();

        commandDetailValue.setCommandName(edit.getCommandName());
        commandDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        commandControl.updateCommandFromValue(commandDetailValue, partyPK);

        if(commandDescription == null && description != null) {
            commandControl.createCommandDescription(command, getPreferredLanguage(), description, partyPK);
        } else {
            if(commandDescription != null && description == null) {
                commandControl.deleteCommandDescription(commandDescription, partyPK);
            } else {
                if(commandDescription != null && description != null) {
                    var commandDescriptionValue = commandControl.getCommandDescriptionValue(commandDescription);

                    commandDescriptionValue.setDescription(description);
                    commandControl.updateCommandDescriptionFromValue(commandDescriptionValue, partyPK);
                }
            }
        }
    }
     
}
