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

import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.sales.common.transfer.SalesOrderResultTransfer;
import com.echothree.model.control.sales.common.workflow.SalesOrderStatusConstants;
import com.echothree.model.control.search.server.control.SearchControl;
import com.echothree.model.data.associate.server.entity.AssociateReferral;
import com.echothree.model.data.offer.server.entity.OfferUse;
import com.echothree.model.data.order.common.pk.OrderPK;
import com.echothree.model.data.order.server.entity.Order;
import com.echothree.model.data.order.server.entity.OrderLine;
import com.echothree.model.data.order.server.factory.OrderFactory;
import com.echothree.model.data.sales.server.entity.SalesOrder;
import com.echothree.model.data.sales.server.entity.SalesOrderLine;
import com.echothree.model.data.sales.server.factory.SalesOrderFactory;
import com.echothree.model.data.sales.server.factory.SalesOrderLineFactory;
import com.echothree.model.data.sales.server.value.SalesOrderLineValue;
import com.echothree.model.data.sales.server.value.SalesOrderValue;
import com.echothree.model.data.search.common.CachedExecutedSearchResultConstants;
import com.echothree.model.data.search.common.SearchResultConstants;
import com.echothree.model.data.search.server.entity.UserVisitSearch;
import com.echothree.model.data.search.server.factory.CachedExecutedSearchResultFactory;
import com.echothree.model.data.search.server.factory.SearchResultFactory;
import com.echothree.model.data.user.server.entity.UserVisit;
import static com.echothree.model.jooq.server.keys.search.SearchForeignKeys.CACHED_EXECUTED_SEARCH_RESULTS_ENTITY_INSTANCE_FK;
import static com.echothree.model.jooq.server.keys.search.SearchForeignKeys.SEARCH_RESULTS_ENTITY_INSTANCE_FK;
import static com.echothree.model.jooq.server.tables.core.EntityInstances.EntityInstances;
import static com.echothree.model.jooq.server.tables.sales.SalesOrderLines.SalesOrderLines;
import static com.echothree.model.jooq.server.tables.sales.SalesOrders.SalesOrders;
import static com.echothree.model.jooq.server.tables.search.CachedExecutedSearchResults.CachedExecutedSearchResults;
import static com.echothree.model.jooq.server.tables.search.SearchResults.SearchResults;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.cdi.CommandScope;
import com.echothree.util.server.control.BaseModelControl;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import org.jooq.Select;

