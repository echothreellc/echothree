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

import com.echothree.model.control.core.common.exception.DuplicateEntityNameAttributeException;
import com.echothree.model.control.core.common.exception.UnknownEntityNameAttributeException;
import com.echothree.model.data.core.server.entity.EntityAttribute;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.core.server.entity.EntityNameAttribute;
import com.echothree.model.data.core.server.value.EntityNameAttributeValue;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class EntityNameAttributeLogic
        extends BaseEntityAttributeValueLogic {

    protected EntityNameAttributeLogic() {
        super();
    }

    public EntityNameAttribute createEntityNameAttribute(final ExecutionErrorAccumulator eea, final EntityAttribute entityAttribute,
            final String nameAttribute, final EntityInstance entityInstance, final BasePK createdBy) {
        EntityNameAttribute entityNameAttribute = null;

        checkEntityType(eea, entityAttribute, entityInstance);

        if(eea == null || !eea.hasExecutionErrors()) {
            entityNameAttribute = coreControl.getEntityNameAttribute(entityAttribute, entityInstance);

            if(entityNameAttribute == null) {
                coreControl.createEntityNameAttribute(entityAttribute, nameAttribute, entityInstance, createdBy);
            } else {
                handleExecutionError(DuplicateEntityNameAttributeException.class, eea, ExecutionErrors.DuplicateEntityNameAttribute.name(),
                        entityInstanceLogic.getEntityRefFromEntityInstance(entityInstance),
                        entityAttribute.getLastDetail().getEntityAttributeName());
            }
        }

        return entityNameAttribute;
    }

    public EntityNameAttribute getEntityNameAttribute(ExecutionErrorAccumulator eea, EntityAttribute entityAttribute, EntityInstance entityInstance,
            EntityPermission entityPermission) {
        var entityNameAttribute = coreControl.getEntityNameAttribute(entityAttribute, entityInstance, entityPermission);

        if(entityNameAttribute == null) {
            var entityTypeDetail = entityAttribute.getLastDetail().getEntityType().getLastDetail();

            handleExecutionError(UnknownEntityNameAttributeException.class, eea, ExecutionErrors.UnknownEntityNameAttribute.name(),
                    entityInstanceLogic.getEntityRefFromEntityInstance(entityInstance), entityTypeDetail.getComponentVendor().getLastDetail().getComponentVendorName(),
                    entityTypeDetail.getEntityTypeName(), entityAttribute.getLastDetail().getEntityAttributeName());
        }

        return entityNameAttribute;
    }

    public EntityNameAttribute getEntityNameAttributeForUpdate(ExecutionErrorAccumulator eea, EntityAttribute entityAttribute,
            EntityInstance entityInstance) {
        return getEntityNameAttribute(eea, entityAttribute, entityInstance, EntityPermission.READ_WRITE);
    }

    public void updateEntityNameAttributeFromValue(final ExecutionErrorAccumulator eea, final EntityNameAttributeValue entityNameAttributeValue,
            final BasePK updatedBy) {
        coreControl.updateEntityNameAttributeFromValue(entityNameAttributeValue, updatedBy);
    }

    public void deleteEntityNameAttribute(final ExecutionErrorAccumulator eea, final EntityNameAttribute entityNameAttribute,
            final BasePK deletedBy) {
        coreControl.deleteEntityNameAttribute(entityNameAttribute, deletedBy);
    }

}
