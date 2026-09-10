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
import com.echothree.model.data.inventory.server.entity.InventoryTransactionLine;
import com.echothree.model.data.inventory.server.entity.InventoryTransactionLineRole;
import com.echothree.model.data.inventory.server.entity.InventoryTransactionRoleType;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class InventoryTransactionLineRoleLogic
        extends BaseLogic {

    @Inject
    InventoryTransactionRoleControl inventoryTransactionRoleControl;

    @Inject
    InventoryTransactionRoleTypeLogic inventoryTransactionRoleTypeLogic;

    protected InventoryTransactionLineRoleLogic() {
        super();
    }

    public InventoryTransactionLineRole createInventoryTransactionLineRole(final ExecutionErrorAccumulator eea,
            final InventoryTransactionLine inventoryTransactionLine, final String inventoryTransactionRoleTypeName,
            final Party party, final BasePK createdBy) {
        var inventoryTransactionRoleType = getInventoryTransactionRoleTypeByName(eea, inventoryTransactionLine, inventoryTransactionRoleTypeName);
        InventoryTransactionLineRole inventoryTransactionLineRole = null;

        if(eea == null || !eea.hasExecutionErrors()) {
            inventoryTransactionLineRole = createInventoryTransactionLineRole(inventoryTransactionLine, inventoryTransactionRoleType, party, createdBy);
        }

        return inventoryTransactionLineRole;
    }

    public InventoryTransactionLineRole createInventoryTransactionLineRole(final InventoryTransactionLine inventoryTransactionLine,
            final InventoryTransactionRoleType inventoryTransactionRoleType, final Party party, final BasePK createdBy) {
        return inventoryTransactionRoleControl.createInventoryTransactionLineRole(inventoryTransactionLine, inventoryTransactionRoleType, party, createdBy);
    }

    private InventoryTransactionRoleType getInventoryTransactionRoleTypeByName(final ExecutionErrorAccumulator eea,
            final InventoryTransactionLine inventoryTransactionLine, final String inventoryTransactionRoleTypeName) {
        return inventoryTransactionRoleTypeLogic.getInventoryTransactionRoleTypeByName(eea,
                inventoryTransactionLine.getLastDetail().getInventoryTransaction().getLastDetail().getInventoryTransactionType(), inventoryTransactionRoleTypeName);
    }

    public void ensureInventoryTransactionLineRole(final ExecutionErrorAccumulator eea,
            final InventoryTransactionLine inventoryTransactionLine, final String inventoryTransactionRoleTypeName,
            final Party party, final BasePK createdBy) {
        var inventoryTransactionRoleType = getInventoryTransactionRoleTypeByName(eea, inventoryTransactionLine, inventoryTransactionRoleTypeName);

        if(eea == null || !eea.hasExecutionErrors()) {
            if(!inventoryTransactionRoleControl.inventoryTransactionLineRoleExists(inventoryTransactionLine, inventoryTransactionRoleType, party)) {
                inventoryTransactionRoleControl.createInventoryTransactionLineRole(inventoryTransactionLine, inventoryTransactionRoleType, party, createdBy);
            }
        }
    }

    public void deleteInventoryTransactionLineRole(final ExecutionErrorAccumulator eea, final InventoryTransactionLineRole inventoryTransactionLineRole,
            final BasePK deletedBy) {
        inventoryTransactionRoleControl.deleteInventoryTransactionLineRole(inventoryTransactionLineRole, deletedBy);
    }

}
