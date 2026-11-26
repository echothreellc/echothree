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
import com.echothree.model.control.core.common.exception.DuplicateEntityLongAttributeException;
import com.echothree.model.control.core.common.exception.DuplicateEntityLongDefaultException;
import com.echothree.model.control.core.common.exception.LowerRangeExceededException;
import com.echothree.model.control.core.common.exception.UnknownEntityLongAttributeException;
import com.echothree.model.control.core.common.exception.UnknownEntityLongDefaultException;
import com.echothree.model.control.core.common.exception.UpperRangeExceededException;
import com.echothree.model.control.core.server.database.EntityInstancesMissingLongEntityAttributeQuery;
import com.echothree.model.data.core.server.entity.EntityAttribute;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.core.server.entity.EntityLongAttribute;
import com.echothree.model.data.core.server.entity.EntityLongDefault;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class EntityLongAttributeLogic
        extends BaseEntityAttributeValueLogic {

    protected EntityLongAttributeLogic() {
        super();
    }

    public EntityLongDefault createEntityLongDefault(final ExecutionErrorAccumulator eea, final EntityAttribute entityAttribute,
            final Long longAttribute, final boolean addMissingAttributes, BasePK createdBy) {
        EntityLongDefault entityLongDefault = null;

        checkEntityType(eea, entityAttribute, EntityAttributeTypes.LONG);

        if(eea == null || !eea.hasExecutionErrors()) {
            var entityAttributeLong = coreControl.getEntityAttributeLong(entityAttribute);

            if(entityAttributeLong != null) {
                var upperRangeLongValue = entityAttributeLong.getUpperRangeLongValue();
                var lowerRangeLongValue = entityAttributeLong.getLowerRangeLongValue();

                if(upperRangeLongValue != null && longAttribute > upperRangeLongValue){
                    handleExecutionError(UpperRangeExceededException.class, eea, ExecutionErrors.UpperRangeExceeded.name(),
                            upperRangeLongValue, longAttribute);
                }

                if(lowerRangeLongValue != null && longAttribute < lowerRangeLongValue) {
                    handleExecutionError(LowerRangeExceededException.class, eea, ExecutionErrors.LowerRangeExceeded.name(),
                            lowerRangeLongValue, longAttribute);
                }
            }

            if(eea == null || !eea.hasExecutionErrors()) {
                entityLongDefault = coreControl.getEntityLongDefault(entityAttribute);

                if(entityLongDefault == null) {
                    coreControl.createEntityLongDefault(entityAttribute, longAttribute, createdBy);

                    if(addMissingAttributes) {
                        new EntityInstancesMissingLongEntityAttributeQuery().execute(entityAttribute).forEach(entityInstanceResult ->
                                coreControl.createEntityLongAttribute(entityAttribute.getPrimaryKey(),
                                        entityInstanceResult.getEntityInstance(), longAttribute, createdBy));
                    }
                } else {
                    handleExecutionError(DuplicateEntityLongDefaultException.class, eea, ExecutionErrors.DuplicateEntityLongDefault.name(),
                            entityAttribute.getLastDetail().getEntityAttributeName());
                }
            }
        }

        return entityLongDefault;
    }

    public void deleteEntityLongDefault(final ExecutionErrorAccumulator eea, final EntityAttribute entityAttribute,
            final BasePK deletedBy) {
        checkEntityType(eea, entityAttribute, EntityAttributeTypes.LONG);

        if(eea == null || !eea.hasExecutionErrors()) {
            var entityLongDefault = coreControl.getEntityLongDefaultForUpdate(entityAttribute);

            if(entityLongDefault == null) {
                handleExecutionError(UnknownEntityLongDefaultException.class, eea, ExecutionErrors.UnknownEntityLongDefault.name(),
                        entityAttribute.getLastDetail().getEntityAttributeName());
            } else {
                coreControl.deleteEntityLongDefault(entityLongDefault, deletedBy);
            }
        }
    }

    public EntityLongAttribute createEntityLongAttribute(final ExecutionErrorAccumulator eea, final EntityAttribute entityAttribute,
            final EntityInstance entityInstance, final Long longAttribute, final BasePK createdBy) {
        EntityLongAttribute entityLongAttribute = null;

        checkEntityType(eea, entityAttribute, entityInstance);

        if(eea == null || !eea.hasExecutionErrors()) {
            var entityAttributeLong = coreControl.getEntityAttributeLong(entityAttribute);

            if(entityAttributeLong != null) {
                var upperRangeLongValue = entityAttributeLong.getUpperRangeLongValue();
                var lowerRangeLongValue = entityAttributeLong.getLowerRangeLongValue();

                if(upperRangeLongValue != null && longAttribute > upperRangeLongValue){
                    handleExecutionError(UpperRangeExceededException.class, eea, ExecutionErrors.UpperRangeExceeded.name(),
                            upperRangeLongValue, longAttribute);
                }

                if(lowerRangeLongValue != null && longAttribute < lowerRangeLongValue) {
                    handleExecutionError(LowerRangeExceededException.class, eea, ExecutionErrors.LowerRangeExceeded.name(),
                            lowerRangeLongValue, longAttribute);
                }
            }

            if(eea == null || !eea.hasExecutionErrors()) {
                entityLongAttribute = coreControl.getEntityLongAttribute(entityAttribute, entityInstance);

                if(entityLongAttribute == null) {
                    coreControl.createEntityLongAttribute(entityAttribute, entityInstance, longAttribute, createdBy);
                } else {
                    handleExecutionError(DuplicateEntityLongAttributeException.class, eea, ExecutionErrors.DuplicateEntityLongAttribute.name(),
                            entityInstanceLogic.getEntityRefFromEntityInstance(entityInstance),
                            entityAttribute.getLastDetail().getEntityAttributeName());
                }
            }
        }

        return entityLongAttribute;
    }

    public EntityLongAttribute getEntityLongAttribute(ExecutionErrorAccumulator eea, EntityAttribute entityAttribute, EntityInstance entityInstance,
            EntityPermission entityPermission) {
        var entityLongAttribute = coreControl.getEntityLongAttribute(entityAttribute, entityInstance, entityPermission);

        if(entityLongAttribute == null) {
            var entityTypeDetail = entityAttribute.getLastDetail().getEntityType().getLastDetail();

            handleExecutionError(UnknownEntityLongAttributeException.class, eea, ExecutionErrors.UnknownEntityLongAttribute.name(),
                    entityInstanceLogic.getEntityRefFromEntityInstance(entityInstance), entityTypeDetail.getComponentVendor().getLastDetail().getComponentVendorName(),
                    entityTypeDetail.getEntityTypeName(), entityAttribute.getLastDetail().getEntityAttributeName());
        }

        return entityLongAttribute;
    }

    public EntityLongAttribute getEntityLongAttributeForUpdate(ExecutionErrorAccumulator eea, EntityAttribute entityAttribute,
            EntityInstance entityInstance) {
        return getEntityLongAttribute(eea, entityAttribute, entityInstance, EntityPermission.READ_WRITE);
    }

}
