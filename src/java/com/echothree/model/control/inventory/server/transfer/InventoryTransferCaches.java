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

import com.echothree.model.control.inventory.server.control.InventoryControl;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.transfer.BaseTransferCaches;

public class InventoryTransferCaches
        extends BaseTransferCaches {
    
    protected InventoryControl inventoryControl;
    
    protected InventoryLocationGroupTransferCache inventoryLocationGroupTransferCache;
    protected InventoryLocationGroupDescriptionTransferCache inventoryLocationGroupDescriptionTransferCache;
    protected InventoryConditionTransferCache inventoryConditionTransferCache;
    protected InventoryConditionDescriptionTransferCache inventoryConditionDescriptionTransferCache;
    protected InventoryLocationGroupCapacityTransferCache inventoryLocationGroupCapacityTransferCache;
    protected InventoryLocationGroupVolumeTransferCache inventoryLocationGroupVolumeTransferCache;
    protected PartyInventoryLevelTransferCache partyInventoryLevelTransferCache;
    protected InventoryConditionUseTransferCache inventoryKindUseTransferCache;
    protected InventoryConditionUseTypeTransferCache inventoryConditionUseTypeTransferCache;
    protected InventoryConditionGlAccountTransferCache inventoryConditionGlAccountTransferCache;
    protected LotAliasTypeTransferCache lotAliasTypeTransferCache;
    protected LotAliasTypeDescriptionTransferCache lotAliasTypeDescriptionTransferCache;
    protected LotAliasTransferCache lotAliasTransferCache;
    protected LotTimeTypeTransferCache lotTimeTypeTransferCache;
    protected LotTimeTypeDescriptionTransferCache lotTimeTypeDescriptionTransferCache;
    protected LotTimeTransferCache lotTimeTransferCache;
    protected AllocationPriorityTransferCache allocationPriorityTransferCache;
    protected AllocationPriorityDescriptionTransferCache allocationPriorityDescriptionTransferCache;
    protected LotTransferCache lotTransferCache;
    protected InventoryTransactionTypeTransferCache inventoryTransactionTypeTransferCache;
    protected InventoryTransactionTypeDescriptionTransferCache inventoryTransactionTypeDescriptionTransferCache;
    protected InventoryAdjustmentTypeTransferCache inventoryAdjustmentTypeTransferCache;
    protected InventoryAdjustmentTypeDescriptionTransferCache inventoryAdjustmentTypeDescriptionTransferCache;

    /** Creates a new instance of InventoryTransferCaches */
    public InventoryTransferCaches(UserVisit userVisit, InventoryControl inventoryControl) {
        super(userVisit);
        
        this.inventoryControl = inventoryControl;
    }
    
    public InventoryLocationGroupTransferCache getInventoryLocationGroupTransferCache() {
        if(inventoryLocationGroupTransferCache == null)
            inventoryLocationGroupTransferCache = new InventoryLocationGroupTransferCache(userVisit, inventoryControl);
        
        return inventoryLocationGroupTransferCache;
    }
    
    public InventoryLocationGroupDescriptionTransferCache getInventoryLocationGroupDescriptionTransferCache() {
        if(inventoryLocationGroupDescriptionTransferCache == null)
            inventoryLocationGroupDescriptionTransferCache = new InventoryLocationGroupDescriptionTransferCache(userVisit, inventoryControl);
        
        return inventoryLocationGroupDescriptionTransferCache;
    }
    
    public InventoryConditionTransferCache getInventoryConditionTransferCache() {
        if(inventoryConditionTransferCache == null)
            inventoryConditionTransferCache = new InventoryConditionTransferCache(userVisit, inventoryControl);
        
        return inventoryConditionTransferCache;
    }
    
    public InventoryConditionDescriptionTransferCache getInventoryConditionDescriptionTransferCache() {
        if(inventoryConditionDescriptionTransferCache == null)
            inventoryConditionDescriptionTransferCache = new InventoryConditionDescriptionTransferCache(userVisit, inventoryControl);
        
        return inventoryConditionDescriptionTransferCache;
    }
    
    public InventoryLocationGroupCapacityTransferCache getInventoryLocationGroupCapacityTransferCache() {
        if(inventoryLocationGroupCapacityTransferCache == null)
            inventoryLocationGroupCapacityTransferCache = new InventoryLocationGroupCapacityTransferCache(userVisit, inventoryControl);
        
        return inventoryLocationGroupCapacityTransferCache;
    }
    
    public InventoryLocationGroupVolumeTransferCache getInventoryLocationGroupVolumeTransferCache() {
        if(inventoryLocationGroupVolumeTransferCache == null)
            inventoryLocationGroupVolumeTransferCache = new InventoryLocationGroupVolumeTransferCache(userVisit, inventoryControl);
        
        return inventoryLocationGroupVolumeTransferCache;
    }
    
    public PartyInventoryLevelTransferCache getPartyInventoryLevelTransferCache() {
        if(partyInventoryLevelTransferCache == null)
            partyInventoryLevelTransferCache = new PartyInventoryLevelTransferCache(userVisit, inventoryControl);
        
        return partyInventoryLevelTransferCache;
    }
    
    public InventoryConditionUseTransferCache getInventoryConditionUseTransferCache() {
        if(inventoryKindUseTransferCache == null)
            inventoryKindUseTransferCache = new InventoryConditionUseTransferCache(userVisit, inventoryControl);
        
        return inventoryKindUseTransferCache;
    }
    
    public InventoryConditionUseTypeTransferCache getInventoryConditionUseTypeTransferCache() {
        if(inventoryConditionUseTypeTransferCache == null)
            inventoryConditionUseTypeTransferCache = new InventoryConditionUseTypeTransferCache(userVisit, inventoryControl);
        
        return inventoryConditionUseTypeTransferCache;
    }
    
    public InventoryConditionGlAccountTransferCache getInventoryConditionGlAccountTransferCache() {
        if(inventoryConditionGlAccountTransferCache == null)
            inventoryConditionGlAccountTransferCache = new InventoryConditionGlAccountTransferCache(userVisit, inventoryControl);
        
        return inventoryConditionGlAccountTransferCache;
    }
    
    public LotAliasTypeTransferCache getLotAliasTypeTransferCache() {
        if(lotAliasTypeTransferCache == null)
            lotAliasTypeTransferCache = new LotAliasTypeTransferCache(userVisit, inventoryControl);

        return lotAliasTypeTransferCache;
    }

    public LotAliasTypeDescriptionTransferCache getLotAliasTypeDescriptionTransferCache() {
        if(lotAliasTypeDescriptionTransferCache == null)
            lotAliasTypeDescriptionTransferCache = new LotAliasTypeDescriptionTransferCache(userVisit, inventoryControl);

        return lotAliasTypeDescriptionTransferCache;
    }

    public LotAliasTransferCache getLotAliasTransferCache() {
        if(lotAliasTransferCache == null)
            lotAliasTransferCache = new LotAliasTransferCache(userVisit, inventoryControl);

        return lotAliasTransferCache;
    }

    public LotTimeTypeTransferCache getLotTimeTypeTransferCache() {
        if(lotTimeTypeTransferCache == null)
            lotTimeTypeTransferCache = new LotTimeTypeTransferCache(userVisit, inventoryControl);

        return lotTimeTypeTransferCache;
    }

    public LotTimeTransferCache getLotTimeTransferCache() {
        if(lotTimeTransferCache == null)
            lotTimeTransferCache = new LotTimeTransferCache(userVisit, inventoryControl);

        return lotTimeTransferCache;
    }

    public LotTimeTypeDescriptionTransferCache getLotTimeTypeDescriptionTransferCache() {
        if(lotTimeTypeDescriptionTransferCache == null)
            lotTimeTypeDescriptionTransferCache = new LotTimeTypeDescriptionTransferCache(userVisit, inventoryControl);

        return lotTimeTypeDescriptionTransferCache;
    }

    public AllocationPriorityTransferCache getAllocationPriorityTransferCache() {
        if(allocationPriorityTransferCache == null)
            allocationPriorityTransferCache = new AllocationPriorityTransferCache(userVisit, inventoryControl);

        return allocationPriorityTransferCache;
    }

    public AllocationPriorityDescriptionTransferCache getAllocationPriorityDescriptionTransferCache() {
        if(allocationPriorityDescriptionTransferCache == null)
            allocationPriorityDescriptionTransferCache = new AllocationPriorityDescriptionTransferCache(userVisit, inventoryControl);

        return allocationPriorityDescriptionTransferCache;
    }

    public LotTransferCache getLotTransferCache() {
        if(lotTransferCache == null)
            lotTransferCache = new LotTransferCache(userVisit, inventoryControl);

        return lotTransferCache;
    }

    public InventoryTransactionTypeTransferCache getInventoryTransactionTypeTransferCache() {
        if(inventoryTransactionTypeTransferCache == null)
            inventoryTransactionTypeTransferCache = new InventoryTransactionTypeTransferCache(userVisit, inventoryControl);

        return inventoryTransactionTypeTransferCache;
    }

    public InventoryTransactionTypeDescriptionTransferCache getInventoryTransactionTypeDescriptionTransferCache() {
        if(inventoryTransactionTypeDescriptionTransferCache == null)
            inventoryTransactionTypeDescriptionTransferCache = new InventoryTransactionTypeDescriptionTransferCache(userVisit, inventoryControl);

        return inventoryTransactionTypeDescriptionTransferCache;
    }

    public InventoryAdjustmentTypeTransferCache getInventoryAdjustmentTypeTransferCache() {
        if(inventoryAdjustmentTypeTransferCache == null)
            inventoryAdjustmentTypeTransferCache = new InventoryAdjustmentTypeTransferCache(userVisit, inventoryControl);

        return inventoryAdjustmentTypeTransferCache;
    }

    public InventoryAdjustmentTypeDescriptionTransferCache getInventoryAdjustmentTypeDescriptionTransferCache() {
        if(inventoryAdjustmentTypeDescriptionTransferCache == null)
            inventoryAdjustmentTypeDescriptionTransferCache = new InventoryAdjustmentTypeDescriptionTransferCache(userVisit, inventoryControl);

        return inventoryAdjustmentTypeDescriptionTransferCache;
    }

}
