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

package com.echothree.model.control.sales.server.control;

import com.echothree.model.control.batch.common.BatchTypes;
import com.echothree.model.control.batch.server.control.BatchControl;
import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.sales.common.transfer.SalesOrderBatchResultTransfer;
import com.echothree.model.control.sales.common.transfer.SalesOrderBatchTransfer;
import com.echothree.model.control.sales.server.transfer.SalesOrderBatchTransferCache;
import com.echothree.model.control.search.common.SearchOptions;
import com.echothree.model.control.search.server.control.SearchControl;
import com.echothree.model.data.batch.common.pk.BatchPK;
import com.echothree.model.data.batch.server.entity.Batch;
import com.echothree.model.data.payment.server.entity.PaymentMethod;
import com.echothree.model.data.sales.server.entity.SalesOrderBatch;
import com.echothree.model.data.sales.server.factory.SalesOrderBatchFactory;
import com.echothree.model.data.sales.server.value.SalesOrderBatchValue;
import com.echothree.model.data.search.common.CachedExecutedSearchResultConstants;
import com.echothree.model.data.search.common.SearchResultConstants;
import com.echothree.model.data.search.server.entity.UserVisitSearch;
import com.echothree.model.data.search.server.factory.CachedExecutedSearchResultFactory;
import com.echothree.model.data.search.server.factory.SearchResultFactory;
import com.echothree.model.data.user.server.entity.UserVisit;
import static com.echothree.model.jooq.server.keys.search.SearchForeignKeys.CACHED_EXECUTED_SEARCH_RESULTS_ENTITY_INSTANCE_FK;
import static com.echothree.model.jooq.server.keys.search.SearchForeignKeys.SEARCH_RESULTS_ENTITY_INSTANCE_FK;
import static com.echothree.model.jooq.server.tables.core.EntityInstances.EntityInstances;
import static com.echothree.model.jooq.server.tables.sales.SalesOrderBatches.SalesOrderBatches;
import static com.echothree.model.jooq.server.tables.search.CachedExecutedSearchResults.CachedExecutedSearchResults;
import static com.echothree.model.jooq.server.tables.search.SearchResults.SearchResults;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.cdi.CommandScope;
import com.echothree.util.server.control.BaseModelControl;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.inject.Inject;
import org.jooq.Select;

