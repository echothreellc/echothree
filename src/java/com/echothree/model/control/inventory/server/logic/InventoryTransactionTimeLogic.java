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

import com.echothree.model.control.inventory.common.exception.UnknownInventoryTransactionTimeException;
import com.echothree.model.control.inventory.server.control.InventoryTransactionTimeControl;
import com.echothree.model.data.inventory.server.entity.InventoryTransaction;
import com.echothree.model.data.inventory.server.entity.InventoryTransactionTime;
import com.echothree.model.data.inventory.server.entity.InventoryTransactionTimeType;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class InventoryTransactionTimeLogic
        extends BaseLogic {

    @Inject
    InventoryTransactionTimeControl inventoryTransactionTimeControl;

    @Inject
    InventoryTransactionTimeTypeLogic inventoryTransactionTimeTypeLogic;

    protected InventoryTransactionTimeLogic() {
        super();
    }

    private InventoryTransactionTimeType getInventoryTransactionTimeTypeByName(final ExecutionErrorAccumulator eea,
            final InventoryTransaction inventoryTransaction, final String inventoryTransactionTimeTypeName) {
        return inventoryTransactionTimeTypeLogic.getInventoryTransactionTimeTypeByName(eea,
                inventoryTransaction.getLastDetail().getInventoryTransactionType(), inventoryTransactionTimeTypeName);
    }

    public InventoryTransactionTime createInventoryTransactionTime(final ExecutionErrorAccumulator eea,
            final InventoryTransaction inventoryTransaction, final String inventoryTransactionTimeTypeName,
            final Long time, final BasePK createdBy) {
        var inventoryTransactionTimeType = getInventoryTransactionTimeTypeByName(eea, inventoryTransaction, inventoryTransactionTimeTypeName);
        InventoryTransactionTime inventoryTransactionTime = null;

        if(eea == null || !eea.hasExecutionErrors()) {
            inventoryTransactionTime = createInventoryTransactionTime(inventoryTransaction, inventoryTransactionTimeType, time, createdBy);
        }

        return inventoryTransactionTime;
    }

    public InventoryTransactionTime createInventoryTransactionTime(final InventoryTransaction inventoryTransaction,
            final InventoryTransactionTimeType inventoryTransactionTimeType, final Long time, final BasePK createdBy) {
        return inventoryTransactionTimeControl.createInventoryTransactionTime(inventoryTransaction, inventoryTransactionTimeType, time, createdBy);
    }

    public void createOrUpdateInventoryTransactionTime(final ExecutionErrorAccumulator eea,
            final InventoryTransaction inventoryTransaction, final String inventoryTransactionTimeTypeName,
            final Long time, final BasePK createdBy) {
        var inventoryTransactionTimeType = getInventoryTransactionTimeTypeByName(eea, inventoryTransaction, inventoryTransactionTimeTypeName);

        if(eea == null || !eea.hasExecutionErrors()) {
            var inventoryTransactionTime = inventoryTransactionTimeControl.getInventoryTransactionTime(inventoryTransaction, inventoryTransactionTimeType);

            if(inventoryTransactionTime == null) {
                inventoryTransactionTimeControl.createInventoryTransactionTime(inventoryTransaction, inventoryTransactionTimeType, time, createdBy);
            } else {
                var inventoryTransactionTimeValue = inventoryTransactionTimeControl.getInventoryTransactionTimeValue(inventoryTransactionTime);

                inventoryTransactionTimeValue.setTime(time);

                inventoryTransactionTimeControl.updateInventoryTransactionTimeFromValue(inventoryTransactionTimeValue, createdBy);
            }
        }
    }

    public void deleteInventoryTransactionTime(final ExecutionErrorAccumulator eea, final InventoryTransactionTime inventoryTransactionTime,
            final BasePK deletedBy) {
        inventoryTransactionTimeControl.deleteInventoryTransactionTime(inventoryTransactionTime, deletedBy);
    }

}
