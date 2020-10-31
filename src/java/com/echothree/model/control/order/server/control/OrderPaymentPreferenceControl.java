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

package com.echothree.model.control.order.server.control;

import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.order.common.transfer.OrderPaymentPreferenceTransfer;
import com.echothree.model.control.order.server.transfer.OrderPaymentPreferenceTransferCache;
import com.echothree.model.data.order.common.pk.OrderPK;
import com.echothree.model.data.order.common.pk.OrderPaymentPreferencePK;
import com.echothree.model.data.order.server.entity.Order;
import com.echothree.model.data.order.server.entity.OrderPaymentPreference;
import com.echothree.model.data.order.server.entity.OrderPaymentPreferenceDetail;
import com.echothree.model.data.order.server.factory.OrderPaymentPreferenceDetailFactory;
import com.echothree.model.data.order.server.factory.OrderPaymentPreferenceFactory;
import com.echothree.model.data.order.server.value.OrderPaymentPreferenceDetailValue;
import com.echothree.model.data.payment.common.pk.PartyPaymentMethodPK;
import com.echothree.model.data.payment.common.pk.PaymentMethodPK;
import com.echothree.model.data.payment.server.entity.PartyPaymentMethod;
import com.echothree.model.data.payment.server.entity.PaymentMethod;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.exception.PersistenceDatabaseException;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderPaymentPreferenceControl
        extends BaseOrderControl {

    /** Creates a new instance of OrderControl */
    public OrderPaymentPreferenceControl() {
        super();
    }

    // --------------------------------------------------------------------------------
    //   Order Payment Preferences
    // --------------------------------------------------------------------------------

    public OrderPaymentPreference createOrderPaymentPreference(Order order, Integer orderPaymentPreferenceSequence, PaymentMethod paymentMethod,
            PartyPaymentMethod partyPaymentMethod, Boolean wasPresent, Long maximumAmount, Integer sortOrder, BasePK createdBy) {
        OrderPaymentPreference orderPaymentPreference = OrderPaymentPreferenceFactory.getInstance().create();
        OrderPaymentPreferenceDetail orderPaymentPreferenceDetail = OrderPaymentPreferenceDetailFactory.getInstance().create(orderPaymentPreference, order,
                orderPaymentPreferenceSequence, paymentMethod, partyPaymentMethod, wasPresent, maximumAmount, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        // Convert to R/W
        orderPaymentPreference = OrderPaymentPreferenceFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                orderPaymentPreference.getPrimaryKey());
        orderPaymentPreference.setActiveDetail(orderPaymentPreferenceDetail);
        orderPaymentPreference.setLastDetail(orderPaymentPreferenceDetail);
        orderPaymentPreference.store();

        sendEventUsingNames(orderPaymentPreference.getPrimaryKey(), EventTypes.CREATE.name(), null, null, createdBy);

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
        OrderPaymentPreference orderPaymentPreference = null;

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

            PreparedStatement ps = OrderPaymentPreferenceFactory.getInstance().prepareStatement(query);

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
        List<OrderPaymentPreference> orderPaymentPreferences = null;

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

            PreparedStatement ps = OrderPaymentPreferenceFactory.getInstance().prepareStatement(query);

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
        List<OrderPaymentPreference> orderPaymentPreferences = null;

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

            PreparedStatement ps = OrderPaymentPreferenceFactory.getInstance().prepareStatement(query);

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
        List<OrderPaymentPreference> orderPaymentPreferences = null;

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

            PreparedStatement ps = OrderPaymentPreferenceFactory.getInstance().prepareStatement(query);

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
        return getOrderTransferCaches(userVisit).getOrderPaymentPreferenceTransferCache().getOrderPaymentPreferenceTransfer(orderPaymentPreference);
    }

    public List<OrderPaymentPreferenceTransfer> getOrderPaymentPreferenceTransfers(UserVisit userVisit, List<OrderPaymentPreference> orderPaymentPreferences) {
        List<OrderPaymentPreferenceTransfer> orderPaymentPreferenceTransfers = new ArrayList<>(orderPaymentPreferences.size());
        OrderPaymentPreferenceTransferCache orderPaymentPreferenceTransferCache = getOrderTransferCaches(userVisit).getOrderPaymentPreferenceTransferCache();

        orderPaymentPreferences.forEach((orderPaymentPreference) ->
                orderPaymentPreferenceTransfers.add(orderPaymentPreferenceTransferCache.getOrderPaymentPreferenceTransfer(orderPaymentPreference))
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
            OrderPaymentPreference orderPaymentPreference = OrderPaymentPreferenceFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    orderPaymentPreferenceDetailValue.getOrderPaymentPreferencePK());
            OrderPaymentPreferenceDetail orderPaymentPreferenceDetail = orderPaymentPreference.getActiveDetailForUpdate();

            orderPaymentPreferenceDetail.setThruTime(session.START_TIME_LONG);
            orderPaymentPreferenceDetail.store();

            OrderPaymentPreferencePK orderPaymentPreferencePK = orderPaymentPreferenceDetail.getOrderPaymentPreferencePK(); // Not updated
            OrderPK orderPK = orderPaymentPreferenceDetail.getOrderPK(); // Not updated
            Integer orderPaymentPreferenceSequence = orderPaymentPreferenceDetail.getOrderPaymentPreferenceSequence(); // Not updated
            PaymentMethodPK paymentMethodPK = orderPaymentPreferenceDetailValue.getPaymentMethodPK();
            PartyPaymentMethodPK partyPaymentMethodPK = orderPaymentPreferenceDetailValue.getPartyPaymentMethodPK();
            Boolean wasPresent = orderPaymentPreferenceDetailValue.getWasPresent();
            Long maximumAmount = orderPaymentPreferenceDetailValue.getMaximumAmount();
            Integer sortOrder = orderPaymentPreferenceDetailValue.getSortOrder();

            orderPaymentPreferenceDetail = OrderPaymentPreferenceDetailFactory.getInstance().create(orderPaymentPreferencePK, orderPK,
                    orderPaymentPreferenceSequence, paymentMethodPK, partyPaymentMethodPK, wasPresent, maximumAmount, sortOrder, session.START_TIME_LONG,
                    Session.MAX_TIME_LONG);

            orderPaymentPreference.setActiveDetail(orderPaymentPreferenceDetail);
            orderPaymentPreference.setLastDetail(orderPaymentPreferenceDetail);

            sendEventUsingNames(orderPaymentPreferencePK, EventTypes.MODIFY.name(), null, null, updatedBy);
        }
    }

    public void deleteOrderPaymentPreference(OrderPaymentPreference orderPaymentPreference, BasePK deletedBy) {
        OrderPaymentPreferenceDetail orderPaymentPreferenceDetail = orderPaymentPreference.getLastDetailForUpdate();

        orderPaymentPreferenceDetail.setThruTime(session.START_TIME_LONG);
        orderPaymentPreference.setActiveDetail(null);
        orderPaymentPreference.store();

        sendEventUsingNames(orderPaymentPreference.getPrimaryKey(), EventTypes.DELETE.name(), null, null, deletedBy);
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
