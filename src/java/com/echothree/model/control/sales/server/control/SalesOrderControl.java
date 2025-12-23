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
import com.echothree.model.control.core.server.control.EntityInstanceControl;
import com.echothree.model.control.sales.common.transfer.SalesOrderResultTransfer;
import com.echothree.model.control.sales.common.workflow.SalesOrderStatusConstants;
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
import com.echothree.model.data.search.server.entity.UserVisitSearch;
import com.echothree.model.data.search.server.factory.SearchResultFactory;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.exception.PersistenceDatabaseException;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.echothree.util.server.cdi.CommandScope;

@CommandScope
public class SalesOrderControl
        extends BaseSalesControl {

    /** Creates a new instance of SalesOrderControl */
    protected SalesOrderControl() {
        super();
    }

    // --------------------------------------------------------------------------------
    //   Sales Orders
    // --------------------------------------------------------------------------------

    public SalesOrder createSalesOrder(Order order, OfferUse offerUse, AssociateReferral associateReferral, BasePK createdBy) {
        var salesOrder = SalesOrderFactory.getInstance().create(order, offerUse, associateReferral,
                session.getStartTime(), Session.MAX_TIME);

        sendEvent(order.getPrimaryKey(), EventTypes.MODIFY, salesOrder.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return salesOrder;
    }

    public long countSalesOrdersByOfferUse(final OfferUse offerUse) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM salesorders " +
                "WHERE salord_ofruse_offeruseid = ? AND salord_thrutime = ?",
                offerUse, Session.MAX_TIME);
    }

    public long countSalesOrdersByAssociateReferral(final AssociateReferral associateReferral) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM salesorders " +
                "WHERE salord_ascrfr_associatereferralid = ? AND salord_thrutime = ?",
                associateReferral, Session.MAX_TIME);
    }

    private SalesOrder getSalesOrder(Order order, EntityPermission entityPermission) {
        SalesOrder salesOrder;

        try {
            String query = null;

            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM salesorders " +
                        "WHERE salord_ord_orderid = ? AND salord_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM salesorders " +
                        "WHERE salord_ord_orderid = ? AND salord_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = SalesOrderFactory.getInstance().prepareStatement(query);

            ps.setLong(1, order.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);

            salesOrder = SalesOrderFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return salesOrder;
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
            var salesOrder = SalesOrderFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    salesOrderValue.getPrimaryKey());

            salesOrder.setThruTime(session.getStartTime());
            salesOrder.store();

            var orderPK = salesOrder.getOrderPK(); // Not updated
            var offerUsePK = salesOrderValue.getOfferUsePK();
            var associateReferralPK = salesOrderValue.getAssociateReferralPK();

            salesOrder = SalesOrderFactory.getInstance().create(orderPK, offerUsePK, associateReferralPK,
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

    public SalesOrderLine createSalesOrderLine(OrderLine orderLine, OfferUse offerUse, AssociateReferral associateReferral,
            BasePK createdBy) {
        var salesOrderLine = SalesOrderLineFactory.getInstance().create(orderLine, offerUse, associateReferral,
                session.getStartTime(), Session.MAX_TIME);

        sendEvent(orderLine.getPrimaryKey(), EventTypes.MODIFY, salesOrderLine.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return salesOrderLine;
    }

    public long countSalesOrderLinesByOfferUse(final OfferUse offerUse) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                        "FROM salesorderlines " +
                        "WHERE salordl_ofruse_offeruseid = ? AND salordl_thrutime = ?",
                offerUse, Session.MAX_TIME);
    }

    public long countSalesOrderLinesByAssociateReferral(final AssociateReferral associateReferral) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                        "FROM salesorderlines " +
                        "WHERE salordl_ascrfr_associatereferralid = ? AND salordl_thrutime = ?",
                associateReferral, Session.MAX_TIME);
    }

    private SalesOrderLine getSalesOrderLine(OrderLine orderLine, EntityPermission entityPermission) {
        SalesOrderLine salesOrderLine;

        try {
            String query = null;

            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM salesorderlines " +
                        "WHERE salordl_ordl_orderlineid = ? AND salordl_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM salesorderlines " +
                        "WHERE salordl_ordl_orderlineid = ? AND salordl_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = SalesOrderLineFactory.getInstance().prepareStatement(query);

            ps.setLong(1, orderLine.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);

            salesOrderLine = SalesOrderLineFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return salesOrderLine;
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
            var salesOrderLine = SalesOrderLineFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    salesOrderLineValue.getPrimaryKey());

            salesOrderLine.setThruTime(session.getStartTime());
            salesOrderLine.store();

            var orderLinePK = salesOrderLine.getOrderLinePK(); // Not updated
            var offerUsePK = salesOrderLineValue.getOfferUsePK();
            var associateReferralPK = salesOrderLineValue.getAssociateReferralPK();

            salesOrderLine = SalesOrderLineFactory.getInstance().create(orderLinePK, offerUsePK, associateReferralPK,
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

    public List<SalesOrderResultTransfer> getSalesOrderResultTransfers(UserVisit userVisit, UserVisitSearch userVisitSearch) {
        var search = userVisitSearch.getSearch();
        var salesOrderResultTransfers = new ArrayList<SalesOrderResultTransfer>();

        try {
            var entityInstanceControl = Session.getModelController(EntityInstanceControl.class);
            var ps = SearchResultFactory.getInstance().prepareStatement(
                    "SELECT eni_entityuniqueid " +
                            "FROM searchresults, entityinstances " +
                            "WHERE srchr_srch_searchid = ? AND srchr_eni_entityinstanceid = eni_entityinstanceid " +
                            "ORDER BY srchr_sortorder, srchr_eni_entityinstanceid " +
                            "_LIMIT_");

            ps.setLong(1, search.getPrimaryKey().getEntityId());

            try (var rs = ps.executeQuery()) {
                while(rs.next()) {
                    var orderPK = new OrderPK(rs.getLong(1));
                    var order = OrderFactory.getInstance().getEntityFromPK(EntityPermission.READ_ONLY, orderPK);

                    var entityInstance = entityInstanceControl.getEntityInstanceByBasePK(orderPK);
                    var orderStatusTransfer = workflowControl.getWorkflowEntityStatusTransferByEntityInstanceUsingNames(userVisit,
                            SalesOrderStatusConstants.Workflow_SALES_ORDER_STATUS, entityInstance);

                    salesOrderResultTransfers.add(new SalesOrderResultTransfer(order.getLastDetail().getOrderName(), orderStatusTransfer));
                }
            } catch (SQLException se) {
                throw new PersistenceDatabaseException(se);
            }
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return salesOrderResultTransfers;
    }

}
