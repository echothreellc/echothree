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
import com.echothree.model.control.core.common.exception.DuplicateEntityTimeAttributeException;
import com.echothree.model.control.core.common.exception.DuplicateEntityTimeDefaultException;
import com.echothree.model.control.core.common.exception.UnknownEntityTimeAttributeException;
import com.echothree.model.control.core.common.exception.UnknownEntityTimeDefaultException;
import com.echothree.model.control.core.server.database.EntityInstancesMissingTimeEntityAttributeQuery;
import com.echothree.model.data.core.server.entity.EntityAttribute;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.core.server.entity.EntityTimeAttribute;
import com.echothree.model.data.core.server.entity.EntityTimeDefault;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class EntityTimeAttributeLogic
        extends BaseEntityAttributeValueLogic {

    protected EntityTimeAttributeLogic() {
        super();
    }

    public EntityTimeDefault createEntityTimeDefault(final ExecutionErrorAccumulator eea, final EntityAttribute entityAttribute,
            final Long timeAttribute, final boolean addMissingAttributes, BasePK createdBy) {
        EntityTimeDefault entityTimeDefault = null;

        checkEntityType(eea, entityAttribute, EntityAttributeTypes.TIME);

        if(eea == null || !eea.hasExecutionErrors()) {
            entityTimeDefault = coreControl.getEntityTimeDefault(entityAttribute);

            if(entityTimeDefault == null) {
                coreControl.createEntityTimeDefault(entityAttribute, timeAttribute, createdBy);

                if(addMissingAttributes) {
                    new EntityInstancesMissingTimeEntityAttributeQuery().execute(entityAttribute).forEach(entityInstanceResult ->
                            coreControl.createEntityTimeAttribute(entityAttribute.getPrimaryKey(),
                                    entityInstanceResult.getEntityInstance(), timeAttribute, createdBy));
                }
            } else {
                handleExecutionError(DuplicateEntityTimeDefaultException.class, eea, ExecutionErrors.DuplicateEntityTimeDefault.name(),
                        entityAttribute.getLastDetail().getEntityAttributeName());
            }
        }

        return entityTimeDefault;
    }

    public void deleteEntityTimeDefault(final ExecutionErrorAccumulator eea, final EntityAttribute entityAttribute,
            final BasePK deletedBy) {
        checkEntityType(eea, entityAttribute, EntityAttributeTypes.TIME);

        if(eea == null || !eea.hasExecutionErrors()) {
            var entityTimeDefault = coreControl.getEntityTimeDefaultForUpdate(entityAttribute);

            if(entityTimeDefault == null) {
                handleExecutionError(UnknownEntityTimeDefaultException.class, eea, ExecutionErrors.UnknownEntityTimeDefault.name(),
                        entityAttribute.getLastDetail().getEntityAttributeName());
            } else {
                coreControl.deleteEntityTimeDefault(entityTimeDefault, deletedBy);
            }
        }
    }

    public EntityTimeAttribute createEntityTimeAttribute(final ExecutionErrorAccumulator eea, final EntityAttribute entityAttribute,
            final EntityInstance entityInstance, final Long timeAttribute, final BasePK createdBy) {
        EntityTimeAttribute entityTimeAttribute = null;

        checkEntityType(eea, entityAttribute, entityInstance);

        if(eea == null || !eea.hasExecutionErrors()) {
            entityTimeAttribute = coreControl.getEntityTimeAttribute(entityAttribute, entityInstance);

            if(entityTimeAttribute == null) {
                coreControl.createEntityTimeAttribute(entityAttribute, entityInstance, timeAttribute, createdBy);
            } else {
                handleExecutionError(DuplicateEntityTimeAttributeException.class, eea, ExecutionErrors.DuplicateEntityTimeAttribute.name(),
                        entityInstanceLogic.getEntityRefFromEntityInstance(entityInstance),
                        entityAttribute.getLastDetail().getEntityAttributeName());
            }
        }

        return entityTimeAttribute;
    }

    public EntityTimeAttribute getEntityTimeAttribute(ExecutionErrorAccumulator eea, EntityAttribute entityAttribute, EntityInstance entityInstance,
            EntityPermission entityPermission) {
        var entityTimeAttribute = coreControl.getEntityTimeAttribute(entityAttribute, entityInstance, entityPermission);

        if(entityTimeAttribute == null) {
            var entityTypeDetail = entityAttribute.getLastDetail().getEntityType().getLastDetail();

            handleExecutionError(UnknownEntityTimeAttributeException.class, eea, ExecutionErrors.UnknownEntityTimeAttribute.name(),
                    entityInstanceLogic.getEntityRefFromEntityInstance(entityInstance), entityTypeDetail.getComponentVendor().getLastDetail().getComponentVendorName(),
                    entityTypeDetail.getEntityTypeName(), entityAttribute.getLastDetail().getEntityAttributeName());
        }

        return entityTimeAttribute;
    }

    public EntityTimeAttribute getEntityTimeAttributeForUpdate(ExecutionErrorAccumulator eea, EntityAttribute entityAttribute,
            EntityInstance entityInstance) {
        return getEntityTimeAttribute(eea, entityAttribute, entityInstance, EntityPermission.READ_WRITE);
    }

}
