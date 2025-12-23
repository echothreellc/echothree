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

import com.echothree.control.user.inventory.common.spec.InventoryAdjustmentTypeUniversalSpec;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.inventory.common.exception.DuplicateInventoryAdjustmentTypeNameException;
import com.echothree.model.control.inventory.common.exception.UnknownDefaultInventoryAdjustmentTypeException;
import com.echothree.model.control.inventory.common.exception.UnknownInventoryAdjustmentTypeNameException;
import com.echothree.model.control.inventory.server.control.InventoryAdjustmentTypeControl;
import com.echothree.model.data.inventory.server.entity.InventoryAdjustmentType;
import com.echothree.model.data.inventory.server.value.InventoryAdjustmentTypeDetailValue;
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
public class InventoryAdjustmentTypeLogic
    extends BaseLogic {

    protected InventoryAdjustmentTypeLogic() {
        super();
    }

    public static InventoryAdjustmentTypeLogic getInstance() {
        return CDI.current().select(InventoryAdjustmentTypeLogic.class).get();
    }

    public InventoryAdjustmentType createInventoryAdjustmentType(final ExecutionErrorAccumulator eea, final String inventoryAdjustmentTypeName,
            final Boolean isDefault, final Integer sortOrder,
            final Language language, final String description, final BasePK createdBy) {
        var inventoryAdjustmentTypeControl = Session.getModelController(InventoryAdjustmentTypeControl.class);
        var inventoryAdjustmentType = inventoryAdjustmentTypeControl.getInventoryAdjustmentTypeByName(inventoryAdjustmentTypeName);

        if(inventoryAdjustmentType == null) {
            inventoryAdjustmentType = inventoryAdjustmentTypeControl.createInventoryAdjustmentType(inventoryAdjustmentTypeName,
                    isDefault, sortOrder, createdBy);

            if(description != null) {
                inventoryAdjustmentTypeControl.createInventoryAdjustmentTypeDescription(inventoryAdjustmentType, language, description, createdBy);
            }
        } else {
            handleExecutionError(DuplicateInventoryAdjustmentTypeNameException.class, eea, ExecutionErrors.DuplicateInventoryAdjustmentTypeName.name(), inventoryAdjustmentTypeName);
        }

        return inventoryAdjustmentType;
    }

    public InventoryAdjustmentType getInventoryAdjustmentTypeByName(final ExecutionErrorAccumulator eea, final String inventoryAdjustmentTypeName,
            final EntityPermission entityPermission) {
        var inventoryAdjustmentTypeControl = Session.getModelController(InventoryAdjustmentTypeControl.class);
        var inventoryAdjustmentType = inventoryAdjustmentTypeControl.getInventoryAdjustmentTypeByName(inventoryAdjustmentTypeName, entityPermission);

        if(inventoryAdjustmentType == null) {
            handleExecutionError(UnknownInventoryAdjustmentTypeNameException.class, eea, ExecutionErrors.UnknownInventoryAdjustmentTypeName.name(), inventoryAdjustmentTypeName);
        }

        return inventoryAdjustmentType;
    }

    public InventoryAdjustmentType getInventoryAdjustmentTypeByName(final ExecutionErrorAccumulator eea, final String inventoryAdjustmentTypeName) {
        return getInventoryAdjustmentTypeByName(eea, inventoryAdjustmentTypeName, EntityPermission.READ_ONLY);
    }

    public InventoryAdjustmentType getInventoryAdjustmentTypeByNameForUpdate(final ExecutionErrorAccumulator eea, final String inventoryAdjustmentTypeName) {
        return getInventoryAdjustmentTypeByName(eea, inventoryAdjustmentTypeName, EntityPermission.READ_WRITE);
    }

    public InventoryAdjustmentType getInventoryAdjustmentTypeByUniversalSpec(final ExecutionErrorAccumulator eea,
            final InventoryAdjustmentTypeUniversalSpec universalSpec, boolean allowDefault, final EntityPermission entityPermission) {
        InventoryAdjustmentType inventoryAdjustmentType = null;
        var inventoryAdjustmentTypeControl = Session.getModelController(InventoryAdjustmentTypeControl.class);
        var inventoryAdjustmentTypeName = universalSpec.getInventoryAdjustmentTypeName();
        var parameterCount = (inventoryAdjustmentTypeName == null ? 0 : 1) + EntityInstanceLogic.getInstance().countPossibleEntitySpecs(universalSpec);

        switch(parameterCount) {
            case 0 -> {
                if(allowDefault) {
                    inventoryAdjustmentType = inventoryAdjustmentTypeControl.getDefaultInventoryAdjustmentType(entityPermission);

                    if(inventoryAdjustmentType == null) {
                        handleExecutionError(UnknownDefaultInventoryAdjustmentTypeException.class, eea, ExecutionErrors.UnknownDefaultInventoryAdjustmentType.name());
                    }
                } else {
                    handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
                }
            }
            case 1 -> {
                if(inventoryAdjustmentTypeName == null) {
                    var entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(eea, universalSpec,
                            ComponentVendors.ECHO_THREE.name(), EntityTypes.InventoryAdjustmentType.name());

                    if(!eea.hasExecutionErrors()) {
                        inventoryAdjustmentType = inventoryAdjustmentTypeControl.getInventoryAdjustmentTypeByEntityInstance(entityInstance, entityPermission);
                    }
                } else {
                    inventoryAdjustmentType = getInventoryAdjustmentTypeByName(eea, inventoryAdjustmentTypeName, entityPermission);
                }
            }
            default ->
                    handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
        }

        return inventoryAdjustmentType;
    }

    public InventoryAdjustmentType getInventoryAdjustmentTypeByUniversalSpec(final ExecutionErrorAccumulator eea,
            final InventoryAdjustmentTypeUniversalSpec universalSpec, boolean allowDefault) {
        return getInventoryAdjustmentTypeByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_ONLY);
    }

    public InventoryAdjustmentType getInventoryAdjustmentTypeByUniversalSpecForUpdate(final ExecutionErrorAccumulator eea,
            final InventoryAdjustmentTypeUniversalSpec universalSpec, boolean allowDefault) {
        return getInventoryAdjustmentTypeByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_WRITE);
    }

    public void updateInventoryAdjustmentTypeFromValue(final InventoryAdjustmentTypeDetailValue inventoryAdjustmentTypeDetailValue,
            final BasePK updatedBy) {
        final var inventoryAdjustmentTypeControl = Session.getModelController(InventoryAdjustmentTypeControl.class);

        inventoryAdjustmentTypeControl.updateInventoryAdjustmentTypeFromValue(inventoryAdjustmentTypeDetailValue, updatedBy);
    }
    
    public void deleteInventoryAdjustmentType(final ExecutionErrorAccumulator eea, final InventoryAdjustmentType inventoryAdjustmentType,
            final BasePK deletedBy) {
        var inventoryAdjustmentTypeControl = Session.getModelController(InventoryAdjustmentTypeControl.class);

        inventoryAdjustmentTypeControl.deleteInventoryAdjustmentType(inventoryAdjustmentType, deletedBy);
    }

}
