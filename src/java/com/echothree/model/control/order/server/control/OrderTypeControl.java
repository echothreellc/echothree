// --------------------------------------------------------------------------------
// Copyright 2002-2023 Echo Three, LLC
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
import com.echothree.model.control.order.common.choice.OrderTypeChoicesBean;
import com.echothree.model.control.order.common.transfer.OrderTypeDescriptionTransfer;
import com.echothree.model.control.order.common.transfer.OrderTypeTransfer;
import com.echothree.model.control.order.server.transfer.OrderTypeDescriptionTransferCache;
import com.echothree.model.control.order.server.transfer.OrderTypeTransferCache;
import com.echothree.model.data.order.common.pk.OrderTypePK;
import com.echothree.model.data.order.server.entity.OrderType;
import com.echothree.model.data.order.server.entity.OrderTypeDescription;
import com.echothree.model.data.order.server.entity.OrderTypeDetail;
import com.echothree.model.data.order.server.factory.OrderTypeDescriptionFactory;
import com.echothree.model.data.order.server.factory.OrderTypeDetailFactory;
import com.echothree.model.data.order.server.factory.OrderTypeFactory;
import com.echothree.model.data.order.server.value.OrderTypeDescriptionValue;
import com.echothree.model.data.order.server.value.OrderTypeDetailValue;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.sequence.common.pk.SequenceTypePK;
import com.echothree.model.data.sequence.server.entity.SequenceType;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.model.data.workflow.common.pk.WorkflowEntrancePK;
import com.echothree.model.data.workflow.common.pk.WorkflowPK;
import com.echothree.model.data.workflow.server.entity.Workflow;
import com.echothree.model.data.workflow.server.entity.WorkflowEntrance;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class OrderTypeControl
        extends BaseOrderControl {

    /** Creates a new instance of OrderControl */
    public OrderTypeControl() {
        super();
    }
    
    // --------------------------------------------------------------------------------
    //   Order Types
    // --------------------------------------------------------------------------------

    public OrderType createOrderType(String orderTypeName, OrderType parentOrderType, SequenceType orderSequenceType, Workflow orderWorkflow,
            WorkflowEntrance orderWorkflowEntrance, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        OrderType defaultOrderType = getDefaultOrderType();
        boolean defaultFound = defaultOrderType != null;

        if(defaultFound && isDefault) {
            OrderTypeDetailValue defaultOrderTypeDetailValue = getDefaultOrderTypeDetailValueForUpdate();

            defaultOrderTypeDetailValue.setIsDefault(Boolean.FALSE);
            updateOrderTypeFromValue(defaultOrderTypeDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = Boolean.TRUE;
        }

        OrderType orderType = OrderTypeFactory.getInstance().create();
        OrderTypeDetail orderTypeDetail = OrderTypeDetailFactory.getInstance().create(orderType, orderTypeName, parentOrderType, orderSequenceType,
                orderWorkflow, orderWorkflowEntrance, isDefault, sortOrder, session.START_TIME_LONG,
                Session.MAX_TIME_LONG);

        // Convert to R/W
        orderType = OrderTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                orderType.getPrimaryKey());
        orderType.setActiveDetail(orderTypeDetail);
        orderType.setLastDetail(orderTypeDetail);
        orderType.store();

        sendEvent(orderType.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return orderType;
    }

    private static final Map<EntityPermission, String> getOrderTypeByNameQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM ordertypes, ordertypedetails " +
                "WHERE ordtyp_activedetailid = ordtypdt_ordertypedetailid " +
                "AND ordtypdt_ordertypename = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM ordertypes, ordertypedetails " +
                "WHERE ordtyp_activedetailid = ordtypdt_ordertypedetailid " +
                "AND ordtypdt_ordertypename = ? " +
                "FOR UPDATE");
        getOrderTypeByNameQueries = Collections.unmodifiableMap(queryMap);
    }

    private OrderType getOrderTypeByName(String orderTypeName, EntityPermission entityPermission) {
        return OrderTypeFactory.getInstance().getEntityFromQuery(entityPermission, getOrderTypeByNameQueries, orderTypeName);
    }

    public OrderType getOrderTypeByName(String orderTypeName) {
        return getOrderTypeByName(orderTypeName, EntityPermission.READ_ONLY);
    }

    public OrderType getOrderTypeByNameForUpdate(String orderTypeName) {
        return getOrderTypeByName(orderTypeName, EntityPermission.READ_WRITE);
    }

    public OrderTypeDetailValue getOrderTypeDetailValueForUpdate(OrderType orderType) {
        return orderType == null? null: orderType.getLastDetailForUpdate().getOrderTypeDetailValue().clone();
    }

    public OrderTypeDetailValue getOrderTypeDetailValueByNameForUpdate(String orderTypeName) {
        return getOrderTypeDetailValueForUpdate(getOrderTypeByNameForUpdate(orderTypeName));
    }

    private static final Map<EntityPermission, String> getDefaultOrderTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM ordertypes, ordertypedetails " +
                "WHERE ordtyp_activedetailid = ordtypdt_ordertypedetailid " +
                "AND ordtypdt_isdefault = 1");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM ordertypes, ordertypedetails " +
                "WHERE ordtyp_activedetailid = ordtypdt_ordertypedetailid " +
                "AND ordtypdt_isdefault = 1 " +
                "FOR UPDATE");
        getDefaultOrderTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    private OrderType getDefaultOrderType(EntityPermission entityPermission) {
        return OrderTypeFactory.getInstance().getEntityFromQuery(entityPermission, getDefaultOrderTypeQueries);
    }

    public OrderType getDefaultOrderType() {
        return getDefaultOrderType(EntityPermission.READ_ONLY);
    }

    public OrderType getDefaultOrderTypeForUpdate() {
        return getDefaultOrderType(EntityPermission.READ_WRITE);
    }

    public OrderTypeDetailValue getDefaultOrderTypeDetailValueForUpdate() {
        return getDefaultOrderTypeForUpdate().getLastDetailForUpdate().getOrderTypeDetailValue().clone();
    }

    private static final Map<EntityPermission, String> getOrderTypesQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM ordertypes, ordertypedetails " +
                "WHERE ordtyp_activedetailid = ordtypdt_ordertypedetailid " +
                "ORDER BY ordtypdt_sortorder, ordtypdt_ordertypename " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM ordertypes, ordertypedetails " +
                "WHERE ordtyp_activedetailid = ordtypdt_ordertypedetailid " +
                "FOR UPDATE");
        getOrderTypesQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<OrderType> getOrderTypes(EntityPermission entityPermission) {
        return OrderTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, getOrderTypesQueries);
    }

    public List<OrderType> getOrderTypes() {
        return getOrderTypes(EntityPermission.READ_ONLY);
    }

    public List<OrderType> getOrderTypesForUpdate() {
        return getOrderTypes(EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getOrderTypesByParentOrderTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM ordertypes, ordertypedetails " +
                "WHERE ordtyp_activedetailid = ordtypdt_ordertypedetailid AND ordtypdt_parentordertypeid = ? " +
                "ORDER BY ordtypdt_sortorder, ordtypdt_ordertypename " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM ordertypes, ordertypedetails " +
                "WHERE ordtyp_activedetailid = ordtypdt_ordertypedetailid AND ordtypdt_parentordertypeid = ? " +
                "FOR UPDATE");
        getOrderTypesByParentOrderTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<OrderType> getOrderTypesByParentOrderType(OrderType parentOrderType,
            EntityPermission entityPermission) {
        return OrderTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, getOrderTypesByParentOrderTypeQueries,
                parentOrderType);
    }

    public List<OrderType> getOrderTypesByParentOrderType(OrderType parentOrderType) {
        return getOrderTypesByParentOrderType(parentOrderType, EntityPermission.READ_ONLY);
    }

    public List<OrderType> getOrderTypesByParentOrderTypeForUpdate(OrderType parentOrderType) {
        return getOrderTypesByParentOrderType(parentOrderType, EntityPermission.READ_WRITE);
    }

    public OrderTypeTransfer getOrderTypeTransfer(UserVisit userVisit, OrderType orderType) {
        return getOrderTransferCaches(userVisit).getOrderTypeTransferCache().getOrderTypeTransfer(orderType);
    }

    public List<OrderTypeTransfer> getOrderTypeTransfers(UserVisit userVisit) {
        List<OrderType> orderTypes = getOrderTypes();
        List<OrderTypeTransfer> orderTypeTransfers = new ArrayList<>(orderTypes.size());
        OrderTypeTransferCache orderTypeTransferCache = getOrderTransferCaches(userVisit).getOrderTypeTransferCache();

        orderTypes.forEach((orderType) ->
                orderTypeTransfers.add(orderTypeTransferCache.getOrderTypeTransfer(orderType))
        );

        return orderTypeTransfers;
    }

    public OrderTypeChoicesBean getOrderTypeChoices(String defaultOrderTypeChoice,
            Language language, boolean allowNullChoice) {
        List<OrderType> orderTypes = getOrderTypes();
        var size = orderTypes.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultOrderTypeChoice == null) {
                defaultValue = "";
            }
        }

        for(var orderType : orderTypes) {
            OrderTypeDetail orderTypeDetail = orderType.getLastDetail();

            var label = getBestOrderTypeDescription(orderType, language);
            var value = orderTypeDetail.getOrderTypeName();

            labels.add(label == null? value: label);
            values.add(value);

            var usingDefaultChoice = defaultOrderTypeChoice != null && defaultOrderTypeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && orderTypeDetail.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new OrderTypeChoicesBean(labels, values, defaultValue);
    }

    public boolean isParentOrderTypeSafe(OrderType orderType,
            OrderType parentOrderType) {
        boolean safe = true;

        if(parentOrderType != null) {
            Set<OrderType> parentOrderTypes = new HashSet<>();

            parentOrderTypes.add(orderType);
            do {
                if(parentOrderTypes.contains(parentOrderType)) {
                    safe = false;
                    break;
                }

                parentOrderTypes.add(parentOrderType);
                parentOrderType = parentOrderType.getLastDetail().getParentOrderType();
            } while(parentOrderType != null);
        }

        return safe;
    }

    private void updateOrderTypeFromValue(OrderTypeDetailValue orderTypeDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(orderTypeDetailValue.hasBeenModified()) {
            OrderType orderType = OrderTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     orderTypeDetailValue.getOrderTypePK());
            OrderTypeDetail orderTypeDetail = orderType.getActiveDetailForUpdate();

            orderTypeDetail.setThruTime(session.START_TIME_LONG);
            orderTypeDetail.store();

            OrderTypePK orderTypePK = orderTypeDetail.getOrderTypePK(); // Not updated
            String orderTypeName = orderTypeDetailValue.getOrderTypeName();
            OrderTypePK parentOrderTypePK = orderTypeDetailValue.getParentOrderTypePK();
            SequenceTypePK orderSequenceTypePK = orderTypeDetailValue.getOrderSequenceTypePK();
            WorkflowPK orderWorkflowPK = orderTypeDetailValue.getOrderWorkflowPK();
            WorkflowEntrancePK orderWorkflowEntrancePK = orderTypeDetailValue.getOrderWorkflowEntrancePK();
            Boolean isDefault = orderTypeDetailValue.getIsDefault();
            Integer sortOrder = orderTypeDetailValue.getSortOrder();

            if(checkDefault) {
                OrderType defaultOrderType = getDefaultOrderType();
                boolean defaultFound = defaultOrderType != null && !defaultOrderType.equals(orderType);

                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    OrderTypeDetailValue defaultOrderTypeDetailValue = getDefaultOrderTypeDetailValueForUpdate();

                    defaultOrderTypeDetailValue.setIsDefault(Boolean.FALSE);
                    updateOrderTypeFromValue(defaultOrderTypeDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = Boolean.TRUE;
                }
            }

            orderTypeDetail = OrderTypeDetailFactory.getInstance().create(orderTypePK, orderTypeName, parentOrderTypePK, orderSequenceTypePK,
                    orderWorkflowPK, orderWorkflowEntrancePK, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);

            orderType.setActiveDetail(orderTypeDetail);
            orderType.setLastDetail(orderTypeDetail);

            sendEvent(orderTypePK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }

    public void updateOrderTypeFromValue(OrderTypeDetailValue orderTypeDetailValue, BasePK updatedBy) {
        updateOrderTypeFromValue(orderTypeDetailValue, true, updatedBy);
    }

    private void deleteOrderType(OrderType orderType, boolean checkDefault, BasePK deletedBy) {
        var orderAliasControl = Session.getModelController(OrderAliasControl.class);
        OrderTypeDetail orderTypeDetail = orderType.getLastDetailForUpdate();

        deleteOrderTypesByParentOrderType(orderType, deletedBy);
        deleteOrderTypeDescriptionsByOrderType(orderType, deletedBy);
        orderAliasControl.deleteOrderAliasTypesByOrderType(orderType, deletedBy);
        // TODO: deleteOrdersByOrderType(orderType, deletedBy);

        orderTypeDetail.setThruTime(session.START_TIME_LONG);
        orderType.setActiveDetail(null);
        orderType.store();

        if(checkDefault) {
        // Check for default, and pick one if necessary
        OrderType defaultOrderType = getDefaultOrderType();
            if(defaultOrderType == null) {
                List<OrderType> orderTypes = getOrderTypesForUpdate();

                if(!orderTypes.isEmpty()) {
                    Iterator<OrderType> iter = orderTypes.iterator();
                    if(iter.hasNext()) {
                        defaultOrderType = iter.next();
                    }
                    OrderTypeDetailValue orderTypeDetailValue = Objects.requireNonNull(defaultOrderType).getLastDetailForUpdate().getOrderTypeDetailValue().clone();

                    orderTypeDetailValue.setIsDefault(Boolean.TRUE);
                    updateOrderTypeFromValue(orderTypeDetailValue, false, deletedBy);
                }
            }
        }

        sendEvent(orderType.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }

    public void deleteOrderType(OrderType orderType, BasePK deletedBy) {
        deleteOrderType(orderType, true, deletedBy);
    }

    private void deleteOrderTypes(List<OrderType> orderTypes, boolean checkDefault, BasePK deletedBy) {
        orderTypes.forEach((orderType) -> deleteOrderType(orderType, checkDefault, deletedBy));
    }

    public void deleteOrderTypes(List<OrderType> orderTypes, BasePK deletedBy) {
        deleteOrderTypes(orderTypes, true, deletedBy);
    }

    private void deleteOrderTypesByParentOrderType(OrderType parentOrderType, BasePK deletedBy) {
        deleteOrderTypes(getOrderTypesByParentOrderTypeForUpdate(parentOrderType), false, deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Order Type Descriptions
    // --------------------------------------------------------------------------------

    public OrderTypeDescription createOrderTypeDescription(OrderType orderType, Language language, String description, BasePK createdBy) {
        OrderTypeDescription orderTypeDescription = OrderTypeDescriptionFactory.getInstance().create(orderType, language, description,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEvent(orderType.getPrimaryKey(), EventTypes.MODIFY, orderTypeDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return orderTypeDescription;
    }

    private static final Map<EntityPermission, String> getOrderTypeDescriptionQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM ordertypedescriptions " +
                "WHERE ordtypd_ordtyp_ordertypeid = ? AND ordtypd_lang_languageid = ? AND ordtypd_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM ordertypedescriptions " +
                "WHERE ordtypd_ordtyp_ordertypeid = ? AND ordtypd_lang_languageid = ? AND ordtypd_thrutime = ? " +
                "FOR UPDATE");
        getOrderTypeDescriptionQueries = Collections.unmodifiableMap(queryMap);
    }

    private OrderTypeDescription getOrderTypeDescription(OrderType orderType, Language language, EntityPermission entityPermission) {
        return OrderTypeDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, getOrderTypeDescriptionQueries,
                orderType, language, Session.MAX_TIME);
    }

    public OrderTypeDescription getOrderTypeDescription(OrderType orderType, Language language) {
        return getOrderTypeDescription(orderType, language, EntityPermission.READ_ONLY);
    }

    public OrderTypeDescription getOrderTypeDescriptionForUpdate(OrderType orderType, Language language) {
        return getOrderTypeDescription(orderType, language, EntityPermission.READ_WRITE);
    }

    public OrderTypeDescriptionValue getOrderTypeDescriptionValue(OrderTypeDescription orderTypeDescription) {
        return orderTypeDescription == null? null: orderTypeDescription.getOrderTypeDescriptionValue().clone();
    }

    public OrderTypeDescriptionValue getOrderTypeDescriptionValueForUpdate(OrderType orderType, Language language) {
        return getOrderTypeDescriptionValue(getOrderTypeDescriptionForUpdate(orderType, language));
    }

    private static final Map<EntityPermission, String> getOrderTypeDescriptionsByOrderTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM ordertypedescriptions, languages " +
                "WHERE ordtypd_ordtyp_ordertypeid = ? AND ordtypd_thrutime = ? AND ordtypd_lang_languageid = lang_languageid " +
                "ORDER BY lang_sortorder, lang_languageisoname");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM ordertypedescriptions " +
                "WHERE ordtypd_ordtyp_ordertypeid = ? AND ordtypd_thrutime = ? " +
                "FOR UPDATE");
        getOrderTypeDescriptionsByOrderTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<OrderTypeDescription> getOrderTypeDescriptionsByOrderType(OrderType orderType, EntityPermission entityPermission) {
        return OrderTypeDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, getOrderTypeDescriptionsByOrderTypeQueries,
                orderType, Session.MAX_TIME);
    }

    public List<OrderTypeDescription> getOrderTypeDescriptionsByOrderType(OrderType orderType) {
        return getOrderTypeDescriptionsByOrderType(orderType, EntityPermission.READ_ONLY);
    }

    public List<OrderTypeDescription> getOrderTypeDescriptionsByOrderTypeForUpdate(OrderType orderType) {
        return getOrderTypeDescriptionsByOrderType(orderType, EntityPermission.READ_WRITE);
    }

    public String getBestOrderTypeDescription(OrderType orderType, Language language) {
        String description;
        OrderTypeDescription orderTypeDescription = getOrderTypeDescription(orderType, language);

        if(orderTypeDescription == null && !language.getIsDefault()) {
            orderTypeDescription = getOrderTypeDescription(orderType, getPartyControl().getDefaultLanguage());
        }

        if(orderTypeDescription == null) {
            description = orderType.getLastDetail().getOrderTypeName();
        } else {
            description = orderTypeDescription.getDescription();
        }

        return description;
    }

    public OrderTypeDescriptionTransfer getOrderTypeDescriptionTransfer(UserVisit userVisit, OrderTypeDescription orderTypeDescription) {
        return getOrderTransferCaches(userVisit).getOrderTypeDescriptionTransferCache().getOrderTypeDescriptionTransfer(orderTypeDescription);
    }

    public List<OrderTypeDescriptionTransfer> getOrderTypeDescriptionTransfersByOrderType(UserVisit userVisit, OrderType orderType) {
        List<OrderTypeDescription> orderTypeDescriptions = getOrderTypeDescriptionsByOrderType(orderType);
        List<OrderTypeDescriptionTransfer> orderTypeDescriptionTransfers = new ArrayList<>(orderTypeDescriptions.size());
        OrderTypeDescriptionTransferCache orderTypeDescriptionTransferCache = getOrderTransferCaches(userVisit).getOrderTypeDescriptionTransferCache();

        orderTypeDescriptions.forEach((orderTypeDescription) ->
                orderTypeDescriptionTransfers.add(orderTypeDescriptionTransferCache.getOrderTypeDescriptionTransfer(orderTypeDescription))
        );

        return orderTypeDescriptionTransfers;
    }

    public void updateOrderTypeDescriptionFromValue(OrderTypeDescriptionValue orderTypeDescriptionValue, BasePK updatedBy) {
        if(orderTypeDescriptionValue.hasBeenModified()) {
            OrderTypeDescription orderTypeDescription = OrderTypeDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    orderTypeDescriptionValue.getPrimaryKey());

            orderTypeDescription.setThruTime(session.START_TIME_LONG);
            orderTypeDescription.store();

            OrderType orderType = orderTypeDescription.getOrderType();
            Language language = orderTypeDescription.getLanguage();
            String description = orderTypeDescriptionValue.getDescription();

            orderTypeDescription = OrderTypeDescriptionFactory.getInstance().create(orderType, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);

            sendEvent(orderType.getPrimaryKey(), EventTypes.MODIFY, orderTypeDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteOrderTypeDescription(OrderTypeDescription orderTypeDescription, BasePK deletedBy) {
        orderTypeDescription.setThruTime(session.START_TIME_LONG);

        sendEvent(orderTypeDescription.getOrderTypePK(), EventTypes.MODIFY, orderTypeDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);

    }

    public void deleteOrderTypeDescriptionsByOrderType(OrderType orderType, BasePK deletedBy) {
        List<OrderTypeDescription> orderTypeDescriptions = getOrderTypeDescriptionsByOrderTypeForUpdate(orderType);

        orderTypeDescriptions.forEach((orderTypeDescription) -> 
                deleteOrderTypeDescription(orderTypeDescription, deletedBy)
        );
    }

}
