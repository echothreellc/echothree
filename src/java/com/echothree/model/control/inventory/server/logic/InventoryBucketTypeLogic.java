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

package com.echothree.model.control.inventory.server.logic;

import com.echothree.control.user.inventory.common.spec.InventoryBucketTypeUniversalSpec;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.inventory.common.exception.DuplicateInventoryBucketTypeNameException;
import com.echothree.model.control.inventory.common.exception.UnknownDefaultInventoryBucketTypeException;
import com.echothree.model.control.inventory.common.exception.UnknownInventoryBucketTypeNameException;
import com.echothree.model.control.inventory.server.control.InventoryBucketTypeControl;
import com.echothree.model.data.inventory.server.entity.InventoryBucketType;
import com.echothree.model.data.inventory.server.value.InventoryBucketTypeDetailValue;
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
public class InventoryBucketTypeLogic
    extends BaseLogic {

    @Inject
    InventoryBucketTypeControl inventoryBucketTypeControl;

    @Inject
    EntityInstanceLogic entityInstanceLogic;

    protected InventoryBucketTypeLogic() {
        super();
    }

    public static InventoryBucketTypeLogic getInstance() {
        return CDI.current().select(InventoryBucketTypeLogic.class).get();
    }

    public InventoryBucketType createInventoryBucketType(final ExecutionErrorAccumulator eea, final String inventoryBucketTypeName,
            final Boolean isDefault, final Integer sortOrder,
            final Language language, final String description, final BasePK createdBy) {
        var inventoryBucketType = inventoryBucketTypeControl.getInventoryBucketTypeByName(inventoryBucketTypeName);

        if(inventoryBucketType == null) {
            inventoryBucketType = inventoryBucketTypeControl.createInventoryBucketType(inventoryBucketTypeName,
                    isDefault, sortOrder, createdBy);

            if(description != null) {
                inventoryBucketTypeControl.createInventoryBucketTypeDescription(inventoryBucketType, language, description, createdBy);
            }
        } else {
            handleExecutionError(DuplicateInventoryBucketTypeNameException.class, eea, ExecutionErrors.DuplicateInventoryBucketTypeName.name(), inventoryBucketTypeName);
        }

        return inventoryBucketType;
    }

    public InventoryBucketType getInventoryBucketTypeByName(final ExecutionErrorAccumulator eea, final String inventoryBucketTypeName,
            final EntityPermission entityPermission) {
        var inventoryBucketType = inventoryBucketTypeControl.getInventoryBucketTypeByName(inventoryBucketTypeName, entityPermission);

        if(inventoryBucketType == null) {
            handleExecutionError(UnknownInventoryBucketTypeNameException.class, eea, ExecutionErrors.UnknownInventoryBucketTypeName.name(), inventoryBucketTypeName);
        }

        return inventoryBucketType;
    }

    public InventoryBucketType getInventoryBucketTypeByName(final ExecutionErrorAccumulator eea, final String inventoryBucketTypeName) {
        return getInventoryBucketTypeByName(eea, inventoryBucketTypeName, EntityPermission.READ_ONLY);
    }

    public InventoryBucketType getInventoryBucketTypeByNameForUpdate(final ExecutionErrorAccumulator eea, final String inventoryBucketTypeName) {
        return getInventoryBucketTypeByName(eea, inventoryBucketTypeName, EntityPermission.READ_WRITE);
    }

    public InventoryBucketType getInventoryBucketTypeByUniversalSpec(final ExecutionErrorAccumulator eea,
            final InventoryBucketTypeUniversalSpec universalSpec, boolean allowDefault, final EntityPermission entityPermission) {
        InventoryBucketType inventoryBucketType = null;
        var inventoryBucketTypeName = universalSpec.getInventoryBucketTypeName();
        var parameterCount = (inventoryBucketTypeName == null ? 0 : 1) + entityInstanceLogic.countPossibleEntitySpecs(universalSpec);

        switch(parameterCount) {
            case 0 -> {
                if(allowDefault) {
                    inventoryBucketType = inventoryBucketTypeControl.getDefaultInventoryBucketType(entityPermission);

                    if(inventoryBucketType == null) {
                        handleExecutionError(UnknownDefaultInventoryBucketTypeException.class, eea, ExecutionErrors.UnknownDefaultInventoryBucketType.name());
                    }
                } else {
                    handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
                }
            }
            case 1 -> {
                if(inventoryBucketTypeName == null) {
                    var entityInstance = entityInstanceLogic.getEntityInstance(eea, universalSpec,
                            ComponentVendors.ECHO_THREE.name(), EntityTypes.InventoryBucketType.name());

                    if(eea == null || !eea.hasExecutionErrors()) {
                        inventoryBucketType = inventoryBucketTypeControl.getInventoryBucketTypeByEntityInstance(entityInstance, entityPermission);
                    }
                } else {
                    inventoryBucketType = getInventoryBucketTypeByName(eea, inventoryBucketTypeName, entityPermission);
                }
            }
            default ->
                    handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
        }

        return inventoryBucketType;
    }

    public InventoryBucketType getInventoryBucketTypeByUniversalSpec(final ExecutionErrorAccumulator eea,
            final InventoryBucketTypeUniversalSpec universalSpec, boolean allowDefault) {
        return getInventoryBucketTypeByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_ONLY);
    }

    public InventoryBucketType getInventoryBucketTypeByUniversalSpecForUpdate(final ExecutionErrorAccumulator eea,
            final InventoryBucketTypeUniversalSpec universalSpec, boolean allowDefault) {
        return getInventoryBucketTypeByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_WRITE);
    }

    public void updateInventoryBucketTypeFromValue(final InventoryBucketTypeDetailValue inventoryBucketTypeDetailValue,
            final BasePK updatedBy) {
        inventoryBucketTypeControl.updateInventoryBucketTypeFromValue(inventoryBucketTypeDetailValue, updatedBy);
    }
    
    public void deleteInventoryBucketType(final ExecutionErrorAccumulator eea, final InventoryBucketType inventoryBucketType,
            final BasePK deletedBy) {
        inventoryBucketTypeControl.deleteInventoryBucketType(inventoryBucketType, deletedBy);
    }

}
