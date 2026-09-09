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
import com.echothree.model.data.inventory.common.pk.InventoryTransactionPK;
import com.echothree.model.data.inventory.server.entity.InventoryTransaction;
import com.echothree.model.data.inventory.server.entity.InventoryTransactionReason;
import com.echothree.model.data.inventory.server.entity.InventoryTransactionStatus;
import com.echothree.model.data.inventory.server.entity.InventoryTransactionType;
import com.echothree.model.data.inventory.server.factory.InventoryTransactionDetailFactory;
import com.echothree.model.data.inventory.server.factory.InventoryTransactionFactory;
import com.echothree.model.data.inventory.server.factory.InventoryTransactionStatusFactory;
import com.echothree.model.data.inventory.server.value.InventoryTransactionDetailValue;
import com.echothree.model.data.party.server.entity.Party;
import static com.echothree.model.jooq.server.keys.inventory.InventoryForeignKeys.INVENTORY_TRANSACTIONS_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.tables.inventory.InventoryTransactionDetails.InventoryTransactionDetails;
import static com.echothree.model.jooq.server.tables.inventory.InventoryTransactions.InventoryTransactions;
import static com.echothree.model.jooq.server.tables.inventory.InventoryTransactionStatuses.InventoryTransactionStatuses;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.cdi.CommandScope;
import com.echothree.util.server.control.BaseModelControl;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.util.List;
import javax.inject.Inject;
import org.jooq.Condition;

