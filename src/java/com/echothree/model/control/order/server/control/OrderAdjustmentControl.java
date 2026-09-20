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
import com.echothree.model.control.order.common.choice.OrderAdjustmentTypeChoicesBean;
import com.echothree.model.control.order.common.transfer.OrderAdjustmentTypeDescriptionTransfer;
import com.echothree.model.control.order.common.transfer.OrderAdjustmentTypeTransfer;
import com.echothree.model.control.order.server.transfer.OrderAdjustmentTypeDescriptionTransferCache;
import com.echothree.model.control.order.server.transfer.OrderAdjustmentTypeTransferCache;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.order.common.pk.OrderAdjustmentPK;
import com.echothree.model.data.order.common.pk.OrderAdjustmentTypePK;
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
import static com.echothree.model.jooq.server.keys.order.OrderForeignKeys.ORDER_ADJUSTMENTS_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.order.OrderForeignKeys.ORDER_ADJUSTMENT_TYPES_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.order.OrderForeignKeys.ORDER_ADJUSTMENT_TYPE_DESCRIPTIONS_LANGUAGE_FK;
import static com.echothree.model.jooq.server.tables.order.OrderAdjustmentDetails.OrderAdjustmentDetails;
import static com.echothree.model.jooq.server.tables.order.OrderAdjustmentTypeDescriptions.OrderAdjustmentTypeDescriptions;
import static com.echothree.model.jooq.server.tables.order.OrderAdjustmentTypeDetails.OrderAdjustmentTypeDetails;
import static com.echothree.model.jooq.server.tables.order.OrderAdjustmentTypes.OrderAdjustmentTypes;
import static com.echothree.model.jooq.server.tables.order.OrderAdjustments.OrderAdjustments;
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
public class OrderAdjustmentControl
        extends BaseModelControl {

    /** Creates a new instance of OrderControl */
    protected OrderAdjustmentControl() {
        super();
    }
    
    // --------------------------------------------------------------------------------
    //   Order Adjustment Types
    // --------------------------------------------------------------------------------

    @Inject
    protected OrderAdjustmentTypeDetailFactory orderAdjustmentTypeDetailFactory;

    @Inject
    private OrderAdjustmentTypeFactory orderAdjustmentTypeFactory;

    @Inject
    private OrderAdjustmentTypeTransferCache orderAdjustmentTypeTransferCache;

    public OrderAdjustmentType createOrderAdjustmentType(OrderType orderType, String orderAdjustmentTypeName,
            Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        var defaultOrderAdjustmentType = getDefaultOrderAdjustmentType(orderType);
        var defaultFound = defaultOrderAdjustmentType != null;

        if(defaultFound && isDefault) {
            var defaultOrderAdjustmentTypeDetailValue = getDefaultOrderAdjustmentTypeDetailValueForUpdate(orderType);

            defaultOrderAdjustmentTypeDetailValue.setIsDefault(false);
            updateOrderAdjustmentTypeFromValue(defaultOrderAdjustmentTypeDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var orderAdjustmentType = orderAdjustmentTypeFactory.create();
        var orderAdjustmentTypeDetail = orderAdjustmentTypeDetailFactory.create(orderAdjustmentType, orderType,
                orderAdjustmentTypeName, isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);

        // Convert to R/W
        orderAdjustmentType = orderAdjustmentTypeFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                orderAdjustmentType.getPrimaryKey());
        orderAdjustmentType.setActiveDetail(orderAdjustmentTypeDetail);
        orderAdjustmentType.setLastDetail(orderAdjustmentTypeDetail);
        orderAdjustmentType.store();

        sendEvent(orderAdjustmentType.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return orderAdjustmentType;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.OrderAdjustmentType */
    public OrderAdjustmentType getOrderAdjustmentTypeByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new OrderAdjustmentTypePK(entityInstance.getEntityUniqueId());

        return orderAdjustmentTypeFactory.getEntityFromPK(entityPermission, pk);
    }

    public OrderAdjustmentType getOrderAdjustmentTypeByEntityInstance(EntityInstance entityInstance) {
        return getOrderAdjustmentTypeByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public OrderAdjustmentType getOrderAdjustmentTypeByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getOrderAdjustmentTypeByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countOrderAdjustmentTypesByOrderType(final OrderType orderType) {
        return session.getDslContext()
                .selectCount()
                .from(OrderAdjustmentTypes)
                .join(OrderAdjustmentTypeDetails).onKey(ORDER_ADJUSTMENT_TYPES_ACTIVE_DETAIL_FK)
                .where(OrderAdjustmentTypeDetails.ORDER_TYPE.eq(orderType.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private OrderAdjustmentType getOrderAdjustmentTypeByName(OrderType orderType, String orderAdjustmentTypeName, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(OrderAdjustmentTypes.fields())
                .from(OrderAdjustmentTypes)
                .join(OrderAdjustmentTypeDetails).onKey(ORDER_ADJUSTMENT_TYPES_ACTIVE_DETAIL_FK)
                .where(OrderAdjustmentTypeDetails.ORDER_TYPE.eq(orderType.getPrimaryKey()),
                        OrderAdjustmentTypeDetails.ORDER_ADJUSTMENT_TYPE_NAME.eq(orderAdjustmentTypeName));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return orderAdjustmentTypeFactory.getEntityFromQuery(entityPermission, query);
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

    private OrderAdjustmentType getDefaultOrderAdjustmentType(OrderType orderType, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(OrderAdjustmentTypes.fields())
                .from(OrderAdjustmentTypes)
                .join(OrderAdjustmentTypeDetails).onKey(ORDER_ADJUSTMENT_TYPES_ACTIVE_DETAIL_FK)
                .where(OrderAdjustmentTypeDetails.ORDER_TYPE.eq(orderType.getPrimaryKey()),
                        OrderAdjustmentTypeDetails.IS_DEFAULT.eq(true));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return orderAdjustmentTypeFactory.getEntityFromQuery(entityPermission, query);
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

    private List<OrderAdjustmentType> getOrderAdjustmentTypes(OrderType orderType, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(OrderAdjustmentTypes.fields())
                .from(OrderAdjustmentTypes)
                .join(OrderAdjustmentTypeDetails).onKey(ORDER_ADJUSTMENT_TYPES_ACTIVE_DETAIL_FK)
                .where(OrderAdjustmentTypeDetails.ORDER_TYPE.eq(orderType.getPrimaryKey()));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(OrderAdjustmentTypeDetails.SORT_ORDER, OrderAdjustmentTypeDetails.ORDER_ADJUSTMENT_TYPE_NAME),
                    OrderAdjustmentTypeFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return orderAdjustmentTypeFactory.getEntitiesFromQuery(entityPermission, query);
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

    public List<OrderAdjustmentTypeTransfer> getOrderAdjustmentTypeTransfers(UserVisit userVisit, Collection<OrderAdjustmentType> orderAdjustmentTypes) {
        List<OrderAdjustmentTypeTransfer> orderAdjustmentTypeTransfers = new ArrayList<>(orderAdjustmentTypes.size());

        orderAdjustmentTypes.forEach((orderAdjustmentType) ->
                orderAdjustmentTypeTransfers.add(orderAdjustmentTypeTransferCache.getOrderAdjustmentTypeTransfer(userVisit, orderAdjustmentType))
        );

        return orderAdjustmentTypeTransfers;
    }

    public List<OrderAdjustmentTypeTransfer> getOrderAdjustmentTypeTransfers(UserVisit userVisit, OrderType orderType) {
        return getOrderAdjustmentTypeTransfers(userVisit, getOrderAdjustmentTypes(orderType));
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
            var orderAdjustmentType = orderAdjustmentTypeFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     orderAdjustmentTypeDetailValue.getOrderAdjustmentTypePK());
            var orderAdjustmentTypeDetail = orderAdjustmentType.getActiveDetailForUpdate();

            orderAdjustmentTypeDetail.setThruTime(session.getStartTime());
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

            orderAdjustmentTypeDetail = orderAdjustmentTypeDetailFactory.create(orderAdjustmentTypePK, orderTypePK, orderAdjustmentTypeName, isDefault, sortOrder,
                    session.getStartTime(), Session.MAX_TIME);

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
        orderAdjustmentTypeDetail.setThruTime(session.getStartTime());
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

    @Inject
    private OrderAdjustmentTypeDescriptionFactory orderAdjustmentTypeDescriptionFactory;

    @Inject
    private OrderAdjustmentTypeDescriptionTransferCache orderAdjustmentTypeDescriptionTransferCache;

    public OrderAdjustmentTypeDescription createOrderAdjustmentTypeDescription(OrderAdjustmentType orderAdjustmentType, Language language, String description, BasePK createdBy) {
        var orderAdjustmentTypeDescription = orderAdjustmentTypeDescriptionFactory.create(orderAdjustmentType, language, description,
                session.getStartTime(), Session.MAX_TIME);

        sendEvent(orderAdjustmentType.getPrimaryKey(), EventTypes.MODIFY, orderAdjustmentTypeDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return orderAdjustmentTypeDescription;
    }

    private OrderAdjustmentTypeDescription getOrderAdjustmentTypeDescription(OrderAdjustmentType orderAdjustmentType, Language language, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(OrderAdjustmentTypeDescriptions.fields())
                .from(OrderAdjustmentTypeDescriptions)
                .where(OrderAdjustmentTypeDescriptions.ORDER_ADJUSTMENT_TYPE.eq(orderAdjustmentType.getPrimaryKey()),
                        OrderAdjustmentTypeDescriptions.LANGUAGE.eq(language.getPrimaryKey()),
                        OrderAdjustmentTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return orderAdjustmentTypeDescriptionFactory.getEntityFromQuery(entityPermission, query);
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

    private List<OrderAdjustmentTypeDescription> getOrderAdjustmentTypeDescriptionsByOrderAdjustmentType(OrderAdjustmentType orderAdjustmentType, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(OrderAdjustmentTypeDescriptions.fields())
                    .from(OrderAdjustmentTypeDescriptions)
                    .join(Languages).onKey(ORDER_ADJUSTMENT_TYPE_DESCRIPTIONS_LANGUAGE_FK)
                    .where(OrderAdjustmentTypeDescriptions.ORDER_ADJUSTMENT_TYPE.eq(orderAdjustmentType.getPrimaryKey()),
                            OrderAdjustmentTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(Languages.SORT_ORDER, Languages.LANGUAGE_ISO_NAME),
                    OrderAdjustmentTypeDescriptionFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(OrderAdjustmentTypeDescriptions.fields())
                    .from(OrderAdjustmentTypeDescriptions)
                    .where(OrderAdjustmentTypeDescriptions.ORDER_ADJUSTMENT_TYPE.eq(orderAdjustmentType.getPrimaryKey()),
                            OrderAdjustmentTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return orderAdjustmentTypeDescriptionFactory.getEntitiesFromQuery(entityPermission, query);
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
            var orderAdjustmentTypeDescription = orderAdjustmentTypeDescriptionFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                    orderAdjustmentTypeDescriptionValue.getPrimaryKey());

            orderAdjustmentTypeDescription.setThruTime(session.getStartTime());
            orderAdjustmentTypeDescription.store();

            var orderAdjustmentType = orderAdjustmentTypeDescription.getOrderAdjustmentType();
            var language = orderAdjustmentTypeDescription.getLanguage();
            var description = orderAdjustmentTypeDescriptionValue.getDescription();

            orderAdjustmentTypeDescription = orderAdjustmentTypeDescriptionFactory.create(orderAdjustmentType, language, description,
                    session.getStartTime(), Session.MAX_TIME);

            sendEvent(orderAdjustmentType.getPrimaryKey(), EventTypes.MODIFY, orderAdjustmentTypeDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteOrderAdjustmentTypeDescription(OrderAdjustmentTypeDescription orderAdjustmentTypeDescription, BasePK deletedBy) {
        orderAdjustmentTypeDescription.setThruTime(session.getStartTime());

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

    @Inject
    protected OrderAdjustmentDetailFactory orderAdjustmentDetailFactory;

    @Inject
    private OrderAdjustmentFactory orderAdjustmentFactory;

    public OrderAdjustment createOrderAdjustment(Order order, Integer orderAdjustmentSequence, OrderAdjustmentType orderAdjustmentType, Long amount,
            BasePK createdBy) {
        var orderAdjustment = orderAdjustmentFactory.create();
        var orderAdjustmentDetail = orderAdjustmentDetailFactory.create(orderAdjustment,
                order, orderAdjustmentSequence, orderAdjustmentType, amount, session.getStartTime(), Session.MAX_TIME);

        // Convert to R/W
        orderAdjustment = orderAdjustmentFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                orderAdjustment.getPrimaryKey());
        orderAdjustment.setActiveDetail(orderAdjustmentDetail);
        orderAdjustment.setLastDetail(orderAdjustmentDetail);
        orderAdjustment.store();

        sendEvent(orderAdjustment.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return orderAdjustment;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.OrderAdjustment */
    public OrderAdjustment getOrderAdjustmentByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new OrderAdjustmentPK(entityInstance.getEntityUniqueId());

        return orderAdjustmentFactory.getEntityFromPK(entityPermission, pk);
    }

    public OrderAdjustment getOrderAdjustmentByEntityInstance(EntityInstance entityInstance) {
        return getOrderAdjustmentByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public OrderAdjustment getOrderAdjustmentByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getOrderAdjustmentByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countOrderAdjustmentsByOrder(final Order order) {
        return session.getDslContext()
                .selectCount()
                .from(OrderAdjustments)
                .join(OrderAdjustmentDetails).onKey(ORDER_ADJUSTMENTS_ACTIVE_DETAIL_FK)
                .where(OrderAdjustmentDetails.ORDER.eq(order.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countOrderAdjustmentsByOrderAdjustmentType(final OrderAdjustmentType orderAdjustmentType) {
        return session.getDslContext()
                .selectCount()
                .from(OrderAdjustments)
                .join(OrderAdjustmentDetails).onKey(ORDER_ADJUSTMENTS_ACTIVE_DETAIL_FK)
                .where(OrderAdjustmentDetails.ORDER_ADJUSTMENT_TYPE.eq(orderAdjustmentType.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private List<OrderAdjustment> getOrderAdjustmentsByOrder(Order order, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(OrderAdjustments.fields())
                .from(OrderAdjustments)
                .join(OrderAdjustmentDetails).onKey(ORDER_ADJUSTMENTS_ACTIVE_DETAIL_FK)
                .where(OrderAdjustmentDetails.ORDER.eq(order.getPrimaryKey()));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(OrderAdjustmentDetails.ORDER_ADJUSTMENT_SEQUENCE),
                    OrderAdjustmentFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return orderAdjustmentFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<OrderAdjustment> getOrderAdjustmentsByOrder(Order order) {
        return getOrderAdjustmentsByOrder(order, EntityPermission.READ_ONLY);
    }

    public List<OrderAdjustment> getOrderAdjustmentsByOrderForUpdate(Order order) {
        return getOrderAdjustmentsByOrder(order, EntityPermission.READ_WRITE);
    }

    public void deleteOrderAdjustment(OrderAdjustment orderAdjustment, BasePK deletedBy) {
        var orderAdjustmentDetail = orderAdjustment.getLastDetailForUpdate();
        orderAdjustmentDetail.setThruTime(session.getStartTime());
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
