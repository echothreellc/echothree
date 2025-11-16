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

import com.echothree.util.server.transfer.BaseTransferCaches;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

@RequestScoped
public class InventoryTransferCaches
        extends BaseTransferCaches {
    
    @Inject
    InventoryLocationGroupTransferCache inventoryLocationGroupTransferCache;
    
    @Inject
    InventoryLocationGroupDescriptionTransferCache inventoryLocationGroupDescriptionTransferCache;
    
    @Inject
    InventoryConditionTransferCache inventoryConditionTransferCache;
    
    @Inject
    InventoryConditionDescriptionTransferCache inventoryConditionDescriptionTransferCache;
    
    @Inject
    InventoryLocationGroupCapacityTransferCache inventoryLocationGroupCapacityTransferCache;
    
    @Inject
    InventoryLocationGroupVolumeTransferCache inventoryLocationGroupVolumeTransferCache;
    
    @Inject
    PartyInventoryLevelTransferCache partyInventoryLevelTransferCache;
    
    @Inject
    InventoryConditionUseTransferCache inventoryKindUseTransferCache;
    
    @Inject
    InventoryConditionUseTypeTransferCache inventoryConditionUseTypeTransferCache;
    
    @Inject
    InventoryConditionGlAccountTransferCache inventoryConditionGlAccountTransferCache;
    
    @Inject
    LotAliasTypeTransferCache lotAliasTypeTransferCache;
    
    @Inject
    LotAliasTypeDescriptionTransferCache lotAliasTypeDescriptionTransferCache;
    
    @Inject
    LotAliasTransferCache lotAliasTransferCache;
    
    @Inject
    LotTimeTypeTransferCache lotTimeTypeTransferCache;
    
    @Inject
    LotTimeTypeDescriptionTransferCache lotTimeTypeDescriptionTransferCache;
    
    @Inject
    LotTimeTransferCache lotTimeTransferCache;
    
    @Inject
    AllocationPriorityTransferCache allocationPriorityTransferCache;
    
    @Inject
    AllocationPriorityDescriptionTransferCache allocationPriorityDescriptionTransferCache;
    
    @Inject
    LotTransferCache lotTransferCache;
    
    @Inject
    InventoryTransactionTypeTransferCache inventoryTransactionTypeTransferCache;
    
    @Inject
    InventoryTransactionTypeDescriptionTransferCache inventoryTransactionTypeDescriptionTransferCache;
    
    @Inject
    InventoryAdjustmentTypeTransferCache inventoryAdjustmentTypeTransferCache;
    
    @Inject
    InventoryAdjustmentTypeDescriptionTransferCache inventoryAdjustmentTypeDescriptionTransferCache;
    
    /** Creates a new instance of InventoryTransferCaches */
    protected InventoryTransferCaches() {
        super();
    }
    
    public InventoryLocationGroupTransferCache getInventoryLocationGroupTransferCache() {
        return inventoryLocationGroupTransferCache;
    }
    
    public InventoryLocationGroupDescriptionTransferCache getInventoryLocationGroupDescriptionTransferCache() {
        return inventoryLocationGroupDescriptionTransferCache;
    }
    
    public InventoryConditionTransferCache getInventoryConditionTransferCache() {
        return inventoryConditionTransferCache;
    }
    
    public InventoryConditionDescriptionTransferCache getInventoryConditionDescriptionTransferCache() {
        return inventoryConditionDescriptionTransferCache;
    }
    
    public InventoryLocationGroupCapacityTransferCache getInventoryLocationGroupCapacityTransferCache() {
        return inventoryLocationGroupCapacityTransferCache;
    }
    
    public InventoryLocationGroupVolumeTransferCache getInventoryLocationGroupVolumeTransferCache() {
        return inventoryLocationGroupVolumeTransferCache;
    }
    
    public PartyInventoryLevelTransferCache getPartyInventoryLevelTransferCache() {
        return partyInventoryLevelTransferCache;
    }
    
    public InventoryConditionUseTransferCache getInventoryConditionUseTransferCache() {
        return inventoryKindUseTransferCache;
    }
    
    public InventoryConditionUseTypeTransferCache getInventoryConditionUseTypeTransferCache() {
        return inventoryConditionUseTypeTransferCache;
    }
    
    public InventoryConditionGlAccountTransferCache getInventoryConditionGlAccountTransferCache() {
        return inventoryConditionGlAccountTransferCache;
    }
    
    public LotAliasTypeTransferCache getLotAliasTypeTransferCache() {
        return lotAliasTypeTransferCache;
    }

    public LotAliasTypeDescriptionTransferCache getLotAliasTypeDescriptionTransferCache() {
        return lotAliasTypeDescriptionTransferCache;
    }

    public LotAliasTransferCache getLotAliasTransferCache() {
        return lotAliasTransferCache;
    }

    public LotTimeTypeTransferCache getLotTimeTypeTransferCache() {
        return lotTimeTypeTransferCache;
    }

    public LotTimeTransferCache getLotTimeTransferCache() {
        return lotTimeTransferCache;
    }

    public LotTimeTypeDescriptionTransferCache getLotTimeTypeDescriptionTransferCache() {
        return lotTimeTypeDescriptionTransferCache;
    }

    public AllocationPriorityTransferCache getAllocationPriorityTransferCache() {
        return allocationPriorityTransferCache;
    }

    public AllocationPriorityDescriptionTransferCache getAllocationPriorityDescriptionTransferCache() {
        return allocationPriorityDescriptionTransferCache;
    }

    public LotTransferCache getLotTransferCache() {
        return lotTransferCache;
    }

    public InventoryTransactionTypeTransferCache getInventoryTransactionTypeTransferCache() {
        return inventoryTransactionTypeTransferCache;
    }

    public InventoryTransactionTypeDescriptionTransferCache getInventoryTransactionTypeDescriptionTransferCache() {
        return inventoryTransactionTypeDescriptionTransferCache;
    }

    public InventoryAdjustmentTypeTransferCache getInventoryAdjustmentTypeTransferCache() {
        return inventoryAdjustmentTypeTransferCache;
    }

    public InventoryAdjustmentTypeDescriptionTransferCache getInventoryAdjustmentTypeDescriptionTransferCache() {
        return inventoryAdjustmentTypeDescriptionTransferCache;
    }

}
