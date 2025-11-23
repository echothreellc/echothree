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

import com.echothree.control.user.item.common.spec.ItemImageTypeUniversalSpec;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.item.common.exception.DuplicateItemImageTypeNameException;
import com.echothree.model.control.item.common.exception.UnknownDefaultItemImageTypeException;
import com.echothree.model.control.item.common.exception.UnknownItemImageTypeNameException;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.data.core.server.entity.MimeType;
import com.echothree.model.data.item.server.entity.ItemImageType;
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
public class ItemImageTypeLogic
    extends BaseLogic {

    protected ItemImageTypeLogic() {
        super();
    }

    public static ItemImageTypeLogic getInstance() {
        return CDI.current().select(ItemImageTypeLogic.class).get();
    }

    public ItemImageType createItemImageType(final ExecutionErrorAccumulator eea, final String itemImageTypeName,
            final MimeType preferredMimeType, final Integer quality, final Boolean isDefault, final Integer sortOrder,
            final Language language, final String description, final BasePK createdBy) {
        var itemControl = Session.getModelController(ItemControl.class);
        var itemImageType = itemControl.getItemImageTypeByName(itemImageTypeName);

        if(itemImageType == null) {
            itemImageType = itemControl.createItemImageType(itemImageTypeName, preferredMimeType, quality, isDefault,
                    sortOrder, createdBy);

            if(description != null) {
                itemControl.createItemImageTypeDescription(itemImageType, language, description, createdBy);
            }
        } else {
            handleExecutionError(DuplicateItemImageTypeNameException.class, eea, ExecutionErrors.DuplicateItemImageTypeName.name(), itemImageTypeName);
        }

        return itemImageType;
    }

    public ItemImageType getItemImageTypeByName(final ExecutionErrorAccumulator eea, final String itemImageTypeName,
            final EntityPermission entityPermission) {
        var itemControl = Session.getModelController(ItemControl.class);
        var itemImageType = itemControl.getItemImageTypeByName(itemImageTypeName, entityPermission);

        if(itemImageType == null) {
            handleExecutionError(UnknownItemImageTypeNameException.class, eea, ExecutionErrors.UnknownItemImageTypeName.name(), itemImageTypeName);
        }

        return itemImageType;
    }

    public ItemImageType getItemImageTypeByName(final ExecutionErrorAccumulator eea, final String itemImageTypeName) {
        return getItemImageTypeByName(eea, itemImageTypeName, EntityPermission.READ_ONLY);
    }

    public ItemImageType getItemImageTypeByNameForUpdate(final ExecutionErrorAccumulator eea, final String itemImageTypeName) {
        return getItemImageTypeByName(eea, itemImageTypeName, EntityPermission.READ_WRITE);
    }

    public ItemImageType getItemImageTypeByUniversalSpec(final ExecutionErrorAccumulator eea,
            final ItemImageTypeUniversalSpec universalSpec, boolean allowDefault, final EntityPermission entityPermission) {
        ItemImageType itemImageType = null;
        var itemControl = Session.getModelController(ItemControl.class);
        var itemImageTypeName = universalSpec.getItemImageTypeName();
        var parameterCount = (itemImageTypeName == null ? 0 : 1) + EntityInstanceLogic.getInstance().countPossibleEntitySpecs(universalSpec);

        switch(parameterCount) {
            case 0 -> {
                if(allowDefault) {
                    itemImageType = itemControl.getDefaultItemImageType(entityPermission);

                    if(itemImageType == null) {
                        handleExecutionError(UnknownDefaultItemImageTypeException.class, eea, ExecutionErrors.UnknownDefaultItemImageType.name());
                    }
                } else {
                    handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
                }
            }
            case 1 -> {
                if(itemImageTypeName == null) {
                    var entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(eea, universalSpec,
                            ComponentVendors.ECHO_THREE.name(), EntityTypes.ItemImageType.name());

                    if(!eea.hasExecutionErrors()) {
                        itemImageType = itemControl.getItemImageTypeByEntityInstance(entityInstance, entityPermission);
                    }
                } else {
                    itemImageType = getItemImageTypeByName(eea, itemImageTypeName, entityPermission);
                }
            }
            default ->
                    handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
        }

        return itemImageType;
    }

    public ItemImageType getItemImageTypeByUniversalSpec(final ExecutionErrorAccumulator eea,
            final ItemImageTypeUniversalSpec universalSpec, boolean allowDefault) {
        return getItemImageTypeByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_ONLY);
    }

    public ItemImageType getItemImageTypeByUniversalSpecForUpdate(final ExecutionErrorAccumulator eea,
            final ItemImageTypeUniversalSpec universalSpec, boolean allowDefault) {
        return getItemImageTypeByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_WRITE);
    }

    public void deleteItemImageType(final ExecutionErrorAccumulator eea, final ItemImageType itemImageType,
            final BasePK deletedBy) {
        var itemControl = Session.getModelController(ItemControl.class);

        itemControl.deleteItemImageType(itemImageType, deletedBy);
    }

}
