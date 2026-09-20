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
import com.echothree.model.control.order.common.transfer.OrderShipmentGroupTransfer;
import com.echothree.model.control.order.server.transfer.OrderShipmentGroupTransferCache;
import com.echothree.model.data.contact.server.entity.PartyContactMechanism;
import com.echothree.model.data.item.server.entity.ItemDeliveryType;
import com.echothree.model.data.order.server.entity.Order;
import com.echothree.model.data.order.server.entity.OrderShipmentGroup;
import com.echothree.model.data.order.server.factory.OrderShipmentGroupDetailFactory;
import com.echothree.model.data.order.server.factory.OrderShipmentGroupFactory;
import com.echothree.model.data.order.server.value.OrderShipmentGroupDetailValue;
import com.echothree.model.data.shipping.server.entity.ShippingMethod;
import com.echothree.model.data.user.server.entity.UserVisit;
import static com.echothree.model.jooq.server.keys.order.OrderForeignKeys.ORDER_SHIPMENT_GROUPS_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.tables.order.OrderShipmentGroupDetails.OrderShipmentGroupDetails;
import static com.echothree.model.jooq.server.tables.order.OrderShipmentGroups.OrderShipmentGroups;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.cdi.CommandScope;
import com.echothree.util.server.control.BaseModelControl;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import javax.inject.Inject;