@CommandScope
public class SalesOrderControl
        extends BaseModelControl {

    /** Creates a new instance of SalesOrderControl */

    protected SalesOrderControl() {
        super();
    }

    // --------------------------------------------------------------------------------
    //   Sales Orders
    // --------------------------------------------------------------------------------

    @Inject
    protected SalesOrderFactory salesOrderFactory;

    @Inject
    private SearchControl searchControl;

    public SalesOrder createSalesOrder(Order order, OfferUse offerUse, AssociateReferral associateReferral, BasePK createdBy) {
        var salesOrder = salesOrderFactory.create(order, offerUse, associateReferral,
                session.getStartTime(), Session.MAX_TIME);

        sendEvent(order.getPrimaryKey(), EventTypes.MODIFY, salesOrder.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return salesOrder;
    }

    public long countSalesOrdersByOfferUse(final OfferUse offerUse) {
        return session.getDslContext()
                .selectCount()
                .from(SalesOrders)
                .where(SalesOrders.OFFER_USE.eq(offerUse.getPrimaryKey()),
                        SalesOrders.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countSalesOrdersByAssociateReferral(final AssociateReferral associateReferral) {
        return session.getDslContext()
                .selectCount()
                .from(SalesOrders)
                .where(SalesOrders.ASSOCIATE_REFERRAL.eq(associateReferral.getPrimaryKey()),
                        SalesOrders.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private SalesOrder getSalesOrder(Order order, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(SalesOrders.fields())
                .from(SalesOrders)
                .where(SalesOrders.ORDER.eq(order.getPrimaryKey()),
                        SalesOrders.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return salesOrderFactory.getEntityFromQuery(entityPermission, query);
    }

    public SalesOrder getSalesOrder(Order order) {
        return getSalesOrder(order, EntityPermission.READ_ONLY);
    }

    public SalesOrder getSalesOrderForUpdate(Order order) {
        return getSalesOrder(order, EntityPermission.READ_WRITE);
    }

    public SalesOrderValue getSalesOrderValue(SalesOrder salesOrder) {
        return salesOrder == null? null: salesOrder.getSalesOrderValue().clone();
    }

    public SalesOrderValue getSalesOrderValueForUpdate(Order order) {
        return getSalesOrderValue(getSalesOrderForUpdate(order));
    }

    public void updateSalesOrderFromValue(SalesOrderValue salesOrderValue, BasePK updatedBy) {
        if(salesOrderValue.hasBeenModified()) {
            var salesOrder = salesOrderFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                    salesOrderValue.getPrimaryKey());

            salesOrder.setThruTime(session.getStartTime());
            salesOrder.store();

            var orderPK = salesOrder.getOrderPK(); // Not updated
            var offerUsePK = salesOrderValue.getOfferUsePK();
            var associateReferralPK = salesOrderValue.getAssociateReferralPK();

            salesOrder = salesOrderFactory.create(orderPK, offerUsePK, associateReferralPK,
                    session.getStartTime(), Session.MAX_TIME);

            sendEvent(orderPK, EventTypes.MODIFY, salesOrder.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteSalesOrder(SalesOrder salesOrder, BasePK deletedBy) {
        salesOrder.setThruTime(session.getStartTime());

        sendEvent(salesOrder.getOrderPK(), EventTypes.MODIFY, salesOrder.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Sales Order Lines
    // --------------------------------------------------------------------------------

    @Inject
    protected SalesOrderLineFactory salesOrderLineFactory;

    public SalesOrderLine createSalesOrderLine(OrderLine orderLine, OfferUse offerUse, AssociateReferral associateReferral,
            BasePK createdBy) {
        var salesOrderLine = salesOrderLineFactory.create(orderLine, offerUse, associateReferral,
                session.getStartTime(), Session.MAX_TIME);

        sendEvent(orderLine.getPrimaryKey(), EventTypes.MODIFY, salesOrderLine.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return salesOrderLine;
    }

    public long countSalesOrderLinesByOfferUse(final OfferUse offerUse) {
        return session.getDslContext()
                .selectCount()
                .from(SalesOrderLines)
                .where(SalesOrderLines.OFFER_USE.eq(offerUse.getPrimaryKey()),
                        SalesOrderLines.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countSalesOrderLinesByAssociateReferral(final AssociateReferral associateReferral) {
        return session.getDslContext()
                .selectCount()
                .from(SalesOrderLines)
                .where(SalesOrderLines.ASSOCIATE_REFERRAL.eq(associateReferral.getPrimaryKey()),
                        SalesOrderLines.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private SalesOrderLine getSalesOrderLine(OrderLine orderLine, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(SalesOrderLines.fields())
                .from(SalesOrderLines)
                .where(SalesOrderLines.ORDER_LINE.eq(orderLine.getPrimaryKey()),
                        SalesOrderLines.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return salesOrderLineFactory.getEntityFromQuery(entityPermission, query);
    }

    public SalesOrderLine getSalesOrderLine(OrderLine orderLine) {
        return getSalesOrderLine(orderLine, EntityPermission.READ_ONLY);
    }

    public SalesOrderLine getSalesOrderLineForUpdate(OrderLine orderLine) {
        return getSalesOrderLine(orderLine, EntityPermission.READ_WRITE);
    }

    public SalesOrderLineValue getSalesOrderLineValue(SalesOrderLine salesOrderLine) {
        return salesOrderLine == null? null: salesOrderLine.getSalesOrderLineValue().clone();
    }

    public SalesOrderLineValue getSalesOrderLineValueForUpdate(OrderLine orderLine) {
        return getSalesOrderLineValue(getSalesOrderLineForUpdate(orderLine));
    }

    public void updateSalesOrderLineFromValue(SalesOrderLineValue salesOrderLineValue, BasePK updatedBy) {
        if(salesOrderLineValue.hasBeenModified()) {
            var salesOrderLine = salesOrderLineFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                    salesOrderLineValue.getPrimaryKey());

            salesOrderLine.setThruTime(session.getStartTime());
            salesOrderLine.store();

            var orderLinePK = salesOrderLine.getOrderLinePK(); // Not updated
            var offerUsePK = salesOrderLineValue.getOfferUsePK();
            var associateReferralPK = salesOrderLineValue.getAssociateReferralPK();

            salesOrderLine = salesOrderLineFactory.create(orderLinePK, offerUsePK, associateReferralPK,
                    session.getStartTime(), Session.MAX_TIME);

            sendEvent(orderLinePK, EventTypes.MODIFY, salesOrderLine.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteSalesOrderLine(SalesOrderLine salesOrderLine, BasePK deletedBy) {
        salesOrderLine.setThruTime(session.getStartTime());

        sendEvent(salesOrderLine.getOrderLinePK(), EventTypes.MODIFY, salesOrderLine.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }

    public void deleteSalesOrderLineByOrderLine(OrderLine orderLine, BasePK deletedBy) {
        deleteSalesOrderLine(getSalesOrderLineForUpdate(orderLine), deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Sales Order Searches
    // --------------------------------------------------------------------------------

    @Inject
    protected OrderFactory orderFactory;

    @Inject
    protected SearchResultFactory searchResultFactory;

    private List<Long> getSalesOrderIdsFromUserVisitSearch(UserVisitSearch userVisitSearch) {
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

    public List<SalesOrderResultTransfer> getSalesOrderResultTransfers(UserVisit userVisit, UserVisitSearch userVisitSearch) {
        var entityIds = getSalesOrderIdsFromUserVisitSearch(userVisitSearch);
        var salesOrderResultTransfers = new ArrayList<SalesOrderResultTransfer>(entityIds.size());

        for(var entityUniqueId : entityIds) {
            var orderPK = new OrderPK(entityUniqueId);
            var order = orderFactory.getEntityFromPK(EntityPermission.READ_ONLY, orderPK);

            var entityInstance = entityInstanceControl.getEntityInstanceByBasePK(orderPK);
            var orderStatusTransfer = workflowControl.getWorkflowEntityStatusTransferByEntityInstanceUsingNames(userVisit,
                    SalesOrderStatusConstants.Workflow_SALES_ORDER_STATUS, entityInstance);

            salesOrderResultTransfers.add(new SalesOrderResultTransfer(order.getLastDetail().getOrderName(), orderStatusTransfer));
        }

        return salesOrderResultTransfers;
    }

}
