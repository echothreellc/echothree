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

import com.echothree.control.user.item.common.spec.ItemDescriptionTypeUseTypeUniversalSpec;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.item.common.exception.DuplicateItemDescriptionTypeUseTypeNameException;
import com.echothree.model.control.item.common.exception.UnknownDefaultItemDescriptionTypeUseTypeException;
import com.echothree.model.control.item.common.exception.UnknownItemDescriptionTypeUseTypeNameException;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.data.item.server.entity.ItemDescriptionTypeUseType;
import com.echothree.model.data.item.server.value.ItemDescriptionTypeUseTypeDetailValue;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;
import javax.inject.Inject;

@ApplicationScoped
public class ItemDescriptionTypeUseTypeLogic
    extends BaseLogic {

    @Inject
    ItemControl itemControl;

    protected ItemDescriptionTypeUseTypeLogic() {
        super();
    }

    public static ItemDescriptionTypeUseTypeLogic getInstance() {
        return CDI.current().select(ItemDescriptionTypeUseTypeLogic.class).get();
    }

    public ItemDescriptionTypeUseType createItemDescriptionTypeUseType(final ExecutionErrorAccumulator eea, final String itemDescriptionTypeUseTypeName,
            final Boolean isDefault, final Integer sortOrder, final Language language, final String description, final BasePK createdBy) {
        var itemDescriptionTypeUseType = itemControl.getItemDescriptionTypeUseTypeByName(itemDescriptionTypeUseTypeName);

        if(itemDescriptionTypeUseType == null) {
            itemDescriptionTypeUseType = itemControl.createItemDescriptionTypeUseType(itemDescriptionTypeUseTypeName, isDefault,
                    sortOrder, createdBy);

            if(description != null) {
                itemControl.createItemDescriptionTypeUseTypeDescription(itemDescriptionTypeUseType, language, description, createdBy);
            }
        } else {
            handleExecutionError(DuplicateItemDescriptionTypeUseTypeNameException.class, eea, ExecutionErrors.DuplicateItemDescriptionTypeUseTypeName.name(), itemDescriptionTypeUseTypeName);
        }

        return itemDescriptionTypeUseType;
    }

    public ItemDescriptionTypeUseType getItemDescriptionTypeUseTypeByName(final ExecutionErrorAccumulator eea, final String itemDescriptionTypeUseTypeName,
            final EntityPermission entityPermission) {
        var itemDescriptionTypeUseType = itemControl.getItemDescriptionTypeUseTypeByName(itemDescriptionTypeUseTypeName, entityPermission);

        if(itemDescriptionTypeUseType == null) {
            handleExecutionError(UnknownItemDescriptionTypeUseTypeNameException.class, eea, ExecutionErrors.UnknownItemDescriptionTypeUseTypeName.name(), itemDescriptionTypeUseTypeName);
        }

        return itemDescriptionTypeUseType;
    }

    public ItemDescriptionTypeUseType getItemDescriptionTypeUseTypeByName(final ExecutionErrorAccumulator eea, final String itemDescriptionTypeUseTypeName) {
        return getItemDescriptionTypeUseTypeByName(eea, itemDescriptionTypeUseTypeName, EntityPermission.READ_ONLY);
    }

    public ItemDescriptionTypeUseType getItemDescriptionTypeUseTypeByNameForUpdate(final ExecutionErrorAccumulator eea, final String itemDescriptionTypeUseTypeName) {
        return getItemDescriptionTypeUseTypeByName(eea, itemDescriptionTypeUseTypeName, EntityPermission.READ_WRITE);
    }

    public ItemDescriptionTypeUseType getItemDescriptionTypeUseTypeByUniversalSpec(final ExecutionErrorAccumulator eea,
            final ItemDescriptionTypeUseTypeUniversalSpec universalSpec, boolean allowDefault, final EntityPermission entityPermission) {
        ItemDescriptionTypeUseType itemDescriptionTypeUseType = null;
        var itemDescriptionTypeUseTypeName = universalSpec.getItemDescriptionTypeUseTypeName();
        var parameterCount = (itemDescriptionTypeUseTypeName == null ? 0 : 1) + EntityInstanceLogic.getInstance().countPossibleEntitySpecs(universalSpec);

        switch(parameterCount) {
            case 0 -> {
                if(allowDefault) {
                    itemDescriptionTypeUseType = itemControl.getDefaultItemDescriptionTypeUseType(entityPermission);

                    if(itemDescriptionTypeUseType == null) {
                        handleExecutionError(UnknownDefaultItemDescriptionTypeUseTypeException.class, eea, ExecutionErrors.UnknownDefaultItemDescriptionTypeUseType.name());
                    }
                } else {
                    handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
                }
            }
            case 1 -> {
                if(itemDescriptionTypeUseTypeName == null) {
                    var entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(eea, universalSpec,
                            ComponentVendors.ECHO_THREE.name(), EntityTypes.ItemDescriptionTypeUseType.name());

                    if(!eea.hasExecutionErrors()) {
                        itemDescriptionTypeUseType = itemControl.getItemDescriptionTypeUseTypeByEntityInstance(entityInstance, entityPermission);
                    }
                } else {
                    itemDescriptionTypeUseType = getItemDescriptionTypeUseTypeByName(eea, itemDescriptionTypeUseTypeName, entityPermission);
                }
            }
            default ->
                    handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
        }

        return itemDescriptionTypeUseType;
    }

    public ItemDescriptionTypeUseType getItemDescriptionTypeUseTypeByUniversalSpec(final ExecutionErrorAccumulator eea,
            final ItemDescriptionTypeUseTypeUniversalSpec universalSpec, boolean allowDefault) {
        return getItemDescriptionTypeUseTypeByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_ONLY);
    }

    public ItemDescriptionTypeUseType getItemDescriptionTypeUseTypeByUniversalSpecForUpdate(final ExecutionErrorAccumulator eea,
            final ItemDescriptionTypeUseTypeUniversalSpec universalSpec, boolean allowDefault) {
        return getItemDescriptionTypeUseTypeByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_WRITE);
    }

    public void updateItemDescriptionTypeUseTypeFromValue(ItemDescriptionTypeUseTypeDetailValue itemDescriptionTypeUseTypeDetailValue, BasePK updatedBy) {
        itemControl.updateItemDescriptionTypeUseTypeFromValue(itemDescriptionTypeUseTypeDetailValue, updatedBy);
    }

    public void deleteItemDescriptionTypeUseType(final ExecutionErrorAccumulator eea, final ItemDescriptionTypeUseType itemDescriptionTypeUseType,
            final BasePK deletedBy) {
        itemControl.deleteItemDescriptionTypeUseType(itemDescriptionTypeUseType, deletedBy);
    }

}
