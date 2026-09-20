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
import com.echothree.model.control.order.common.choice.OrderTimeTypeChoicesBean;
import com.echothree.model.control.order.common.transfer.OrderLineTimeTransfer;
import com.echothree.model.control.order.common.transfer.OrderTimeTransfer;
import com.echothree.model.control.order.common.transfer.OrderTimeTypeDescriptionTransfer;
import com.echothree.model.control.order.common.transfer.OrderTimeTypeTransfer;
import com.echothree.model.control.order.server.transfer.OrderLineTimeTransferCache;
import com.echothree.model.control.order.server.transfer.OrderTimeTransferCache;
import com.echothree.model.control.order.server.transfer.OrderTimeTypeDescriptionTransferCache;
import com.echothree.model.control.order.server.transfer.OrderTimeTypeTransferCache;
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
import static com.echothree.model.jooq.server.keys.order.OrderForeignKeys.ORDERS_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.order.OrderForeignKeys.ORDER_LINES_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.order.OrderForeignKeys.ORDER_LINE_DETAILS_ORDER_FK;
import static com.echothree.model.jooq.server.keys.order.OrderForeignKeys.ORDER_LINE_TIMES_ORDER_LINE_FK;
import static com.echothree.model.jooq.server.keys.order.OrderForeignKeys.ORDER_LINE_TIMES_ORDER_TIME_TYPE_FK;
import static com.echothree.model.jooq.server.keys.order.OrderForeignKeys.ORDER_TIMES_ORDER_FK;
import static com.echothree.model.jooq.server.keys.order.OrderForeignKeys.ORDER_TIMES_ORDER_TIME_TYPE_FK;
import static com.echothree.model.jooq.server.keys.order.OrderForeignKeys.ORDER_TIME_TYPES_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.order.OrderForeignKeys.ORDER_TIME_TYPES_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.order.OrderForeignKeys.ORDER_TIME_TYPE_DESCRIPTIONS_LANGUAGE_FK;
import static com.echothree.model.jooq.server.tables.order.OrderDetails.OrderDetails;
import static com.echothree.model.jooq.server.tables.order.OrderLineDetails.OrderLineDetails;
import static com.echothree.model.jooq.server.tables.order.OrderLineTimes.OrderLineTimes;
import static com.echothree.model.jooq.server.tables.order.OrderLines.OrderLines;
import static com.echothree.model.jooq.server.tables.order.OrderTimeTypeDescriptions.OrderTimeTypeDescriptions;
import static com.echothree.model.jooq.server.tables.order.OrderTimeTypeDetails.OrderTimeTypeDetails;
import static com.echothree.model.jooq.server.tables.order.OrderTimeTypes.OrderTimeTypes;
import static com.echothree.model.jooq.server.tables.order.OrderTimes.OrderTimes;
import static com.echothree.model.jooq.server.tables.order.Orders.Orders;
import static com.echothree.model.jooq.server.tables.party.Languages.Languages;
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
public class OrderTimeControl
        extends BaseModelControl {

    /** Creates a new instance of OrderControl */
    protected OrderTimeControl() {
        super();
    }
    
    // --------------------------------------------------------------------------------
    //   Order Time Types
    // --------------------------------------------------------------------------------

    @Inject
    protected OrderTimeTypeDetailFactory orderTimeTypeDetailFactory;

    @Inject
    private OrderTimeTypeFactory orderTimeTypeFactory;

    @Inject
    private OrderTimeTypeTransferCache orderTimeTypeTransferCache;

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

        var orderTimeType = orderTimeTypeFactory.create();
        var orderTimeTypeDetail = orderTimeTypeDetailFactory.create(orderTimeType, orderType, orderTimeTypeName, isDefault,
                sortOrder, session.getStartTime(), Session.MAX_TIME);

        // Convert to R/W
        orderTimeType = orderTimeTypeFactory.getEntityFromPK(EntityPermission.READ_WRITE,
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

        return orderTimeTypeFactory.getEntityFromPK(entityPermission, pk);
    }

    public OrderTimeType getOrderTimeTypeByEntityInstance(final EntityInstance entityInstance) {
        return getOrderTimeTypeByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public OrderTimeType getOrderTimeTypeByEntityInstanceForUpdate(final EntityInstance entityInstance) {
        return getOrderTimeTypeByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public OrderTimeType getOrderTimeTypeByPK(OrderTimeTypePK pk) {
        return orderTimeTypeFactory.getEntityFromPK(EntityPermission.READ_ONLY, pk);
    }

    public long countOrderTimeTypes(OrderType orderType) {
        return session.getDslContext()
                .selectCount()
                .from(OrderTimeTypes)
                .join(OrderTimeTypeDetails).onKey(ORDER_TIME_TYPES_ACTIVE_DETAIL_FK)
                .where(OrderTimeTypeDetails.ORDER_TYPE.eq(orderType.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public OrderTimeType getOrderTimeTypeByName(OrderType orderType, String orderTimeTypeName, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(OrderTimeTypes.fields())
                .from(OrderTimeTypes)
                .join(OrderTimeTypeDetails).onKey(ORDER_TIME_TYPES_ACTIVE_DETAIL_FK)
                .where(OrderTimeTypeDetails.ORDER_TYPE.eq(orderType.getPrimaryKey()),
                        OrderTimeTypeDetails.ORDER_TIME_TYPE_NAME.eq(orderTimeTypeName));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return orderTimeTypeFactory.getEntityFromQuery(entityPermission, query);
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

    public OrderTimeType getDefaultOrderTimeType(OrderType orderType, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(OrderTimeTypes.fields())
                .from(OrderTimeTypes)
                .join(OrderTimeTypeDetails).onKey(ORDER_TIME_TYPES_ACTIVE_DETAIL_FK)
                .where(OrderTimeTypeDetails.ORDER_TYPE.eq(orderType.getPrimaryKey()),
                        OrderTimeTypeDetails.IS_DEFAULT.eq(true));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return orderTimeTypeFactory.getEntityFromQuery(entityPermission, query);
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

    private List<OrderTimeType> getOrderTimeTypes(OrderType orderType, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(OrderTimeTypes.fields())
                .from(OrderTimeTypes)
                .join(OrderTimeTypeDetails).onKey(ORDER_TIME_TYPES_ACTIVE_DETAIL_FK)
                .where(OrderTimeTypeDetails.ORDER_TYPE.eq(orderType.getPrimaryKey()));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(OrderTimeTypeDetails.SORT_ORDER, OrderTimeTypeDetails.ORDER_TIME_TYPE_NAME),
                    OrderTimeTypeFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return orderTimeTypeFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<OrderTimeType> getOrderTimeTypes(OrderType orderType) {
        return getOrderTimeTypes(orderType, EntityPermission.READ_ONLY);
    }

    public List<OrderTimeType> getOrderTimeTypesForUpdate(OrderType orderType) {
        return getOrderTimeTypes(orderType, EntityPermission.READ_WRITE);
    }

    public OrderTimeTypeTransfer getOrderTimeTypeTransfer(UserVisit userVisit, OrderTimeType orderTimeType) {
        return orderTimeTypeTransferCache.getOrderTimeTypeTransfer(userVisit, orderTimeType);
    }

    public List<OrderTimeTypeTransfer> getOrderTimeTypeTransfers(UserVisit userVisit, Collection<OrderTimeType> orderTimeTypes) {
        List<OrderTimeTypeTransfer> orderTimeTypeTransfers = new ArrayList<>(orderTimeTypes.size());

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
            var orderTimeType = orderTimeTypeFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     orderTimeTypeDetailValue.getOrderTimeTypePK());
            var orderTimeTypeDetail = orderTimeType.getActiveDetailForUpdate();

            orderTimeTypeDetail.setThruTime(session.getStartTime());
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

            orderTimeTypeDetail = orderTimeTypeDetailFactory.create(orderTimeTypePK, orderTypePK, orderTimeTypeName, isDefault, sortOrder,
                    session.getStartTime(), Session.MAX_TIME);

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
        orderTimeTypeDetail.setThruTime(session.getStartTime());
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

    @Inject
    private OrderTimeTypeDescriptionFactory orderTimeTypeDescriptionFactory;

    @Inject
    private OrderTimeTypeDescriptionTransferCache orderTimeTypeDescriptionTransferCache;

    public OrderTimeTypeDescription createOrderTimeTypeDescription(OrderTimeType orderTimeType, Language language, String description, BasePK createdBy) {
        var orderTimeTypeDescription = orderTimeTypeDescriptionFactory.create(orderTimeType, language, description,
                session.getStartTime(), Session.MAX_TIME);

        sendEvent(orderTimeType.getPrimaryKey(), EventTypes.MODIFY, orderTimeTypeDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return orderTimeTypeDescription;
    }

    private OrderTimeTypeDescription getOrderTimeTypeDescription(OrderTimeType orderTimeType, Language language, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(OrderTimeTypeDescriptions.fields())
                .from(OrderTimeTypeDescriptions)
                .where(OrderTimeTypeDescriptions.ORDER_TIME_TYPE.eq(orderTimeType.getPrimaryKey()),
                        OrderTimeTypeDescriptions.LANGUAGE.eq(language.getPrimaryKey()),
                        OrderTimeTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return orderTimeTypeDescriptionFactory.getEntityFromQuery(entityPermission, query);
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

    private List<OrderTimeTypeDescription> getOrderTimeTypeDescriptionsByOrderTimeType(OrderTimeType orderTimeType, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(OrderTimeTypeDescriptions.fields())
                    .from(OrderTimeTypeDescriptions)
                    .join(Languages).onKey(ORDER_TIME_TYPE_DESCRIPTIONS_LANGUAGE_FK)
                    .where(OrderTimeTypeDescriptions.ORDER_TIME_TYPE.eq(orderTimeType.getPrimaryKey()),
                            OrderTimeTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(Languages.SORT_ORDER, Languages.LANGUAGE_ISO_NAME),
                    OrderTimeTypeDescriptionFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(OrderTimeTypeDescriptions.fields())
                    .from(OrderTimeTypeDescriptions)
                    .where(OrderTimeTypeDescriptions.ORDER_TIME_TYPE.eq(orderTimeType.getPrimaryKey()),
                            OrderTimeTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return orderTimeTypeDescriptionFactory.getEntitiesFromQuery(entityPermission, query);
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
        return orderTimeTypeDescriptionTransferCache.getOrderTimeTypeDescriptionTransfer(userVisit, orderTimeTypeDescription);
    }

    public List<OrderTimeTypeDescriptionTransfer> getOrderTimeTypeDescriptionTransfersByOrderTimeType(UserVisit userVisit, OrderTimeType orderTimeType) {
        var orderTimeTypeDescriptions = getOrderTimeTypeDescriptionsByOrderTimeType(orderTimeType);
        List<OrderTimeTypeDescriptionTransfer> orderTimeTypeDescriptionTransfers = new ArrayList<>(orderTimeTypeDescriptions.size());

        orderTimeTypeDescriptions.forEach((orderTimeTypeDescription) ->
                orderTimeTypeDescriptionTransfers.add(orderTimeTypeDescriptionTransferCache.getOrderTimeTypeDescriptionTransfer(userVisit, orderTimeTypeDescription))
        );

        return orderTimeTypeDescriptionTransfers;
    }

    public void updateOrderTimeTypeDescriptionFromValue(OrderTimeTypeDescriptionValue orderTimeTypeDescriptionValue, BasePK updatedBy) {
        if(orderTimeTypeDescriptionValue.hasBeenModified()) {
            var orderTimeTypeDescription = orderTimeTypeDescriptionFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                    orderTimeTypeDescriptionValue.getPrimaryKey());

            orderTimeTypeDescription.setThruTime(session.getStartTime());
            orderTimeTypeDescription.store();

            var orderTimeType = orderTimeTypeDescription.getOrderTimeType();
            var language = orderTimeTypeDescription.getLanguage();
            var description = orderTimeTypeDescriptionValue.getDescription();

            orderTimeTypeDescription = orderTimeTypeDescriptionFactory.create(orderTimeType, language, description,
                    session.getStartTime(), Session.MAX_TIME);

            sendEvent(orderTimeType.getPrimaryKey(), EventTypes.MODIFY, orderTimeTypeDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteOrderTimeTypeDescription(OrderTimeTypeDescription orderTimeTypeDescription, BasePK deletedBy) {
        orderTimeTypeDescription.setThruTime(session.getStartTime());

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

    @Inject
    private OrderTimeFactory orderTimeFactory;

    @Inject
    private OrderTimeTransferCache orderTimeTransferCache;

    public OrderTime createOrderTime(Order order, OrderTimeType orderTimeType, Long time, BasePK createdBy) {
        var orderTime = orderTimeFactory.create(order, orderTimeType, time, session.getStartTime(), Session.MAX_TIME);

        sendEvent(order.getPrimaryKey(), EventTypes.MODIFY, orderTime.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return orderTime;
    }

    public long countOrderTimesByOrder(Order order) {
        return session.getDslContext()
                .selectCount()
                .from(OrderTimes)
                .where(OrderTimes.ORDER.eq(order.getPrimaryKey()),
                        OrderTimes.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countOrderTimesByOrderTimeType(OrderTimeType orderTimeType) {
        return session.getDslContext()
                .selectCount()
                .from(OrderTimes)
                .where(OrderTimes.ORDER_TIME_TYPE.eq(orderTimeType.getPrimaryKey()),
                        OrderTimes.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public boolean orderTimeExists(Order order, OrderTimeType orderTimeType) {
        return session.getDslContext()
                .selectCount()
                .from(OrderTimes)
                .where(OrderTimes.ORDER.eq(order.getPrimaryKey()),
                        OrderTimes.ORDER_TIME_TYPE.eq(orderTimeType.getPrimaryKey()),
                        OrderTimes.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L) == 1L;
    }

    private OrderTime getOrderTime(Order order, OrderTimeType orderTimeType, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(OrderTimes.fields())
                .from(OrderTimes)
                .where(OrderTimes.ORDER.eq(order.getPrimaryKey()),
                        OrderTimes.ORDER_TIME_TYPE.eq(orderTimeType.getPrimaryKey()),
                        OrderTimes.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return orderTimeFactory.getEntityFromQuery(entityPermission, query);
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

    private List<OrderTime> getOrderTimesByOrder(Order order, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(OrderTimes.fields())
                    .from(OrderTimes)
                    .join(OrderTimeTypes).onKey(ORDER_TIMES_ORDER_TIME_TYPE_FK)
                    .join(OrderTimeTypeDetails).onKey(ORDER_TIME_TYPES_LAST_DETAIL_FK)
                    .where(OrderTimes.ORDER.eq(order.getPrimaryKey()),
                            OrderTimes.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(OrderTimeTypeDetails.SORT_ORDER, OrderTimeTypeDetails.ORDER_TIME_TYPE_NAME),
                    OrderTimeFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(OrderTimes.fields())
                    .from(OrderTimes)
                    .where(OrderTimes.ORDER.eq(order.getPrimaryKey()),
                            OrderTimes.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return orderTimeFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<OrderTime> getOrderTimesByOrder(Order order) {
        return getOrderTimesByOrder(order, EntityPermission.READ_ONLY);
    }

    public List<OrderTime> getOrderTimesByOrderForUpdate(Order order) {
        return getOrderTimesByOrder(order, EntityPermission.READ_WRITE);
    }

    private List<OrderTime> getOrderTimesByOrderTimeType(OrderTimeType orderTimeType, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(OrderTimes.fields())
                    .from(OrderTimes)
                    .join(Orders).onKey(ORDER_TIMES_ORDER_FK)
                    .join(OrderDetails).onKey(ORDERS_LAST_DETAIL_FK)
                    .where(OrderTimes.ORDER_TIME_TYPE.eq(orderTimeType.getPrimaryKey()),
                            OrderTimes.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(OrderDetails.ORDER_NAME),
                    OrderTimeFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(OrderTimes.fields())
                    .from(OrderTimes)
                    .where(OrderTimes.ORDER_TIME_TYPE.eq(orderTimeType.getPrimaryKey()),
                            OrderTimes.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return orderTimeFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<OrderTime> getOrderTimesByOrderTimeType(OrderTimeType orderTimeType) {
        return getOrderTimesByOrderTimeType(orderTimeType, EntityPermission.READ_ONLY);
    }

    public List<OrderTime> getOrderTimesByOrderTimeTypeForUpdate(OrderTimeType orderTimeType) {
        return getOrderTimesByOrderTimeType(orderTimeType, EntityPermission.READ_WRITE);
    }

    public OrderTimeTransfer getOrderTimeTransfer(UserVisit userVisit, OrderTime orderTime) {
        return orderTimeTransferCache.getOrderTimeTransfer(userVisit, orderTime);
    }

    public List<OrderTimeTransfer> getOrderTimeTransfers(UserVisit userVisit, Collection<OrderTime> orderTimes) {
        List<OrderTimeTransfer> orderTimeTransfers = new ArrayList<>(orderTimes.size());

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
            var orderTime = orderTimeFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                    orderTimeValue.getPrimaryKey());

            orderTime.setThruTime(session.getStartTime());
            orderTime.store();

            var orderPK = orderTime.getOrderPK(); // Not updated
            var orderTimeTypePK = orderTime.getOrderTimeTypePK(); // Not updated
            var time = orderTimeValue.getTime();

            orderTime = orderTimeFactory.create(orderPK, orderTimeTypePK, time, session.getStartTime(), Session.MAX_TIME);

            sendEvent(orderPK, EventTypes.MODIFY, orderTime.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteOrderTime(OrderTime orderTime, BasePK deletedBy) {
        orderTime.setThruTime(session.getStartTime());

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

    @Inject
    private OrderLineTimeFactory orderLineTimeFactory;

    @Inject
    private OrderLineTimeTransferCache orderLineTimeTransferCache;

    public OrderLineTime createOrderLineTime(OrderLine orderLine, OrderTimeType orderTimeType, Long time, BasePK createdBy) {
        var orderLineTime = orderLineTimeFactory.create(orderLine, orderTimeType, time, session.getStartTime(), Session.MAX_TIME);

        sendEvent(orderLine.getPrimaryKey(), EventTypes.MODIFY, orderLineTime.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return orderLineTime;
    }

    public long countOrderLineTimesByOrderLine(OrderLine orderLine) {
        return session.getDslContext()
                .selectCount()
                .from(OrderLineTimes)
                .where(OrderLineTimes.ORDER_LINE.eq(orderLine.getPrimaryKey()),
                        OrderLineTimes.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countOrderLineTimesByOrderTimeType(OrderTimeType orderTimeType) {
        return session.getDslContext()
                .selectCount()
                .from(OrderLineTimes)
                .where(OrderLineTimes.ORDER_TIME_TYPE.eq(orderTimeType.getPrimaryKey()),
                        OrderLineTimes.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public boolean orderLineTimeExists(OrderLine orderLine, OrderTimeType orderTimeType) {
        return session.getDslContext()
                .selectCount()
                .from(OrderLineTimes)
                .where(OrderLineTimes.ORDER_LINE.eq(orderLine.getPrimaryKey()),
                        OrderLineTimes.ORDER_TIME_TYPE.eq(orderTimeType.getPrimaryKey()),
                        OrderLineTimes.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L) == 1L;
    }

    private OrderLineTime getOrderLineTime(OrderLine orderLine, OrderTimeType orderTimeType, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(OrderLineTimes.fields())
                .from(OrderLineTimes)
                .where(OrderLineTimes.ORDER_LINE.eq(orderLine.getPrimaryKey()),
                        OrderLineTimes.ORDER_TIME_TYPE.eq(orderTimeType.getPrimaryKey()),
                        OrderLineTimes.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return orderLineTimeFactory.getEntityFromQuery(entityPermission, query);
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

    private List<OrderLineTime> getOrderLineTimesByOrderLine(OrderLine orderLine, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(OrderLineTimes.fields())
                    .from(OrderLineTimes)
                    .join(OrderTimeTypes).onKey(ORDER_LINE_TIMES_ORDER_TIME_TYPE_FK)
                    .join(OrderTimeTypeDetails).onKey(ORDER_TIME_TYPES_LAST_DETAIL_FK)
                    .where(OrderLineTimes.ORDER_LINE.eq(orderLine.getPrimaryKey()),
                            OrderLineTimes.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(OrderTimeTypeDetails.SORT_ORDER, OrderTimeTypeDetails.ORDER_TIME_TYPE_NAME),
                    OrderLineTimeFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(OrderLineTimes.fields())
                    .from(OrderLineTimes)
                    .where(OrderLineTimes.ORDER_LINE.eq(orderLine.getPrimaryKey()),
                            OrderLineTimes.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return orderLineTimeFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<OrderLineTime> getOrderLineTimesByOrderLine(OrderLine orderLine) {
        return getOrderLineTimesByOrderLine(orderLine, EntityPermission.READ_ONLY);
    }

    public List<OrderLineTime> getOrderLineTimesByOrderForUpdate(OrderLine orderLine) {
        return getOrderLineTimesByOrderLine(orderLine, EntityPermission.READ_WRITE);
    }

    private List<OrderLineTime> getOrderLineTimesByOrderTimeType(OrderTimeType orderTimeType, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(OrderLineTimes.fields())
                    .from(OrderLineTimes)
                    .join(OrderLines).onKey(ORDER_LINE_TIMES_ORDER_LINE_FK)
                    .join(OrderLineDetails).onKey(ORDER_LINES_LAST_DETAIL_FK)
                    .join(Orders).onKey(ORDER_LINE_DETAILS_ORDER_FK)
                    .join(OrderDetails).onKey(ORDERS_LAST_DETAIL_FK)
                    .where(OrderLineTimes.ORDER_TIME_TYPE.eq(orderTimeType.getPrimaryKey()),
                            OrderLineTimes.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(OrderDetails.ORDER_NAME, OrderLineDetails.ORDER_LINE_SEQUENCE),
                    OrderLineTimeFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(OrderLineTimes.fields())
                    .from(OrderLineTimes)
                    .where(OrderLineTimes.ORDER_TIME_TYPE.eq(orderTimeType.getPrimaryKey()),
                            OrderLineTimes.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return orderLineTimeFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<OrderLineTime> getOrderLineTimesByOrderTimeType(OrderTimeType orderTimeType) {
        return getOrderLineTimesByOrderTimeType(orderTimeType, EntityPermission.READ_ONLY);
    }

    public List<OrderLineTime> getOrderLineTimesByOrderTimeTypeForUpdate(OrderTimeType orderTimeType) {
        return getOrderLineTimesByOrderTimeType(orderTimeType, EntityPermission.READ_WRITE);
    }

    public OrderLineTimeTransfer getOrderLineTimeTransfer(UserVisit userVisit, OrderLineTime orderLineTime) {
        return orderLineTimeTransferCache.getOrderLineTimeTransfer(userVisit, orderLineTime);
    }

    public List<OrderLineTimeTransfer> getOrderLineTimeTransfers(UserVisit userVisit, Collection<OrderLineTime> orderLineTimes) {
        List<OrderLineTimeTransfer> orderLineTimeTransfers = new ArrayList<>(orderLineTimes.size());

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
            var orderLineTime = orderLineTimeFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                    orderLineTimeValue.getPrimaryKey());

            orderLineTime.setThruTime(session.getStartTime());
            orderLineTime.store();

            var orderLinePK = orderLineTime.getOrderLinePK(); // Not updated
            var orderTimeTypePK = orderLineTime.getOrderTimeTypePK(); // Not updated
            var time = orderLineTimeValue.getTime();

            orderLineTime = orderLineTimeFactory.create(orderLinePK, orderTimeTypePK, time, session.getStartTime(), Session.MAX_TIME);

            sendEvent(orderLinePK, EventTypes.MODIFY, orderLineTime.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteOrderLineTime(OrderLineTime orderLineTime, BasePK deletedBy) {
        orderLineTime.setThruTime(session.getStartTime());

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
