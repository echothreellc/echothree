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

import com.echothree.control.user.message.common.form.GetMessageForm;
import com.echothree.control.user.message.common.result.MessageResultFactory;
import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.core.server.logic.ComponentVendorLogic;
import com.echothree.model.control.core.server.logic.EntityTypeLogic;
import com.echothree.model.control.message.server.control.MessageControl;
import com.echothree.model.data.message.server.entity.Message;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSingleEntityCommand;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class GetMessageCommand
        extends BaseSingleEntityCommand<Message, GetMessageForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("ComponentVendorName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("EntityTypeName", FieldType.ENTITY_TYPE_NAME, true, null, null),
                new FieldDefinition("MessageTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("MessageName", FieldType.ENTITY_NAME, true, null, null)
        );
    }
    
    @Inject
    ComponentVendorLogic componentVendorLogic;

    @Inject
    EntityTypeLogic entityTypeLogic;

    @Inject
    MessageControl messageControl;

    /** Creates a new instance of GetMessageCommand */
    public GetMessageCommand() {
        super(null, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected Message getEntity() {
        Message message = null;
        var componentVendorName = form.getComponentVendorName();
        var entityTypeName = form.getEntityTypeName();
        var entityType = entityTypeLogic.getEntityTypeByName(this, componentVendorName, entityTypeName);

        if(!hasExecutionErrors()) {
            var messageTypeName = form.getMessageTypeName();
            var messageType = messageControl.getMessageTypeByName(entityType, messageTypeName);

            if(messageType != null) {
                var messageName = form.getMessageName();

                message = messageControl.getMessageByName(messageType, messageName);

                if(message != null) {
                    sendEvent(message.getPrimaryKey(), EventTypes.READ, null, null, getPartyPK());
                } else {
                    addExecutionError(ExecutionErrors.UnknownMessageName.name(), messageName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownMessageTypeName.name(), messageTypeName);
            }
        }

        return message;
    }
    
    @Override
    protected BaseResult getResult(Message message) {
        var result = MessageResultFactory.getGetMessageResult();
        
        if(message != null) {
            result.setMessage(messageControl.getMessageTransfer(getUserVisit(), message));
        }
        
        return result;
    }
    
}