@CommandScope
public class InventoryTransactionControl
        extends BaseModelControl {

    @Inject
    InventoryTransactionLineControl inventoryTransactionLineControl;

    @Inject
    InventoryTransactionRoleControl inventoryTransactionRoleControl;

    @Inject
    InventoryTransactionTimeControl inventoryTransactionTimeControl;

    /**
     * Creates a new instance of InventoryTransactionControl
     */
    protected InventoryTransactionControl() {
        super();
    }

    // --------------------------------------------------------------------------------
    //   Inventory Transactions
    // --------------------------------------------------------------------------------

    @Inject
    protected InventoryTransactionDetailFactory inventoryTransactionDetailFactory;

    @Inject
    protected InventoryTransactionFactory inventoryTransactionFactory;

    public InventoryTransaction createInventoryTransaction(InventoryTransactionType inventoryTransactionType, String inventoryTransactionName,
            Party companyParty, InventoryTransactionReason inventoryTransactionReason, String reference, String description, BasePK createdBy) {
        var inventoryTransaction = inventoryTransactionFactory.create();
        var inventoryTransactionDetail = inventoryTransactionDetailFactory.create(inventoryTransaction, inventoryTransactionType,
                inventoryTransactionName, companyParty, inventoryTransactionReason, reference, description, session.getStartTime(), Session.MAX_TIME);

        // Convert to R/W
        inventoryTransaction = inventoryTransactionFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                inventoryTransaction.getPrimaryKey());
        inventoryTransaction.setActiveDetail(inventoryTransactionDetail);
        inventoryTransaction.setLastDetail(inventoryTransactionDetail);
        inventoryTransaction.store();

        sendEvent(inventoryTransaction.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        createInventoryTransactionStatus(inventoryTransaction);

        return inventoryTransaction;
    }

    /**
     * Assume that the entityInstance passed to this function is a ECHO_THREE.InventoryTransaction
     */
    public InventoryTransaction getInventoryTransactionByEntityInstance(final EntityInstance entityInstance,
            final EntityPermission entityPermission) {
        var pk = new InventoryTransactionPK(entityInstance.getEntityUniqueId());

        return inventoryTransactionFactory.getEntityFromPK(entityPermission, pk);
    }

    public InventoryTransaction getInventoryTransactionByEntityInstance(final EntityInstance entityInstance) {
        return getInventoryTransactionByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public InventoryTransaction getInventoryTransactionByEntityInstanceForUpdate(final EntityInstance entityInstance) {
        return getInventoryTransactionByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public InventoryTransaction getInventoryTransactionByPK(InventoryTransactionPK pk) {
        return inventoryTransactionFactory.getEntityFromPK(EntityPermission.READ_ONLY, pk);
    }

    private long countInventoryTransactions(Condition condition) {
        return session.getDslContext()
                .selectCount()
                .from(InventoryTransactions)
                .join(InventoryTransactionDetails).onKey(INVENTORY_TRANSACTIONS_ACTIVE_DETAIL_FK)
                .where(condition)
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countInventoryTransactionsByInventoryTransactionType(InventoryTransactionType inventoryTransactionType) {
        return countInventoryTransactions(InventoryTransactionDetails.INVENTORY_TRANSACTION_TYPE.eq(inventoryTransactionType.getPrimaryKey()));
    }

    public long countInventoryTransactionsByCompanyParty(Party companyParty) {
        return countInventoryTransactions(InventoryTransactionDetails.COMPANY_PARTY.eq(companyParty.getPrimaryKey()));
    }

    public long countInventoryTransactionsByInventoryTransactionReason(InventoryTransactionReason inventoryTransactionReason) {
        return countInventoryTransactions(InventoryTransactionDetails.INVENTORY_TRANSACTION_REASON.eq(inventoryTransactionReason.getPrimaryKey()));
    }

    public InventoryTransaction getInventoryTransactionByName(final InventoryTransactionType inventoryTransactionType,
            final String inventoryTransactionName, final EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(InventoryTransactions.fields())
                .from(InventoryTransactions)
                .join(InventoryTransactionDetails).onKey(INVENTORY_TRANSACTIONS_ACTIVE_DETAIL_FK)
                .where(InventoryTransactionDetails.INVENTORY_TRANSACTION_TYPE.eq(inventoryTransactionType.getPrimaryKey()),
                        InventoryTransactionDetails.INVENTORY_TRANSACTION_NAME.eq(inventoryTransactionName));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return inventoryTransactionFactory.getEntityFromQuery(entityPermission, query);
    }

    public InventoryTransaction getInventoryTransactionByName(InventoryTransactionType inventoryTransactionType, String inventoryTransactionName) {
        return getInventoryTransactionByName(inventoryTransactionType, inventoryTransactionName, EntityPermission.READ_ONLY);
    }

    public InventoryTransaction getInventoryTransactionByNameForUpdate(InventoryTransactionType inventoryTransactionType,
            String inventoryTransactionName) {
        return getInventoryTransactionByName(inventoryTransactionType, inventoryTransactionName, EntityPermission.READ_WRITE);
    }

    public InventoryTransactionDetailValue getInventoryTransactionDetailValueForUpdate(InventoryTransaction inventoryTransaction) {
        return inventoryTransaction == null ? null : inventoryTransaction.getLastDetailForUpdate().getInventoryTransactionDetailValue().clone();
    }

    public InventoryTransactionDetailValue getInventoryTransactionDetailValueByNameForUpdate(InventoryTransactionType inventoryTransactionType,
            String inventoryTransactionName) {
        return getInventoryTransactionDetailValueForUpdate(getInventoryTransactionByNameForUpdate(inventoryTransactionType, inventoryTransactionName));
    }

    private List<InventoryTransaction> getInventoryTransactions(final InventoryTransactionType inventoryTransactionType,
            final EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(InventoryTransactions.fields())
                .from(InventoryTransactions)
                .join(InventoryTransactionDetails).onKey(INVENTORY_TRANSACTIONS_ACTIVE_DETAIL_FK)
                .where(InventoryTransactionDetails.INVENTORY_TRANSACTION_TYPE.eq(inventoryTransactionType.getPrimaryKey()));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(InventoryTransactionDetails.INVENTORY_TRANSACTION_NAME),
                    InventoryTransactionFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return inventoryTransactionFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<InventoryTransaction> getInventoryTransactions(InventoryTransactionType inventoryTransactionType) {
        return getInventoryTransactions(inventoryTransactionType, EntityPermission.READ_ONLY);
    }

    public List<InventoryTransaction> getInventoryTransactionsForUpdate(InventoryTransactionType inventoryTransactionType) {
        return getInventoryTransactions(inventoryTransactionType, EntityPermission.READ_WRITE);
    }

    public void updateInventoryTransactionFromValue(InventoryTransactionDetailValue inventoryTransactionDetailValue, BasePK updatedBy) {
        if(inventoryTransactionDetailValue.hasBeenModified()) {
            var inventoryTransaction = inventoryTransactionFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                    inventoryTransactionDetailValue.getInventoryTransactionPK());
            var inventoryTransactionDetail = inventoryTransaction.getActiveDetailForUpdate();

            inventoryTransactionDetail.setThruTime(session.getStartTime());
            inventoryTransactionDetail.store();

            var inventoryTransactionPK = inventoryTransactionDetail.getInventoryTransactionPK(); // Not updated
            var inventoryTransactionTypePK = inventoryTransactionDetail.getInventoryTransactionTypePK(); // Not updated
            var inventoryTransactionName = inventoryTransactionDetailValue.getInventoryTransactionName();
            var companyPartyPK = inventoryTransactionDetailValue.getCompanyPartyPK();
            var inventoryTransactionReasonPK = inventoryTransactionDetailValue.getInventoryTransactionReasonPK();
            var reference = inventoryTransactionDetailValue.getReference();
            var description = inventoryTransactionDetailValue.getDescription();

            inventoryTransactionDetail = inventoryTransactionDetailFactory.create(inventoryTransactionPK, inventoryTransactionTypePK,
                    inventoryTransactionName, companyPartyPK, inventoryTransactionReasonPK, reference, description,
                    session.getStartTime(), Session.MAX_TIME);

            inventoryTransaction.setActiveDetail(inventoryTransactionDetail);
            inventoryTransaction.setLastDetail(inventoryTransactionDetail);

            sendEvent(inventoryTransactionPK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }

    public void deleteInventoryTransaction(InventoryTransaction inventoryTransaction, BasePK deletedBy) {
        var inventoryTransactionDetail = inventoryTransaction.getLastDetailForUpdate();

        removeInventoryTransactionStatusByInventoryTransaction(inventoryTransaction);
        inventoryTransactionTimeControl.deleteInventoryTransactionTimesByInventoryTransaction(inventoryTransaction, deletedBy);
        inventoryTransactionRoleControl.deleteInventoryTransactionRolesByInventoryTransaction(inventoryTransaction, deletedBy);
        inventoryTransactionLineControl.deleteInventoryTransactionLinesByInventoryTransaction(inventoryTransaction, deletedBy);

        inventoryTransactionDetail.setThruTime(session.getStartTime());
        inventoryTransaction.setActiveDetail(null);
        inventoryTransaction.store();

        sendEvent(inventoryTransaction.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }

    public void deleteInventoryTransactionsByInventoryTransactionType(InventoryTransactionType inventoryTransactionType, BasePK deletedBy) {
        var inventoryTransactions = getInventoryTransactionsForUpdate(inventoryTransactionType);

        inventoryTransactions.forEach(inventoryTransaction -> deleteInventoryTransaction(inventoryTransaction, deletedBy));
    }

    // --------------------------------------------------------------------------------
    //   Inventory Transaction Statuses
    // --------------------------------------------------------------------------------

    @Inject
    protected InventoryTransactionStatusFactory inventoryTransactionStatusFactory;

    public InventoryTransactionStatus createInventoryTransactionStatus(InventoryTransaction inventoryTransaction) {
        return inventoryTransactionStatusFactory.create(inventoryTransaction, 0);
    }

    private InventoryTransactionStatus getInventoryTransactionStatus(InventoryTransaction inventoryTransaction,
            EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(InventoryTransactionStatuses.fields())
                .from(InventoryTransactionStatuses)
                .where(InventoryTransactionStatuses.INVENTORY_TRANSACTION.eq(inventoryTransaction.getPrimaryKey()));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return inventoryTransactionStatusFactory.getEntityFromQuery(entityPermission, query);
    }

    public InventoryTransactionStatus getInventoryTransactionStatus(InventoryTransaction inventoryTransaction) {
        return getInventoryTransactionStatus(inventoryTransaction, EntityPermission.READ_ONLY);
    }

    public InventoryTransactionStatus getInventoryTransactionStatusForUpdate(InventoryTransaction inventoryTransaction) {
        return getInventoryTransactionStatus(inventoryTransaction, EntityPermission.READ_WRITE);
    }

    public void removeInventoryTransactionStatusByInventoryTransaction(InventoryTransaction inventoryTransaction) {
        var inventoryTransactionStatus = getInventoryTransactionStatusForUpdate(inventoryTransaction);

        if(inventoryTransactionStatus != null) {
            inventoryTransactionStatus.remove();
        }
    }

}
