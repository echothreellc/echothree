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
import com.echothree.model.control.order.common.choice.OrderLineAdjustmentTypeChoicesBean;
import com.echothree.model.control.order.common.transfer.OrderLineAdjustmentTypeDescriptionTransfer;
import com.echothree.model.control.order.common.transfer.OrderLineAdjustmentTypeTransfer;
import com.echothree.model.data.order.server.entity.OrderLine;
import com.echothree.model.data.order.server.entity.OrderLineAdjustment;
import com.echothree.model.data.order.server.entity.OrderLineAdjustmentType;
import com.echothree.model.data.order.server.entity.OrderLineAdjustmentTypeDescription;
import com.echothree.model.data.order.server.entity.OrderType;
import com.echothree.model.data.order.server.factory.OrderLineAdjustmentDetailFactory;
import com.echothree.model.data.order.server.factory.OrderLineAdjustmentFactory;
import com.echothree.model.data.order.server.factory.OrderLineAdjustmentTypeDescriptionFactory;
import com.echothree.model.data.order.server.factory.OrderLineAdjustmentTypeDetailFactory;
import com.echothree.model.data.order.server.factory.OrderLineAdjustmentTypeFactory;
import com.echothree.model.data.order.server.value.OrderLineAdjustmentTypeDescriptionValue;
import com.echothree.model.data.order.server.value.OrderLineAdjustmentTypeDetailValue;
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
public class OrderLineAdjustmentControl
        extends BaseOrderControl {

    /** Creates a new instance of OrderControl */
    protected OrderLineAdjustmentControl() {
        super();
    }

    // --------------------------------------------------------------------------------
    //   Order Line Adjustment Types
    // --------------------------------------------------------------------------------

    public OrderLineAdjustmentType createOrderLineAdjustmentType(OrderType orderType, String orderLineAdjustmentTypeName, Boolean isDefault, Integer sortOrder,
            BasePK createdBy) {
        var defaultOrderLineAdjustmentType = getDefaultOrderLineAdjustmentType(orderType);
        var defaultFound = defaultOrderLineAdjustmentType != null;

        if(defaultFound && isDefault) {
            var defaultOrderLineAdjustmentTypeDetailValue = getDefaultOrderLineAdjustmentTypeDetailValueForUpdate(orderType);

            defaultOrderLineAdjustmentTypeDetailValue.setIsDefault(false);
            updateOrderLineAdjustmentTypeFromValue(defaultOrderLineAdjustmentTypeDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var orderLineAdjustmentType = OrderLineAdjustmentTypeFactory.getInstance().create();
        var orderLineAdjustmentTypeDetail = OrderLineAdjustmentTypeDetailFactory.getInstance().create(orderLineAdjustmentType,
                orderType, orderLineAdjustmentTypeName, isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);

        // Convert to R/W
        orderLineAdjustmentType = OrderLineAdjustmentTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                orderLineAdjustmentType.getPrimaryKey());
        orderLineAdjustmentType.setActiveDetail(orderLineAdjustmentTypeDetail);
        orderLineAdjustmentType.setLastDetail(orderLineAdjustmentTypeDetail);
        orderLineAdjustmentType.store();

        sendEvent(orderLineAdjustmentType.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return orderLineAdjustmentType;
    }

    private static final Map<EntityPermission, String> getOrderLineAdjustmentTypeByNameQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM orderlineadjustmenttypes, orderlineadjustmenttypedetails " +
                "WHERE ordladjtyp_activedetailid = ordladjtypdt_orderlineadjustmenttypedetailid " +
                "AND ordladjtypdt_ordtyp_ordertypeid = ? AND ordladjtypdt_orderlineadjustmenttypename = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM orderlineadjustmenttypes, orderlineadjustmenttypedetails " +
                "WHERE ordladjtyp_activedetailid = ordladjtypdt_orderlineadjustmenttypedetailid " +
                "AND ordladjtypdt_ordtyp_ordertypeid = ? AND ordladjtypdt_orderlineadjustmenttypename = ? " +
                "FOR UPDATE");
        getOrderLineAdjustmentTypeByNameQueries = Collections.unmodifiableMap(queryMap);
    }

    private OrderLineAdjustmentType getOrderLineAdjustmentTypeByName(OrderType orderType, String orderLineAdjustmentTypeName, EntityPermission entityPermission) {
        return OrderLineAdjustmentTypeFactory.getInstance().getEntityFromQuery(entityPermission, getOrderLineAdjustmentTypeByNameQueries,
                orderType, orderLineAdjustmentTypeName);
    }

    public OrderLineAdjustmentType getOrderLineAdjustmentTypeByName(OrderType orderType, String orderLineAdjustmentTypeName) {
        return getOrderLineAdjustmentTypeByName(orderType, orderLineAdjustmentTypeName, EntityPermission.READ_ONLY);
    }

    public OrderLineAdjustmentType getOrderLineAdjustmentTypeByNameForUpdate(OrderType orderType, String orderLineAdjustmentTypeName) {
        return getOrderLineAdjustmentTypeByName(orderType, orderLineAdjustmentTypeName, EntityPermission.READ_WRITE);
    }

    public OrderLineAdjustmentTypeDetailValue getOrderLineAdjustmentTypeDetailValueForUpdate(OrderLineAdjustmentType orderLineAdjustmentType) {
        return orderLineAdjustmentType == null? null: orderLineAdjustmentType.getLastDetailForUpdate().getOrderLineAdjustmentTypeDetailValue().clone();
    }

    public OrderLineAdjustmentTypeDetailValue getOrderLineAdjustmentTypeDetailValueByNameForUpdate(OrderType orderType, String orderLineAdjustmentTypeName) {
        return getOrderLineAdjustmentTypeDetailValueForUpdate(getOrderLineAdjustmentTypeByNameForUpdate(orderType, orderLineAdjustmentTypeName));
    }

    private static final Map<EntityPermission, String> getDefaultOrderLineAdjustmentTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM orderlineadjustmenttypes, orderlineadjustmenttypedetails " +
                "WHERE ordladjtyp_activedetailid = ordladjtypdt_orderlineadjustmenttypedetailid " +
                "AND ordladjtypdt_ordtyp_ordertypeid = ? AND ordladjtypdt_isdefault = 1");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM orderlineadjustmenttypes, orderlineadjustmenttypedetails " +
                "WHERE ordladjtyp_activedetailid = ordladjtypdt_orderlineadjustmenttypedetailid " +
                "AND ordladjtypdt_ordtyp_ordertypeid = ? AND ordladjtypdt_isdefault = 1 " +
                "FOR UPDATE");
        getDefaultOrderLineAdjustmentTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    private OrderLineAdjustmentType getDefaultOrderLineAdjustmentType(OrderType orderType, EntityPermission entityPermission) {
        return OrderLineAdjustmentTypeFactory.getInstance().getEntityFromQuery(entityPermission, getDefaultOrderLineAdjustmentTypeQueries,
                orderType);
    }

    public OrderLineAdjustmentType getDefaultOrderLineAdjustmentType(OrderType orderType) {
        return getDefaultOrderLineAdjustmentType(orderType, EntityPermission.READ_ONLY);
    }

    public OrderLineAdjustmentType getDefaultOrderLineAdjustmentTypeForUpdate(OrderType orderType) {
        return getDefaultOrderLineAdjustmentType(orderType, EntityPermission.READ_WRITE);
    }

    public OrderLineAdjustmentTypeDetailValue getDefaultOrderLineAdjustmentTypeDetailValueForUpdate(OrderType orderType) {
        return getDefaultOrderLineAdjustmentTypeForUpdate(orderType).getLastDetailForUpdate().getOrderLineAdjustmentTypeDetailValue().clone();
    }

    private static final Map<EntityPermission, String> getOrderLineAdjustmentTypesQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM orderlineadjustmenttypes, orderlineadjustmenttypedetails " +
                "WHERE ordladjtyp_activedetailid = ordladjtypdt_orderlineadjustmenttypedetailid " +
                "AND ordladjtypdt_ordtyp_ordertypeid = ? " +
                "ORDER BY ordladjtypdt_sortorder, ordladjtypdt_orderlineadjustmenttypename");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM orderlineadjustmenttypes, orderlineadjustmenttypedetails " +
                "WHERE ordladjtyp_activedetailid = ordladjtypdt_orderlineadjustmenttypedetailid " +
                "AND ordladjtypdt_ordtyp_ordertypeid = ? " +
                "FOR UPDATE");
        getOrderLineAdjustmentTypesQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<OrderLineAdjustmentType> getOrderLineAdjustmentTypes(OrderType orderType, EntityPermission entityPermission) {
        return OrderLineAdjustmentTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, getOrderLineAdjustmentTypesQueries,
                orderType);
    }

    public List<OrderLineAdjustmentType> getOrderLineAdjustmentTypes(OrderType orderType) {
        return getOrderLineAdjustmentTypes(orderType, EntityPermission.READ_ONLY);
    }

    public List<OrderLineAdjustmentType> getOrderLineAdjustmentTypesForUpdate(OrderType orderType) {
        return getOrderLineAdjustmentTypes(orderType, EntityPermission.READ_WRITE);
    }

    public OrderLineAdjustmentTypeTransfer getOrderLineAdjustmentTypeTransfer(UserVisit userVisit, OrderLineAdjustmentType orderLineAdjustmentType) {
        return orderLineAdjustmentTypeTransferCache.getOrderLineAdjustmentTypeTransfer(userVisit, orderLineAdjustmentType);
    }

    public List<OrderLineAdjustmentTypeTransfer> getOrderLineAdjustmentTypeTransfers(UserVisit userVisit, OrderType orderType) {
        var orderLineAdjustmentTypes = getOrderLineAdjustmentTypes(orderType);
        List<OrderLineAdjustmentTypeTransfer> orderLineAdjustmentTypeTransfers = new ArrayList<>(orderLineAdjustmentTypes.size());

        orderLineAdjustmentTypes.forEach((orderLineAdjustmentType) ->
                orderLineAdjustmentTypeTransfers.add(orderLineAdjustmentTypeTransferCache.getOrderLineAdjustmentTypeTransfer(userVisit, orderLineAdjustmentType))
        );

        return orderLineAdjustmentTypeTransfers;
    }

    public OrderLineAdjustmentTypeChoicesBean getOrderLineAdjustmentTypeChoices(String defaultOrderLineAdjustmentTypeChoice, Language language, boolean allowNullChoice,
            OrderType orderType) {
        var orderLineAdjustmentTypes = getOrderLineAdjustmentTypes(orderType);
        var size = orderLineAdjustmentTypes.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultOrderLineAdjustmentTypeChoice == null) {
                defaultValue = "";
            }
        }

        for(var orderLineAdjustmentType : orderLineAdjustmentTypes) {
            var orderLineAdjustmentTypeDetail = orderLineAdjustmentType.getLastDetail();

            var label = getBestOrderLineAdjustmentTypeDescription(orderLineAdjustmentType, language);
            var value = orderLineAdjustmentTypeDetail.getOrderLineAdjustmentTypeName();

            labels.add(label == null? value: label);
            values.add(value);

            var usingDefaultChoice = defaultOrderLineAdjustmentTypeChoice != null && defaultOrderLineAdjustmentTypeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && orderLineAdjustmentTypeDetail.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new OrderLineAdjustmentTypeChoicesBean(labels, values, defaultValue);
    }

    private void updateOrderLineAdjustmentTypeFromValue(OrderLineAdjustmentTypeDetailValue orderLineAdjustmentTypeDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(orderLineAdjustmentTypeDetailValue.hasBeenModified()) {
            var orderLineAdjustmentType = OrderLineAdjustmentTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     orderLineAdjustmentTypeDetailValue.getOrderLineAdjustmentTypePK());
            var orderLineAdjustmentTypeDetail = orderLineAdjustmentType.getActiveDetailForUpdate();

            orderLineAdjustmentTypeDetail.setThruTime(session.getStartTime());
            orderLineAdjustmentTypeDetail.store();

            var orderType = orderLineAdjustmentTypeDetail.getOrderType(); // Not updated
            var orderTypePK = orderType.getPrimaryKey(); // Not updated
            var orderLineAdjustmentTypePK = orderLineAdjustmentTypeDetail.getOrderLineAdjustmentTypePK(); // Not updated
            var orderLineAdjustmentTypeName = orderLineAdjustmentTypeDetailValue.getOrderLineAdjustmentTypeName();
            var isDefault = orderLineAdjustmentTypeDetailValue.getIsDefault();
            var sortOrder = orderLineAdjustmentTypeDetailValue.getSortOrder();

            if(checkDefault) {
                var defaultOrderLineAdjustmentType = getDefaultOrderLineAdjustmentType(orderType);
                var defaultFound = defaultOrderLineAdjustmentType != null && !defaultOrderLineAdjustmentType.equals(orderLineAdjustmentType);

                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultOrderLineAdjustmentTypeDetailValue = getDefaultOrderLineAdjustmentTypeDetailValueForUpdate(orderType);

                    defaultOrderLineAdjustmentTypeDetailValue.setIsDefault(false);
                    updateOrderLineAdjustmentTypeFromValue(defaultOrderLineAdjustmentTypeDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }

            orderLineAdjustmentTypeDetail = OrderLineAdjustmentTypeDetailFactory.getInstance().create(orderLineAdjustmentTypePK, orderTypePK, orderLineAdjustmentTypeName, isDefault, sortOrder,
                    session.getStartTime(), Session.MAX_TIME);

            orderLineAdjustmentType.setActiveDetail(orderLineAdjustmentTypeDetail);
            orderLineAdjustmentType.setLastDetail(orderLineAdjustmentTypeDetail);

            sendEvent(orderLineAdjustmentTypePK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }

    public void updateOrderLineAdjustmentTypeFromValue(OrderLineAdjustmentTypeDetailValue orderLineAdjustmentTypeDetailValue, BasePK updatedBy) {
        updateOrderLineAdjustmentTypeFromValue(orderLineAdjustmentTypeDetailValue, true, updatedBy);
    }

    public void deleteOrderLineAdjustmentType(OrderLineAdjustmentType orderLineAdjustmentType, BasePK deletedBy) {
        // TODO: deleteOrderLineAdjustmentsByOrderLineAdjustmentType(orderLineAdjustmentType, deletedBy);
        deleteOrderLineAdjustmentTypeDescriptionsByOrderLineAdjustmentType(orderLineAdjustmentType, deletedBy);

        var orderLineAdjustmentTypeDetail = orderLineAdjustmentType.getLastDetailForUpdate();
        orderLineAdjustmentTypeDetail.setThruTime(session.getStartTime());
        orderLineAdjustmentType.setActiveDetail(null);
        orderLineAdjustmentType.store();

        // Check for default, and pick one if necessary
        var orderType = orderLineAdjustmentTypeDetail.getOrderType();
        var defaultOrderLineAdjustmentType = getDefaultOrderLineAdjustmentType(orderType);
        if(defaultOrderLineAdjustmentType == null) {
            var orderLineAdjustmentTypes = getOrderLineAdjustmentTypesForUpdate(orderType);

            if(!orderLineAdjustmentTypes.isEmpty()) {
                var iter = orderLineAdjustmentTypes.iterator();
                if(iter.hasNext()) {
                    defaultOrderLineAdjustmentType = iter.next();
                }
                var orderLineAdjustmentTypeDetailValue = Objects.requireNonNull(defaultOrderLineAdjustmentType).getLastDetailForUpdate().getOrderLineAdjustmentTypeDetailValue().clone();

                orderLineAdjustmentTypeDetailValue.setIsDefault(true);
                updateOrderLineAdjustmentTypeFromValue(orderLineAdjustmentTypeDetailValue, false, deletedBy);
            }
        }

        sendEvent(orderLineAdjustmentType.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Order Line Adjustment Type Descriptions
    // --------------------------------------------------------------------------------

    public OrderLineAdjustmentTypeDescription createOrderLineAdjustmentTypeDescription(OrderLineAdjustmentType orderLineAdjustmentType, Language language, String description, BasePK createdBy) {
        var orderLineAdjustmentTypeDescription = OrderLineAdjustmentTypeDescriptionFactory.getInstance().create(orderLineAdjustmentType, language, description,
                session.getStartTime(), Session.MAX_TIME);

        sendEvent(orderLineAdjustmentType.getPrimaryKey(), EventTypes.MODIFY, orderLineAdjustmentTypeDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return orderLineAdjustmentTypeDescription;
    }

    private static final Map<EntityPermission, String> getOrderLineAdjustmentTypeDescriptionQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM orderlineadjustmenttypedescriptions " +
                "WHERE ordladjtypd_ordladjtyp_orderlineadjustmenttypeid = ? AND ordladjtypd_lang_languageid = ? AND ordladjtypd_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM orderlineadjustmenttypedescriptions " +
                "WHERE ordladjtypd_ordladjtyp_orderlineadjustmenttypeid = ? AND ordladjtypd_lang_languageid = ? AND ordladjtypd_thrutime = ? " +
                "FOR UPDATE");
        getOrderLineAdjustmentTypeDescriptionQueries = Collections.unmodifiableMap(queryMap);
    }

    private OrderLineAdjustmentTypeDescription getOrderLineAdjustmentTypeDescription(OrderLineAdjustmentType orderLineAdjustmentType, Language language, EntityPermission entityPermission) {
        return OrderLineAdjustmentTypeDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, getOrderLineAdjustmentTypeDescriptionQueries,
                orderLineAdjustmentType, language, Session.MAX_TIME);
    }

    public OrderLineAdjustmentTypeDescription getOrderLineAdjustmentTypeDescription(OrderLineAdjustmentType orderLineAdjustmentType, Language language) {
        return getOrderLineAdjustmentTypeDescription(orderLineAdjustmentType, language, EntityPermission.READ_ONLY);
    }

    public OrderLineAdjustmentTypeDescription getOrderLineAdjustmentTypeDescriptionForUpdate(OrderLineAdjustmentType orderLineAdjustmentType, Language language) {
        return getOrderLineAdjustmentTypeDescription(orderLineAdjustmentType, language, EntityPermission.READ_WRITE);
    }

    public OrderLineAdjustmentTypeDescriptionValue getOrderLineAdjustmentTypeDescriptionValue(OrderLineAdjustmentTypeDescription orderLineAdjustmentTypeDescription) {
        return orderLineAdjustmentTypeDescription == null? null: orderLineAdjustmentTypeDescription.getOrderLineAdjustmentTypeDescriptionValue().clone();
    }

    public OrderLineAdjustmentTypeDescriptionValue getOrderLineAdjustmentTypeDescriptionValueForUpdate(OrderLineAdjustmentType orderLineAdjustmentType, Language language) {
        return getOrderLineAdjustmentTypeDescriptionValue(getOrderLineAdjustmentTypeDescriptionForUpdate(orderLineAdjustmentType, language));
    }

    private static final Map<EntityPermission, String> getOrderLineAdjustmentTypeDescriptionsByOrderLineAdjustmentTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM orderlineadjustmenttypedescriptions, languages " +
                "WHERE ordladjtypd_ordladjtyp_orderlineadjustmenttypeid = ? AND ordladjtypd_thrutime = ? AND ordladjtypd_lang_languageid = lang_languageid " +
                "ORDER BY lang_sortorder, lang_languageisoname");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM orderlineadjustmenttypedescriptions " +
                "WHERE ordladjtypd_ordladjtyp_orderlineadjustmenttypeid = ? AND ordladjtypd_thrutime = ? " +
                "FOR UPDATE");
        getOrderLineAdjustmentTypeDescriptionsByOrderLineAdjustmentTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<OrderLineAdjustmentTypeDescription> getOrderLineAdjustmentTypeDescriptionsByOrderLineAdjustmentType(OrderLineAdjustmentType orderLineAdjustmentType, EntityPermission entityPermission) {
        return OrderLineAdjustmentTypeDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, getOrderLineAdjustmentTypeDescriptionsByOrderLineAdjustmentTypeQueries,
                orderLineAdjustmentType, Session.MAX_TIME);
    }

    public List<OrderLineAdjustmentTypeDescription> getOrderLineAdjustmentTypeDescriptionsByOrderLineAdjustmentType(OrderLineAdjustmentType orderLineAdjustmentType) {
        return getOrderLineAdjustmentTypeDescriptionsByOrderLineAdjustmentType(orderLineAdjustmentType, EntityPermission.READ_ONLY);
    }

    public List<OrderLineAdjustmentTypeDescription> getOrderLineAdjustmentTypeDescriptionsByOrderLineAdjustmentTypeForUpdate(OrderLineAdjustmentType orderLineAdjustmentType) {
        return getOrderLineAdjustmentTypeDescriptionsByOrderLineAdjustmentType(orderLineAdjustmentType, EntityPermission.READ_WRITE);
    }

    public String getBestOrderLineAdjustmentTypeDescription(OrderLineAdjustmentType orderLineAdjustmentType, Language language) {
        String description;
        var orderLineAdjustmentTypeDescription = getOrderLineAdjustmentTypeDescription(orderLineAdjustmentType, language);

        if(orderLineAdjustmentTypeDescription == null && !language.getIsDefault()) {
            orderLineAdjustmentTypeDescription = getOrderLineAdjustmentTypeDescription(orderLineAdjustmentType, partyControl.getDefaultLanguage());
        }

        if(orderLineAdjustmentTypeDescription == null) {
            description = orderLineAdjustmentType.getLastDetail().getOrderLineAdjustmentTypeName();
        } else {
            description = orderLineAdjustmentTypeDescription.getDescription();
        }

        return description;
    }

    public OrderLineAdjustmentTypeDescriptionTransfer getOrderLineAdjustmentTypeDescriptionTransfer(UserVisit userVisit, OrderLineAdjustmentTypeDescription orderLineAdjustmentTypeDescription) {
        return orderLineAdjustmentTypeDescriptionTransferCache.getOrderLineAdjustmentTypeDescriptionTransfer(userVisit, orderLineAdjustmentTypeDescription);
    }

    public List<OrderLineAdjustmentTypeDescriptionTransfer> getOrderLineAdjustmentTypeDescriptionTransfersByOrderLineAdjustmentType(UserVisit userVisit, OrderLineAdjustmentType orderLineAdjustmentType) {
        var orderLineAdjustmentTypeDescriptions = getOrderLineAdjustmentTypeDescriptionsByOrderLineAdjustmentType(orderLineAdjustmentType);
        List<OrderLineAdjustmentTypeDescriptionTransfer> orderLineAdjustmentTypeDescriptionTransfers = new ArrayList<>(orderLineAdjustmentTypeDescriptions.size());

        orderLineAdjustmentTypeDescriptions.forEach((orderLineAdjustmentTypeDescription) ->
                orderLineAdjustmentTypeDescriptionTransfers.add(orderLineAdjustmentTypeDescriptionTransferCache.getOrderLineAdjustmentTypeDescriptionTransfer(userVisit, orderLineAdjustmentTypeDescription))
        );

        return orderLineAdjustmentTypeDescriptionTransfers;
    }

    public void updateOrderLineAdjustmentTypeDescriptionFromValue(OrderLineAdjustmentTypeDescriptionValue orderLineAdjustmentTypeDescriptionValue, BasePK updatedBy) {
        if(orderLineAdjustmentTypeDescriptionValue.hasBeenModified()) {
            var orderLineAdjustmentTypeDescription = OrderLineAdjustmentTypeDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    orderLineAdjustmentTypeDescriptionValue.getPrimaryKey());

            orderLineAdjustmentTypeDescription.setThruTime(session.getStartTime());
            orderLineAdjustmentTypeDescription.store();

            var orderLineAdjustmentType = orderLineAdjustmentTypeDescription.getOrderLineAdjustmentType();
            var language = orderLineAdjustmentTypeDescription.getLanguage();
            var description = orderLineAdjustmentTypeDescriptionValue.getDescription();

            orderLineAdjustmentTypeDescription = OrderLineAdjustmentTypeDescriptionFactory.getInstance().create(orderLineAdjustmentType, language, description,
                    session.getStartTime(), Session.MAX_TIME);

            sendEvent(orderLineAdjustmentType.getPrimaryKey(), EventTypes.MODIFY, orderLineAdjustmentTypeDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteOrderLineAdjustmentTypeDescription(OrderLineAdjustmentTypeDescription orderLineAdjustmentTypeDescription, BasePK deletedBy) {
        orderLineAdjustmentTypeDescription.setThruTime(session.getStartTime());

        sendEvent(orderLineAdjustmentTypeDescription.getOrderLineAdjustmentTypePK(), EventTypes.MODIFY, orderLineAdjustmentTypeDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);

    }

    public void deleteOrderLineAdjustmentTypeDescriptionsByOrderLineAdjustmentType(OrderLineAdjustmentType orderLineAdjustmentType, BasePK deletedBy) {
        var orderLineAdjustmentTypeDescriptions = getOrderLineAdjustmentTypeDescriptionsByOrderLineAdjustmentTypeForUpdate(orderLineAdjustmentType);

        orderLineAdjustmentTypeDescriptions.forEach((orderLineAdjustmentTypeDescription) -> 
                deleteOrderLineAdjustmentTypeDescription(orderLineAdjustmentTypeDescription, deletedBy)
        );
    }

    // --------------------------------------------------------------------------------
    //   Order Line Adjustments
    // --------------------------------------------------------------------------------

    public OrderLineAdjustment createOrderLineAdjustment(OrderLine orderLine, Integer orderLineAdjustmentSequence,
            OrderLineAdjustmentType orderLineAdjustmentType, Long amount, BasePK createdBy) {
        var orderLineAdjustment = OrderLineAdjustmentFactory.getInstance().create();
        var orderLineAdjustmentDetail = OrderLineAdjustmentDetailFactory.getInstance().create(
                orderLineAdjustment, orderLine, orderLineAdjustmentSequence, orderLineAdjustmentType, amount,
                session.getStartTime(), Session.MAX_TIME);

        // Convert to R/W
        orderLineAdjustment = OrderLineAdjustmentFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                orderLineAdjustment.getPrimaryKey());
        orderLineAdjustment.setActiveDetail(orderLineAdjustmentDetail);
        orderLineAdjustment.setLastDetail(orderLineAdjustmentDetail);
        orderLineAdjustment.store();

        sendEvent(orderLineAdjustment.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return orderLineAdjustment;
    }

    private List<OrderLineAdjustment> getOrderLineAdjustmentsByOrderLine(OrderLine orderLine, EntityPermission entityPermission) {
        List<OrderLineAdjustment> orderLineAdjustments;

        try {
            String query = null;

            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM orderlineadjustments, orderlineadjustmentdetails " +
                        "WHERE ordladj_activedetailid = ordladjdt_orderlineadjustmentdetailid AND ordladjdt_ordl_orderlineid = ? " +
                        "ORDER BY ordladjdt_orderlineadjustmentsequence";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM orderlineadjustments, orderlineadjustmentdetails " +
                        "WHERE ordladj_activedetailid = ordladjdt_orderlineadjustmentdetailid AND ordladjdt_ordl_orderlineid = ? " +
                        "FOR UPDATE";
            }

            var ps = OrderLineAdjustmentFactory.getInstance().prepareStatement(query);

            ps.setLong(1, orderLine.getPrimaryKey().getEntityId());

            orderLineAdjustments = OrderLineAdjustmentFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return orderLineAdjustments;
    }

    public List<OrderLineAdjustment> getOrderLineAdjustmentsByOrderLine(OrderLine orderLine) {
        return getOrderLineAdjustmentsByOrderLine(orderLine, EntityPermission.READ_ONLY);
    }

    public List<OrderLineAdjustment> getOrderLineAdjustmentsByOrderLineForUpdate(OrderLine orderLine) {
        return getOrderLineAdjustmentsByOrderLine(orderLine, EntityPermission.READ_WRITE);
    }

    public void deleteOrderLineAdjustment(OrderLineAdjustment orderLineAdjustment, BasePK deletedBy) {
        var orderLineAdjustmentDetail = orderLineAdjustment.getLastDetailForUpdate();
        orderLineAdjustmentDetail.setThruTime(session.getStartTime());
        orderLineAdjustment.setActiveDetail(null);
        orderLineAdjustment.store();

        sendEvent(orderLineAdjustmentDetail.getOrderLinePK(), EventTypes.DELETE, null, null, deletedBy);
    }

    public void deleteOrderLineAdjustmentsByOrderLine(OrderLine orderLine, BasePK deletedBy) {
        var orderLineAdjustments = getOrderLineAdjustmentsByOrderLineForUpdate(orderLine);

        orderLineAdjustments.forEach((orderLineAdjustment) -> 
                deleteOrderLineAdjustment(orderLineAdjustment, deletedBy)
        );
    }

}
