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
import com.echothree.model.control.order.common.transfer.OrderShipmentGroupTransfer;
import com.echothree.model.data.batch.server.entity.Batch;
import com.echothree.model.data.contact.server.entity.PartyContactMechanism;
import com.echothree.model.data.item.server.entity.ItemDeliveryType;
import com.echothree.model.data.order.server.entity.Order;
import com.echothree.model.data.order.server.entity.OrderBatch;
import com.echothree.model.data.order.server.entity.OrderShipmentGroup;
import com.echothree.model.data.order.server.factory.OrderShipmentGroupDetailFactory;
import com.echothree.model.data.order.server.factory.OrderShipmentGroupFactory;
import com.echothree.model.data.order.server.value.OrderBatchValue;
import com.echothree.model.data.order.server.value.OrderShipmentGroupDetailValue;
import com.echothree.model.data.shipping.server.entity.ShippingMethod;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class OrderShipmentGroupControl
        extends BaseOrderControl {

    /** Creates a new instance of OrderControl */
    public OrderShipmentGroupControl() {
        super();
    }

    // --------------------------------------------------------------------------------
    //   Order Shipment Groups
    // --------------------------------------------------------------------------------
    
    public OrderShipmentGroup createOrderShipmentGroup(Order order, Integer orderShipmentGroupSequence, ItemDeliveryType itemDeliveryType, Boolean isDefault,
            PartyContactMechanism partyContactMechanism, ShippingMethod shippingMethod, Boolean holdUntilComplete, BasePK createdBy) {
        var defaultOrderShipmentGroup = getDefaultOrderShipmentGroup(order, itemDeliveryType);
        var defaultFound = defaultOrderShipmentGroup != null;

        if(defaultFound && isDefault) {
            var defaultOrderShipmentGroupDetailValue = getDefaultOrderShipmentGroupDetailValueForUpdate(order, itemDeliveryType);

            defaultOrderShipmentGroupDetailValue.setIsDefault(false);
            updateOrderShipmentGroupFromValue(defaultOrderShipmentGroupDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var orderShipmentGroup = OrderShipmentGroupFactory.getInstance().create();
        var orderShipmentGroupDetail = OrderShipmentGroupDetailFactory.getInstance().create(orderShipmentGroup, order,
                orderShipmentGroupSequence, itemDeliveryType, isDefault, partyContactMechanism, shippingMethod, holdUntilComplete, session.START_TIME_LONG,
                Session.MAX_TIME_LONG);
        
        // Convert to R/W
        orderShipmentGroup = OrderShipmentGroupFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, orderShipmentGroup.getPrimaryKey());
        orderShipmentGroup.setActiveDetail(orderShipmentGroupDetail);
        orderShipmentGroup.setLastDetail(orderShipmentGroupDetail);
        orderShipmentGroup.store();
        
        sendEvent(orderShipmentGroup.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return orderShipmentGroup;
    }
    
    private static final Map<EntityPermission, String> getOrderShipmentGroupBySequenceQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM ordershipmentgroups, ordershipmentgroupdetails " +
                "WHERE ordshpgrp_activedetailid = ordshpgrpdt_ordershipmentgroupdetailid " +
                "AND ordshpgrpdt_ord_orderid = ? AND ordshpgrpdt_ordershipmentgroupsequence = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM ordershipmentgroups, ordershipmentgroupdetails " +
                "WHERE ordshpgrp_activedetailid = ordshpgrpdt_ordershipmentgroupdetailid " +
                "AND ordshpgrpdt_ord_orderid = ? AND ordshpgrpdt_ordershipmentgroupsequence = ? " +
                "FOR UPDATE");
        getOrderShipmentGroupBySequenceQueries = Collections.unmodifiableMap(queryMap);
    }

    public OrderShipmentGroup getOrderShipmentGroupBySequence(Order order, Integer orderShipmentGroupSequence, EntityPermission entityPermission) {
        return OrderShipmentGroupFactory.getInstance().getEntityFromQuery(entityPermission, getOrderShipmentGroupBySequenceQueries,
                order, orderShipmentGroupSequence);
    }

    public OrderShipmentGroup getOrderShipmentGroupBySequence(Order order, Integer orderShipmentGroupSequence) {
        return getOrderShipmentGroupBySequence(order, orderShipmentGroupSequence, EntityPermission.READ_ONLY);
    }

    public OrderShipmentGroup getOrderShipmentGroupBySequenceForUpdate(Order order, Integer orderShipmentGroupSequence) {
        return getOrderShipmentGroupBySequence(order, orderShipmentGroupSequence, EntityPermission.READ_WRITE);
    }

    public OrderShipmentGroupDetailValue getOrderShipmentGroupDetailValueForUpdate(OrderShipmentGroup orderShipmentGroup) {
        return orderShipmentGroup == null? null: orderShipmentGroup.getLastDetailForUpdate().getOrderShipmentGroupDetailValue().clone();
    }

    public OrderShipmentGroupDetailValue getOrderShipmentGroupDetailValueBySequenceForUpdate(Order order, Integer orderShipmentGroupSequence) {
        return getOrderShipmentGroupDetailValueForUpdate(getOrderShipmentGroupBySequenceForUpdate(order, orderShipmentGroupSequence));
    }

    private static final Map<EntityPermission, String> getDefaultOrderShipmentGroupQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM ordershipmentgroups, ordershipmentgroupdetails " +
                "WHERE ordshpgrp_activedetailid = ordshpgrpdt_ordershipmentgroupdetailid " +
                "AND ordshpgrpdt_ord_orderid = ? AND ordshpgrpdt_idlvrtyp_itemdeliverytypeid = ? AND ordshpgrpdt_isdefault = 1");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM ordershipmentgroups, ordershipmentgroupdetails " +
                "WHERE ordshpgrp_activedetailid = ordshpgrpdt_ordershipmentgroupdetailid " +
                "AND ordshpgrpdt_ord_orderid = ? AND ordshpgrpdt_idlvrtyp_itemdeliverytypeid = ? AND ordshpgrpdt_isdefault = 1 " +
                "FOR UPDATE");
        getDefaultOrderShipmentGroupQueries = Collections.unmodifiableMap(queryMap);
    }

    public OrderShipmentGroup getDefaultOrderShipmentGroup(Order order, ItemDeliveryType itemDeliveryType, EntityPermission entityPermission) {
        return OrderShipmentGroupFactory.getInstance().getEntityFromQuery(entityPermission, getDefaultOrderShipmentGroupQueries,
                order, itemDeliveryType);
    }

    public OrderShipmentGroup getDefaultOrderShipmentGroup(Order order, ItemDeliveryType itemDeliveryType) {
        return getDefaultOrderShipmentGroup(order, itemDeliveryType, EntityPermission.READ_ONLY);
    }

    public OrderShipmentGroup getDefaultOrderShipmentGroupForUpdate(Order order, ItemDeliveryType itemDeliveryType) {
        return getDefaultOrderShipmentGroup(order, itemDeliveryType, EntityPermission.READ_WRITE);
    }

    public OrderShipmentGroupDetailValue getDefaultOrderShipmentGroupDetailValueForUpdate(Order order, ItemDeliveryType itemDeliveryType) {
        return getDefaultOrderShipmentGroupForUpdate(order, itemDeliveryType).getLastDetailForUpdate().getOrderShipmentGroupDetailValue().clone();
    }

    private static final Map<EntityPermission, String> getOrderShipmentGroupsByOrderQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM ordershipmentgroups, ordershipmentgroupdetails " +
                "WHERE ordshpgrp_activedetailid = ordshpgrpdt_ordershipmentgroupdetailid " +
                "AND ordshpgrpdt_ord_orderid = ? " +
                "ORDER BY ordshpgrpdt_ordershipmentgroupsequence");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM ordershipmentgroups, ordershipmentgroupdetails " +
                "WHERE ordshpgrp_activedetailid = ordshpgrpdt_ordershipmentgroupdetailid " +
                "AND ordshpgrpdt_ord_orderid = ? " +
                "FOR UPDATE");
        getOrderShipmentGroupsByOrderQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<OrderShipmentGroup> getOrderShipmentGroupsByOrder(Order order, EntityPermission entityPermission) {
        return OrderShipmentGroupFactory.getInstance().getEntitiesFromQuery(entityPermission, getOrderShipmentGroupsByOrderQueries,
                order);
    }

    public List<OrderShipmentGroup> getOrderShipmentGroupsByOrder(Order order) {
        return getOrderShipmentGroupsByOrder(order, EntityPermission.READ_ONLY);
    }

    public List<OrderShipmentGroup> getOrderShipmentGroupsByOrderForUpdate(Order order) {
        return getOrderShipmentGroupsByOrder(order, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getOrderShipmentGroupsQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM ordershipmentgroups, ordershipmentgroupdetails " +
                "WHERE ordshpgrp_activedetailid = ordshpgrpdt_ordershipmentgroupdetailid " +
                "AND ordshpgrpdt_ord_orderid = ? AND ordshpgrpdt_idlvrtyp_itemdeliverytypeid = ? " +
                "ORDER BY ordshpgrpdt_ordershipmentgroupsequence");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM ordershipmentgroups, ordershipmentgroupdetails " +
                "WHERE ordshpgrp_activedetailid = ordshpgrpdt_ordershipmentgroupdetailid " +
                "AND ordshpgrpdt_ord_orderid = ? AND ordshpgrpdt_idlvrtyp_itemdeliverytypeid = ? " +
                "FOR UPDATE");
        getOrderShipmentGroupsQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<OrderShipmentGroup> getOrderShipmentGroups(Order order, ItemDeliveryType itemDeliveryType, EntityPermission entityPermission) {
        return OrderShipmentGroupFactory.getInstance().getEntitiesFromQuery(entityPermission, getOrderShipmentGroupsQueries,
                order, itemDeliveryType);
    }

    public List<OrderShipmentGroup> getOrderShipmentGroups(Order order, ItemDeliveryType itemDeliveryType) {
        return getOrderShipmentGroups(order, itemDeliveryType, EntityPermission.READ_ONLY);
    }

    public List<OrderShipmentGroup> getOrderShipmentGroupsForUpdate(Order order, ItemDeliveryType itemDeliveryType) {
        return getOrderShipmentGroups(order, itemDeliveryType, EntityPermission.READ_WRITE);
    }

    public OrderShipmentGroupTransfer getOrderShipmentGroupTransfer(UserVisit userVisit, OrderShipmentGroup orderShipmentGroup) {
        return getOrderTransferCaches(userVisit).getOrderShipmentGroupTransferCache().getOrderShipmentGroupTransfer(orderShipmentGroup);
    }

    public List<OrderShipmentGroupTransfer> getOrderShipmentGroupTransfers(UserVisit userVisit, Collection<OrderShipmentGroup> orderShipmentGroups) {
        List<OrderShipmentGroupTransfer> orderShipmentGroupTransfers = new ArrayList<>(orderShipmentGroups.size());
        var orderShipmentGroupTransferCache = getOrderTransferCaches(userVisit).getOrderShipmentGroupTransferCache();

        orderShipmentGroups.forEach((orderShipmentGroup) ->
                orderShipmentGroupTransfers.add(orderShipmentGroupTransferCache.getOrderShipmentGroupTransfer(orderShipmentGroup))
        );

        return orderShipmentGroupTransfers;
    }

    public List<OrderShipmentGroupTransfer> getOrderShipmentGroupTransfers(UserVisit userVisit, Order order) {
        return getOrderShipmentGroupTransfers(userVisit, getOrderShipmentGroupsByOrder(order));
    }

    private void updateOrderShipmentGroupFromValue(OrderShipmentGroupDetailValue orderShipmentGroupDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(orderShipmentGroupDetailValue.hasBeenModified()) {
            var orderShipmentGroup = OrderShipmentGroupFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     orderShipmentGroupDetailValue.getOrderShipmentGroupPK());
            var orderShipmentGroupDetail = orderShipmentGroup.getActiveDetailForUpdate();

            orderShipmentGroupDetail.setThruTime(session.START_TIME_LONG);
            orderShipmentGroupDetail.store();

            var orderShipmentGroupPK = orderShipmentGroupDetail.getOrderShipmentGroupPK(); // Not updated
            var order = orderShipmentGroupDetail.getOrder(); // Not updated
            var orderPK = order.getPrimaryKey(); // Not updated
            var orderShipmentGroupSequence = orderShipmentGroupDetail.getOrderShipmentGroupSequence(); // Not updated
            var itemDeliveryType = orderShipmentGroupDetail.getItemDeliveryType(); // Not updated
            var itemDeliveryTypePK = itemDeliveryType.getPrimaryKey(); // Not updated
            var isDefault = orderShipmentGroupDetailValue.getIsDefault();
            var partyContactMechanismPK = orderShipmentGroupDetailValue.getPartyContactMechanismPK();
            var shippingMethodPK = orderShipmentGroupDetailValue.getShippingMethodPK();
            var holdUntilComplete = orderShipmentGroupDetailValue.getHoldUntilComplete();

            if(checkDefault) {
                var defaultOrderShipmentGroup = getDefaultOrderShipmentGroup(order, itemDeliveryType);
                var defaultFound = defaultOrderShipmentGroup != null && !defaultOrderShipmentGroup.equals(orderShipmentGroup);

                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultOrderShipmentGroupDetailValue = getDefaultOrderShipmentGroupDetailValueForUpdate(order, itemDeliveryType);

                    defaultOrderShipmentGroupDetailValue.setIsDefault(false);
                    updateOrderShipmentGroupFromValue(defaultOrderShipmentGroupDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }

            orderShipmentGroupDetail = OrderShipmentGroupDetailFactory.getInstance().create(orderShipmentGroupPK, orderPK, orderShipmentGroupSequence,
                    itemDeliveryTypePK, isDefault, partyContactMechanismPK, shippingMethodPK, holdUntilComplete, session.START_TIME_LONG, Session.MAX_TIME_LONG);

            orderShipmentGroup.setActiveDetail(orderShipmentGroupDetail);
            orderShipmentGroup.setLastDetail(orderShipmentGroupDetail);

            sendEvent(orderShipmentGroupPK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }

    public void updateOrderShipmentGroupFromValue(OrderShipmentGroupDetailValue orderShipmentGroupDetailValue, BasePK updatedBy) {
        updateOrderShipmentGroupFromValue(orderShipmentGroupDetailValue, true, updatedBy);
    }

    public void deleteOrderShipmentGroup(OrderShipmentGroup orderShipmentGroup, BasePK deletedBy) {
        var orderLineControl = Session.getModelController(OrderLineControl.class);

        orderLineControl.deleteOrderLinesByOrderShipmentGroup(orderShipmentGroup, deletedBy);

        var orderShipmentGroupDetail = orderShipmentGroup.getLastDetailForUpdate();
        orderShipmentGroupDetail.setThruTime(session.START_TIME_LONG);
        orderShipmentGroup.setActiveDetail(null);
        orderShipmentGroup.store();

        // Check for default, and pick one if necessary
        var order = orderShipmentGroupDetail.getOrder();
        var itemDeliveryType = orderShipmentGroupDetail.getItemDeliveryType();
        var defaultOrderShipmentGroup = getDefaultOrderShipmentGroup(order, itemDeliveryType);
        if(defaultOrderShipmentGroup == null) {
            var orderShipmentGroups = getOrderShipmentGroupsForUpdate(order, itemDeliveryType);

            if(!orderShipmentGroups.isEmpty()) {
                var iter = orderShipmentGroups.iterator();
                if(iter.hasNext()) {
                    defaultOrderShipmentGroup = iter.next();
                }
                var orderShipmentGroupDetailValue = Objects.requireNonNull(defaultOrderShipmentGroup).getLastDetailForUpdate().getOrderShipmentGroupDetailValue().clone();

                orderShipmentGroupDetailValue.setIsDefault(true);
                updateOrderShipmentGroupFromValue(orderShipmentGroupDetailValue, false, deletedBy);
            }
        }

        sendEvent(orderShipmentGroup.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }

    public void deleteOrderShipmentGroupsByPartyContactMechanism(PartyContactMechanism partyContactMechanism, BasePK deletedBy) {
        // TODO
    }

}
