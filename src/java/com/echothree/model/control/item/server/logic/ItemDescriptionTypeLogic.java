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

import com.echothree.control.user.item.common.spec.ItemDescriptionTypeUniversalSpec;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.item.common.exception.DuplicateItemDescriptionTypeNameException;
import com.echothree.model.control.item.common.exception.UnknownDefaultItemDescriptionTypeException;
import com.echothree.model.control.item.common.exception.UnknownItemDescriptionTypeNameException;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.data.core.server.entity.MimeTypeUsageType;
import com.echothree.model.data.item.server.entity.ItemDescriptionType;
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
public class ItemDescriptionTypeLogic
    extends BaseLogic {

    protected ItemDescriptionTypeLogic() {
        super();
    }

    public static ItemDescriptionTypeLogic getInstance() {
        return CDI.current().select(ItemDescriptionTypeLogic.class).get();
    }

    public ItemDescriptionType createItemDescriptionType(final ExecutionErrorAccumulator eea, final String itemDescriptionTypeName,
            final ItemDescriptionType parentItemDescriptionType, final Boolean useParentIfMissing, final MimeTypeUsageType mimeTypeUsageType,
            final Boolean checkContentWebAddress, final Boolean includeInIndex, final Boolean indexDefault, final Boolean isDefault,
            final Integer sortOrder, final Language language, final String description, final BasePK createdBy) {
        var itemControl = Session.getModelController(ItemControl.class);
        var itemDescriptionType = itemControl.getItemDescriptionTypeByName(itemDescriptionTypeName);

        if(itemDescriptionType == null) {
            itemDescriptionType = itemControl.createItemDescriptionType(itemDescriptionTypeName, parentItemDescriptionType,
                    useParentIfMissing, mimeTypeUsageType, checkContentWebAddress, includeInIndex, indexDefault, isDefault,
                    sortOrder, createdBy);

            if(description != null) {
                itemControl.createItemDescriptionTypeDescription(itemDescriptionType, language, description, createdBy);
            }
        } else {
            handleExecutionError(DuplicateItemDescriptionTypeNameException.class, eea, ExecutionErrors.DuplicateItemDescriptionTypeName.name(), itemDescriptionTypeName);
        }

        return itemDescriptionType;
    }

    public ItemDescriptionType getItemDescriptionTypeByName(final ExecutionErrorAccumulator eea, final String itemDescriptionTypeName,
            final EntityPermission entityPermission) {
        var itemControl = Session.getModelController(ItemControl.class);
        var itemDescriptionType = itemControl.getItemDescriptionTypeByName(itemDescriptionTypeName, entityPermission);

        if(itemDescriptionType == null) {
            handleExecutionError(UnknownItemDescriptionTypeNameException.class, eea, ExecutionErrors.UnknownItemDescriptionTypeName.name(), itemDescriptionTypeName);
        }

        return itemDescriptionType;
    }

    public ItemDescriptionType getItemDescriptionTypeByName(final ExecutionErrorAccumulator eea, final String itemDescriptionTypeName) {
        return getItemDescriptionTypeByName(eea, itemDescriptionTypeName, EntityPermission.READ_ONLY);
    }

    public ItemDescriptionType getItemDescriptionTypeByNameForUpdate(final ExecutionErrorAccumulator eea, final String itemDescriptionTypeName) {
        return getItemDescriptionTypeByName(eea, itemDescriptionTypeName, EntityPermission.READ_WRITE);
    }

    public ItemDescriptionType getItemDescriptionTypeByUniversalSpec(final ExecutionErrorAccumulator eea,
            final ItemDescriptionTypeUniversalSpec universalSpec, boolean allowDefault, final EntityPermission entityPermission) {
        ItemDescriptionType itemDescriptionType = null;
        var itemControl = Session.getModelController(ItemControl.class);
        var itemDescriptionTypeName = universalSpec.getItemDescriptionTypeName();
        var parameterCount = (itemDescriptionTypeName == null ? 0 : 1) + EntityInstanceLogic.getInstance().countPossibleEntitySpecs(universalSpec);

        switch(parameterCount) {
            case 0 -> {
                if(allowDefault) {
                    itemDescriptionType = itemControl.getDefaultItemDescriptionType(entityPermission);

                    if(itemDescriptionType == null) {
                        handleExecutionError(UnknownDefaultItemDescriptionTypeException.class, eea, ExecutionErrors.UnknownDefaultItemDescriptionType.name());
                    }
                } else {
                    handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
                }
            }
            case 1 -> {
                if(itemDescriptionTypeName == null) {
                    var entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(eea, universalSpec,
                            ComponentVendors.ECHO_THREE.name(), EntityTypes.ItemDescriptionType.name());

                    if(!eea.hasExecutionErrors()) {
                        itemDescriptionType = itemControl.getItemDescriptionTypeByEntityInstance(entityInstance, entityPermission);
                    }
                } else {
                    itemDescriptionType = getItemDescriptionTypeByName(eea, itemDescriptionTypeName, entityPermission);
                }
            }
            default ->
                    handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
        }

        return itemDescriptionType;
    }

    public ItemDescriptionType getItemDescriptionTypeByUniversalSpec(final ExecutionErrorAccumulator eea,
            final ItemDescriptionTypeUniversalSpec universalSpec, boolean allowDefault) {
        return getItemDescriptionTypeByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_ONLY);
    }

    public ItemDescriptionType getItemDescriptionTypeByUniversalSpecForUpdate(final ExecutionErrorAccumulator eea,
            final ItemDescriptionTypeUniversalSpec universalSpec, boolean allowDefault) {
        return getItemDescriptionTypeByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_WRITE);
    }

    public void deleteItemDescriptionType(final ExecutionErrorAccumulator eea, final ItemDescriptionType itemDescriptionType,
            final BasePK deletedBy) {
        var itemControl = Session.getModelController(ItemControl.class);

        itemControl.deleteItemDescriptionType(itemDescriptionType, deletedBy);
    }

}
