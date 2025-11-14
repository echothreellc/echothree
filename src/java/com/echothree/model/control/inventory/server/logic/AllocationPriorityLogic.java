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

package com.echothree.model.control.inventory.server.logic;

import com.echothree.control.user.inventory.common.spec.AllocationPriorityUniversalSpec;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.inventory.common.exception.DuplicateAllocationPriorityNameException;
import com.echothree.model.control.inventory.common.exception.UnknownAllocationPriorityNameException;
import com.echothree.model.control.inventory.common.exception.UnknownDefaultAllocationPriorityException;
import com.echothree.model.control.inventory.server.control.InventoryControl;
import com.echothree.model.data.inventory.server.entity.AllocationPriority;
import com.echothree.model.data.inventory.server.value.AllocationPriorityDetailValue;
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
public class AllocationPriorityLogic
        extends BaseLogic {

    protected AllocationPriorityLogic() {
        super();
    }

    public static AllocationPriorityLogic getInstance() {
        return CDI.current().select(AllocationPriorityLogic.class).get();
    }

    public AllocationPriority createAllocationPriority(final ExecutionErrorAccumulator eea, final String allocationPriorityName,
            final Integer priority, final Boolean isDefault, final Integer sortOrder, final Language language, final String description,
            final BasePK createdBy) {
        var inventoryControl = Session.getModelController(InventoryControl.class);
        var allocationPriority = inventoryControl.getAllocationPriorityByName(allocationPriorityName);

        if(allocationPriority == null) {
            allocationPriority = inventoryControl.createAllocationPriority(allocationPriorityName, priority, isDefault, sortOrder, createdBy);

            if(description != null) {
                inventoryControl.createAllocationPriorityDescription(allocationPriority, language, description, createdBy);
            }
        } else {
            handleExecutionError(DuplicateAllocationPriorityNameException.class, eea, ExecutionErrors.DuplicateAllocationPriorityName.name(), allocationPriorityName);
        }

        return allocationPriority;
    }

    public AllocationPriority getAllocationPriorityByName(final ExecutionErrorAccumulator eea, final String allocationPriorityName,
            final EntityPermission entityPermission) {
        var inventoryControl = Session.getModelController(InventoryControl.class);
        var allocationPriority = inventoryControl.getAllocationPriorityByName(allocationPriorityName, entityPermission);

        if(allocationPriority == null) {
            handleExecutionError(UnknownAllocationPriorityNameException.class, eea, ExecutionErrors.UnknownAllocationPriorityName.name(), allocationPriorityName);
        }

        return allocationPriority;
    }

    public AllocationPriority getAllocationPriorityByName(final ExecutionErrorAccumulator eea, final String allocationPriorityName) {
        return getAllocationPriorityByName(eea, allocationPriorityName, EntityPermission.READ_ONLY);
    }

    public AllocationPriority getAllocationPriorityByNameForUpdate(final ExecutionErrorAccumulator eea, final String allocationPriorityName) {
        return getAllocationPriorityByName(eea, allocationPriorityName, EntityPermission.READ_WRITE);
    }

    public AllocationPriority getAllocationPriorityByUniversalSpec(final ExecutionErrorAccumulator eea,
            final AllocationPriorityUniversalSpec universalSpec, boolean allowDefault, final EntityPermission entityPermission) {
        AllocationPriority allocationPriority = null;
        var inventoryControl = Session.getModelController(InventoryControl.class);
        var allocationPriorityName = universalSpec.getAllocationPriorityName();
        var parameterCount = (allocationPriorityName == null ? 0 : 1) + EntityInstanceLogic.getInstance().countPossibleEntitySpecs(universalSpec);

        switch(parameterCount) {
            case 0 -> {
                if(allowDefault) {
                    allocationPriority = inventoryControl.getDefaultAllocationPriority(entityPermission);

                    if(allocationPriority == null) {
                        handleExecutionError(UnknownDefaultAllocationPriorityException.class, eea, ExecutionErrors.UnknownDefaultAllocationPriority.name());
                    }
                } else {
                    handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
                }
            }
            case 1 -> {
                if(allocationPriorityName == null) {
                    var entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(eea, universalSpec,
                            ComponentVendors.ECHO_THREE.name(), EntityTypes.AllocationPriority.name());

                    if(!eea.hasExecutionErrors()) {
                        allocationPriority = inventoryControl.getAllocationPriorityByEntityInstance(entityInstance, entityPermission);
                    }
                } else {
                    allocationPriority = getAllocationPriorityByName(eea, allocationPriorityName, entityPermission);
                }
            }
            default ->
                    handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
        }

        return allocationPriority;
    }

    public AllocationPriority getAllocationPriorityByUniversalSpec(final ExecutionErrorAccumulator eea,
            final AllocationPriorityUniversalSpec universalSpec, boolean allowDefault) {
        return getAllocationPriorityByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_ONLY);
    }

    public AllocationPriority getAllocationPriorityByUniversalSpecForUpdate(final ExecutionErrorAccumulator eea,
            final AllocationPriorityUniversalSpec universalSpec, boolean allowDefault) {
        return getAllocationPriorityByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_WRITE);
    }

    public void updateAllocationPriorityFromValue(final AllocationPriorityDetailValue allocationPriorityDetailValue,
            final BasePK updatedBy) {
        var inventoryControl = Session.getModelController(InventoryControl.class);

        inventoryControl.updateAllocationPriorityFromValue(allocationPriorityDetailValue, updatedBy);
    }
    
    public void deleteAllocationPriority(final ExecutionErrorAccumulator eea, final AllocationPriority allocationPriority,
            final BasePK deletedBy) {
        var inventoryControl = Session.getModelController(InventoryControl.class);

        inventoryControl.deleteAllocationPriority(allocationPriority, deletedBy);
    }

}
