// --------------------------------------------------------------------------------
// Copyright 2002-2019 Echo Three, LLC
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

import com.echothree.model.control.inventory.common.exception.MissingDefaultAllocationPriorityException;
import com.echothree.model.control.inventory.common.exception.UnknownAllocationPriorityNameException;
import com.echothree.model.control.inventory.server.InventoryControl;
import com.echothree.model.data.inventory.server.entity.AllocationPriority;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.Session;

public class AllocationPriorityLogic
        extends BaseLogic {

    protected AllocationPriorityLogic() {
        super();
    }

    private static class AllocationPriorityLogicHolder {
        static AllocationPriorityLogic instance = new AllocationPriorityLogic();
    }

    public static AllocationPriorityLogic getInstance() {
        return AllocationPriorityLogicHolder.instance;
    }

    public AllocationPriority getDefaultAllocationPriority(final ExecutionErrorAccumulator eea) {
        var inventoryControl = (InventoryControl)Session.getModelController(InventoryControl.class);
        AllocationPriority allocationPriority = inventoryControl.getDefaultAllocationPriority();

        if(allocationPriority == null) {
            handleExecutionError(MissingDefaultAllocationPriorityException.class, eea, ExecutionErrors.MissingDefaultAllocationPriority.name());
        }

        return allocationPriority;
    }
    
    public AllocationPriority getAllocationPriorityByName(final ExecutionErrorAccumulator eea, final String allocationPriorityName) {
        var inventoryControl = (InventoryControl)Session.getModelController(InventoryControl.class);
        AllocationPriority allocationPriority = inventoryControl.getAllocationPriorityByName(allocationPriorityName);

        if(allocationPriority == null) {
            handleExecutionError(UnknownAllocationPriorityNameException.class, eea, ExecutionErrors.UnknownAllocationPriorityName.name(), allocationPriorityName);
        }

        return allocationPriority;
    }
    
    public AllocationPriority getAllocationPriorityByNameForUpdate(final ExecutionErrorAccumulator eea, final String allocationPriorityName) {
        var inventoryControl = (InventoryControl)Session.getModelController(InventoryControl.class);
        AllocationPriority allocationPriority = inventoryControl.getAllocationPriorityByNameForUpdate(allocationPriorityName);

        if(allocationPriority == null) {
            handleExecutionError(UnknownAllocationPriorityNameException.class, eea, ExecutionErrors.UnknownAllocationPriorityName.name(), allocationPriorityName);
        }

        return allocationPriority;
    }

}
