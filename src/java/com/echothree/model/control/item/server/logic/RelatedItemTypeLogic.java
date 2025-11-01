// --------------------------------------------------------------------------------
// Copyright 2002-2025 Echo Three, LLC
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

package com.echothree.model.control.item.server.logic;

import com.echothree.control.user.item.common.spec.RelatedItemTypeUniversalSpec;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.item.common.exception.DuplicateRelatedItemTypeNameException;
import com.echothree.model.control.item.common.exception.UnknownDefaultRelatedItemTypeException;
import com.echothree.model.control.item.common.exception.UnknownRelatedItemTypeNameException;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.data.item.server.entity.RelatedItemType;
import com.echothree.model.data.item.server.value.RelatedItemTypeDetailValue;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;

@ApplicationScoped
public class RelatedItemTypeLogic
    extends BaseLogic {

    protected RelatedItemTypeLogic() {
        super();
    }

    public static RelatedItemTypeLogic getInstance() {
        return CDI.current().select(RelatedItemTypeLogic.class).get();
    }

    public RelatedItemType createRelatedItemType(final ExecutionErrorAccumulator eea, final String relatedItemTypeName,
            final Boolean isDefault, final Integer sortOrder, final Language language, final String description,
            final BasePK createdBy) {
        var itemControl = Session.getModelController(ItemControl.class);
        var relatedItemType = itemControl.getRelatedItemTypeByName(relatedItemTypeName);

        if(relatedItemType == null) {
            relatedItemType = itemControl.createRelatedItemType(relatedItemTypeName, isDefault, sortOrder, createdBy);

            if(description != null) {
                itemControl.createRelatedItemTypeDescription(relatedItemType, language, description, createdBy);
            }
        } else {
            handleExecutionError(DuplicateRelatedItemTypeNameException.class, eea, ExecutionErrors.DuplicateRelatedItemTypeName.name(), relatedItemTypeName);
        }

        return relatedItemType;
    }

    public RelatedItemType getRelatedItemTypeByName(final ExecutionErrorAccumulator eea, final String relatedItemTypeName,
            final EntityPermission entityPermission) {
        var itemControl = Session.getModelController(ItemControl.class);
        var relatedItemType = itemControl.getRelatedItemTypeByName(relatedItemTypeName, entityPermission);

        if(relatedItemType == null) {
            handleExecutionError(UnknownRelatedItemTypeNameException.class, eea, ExecutionErrors.UnknownRelatedItemTypeName.name(), relatedItemTypeName);
        }

        return relatedItemType;
    }

    public RelatedItemType getRelatedItemTypeByName(final ExecutionErrorAccumulator eea, final String relatedItemTypeName) {
        return getRelatedItemTypeByName(eea, relatedItemTypeName, EntityPermission.READ_ONLY);
    }

    public RelatedItemType getRelatedItemTypeByNameForUpdate(final ExecutionErrorAccumulator eea, final String relatedItemTypeName) {
        return getRelatedItemTypeByName(eea, relatedItemTypeName, EntityPermission.READ_WRITE);
    }

    public RelatedItemType getRelatedItemTypeByUniversalSpec(final ExecutionErrorAccumulator eea,
            final RelatedItemTypeUniversalSpec universalSpec, boolean allowDefault, final EntityPermission entityPermission) {
        RelatedItemType relatedItemType = null;
        var itemControl = Session.getModelController(ItemControl.class);
        var relatedItemTypeName = universalSpec.getRelatedItemTypeName();
        var parameterCount = (relatedItemTypeName == null ? 0 : 1) + EntityInstanceLogic.getInstance().countPossibleEntitySpecs(universalSpec);

        switch(parameterCount) {
            case 0:
                if(allowDefault) {
                    relatedItemType = itemControl.getDefaultRelatedItemType(entityPermission);

                    if(relatedItemType == null) {
                        handleExecutionError(UnknownDefaultRelatedItemTypeException.class, eea, ExecutionErrors.UnknownDefaultRelatedItemType.name());
                    }
                } else {
                    handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
                }
                break;
            case 1:
                if(relatedItemTypeName == null) {
                    var entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(eea, universalSpec,
                            ComponentVendors.ECHO_THREE.name(), EntityTypes.RelatedItemType.name());

                    if(!eea.hasExecutionErrors()) {
                        relatedItemType = itemControl.getRelatedItemTypeByEntityInstance(entityInstance, entityPermission);
                    }
                } else {
                    relatedItemType = getRelatedItemTypeByName(eea, relatedItemTypeName, entityPermission);
                }
                break;
            default:
                handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
                break;
        }

        return relatedItemType;
    }

    public RelatedItemType getRelatedItemTypeByUniversalSpec(final ExecutionErrorAccumulator eea,
            final RelatedItemTypeUniversalSpec universalSpec, boolean allowDefault) {
        return getRelatedItemTypeByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_ONLY);
    }

    public RelatedItemType getRelatedItemTypeByUniversalSpecForUpdate(final ExecutionErrorAccumulator eea,
            final RelatedItemTypeUniversalSpec universalSpec, boolean allowDefault) {
        return getRelatedItemTypeByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_WRITE);
    }

    public void updateRelatedItemTypeFromValue(final RelatedItemTypeDetailValue relatedItemTypeDetailValue,
            final BasePK updatedBy) {
        final var itemControl = Session.getModelController(ItemControl.class);

        itemControl.updateRelatedItemTypeFromValue(relatedItemTypeDetailValue, updatedBy);
    }
    
    public void deleteRelatedItemType(final ExecutionErrorAccumulator eea, final RelatedItemType relatedItemType,
            final BasePK deletedBy) {
        var itemControl = Session.getModelController(ItemControl.class);

        itemControl.deleteRelatedItemType(relatedItemType, deletedBy);
    }

}
