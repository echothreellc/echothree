// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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

package com.echothree.model.control.sales.server.control;

import com.echothree.model.control.batch.common.BatchConstants;
import com.echothree.model.control.batch.server.BatchControl;
import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.sales.common.transfer.SalesOrderBatchTransfer;
import com.echothree.model.control.sales.server.transfer.SalesOrderBatchTransferCache;
import com.echothree.model.data.batch.common.pk.BatchPK;
import com.echothree.model.data.batch.server.entity.Batch;
import com.echothree.model.data.payment.common.pk.PaymentMethodPK;
import com.echothree.model.data.payment.server.entity.PaymentMethod;
import com.echothree.model.data.sales.server.entity.SalesOrderBatch;
import com.echothree.model.data.sales.server.factory.SalesOrderBatchFactory;
import com.echothree.model.data.sales.server.value.SalesOrderBatchValue;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SalesOrderBatchControl
        extends BaseSalesControl {

    /** Creates a new instance of SalesOrderBatchControl */
    public SalesOrderBatchControl() {
        super();
    }

    // --------------------------------------------------------------------------------
    //   Sales Order Batches
    // --------------------------------------------------------------------------------

    public SalesOrderBatch createSalesOrderBatch(Batch batch, PaymentMethod paymentMethod, BasePK createdBy) {
        SalesOrderBatch salesOrderBatch = SalesOrderBatchFactory.getInstance().create(batch, paymentMethod, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEventUsingNames(batch.getPrimaryKey(), EventTypes.MODIFY.name(), salesOrderBatch.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);

        return salesOrderBatch;
    }

    private static final Map<EntityPermission, String> getSalesOrderBatchQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                        "FROM salesorderbatches " +
                        "WHERE salordbtch_btch_batchid = ? AND salordbtch_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                        "FROM salesorderbatches " +
                        "WHERE salordbtch_btch_batchid = ? AND salordbtch_thrutime = ? " +
                        "FOR UPDATE");
        getSalesOrderBatchQueries = Collections.unmodifiableMap(queryMap);
    }

    private SalesOrderBatch getSalesOrderBatch(Batch batch, EntityPermission entityPermission) {
        return SalesOrderBatchFactory.getInstance().getEntityFromQuery(entityPermission, getSalesOrderBatchQueries,
                batch, Session.MAX_TIME_LONG);
    }

    public SalesOrderBatch getSalesOrderBatch(Batch batch) {
        return getSalesOrderBatch(batch, EntityPermission.READ_ONLY);
    }

    public SalesOrderBatch getSalesOrderBatchForUpdate(Batch batch) {
        return getSalesOrderBatch(batch, EntityPermission.READ_WRITE);
    }

    public SalesOrderBatchValue getSalesOrderBatchValue(SalesOrderBatch salesOrderBatch) {
        return salesOrderBatch == null? null: salesOrderBatch.getSalesOrderBatchValue().clone();
    }

    public SalesOrderBatchValue getSalesOrderBatchValueForUpdate(Batch batch) {
        return getSalesOrderBatchValue(getSalesOrderBatchForUpdate(batch));
    }

    public SalesOrderBatchTransfer getSalesOrderBatchTransfer(UserVisit userVisit, Batch batch) {
        return getSaleTransferCaches(userVisit).getSalesOrderBatchTransferCache().getTransfer(batch);
    }

    public List<SalesOrderBatchTransfer> getSalesOrderBatchTransfers(UserVisit userVisit) {
        var batchControl = (BatchControl)Session.getModelController(BatchControl.class);
        List<Batch> batches = batchControl.getBatchesUsingNames(BatchConstants.BatchType_SALES_ORDER);
        List<SalesOrderBatchTransfer> salesOrderBatchTransfers = new ArrayList<>(batches.size());
        SalesOrderBatchTransferCache salesOrderBatchTransferCache = getSaleTransferCaches(userVisit).getSalesOrderBatchTransferCache();

        batches.stream().forEach((batch) -> {
            salesOrderBatchTransfers.add(salesOrderBatchTransferCache.getTransfer(batch));
        });

        return salesOrderBatchTransfers;
    }

    public void updateSalesOrderBatchFromValue(SalesOrderBatchValue salesOrderBatchValue, BasePK updatedBy) {
        if(salesOrderBatchValue.hasBeenModified()) {
            SalesOrderBatch salesOrderBatch = SalesOrderBatchFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    salesOrderBatchValue.getPrimaryKey());

            salesOrderBatch.setThruTime(session.START_TIME_LONG);
            salesOrderBatch.store();

            BatchPK batchPK = salesOrderBatch.getBatchPK(); // Not updated
            PaymentMethodPK paymentMethodPK = salesOrderBatchValue.getPaymentMethodPK();

            salesOrderBatch = SalesOrderBatchFactory.getInstance().create(batchPK, paymentMethodPK, session.START_TIME_LONG, Session.MAX_TIME_LONG);

            sendEventUsingNames(batchPK, EventTypes.MODIFY.name(), salesOrderBatch.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }

    public void deleteSalesOrderBatch(SalesOrderBatch salesOrderBatch, BasePK deletedBy) {
        salesOrderBatch.setThruTime(session.START_TIME_LONG);

        sendEventUsingNames(salesOrderBatch.getBatchPK(), EventTypes.MODIFY.name(), salesOrderBatch.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
    }

    public void deleteSalesOrderBatch(Batch batch, BasePK deletedBy) {
        deleteSalesOrderBatch(getSalesOrderBatchForUpdate(batch), deletedBy);
    }

}
