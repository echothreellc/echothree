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
import static com.echothree.model.jooq.server.keys.content.ContentForeignKeys.CONTENT_CATALOGS_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.content.ContentForeignKeys.CONTENT_CATALOG_DETAILS_CONTENT_COLLECTION_FK;
import static com.echothree.model.jooq.server.keys.content.ContentForeignKeys.CONTENT_COLLECTIONS_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.order.OrderForeignKeys.ORDERS_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.order.OrderForeignKeys.ORDERS_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.order.OrderForeignKeys.ORDER_CONTENT_CATALOGS_CONTENT_CATALOG_FK;
import static com.echothree.model.jooq.server.keys.order.OrderForeignKeys.ORDER_CONTENT_CATALOGS_ORDER_FK;
import static com.echothree.model.jooq.server.keys.order.OrderForeignKeys.ORDER_ROLES_ORDER_FK;
import static com.echothree.model.jooq.server.keys.order.OrderForeignKeys.ORDER_ROLES_ORDER_ROLE_TYPE_FK;
import static com.echothree.model.jooq.server.keys.order.OrderForeignKeys.ORDER_USER_VISITS_ORDER_FK;
import static com.echothree.model.jooq.server.keys.order.OrderForeignKeys.ORDER_USER_VISITS_USER_VISIT_FK;
import static com.echothree.model.jooq.server.tables.content.ContentCatalogDetails.ContentCatalogDetails;
import static com.echothree.model.jooq.server.tables.content.ContentCatalogs.ContentCatalogs;
import static com.echothree.model.jooq.server.tables.content.ContentCollectionDetails.ContentCollectionDetails;
import static com.echothree.model.jooq.server.tables.content.ContentCollections.ContentCollections;
import static com.echothree.model.jooq.server.tables.order.OrderContentCatalogs.OrderContentCatalogs;
import static com.echothree.model.jooq.server.tables.order.OrderDetails.OrderDetails;
import static com.echothree.model.jooq.server.tables.order.OrderRoleTypes.OrderRoleTypes;
import static com.echothree.model.jooq.server.tables.order.OrderRoles.OrderRoles;
import static com.echothree.model.jooq.server.tables.order.OrderStatuses.OrderStatuses;
import static com.echothree.model.jooq.server.tables.order.OrderUserVisits.OrderUserVisits;
import static com.echothree.model.jooq.server.tables.order.Orders.Orders;
import static com.echothree.model.jooq.server.tables.user.UserVisits.UserVisits;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.cdi.CommandScope;
import com.echothree.util.server.control.BaseModelControl;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.inject.Inject;