@CommandScope
public class SalesOrderBatchControl
        extends BaseModelControl {

    /** Creates a new instance of SalesOrderBatchControl */
    protected SalesOrderBatchControl() {
        super();
    }

    @Inject
    BatchControl batchControl;

    @Inject
    private SearchControl searchControl;

    // --------------------------------------------------------------------------------
    //   Sales Order Batches
    // --------------------------------------------------------------------------------

    @Inject
    protected SalesOrderBatchFactory salesOrderBatchFactory;

    @Inject
    private SalesOrderBatchTransferCache salesOrderBatchTransferCache;

    public SalesOrderBatch createSalesOrderBatch(Batch batch, PaymentMethod paymentMethod, BasePK createdBy) {
        var salesOrderBatch = salesOrderBatchFactory.create(batch, paymentMethod, session.getStartTime(), Session.MAX_TIME);

        sendEvent(batch.getPrimaryKey(), EventTypes.MODIFY, salesOrderBatch.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return salesOrderBatch;
    }

    public long countSalesOrderBatches() {
        return batchControl.countBatchesByBatchType(batchControl.getBatchTypeByName(BatchTypes.SALES_ORDER.name()));
    }

    private SalesOrderBatch getSalesOrderBatch(Batch batch, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(SalesOrderBatches.fields())
                .from(SalesOrderBatches)
                .where(SalesOrderBatches.BATCH.eq(batch.getPrimaryKey()),
                        SalesOrderBatches.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return salesOrderBatchFactory.getEntityFromQuery(entityPermission, query);
    }

    public SalesOrderBatch getSalesOrderBatch(Batch batch) {
        return getSalesOrderBatch(batch, EntityPermission.READ_ONLY);
    }

    public SalesOrderBatch getSalesOrderBatchForUpdate(Batch batch) {
        return getSalesOrderBatch(batch, EntityPermission.READ_WRITE);
    }

    public List<Batch> getSalesOrderBatches() {
        return batchControl.getBatchesUsingNames(BatchTypes.SALES_ORDER.name());
    }

    public SalesOrderBatchValue getSalesOrderBatchValue(SalesOrderBatch salesOrderBatch) {
        return salesOrderBatch == null? null: salesOrderBatch.getSalesOrderBatchValue().clone();
    }

    public SalesOrderBatchValue getSalesOrderBatchValueForUpdate(Batch batch) {
        return getSalesOrderBatchValue(getSalesOrderBatchForUpdate(batch));
    }

    public SalesOrderBatchTransfer getSalesOrderBatchTransfer(UserVisit userVisit, Batch batch) {
        return salesOrderBatchTransferCache.getTransfer(userVisit, batch);
    }

    public List<SalesOrderBatchTransfer> getSalesOrderBatchTransfers(UserVisit userVisit, Collection<Batch> batches) {
        List<SalesOrderBatchTransfer> salesOrderBatchTransfers = new ArrayList<>(batches.size());

        batches.forEach((batch) ->
                salesOrderBatchTransfers.add(salesOrderBatchTransferCache.getTransfer(userVisit, batch))
        );

        return salesOrderBatchTransfers;
    }

    public List<SalesOrderBatchTransfer> getSalesOrderBatchTransfers(UserVisit userVisit) {
        return getSalesOrderBatchTransfers(userVisit, getSalesOrderBatches());
    }

    public void updateSalesOrderBatchFromValue(SalesOrderBatchValue salesOrderBatchValue, BasePK updatedBy) {
        if(salesOrderBatchValue.hasBeenModified()) {
            var salesOrderBatch = salesOrderBatchFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                    salesOrderBatchValue.getPrimaryKey());

            salesOrderBatch.setThruTime(session.getStartTime());
            salesOrderBatch.store();

            var batchPK = salesOrderBatch.getBatchPK(); // Not updated
            var paymentMethodPK = salesOrderBatchValue.getPaymentMethodPK();

            salesOrderBatch = salesOrderBatchFactory.create(batchPK, paymentMethodPK, session.getStartTime(), Session.MAX_TIME);

            sendEvent(batchPK, EventTypes.MODIFY, salesOrderBatch.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteSalesOrderBatch(SalesOrderBatch salesOrderBatch, BasePK deletedBy) {
        salesOrderBatch.setThruTime(session.getStartTime());

        sendEvent(salesOrderBatch.getBatchPK(), EventTypes.MODIFY, salesOrderBatch.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }

    public void deleteSalesOrderBatch(Batch batch, BasePK deletedBy) {
        deleteSalesOrderBatch(getSalesOrderBatchForUpdate(batch), deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Sales Order Batch Searches
    // --------------------------------------------------------------------------------

    @Inject
    protected SearchResultFactory searchResultFactory;

    private List<Long> getSalesOrderBatchIdsFromUserVisitSearch(UserVisitSearch userVisitSearch) {
        var search = userVisitSearch.getSearch();
        var cachedSearch = search.getCachedSearch();
        Select<?> query;

        if(cachedSearch == null) {
            query = session.applyLimit(session.getDslContext()
                    .select(EntityInstances.ENTITY_UNIQUE)
                    .from(SearchResults)
                    .join(EntityInstances).onKey(SEARCH_RESULTS_ENTITY_INSTANCE_FK)
                    .where(SearchResults.SEARCH.eq(search.getPrimaryKey()))
                    .orderBy(SearchResults.SORT_ORDER, SearchResults.ENTITY_INSTANCE), SearchResultFactory.class);
        } else {
            var cachedExecutedSearch = searchControl.getCachedExecutedSearch(cachedSearch);

            session.copyLimit(SearchResultConstants.ENTITY_TYPE_NAME, CachedExecutedSearchResultConstants.ENTITY_TYPE_NAME);

            query = session.applyLimit(session.getDslContext()
                    .select(EntityInstances.ENTITY_UNIQUE)
                    .from(CachedExecutedSearchResults)
                    .join(EntityInstances).onKey(CACHED_EXECUTED_SEARCH_RESULTS_ENTITY_INSTANCE_FK)
                    .where(CachedExecutedSearchResults.CACHED_EXECUTED_SEARCH.eq(cachedExecutedSearch.getPrimaryKey()))
                    .orderBy(CachedExecutedSearchResults.SORT_ORDER, CachedExecutedSearchResults.ENTITY_INSTANCE),
                    CachedExecutedSearchResultFactory.class);
        }

        return query.fetch(EntityInstances.ENTITY_UNIQUE);
    }

    public List<SalesOrderBatchResultTransfer> getSalesOrderBatchResultTransfers(UserVisit userVisit, UserVisitSearch userVisitSearch) {
        var entityIds = getSalesOrderBatchIdsFromUserVisitSearch(userVisitSearch);
        var salesOrderBatchResultTransfers = new ArrayList<SalesOrderBatchResultTransfer>(entityIds.size());
        var options = session.getOptions();
        var includeSalesOrderBatch = options != null && options.contains(SearchOptions.SalesOrderBatchResultIncludeSalesOrderBatch);

        for(var entityUniqueId : entityIds) {
            var batch = batchControl.getBatchByPK(new BatchPK(entityUniqueId));

            salesOrderBatchResultTransfers.add(new SalesOrderBatchResultTransfer(batch.getLastDetail().getBatchName(),
                    includeSalesOrderBatch ? getSalesOrderBatchTransfer(userVisit, batch) : null));
        }

        return salesOrderBatchResultTransfers;
    }

}
