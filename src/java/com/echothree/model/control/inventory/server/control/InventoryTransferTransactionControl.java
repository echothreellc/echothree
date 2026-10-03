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

package com.echothree.model.control.inventory.server.control;

import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.inventory.common.pk.InventoryTransferTransactionPK;
import com.echothree.model.data.inventory.server.entity.InventoryTransaction;
import com.echothree.model.data.inventory.server.entity.InventoryTransactionType;
import com.echothree.model.data.inventory.server.entity.InventoryTransferTransaction;
import com.echothree.model.data.inventory.server.factory.InventoryTransferTransactionDetailFactory;
import com.echothree.model.data.inventory.server.factory.InventoryTransferTransactionFactory;
import com.echothree.model.data.inventory.server.value.InventoryTransferTransactionDetailValue;
import static com.echothree.model.jooq.server.keys.inventory.InventoryForeignKeys.INVENTORY_TRANSFER_TRANSACTIONS_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.tables.inventory.InventoryTransferTransactionDetails.InventoryTransferTransactionDetails;
import static com.echothree.model.jooq.server.tables.inventory.InventoryTransferTransactions.InventoryTransferTransactions;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.cdi.CommandScope;
import com.echothree.util.server.control.BaseModelControl;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.util.List;
import javax.inject.Inject;
import org.jooq.Condition;

