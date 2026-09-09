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

import com.echothree.control.user.inventory.common.spec.InventoryTransactionLineUniversalSpec;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.inventory.common.exception.DuplicateInventoryTransactionLineSequenceException;
import com.echothree.model.control.inventory.common.exception.InvalidInventoryTransactionTypeException;
import com.echothree.model.control.inventory.common.exception.UnknownInventoryTransactionLineSequenceException;
import com.echothree.model.control.inventory.server.control.InventoryTransactionLineControl;
import com.echothree.model.data.inventory.server.entity.InventoryAdjustmentType;
import com.echothree.model.data.inventory.server.entity.InventoryTransaction;
import com.echothree.model.data.inventory.server.entity.InventoryTransactionLine;
import com.echothree.model.data.inventory.server.entity.InventoryTransactionReason;
import com.echothree.model.data.inventory.server.entity.Lot;
import com.echothree.model.data.inventory.server.value.InventoryTransactionLineDetailValue;
import com.echothree.model.data.item.server.entity.Item;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class InventoryTransactionLineLogic
        extends BaseLogic {

    @Inject
    InventoryTransactionLineControl inventoryTransactionLineControl;

    @Inject
    EntityInstanceLogic entityInstanceLogic;

    @Inject
    InventoryTransactionLogic inventoryTransactionLogic;

    @Inject
    InventoryTransactionTypeLogic inventoryTransactionTypeLogic;

    /** Creates a new instance of InventoryTransactionLineLogic */
    protected InventoryTransactionLineLogic() {
        super();
    }

    public InventoryTransactionLine createInventoryTransactionLine(final ExecutionErrorAccumulator eea,
            final String inventoryTransactionTypeName, final String inventoryTransactionName,
            final InventoryTransactionReason inventoryTransactionReason, final Item item, final Lot lot,
            final InventoryAdjustmentType inventoryAdjustmentType, final Long unitAmount, final String description, final BasePK createdBy) {
        return createInventoryTransactionLine(eea, inventoryTransactionTypeName, inventoryTransactionName, null,
                inventoryTransactionReason, item, lot, inventoryAdjustmentType, unitAmount, description, createdBy);
    }

    public InventoryTransactionLine createInventoryTransactionLine(final ExecutionErrorAccumulator eea,
            final String inventoryTransactionTypeName, final String inventoryTransactionName, final Integer inventoryTransactionLineSequence,
            final InventoryTransactionReason inventoryTransactionReason, final Item item, final Lot lot,
            final InventoryAdjustmentType inventoryAdjustmentType, final Long unitAmount, final String description, final BasePK createdBy) {
        var inventoryTransaction = inventoryTransactionLogic.getInventoryTransactionByName(eea, inventoryTransactionTypeName, inventoryTransactionName);
        InventoryTransactionLine inventoryTransactionLine = null;

        if(eea == null || !eea.hasExecutionErrors()) {
            inventoryTransactionLine = createInventoryTransactionLine(eea, inventoryTransaction, inventoryTransactionLineSequence,
                    inventoryTransactionReason, item, lot, inventoryAdjustmentType, unitAmount, description, createdBy);
        }

        return inventoryTransactionLine;
    }

    public InventoryTransactionLine createInventoryTransactionLine(final ExecutionErrorAccumulator eea,
            final InventoryTransaction inventoryTransaction, final Integer inventoryTransactionLineSequence,
            final InventoryTransactionReason inventoryTransactionReason, final Item item, final Lot lot,
            final InventoryAdjustmentType inventoryAdjustmentType, final Long unitAmount, final String description, final BasePK createdBy) {
        var inventoryTransactionLine = inventoryTransactionLineSequence == null ? null
                : inventoryTransactionLineControl.getInventoryTransactionLineBySequence(inventoryTransaction, inventoryTransactionLineSequence);

        if(inventoryTransactionLine == null) {
            inventoryTransactionLine = inventoryTransactionLineControl.createInventoryTransactionLine(inventoryTransaction,
                    inventoryTransactionLineSequence, inventoryTransactionReason, item, lot, inventoryAdjustmentType, unitAmount, description, createdBy);
        } else {
            handleExecutionError(DuplicateInventoryTransactionLineSequenceException.class, eea,
                    ExecutionErrors.DuplicateInventoryTransactionLineSequence.name(),
                    inventoryTransaction.getLastDetail().getInventoryTransactionType().getLastDetail().getInventoryTransactionTypeName(),
                    inventoryTransaction.getLastDetail().getInventoryTransactionName(), inventoryTransactionLineSequence);
        }

        return inventoryTransactionLine;
    }

    public InventoryTransactionLine getInventoryTransactionLineBySequence(final ExecutionErrorAccumulator eea,
            final InventoryTransaction inventoryTransaction, final Integer inventoryTransactionLineSequence, final EntityPermission entityPermission) {
        var inventoryTransactionLine = inventoryTransactionLineControl.getInventoryTransactionLineBySequence(inventoryTransaction,
                inventoryTransactionLineSequence, entityPermission);

        if(inventoryTransactionLine == null) {
            handleExecutionError(UnknownInventoryTransactionLineSequenceException.class, eea,
                    ExecutionErrors.UnknownInventoryTransactionLineSequence.name(),
                    inventoryTransaction.getLastDetail().getInventoryTransactionType().getLastDetail().getInventoryTransactionTypeName(),
                    inventoryTransaction.getLastDetail().getInventoryTransactionName(), inventoryTransactionLineSequence);
        }

        return inventoryTransactionLine;
    }

    public InventoryTransactionLine getInventoryTransactionLineBySequence(final ExecutionErrorAccumulator eea,
            final InventoryTransaction inventoryTransaction, final Integer inventoryTransactionLineSequence) {
        return getInventoryTransactionLineBySequence(eea, inventoryTransaction, inventoryTransactionLineSequence, EntityPermission.READ_ONLY);
    }

    public InventoryTransactionLine getInventoryTransactionLineBySequenceForUpdate(final ExecutionErrorAccumulator eea,
            final InventoryTransaction inventoryTransaction, final Integer inventoryTransactionLineSequence) {
        return getInventoryTransactionLineBySequence(eea, inventoryTransaction, inventoryTransactionLineSequence, EntityPermission.READ_WRITE);
    }

    public InventoryTransactionLine getInventoryTransactionLineBySequence(final ExecutionErrorAccumulator eea,
            final String inventoryTransactionTypeName, final String inventoryTransactionName, final Integer inventoryTransactionLineSequence,
            final EntityPermission entityPermission) {
        var inventoryTransaction = inventoryTransactionLogic.getInventoryTransactionByName(eea, inventoryTransactionTypeName, inventoryTransactionName);
        InventoryTransactionLine inventoryTransactionLine = null;

        if(eea == null || !eea.hasExecutionErrors()) {
            inventoryTransactionLine = getInventoryTransactionLineBySequence(eea, inventoryTransaction, inventoryTransactionLineSequence, entityPermission);
        }

        return inventoryTransactionLine;
    }

    public InventoryTransactionLine getInventoryTransactionLineBySequence(final ExecutionErrorAccumulator eea,
            final String inventoryTransactionTypeName, final String inventoryTransactionName, final Integer inventoryTransactionLineSequence) {
        return getInventoryTransactionLineBySequence(eea, inventoryTransactionTypeName, inventoryTransactionName,
                inventoryTransactionLineSequence, EntityPermission.READ_ONLY);
    }

    public InventoryTransactionLine getInventoryTransactionLineBySequenceForUpdate(final ExecutionErrorAccumulator eea,
            final String inventoryTransactionTypeName, final String inventoryTransactionName, final Integer inventoryTransactionLineSequence) {
        return getInventoryTransactionLineBySequence(eea, inventoryTransactionTypeName, inventoryTransactionName,
                inventoryTransactionLineSequence, EntityPermission.READ_WRITE);
    }

    public InventoryTransactionLine getInventoryTransactionLineByUniversalSpec(final ExecutionErrorAccumulator eea,
            final InventoryTransactionLineUniversalSpec universalSpec, final String inventoryTransactionTypeName,
            final EntityPermission entityPermission) {
        var inventoryTransactionType = inventoryTransactionTypeLogic.getInventoryTransactionTypeByName(eea, inventoryTransactionTypeName);
        InventoryTransactionLine inventoryTransactionLine = null;

        if(eea == null || !eea.hasExecutionErrors()) {
            var inventoryTransactionName = universalSpec.getInventoryTransactionName();
            var inventoryTransactionLineSequence = universalSpec.getInventoryTransactionLineSequence();
            var possibleEntitySpecs = entityInstanceLogic.countPossibleEntitySpecs(universalSpec);

            if(inventoryTransactionName != null && inventoryTransactionLineSequence != null && possibleEntitySpecs == 0) {
                var inventoryTransaction = inventoryTransactionLogic.getInventoryTransactionByName(eea, inventoryTransactionType, inventoryTransactionName);

                if(eea == null || !eea.hasExecutionErrors()) {
                    inventoryTransactionLine = getInventoryTransactionLineBySequence(eea, inventoryTransaction,
                            Integer.valueOf(inventoryTransactionLineSequence), entityPermission);
                }
            } else if(inventoryTransactionName == null && inventoryTransactionLineSequence == null && possibleEntitySpecs == 1) {
                var entityInstance = entityInstanceLogic.getEntityInstance(eea, universalSpec,
                        ComponentVendors.ECHO_THREE.name(), EntityTypes.InventoryTransactionLine.name());

                if(eea == null || !eea.hasExecutionErrors()) {
                    inventoryTransactionLine = inventoryTransactionLineControl.getInventoryTransactionLineByEntityInstance(entityInstance, entityPermission);
                }
            } else {
                handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
            }

            if((eea == null || !eea.hasExecutionErrors()) && inventoryTransactionLine != null) {
                var foundInventoryTransactionType = inventoryTransactionLine.getLastDetail().getInventoryTransaction().getLastDetail().getInventoryTransactionType();

                if(!foundInventoryTransactionType.equals(inventoryTransactionType)) {
                    handleExecutionError(InvalidInventoryTransactionTypeException.class, eea, ExecutionErrors.InvalidInventoryTransactionType.name(),
                            foundInventoryTransactionType.getLastDetail().getInventoryTransactionTypeName());
                }
            }
        }

        return inventoryTransactionLine;
    }

    public InventoryTransactionLine getInventoryTransactionLineByUniversalSpec(final ExecutionErrorAccumulator eea,
            final InventoryTransactionLineUniversalSpec universalSpec, final String inventoryTransactionTypeName) {
        return getInventoryTransactionLineByUniversalSpec(eea, universalSpec, inventoryTransactionTypeName, EntityPermission.READ_ONLY);
    }

    public InventoryTransactionLine getInventoryTransactionLineByUniversalSpecForUpdate(final ExecutionErrorAccumulator eea,
            final InventoryTransactionLineUniversalSpec universalSpec, final String inventoryTransactionTypeName) {
        return getInventoryTransactionLineByUniversalSpec(eea, universalSpec, inventoryTransactionTypeName, EntityPermission.READ_WRITE);
    }

    public void updateInventoryTransactionLineFromValue(final InventoryTransactionLineDetailValue inventoryTransactionLineDetailValue,
            final BasePK updatedBy) {
        inventoryTransactionLineControl.updateInventoryTransactionLineFromValue(inventoryTransactionLineDetailValue, updatedBy);
    }

    public void deleteInventoryTransactionLine(final ExecutionErrorAccumulator eea, final InventoryTransactionLine inventoryTransactionLine,
            final BasePK deletedBy) {
        inventoryTransactionLineControl.deleteInventoryTransactionLine(inventoryTransactionLine, deletedBy);
    }

}
