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
import com.echothree.model.control.core.common.exception.DuplicateEntityGeoPointAttributeException;
import com.echothree.model.control.core.common.exception.DuplicateEntityGeoPointDefaultException;
import com.echothree.model.control.core.common.exception.UnknownEntityGeoPointAttributeException;
import com.echothree.model.control.core.common.exception.UnknownEntityGeoPointDefaultException;
import com.echothree.model.control.core.server.database.EntityInstancesMissingGeoPointEntityAttributeQuery;
import com.echothree.model.data.core.server.entity.EntityAttribute;
import com.echothree.model.data.core.server.entity.EntityGeoPointAttribute;
import com.echothree.model.data.core.server.entity.EntityGeoPointDefault;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.core.server.value.EntityGeoPointAttributeValue;
import com.echothree.model.data.core.server.value.EntityGeoPointDefaultValue;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class EntityGeoPointAttributeLogic
        extends BaseEntityAttributeValueLogic {

    protected EntityGeoPointAttributeLogic() {
        super();
    }

    public EntityGeoPointDefault createEntityGeoPointDefault(final ExecutionErrorAccumulator eea, final EntityAttribute entityAttribute,
            final Integer latitude, final Integer longitude, final Long elevation, final Long altitude, final boolean addMissingAttributes, BasePK createdBy) {
        EntityGeoPointDefault entityGeoPointDefault = null;

        checkEntityType(eea, entityAttribute, EntityAttributeTypes.GEOPOINT);

        if(eea == null || !eea.hasExecutionErrors()) {
            entityGeoPointDefault = coreControl.getEntityGeoPointDefault(entityAttribute);

            if(entityGeoPointDefault == null) {
                coreControl.createEntityGeoPointDefault(entityAttribute, latitude, longitude, elevation, altitude, createdBy);

                if(addMissingAttributes) {
                    new EntityInstancesMissingGeoPointEntityAttributeQuery().execute(entityAttribute).forEach(entityInstanceResult ->
                            coreControl.createEntityGeoPointAttribute(entityAttribute.getPrimaryKey(),
                                    entityInstanceResult.getEntityInstance(), latitude, longitude, elevation, altitude, createdBy));
                }
            } else {
                handleExecutionError(DuplicateEntityGeoPointDefaultException.class, eea, ExecutionErrors.DuplicateEntityGeoPointDefault.name(),
                        entityAttribute.getLastDetail().getEntityType().getLastDetail().getComponentVendor().getLastDetail().getComponentVendorName(),
                        entityAttribute.getLastDetail().getEntityType().getLastDetail().getEntityTypeName(),
                        entityAttribute.getLastDetail().getEntityAttributeName());
            }
        }

        return entityGeoPointDefault;
    }

    public EntityGeoPointAttribute createEntityGeoPointAttribute(final ExecutionErrorAccumulator eea, final EntityAttribute entityAttribute,
            final EntityInstance entityInstance, final Integer latitude, final Integer longitude, final Long elevation, final Long altitude, final BasePK createdBy) {
        EntityGeoPointAttribute entityGeoPointAttribute = null;

        checkEntityType(eea, entityAttribute, entityInstance);

        if(eea == null || !eea.hasExecutionErrors()) {
            entityGeoPointAttribute = coreControl.getEntityGeoPointAttribute(entityAttribute, entityInstance);

            if(entityGeoPointAttribute == null) {
                coreControl.createEntityGeoPointAttribute(entityAttribute, entityInstance, latitude, longitude, elevation, altitude, createdBy);
            } else {
                handleExecutionError(DuplicateEntityGeoPointAttributeException.class, eea, ExecutionErrors.DuplicateEntityGeoPointAttribute.name(),
                        entityInstanceLogic.getEntityRefFromEntityInstance(entityInstance),
                        entityAttribute.getLastDetail().getEntityAttributeName());
            }
        }

        return entityGeoPointAttribute;
    }

    public EntityGeoPointAttribute getEntityGeoPointAttribute(ExecutionErrorAccumulator eea, EntityAttribute entityAttribute, EntityInstance entityInstance,
            EntityPermission entityPermission) {
        var entityGeoPointAttribute = coreControl.getEntityGeoPointAttribute(entityAttribute, entityInstance, entityPermission);

        if(entityGeoPointAttribute == null) {
            var entityTypeDetail = entityAttribute.getLastDetail().getEntityType().getLastDetail();

            handleExecutionError(UnknownEntityGeoPointAttributeException.class, eea, ExecutionErrors.UnknownEntityGeoPointAttribute.name(),
                    entityInstanceLogic.getEntityRefFromEntityInstance(entityInstance), entityTypeDetail.getComponentVendor().getLastDetail().getComponentVendorName(),
                    entityTypeDetail.getEntityTypeName(), entityAttribute.getLastDetail().getEntityAttributeName());
        }

        return entityGeoPointAttribute;
    }

    public EntityGeoPointAttribute getEntityGeoPointAttributeForUpdate(ExecutionErrorAccumulator eea, EntityAttribute entityAttribute,
            EntityInstance entityInstance) {
        return getEntityGeoPointAttribute(eea, entityAttribute, entityInstance, EntityPermission.READ_WRITE);
    }

    public void updateEntityGeoPointDefaultFromValue(final ExecutionErrorAccumulator eea, final EntityGeoPointDefaultValue entityGeoPointDefaultValue,
            final BasePK updatedBy) {
        coreControl.updateEntityGeoPointDefaultFromValue(entityGeoPointDefaultValue, updatedBy);
    }

    public void updateEntityGeoPointAttributeFromValue(final ExecutionErrorAccumulator eea, final EntityGeoPointAttributeValue entityGeoPointAttributeValue,
            final BasePK updatedBy) {
        coreControl.updateEntityGeoPointAttributeFromValue(entityGeoPointAttributeValue, updatedBy);
    }

    public void deleteEntityGeoPointDefault(final ExecutionErrorAccumulator eea, final EntityAttribute entityAttribute,
            final BasePK deletedBy) {
        checkEntityType(eea, entityAttribute, EntityAttributeTypes.GEOPOINT);

        if(eea == null || !eea.hasExecutionErrors()) {
            var entityGeoPointDefault = coreControl.getEntityGeoPointDefaultForUpdate(entityAttribute);

            if(entityGeoPointDefault == null) {
                handleExecutionError(UnknownEntityGeoPointDefaultException.class, eea, ExecutionErrors.UnknownEntityGeoPointDefault.name(),
                        entityAttribute.getLastDetail().getEntityType().getLastDetail().getComponentVendor().getLastDetail().getComponentVendorName(),
                        entityAttribute.getLastDetail().getEntityType().getLastDetail().getEntityTypeName(),
                        entityAttribute.getLastDetail().getEntityAttributeName());
            } else {
                coreControl.deleteEntityGeoPointDefault(entityGeoPointDefault, deletedBy);
            }
        }
    }

    public void deleteEntityGeoPointAttribute(final ExecutionErrorAccumulator eea, final EntityGeoPointAttribute entityGeoPointAttribute,
            final BasePK deletedBy) {
        coreControl.deleteEntityGeoPointAttribute(entityGeoPointAttribute, deletedBy);
    }

}
