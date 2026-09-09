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

import com.echothree.model.control.inventory.server.control.InventoryTransactionRoleControl;
import com.echothree.model.data.inventory.server.entity.InventoryTransaction;
import com.echothree.model.data.inventory.server.entity.InventoryTransactionRole;
import com.echothree.model.data.inventory.server.entity.InventoryTransactionRoleType;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class InventoryTransactionRoleLogic
        extends BaseLogic {

    @Inject
    InventoryTransactionRoleControl inventoryTransactionRoleControl;

    @Inject
    InventoryTransactionRoleTypeLogic inventoryTransactionRoleTypeLogic;

    protected InventoryTransactionRoleLogic() {
        super();
    }

    public InventoryTransactionRole createInventoryTransactionRole(final ExecutionErrorAccumulator eea,
            final InventoryTransaction inventoryTransaction, final String inventoryTransactionRoleTypeName,
            final Party party, final BasePK createdBy) {
        var inventoryTransactionRoleType = getInventoryTransactionRoleTypeByName(eea, inventoryTransaction, inventoryTransactionRoleTypeName);
        InventoryTransactionRole inventoryTransactionRole = null;

        if(eea == null || !eea.hasExecutionErrors()) {
            inventoryTransactionRole = createInventoryTransactionRole(inventoryTransaction, inventoryTransactionRoleType, party, createdBy);
        }

        return inventoryTransactionRole;
    }

    public InventoryTransactionRole createInventoryTransactionRole(final InventoryTransaction inventoryTransaction,
            final InventoryTransactionRoleType inventoryTransactionRoleType, final Party party, final BasePK createdBy) {
        return inventoryTransactionRoleControl.createInventoryTransactionRole(inventoryTransaction, inventoryTransactionRoleType, party, createdBy);
    }

    private InventoryTransactionRoleType getInventoryTransactionRoleTypeByName(final ExecutionErrorAccumulator eea,
            final InventoryTransaction inventoryTransaction, final String inventoryTransactionRoleTypeName) {
        return inventoryTransactionRoleTypeLogic.getInventoryTransactionRoleTypeByName(eea,
                inventoryTransaction.getLastDetail().getInventoryTransactionType(), inventoryTransactionRoleTypeName);
    }

    public void ensureInventoryTransactionRole(final ExecutionErrorAccumulator eea,
            final InventoryTransaction inventoryTransaction, final String inventoryTransactionRoleTypeName,
            final Party party, final BasePK createdBy) {
        var inventoryTransactionRoleType = getInventoryTransactionRoleTypeByName(eea, inventoryTransaction, inventoryTransactionRoleTypeName);

        if(eea == null || !eea.hasExecutionErrors()) {
            if(!inventoryTransactionRoleControl.inventoryTransactionRoleExists(inventoryTransaction, inventoryTransactionRoleType, party)) {
                inventoryTransactionRoleControl.createInventoryTransactionRole(inventoryTransaction, inventoryTransactionRoleType, party, createdBy);
            }
        }
    }

    public void deleteInventoryTransactionRole(final ExecutionErrorAccumulator eea, final InventoryTransactionRole inventoryTransactionRole,
            final BasePK deletedBy) {
        inventoryTransactionRoleControl.deleteInventoryTransactionRole(inventoryTransactionRole, deletedBy);
    }

}