@CommandScope
public class OrderControl
        extends BaseModelControl {
    
    @Inject
    private OrderAdjustmentControl orderAdjustmentControl;

    @Inject
    protected OrderLineControl orderLineControl;

    @Inject
    private OrderRoleControl orderRoleControl;

    @Inject
    protected OrderTimeControl orderTimeControl;

    @Inject
    private OrderTypeControl orderTypeControl;

    @Inject
    protected WishlistControl wishlistControl;

    /** Creates a new instance of OrderControl */
    protected OrderControl() {
        super();
    }

    // --------------------------------------------------------------------------------
    //   Orders
    // --------------------------------------------------------------------------------

    @Inject
    protected OrderDetailFactory orderDetailFactory;

    @Inject
    private OrderFactory orderFactory;

    public Order createOrder(OrderType orderType, String orderName, OrderPriority orderPriority, Currency currency, Boolean holdUntilComplete,
            Boolean allowBackorders, Boolean allowSubstitutions, Boolean allowCombiningShipments, Term term, FreeOnBoard freeOnBoard,
            String reference, String description, CancellationPolicy cancellationPolicy, ReturnPolicy returnPolicy, Boolean taxable,
            BasePK createdBy) {
        var order = orderFactory.create();
        var orderDetail = orderDetailFactory.create(order, orderType, orderName, orderPriority,
                currency, holdUntilComplete, allowBackorders, allowSubstitutions, allowCombiningShipments, term,
                freeOnBoard, reference, description, cancellationPolicy, returnPolicy, taxable, session.getStartTime(),
                Session.MAX_TIME);
        
        // Convert to R/W
        order = orderFactory.getEntityFromPK(EntityPermission.READ_WRITE, order.getPrimaryKey());
        order.setActiveDetail(orderDetail);
        order.setLastDetail(orderDetail);
        order.store();
        
        sendEvent(order.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        createOrderStatus(order);
        
        return order;
    }
    
    public Order getOrderByPK(OrderPK orderPK) {
        return orderFactory.getEntityFromPK(EntityPermission.READ_ONLY, orderPK);
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.Order */
    public Order getOrderByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new OrderPK(entityInstance.getEntityUniqueId());

        return orderFactory.getEntityFromPK(entityPermission, pk);
    }

    public Order getOrderByEntityInstance(EntityInstance entityInstance) {
        return getOrderByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public Order getOrderByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getOrderByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countOrdersByOrderType(final OrderType orderType) {
        return session.getDslContext()
                .selectCount()
                .from(Orders)
                .join(OrderDetails).onKey(ORDERS_ACTIVE_DETAIL_FK)
                .where(OrderDetails.ORDER_TYPE.eq(orderType.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countOrdersByOrderPriority(final OrderPriority orderPriority) {
        return session.getDslContext()
                .selectCount()
                .from(Orders)
                .join(OrderDetails).onKey(ORDERS_ACTIVE_DETAIL_FK)
                .where(OrderDetails.ORDER_PRIORITY.eq(orderPriority.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countOrdersByCancellationPolicy(final CancellationPolicy cancellationPolicy) {
        return session.getDslContext()
                .selectCount()
                .from(Orders)
                .join(OrderDetails).onKey(ORDERS_ACTIVE_DETAIL_FK)
                .where(OrderDetails.CANCELLATION_POLICY.eq(cancellationPolicy.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countOrdersByReturnPolicy(final ReturnPolicy returnPolicy) {
        return session.getDslContext()
                .selectCount()
                .from(Orders)
                .join(OrderDetails).onKey(ORDERS_ACTIVE_DETAIL_FK)
                .where(OrderDetails.RETURN_POLICY.eq(returnPolicy.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public Order getOrderByName(OrderType orderType, String orderName, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(Orders.fields())
                .from(Orders)
                .join(OrderDetails).onKey(ORDERS_ACTIVE_DETAIL_FK)
                .where(OrderDetails.ORDER_TYPE.eq(orderType.getPrimaryKey()),
                        OrderDetails.ORDER_NAME.eq(orderName));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return orderFactory.getEntityFromQuery(entityPermission, query);
    }

    private Order getOrderByNameUsingNames(String orderTypeName, String orderName, EntityPermission entityPermission) {

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
        return session.getDslContext()
                .selectCount()
                .from(Orders)
                .join(OrderDetails).onKey(ORDERS_ACTIVE_DETAIL_FK)
                .join(OrderRoles).onKey(ORDER_ROLES_ORDER_FK)
                .join(OrderRoleTypes).onKey(ORDER_ROLES_ORDER_ROLE_TYPE_FK)
                .where(OrderDetails.REFERENCE.eq(reference),
                        OrderRoles.THRU_TIME.eq(Session.MAX_TIME),
                        OrderRoles.PARTY.eq(billToParty.getPrimaryKey()),
                        OrderRoleTypes.ORDER_ROLE_TYPE_NAME.eq(com.echothree.model.control.order.common.OrderRoleTypes.BILL_TO.name()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public OrderTransfer getOrderTransfer(UserVisit userVisit, Order order) {
        // TODO
        return null;
    }

    public void updateOrderFromValue(OrderDetailValue orderDetailValue, BasePK updatedBy) {
        if(orderDetailValue.hasBeenModified()) {
            var order = orderFactory.getEntityFromPK(EntityPermission.READ_WRITE, orderDetailValue.getOrderPK());
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
            
            orderDetail = orderDetailFactory.create(orderPK, orderTypePK, orderName, orderPriorityPK, currencyPK, holdUntilComplete,
                    allowBackorders, allowSubstitutions, allowCombiningShipments, termPK, freeOnBoardPK, reference, description, cancellationPolicyPK,
                    returnPolicyPK, taxable, session.getStartTime(), Session.MAX_TIME);
            
            order.setActiveDetail(orderDetail);
            order.setLastDetail(orderDetail);
            
            sendEvent(orderPK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }
    
    public void deleteOrder(Order order, BasePK deletedBy) {

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
        var wishlists = wishlistControl.getWishlistsByWishlistTypeForUpdate(wishlistType);
        
        wishlists.forEach((wishlist) -> {
            deleteOrder(wishlist.getOrderForUpdate(), deletedBy);
        });
    }
    
    // --------------------------------------------------------------------------------
    //   Order Statuses
    // --------------------------------------------------------------------------------

    @Inject
    private OrderStatusFactory orderStatusFactory;

    public OrderStatus createOrderStatus(Order order) {
        return orderStatusFactory.create(order, 0, 0, 0, 0);
    }
    
    private OrderStatus getOrderStatus(Order order, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(OrderStatuses.fields())
                .from(OrderStatuses)
                .where(OrderStatuses.ORDER.eq(order.getPrimaryKey()));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return orderStatusFactory.getEntityFromQuery(entityPermission, query);
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

    @Inject
    private OrderUserVisitFactory orderUserVisitFactory;

    public OrderUserVisit createOrderUserVisit(Order order, UserVisit userVisit) {
        return orderUserVisitFactory.create(order, userVisit, session.getStartTime(), Session.MAX_TIME);
    }

    public long countOrderUserVisitByOrder(final Order order) {
        return session.getDslContext()
                .selectCount()
                .from(OrderUserVisits)
                .where(OrderUserVisits.ORDER.eq(order.getPrimaryKey()),
                        OrderUserVisits.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countOrderUserVisitByUserVisit(final UserVisit userVisit) {
        return session.getDslContext()
                .selectCount()
                .from(OrderUserVisits)
                .where(OrderUserVisits.USER_VISIT.eq(userVisit.getPrimaryKey()),
                        OrderUserVisits.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private OrderUserVisit getOrderUserVisit(Order order, UserVisit userVisit, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(OrderUserVisits.fields())
                .from(OrderUserVisits)
                .where(OrderUserVisits.ORDER.eq(order.getPrimaryKey()),
                        OrderUserVisits.USER_VISIT.eq(userVisit.getPrimaryKey()),
                        OrderUserVisits.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return orderUserVisitFactory.getEntityFromQuery(entityPermission, query);
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
    
    private List<OrderUserVisit> getOrderUserVisitsByOrder(Order order, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(OrderUserVisits.fields())
                    .from(OrderUserVisits)
                    .join(UserVisits).onKey(ORDER_USER_VISITS_USER_VISIT_FK)
                    .where(OrderUserVisits.ORDER.eq(order.getPrimaryKey()),
                            OrderUserVisits.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(UserVisits.FROM_TIME),
                    OrderUserVisitFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(OrderUserVisits.fields())
                    .from(OrderUserVisits)
                    .where(OrderUserVisits.ORDER.eq(order.getPrimaryKey()),
                            OrderUserVisits.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return orderUserVisitFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<OrderUserVisit> getOrderUserVisitsByOrder(Order order) {
        return getOrderUserVisitsByOrder(order, EntityPermission.READ_ONLY);
    }
    
    public List<OrderUserVisit> getOrderUserVisitsByOrderForUpdate(Order order) {
        return getOrderUserVisitsByOrder(order, EntityPermission.READ_WRITE);
    }
    
    private List<OrderUserVisit> getOrderUserVisitsByUserVisit(UserVisit userVisit, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(OrderUserVisits.fields())
                    .from(OrderUserVisits)
                    .join(Orders).onKey(ORDER_USER_VISITS_ORDER_FK)
                    .join(OrderDetails).onKey(ORDERS_LAST_DETAIL_FK)
                    .where(OrderUserVisits.USER_VISIT.eq(userVisit.getPrimaryKey()),
                            OrderUserVisits.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(OrderDetails.ORDER_NAME),
                    OrderUserVisitFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(OrderUserVisits.fields())
                    .from(OrderUserVisits)
                    .where(OrderUserVisits.USER_VISIT.eq(userVisit.getPrimaryKey()),
                            OrderUserVisits.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return orderUserVisitFactory.getEntitiesFromQuery(entityPermission, query);
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
    private OrderContentCatalogFactory orderContentCatalogFactory;

    @Inject
    private OrderContentCatalogTransferCache orderContentCatalogTransferCache;

    public OrderContentCatalog createOrderContentCatalog(Order order, ContentCatalog contentCatalog, BasePK createdBy) {
        var orderContentCatalog = orderContentCatalogFactory.create(order, contentCatalog, session.getStartTime(), Session.MAX_TIME);

        sendEvent(order.getPrimaryKey(), EventTypes.MODIFY, orderContentCatalog.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return orderContentCatalog;
    }

    public long countOrderContentCatalogsByOrder(Order order) {
        return session.getDslContext()
                .selectCount()
                .from(OrderContentCatalogs)
                .where(OrderContentCatalogs.ORDER.eq(order.getPrimaryKey()),
                        OrderContentCatalogs.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countOrderContentCatalogsByContentCatalog(ContentCatalog contentCatalog) {
        return session.getDslContext()
                .selectCount()
                .from(OrderContentCatalogs)
                .where(OrderContentCatalogs.CONTENT_CATALOG.eq(contentCatalog.getPrimaryKey()),
                        OrderContentCatalogs.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public boolean orderContentCatalogExists(Order order, ContentCatalog contentCatalog) {
        return session.getDslContext()
                .selectCount()
                .from(OrderContentCatalogs)
                .where(OrderContentCatalogs.ORDER.eq(order.getPrimaryKey()),
                        OrderContentCatalogs.CONTENT_CATALOG.eq(contentCatalog.getPrimaryKey()),
                        OrderContentCatalogs.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L) == 1L;
    }

    private OrderContentCatalog getOrderContentCatalog(Order order, ContentCatalog contentCatalog, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(OrderContentCatalogs.fields())
                .from(OrderContentCatalogs)
                .where(OrderContentCatalogs.ORDER.eq(order.getPrimaryKey()),
                        OrderContentCatalogs.CONTENT_CATALOG.eq(contentCatalog.getPrimaryKey()),
                        OrderContentCatalogs.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return orderContentCatalogFactory.getEntityFromQuery(entityPermission, query);
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

    private List<OrderContentCatalog> getOrderContentCatalogsByOrder(Order order, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(OrderContentCatalogs.fields())
                    .from(OrderContentCatalogs)
                    .join(ContentCatalogs).onKey(ORDER_CONTENT_CATALOGS_CONTENT_CATALOG_FK)
                    .join(ContentCatalogDetails).onKey(CONTENT_CATALOGS_LAST_DETAIL_FK)
                    .join(ContentCollections).onKey(CONTENT_CATALOG_DETAILS_CONTENT_COLLECTION_FK)
                    .join(ContentCollectionDetails).onKey(CONTENT_COLLECTIONS_LAST_DETAIL_FK)
                    .where(OrderContentCatalogs.ORDER.eq(order.getPrimaryKey()),
                            OrderContentCatalogs.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(ContentCollectionDetails.CONTENT_COLLECTION_NAME, ContentCatalogDetails.SORT_ORDER, ContentCatalogDetails.CONTENT_CATALOG_NAME),
                    OrderContentCatalogFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(OrderContentCatalogs.fields())
                    .from(OrderContentCatalogs)
                    .where(OrderContentCatalogs.ORDER.eq(order.getPrimaryKey()),
                            OrderContentCatalogs.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return orderContentCatalogFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<OrderContentCatalog> getOrderContentCatalogsByOrder(Order order) {
        return getOrderContentCatalogsByOrder(order, EntityPermission.READ_ONLY);
    }

    public List<OrderContentCatalog> getOrderContentCatalogsByOrderForUpdate(Order order) {
        return getOrderContentCatalogsByOrder(order, EntityPermission.READ_WRITE);
    }

    private List<OrderContentCatalog> getOrderContentCatalogsByContentCatalog(ContentCatalog contentCatalog, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(OrderContentCatalogs.fields())
                    .from(OrderContentCatalogs)
                    .join(Orders).onKey(ORDER_CONTENT_CATALOGS_ORDER_FK)
                    .join(OrderDetails).onKey(ORDERS_LAST_DETAIL_FK)
                    .where(OrderContentCatalogs.CONTENT_CATALOG.eq(contentCatalog.getPrimaryKey()),
                            OrderContentCatalogs.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(OrderDetails.ORDER_NAME),
                    OrderContentCatalogFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(OrderContentCatalogs.fields())
                    .from(OrderContentCatalogs)
                    .where(OrderContentCatalogs.CONTENT_CATALOG.eq(contentCatalog.getPrimaryKey()),
                            OrderContentCatalogs.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return orderContentCatalogFactory.getEntitiesFromQuery(entityPermission, query);
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