@CommandScope
public class OrderShipmentGroupControl
        extends BaseModelControl {

    @Inject
    private OrderLineControl orderLineControl;

    /** Creates a new instance of OrderControl */
    protected OrderShipmentGroupControl() {
        super();
    }

    // --------------------------------------------------------------------------------
    //   Order Shipment Groups
    // --------------------------------------------------------------------------------

    @Inject
    protected OrderShipmentGroupDetailFactory orderShipmentGroupDetailFactory;

    @Inject
    private OrderShipmentGroupFactory orderShipmentGroupFactory;

    @Inject
    private OrderShipmentGroupTransferCache orderShipmentGroupTransferCache;

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

        var orderShipmentGroup = orderShipmentGroupFactory.create();
        var orderShipmentGroupDetail = orderShipmentGroupDetailFactory.create(orderShipmentGroup, order,
                orderShipmentGroupSequence, itemDeliveryType, isDefault, partyContactMechanism, shippingMethod, holdUntilComplete, session.getStartTime(),
                Session.MAX_TIME);
        
        // Convert to R/W
        orderShipmentGroup = orderShipmentGroupFactory.getEntityFromPK(EntityPermission.READ_WRITE, orderShipmentGroup.getPrimaryKey());
        orderShipmentGroup.setActiveDetail(orderShipmentGroupDetail);
        orderShipmentGroup.setLastDetail(orderShipmentGroupDetail);
        orderShipmentGroup.store();
        
        sendEvent(orderShipmentGroup.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return orderShipmentGroup;
    }
    
    public OrderShipmentGroup getOrderShipmentGroupBySequence(Order order, Integer orderShipmentGroupSequence, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(OrderShipmentGroups.fields())
                .from(OrderShipmentGroups)
                .join(OrderShipmentGroupDetails).onKey(ORDER_SHIPMENT_GROUPS_ACTIVE_DETAIL_FK)
                .where(OrderShipmentGroupDetails.ORDER.eq(order.getPrimaryKey()),
                        OrderShipmentGroupDetails.ORDER_SHIPMENT_GROUP_SEQUENCE.eq(orderShipmentGroupSequence));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return orderShipmentGroupFactory.getEntityFromQuery(entityPermission, query);
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

    public OrderShipmentGroup getDefaultOrderShipmentGroup(Order order, ItemDeliveryType itemDeliveryType, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(OrderShipmentGroups.fields())
                .from(OrderShipmentGroups)
                .join(OrderShipmentGroupDetails).onKey(ORDER_SHIPMENT_GROUPS_ACTIVE_DETAIL_FK)
                .where(OrderShipmentGroupDetails.ORDER.eq(order.getPrimaryKey()),
                        OrderShipmentGroupDetails.ITEM_DELIVERY_TYPE.eq(itemDeliveryType.getPrimaryKey()),
                        OrderShipmentGroupDetails.IS_DEFAULT.eq(true));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return orderShipmentGroupFactory.getEntityFromQuery(entityPermission, query);
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

    private List<OrderShipmentGroup> getOrderShipmentGroupsByOrder(Order order, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(OrderShipmentGroups.fields())
                .from(OrderShipmentGroups)
                .join(OrderShipmentGroupDetails).onKey(ORDER_SHIPMENT_GROUPS_ACTIVE_DETAIL_FK)
                .where(OrderShipmentGroupDetails.ORDER.eq(order.getPrimaryKey()));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(OrderShipmentGroupDetails.ORDER_SHIPMENT_GROUP_SEQUENCE),
                    OrderShipmentGroupFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return orderShipmentGroupFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<OrderShipmentGroup> getOrderShipmentGroupsByOrder(Order order) {
        return getOrderShipmentGroupsByOrder(order, EntityPermission.READ_ONLY);
    }

    public List<OrderShipmentGroup> getOrderShipmentGroupsByOrderForUpdate(Order order) {
        return getOrderShipmentGroupsByOrder(order, EntityPermission.READ_WRITE);
    }

    private List<OrderShipmentGroup> getOrderShipmentGroups(Order order, ItemDeliveryType itemDeliveryType, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(OrderShipmentGroups.fields())
                .from(OrderShipmentGroups)
                .join(OrderShipmentGroupDetails).onKey(ORDER_SHIPMENT_GROUPS_ACTIVE_DETAIL_FK)
                .where(OrderShipmentGroupDetails.ORDER.eq(order.getPrimaryKey()),
                        OrderShipmentGroupDetails.ITEM_DELIVERY_TYPE.eq(itemDeliveryType.getPrimaryKey()));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(OrderShipmentGroupDetails.ORDER_SHIPMENT_GROUP_SEQUENCE),
                    OrderShipmentGroupFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return orderShipmentGroupFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<OrderShipmentGroup> getOrderShipmentGroups(Order order, ItemDeliveryType itemDeliveryType) {
        return getOrderShipmentGroups(order, itemDeliveryType, EntityPermission.READ_ONLY);
    }

    public List<OrderShipmentGroup> getOrderShipmentGroupsForUpdate(Order order, ItemDeliveryType itemDeliveryType) {
        return getOrderShipmentGroups(order, itemDeliveryType, EntityPermission.READ_WRITE);
    }

    public OrderShipmentGroupTransfer getOrderShipmentGroupTransfer(UserVisit userVisit, OrderShipmentGroup orderShipmentGroup) {
        return orderShipmentGroupTransferCache.getOrderShipmentGroupTransfer(userVisit, orderShipmentGroup);
    }

    public List<OrderShipmentGroupTransfer> getOrderShipmentGroupTransfers(UserVisit userVisit, Collection<OrderShipmentGroup> orderShipmentGroups) {
        List<OrderShipmentGroupTransfer> orderShipmentGroupTransfers = new ArrayList<>(orderShipmentGroups.size());

        orderShipmentGroups.forEach((orderShipmentGroup) ->
                orderShipmentGroupTransfers.add(orderShipmentGroupTransferCache.getOrderShipmentGroupTransfer(userVisit, orderShipmentGroup))
        );

        return orderShipmentGroupTransfers;
    }

    public List<OrderShipmentGroupTransfer> getOrderShipmentGroupTransfers(UserVisit userVisit, Order order) {
        return getOrderShipmentGroupTransfers(userVisit, getOrderShipmentGroupsByOrder(order));
    }

    private void updateOrderShipmentGroupFromValue(OrderShipmentGroupDetailValue orderShipmentGroupDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(orderShipmentGroupDetailValue.hasBeenModified()) {
            var orderShipmentGroup = orderShipmentGroupFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     orderShipmentGroupDetailValue.getOrderShipmentGroupPK());
            var orderShipmentGroupDetail = orderShipmentGroup.getActiveDetailForUpdate();

            orderShipmentGroupDetail.setThruTime(session.getStartTime());
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

            orderShipmentGroupDetail = orderShipmentGroupDetailFactory.create(orderShipmentGroupPK, orderPK, orderShipmentGroupSequence,
                    itemDeliveryTypePK, isDefault, partyContactMechanismPK, shippingMethodPK, holdUntilComplete, session.getStartTime(), Session.MAX_TIME);

            orderShipmentGroup.setActiveDetail(orderShipmentGroupDetail);
            orderShipmentGroup.setLastDetail(orderShipmentGroupDetail);

            sendEvent(orderShipmentGroupPK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }

    public void updateOrderShipmentGroupFromValue(OrderShipmentGroupDetailValue orderShipmentGroupDetailValue, BasePK updatedBy) {
        updateOrderShipmentGroupFromValue(orderShipmentGroupDetailValue, true, updatedBy);
    }

    public void deleteOrderShipmentGroup(OrderShipmentGroup orderShipmentGroup, BasePK deletedBy) {

        orderLineControl.deleteOrderLinesByOrderShipmentGroup(orderShipmentGroup, deletedBy);

        var orderShipmentGroupDetail = orderShipmentGroup.getLastDetailForUpdate();
        orderShipmentGroupDetail.setThruTime(session.getStartTime());
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
