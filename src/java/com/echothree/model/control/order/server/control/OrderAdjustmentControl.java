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
import com.echothree.model.control.order.common.choice.OrderAdjustmentTypeChoicesBean;
import com.echothree.model.control.order.common.transfer.OrderAdjustmentTypeDescriptionTransfer;
import com.echothree.model.control.order.common.transfer.OrderAdjustmentTypeTransfer;
import com.echothree.model.data.order.server.entity.Order;
import com.echothree.model.data.order.server.entity.OrderAdjustment;
import com.echothree.model.data.order.server.entity.OrderAdjustmentType;
import com.echothree.model.data.order.server.entity.OrderAdjustmentTypeDescription;
import com.echothree.model.data.order.server.entity.OrderType;
import com.echothree.model.data.order.server.factory.OrderAdjustmentDetailFactory;
import com.echothree.model.data.order.server.factory.OrderAdjustmentFactory;
import com.echothree.model.data.order.server.factory.OrderAdjustmentTypeDescriptionFactory;
import com.echothree.model.data.order.server.factory.OrderAdjustmentTypeDetailFactory;
import com.echothree.model.data.order.server.factory.OrderAdjustmentTypeFactory;
import com.echothree.model.data.order.server.value.OrderAdjustmentTypeDescriptionValue;
import com.echothree.model.data.order.server.value.OrderAdjustmentTypeDetailValue;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.exception.PersistenceDatabaseException;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import com.echothree.util.server.cdi.CommandScope;

