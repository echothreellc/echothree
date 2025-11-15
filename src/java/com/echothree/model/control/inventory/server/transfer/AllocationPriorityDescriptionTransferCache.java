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

package com.echothree.model.control.inventory.server.transfer;

import com.echothree.model.control.inventory.common.transfer.AllocationPriorityDescriptionTransfer;
import com.echothree.model.control.inventory.server.control.InventoryControl;
import com.echothree.model.data.inventory.server.entity.AllocationPriorityDescription;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class AllocationPriorityDescriptionTransferCache
        extends BaseInventoryDescriptionTransferCache<AllocationPriorityDescription, AllocationPriorityDescriptionTransfer> {

    InventoryControl inventoryControl = Session.getModelController(InventoryControl.class);

    /** Creates a new instance of AllocationPriorityDescriptionTransferCache */
    public AllocationPriorityDescriptionTransferCache() {
        super();
    }
    
    @Override
    public AllocationPriorityDescriptionTransfer getTransfer(UserVisit userVisit, AllocationPriorityDescription allocationPriorityDescription) {
        var allocationPriorityDescriptionTransfer = get(allocationPriorityDescription);
        
        if(allocationPriorityDescriptionTransfer == null) {
            var allocationPriorityTransfer = inventoryControl.getAllocationPriorityTransfer(userVisit, allocationPriorityDescription.getAllocationPriority());
            var languageTransfer = partyControl.getLanguageTransfer(userVisit, allocationPriorityDescription.getLanguage());
            
            allocationPriorityDescriptionTransfer = new AllocationPriorityDescriptionTransfer(languageTransfer, allocationPriorityTransfer, allocationPriorityDescription.getDescription());
            put(userVisit, allocationPriorityDescription, allocationPriorityDescriptionTransfer);
        }
        
        return allocationPriorityDescriptionTransfer;
    }
    
}
