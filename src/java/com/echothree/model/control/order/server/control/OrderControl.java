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

package com.echothree.model.control.order.server.control;

import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.order.common.OrderRoleTypes;
import com.echothree.model.control.order.common.OrderTypes;
import com.echothree.model.control.order.common.transfer.OrderContentCatalogTransfer;
import com.echothree.model.control.order.common.transfer.OrderTransfer;
import com.echothree.model.control.order.server.transfer.OrderContentCatalogTransferCache;
import com.echothree.model.control.wishlist.server.control.WishlistControl;
import com.echothree.model.data.accounting.server.entity.Currency;
import com.echothree.model.data.cancellationpolicy.server.entity.CancellationPolicy;
import com.echothree.model.data.content.server.entity.ContentCatalog;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.order.common.pk.OrderPK;
import com.echothree.model.data.order.server.entity.Order;
import com.echothree.model.data.order.server.entity.OrderContentCatalog;
import com.echothree.model.data.order.server.entity.OrderPriority;
import com.echothree.model.data.order.server.entity.OrderStatus;
import com.echothree.model.data.order.server.entity.OrderType;
import com.echothree.model.data.order.server.entity.OrderUserVisit;
import com.echothree.model.data.order.server.factory.OrderContentCatalogFactory;
import com.echothree.model.data.order.server.factory.OrderDetailFactory;
import com.echothree.model.data.order.server.factory.OrderFactory;
import com.echothree.model.data.order.server.factory.OrderStatusFactory;
import com.echothree.model.data.order.server.factory.OrderUserVisitFactory;
import com.echothree.model.data.order.server.value.OrderContentCatalogValue;
import com.echothree.model.data.order.server.value.OrderDetailValue;
import com.echothree.model.data.order.server.value.OrderUserVisitValue;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.returnpolicy.server.entity.ReturnPolicy;
import com.echothree.model.data.shipment.server.entity.FreeOnBoard;
import com.echothree.model.data.term.server.entity.Term;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.model.data.wishlist.server.entity.WishlistType;
import com.echothree.util.common.exception.PersistenceDatabaseException;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.cdi.CommandScope;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;

