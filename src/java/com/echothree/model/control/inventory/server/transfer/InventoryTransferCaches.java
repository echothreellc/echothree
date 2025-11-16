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
import javax.enterprise.inject.spi.CDI;

public class InventoryTransferCaches
        extends BaseTransferCaches {
    
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
    public InventoryTransferCaches() {
        super();
    }
    
    public InventoryLocationGroupTransferCache getInventoryLocationGroupTransferCache() {
        if(inventoryLocationGroupTransferCache == null)
            inventoryLocationGroupTransferCache = CDI.current().select(InventoryLocationGroupTransferCache.class).get();
        
        return inventoryLocationGroupTransferCache;
    }
    
    public InventoryLocationGroupDescriptionTransferCache getInventoryLocationGroupDescriptionTransferCache() {
        if(inventoryLocationGroupDescriptionTransferCache == null)
            inventoryLocationGroupDescriptionTransferCache = CDI.current().select(InventoryLocationGroupDescriptionTransferCache.class).get();
        
        return inventoryLocationGroupDescriptionTransferCache;
    }
    
    public InventoryConditionTransferCache getInventoryConditionTransferCache() {
        if(inventoryConditionTransferCache == null)
            inventoryConditionTransferCache = CDI.current().select(InventoryConditionTransferCache.class).get();
        
        return inventoryConditionTransferCache;
    }
    
    public InventoryConditionDescriptionTransferCache getInventoryConditionDescriptionTransferCache() {
        if(inventoryConditionDescriptionTransferCache == null)
            inventoryConditionDescriptionTransferCache = CDI.current().select(InventoryConditionDescriptionTransferCache.class).get();
        
        return inventoryConditionDescriptionTransferCache;
    }
    
    public InventoryLocationGroupCapacityTransferCache getInventoryLocationGroupCapacityTransferCache() {
        if(inventoryLocationGroupCapacityTransferCache == null)
            inventoryLocationGroupCapacityTransferCache = CDI.current().select(InventoryLocationGroupCapacityTransferCache.class).get();
        
        return inventoryLocationGroupCapacityTransferCache;
    }
    
    public InventoryLocationGroupVolumeTransferCache getInventoryLocationGroupVolumeTransferCache() {
        if(inventoryLocationGroupVolumeTransferCache == null)
            inventoryLocationGroupVolumeTransferCache = CDI.current().select(InventoryLocationGroupVolumeTransferCache.class).get();
        
        return inventoryLocationGroupVolumeTransferCache;
    }
    
    public PartyInventoryLevelTransferCache getPartyInventoryLevelTransferCache() {
        if(partyInventoryLevelTransferCache == null)
            partyInventoryLevelTransferCache = CDI.current().select(PartyInventoryLevelTransferCache.class).get();
        
        return partyInventoryLevelTransferCache;
    }
    
    public InventoryConditionUseTransferCache getInventoryConditionUseTransferCache() {
        if(inventoryKindUseTransferCache == null)
            inventoryKindUseTransferCache = CDI.current().select(InventoryConditionUseTransferCache.class).get();
        
        return inventoryKindUseTransferCache;
    }
    
    public InventoryConditionUseTypeTransferCache getInventoryConditionUseTypeTransferCache() {
        if(inventoryConditionUseTypeTransferCache == null)
            inventoryConditionUseTypeTransferCache = CDI.current().select(InventoryConditionUseTypeTransferCache.class).get();
        
        return inventoryConditionUseTypeTransferCache;
    }
    
    public InventoryConditionGlAccountTransferCache getInventoryConditionGlAccountTransferCache() {
        if(inventoryConditionGlAccountTransferCache == null)
            inventoryConditionGlAccountTransferCache = CDI.current().select(InventoryConditionGlAccountTransferCache.class).get();
        
        return inventoryConditionGlAccountTransferCache;
    }
    
    public LotAliasTypeTransferCache getLotAliasTypeTransferCache() {
        if(lotAliasTypeTransferCache == null)
            lotAliasTypeTransferCache = CDI.current().select(LotAliasTypeTransferCache.class).get();

        return lotAliasTypeTransferCache;
    }

    public LotAliasTypeDescriptionTransferCache getLotAliasTypeDescriptionTransferCache() {
        if(lotAliasTypeDescriptionTransferCache == null)
            lotAliasTypeDescriptionTransferCache = CDI.current().select(LotAliasTypeDescriptionTransferCache.class).get();

        return lotAliasTypeDescriptionTransferCache;
    }

    public LotAliasTransferCache getLotAliasTransferCache() {
        if(lotAliasTransferCache == null)
            lotAliasTransferCache = CDI.current().select(LotAliasTransferCache.class).get();

        return lotAliasTransferCache;
    }

    public LotTimeTypeTransferCache getLotTimeTypeTransferCache() {
        if(lotTimeTypeTransferCache == null)
            lotTimeTypeTransferCache = CDI.current().select(LotTimeTypeTransferCache.class).get();

        return lotTimeTypeTransferCache;
    }

    public LotTimeTransferCache getLotTimeTransferCache() {
        if(lotTimeTransferCache == null)
            lotTimeTransferCache = CDI.current().select(LotTimeTransferCache.class).get();

        return lotTimeTransferCache;
    }

    public LotTimeTypeDescriptionTransferCache getLotTimeTypeDescriptionTransferCache() {
        if(lotTimeTypeDescriptionTransferCache == null)
            lotTimeTypeDescriptionTransferCache = CDI.current().select(LotTimeTypeDescriptionTransferCache.class).get();

        return lotTimeTypeDescriptionTransferCache;
    }

    public AllocationPriorityTransferCache getAllocationPriorityTransferCache() {
        if(allocationPriorityTransferCache == null)
            allocationPriorityTransferCache = CDI.current().select(AllocationPriorityTransferCache.class).get();

        return allocationPriorityTransferCache;
    }

    public AllocationPriorityDescriptionTransferCache getAllocationPriorityDescriptionTransferCache() {
        if(allocationPriorityDescriptionTransferCache == null)
            allocationPriorityDescriptionTransferCache = CDI.current().select(AllocationPriorityDescriptionTransferCache.class).get();

        return allocationPriorityDescriptionTransferCache;
    }

    public LotTransferCache getLotTransferCache() {
        if(lotTransferCache == null)
            lotTransferCache = CDI.current().select(LotTransferCache.class).get();

        return lotTransferCache;
    }

    public InventoryTransactionTypeTransferCache getInventoryTransactionTypeTransferCache() {
        if(inventoryTransactionTypeTransferCache == null)
            inventoryTransactionTypeTransferCache = CDI.current().select(InventoryTransactionTypeTransferCache.class).get();

        return inventoryTransactionTypeTransferCache;
    }

    public InventoryTransactionTypeDescriptionTransferCache getInventoryTransactionTypeDescriptionTransferCache() {
        if(inventoryTransactionTypeDescriptionTransferCache == null)
            inventoryTransactionTypeDescriptionTransferCache = CDI.current().select(InventoryTransactionTypeDescriptionTransferCache.class).get();

        return inventoryTransactionTypeDescriptionTransferCache;
    }

    public InventoryAdjustmentTypeTransferCache getInventoryAdjustmentTypeTransferCache() {
        if(inventoryAdjustmentTypeTransferCache == null)
            inventoryAdjustmentTypeTransferCache = CDI.current().select(InventoryAdjustmentTypeTransferCache.class).get();

        return inventoryAdjustmentTypeTransferCache;
    }

    public InventoryAdjustmentTypeDescriptionTransferCache getInventoryAdjustmentTypeDescriptionTransferCache() {
        if(inventoryAdjustmentTypeDescriptionTransferCache == null)
            inventoryAdjustmentTypeDescriptionTransferCache = CDI.current().select(InventoryAdjustmentTypeDescriptionTransferCache.class).get();

        return inventoryAdjustmentTypeDescriptionTransferCache;
    }

}
