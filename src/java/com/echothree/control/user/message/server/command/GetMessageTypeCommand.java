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

import com.echothree.control.user.message.common.form.GetMessageTypeForm;
import com.echothree.control.user.message.common.result.MessageResultFactory;
import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.core.server.logic.EntityTypeLogic;
import com.echothree.model.control.message.server.control.MessageControl;
import com.echothree.model.data.message.server.entity.MessageType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSingleEntityCommand;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class GetMessageTypeCommand
        extends BaseSingleEntityCommand<MessageType, GetMessageTypeForm> {
    
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
    EntityTypeLogic entityTypeLogic;

    /** Creates a new instance of GetMessageTypeCommand */
    public GetMessageTypeCommand() {
        super(null, FORM_FIELD_DEFINITIONS, true);
    }

    @Override
    protected MessageType getEntity() {
        MessageType messageType = null;
        var componentVendorName = form.getComponentVendorName();
        var entityTypeName = form.getEntityTypeName();
        var entityType = entityTypeLogic.getEntityTypeByName(this, componentVendorName, entityTypeName);

        if(!hasExecutionErrors()) {
            var messageTypeName = form.getMessageTypeName();
            messageType = messageControl.getMessageTypeByName(entityType, messageTypeName);

            if(messageType == null) {
                addExecutionError(ExecutionErrors.UnknownMessageTypeName.name(), messageTypeName);
            }
        }

        return messageType;
    }

    @Override
    protected BaseResult getResult(MessageType messageType) {
        var result = MessageResultFactory.getGetMessageTypeResult();

        if(messageType != null) {
            var userVisit = getUserVisit();

            result.setMessageType(messageControl.getMessageTypeTransfer(userVisit, messageType));

            sendEvent(messageType.getPrimaryKey(), EventTypes.READ, null, null, getPartyPK());
        }

        return result;
    }
    
}
