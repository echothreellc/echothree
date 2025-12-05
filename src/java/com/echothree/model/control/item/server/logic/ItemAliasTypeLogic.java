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

import com.echothree.control.user.item.common.spec.ItemAliasTypeUniversalSpec;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.item.common.exception.DuplicateItemAliasTypeNameException;
import com.echothree.model.control.item.common.exception.UnknownDefaultItemAliasTypeException;
import com.echothree.model.control.item.common.exception.UnknownItemAliasTypeNameException;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.control.item.server.database.ItemEntityInstanceResult;
import com.echothree.model.control.item.server.database.ItemEntityInstancesByItemAliasTypeQuery;
import com.echothree.model.control.queue.common.QueueTypes;
import com.echothree.model.control.queue.server.control.QueueControl;
import com.echothree.model.control.queue.server.logic.QueueTypeLogic;
import com.echothree.model.data.item.common.pk.ItemAliasTypePK;
import com.echothree.model.data.item.server.entity.ItemAliasChecksumType;
import com.echothree.model.data.item.server.entity.ItemAliasType;
import com.echothree.model.data.item.server.value.ItemAliasTypeDetailValue;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.queue.server.value.QueuedEntityValue;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;

@ApplicationScoped
public class ItemAliasTypeLogic
    extends BaseLogic {

    protected ItemAliasTypeLogic() {
        super();
    }

    public static ItemAliasTypeLogic getInstance() {
        return CDI.current().select(ItemAliasTypeLogic.class).get();
    }

    public ItemAliasType createItemAliasType(final ExecutionErrorAccumulator eea, final String itemAliasTypeName,
            final String validationPattern, final ItemAliasChecksumType itemAliasChecksumType, final Boolean allowMultiple,
            final Boolean isDefault, final Integer sortOrder, final Language language, final String description,
            final BasePK createdBy) {
        var itemControl = Session.getModelController(ItemControl.class);
        var itemAliasType = itemControl.getItemAliasTypeByName(itemAliasTypeName);

        if(itemAliasType == null) {
            itemAliasType = itemControl.createItemAliasType(itemAliasTypeName, validationPattern, itemAliasChecksumType,
                    allowMultiple, isDefault, sortOrder, createdBy);

            if(description != null) {
                itemControl.createItemAliasTypeDescription(itemAliasType, language, description, createdBy);
            }
        } else {
            handleExecutionError(DuplicateItemAliasTypeNameException.class, eea, ExecutionErrors.DuplicateItemAliasTypeName.name(), itemAliasTypeName);
        }

        return itemAliasType;
    }

    public ItemAliasType getItemAliasTypeByName(final ExecutionErrorAccumulator eea, final String itemAliasTypeName,
            final EntityPermission entityPermission) {
        var itemControl = Session.getModelController(ItemControl.class);
        var itemAliasType = itemControl.getItemAliasTypeByName(itemAliasTypeName, entityPermission);

        if(itemAliasType == null) {
            handleExecutionError(UnknownItemAliasTypeNameException.class, eea, ExecutionErrors.UnknownItemAliasTypeName.name(), itemAliasTypeName);
        }

        return itemAliasType;
    }

    public ItemAliasType getItemAliasTypeByName(final ExecutionErrorAccumulator eea, final String itemAliasTypeName) {
        return getItemAliasTypeByName(eea, itemAliasTypeName, EntityPermission.READ_ONLY);
    }

    public ItemAliasType getItemAliasTypeByNameForUpdate(final ExecutionErrorAccumulator eea, final String itemAliasTypeName) {
        return getItemAliasTypeByName(eea, itemAliasTypeName, EntityPermission.READ_WRITE);
    }

    public ItemAliasType getItemAliasTypeByUniversalSpec(final ExecutionErrorAccumulator eea,
            final ItemAliasTypeUniversalSpec universalSpec, boolean allowDefault, final EntityPermission entityPermission) {
        ItemAliasType itemAliasType = null;
        var itemControl = Session.getModelController(ItemControl.class);
        var itemAliasTypeName = universalSpec.getItemAliasTypeName();
        var parameterCount = (itemAliasTypeName == null ? 0 : 1) + EntityInstanceLogic.getInstance().countPossibleEntitySpecs(universalSpec);

        switch(parameterCount) {
            case 0 -> {
                if(allowDefault) {
                    itemAliasType = itemControl.getDefaultItemAliasType(entityPermission);

                    if(itemAliasType == null) {
                        handleExecutionError(UnknownDefaultItemAliasTypeException.class, eea, ExecutionErrors.UnknownDefaultItemAliasType.name());
                    }
                } else {
                    handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
                }
            }
            case 1 -> {
                if(itemAliasTypeName == null) {
                    var entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(eea, universalSpec,
                            ComponentVendors.ECHO_THREE.name(), EntityTypes.ItemAliasType.name());

                    if(!eea.hasExecutionErrors()) {
                        itemAliasType = itemControl.getItemAliasTypeByEntityInstance(entityInstance, entityPermission);
                    }
                } else {
                    itemAliasType = getItemAliasTypeByName(eea, itemAliasTypeName, entityPermission);
                }
            }
            default ->
                    handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
        }

        return itemAliasType;
    }

    public ItemAliasType getItemAliasTypeByUniversalSpec(final ExecutionErrorAccumulator eea,
            final ItemAliasTypeUniversalSpec universalSpec, boolean allowDefault) {
        return getItemAliasTypeByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_ONLY);
    }

    public ItemAliasType getItemAliasTypeByUniversalSpecForUpdate(final ExecutionErrorAccumulator eea,
            final ItemAliasTypeUniversalSpec universalSpec, boolean allowDefault) {
        return getItemAliasTypeByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_WRITE);
    }

    private List<ItemEntityInstanceResult> getItemEntityInstanceResultsByItemAliasType(final ItemAliasTypePK itemAliasTypePK) {
        return new ItemEntityInstancesByItemAliasTypeQuery().execute(itemAliasTypePK);
    }

    public void updateItemAliasTypeFromValue(final Session session, final ItemAliasTypeDetailValue itemAliasTypeDetailValue,
            final BasePK updatedBy) {
        final var itemControl = Session.getModelController(ItemControl.class);

        if(itemAliasTypeDetailValue.getItemAliasTypeNameHasBeenModified()) {
            final var queueControl = Session.getModelController(QueueControl.class);
            final var queueTypePK = QueueTypeLogic.getInstance().getQueueTypeByName(null, QueueTypes.INDEXING.name()).getPrimaryKey();
            final var itemEntityInstanceResults = getItemEntityInstanceResultsByItemAliasType(itemAliasTypeDetailValue.getItemAliasTypePK());
            final var queuedEntities = new ArrayList<QueuedEntityValue>(itemEntityInstanceResults.size());

            for(final var itemEntityInstanceResult : itemEntityInstanceResults) {
                queuedEntities.add(new QueuedEntityValue(queueTypePK, itemEntityInstanceResult.getEntityInstancePK(), session.getStartTimeLong()));
            }

            queueControl.createQueuedEntities(queuedEntities);
        }

        itemControl.updateItemAliasTypeFromValue(itemAliasTypeDetailValue, updatedBy);
    }
    
    public void deleteItemAliasType(final ExecutionErrorAccumulator eea, final ItemAliasType itemAliasType,
            final BasePK deletedBy) {
        var itemControl = Session.getModelController(ItemControl.class);

        itemControl.deleteItemAliasType(itemAliasType, deletedBy);
    }

}
