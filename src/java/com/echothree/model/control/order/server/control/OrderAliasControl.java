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
import com.echothree.model.control.order.common.choice.OrderAliasTypeChoicesBean;
import com.echothree.model.control.order.common.transfer.OrderAliasTransfer;
import com.echothree.model.control.order.common.transfer.OrderAliasTypeDescriptionTransfer;
import com.echothree.model.control.order.common.transfer.OrderAliasTypeTransfer;
import com.echothree.model.data.order.server.entity.Order;
import com.echothree.model.data.order.server.entity.OrderAlias;
import com.echothree.model.data.order.server.entity.OrderAliasType;
import com.echothree.model.data.order.server.entity.OrderAliasTypeDescription;
import com.echothree.model.data.order.server.entity.OrderType;
import com.echothree.model.data.order.server.factory.OrderAliasFactory;
import com.echothree.model.data.order.server.factory.OrderAliasTypeDescriptionFactory;
import com.echothree.model.data.order.server.factory.OrderAliasTypeDetailFactory;
import com.echothree.model.data.order.server.factory.OrderAliasTypeFactory;
import com.echothree.model.data.order.server.value.OrderAliasTypeDescriptionValue;
import com.echothree.model.data.order.server.value.OrderAliasTypeDetailValue;
import com.echothree.model.data.order.server.value.OrderAliasValue;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class OrderAliasControl
        extends BaseOrderControl {

    /** Creates a new instance of OrderControl */
    protected OrderAliasControl() {
        super();
    }

    // --------------------------------------------------------------------------------
    //   Order Alias Types
    // --------------------------------------------------------------------------------

    public OrderAliasType createOrderAliasType(OrderType orderType, String orderAliasTypeName, String validationPattern, Boolean isDefault, Integer sortOrder,
            BasePK createdBy) {
        var defaultOrderAliasType = getDefaultOrderAliasType(orderType);
        var defaultFound = defaultOrderAliasType != null;

        if(defaultFound && isDefault) {
            var defaultOrderAliasTypeDetailValue = getDefaultOrderAliasTypeDetailValueForUpdate(orderType);

            defaultOrderAliasTypeDetailValue.setIsDefault(false);
            updateOrderAliasTypeFromValue(defaultOrderAliasTypeDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var orderAliasType = OrderAliasTypeFactory.getInstance().create();
        var orderAliasTypeDetail = OrderAliasTypeDetailFactory.getInstance().create(orderAliasType, orderType, orderAliasTypeName,
                validationPattern, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        // Convert to R/W
        orderAliasType = OrderAliasTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, orderAliasType.getPrimaryKey());
        orderAliasType.setActiveDetail(orderAliasTypeDetail);
        orderAliasType.setLastDetail(orderAliasTypeDetail);
        orderAliasType.store();

        sendEvent(orderAliasType.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return orderAliasType;
    }

    private static final Map<EntityPermission, String> getOrderAliasTypeByNameQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM orderaliastypes, orderaliastypedetails " +
                "WHERE ordaltyp_activedetailid = ordaltypdt_orderaliastypedetailid AND ordaltypdt_ordtyp_ordertypeid = ? " +
                "AND ordaltypdt_orderaliastypename = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM orderaliastypes, orderaliastypedetails " +
                "WHERE ordaltyp_activedetailid = ordaltypdt_orderaliastypedetailid AND ordaltypdt_ordtyp_ordertypeid = ? " +
                "AND ordaltypdt_orderaliastypename = ? " +
                "FOR UPDATE");
        getOrderAliasTypeByNameQueries = Collections.unmodifiableMap(queryMap);
    }

    private OrderAliasType getOrderAliasTypeByName(OrderType orderType, String orderAliasTypeName, EntityPermission entityPermission) {
        return OrderAliasTypeFactory.getInstance().getEntityFromQuery(entityPermission, getOrderAliasTypeByNameQueries,
                orderType, orderAliasTypeName);
    }

    public OrderAliasType getOrderAliasTypeByName(OrderType orderType, String orderAliasTypeName) {
        return getOrderAliasTypeByName(orderType, orderAliasTypeName, EntityPermission.READ_ONLY);
    }

    public OrderAliasType getOrderAliasTypeByNameForUpdate(OrderType orderType, String orderAliasTypeName) {
        return getOrderAliasTypeByName(orderType, orderAliasTypeName, EntityPermission.READ_WRITE);
    }

    public OrderAliasTypeDetailValue getOrderAliasTypeDetailValueForUpdate(OrderAliasType orderAliasType) {
        return orderAliasType == null? null: orderAliasType.getLastDetailForUpdate().getOrderAliasTypeDetailValue().clone();
    }

    public OrderAliasTypeDetailValue getOrderAliasTypeDetailValueByNameForUpdate(OrderType orderType,
            String orderAliasTypeName) {
        return getOrderAliasTypeDetailValueForUpdate(getOrderAliasTypeByNameForUpdate(orderType, orderAliasTypeName));
    }

    private static final Map<EntityPermission, String> getDefaultOrderAliasTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM orderaliastypes, orderaliastypedetails " +
                "WHERE ordaltyp_activedetailid = ordaltypdt_orderaliastypedetailid AND ordaltypdt_ordtyp_ordertypeid = ? " +
                "AND ordaltypdt_isdefault = 1");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM orderaliastypes, orderaliastypedetails " +
                "WHERE ordaltyp_activedetailid = ordaltypdt_orderaliastypedetailid AND ordaltypdt_ordtyp_ordertypeid = ? " +
                "AND ordaltypdt_isdefault = 1 " +
                "FOR UPDATE");
        getDefaultOrderAliasTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    private OrderAliasType getDefaultOrderAliasType(OrderType orderType, EntityPermission entityPermission) {
        return OrderAliasTypeFactory.getInstance().getEntityFromQuery(entityPermission, getDefaultOrderAliasTypeQueries, orderType);
    }

    public OrderAliasType getDefaultOrderAliasType(OrderType orderType) {
        return getDefaultOrderAliasType(orderType, EntityPermission.READ_ONLY);
    }

    public OrderAliasType getDefaultOrderAliasTypeForUpdate(OrderType orderType) {
        return getDefaultOrderAliasType(orderType, EntityPermission.READ_WRITE);
    }

    public OrderAliasTypeDetailValue getDefaultOrderAliasTypeDetailValueForUpdate(OrderType orderType) {
        return getDefaultOrderAliasTypeForUpdate(orderType).getLastDetailForUpdate().getOrderAliasTypeDetailValue().clone();
    }

    private static final Map<EntityPermission, String> getOrderAliasTypesQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM orderaliastypes, orderaliastypedetails " +
                "WHERE ordaltyp_activedetailid = ordaltypdt_orderaliastypedetailid AND ordaltypdt_ordtyp_ordertypeid = ? " +
                "ORDER BY ordaltypdt_sortorder, ordaltypdt_orderaliastypename");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM orderaliastypes, orderaliastypedetails " +
                "WHERE ordaltyp_activedetailid = ordaltypdt_orderaliastypedetailid AND ordaltypdt_ordtyp_ordertypeid = ? " +
                "FOR UPDATE");
        getOrderAliasTypesQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<OrderAliasType> getOrderAliasTypes(OrderType orderType, EntityPermission entityPermission) {
        return OrderAliasTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, getOrderAliasTypesQueries, orderType);
    }

    public List<OrderAliasType> getOrderAliasTypes(OrderType orderType) {
        return getOrderAliasTypes(orderType, EntityPermission.READ_ONLY);
    }

    public List<OrderAliasType> getOrderAliasTypesForUpdate(OrderType orderType) {
        return getOrderAliasTypes(orderType, EntityPermission.READ_WRITE);
    }

    public OrderAliasTypeTransfer getOrderAliasTypeTransfer(UserVisit userVisit, OrderAliasType orderAliasType) {
        return orderTransferCaches.getOrderAliasTypeTransferCache().getOrderAliasTypeTransfer(userVisit, orderAliasType);
    }

    public List<OrderAliasTypeTransfer> getOrderAliasTypeTransfers(UserVisit userVisit, OrderType orderType) {
        var orderAliasTypes = getOrderAliasTypes(orderType);
        List<OrderAliasTypeTransfer> orderAliasTypeTransfers = new ArrayList<>(orderAliasTypes.size());
        var orderAliasTypeTransferCache = orderTransferCaches.getOrderAliasTypeTransferCache();

        orderAliasTypes.forEach((orderAliasType) ->
                orderAliasTypeTransfers.add(orderAliasTypeTransferCache.getOrderAliasTypeTransfer(userVisit, orderAliasType))
        );

        return orderAliasTypeTransfers;
    }

    public OrderAliasTypeChoicesBean getOrderAliasTypeChoices(String defaultOrderAliasTypeChoice, Language language,
            boolean allowNullChoice, OrderType orderType) {
        var orderAliasTypes = getOrderAliasTypes(orderType);
        var size = orderAliasTypes.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultOrderAliasTypeChoice == null) {
                defaultValue = "";
            }
        }

        for(var orderAliasType : orderAliasTypes) {
            var orderAliasTypeDetail = orderAliasType.getLastDetail();

            var label = getBestOrderAliasTypeDescription(orderAliasType, language);
            var value = orderAliasTypeDetail.getOrderAliasTypeName();

            labels.add(label == null? value: label);
            values.add(value);

            var usingDefaultChoice = defaultOrderAliasTypeChoice != null && defaultOrderAliasTypeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && orderAliasTypeDetail.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new OrderAliasTypeChoicesBean(labels, values, defaultValue);
    }

    private void updateOrderAliasTypeFromValue(OrderAliasTypeDetailValue orderAliasTypeDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(orderAliasTypeDetailValue.hasBeenModified()) {
            var orderAliasType = OrderAliasTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    orderAliasTypeDetailValue.getOrderAliasTypePK());
            var orderAliasTypeDetail = orderAliasType.getActiveDetailForUpdate();

            orderAliasTypeDetail.setThruTime(session.START_TIME_LONG);
            orderAliasTypeDetail.store();

            var orderAliasTypePK = orderAliasTypeDetail.getOrderAliasTypePK();
            var orderType = orderAliasTypeDetail.getOrderType();
            var orderTypePK = orderType.getPrimaryKey();
            var orderAliasTypeName = orderAliasTypeDetailValue.getOrderAliasTypeName();
            var validationPattern = orderAliasTypeDetailValue.getValidationPattern();
            var isDefault = orderAliasTypeDetailValue.getIsDefault();
            var sortOrder = orderAliasTypeDetailValue.getSortOrder();

            if(checkDefault) {
                var defaultOrderAliasType = getDefaultOrderAliasType(orderType);
                var defaultFound = defaultOrderAliasType != null && !defaultOrderAliasType.equals(orderAliasType);

                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultOrderAliasTypeDetailValue = getDefaultOrderAliasTypeDetailValueForUpdate(orderType);

                    defaultOrderAliasTypeDetailValue.setIsDefault(false);
                    updateOrderAliasTypeFromValue(defaultOrderAliasTypeDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }

            orderAliasTypeDetail = OrderAliasTypeDetailFactory.getInstance().create(orderAliasTypePK, orderTypePK, orderAliasTypeName,
                    validationPattern, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);

            orderAliasType.setActiveDetail(orderAliasTypeDetail);
            orderAliasType.setLastDetail(orderAliasTypeDetail);

            sendEvent(orderAliasTypePK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }

    public void updateOrderAliasTypeFromValue(OrderAliasTypeDetailValue orderAliasTypeDetailValue, BasePK updatedBy) {
        updateOrderAliasTypeFromValue(orderAliasTypeDetailValue, true, updatedBy);
    }

    public void deleteOrderAliasType(OrderAliasType orderAliasType, BasePK deletedBy) {
        deleteOrderAliasesByOrderAliasType(orderAliasType, deletedBy);
        deleteOrderAliasTypeDescriptionsByOrderAliasType(orderAliasType, deletedBy);

        var orderAliasTypeDetail = orderAliasType.getLastDetailForUpdate();
        orderAliasTypeDetail.setThruTime(session.START_TIME_LONG);
        orderAliasType.setActiveDetail(null);
        orderAliasType.store();

        // Check for default, and pick one if necessary
        var orderType = orderAliasTypeDetail.getOrderType();
        var defaultOrderAliasType = getDefaultOrderAliasType(orderType);
        if(defaultOrderAliasType == null) {
            var orderAliasTypes = getOrderAliasTypesForUpdate(orderType);

            if(!orderAliasTypes.isEmpty()) {
                var iter = orderAliasTypes.iterator();
                if(iter.hasNext()) {
                    defaultOrderAliasType = iter.next();
                }
                var orderAliasTypeDetailValue = Objects.requireNonNull(defaultOrderAliasType).getLastDetailForUpdate().getOrderAliasTypeDetailValue().clone();

                orderAliasTypeDetailValue.setIsDefault(true);
                updateOrderAliasTypeFromValue(orderAliasTypeDetailValue, false, deletedBy);
            }
        }

        sendEvent(orderAliasType.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }

    public void deleteOrderAliasTypes(List<OrderAliasType> orderAliasTypes, BasePK deletedBy) {
        orderAliasTypes.forEach((orderAliasType) -> 
                deleteOrderAliasType(orderAliasType, deletedBy)
        );
    }

    public void deleteOrderAliasTypesByOrderType(OrderType orderType, BasePK deletedBy) {
        deleteOrderAliasTypes(getOrderAliasTypesForUpdate(orderType), deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Order Alias Type Descriptions
    // --------------------------------------------------------------------------------

    public OrderAliasTypeDescription createOrderAliasTypeDescription(OrderAliasType orderAliasType, Language language, String description, BasePK createdBy) {
        var orderAliasTypeDescription = OrderAliasTypeDescriptionFactory.getInstance().create(orderAliasType, language,
                description, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEvent(orderAliasType.getPrimaryKey(), EventTypes.MODIFY, orderAliasTypeDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return orderAliasTypeDescription;
    }

    private static final Map<EntityPermission, String> getOrderAliasTypeDescriptionQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM orderaliastypedescriptions " +
                "WHERE ordaltypd_ordaltyp_orderaliastypeid = ? AND ordaltypd_lang_languageid = ? AND ordaltypd_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM orderaliastypedescriptions " +
                "WHERE ordaltypd_ordaltyp_orderaliastypeid = ? AND ordaltypd_lang_languageid = ? AND ordaltypd_thrutime = ? " +
                "FOR UPDATE");
        getOrderAliasTypeDescriptionQueries = Collections.unmodifiableMap(queryMap);
    }

    private OrderAliasTypeDescription getOrderAliasTypeDescription(OrderAliasType orderAliasType, Language language, EntityPermission entityPermission) {
        return OrderAliasTypeDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, getOrderAliasTypeDescriptionQueries,
                orderAliasType, language, Session.MAX_TIME);
    }

    public OrderAliasTypeDescription getOrderAliasTypeDescription(OrderAliasType orderAliasType, Language language) {
        return getOrderAliasTypeDescription(orderAliasType, language, EntityPermission.READ_ONLY);
    }

    public OrderAliasTypeDescription getOrderAliasTypeDescriptionForUpdate(OrderAliasType orderAliasType, Language language) {
        return getOrderAliasTypeDescription(orderAliasType, language, EntityPermission.READ_WRITE);
    }

    public OrderAliasTypeDescriptionValue getOrderAliasTypeDescriptionValue(OrderAliasTypeDescription orderAliasTypeDescription) {
        return orderAliasTypeDescription == null? null: orderAliasTypeDescription.getOrderAliasTypeDescriptionValue().clone();
    }

    public OrderAliasTypeDescriptionValue getOrderAliasTypeDescriptionValueForUpdate(OrderAliasType orderAliasType, Language language) {
        return getOrderAliasTypeDescriptionValue(getOrderAliasTypeDescriptionForUpdate(orderAliasType, language));
    }

    private static final Map<EntityPermission, String> getOrderAliasTypeDescriptionsByOrderAliasTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM orderaliastypedescriptions, languages " +
                "WHERE ordaltypd_ordaltyp_orderaliastypeid = ? AND ordaltypd_thrutime = ? AND ordaltypd_lang_languageid = lang_languageid " +
                "ORDER BY lang_sortorder, lang_languageisoname");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM orderaliastypedescriptions " +
                "WHERE ordaltypd_ordaltyp_orderaliastypeid = ? AND ordaltypd_thrutime = ? " +
                "FOR UPDATE");
        getOrderAliasTypeDescriptionsByOrderAliasTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<OrderAliasTypeDescription> getOrderAliasTypeDescriptionsByOrderAliasType(OrderAliasType orderAliasType, EntityPermission entityPermission) {
        return OrderAliasTypeDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, getOrderAliasTypeDescriptionsByOrderAliasTypeQueries,
                orderAliasType, Session.MAX_TIME);
    }

    public List<OrderAliasTypeDescription> getOrderAliasTypeDescriptionsByOrderAliasType(OrderAliasType orderAliasType) {
        return getOrderAliasTypeDescriptionsByOrderAliasType(orderAliasType, EntityPermission.READ_ONLY);
    }

    public List<OrderAliasTypeDescription> getOrderAliasTypeDescriptionsByOrderAliasTypeForUpdate(OrderAliasType orderAliasType) {
        return getOrderAliasTypeDescriptionsByOrderAliasType(orderAliasType, EntityPermission.READ_WRITE);
    }

    public String getBestOrderAliasTypeDescription(OrderAliasType orderAliasType, Language language) {
        String description;
        var orderAliasTypeDescription = getOrderAliasTypeDescription(orderAliasType, language);

        if(orderAliasTypeDescription == null && !language.getIsDefault()) {
            orderAliasTypeDescription = getOrderAliasTypeDescription(orderAliasType, partyControl.getDefaultLanguage());
        }

        if(orderAliasTypeDescription == null) {
            description = orderAliasType.getLastDetail().getOrderAliasTypeName();
        } else {
            description = orderAliasTypeDescription.getDescription();
        }

        return description;
    }

    public OrderAliasTypeDescriptionTransfer getOrderAliasTypeDescriptionTransfer(UserVisit userVisit, OrderAliasTypeDescription orderAliasTypeDescription) {
        return orderTransferCaches.getOrderAliasTypeDescriptionTransferCache().getOrderAliasTypeDescriptionTransfer(userVisit, orderAliasTypeDescription);
    }

    public List<OrderAliasTypeDescriptionTransfer> getOrderAliasTypeDescriptionTransfersByOrderAliasType(UserVisit userVisit, OrderAliasType orderAliasType) {
        var orderAliasTypeDescriptions = getOrderAliasTypeDescriptionsByOrderAliasType(orderAliasType);
        List<OrderAliasTypeDescriptionTransfer> orderAliasTypeDescriptionTransfers = new ArrayList<>(orderAliasTypeDescriptions.size());
        var orderAliasTypeDescriptionTransferCache = orderTransferCaches.getOrderAliasTypeDescriptionTransferCache();

        orderAliasTypeDescriptions.forEach((orderAliasTypeDescription) ->
                orderAliasTypeDescriptionTransfers.add(orderAliasTypeDescriptionTransferCache.getOrderAliasTypeDescriptionTransfer(userVisit, orderAliasTypeDescription))
        );

        return orderAliasTypeDescriptionTransfers;
    }

    public void updateOrderAliasTypeDescriptionFromValue(OrderAliasTypeDescriptionValue orderAliasTypeDescriptionValue, BasePK updatedBy) {
        if(orderAliasTypeDescriptionValue.hasBeenModified()) {
            var orderAliasTypeDescription = OrderAliasTypeDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     orderAliasTypeDescriptionValue.getPrimaryKey());

            orderAliasTypeDescription.setThruTime(session.START_TIME_LONG);
            orderAliasTypeDescription.store();

            var orderAliasType = orderAliasTypeDescription.getOrderAliasType();
            var language = orderAliasTypeDescription.getLanguage();
            var description = orderAliasTypeDescriptionValue.getDescription();

            orderAliasTypeDescription = OrderAliasTypeDescriptionFactory.getInstance().create(orderAliasType, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);

            sendEvent(orderAliasType.getPrimaryKey(), EventTypes.MODIFY, orderAliasTypeDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteOrderAliasTypeDescription(OrderAliasTypeDescription orderAliasTypeDescription, BasePK deletedBy) {
        orderAliasTypeDescription.setThruTime(session.START_TIME_LONG);

        sendEvent(orderAliasTypeDescription.getOrderAliasTypePK(), EventTypes.MODIFY, orderAliasTypeDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);

    }

    public void deleteOrderAliasTypeDescriptionsByOrderAliasType(OrderAliasType orderAliasType, BasePK deletedBy) {
        var orderAliasTypeDescriptions = getOrderAliasTypeDescriptionsByOrderAliasTypeForUpdate(orderAliasType);

        orderAliasTypeDescriptions.forEach((orderAliasTypeDescription) -> 
                deleteOrderAliasTypeDescription(orderAliasTypeDescription, deletedBy)
        );
    }

    // --------------------------------------------------------------------------------
    //   Order Aliases
    // --------------------------------------------------------------------------------

    public OrderAlias createOrderAlias(Order order, OrderAliasType orderAliasType, String alias, BasePK createdBy) {
        var orderAlias = OrderAliasFactory.getInstance().create(order, orderAliasType, alias, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEvent(order.getPrimaryKey(), EventTypes.MODIFY, orderAlias.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return orderAlias;
    }

    private static final Map<EntityPermission, String> getOrderAliasQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM orderaliases " +
                "WHERE ordal_ord_orderid = ? AND ordal_ordaltyp_orderaliastypeid = ? AND ordal_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM orderaliases " +
                "WHERE ordal_ord_orderid = ? AND ordal_ordaltyp_orderaliastypeid = ? AND ordal_thrutime = ? " +
                "FOR UPDATE");
        getOrderAliasQueries = Collections.unmodifiableMap(queryMap);
    }

    private OrderAlias getOrderAlias(Order order, OrderAliasType orderAliasType, EntityPermission entityPermission) {
        return OrderAliasFactory.getInstance().getEntityFromQuery(entityPermission, getOrderAliasQueries,
                order, orderAliasType, Session.MAX_TIME);
    }

    public OrderAlias getOrderAlias(Order order, OrderAliasType orderAliasType) {
        return getOrderAlias(order, orderAliasType, EntityPermission.READ_ONLY);
    }

    public OrderAlias getOrderAliasForUpdate(Order order, OrderAliasType orderAliasType) {
        return getOrderAlias(order, orderAliasType, EntityPermission.READ_WRITE);
    }

    public OrderAliasValue getOrderAliasValue(OrderAlias orderAlias) {
        return orderAlias == null? null: orderAlias.getOrderAliasValue().clone();
    }

    public OrderAliasValue getOrderAliasValueForUpdate(Order order, OrderAliasType orderAliasType) {
        return getOrderAliasValue(getOrderAliasForUpdate(order, orderAliasType));
    }

    private static final Map<EntityPermission, String> getOrderAliasByAliasQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM orderaliases " +
                "WHERE ordal_ordaltyp_orderaliastypeid = ? AND ordal_alias = ? AND ordal_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM orderaliases " +
                "WHERE ordal_ordaltyp_orderaliastypeid = ? AND ordal_alias = ? AND ordal_thrutime = ? " +
                "FOR UPDATE");
        getOrderAliasByAliasQueries = Collections.unmodifiableMap(queryMap);
    }

    private OrderAlias getOrderAliasByAlias(OrderAliasType orderAliasType, String alias, EntityPermission entityPermission) {
        return OrderAliasFactory.getInstance().getEntityFromQuery(entityPermission, getOrderAliasByAliasQueries, orderAliasType, alias, Session.MAX_TIME);
    }

    public OrderAlias getOrderAliasByAlias(OrderAliasType orderAliasType, String alias) {
        return getOrderAliasByAlias(orderAliasType, alias, EntityPermission.READ_ONLY);
    }

    public OrderAlias getOrderAliasByAliasForUpdate(OrderAliasType orderAliasType, String alias) {
        return getOrderAliasByAlias(orderAliasType, alias, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getOrderAliasesByOrderQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM orderaliases, orderaliastypes, orderaliastypedetails " +
                "WHERE ordal_ord_orderid = ? AND ordal_thrutime = ? " +
                "AND ordal_ordaltyp_orderaliastypeid = ordaltyp_orderaliastypeid AND ordaltyp_lastdetailid = ordaltypdt_orderaliastypedetailid" +
                "ORDER BY ordaltypdt_sortorder, ordaltypdt_orderaliastypename");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM orderaliases " +
                "WHERE ordal_ord_orderid = ? AND ordal_thrutime = ? " +
                "FOR UPDATE");
        getOrderAliasesByOrderQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<OrderAlias> getOrderAliasesByOrder(Order order, EntityPermission entityPermission) {
        return OrderAliasFactory.getInstance().getEntitiesFromQuery(entityPermission, getOrderAliasesByOrderQueries,
                order, Session.MAX_TIME);
    }

    public List<OrderAlias> getOrderAliasesByOrder(Order order) {
        return getOrderAliasesByOrder(order, EntityPermission.READ_ONLY);
    }

    public List<OrderAlias> getOrderAliasesByOrderForUpdate(Order order) {
        return getOrderAliasesByOrder(order, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getOrderAliasesByOrderAliasTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM orderaliases, orderes, orderdetails " +
                "WHERE ordal_ordaltyp_orderaliastypeid = ? AND ordal_thrutime = ? " +
                "AND ordal_ord_orderid = ord_orderid AND ord_lastdetailid = orddt_orderdetailid " +
                "ORDER BY lang_sortorder, lang_languageisoname");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM orderaliases " +
                "WHERE ordal_ordaltyp_orderaliastypeid = ? AND ordal_thrutime = ? " +
                "FOR UPDATE");
        getOrderAliasesByOrderAliasTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<OrderAlias> getOrderAliasesByOrderAliasType(OrderAliasType orderAliasType, EntityPermission entityPermission) {
        return OrderAliasFactory.getInstance().getEntitiesFromQuery(entityPermission, getOrderAliasesByOrderAliasTypeQueries,
                orderAliasType, Session.MAX_TIME);
    }

    public List<OrderAlias> getOrderAliasesByOrderAliasType(OrderAliasType orderAliasType) {
        return getOrderAliasesByOrderAliasType(orderAliasType, EntityPermission.READ_ONLY);
    }

    public List<OrderAlias> getOrderAliasesByOrderAliasTypeForUpdate(OrderAliasType orderAliasType) {
        return getOrderAliasesByOrderAliasType(orderAliasType, EntityPermission.READ_WRITE);
    }

    public OrderAliasTransfer getOrderAliasTransfer(UserVisit userVisit, OrderAlias orderAlias) {
        return orderTransferCaches.getOrderAliasTransferCache().getOrderAliasTransfer(userVisit, orderAlias);
    }

    public List<OrderAliasTransfer> getOrderAliasTransfersByOrder(UserVisit userVisit, Order order) {
        var orderaliases = getOrderAliasesByOrder(order);
        List<OrderAliasTransfer> orderAliasTransfers = new ArrayList<>(orderaliases.size());
        var orderAliasTransferCache = orderTransferCaches.getOrderAliasTransferCache();

        orderaliases.forEach((orderAlias) ->
                orderAliasTransfers.add(orderAliasTransferCache.getOrderAliasTransfer(userVisit, orderAlias))
        );

        return orderAliasTransfers;
    }

    public void updateOrderAliasFromValue(OrderAliasValue orderAliasValue, BasePK updatedBy) {
        if(orderAliasValue.hasBeenModified()) {
            var orderAlias = OrderAliasFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, orderAliasValue.getPrimaryKey());

            orderAlias.setThruTime(session.START_TIME_LONG);
            orderAlias.store();

            var orderPK = orderAlias.getOrderPK();
            var orderAliasTypePK = orderAlias.getOrderAliasTypePK();
            var alias  = orderAliasValue.getAlias();

            orderAlias = OrderAliasFactory.getInstance().create(orderPK, orderAliasTypePK, alias, session.START_TIME_LONG, Session.MAX_TIME_LONG);

            sendEvent(orderPK, EventTypes.MODIFY, orderAlias.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteOrderAlias(OrderAlias orderAlias, BasePK deletedBy) {
        orderAlias.setThruTime(session.START_TIME_LONG);

        sendEvent(orderAlias.getOrderPK(), EventTypes.MODIFY, orderAlias.getPrimaryKey(), EventTypes.DELETE, deletedBy);

    }

    public void deleteOrderAliasesByOrderAliasType(OrderAliasType orderAliasType, BasePK deletedBy) {
        var orderaliases = getOrderAliasesByOrderAliasTypeForUpdate(orderAliasType);

        orderaliases.forEach((orderAlias) -> 
                deleteOrderAlias(orderAlias, deletedBy)
        );
    }

    public void deleteOrderAliasesByOrder(Order order, BasePK deletedBy) {
        var orderaliases = getOrderAliasesByOrderForUpdate(order);

        orderaliases.forEach((orderAlias) -> 
                deleteOrderAlias(orderAlias, deletedBy)
        );
    }

}
