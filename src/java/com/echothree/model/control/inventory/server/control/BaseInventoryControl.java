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

package com.echothree.model.control.inventory.server.control;

import com.echothree.model.control.inventory.server.transfer.AllocationPriorityDescriptionTransferCache;
import com.echothree.model.control.inventory.server.transfer.AllocationPriorityTransferCache;
import com.echothree.model.control.inventory.server.transfer.InventoryAdjustmentTypeDescriptionTransferCache;
import com.echothree.model.control.inventory.server.transfer.InventoryAdjustmentTypeTransferCache;
import com.echothree.model.control.inventory.server.transfer.InventoryConditionDescriptionTransferCache;
import com.echothree.model.control.inventory.server.transfer.InventoryConditionGlAccountTransferCache;
import com.echothree.model.control.inventory.server.transfer.InventoryConditionTransferCache;
import com.echothree.model.control.inventory.server.transfer.InventoryConditionUseTransferCache;
import com.echothree.model.control.inventory.server.transfer.InventoryConditionUseTypeTransferCache;
import com.echothree.model.control.inventory.server.transfer.InventoryLocationGroupCapacityTransferCache;
import com.echothree.model.control.inventory.server.transfer.InventoryLocationGroupDescriptionTransferCache;
import com.echothree.model.control.inventory.server.transfer.InventoryLocationGroupTransferCache;
import com.echothree.model.control.inventory.server.transfer.InventoryLocationGroupVolumeTransferCache;
import com.echothree.model.control.inventory.server.transfer.InventoryTransactionTypeDescriptionTransferCache;
import com.echothree.model.control.inventory.server.transfer.InventoryTransactionTypeTransferCache;
import com.echothree.model.control.inventory.server.transfer.LotAliasTransferCache;
import com.echothree.model.control.inventory.server.transfer.LotAliasTypeDescriptionTransferCache;
import com.echothree.model.control.inventory.server.transfer.LotAliasTypeTransferCache;
import com.echothree.model.control.inventory.server.transfer.LotTimeTransferCache;
import com.echothree.model.control.inventory.server.transfer.LotTimeTypeDescriptionTransferCache;
import com.echothree.model.control.inventory.server.transfer.LotTimeTypeTransferCache;
import com.echothree.model.control.inventory.server.transfer.LotTransferCache;
import com.echothree.model.control.inventory.server.transfer.PartyInventoryLevelTransferCache;
import com.echothree.util.server.control.BaseModelControl;
import javax.inject.Inject;

public abstract class BaseInventoryControl
        extends BaseModelControl {

    /** Creates a new instance of BaseInventoryControl */
    protected BaseInventoryControl() {
        super();
    }
    
    // --------------------------------------------------------------------------------
    //   Inventory Transfer Caches
    // --------------------------------------------------------------------------------

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
    InventoryConditionUseTransferCache inventoryConditionUseTransferCache;

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

}
