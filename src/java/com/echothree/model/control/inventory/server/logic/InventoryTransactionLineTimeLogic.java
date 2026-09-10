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

import com.echothree.model.control.inventory.server.control.InventoryTransactionTimeControl;
import com.echothree.model.data.inventory.server.entity.InventoryTransactionLine;
import com.echothree.model.data.inventory.server.entity.InventoryTransactionLineTime;
import com.echothree.model.data.inventory.server.entity.InventoryTransactionTimeType;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class InventoryTransactionLineTimeLogic
        extends BaseLogic {

    @Inject
    InventoryTransactionTimeControl inventoryTransactionTimeControl;

    @Inject
    InventoryTransactionTimeTypeLogic inventoryTransactionTimeTypeLogic;

    protected InventoryTransactionLineTimeLogic() {
        super();
    }

    public InventoryTransactionLineTime createInventoryTransactionLineTime(final ExecutionErrorAccumulator eea,
            final InventoryTransactionLine inventoryTransactionLine, final String inventoryTransactionTimeTypeName,
            final Long time, final BasePK createdBy) {
        var inventoryTransactionTimeType = getInventoryTransactionTimeTypeByName(eea, inventoryTransactionLine, inventoryTransactionTimeTypeName);
        InventoryTransactionLineTime inventoryTransactionLineTime = null;

        if(eea == null || !eea.hasExecutionErrors()) {
            inventoryTransactionLineTime = createInventoryTransactionLineTime(inventoryTransactionLine, inventoryTransactionTimeType, time, createdBy);
        }

        return inventoryTransactionLineTime;
    }

    public InventoryTransactionLineTime createInventoryTransactionLineTime(final InventoryTransactionLine inventoryTransactionLine,
            final InventoryTransactionTimeType inventoryTransactionTimeType, final Long time, final BasePK createdBy) {
        return inventoryTransactionTimeControl.createInventoryTransactionLineTime(inventoryTransactionLine, inventoryTransactionTimeType, time, createdBy);
    }

    private InventoryTransactionTimeType getInventoryTransactionTimeTypeByName(final ExecutionErrorAccumulator eea,
            final InventoryTransactionLine inventoryTransactionLine, final String inventoryTransactionTimeTypeName) {
        return inventoryTransactionTimeTypeLogic.getInventoryTransactionTimeTypeByName(eea,
                inventoryTransactionLine.getLastDetail().getInventoryTransaction().getLastDetail().getInventoryTransactionType(), inventoryTransactionTimeTypeName);
    }

    public void createOrUpdateInventoryTransactionLineTime(final ExecutionErrorAccumulator eea,
            final InventoryTransactionLine inventoryTransactionLine, final String inventoryTransactionTimeTypeName,
            final Long time, final BasePK createdBy) {
        var inventoryTransactionTimeType = getInventoryTransactionTimeTypeByName(eea, inventoryTransactionLine, inventoryTransactionTimeTypeName);

        if(eea == null || !eea.hasExecutionErrors()) {
            var inventoryTransactionLineTime = inventoryTransactionTimeControl.getInventoryTransactionLineTime(inventoryTransactionLine, inventoryTransactionTimeType);

            if(inventoryTransactionLineTime == null) {
                inventoryTransactionTimeControl.createInventoryTransactionLineTime(inventoryTransactionLine, inventoryTransactionTimeType, time, createdBy);
            } else {
                var inventoryTransactionLineTimeValue = inventoryTransactionTimeControl.getInventoryTransactionLineTimeValue(inventoryTransactionLineTime);

                inventoryTransactionLineTimeValue.setTime(time);

                inventoryTransactionTimeControl.updateInventoryTransactionLineTimeFromValue(inventoryTransactionLineTimeValue, createdBy);
            }
        }
    }

    public void deleteInventoryTransactionLineTime(final ExecutionErrorAccumulator eea, final InventoryTransactionLineTime inventoryTransactionLineTime,
            final BasePK deletedBy) {
        inventoryTransactionTimeControl.deleteInventoryTransactionLineTime(inventoryTransactionLineTime, deletedBy);
    }

}
