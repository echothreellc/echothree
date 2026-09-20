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
import com.echothree.model.control.order.common.transfer.OrderPaymentPreferenceTransfer;
import com.echothree.model.control.order.server.transfer.OrderPaymentPreferenceTransferCache;
import com.echothree.model.data.order.server.entity.Order;
import com.echothree.model.data.order.server.entity.OrderPaymentPreference;
import com.echothree.model.data.order.server.factory.OrderPaymentPreferenceDetailFactory;
import com.echothree.model.data.order.server.factory.OrderPaymentPreferenceFactory;
import com.echothree.model.data.order.server.value.OrderPaymentPreferenceDetailValue;
import com.echothree.model.data.payment.server.entity.PartyPaymentMethod;
import com.echothree.model.data.payment.server.entity.PaymentMethod;
import com.echothree.model.data.user.server.entity.UserVisit;
import static com.echothree.model.jooq.server.keys.order.OrderForeignKeys.ORDERS_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.order.OrderForeignKeys.ORDER_PAYMENT_PREFERENCES_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.order.OrderForeignKeys.ORDER_PAYMENT_PREFERENCE_DETAILS_ORDER_FK;
import static com.echothree.model.jooq.server.keys.order.OrderForeignKeys.ORDER_PAYMENT_PREFERENCE_DETAILS_PAYMENT_METHOD_FK;
import static com.echothree.model.jooq.server.keys.payment.PaymentForeignKeys.PAYMENT_METHODS_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.tables.order.OrderDetails.OrderDetails;
import static com.echothree.model.jooq.server.tables.order.OrderPaymentPreferenceDetails.OrderPaymentPreferenceDetails;
import static com.echothree.model.jooq.server.tables.order.OrderPaymentPreferences.OrderPaymentPreferences;
import static com.echothree.model.jooq.server.tables.order.Orders.Orders;
import static com.echothree.model.jooq.server.tables.payment.PaymentMethodDetails.PaymentMethodDetails;
import static com.echothree.model.jooq.server.tables.payment.PaymentMethods.PaymentMethods;
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
public class OrderPaymentPreferenceControl
        extends BaseModelControl {

    /** Creates a new instance of OrderControl */
    protected OrderPaymentPreferenceControl() {
        super();
    }

    // --------------------------------------------------------------------------------
    //   Order Payment Preferences
    // --------------------------------------------------------------------------------

    @Inject
    protected OrderPaymentPreferenceDetailFactory orderPaymentPreferenceDetailFactory;

    @Inject
    private OrderPaymentPreferenceFactory orderPaymentPreferenceFactory;

    @Inject
    private OrderPaymentPreferenceTransferCache orderPaymentPreferenceTransferCache;

    public OrderPaymentPreference createOrderPaymentPreference(Order order, Integer orderPaymentPreferenceSequence, PaymentMethod paymentMethod,
            PartyPaymentMethod partyPaymentMethod, Boolean wasPresent, Long maximumAmount, Integer sortOrder, BasePK createdBy) {
        var orderPaymentPreference = orderPaymentPreferenceFactory.create();
        var orderPaymentPreferenceDetail = orderPaymentPreferenceDetailFactory.create(orderPaymentPreference, order,
                orderPaymentPreferenceSequence, paymentMethod, partyPaymentMethod, wasPresent, maximumAmount, sortOrder, session.getStartTime(), Session.MAX_TIME);

        // Convert to R/W
        orderPaymentPreference = orderPaymentPreferenceFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                orderPaymentPreference.getPrimaryKey());
        orderPaymentPreference.setActiveDetail(orderPaymentPreferenceDetail);
        orderPaymentPreference.setLastDetail(orderPaymentPreferenceDetail);
        orderPaymentPreference.store();

        sendEvent(orderPaymentPreference.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return orderPaymentPreference;
    }

    public long countOrderPaymentPreferencesByOrder(Order order) {
        return session.getDslContext()
                .selectCount()
                .from(OrderPaymentPreferences)
                .join(OrderPaymentPreferenceDetails).onKey(ORDER_PAYMENT_PREFERENCES_ACTIVE_DETAIL_FK)
                .where(OrderPaymentPreferenceDetails.ORDER.eq(order.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countOrderPaymentPreferencesByPaymentMethod(PaymentMethod paymentMethod) {
        return session.getDslContext()
                .selectCount()
                .from(OrderPaymentPreferences)
                .join(OrderPaymentPreferenceDetails).onKey(ORDER_PAYMENT_PREFERENCES_ACTIVE_DETAIL_FK)
                .where(OrderPaymentPreferenceDetails.PAYMENT_METHOD.eq(paymentMethod.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countOrderPaymentPreferencesByPartyPaymentMethod(PartyPaymentMethod partyPaymentMethod) {
        return session.getDslContext()
                .selectCount()
                .from(OrderPaymentPreferences)
                .join(OrderPaymentPreferenceDetails).onKey(ORDER_PAYMENT_PREFERENCES_ACTIVE_DETAIL_FK)
                .where(OrderPaymentPreferenceDetails.PARTY_PAYMENT_METHOD.eq(partyPaymentMethod.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public OrderPaymentPreference getOrderPaymentPreferenceBySequence(Order order, Integer orderPaymentPreferenceSequence, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(OrderPaymentPreferences.fields())
                .from(OrderPaymentPreferences)
                .join(OrderPaymentPreferenceDetails).onKey(ORDER_PAYMENT_PREFERENCES_ACTIVE_DETAIL_FK)
                .where(OrderPaymentPreferenceDetails.ORDER.eq(order.getPrimaryKey()),
                        OrderPaymentPreferenceDetails.ORDER_PAYMENT_PREFERENCE_SEQUENCE.eq(orderPaymentPreferenceSequence));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return orderPaymentPreferenceFactory.getEntityFromQuery(entityPermission, query);
    }

    public OrderPaymentPreference getOrderPaymentPreferenceBySequence(Order order, Integer orderPaymentPreferenceSequence) {
        return getOrderPaymentPreferenceBySequence(order, orderPaymentPreferenceSequence, EntityPermission.READ_ONLY);
    }

    public OrderPaymentPreference getOrderPaymentPreferenceBySequenceForUpdate(Order order, Integer orderPaymentPreferenceSequence) {
        return getOrderPaymentPreferenceBySequence(order, orderPaymentPreferenceSequence, EntityPermission.READ_WRITE);
    }

    public OrderPaymentPreferenceDetailValue getOrderPaymentPreferenceDetailValueForUpdate(OrderPaymentPreference orderPaymentPreference) {
        return orderPaymentPreference == null? null: orderPaymentPreference.getLastDetailForUpdate().getOrderPaymentPreferenceDetailValue().clone();
    }

    public OrderPaymentPreferenceDetailValue getOrderPaymentPreferenceDetailValueBySequenceForUpdate(Order order, Integer orderPaymentPreferenceSequence) {
        return getOrderPaymentPreferenceDetailValueForUpdate(getOrderPaymentPreferenceBySequenceForUpdate(order, orderPaymentPreferenceSequence));
    }

    private List<OrderPaymentPreference> getOrderPaymentPreferencesByOrder(Order order, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(OrderPaymentPreferences.fields())
                    .from(OrderPaymentPreferences)
                    .join(OrderPaymentPreferenceDetails).onKey(ORDER_PAYMENT_PREFERENCES_ACTIVE_DETAIL_FK)
                    .join(PaymentMethods).onKey(ORDER_PAYMENT_PREFERENCE_DETAILS_PAYMENT_METHOD_FK)
                    .join(PaymentMethodDetails).onKey(PAYMENT_METHODS_LAST_DETAIL_FK)
                    .where(OrderPaymentPreferenceDetails.ORDER.eq(order.getPrimaryKey()))
                    .orderBy(PaymentMethodDetails.SORT_ORDER, PaymentMethodDetails.PAYMENT_METHOD_NAME, OrderPaymentPreferenceDetails.SORT_ORDER),
                    OrderPaymentPreferenceFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(OrderPaymentPreferences.fields())
                    .from(OrderPaymentPreferences)
                    .join(OrderPaymentPreferenceDetails).onKey(ORDER_PAYMENT_PREFERENCES_ACTIVE_DETAIL_FK)
                    .where(OrderPaymentPreferenceDetails.ORDER.eq(order.getPrimaryKey()))
                    .forUpdate();
        };

        return orderPaymentPreferenceFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<OrderPaymentPreference> getOrderPaymentPreferencesByOrder(Order order) {
        return getOrderPaymentPreferencesByOrder(order, EntityPermission.READ_ONLY);
    }

    public List<OrderPaymentPreference> getOrderPaymentPreferencesByOrderForUpdate(Order order) {
        return getOrderPaymentPreferencesByOrder(order, EntityPermission.READ_WRITE);
    }

    private List<OrderPaymentPreference> getOrderPaymentPreferencesByPaymentMethod(PaymentMethod paymentMethod, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(OrderPaymentPreferences.fields())
                    .from(OrderPaymentPreferences)
                    .join(OrderPaymentPreferenceDetails).onKey(ORDER_PAYMENT_PREFERENCES_ACTIVE_DETAIL_FK)
                    .join(Orders).onKey(ORDER_PAYMENT_PREFERENCE_DETAILS_ORDER_FK)
                    .join(OrderDetails).onKey(ORDERS_LAST_DETAIL_FK)
                    .where(OrderPaymentPreferenceDetails.PAYMENT_METHOD.eq(paymentMethod.getPrimaryKey()))
                    .orderBy(OrderDetails.ORDER_NAME, OrderPaymentPreferenceDetails.SORT_ORDER),
                    OrderPaymentPreferenceFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(OrderPaymentPreferences.fields())
                    .from(OrderPaymentPreferences)
                    .join(OrderPaymentPreferenceDetails).onKey(ORDER_PAYMENT_PREFERENCES_ACTIVE_DETAIL_FK)
                    .where(OrderPaymentPreferenceDetails.PAYMENT_METHOD.eq(paymentMethod.getPrimaryKey()))
                    .forUpdate();
        };

        return orderPaymentPreferenceFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<OrderPaymentPreference> getOrderPaymentPreferencesByPaymentMethod(PaymentMethod paymentMethod) {
        return getOrderPaymentPreferencesByPaymentMethod(paymentMethod, EntityPermission.READ_ONLY);
    }

    public List<OrderPaymentPreference> getOrderPaymentPreferencesByPaymentMethodForUpdate(PaymentMethod paymentMethod) {
        return getOrderPaymentPreferencesByPaymentMethod(paymentMethod, EntityPermission.READ_WRITE);
    }

    private List<OrderPaymentPreference> getOrderPaymentPreferencesByPartyPaymentMethod(PartyPaymentMethod partyPaymentMethod, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(OrderPaymentPreferences.fields())
                    .from(OrderPaymentPreferences)
                    .join(OrderPaymentPreferenceDetails).onKey(ORDER_PAYMENT_PREFERENCES_ACTIVE_DETAIL_FK)
                    .join(Orders).onKey(ORDER_PAYMENT_PREFERENCE_DETAILS_ORDER_FK)
                    .join(OrderDetails).onKey(ORDERS_LAST_DETAIL_FK)
                    .where(OrderPaymentPreferenceDetails.PARTY_PAYMENT_METHOD.eq(partyPaymentMethod.getPrimaryKey()))
                    .orderBy(OrderDetails.ORDER_NAME, OrderPaymentPreferenceDetails.SORT_ORDER),
                    OrderPaymentPreferenceFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(OrderPaymentPreferences.fields())
                    .from(OrderPaymentPreferences)
                    .join(OrderPaymentPreferenceDetails).onKey(ORDER_PAYMENT_PREFERENCES_ACTIVE_DETAIL_FK)
                    .where(OrderPaymentPreferenceDetails.PARTY_PAYMENT_METHOD.eq(partyPaymentMethod.getPrimaryKey()))
                    .forUpdate();
        };

        return orderPaymentPreferenceFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<OrderPaymentPreference> getOrderPaymentPreferencesByPartyPaymentMethod(PartyPaymentMethod partyPaymentMethod) {
        return getOrderPaymentPreferencesByPartyPaymentMethod(partyPaymentMethod, EntityPermission.READ_ONLY);
    }

    public List<OrderPaymentPreference> getOrderPaymentPreferencesByPartyPaymentMethodForUpdate(PartyPaymentMethod partyPaymentMethod) {
        return getOrderPaymentPreferencesByPartyPaymentMethod(partyPaymentMethod, EntityPermission.READ_WRITE);
    }

    public OrderPaymentPreferenceTransfer getOrderPaymentPreferenceTransfer(UserVisit userVisit, OrderPaymentPreference orderPaymentPreference) {
        return orderPaymentPreferenceTransferCache.getOrderPaymentPreferenceTransfer(userVisit, orderPaymentPreference);
    }

    public List<OrderPaymentPreferenceTransfer> getOrderPaymentPreferenceTransfers(UserVisit userVisit, Collection<OrderPaymentPreference> orderPaymentPreferences) {
        List<OrderPaymentPreferenceTransfer> orderPaymentPreferenceTransfers = new ArrayList<>(orderPaymentPreferences.size());

        orderPaymentPreferences.forEach((orderPaymentPreference) ->
                orderPaymentPreferenceTransfers.add(orderPaymentPreferenceTransferCache.getOrderPaymentPreferenceTransfer(userVisit, orderPaymentPreference))
        );

        return orderPaymentPreferenceTransfers;
    }

    public List<OrderPaymentPreferenceTransfer> getOrderPaymentPreferenceTransfersByOrder(UserVisit userVisit, Order order) {
        return getOrderPaymentPreferenceTransfers(userVisit, getOrderPaymentPreferencesByOrder(order));
    }

    public List<OrderPaymentPreferenceTransfer> getOrderPaymentPreferenceTransfersByOrder(UserVisit userVisit, PaymentMethod paymentMethod) {
        return getOrderPaymentPreferenceTransfers(userVisit, getOrderPaymentPreferencesByPaymentMethod(paymentMethod));
    }

    public List<OrderPaymentPreferenceTransfer> getOrderPaymentPreferenceTransfersByOrder(UserVisit userVisit, PartyPaymentMethod partyPaymentMethod) {
        return getOrderPaymentPreferenceTransfers(userVisit, getOrderPaymentPreferencesByPartyPaymentMethod(partyPaymentMethod));
    }

    public void updateOrderPaymentPreferenceFromValue(OrderPaymentPreferenceDetailValue orderPaymentPreferenceDetailValue, BasePK updatedBy) {
        if(orderPaymentPreferenceDetailValue.hasBeenModified()) {
            var orderPaymentPreference = orderPaymentPreferenceFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                    orderPaymentPreferenceDetailValue.getOrderPaymentPreferencePK());
            var orderPaymentPreferenceDetail = orderPaymentPreference.getActiveDetailForUpdate();

            orderPaymentPreferenceDetail.setThruTime(session.getStartTime());
            orderPaymentPreferenceDetail.store();

            var orderPaymentPreferencePK = orderPaymentPreferenceDetail.getOrderPaymentPreferencePK(); // Not updated
            var orderPK = orderPaymentPreferenceDetail.getOrderPK(); // Not updated
            var orderPaymentPreferenceSequence = orderPaymentPreferenceDetail.getOrderPaymentPreferenceSequence(); // Not updated
            var paymentMethodPK = orderPaymentPreferenceDetailValue.getPaymentMethodPK();
            var partyPaymentMethodPK = orderPaymentPreferenceDetailValue.getPartyPaymentMethodPK();
            var wasPresent = orderPaymentPreferenceDetailValue.getWasPresent();
            var maximumAmount = orderPaymentPreferenceDetailValue.getMaximumAmount();
            var sortOrder = orderPaymentPreferenceDetailValue.getSortOrder();

            orderPaymentPreferenceDetail = orderPaymentPreferenceDetailFactory.create(orderPaymentPreferencePK, orderPK,
                    orderPaymentPreferenceSequence, paymentMethodPK, partyPaymentMethodPK, wasPresent, maximumAmount, sortOrder, session.getStartTime(),
                    Session.MAX_TIME);

            orderPaymentPreference.setActiveDetail(orderPaymentPreferenceDetail);
            orderPaymentPreference.setLastDetail(orderPaymentPreferenceDetail);

            sendEvent(orderPaymentPreferencePK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }

    public void deleteOrderPaymentPreference(OrderPaymentPreference orderPaymentPreference, BasePK deletedBy) {
        var orderPaymentPreferenceDetail = orderPaymentPreference.getLastDetailForUpdate();

        orderPaymentPreferenceDetail.setThruTime(session.getStartTime());
        orderPaymentPreference.setActiveDetail(null);
        orderPaymentPreference.store();

        sendEvent(orderPaymentPreference.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }

    public void deleteOrderPaymentPreferencesByOrder(List<OrderPaymentPreference> orderPaymentPreferences, BasePK deletedBy) {
        orderPaymentPreferences.forEach((orderPaymentPreference) -> 
                deleteOrderPaymentPreference(orderPaymentPreference, deletedBy)
        );
    }

    public void deleteOrderPaymentPreferencesByOrder(Order order, BasePK deletedBy) {
        deleteOrderPaymentPreferencesByOrder(getOrderPaymentPreferencesByOrderForUpdate(order), deletedBy);
    }

    public void deleteOrderPaymentPreferencesByPaymentMethod(PaymentMethod paymentMethod, BasePK deletedBy) {
        deleteOrderPaymentPreferencesByOrder(getOrderPaymentPreferencesByPaymentMethodForUpdate(paymentMethod), deletedBy);
    }

    public void deleteOrderPaymentPreferencesByPartyPaymentMethod(PartyPaymentMethod partyPaymentMethod, BasePK deletedBy) {
        deleteOrderPaymentPreferencesByOrder(getOrderPaymentPreferencesByPartyPaymentMethodForUpdate(partyPaymentMethod), deletedBy);
    }

}
