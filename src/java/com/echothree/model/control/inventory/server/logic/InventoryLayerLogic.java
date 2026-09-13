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

import com.echothree.model.control.inventory.common.exception.DuplicateInventoryLayerSequenceException;
import com.echothree.model.control.inventory.common.exception.UnknownInventoryLayerSequenceException;
import com.echothree.model.control.inventory.server.control.InventoryLayerControl;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.data.inventory.server.entity.InventoryCostingPool;
import com.echothree.model.data.inventory.server.entity.InventoryLayer;
import com.echothree.model.data.inventory.server.entity.InventoryTransactionLine;
import com.echothree.model.data.inventory.server.value.InventoryLayerDetailValue;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class InventoryLayerLogic
        extends BaseLogic {

    @Inject
    InventoryLayerControl inventoryLayerControl;

    @Inject
    PartyControl partyControl;

    @Inject
    InventoryCostingPoolLogic inventoryCostingPoolLogic;

    protected InventoryLayerLogic() {
        super();
    }

    public InventoryLayer createInventoryLayer(final ExecutionErrorAccumulator eea, final InventoryCostingPool inventoryCostingPool,
            final InventoryTransactionLine inventoryTransactionLine, final Long receiptQuantity, final Long unitCost,
            final String description, final BasePK createdBy) {
        return createInventoryLayer(eea, inventoryCostingPool, null, inventoryTransactionLine, receiptQuantity, unitCost, description, createdBy);
    }

    public InventoryLayer createInventoryLayer(final ExecutionErrorAccumulator eea, final InventoryCostingPool inventoryCostingPool,
            final Integer inventoryLayerSequence, final InventoryTransactionLine inventoryTransactionLine, final Long receiptQuantity,
            final Long unitCost, final String description, final BasePK createdBy) {
        var inventoryLayer = inventoryLayerSequence == null ? null
                : inventoryLayerControl.getInventoryLayerBySequence(inventoryCostingPool, inventoryLayerSequence);

        if(inventoryLayer == null) {
            inventoryLayer = inventoryLayerControl.createInventoryLayer(inventoryCostingPool, inventoryLayerSequence,
                    inventoryTransactionLine, receiptQuantity, unitCost, description, createdBy);
        } else {
            var inventoryCostingPoolDetail = inventoryCostingPool.getLastDetail();

            handleExecutionError(DuplicateInventoryLayerSequenceException.class, eea, ExecutionErrors.DuplicateInventoryLayerSequence.name(),
                    partyControl.getPartyCompany(inventoryCostingPoolDetail.getCompanyParty()).getPartyCompanyName(),
                    inventoryCostingPoolDetail.getItem().getLastDetail().getItemName(),
                    inventoryCostingPoolDetail.getInventoryCondition().getLastDetail().getInventoryConditionName(), inventoryLayerSequence);
        }

        return inventoryLayer;
    }

    public InventoryLayer getInventoryLayerBySequence(final ExecutionErrorAccumulator eea, final InventoryCostingPool inventoryCostingPool,
            final Integer inventoryLayerSequence, final EntityPermission entityPermission) {
        var inventoryLayer = inventoryLayerControl.getInventoryLayerBySequence(inventoryCostingPool, inventoryLayerSequence, entityPermission);

        if(inventoryLayer == null) {
            var inventoryCostingPoolDetail = inventoryCostingPool.getLastDetail();

            handleExecutionError(UnknownInventoryLayerSequenceException.class, eea, ExecutionErrors.UnknownInventoryLayerSequence.name(),
                    partyControl.getPartyCompany(inventoryCostingPoolDetail.getCompanyParty()).getPartyCompanyName(),
                    inventoryCostingPoolDetail.getItem().getLastDetail().getItemName(),
                    inventoryCostingPoolDetail.getInventoryCondition().getLastDetail().getInventoryConditionName(), inventoryLayerSequence);
        }

        return inventoryLayer;
    }

    public InventoryLayer getInventoryLayerBySequence(final ExecutionErrorAccumulator eea, final InventoryCostingPool inventoryCostingPool,
            final Integer inventoryLayerSequence) {
        return getInventoryLayerBySequence(eea, inventoryCostingPool, inventoryLayerSequence, EntityPermission.READ_ONLY);
    }

    public InventoryLayer getInventoryLayerBySequenceForUpdate(final ExecutionErrorAccumulator eea, final InventoryCostingPool inventoryCostingPool,
            final Integer inventoryLayerSequence) {
        return getInventoryLayerBySequence(eea, inventoryCostingPool, inventoryLayerSequence, EntityPermission.READ_WRITE);
    }

    public InventoryLayer getInventoryLayerByName(final ExecutionErrorAccumulator eea, final String companyName, final String partyName,
            final String itemName, final String inventoryConditionName, final Integer inventoryLayerSequence, final EntityPermission entityPermission) {
        var inventoryCostingPool = inventoryCostingPoolLogic.getInventoryCostingPoolByName(eea, companyName, partyName, itemName, inventoryConditionName);
        InventoryLayer inventoryLayer = null;

        if(eea == null || !eea.hasExecutionErrors()) {
            inventoryLayer = getInventoryLayerBySequence(eea, inventoryCostingPool, inventoryLayerSequence, entityPermission);
        }

        return inventoryLayer;
    }

    public InventoryLayer getInventoryLayerByName(final ExecutionErrorAccumulator eea, final String companyName, final String partyName,
            final String itemName, final String inventoryConditionName, final Integer inventoryLayerSequence) {
        return getInventoryLayerByName(eea, companyName, partyName, itemName, inventoryConditionName, inventoryLayerSequence, EntityPermission.READ_ONLY);
    }

    public void updateInventoryLayerFromValue(final InventoryLayerDetailValue value, final BasePK updatedBy) {
        inventoryLayerControl.updateInventoryLayerFromValue(value, updatedBy);
    }

    public void deleteInventoryLayer(final InventoryLayer inventoryLayer, final BasePK deletedBy) {
        inventoryLayerControl.deleteInventoryLayer(inventoryLayer, deletedBy);
    }

}