@CommandScope
public class InventoryTransferTransactionControl
        extends BaseModelControl {

    /**
     * Creates a new instance of InventoryTransferTransactionControl
     */
    protected InventoryTransferTransactionControl() {
        super();
    }

    // --------------------------------------------------------------------------------
    //   Inventory Transfer Transactions
    // --------------------------------------------------------------------------------

    @Inject
    protected InventoryTransferTransactionDetailFactory inventoryTransferTransactionDetailFactory;

    @Inject
    protected InventoryTransferTransactionFactory inventoryTransferTransactionFactory;

    public InventoryTransferTransaction createInventoryTransferTransaction(InventoryTransactionType inventoryTransactionType, String inventoryTransferTransactionName,
            InventoryTransaction sourceInventoryTransaction, InventoryTransaction destinationInventoryTransaction, String reference, String description,
            BasePK createdBy) {
        var inventoryTransferTransaction = inventoryTransferTransactionFactory.create();
        var inventoryTransferTransactionDetail = inventoryTransferTransactionDetailFactory.create(inventoryTransferTransaction, inventoryTransactionType,
                inventoryTransferTransactionName, sourceInventoryTransaction, destinationInventoryTransaction, reference, description,
                session.getStartTime(), Session.MAX_TIME);

        // Convert to R/W
        inventoryTransferTransaction = inventoryTransferTransactionFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                inventoryTransferTransaction.getPrimaryKey());
        inventoryTransferTransaction.setActiveDetail(inventoryTransferTransactionDetail);
        inventoryTransferTransaction.setLastDetail(inventoryTransferTransactionDetail);
        inventoryTransferTransaction.store();

        sendEvent(inventoryTransferTransaction.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return inventoryTransferTransaction;
    }

    /**
     * Assume that the entityInstance passed to this function is a ECHO_THREE.InventoryTransferTransaction
     */
    public InventoryTransferTransaction getInventoryTransferTransactionByEntityInstance(final EntityInstance entityInstance,
            final EntityPermission entityPermission) {
        var pk = new InventoryTransferTransactionPK(entityInstance.getEntityUniqueId());

        return inventoryTransferTransactionFactory.getEntityFromPK(entityPermission, pk);
    }

    public InventoryTransferTransaction getInventoryTransferTransactionByEntityInstance(final EntityInstance entityInstance) {
        return getInventoryTransferTransactionByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public InventoryTransferTransaction getInventoryTransferTransactionByEntityInstanceForUpdate(final EntityInstance entityInstance) {
        return getInventoryTransferTransactionByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public InventoryTransferTransaction getInventoryTransferTransactionByPK(InventoryTransferTransactionPK pk) {
        return inventoryTransferTransactionFactory.getEntityFromPK(EntityPermission.READ_ONLY, pk);
    }

    private long countInventoryTransferTransactions(Condition condition) {
        return session.getDslContext()
                .selectCount()
                .from(InventoryTransferTransactions)
                .join(InventoryTransferTransactionDetails).onKey(INVENTORY_TRANSFER_TRANSACTIONS_ACTIVE_DETAIL_FK)
                .where(condition)
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countInventoryTransferTransactionsByInventoryTransactionType(InventoryTransactionType inventoryTransactionType) {
        return countInventoryTransferTransactions(InventoryTransferTransactionDetails.INVENTORY_TRANSACTION_TYPE.eq(inventoryTransactionType.getPrimaryKey()));
    }

    public long countInventoryTransferTransactionsBySourceInventoryTransaction(InventoryTransaction sourceInventoryTransaction) {
        return countInventoryTransferTransactions(InventoryTransferTransactionDetails.SOURCE_INVENTORY_TRANSACTION.eq(sourceInventoryTransaction.getPrimaryKey()));
    }

    public long countInventoryTransferTransactionsByDestinationInventoryTransaction(InventoryTransaction destinationInventoryTransaction) {
        return countInventoryTransferTransactions(InventoryTransferTransactionDetails.DESTINATION_INVENTORY_TRANSACTION.eq(destinationInventoryTransaction.getPrimaryKey()));
    }

    public InventoryTransferTransaction getInventoryTransferTransactionByName(final InventoryTransactionType inventoryTransactionType,
            final String inventoryTransferTransactionName, final EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(InventoryTransferTransactions.fields())
                .from(InventoryTransferTransactions)
                .join(InventoryTransferTransactionDetails).onKey(INVENTORY_TRANSFER_TRANSACTIONS_ACTIVE_DETAIL_FK)
                .where(InventoryTransferTransactionDetails.INVENTORY_TRANSACTION_TYPE.eq(inventoryTransactionType.getPrimaryKey()),
                        InventoryTransferTransactionDetails.INVENTORY_TRANSFER_TRANSACTION_NAME.eq(inventoryTransferTransactionName));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return inventoryTransferTransactionFactory.getEntityFromQuery(entityPermission, query);
    }

    public InventoryTransferTransaction getInventoryTransferTransactionByName(InventoryTransactionType inventoryTransactionType, String inventoryTransferTransactionName) {
        return getInventoryTransferTransactionByName(inventoryTransactionType, inventoryTransferTransactionName, EntityPermission.READ_ONLY);
    }

    public InventoryTransferTransaction getInventoryTransferTransactionByNameForUpdate(InventoryTransactionType inventoryTransactionType,
            String inventoryTransferTransactionName) {
        return getInventoryTransferTransactionByName(inventoryTransactionType, inventoryTransferTransactionName, EntityPermission.READ_WRITE);
    }

    public InventoryTransferTransactionDetailValue getInventoryTransferTransactionDetailValueForUpdate(InventoryTransferTransaction inventoryTransferTransaction) {
        return inventoryTransferTransaction == null ? null : inventoryTransferTransaction.getLastDetailForUpdate().getInventoryTransferTransactionDetailValue().clone();
    }

    public InventoryTransferTransactionDetailValue getInventoryTransferTransactionDetailValueByNameForUpdate(InventoryTransactionType inventoryTransactionType,
            String inventoryTransferTransactionName) {
        return getInventoryTransferTransactionDetailValueForUpdate(getInventoryTransferTransactionByNameForUpdate(inventoryTransactionType, inventoryTransferTransactionName));
    }

    public List<InventoryTransferTransaction> getInventoryTransferTransactions(final InventoryTransactionType inventoryTransactionType,
            final EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(InventoryTransferTransactions.fields())
                .from(InventoryTransferTransactions)
                .join(InventoryTransferTransactionDetails).onKey(INVENTORY_TRANSFER_TRANSACTIONS_ACTIVE_DETAIL_FK)
                .where(InventoryTransferTransactionDetails.INVENTORY_TRANSACTION_TYPE.eq(inventoryTransactionType.getPrimaryKey()));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(InventoryTransferTransactionDetails.INVENTORY_TRANSFER_TRANSACTION_NAME),
                    InventoryTransferTransactionFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return inventoryTransferTransactionFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<InventoryTransferTransaction> getInventoryTransferTransactions(InventoryTransactionType inventoryTransactionType) {
        return getInventoryTransferTransactions(inventoryTransactionType, EntityPermission.READ_ONLY);
    }

    public List<InventoryTransferTransaction> getInventoryTransferTransactionsForUpdate(InventoryTransactionType inventoryTransactionType) {
        return getInventoryTransferTransactions(inventoryTransactionType, EntityPermission.READ_WRITE);
    }

    private InventoryTransferTransaction getInventoryTransferTransaction(final Condition condition,
            final EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(InventoryTransferTransactions.fields())
                .from(InventoryTransferTransactions)
                .join(InventoryTransferTransactionDetails).onKey(INVENTORY_TRANSFER_TRANSACTIONS_ACTIVE_DETAIL_FK)
                .where(condition);

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return inventoryTransferTransactionFactory.getEntityFromQuery(entityPermission, query);
    }

    public InventoryTransferTransaction getInventoryTransferTransactionBySourceInventoryTransaction(
            final InventoryTransaction sourceInventoryTransaction, final EntityPermission entityPermission) {
        return getInventoryTransferTransaction(InventoryTransferTransactionDetails.SOURCE_INVENTORY_TRANSACTION.eq(sourceInventoryTransaction.getPrimaryKey()),
                entityPermission);
    }

    public InventoryTransferTransaction getInventoryTransferTransactionBySourceInventoryTransaction(InventoryTransaction sourceInventoryTransaction) {
        return getInventoryTransferTransactionBySourceInventoryTransaction(sourceInventoryTransaction, EntityPermission.READ_ONLY);
    }

    public InventoryTransferTransaction getInventoryTransferTransactionBySourceInventoryTransactionForUpdate(InventoryTransaction sourceInventoryTransaction) {
        return getInventoryTransferTransactionBySourceInventoryTransaction(sourceInventoryTransaction, EntityPermission.READ_WRITE);
    }

    public InventoryTransferTransaction getInventoryTransferTransactionByDestinationInventoryTransaction(
            final InventoryTransaction destinationInventoryTransaction, final EntityPermission entityPermission) {
        return getInventoryTransferTransaction(InventoryTransferTransactionDetails.DESTINATION_INVENTORY_TRANSACTION.eq(destinationInventoryTransaction.getPrimaryKey()),
                entityPermission);
    }

    public InventoryTransferTransaction getInventoryTransferTransactionByDestinationInventoryTransaction(InventoryTransaction destinationInventoryTransaction) {
        return getInventoryTransferTransactionByDestinationInventoryTransaction(destinationInventoryTransaction, EntityPermission.READ_ONLY);
    }

    public InventoryTransferTransaction getInventoryTransferTransactionByDestinationInventoryTransactionForUpdate(InventoryTransaction destinationInventoryTransaction) {
        return getInventoryTransferTransactionByDestinationInventoryTransaction(destinationInventoryTransaction, EntityPermission.READ_WRITE);
    }

    public void updateInventoryTransferTransactionFromValue(InventoryTransferTransactionDetailValue inventoryTransferTransactionDetailValue, BasePK updatedBy) {
        if(inventoryTransferTransactionDetailValue.hasBeenModified()) {
            var inventoryTransferTransaction = inventoryTransferTransactionFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                    inventoryTransferTransactionDetailValue.getInventoryTransferTransactionPK());
            var inventoryTransferTransactionDetail = inventoryTransferTransaction.getActiveDetailForUpdate();

            inventoryTransferTransactionDetail.setThruTime(session.getStartTime());
            inventoryTransferTransactionDetail.store();

            var inventoryTransferTransactionPK = inventoryTransferTransactionDetail.getInventoryTransferTransactionPK(); // Not updated
            var inventoryTransactionTypePK = inventoryTransferTransactionDetail.getInventoryTransactionTypePK(); // Not updated
            var inventoryTransferTransactionName = inventoryTransferTransactionDetailValue.getInventoryTransferTransactionName();
            var sourceInventoryTransactionPK = inventoryTransferTransactionDetailValue.getSourceInventoryTransactionPK();
            var destinationInventoryTransactionPK = inventoryTransferTransactionDetailValue.getDestinationInventoryTransactionPK();
            var reference = inventoryTransferTransactionDetailValue.getReference();
            var description = inventoryTransferTransactionDetailValue.getDescription();

            inventoryTransferTransactionDetail = inventoryTransferTransactionDetailFactory.create(inventoryTransferTransactionPK, inventoryTransactionTypePK,
                    inventoryTransferTransactionName, sourceInventoryTransactionPK, destinationInventoryTransactionPK, reference, description,
                    session.getStartTime(), Session.MAX_TIME);

            inventoryTransferTransaction.setActiveDetail(inventoryTransferTransactionDetail);
            inventoryTransferTransaction.setLastDetail(inventoryTransferTransactionDetail);

            sendEvent(inventoryTransferTransactionPK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }

    public void deleteInventoryTransferTransaction(InventoryTransferTransaction inventoryTransferTransaction, BasePK deletedBy) {
        var inventoryTransferTransactionDetail = inventoryTransferTransaction.getLastDetailForUpdate();

        inventoryTransferTransactionDetail.setThruTime(session.getStartTime());
        inventoryTransferTransaction.setActiveDetail(null);
        inventoryTransferTransaction.store();

        sendEvent(inventoryTransferTransaction.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }

    public void deleteInventoryTransferTransactionsByInventoryTransactionType(InventoryTransactionType inventoryTransactionType, BasePK deletedBy) {
        var inventoryTransferTransactions = getInventoryTransferTransactionsForUpdate(inventoryTransactionType);

        inventoryTransferTransactions.forEach(inventoryTransferTransaction -> deleteInventoryTransferTransaction(inventoryTransferTransaction, deletedBy));
    }

    public void deleteInventoryTransferTransactionBySourceInventoryTransaction(InventoryTransaction sourceInventoryTransaction, BasePK deletedBy) {
        var inventoryTransferTransaction = getInventoryTransferTransactionBySourceInventoryTransactionForUpdate(sourceInventoryTransaction);

        if(inventoryTransferTransaction != null) {
            deleteInventoryTransferTransaction(inventoryTransferTransaction, deletedBy);
        }
    }

    public void deleteInventoryTransferTransactionByDestinationInventoryTransaction(InventoryTransaction destinationInventoryTransaction, BasePK deletedBy) {
        var inventoryTransferTransaction = getInventoryTransferTransactionByDestinationInventoryTransactionForUpdate(destinationInventoryTransaction);

        if(inventoryTransferTransaction != null) {
            deleteInventoryTransferTransaction(inventoryTransferTransaction, deletedBy);
        }
    }

}
