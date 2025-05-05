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

import com.echothree.control.user.core.common.form.GetEntityListItemsForm;
import com.echothree.control.user.core.common.result.CoreResultFactory;
import com.echothree.model.control.core.common.EntityAttributeTypes;
import com.echothree.model.control.core.server.logic.EntityAttributeLogic;
import com.echothree.model.data.core.server.entity.EntityAttribute;
import com.echothree.model.data.core.server.entity.EntityListItem;
import com.echothree.model.data.core.server.factory.EntityListItemFactory;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseMultipleEntitiesCommand;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class GetEntityListItemsCommand
        extends BaseMultipleEntitiesCommand<EntityListItem, GetEntityListItemsForm> {

    // No COMMAND_SECURITY_DEFINITION, anyone may execute this command.
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ComponentVendorName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("EntityTypeName", FieldType.ENTITY_TYPE_NAME, true, null, null),
                new FieldDefinition("EntityAttributeName", FieldType.ENTITY_NAME, true, null, null)
                ));
    }
    
    /** Creates a new instance of GetEntityListItemsCommand */
    public GetEntityListItemsCommand() {
        super(null, FORM_FIELD_DEFINITIONS, true);
    }
    
    EntityAttribute entityAttribute;
    
    @Override
    protected Collection<EntityListItem> getEntities() {
        Collection<EntityListItem> entities = null;
        
        entityAttribute = EntityAttributeLogic.getInstance().getEntityAttributeByName(this,
                form.getComponentVendorName(), form.getEntityTypeName(), form.getEntityAttributeName());
                
        if(!hasExecutionErrors()) {
            var entityAttributeType = entityAttribute.getLastDetail().getEntityAttributeType();
            var entityAttributeTypeName = entityAttributeType.getEntityAttributeTypeName();

            if(entityAttributeTypeName.equals(EntityAttributeTypes.LISTITEM.name())
                    || entityAttributeTypeName.equals(EntityAttributeTypes.MULTIPLELISTITEM.name())) {
                entities = getCoreControl().getEntityListItems(entityAttribute);
            } else {
                var entityAttributeDetail = entityAttribute.getLastDetail();
                var entityTypeDetail = entityAttributeDetail.getEntityType().getLastDetail();
                
                addExecutionError(ExecutionErrors.InvalidEntityAttributeType.name(),
                        entityTypeDetail.getComponentVendor().getLastDetail().getComponentVendorName(),
                        entityTypeDetail.getEntityTypeName(), entityAttributeDetail.getEntityAttributeName(),
                        entityAttributeTypeName);
            }
        }
        
        return entities;
    }
    
    @Override
    protected BaseResult getResult(Collection<EntityListItem> entities) {
            var coreControl = getCoreControl();
        var result = CoreResultFactory.getGetEntityListItemsResult();
        
        if(entities != null) {
            if(session.hasLimit(EntityListItemFactory.class)) {
                result.setEntityListItemCount(coreControl.countEntityListItems(entityAttribute));
            }

            result.setEntityAttribute(coreControl.getEntityAttributeTransfer(getUserVisit(), entityAttribute, null));
            result.setEntityListItems(coreControl.getEntityListItemTransfers(getUserVisit(), entities, null));
        }

        return result;
    }
    
}
