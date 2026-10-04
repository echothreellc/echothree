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

import com.echothree.control.user.inventory.common.spec.InventoryTransferTransactionUniversalSpec;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.inventory.common.exception.DuplicateInventoryTransferTransactionNameException;
import com.echothree.model.control.inventory.common.exception.InvalidInventoryTransactionTypeException;
import com.echothree.model.control.inventory.common.exception.UnknownInventoryTransferTransactionNameException;
import com.echothree.model.control.inventory.server.control.InventoryTransferTransactionControl;
import com.echothree.model.control.sequence.server.logic.SequenceGeneratorLogic;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.data.inventory.server.entity.InventoryTransaction;
import com.echothree.model.data.inventory.server.entity.InventoryTransactionType;
import com.echothree.model.data.inventory.server.entity.InventoryTransferTransaction;
import com.echothree.model.data.inventory.server.value.InventoryTransferTransactionDetailValue;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class InventoryTransferTransactionLogic
    extends BaseLogic {

    @Inject
    InventoryTransferTransactionControl inventoryTransferTransactionControl;

    @Inject
    WorkflowControl workflowControl;

    @Inject
    EntityInstanceLogic entityInstanceLogic;

    @Inject
    InventoryTransactionTypeLogic inventoryTransactionTypeLogic;

    @Inject
    SequenceGeneratorLogic sequenceGeneratorLogic;

    protected InventoryTransferTransactionLogic() {
        super();
    }

    // --------------------------------------------------------------------------------
    //   Inventory Transfer Transactions
    // --------------------------------------------------------------------------------

    public InventoryTransferTransaction createInventoryTransferTransaction(final ExecutionErrorAccumulator eea,
            final String inventoryTransactionTypeName, String inventoryTransferTransactionName,
            final InventoryTransaction sourceInventoryTransaction, final InventoryTransaction destinationInventoryTransaction,
            final String reference, final String description, final BasePK createdBy) {
        var inventoryTransactionType = inventoryTransactionTypeLogic.getInventoryTransactionTypeByName(eea, inventoryTransactionTypeName);
        InventoryTransferTransaction inventoryTransferTransaction = null;

        if(eea == null || !eea.hasExecutionErrors()) {
            if(inventoryTransferTransactionName == null) {
                var inventoryTransferTransactionSequenceType = inventoryTransactionType.getLastDetail().getInventoryTransferTransactionSequenceType();

                if(inventoryTransferTransactionSequenceType != null) {
                    inventoryTransferTransactionName = sequenceGeneratorLogic.getNextSequenceValue(eea, inventoryTransferTransactionSequenceType);
                }
            }

            if(eea == null || !eea.hasExecutionErrors()) {
                inventoryTransferTransaction = createInventoryTransferTransaction(eea, inventoryTransactionType, inventoryTransferTransactionName,
                        sourceInventoryTransaction, destinationInventoryTransaction, reference, description, createdBy);

                if(eea == null || !eea.hasExecutionErrors()) {
                    var inventoryTransferTransactionWorkflowEntrance = inventoryTransactionType.getLastDetail().getInventoryTransferTransactionWorkflowEntrance();

                    if(inventoryTransferTransactionWorkflowEntrance != null) {
                        workflowControl.addEntityToWorkflow(inventoryTransferTransactionWorkflowEntrance,
                                getEntityInstanceByBaseEntity(inventoryTransferTransaction), null, null, createdBy);
                    }
                }
            }
        }

        return inventoryTransferTransaction;
    }

    public InventoryTransferTransaction createInventoryTransferTransaction(final ExecutionErrorAccumulator eea,
            final InventoryTransactionType inventoryTransactionType, final String inventoryTransferTransactionName,
            final InventoryTransaction sourceInventoryTransaction, final InventoryTransaction destinationInventoryTransaction,
            final String reference, final String description, final BasePK createdBy) {
        var inventoryTransferTransaction = inventoryTransferTransactionControl.getInventoryTransferTransactionByName(inventoryTransactionType,
                inventoryTransferTransactionName);

        if(inventoryTransferTransaction == null) {
            inventoryTransferTransaction = inventoryTransferTransactionControl.createInventoryTransferTransaction(inventoryTransactionType,
                    inventoryTransferTransactionName, sourceInventoryTransaction, destinationInventoryTransaction, reference, description, createdBy);
        } else {
            handleExecutionError(DuplicateInventoryTransferTransactionNameException.class, eea,
                    ExecutionErrors.DuplicateInventoryTransferTransactionName.name(),
                    inventoryTransactionType.getLastDetail().getInventoryTransactionTypeName(), inventoryTransferTransactionName);
        }

        return inventoryTransferTransaction;
    }

    public InventoryTransferTransaction getInventoryTransferTransactionByName(final ExecutionErrorAccumulator eea,
            final InventoryTransactionType inventoryTransactionType, final String inventoryTransferTransactionName, final EntityPermission entityPermission) {
        var inventoryTransferTransaction = inventoryTransferTransactionControl.getInventoryTransferTransactionByName(inventoryTransactionType,
                inventoryTransferTransactionName, entityPermission);

        if(inventoryTransferTransaction == null) {
            handleExecutionError(UnknownInventoryTransferTransactionNameException.class, eea, ExecutionErrors.UnknownInventoryTransferTransactionName.name(),
                    inventoryTransactionType.getLastDetail().getInventoryTransactionTypeName(), inventoryTransferTransactionName);
        }

        return inventoryTransferTransaction;
    }

    public InventoryTransferTransaction getInventoryTransferTransactionByName(final ExecutionErrorAccumulator eea,
            final InventoryTransactionType inventoryTransactionType, final String inventoryTransferTransactionName) {
        return getInventoryTransferTransactionByName(eea, inventoryTransactionType, inventoryTransferTransactionName, EntityPermission.READ_ONLY);
    }

    public InventoryTransferTransaction getInventoryTransferTransactionByNameForUpdate(final ExecutionErrorAccumulator eea,
            final InventoryTransactionType inventoryTransactionType, final String inventoryTransferTransactionName) {
        return getInventoryTransferTransactionByName(eea, inventoryTransactionType, inventoryTransferTransactionName, EntityPermission.READ_WRITE);
    }

    public InventoryTransferTransaction getInventoryTransferTransactionByName(final ExecutionErrorAccumulator eea, final String inventoryTransactionTypeName,
            final String inventoryTransferTransactionName, final EntityPermission entityPermission) {
        var inventoryTransactionType = inventoryTransactionTypeLogic.getInventoryTransactionTypeByName(eea, inventoryTransactionTypeName);
        InventoryTransferTransaction inventoryTransferTransaction = null;

        if(eea == null || !eea.hasExecutionErrors()) {
            inventoryTransferTransaction = getInventoryTransferTransactionByName(eea, inventoryTransactionType, inventoryTransferTransactionName, entityPermission);
        }

        return inventoryTransferTransaction;
    }

    public InventoryTransferTransaction getInventoryTransferTransactionByName(final ExecutionErrorAccumulator eea, final String inventoryTransactionTypeName,
            final String inventoryTransferTransactionName) {
        return getInventoryTransferTransactionByName(eea, inventoryTransactionTypeName, inventoryTransferTransactionName, EntityPermission.READ_ONLY);
    }

    public InventoryTransferTransaction getInventoryTransferTransactionByNameForUpdate(final ExecutionErrorAccumulator eea, final String inventoryTransactionTypeName,
            final String inventoryTransferTransactionName) {
        return getInventoryTransferTransactionByName(eea, inventoryTransactionTypeName, inventoryTransferTransactionName, EntityPermission.READ_WRITE);
    }

    public InventoryTransferTransaction getInventoryTransferTransactionByUniversalSpec(final ExecutionErrorAccumulator eea,
            final InventoryTransferTransactionUniversalSpec universalSpec, final String inventoryTransactionTypeName,
            final EntityPermission entityPermission) {
        var inventoryTransactionType = inventoryTransactionTypeLogic.getInventoryTransactionTypeByName(eea, inventoryTransactionTypeName);
        InventoryTransferTransaction inventoryTransferTransaction = null;

        if(eea == null || !eea.hasExecutionErrors()) {
            var inventoryTransferTransactionName = universalSpec.getInventoryTransferTransactionName();
            var possibleEntitySpecs = entityInstanceLogic.countPossibleEntitySpecs(universalSpec);

            if(inventoryTransferTransactionName != null && possibleEntitySpecs == 0) {
                inventoryTransferTransaction = getInventoryTransferTransactionByName(eea, inventoryTransactionType, inventoryTransferTransactionName, entityPermission);
            } else if(inventoryTransferTransactionName == null && possibleEntitySpecs == 1) {
                var entityInstance = entityInstanceLogic.getEntityInstance(eea, universalSpec, ComponentVendors.ECHO_THREE.name(), EntityTypes.InventoryTransferTransaction.name());

                if(eea == null || !eea.hasExecutionErrors()) {
                    inventoryTransferTransaction = inventoryTransferTransactionControl.getInventoryTransferTransactionByEntityInstance(entityInstance, entityPermission);
                }
            } else {
                handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
            }

            if((eea == null || !eea.hasExecutionErrors()) && inventoryTransferTransaction != null) {
                var foundInventoryTransactionType = inventoryTransferTransaction.getLastDetail().getInventoryTransactionType();

                if(!foundInventoryTransactionType.equals(inventoryTransactionType)) {
                    handleExecutionError(InvalidInventoryTransactionTypeException.class, eea, ExecutionErrors.InvalidInventoryTransactionType.name(),
                            foundInventoryTransactionType.getLastDetail().getInventoryTransactionTypeName());
                }
            }
        }

        return inventoryTransferTransaction;
    }

    public InventoryTransferTransaction getInventoryTransferTransactionByUniversalSpec(final ExecutionErrorAccumulator eea,
            final InventoryTransferTransactionUniversalSpec universalSpec, final String inventoryTransactionTypeName) {
        return getInventoryTransferTransactionByUniversalSpec(eea, universalSpec, inventoryTransactionTypeName, EntityPermission.READ_ONLY);
    }

    public InventoryTransferTransaction getInventoryTransferTransactionByUniversalSpecForUpdate(final ExecutionErrorAccumulator eea,
            final InventoryTransferTransactionUniversalSpec universalSpec, final String inventoryTransactionTypeName) {
        return getInventoryTransferTransactionByUniversalSpec(eea, universalSpec, inventoryTransactionTypeName, EntityPermission.READ_WRITE);
    }

    public void updateInventoryTransferTransactionFromValue(final InventoryTransferTransactionDetailValue inventoryTransferTransactionDetailValue,
            final BasePK updatedBy) {
        inventoryTransferTransactionControl.updateInventoryTransferTransactionFromValue(inventoryTransferTransactionDetailValue, updatedBy);
    }

    public void deleteInventoryTransferTransaction(final ExecutionErrorAccumulator eea, final InventoryTransferTransaction inventoryTransferTransaction,
            final BasePK deletedBy) {
        inventoryTransferTransactionControl.deleteInventoryTransferTransaction(inventoryTransferTransaction, deletedBy);
    }

}
