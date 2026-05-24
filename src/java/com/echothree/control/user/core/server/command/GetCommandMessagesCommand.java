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

import com.echothree.control.user.core.common.form.GetCommandMessagesForm;
import com.echothree.control.user.core.common.result.CoreResultFactory;
import com.echothree.model.control.core.server.control.CommandControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.core.server.entity.CommandMessage;
import com.echothree.model.data.core.server.entity.CommandMessageType;
import com.echothree.model.data.core.server.factory.CommandMessageFactory;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BasePaginatedMultipleEntitiesCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import java.util.Collection;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class GetCommandMessagesCommand
        extends BasePaginatedMultipleEntitiesCommand<CommandMessage, GetCommandMessagesForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.CommandMessage.name(), SecurityRoles.List.name())
                ))
        ));
        
        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("CommandMessageTypeName", FieldType.ENTITY_NAME, true, null, null)
        );
    }

    /** Creates a new instance of GetCommandMessagesCommand */
    public GetCommandMessagesCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }

    CommandMessageType commandMessageType;

    @Override
    protected void handleForm() {
        var commandMessageTypeName = form.getCommandMessageTypeName();

        commandMessageType = commandControl.getCommandMessageTypeByName(commandMessageTypeName);

        if(commandMessageType == null) {
            addExecutionError(ExecutionErrors.UnknownCommandMessageTypeName.name(), commandMessageTypeName);
        }
    }

    @Override
    protected Long getTotalEntities() {
        return hasExecutionErrors() ? null : commandControl.countCommandMessagesByCommandMessageType(commandMessageType);
    }

    @Override
    protected Collection<CommandMessage> getEntities() {
        return hasExecutionErrors() ? null : commandControl.getCommandMessagesByCommandMessageType(commandMessageType);
    }

    @Override
    protected BaseResult getResult(Collection<CommandMessage> entities) {
        var result = CoreResultFactory.getGetCommandMessagesResult();

        if(entities != null) {
            result.setCommandMessageType(commandControl.getCommandMessageTypeTransfer(getUserVisit(), commandMessageType));

            if(session.hasLimit(CommandMessageFactory.class)) {
                result.setCommandMessageCount(getTotalEntities());
            }

            result.setCommandMessages(commandControl.getCommandMessageTransfers(getUserVisit(), entities));
        }

        return result;
    }
    
}
