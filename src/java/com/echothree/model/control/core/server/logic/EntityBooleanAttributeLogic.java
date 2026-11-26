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
import com.echothree.model.control.core.common.exception.DuplicateEntityBooleanAttributeException;
import com.echothree.model.control.core.common.exception.DuplicateEntityBooleanDefaultException;
import com.echothree.model.control.core.common.exception.UnknownEntityBooleanAttributeException;
import com.echothree.model.control.core.common.exception.UnknownEntityBooleanDefaultException;
import com.echothree.model.control.core.server.database.EntityInstancesMissingBooleanEntityAttributeQuery;
import com.echothree.model.data.core.server.entity.EntityAttribute;
import com.echothree.model.data.core.server.entity.EntityBooleanAttribute;
import com.echothree.model.data.core.server.entity.EntityBooleanDefault;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class EntityBooleanAttributeLogic
        extends BaseEntityAttributeValueLogic {

    protected EntityBooleanAttributeLogic() {
        super();
    }

    public EntityBooleanDefault createEntityBooleanDefault(final ExecutionErrorAccumulator eea, final EntityAttribute entityAttribute,
            final Boolean booleanAttribute, final boolean addMissingAttributes, final BasePK createdBy) {
        EntityBooleanDefault entityBooleanDefault = null;

        checkEntityType(eea, entityAttribute, EntityAttributeTypes.BOOLEAN);

        if(eea == null || !eea.hasExecutionErrors()) {
            entityBooleanDefault = coreControl.getEntityBooleanDefault(entityAttribute);

            if(entityBooleanDefault == null) {
                coreControl.createEntityBooleanDefault(entityAttribute, booleanAttribute, createdBy);

                if(addMissingAttributes) {
                    new EntityInstancesMissingBooleanEntityAttributeQuery().execute(entityAttribute).forEach(entityInstanceResult ->
                            coreControl.createEntityBooleanAttribute(entityAttribute.getPrimaryKey(),
                                    entityInstanceResult.getEntityInstance(), booleanAttribute, createdBy));
                }
            } else {
                handleExecutionError(DuplicateEntityBooleanDefaultException.class, eea, ExecutionErrors.DuplicateEntityBooleanDefault.name(),
                        entityAttribute.getLastDetail().getEntityAttributeName());
            }
        }

        return entityBooleanDefault;
    }

    public void deleteEntityBooleanDefault(final ExecutionErrorAccumulator eea, final EntityAttribute entityAttribute,
            final BasePK deletedBy) {
        checkEntityType(eea, entityAttribute, EntityAttributeTypes.BOOLEAN);

        if(eea == null || !eea.hasExecutionErrors()) {
            var entityBooleanDefault = coreControl.getEntityBooleanDefaultForUpdate(entityAttribute);

            if(entityBooleanDefault == null) {
                handleExecutionError(UnknownEntityBooleanDefaultException.class, eea, ExecutionErrors.UnknownEntityBooleanDefault.name(),
                        entityAttribute.getLastDetail().getEntityAttributeName());
            } else {
                coreControl.deleteEntityBooleanDefault(entityBooleanDefault, deletedBy);
            }
        }
    }

    public EntityBooleanAttribute createEntityBooleanAttribute(final ExecutionErrorAccumulator eea, final EntityAttribute entityAttribute,
            final EntityInstance entityInstance, final Boolean booleanAttribute, final BasePK createdBy) {
        EntityBooleanAttribute entityBooleanAttribute = null;

        checkEntityType(eea, entityAttribute, entityInstance);

        if(eea == null || !eea.hasExecutionErrors()) {
            entityBooleanAttribute = coreControl.getEntityBooleanAttribute(entityAttribute, entityInstance);

            if(entityBooleanAttribute == null) {
                coreControl.createEntityBooleanAttribute(entityAttribute, entityInstance, booleanAttribute, createdBy);
            } else {
                handleExecutionError(DuplicateEntityBooleanAttributeException.class, eea, ExecutionErrors.DuplicateEntityBooleanAttribute.name(),
                        entityInstanceLogic.getEntityRefFromEntityInstance(entityInstance),
                        entityAttribute.getLastDetail().getEntityAttributeName());
            }
        }

        return entityBooleanAttribute;
    }

    public EntityBooleanAttribute getEntityBooleanAttribute(ExecutionErrorAccumulator eea, EntityAttribute entityAttribute, EntityInstance entityInstance,
            EntityPermission entityPermission) {
        var entityBooleanAttribute = coreControl.getEntityBooleanAttribute(entityAttribute, entityInstance, entityPermission);

        if(entityBooleanAttribute == null) {
            var entityTypeDetail = entityAttribute.getLastDetail().getEntityType().getLastDetail();

            handleExecutionError(UnknownEntityBooleanAttributeException.class, eea, ExecutionErrors.UnknownEntityBooleanAttribute.name(),
                    entityInstanceLogic.getEntityRefFromEntityInstance(entityInstance), entityTypeDetail.getComponentVendor().getLastDetail().getComponentVendorName(),
                    entityTypeDetail.getEntityTypeName(), entityAttribute.getLastDetail().getEntityAttributeName());
        }

        return entityBooleanAttribute;
    }

    public EntityBooleanAttribute getEntityBooleanAttributeForUpdate(ExecutionErrorAccumulator eea, EntityAttribute entityAttribute,
            EntityInstance entityInstance) {
        return getEntityBooleanAttribute(eea, entityAttribute, entityInstance, EntityPermission.READ_WRITE);
    }

    public void deleteEntityBooleanAttribute(final ExecutionErrorAccumulator eea, final EntityBooleanAttribute entityBooleanAttribute,
            final BasePK deletedBy) {
        coreControl.deleteEntityBooleanAttribute(entityBooleanAttribute, deletedBy);
    }

}