@CommandScope
public class OrderAdjustmentControl
        extends BaseOrderControl {

    /** Creates a new instance of OrderControl */
    protected OrderAdjustmentControl() {
        super();
    }
    
    // --------------------------------------------------------------------------------
    //   Order Adjustment Types
    // --------------------------------------------------------------------------------

    public OrderAdjustmentType createOrderAdjustmentType(OrderType orderType, String orderAdjustmentTypeName, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        var defaultOrderAdjustmentType = getDefaultOrderAdjustmentType(orderType);
        var defaultFound = defaultOrderAdjustmentType != null;

        if(defaultFound && isDefault) {
            var defaultOrderAdjustmentTypeDetailValue = getDefaultOrderAdjustmentTypeDetailValueForUpdate(orderType);

            defaultOrderAdjustmentTypeDetailValue.setIsDefault(false);
            updateOrderAdjustmentTypeFromValue(defaultOrderAdjustmentTypeDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var orderAdjustmentType = OrderAdjustmentTypeFactory.getInstance().create();
        var orderAdjustmentTypeDetail = OrderAdjustmentTypeDetailFactory.getInstance().create(orderAdjustmentType, orderType,
                orderAdjustmentTypeName, isDefault, sortOrder, session.getStartTimeLong(), Session.MAX_TIME_LONG);

        // Convert to R/W
        orderAdjustmentType = OrderAdjustmentTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                orderAdjustmentType.getPrimaryKey());
        orderAdjustmentType.setActiveDetail(orderAdjustmentTypeDetail);
        orderAdjustmentType.setLastDetail(orderAdjustmentTypeDetail);
        orderAdjustmentType.store();

        sendEvent(orderAdjustmentType.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return orderAdjustmentType;
    }

    private static final Map<EntityPermission, String> getOrderAdjustmentTypeByNameQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM orderadjustmenttypes, orderadjustmenttypedetails " +
                "WHERE ordadjtyp_activedetailid = ordadjtypdt_orderadjustmenttypedetailid " +
                "AND ordadjtypdt_ordtyp_ordertypeid = ? AND ordadjtypdt_orderadjustmenttypename = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM orderadjustmenttypes, orderadjustmenttypedetails " +
                "WHERE ordadjtyp_activedetailid = ordadjtypdt_orderadjustmenttypedetailid " +
                "AND ordadjtypdt_ordtyp_ordertypeid = ? AND ordadjtypdt_orderadjustmenttypename = ? " +
                "FOR UPDATE");
        getOrderAdjustmentTypeByNameQueries = Collections.unmodifiableMap(queryMap);
    }

    private OrderAdjustmentType getOrderAdjustmentTypeByName(OrderType orderType, String orderAdjustmentTypeName, EntityPermission entityPermission) {
        return OrderAdjustmentTypeFactory.getInstance().getEntityFromQuery(entityPermission, getOrderAdjustmentTypeByNameQueries,
                orderType, orderAdjustmentTypeName);
    }

    public OrderAdjustmentType getOrderAdjustmentTypeByName(OrderType orderType, String orderAdjustmentTypeName) {
        return getOrderAdjustmentTypeByName(orderType, orderAdjustmentTypeName, EntityPermission.READ_ONLY);
    }

    public OrderAdjustmentType getOrderAdjustmentTypeByNameForUpdate(OrderType orderType, String orderAdjustmentTypeName) {
        return getOrderAdjustmentTypeByName(orderType, orderAdjustmentTypeName, EntityPermission.READ_WRITE);
    }

    public OrderAdjustmentTypeDetailValue getOrderAdjustmentTypeDetailValueForUpdate(OrderAdjustmentType orderAdjustmentType) {
        return orderAdjustmentType == null? null: orderAdjustmentType.getLastDetailForUpdate().getOrderAdjustmentTypeDetailValue().clone();
    }

    public OrderAdjustmentTypeDetailValue getOrderAdjustmentTypeDetailValueByNameForUpdate(OrderType orderType, String orderAdjustmentTypeName) {
        return getOrderAdjustmentTypeDetailValueForUpdate(getOrderAdjustmentTypeByNameForUpdate(orderType, orderAdjustmentTypeName));
    }

    private static final Map<EntityPermission, String> getDefaultOrderAdjustmentTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM orderadjustmenttypes, orderadjustmenttypedetails " +
                "WHERE ordadjtyp_activedetailid = ordadjtypdt_orderadjustmenttypedetailid " +
                "AND ordadjtypdt_ordtyp_ordertypeid = ? AND ordadjtypdt_isdefault = 1");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM orderadjustmenttypes, orderadjustmenttypedetails " +
                "WHERE ordadjtyp_activedetailid = ordadjtypdt_orderadjustmenttypedetailid " +
                "AND ordadjtypdt_ordtyp_ordertypeid = ? AND ordadjtypdt_isdefault = 1 " +
                "FOR UPDATE");
        getDefaultOrderAdjustmentTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    private OrderAdjustmentType getDefaultOrderAdjustmentType(OrderType orderType, EntityPermission entityPermission) {
        return OrderAdjustmentTypeFactory.getInstance().getEntityFromQuery(entityPermission, getDefaultOrderAdjustmentTypeQueries,
                orderType);
    }

    public OrderAdjustmentType getDefaultOrderAdjustmentType(OrderType orderType) {
        return getDefaultOrderAdjustmentType(orderType, EntityPermission.READ_ONLY);
    }

    public OrderAdjustmentType getDefaultOrderAdjustmentTypeForUpdate(OrderType orderType) {
        return getDefaultOrderAdjustmentType(orderType, EntityPermission.READ_WRITE);
    }

    public OrderAdjustmentTypeDetailValue getDefaultOrderAdjustmentTypeDetailValueForUpdate(OrderType orderType) {
        return getDefaultOrderAdjustmentTypeForUpdate(orderType).getLastDetailForUpdate().getOrderAdjustmentTypeDetailValue().clone();
    }

    private static final Map<EntityPermission, String> getOrderAdjustmentTypesQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM orderadjustmenttypes, orderadjustmenttypedetails " +
                "WHERE ordadjtyp_activedetailid = ordadjtypdt_orderadjustmenttypedetailid " +
                "AND ordadjtypdt_ordtyp_ordertypeid = ? " +
                "ORDER BY ordadjtypdt_sortorder, ordadjtypdt_orderadjustmenttypename");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM orderadjustmenttypes, orderadjustmenttypedetails " +
                "WHERE ordadjtyp_activedetailid = ordadjtypdt_orderadjustmenttypedetailid " +
                "AND ordadjtypdt_ordtyp_ordertypeid = ? " +
                "FOR UPDATE");
        getOrderAdjustmentTypesQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<OrderAdjustmentType> getOrderAdjustmentTypes(OrderType orderType, EntityPermission entityPermission) {
        return OrderAdjustmentTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, getOrderAdjustmentTypesQueries,
                orderType);
    }

    public List<OrderAdjustmentType> getOrderAdjustmentTypes(OrderType orderType) {
        return getOrderAdjustmentTypes(orderType, EntityPermission.READ_ONLY);
    }

    public List<OrderAdjustmentType> getOrderAdjustmentTypesForUpdate(OrderType orderType) {
        return getOrderAdjustmentTypes(orderType, EntityPermission.READ_WRITE);
    }

    public OrderAdjustmentTypeTransfer getOrderAdjustmentTypeTransfer(UserVisit userVisit, OrderAdjustmentType orderAdjustmentType) {
        return orderAdjustmentTypeTransferCache.getOrderAdjustmentTypeTransfer(userVisit, orderAdjustmentType);
    }

    public List<OrderAdjustmentTypeTransfer> getOrderAdjustmentTypeTransfers(UserVisit userVisit, OrderType orderType) {
        var orderAdjustmentTypes = getOrderAdjustmentTypes(orderType);
        List<OrderAdjustmentTypeTransfer> orderAdjustmentTypeTransfers = new ArrayList<>(orderAdjustmentTypes.size());

        orderAdjustmentTypes.forEach((orderAdjustmentType) ->
                orderAdjustmentTypeTransfers.add(orderAdjustmentTypeTransferCache.getOrderAdjustmentTypeTransfer(userVisit, orderAdjustmentType))
        );

        return orderAdjustmentTypeTransfers;
    }

    public OrderAdjustmentTypeChoicesBean getOrderAdjustmentTypeChoices(String defaultOrderAdjustmentTypeChoice, Language language, boolean allowNullChoice,
            OrderType orderType) {
        var orderAdjustmentTypes = getOrderAdjustmentTypes(orderType);
        var size = orderAdjustmentTypes.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultOrderAdjustmentTypeChoice == null) {
                defaultValue = "";
            }
        }

        for(var orderAdjustmentType : orderAdjustmentTypes) {
            var orderAdjustmentTypeDetail = orderAdjustmentType.getLastDetail();

            var label = getBestOrderAdjustmentTypeDescription(orderAdjustmentType, language);
            var value = orderAdjustmentTypeDetail.getOrderAdjustmentTypeName();

            labels.add(label == null? value: label);
            values.add(value);

            var usingDefaultChoice = defaultOrderAdjustmentTypeChoice != null && defaultOrderAdjustmentTypeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && orderAdjustmentTypeDetail.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new OrderAdjustmentTypeChoicesBean(labels, values, defaultValue);
    }

    private void updateOrderAdjustmentTypeFromValue(OrderAdjustmentTypeDetailValue orderAdjustmentTypeDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(orderAdjustmentTypeDetailValue.hasBeenModified()) {
            var orderAdjustmentType = OrderAdjustmentTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     orderAdjustmentTypeDetailValue.getOrderAdjustmentTypePK());
            var orderAdjustmentTypeDetail = orderAdjustmentType.getActiveDetailForUpdate();

            orderAdjustmentTypeDetail.setThruTime(session.getStartTimeLong());
            orderAdjustmentTypeDetail.store();

            var orderType = orderAdjustmentTypeDetail.getOrderType(); // Not updated
            var orderTypePK = orderType.getPrimaryKey(); // Not updated
            var orderAdjustmentTypePK = orderAdjustmentTypeDetail.getOrderAdjustmentTypePK(); // Not updated
            var orderAdjustmentTypeName = orderAdjustmentTypeDetailValue.getOrderAdjustmentTypeName();
            var isDefault = orderAdjustmentTypeDetailValue.getIsDefault();
            var sortOrder = orderAdjustmentTypeDetailValue.getSortOrder();

            if(checkDefault) {
                var defaultOrderAdjustmentType = getDefaultOrderAdjustmentType(orderType);
                var defaultFound = defaultOrderAdjustmentType != null && !defaultOrderAdjustmentType.equals(orderAdjustmentType);

                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultOrderAdjustmentTypeDetailValue = getDefaultOrderAdjustmentTypeDetailValueForUpdate(orderType);

                    defaultOrderAdjustmentTypeDetailValue.setIsDefault(false);
                    updateOrderAdjustmentTypeFromValue(defaultOrderAdjustmentTypeDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }

            orderAdjustmentTypeDetail = OrderAdjustmentTypeDetailFactory.getInstance().create(orderAdjustmentTypePK, orderTypePK, orderAdjustmentTypeName, isDefault, sortOrder,
                    session.getStartTimeLong(), Session.MAX_TIME_LONG);

            orderAdjustmentType.setActiveDetail(orderAdjustmentTypeDetail);
            orderAdjustmentType.setLastDetail(orderAdjustmentTypeDetail);

            sendEvent(orderAdjustmentTypePK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }

    public void updateOrderAdjustmentTypeFromValue(OrderAdjustmentTypeDetailValue orderAdjustmentTypeDetailValue, BasePK updatedBy) {
        updateOrderAdjustmentTypeFromValue(orderAdjustmentTypeDetailValue, true, updatedBy);
    }

    public void deleteOrderAdjustmentType(OrderAdjustmentType orderAdjustmentType, BasePK deletedBy) {
        // TODO: deleteOrderAdjustmentsByOrderAdjustmentType(orderAdjustmentType, deletedBy);
        deleteOrderAdjustmentTypeDescriptionsByOrderAdjustmentType(orderAdjustmentType, deletedBy);

        var orderAdjustmentTypeDetail = orderAdjustmentType.getLastDetailForUpdate();
        orderAdjustmentTypeDetail.setThruTime(session.getStartTimeLong());
        orderAdjustmentType.setActiveDetail(null);
        orderAdjustmentType.store();

        // Check for default, and pick one if necessary
        var orderType = orderAdjustmentTypeDetail.getOrderType();
        var defaultOrderAdjustmentType = getDefaultOrderAdjustmentType(orderType);
        if(defaultOrderAdjustmentType == null) {
            var orderAdjustmentTypes = getOrderAdjustmentTypesForUpdate(orderType);

            if(!orderAdjustmentTypes.isEmpty()) {
                var iter = orderAdjustmentTypes.iterator();
                if(iter.hasNext()) {
                    defaultOrderAdjustmentType = iter.next();
                }
                var orderAdjustmentTypeDetailValue = Objects.requireNonNull(defaultOrderAdjustmentType).getLastDetailForUpdate().getOrderAdjustmentTypeDetailValue().clone();

                orderAdjustmentTypeDetailValue.setIsDefault(true);
                updateOrderAdjustmentTypeFromValue(orderAdjustmentTypeDetailValue, false, deletedBy);
            }
        }

        sendEvent(orderAdjustmentType.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Order Adjustment Type Descriptions
    // --------------------------------------------------------------------------------

    public OrderAdjustmentTypeDescription createOrderAdjustmentTypeDescription(OrderAdjustmentType orderAdjustmentType, Language language, String description, BasePK createdBy) {
        var orderAdjustmentTypeDescription = OrderAdjustmentTypeDescriptionFactory.getInstance().create(orderAdjustmentType, language, description,
                session.getStartTimeLong(), Session.MAX_TIME_LONG);

        sendEvent(orderAdjustmentType.getPrimaryKey(), EventTypes.MODIFY, orderAdjustmentTypeDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return orderAdjustmentTypeDescription;
    }

    private static final Map<EntityPermission, String> getOrderAdjustmentTypeDescriptionQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM orderadjustmenttypedescriptions " +
                "WHERE ordadjtypd_ordadjtyp_orderadjustmenttypeid = ? AND ordadjtypd_lang_languageid = ? AND ordadjtypd_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM orderadjustmenttypedescriptions " +
                "WHERE ordadjtypd_ordadjtyp_orderadjustmenttypeid = ? AND ordadjtypd_lang_languageid = ? AND ordadjtypd_thrutime = ? " +
                "FOR UPDATE");
        getOrderAdjustmentTypeDescriptionQueries = Collections.unmodifiableMap(queryMap);
    }

    private OrderAdjustmentTypeDescription getOrderAdjustmentTypeDescription(OrderAdjustmentType orderAdjustmentType, Language language, EntityPermission entityPermission) {
        return OrderAdjustmentTypeDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, getOrderAdjustmentTypeDescriptionQueries,
                orderAdjustmentType, language, Session.MAX_TIME);
    }

    public OrderAdjustmentTypeDescription getOrderAdjustmentTypeDescription(OrderAdjustmentType orderAdjustmentType, Language language) {
        return getOrderAdjustmentTypeDescription(orderAdjustmentType, language, EntityPermission.READ_ONLY);
    }

    public OrderAdjustmentTypeDescription getOrderAdjustmentTypeDescriptionForUpdate(OrderAdjustmentType orderAdjustmentType, Language language) {
        return getOrderAdjustmentTypeDescription(orderAdjustmentType, language, EntityPermission.READ_WRITE);
    }

    public OrderAdjustmentTypeDescriptionValue getOrderAdjustmentTypeDescriptionValue(OrderAdjustmentTypeDescription orderAdjustmentTypeDescription) {
        return orderAdjustmentTypeDescription == null? null: orderAdjustmentTypeDescription.getOrderAdjustmentTypeDescriptionValue().clone();
    }

    public OrderAdjustmentTypeDescriptionValue getOrderAdjustmentTypeDescriptionValueForUpdate(OrderAdjustmentType orderAdjustmentType, Language language) {
        return getOrderAdjustmentTypeDescriptionValue(getOrderAdjustmentTypeDescriptionForUpdate(orderAdjustmentType, language));
    }

    private static final Map<EntityPermission, String> getOrderAdjustmentTypeDescriptionsByOrderAdjustmentTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM orderadjustmenttypedescriptions, languages " +
                "WHERE ordadjtypd_ordadjtyp_orderadjustmenttypeid = ? AND ordadjtypd_thrutime = ? AND ordadjtypd_lang_languageid = lang_languageid " +
                "ORDER BY lang_sortorder, lang_languageisoname");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM orderadjustmenttypedescriptions " +
                "WHERE ordadjtypd_ordadjtyp_orderadjustmenttypeid = ? AND ordadjtypd_thrutime = ? " +
                "FOR UPDATE");
        getOrderAdjustmentTypeDescriptionsByOrderAdjustmentTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<OrderAdjustmentTypeDescription> getOrderAdjustmentTypeDescriptionsByOrderAdjustmentType(OrderAdjustmentType orderAdjustmentType, EntityPermission entityPermission) {
        return OrderAdjustmentTypeDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, getOrderAdjustmentTypeDescriptionsByOrderAdjustmentTypeQueries,
                orderAdjustmentType, Session.MAX_TIME);
    }

    public List<OrderAdjustmentTypeDescription> getOrderAdjustmentTypeDescriptionsByOrderAdjustmentType(OrderAdjustmentType orderAdjustmentType) {
        return getOrderAdjustmentTypeDescriptionsByOrderAdjustmentType(orderAdjustmentType, EntityPermission.READ_ONLY);
    }

    public List<OrderAdjustmentTypeDescription> getOrderAdjustmentTypeDescriptionsByOrderAdjustmentTypeForUpdate(OrderAdjustmentType orderAdjustmentType) {
        return getOrderAdjustmentTypeDescriptionsByOrderAdjustmentType(orderAdjustmentType, EntityPermission.READ_WRITE);
    }

    public String getBestOrderAdjustmentTypeDescription(OrderAdjustmentType orderAdjustmentType, Language language) {
        String description;
        var orderAdjustmentTypeDescription = getOrderAdjustmentTypeDescription(orderAdjustmentType, language);

        if(orderAdjustmentTypeDescription == null && !language.getIsDefault()) {
            orderAdjustmentTypeDescription = getOrderAdjustmentTypeDescription(orderAdjustmentType, partyControl.getDefaultLanguage());
        }

        if(orderAdjustmentTypeDescription == null) {
            description = orderAdjustmentType.getLastDetail().getOrderAdjustmentTypeName();
        } else {
            description = orderAdjustmentTypeDescription.getDescription();
        }

        return description;
    }

    public OrderAdjustmentTypeDescriptionTransfer getOrderAdjustmentTypeDescriptionTransfer(UserVisit userVisit, OrderAdjustmentTypeDescription orderAdjustmentTypeDescription) {
        return orderAdjustmentTypeDescriptionTransferCache.getOrderAdjustmentTypeDescriptionTransfer(userVisit, orderAdjustmentTypeDescription);
    }

    public List<OrderAdjustmentTypeDescriptionTransfer> getOrderAdjustmentTypeDescriptionTransfersByOrderAdjustmentType(UserVisit userVisit, OrderAdjustmentType orderAdjustmentType) {
        var orderAdjustmentTypeDescriptions = getOrderAdjustmentTypeDescriptionsByOrderAdjustmentType(orderAdjustmentType);
        List<OrderAdjustmentTypeDescriptionTransfer> orderAdjustmentTypeDescriptionTransfers = new ArrayList<>(orderAdjustmentTypeDescriptions.size());

        orderAdjustmentTypeDescriptions.forEach((orderAdjustmentTypeDescription) ->
                orderAdjustmentTypeDescriptionTransfers.add(orderAdjustmentTypeDescriptionTransferCache.getOrderAdjustmentTypeDescriptionTransfer(userVisit, orderAdjustmentTypeDescription))
        );

        return orderAdjustmentTypeDescriptionTransfers;
    }

    public void updateOrderAdjustmentTypeDescriptionFromValue(OrderAdjustmentTypeDescriptionValue orderAdjustmentTypeDescriptionValue, BasePK updatedBy) {
        if(orderAdjustmentTypeDescriptionValue.hasBeenModified()) {
            var orderAdjustmentTypeDescription = OrderAdjustmentTypeDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    orderAdjustmentTypeDescriptionValue.getPrimaryKey());

            orderAdjustmentTypeDescription.setThruTime(session.getStartTimeLong());
            orderAdjustmentTypeDescription.store();

            var orderAdjustmentType = orderAdjustmentTypeDescription.getOrderAdjustmentType();
            var language = orderAdjustmentTypeDescription.getLanguage();
            var description = orderAdjustmentTypeDescriptionValue.getDescription();

            orderAdjustmentTypeDescription = OrderAdjustmentTypeDescriptionFactory.getInstance().create(orderAdjustmentType, language, description,
                    session.getStartTimeLong(), Session.MAX_TIME_LONG);

            sendEvent(orderAdjustmentType.getPrimaryKey(), EventTypes.MODIFY, orderAdjustmentTypeDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteOrderAdjustmentTypeDescription(OrderAdjustmentTypeDescription orderAdjustmentTypeDescription, BasePK deletedBy) {
        orderAdjustmentTypeDescription.setThruTime(session.getStartTimeLong());

        sendEvent(orderAdjustmentTypeDescription.getOrderAdjustmentTypePK(), EventTypes.MODIFY, orderAdjustmentTypeDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);

    }

    public void deleteOrderAdjustmentTypeDescriptionsByOrderAdjustmentType(OrderAdjustmentType orderAdjustmentType, BasePK deletedBy) {
        var orderAdjustmentTypeDescriptions = getOrderAdjustmentTypeDescriptionsByOrderAdjustmentTypeForUpdate(orderAdjustmentType);

        orderAdjustmentTypeDescriptions.forEach((orderAdjustmentTypeDescription) -> 
                deleteOrderAdjustmentTypeDescription(orderAdjustmentTypeDescription, deletedBy)
        );
    }

    // --------------------------------------------------------------------------------
    //   Order Adjustments
    // --------------------------------------------------------------------------------

    public OrderAdjustment createOrderAdjustment(Order order, Integer orderAdjustmentSequence, OrderAdjustmentType orderAdjustmentType, Long amount,
            BasePK createdBy) {
        var orderAdjustment = OrderAdjustmentFactory.getInstance().create();
        var orderAdjustmentDetail = OrderAdjustmentDetailFactory.getInstance().create(orderAdjustment,
                order, orderAdjustmentSequence, orderAdjustmentType, amount, session.getStartTimeLong(), Session.MAX_TIME_LONG);

        // Convert to R/W
        orderAdjustment = OrderAdjustmentFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                orderAdjustment.getPrimaryKey());
        orderAdjustment.setActiveDetail(orderAdjustmentDetail);
        orderAdjustment.setLastDetail(orderAdjustmentDetail);
        orderAdjustment.store();

        sendEvent(orderAdjustment.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return orderAdjustment;
    }

    private List<OrderAdjustment> getOrderAdjustmentsByOrder(Order order, EntityPermission entityPermission) {
        List<OrderAdjustment> orderAdjustments;

        try {
            String query = null;

            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM orderadjustments, orderadjustmentdetails " +
                        "WHERE ordadj_activedetailid = ordadjdt_orderadjustmentdetailid AND ordadjdt_ord_orderid = ? " +
                        "ORDER BY ordadjdt_orderadjustmentsequence";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM orderadjustments, orderadjustmentdetails " +
                        "WHERE ordadj_activedetailid = ordadjdt_orderadjustmentdetailid AND ordadjdt_ord_orderid = ? " +
                        "FOR UPDATE";
            }

            var ps = OrderAdjustmentFactory.getInstance().prepareStatement(query);

            ps.setLong(1, order.getPrimaryKey().getEntityId());

            orderAdjustments = OrderAdjustmentFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return orderAdjustments;
    }

    public List<OrderAdjustment> getOrderAdjustmentsByOrder(Order order) {
        return getOrderAdjustmentsByOrder(order, EntityPermission.READ_ONLY);
    }

    public List<OrderAdjustment> getOrderAdjustmentsByOrderForUpdate(Order order) {
        return getOrderAdjustmentsByOrder(order, EntityPermission.READ_WRITE);
    }

    public void deleteOrderAdjustment(OrderAdjustment orderAdjustment, BasePK deletedBy) {
        var orderAdjustmentDetail = orderAdjustment.getLastDetailForUpdate();
        orderAdjustmentDetail.setThruTime(session.getStartTimeLong());
        orderAdjustment.setActiveDetail(null);
        orderAdjustment.store();

        sendEvent(orderAdjustmentDetail.getOrderPK(), EventTypes.DELETE, null, null, deletedBy);
    }

    public void deleteOrderAdjustmentsByOrder(Order order, BasePK deletedBy) {
        var orderAdjustments = getOrderAdjustmentsByOrderForUpdate(order);

        orderAdjustments.forEach((orderAdjustment) -> 
                deleteOrderAdjustment(orderAdjustment, deletedBy)
        );
    }

}
