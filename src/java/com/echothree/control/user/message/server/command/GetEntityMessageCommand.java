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

import com.echothree.control.user.message.common.form.GetEntityMessageForm;
import com.echothree.control.user.message.common.result.MessageResultFactory;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.core.server.logic.EntityTypeLogic;
import com.echothree.model.control.message.server.control.MessageControl;
import com.echothree.model.data.message.server.entity.EntityMessage;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSingleEntityCommand;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class GetEntityMessageCommand
        extends BaseSingleEntityCommand<EntityMessage, GetEntityMessageForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = List.of(
            new FieldDefinition("EntityRef", FieldType.ENTITY_REF, true, null, null),
            new FieldDefinition("ComponentVendorName", FieldType.ENTITY_NAME, true, null, null),
            new FieldDefinition("EntityTypeName", FieldType.ENTITY_TYPE_NAME, true, null, null),
            new FieldDefinition("MessageTypeName", FieldType.ENTITY_NAME, true, null, null),
            new FieldDefinition("MessageName", FieldType.TAG, true, null, null)
        );
    }
    
    @Inject
    MessageControl messageControl;

    @Inject
    EntityInstanceLogic entityInstanceLogic;

    @Inject
    EntityTypeLogic entityTypeLogic;

    /** Creates a new instance of GetEntityMessageCommand */
    public GetEntityMessageCommand() {
        super(null, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected EntityMessage getEntity() {
        EntityMessage entityMessage = null;
        var entityRef = form.getEntityRef();
        var entityInstance = entityInstanceLogic.getEntityInstanceByEntityRef(this, entityRef);

        if(!hasExecutionErrors()) {
            var componentVendorName = form.getComponentVendorName();
            var entityTypeName = form.getEntityTypeName();
            var entityType = entityTypeLogic.getEntityTypeByName(this, componentVendorName, entityTypeName);

            if(!hasExecutionErrors()) {
                var messageTypeName = form.getMessageTypeName();
                var messageType = messageControl.getMessageTypeByName(entityType, messageTypeName);

                if(messageType != null) {
                    var messageName = form.getMessageName();
                    var message = messageControl.getMessageByName(messageType, messageName);

                    if(message != null) {
                        entityMessage = messageControl.getEntityMessage(entityInstance, message);

                        if(entityMessage == null) {
                            addExecutionError(ExecutionErrors.UnknownEntityMessage.name());
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownMessageName.name(), messageTypeName, messageName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownMessageTypeName.name(), messageTypeName);
                }
            }
        }

        return entityMessage;
    }
    
    @Override
    protected BaseResult getResult(EntityMessage entityMessage) {
        var result = MessageResultFactory.getGetEntityMessageResult();

        if(entityMessage != null) {
            result.setEntityMessage(messageControl.getEntityMessageTransfer(getUserVisit(), entityMessage));
        }

        return result;
    }
    
}
