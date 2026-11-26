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
import com.echothree.model.control.core.common.exception.DuplicateEntityListItemAttributeException;
import com.echothree.model.control.core.common.exception.DuplicateEntityListItemDefaultException;
import com.echothree.model.control.core.common.exception.UnknownEntityListItemAttributeException;
import com.echothree.model.control.core.common.exception.UnknownEntityListItemDefaultException;
import com.echothree.model.control.core.server.database.EntityInstancesMissingListItemEntityAttributeQuery;
import com.echothree.model.data.core.server.entity.EntityAttribute;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.core.server.entity.EntityListItem;
import com.echothree.model.data.core.server.entity.EntityListItemAttribute;
import com.echothree.model.data.core.server.entity.EntityListItemDefault;
import com.echothree.model.data.core.server.value.EntityListItemAttributeValue;
import com.echothree.model.data.core.server.value.EntityListItemDefaultValue;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class EntityListItemAttributeLogic
        extends BaseEntityAttributeValueLogic {

    protected EntityListItemAttributeLogic() {
        super();
    }

    public EntityListItemDefault createEntityListItemDefault(final ExecutionErrorAccumulator eea, final EntityAttribute entityAttribute,
            final EntityListItem entityListItem, final boolean addMissingAttributes, final BasePK createdBy) {
        EntityListItemDefault entityListItemDefault = null;

        checkEntityType(eea, entityAttribute, EntityAttributeTypes.LISTITEM);

        if(eea == null || !eea.hasExecutionErrors()) {
            entityListItemDefault = coreControl.getEntityListItemDefault(entityAttribute);

            if(entityListItemDefault == null) {
                coreControl.createEntityListItemDefault(entityAttribute, entityListItem, createdBy);

                if(addMissingAttributes) {
                    new EntityInstancesMissingListItemEntityAttributeQuery().execute(entityAttribute).forEach(entityInstanceResult ->
                            coreControl.createEntityListItemAttribute(entityAttribute.getPrimaryKey(),
                                    entityInstanceResult.getEntityInstance(), entityListItem, createdBy));
                }
            } else {
                handleExecutionError(DuplicateEntityListItemDefaultException.class, eea, ExecutionErrors.DuplicateEntityListItemDefault.name(),
                        entityAttribute.getLastDetail().getEntityType().getLastDetail().getComponentVendor().getLastDetail().getComponentVendorName(),
                        entityAttribute.getLastDetail().getEntityType().getLastDetail().getEntityTypeName(),
                        entityAttribute.getLastDetail().getEntityAttributeName());
            }
        }

        return entityListItemDefault;
    }

    public EntityListItemAttribute createEntityListItemAttribute(final ExecutionErrorAccumulator eea, final EntityAttribute entityAttribute,
        final EntityInstance entityInstance, final EntityListItem entityListItem, final BasePK createdBy) {
        EntityListItemAttribute entityListItemAttribute = null;

        checkEntityType(eea, entityAttribute, entityInstance);

        if(eea == null || !eea.hasExecutionErrors()) {
            entityListItemAttribute = coreControl.getEntityListItemAttribute(entityAttribute, entityInstance);

            if(entityListItemAttribute == null) {
                entityListItemAttribute = coreControl.createEntityListItemAttribute(entityAttribute, entityInstance, entityListItem, createdBy);
            } else {
                handleExecutionError(DuplicateEntityListItemAttributeException.class, eea, ExecutionErrors.DuplicateEntityListItemAttribute.name(),
                        entityInstanceLogic.getEntityRefFromEntityInstance(entityInstance),
                        entityAttribute.getLastDetail().getEntityAttributeName());
            }
        }

        return entityListItemAttribute;
    }

    public EntityListItemDefault getEntityListItemDefault(ExecutionErrorAccumulator eea, EntityAttribute entityAttribute,
            EntityPermission entityPermission) {
        var entityListItemDefault = coreControl.getEntityListItemDefault(entityAttribute, entityPermission);

        if(entityListItemDefault == null) {
            var entityTypeDetail = entityAttribute.getLastDetail().getEntityType().getLastDetail();

            handleExecutionError(UnknownEntityListItemDefaultException.class, eea, ExecutionErrors.UnknownEntityListItemDefault.name(),
                    entityTypeDetail.getComponentVendor().getLastDetail().getComponentVendorName(),
                    entityTypeDetail.getEntityTypeName(), entityAttribute.getLastDetail().getEntityAttributeName());
        }

        return entityListItemDefault;
    }

    public EntityListItemDefault getEntityListItemDefaultForUpdate(ExecutionErrorAccumulator eea, EntityAttribute entityAttribute) {
        return getEntityListItemDefault(eea, entityAttribute, EntityPermission.READ_WRITE);
    }

    public EntityListItemAttribute getEntityListItemAttribute(ExecutionErrorAccumulator eea, EntityAttribute entityAttribute, EntityInstance entityInstance,
            EntityPermission entityPermission) {
        var entityListItemAttribute = coreControl.getEntityListItemAttribute(entityAttribute, entityInstance, entityPermission);

        if(entityListItemAttribute == null) {
            var entityTypeDetail = entityAttribute.getLastDetail().getEntityType().getLastDetail();

            handleExecutionError(UnknownEntityListItemAttributeException.class, eea, ExecutionErrors.UnknownEntityListItemAttribute.name(),
                    entityInstanceLogic.getEntityRefFromEntityInstance(entityInstance), entityTypeDetail.getComponentVendor().getLastDetail().getComponentVendorName(),
                    entityTypeDetail.getEntityTypeName(), entityAttribute.getLastDetail().getEntityAttributeName());
        }

        return entityListItemAttribute;
    }

    public EntityListItemAttribute getEntityListItemAttributeForUpdate(ExecutionErrorAccumulator eea, EntityAttribute entityAttribute,
            EntityInstance entityInstance) {
        return getEntityListItemAttribute(eea, entityAttribute, entityInstance, EntityPermission.READ_WRITE);
    }

    public void updateEntityListItemDefaultFromValue(final ExecutionErrorAccumulator eea, final EntityListItemDefaultValue entityListItemDefaultValue,
            final BasePK updatedBy) {
        coreControl.updateEntityListItemDefaultFromValue(entityListItemDefaultValue, updatedBy);
    }

    public void updateEntityListItemAttributeFromValue(final ExecutionErrorAccumulator eea, final EntityListItemAttributeValue entityListItemAttributeValue,
            final BasePK updatedBy) {
        coreControl.updateEntityListItemAttributeFromValue(entityListItemAttributeValue, updatedBy);
    }

    public void deleteEntityListItemDefault(final ExecutionErrorAccumulator eea, final EntityAttribute entityAttribute,
            final BasePK deletedBy) {
        checkEntityType(eea, entityAttribute, EntityAttributeTypes.LISTITEM);

        if(eea == null || !eea.hasExecutionErrors()) {
            var entityListItemDefault = getEntityListItemDefaultForUpdate(eea, entityAttribute);

            if(entityListItemDefault != null) {
                coreControl.deleteEntityListItemDefault(entityListItemDefault, deletedBy);
            }
        }
    }

    public void deleteEntityListItemAttribute(final ExecutionErrorAccumulator eea, final EntityListItemAttribute entityListItemAttribute,
            final BasePK deletedBy) {
        coreControl.deleteEntityListItemAttribute(entityListItemAttribute, deletedBy);
    }

}
