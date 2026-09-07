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

import com.echothree.control.user.core.common.form.GetCommandForm;
import com.echothree.control.user.core.common.result.CoreResultFactory;
import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.core.server.logic.ComponentVendorLogic;
import com.echothree.model.data.core.server.entity.Command;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSingleEntityCommand;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class GetCommandCommand
        extends BaseSingleEntityCommand<Command, GetCommandForm> {

    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("ComponentVendorName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("CommandName", FieldType.COMMAND_NAME, true, null, null)
        );
    }

    @Inject
    ComponentVendorLogic componentVendorLogic;

    /** Creates a new instance of GetCommandCommand */
    public GetCommandCommand() {
        super(null, FORM_FIELD_DEFINITIONS, true);
    }

    @Override
    protected Command getEntity() {
        var componentVendorName = form.getComponentVendorName();
        var componentVendor = componentVendorLogic.getComponentVendorByName(this, componentVendorName);
        Command command = null;

        if(!hasExecutionErrors()) {
            var commandName = form.getCommandName();
            command = commandControl.getCommandByName(componentVendor, commandName);

            if(command == null) {
                addExecutionError(ExecutionErrors.UnknownCommandName.name(), componentVendorName, commandName);
            } else {
                sendEvent(command.getPrimaryKey(), EventTypes.READ, null, null, getPartyPK());
            }
        }

        return command;
    }

    @Override
    protected BaseResult getResult(Command command) {
        var result = CoreResultFactory.getGetCommandResult();

        if(command != null) {
            result.setCommand(commandControl.getCommandTransfer(getUserVisit(), command));
        }

        return result;
    }

}
