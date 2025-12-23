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

package com.echothree.model.control.item.server.logic;

import com.echothree.control.user.item.common.spec.ItemWeightTypeUniversalSpec;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.item.common.exception.DuplicateItemWeightTypeNameException;
import com.echothree.model.control.item.common.exception.UnknownDefaultItemWeightTypeException;
import com.echothree.model.control.item.common.exception.UnknownItemWeightTypeNameException;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.data.item.server.entity.ItemWeightType;
import com.echothree.model.data.item.server.value.ItemWeightTypeDetailValue;
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
public class ItemWeightTypeLogic
    extends BaseLogic {

    protected ItemWeightTypeLogic() {
        super();
    }

    public static ItemWeightTypeLogic getInstance() {
        return CDI.current().select(ItemWeightTypeLogic.class).get();
    }

    public ItemWeightType createItemWeightType(final ExecutionErrorAccumulator eea, final String itemWeightTypeName,
            final Boolean isDefault, final Integer sortOrder, final Language language, final String description,
            final BasePK createdBy) {
        var itemControl = Session.getModelController(ItemControl.class);
        var itemWeightType = itemControl.getItemWeightTypeByName(itemWeightTypeName);

        if(itemWeightType == null) {
            itemWeightType = itemControl.createItemWeightType(itemWeightTypeName, isDefault, sortOrder, createdBy);

            if(description != null) {
                itemControl.createItemWeightTypeDescription(itemWeightType, language, description, createdBy);
            }
        } else {
            handleExecutionError(DuplicateItemWeightTypeNameException.class, eea, ExecutionErrors.DuplicateItemWeightTypeName.name(), itemWeightTypeName);
        }

        return itemWeightType;
    }

    public ItemWeightType getItemWeightTypeByName(final ExecutionErrorAccumulator eea, final String itemWeightTypeName,
            final EntityPermission entityPermission) {
        var itemControl = Session.getModelController(ItemControl.class);
        var itemWeightType = itemControl.getItemWeightTypeByName(itemWeightTypeName, entityPermission);

        if(itemWeightType == null) {
            handleExecutionError(UnknownItemWeightTypeNameException.class, eea, ExecutionErrors.UnknownItemWeightTypeName.name(), itemWeightTypeName);
        }

        return itemWeightType;
    }

    public ItemWeightType getItemWeightTypeByName(final ExecutionErrorAccumulator eea, final String itemWeightTypeName) {
        return getItemWeightTypeByName(eea, itemWeightTypeName, EntityPermission.READ_ONLY);
    }

    public ItemWeightType getItemWeightTypeByNameForUpdate(final ExecutionErrorAccumulator eea, final String itemWeightTypeName) {
        return getItemWeightTypeByName(eea, itemWeightTypeName, EntityPermission.READ_WRITE);
    }

    public ItemWeightType getItemWeightTypeByUniversalSpec(final ExecutionErrorAccumulator eea,
            final ItemWeightTypeUniversalSpec universalSpec, boolean allowDefault, final EntityPermission entityPermission) {
        ItemWeightType itemWeightType = null;
        var itemControl = Session.getModelController(ItemControl.class);
        var itemWeightTypeName = universalSpec.getItemWeightTypeName();
        var parameterCount = (itemWeightTypeName == null ? 0 : 1) + EntityInstanceLogic.getInstance().countPossibleEntitySpecs(universalSpec);

        switch(parameterCount) {
            case 0 -> {
                if(allowDefault) {
                    itemWeightType = itemControl.getDefaultItemWeightType(entityPermission);

                    if(itemWeightType == null) {
                        handleExecutionError(UnknownDefaultItemWeightTypeException.class, eea, ExecutionErrors.UnknownDefaultItemWeightType.name());
                    }
                } else {
                    handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
                }
            }
            case 1 -> {
                if(itemWeightTypeName == null) {
                    var entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(eea, universalSpec,
                            ComponentVendors.ECHO_THREE.name(), EntityTypes.ItemWeightType.name());

                    if(!eea.hasExecutionErrors()) {
                        itemWeightType = itemControl.getItemWeightTypeByEntityInstance(entityInstance, entityPermission);
                    }
                } else {
                    itemWeightType = getItemWeightTypeByName(eea, itemWeightTypeName, entityPermission);
                }
            }
            default ->
                    handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
        }

        return itemWeightType;
    }

    public ItemWeightType getItemWeightTypeByUniversalSpec(final ExecutionErrorAccumulator eea,
            final ItemWeightTypeUniversalSpec universalSpec, boolean allowDefault) {
        return getItemWeightTypeByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_ONLY);
    }

    public ItemWeightType getItemWeightTypeByUniversalSpecForUpdate(final ExecutionErrorAccumulator eea,
            final ItemWeightTypeUniversalSpec universalSpec, boolean allowDefault) {
        return getItemWeightTypeByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_WRITE);
    }

    public void updateItemWeightTypeFromValue(final Session session, final ItemWeightTypeDetailValue itemWeightTypeDetailValue,
            final BasePK updatedBy) {
        final var itemControl = Session.getModelController(ItemControl.class);

        itemControl.updateItemWeightTypeFromValue(itemWeightTypeDetailValue, updatedBy);
    }
    
    public void deleteItemWeightType(final ExecutionErrorAccumulator eea, final ItemWeightType itemWeightType,
            final BasePK deletedBy) {
        var itemControl = Session.getModelController(ItemControl.class);

        itemControl.deleteItemWeightType(itemWeightType, deletedBy);
    }

}
