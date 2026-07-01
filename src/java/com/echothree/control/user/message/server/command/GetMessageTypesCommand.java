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

import com.echothree.control.user.message.common.form.GetMessageTypesForm;
import com.echothree.control.user.message.common.result.MessageResultFactory;
import com.echothree.model.control.core.server.logic.EntityTypeLogic;
import com.echothree.model.control.message.server.control.MessageControl;
import com.echothree.model.data.core.server.entity.EntityType;
import com.echothree.model.data.message.server.entity.MessageType;
import com.echothree.model.data.message.server.factory.MessageTypeFactory;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BasePaginatedMultipleEntitiesCommand;
import java.util.Collection;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class GetMessageTypesCommand
        extends BasePaginatedMultipleEntitiesCommand<MessageType, GetMessageTypesForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("ComponentVendorName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("EntityTypeName", FieldType.ENTITY_TYPE_NAME, true, null, null)
        );
    }
    
    @Inject
    MessageControl messageControl;

    @Inject
    EntityTypeLogic entityTypeLogic;

    /** Creates a new instance of GetMessageTypesCommand */
    public GetMessageTypesCommand() {
        super(null, FORM_FIELD_DEFINITIONS, true);
    }

    EntityType entityType;

    @Override
    protected void handleForm() {
        entityType = entityTypeLogic.getEntityTypeByName(this, form.getComponentVendorName(), form.getEntityTypeName());
    }

    @Override
    protected Long getTotalEntities() {
        return hasExecutionErrors() ? null : messageControl.countMessageTypesByEntityType(entityType);
    }

    @Override
    protected Collection<MessageType> getEntities() {
        return hasExecutionErrors() ? null : messageControl.getMessageTypes(entityType);
    }

    @Override
    protected BaseResult getResult(Collection<MessageType> entities) {
        var result = MessageResultFactory.getGetMessageTypesResult();

        if(entities != null) {
            var userVisit = getUserVisit();

            result.setEntityType(entityTypeControl.getEntityTypeTransfer(userVisit, entityType));

            if(session.hasLimit(MessageTypeFactory.class)) {
                result.setMessageTypeCount(getTotalEntities());
            }

            result.setMessageTypes(messageControl.getMessageTypeTransfers(userVisit, entities));
        }

        return result;
    }
    
}
