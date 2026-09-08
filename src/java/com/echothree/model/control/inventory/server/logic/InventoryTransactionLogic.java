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

import com.echothree.control.user.inventory.common.spec.InventoryTransactionUniversalSpec;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.inventory.common.exception.DuplicateInventoryTransactionNameException;
import com.echothree.model.control.inventory.common.exception.InvalidInventoryTransactionTypeException;
import com.echothree.model.control.inventory.common.exception.UnknownInventoryTransactionNameException;
import com.echothree.model.control.inventory.server.control.InventoryTransactionControl;
import com.echothree.model.control.sequence.server.logic.SequenceGeneratorLogic;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.data.inventory.server.entity.InventoryTransaction;
import com.echothree.model.data.inventory.server.entity.InventoryTransactionReason;
import com.echothree.model.data.inventory.server.entity.InventoryTransactionType;
import com.echothree.model.data.inventory.server.value.InventoryTransactionDetailValue;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;
import javax.inject.Inject;

@ApplicationScoped
public class InventoryTransactionLogic
    extends BaseLogic {

    @Inject
    InventoryTransactionControl inventoryTransactionControl;

    @Inject
    WorkflowControl workflowControl;

    @Inject
    EntityInstanceLogic entityInstanceLogic;

    @Inject
    InventoryTransactionTypeLogic inventoryTransactionTypeLogic;

    @Inject
    SequenceGeneratorLogic sequenceGeneratorLogic;

    protected InventoryTransactionLogic() {
        super();
    }

    // --------------------------------------------------------------------------------
    //   Inventory Transactions
    // --------------------------------------------------------------------------------

    public static InventoryTransactionLogic getInstance() {
        return CDI.current().select(InventoryTransactionLogic.class).get();
    }

    public InventoryTransaction createInventoryTransaction(final ExecutionErrorAccumulator eea, final String inventoryTransactionTypeName,
            String inventoryTransactionName, final Party companyParty, final InventoryTransactionReason inventoryTransactionReason,
            final String reference, final String description, final BasePK createdBy) {
        var inventoryTransactionType = inventoryTransactionTypeLogic.getInventoryTransactionTypeByName(eea, inventoryTransactionTypeName);
        InventoryTransaction inventoryTransaction = null;

        if(eea == null || !eea.hasExecutionErrors()) {
            if(inventoryTransactionName == null) {
                var inventoryTransactionSequenceType = inventoryTransactionType.getLastDetail().getInventoryTransactionSequenceType();

                if(inventoryTransactionSequenceType != null) {
                    inventoryTransactionName = sequenceGeneratorLogic.getNextSequenceValue(eea, inventoryTransactionSequenceType);
                }
            }

            if(eea == null || !eea.hasExecutionErrors()) {
                inventoryTransaction = createInventoryTransaction(eea, inventoryTransactionType, inventoryTransactionName, companyParty,
                        inventoryTransactionReason, reference, description, createdBy);

                if(eea == null || !eea.hasExecutionErrors()) {
                    var inventoryTransactionWorkflowEntrance = inventoryTransactionType.getLastDetail().getInventoryTransactionWorkflowEntrance();

                    if(inventoryTransactionWorkflowEntrance != null) {
                        workflowControl.addEntityToWorkflow(inventoryTransactionWorkflowEntrance,
                                getEntityInstanceByBaseEntity(inventoryTransaction), null, null, createdBy);
                    }
                }
            }
        }

        return inventoryTransaction;
    }

    public InventoryTransaction createInventoryTransaction(final ExecutionErrorAccumulator eea,
            final InventoryTransactionType inventoryTransactionType, final String inventoryTransactionName, final Party companyParty,
            final InventoryTransactionReason inventoryTransactionReason, final String reference, final String description, final BasePK createdBy) {
        var inventoryTransaction = inventoryTransactionControl.getInventoryTransactionByName(inventoryTransactionType, inventoryTransactionName);

        if(inventoryTransaction == null) {
            inventoryTransaction = inventoryTransactionControl.createInventoryTransaction(inventoryTransactionType, inventoryTransactionName,
                    companyParty, inventoryTransactionReason, reference, description, createdBy);
        } else {
            handleExecutionError(DuplicateInventoryTransactionNameException.class, eea, ExecutionErrors.DuplicateInventoryTransactionName.name(),
                    inventoryTransactionType.getLastDetail().getInventoryTransactionTypeName(), inventoryTransactionName);
        }

        return inventoryTransaction;
    }

    public InventoryTransaction getInventoryTransactionByName(final ExecutionErrorAccumulator eea,
            final InventoryTransactionType inventoryTransactionType, final String inventoryTransactionName, final EntityPermission entityPermission) {
        var inventoryTransaction = inventoryTransactionControl.getInventoryTransactionByName(inventoryTransactionType,
                inventoryTransactionName, entityPermission);

        if(inventoryTransaction == null) {
            handleExecutionError(UnknownInventoryTransactionNameException.class, eea, ExecutionErrors.UnknownInventoryTransactionName.name(),
                    inventoryTransactionType.getLastDetail().getInventoryTransactionTypeName(), inventoryTransactionName);
        }

        return inventoryTransaction;
    }

    public InventoryTransaction getInventoryTransactionByName(final ExecutionErrorAccumulator eea,
            final InventoryTransactionType inventoryTransactionType, final String inventoryTransactionName) {
        return getInventoryTransactionByName(eea, inventoryTransactionType, inventoryTransactionName, EntityPermission.READ_ONLY);
    }

    public InventoryTransaction getInventoryTransactionByNameForUpdate(final ExecutionErrorAccumulator eea,
            final InventoryTransactionType inventoryTransactionType, final String inventoryTransactionName) {
        return getInventoryTransactionByName(eea, inventoryTransactionType, inventoryTransactionName, EntityPermission.READ_WRITE);
    }

    public InventoryTransaction getInventoryTransactionByName(final ExecutionErrorAccumulator eea, final String inventoryTransactionTypeName,
            final String inventoryTransactionName, final EntityPermission entityPermission) {
        var inventoryTransactionType = inventoryTransactionTypeLogic.getInventoryTransactionTypeByName(eea, inventoryTransactionTypeName);
        InventoryTransaction inventoryTransaction = null;

        if(eea == null || !eea.hasExecutionErrors()) {
            inventoryTransaction = getInventoryTransactionByName(eea, inventoryTransactionType, inventoryTransactionName, entityPermission);
        }

        return inventoryTransaction;
    }

    public InventoryTransaction getInventoryTransactionByName(final ExecutionErrorAccumulator eea, final String inventoryTransactionTypeName,
            final String inventoryTransactionName) {
        return getInventoryTransactionByName(eea, inventoryTransactionTypeName, inventoryTransactionName, EntityPermission.READ_ONLY);
    }

    public InventoryTransaction getInventoryTransactionByNameForUpdate(final ExecutionErrorAccumulator eea, final String inventoryTransactionTypeName,
            final String inventoryTransactionName) {
        return getInventoryTransactionByName(eea, inventoryTransactionTypeName, inventoryTransactionName, EntityPermission.READ_WRITE);
    }

    public InventoryTransaction getInventoryTransactionByUniversalSpec(final ExecutionErrorAccumulator eea,
            final InventoryTransactionUniversalSpec universalSpec, final String inventoryTransactionTypeName,
            final EntityPermission entityPermission) {
        var inventoryTransactionType = inventoryTransactionTypeLogic.getInventoryTransactionTypeByName(eea, inventoryTransactionTypeName);
        InventoryTransaction inventoryTransaction = null;

        if(eea == null || !eea.hasExecutionErrors()) {
            var inventoryTransactionName = universalSpec.getInventoryTransactionName();
            var possibleEntitySpecs = entityInstanceLogic.countPossibleEntitySpecs(universalSpec);

            if(inventoryTransactionName != null && possibleEntitySpecs == 0) {
                inventoryTransaction = getInventoryTransactionByName(eea, inventoryTransactionType, inventoryTransactionName, entityPermission);
            } else if(inventoryTransactionName == null && possibleEntitySpecs == 1) {
                var entityInstance = entityInstanceLogic.getEntityInstance(eea, universalSpec, ComponentVendors.ECHO_THREE.name(), EntityTypes.InventoryTransaction.name());

                if(eea == null || !eea.hasExecutionErrors()) {
                    inventoryTransaction = inventoryTransactionControl.getInventoryTransactionByEntityInstance(entityInstance, entityPermission);
                }
            } else {
                handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
            }

            if((eea == null || !eea.hasExecutionErrors()) && inventoryTransaction != null) {
                var foundInventoryTransactionType = inventoryTransaction.getLastDetail().getInventoryTransactionType();

                if(!foundInventoryTransactionType.equals(inventoryTransactionType)) {
                    handleExecutionError(InvalidInventoryTransactionTypeException.class, eea, ExecutionErrors.InvalidInventoryTransactionType.name(),
                            foundInventoryTransactionType.getLastDetail().getInventoryTransactionTypeName());
                }
            }
        }

        return inventoryTransaction;
    }

    public InventoryTransaction getInventoryTransactionByUniversalSpec(final ExecutionErrorAccumulator eea,
            final InventoryTransactionUniversalSpec universalSpec, final String inventoryTransactionTypeName) {
        return getInventoryTransactionByUniversalSpec(eea, universalSpec, inventoryTransactionTypeName, EntityPermission.READ_ONLY);
    }

    public InventoryTransaction getInventoryTransactionByUniversalSpecForUpdate(final ExecutionErrorAccumulator eea,
            final InventoryTransactionUniversalSpec universalSpec, final String inventoryTransactionTypeName) {
        return getInventoryTransactionByUniversalSpec(eea, universalSpec, inventoryTransactionTypeName, EntityPermission.READ_WRITE);
    }

    public void updateInventoryTransactionFromValue(final InventoryTransactionDetailValue inventoryTransactionDetailValue,
            final BasePK updatedBy) {
        inventoryTransactionControl.updateInventoryTransactionFromValue(inventoryTransactionDetailValue, updatedBy);
    }

    public void deleteInventoryTransaction(final ExecutionErrorAccumulator eea, final InventoryTransaction inventoryTransaction,
            final BasePK deletedBy) {
        inventoryTransactionControl.deleteInventoryTransaction(inventoryTransaction, deletedBy);
    }

}
