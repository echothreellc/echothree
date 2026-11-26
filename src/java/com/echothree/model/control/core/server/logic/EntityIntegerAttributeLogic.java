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
import com.echothree.model.control.core.common.exception.DuplicateEntityIntegerAttributeException;
import com.echothree.model.control.core.common.exception.DuplicateEntityIntegerDefaultException;
import com.echothree.model.control.core.common.exception.LowerRangeExceededException;
import com.echothree.model.control.core.common.exception.UnknownEntityIntegerAttributeException;
import com.echothree.model.control.core.common.exception.UnknownEntityIntegerDefaultException;
import com.echothree.model.control.core.common.exception.UpperRangeExceededException;
import com.echothree.model.control.core.server.database.EntityInstancesMissingIntegerEntityAttributeQuery;
import com.echothree.model.data.core.server.entity.EntityAttribute;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.core.server.entity.EntityIntegerAttribute;
import com.echothree.model.data.core.server.entity.EntityIntegerDefault;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class EntityIntegerAttributeLogic
        extends BaseEntityAttributeValueLogic {

    protected EntityIntegerAttributeLogic() {
        super();
    }

    public EntityIntegerDefault createEntityIntegerDefault(final ExecutionErrorAccumulator eea, final EntityAttribute entityAttribute,
            final Integer integerAttribute, final boolean addMissingAttributes, final BasePK createdBy) {
        EntityIntegerDefault entityIntegerDefault = null;

        checkEntityType(eea, entityAttribute, EntityAttributeTypes.INTEGER);

        if(eea == null || !eea.hasExecutionErrors()) {
            var entityAttributeInteger = coreControl.getEntityAttributeInteger(entityAttribute);

            if(entityAttributeInteger != null) {
                var upperRangeIntegerValue = entityAttributeInteger.getUpperRangeIntegerValue();
                var lowerRangeIntegerValue = entityAttributeInteger.getLowerRangeIntegerValue();

                if(upperRangeIntegerValue != null && integerAttribute > upperRangeIntegerValue){
                    handleExecutionError(UpperRangeExceededException.class, eea, ExecutionErrors.UpperRangeExceeded.name(),
                            upperRangeIntegerValue, integerAttribute);
                }

                if(lowerRangeIntegerValue != null && integerAttribute < lowerRangeIntegerValue) {
                    handleExecutionError(LowerRangeExceededException.class, eea, ExecutionErrors.LowerRangeExceeded.name(),
                            lowerRangeIntegerValue, integerAttribute);
                }
            }

            if(eea == null || !eea.hasExecutionErrors()) {
                entityIntegerDefault = coreControl.getEntityIntegerDefault(entityAttribute);

                if(entityIntegerDefault == null) {
                    coreControl.createEntityIntegerDefault(entityAttribute, integerAttribute, createdBy);

                    if(addMissingAttributes) {
                        new EntityInstancesMissingIntegerEntityAttributeQuery().execute(entityAttribute).forEach(entityInstanceResult ->
                                coreControl.createEntityIntegerAttribute(entityAttribute.getPrimaryKey(),
                                        entityInstanceResult.getEntityInstance(), integerAttribute, createdBy));
                    }
                } else {
                    handleExecutionError(DuplicateEntityIntegerDefaultException.class, eea, ExecutionErrors.DuplicateEntityIntegerDefault.name(),
                            entityAttribute.getLastDetail().getEntityAttributeName());
                }
            }
        }

        return entityIntegerDefault;
    }

    public void deleteEntityIntegerDefault(final ExecutionErrorAccumulator eea, final EntityAttribute entityAttribute,
            final BasePK deletedBy) {
        checkEntityType(eea, entityAttribute, EntityAttributeTypes.INTEGER);

        if(eea == null || !eea.hasExecutionErrors()) {
            var entityIntegerDefault = coreControl.getEntityIntegerDefaultForUpdate(entityAttribute);

            if(entityIntegerDefault == null) {
                handleExecutionError(UnknownEntityIntegerDefaultException.class, eea, ExecutionErrors.UnknownEntityIntegerDefault.name(),
                        entityAttribute.getLastDetail().getEntityAttributeName());
            } else {
                coreControl.deleteEntityIntegerDefault(entityIntegerDefault, deletedBy);
            }
        }
    }

    public EntityIntegerAttribute createEntityIntegerAttribute(final ExecutionErrorAccumulator eea, final EntityAttribute entityAttribute,
            final EntityInstance entityInstance, final Integer integerAttribute, final BasePK createdBy) {
        EntityIntegerAttribute entityIntegerAttribute = null;

        checkEntityType(eea, entityAttribute, entityInstance);

        if(eea == null || !eea.hasExecutionErrors()) {
            var entityAttributeInteger = coreControl.getEntityAttributeInteger(entityAttribute);

            if(entityAttributeInteger != null) {
                var upperRangeIntegerValue = entityAttributeInteger.getUpperRangeIntegerValue();
                var lowerRangeIntegerValue = entityAttributeInteger.getLowerRangeIntegerValue();

                if(upperRangeIntegerValue != null && integerAttribute > upperRangeIntegerValue){
                    handleExecutionError(UpperRangeExceededException.class, eea, ExecutionErrors.UpperRangeExceeded.name(),
                            upperRangeIntegerValue, integerAttribute);
                }

                if(lowerRangeIntegerValue != null && integerAttribute < lowerRangeIntegerValue) {
                    handleExecutionError(LowerRangeExceededException.class, eea, ExecutionErrors.LowerRangeExceeded.name(),
                            lowerRangeIntegerValue, integerAttribute);
                }
            }

            if(eea == null || !eea.hasExecutionErrors()) {
                entityIntegerAttribute = coreControl.getEntityIntegerAttribute(entityAttribute, entityInstance);

                if(entityIntegerAttribute == null) {
                    coreControl.createEntityIntegerAttribute(entityAttribute, entityInstance, integerAttribute, createdBy);
                } else {
                    handleExecutionError(DuplicateEntityIntegerAttributeException.class, eea, ExecutionErrors.DuplicateEntityIntegerAttribute.name(),
                            entityInstanceLogic.getEntityRefFromEntityInstance(entityInstance),
                            entityAttribute.getLastDetail().getEntityAttributeName());
                }
            }
        }

        return entityIntegerAttribute;
    }

    public EntityIntegerAttribute getEntityIntegerAttribute(ExecutionErrorAccumulator eea, EntityAttribute entityAttribute, EntityInstance entityInstance,
            EntityPermission entityPermission) {
        var entityIntegerAttribute = coreControl.getEntityIntegerAttribute(entityAttribute, entityInstance, entityPermission);

        if(entityIntegerAttribute == null) {
            var entityTypeDetail = entityAttribute.getLastDetail().getEntityType().getLastDetail();

            handleExecutionError(UnknownEntityIntegerAttributeException.class, eea, ExecutionErrors.UnknownEntityIntegerAttribute.name(),
                    entityInstanceLogic.getEntityRefFromEntityInstance(entityInstance), entityTypeDetail.getComponentVendor().getLastDetail().getComponentVendorName(),
                    entityTypeDetail.getEntityTypeName(), entityAttribute.getLastDetail().getEntityAttributeName());
        }

        return entityIntegerAttribute;
    }

    public EntityIntegerAttribute getEntityIntegerAttributeForUpdate(ExecutionErrorAccumulator eea, EntityAttribute entityAttribute,
            EntityInstance entityInstance) {
        return getEntityIntegerAttribute(eea, entityAttribute, entityInstance, EntityPermission.READ_WRITE);
    }

    public void deleteEntityIntegerAttribute(final ExecutionErrorAccumulator eea, final EntityIntegerAttribute entityIntegerAttribute,
            final BasePK deletedBy) {
        coreControl.deleteEntityIntegerAttribute(entityIntegerAttribute, deletedBy);
    }

}
