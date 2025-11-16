// --------------------------------------------------------------------------------
// Copyright 2002-2025 Echo Three, LLC
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
import com.echothree.model.control.batch.server.control.BatchControl;
import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.sales.common.transfer.SalesOrderBatchResultTransfer;
import com.echothree.model.control.sales.common.transfer.SalesOrderBatchTransfer;
import com.echothree.model.control.search.common.SearchOptions;
import com.echothree.model.data.batch.common.pk.BatchPK;
import com.echothree.model.data.batch.server.entity.Batch;
import com.echothree.model.data.payment.server.entity.PaymentMethod;
import com.echothree.model.data.sales.server.entity.SalesOrderBatch;
import com.echothree.model.data.sales.server.factory.SalesOrderBatchFactory;
import com.echothree.model.data.sales.server.value.SalesOrderBatchValue;
import com.echothree.model.data.search.server.entity.UserVisitSearch;
import com.echothree.model.data.search.server.factory.SearchResultFactory;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.exception.PersistenceDatabaseException;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class SalesOrderBatchControl
        extends BaseSalesControl {

    /** Creates a new instance of SalesOrderBatchControl */
    protected SalesOrderBatchControl() {
        super();
    }

    // --------------------------------------------------------------------------------
    //   Sales Order Batches
    // --------------------------------------------------------------------------------

    public SalesOrderBatch createSalesOrderBatch(Batch batch, PaymentMethod paymentMethod, BasePK createdBy) {
        var salesOrderBatch = SalesOrderBatchFactory.getInstance().create(batch, paymentMethod, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEvent(batch.getPrimaryKey(), EventTypes.MODIFY, salesOrderBatch.getPrimaryKey(), EventTypes.CREATE, createdBy);

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
        return saleTransferCaches.getSalesOrderBatchTransferCache().getTransfer(userVisit, batch);
    }

    public List<SalesOrderBatchTransfer> getSalesOrderBatchTransfers(UserVisit userVisit) {
        var batchControl = Session.getModelController(BatchControl.class);
        var batches = batchControl.getBatchesUsingNames(BatchConstants.BatchType_SALES_ORDER);
        List<SalesOrderBatchTransfer> salesOrderBatchTransfers = new ArrayList<>(batches.size());
        var salesOrderBatchTransferCache = saleTransferCaches.getSalesOrderBatchTransferCache();

        batches.forEach((batch) ->
                salesOrderBatchTransfers.add(salesOrderBatchTransferCache.getTransfer(userVisit, batch))
        );

        return salesOrderBatchTransfers;
    }

    public void updateSalesOrderBatchFromValue(SalesOrderBatchValue salesOrderBatchValue, BasePK updatedBy) {
        if(salesOrderBatchValue.hasBeenModified()) {
            var salesOrderBatch = SalesOrderBatchFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    salesOrderBatchValue.getPrimaryKey());

            salesOrderBatch.setThruTime(session.START_TIME_LONG);
            salesOrderBatch.store();

            var batchPK = salesOrderBatch.getBatchPK(); // Not updated
            var paymentMethodPK = salesOrderBatchValue.getPaymentMethodPK();

            salesOrderBatch = SalesOrderBatchFactory.getInstance().create(batchPK, paymentMethodPK, session.START_TIME_LONG, Session.MAX_TIME_LONG);

            sendEvent(batchPK, EventTypes.MODIFY, salesOrderBatch.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteSalesOrderBatch(SalesOrderBatch salesOrderBatch, BasePK deletedBy) {
        salesOrderBatch.setThruTime(session.START_TIME_LONG);

        sendEvent(salesOrderBatch.getBatchPK(), EventTypes.MODIFY, salesOrderBatch.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }

    public void deleteSalesOrderBatch(Batch batch, BasePK deletedBy) {
        deleteSalesOrderBatch(getSalesOrderBatchForUpdate(batch), deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Sales Order Batch Searches
    // --------------------------------------------------------------------------------

    public List<SalesOrderBatchResultTransfer> getSalesOrderBatchResultTransfers(UserVisit userVisit, UserVisitSearch userVisitSearch) {
        var search = userVisitSearch.getSearch();
        var salesOrderBatchResultTransfers = new ArrayList<SalesOrderBatchResultTransfer>();
        var includeSalesOrderBatch = false;

        var options = session.getOptions();
        if(options != null) {
            includeSalesOrderBatch = options.contains(SearchOptions.SalesOrderBatchResultIncludeSalesOrderBatch);
        }

        try {
            var batchControl = Session.getModelController(BatchControl.class);
            var salesOrderBatchControl = Session.getModelController(SalesOrderBatchControl.class);
            var ps = SearchResultFactory.getInstance().prepareStatement(
                    "SELECT eni_entityuniqueid " +
                            "FROM searchresults, entityinstances " +
                            "WHERE srchr_srch_searchid = ? AND srchr_eni_entityinstanceid = eni_entityinstanceid " +
                            "ORDER BY srchr_sortorder, srchr_eni_entityinstanceid " +
                            "_LIMIT_");

            ps.setLong(1, search.getPrimaryKey().getEntityId());

            try (var rs = ps.executeQuery()) {
                while(rs.next()) {
                    var batch = batchControl.getBatchByPK(new BatchPK(rs.getLong(1)));

                    salesOrderBatchResultTransfers.add(new SalesOrderBatchResultTransfer(batch.getLastDetail().getBatchName(),
                            includeSalesOrderBatch ? salesOrderBatchControl.getSalesOrderBatchTransfer(userVisit, batch) : null));
                }
            } catch (SQLException se) {
                throw new PersistenceDatabaseException(se);
            }
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return salesOrderBatchResultTransfers;
    }

}
