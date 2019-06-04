// --------------------------------------------------------------------------------
// Copyright 2002-2019 Echo Three, LLC
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

package com.echothree.model.control.sales.server;

import com.echothree.model.control.batch.common.BatchConstants;
import com.echothree.model.control.batch.server.BatchControl;
import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.sales.common.transfer.SalesOrderBatchTransfer;
import com.echothree.model.control.sales.server.transfer.SalesOrderBatchTransferCache;
import com.echothree.model.control.sales.server.transfer.SalesTransferCaches;
import com.echothree.model.data.associate.common.pk.AssociateReferralPK;
import com.echothree.model.data.associate.server.entity.AssociateReferral;
import com.echothree.model.data.batch.common.pk.BatchPK;
import com.echothree.model.data.batch.server.entity.Batch;
import com.echothree.model.data.offer.common.pk.OfferUsePK;
import com.echothree.model.data.offer.server.entity.OfferUse;
import com.echothree.model.data.order.common.pk.OrderLinePK;
import com.echothree.model.data.order.common.pk.OrderPK;
import com.echothree.model.data.order.server.entity.Order;
import com.echothree.model.data.order.server.entity.OrderLine;
import com.echothree.model.data.payment.common.pk.PaymentMethodPK;
import com.echothree.model.data.payment.server.entity.PaymentMethod;
import com.echothree.model.data.sales.server.entity.SalesOrder;
import com.echothree.model.data.sales.server.entity.SalesOrderBatch;
import com.echothree.model.data.sales.server.entity.SalesOrderLine;
import com.echothree.model.data.sales.server.factory.SalesOrderBatchFactory;
import com.echothree.model.data.sales.server.factory.SalesOrderFactory;
import com.echothree.model.data.sales.server.factory.SalesOrderLineFactory;
import com.echothree.model.data.sales.server.value.SalesOrderBatchValue;
import com.echothree.model.data.sales.server.value.SalesOrderLineValue;
import com.echothree.model.data.sales.server.value.SalesOrderValue;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.exception.PersistenceDatabaseException;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseModelControl;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SalesControl
        extends BaseModelControl {
    
    /** Creates a new instance of SalesControl */
    public SalesControl() {
        super();
    }
    
    // --------------------------------------------------------------------------------
    //   Sale Transfer Caches
    // --------------------------------------------------------------------------------
    
    private SalesTransferCaches saleTransferCaches = null;
    
    public SalesTransferCaches getSaleTransferCaches(UserVisit userVisit) {
        if(saleTransferCaches == null) {
            saleTransferCaches = new SalesTransferCaches(userVisit, this);
        }
        
        return saleTransferCaches;
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
    
    // --------------------------------------------------------------------------------
    //   Sales Orders
    // --------------------------------------------------------------------------------
    
    public SalesOrder createSalesOrder(Order order, OfferUse offerUse, AssociateReferral associateReferral, BasePK createdBy) {
        SalesOrder salesOrder = SalesOrderFactory.getInstance().create(order, offerUse, associateReferral,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(order.getPrimaryKey(), EventTypes.MODIFY.name(), salesOrder.getPrimaryKey(), null, createdBy);
        
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
            
            sendEventUsingNames(orderPK, EventTypes.MODIFY.name(), salesOrder.getPrimaryKey(), null, updatedBy);
        }
    }
    
    public void deleteSalesOrder(SalesOrder salesOrder, BasePK deletedBy) {
        salesOrder.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(salesOrder.getOrderPK(), EventTypes.MODIFY.name(), salesOrder.getPrimaryKey(),
                null, deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Sales Order Lines
    // --------------------------------------------------------------------------------
    
    public SalesOrderLine createSalesOrderLine(OrderLine orderLine, OfferUse offerUse, AssociateReferral associateReferral,
            BasePK createdBy) {
        SalesOrderLine salesOrderLine = SalesOrderLineFactory.getInstance().create(orderLine, offerUse, associateReferral,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(orderLine.getPrimaryKey(), EventTypes.MODIFY.name(), salesOrderLine.getPrimaryKey(), null, createdBy);
        
        return salesOrderLine;
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
            
            sendEventUsingNames(orderLinePK, EventTypes.MODIFY.name(), salesOrderLine.getPrimaryKey(), null, updatedBy);
        }
    }
    
    public void deleteSalesOrderLine(SalesOrderLine salesOrderLine, BasePK deletedBy) {
        salesOrderLine.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(salesOrderLine.getOrderLinePK(), EventTypes.MODIFY.name(), salesOrderLine.getPrimaryKey(), null, deletedBy);
    }

    public void deleteSalesOrderLineByOrderLine(OrderLine orderLine, BasePK deletedBy) {
        deleteSalesOrderLine(getSalesOrderLineForUpdate(orderLine), deletedBy);
    }
    
}
