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

import com.echothree.control.user.item.common.spec.ItemVolumeTypeUniversalSpec;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.item.common.exception.DuplicateItemVolumeTypeNameException;
import com.echothree.model.control.item.common.exception.UnknownDefaultItemVolumeTypeException;
import com.echothree.model.control.item.common.exception.UnknownItemVolumeTypeNameException;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.data.item.server.entity.ItemVolumeType;
import com.echothree.model.data.item.server.value.ItemVolumeTypeDetailValue;
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
public class ItemVolumeTypeLogic
    extends BaseLogic {

    protected ItemVolumeTypeLogic() {
        super();
    }

    public static ItemVolumeTypeLogic getInstance() {
        return CDI.current().select(ItemVolumeTypeLogic.class).get();
    }

    public ItemVolumeType createItemVolumeType(final ExecutionErrorAccumulator eea, final String itemVolumeTypeName,
            final Boolean isDefault, final Integer sortOrder, final Language language, final String description,
            final BasePK createdBy) {
        var itemControl = Session.getModelController(ItemControl.class);
        var itemVolumeType = itemControl.getItemVolumeTypeByName(itemVolumeTypeName);

        if(itemVolumeType == null) {
            itemVolumeType = itemControl.createItemVolumeType(itemVolumeTypeName, isDefault, sortOrder, createdBy);

            if(description != null) {
                itemControl.createItemVolumeTypeDescription(itemVolumeType, language, description, createdBy);
            }
        } else {
            handleExecutionError(DuplicateItemVolumeTypeNameException.class, eea, ExecutionErrors.DuplicateItemVolumeTypeName.name(), itemVolumeTypeName);
        }

        return itemVolumeType;
    }

    public ItemVolumeType getItemVolumeTypeByName(final ExecutionErrorAccumulator eea, final String itemVolumeTypeName,
            final EntityPermission entityPermission) {
        var itemControl = Session.getModelController(ItemControl.class);
        var itemVolumeType = itemControl.getItemVolumeTypeByName(itemVolumeTypeName, entityPermission);

        if(itemVolumeType == null) {
            handleExecutionError(UnknownItemVolumeTypeNameException.class, eea, ExecutionErrors.UnknownItemVolumeTypeName.name(), itemVolumeTypeName);
        }

        return itemVolumeType;
    }

    public ItemVolumeType getItemVolumeTypeByName(final ExecutionErrorAccumulator eea, final String itemVolumeTypeName) {
        return getItemVolumeTypeByName(eea, itemVolumeTypeName, EntityPermission.READ_ONLY);
    }

    public ItemVolumeType getItemVolumeTypeByNameForUpdate(final ExecutionErrorAccumulator eea, final String itemVolumeTypeName) {
        return getItemVolumeTypeByName(eea, itemVolumeTypeName, EntityPermission.READ_WRITE);
    }

    public ItemVolumeType getItemVolumeTypeByUniversalSpec(final ExecutionErrorAccumulator eea,
            final ItemVolumeTypeUniversalSpec universalSpec, boolean allowDefault, final EntityPermission entityPermission) {
        ItemVolumeType itemVolumeType = null;
        var itemControl = Session.getModelController(ItemControl.class);
        var itemVolumeTypeName = universalSpec.getItemVolumeTypeName();
        var parameterCount = (itemVolumeTypeName == null ? 0 : 1) + EntityInstanceLogic.getInstance().countPossibleEntitySpecs(universalSpec);

        switch(parameterCount) {
            case 0 -> {
                if(allowDefault) {
                    itemVolumeType = itemControl.getDefaultItemVolumeType(entityPermission);

                    if(itemVolumeType == null) {
                        handleExecutionError(UnknownDefaultItemVolumeTypeException.class, eea, ExecutionErrors.UnknownDefaultItemVolumeType.name());
                    }
                } else {
                    handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
                }
            }
            case 1 -> {
                if(itemVolumeTypeName == null) {
                    var entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(eea, universalSpec,
                            ComponentVendors.ECHO_THREE.name(), EntityTypes.ItemVolumeType.name());

                    if(!eea.hasExecutionErrors()) {
                        itemVolumeType = itemControl.getItemVolumeTypeByEntityInstance(entityInstance, entityPermission);
                    }
                } else {
                    itemVolumeType = getItemVolumeTypeByName(eea, itemVolumeTypeName, entityPermission);
                }
            }
            default ->
                    handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
        }

        return itemVolumeType;
    }

    public ItemVolumeType getItemVolumeTypeByUniversalSpec(final ExecutionErrorAccumulator eea,
            final ItemVolumeTypeUniversalSpec universalSpec, boolean allowDefault) {
        return getItemVolumeTypeByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_ONLY);
    }

    public ItemVolumeType getItemVolumeTypeByUniversalSpecForUpdate(final ExecutionErrorAccumulator eea,
            final ItemVolumeTypeUniversalSpec universalSpec, boolean allowDefault) {
        return getItemVolumeTypeByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_WRITE);
    }

    public void updateItemVolumeTypeFromValue(final Session session, final ItemVolumeTypeDetailValue itemVolumeTypeDetailValue,
            final BasePK updatedBy) {
        final var itemControl = Session.getModelController(ItemControl.class);

        itemControl.updateItemVolumeTypeFromValue(itemVolumeTypeDetailValue, updatedBy);
    }
    
    public void deleteItemVolumeType(final ExecutionErrorAccumulator eea, final ItemVolumeType itemVolumeType,
            final BasePK deletedBy) {
        var itemControl = Session.getModelController(ItemControl.class);

        itemControl.deleteItemVolumeType(itemVolumeType, deletedBy);
    }

}
