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

package com.echothree.model.control.order.server.control;

import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.wishlist.server.control.WishlistControl;
import com.echothree.model.data.cancellationpolicy.server.entity.CancellationPolicy;
import com.echothree.model.data.inventory.server.entity.InventoryCondition;
import com.echothree.model.data.item.server.entity.Item;
import com.echothree.model.data.order.server.entity.Order;
import com.echothree.model.data.order.server.entity.OrderLine;
import com.echothree.model.data.order.server.entity.OrderLineStatus;
import com.echothree.model.data.order.server.entity.OrderShipmentGroup;
import com.echothree.model.data.order.server.factory.OrderLineDetailFactory;
import com.echothree.model.data.order.server.factory.OrderLineFactory;
import com.echothree.model.data.order.server.factory.OrderLineStatusFactory;
import com.echothree.model.data.order.server.value.OrderLineDetailValue;
import com.echothree.model.data.returnpolicy.server.entity.ReturnPolicy;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureType;
import com.echothree.model.data.wishlist.server.entity.WishlistPriority;
import com.echothree.util.common.exception.PersistenceDatabaseException;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.sql.SQLException;
import java.util.List;
import com.echothree.util.server.cdi.CommandScope;

@CommandScope
public class OrderLineControl
        extends BaseOrderControl {

    /** Creates a new instance of OrderControl */
    protected OrderLineControl() {
        super();
    }

    // --------------------------------------------------------------------------------
    //   Order Lines
    // --------------------------------------------------------------------------------
    
    public OrderLine createOrderLine(Order order, Integer orderLineSequence, OrderLine parentOrderLine, OrderShipmentGroup orderShipmentGroup, Item item,
            InventoryCondition inventoryCondition, UnitOfMeasureType unitOfMeasureType, Long quantity, Long unitAmount, String description,
            CancellationPolicy cancellationPolicy, ReturnPolicy returnPolicy, Boolean taxable, BasePK createdBy) {
        var orderLine = OrderLineFactory.getInstance().create();
        var orderLineDetail = OrderLineDetailFactory.getInstance().create(orderLine, order, orderLineSequence, parentOrderLine, orderShipmentGroup,
                item, inventoryCondition, unitOfMeasureType, quantity, unitAmount, description, cancellationPolicy, returnPolicy, taxable,
                session.getStartTime(), Session.MAX_TIME_LONG);
        
        // Convert to R/W
        orderLine = OrderLineFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                orderLine.getPrimaryKey());
        orderLine.setActiveDetail(orderLineDetail);
        orderLine.setLastDetail(orderLineDetail);
        orderLine.store();
        
        sendEvent(orderLine.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        createOrderLineStatus(orderLine);

        return orderLine;
    }
    
    public long countOrderLinesByCancellationPolicy(CancellationPolicy cancellationPolicy) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM orderlines, orderlinedetails " +
                "WHERE ordl_activedetailid = ordldt_orderlinedetailid AND ordldt_cnclplcy_cancellationpolicyid = ?",
                cancellationPolicy);
    }

    public long countOrderLinesByReturnPolicy(ReturnPolicy returnPolicy) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM orderlines, orderlinedetails " +
                "WHERE ordl_activedetailid = ordldt_orderlinedetailid AND ordldt_rtnplcy_returnpolicyid = ?",
                returnPolicy);
    }

    public long countOrderLinesByInventoryCondition(InventoryCondition inventoryCondition) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM orderlines, orderlinedetails " +
                "WHERE ordl_activedetailid = ordldt_orderlinedetailid AND ordldt_invcon_inventoryconditionid = ?",
                inventoryCondition);
    }

    public long countOrderLinesByUnitOfMeasureType(UnitOfMeasureType unitOfMeasureType) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM orderlines, orderlinedetails " +
                "WHERE ordl_activedetailid = ordldt_orderlinedetailid AND ordldt_uomt_unitofmeasuretypeid = ?",
                unitOfMeasureType);
    }

    public long countOrderLinesByOrderAndCancellationPolicy(Order order, CancellationPolicy cancellationPolicy) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM orderlines, orderlinedetails " +
                "WHERE ordl_activedetailid = ordldt_orderlinedetailid AND ordldt_ord_orderid = ? AND ordldt_cnclplcy_cancellationpolicyid = ?",
                order, cancellationPolicy);
    }

    public long countOrderLinesByOrderAndReturnPolicy(Order order, ReturnPolicy returnPolicy) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM orderlines, orderlinedetails " +
                "WHERE ordl_activedetailid = ordldt_orderlinedetailid AND ordldt_ord_orderid = ? AND ordldt_rtnplcy_returnpolicyid = ?",
                order, returnPolicy);
    }

    public long countOrderLinesByOrderAndShipmentGroup(Order order, OrderShipmentGroup orderShipmentGroup) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM orderlines, orderlinedetails " +
                "WHERE ordl_activedetailid = ordldt_orderlinedetailid AND ordldt_ord_orderid = ? AND ordldt_ordshpgrp_ordershipmentgroupid = ?",
                order, orderShipmentGroup);
    }

    public OrderLine getOrderLineBySequence(Order order, Integer orderLineSequence, EntityPermission entityPermission) {
        OrderLine orderLine;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM orderlines, orderlinedetails " +
                        "WHERE ordl_activedetailid = ordldt_orderlinedetailid AND ordldt_ord_orderid = ? AND ordldt_orderlinesequence = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM orderlines, orderlinedetails " +
                        "WHERE ordl_activedetailid = ordldt_orderlinedetailid AND ordldt_ord_orderid = ? AND ordldt_orderlinesequence = ? " +
                        "FOR UPDATE";
            }

            var ps = OrderLineFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, order.getPrimaryKey().getEntityId());
            ps.setInt(2, orderLineSequence);
            
            orderLine = OrderLineFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return orderLine;
    }
    
    public OrderLine getOrderLineBySequence(Order order, Integer orderLineSequence) {
        return getOrderLineBySequence(order, orderLineSequence, EntityPermission.READ_ONLY);
    }
    
    public OrderLine getOrderLineBySequenceForUpdate(Order order, Integer orderLineSequence) {
        return getOrderLineBySequence(order, orderLineSequence, EntityPermission.READ_WRITE);
    }
    
    public OrderLineDetailValue getOrderLineDetailValueForUpdate(OrderLine orderLine) {
        return orderLine == null? null: orderLine.getLastDetailForUpdate().getOrderLineDetailValue().clone();
    }
    
    public OrderLineDetailValue getOrderLineDetailValueBySequenceForUpdate(Order order, Integer orderLineSequence) {
        return getOrderLineDetailValueForUpdate(getOrderLineBySequenceForUpdate(order, orderLineSequence));
    }
    
    private List<OrderLine> getOrderLinesByOrder(Order order, EntityPermission entityPermission) {
        List<OrderLine> orderLines;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM orderlines, orderlinedetails " +
                        "WHERE ordl_activedetailid = ordldt_orderlinedetailid AND ordldt_ord_orderid = ? " +
                        "ORDER BY ordldt_orderlinesequence";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM orderlines, orderlinedetails " +
                        "WHERE ordl_activedetailid = ordldt_orderlinedetailid AND ordldt_ord_orderid = ? " +
                        "FOR UPDATE";
            }

            var ps = OrderLineFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, order.getPrimaryKey().getEntityId());
            
            orderLines = OrderLineFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return orderLines;
    }
    
    public List<OrderLine> getOrderLinesByOrder(Order order) {
        return getOrderLinesByOrder(order, EntityPermission.READ_ONLY);
    }
    
    public List<OrderLine> getOrderLinesByOrderForUpdate(Order order) {
        return getOrderLinesByOrder(order, EntityPermission.READ_WRITE);
    }
    
    private List<OrderLine> getOrderLinesByOrderShipmentGroup(OrderShipmentGroup orderShipmentGroup, EntityPermission entityPermission) {
        List<OrderLine> orderLines;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM orderlines, orderlinedetails " +
                        "WHERE ordl_activedetailid = ordldt_orderlinedetailid AND ordldt_ordshpgrp_ordershipmentgroupid = ? " +
                        "ORDER BY ordldt_orderlinesequence";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM orderlines, orderlinedetails " +
                        "WHERE ordl_activedetailid = ordldt_orderlinedetailid AND ordldt_ordshpgrp_ordershipmentgroupid = ? " +
                        "FOR UPDATE";
            }

            var ps = OrderLineFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, orderShipmentGroup.getPrimaryKey().getEntityId());
            
            orderLines = OrderLineFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return orderLines;
    }
    
    public List<OrderLine> getOrderLinesByOrderShipmentGroup(OrderShipmentGroup orderShipmentGroup) {
        return getOrderLinesByOrderShipmentGroup(orderShipmentGroup, EntityPermission.READ_ONLY);
    }
    
    public List<OrderLine> getOrderLinesByOrderShipmentGroupForUpdate(OrderShipmentGroup orderShipmentGroup) {
        return getOrderLinesByOrderShipmentGroup(orderShipmentGroup, EntityPermission.READ_WRITE);
    }
    
    public void updateOrderLineFromValue(OrderLineDetailValue orderLineDetailValue, BasePK updatedBy) {
        if(orderLineDetailValue.hasBeenModified()) {
            var orderLine = OrderLineFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    orderLineDetailValue.getOrderLinePK());
            var orderLineDetail = orderLine.getActiveDetailForUpdate();
            
            orderLineDetail.setThruTime(session.getStartTime());
            orderLineDetail.store();

            var orderLinePK = orderLineDetail.getOrderLinePK(); // Not updated
            var orderPK = orderLineDetail.getOrderPK(); // Not updated
            var orderLineSequence = orderLineDetail.getOrderLineSequence(); // Not updated
            var parentOrderLinePK = orderLineDetail.getParentOrderLinePK(); // Not updated
            var orderShipmentGroupPK = orderLineDetailValue.getOrderShipmentGroupPK();
            var itemPK = orderLineDetailValue.getItemPK();
            var inventoryConditionPK = orderLineDetailValue.getInventoryConditionPK();
            var unitOfMeasureTypePK = orderLineDetailValue.getUnitOfMeasureTypePK();
            var quantity = orderLineDetailValue.getQuantity();
            var unitAmount = orderLineDetailValue.getUnitAmount();
            var description = orderLineDetailValue.getDescription();
            var cancellationPolicyPK = orderLineDetailValue.getCancellationPolicyPK();
            var returnPolicyPK = orderLineDetailValue.getReturnPolicyPK();
            var taxable = orderLineDetailValue.getTaxable();
            
            orderLineDetail = OrderLineDetailFactory.getInstance().create(orderLinePK, orderPK, orderLineSequence, parentOrderLinePK, orderShipmentGroupPK,
                    itemPK, inventoryConditionPK, unitOfMeasureTypePK, quantity, unitAmount, description, cancellationPolicyPK, returnPolicyPK, taxable,
                    session.getStartTime(), Session.MAX_TIME_LONG);
            
            orderLine.setActiveDetail(orderLineDetail);
            orderLine.setLastDetail(orderLineDetail);
            
            sendEvent(orderLinePK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }
    
    public void deleteOrderLine(OrderLine orderLine, BasePK deletedBy) {
        var orderLineAdjustmentControl = Session.getModelController(OrderLineAdjustmentControl.class);
        var orderLineDetail = orderLine.getLastDetailForUpdate();

        removeOrderLineStatusByOrderLine(orderLine);
        orderLineAdjustmentControl.deleteOrderLineAdjustmentsByOrderLine(orderLine, deletedBy);
        
        orderLineDetail.setThruTime(session.getStartTime());
        orderLine.setActiveDetail(null);
        orderLine.store();
        
        sendEvent(orderLine.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }
    
    public void deleteOrderLinesByOrder(List<OrderLine> orderLines, BasePK deletedBy) {
        orderLines.forEach((orderLine) -> 
                deleteOrderLine(orderLine, deletedBy)
        );
    }
    
    public void deleteOrderLinesByOrder(Order order, BasePK deletedBy) {
        deleteOrderLinesByOrder(getOrderLinesByOrderForUpdate(order), deletedBy);
    }
    
    public void deleteOrderLinesByOrderShipmentGroup(OrderShipmentGroup orderShipmentGroup, BasePK deletedBy) {
        deleteOrderLinesByOrder(getOrderLinesByOrderShipmentGroupForUpdate(orderShipmentGroup), deletedBy);
    }
    
    public void deleteOrderLinesByWishlistPriority(WishlistPriority wishlistPriority, BasePK deletedBy) {
        var wishlistControl = Session.getModelController(WishlistControl.class);
        var wishlistLines = wishlistControl.getWishlistLinesByWishlistPriorityForUpdate(wishlistPriority);
        
        wishlistLines.forEach((wishlistLine) -> {
            deleteOrderLine(wishlistLine.getOrderLineForUpdate(), deletedBy);
        });
    }
    
    // --------------------------------------------------------------------------------
    //   Order Line Statuses
    // --------------------------------------------------------------------------------
    
    public OrderLineStatus createOrderLineStatus(OrderLine orderLine) {
        return OrderLineStatusFactory.getInstance().create(orderLine, 0);
    }
    
    private OrderLineStatus getOrderLineStatus(OrderLine orderLine, EntityPermission entityPermission) {
        OrderLineStatus orderLineStatus;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM orderlinestatuses " +
                        "WHERE ordlst_ordl_orderlineid = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM orderlinestatuses " +
                        "WHERE ordlst_ordl_orderlineid = ? " +
                        "FOR UPDATE";
            }

            var ps = OrderLineStatusFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, orderLine.getPrimaryKey().getEntityId());
            
            orderLineStatus = OrderLineStatusFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return orderLineStatus;
    }
    
    public OrderLineStatus getOrderLineStatus(OrderLine orderLine) {
        return getOrderLineStatus(orderLine, EntityPermission.READ_ONLY);
    }
    
    public OrderLineStatus getOrderLineStatusForUpdate(OrderLine orderLine) {
        return getOrderLineStatus(orderLine, EntityPermission.READ_WRITE);
    }
    
    public void removeOrderLineStatusByOrderLine(OrderLine orderLine) {
        var orderLineStatus = getOrderLineStatusForUpdate(orderLine);
        
        if(orderLineStatus != null) {
            orderLineStatus.remove();
        }
    }

}
