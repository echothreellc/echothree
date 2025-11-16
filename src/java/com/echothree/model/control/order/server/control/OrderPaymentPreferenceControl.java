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
import com.echothree.model.control.order.common.transfer.OrderPaymentPreferenceTransfer;
import com.echothree.model.data.order.server.entity.Order;
import com.echothree.model.data.order.server.entity.OrderPaymentPreference;
import com.echothree.model.data.order.server.factory.OrderPaymentPreferenceDetailFactory;
import com.echothree.model.data.order.server.factory.OrderPaymentPreferenceFactory;
import com.echothree.model.data.order.server.value.OrderPaymentPreferenceDetailValue;
import com.echothree.model.data.payment.server.entity.PartyPaymentMethod;
import com.echothree.model.data.payment.server.entity.PaymentMethod;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.exception.PersistenceDatabaseException;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class OrderPaymentPreferenceControl
        extends BaseOrderControl {

    /** Creates a new instance of OrderControl */
    protected OrderPaymentPreferenceControl() {
        super();
    }

    // --------------------------------------------------------------------------------
    //   Order Payment Preferences
    // --------------------------------------------------------------------------------

    public OrderPaymentPreference createOrderPaymentPreference(Order order, Integer orderPaymentPreferenceSequence, PaymentMethod paymentMethod,
            PartyPaymentMethod partyPaymentMethod, Boolean wasPresent, Long maximumAmount, Integer sortOrder, BasePK createdBy) {
        var orderPaymentPreference = OrderPaymentPreferenceFactory.getInstance().create();
        var orderPaymentPreferenceDetail = OrderPaymentPreferenceDetailFactory.getInstance().create(orderPaymentPreference, order,
                orderPaymentPreferenceSequence, paymentMethod, partyPaymentMethod, wasPresent, maximumAmount, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        // Convert to R/W
        orderPaymentPreference = OrderPaymentPreferenceFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                orderPaymentPreference.getPrimaryKey());
        orderPaymentPreference.setActiveDetail(orderPaymentPreferenceDetail);
        orderPaymentPreference.setLastDetail(orderPaymentPreferenceDetail);
        orderPaymentPreference.store();

        sendEvent(orderPaymentPreference.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return orderPaymentPreference;
    }

    public long countOrderPaymentPreferencesByOrder(Order order) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                        "FROM orderpaymentpreferences, orderpaymentpreferencedetails " +
                        "WHERE ordpymtprf_activedetailid = ordpymtprfdt_orderpaymentpreferencedetailid AND ordpymtprfdt_ord_orderid = ?",
                order);
    }

    public long countOrderPaymentPreferencesByPaymentMethod(PaymentMethod paymentMethod) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                        "FROM orderpaymentpreferences, orderpaymentpreferencedetails " +
                        "WHERE ordpymtprf_activedetailid = ordpymtprfdt_orderpaymentpreferencedetailid AND ordpymtprfdt_pm_paymentmethodid = ?",
                paymentMethod);
    }

    public long countOrderPaymentPreferencesByPartyPaymentMethod(PartyPaymentMethod partyPaymentMethod) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                        "FROM orderpaymentpreferences, orderpaymentpreferencedetails " +
                        "WHERE ordpymtprf_activedetailid = ordpymtprfdt_orderpaymentpreferencedetailid AND ordpymtprfdt_parpm_partypaymentmethodid = ?",
                partyPaymentMethod);
    }

    public OrderPaymentPreference getOrderPaymentPreferenceBySequence(Order order, Integer orderPaymentPreferenceSequence, EntityPermission entityPermission) {
        OrderPaymentPreference orderPaymentPreference;

        try {
            String query = null;

            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM orderpaymentpreferences, orderpaymentpreferencedetails " +
                        "WHERE ordpymtprf_activedetailid = ordpymtprfdt_orderpaymentpreferencedetailid AND ordpymtprfdt_ord_orderid = ? AND ordpymtprfdt_orderpaymentpreferencesequence = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM orderpaymentpreferences, orderpaymentpreferencedetails " +
                        "WHERE ordpymtprf_activedetailid = ordpymtprfdt_orderpaymentpreferencedetailid AND ordpymtprfdt_ord_orderid = ? AND ordpymtprfdt_orderpaymentpreferencesequence = ? " +
                        "FOR UPDATE";
            }

            var ps = OrderPaymentPreferenceFactory.getInstance().prepareStatement(query);

            ps.setLong(1, order.getPrimaryKey().getEntityId());
            ps.setInt(2, orderPaymentPreferenceSequence);

            orderPaymentPreference = OrderPaymentPreferenceFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return orderPaymentPreference;
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
        List<OrderPaymentPreference> orderPaymentPreferences;

        try {
            String query = null;

            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM orderpaymentpreferences, orderpaymentpreferencedetails, paymentmethods, paymentmethoddetails " +
                        "WHERE ordpymtprf_activedetailid = ordpymtprfdt_orderpaymentpreferencedetailid AND ordpymtprfdt_ord_orderid = ? " +
                        "AND ordpymtprfdt_pm_paymentmethodid = pm_paymentmethodid AND pm_lastdetailid = pmdt_paymentmethoddetailid " +
                        "ORDER BY pmdt_sortorder, pmdt_paymentmethodname, ordpymtprfdt_sortorder";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM orderpaymentpreferences, orderpaymentpreferencedetails " +
                        "WHERE ordpymtprf_activedetailid = ordpymtprfdt_orderpaymentpreferencedetailid AND ordpymtprfdt_ord_orderid = ? " +
                        "FOR UPDATE";
            }

            var ps = OrderPaymentPreferenceFactory.getInstance().prepareStatement(query);

            ps.setLong(1, order.getPrimaryKey().getEntityId());

            orderPaymentPreferences = OrderPaymentPreferenceFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return orderPaymentPreferences;
    }

    public List<OrderPaymentPreference> getOrderPaymentPreferencesByOrder(Order order) {
        return getOrderPaymentPreferencesByOrder(order, EntityPermission.READ_ONLY);
    }

    public List<OrderPaymentPreference> getOrderPaymentPreferencesByOrderForUpdate(Order order) {
        return getOrderPaymentPreferencesByOrder(order, EntityPermission.READ_WRITE);
    }

    private List<OrderPaymentPreference> getOrderPaymentPreferencesByPaymentMethod(PaymentMethod paymentMethod, EntityPermission entityPermission) {
        List<OrderPaymentPreference> orderPaymentPreferences;

        try {
            String query = null;

            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM orderpaymentpreferences, orderpaymentpreferencedetails, paymentmethods, paymentmethoddetails " +
                        "WHERE ordpymtprf_activedetailid = ordpymtprfdt_orderpaymentpreferencedetailid AND ordpymtprfdt_pm_paymentmethodid = ? " +
                        "ORDER BY pmdt_sortorder, pmdt_paymentmethodname, ordpymtprfdt_sortorder";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM orderpaymentpreferences, orderpaymentpreferencedetails " +
                        "WHERE ordpymtprf_activedetailid = ordpymtprfdt_orderpaymentpreferencedetailid AND ordpymtprfdt_pm_paymentmethodid = ? " +
                        "FOR UPDATE";
            }

            var ps = OrderPaymentPreferenceFactory.getInstance().prepareStatement(query);

            ps.setLong(1, paymentMethod.getPrimaryKey().getEntityId());

            orderPaymentPreferences = OrderPaymentPreferenceFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return orderPaymentPreferences;
    }

    public List<OrderPaymentPreference> getOrderPaymentPreferencesByPaymentMethod(PaymentMethod paymentMethod) {
        return getOrderPaymentPreferencesByPaymentMethod(paymentMethod, EntityPermission.READ_ONLY);
    }

    public List<OrderPaymentPreference> getOrderPaymentPreferencesByPaymentMethodForUpdate(PaymentMethod paymentMethod) {
        return getOrderPaymentPreferencesByPaymentMethod(paymentMethod, EntityPermission.READ_WRITE);
    }

    private List<OrderPaymentPreference> getOrderPaymentPreferencesByPartyPaymentMethod(PartyPaymentMethod partyPaymentMethod, EntityPermission entityPermission) {
        List<OrderPaymentPreference> orderPaymentPreferences;

        try {
            String query = null;

            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM orderpaymentpreferences, orderpaymentpreferencedetails, paymentmethods, paymentmethoddetails " +
                        "WHERE ordpymtprf_activedetailid = ordpymtprfdt_orderpaymentpreferencedetailid AND ordpymtprfdt_parpm_partypaymentmethodid = ? " +
                        "ORDER BY ordpymtprfdt_sortorder";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM orderpaymentpreferences, orderpaymentpreferencedetails " +
                        "WHERE ordpymtprf_activedetailid = ordpymtprfdt_orderpaymentpreferencedetailid AND ordpymtprfdt_parpm_partypaymentmethodid = ? " +
                        "FOR UPDATE";
            }

            var ps = OrderPaymentPreferenceFactory.getInstance().prepareStatement(query);

            ps.setLong(1, partyPaymentMethod.getPrimaryKey().getEntityId());

            orderPaymentPreferences = OrderPaymentPreferenceFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return orderPaymentPreferences;
    }

    public List<OrderPaymentPreference> getOrderPaymentPreferencesByPartyPaymentMethod(PartyPaymentMethod partyPaymentMethod) {
        return getOrderPaymentPreferencesByPartyPaymentMethod(partyPaymentMethod, EntityPermission.READ_ONLY);
    }

    public List<OrderPaymentPreference> getOrderPaymentPreferencesByPartyPaymentMethodForUpdate(PartyPaymentMethod partyPaymentMethod) {
        return getOrderPaymentPreferencesByPartyPaymentMethod(partyPaymentMethod, EntityPermission.READ_WRITE);
    }

    public OrderPaymentPreferenceTransfer getOrderPaymentPreferenceTransfer(UserVisit userVisit, OrderPaymentPreference orderPaymentPreference) {
        return orderTransferCaches.getOrderPaymentPreferenceTransferCache().getOrderPaymentPreferenceTransfer(userVisit, orderPaymentPreference);
    }

    public List<OrderPaymentPreferenceTransfer> getOrderPaymentPreferenceTransfers(UserVisit userVisit, Collection<OrderPaymentPreference> orderPaymentPreferences) {
        List<OrderPaymentPreferenceTransfer> orderPaymentPreferenceTransfers = new ArrayList<>(orderPaymentPreferences.size());
        var orderPaymentPreferenceTransferCache = orderTransferCaches.getOrderPaymentPreferenceTransferCache();

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
            var orderPaymentPreference = OrderPaymentPreferenceFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    orderPaymentPreferenceDetailValue.getOrderPaymentPreferencePK());
            var orderPaymentPreferenceDetail = orderPaymentPreference.getActiveDetailForUpdate();

            orderPaymentPreferenceDetail.setThruTime(session.START_TIME_LONG);
            orderPaymentPreferenceDetail.store();

            var orderPaymentPreferencePK = orderPaymentPreferenceDetail.getOrderPaymentPreferencePK(); // Not updated
            var orderPK = orderPaymentPreferenceDetail.getOrderPK(); // Not updated
            var orderPaymentPreferenceSequence = orderPaymentPreferenceDetail.getOrderPaymentPreferenceSequence(); // Not updated
            var paymentMethodPK = orderPaymentPreferenceDetailValue.getPaymentMethodPK();
            var partyPaymentMethodPK = orderPaymentPreferenceDetailValue.getPartyPaymentMethodPK();
            var wasPresent = orderPaymentPreferenceDetailValue.getWasPresent();
            var maximumAmount = orderPaymentPreferenceDetailValue.getMaximumAmount();
            var sortOrder = orderPaymentPreferenceDetailValue.getSortOrder();

            orderPaymentPreferenceDetail = OrderPaymentPreferenceDetailFactory.getInstance().create(orderPaymentPreferencePK, orderPK,
                    orderPaymentPreferenceSequence, paymentMethodPK, partyPaymentMethodPK, wasPresent, maximumAmount, sortOrder, session.START_TIME_LONG,
                    Session.MAX_TIME_LONG);

            orderPaymentPreference.setActiveDetail(orderPaymentPreferenceDetail);
            orderPaymentPreference.setLastDetail(orderPaymentPreferenceDetail);

            sendEvent(orderPaymentPreferencePK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }

    public void deleteOrderPaymentPreference(OrderPaymentPreference orderPaymentPreference, BasePK deletedBy) {
        var orderPaymentPreferenceDetail = orderPaymentPreference.getLastDetailForUpdate();

        orderPaymentPreferenceDetail.setThruTime(session.START_TIME_LONG);
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
