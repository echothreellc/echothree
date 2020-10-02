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

import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.data.associate.common.pk.AssociateReferralPK;
import com.echothree.model.data.associate.server.entity.AssociateReferral;
import com.echothree.model.data.offer.common.pk.OfferUsePK;
import com.echothree.model.data.offer.server.entity.OfferUse;
import com.echothree.model.data.order.common.pk.OrderLinePK;
import com.echothree.model.data.order.common.pk.OrderPK;
import com.echothree.model.data.order.server.entity.Order;
import com.echothree.model.data.order.server.entity.OrderLine;
import com.echothree.model.data.sales.server.entity.SalesOrder;
import com.echothree.model.data.sales.server.entity.SalesOrderLine;
import com.echothree.model.data.sales.server.factory.SalesOrderFactory;
import com.echothree.model.data.sales.server.factory.SalesOrderLineFactory;
import com.echothree.model.data.sales.server.value.SalesOrderLineValue;
import com.echothree.model.data.sales.server.value.SalesOrderValue;
import com.echothree.util.common.exception.PersistenceDatabaseException;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SalesOrderControl
        extends BaseSalesControl {

    /** Creates a new instance of SalesOrderControl */
    public SalesOrderControl() {
        super();
    }

    // --------------------------------------------------------------------------------
    //   Sales Orders
    // --------------------------------------------------------------------------------

    public SalesOrder createSalesOrder(Order order, OfferUse offerUse, AssociateReferral associateReferral, BasePK createdBy) {
        SalesOrder salesOrder = SalesOrderFactory.getInstance().create(order, offerUse, associateReferral,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEventUsingNames(order.getPrimaryKey(), EventTypes.MODIFY.name(), salesOrder.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);

        return salesOrder;
    }

    public long countSalesOrdersByOfferUse(final OfferUse offerUse) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM salesorders " +
                "WHERE salord_ofruse_offeruseid = ? AND salord_thrutime = ?",
                offerUse, Session.MAX_TIME_LONG);
    }

    public long countSalesOrdersByAssociateReferral(final AssociateReferral associateReferral) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM salesorders " +
                "WHERE salord_ascrfr_associatereferralid = ? AND salord_thrutime = ?",
                associateReferral, Session.MAX_TIME_LONG);
    }

    private SalesOrder getSalesOrder(Order order, EntityPermission entityPermission) {
        SalesOrder salesOrder = null;

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

            PreparedStatement ps = SalesOrderFactory.getInstance().prepareStatement(query);

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
            SalesOrder salesOrder = SalesOrderFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    salesOrderValue.getPrimaryKey());

            salesOrder.setThruTime(session.START_TIME_LONG);
            salesOrder.store();

            OrderPK orderPK = salesOrder.getOrderPK(); // Not updated
            OfferUsePK offerUsePK = salesOrderValue.getOfferUsePK();
            AssociateReferralPK associateReferralPK = salesOrderValue.getAssociateReferralPK();

            salesOrder = SalesOrderFactory.getInstance().create(orderPK, offerUsePK, associateReferralPK,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);

            sendEventUsingNames(orderPK, EventTypes.MODIFY.name(), salesOrder.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }

    public void deleteSalesOrder(SalesOrder salesOrder, BasePK deletedBy) {
        salesOrder.setThruTime(session.START_TIME_LONG);

        sendEventUsingNames(salesOrder.getOrderPK(), EventTypes.MODIFY.name(), salesOrder.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Sales Order Lines
    // --------------------------------------------------------------------------------

    public SalesOrderLine createSalesOrderLine(OrderLine orderLine, OfferUse offerUse, AssociateReferral associateReferral,
            BasePK createdBy) {
        SalesOrderLine salesOrderLine = SalesOrderLineFactory.getInstance().create(orderLine, offerUse, associateReferral,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEventUsingNames(orderLine.getPrimaryKey(), EventTypes.MODIFY.name(), salesOrderLine.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);

        return salesOrderLine;
    }

    public long countSalesOrderLinesByOfferUse(final OfferUse offerUse) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                        "FROM salesorderlines " +
                        "WHERE salordl_ofruse_offeruseid = ? AND salordl_thrutime = ?",
                offerUse, Session.MAX_TIME_LONG);
    }

    public long countSalesOrderLinesByAssociateReferral(final AssociateReferral associateReferral) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                        "FROM salesorderlines " +
                        "WHERE salordl_ascrfr_associatereferralid = ? AND salordl_thrutime = ?",
                associateReferral, Session.MAX_TIME_LONG);
    }

    private SalesOrderLine getSalesOrderLine(OrderLine orderLine, EntityPermission entityPermission) {
        SalesOrderLine salesOrderLine = null;

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

            PreparedStatement ps = SalesOrderLineFactory.getInstance().prepareStatement(query);

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
            SalesOrderLine salesOrderLine = SalesOrderLineFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    salesOrderLineValue.getPrimaryKey());

            salesOrderLine.setThruTime(session.START_TIME_LONG);
            salesOrderLine.store();

            OrderLinePK orderLinePK = salesOrderLine.getOrderLinePK(); // Not updated
            OfferUsePK offerUsePK = salesOrderLineValue.getOfferUsePK();
            AssociateReferralPK associateReferralPK = salesOrderLineValue.getAssociateReferralPK();

            salesOrderLine = SalesOrderLineFactory.getInstance().create(orderLinePK, offerUsePK, associateReferralPK,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);

            sendEventUsingNames(orderLinePK, EventTypes.MODIFY.name(), salesOrderLine.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }

    public void deleteSalesOrderLine(SalesOrderLine salesOrderLine, BasePK deletedBy) {
        salesOrderLine.setThruTime(session.START_TIME_LONG);

        sendEventUsingNames(salesOrderLine.getOrderLinePK(), EventTypes.MODIFY.name(), salesOrderLine.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
    }

    public void deleteSalesOrderLineByOrderLine(OrderLine orderLine, BasePK deletedBy) {
        deleteSalesOrderLine(getSalesOrderLineForUpdate(orderLine), deletedBy);
    }

}