@CommandScope
public class OrderControl
        extends BaseOrderControl {
    
    /** Creates a new instance of OrderControl */
    protected OrderControl() {
        super();
    }

    // --------------------------------------------------------------------------------
    //   Orders
    // --------------------------------------------------------------------------------
    
    public Order createOrder(OrderType orderType, String orderName, OrderPriority orderPriority, Currency currency, Boolean holdUntilComplete,
            Boolean allowBackorders, Boolean allowSubstitutions, Boolean allowCombiningShipments, Term term, FreeOnBoard freeOnBoard,
            String reference, String description, CancellationPolicy cancellationPolicy, ReturnPolicy returnPolicy, Boolean taxable,
            BasePK createdBy) {
        var order = OrderFactory.getInstance().create();
        var orderDetail = OrderDetailFactory.getInstance().create(order, orderType, orderName, orderPriority,
                currency, holdUntilComplete, allowBackorders, allowSubstitutions, allowCombiningShipments, term,
                freeOnBoard, reference, description, cancellationPolicy, returnPolicy, taxable, session.getStartTime(),
                Session.MAX_TIME);
        
        // Convert to R/W
        order = OrderFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, order.getPrimaryKey());
        order.setActiveDetail(orderDetail);
        order.setLastDetail(orderDetail);
        order.store();
        
        sendEvent(order.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        createOrderStatus(order);
        
        return order;
    }
    
    public Order getOrderByPK(OrderPK orderPK) {
        return OrderFactory.getInstance().getEntityFromPK(EntityPermission.READ_ONLY, orderPK);
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.Order */
    public Order getOrderByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new OrderPK(entityInstance.getEntityUniqueId());

        return OrderFactory.getInstance().getEntityFromPK(entityPermission, pk);
    }

    public Order getOrderByEntityInstance(EntityInstance entityInstance) {
        return getOrderByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public Order getOrderByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getOrderByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countOrdersByOrderType(final OrderType orderType) {
        return session.queryForLong("""
                SELECT COUNT(*)
                FROM orders, orderdetails
                WHERE ord_activedetailid = orddt_orderdetailid AND orddt_ordtyp_ordertypeid = ?
                """, orderType);
    }

    public long countOrdersByOrderPriority(final OrderPriority orderPriority) {
        return session.queryForLong("""
                SELECT COUNT(*)
                FROM orders, orderdetails
                WHERE ord_activedetailid = orddt_orderdetailid AND orddt_ordpr_orderpriorityid = ?
                """, orderPriority);
    }

    public long countOrdersByCancellationPolicy(final CancellationPolicy cancellationPolicy) {
        return session.queryForLong("""
                SELECT COUNT(*)
                FROM orders, orderdetails
                WHERE ord_activedetailid = orddt_orderdetailid AND orddt_cnclplcy_cancellationpolicyid = ?
                """, cancellationPolicy);
    }

    public long countOrdersByReturnPolicy(final ReturnPolicy returnPolicy) {
        return session.queryForLong("""
                SELECT COUNT(*)
                FROM orders, orderdetails
                WHERE ord_activedetailid = orddt_orderdetailid AND orddt_rtnplcy_returnpolicyid = ?
                """, returnPolicy);
    }

    private static final Map<EntityPermission, String> getOrderByNameQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                """
                SELECT _ALL_
                FROM orders, orderdetails
                WHERE ord_activedetailid = orddt_orderdetailid AND orddt_ordtyp_ordertypeid = ? AND orddt_ordername = ?
                """);
        queryMap.put(EntityPermission.READ_WRITE,
                """
                SELECT _ALL_
                FROM orders, orderdetails
                WHERE ord_activedetailid = orddt_orderdetailid AND orddt_ordtyp_ordertypeid = ? AND orddt_ordername = ?
                FOR UPDATE
                """);
        getOrderByNameQueries = Collections.unmodifiableMap(queryMap);
    }

    public Order getOrderByName(OrderType orderType, String orderName, EntityPermission entityPermission) {
        return OrderFactory.getInstance().getEntityFromQuery(entityPermission, getOrderByNameQueries,
                orderType, orderName);
    }

    private Order getOrderByNameUsingNames(String orderTypeName, String orderName, EntityPermission entityPermission) {
        var orderTypeControl = Session.getModelController(OrderTypeControl.class);

        var orderType = orderTypeControl.getOrderTypeByName(orderTypeName);

        return getOrderByName(orderType, orderName, entityPermission);
    }

    public Order getOrderByName(OrderType orderType, String orderName) {
        return getOrderByName(orderType, orderName, EntityPermission.READ_ONLY);
    }

    public Order getOrderByNameForUpdate(OrderType orderType, String orderName) {
        return getOrderByName(orderType, orderName, EntityPermission.READ_WRITE);
    }

    public Order getOrderByNameUsingNames(String orderTypeName, String orderName) {
        return getOrderByNameUsingNames(orderTypeName, orderName, EntityPermission.READ_ONLY);
    }

    public Order getOrderByNameForUpdateUsingNames(String orderTypeName, String orderName) {
        return getOrderByNameUsingNames(orderTypeName, orderName, EntityPermission.READ_WRITE);
    }

    public OrderDetailValue getOrderDetailValueForUpdate(Order order) {
        return order == null? null: order.getLastDetailForUpdate().getOrderDetailValue().clone();
    }
    
    public OrderDetailValue getOrderDetailValueByNameForUpdate(OrderType orderType, String orderName) {
        return getOrderDetailValueForUpdate(getOrderByNameForUpdate(orderType, orderName));
    }
    
    public long countOrdersByBillToAndReference(final Party billToParty, final String reference) {
        return session.queryForLong(
                """
                SELECT COUNT(*)
                FROM orders, orderdetails, orderroles, orderroletypes
                WHERE ord_activedetailid = orddt_orderdetailid AND orddt_reference = ?
                AND ord_orderid = ordr_ord_orderid AND ordr_thrutime = ? AND ordr_par_partyid = ?
                AND ordr_ordrtyp_orderroletypeid = ordrtyp_orderroletypeid AND ordrtyp_orderroletypename = ?
                """,
                reference, Session.MAX_TIME, billToParty, OrderRoleTypes.BILL_TO.name());
    }

    public OrderTransfer getOrderTransfer(UserVisit userVisit, Order order) {
        // TODO
        return null;
    }

    public void updateOrderFromValue(OrderDetailValue orderDetailValue, BasePK updatedBy) {
        if(orderDetailValue.hasBeenModified()) {
            var order = OrderFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, orderDetailValue.getOrderPK());
            var orderDetail = order.getActiveDetailForUpdate();
            
            orderDetail.setThruTime(session.getStartTime());
            orderDetail.store();

            var orderPK = orderDetail.getOrderPK(); // Not updated
            var orderTypePK = orderDetail.getOrderTypePK(); // Not updated
            var orderName = orderDetailValue.getOrderName();
            var orderPriorityPK = orderDetailValue.getOrderPriorityPK();
            var currencyPK = orderDetail.getCurrencyPK(); // Not updated
            var holdUntilComplete = orderDetailValue.getHoldUntilComplete();
            var allowBackorders = orderDetailValue.getAllowBackorders();
            var allowSubstitutions = orderDetailValue.getAllowSubstitutions();
            var allowCombiningShipments = orderDetailValue.getAllowCombiningShipments();
            var termPK = orderDetailValue.getTermPK();
            var freeOnBoardPK = orderDetailValue.getFreeOnBoardPK();
            var reference = orderDetailValue.getReference();
            var description = orderDetailValue.getDescription();
            var cancellationPolicyPK = orderDetailValue.getCancellationPolicyPK();
            var returnPolicyPK = orderDetailValue.getReturnPolicyPK();
            var taxable = orderDetailValue.getTaxable();
            
            orderDetail = OrderDetailFactory.getInstance().create(orderPK, orderTypePK, orderName, orderPriorityPK, currencyPK, holdUntilComplete,
                    allowBackorders, allowSubstitutions, allowCombiningShipments, termPK, freeOnBoardPK, reference, description, cancellationPolicyPK,
                    returnPolicyPK, taxable, session.getStartTime(), Session.MAX_TIME);
            
            order.setActiveDetail(orderDetail);
            order.setLastDetail(orderDetail);
            
            sendEvent(orderPK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }
    
    public void deleteOrder(Order order, BasePK deletedBy) {
        var orderTimeControl = Session.getModelController(OrderTimeControl.class);
        var orderRoleControl = Session.getModelController(OrderRoleControl.class);
        var orderAdjustmentControl = Session.getModelController(OrderAdjustmentControl.class);
        var orderLineControl = Session.getModelController(OrderLineControl.class);

        removeOrderStatusByOrder(order);
        deleteOrderContentCatalogsByOrder(order, deletedBy);
        orderTimeControl.deleteOrderTimesByOrder(order, deletedBy);
        orderRoleControl.deleteOrderRolesByOrder(order, deletedBy);
        orderAdjustmentControl.deleteOrderAdjustmentsByOrder(order, deletedBy);
        orderLineControl.deleteOrderLinesByOrder(order, deletedBy);
        removeOrderUserVisitsByOrder(order);

        var orderDetail = order.getLastDetailForUpdate();
        var orderTypeName = orderDetail.getOrderType().getLastDetail().getOrderTypeName();
        if(orderTypeName.equals(OrderTypes.WISHLIST.name())) {
            var wishlistControl = Session.getModelController(WishlistControl.class);
            
            wishlistControl.deleteWishlistByOrder(order, deletedBy);
        }
        
        orderDetail.setThruTime(session.getStartTime());
        order.setActiveDetail(null);
        order.store();
        
        sendEvent(order.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }
    
    public void deleteOrders(List<Order> orders, BasePK deletedBy) {
        orders.forEach((order) -> 
                deleteOrder(order, deletedBy)
        );
    }
    
    public void deleteOrdersByWishlistType(WishlistType wishlistType, BasePK deletedBy) {
        var wishlistControl = Session.getModelController(WishlistControl.class);
        var wishlists = wishlistControl.getWishlistsByWishlistTypeForUpdate(wishlistType);
        
        wishlists.forEach((wishlist) -> {
            deleteOrder(wishlist.getOrderForUpdate(), deletedBy);
        });
    }
    
    // --------------------------------------------------------------------------------
    //   Order Statuses
    // --------------------------------------------------------------------------------
    
    public OrderStatus createOrderStatus(Order order) {
        return OrderStatusFactory.getInstance().create(order, 0, 0, 0, 0);
    }
    
    private OrderStatus getOrderStatus(Order order, EntityPermission entityPermission) {
        OrderStatus orderStatus;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = """
                        SELECT _ALL_
                        FROM orderstatuses
                        WHERE ordst_ord_orderid = ?
                        """;
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = """
                        SELECT _ALL_
                        FROM orderstatuses
                        WHERE ordst_ord_orderid = ?
                        FOR UPDATE
                        """;
            }

            var ps = OrderStatusFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, order.getPrimaryKey().getEntityId());
            
            orderStatus = OrderStatusFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return orderStatus;
    }
    
    public OrderStatus getOrderStatus(Order order) {
        return getOrderStatus(order, EntityPermission.READ_ONLY);
    }
    
    public OrderStatus getOrderStatusForUpdate(Order order) {
        return getOrderStatus(order, EntityPermission.READ_WRITE);
    }
    
    public void removeOrderStatusByOrder(Order order) {
        var orderStatus = getOrderStatusForUpdate(order);
        
        if(orderStatus != null) {
            orderStatus.remove();
        }
    }
    
    // --------------------------------------------------------------------------------
    //   Order User Visits
    // --------------------------------------------------------------------------------
    
    public OrderUserVisit createOrderUserVisit(Order order, UserVisit userVisit) {
        return OrderUserVisitFactory.getInstance().create(order, userVisit, session.getStartTime(), Session.MAX_TIME);
    }

    public long countOrderUserVisitByOrder(final Order order) {
        return session.queryForLong("""
                        SELECT COUNT(*)
                        FROM orderuservisits
                        WHERE orduvis_ord_orderid = ? AND orduvis_thrutime = ?
                        """, order, Session.MAX_TIME);
    }

    public long countOrderUserVisitByUserVisit(final UserVisit userVisit) {
        return session.queryForLong("""
                        SELECT COUNT(*)
                        FROM orderuservisits
                        WHERE orduvis_uvis_uservisitid = ? AND orduvis_thrutime = ?
                        """, userVisit, Session.MAX_TIME);
    }

    private static final Map<EntityPermission, String> getOrderUserVisitQueries;
    
    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                """
                SELECT _ALL_
                FROM orderuservisits
                WHERE orduvis_ord_orderid = ? AND orduvis_uvis_uservisitid = ? AND orduvis_thrutime = ?
                """);
        queryMap.put(EntityPermission.READ_WRITE,
                """
                SELECT _ALL_
                FROM orderuservisits
                WHERE orduvis_ord_orderid = ? AND orduvis_uvis_uservisitid = ? AND orduvis_thrutime = ?
                FOR UPDATE
                """);
        getOrderUserVisitQueries = Collections.unmodifiableMap(queryMap);
    }

    private OrderUserVisit getOrderUserVisit(Order order, UserVisit userVisit, EntityPermission entityPermission) {
        return OrderUserVisitFactory.getInstance().getEntityFromQuery(entityPermission, getOrderUserVisitQueries,
                order, userVisit, Session.MAX_TIME);
    }
    
    public OrderUserVisit getOrderUserVisit(Order order, UserVisit userVisit) {
        return getOrderUserVisit(order, userVisit, EntityPermission.READ_ONLY);
    }
    
    public OrderUserVisit getOrderUserVisitForUpdate(Order order, UserVisit userVisit) {
        return getOrderUserVisit(order, userVisit, EntityPermission.READ_WRITE);
    }
    
    public OrderUserVisitValue getOrderUserVisitValue(OrderUserVisit orderUserVisit) {
        return orderUserVisit == null? null: orderUserVisit.getOrderUserVisitValue().clone();
    }
    
    public OrderUserVisitValue getOrderUserVisitValueForUpdate(Order order, UserVisit userVisit) {
        return getOrderUserVisitValue(getOrderUserVisitForUpdate(order, userVisit));
    }
    
    private static final Map<EntityPermission, String> getOrderUserVisitsByOrderQueries;
    
    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                """
                SELECT _ALL_
                FROM orderuservisits, uservisits
                WHERE orduvis_ord_orderid = ? AND orduvis_thrutime = ?
                AND orduvis_uvis_uservisitid = uvis_uservisitid
                ORDER BY uvis_fromtime
                _LIMIT_
                """);
        queryMap.put(EntityPermission.READ_WRITE,
                """
                SELECT _ALL_
                FROM orderuservisits
                WHERE orduvis_ord_orderid = ? AND orduvis_thrutime = ?
                FOR UPDATE
                """);
        getOrderUserVisitsByOrderQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<OrderUserVisit> getOrderUserVisitsByOrder(Order order, EntityPermission entityPermission) {
        return OrderUserVisitFactory.getInstance().getEntitiesFromQuery(entityPermission, getOrderUserVisitsByOrderQueries,
                order, Session.MAX_TIME);
    }
    
    public List<OrderUserVisit> getOrderUserVisitsByOrder(Order order) {
        return getOrderUserVisitsByOrder(order, EntityPermission.READ_ONLY);
    }
    
    public List<OrderUserVisit> getOrderUserVisitsByOrderForUpdate(Order order) {
        return getOrderUserVisitsByOrder(order, EntityPermission.READ_WRITE);
    }
    
    private static final Map<EntityPermission, String> getOrderUserVisitsByUserVisitQueries;
    
    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                """
                SELECT _ALL_
                FROM orderuservisits, orders, orderdetails
                WHERE orduvis_ord_orderid = ? AND orduvis_thrutime = ?
                AND orduvis_ord_orderid = ord_orderid AND ord_lastdetailid = orddt_orderdetailid
                ORDER BY orddt_ordername
                _LIMIT_
                """);
        queryMap.put(EntityPermission.READ_WRITE,
                """
                SELECT _ALL_
                FROM orderuservisits
                WHERE orduvis_ord_orderid = ? AND orduvis_thrutime = ?
                FOR UPDATE
                """);
        getOrderUserVisitsByUserVisitQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<OrderUserVisit> getOrderUserVisitsByUserVisit(UserVisit userVisit, EntityPermission entityPermission) {
        return OrderUserVisitFactory.getInstance().getEntitiesFromQuery(entityPermission, getOrderUserVisitsByUserVisitQueries,
                userVisit, Session.MAX_TIME);
    }
    
    public List<OrderUserVisit> getOrderUserVisitsByUserVisit(UserVisit userVisit) {
        return getOrderUserVisitsByUserVisit(userVisit, EntityPermission.READ_ONLY);
    }
    
    public List<OrderUserVisit> getOrderUserVisitsByUserVisitForUpdate(UserVisit userVisit) {
        return getOrderUserVisitsByUserVisit(userVisit, EntityPermission.READ_WRITE);
    }
    
    public void deleteOrderUserVisit(OrderUserVisit orderUserVisit) {
        orderUserVisit.setThruTime(session.getStartTime());
        orderUserVisit.store();
    }
    
    public void deleteOrderUserVisits(List<OrderUserVisit> orderUserVisits) {
        orderUserVisits.forEach((orderUserVisit) -> {
            deleteOrderUserVisit(orderUserVisit);
        });
    }
    
    public void deleteOrderUserVisitsByOrder(Order order) {
        deleteOrderUserVisits(getOrderUserVisitsByOrderForUpdate(order));
    }
    
    public void deleteOrderUserVisitsByUserVisit(UserVisit userVisit) {
        deleteOrderUserVisits(getOrderUserVisitsByUserVisitForUpdate(userVisit));
    }
    
    public void removeOrderUserVisit(OrderUserVisit orderUserVisit) {
        orderUserVisit.remove();
    }
    
    public void removeOrderUserVisits(List<OrderUserVisit> orderUserVisits) {
        orderUserVisits.forEach((orderUserVisit) -> {
            removeOrderUserVisit(orderUserVisit);
        });
    }
    
    public void removeOrderUserVisitsByOrder(Order order) {
        removeOrderUserVisits(getOrderUserVisitsByOrderForUpdate(order));
    }
    
    public void removeOrderUserVisitsByUserVisit(UserVisit userVisit) {
        removeOrderUserVisits(getOrderUserVisitsByUserVisitForUpdate(userVisit));
    }

    // --------------------------------------------------------------------------------
    //   Order Content Catalog
    // --------------------------------------------------------------------------------
    
    @Inject
    OrderContentCatalogFactory orderContentCatalogFactory;

    @Inject
    OrderContentCatalogTransferCache orderContentCatalogTransferCache;

    public OrderContentCatalog createOrderContentCatalog(Order order, ContentCatalog contentCatalog, BasePK createdBy) {
        var orderContentCatalog = orderContentCatalogFactory.create(order, contentCatalog, session.getStartTime(), Session.MAX_TIME);

        sendEvent(order.getPrimaryKey(), EventTypes.MODIFY, orderContentCatalog.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return orderContentCatalog;
    }

    public long countOrderContentCatalogsByOrder(Order order) {
        return session.queryForLong("""
                        SELECT COUNT(*)
                        FROM ordercontentcatalogs
                        WHERE ordcntct_ord_orderid = ? AND ordcntct_thrutime = ?
                        """, order, Session.MAX_TIME);
    }

    public long countOrderContentCatalogsByContentCatalog(ContentCatalog contentCatalog) {
        return session.queryForLong("""
                        SELECT COUNT(*)
                        FROM ordercontentcatalogs
                        WHERE ordcntct_cntct_contentcatalogid = ? AND ordcntct_thrutime = ?
                        """, contentCatalog, Session.MAX_TIME);
    }

    public boolean orderContentCatalogExists(Order order, ContentCatalog contentCatalog) {
        return session.queryForLong("""
                        SELECT COUNT(*)
                        FROM ordercontentcatalogs
                        WHERE ordcntct_ord_orderid = ? AND ordcntct_cntct_contentcatalogid = ? AND ordcntct_thrutime = ?
                        """, order, contentCatalog, Session.MAX_TIME) == 1;
    }

    private static final Map<EntityPermission, String> getOrderContentCatalogQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY, """
                        SELECT _ALL_
                        FROM ordercontentcatalogs
                        WHERE ordcntct_ord_orderid = ? AND ordcntct_cntct_contentcatalogid = ? AND ordcntct_thrutime = ?
                        """);
        queryMap.put(EntityPermission.READ_WRITE, """
                        SELECT _ALL_
                        FROM ordercontentcatalogs
                        WHERE ordcntct_ord_orderid = ? AND ordcntct_cntct_contentcatalogid = ? AND ordcntct_thrutime = ?
                        FOR UPDATE
                        """);
        getOrderContentCatalogQueries = Collections.unmodifiableMap(queryMap);
    }

    private OrderContentCatalog getOrderContentCatalog(Order order, ContentCatalog contentCatalog, EntityPermission entityPermission) {
        return orderContentCatalogFactory.getEntityFromQuery(entityPermission, getOrderContentCatalogQueries, order, contentCatalog, Session.MAX_TIME);
    }

    public OrderContentCatalog getOrderContentCatalog(Order order, ContentCatalog contentCatalog) {
        return getOrderContentCatalog(order, contentCatalog, EntityPermission.READ_ONLY);
    }

    public OrderContentCatalog getOrderContentCatalogForUpdate(Order order, ContentCatalog contentCatalog) {
        return getOrderContentCatalog(order, contentCatalog, EntityPermission.READ_WRITE);
    }

    public OrderContentCatalogValue getOrderContentCatalogValue(OrderContentCatalog orderContentCatalog) {
        return orderContentCatalog == null? null: orderContentCatalog.getOrderContentCatalogValue().clone();
    }

    public OrderContentCatalogValue getOrderContentCatalogValueForUpdate(Order order, ContentCatalog contentCatalog) {
        return getOrderContentCatalogValue(getOrderContentCatalogForUpdate(order, contentCatalog));
    }

    private static final Map<EntityPermission, String> getOrderContentCatalogsByOrderQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY, """
                        SELECT _ALL_
                        FROM ordercontentcatalogs
                        JOIN contentcatalogs ON cntct_contentcatalogid = ordcntct_cntct_contentcatalogid
                        JOIN contentcatalogdetails ON cntctdt_contentcatalogdetailid = cntct_lastdetailid
                        JOIN contentcollections ON cntc_contentcollectionid = cntctdt_cntc_contentcollectionid
                        JOIN contentcollectiondetails ON cntcdt_contentcollectiondetailid = cntc_lastdetailid
                        WHERE ordcntct_cntct_contentcatalogid = ? AND ordcntct_thrutime = ?
                        ORDER BY cntcdt_contentcollectionname, cntctdt_sortorder, cntctdt_contentcatalogname
                        _LIMIT_
                        """);
        queryMap.put(EntityPermission.READ_WRITE, """
                        SELECT _ALL_
                        FROM ordercontentcatalogs
                        WHERE ordcntct_cntct_contentcatalogid = ? AND ordcntct_thrutime = ?
                        FOR UPDATE
                        """);
        getOrderContentCatalogsByOrderQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<OrderContentCatalog> getOrderContentCatalogsByOrder(Order order, EntityPermission entityPermission) {
        return orderContentCatalogFactory.getEntitiesFromQuery(entityPermission, getOrderContentCatalogsByOrderQueries, order, Session.MAX_TIME);
    }

    public List<OrderContentCatalog> getOrderContentCatalogsByOrder(Order order) {
        return getOrderContentCatalogsByOrder(order, EntityPermission.READ_ONLY);
    }

    public List<OrderContentCatalog> getOrderContentCatalogsByOrderForUpdate(Order order) {
        return getOrderContentCatalogsByOrder(order, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getOrderContentCatalogsByContentCatalogQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY, """
                        SELECT _ALL_
                        FROM ordercontentcatalogs
                        JOIN orders ON ord_orderid = ordcntct_ord_orderid
                        JOIN orderdetails ON ord_lastdetailid = orddt_orderdetailid
                        WHERE ordcntct_cntct_contentcatalogid = ? AND ordcntct_thrutime = ?
                        ORDER BY orddt_ordername
                        _LIMIT_
                        """);
        queryMap.put(EntityPermission.READ_WRITE, """
                        SELECT _ALL_
                        FROM ordercontentcatalogs
                        WHERE ordcntct_cntct_contentcatalogid = ? AND ordcntct_thrutime = ?
                        FOR UPDATE
                        """);
        getOrderContentCatalogsByContentCatalogQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<OrderContentCatalog> getOrderContentCatalogsByContentCatalog(ContentCatalog contentCatalog, EntityPermission entityPermission) {
        return orderContentCatalogFactory.getEntitiesFromQuery(entityPermission, getOrderContentCatalogsByContentCatalogQueries, contentCatalog, Session.MAX_TIME);
    }

    public List<OrderContentCatalog> getOrderContentCatalogsByContentCatalog(ContentCatalog contentCatalog) {
        return getOrderContentCatalogsByContentCatalog(contentCatalog, EntityPermission.READ_ONLY);
    }

    public List<OrderContentCatalog> getOrderContentCatalogsByContentCatalogForUpdate(ContentCatalog contentCatalog) {
        return getOrderContentCatalogsByContentCatalog(contentCatalog, EntityPermission.READ_WRITE);
    }

    public OrderContentCatalogTransfer getOrderContentCatalogTransfer(UserVisit userVisit, OrderContentCatalog orderContentCatalog) {
        return orderContentCatalogTransferCache.getOrderContentCatalogTransfer(userVisit, orderContentCatalog);
    }

    public List<OrderContentCatalogTransfer> getOrderContentCatalogTransfers(UserVisit userVisit, Collection<OrderContentCatalog> orderContentCatalogs) {
        List<OrderContentCatalogTransfer> orderContentCatalogTransfers = new ArrayList<>(orderContentCatalogs.size());

        orderContentCatalogs.forEach((orderContentCatalog) ->
                orderContentCatalogTransfers.add(orderContentCatalogTransferCache.getOrderContentCatalogTransfer(userVisit, orderContentCatalog))
        );

        return orderContentCatalogTransfers;
    }

    public List<OrderContentCatalogTransfer> getOrderContentCatalogTransfersByOrder(UserVisit userVisit, Order order) {
        return getOrderContentCatalogTransfers(userVisit, getOrderContentCatalogsByOrder(order));
    }

    public List<OrderContentCatalogTransfer> getOrderContentCatalogTransfersByContentCatalog(UserVisit userVisit, ContentCatalog contentCatalog) {
        return getOrderContentCatalogTransfers(userVisit, getOrderContentCatalogsByContentCatalog(contentCatalog));
    }

    public void deleteOrderContentCatalog(OrderContentCatalog orderContentCatalog, BasePK deletedBy) {
        orderContentCatalog.setThruTime(session.getStartTime());

        sendEvent(orderContentCatalog.getContentCatalogPK(), EventTypes.MODIFY, orderContentCatalog.getPrimaryKey(), EventTypes.DELETE, deletedBy);

    }

    public void deleteOrderContentCatalogs(List<OrderContentCatalog> orderContentCatalogs, BasePK deletedBy) {
        orderContentCatalogs.forEach((orderContentCatalog) ->
                deleteOrderContentCatalog(orderContentCatalog, deletedBy)
        );
    }

    public void deleteOrderContentCatalogsByOrder(Order order, BasePK deletedBy) {
        deleteOrderContentCatalogs(getOrderContentCatalogsByOrderForUpdate(order), deletedBy);
    }

    public void deleteOrderContentCatalogsByContentCatalog(ContentCatalog contentCatalog, BasePK deletedBy) {
        deleteOrderContentCatalogs(getOrderContentCatalogsByContentCatalogForUpdate(contentCatalog), deletedBy);
    }

}
