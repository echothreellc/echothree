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
import com.echothree.model.control.core.common.exception.DuplicateEntityDateAttributeException;
import com.echothree.model.control.core.common.exception.DuplicateEntityDateDefaultException;
import com.echothree.model.control.core.common.exception.UnknownEntityDateAttributeException;
import com.echothree.model.control.core.common.exception.UnknownEntityDateDefaultException;
import com.echothree.model.control.core.server.database.EntityInstancesMissingDateEntityAttributeQuery;
import com.echothree.model.data.core.server.entity.EntityAttribute;
import com.echothree.model.data.core.server.entity.EntityDateAttribute;
import com.echothree.model.data.core.server.entity.EntityDateDefault;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class EntityDateAttributeLogic
        extends BaseEntityAttributeValueLogic {

    protected EntityDateAttributeLogic() {
        super();
    }

    public EntityDateDefault createEntityDateDefault(final ExecutionErrorAccumulator eea, final EntityAttribute entityAttribute,
            final Integer dateAttribute, final boolean addMissingAttributes, BasePK createdBy) {
        EntityDateDefault entityDateDefault = null;

        checkEntityType(eea, entityAttribute, EntityAttributeTypes.DATE);

        if(eea == null || !eea.hasExecutionErrors()) {
            entityDateDefault = coreControl.getEntityDateDefault(entityAttribute);

            if(entityDateDefault == null) {
                coreControl.createEntityDateDefault(entityAttribute, dateAttribute, createdBy);

                if(addMissingAttributes) {
                    new EntityInstancesMissingDateEntityAttributeQuery().execute(entityAttribute).forEach(entityInstanceResult ->
                            coreControl.createEntityDateAttribute(entityAttribute.getPrimaryKey(),
                                    entityInstanceResult.getEntityInstance(), dateAttribute, createdBy));
                }
            } else {
                handleExecutionError(DuplicateEntityDateDefaultException.class, eea, ExecutionErrors.DuplicateEntityDateDefault.name(),
                        entityAttribute.getLastDetail().getEntityAttributeName());
            }
        }

        return entityDateDefault;
    }

    public void deleteEntityDateDefault(final ExecutionErrorAccumulator eea, final EntityAttribute entityAttribute,
            final BasePK deletedBy) {
        checkEntityType(eea, entityAttribute, EntityAttributeTypes.DATE);

        if(eea == null || !eea.hasExecutionErrors()) {
            var entityDateDefault = coreControl.getEntityDateDefaultForUpdate(entityAttribute);

            if(entityDateDefault == null) {
                handleExecutionError(UnknownEntityDateDefaultException.class, eea, ExecutionErrors.UnknownEntityDateDefault.name(),
                        entityAttribute.getLastDetail().getEntityAttributeName());
            } else {
                coreControl.deleteEntityDateDefault(entityDateDefault, deletedBy);
            }
        }
    }

    public EntityDateAttribute createEntityDateAttribute(final ExecutionErrorAccumulator eea, final EntityAttribute entityAttribute,
            final EntityInstance entityInstance, final Integer dateAttribute, final BasePK createdBy) {
        EntityDateAttribute entityDateAttribute = null;

        checkEntityType(eea, entityAttribute, entityInstance);

        if(eea == null || !eea.hasExecutionErrors()) {
            entityDateAttribute = coreControl.getEntityDateAttribute(entityAttribute, entityInstance);

            if(entityDateAttribute == null) {
                coreControl.createEntityDateAttribute(entityAttribute, entityInstance, dateAttribute, createdBy);
            } else {
                handleExecutionError(DuplicateEntityDateAttributeException.class, eea, ExecutionErrors.DuplicateEntityDateAttribute.name(),
                        entityInstanceLogic.getEntityRefFromEntityInstance(entityInstance),
                        entityAttribute.getLastDetail().getEntityAttributeName());
            }
        }

        return entityDateAttribute;
    }

    public EntityDateAttribute getEntityDateAttribute(ExecutionErrorAccumulator eea, EntityAttribute entityAttribute, EntityInstance entityInstance,
            EntityPermission entityPermission) {
        var entityDateAttribute = coreControl.getEntityDateAttribute(entityAttribute, entityInstance, entityPermission);

        if(entityDateAttribute == null) {
            var entityTypeDetail = entityAttribute.getLastDetail().getEntityType().getLastDetail();

            handleExecutionError(UnknownEntityDateAttributeException.class, eea, ExecutionErrors.UnknownEntityDateAttribute.name(),
                    entityInstanceLogic.getEntityRefFromEntityInstance(entityInstance), entityTypeDetail.getComponentVendor().getLastDetail().getComponentVendorName(),
                    entityTypeDetail.getEntityTypeName(), entityAttribute.getLastDetail().getEntityAttributeName());
        }

        return entityDateAttribute;
    }

    public EntityDateAttribute getEntityDateAttributeForUpdate(ExecutionErrorAccumulator eea, EntityAttribute entityAttribute,
            EntityInstance entityInstance) {
        return getEntityDateAttribute(eea, entityAttribute, entityInstance, EntityPermission.READ_WRITE);
    }

    public void deleteEntityDateAttribute(final ExecutionErrorAccumulator eea, final EntityDateAttribute entityDateAttribute,
            final BasePK deletedBy) {
        coreControl.deleteEntityDateAttribute(entityDateAttribute, deletedBy);
    }

}
