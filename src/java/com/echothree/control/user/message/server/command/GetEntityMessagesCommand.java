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

import com.echothree.control.user.message.common.form.GetEntityMessagesForm;
import com.echothree.control.user.message.common.result.MessageResultFactory;
import com.echothree.model.control.core.server.control.EntityInstanceControl;
import com.echothree.model.control.core.server.logic.ComponentVendorLogic;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.core.server.logic.EntityTypeLogic;
import com.echothree.model.control.message.server.control.MessageControl;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.message.server.entity.EntityMessage;
import com.echothree.model.data.message.server.entity.Message;
import com.echothree.model.data.message.server.factory.EntityMessageFactory;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BasePaginatedMultipleEntitiesCommand;
import java.util.Collection;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class GetEntityMessagesCommand
        extends BasePaginatedMultipleEntitiesCommand<EntityMessage, GetEntityMessagesForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = List.of(
            new FieldDefinition("EntityRef", FieldType.ENTITY_REF, false, null, null),
            new FieldDefinition("ComponentVendorName", FieldType.ENTITY_NAME, false, null, null),
            new FieldDefinition("EntityTypeName", FieldType.ENTITY_TYPE_NAME, false, null, null),
            new FieldDefinition("MessageTypeName", FieldType.ENTITY_NAME, false, null, null),
            new FieldDefinition("MessageName", FieldType.TAG, false, null, null)
        );
    }
    
    @Inject
    EntityInstanceControl entityInstanceControl;

    @Inject
    MessageControl messageControl;

    @Inject
    ComponentVendorLogic componentVendorLogic;

    @Inject
    EntityInstanceLogic entityInstanceLogic;

    @Inject
    EntityTypeLogic entityTypeLogic;

    /** Creates a new instance of GetEntityMessagesCommand */
    public GetEntityMessagesCommand() {
        super(null, FORM_FIELD_DEFINITIONS, true);
    }

    private EntityInstance entityInstance;
    private Message message;

    @Override
    protected void handleForm() {
        var componentVendorName = form.getComponentVendorName();
        var entityTypeName = form.getEntityTypeName();
        var messageTypeName = form.getMessageTypeName();
        var messageName = form.getMessageName();
        var entityRef = form.getEntityRef();
        var parameterCount = (componentVendorName == null && entityTypeName == null && messageTypeName == null && messageName == null ? 0 : 1) + (entityRef == null ? 0 : 1);

        if(parameterCount == 1) {
            if(entityRef != null) {
                entityInstance = entityInstanceLogic.getEntityInstanceByEntityRef(this, entityRef);
            } else {
                var entityType = entityTypeLogic.getEntityTypeByName(this, componentVendorName, entityTypeName);

                if(!hasExecutionErrors()) {
                    var messageType = messageControl.getMessageTypeByName(entityType, messageTypeName);

                    if(messageType != null) {
                        message = messageControl.getMessageByName(messageType, messageName);

                        if(message == null) {
                            addExecutionError(ExecutionErrors.UnknownMessageName.name(), messageTypeName, messageName);
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownMessageTypeName.name(), messageTypeName);
                    }
                }
            }
        } else {
            addExecutionError(ExecutionErrors.InvalidParameterCount.name());
        }
    }

    @Override
    protected Long getTotalEntities() {
        Long total = null;

        if(!hasExecutionErrors()) {
            if(entityInstance != null) {
                total = messageControl.countEntityMessageByEntityInstance(entityInstance);
            } else {
                total = messageControl.countEntityMessageByMessage(message);
            }
        }

        return total;
    }

    @Override
    protected Collection<EntityMessage> getEntities() {
        Collection<EntityMessage> entities = null;

        if(!hasExecutionErrors()) {
            if(entityInstance != null) {
                entities = messageControl.getEntityMessagesByEntityInstance(entityInstance);
            } else {
                entities = messageControl.getEntityMessagesByMessage(message);
            }
        }

        return entities;
    }

    @Override
    protected BaseResult getResult(Collection<EntityMessage> entities) {
        var result = MessageResultFactory.getGetEntityMessagesResult();

        if(entities != null) {
            var userVisit = getUserVisit();

            if(entityInstance != null) {
                result.setEntityInstance(entityInstanceControl.getEntityInstanceTransfer(userVisit, entityInstance, false, false, false, false));
            } else {
                result.setMessage(messageControl.getMessageTransfer(userVisit, message));
            }

            if(session.hasLimit(EntityMessageFactory.class)) {
                result.setEntityMessageCount(getTotalEntities());
            }

            result.setEntityMessages(messageControl.getEntityMessageTransfers(userVisit, entities));
        }

        return result;
    }

}
