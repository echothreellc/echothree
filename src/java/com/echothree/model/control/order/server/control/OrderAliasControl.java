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
import com.echothree.model.control.order.common.choice.OrderAliasTypeChoicesBean;
import com.echothree.model.control.order.common.transfer.OrderAliasTransfer;
import com.echothree.model.control.order.common.transfer.OrderAliasTypeDescriptionTransfer;
import com.echothree.model.control.order.common.transfer.OrderAliasTypeTransfer;
import com.echothree.model.control.order.server.transfer.OrderAliasTransferCache;
import com.echothree.model.control.order.server.transfer.OrderAliasTypeDescriptionTransferCache;
import com.echothree.model.control.order.server.transfer.OrderAliasTypeTransferCache;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.order.common.pk.OrderAliasTypePK;
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
import static com.echothree.model.jooq.server.keys.order.OrderForeignKeys.ORDERS_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.order.OrderForeignKeys.ORDER_ALIASES_ORDER_ALIAS_TYPE_FK;
import static com.echothree.model.jooq.server.keys.order.OrderForeignKeys.ORDER_ALIASES_ORDER_FK;
import static com.echothree.model.jooq.server.keys.order.OrderForeignKeys.ORDER_ALIAS_TYPES_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.order.OrderForeignKeys.ORDER_ALIAS_TYPES_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.order.OrderForeignKeys.ORDER_ALIAS_TYPE_DESCRIPTIONS_LANGUAGE_FK;
import static com.echothree.model.jooq.server.tables.order.OrderAliasTypeDescriptions.OrderAliasTypeDescriptions;
import static com.echothree.model.jooq.server.tables.order.OrderAliasTypeDetails.OrderAliasTypeDetails;
import static com.echothree.model.jooq.server.tables.order.OrderAliasTypes.OrderAliasTypes;
import static com.echothree.model.jooq.server.tables.order.OrderAliases.OrderAliases;
import static com.echothree.model.jooq.server.tables.order.OrderDetails.OrderDetails;
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
public class OrderAliasControl
        extends BaseModelControl {

    /** Creates a new instance of OrderControl */
    protected OrderAliasControl() {
        super();
    }

    // --------------------------------------------------------------------------------
    //   Order Alias Types
    // --------------------------------------------------------------------------------

    @Inject
    protected OrderAliasTypeDetailFactory orderAliasTypeDetailFactory;

    @Inject
    private OrderAliasTypeFactory orderAliasTypeFactory;

    @Inject
    private OrderAliasTypeTransferCache orderAliasTypeTransferCache;

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

        var orderAliasType = orderAliasTypeFactory.create();
        var orderAliasTypeDetail = orderAliasTypeDetailFactory.create(orderAliasType, orderType, orderAliasTypeName,
                validationPattern, isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);

        // Convert to R/W
        orderAliasType = orderAliasTypeFactory.getEntityFromPK(EntityPermission.READ_WRITE, orderAliasType.getPrimaryKey());
        orderAliasType.setActiveDetail(orderAliasTypeDetail);
        orderAliasType.setLastDetail(orderAliasTypeDetail);
        orderAliasType.store();

        sendEvent(orderAliasType.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return orderAliasType;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.OrderAliasType */
    public OrderAliasType getOrderAliasTypeByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new OrderAliasTypePK(entityInstance.getEntityUniqueId());

        return orderAliasTypeFactory.getEntityFromPK(entityPermission, pk);
    }

    public OrderAliasType getOrderAliasTypeByEntityInstance(EntityInstance entityInstance) {
        return getOrderAliasTypeByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public OrderAliasType getOrderAliasTypeByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getOrderAliasTypeByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countOrderAliasTypesByOrderType(final OrderType orderType) {
        return session.getDslContext()
                .selectCount()
                .from(OrderAliasTypes)
                .join(OrderAliasTypeDetails).onKey(ORDER_ALIAS_TYPES_ACTIVE_DETAIL_FK)
                .where(OrderAliasTypeDetails.ORDER_TYPE.eq(orderType.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private OrderAliasType getOrderAliasTypeByName(OrderType orderType, String orderAliasTypeName, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(OrderAliasTypes.fields())
                .from(OrderAliasTypes)
                .join(OrderAliasTypeDetails).onKey(ORDER_ALIAS_TYPES_ACTIVE_DETAIL_FK)
                .where(OrderAliasTypeDetails.ORDER_TYPE.eq(orderType.getPrimaryKey()),
                        OrderAliasTypeDetails.ORDER_ALIAS_TYPE_NAME.eq(orderAliasTypeName));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return orderAliasTypeFactory.getEntityFromQuery(entityPermission, query);
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

    private OrderAliasType getDefaultOrderAliasType(OrderType orderType, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(OrderAliasTypes.fields())
                .from(OrderAliasTypes)
                .join(OrderAliasTypeDetails).onKey(ORDER_ALIAS_TYPES_ACTIVE_DETAIL_FK)
                .where(OrderAliasTypeDetails.ORDER_TYPE.eq(orderType.getPrimaryKey()),
                        OrderAliasTypeDetails.IS_DEFAULT.eq(true));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return orderAliasTypeFactory.getEntityFromQuery(entityPermission, query);
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

    private List<OrderAliasType> getOrderAliasTypes(OrderType orderType, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(OrderAliasTypes.fields())
                .from(OrderAliasTypes)
                .join(OrderAliasTypeDetails).onKey(ORDER_ALIAS_TYPES_ACTIVE_DETAIL_FK)
                .where(OrderAliasTypeDetails.ORDER_TYPE.eq(orderType.getPrimaryKey()));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(OrderAliasTypeDetails.SORT_ORDER, OrderAliasTypeDetails.ORDER_ALIAS_TYPE_NAME),
                    OrderAliasTypeFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return orderAliasTypeFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<OrderAliasType> getOrderAliasTypes(OrderType orderType) {
        return getOrderAliasTypes(orderType, EntityPermission.READ_ONLY);
    }

    public List<OrderAliasType> getOrderAliasTypesForUpdate(OrderType orderType) {
        return getOrderAliasTypes(orderType, EntityPermission.READ_WRITE);
    }

    public OrderAliasTypeTransfer getOrderAliasTypeTransfer(UserVisit userVisit, OrderAliasType orderAliasType) {
        return orderAliasTypeTransferCache.getOrderAliasTypeTransfer(userVisit, orderAliasType);
    }

    public List<OrderAliasTypeTransfer> getOrderAliasTypeTransfers(UserVisit userVisit, Collection<OrderAliasType> orderAliasTypes) {
        List<OrderAliasTypeTransfer> orderAliasTypeTransfers = new ArrayList<>(orderAliasTypes.size());

        orderAliasTypes.forEach((orderAliasType) ->
                orderAliasTypeTransfers.add(orderAliasTypeTransferCache.getOrderAliasTypeTransfer(userVisit, orderAliasType))
        );

        return orderAliasTypeTransfers;
    }

    public List<OrderAliasTypeTransfer> getOrderAliasTypeTransfers(UserVisit userVisit, OrderType orderType) {
        return getOrderAliasTypeTransfers(userVisit, getOrderAliasTypes(orderType));
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
            var orderAliasType = orderAliasTypeFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                    orderAliasTypeDetailValue.getOrderAliasTypePK());
            var orderAliasTypeDetail = orderAliasType.getActiveDetailForUpdate();

            orderAliasTypeDetail.setThruTime(session.getStartTime());
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

            orderAliasTypeDetail = orderAliasTypeDetailFactory.create(orderAliasTypePK, orderTypePK, orderAliasTypeName,
                    validationPattern, isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);

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
        orderAliasTypeDetail.setThruTime(session.getStartTime());
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

    @Inject
    private OrderAliasTypeDescriptionFactory orderAliasTypeDescriptionFactory;

    @Inject
    private OrderAliasTypeDescriptionTransferCache orderAliasTypeDescriptionTransferCache;

    public OrderAliasTypeDescription createOrderAliasTypeDescription(OrderAliasType orderAliasType, Language language, String description, BasePK createdBy) {
        var orderAliasTypeDescription = orderAliasTypeDescriptionFactory.create(orderAliasType, language,
                description, session.getStartTime(), Session.MAX_TIME);

        sendEvent(orderAliasType.getPrimaryKey(), EventTypes.MODIFY, orderAliasTypeDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return orderAliasTypeDescription;
    }

    private OrderAliasTypeDescription getOrderAliasTypeDescription(OrderAliasType orderAliasType, Language language, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(OrderAliasTypeDescriptions.fields())
                .from(OrderAliasTypeDescriptions)
                .where(OrderAliasTypeDescriptions.ORDER_ALIAS_TYPE.eq(orderAliasType.getPrimaryKey()),
                        OrderAliasTypeDescriptions.LANGUAGE.eq(language.getPrimaryKey()),
                        OrderAliasTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return orderAliasTypeDescriptionFactory.getEntityFromQuery(entityPermission, query);
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

    private List<OrderAliasTypeDescription> getOrderAliasTypeDescriptionsByOrderAliasType(OrderAliasType orderAliasType, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(OrderAliasTypeDescriptions.fields())
                    .from(OrderAliasTypeDescriptions)
                    .join(Languages).onKey(ORDER_ALIAS_TYPE_DESCRIPTIONS_LANGUAGE_FK)
                    .where(OrderAliasTypeDescriptions.ORDER_ALIAS_TYPE.eq(orderAliasType.getPrimaryKey()),
                            OrderAliasTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(Languages.SORT_ORDER, Languages.LANGUAGE_ISO_NAME),
                    OrderAliasTypeDescriptionFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(OrderAliasTypeDescriptions.fields())
                    .from(OrderAliasTypeDescriptions)
                    .where(OrderAliasTypeDescriptions.ORDER_ALIAS_TYPE.eq(orderAliasType.getPrimaryKey()),
                            OrderAliasTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return orderAliasTypeDescriptionFactory.getEntitiesFromQuery(entityPermission, query);
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
        return orderAliasTypeDescriptionTransferCache.getOrderAliasTypeDescriptionTransfer(userVisit, orderAliasTypeDescription);
    }

    public List<OrderAliasTypeDescriptionTransfer> getOrderAliasTypeDescriptionTransfersByOrderAliasType(UserVisit userVisit, OrderAliasType orderAliasType) {
        var orderAliasTypeDescriptions = getOrderAliasTypeDescriptionsByOrderAliasType(orderAliasType);
        List<OrderAliasTypeDescriptionTransfer> orderAliasTypeDescriptionTransfers = new ArrayList<>(orderAliasTypeDescriptions.size());

        orderAliasTypeDescriptions.forEach((orderAliasTypeDescription) ->
                orderAliasTypeDescriptionTransfers.add(orderAliasTypeDescriptionTransferCache.getOrderAliasTypeDescriptionTransfer(userVisit, orderAliasTypeDescription))
        );

        return orderAliasTypeDescriptionTransfers;
    }

    public void updateOrderAliasTypeDescriptionFromValue(OrderAliasTypeDescriptionValue orderAliasTypeDescriptionValue, BasePK updatedBy) {
        if(orderAliasTypeDescriptionValue.hasBeenModified()) {
            var orderAliasTypeDescription = orderAliasTypeDescriptionFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     orderAliasTypeDescriptionValue.getPrimaryKey());

            orderAliasTypeDescription.setThruTime(session.getStartTime());
            orderAliasTypeDescription.store();

            var orderAliasType = orderAliasTypeDescription.getOrderAliasType();
            var language = orderAliasTypeDescription.getLanguage();
            var description = orderAliasTypeDescriptionValue.getDescription();

            orderAliasTypeDescription = orderAliasTypeDescriptionFactory.create(orderAliasType, language, description,
                    session.getStartTime(), Session.MAX_TIME);

            sendEvent(orderAliasType.getPrimaryKey(), EventTypes.MODIFY, orderAliasTypeDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteOrderAliasTypeDescription(OrderAliasTypeDescription orderAliasTypeDescription, BasePK deletedBy) {
        orderAliasTypeDescription.setThruTime(session.getStartTime());

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

    @Inject
    private OrderAliasFactory orderAliasFactory;

    @Inject
    private OrderAliasTransferCache orderAliasTransferCache;

    public OrderAlias createOrderAlias(Order order, OrderAliasType orderAliasType, String alias, BasePK createdBy) {
        var orderAlias = orderAliasFactory.create(order, orderAliasType, alias, session.getStartTime(), Session.MAX_TIME);

        sendEvent(order.getPrimaryKey(), EventTypes.MODIFY, orderAlias.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return orderAlias;
    }

    public long countOrderAliasByOrder(final Order order) {
        return session.getDslContext()
                .selectCount()
                .from(OrderAliases)
                .where(OrderAliases.ORDER.eq(order.getPrimaryKey()),
                        OrderAliases.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countOrderAliasByOrderAliasType(final OrderAliasType orderAliasType) {
        return session.getDslContext()
                .selectCount()
                .from(OrderAliases)
                .where(OrderAliases.ORDER_ALIAS_TYPE.eq(orderAliasType.getPrimaryKey()),
                        OrderAliases.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private OrderAlias getOrderAlias(Order order, OrderAliasType orderAliasType, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(OrderAliases.fields())
                .from(OrderAliases)
                .where(OrderAliases.ORDER.eq(order.getPrimaryKey()),
                        OrderAliases.ORDER_ALIAS_TYPE.eq(orderAliasType.getPrimaryKey()),
                        OrderAliases.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return orderAliasFactory.getEntityFromQuery(entityPermission, query);
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

    private OrderAlias getOrderAliasByAlias(OrderAliasType orderAliasType, String alias, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(OrderAliases.fields())
                .from(OrderAliases)
                .where(OrderAliases.ORDER_ALIAS_TYPE.eq(orderAliasType.getPrimaryKey()),
                        OrderAliases.ALIAS.eq(alias),
                        OrderAliases.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return orderAliasFactory.getEntityFromQuery(entityPermission, query);
    }

    public OrderAlias getOrderAliasByAlias(OrderAliasType orderAliasType, String alias) {
        return getOrderAliasByAlias(orderAliasType, alias, EntityPermission.READ_ONLY);
    }

    public OrderAlias getOrderAliasByAliasForUpdate(OrderAliasType orderAliasType, String alias) {
        return getOrderAliasByAlias(orderAliasType, alias, EntityPermission.READ_WRITE);
    }

    private List<OrderAlias> getOrderAliasesByOrder(Order order, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(OrderAliases.fields())
                    .from(OrderAliases)
                    .join(OrderAliasTypes).onKey(ORDER_ALIASES_ORDER_ALIAS_TYPE_FK)
                    .join(OrderAliasTypeDetails).onKey(ORDER_ALIAS_TYPES_LAST_DETAIL_FK)
                    .where(OrderAliases.ORDER.eq(order.getPrimaryKey()),
                            OrderAliases.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(OrderAliasTypeDetails.SORT_ORDER, OrderAliasTypeDetails.ORDER_ALIAS_TYPE_NAME),
                    OrderAliasFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(OrderAliases.fields())
                    .from(OrderAliases)
                    .where(OrderAliases.ORDER.eq(order.getPrimaryKey()),
                            OrderAliases.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return orderAliasFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<OrderAlias> getOrderAliasesByOrder(Order order) {
        return getOrderAliasesByOrder(order, EntityPermission.READ_ONLY);
    }

    public List<OrderAlias> getOrderAliasesByOrderForUpdate(Order order) {
        return getOrderAliasesByOrder(order, EntityPermission.READ_WRITE);
    }

    private List<OrderAlias> getOrderAliasesByOrderAliasType(OrderAliasType orderAliasType, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(OrderAliases.fields())
                    .from(OrderAliases)
                    .join(Orders).onKey(ORDER_ALIASES_ORDER_FK)
                    .join(OrderDetails).onKey(ORDERS_LAST_DETAIL_FK)
                    .where(OrderAliases.ORDER_ALIAS_TYPE.eq(orderAliasType.getPrimaryKey()),
                            OrderAliases.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(OrderDetails.ORDER_NAME, OrderAliases.ALIAS),
                    OrderAliasFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(OrderAliases.fields())
                    .from(OrderAliases)
                    .where(OrderAliases.ORDER_ALIAS_TYPE.eq(orderAliasType.getPrimaryKey()),
                            OrderAliases.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return orderAliasFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<OrderAlias> getOrderAliasesByOrderAliasType(OrderAliasType orderAliasType) {
        return getOrderAliasesByOrderAliasType(orderAliasType, EntityPermission.READ_ONLY);
    }

    public List<OrderAlias> getOrderAliasesByOrderAliasTypeForUpdate(OrderAliasType orderAliasType) {
        return getOrderAliasesByOrderAliasType(orderAliasType, EntityPermission.READ_WRITE);
    }

    public OrderAliasTransfer getOrderAliasTransfer(UserVisit userVisit, OrderAlias orderAlias) {
        return orderAliasTransferCache.getOrderAliasTransfer(userVisit, orderAlias);
    }

    public List<OrderAliasTransfer> getOrderAliasTransfersByOrder(UserVisit userVisit, Order order) {
        var orderaliases = getOrderAliasesByOrder(order);
        List<OrderAliasTransfer> orderAliasTransfers = new ArrayList<>(orderaliases.size());

        orderaliases.forEach((orderAlias) ->
                orderAliasTransfers.add(orderAliasTransferCache.getOrderAliasTransfer(userVisit, orderAlias))
        );

        return orderAliasTransfers;
    }

    public void updateOrderAliasFromValue(OrderAliasValue orderAliasValue, BasePK updatedBy) {
        if(orderAliasValue.hasBeenModified()) {
            var orderAlias = orderAliasFactory.getEntityFromPK(EntityPermission.READ_WRITE, orderAliasValue.getPrimaryKey());

            orderAlias.setThruTime(session.getStartTime());
            orderAlias.store();

            var orderPK = orderAlias.getOrderPK();
            var orderAliasTypePK = orderAlias.getOrderAliasTypePK();
            var alias  = orderAliasValue.getAlias();

            orderAlias = orderAliasFactory.create(orderPK, orderAliasTypePK, alias, session.getStartTime(), Session.MAX_TIME);

            sendEvent(orderPK, EventTypes.MODIFY, orderAlias.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteOrderAlias(OrderAlias orderAlias, BasePK deletedBy) {
        orderAlias.setThruTime(session.getStartTime());

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
