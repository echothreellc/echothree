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

package com.echothree.model.control.core.server.logic;

import com.echothree.model.control.core.common.EntityAttributeTypes;
import com.echothree.model.control.core.common.exception.DuplicateEntityMultipleListItemAttributeException;
import com.echothree.model.control.core.common.exception.DuplicateEntityMultipleListItemDefaultException;
import com.echothree.model.control.core.common.exception.UnknownEntityMultipleListItemDefaultException;
import com.echothree.model.control.core.server.database.EntityInstancesMissingMultipleListItemEntityAttributeQuery;
import com.echothree.model.data.core.server.entity.EntityAttribute;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.core.server.entity.EntityListItem;
import com.echothree.model.data.core.server.entity.EntityMultipleListItemAttribute;
import com.echothree.model.data.core.server.entity.EntityMultipleListItemDefault;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class EntityMultipleListItemAttributeLogic
        extends BaseEntityAttributeValueLogic {

    protected EntityMultipleListItemAttributeLogic() {
        super();
    }

    public EntityMultipleListItemDefault createEntityMultipleListItemDefault(final ExecutionErrorAccumulator eea, final EntityAttribute entityAttribute,
            final EntityListItem entityListItem, final boolean addMissingAttributes, final BasePK createdBy) {
        EntityMultipleListItemDefault entityListItemDefault = null;

        checkEntityType(eea, entityAttribute, EntityAttributeTypes.MULTIPLELISTITEM);

        if(eea == null || !eea.hasExecutionErrors()) {
            entityListItemDefault = coreControl.getEntityMultipleListItemDefault(entityAttribute, entityListItem);

            if(entityListItemDefault == null) {
                coreControl.createEntityMultipleListItemDefault(entityAttribute, entityListItem, createdBy);

                if(addMissingAttributes) {
                    new EntityInstancesMissingMultipleListItemEntityAttributeQuery().execute(entityAttribute, entityListItem).forEach(entityInstanceResult ->
                            coreControl.createEntityMultipleListItemAttribute(entityAttribute.getPrimaryKey(),
                                    entityInstanceResult.getEntityInstance(), entityListItem, createdBy));
                }
            } else {
                handleExecutionError(DuplicateEntityMultipleListItemDefaultException.class, eea, ExecutionErrors.DuplicateEntityMultipleListItemDefault.name(),
                        entityAttribute.getLastDetail().getEntityAttributeName(), entityListItem.getLastDetail().getEntityListItemName());
            }
        }

        return entityListItemDefault;
    }

    public void deleteEntityMultipleListItemDefault(final ExecutionErrorAccumulator eea, final EntityAttribute entityAttribute,
            final EntityListItem entityListItem, final BasePK deletedBy) {
        checkEntityType(eea, entityAttribute, EntityAttributeTypes.MULTIPLELISTITEM);

        if(eea == null || !eea.hasExecutionErrors()) {
            var entityListItemDefault = coreControl.getEntityMultipleListItemDefaultForUpdate(entityAttribute, entityListItem);

            if(entityListItemDefault == null) {
                handleExecutionError(UnknownEntityMultipleListItemDefaultException.class, eea, ExecutionErrors.UnknownEntityMultipleListItemDefault.name(),
                        entityAttribute.getLastDetail().getEntityAttributeName(), entityListItem.getLastDetail().getEntityListItemName());
            } else {
                coreControl.deleteEntityMultipleListItemDefault(entityListItemDefault, deletedBy);
            }
        }
    }

    public EntityMultipleListItemAttribute createEntityMultipleListItemAttribute(final ExecutionErrorAccumulator eea, final EntityAttribute entityAttribute,
        final EntityInstance entityInstance, final EntityListItem entityListItem, final BasePK createdBy) {
        EntityMultipleListItemAttribute entityMultipleListItemAttribute = null;

        checkEntityType(eea, entityAttribute, entityInstance);

        if(eea == null || !eea.hasExecutionErrors()) {
            entityMultipleListItemAttribute = coreControl.getEntityMultipleListItemAttribute(entityAttribute, entityInstance, entityListItem);

            if(entityMultipleListItemAttribute == null) {
                entityMultipleListItemAttribute = coreControl.createEntityMultipleListItemAttribute(entityAttribute, entityInstance, entityListItem, createdBy);
            } else {
                handleExecutionError(DuplicateEntityMultipleListItemAttributeException.class, eea, ExecutionErrors.DuplicateEntityMultipleListItemAttribute.name(),
                    entityInstanceLogic.getEntityRefFromEntityInstance(entityInstance),
                    entityAttribute.getLastDetail().getEntityAttributeName(),
                    entityListItem.getLastDetail().getEntityListItemName());
            }
        }

        return entityMultipleListItemAttribute;
    }

}
