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

package com.echothree.control.user.message.server.command;

import com.echothree.control.user.message.common.form.GetMessagesForm;
import com.echothree.control.user.message.common.result.MessageResultFactory;
import com.echothree.model.control.core.server.logic.ComponentVendorLogic;
import com.echothree.model.control.core.server.logic.EntityTypeLogic;
import com.echothree.model.control.message.server.control.MessageControl;
import com.echothree.model.data.message.server.entity.Message;
import com.echothree.model.data.message.server.entity.MessageType;
import com.echothree.model.data.message.server.factory.MessageFactory;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BasePaginatedMultipleEntitiesCommand;
import java.util.Collection;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class GetMessagesCommand
        extends BasePaginatedMultipleEntitiesCommand<Message, GetMessagesForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("ComponentVendorName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("EntityTypeName", FieldType.ENTITY_TYPE_NAME, true, null, null),
                new FieldDefinition("MessageTypeName", FieldType.ENTITY_NAME, true, null, null)
        );
    }
    
    @Inject
    MessageControl messageControl;

    @Inject
    ComponentVendorLogic componentVendorLogic;

    @Inject
    EntityTypeLogic entityTypeLogic;

    /** Creates a new instance of GetMessagesCommand */
    public GetMessagesCommand() {
        super(null, FORM_FIELD_DEFINITIONS, true);
    }

    MessageType messageType;

    @Override
    protected void handleForm() {
        var componentVendorName = form.getComponentVendorName();
        var entityTypeName = form.getEntityTypeName();
        var entityType = entityTypeLogic.getEntityTypeByName(this, componentVendorName, entityTypeName);

        if(!hasExecutionErrors()) {
            var messageTypeName = form.getMessageTypeName();

            messageType = messageControl.getMessageTypeByName(entityType, messageTypeName);

            if(messageType == null) {
                addExecutionError(com.echothree.util.common.message.ExecutionErrors.UnknownMessageTypeName.name(), messageTypeName);
            }
        }
    }

    @Override
    protected Long getTotalEntities() {
        return hasExecutionErrors() ? null : messageControl.countMessagesByMessageType(messageType);
    }

    @Override
    protected Collection<Message> getEntities() {
        return hasExecutionErrors() ? null : messageControl.getMessagesByMessageType(messageType);
    }

    @Override
    protected BaseResult getResult(Collection<Message> entities) {
        var result = MessageResultFactory.getGetMessagesResult();

        if(entities != null) {
            var userVisit = getUserVisit();

            result.setMessageType(messageControl.getMessageTypeTransfer(userVisit, messageType));

            if(session.hasLimit(MessageFactory.class)) {
                result.setMessageCount(getTotalEntities());
            }

            result.setMessages(messageControl.getMessageTransfers(userVisit, entities));
        }

        return result;
    }
    
}
