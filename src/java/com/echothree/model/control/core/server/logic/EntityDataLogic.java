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

import com.echothree.model.control.core.common.exception.UnknownEntityAliasException;
import com.echothree.model.control.core.common.exception.UnknownEntityAppearanceException;
import com.echothree.model.control.core.common.exception.UnknownEntityBlobAttributeException;
import com.echothree.model.control.core.common.exception.UnknownEntityBooleanAttributeException;
import com.echothree.model.control.core.common.exception.UnknownEntityClobAttributeException;
import com.echothree.model.control.core.common.exception.UnknownEntityDateAttributeException;
import com.echothree.model.control.core.common.exception.UnknownEntityEntityAttributeException;
import com.echothree.model.control.core.common.exception.UnknownEntityGeoPointAttributeException;
import com.echothree.model.control.core.common.exception.UnknownEntityIntegerAttributeException;
import com.echothree.model.control.core.common.exception.UnknownEntityListItemAttributeException;
import com.echothree.model.control.core.common.exception.UnknownEntityListItemDefaultException;
import com.echothree.model.control.core.common.exception.UnknownEntityLongAttributeException;
import com.echothree.model.control.core.common.exception.UnknownEntityNameAttributeException;
import com.echothree.model.control.core.common.exception.UnknownEntityRefException;
import com.echothree.model.control.core.common.exception.UnknownEntityStringAttributeException;
import com.echothree.model.control.core.common.exception.UnknownEntityTimeAttributeException;
import com.echothree.model.control.core.server.control.AppearanceControl;
import com.echothree.model.control.core.server.control.EntityAliasControl;
import com.echothree.model.control.core.server.control.EntityInstanceControl;
import com.echothree.model.data.core.server.entity.EntityAlias;
import com.echothree.model.data.core.server.entity.EntityAliasType;
import com.echothree.model.data.core.server.entity.EntityAppearance;
import com.echothree.model.data.core.server.entity.EntityAttribute;
import com.echothree.model.data.core.server.entity.EntityBlobAttribute;
import com.echothree.model.data.core.server.entity.EntityBooleanAttribute;
import com.echothree.model.data.core.server.entity.EntityClobAttribute;
import com.echothree.model.data.core.server.entity.EntityDateAttribute;
import com.echothree.model.data.core.server.entity.EntityEntityAttribute;
import com.echothree.model.data.core.server.entity.EntityGeoPointAttribute;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.core.server.entity.EntityIntegerAttribute;
import com.echothree.model.data.core.server.entity.EntityListItemAttribute;
import com.echothree.model.data.core.server.entity.EntityListItemDefault;
import com.echothree.model.data.core.server.entity.EntityLongAttribute;
import com.echothree.model.data.core.server.entity.EntityNameAttribute;
import com.echothree.model.data.core.server.entity.EntityStringAttribute;
import com.echothree.model.data.core.server.entity.EntityTimeAttribute;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class EntityDataLogic extends BaseLogic {

    @Inject
    AppearanceControl appearanceControl;

    @Inject
    EntityAliasControl entityAliasControl;

    @Inject
    EntityInstanceControl entityInstanceControl;

    @Inject
    EntityInstanceLogic entityInstanceLogic;

    protected EntityDataLogic() {
        super();
    }

    /** Preserve the existence-only lookup used by Appearance and Blob edits. */
    public EntityInstance getEntityInstanceByEntityRef(ExecutionErrorAccumulator eea, String entityRef) {
        var entityInstance = entityInstanceControl.getEntityInstanceByEntityRef(entityRef);

        if(entityInstance == null) {
            handleExecutionError(UnknownEntityRefException.class, eea, ExecutionErrors.UnknownEntityRef.name(), entityRef);
        }

        return entityInstance;
    }

    public EntityAppearance getEntityAppearance(ExecutionErrorAccumulator eea, EntityInstance entityInstance,
            EntityPermission entityPermission) {
        var entityAppearance = appearanceControl.getEntityAppearance(entityInstance, entityPermission);

        if(entityAppearance == null) {
            handleExecutionError(UnknownEntityAppearanceException.class, eea, ExecutionErrors.UnknownEntityAppearance.name(),
                    entityInstanceLogic.getEntityRefFromEntityInstance(entityInstance));
        }

        return entityAppearance;
    }

    public EntityAlias getEntityAlias(ExecutionErrorAccumulator eea, EntityInstance entityInstance, EntityAliasType entityAliasType,
            EntityPermission entityPermission) {
        var entityAlias = entityAliasControl.getEntityAlias(entityInstance, entityAliasType, entityPermission);

        if(entityAlias == null) {
            handleExecutionError(UnknownEntityAliasException.class, eea, ExecutionErrors.UnknownEntityAlias.name(),
                    entityInstanceLogic.getEntityRefFromEntityInstance(entityInstance), entityAliasType.getLastDetail().getEntityAliasTypeName());
        }

        return entityAlias;
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

    public EntityStringAttribute getEntityStringAttribute(ExecutionErrorAccumulator eea, EntityAttribute entityAttribute, EntityInstance entityInstance, Language language,
            EntityPermission entityPermission) {
        var entityStringAttribute = coreControl.getEntityStringAttribute(entityAttribute, entityInstance, language, entityPermission);

        if(entityStringAttribute == null) {
            var entityTypeDetail = entityAttribute.getLastDetail().getEntityType().getLastDetail();

            handleExecutionError(UnknownEntityStringAttributeException.class, eea, ExecutionErrors.UnknownEntityStringAttribute.name(),
                    entityInstanceLogic.getEntityRefFromEntityInstance(entityInstance), entityTypeDetail.getComponentVendor().getLastDetail().getComponentVendorName(),
                    entityTypeDetail.getEntityTypeName(), entityAttribute.getLastDetail().getEntityAttributeName(), language.getLanguageIsoName());
        }

        return entityStringAttribute;
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

    public EntityBlobAttribute getEntityBlobAttribute(ExecutionErrorAccumulator eea, EntityAttribute entityAttribute, EntityInstance entityInstance, Language language,
            EntityPermission entityPermission) {
        var entityBlobAttribute = coreControl.getEntityBlobAttribute(entityAttribute, entityInstance, language, entityPermission);

        if(entityBlobAttribute == null) {
            var entityTypeDetail = entityAttribute.getLastDetail().getEntityType().getLastDetail();

            handleExecutionError(UnknownEntityBlobAttributeException.class, eea, ExecutionErrors.UnknownEntityBlobAttribute.name(),
                    entityInstanceLogic.getEntityRefFromEntityInstance(entityInstance), entityTypeDetail.getComponentVendor().getLastDetail().getComponentVendorName(),
                    entityTypeDetail.getEntityTypeName(), entityAttribute.getLastDetail().getEntityAttributeName(), language.getLanguageIsoName());
        }

        return entityBlobAttribute;
    }

    public EntityClobAttribute getEntityClobAttribute(ExecutionErrorAccumulator eea, EntityAttribute entityAttribute, EntityInstance entityInstance, Language language,
            EntityPermission entityPermission) {
        var entityClobAttribute = coreControl.getEntityClobAttribute(entityAttribute, entityInstance, language, entityPermission);

        if(entityClobAttribute == null) {
            var entityTypeDetail = entityAttribute.getLastDetail().getEntityType().getLastDetail();

            handleExecutionError(UnknownEntityClobAttributeException.class, eea, ExecutionErrors.UnknownEntityClobAttribute.name(),
                    entityInstanceLogic.getEntityRefFromEntityInstance(entityInstance), entityTypeDetail.getComponentVendor().getLastDetail().getComponentVendorName(),
                    entityTypeDetail.getEntityTypeName(), entityAttribute.getLastDetail().getEntityAttributeName(), language.getLanguageIsoName());
        }

        return entityClobAttribute;
    }

    public EntityEntityAttribute getEntityEntityAttribute(ExecutionErrorAccumulator eea, EntityAttribute entityAttribute, EntityInstance entityInstance,
            EntityPermission entityPermission) {
        var entityEntityAttribute = coreControl.getEntityEntityAttribute(entityAttribute, entityInstance, entityPermission);

        if(entityEntityAttribute == null) {
            var entityTypeDetail = entityAttribute.getLastDetail().getEntityType().getLastDetail();

            handleExecutionError(UnknownEntityEntityAttributeException.class, eea, ExecutionErrors.UnknownEntityEntityAttribute.name(),
                    entityInstanceLogic.getEntityRefFromEntityInstance(entityInstance), entityTypeDetail.getComponentVendor().getLastDetail().getComponentVendorName(),
                    entityTypeDetail.getEntityTypeName(), entityAttribute.getLastDetail().getEntityAttributeName());
        }

        return entityEntityAttribute;
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

}
