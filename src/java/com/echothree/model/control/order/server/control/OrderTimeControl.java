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
import com.echothree.model.control.order.common.choice.OrderTimeTypeChoicesBean;
import com.echothree.model.control.order.common.transfer.OrderLineTimeTransfer;
import com.echothree.model.control.order.common.transfer.OrderTimeTransfer;
import com.echothree.model.control.order.common.transfer.OrderTimeTypeDescriptionTransfer;
import com.echothree.model.control.order.common.transfer.OrderTimeTypeTransfer;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.order.common.pk.OrderTimeTypePK;
import com.echothree.model.data.order.server.entity.Order;
import com.echothree.model.data.order.server.entity.OrderLine;
import com.echothree.model.data.order.server.entity.OrderLineTime;
import com.echothree.model.data.order.server.entity.OrderTime;
import com.echothree.model.data.order.server.entity.OrderTimeType;
import com.echothree.model.data.order.server.entity.OrderTimeTypeDescription;
import com.echothree.model.data.order.server.entity.OrderType;
import com.echothree.model.data.order.server.factory.OrderLineTimeFactory;
import com.echothree.model.data.order.server.factory.OrderTimeFactory;
import com.echothree.model.data.order.server.factory.OrderTimeTypeDescriptionFactory;
import com.echothree.model.data.order.server.factory.OrderTimeTypeDetailFactory;
import com.echothree.model.data.order.server.factory.OrderTimeTypeFactory;
import com.echothree.model.data.order.server.value.OrderLineTimeValue;
import com.echothree.model.data.order.server.value.OrderTimeTypeDescriptionValue;
import com.echothree.model.data.order.server.value.OrderTimeTypeDetailValue;
import com.echothree.model.data.order.server.value.OrderTimeValue;
import com.echothree.model.data.party.server.entity.Language;
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
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class OrderTimeControl
        extends BaseOrderControl {

    /** Creates a new instance of OrderControl */
    protected OrderTimeControl() {
        super();
    }
    
    // --------------------------------------------------------------------------------
    //   Order Time Types
    // --------------------------------------------------------------------------------

    public OrderTimeType createOrderTimeType(OrderType orderType, String orderTimeTypeName, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        var defaultOrderTimeType = getDefaultOrderTimeType(orderType);
        var defaultFound = defaultOrderTimeType != null;

        if(defaultFound && isDefault) {
            var defaultOrderTimeTypeDetailValue = getDefaultOrderTimeTypeDetailValueForUpdate(orderType);

            defaultOrderTimeTypeDetailValue.setIsDefault(false);
            updateOrderTimeTypeFromValue(defaultOrderTimeTypeDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var orderTimeType = OrderTimeTypeFactory.getInstance().create();
        var orderTimeTypeDetail = OrderTimeTypeDetailFactory.getInstance().create(orderTimeType, orderType, orderTimeTypeName, isDefault,
                sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        // Convert to R/W
        orderTimeType = OrderTimeTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                orderTimeType.getPrimaryKey());
        orderTimeType.setActiveDetail(orderTimeTypeDetail);
        orderTimeType.setLastDetail(orderTimeTypeDetail);
        orderTimeType.store();

        sendEvent(orderTimeType.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return orderTimeType;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.OrderTimeType */
    public OrderTimeType getOrderTimeTypeByEntityInstance(final EntityInstance entityInstance,
            final EntityPermission entityPermission) {
        var pk = new OrderTimeTypePK(entityInstance.getEntityUniqueId());

        return OrderTimeTypeFactory.getInstance().getEntityFromPK(entityPermission, pk);
    }

    public OrderTimeType getOrderTimeTypeByEntityInstance(final EntityInstance entityInstance) {
        return getOrderTimeTypeByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public OrderTimeType getOrderTimeTypeByEntityInstanceForUpdate(final EntityInstance entityInstance) {
        return getOrderTimeTypeByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public OrderTimeType getOrderTimeTypeByPK(OrderTimeTypePK pk) {
        return OrderTimeTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_ONLY, pk);
    }

    public long countOrderTimeTypes(OrderType orderType) {
        return session.queryForLong("""
                SELECT COUNT(*)
                FROM ordertimetypes, ordertimetypedetails
                WHERE ordtimtyp_activedetailid = ordtimtypdt_ordertimetypedetailid
                AND ordtimtypdt_ordtyp_ordertypeid = ?
                """, orderType);
    }

    private static final Map<EntityPermission, String> getOrderTimeTypeByNameQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM ordertimetypes, ordertimetypedetails " +
                "WHERE ordtimtyp_activedetailid = ordtimtypdt_ordertimetypedetailid " +
                "AND ordtimtypdt_ordtyp_ordertypeid = ? AND ordtimtypdt_ordertimetypename = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM ordertimetypes, ordertimetypedetails " +
                "WHERE ordtimtyp_activedetailid = ordtimtypdt_ordertimetypedetailid " +
                "AND ordtimtypdt_ordtyp_ordertypeid = ? AND ordtimtypdt_ordertimetypename = ? " +
                "FOR UPDATE");
        getOrderTimeTypeByNameQueries = Collections.unmodifiableMap(queryMap);
    }

    public OrderTimeType getOrderTimeTypeByName(OrderType orderType, String orderTimeTypeName, EntityPermission entityPermission) {
        return OrderTimeTypeFactory.getInstance().getEntityFromQuery(entityPermission, getOrderTimeTypeByNameQueries,
                orderType, orderTimeTypeName);
    }

    public OrderTimeType getOrderTimeTypeByName(OrderType orderType, String orderTimeTypeName) {
        return getOrderTimeTypeByName(orderType, orderTimeTypeName, EntityPermission.READ_ONLY);
    }

    public OrderTimeType getOrderTimeTypeByNameForUpdate(OrderType orderType, String orderTimeTypeName) {
        return getOrderTimeTypeByName(orderType, orderTimeTypeName, EntityPermission.READ_WRITE);
    }

    public OrderTimeTypeDetailValue getOrderTimeTypeDetailValueForUpdate(OrderTimeType orderTimeType) {
        return orderTimeType == null? null: orderTimeType.getLastDetailForUpdate().getOrderTimeTypeDetailValue().clone();
    }

    public OrderTimeTypeDetailValue getOrderTimeTypeDetailValueByNameForUpdate(OrderType orderType, String orderTimeTypeName) {
        return getOrderTimeTypeDetailValueForUpdate(getOrderTimeTypeByNameForUpdate(orderType, orderTimeTypeName));
    }

    private static final Map<EntityPermission, String> getDefaultOrderTimeTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM ordertimetypes, ordertimetypedetails " +
                "WHERE ordtimtyp_activedetailid = ordtimtypdt_ordertimetypedetailid " +
                "AND ordtimtypdt_ordtyp_ordertypeid = ? AND ordtimtypdt_isdefault = 1");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM ordertimetypes, ordertimetypedetails " +
                "WHERE ordtimtyp_activedetailid = ordtimtypdt_ordertimetypedetailid " +
                "AND ordtimtypdt_ordtyp_ordertypeid = ? AND ordtimtypdt_isdefault = 1 " +
                "FOR UPDATE");
        getDefaultOrderTimeTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    public OrderTimeType getDefaultOrderTimeType(OrderType orderType, EntityPermission entityPermission) {
        return OrderTimeTypeFactory.getInstance().getEntityFromQuery(entityPermission, getDefaultOrderTimeTypeQueries,
                orderType);
    }

    public OrderTimeType getDefaultOrderTimeType(OrderType orderType) {
        return getDefaultOrderTimeType(orderType, EntityPermission.READ_ONLY);
    }

    public OrderTimeType getDefaultOrderTimeTypeForUpdate(OrderType orderType) {
        return getDefaultOrderTimeType(orderType, EntityPermission.READ_WRITE);
    }

    public OrderTimeTypeDetailValue getDefaultOrderTimeTypeDetailValueForUpdate(OrderType orderType) {
        return getDefaultOrderTimeTypeForUpdate(orderType).getLastDetailForUpdate().getOrderTimeTypeDetailValue().clone();
    }

    private static final Map<EntityPermission, String> getOrderTimeTypesQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM ordertimetypes, ordertimetypedetails " +
                "WHERE ordtimtyp_activedetailid = ordtimtypdt_ordertimetypedetailid " +
                "AND ordtimtypdt_ordtyp_ordertypeid = ? " +
                "ORDER BY ordtimtypdt_sortorder, ordtimtypdt_ordertimetypename");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM ordertimetypes, ordertimetypedetails " +
                "WHERE ordtimtyp_activedetailid = ordtimtypdt_ordertimetypedetailid " +
                "AND ordtimtypdt_ordtyp_ordertypeid = ? " +
                "FOR UPDATE");
        getOrderTimeTypesQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<OrderTimeType> getOrderTimeTypes(OrderType orderType, EntityPermission entityPermission) {
        return OrderTimeTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, getOrderTimeTypesQueries,
                orderType);
    }

    public List<OrderTimeType> getOrderTimeTypes(OrderType orderType) {
        return getOrderTimeTypes(orderType, EntityPermission.READ_ONLY);
    }

    public List<OrderTimeType> getOrderTimeTypesForUpdate(OrderType orderType) {
        return getOrderTimeTypes(orderType, EntityPermission.READ_WRITE);
    }

    public OrderTimeTypeTransfer getOrderTimeTypeTransfer(UserVisit userVisit, OrderTimeType orderTimeType) {
        return orderTransferCaches.getOrderTimeTypeTransferCache().getOrderTimeTypeTransfer(userVisit, orderTimeType);
    }

    public List<OrderTimeTypeTransfer> getOrderTimeTypeTransfers(UserVisit userVisit, Collection<OrderTimeType> orderTimeTypes) {
        List<OrderTimeTypeTransfer> orderTimeTypeTransfers = new ArrayList<>(orderTimeTypes.size());
        var orderTimeTypeTransferCache = orderTransferCaches.getOrderTimeTypeTransferCache();

        orderTimeTypes.forEach((orderTimeType) ->
                orderTimeTypeTransfers.add(orderTimeTypeTransferCache.getOrderTimeTypeTransfer(userVisit, orderTimeType))
        );

        return orderTimeTypeTransfers;
    }

    public List<OrderTimeTypeTransfer> getOrderTimeTypeTransfers(UserVisit userVisit, OrderType orderType) {
        return getOrderTimeTypeTransfers(userVisit, getOrderTimeTypes(orderType));
    }

    public OrderTimeTypeChoicesBean getOrderTimeTypeChoices(String defaultOrderTimeTypeChoice, Language language, boolean allowNullChoice,
            OrderType orderType) {
        var orderTimeTypes = getOrderTimeTypes(orderType);
        var size = orderTimeTypes.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultOrderTimeTypeChoice == null) {
                defaultValue = "";
            }
        }

        for(var orderTimeType : orderTimeTypes) {
            var orderTimeTypeDetail = orderTimeType.getLastDetail();

            var label = getBestOrderTimeTypeDescription(orderTimeType, language);
            var value = orderTimeTypeDetail.getOrderTimeTypeName();

            labels.add(label == null? value: label);
            values.add(value);

            var usingDefaultChoice = defaultOrderTimeTypeChoice != null && defaultOrderTimeTypeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && orderTimeTypeDetail.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new OrderTimeTypeChoicesBean(labels, values, defaultValue);
    }

    private void updateOrderTimeTypeFromValue(OrderTimeTypeDetailValue orderTimeTypeDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(orderTimeTypeDetailValue.hasBeenModified()) {
            var orderTimeType = OrderTimeTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     orderTimeTypeDetailValue.getOrderTimeTypePK());
            var orderTimeTypeDetail = orderTimeType.getActiveDetailForUpdate();

            orderTimeTypeDetail.setThruTime(session.START_TIME_LONG);
            orderTimeTypeDetail.store();

            var orderType = orderTimeTypeDetail.getOrderType(); // Not updated
            var orderTypePK = orderType.getPrimaryKey(); // Not updated
            var orderTimeTypePK = orderTimeTypeDetail.getOrderTimeTypePK(); // Not updated
            var orderTimeTypeName = orderTimeTypeDetailValue.getOrderTimeTypeName();
            var isDefault = orderTimeTypeDetailValue.getIsDefault();
            var sortOrder = orderTimeTypeDetailValue.getSortOrder();

            if(checkDefault) {
                var defaultOrderTimeType = getDefaultOrderTimeType(orderType);
                var defaultFound = defaultOrderTimeType != null && !defaultOrderTimeType.equals(orderTimeType);

                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultOrderTimeTypeDetailValue = getDefaultOrderTimeTypeDetailValueForUpdate(orderType);

                    defaultOrderTimeTypeDetailValue.setIsDefault(false);
                    updateOrderTimeTypeFromValue(defaultOrderTimeTypeDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }

            orderTimeTypeDetail = OrderTimeTypeDetailFactory.getInstance().create(orderTimeTypePK, orderTypePK, orderTimeTypeName, isDefault, sortOrder,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);

            orderTimeType.setActiveDetail(orderTimeTypeDetail);
            orderTimeType.setLastDetail(orderTimeTypeDetail);

            sendEvent(orderTimeTypePK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }

    public void updateOrderTimeTypeFromValue(OrderTimeTypeDetailValue orderTimeTypeDetailValue, BasePK updatedBy) {
        updateOrderTimeTypeFromValue(orderTimeTypeDetailValue, true, updatedBy);
    }

    public void deleteOrderTimeType(OrderTimeType orderTimeType, BasePK deletedBy) {
        deleteOrderTimesByOrderTimeType(orderTimeType, deletedBy);
        deleteOrderTimeTypeDescriptionsByOrderTimeType(orderTimeType, deletedBy);

        var orderTimeTypeDetail = orderTimeType.getLastDetailForUpdate();
        orderTimeTypeDetail.setThruTime(session.START_TIME_LONG);
        orderTimeType.setActiveDetail(null);
        orderTimeType.store();

        // Check for default, and pick one if necessary
        var orderType = orderTimeTypeDetail.getOrderType();
        var defaultOrderTimeType = getDefaultOrderTimeType(orderType);
        if(defaultOrderTimeType == null) {
            var orderTimeTypes = getOrderTimeTypesForUpdate(orderType);

            if(!orderTimeTypes.isEmpty()) {
                var iter = orderTimeTypes.iterator();
                if(iter.hasNext()) {
                    defaultOrderTimeType = iter.next();
                }
                var orderTimeTypeDetailValue = Objects.requireNonNull(defaultOrderTimeType).getLastDetailForUpdate().getOrderTimeTypeDetailValue().clone();

                orderTimeTypeDetailValue.setIsDefault(true);
                updateOrderTimeTypeFromValue(orderTimeTypeDetailValue, false, deletedBy);
            }
        }

        sendEvent(orderTimeType.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Order Time Type Descriptions
    // --------------------------------------------------------------------------------

    public OrderTimeTypeDescription createOrderTimeTypeDescription(OrderTimeType orderTimeType, Language language, String description, BasePK createdBy) {
        var orderTimeTypeDescription = OrderTimeTypeDescriptionFactory.getInstance().create(orderTimeType, language, description,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEvent(orderTimeType.getPrimaryKey(), EventTypes.MODIFY, orderTimeTypeDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return orderTimeTypeDescription;
    }

    private static final Map<EntityPermission, String> getOrderTimeTypeDescriptionQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM ordertimetypedescriptions " +
                "WHERE ordtimtypd_ordtimtyp_ordertimetypeid = ? AND ordtimtypd_lang_languageid = ? AND ordtimtypd_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM ordertimetypedescriptions " +
                "WHERE ordtimtypd_ordtimtyp_ordertimetypeid = ? AND ordtimtypd_lang_languageid = ? AND ordtimtypd_thrutime = ? " +
                "FOR UPDATE");
        getOrderTimeTypeDescriptionQueries = Collections.unmodifiableMap(queryMap);
    }

    private OrderTimeTypeDescription getOrderTimeTypeDescription(OrderTimeType orderTimeType, Language language, EntityPermission entityPermission) {
        return OrderTimeTypeDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, getOrderTimeTypeDescriptionQueries,
                orderTimeType, language, Session.MAX_TIME);
    }

    public OrderTimeTypeDescription getOrderTimeTypeDescription(OrderTimeType orderTimeType, Language language) {
        return getOrderTimeTypeDescription(orderTimeType, language, EntityPermission.READ_ONLY);
    }

    public OrderTimeTypeDescription getOrderTimeTypeDescriptionForUpdate(OrderTimeType orderTimeType, Language language) {
        return getOrderTimeTypeDescription(orderTimeType, language, EntityPermission.READ_WRITE);
    }

    public OrderTimeTypeDescriptionValue getOrderTimeTypeDescriptionValue(OrderTimeTypeDescription orderTimeTypeDescription) {
        return orderTimeTypeDescription == null? null: orderTimeTypeDescription.getOrderTimeTypeDescriptionValue().clone();
    }

    public OrderTimeTypeDescriptionValue getOrderTimeTypeDescriptionValueForUpdate(OrderTimeType orderTimeType, Language language) {
        return getOrderTimeTypeDescriptionValue(getOrderTimeTypeDescriptionForUpdate(orderTimeType, language));
    }

    private static final Map<EntityPermission, String> getOrderTimeTypeDescriptionsByOrderTimeTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM ordertimetypedescriptions, languages " +
                "WHERE ordtimtypd_ordtimtyp_ordertimetypeid = ? AND ordtimtypd_thrutime = ? AND ordtimtypd_lang_languageid = lang_languageid " +
                "ORDER BY lang_sortorder, lang_languageisoname");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM ordertimetypedescriptions " +
                "WHERE ordtimtypd_ordtimtyp_ordertimetypeid = ? AND ordtimtypd_thrutime = ? " +
                "FOR UPDATE");
        getOrderTimeTypeDescriptionsByOrderTimeTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<OrderTimeTypeDescription> getOrderTimeTypeDescriptionsByOrderTimeType(OrderTimeType orderTimeType, EntityPermission entityPermission) {
        return OrderTimeTypeDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, getOrderTimeTypeDescriptionsByOrderTimeTypeQueries,
                orderTimeType, Session.MAX_TIME);
    }

    public List<OrderTimeTypeDescription> getOrderTimeTypeDescriptionsByOrderTimeType(OrderTimeType orderTimeType) {
        return getOrderTimeTypeDescriptionsByOrderTimeType(orderTimeType, EntityPermission.READ_ONLY);
    }

    public List<OrderTimeTypeDescription> getOrderTimeTypeDescriptionsByOrderTimeTypeForUpdate(OrderTimeType orderTimeType) {
        return getOrderTimeTypeDescriptionsByOrderTimeType(orderTimeType, EntityPermission.READ_WRITE);
    }

    public String getBestOrderTimeTypeDescription(OrderTimeType orderTimeType, Language language) {
        String description;
        var orderTimeTypeDescription = getOrderTimeTypeDescription(orderTimeType, language);

        if(orderTimeTypeDescription == null && !language.getIsDefault()) {
            orderTimeTypeDescription = getOrderTimeTypeDescription(orderTimeType, partyControl.getDefaultLanguage());
        }

        if(orderTimeTypeDescription == null) {
            description = orderTimeType.getLastDetail().getOrderTimeTypeName();
        } else {
            description = orderTimeTypeDescription.getDescription();
        }

        return description;
    }

    public OrderTimeTypeDescriptionTransfer getOrderTimeTypeDescriptionTransfer(UserVisit userVisit, OrderTimeTypeDescription orderTimeTypeDescription) {
        return orderTransferCaches.getOrderTimeTypeDescriptionTransferCache().getOrderTimeTypeDescriptionTransfer(userVisit, orderTimeTypeDescription);
    }

    public List<OrderTimeTypeDescriptionTransfer> getOrderTimeTypeDescriptionTransfersByOrderTimeType(UserVisit userVisit, OrderTimeType orderTimeType) {
        var orderTimeTypeDescriptions = getOrderTimeTypeDescriptionsByOrderTimeType(orderTimeType);
        List<OrderTimeTypeDescriptionTransfer> orderTimeTypeDescriptionTransfers = new ArrayList<>(orderTimeTypeDescriptions.size());
        var orderTimeTypeDescriptionTransferCache = orderTransferCaches.getOrderTimeTypeDescriptionTransferCache();

        orderTimeTypeDescriptions.forEach((orderTimeTypeDescription) ->
                orderTimeTypeDescriptionTransfers.add(orderTimeTypeDescriptionTransferCache.getOrderTimeTypeDescriptionTransfer(userVisit, orderTimeTypeDescription))
        );

        return orderTimeTypeDescriptionTransfers;
    }

    public void updateOrderTimeTypeDescriptionFromValue(OrderTimeTypeDescriptionValue orderTimeTypeDescriptionValue, BasePK updatedBy) {
        if(orderTimeTypeDescriptionValue.hasBeenModified()) {
            var orderTimeTypeDescription = OrderTimeTypeDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    orderTimeTypeDescriptionValue.getPrimaryKey());

            orderTimeTypeDescription.setThruTime(session.START_TIME_LONG);
            orderTimeTypeDescription.store();

            var orderTimeType = orderTimeTypeDescription.getOrderTimeType();
            var language = orderTimeTypeDescription.getLanguage();
            var description = orderTimeTypeDescriptionValue.getDescription();

            orderTimeTypeDescription = OrderTimeTypeDescriptionFactory.getInstance().create(orderTimeType, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);

            sendEvent(orderTimeType.getPrimaryKey(), EventTypes.MODIFY, orderTimeTypeDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteOrderTimeTypeDescription(OrderTimeTypeDescription orderTimeTypeDescription, BasePK deletedBy) {
        orderTimeTypeDescription.setThruTime(session.START_TIME_LONG);

        sendEvent(orderTimeTypeDescription.getOrderTimeTypePK(), EventTypes.MODIFY, orderTimeTypeDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);

    }

    public void deleteOrderTimeTypeDescriptionsByOrderTimeType(OrderTimeType orderTimeType, BasePK deletedBy) {
        var orderTimeTypeDescriptions = getOrderTimeTypeDescriptionsByOrderTimeTypeForUpdate(orderTimeType);

        orderTimeTypeDescriptions.forEach((orderTimeTypeDescription) -> 
                deleteOrderTimeTypeDescription(orderTimeTypeDescription, deletedBy)
        );
    }

    // --------------------------------------------------------------------------------
    //   Order Times
    // --------------------------------------------------------------------------------

    public OrderTime createOrderTime(Order order, OrderTimeType orderTimeType, Long time, BasePK createdBy) {
        var orderTime = OrderTimeFactory.getInstance().create(order, orderTimeType, time, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEvent(order.getPrimaryKey(), EventTypes.MODIFY, orderTime.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return orderTime;
    }

    public long countOrderTimesByOrder(Order order) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                        "FROM ordertimes " +
                        "WHERE ordtim_ord_orderid = ? AND ordtim_thrutime = ?",
                order, Session.MAX_TIME_LONG);
    }

    public long countOrderTimesByOrderTimeType(OrderTimeType orderTimeType) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                        "FROM ordertimes " +
                        "WHERE ordtim_ordtimtyp_ordertimetypeid = ? AND ordtim_thrutime = ?",
                orderTimeType, Session.MAX_TIME_LONG);
    }

    public boolean orderTimeExists(Order order, OrderTimeType orderTimeType) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                        "FROM ordertimes " +
                        "WHERE ordtim_ord_orderid = ? AND ordtim_ordtimtyp_ordertimetypeid = ? AND ordtim_thrutime = ?",
                order, orderTimeType, Session.MAX_TIME_LONG) == 1;
    }

    private static final Map<EntityPermission, String> getOrderTimeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                        "FROM ordertimes " +
                        "WHERE ordtim_ord_orderid = ? AND ordtim_ordtimtyp_ordertimetypeid = ? AND ordtim_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                        "FROM ordertimes " +
                        "WHERE ordtim_ord_orderid = ? AND ordtim_ordtimtyp_ordertimetypeid = ? AND ordtim_thrutime = ? " +
                        "FOR UPDATE");
        getOrderTimeQueries = Collections.unmodifiableMap(queryMap);
    }

    private OrderTime getOrderTime(Order order, OrderTimeType orderTimeType, EntityPermission entityPermission) {
        return OrderTimeFactory.getInstance().getEntityFromQuery(entityPermission, getOrderTimeQueries, order, orderTimeType, Session.MAX_TIME);
    }

    public OrderTime getOrderTime(Order order, OrderTimeType orderTimeType) {
        return getOrderTime(order, orderTimeType, EntityPermission.READ_ONLY);
    }

    public OrderTime getOrderTimeForUpdate(Order order, OrderTimeType orderTimeType) {
        return getOrderTime(order, orderTimeType, EntityPermission.READ_WRITE);
    }

    public OrderTimeValue getOrderTimeValue(OrderTime orderTime) {
        return orderTime == null? null: orderTime.getOrderTimeValue().clone();
    }

    public OrderTimeValue getOrderTimeValueForUpdate(Order order, OrderTimeType orderTimeType) {
        return getOrderTimeValue(getOrderTimeForUpdate(order, orderTimeType));
    }

    private static final Map<EntityPermission, String> getOrderTimesByOrderQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                        "FROM ordertimes, ordertimetypes, ordertimetypedetails " +
                        "WHERE ordtim_ord_orderid = ? AND ordtim_thrutime = ? " +
                        "AND ordtim_ordtimtyp_ordertimetypeid = ordtimtyp_ordertimetypeid AND ordtimtyp_activedetailid = ordtimtypdt_ordertimetypedetailid " +
                        "ORDER BY ordtimtypdt_sortorder, ordtimtypdt_ordertimetypename");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                        "FROM ordertimes " +
                        "WHERE ordtim_ord_orderid = ? AND ordtim_thrutime = ? " +
                        "FOR UPDATE");
        getOrderTimesByOrderQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<OrderTime> getOrderTimesByOrder(Order order, EntityPermission entityPermission) {
        return OrderTimeFactory.getInstance().getEntitiesFromQuery(entityPermission, getOrderTimesByOrderQueries, order, Session.MAX_TIME);
    }

    public List<OrderTime> getOrderTimesByOrder(Order order) {
        return getOrderTimesByOrder(order, EntityPermission.READ_ONLY);
    }

    public List<OrderTime> getOrderTimesByOrderForUpdate(Order order) {
        return getOrderTimesByOrder(order, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getOrderTimesByOrderTimeTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                        "FROM ordertimes, orders, orderdetails " +
                        "WHERE ordtim_ordtimtyp_ordertimetypeid = ? AND ordtim_thrutime = ? " +
                        "AND ordtim_ord_orderid = ordtim_ord_orderid AND ord_activedetailid = orddt_orderdetailid " +
                        "ORDER BY orddt_ordername");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                        "FROM ordertimes " +
                        "WHERE ordtim_ordtimtyp_ordertimetypeid = ? AND ordtim_thrutime = ? " +
                        "FOR UPDATE");
        getOrderTimesByOrderTimeTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<OrderTime> getOrderTimesByOrderTimeType(OrderTimeType orderTimeType, EntityPermission entityPermission) {
        return OrderTimeFactory.getInstance().getEntitiesFromQuery(entityPermission, getOrderTimesByOrderTimeTypeQueries, orderTimeType, Session.MAX_TIME);
    }

    public List<OrderTime> getOrderTimesByOrderTimeType(OrderTimeType orderTimeType) {
        return getOrderTimesByOrderTimeType(orderTimeType, EntityPermission.READ_ONLY);
    }

    public List<OrderTime> getOrderTimesByOrderTimeTypeForUpdate(OrderTimeType orderTimeType) {
        return getOrderTimesByOrderTimeType(orderTimeType, EntityPermission.READ_WRITE);
    }

    public OrderTimeTransfer getOrderTimeTransfer(UserVisit userVisit, OrderTime orderTime) {
        return orderTransferCaches.getOrderTimeTransferCache().getOrderTimeTransfer(userVisit, orderTime);
    }

    public List<OrderTimeTransfer> getOrderTimeTransfers(UserVisit userVisit, Collection<OrderTime> orderTimes) {
        List<OrderTimeTransfer> orderTimeTransfers = new ArrayList<>(orderTimes.size());
        var orderTimeTransferCache = orderTransferCaches.getOrderTimeTransferCache();

        orderTimes.forEach((orderTime) ->
                orderTimeTransfers.add(orderTimeTransferCache.getOrderTimeTransfer(userVisit, orderTime))
        );

        return orderTimeTransfers;
    }

    public List<OrderTimeTransfer> getOrderTimeTransfersByOrder(UserVisit userVisit, Order order) {
        return getOrderTimeTransfers(userVisit, getOrderTimesByOrder(order));
    }

    public List<OrderTimeTransfer> getOrderTimeTransfersByOrderTimeType(UserVisit userVisit, OrderTimeType orderTimeType) {
        return getOrderTimeTransfers(userVisit, getOrderTimesByOrderTimeType(orderTimeType));
    }

    public void updateOrderTimeFromValue(OrderTimeValue orderTimeValue, BasePK updatedBy) {
        if(orderTimeValue.hasBeenModified()) {
            var orderTime = OrderTimeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    orderTimeValue.getPrimaryKey());

            orderTime.setThruTime(session.START_TIME_LONG);
            orderTime.store();

            var orderPK = orderTime.getOrderPK(); // Not updated
            var orderTimeTypePK = orderTime.getOrderTimeTypePK(); // Not updated
            var time = orderTimeValue.getTime();

            orderTime = OrderTimeFactory.getInstance().create(orderPK, orderTimeTypePK, time, session.START_TIME_LONG, Session.MAX_TIME_LONG);

            sendEvent(orderPK, EventTypes.MODIFY, orderTime.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteOrderTime(OrderTime orderTime, BasePK deletedBy) {
        orderTime.setThruTime(session.START_TIME_LONG);

        sendEvent(orderTime.getOrderTimeTypePK(), EventTypes.MODIFY, orderTime.getPrimaryKey(), EventTypes.DELETE, deletedBy);

    }

    public void deleteOrderTimes(List<OrderTime> orderTimes, BasePK deletedBy) {
        orderTimes.forEach((orderTime) -> 
                deleteOrderTime(orderTime, deletedBy)
        );
    }

    public void deleteOrderTimesByOrder(Order order, BasePK deletedBy) {
        deleteOrderTimes(getOrderTimesByOrderForUpdate(order), deletedBy);
    }

    public void deleteOrderTimesByOrderTimeType(OrderTimeType orderTimeType, BasePK deletedBy) {
        deleteOrderTimes(getOrderTimesByOrderTimeTypeForUpdate(orderTimeType), deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Order Line Times
    // --------------------------------------------------------------------------------

    public OrderLineTime createOrderLineTime(OrderLine orderLine, OrderTimeType orderTimeType, Long time, BasePK createdBy) {
        var orderLineTime = OrderLineTimeFactory.getInstance().create(orderLine, orderTimeType, time, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEvent(orderLine.getPrimaryKey(), EventTypes.MODIFY, orderLineTime.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return orderLineTime;
    }

    public long countOrderLineTimesByOrderLine(OrderLine orderLine) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                        "FROM orderlinetimes " +
                        "WHERE ordltim_ordl_orderlineid = ? AND ordltim_thrutime = ?",
                orderLine, Session.MAX_TIME_LONG);
    }

    public long countOrderLineTimesByOrderTimeType(OrderTimeType orderTimeType) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                        "FROM orderlinetimes " +
                        "WHERE ordltim_ordtimtyp_ordertimetypeid = ? AND ordltim_thrutime = ?",
                orderTimeType, Session.MAX_TIME_LONG);
    }

    public boolean orderLineTimeExists(OrderLine orderLine, OrderTimeType orderTimeType) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                        "FROM orderlinetimes " +
                        "WHERE ordltim_ordl_orderlineid = ? AND ordltim_ordtimtyp_ordertimetypeid = ? AND ordltim_thrutime = ?",
                orderLine, orderTimeType, Session.MAX_TIME_LONG) == 1;
    }

    private static final Map<EntityPermission, String> getOrderLineTimeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                        "FROM orderlinetimes " +
                        "WHERE ordltim_ordl_orderlineid = ? AND ordltim_ordtimtyp_ordertimetypeid = ? AND ordltim_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                        "FROM orderlinetimes " +
                        "WHERE ordltim_ordl_orderlineid = ? AND ordltim_ordtimtyp_ordertimetypeid = ? AND ordltim_thrutime = ? " +
                        "FOR UPDATE");
        getOrderLineTimeQueries = Collections.unmodifiableMap(queryMap);
    }

    private OrderLineTime getOrderLineTime(OrderLine orderLine, OrderTimeType orderTimeType, EntityPermission entityPermission) {
        return OrderLineTimeFactory.getInstance().getEntityFromQuery(entityPermission, getOrderLineTimeQueries,
                orderLine, orderTimeType, Session.MAX_TIME);
    }

    public OrderLineTime getOrderLineTime(OrderLine orderLine, OrderTimeType orderTimeType) {
        return getOrderLineTime(orderLine, orderTimeType, EntityPermission.READ_ONLY);
    }

    public OrderLineTime getOrderLineTimeForUpdate(OrderLine orderLine, OrderTimeType orderTimeType) {
        return getOrderLineTime(orderLine, orderTimeType, EntityPermission.READ_WRITE);
    }

    public OrderLineTimeValue getOrderLineTimeValue(OrderLineTime orderLineTime) {
        return orderLineTime == null? null: orderLineTime.getOrderLineTimeValue().clone();
    }

    public OrderLineTimeValue getOrderLineTimeValueForUpdate(OrderLine orderLine, OrderTimeType orderTimeType) {
        return getOrderLineTimeValue(getOrderLineTimeForUpdate(orderLine, orderTimeType));
    }

    private static final Map<EntityPermission, String> getOrderLineTimesByOrderQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                        "FROM orderlinetimes, ordertimetypes, ordertimetypedetails " +
                        "WHERE ordltim_ordl_orderlineid = ? AND ordltim_thrutime = ? " +
                        "AND ordltim_ordtimtyp_ordertimetypeid = ordtimtyp_ordertimetypeid AND ordtimtyp_activedetailid = ordtimtypdt_ordertimetypedetailid " +
                        "ORDER BY ordtimtypdt_sortorder, ordtimtypdt_ordertimetypename");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                        "FROM orderlinetimes " +
                        "WHERE ordltim_ordl_orderlineid = ? AND ordltim_thrutime = ? " +
                        "FOR UPDATE");
        getOrderLineTimesByOrderQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<OrderLineTime> getOrderLineTimesByOrderLine(OrderLine orderLine, EntityPermission entityPermission) {
        return OrderLineTimeFactory.getInstance().getEntitiesFromQuery(entityPermission, getOrderLineTimesByOrderQueries,
                orderLine, Session.MAX_TIME);
    }

    public List<OrderLineTime> getOrderLineTimesByOrderLine(OrderLine orderLine) {
        return getOrderLineTimesByOrderLine(orderLine, EntityPermission.READ_ONLY);
    }

    public List<OrderLineTime> getOrderLineTimesByOrderForUpdate(OrderLine orderLine) {
        return getOrderLineTimesByOrderLine(orderLine, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getOrderLineTimesByOrderTimeTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                        "FROM orderlinetimes, orders, orderdetails " +
                        "WHERE ordltim_ordtimtyp_ordertimetypeid = ? AND ordltim_thrutime = ? " +
                        "AND ordltim_ordl_orderlineid = ordltim_ordl_orderlineid AND ord_activedetailid = orddt_orderdetailid " +
                        "ORDER BY orddt_ordername");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                        "FROM orderlinetimes " +
                        "WHERE ordltim_ordtimtyp_ordertimetypeid = ? AND ordltim_thrutime = ? " +
                        "FOR UPDATE");
        getOrderLineTimesByOrderTimeTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<OrderLineTime> getOrderLineTimesByOrderTimeType(OrderTimeType orderTimeType, EntityPermission entityPermission) {
        return OrderLineTimeFactory.getInstance().getEntitiesFromQuery(entityPermission, getOrderLineTimesByOrderTimeTypeQueries,
                orderTimeType, Session.MAX_TIME);
    }

    public List<OrderLineTime> getOrderLineTimesByOrderTimeType(OrderTimeType orderTimeType) {
        return getOrderLineTimesByOrderTimeType(orderTimeType, EntityPermission.READ_ONLY);
    }

    public List<OrderLineTime> getOrderLineTimesByOrderTimeTypeForUpdate(OrderTimeType orderTimeType) {
        return getOrderLineTimesByOrderTimeType(orderTimeType, EntityPermission.READ_WRITE);
    }

    public OrderLineTimeTransfer getOrderLineTimeTransfer(UserVisit userVisit, OrderLineTime orderLineTime) {
        return orderTransferCaches.getOrderLineTimeTransferCache().getOrderLineTimeTransfer(userVisit, orderLineTime);
    }

    public List<OrderLineTimeTransfer> getOrderLineTimeTransfers(UserVisit userVisit, Collection<OrderLineTime> orderLineTimes) {
        List<OrderLineTimeTransfer> orderLineTimeTransfers = new ArrayList<>(orderLineTimes.size());
        var orderLineTimeTransferCache = orderTransferCaches.getOrderLineTimeTransferCache();

        orderLineTimes.forEach((orderLineTime) ->
                orderLineTimeTransfers.add(orderLineTimeTransferCache.getOrderLineTimeTransfer(userVisit, orderLineTime))
        );

        return orderLineTimeTransfers;
    }

    public List<OrderLineTimeTransfer> getOrderLineTimeTransfersByOrderLine(UserVisit userVisit, OrderLine orderLine) {
        return getOrderLineTimeTransfers(userVisit, getOrderLineTimesByOrderLine(orderLine));
    }

    public List<OrderLineTimeTransfer> getOrderLineTimeTransfersByOrderTimeType(UserVisit userVisit, OrderTimeType orderTimeType) {
        return getOrderLineTimeTransfers(userVisit, getOrderLineTimesByOrderTimeType(orderTimeType));
    }

    public void updateOrderLineTimeFromValue(OrderLineTimeValue orderLineTimeValue, BasePK updatedBy) {
        if(orderLineTimeValue.hasBeenModified()) {
            var orderLineTime = OrderLineTimeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    orderLineTimeValue.getPrimaryKey());

            orderLineTime.setThruTime(session.START_TIME_LONG);
            orderLineTime.store();

            var orderLinePK = orderLineTime.getOrderLinePK(); // Not updated
            var orderTimeTypePK = orderLineTime.getOrderTimeTypePK(); // Not updated
            var time = orderLineTimeValue.getTime();

            orderLineTime = OrderLineTimeFactory.getInstance().create(orderLinePK, orderTimeTypePK, time, session.START_TIME_LONG, Session.MAX_TIME_LONG);

            sendEvent(orderLinePK, EventTypes.MODIFY, orderLineTime.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteOrderLineTime(OrderLineTime orderLineTime, BasePK deletedBy) {
        orderLineTime.setThruTime(session.START_TIME_LONG);

        sendEvent(orderLineTime.getOrderTimeTypePK(), EventTypes.MODIFY, orderLineTime.getPrimaryKey(), EventTypes.DELETE, deletedBy);

    }

    public void deleteOrderLineTimes(List<OrderLineTime> orderLineTimes, BasePK deletedBy) {
        orderLineTimes.forEach((orderLineTime) -> 
                deleteOrderLineTime(orderLineTime, deletedBy)
        );
    }

    public void deleteOrderLineTimesByOrder(OrderLine orderLine, BasePK deletedBy) {
        deleteOrderLineTimes(getOrderLineTimesByOrderForUpdate(orderLine), deletedBy);
    }

    public void deleteOrderLineTimesByOrderTimeType(OrderTimeType orderTimeType, BasePK deletedBy) {
        deleteOrderLineTimes(getOrderLineTimesByOrderTimeTypeForUpdate(orderTimeType), deletedBy);
    }

}
