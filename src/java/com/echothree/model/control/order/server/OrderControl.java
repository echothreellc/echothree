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

package com.echothree.model.control.order.server;

import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.order.common.OrderRoleTypes;
import com.echothree.model.control.order.common.OrderTypes;
import com.echothree.model.control.order.common.choice.OrderAdjustmentTypeChoicesBean;
import com.echothree.model.control.order.common.choice.OrderAliasTypeChoicesBean;
import com.echothree.model.control.order.common.choice.OrderLineAdjustmentTypeChoicesBean;
import com.echothree.model.control.order.common.choice.OrderPriorityChoicesBean;
import com.echothree.model.control.order.common.choice.OrderTimeTypeChoicesBean;
import com.echothree.model.control.order.common.choice.OrderTypeChoicesBean;
import com.echothree.model.control.order.common.transfer.OrderAdjustmentTypeDescriptionTransfer;
import com.echothree.model.control.order.common.transfer.OrderAdjustmentTypeTransfer;
import com.echothree.model.control.order.common.transfer.OrderAliasTransfer;
import com.echothree.model.control.order.common.transfer.OrderAliasTypeDescriptionTransfer;
import com.echothree.model.control.order.common.transfer.OrderAliasTypeTransfer;
import com.echothree.model.control.order.common.transfer.OrderLineAdjustmentTypeDescriptionTransfer;
import com.echothree.model.control.order.common.transfer.OrderLineAdjustmentTypeTransfer;
import com.echothree.model.control.order.common.transfer.OrderLineTimeTransfer;
import com.echothree.model.control.order.common.transfer.OrderPaymentPreferenceTransfer;
import com.echothree.model.control.order.common.transfer.OrderPriorityDescriptionTransfer;
import com.echothree.model.control.order.common.transfer.OrderPriorityTransfer;
import com.echothree.model.control.order.common.transfer.OrderRoleTransfer;
import com.echothree.model.control.order.common.transfer.OrderRoleTypeTransfer;
import com.echothree.model.control.order.common.transfer.OrderShipmentGroupTransfer;
import com.echothree.model.control.order.common.transfer.OrderTimeTransfer;
import com.echothree.model.control.order.common.transfer.OrderTimeTypeDescriptionTransfer;
import com.echothree.model.control.order.common.transfer.OrderTimeTypeTransfer;
import com.echothree.model.control.order.common.transfer.OrderTransfer;
import com.echothree.model.control.order.common.transfer.OrderTypeDescriptionTransfer;
import com.echothree.model.control.order.common.transfer.OrderTypeTransfer;
import com.echothree.model.control.order.server.transfer.OrderAdjustmentTypeDescriptionTransferCache;
import com.echothree.model.control.order.server.transfer.OrderAdjustmentTypeTransferCache;
import com.echothree.model.control.order.server.transfer.OrderAliasTransferCache;
import com.echothree.model.control.order.server.transfer.OrderAliasTypeDescriptionTransferCache;
import com.echothree.model.control.order.server.transfer.OrderAliasTypeTransferCache;
import com.echothree.model.control.order.server.transfer.OrderLineAdjustmentTypeDescriptionTransferCache;
import com.echothree.model.control.order.server.transfer.OrderLineAdjustmentTypeTransferCache;
import com.echothree.model.control.order.server.transfer.OrderLineTimeTransferCache;
import com.echothree.model.control.order.server.transfer.OrderPaymentPreferenceTransferCache;
import com.echothree.model.control.order.server.transfer.OrderPriorityDescriptionTransferCache;
import com.echothree.model.control.order.server.transfer.OrderPriorityTransferCache;
import com.echothree.model.control.order.server.transfer.OrderShipmentGroupTransferCache;
import com.echothree.model.control.order.server.transfer.OrderTimeTransferCache;
import com.echothree.model.control.order.server.transfer.OrderTimeTypeDescriptionTransferCache;
import com.echothree.model.control.order.server.transfer.OrderTimeTypeTransferCache;
import com.echothree.model.control.order.server.transfer.OrderTransferCaches;
import com.echothree.model.control.order.server.transfer.OrderTypeDescriptionTransferCache;
import com.echothree.model.control.order.server.transfer.OrderTypeTransferCache;
import com.echothree.model.control.wishlist.server.WishlistControl;
import com.echothree.model.data.accounting.common.pk.CurrencyPK;
import com.echothree.model.data.accounting.server.entity.Currency;
import com.echothree.model.data.batch.common.pk.BatchPK;
import com.echothree.model.data.batch.server.entity.Batch;
import com.echothree.model.data.cancellationpolicy.common.pk.CancellationPolicyPK;
import com.echothree.model.data.cancellationpolicy.server.entity.CancellationPolicy;
import com.echothree.model.data.contact.common.pk.PartyContactMechanismPK;
import com.echothree.model.data.contact.server.entity.PartyContactMechanism;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.inventory.common.pk.InventoryConditionPK;
import com.echothree.model.data.inventory.server.entity.InventoryCondition;
import com.echothree.model.data.item.common.pk.ItemDeliveryTypePK;
import com.echothree.model.data.item.common.pk.ItemPK;
import com.echothree.model.data.item.server.entity.Item;
import com.echothree.model.data.item.server.entity.ItemDeliveryType;
import com.echothree.model.data.order.common.pk.OrderAdjustmentTypePK;
import com.echothree.model.data.order.common.pk.OrderAliasTypePK;
import com.echothree.model.data.order.common.pk.OrderLineAdjustmentTypePK;
import com.echothree.model.data.order.common.pk.OrderLinePK;
import com.echothree.model.data.order.common.pk.OrderPK;
import com.echothree.model.data.order.common.pk.OrderPaymentPreferencePK;
import com.echothree.model.data.order.common.pk.OrderPriorityPK;
import com.echothree.model.data.order.common.pk.OrderShipmentGroupPK;
import com.echothree.model.data.order.common.pk.OrderTimeTypePK;
import com.echothree.model.data.order.common.pk.OrderTypePK;
import com.echothree.model.data.order.server.entity.Order;
import com.echothree.model.data.order.server.entity.OrderAdjustment;
import com.echothree.model.data.order.server.entity.OrderAdjustmentDetail;
import com.echothree.model.data.order.server.entity.OrderAdjustmentType;
import com.echothree.model.data.order.server.entity.OrderAdjustmentTypeDescription;
import com.echothree.model.data.order.server.entity.OrderAdjustmentTypeDetail;
import com.echothree.model.data.order.server.entity.OrderAlias;
import com.echothree.model.data.order.server.entity.OrderAliasType;
import com.echothree.model.data.order.server.entity.OrderAliasTypeDescription;
import com.echothree.model.data.order.server.entity.OrderAliasTypeDetail;
import com.echothree.model.data.order.server.entity.OrderBatch;
import com.echothree.model.data.order.server.entity.OrderDetail;
import com.echothree.model.data.order.server.entity.OrderLine;
import com.echothree.model.data.order.server.entity.OrderLineAdjustment;
import com.echothree.model.data.order.server.entity.OrderLineAdjustmentDetail;
import com.echothree.model.data.order.server.entity.OrderLineAdjustmentType;
import com.echothree.model.data.order.server.entity.OrderLineAdjustmentTypeDescription;
import com.echothree.model.data.order.server.entity.OrderLineAdjustmentTypeDetail;
import com.echothree.model.data.order.server.entity.OrderLineDetail;
import com.echothree.model.data.order.server.entity.OrderLineStatus;
import com.echothree.model.data.order.server.entity.OrderLineTime;
import com.echothree.model.data.order.server.entity.OrderPaymentPreference;
import com.echothree.model.data.order.server.entity.OrderPaymentPreferenceDetail;
import com.echothree.model.data.order.server.entity.OrderPriority;
import com.echothree.model.data.order.server.entity.OrderPriorityDescription;
import com.echothree.model.data.order.server.entity.OrderPriorityDetail;
import com.echothree.model.data.order.server.entity.OrderRole;
import com.echothree.model.data.order.server.entity.OrderRoleType;
import com.echothree.model.data.order.server.entity.OrderRoleTypeDescription;
import com.echothree.model.data.order.server.entity.OrderShipmentGroup;
import com.echothree.model.data.order.server.entity.OrderShipmentGroupDetail;
import com.echothree.model.data.order.server.entity.OrderStatus;
import com.echothree.model.data.order.server.entity.OrderTime;
import com.echothree.model.data.order.server.entity.OrderTimeType;
import com.echothree.model.data.order.server.entity.OrderTimeTypeDescription;
import com.echothree.model.data.order.server.entity.OrderTimeTypeDetail;
import com.echothree.model.data.order.server.entity.OrderType;
import com.echothree.model.data.order.server.entity.OrderTypeDescription;
import com.echothree.model.data.order.server.entity.OrderTypeDetail;
import com.echothree.model.data.order.server.entity.OrderUserVisit;
import com.echothree.model.data.order.server.factory.OrderAdjustmentDetailFactory;
import com.echothree.model.data.order.server.factory.OrderAdjustmentFactory;
import com.echothree.model.data.order.server.factory.OrderAdjustmentTypeDescriptionFactory;
import com.echothree.model.data.order.server.factory.OrderAdjustmentTypeDetailFactory;
import com.echothree.model.data.order.server.factory.OrderAdjustmentTypeFactory;
import com.echothree.model.data.order.server.factory.OrderAliasFactory;
import com.echothree.model.data.order.server.factory.OrderAliasTypeDescriptionFactory;
import com.echothree.model.data.order.server.factory.OrderAliasTypeDetailFactory;
import com.echothree.model.data.order.server.factory.OrderAliasTypeFactory;
import com.echothree.model.data.order.server.factory.OrderBatchFactory;
import com.echothree.model.data.order.server.factory.OrderDetailFactory;
import com.echothree.model.data.order.server.factory.OrderFactory;
import com.echothree.model.data.order.server.factory.OrderLineAdjustmentDetailFactory;
import com.echothree.model.data.order.server.factory.OrderLineAdjustmentFactory;
import com.echothree.model.data.order.server.factory.OrderLineAdjustmentTypeDescriptionFactory;
import com.echothree.model.data.order.server.factory.OrderLineAdjustmentTypeDetailFactory;
import com.echothree.model.data.order.server.factory.OrderLineAdjustmentTypeFactory;
import com.echothree.model.data.order.server.factory.OrderLineDetailFactory;
import com.echothree.model.data.order.server.factory.OrderLineFactory;
import com.echothree.model.data.order.server.factory.OrderLineStatusFactory;
import com.echothree.model.data.order.server.factory.OrderLineTimeFactory;
import com.echothree.model.data.order.server.factory.OrderPaymentPreferenceDetailFactory;
import com.echothree.model.data.order.server.factory.OrderPaymentPreferenceFactory;
import com.echothree.model.data.order.server.factory.OrderPriorityDescriptionFactory;
import com.echothree.model.data.order.server.factory.OrderPriorityDetailFactory;
import com.echothree.model.data.order.server.factory.OrderPriorityFactory;
import com.echothree.model.data.order.server.factory.OrderRoleFactory;
import com.echothree.model.data.order.server.factory.OrderRoleTypeDescriptionFactory;
import com.echothree.model.data.order.server.factory.OrderRoleTypeFactory;
import com.echothree.model.data.order.server.factory.OrderShipmentGroupDetailFactory;
import com.echothree.model.data.order.server.factory.OrderShipmentGroupFactory;
import com.echothree.model.data.order.server.factory.OrderStatusFactory;
import com.echothree.model.data.order.server.factory.OrderTimeFactory;
import com.echothree.model.data.order.server.factory.OrderTimeTypeDescriptionFactory;
import com.echothree.model.data.order.server.factory.OrderTimeTypeDetailFactory;
import com.echothree.model.data.order.server.factory.OrderTimeTypeFactory;
import com.echothree.model.data.order.server.factory.OrderTypeDescriptionFactory;
import com.echothree.model.data.order.server.factory.OrderTypeDetailFactory;
import com.echothree.model.data.order.server.factory.OrderTypeFactory;
import com.echothree.model.data.order.server.factory.OrderUserVisitFactory;
import com.echothree.model.data.order.server.value.OrderAdjustmentTypeDescriptionValue;
import com.echothree.model.data.order.server.value.OrderAdjustmentTypeDetailValue;
import com.echothree.model.data.order.server.value.OrderAliasTypeDescriptionValue;
import com.echothree.model.data.order.server.value.OrderAliasTypeDetailValue;
import com.echothree.model.data.order.server.value.OrderAliasValue;
import com.echothree.model.data.order.server.value.OrderBatchValue;
import com.echothree.model.data.order.server.value.OrderDetailValue;
import com.echothree.model.data.order.server.value.OrderLineAdjustmentTypeDescriptionValue;
import com.echothree.model.data.order.server.value.OrderLineAdjustmentTypeDetailValue;
import com.echothree.model.data.order.server.value.OrderLineDetailValue;
import com.echothree.model.data.order.server.value.OrderLineTimeValue;
import com.echothree.model.data.order.server.value.OrderPaymentPreferenceDetailValue;
import com.echothree.model.data.order.server.value.OrderPriorityDescriptionValue;
import com.echothree.model.data.order.server.value.OrderPriorityDetailValue;
import com.echothree.model.data.order.server.value.OrderShipmentGroupDetailValue;
import com.echothree.model.data.order.server.value.OrderTimeTypeDescriptionValue;
import com.echothree.model.data.order.server.value.OrderTimeTypeDetailValue;
import com.echothree.model.data.order.server.value.OrderTimeValue;
import com.echothree.model.data.order.server.value.OrderTypeDescriptionValue;
import com.echothree.model.data.order.server.value.OrderTypeDetailValue;
import com.echothree.model.data.order.server.value.OrderUserVisitValue;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.payment.common.pk.PartyPaymentMethodPK;
import com.echothree.model.data.payment.common.pk.PaymentMethodPK;
import com.echothree.model.data.payment.server.entity.PartyPaymentMethod;
import com.echothree.model.data.payment.server.entity.PaymentMethod;
import com.echothree.model.data.returnpolicy.common.pk.ReturnPolicyPK;
import com.echothree.model.data.returnpolicy.server.entity.ReturnPolicy;
import com.echothree.model.data.sequence.common.pk.SequenceTypePK;
import com.echothree.model.data.sequence.server.entity.SequenceType;
import com.echothree.model.data.shipping.common.pk.ShippingMethodPK;
import com.echothree.model.data.shipping.server.entity.ShippingMethod;
import com.echothree.model.data.term.common.pk.TermPK;
import com.echothree.model.data.term.server.entity.Term;
import com.echothree.model.data.uom.common.pk.UnitOfMeasureTypePK;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureType;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.model.data.wishlist.server.entity.Wishlist;
import com.echothree.model.data.wishlist.server.entity.WishlistLine;
import com.echothree.model.data.wishlist.server.entity.WishlistType;
import com.echothree.model.data.wishlist.server.entity.WishlistTypePriority;
import com.echothree.model.data.workflow.common.pk.WorkflowEntrancePK;
import com.echothree.model.data.workflow.common.pk.WorkflowPK;
import com.echothree.model.data.workflow.server.entity.Workflow;
import com.echothree.model.data.workflow.server.entity.WorkflowEntrance;
import com.echothree.util.common.exception.PersistenceDatabaseException;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseModelControl;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class OrderControl
        extends BaseModelControl {
    
    /** Creates a new instance of OrderControl */
    public OrderControl() {
        super();
    }
    
    // --------------------------------------------------------------------------------
    //   Order Transfer Caches
    // --------------------------------------------------------------------------------
    
    private OrderTransferCaches orderTransferCaches = null;
    
    public OrderTransferCaches getOrderTransferCaches(UserVisit userVisit) {
        if(orderTransferCaches == null) {
            orderTransferCaches = new OrderTransferCaches(userVisit, this);
        }
        
        return orderTransferCaches;
    }
    
    // --------------------------------------------------------------------------------
    //   Order Role Types
    // --------------------------------------------------------------------------------
    
    public OrderRoleType createOrderRoleType(String orderRoleTypeName, Integer sortOrder) {
        return OrderRoleTypeFactory.getInstance().create(orderRoleTypeName, sortOrder);
    }
    
    public OrderRoleType getOrderRoleTypeByName(String orderRoleTypeName) {
        OrderRoleType orderRoleType = null;
        
        try {
            PreparedStatement ps = OrderRoleTypeFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM orderroletypes " +
                    "WHERE ordrtyp_orderroletypename = ?");
            
            ps.setString(1, orderRoleTypeName);
            
            orderRoleType = OrderRoleTypeFactory.getInstance().getEntityFromQuery(EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return orderRoleType;
    }
    
    public OrderRoleTypeTransfer getOrderRoleTypeTransfer(UserVisit userVisit, OrderRoleType orderRoleType) {
        return getOrderTransferCaches(userVisit).getOrderRoleTypeTransferCache().getOrderRoleTypeTransfer(orderRoleType);
    }

    // --------------------------------------------------------------------------------
    //   Order Role Type Descriptions
    // --------------------------------------------------------------------------------
    
    public OrderRoleTypeDescription createOrderRoleTypeDescription(OrderRoleType orderRoleType, Language language,
            String description) {
        return OrderRoleTypeDescriptionFactory.getInstance().create(orderRoleType, language, description);
    }
    
    public OrderRoleTypeDescription getOrderRoleTypeDescription(OrderRoleType orderRoleType, Language language) {
        OrderRoleTypeDescription orderRoleTypeDescription = null;
        
        try {
            PreparedStatement ps = OrderRoleTypeDescriptionFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM orderroletypedescriptions " +
                    "WHERE ordrtyps_ordrtyp_orderroletypeid = ? AND ordrtyps_lang_languageid = ?");
            
            ps.setLong(1, orderRoleType.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            
            orderRoleTypeDescription = OrderRoleTypeDescriptionFactory.getInstance().getEntityFromQuery(session,
                    EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return orderRoleTypeDescription;
    }
    
    public String getBestOrderRoleTypeDescription(OrderRoleType orderRoleType, Language language) {
        String description;
        OrderRoleTypeDescription orderRoleTypeDescription = getOrderRoleTypeDescription(orderRoleType, language);

        if(orderRoleTypeDescription == null && !language.getIsDefault()) {
            orderRoleTypeDescription = getOrderRoleTypeDescription(orderRoleType, getPartyControl().getDefaultLanguage());
        }

        if(orderRoleTypeDescription == null) {
            description = orderRoleType.getOrderRoleTypeName();
        } else {
            description = orderRoleTypeDescription.getDescription();
        }

        return description;
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

        sendEventUsingNames(orderType.getPrimaryKey(), EventTypes.CREATE.name(), null, null, createdBy);

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

        orderTypes.stream().forEach((orderType) -> {
            orderTypeTransfers.add(orderTypeTransferCache.getOrderTypeTransfer(orderType));
        });

        return orderTypeTransfers;
    }

    public OrderTypeChoicesBean getOrderTypeChoices(String defaultOrderTypeChoice,
            Language language, boolean allowNullChoice) {
        List<OrderType> orderTypes = getOrderTypes();
        int size = orderTypes.size();
        List<String> labels = new ArrayList<>(size);
        List<String> values = new ArrayList<>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultOrderTypeChoice == null) {
                defaultValue = "";
            }
        }

        for(OrderType orderType: orderTypes) {
            OrderTypeDetail orderTypeDetail = orderType.getLastDetail();

            String label = getBestOrderTypeDescription(orderType, language);
            String value = orderTypeDetail.getOrderTypeName();

            labels.add(label == null? value: label);
            values.add(value);

            boolean usingDefaultChoice = defaultOrderTypeChoice == null? false: defaultOrderTypeChoice.equals(value);
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

            sendEventUsingNames(orderTypePK, EventTypes.MODIFY.name(), null, null, updatedBy);
        }
    }

    public void updateOrderTypeFromValue(OrderTypeDetailValue orderTypeDetailValue, BasePK updatedBy) {
        updateOrderTypeFromValue(orderTypeDetailValue, true, updatedBy);
    }

    private void deleteOrderType(OrderType orderType, boolean checkDefault, BasePK deletedBy) {
        OrderTypeDetail orderTypeDetail = orderType.getLastDetailForUpdate();

        deleteOrderTypesByParentOrderType(orderType, deletedBy);
        deleteOrderTypeDescriptionsByOrderType(orderType, deletedBy);
        deleteOrderAliasTypesByOrderType(orderType, deletedBy);
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
                    OrderTypeDetailValue orderTypeDetailValue = defaultOrderType.getLastDetailForUpdate().getOrderTypeDetailValue().clone();

                    orderTypeDetailValue.setIsDefault(Boolean.TRUE);
                    updateOrderTypeFromValue(orderTypeDetailValue, false, deletedBy);
                }
            }
        }

        sendEventUsingNames(orderType.getPrimaryKey(), EventTypes.DELETE.name(), null, null, deletedBy);
    }

    public void deleteOrderType(OrderType orderType, BasePK deletedBy) {
        deleteOrderType(orderType, true, deletedBy);
    }

    private void deleteOrderTypes(List<OrderType> orderTypes, boolean checkDefault, BasePK deletedBy) {
        orderTypes.stream().forEach((orderType) -> {
            deleteOrderType(orderType, checkDefault, deletedBy);
        });
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

        sendEventUsingNames(orderType.getPrimaryKey(), EventTypes.MODIFY.name(), orderTypeDescription.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);

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

        orderTypeDescriptions.stream().forEach((orderTypeDescription) -> {
            orderTypeDescriptionTransfers.add(orderTypeDescriptionTransferCache.getOrderTypeDescriptionTransfer(orderTypeDescription));
        });

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

            sendEventUsingNames(orderType.getPrimaryKey(), EventTypes.MODIFY.name(), orderTypeDescription.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }

    public void deleteOrderTypeDescription(OrderTypeDescription orderTypeDescription, BasePK deletedBy) {
        orderTypeDescription.setThruTime(session.START_TIME_LONG);

        sendEventUsingNames(orderTypeDescription.getOrderTypePK(), EventTypes.MODIFY.name(), orderTypeDescription.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);

    }

    public void deleteOrderTypeDescriptionsByOrderType(OrderType orderType, BasePK deletedBy) {
        List<OrderTypeDescription> orderTypeDescriptions = getOrderTypeDescriptionsByOrderTypeForUpdate(orderType);

        orderTypeDescriptions.stream().forEach((orderTypeDescription) -> {
            deleteOrderTypeDescription(orderTypeDescription, deletedBy);
        });
    }

    // --------------------------------------------------------------------------------
    //   Order Time Types
    // --------------------------------------------------------------------------------

    public OrderTimeType createOrderTimeType(OrderType orderType, String orderTimeTypeName, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        OrderTimeType defaultOrderTimeType = getDefaultOrderTimeType(orderType);
        boolean defaultFound = defaultOrderTimeType != null;

        if(defaultFound && isDefault) {
            OrderTimeTypeDetailValue defaultOrderTimeTypeDetailValue = getDefaultOrderTimeTypeDetailValueForUpdate(orderType);

            defaultOrderTimeTypeDetailValue.setIsDefault(Boolean.FALSE);
            updateOrderTimeTypeFromValue(defaultOrderTimeTypeDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = Boolean.TRUE;
        }

        OrderTimeType orderTimeType = OrderTimeTypeFactory.getInstance().create();
        OrderTimeTypeDetail orderTimeTypeDetail = OrderTimeTypeDetailFactory.getInstance().create(orderTimeType, orderType, orderTimeTypeName, isDefault,
                sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        // Convert to R/W
        orderTimeType = OrderTimeTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                orderTimeType.getPrimaryKey());
        orderTimeType.setActiveDetail(orderTimeTypeDetail);
        orderTimeType.setLastDetail(orderTimeTypeDetail);
        orderTimeType.store();

        sendEventUsingNames(orderTimeType.getPrimaryKey(), EventTypes.CREATE.name(), null, null, createdBy);

        return orderTimeType;
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

    private OrderTimeType getOrderTimeTypeByName(OrderType orderType, String orderTimeTypeName, EntityPermission entityPermission) {
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

    private OrderTimeType getDefaultOrderTimeType(OrderType orderType, EntityPermission entityPermission) {
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
        return getOrderTransferCaches(userVisit).getOrderTimeTypeTransferCache().getOrderTimeTypeTransfer(orderTimeType);
    }

    public List<OrderTimeTypeTransfer> getOrderTimeTypeTransfers(UserVisit userVisit, OrderType orderType) {
        List<OrderTimeType> orderTimeTypes = getOrderTimeTypes(orderType);
        List<OrderTimeTypeTransfer> orderTimeTypeTransfers = new ArrayList<>(orderTimeTypes.size());
        OrderTimeTypeTransferCache orderTimeTypeTransferCache = getOrderTransferCaches(userVisit).getOrderTimeTypeTransferCache();

        orderTimeTypes.stream().forEach((orderTimeType) -> {
            orderTimeTypeTransfers.add(orderTimeTypeTransferCache.getOrderTimeTypeTransfer(orderTimeType));
        });

        return orderTimeTypeTransfers;
    }

    public OrderTimeTypeChoicesBean getOrderTimeTypeChoices(String defaultOrderTimeTypeChoice, Language language, boolean allowNullChoice,
            OrderType orderType) {
        List<OrderTimeType> orderTimeTypes = getOrderTimeTypes(orderType);
        int size = orderTimeTypes.size();
        List<String> labels = new ArrayList<>(size);
        List<String> values = new ArrayList<>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultOrderTimeTypeChoice == null) {
                defaultValue = "";
            }
        }

        for(OrderTimeType orderTimeType: orderTimeTypes) {
            OrderTimeTypeDetail orderTimeTypeDetail = orderTimeType.getLastDetail();

            String label = getBestOrderTimeTypeDescription(orderTimeType, language);
            String value = orderTimeTypeDetail.getOrderTimeTypeName();

            labels.add(label == null? value: label);
            values.add(value);

            boolean usingDefaultChoice = defaultOrderTimeTypeChoice == null? false: defaultOrderTimeTypeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && orderTimeTypeDetail.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new OrderTimeTypeChoicesBean(labels, values, defaultValue);
    }

    private void updateOrderTimeTypeFromValue(OrderTimeTypeDetailValue orderTimeTypeDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(orderTimeTypeDetailValue.hasBeenModified()) {
            OrderTimeType orderTimeType = OrderTimeTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     orderTimeTypeDetailValue.getOrderTimeTypePK());
            OrderTimeTypeDetail orderTimeTypeDetail = orderTimeType.getActiveDetailForUpdate();

            orderTimeTypeDetail.setThruTime(session.START_TIME_LONG);
            orderTimeTypeDetail.store();

            OrderType orderType = orderTimeTypeDetail.getOrderType(); // Not updated
            OrderTypePK orderTypePK = orderType.getPrimaryKey(); // Not updated
            OrderTimeTypePK orderTimeTypePK = orderTimeTypeDetail.getOrderTimeTypePK(); // Not updated
            String orderTimeTypeName = orderTimeTypeDetailValue.getOrderTimeTypeName();
            Boolean isDefault = orderTimeTypeDetailValue.getIsDefault();
            Integer sortOrder = orderTimeTypeDetailValue.getSortOrder();

            if(checkDefault) {
                OrderTimeType defaultOrderTimeType = getDefaultOrderTimeType(orderType);
                boolean defaultFound = defaultOrderTimeType != null && !defaultOrderTimeType.equals(orderTimeType);

                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    OrderTimeTypeDetailValue defaultOrderTimeTypeDetailValue = getDefaultOrderTimeTypeDetailValueForUpdate(orderType);

                    defaultOrderTimeTypeDetailValue.setIsDefault(Boolean.FALSE);
                    updateOrderTimeTypeFromValue(defaultOrderTimeTypeDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = Boolean.TRUE;
                }
            }

            orderTimeTypeDetail = OrderTimeTypeDetailFactory.getInstance().create(orderTimeTypePK, orderTypePK, orderTimeTypeName, isDefault, sortOrder,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);

            orderTimeType.setActiveDetail(orderTimeTypeDetail);
            orderTimeType.setLastDetail(orderTimeTypeDetail);

            sendEventUsingNames(orderTimeTypePK, EventTypes.MODIFY.name(), null, null, updatedBy);
        }
    }

    public void updateOrderTimeTypeFromValue(OrderTimeTypeDetailValue orderTimeTypeDetailValue, BasePK updatedBy) {
        updateOrderTimeTypeFromValue(orderTimeTypeDetailValue, true, updatedBy);
    }

    public void deleteOrderTimeType(OrderTimeType orderTimeType, BasePK deletedBy) {
        deleteOrderTimesByOrderTimeType(orderTimeType, deletedBy);
        deleteOrderTimeTypeDescriptionsByOrderTimeType(orderTimeType, deletedBy);

        OrderTimeTypeDetail orderTimeTypeDetail = orderTimeType.getLastDetailForUpdate();
        orderTimeTypeDetail.setThruTime(session.START_TIME_LONG);
        orderTimeType.setActiveDetail(null);
        orderTimeType.store();

        // Check for default, and pick one if necessary
        OrderType orderType = orderTimeTypeDetail.getOrderType();
        OrderTimeType defaultOrderTimeType = getDefaultOrderTimeType(orderType);
        if(defaultOrderTimeType == null) {
            List<OrderTimeType> orderTimeTypes = getOrderTimeTypesForUpdate(orderType);

            if(!orderTimeTypes.isEmpty()) {
                Iterator<OrderTimeType> iter = orderTimeTypes.iterator();
                if(iter.hasNext()) {
                    defaultOrderTimeType = iter.next();
                }
                OrderTimeTypeDetailValue orderTimeTypeDetailValue = defaultOrderTimeType.getLastDetailForUpdate().getOrderTimeTypeDetailValue().clone();

                orderTimeTypeDetailValue.setIsDefault(Boolean.TRUE);
                updateOrderTimeTypeFromValue(orderTimeTypeDetailValue, false, deletedBy);
            }
        }

        sendEventUsingNames(orderTimeType.getPrimaryKey(), EventTypes.DELETE.name(), null, null, deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Order Time Type Descriptions
    // --------------------------------------------------------------------------------

    public OrderTimeTypeDescription createOrderTimeTypeDescription(OrderTimeType orderTimeType, Language language, String description, BasePK createdBy) {
        OrderTimeTypeDescription orderTimeTypeDescription = OrderTimeTypeDescriptionFactory.getInstance().create(orderTimeType, language, description,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEventUsingNames(orderTimeType.getPrimaryKey(), EventTypes.MODIFY.name(), orderTimeTypeDescription.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);

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
        OrderTimeTypeDescription orderTimeTypeDescription = getOrderTimeTypeDescription(orderTimeType, language);

        if(orderTimeTypeDescription == null && !language.getIsDefault()) {
            orderTimeTypeDescription = getOrderTimeTypeDescription(orderTimeType, getPartyControl().getDefaultLanguage());
        }

        if(orderTimeTypeDescription == null) {
            description = orderTimeType.getLastDetail().getOrderTimeTypeName();
        } else {
            description = orderTimeTypeDescription.getDescription();
        }

        return description;
    }

    public OrderTimeTypeDescriptionTransfer getOrderTimeTypeDescriptionTransfer(UserVisit userVisit, OrderTimeTypeDescription orderTimeTypeDescription) {
        return getOrderTransferCaches(userVisit).getOrderTimeTypeDescriptionTransferCache().getOrderTimeTypeDescriptionTransfer(orderTimeTypeDescription);
    }

    public List<OrderTimeTypeDescriptionTransfer> getOrderTimeTypeDescriptionTransfersByOrderTimeType(UserVisit userVisit, OrderTimeType orderTimeType) {
        List<OrderTimeTypeDescription> orderTimeTypeDescriptions = getOrderTimeTypeDescriptionsByOrderTimeType(orderTimeType);
        List<OrderTimeTypeDescriptionTransfer> orderTimeTypeDescriptionTransfers = new ArrayList<>(orderTimeTypeDescriptions.size());
        OrderTimeTypeDescriptionTransferCache orderTimeTypeDescriptionTransferCache = getOrderTransferCaches(userVisit).getOrderTimeTypeDescriptionTransferCache();

        orderTimeTypeDescriptions.stream().forEach((orderTimeTypeDescription) -> {
            orderTimeTypeDescriptionTransfers.add(orderTimeTypeDescriptionTransferCache.getOrderTimeTypeDescriptionTransfer(orderTimeTypeDescription));
        });

        return orderTimeTypeDescriptionTransfers;
    }

    public void updateOrderTimeTypeDescriptionFromValue(OrderTimeTypeDescriptionValue orderTimeTypeDescriptionValue, BasePK updatedBy) {
        if(orderTimeTypeDescriptionValue.hasBeenModified()) {
            OrderTimeTypeDescription orderTimeTypeDescription = OrderTimeTypeDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    orderTimeTypeDescriptionValue.getPrimaryKey());

            orderTimeTypeDescription.setThruTime(session.START_TIME_LONG);
            orderTimeTypeDescription.store();

            OrderTimeType orderTimeType = orderTimeTypeDescription.getOrderTimeType();
            Language language = orderTimeTypeDescription.getLanguage();
            String description = orderTimeTypeDescriptionValue.getDescription();

            orderTimeTypeDescription = OrderTimeTypeDescriptionFactory.getInstance().create(orderTimeType, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);

            sendEventUsingNames(orderTimeType.getPrimaryKey(), EventTypes.MODIFY.name(), orderTimeTypeDescription.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }

    public void deleteOrderTimeTypeDescription(OrderTimeTypeDescription orderTimeTypeDescription, BasePK deletedBy) {
        orderTimeTypeDescription.setThruTime(session.START_TIME_LONG);

        sendEventUsingNames(orderTimeTypeDescription.getOrderTimeTypePK(), EventTypes.MODIFY.name(), orderTimeTypeDescription.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);

    }

    public void deleteOrderTimeTypeDescriptionsByOrderTimeType(OrderTimeType orderTimeType, BasePK deletedBy) {
        List<OrderTimeTypeDescription> orderTimeTypeDescriptions = getOrderTimeTypeDescriptionsByOrderTimeTypeForUpdate(orderTimeType);

        orderTimeTypeDescriptions.stream().forEach((orderTimeTypeDescription) -> {
            deleteOrderTimeTypeDescription(orderTimeTypeDescription, deletedBy);
        });
    }

    // --------------------------------------------------------------------------------
    //   Order Adjustment Types
    // --------------------------------------------------------------------------------
    
    public OrderAdjustmentType createOrderAdjustmentType(OrderType orderType, String orderAdjustmentTypeName, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        OrderAdjustmentType defaultOrderAdjustmentType = getDefaultOrderAdjustmentType(orderType);
        boolean defaultFound = defaultOrderAdjustmentType != null;

        if(defaultFound && isDefault) {
            OrderAdjustmentTypeDetailValue defaultOrderAdjustmentTypeDetailValue = getDefaultOrderAdjustmentTypeDetailValueForUpdate(orderType);

            defaultOrderAdjustmentTypeDetailValue.setIsDefault(Boolean.FALSE);
            updateOrderAdjustmentTypeFromValue(defaultOrderAdjustmentTypeDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = Boolean.TRUE;
        }

        OrderAdjustmentType orderAdjustmentType = OrderAdjustmentTypeFactory.getInstance().create();
        OrderAdjustmentTypeDetail orderAdjustmentTypeDetail = OrderAdjustmentTypeDetailFactory.getInstance().create(orderAdjustmentType, orderType,
                orderAdjustmentTypeName, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        // Convert to R/W
        orderAdjustmentType = OrderAdjustmentTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                orderAdjustmentType.getPrimaryKey());
        orderAdjustmentType.setActiveDetail(orderAdjustmentTypeDetail);
        orderAdjustmentType.setLastDetail(orderAdjustmentTypeDetail);
        orderAdjustmentType.store();

        sendEventUsingNames(orderAdjustmentType.getPrimaryKey(), EventTypes.CREATE.name(), null, null, createdBy);

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
        return getOrderTransferCaches(userVisit).getOrderAdjustmentTypeTransferCache().getOrderAdjustmentTypeTransfer(orderAdjustmentType);
    }

    public List<OrderAdjustmentTypeTransfer> getOrderAdjustmentTypeTransfers(UserVisit userVisit, OrderType orderType) {
        List<OrderAdjustmentType> orderAdjustmentTypes = getOrderAdjustmentTypes(orderType);
        List<OrderAdjustmentTypeTransfer> orderAdjustmentTypeTransfers = new ArrayList<>(orderAdjustmentTypes.size());
        OrderAdjustmentTypeTransferCache orderAdjustmentTypeTransferCache = getOrderTransferCaches(userVisit).getOrderAdjustmentTypeTransferCache();

        orderAdjustmentTypes.stream().forEach((orderAdjustmentType) -> {
            orderAdjustmentTypeTransfers.add(orderAdjustmentTypeTransferCache.getOrderAdjustmentTypeTransfer(orderAdjustmentType));
        });

        return orderAdjustmentTypeTransfers;
    }

    public OrderAdjustmentTypeChoicesBean getOrderAdjustmentTypeChoices(String defaultOrderAdjustmentTypeChoice, Language language, boolean allowNullChoice,
            OrderType orderType) {
        List<OrderAdjustmentType> orderAdjustmentTypes = getOrderAdjustmentTypes(orderType);
        int size = orderAdjustmentTypes.size();
        List<String> labels = new ArrayList<>(size);
        List<String> values = new ArrayList<>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultOrderAdjustmentTypeChoice == null) {
                defaultValue = "";
            }
        }

        for(OrderAdjustmentType orderAdjustmentType: orderAdjustmentTypes) {
            OrderAdjustmentTypeDetail orderAdjustmentTypeDetail = orderAdjustmentType.getLastDetail();

            String label = getBestOrderAdjustmentTypeDescription(orderAdjustmentType, language);
            String value = orderAdjustmentTypeDetail.getOrderAdjustmentTypeName();

            labels.add(label == null? value: label);
            values.add(value);

            boolean usingDefaultChoice = defaultOrderAdjustmentTypeChoice == null? false: defaultOrderAdjustmentTypeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && orderAdjustmentTypeDetail.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new OrderAdjustmentTypeChoicesBean(labels, values, defaultValue);
    }

    private void updateOrderAdjustmentTypeFromValue(OrderAdjustmentTypeDetailValue orderAdjustmentTypeDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(orderAdjustmentTypeDetailValue.hasBeenModified()) {
            OrderAdjustmentType orderAdjustmentType = OrderAdjustmentTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     orderAdjustmentTypeDetailValue.getOrderAdjustmentTypePK());
            OrderAdjustmentTypeDetail orderAdjustmentTypeDetail = orderAdjustmentType.getActiveDetailForUpdate();

            orderAdjustmentTypeDetail.setThruTime(session.START_TIME_LONG);
            orderAdjustmentTypeDetail.store();

            OrderType orderType = orderAdjustmentTypeDetail.getOrderType(); // Not updated
            OrderTypePK orderTypePK = orderType.getPrimaryKey(); // Not updated
            OrderAdjustmentTypePK orderAdjustmentTypePK = orderAdjustmentTypeDetail.getOrderAdjustmentTypePK(); // Not updated
            String orderAdjustmentTypeName = orderAdjustmentTypeDetailValue.getOrderAdjustmentTypeName();
            Boolean isDefault = orderAdjustmentTypeDetailValue.getIsDefault();
            Integer sortOrder = orderAdjustmentTypeDetailValue.getSortOrder();

            if(checkDefault) {
                OrderAdjustmentType defaultOrderAdjustmentType = getDefaultOrderAdjustmentType(orderType);
                boolean defaultFound = defaultOrderAdjustmentType != null && !defaultOrderAdjustmentType.equals(orderAdjustmentType);

                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    OrderAdjustmentTypeDetailValue defaultOrderAdjustmentTypeDetailValue = getDefaultOrderAdjustmentTypeDetailValueForUpdate(orderType);

                    defaultOrderAdjustmentTypeDetailValue.setIsDefault(Boolean.FALSE);
                    updateOrderAdjustmentTypeFromValue(defaultOrderAdjustmentTypeDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = Boolean.TRUE;
                }
            }

            orderAdjustmentTypeDetail = OrderAdjustmentTypeDetailFactory.getInstance().create(orderAdjustmentTypePK, orderTypePK, orderAdjustmentTypeName, isDefault, sortOrder,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);

            orderAdjustmentType.setActiveDetail(orderAdjustmentTypeDetail);
            orderAdjustmentType.setLastDetail(orderAdjustmentTypeDetail);

            sendEventUsingNames(orderAdjustmentTypePK, EventTypes.MODIFY.name(), null, null, updatedBy);
        }
    }

    public void updateOrderAdjustmentTypeFromValue(OrderAdjustmentTypeDetailValue orderAdjustmentTypeDetailValue, BasePK updatedBy) {
        updateOrderAdjustmentTypeFromValue(orderAdjustmentTypeDetailValue, true, updatedBy);
    }

    public void deleteOrderAdjustmentType(OrderAdjustmentType orderAdjustmentType, BasePK deletedBy) {
        // TODO: deleteOrderAdjustmentsByOrderAdjustmentType(orderAdjustmentType, deletedBy);
        deleteOrderAdjustmentTypeDescriptionsByOrderAdjustmentType(orderAdjustmentType, deletedBy);

        OrderAdjustmentTypeDetail orderAdjustmentTypeDetail = orderAdjustmentType.getLastDetailForUpdate();
        orderAdjustmentTypeDetail.setThruTime(session.START_TIME_LONG);
        orderAdjustmentType.setActiveDetail(null);
        orderAdjustmentType.store();

        // Check for default, and pick one if necessary
        OrderType orderType = orderAdjustmentTypeDetail.getOrderType();
        OrderAdjustmentType defaultOrderAdjustmentType = getDefaultOrderAdjustmentType(orderType);
        if(defaultOrderAdjustmentType == null) {
            List<OrderAdjustmentType> orderAdjustmentTypes = getOrderAdjustmentTypesForUpdate(orderType);

            if(!orderAdjustmentTypes.isEmpty()) {
                Iterator<OrderAdjustmentType> iter = orderAdjustmentTypes.iterator();
                if(iter.hasNext()) {
                    defaultOrderAdjustmentType = iter.next();
                }
                OrderAdjustmentTypeDetailValue orderAdjustmentTypeDetailValue = defaultOrderAdjustmentType.getLastDetailForUpdate().getOrderAdjustmentTypeDetailValue().clone();

                orderAdjustmentTypeDetailValue.setIsDefault(Boolean.TRUE);
                updateOrderAdjustmentTypeFromValue(orderAdjustmentTypeDetailValue, false, deletedBy);
            }
        }

        sendEventUsingNames(orderAdjustmentType.getPrimaryKey(), EventTypes.DELETE.name(), null, null, deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Order Adjustment Type Descriptions
    // --------------------------------------------------------------------------------

    public OrderAdjustmentTypeDescription createOrderAdjustmentTypeDescription(OrderAdjustmentType orderAdjustmentType, Language language, String description, BasePK createdBy) {
        OrderAdjustmentTypeDescription orderAdjustmentTypeDescription = OrderAdjustmentTypeDescriptionFactory.getInstance().create(orderAdjustmentType, language, description,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEventUsingNames(orderAdjustmentType.getPrimaryKey(), EventTypes.MODIFY.name(), orderAdjustmentTypeDescription.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);

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
        OrderAdjustmentTypeDescription orderAdjustmentTypeDescription = getOrderAdjustmentTypeDescription(orderAdjustmentType, language);

        if(orderAdjustmentTypeDescription == null && !language.getIsDefault()) {
            orderAdjustmentTypeDescription = getOrderAdjustmentTypeDescription(orderAdjustmentType, getPartyControl().getDefaultLanguage());
        }

        if(orderAdjustmentTypeDescription == null) {
            description = orderAdjustmentType.getLastDetail().getOrderAdjustmentTypeName();
        } else {
            description = orderAdjustmentTypeDescription.getDescription();
        }

        return description;
    }

    public OrderAdjustmentTypeDescriptionTransfer getOrderAdjustmentTypeDescriptionTransfer(UserVisit userVisit, OrderAdjustmentTypeDescription orderAdjustmentTypeDescription) {
        return getOrderTransferCaches(userVisit).getOrderAdjustmentTypeDescriptionTransferCache().getOrderAdjustmentTypeDescriptionTransfer(orderAdjustmentTypeDescription);
    }

    public List<OrderAdjustmentTypeDescriptionTransfer> getOrderAdjustmentTypeDescriptionTransfersByOrderAdjustmentType(UserVisit userVisit, OrderAdjustmentType orderAdjustmentType) {
        List<OrderAdjustmentTypeDescription> orderAdjustmentTypeDescriptions = getOrderAdjustmentTypeDescriptionsByOrderAdjustmentType(orderAdjustmentType);
        List<OrderAdjustmentTypeDescriptionTransfer> orderAdjustmentTypeDescriptionTransfers = new ArrayList<>(orderAdjustmentTypeDescriptions.size());
        OrderAdjustmentTypeDescriptionTransferCache orderAdjustmentTypeDescriptionTransferCache = getOrderTransferCaches(userVisit).getOrderAdjustmentTypeDescriptionTransferCache();

        orderAdjustmentTypeDescriptions.stream().forEach((orderAdjustmentTypeDescription) -> {
            orderAdjustmentTypeDescriptionTransfers.add(orderAdjustmentTypeDescriptionTransferCache.getOrderAdjustmentTypeDescriptionTransfer(orderAdjustmentTypeDescription));
        });

        return orderAdjustmentTypeDescriptionTransfers;
    }

    public void updateOrderAdjustmentTypeDescriptionFromValue(OrderAdjustmentTypeDescriptionValue orderAdjustmentTypeDescriptionValue, BasePK updatedBy) {
        if(orderAdjustmentTypeDescriptionValue.hasBeenModified()) {
            OrderAdjustmentTypeDescription orderAdjustmentTypeDescription = OrderAdjustmentTypeDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    orderAdjustmentTypeDescriptionValue.getPrimaryKey());

            orderAdjustmentTypeDescription.setThruTime(session.START_TIME_LONG);
            orderAdjustmentTypeDescription.store();

            OrderAdjustmentType orderAdjustmentType = orderAdjustmentTypeDescription.getOrderAdjustmentType();
            Language language = orderAdjustmentTypeDescription.getLanguage();
            String description = orderAdjustmentTypeDescriptionValue.getDescription();

            orderAdjustmentTypeDescription = OrderAdjustmentTypeDescriptionFactory.getInstance().create(orderAdjustmentType, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);

            sendEventUsingNames(orderAdjustmentType.getPrimaryKey(), EventTypes.MODIFY.name(), orderAdjustmentTypeDescription.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }

    public void deleteOrderAdjustmentTypeDescription(OrderAdjustmentTypeDescription orderAdjustmentTypeDescription, BasePK deletedBy) {
        orderAdjustmentTypeDescription.setThruTime(session.START_TIME_LONG);

        sendEventUsingNames(orderAdjustmentTypeDescription.getOrderAdjustmentTypePK(), EventTypes.MODIFY.name(), orderAdjustmentTypeDescription.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);

    }

    public void deleteOrderAdjustmentTypeDescriptionsByOrderAdjustmentType(OrderAdjustmentType orderAdjustmentType, BasePK deletedBy) {
        List<OrderAdjustmentTypeDescription> orderAdjustmentTypeDescriptions = getOrderAdjustmentTypeDescriptionsByOrderAdjustmentTypeForUpdate(orderAdjustmentType);

        orderAdjustmentTypeDescriptions.stream().forEach((orderAdjustmentTypeDescription) -> {
            deleteOrderAdjustmentTypeDescription(orderAdjustmentTypeDescription, deletedBy);
        });
    }
    
    // --------------------------------------------------------------------------------
    //   Order Line Adjustment Types
    // --------------------------------------------------------------------------------

    public OrderLineAdjustmentType createOrderLineAdjustmentType(OrderType orderType, String orderLineAdjustmentTypeName, Boolean isDefault, Integer sortOrder,
            BasePK createdBy) {
        OrderLineAdjustmentType defaultOrderLineAdjustmentType = getDefaultOrderLineAdjustmentType(orderType);
        boolean defaultFound = defaultOrderLineAdjustmentType != null;

        if(defaultFound && isDefault) {
            OrderLineAdjustmentTypeDetailValue defaultOrderLineAdjustmentTypeDetailValue = getDefaultOrderLineAdjustmentTypeDetailValueForUpdate(orderType);

            defaultOrderLineAdjustmentTypeDetailValue.setIsDefault(Boolean.FALSE);
            updateOrderLineAdjustmentTypeFromValue(defaultOrderLineAdjustmentTypeDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = Boolean.TRUE;
        }

        OrderLineAdjustmentType orderLineAdjustmentType = OrderLineAdjustmentTypeFactory.getInstance().create();
        OrderLineAdjustmentTypeDetail orderLineAdjustmentTypeDetail = OrderLineAdjustmentTypeDetailFactory.getInstance().create(orderLineAdjustmentType, 
                orderType, orderLineAdjustmentTypeName, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        // Convert to R/W
        orderLineAdjustmentType = OrderLineAdjustmentTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                orderLineAdjustmentType.getPrimaryKey());
        orderLineAdjustmentType.setActiveDetail(orderLineAdjustmentTypeDetail);
        orderLineAdjustmentType.setLastDetail(orderLineAdjustmentTypeDetail);
        orderLineAdjustmentType.store();

        sendEventUsingNames(orderLineAdjustmentType.getPrimaryKey(), EventTypes.CREATE.name(), null, null, createdBy);

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
        return getOrderTransferCaches(userVisit).getOrderLineAdjustmentTypeTransferCache().getOrderLineAdjustmentTypeTransfer(orderLineAdjustmentType);
    }

    public List<OrderLineAdjustmentTypeTransfer> getOrderLineAdjustmentTypeTransfers(UserVisit userVisit, OrderType orderType) {
        List<OrderLineAdjustmentType> orderLineAdjustmentTypes = getOrderLineAdjustmentTypes(orderType);
        List<OrderLineAdjustmentTypeTransfer> orderLineAdjustmentTypeTransfers = new ArrayList<>(orderLineAdjustmentTypes.size());
        OrderLineAdjustmentTypeTransferCache orderLineAdjustmentTypeTransferCache = getOrderTransferCaches(userVisit).getOrderLineAdjustmentTypeTransferCache();

        orderLineAdjustmentTypes.stream().forEach((orderLineAdjustmentType) -> {
            orderLineAdjustmentTypeTransfers.add(orderLineAdjustmentTypeTransferCache.getOrderLineAdjustmentTypeTransfer(orderLineAdjustmentType));
        });

        return orderLineAdjustmentTypeTransfers;
    }

    public OrderLineAdjustmentTypeChoicesBean getOrderLineAdjustmentTypeChoices(String defaultOrderLineAdjustmentTypeChoice, Language language, boolean allowNullChoice,
            OrderType orderType) {
        List<OrderLineAdjustmentType> orderLineAdjustmentTypes = getOrderLineAdjustmentTypes(orderType);
        int size = orderLineAdjustmentTypes.size();
        List<String> labels = new ArrayList<>(size);
        List<String> values = new ArrayList<>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultOrderLineAdjustmentTypeChoice == null) {
                defaultValue = "";
            }
        }

        for(OrderLineAdjustmentType orderLineAdjustmentType: orderLineAdjustmentTypes) {
            OrderLineAdjustmentTypeDetail orderLineAdjustmentTypeDetail = orderLineAdjustmentType.getLastDetail();

            String label = getBestOrderLineAdjustmentTypeDescription(orderLineAdjustmentType, language);
            String value = orderLineAdjustmentTypeDetail.getOrderLineAdjustmentTypeName();

            labels.add(label == null? value: label);
            values.add(value);

            boolean usingDefaultChoice = defaultOrderLineAdjustmentTypeChoice == null? false: defaultOrderLineAdjustmentTypeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && orderLineAdjustmentTypeDetail.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new OrderLineAdjustmentTypeChoicesBean(labels, values, defaultValue);
    }

    private void updateOrderLineAdjustmentTypeFromValue(OrderLineAdjustmentTypeDetailValue orderLineAdjustmentTypeDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(orderLineAdjustmentTypeDetailValue.hasBeenModified()) {
            OrderLineAdjustmentType orderLineAdjustmentType = OrderLineAdjustmentTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     orderLineAdjustmentTypeDetailValue.getOrderLineAdjustmentTypePK());
            OrderLineAdjustmentTypeDetail orderLineAdjustmentTypeDetail = orderLineAdjustmentType.getActiveDetailForUpdate();

            orderLineAdjustmentTypeDetail.setThruTime(session.START_TIME_LONG);
            orderLineAdjustmentTypeDetail.store();

            OrderType orderType = orderLineAdjustmentTypeDetail.getOrderType(); // Not updated
            OrderTypePK orderTypePK = orderType.getPrimaryKey(); // Not updated
            OrderLineAdjustmentTypePK orderLineAdjustmentTypePK = orderLineAdjustmentTypeDetail.getOrderLineAdjustmentTypePK(); // Not updated
            String orderLineAdjustmentTypeName = orderLineAdjustmentTypeDetailValue.getOrderLineAdjustmentTypeName();
            Boolean isDefault = orderLineAdjustmentTypeDetailValue.getIsDefault();
            Integer sortOrder = orderLineAdjustmentTypeDetailValue.getSortOrder();

            if(checkDefault) {
                OrderLineAdjustmentType defaultOrderLineAdjustmentType = getDefaultOrderLineAdjustmentType(orderType);
                boolean defaultFound = defaultOrderLineAdjustmentType != null && !defaultOrderLineAdjustmentType.equals(orderLineAdjustmentType);

                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    OrderLineAdjustmentTypeDetailValue defaultOrderLineAdjustmentTypeDetailValue = getDefaultOrderLineAdjustmentTypeDetailValueForUpdate(orderType);

                    defaultOrderLineAdjustmentTypeDetailValue.setIsDefault(Boolean.FALSE);
                    updateOrderLineAdjustmentTypeFromValue(defaultOrderLineAdjustmentTypeDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = Boolean.TRUE;
                }
            }

            orderLineAdjustmentTypeDetail = OrderLineAdjustmentTypeDetailFactory.getInstance().create(orderLineAdjustmentTypePK, orderTypePK, orderLineAdjustmentTypeName, isDefault, sortOrder,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);

            orderLineAdjustmentType.setActiveDetail(orderLineAdjustmentTypeDetail);
            orderLineAdjustmentType.setLastDetail(orderLineAdjustmentTypeDetail);

            sendEventUsingNames(orderLineAdjustmentTypePK, EventTypes.MODIFY.name(), null, null, updatedBy);
        }
    }

    public void updateOrderLineAdjustmentTypeFromValue(OrderLineAdjustmentTypeDetailValue orderLineAdjustmentTypeDetailValue, BasePK updatedBy) {
        updateOrderLineAdjustmentTypeFromValue(orderLineAdjustmentTypeDetailValue, true, updatedBy);
    }

    public void deleteOrderLineAdjustmentType(OrderLineAdjustmentType orderLineAdjustmentType, BasePK deletedBy) {
        // TODO: deleteOrderLineAdjustmentsByOrderLineAdjustmentType(orderLineAdjustmentType, deletedBy);
        deleteOrderLineAdjustmentTypeDescriptionsByOrderLineAdjustmentType(orderLineAdjustmentType, deletedBy);

        OrderLineAdjustmentTypeDetail orderLineAdjustmentTypeDetail = orderLineAdjustmentType.getLastDetailForUpdate();
        orderLineAdjustmentTypeDetail.setThruTime(session.START_TIME_LONG);
        orderLineAdjustmentType.setActiveDetail(null);
        orderLineAdjustmentType.store();

        // Check for default, and pick one if necessary
        OrderType orderType = orderLineAdjustmentTypeDetail.getOrderType();
        OrderLineAdjustmentType defaultOrderLineAdjustmentType = getDefaultOrderLineAdjustmentType(orderType);
        if(defaultOrderLineAdjustmentType == null) {
            List<OrderLineAdjustmentType> orderLineAdjustmentTypes = getOrderLineAdjustmentTypesForUpdate(orderType);

            if(!orderLineAdjustmentTypes.isEmpty()) {
                Iterator<OrderLineAdjustmentType> iter = orderLineAdjustmentTypes.iterator();
                if(iter.hasNext()) {
                    defaultOrderLineAdjustmentType = iter.next();
                }
                OrderLineAdjustmentTypeDetailValue orderLineAdjustmentTypeDetailValue = defaultOrderLineAdjustmentType.getLastDetailForUpdate().getOrderLineAdjustmentTypeDetailValue().clone();

                orderLineAdjustmentTypeDetailValue.setIsDefault(Boolean.TRUE);
                updateOrderLineAdjustmentTypeFromValue(orderLineAdjustmentTypeDetailValue, false, deletedBy);
            }
        }

        sendEventUsingNames(orderLineAdjustmentType.getPrimaryKey(), EventTypes.DELETE.name(), null, null, deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Order Line Adjustment Type Descriptions
    // --------------------------------------------------------------------------------

    public OrderLineAdjustmentTypeDescription createOrderLineAdjustmentTypeDescription(OrderLineAdjustmentType orderLineAdjustmentType, Language language, String description, BasePK createdBy) {
        OrderLineAdjustmentTypeDescription orderLineAdjustmentTypeDescription = OrderLineAdjustmentTypeDescriptionFactory.getInstance().create(orderLineAdjustmentType, language, description,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEventUsingNames(orderLineAdjustmentType.getPrimaryKey(), EventTypes.MODIFY.name(), orderLineAdjustmentTypeDescription.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);

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
        OrderLineAdjustmentTypeDescription orderLineAdjustmentTypeDescription = getOrderLineAdjustmentTypeDescription(orderLineAdjustmentType, language);

        if(orderLineAdjustmentTypeDescription == null && !language.getIsDefault()) {
            orderLineAdjustmentTypeDescription = getOrderLineAdjustmentTypeDescription(orderLineAdjustmentType, getPartyControl().getDefaultLanguage());
        }

        if(orderLineAdjustmentTypeDescription == null) {
            description = orderLineAdjustmentType.getLastDetail().getOrderLineAdjustmentTypeName();
        } else {
            description = orderLineAdjustmentTypeDescription.getDescription();
        }

        return description;
    }

    public OrderLineAdjustmentTypeDescriptionTransfer getOrderLineAdjustmentTypeDescriptionTransfer(UserVisit userVisit, OrderLineAdjustmentTypeDescription orderLineAdjustmentTypeDescription) {
        return getOrderTransferCaches(userVisit).getOrderLineAdjustmentTypeDescriptionTransferCache().getOrderLineAdjustmentTypeDescriptionTransfer(orderLineAdjustmentTypeDescription);
    }

    public List<OrderLineAdjustmentTypeDescriptionTransfer> getOrderLineAdjustmentTypeDescriptionTransfersByOrderLineAdjustmentType(UserVisit userVisit, OrderLineAdjustmentType orderLineAdjustmentType) {
        List<OrderLineAdjustmentTypeDescription> orderLineAdjustmentTypeDescriptions = getOrderLineAdjustmentTypeDescriptionsByOrderLineAdjustmentType(orderLineAdjustmentType);
        List<OrderLineAdjustmentTypeDescriptionTransfer> orderLineAdjustmentTypeDescriptionTransfers = new ArrayList<>(orderLineAdjustmentTypeDescriptions.size());
        OrderLineAdjustmentTypeDescriptionTransferCache orderLineAdjustmentTypeDescriptionTransferCache = getOrderTransferCaches(userVisit).getOrderLineAdjustmentTypeDescriptionTransferCache();

        orderLineAdjustmentTypeDescriptions.stream().forEach((orderLineAdjustmentTypeDescription) -> {
            orderLineAdjustmentTypeDescriptionTransfers.add(orderLineAdjustmentTypeDescriptionTransferCache.getOrderLineAdjustmentTypeDescriptionTransfer(orderLineAdjustmentTypeDescription));
        });

        return orderLineAdjustmentTypeDescriptionTransfers;
    }

    public void updateOrderLineAdjustmentTypeDescriptionFromValue(OrderLineAdjustmentTypeDescriptionValue orderLineAdjustmentTypeDescriptionValue, BasePK updatedBy) {
        if(orderLineAdjustmentTypeDescriptionValue.hasBeenModified()) {
            OrderLineAdjustmentTypeDescription orderLineAdjustmentTypeDescription = OrderLineAdjustmentTypeDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    orderLineAdjustmentTypeDescriptionValue.getPrimaryKey());

            orderLineAdjustmentTypeDescription.setThruTime(session.START_TIME_LONG);
            orderLineAdjustmentTypeDescription.store();

            OrderLineAdjustmentType orderLineAdjustmentType = orderLineAdjustmentTypeDescription.getOrderLineAdjustmentType();
            Language language = orderLineAdjustmentTypeDescription.getLanguage();
            String description = orderLineAdjustmentTypeDescriptionValue.getDescription();

            orderLineAdjustmentTypeDescription = OrderLineAdjustmentTypeDescriptionFactory.getInstance().create(orderLineAdjustmentType, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);

            sendEventUsingNames(orderLineAdjustmentType.getPrimaryKey(), EventTypes.MODIFY.name(), orderLineAdjustmentTypeDescription.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }

    public void deleteOrderLineAdjustmentTypeDescription(OrderLineAdjustmentTypeDescription orderLineAdjustmentTypeDescription, BasePK deletedBy) {
        orderLineAdjustmentTypeDescription.setThruTime(session.START_TIME_LONG);

        sendEventUsingNames(orderLineAdjustmentTypeDescription.getOrderLineAdjustmentTypePK(), EventTypes.MODIFY.name(), orderLineAdjustmentTypeDescription.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);

    }

    public void deleteOrderLineAdjustmentTypeDescriptionsByOrderLineAdjustmentType(OrderLineAdjustmentType orderLineAdjustmentType, BasePK deletedBy) {
        List<OrderLineAdjustmentTypeDescription> orderLineAdjustmentTypeDescriptions = getOrderLineAdjustmentTypeDescriptionsByOrderLineAdjustmentTypeForUpdate(orderLineAdjustmentType);

        orderLineAdjustmentTypeDescriptions.stream().forEach((orderLineAdjustmentTypeDescription) -> {
            deleteOrderLineAdjustmentTypeDescription(orderLineAdjustmentTypeDescription, deletedBy);
        });
    }

    // --------------------------------------------------------------------------------
    //   Order Alias Types
    // --------------------------------------------------------------------------------

    public OrderAliasType createOrderAliasType(OrderType orderType, String orderAliasTypeName, String validationPattern, Boolean isDefault, Integer sortOrder,
            BasePK createdBy) {
        OrderAliasType defaultOrderAliasType = getDefaultOrderAliasType(orderType);
        boolean defaultFound = defaultOrderAliasType != null;

        if(defaultFound && isDefault) {
            OrderAliasTypeDetailValue defaultOrderAliasTypeDetailValue = getDefaultOrderAliasTypeDetailValueForUpdate(orderType);

            defaultOrderAliasTypeDetailValue.setIsDefault(Boolean.FALSE);
            updateOrderAliasTypeFromValue(defaultOrderAliasTypeDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = Boolean.TRUE;
        }

        OrderAliasType orderAliasType = OrderAliasTypeFactory.getInstance().create();
        OrderAliasTypeDetail orderAliasTypeDetail = OrderAliasTypeDetailFactory.getInstance().create(orderAliasType, orderType, orderAliasTypeName,
                validationPattern, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        // Convert to R/W
        orderAliasType = OrderAliasTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, orderAliasType.getPrimaryKey());
        orderAliasType.setActiveDetail(orderAliasTypeDetail);
        orderAliasType.setLastDetail(orderAliasTypeDetail);
        orderAliasType.store();

        sendEventUsingNames(orderAliasType.getPrimaryKey(), EventTypes.CREATE.name(), null, null, createdBy);

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
        return getOrderTransferCaches(userVisit).getOrderAliasTypeTransferCache().getOrderAliasTypeTransfer(orderAliasType);
    }

    public List<OrderAliasTypeTransfer> getOrderAliasTypeTransfers(UserVisit userVisit, OrderType orderType) {
        List<OrderAliasType> orderAliasTypes = getOrderAliasTypes(orderType);
        List<OrderAliasTypeTransfer> orderAliasTypeTransfers = new ArrayList<>(orderAliasTypes.size());
        OrderAliasTypeTransferCache orderAliasTypeTransferCache = getOrderTransferCaches(userVisit).getOrderAliasTypeTransferCache();

        orderAliasTypes.stream().forEach((orderAliasType) -> {
            orderAliasTypeTransfers.add(orderAliasTypeTransferCache.getOrderAliasTypeTransfer(orderAliasType));
        });

        return orderAliasTypeTransfers;
    }

    public OrderAliasTypeChoicesBean getOrderAliasTypeChoices(String defaultOrderAliasTypeChoice, Language language,
            boolean allowNullChoice, OrderType orderType) {
        List<OrderAliasType> orderAliasTypes = getOrderAliasTypes(orderType);
        int size = orderAliasTypes.size();
        List<String> labels = new ArrayList<>(size);
        List<String> values = new ArrayList<>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultOrderAliasTypeChoice == null) {
                defaultValue = "";
            }
        }

        for(OrderAliasType orderAliasType: orderAliasTypes) {
            OrderAliasTypeDetail orderAliasTypeDetail = orderAliasType.getLastDetail();

            String label = getBestOrderAliasTypeDescription(orderAliasType, language);
            String value = orderAliasTypeDetail.getOrderAliasTypeName();

            labels.add(label == null? value: label);
            values.add(value);

            boolean usingDefaultChoice = defaultOrderAliasTypeChoice == null? false: defaultOrderAliasTypeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && orderAliasTypeDetail.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new OrderAliasTypeChoicesBean(labels, values, defaultValue);
    }

    private void updateOrderAliasTypeFromValue(OrderAliasTypeDetailValue orderAliasTypeDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(orderAliasTypeDetailValue.hasBeenModified()) {
            OrderAliasType orderAliasType = OrderAliasTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    orderAliasTypeDetailValue.getOrderAliasTypePK());
            OrderAliasTypeDetail orderAliasTypeDetail = orderAliasType.getActiveDetailForUpdate();

            orderAliasTypeDetail.setThruTime(session.START_TIME_LONG);
            orderAliasTypeDetail.store();

            OrderAliasTypePK orderAliasTypePK = orderAliasTypeDetail.getOrderAliasTypePK();
            OrderType orderType = orderAliasTypeDetail.getOrderType();
            OrderTypePK orderTypePK = orderType.getPrimaryKey();
            String orderAliasTypeName = orderAliasTypeDetailValue.getOrderAliasTypeName();
            String validationPattern = orderAliasTypeDetailValue.getValidationPattern();
            Boolean isDefault = orderAliasTypeDetailValue.getIsDefault();
            Integer sortOrder = orderAliasTypeDetailValue.getSortOrder();

            if(checkDefault) {
                OrderAliasType defaultOrderAliasType = getDefaultOrderAliasType(orderType);
                boolean defaultFound = defaultOrderAliasType != null && !defaultOrderAliasType.equals(orderAliasType);

                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    OrderAliasTypeDetailValue defaultOrderAliasTypeDetailValue = getDefaultOrderAliasTypeDetailValueForUpdate(orderType);

                    defaultOrderAliasTypeDetailValue.setIsDefault(Boolean.FALSE);
                    updateOrderAliasTypeFromValue(defaultOrderAliasTypeDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = Boolean.TRUE;
                }
            }

            orderAliasTypeDetail = OrderAliasTypeDetailFactory.getInstance().create(orderAliasTypePK, orderTypePK, orderAliasTypeName,
                    validationPattern, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);

            orderAliasType.setActiveDetail(orderAliasTypeDetail);
            orderAliasType.setLastDetail(orderAliasTypeDetail);

            sendEventUsingNames(orderAliasTypePK, EventTypes.MODIFY.name(), null, null, updatedBy);
        }
    }

    public void updateOrderAliasTypeFromValue(OrderAliasTypeDetailValue orderAliasTypeDetailValue, BasePK updatedBy) {
        updateOrderAliasTypeFromValue(orderAliasTypeDetailValue, true, updatedBy);
    }

    public void deleteOrderAliasType(OrderAliasType orderAliasType, BasePK deletedBy) {
        deleteOrderAliasesByOrderAliasType(orderAliasType, deletedBy);
        deleteOrderAliasTypeDescriptionsByOrderAliasType(orderAliasType, deletedBy);

        OrderAliasTypeDetail orderAliasTypeDetail = orderAliasType.getLastDetailForUpdate();
        orderAliasTypeDetail.setThruTime(session.START_TIME_LONG);
        orderAliasType.setActiveDetail(null);
        orderAliasType.store();

        // Check for default, and pick one if necessary
        OrderType orderType = orderAliasTypeDetail.getOrderType();
        OrderAliasType defaultOrderAliasType = getDefaultOrderAliasType(orderType);
        if(defaultOrderAliasType == null) {
            List<OrderAliasType> orderAliasTypes = getOrderAliasTypesForUpdate(orderType);

            if(!orderAliasTypes.isEmpty()) {
                Iterator<OrderAliasType> iter = orderAliasTypes.iterator();
                if(iter.hasNext()) {
                    defaultOrderAliasType = iter.next();
                }
                OrderAliasTypeDetailValue orderAliasTypeDetailValue = defaultOrderAliasType.getLastDetailForUpdate().getOrderAliasTypeDetailValue().clone();

                orderAliasTypeDetailValue.setIsDefault(Boolean.TRUE);
                updateOrderAliasTypeFromValue(orderAliasTypeDetailValue, false, deletedBy);
            }
        }

        sendEventUsingNames(orderAliasType.getPrimaryKey(), EventTypes.DELETE.name(), null, null, deletedBy);
    }

    public void deleteOrderAliasTypes(List<OrderAliasType> orderAliasTypes, BasePK deletedBy) {
        orderAliasTypes.stream().forEach((orderAliasType) -> {
            deleteOrderAliasType(orderAliasType, deletedBy);
        });
    }

    public void deleteOrderAliasTypesByOrderType(OrderType orderType, BasePK deletedBy) {
        deleteOrderAliasTypes(getOrderAliasTypesForUpdate(orderType), deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Order Alias Type Descriptions
    // --------------------------------------------------------------------------------

    public OrderAliasTypeDescription createOrderAliasTypeDescription(OrderAliasType orderAliasType, Language language, String description, BasePK createdBy) {
        OrderAliasTypeDescription orderAliasTypeDescription = OrderAliasTypeDescriptionFactory.getInstance().create(orderAliasType, language,
                description, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEventUsingNames(orderAliasType.getPrimaryKey(), EventTypes.MODIFY.name(), orderAliasTypeDescription.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);

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
        OrderAliasTypeDescription orderAliasTypeDescription = getOrderAliasTypeDescription(orderAliasType, language);

        if(orderAliasTypeDescription == null && !language.getIsDefault()) {
            orderAliasTypeDescription = getOrderAliasTypeDescription(orderAliasType, getPartyControl().getDefaultLanguage());
        }

        if(orderAliasTypeDescription == null) {
            description = orderAliasType.getLastDetail().getOrderAliasTypeName();
        } else {
            description = orderAliasTypeDescription.getDescription();
        }

        return description;
    }

    public OrderAliasTypeDescriptionTransfer getOrderAliasTypeDescriptionTransfer(UserVisit userVisit, OrderAliasTypeDescription orderAliasTypeDescription) {
        return getOrderTransferCaches(userVisit).getOrderAliasTypeDescriptionTransferCache().getOrderAliasTypeDescriptionTransfer(orderAliasTypeDescription);
    }

    public List<OrderAliasTypeDescriptionTransfer> getOrderAliasTypeDescriptionTransfersByOrderAliasType(UserVisit userVisit, OrderAliasType orderAliasType) {
        List<OrderAliasTypeDescription> orderAliasTypeDescriptions = getOrderAliasTypeDescriptionsByOrderAliasType(orderAliasType);
        List<OrderAliasTypeDescriptionTransfer> orderAliasTypeDescriptionTransfers = new ArrayList<>(orderAliasTypeDescriptions.size());
        OrderAliasTypeDescriptionTransferCache orderAliasTypeDescriptionTransferCache = getOrderTransferCaches(userVisit).getOrderAliasTypeDescriptionTransferCache();

        orderAliasTypeDescriptions.stream().forEach((orderAliasTypeDescription) -> {
            orderAliasTypeDescriptionTransfers.add(orderAliasTypeDescriptionTransferCache.getOrderAliasTypeDescriptionTransfer(orderAliasTypeDescription));
        });

        return orderAliasTypeDescriptionTransfers;
    }

    public void updateOrderAliasTypeDescriptionFromValue(OrderAliasTypeDescriptionValue orderAliasTypeDescriptionValue, BasePK updatedBy) {
        if(orderAliasTypeDescriptionValue.hasBeenModified()) {
            OrderAliasTypeDescription orderAliasTypeDescription = OrderAliasTypeDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     orderAliasTypeDescriptionValue.getPrimaryKey());

            orderAliasTypeDescription.setThruTime(session.START_TIME_LONG);
            orderAliasTypeDescription.store();

            OrderAliasType orderAliasType = orderAliasTypeDescription.getOrderAliasType();
            Language language = orderAliasTypeDescription.getLanguage();
            String description = orderAliasTypeDescriptionValue.getDescription();

            orderAliasTypeDescription = OrderAliasTypeDescriptionFactory.getInstance().create(orderAliasType, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);

            sendEventUsingNames(orderAliasType.getPrimaryKey(), EventTypes.MODIFY.name(), orderAliasTypeDescription.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }

    public void deleteOrderAliasTypeDescription(OrderAliasTypeDescription orderAliasTypeDescription, BasePK deletedBy) {
        orderAliasTypeDescription.setThruTime(session.START_TIME_LONG);

        sendEventUsingNames(orderAliasTypeDescription.getOrderAliasTypePK(), EventTypes.MODIFY.name(), orderAliasTypeDescription.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);

    }

    public void deleteOrderAliasTypeDescriptionsByOrderAliasType(OrderAliasType orderAliasType, BasePK deletedBy) {
        List<OrderAliasTypeDescription> orderAliasTypeDescriptions = getOrderAliasTypeDescriptionsByOrderAliasTypeForUpdate(orderAliasType);

        orderAliasTypeDescriptions.stream().forEach((orderAliasTypeDescription) -> {
            deleteOrderAliasTypeDescription(orderAliasTypeDescription, deletedBy);
        });
    }

    // --------------------------------------------------------------------------------
    //   Order Aliases
    // --------------------------------------------------------------------------------

    public OrderAlias createOrderAlias(Order order, OrderAliasType orderAliasType, String alias, BasePK createdBy) {
        OrderAlias orderAlias = OrderAliasFactory.getInstance().create(order, orderAliasType, alias, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEventUsingNames(order.getPrimaryKey(), EventTypes.MODIFY.name(), orderAlias.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);

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
        return getOrderTransferCaches(userVisit).getOrderAliasTransferCache().getOrderAliasTransfer(orderAlias);
    }

    public List<OrderAliasTransfer> getOrderAliasTransfersByOrder(UserVisit userVisit, Order order) {
        List<OrderAlias> orderaliases = getOrderAliasesByOrder(order);
        List<OrderAliasTransfer> orderAliasTransfers = new ArrayList<>(orderaliases.size());
        OrderAliasTransferCache orderAliasTransferCache = getOrderTransferCaches(userVisit).getOrderAliasTransferCache();

        orderaliases.stream().forEach((orderAlias) -> {
            orderAliasTransfers.add(orderAliasTransferCache.getOrderAliasTransfer(orderAlias));
        });

        return orderAliasTransfers;
    }

    public void updateOrderAliasFromValue(OrderAliasValue orderAliasValue, BasePK updatedBy) {
        if(orderAliasValue.hasBeenModified()) {
            OrderAlias orderAlias = OrderAliasFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, orderAliasValue.getPrimaryKey());

            orderAlias.setThruTime(session.START_TIME_LONG);
            orderAlias.store();

            OrderPK orderPK = orderAlias.getOrderPK();
            OrderAliasTypePK orderAliasTypePK = orderAlias.getOrderAliasTypePK();
            String alias  = orderAliasValue.getAlias();

            orderAlias = OrderAliasFactory.getInstance().create(orderPK, orderAliasTypePK, alias, session.START_TIME_LONG, Session.MAX_TIME_LONG);

            sendEventUsingNames(orderPK, EventTypes.MODIFY.name(), orderAlias.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }

    public void deleteOrderAlias(OrderAlias orderAlias, BasePK deletedBy) {
        orderAlias.setThruTime(session.START_TIME_LONG);

        sendEventUsingNames(orderAlias.getOrderPK(), EventTypes.MODIFY.name(), orderAlias.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);

    }

    public void deleteOrderAliasesByOrderAliasType(OrderAliasType orderAliasType, BasePK deletedBy) {
        List<OrderAlias> orderaliases = getOrderAliasesByOrderAliasTypeForUpdate(orderAliasType);

        orderaliases.stream().forEach((orderAlias) -> {
            deleteOrderAlias(orderAlias, deletedBy);
        });
    }

    public void deleteOrderAliasesByOrder(Order order, BasePK deletedBy) {
        List<OrderAlias> orderaliases = getOrderAliasesByOrderForUpdate(order);

        orderaliases.stream().forEach((orderAlias) -> {
            deleteOrderAlias(orderAlias, deletedBy);
        });
    }

    // --------------------------------------------------------------------------------
    //   Order Priorities
    // --------------------------------------------------------------------------------

    public OrderPriority createOrderPriority(OrderType orderType, String orderPriorityName, Integer priority, Boolean isDefault, Integer sortOrder,
            BasePK createdBy) {
        OrderPriority defaultOrderPriority = getDefaultOrderPriority(orderType);
        boolean defaultFound = defaultOrderPriority != null;

        if(defaultFound && isDefault) {
            OrderPriorityDetailValue defaultOrderPriorityDetailValue = getDefaultOrderPriorityDetailValueForUpdate(orderType);

            defaultOrderPriorityDetailValue.setIsDefault(Boolean.FALSE);
            updateOrderPriorityFromValue(defaultOrderPriorityDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = Boolean.TRUE;
        }

        OrderPriority orderPriority = OrderPriorityFactory.getInstance().create();
        OrderPriorityDetail orderPriorityDetail = OrderPriorityDetailFactory.getInstance().create(orderPriority, orderType, orderPriorityName, priority,
                isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        // Convert to R/W
        orderPriority = OrderPriorityFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                orderPriority.getPrimaryKey());
        orderPriority.setActiveDetail(orderPriorityDetail);
        orderPriority.setLastDetail(orderPriorityDetail);
        orderPriority.store();

        sendEventUsingNames(orderPriority.getPrimaryKey(), EventTypes.CREATE.name(), null, null, createdBy);

        return orderPriority;
    }

    private static final Map<EntityPermission, String> getOrderPriorityByNameQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM orderpriorities, orderprioritydetails " +
                "WHERE ordpr_activedetailid = ordprdt_orderprioritydetailid " +
                "AND ordprdt_ordtyp_ordertypeid = ? AND ordprdt_orderpriorityname = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM orderpriorities, orderprioritydetails " +
                "WHERE ordpr_activedetailid = ordprdt_orderprioritydetailid " +
                "AND ordprdt_ordtyp_ordertypeid = ? AND ordprdt_orderpriorityname = ? " +
                "FOR UPDATE");
        getOrderPriorityByNameQueries = Collections.unmodifiableMap(queryMap);
    }

    private OrderPriority getOrderPriorityByName(OrderType orderType, String orderPriorityName, EntityPermission entityPermission) {
        return OrderPriorityFactory.getInstance().getEntityFromQuery(entityPermission, getOrderPriorityByNameQueries,
                orderType, orderPriorityName);
    }

    public OrderPriority getOrderPriorityByName(OrderType orderType, String orderPriorityName) {
        return getOrderPriorityByName(orderType, orderPriorityName, EntityPermission.READ_ONLY);
    }

    public OrderPriority getOrderPriorityByNameForUpdate(OrderType orderType, String orderPriorityName) {
        return getOrderPriorityByName(orderType, orderPriorityName, EntityPermission.READ_WRITE);
    }

    public OrderPriorityDetailValue getOrderPriorityDetailValueForUpdate(OrderPriority orderPriority) {
        return orderPriority == null? null: orderPriority.getLastDetailForUpdate().getOrderPriorityDetailValue().clone();
    }

    public OrderPriorityDetailValue getOrderPriorityDetailValueByNameForUpdate(OrderType orderType, String orderPriorityName) {
        return getOrderPriorityDetailValueForUpdate(getOrderPriorityByNameForUpdate(orderType, orderPriorityName));
    }

    private static final Map<EntityPermission, String> getDefaultOrderPriorityQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM orderpriorities, orderprioritydetails " +
                "WHERE ordpr_activedetailid = ordprdt_orderprioritydetailid " +
                "AND ordprdt_ordtyp_ordertypeid = ? AND ordprdt_isdefault = 1");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM orderpriorities, orderprioritydetails " +
                "WHERE ordpr_activedetailid = ordprdt_orderprioritydetailid " +
                "AND ordprdt_ordtyp_ordertypeid = ? AND ordprdt_isdefault = 1 " +
                "FOR UPDATE");
        getDefaultOrderPriorityQueries = Collections.unmodifiableMap(queryMap);
    }

    private OrderPriority getDefaultOrderPriority(OrderType orderType, EntityPermission entityPermission) {
        return OrderPriorityFactory.getInstance().getEntityFromQuery(entityPermission, getDefaultOrderPriorityQueries,
                orderType);
    }

    public OrderPriority getDefaultOrderPriority(OrderType orderType) {
        return getDefaultOrderPriority(orderType, EntityPermission.READ_ONLY);
    }

    public OrderPriority getDefaultOrderPriorityForUpdate(OrderType orderType) {
        return getDefaultOrderPriority(orderType, EntityPermission.READ_WRITE);
    }

    public OrderPriorityDetailValue getDefaultOrderPriorityDetailValueForUpdate(OrderType orderType) {
        return getDefaultOrderPriorityForUpdate(orderType).getLastDetailForUpdate().getOrderPriorityDetailValue().clone();
    }

    private static final Map<EntityPermission, String> getOrderPrioritiesQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM orderpriorities, orderprioritydetails " +
                "WHERE ordpr_activedetailid = ordprdt_orderprioritydetailid " +
                "AND ordprdt_ordtyp_ordertypeid = ? " +
                "ORDER BY ordprdt_sortorder, ordprdt_orderpriorityname");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM orderpriorities, orderprioritydetails " +
                "WHERE ordpr_activedetailid = ordprdt_orderprioritydetailid " +
                "AND ordprdt_ordtyp_ordertypeid = ? " +
                "FOR UPDATE");
        getOrderPrioritiesQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<OrderPriority> getOrderPriorities(OrderType orderType, EntityPermission entityPermission) {
        return OrderPriorityFactory.getInstance().getEntitiesFromQuery(entityPermission, getOrderPrioritiesQueries,
                orderType);
    }

    public List<OrderPriority> getOrderPriorities(OrderType orderType) {
        return getOrderPriorities(orderType, EntityPermission.READ_ONLY);
    }

    public List<OrderPriority> getOrderPrioritiesForUpdate(OrderType orderType) {
        return getOrderPriorities(orderType, EntityPermission.READ_WRITE);
    }

    public OrderPriorityTransfer getOrderPriorityTransfer(UserVisit userVisit, OrderPriority orderPriority) {
        return getOrderTransferCaches(userVisit).getOrderPriorityTransferCache().getOrderPriorityTransfer(orderPriority);
    }

    public List<OrderPriorityTransfer> getOrderPriorityTransfers(UserVisit userVisit, OrderType orderType) {
        List<OrderPriority> orderPriorities = getOrderPriorities(orderType);
        List<OrderPriorityTransfer> orderPriorityTransfers = new ArrayList<>(orderPriorities.size());
        OrderPriorityTransferCache orderPriorityTransferCache = getOrderTransferCaches(userVisit).getOrderPriorityTransferCache();

        orderPriorities.stream().forEach((orderPriority) -> {
            orderPriorityTransfers.add(orderPriorityTransferCache.getOrderPriorityTransfer(orderPriority));
        });

        return orderPriorityTransfers;
    }

    public OrderPriorityChoicesBean getOrderPriorityChoices(String defaultOrderPriorityChoice, Language language, boolean allowNullChoice,
            OrderType orderType) {
        List<OrderPriority> orderPriorities = getOrderPriorities(orderType);
        int size = orderPriorities.size();
        List<String> labels = new ArrayList<>(size);
        List<String> values = new ArrayList<>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultOrderPriorityChoice == null) {
                defaultValue = "";
            }
        }

        for(OrderPriority orderPriority: orderPriorities) {
            OrderPriorityDetail orderPriorityDetail = orderPriority.getLastDetail();

            String label = getBestOrderPriorityDescription(orderPriority, language);
            String value = orderPriorityDetail.getOrderPriorityName();

            labels.add(label == null? value: label);
            values.add(value);

            boolean usingDefaultChoice = defaultOrderPriorityChoice == null? false: defaultOrderPriorityChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && orderPriorityDetail.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new OrderPriorityChoicesBean(labels, values, defaultValue);
    }

    private void updateOrderPriorityFromValue(OrderPriorityDetailValue orderPriorityDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(orderPriorityDetailValue.hasBeenModified()) {
            OrderPriority orderPriority = OrderPriorityFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     orderPriorityDetailValue.getOrderPriorityPK());
            OrderPriorityDetail orderPriorityDetail = orderPriority.getActiveDetailForUpdate();

            orderPriorityDetail.setThruTime(session.START_TIME_LONG);
            orderPriorityDetail.store();

            OrderType orderType = orderPriorityDetail.getOrderType(); // Not updated
            OrderTypePK orderTypePK = orderType.getPrimaryKey(); // Not updated
            OrderPriorityPK orderPriorityPK = orderPriorityDetail.getOrderPriorityPK(); // Not updated
            String orderPriorityName = orderPriorityDetailValue.getOrderPriorityName();
            Integer priority = orderPriorityDetailValue.getPriority();
            Boolean isDefault = orderPriorityDetailValue.getIsDefault();
            Integer sortOrder = orderPriorityDetailValue.getSortOrder();

            if(checkDefault) {
                OrderPriority defaultOrderPriority = getDefaultOrderPriority(orderType);
                boolean defaultFound = defaultOrderPriority != null && !defaultOrderPriority.equals(orderPriority);

                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    OrderPriorityDetailValue defaultOrderPriorityDetailValue = getDefaultOrderPriorityDetailValueForUpdate(orderType);

                    defaultOrderPriorityDetailValue.setIsDefault(Boolean.FALSE);
                    updateOrderPriorityFromValue(defaultOrderPriorityDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = Boolean.TRUE;
                }
            }

            orderPriorityDetail = OrderPriorityDetailFactory.getInstance().create(orderPriorityPK, orderTypePK, orderPriorityName, priority, isDefault,
                    sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);

            orderPriority.setActiveDetail(orderPriorityDetail);
            orderPriority.setLastDetail(orderPriorityDetail);

            sendEventUsingNames(orderPriorityPK, EventTypes.MODIFY.name(), null, null, updatedBy);
        }
    }

    public void updateOrderPriorityFromValue(OrderPriorityDetailValue orderPriorityDetailValue, BasePK updatedBy) {
        updateOrderPriorityFromValue(orderPriorityDetailValue, true, updatedBy);
    }

    public void deleteOrderPriority(OrderPriority orderPriority, BasePK deletedBy) {
        deleteOrderPriorityDescriptionsByOrderPriority(orderPriority, deletedBy);

        OrderPriorityDetail orderPriorityDetail = orderPriority.getLastDetailForUpdate();
        orderPriorityDetail.setThruTime(session.START_TIME_LONG);
        orderPriority.setActiveDetail(null);
        orderPriority.store();

        // Check for default, and pick one if necessary
        OrderType orderType = orderPriorityDetail.getOrderType();
        OrderPriority defaultOrderPriority = getDefaultOrderPriority(orderType);
        if(defaultOrderPriority == null) {
            List<OrderPriority> orderPriorities = getOrderPrioritiesForUpdate(orderType);

            if(!orderPriorities.isEmpty()) {
                Iterator<OrderPriority> iter = orderPriorities.iterator();
                if(iter.hasNext()) {
                    defaultOrderPriority = iter.next();
                }
                OrderPriorityDetailValue orderPriorityDetailValue = defaultOrderPriority.getLastDetailForUpdate().getOrderPriorityDetailValue().clone();

                orderPriorityDetailValue.setIsDefault(Boolean.TRUE);
                updateOrderPriorityFromValue(orderPriorityDetailValue, false, deletedBy);
            }
        }

        sendEventUsingNames(orderPriority.getPrimaryKey(), EventTypes.DELETE.name(), null, null, deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Order Priority Descriptions
    // --------------------------------------------------------------------------------

    public OrderPriorityDescription createOrderPriorityDescription(OrderPriority orderPriority, Language language, String description, BasePK createdBy) {
        OrderPriorityDescription orderPriorityDescription = OrderPriorityDescriptionFactory.getInstance().create(orderPriority, language, description,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEventUsingNames(orderPriority.getPrimaryKey(), EventTypes.MODIFY.name(), orderPriorityDescription.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);

        return orderPriorityDescription;
    }

    private static final Map<EntityPermission, String> getOrderPriorityDescriptionQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM orderprioritydescriptions " +
                "WHERE ordprd_ordpr_orderpriorityid = ? AND ordprd_lang_languageid = ? AND ordprd_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM orderprioritydescriptions " +
                "WHERE ordprd_ordpr_orderpriorityid = ? AND ordprd_lang_languageid = ? AND ordprd_thrutime = ? " +
                "FOR UPDATE");
        getOrderPriorityDescriptionQueries = Collections.unmodifiableMap(queryMap);
    }

    private OrderPriorityDescription getOrderPriorityDescription(OrderPriority orderPriority, Language language, EntityPermission entityPermission) {
        return OrderPriorityDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, getOrderPriorityDescriptionQueries,
                orderPriority, language, Session.MAX_TIME);
    }

    public OrderPriorityDescription getOrderPriorityDescription(OrderPriority orderPriority, Language language) {
        return getOrderPriorityDescription(orderPriority, language, EntityPermission.READ_ONLY);
    }

    public OrderPriorityDescription getOrderPriorityDescriptionForUpdate(OrderPriority orderPriority, Language language) {
        return getOrderPriorityDescription(orderPriority, language, EntityPermission.READ_WRITE);
    }

    public OrderPriorityDescriptionValue getOrderPriorityDescriptionValue(OrderPriorityDescription orderPriorityDescription) {
        return orderPriorityDescription == null? null: orderPriorityDescription.getOrderPriorityDescriptionValue().clone();
    }

    public OrderPriorityDescriptionValue getOrderPriorityDescriptionValueForUpdate(OrderPriority orderPriority, Language language) {
        return getOrderPriorityDescriptionValue(getOrderPriorityDescriptionForUpdate(orderPriority, language));
    }

    private static final Map<EntityPermission, String> getOrderPriorityDescriptionsByOrderPriorityQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM orderprioritydescriptions, languages " +
                "WHERE ordprd_ordpr_orderpriorityid = ? AND ordprd_thrutime = ? AND ordprd_lang_languageid = lang_languageid " +
                "ORDER BY lang_sortorder, lang_languageisoname");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM orderprioritydescriptions " +
                "WHERE ordprd_ordpr_orderpriorityid = ? AND ordprd_thrutime = ? " +
                "FOR UPDATE");
        getOrderPriorityDescriptionsByOrderPriorityQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<OrderPriorityDescription> getOrderPriorityDescriptionsByOrderPriority(OrderPriority orderPriority, EntityPermission entityPermission) {
        return OrderPriorityDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, getOrderPriorityDescriptionsByOrderPriorityQueries,
                orderPriority, Session.MAX_TIME);
    }

    public List<OrderPriorityDescription> getOrderPriorityDescriptionsByOrderPriority(OrderPriority orderPriority) {
        return getOrderPriorityDescriptionsByOrderPriority(orderPriority, EntityPermission.READ_ONLY);
    }

    public List<OrderPriorityDescription> getOrderPriorityDescriptionsByOrderPriorityForUpdate(OrderPriority orderPriority) {
        return getOrderPriorityDescriptionsByOrderPriority(orderPriority, EntityPermission.READ_WRITE);
    }

    public String getBestOrderPriorityDescription(OrderPriority orderPriority, Language language) {
        String description;
        OrderPriorityDescription orderPriorityDescription = getOrderPriorityDescription(orderPriority, language);

        if(orderPriorityDescription == null && !language.getIsDefault()) {
            orderPriorityDescription = getOrderPriorityDescription(orderPriority, getPartyControl().getDefaultLanguage());
        }

        if(orderPriorityDescription == null) {
            description = orderPriority.getLastDetail().getOrderPriorityName();
        } else {
            description = orderPriorityDescription.getDescription();
        }

        return description;
    }

    public OrderPriorityDescriptionTransfer getOrderPriorityDescriptionTransfer(UserVisit userVisit, OrderPriorityDescription orderPriorityDescription) {
        return getOrderTransferCaches(userVisit).getOrderPriorityDescriptionTransferCache().getOrderPriorityDescriptionTransfer(orderPriorityDescription);
    }

    public List<OrderPriorityDescriptionTransfer> getOrderPriorityDescriptionTransfersByOrderPriority(UserVisit userVisit, OrderPriority orderPriority) {
        List<OrderPriorityDescription> orderPriorityDescriptions = getOrderPriorityDescriptionsByOrderPriority(orderPriority);
        List<OrderPriorityDescriptionTransfer> orderPriorityDescriptionTransfers = new ArrayList<>(orderPriorityDescriptions.size());
        OrderPriorityDescriptionTransferCache orderPriorityDescriptionTransferCache = getOrderTransferCaches(userVisit).getOrderPriorityDescriptionTransferCache();

        orderPriorityDescriptions.stream().forEach((orderPriorityDescription) -> {
            orderPriorityDescriptionTransfers.add(orderPriorityDescriptionTransferCache.getOrderPriorityDescriptionTransfer(orderPriorityDescription));
        });

        return orderPriorityDescriptionTransfers;
    }

    public void updateOrderPriorityDescriptionFromValue(OrderPriorityDescriptionValue orderPriorityDescriptionValue, BasePK updatedBy) {
        if(orderPriorityDescriptionValue.hasBeenModified()) {
            OrderPriorityDescription orderPriorityDescription = OrderPriorityDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    orderPriorityDescriptionValue.getPrimaryKey());

            orderPriorityDescription.setThruTime(session.START_TIME_LONG);
            orderPriorityDescription.store();

            OrderPriority orderPriority = orderPriorityDescription.getOrderPriority();
            Language language = orderPriorityDescription.getLanguage();
            String description = orderPriorityDescriptionValue.getDescription();

            orderPriorityDescription = OrderPriorityDescriptionFactory.getInstance().create(orderPriority, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);

            sendEventUsingNames(orderPriority.getPrimaryKey(), EventTypes.MODIFY.name(), orderPriorityDescription.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }

    public void deleteOrderPriorityDescription(OrderPriorityDescription orderPriorityDescription, BasePK deletedBy) {
        orderPriorityDescription.setThruTime(session.START_TIME_LONG);

        sendEventUsingNames(orderPriorityDescription.getOrderPriorityPK(), EventTypes.MODIFY.name(), orderPriorityDescription.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);

    }

    public void deleteOrderPriorityDescriptionsByOrderPriority(OrderPriority orderPriority, BasePK deletedBy) {
        List<OrderPriorityDescription> orderPriorityDescriptions = getOrderPriorityDescriptionsByOrderPriorityForUpdate(orderPriority);

        orderPriorityDescriptions.stream().forEach((orderPriorityDescription) -> {
            deleteOrderPriorityDescription(orderPriorityDescription, deletedBy);
        });
    }

    // --------------------------------------------------------------------------------
    //    Order Batches
    // --------------------------------------------------------------------------------

    public OrderBatch createOrderBatch(Batch batch, Currency currency, Long count, Long amount, BasePK createdBy) {
        OrderBatch orderBatch = OrderBatchFactory.getInstance().create(batch, currency, count, amount, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEventUsingNames(batch.getPrimaryKey(), EventTypes.MODIFY.name(), orderBatch.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);

        return orderBatch;
    }

    private static final Map<EntityPermission, String> getOrderBatchQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM orderbatches " +
                "WHERE ordbtch_btch_batchid = ? AND ordbtch_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM orderbatches " +
                "WHERE ordbtch_btch_batchid = ? AND ordbtch_thrutime = ? " +
                "FOR UPDATE");
        getOrderBatchQueries = Collections.unmodifiableMap(queryMap);
    }

    private OrderBatch getOrderBatch(Batch batch, EntityPermission entityPermission) {
        return OrderBatchFactory.getInstance().getEntityFromQuery(entityPermission, getOrderBatchQueries,
                batch, Session.MAX_TIME_LONG);
    }

    public OrderBatch getOrderBatch(Batch batch) {
        return getOrderBatch(batch, EntityPermission.READ_ONLY);
    }

    public OrderBatch getOrderBatchForUpdate(Batch batch) {
        return getOrderBatch(batch, EntityPermission.READ_WRITE);
    }

    public OrderBatchValue getOrderBatchValue(OrderBatch orderBatch) {
        return orderBatch == null? null: orderBatch.getOrderBatchValue().clone();
    }

    public OrderBatchValue getOrderBatchValueForUpdate(Batch batch) {
        return getOrderBatchValue(getOrderBatchForUpdate(batch));
    }

    public void updateOrderBatchFromValue(OrderBatchValue orderBatchValue, BasePK updatedBy) {
        if(orderBatchValue.hasBeenModified()) {
            OrderBatch orderBatch = OrderBatchFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    orderBatchValue.getPrimaryKey());

            orderBatch.setThruTime(session.START_TIME_LONG);
            orderBatch.store();

            BatchPK batchPK = orderBatch.getBatchPK(); // Not updated
            CurrencyPK currencyPK = orderBatchValue.getCurrencyPK();
            Long count = orderBatchValue.getCount();
            Long amount = orderBatchValue.getAmount();

            orderBatch = OrderBatchFactory.getInstance().create(batchPK, currencyPK, count, amount, session.START_TIME_LONG, Session.MAX_TIME_LONG);

            sendEventUsingNames(batchPK, EventTypes.MODIFY.name(), orderBatch.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }

    public void deleteOrderBatch(OrderBatch orderBatch, BasePK deletedBy) {
        orderBatch.setThruTime(session.START_TIME_LONG);

        sendEventUsingNames(orderBatch.getBatchPK(), EventTypes.MODIFY.name(), orderBatch.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
    }

    public void deleteOrderBatch(Batch batch, BasePK deletedBy) {
        deleteOrderBatch(getOrderBatchForUpdate(batch), deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Orders
    // --------------------------------------------------------------------------------
    
    public Order createOrder(OrderType orderType, String orderName, OrderPriority orderPriority, Currency currency, Boolean holdUntilComplete,
            Boolean allowBackorders, Boolean allowSubstitutions, Boolean allowCombiningShipments, Term term, String reference, String description,
            CancellationPolicy cancellationPolicy, ReturnPolicy returnPolicy, Boolean taxable, BasePK createdBy) {
        Order order = OrderFactory.getInstance().create();
        OrderDetail orderDetail = OrderDetailFactory.getInstance().create(order, orderType, orderName, orderPriority, currency, holdUntilComplete,
                allowBackorders, allowSubstitutions, allowCombiningShipments, term, reference, description, cancellationPolicy, returnPolicy, taxable,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        order = OrderFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, order.getPrimaryKey());
        order.setActiveDetail(orderDetail);
        order.setLastDetail(orderDetail);
        order.store();
        
        sendEventUsingNames(order.getPrimaryKey(), EventTypes.CREATE.name(), null, null, createdBy);
        
        createOrderStatus(order);
        
        return order;
    }
    
    public Order getOrderByPK(OrderPK orderPK) {
        return OrderFactory.getInstance().getEntityFromPK(EntityPermission.READ_ONLY, orderPK);
    }
    
    /** Assume that the entityInstance passed to this function is a ECHOTHREE.Order */
    public Order getOrderByEntityInstance(EntityInstance entityInstance) {
        OrderPK pk = new OrderPK(entityInstance.getEntityUniqueId());
        Order order = OrderFactory.getInstance().getEntityFromPK(EntityPermission.READ_ONLY, pk);

        return order;
    }

    private Order convertEntityInstanceToOrder(final EntityInstance entityInstance, final EntityPermission entityPermission) {
        Order order = null;

        if(getCoreControl().verifyEntityInstance(entityInstance, ComponentVendors.ECHOTHREE.name(), EntityTypes.Order.name())) {
            order = OrderFactory.getInstance().getEntityFromPK(entityPermission, new OrderPK(entityInstance.getEntityUniqueId()));
        }

        return order;
    }

    public Order convertEntityInstanceToOrder(final EntityInstance entityInstance) {
        return convertEntityInstanceToOrder(entityInstance, EntityPermission.READ_ONLY);
    }

    public Order convertEntityInstanceToOrderForUpdate(final EntityInstance entityInstance) {
        return convertEntityInstanceToOrder(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countOrdersByOrderType(final OrderType orderType) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM orders, orderdetails " +
                "WHERE ord_activedetailid = orddt_orderdetailid AND orddt_ordtyp_ordertypeid = ?",
                orderType);
    }

    public long countOrdersByOrderPriority(final OrderPriority orderPriority) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM orders, orderdetails " +
                "WHERE ord_activedetailid = orddt_orderdetailid AND orddt_ordpr_orderpriorityid = ?",
                orderPriority);
    }

    public long countOrdersByCancellationPolicy(final CancellationPolicy cancellationPolicy) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM orders, orderdetails " +
                "WHERE ord_activedetailid = orddt_orderdetailid AND orddt_cnclplcy_cancellationpolicyid = ?",
                cancellationPolicy);
    }

    public long countOrdersByReturnPolicy(final ReturnPolicy returnPolicy) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM orders, orderdetails " +
                "WHERE ord_activedetailid = orddt_orderdetailid AND orddt_rtnplcy_returnpolicyid = ?",
                returnPolicy);
    }

    private static final Map<EntityPermission, String> getOrderByNameQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM orders, orderdetails " +
                "WHERE ord_activedetailid = orddt_orderdetailid AND orddt_ordtyp_ordertypeid = ? AND orddt_ordername = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM orders, orderdetails " +
                "WHERE ord_activedetailid = orddt_orderdetailid AND orddt_ordtyp_ordertypeid = ? AND orddt_ordername = ? " +
                "FOR UPDATE");
        getOrderByNameQueries = Collections.unmodifiableMap(queryMap);
    }

    public Order getOrderByName(OrderType orderType, String orderName, EntityPermission entityPermission) {
        return OrderFactory.getInstance().getEntityFromQuery(entityPermission, getOrderByNameQueries,
                orderType, orderName);
    }

    private Order getOrderByNameUsingNames(String orderTypeName, String orderName, EntityPermission entityPermission) {
        OrderType orderType = getOrderTypeByName(orderTypeName);

        return getOrderByName(orderType, orderName, entityPermission);
    }

    public Order getOrderByName(OrderType orderType, String orderName) {
        return getOrderByName(orderType, orderName, EntityPermission.READ_ONLY);
    }

    public Order getOrderByNameForUpdate(OrderType orderType, String orderName) {
        return getOrderByName(orderType, orderName, EntityPermission.READ_WRITE);
    }

    public Order getOrderByNameUsingNames(String orderTypeName, String orderName) {
        return getOrderByNameUsingNames(orderTypeName, orderName, EntityPermission.READ_ONLY);
    }

    public Order getOrderByNameForUpdateUsingNames(String orderTypeName, String orderName) {
        return getOrderByNameUsingNames(orderTypeName, orderName, EntityPermission.READ_WRITE);
    }

    public OrderDetailValue getOrderDetailValueForUpdate(Order order) {
        return order == null? null: order.getLastDetailForUpdate().getOrderDetailValue().clone();
    }
    
    public OrderDetailValue getOrderDetailValueByNameForUpdate(OrderType orderType, String orderName) {
        return getOrderDetailValueForUpdate(getOrderByNameForUpdate(orderType, orderName));
    }
    
    public long countOrdersByBillToAndReference(final Party billToParty, final String reference) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM orders, orderdetails, orderroles, orderroletypes " +
                "WHERE ord_activedetailid = orddt_orderdetailid AND orddt_reference = ? " +
                "AND ord_orderid = ordr_ord_orderid AND ordr_thrutime = ? AND ordr_par_partyid = ? " +
                "AND ordr_ordrtyp_orderroletypeid = ordrtyp_orderroletypeid AND ordrtyp_orderroletypename = ?",
                reference, Session.MAX_TIME_LONG, billToParty, OrderRoleTypes.BILL_TO.name());
    }

    public OrderTransfer getOrderTransfer(UserVisit userVisit, Order order) {
        // TODO
        return null;
    }

    public void updateOrderFromValue(OrderDetailValue orderDetailValue, BasePK updatedBy) {
        if(orderDetailValue.hasBeenModified()) {
            Order order = OrderFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, orderDetailValue.getOrderPK());
            OrderDetail orderDetail = order.getActiveDetailForUpdate();
            
            orderDetail.setThruTime(session.START_TIME_LONG);
            orderDetail.store();
            
            OrderPK orderPK = orderDetail.getOrderPK(); // Not updated
            OrderTypePK orderTypePK = orderDetail.getOrderTypePK(); // Not updated
            String orderName = orderDetailValue.getOrderName();
            OrderPriorityPK orderPriorityPK = orderDetailValue.getOrderPriorityPK();
            CurrencyPK currencyPK = orderDetail.getCurrencyPK(); // Not updated
            Boolean holdUntilComplete = orderDetailValue.getHoldUntilComplete();
            Boolean allowBackorders = orderDetailValue.getAllowBackorders();
            Boolean allowSubstitutions = orderDetailValue.getAllowSubstitutions();
            Boolean allowCombiningShipments = orderDetailValue.getAllowCombiningShipments();
            TermPK termPK = orderDetailValue.getTermPK();
            String reference = orderDetailValue.getReference();
            String description = orderDetailValue.getDescription();
            CancellationPolicyPK cancellationPolicyPK = orderDetailValue.getCancellationPolicyPK();
            ReturnPolicyPK returnPolicyPK = orderDetailValue.getReturnPolicyPK();
            Boolean taxable = orderDetailValue.getTaxable();
            
            orderDetail = OrderDetailFactory.getInstance().create(orderPK, orderTypePK, orderName, orderPriorityPK, currencyPK, holdUntilComplete,
                    allowBackorders, allowSubstitutions, allowCombiningShipments, termPK, reference, description, cancellationPolicyPK, returnPolicyPK,
                    taxable, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            order.setActiveDetail(orderDetail);
            order.setLastDetail(orderDetail);
            
            sendEventUsingNames(orderPK, EventTypes.MODIFY.name(), null, null, updatedBy);
        }
    }
    
    public void deleteOrder(Order order, BasePK deletedBy) {
        removeOrderStatusByOrder(order);
        deleteOrderTimesByOrder(order, deletedBy);
        deleteOrderRolesByOrder(order, deletedBy);
        deleteOrderAdjustmentsByOrder(order, deletedBy);
        deleteOrderLinesByOrder(order, deletedBy);
        removeOrderUserVisitsByOrder(order);
        
        OrderDetail orderDetail = order.getLastDetailForUpdate();
        String orderTypeName = orderDetail.getOrderType().getLastDetail().getOrderTypeName();
        if(orderTypeName.equals(OrderTypes.WISHLIST.name())) {
            var wishlistControl = (WishlistControl)Session.getModelController(WishlistControl.class);
            
            wishlistControl.deleteWishlistByOrder(order, deletedBy);
        }
        
        orderDetail.setThruTime(session.START_TIME_LONG);
        order.setActiveDetail(null);
        order.store();
        
        sendEventUsingNames(order.getPrimaryKey(), EventTypes.DELETE.name(), null, null, deletedBy);
    }
    
    public void deleteOrders(List<Order> orders, BasePK deletedBy) {
        orders.stream().forEach((order) -> {
            deleteOrder(order, deletedBy);
        });
    }
    
    public void deleteOrdersByWishlistType(WishlistType wishlistType, BasePK deletedBy) {
        var wishlistControl = (WishlistControl)Session.getModelController(WishlistControl.class);
        List<Wishlist> wishlists = wishlistControl.getWishlistsByWishlistTypeForUpdate(wishlistType);
        
        wishlists.stream().forEach((wishlist) -> {
            deleteOrder(wishlist.getOrderForUpdate(), deletedBy);
        });
    }
    
    // --------------------------------------------------------------------------------
    //   Order Statuses
    // --------------------------------------------------------------------------------
    
    public OrderStatus createOrderStatus(Order order) {
        return OrderStatusFactory.getInstance().create(order, 0, 0, 0, 0);
    }
    
    private OrderStatus getOrderStatus(Order order, EntityPermission entityPermission) {
        OrderStatus orderStatus = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM orderstatuses " +
                        "WHERE ordst_ord_orderid = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM orderstatuses " +
                        "WHERE ordst_ord_orderid = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = OrderStatusFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, order.getPrimaryKey().getEntityId());
            
            orderStatus = OrderStatusFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return orderStatus;
    }
    
    public OrderStatus getOrderStatus(Order order) {
        return getOrderStatus(order, EntityPermission.READ_ONLY);
    }
    
    public OrderStatus getOrderStatusForUpdate(Order order) {
        return getOrderStatus(order, EntityPermission.READ_WRITE);
    }
    
    public void removeOrderStatusByOrder(Order order) {
        OrderStatus orderStatus = getOrderStatusForUpdate(order);
        
        if(orderStatus != null) {
            orderStatus.remove();
        }
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

        orderPaymentPreferences.stream().forEach((orderPaymentPreference) -> {
            orderPaymentPreferenceTransfers.add(orderPaymentPreferenceTransferCache.getOrderPaymentPreferenceTransfer(orderPaymentPreference));
        });

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
        orderPaymentPreferences.stream().forEach((orderPaymentPreference) -> {
            deleteOrderPaymentPreference(orderPaymentPreference, deletedBy);
        });
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
    
    // --------------------------------------------------------------------------------
    //   Order Times
    // --------------------------------------------------------------------------------

    public OrderTime createOrderTime(Order order, OrderTimeType orderTimeType, Long time, BasePK createdBy) {
        OrderTime orderTime = OrderTimeFactory.getInstance().create(order, orderTimeType, time, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEventUsingNames(order.getPrimaryKey(), EventTypes.MODIFY.name(), orderTime.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);

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
        return getOrderTransferCaches(userVisit).getOrderTimeTransferCache().getOrderTimeTransfer(orderTime);
    }

    public List<OrderTimeTransfer> getOrderTimeTransfers(UserVisit userVisit, List<OrderTime> orderTimes) {
        List<OrderTimeTransfer> orderTimeTransfers = new ArrayList<>(orderTimes.size());
        OrderTimeTransferCache orderTimeTransferCache = getOrderTransferCaches(userVisit).getOrderTimeTransferCache();

        orderTimes.stream().forEach((orderTime) -> {
            orderTimeTransfers.add(orderTimeTransferCache.getOrderTimeTransfer(orderTime));
        });

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
            OrderTime orderTime = OrderTimeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    orderTimeValue.getPrimaryKey());

            orderTime.setThruTime(session.START_TIME_LONG);
            orderTime.store();

            OrderPK orderPK = orderTime.getOrderPK(); // Not updated
            OrderTimeTypePK orderTimeTypePK = orderTime.getOrderTimeTypePK(); // Not updated
            Long time = orderTimeValue.getTime();

            orderTime = OrderTimeFactory.getInstance().create(orderPK, orderTimeTypePK, time, session.START_TIME_LONG, Session.MAX_TIME_LONG);

            sendEventUsingNames(orderPK, EventTypes.MODIFY.name(), orderTime.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }

    public void deleteOrderTime(OrderTime orderTime, BasePK deletedBy) {
        orderTime.setThruTime(session.START_TIME_LONG);

        sendEventUsingNames(orderTime.getOrderTimeTypePK(), EventTypes.MODIFY.name(), orderTime.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);

    }

    public void deleteOrderTimes(List<OrderTime> orderTimes, BasePK deletedBy) {
        orderTimes.stream().forEach((orderTime) -> {
            deleteOrderTime(orderTime, deletedBy);
        });
    }

    public void deleteOrderTimesByOrder(Order order, BasePK deletedBy) {
        deleteOrderTimes(getOrderTimesByOrderForUpdate(order), deletedBy);
    }

    public void deleteOrderTimesByOrderTimeType(OrderTimeType orderTimeType, BasePK deletedBy) {
        deleteOrderTimes(getOrderTimesByOrderTimeTypeForUpdate(orderTimeType), deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Order Roles
    // --------------------------------------------------------------------------------
    
    public OrderRole createOrderRoleUsingNames(Order order, Party party, String orderRoleTypeName, BasePK createdBy) {
        return createOrderRole(order, party, getOrderRoleTypeByName(orderRoleTypeName), createdBy);
    }
    
    public OrderRole createOrderRole(Order order, Party party, OrderRoleType orderRoleType, BasePK createdBy) {
        OrderRole orderRole = OrderRoleFactory.getInstance().create(order, party, orderRoleType, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(order.getPrimaryKey(), EventTypes.MODIFY.name(), orderRole.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
        return orderRole;
    }
    
    public long countOrderRolesByOrderAndOrderRoleType(Order order, OrderRoleType orderRoleType) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM orderroles " +
                "WHERE ordr_ord_orderid = ? AND ordr_ordrtyp_orderroletypeid = ? AND ordr_thrutime = ?",
                order, orderRoleType, Session.MAX_TIME);
    }

    private static final Map<EntityPermission, String> getOrderRolesByOrderAndOrderRoleTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM orderroles, orderroletypes, parties, partydetails " +
                "WHERE ordr_ord_orderid = ? AND ordr_ordrtyp_orderroletypeid = ? AND ordr_thrutime = ? " +
                "AND ordr_ordrtyp_orderroletypeid = ordrtyp_orderroletypeid " +
                "AND ordr_par_partyid = par_partyid AND par_activedetailid = pardt_partydetailid " +
                "ORDER BY ordrtyp_sortorder, pardt_partyname");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM orderroles " +
                "WHERE ordr_ord_orderid = ? AND ordr_ordrtyp_orderroletypeid = ? AND ordr_thrutime = ? " +
                "FOR UPDATE");
        getOrderRolesByOrderAndOrderRoleTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<OrderRole> getOrderRolesByOrderAndOrderRoleType(Order order, OrderRoleType orderRoleType, EntityPermission entityPermission) {
        return OrderRoleFactory.getInstance().getEntitiesFromQuery(entityPermission, getOrderRolesByOrderAndOrderRoleTypeQueries,
                order, orderRoleType, Session.MAX_TIME);
    }

    public List<OrderRole> getOrderRolesByOrderAndOrderRoleType(Order order, OrderRoleType orderRoleType) {
        return getOrderRolesByOrderAndOrderRoleType(order, orderRoleType, EntityPermission.READ_ONLY);
    }
    
    public List<OrderRole> getOrderRolesByOrderAndOrderRoleTypeForUpdate(Order order, OrderRoleType orderRoleType) {
        return getOrderRolesByOrderAndOrderRoleType(order, orderRoleType, EntityPermission.READ_WRITE);
    }
    
    private OrderRole getOrderRoleByOrderAndOrderRoleType(Order order, OrderRoleType orderRoleType, EntityPermission entityPermission) {
        OrderRole orderRole = null;
        
        // TODO: Check orderRoleType's AllowMultiple flag here, and throw an IllegalArgumentException if it's true.
        if(false) {
            throw new IllegalArgumentException();
        } else {
            List<OrderRole> orderRoles = getOrderRolesByOrderAndOrderRoleType(order, orderRoleType, entityPermission);
            int size = orderRoles.size();

            if(size > 1) {
                throw new IllegalStateException();
            } else if(size == 1) {
                orderRole = orderRoles.get(0);
            }
        }
        
        return orderRole;
    }
    
    public OrderRole getOrderRoleByOrderAndOrderRoleType(Order order, OrderRoleType orderRoleType) {
        return getOrderRoleByOrderAndOrderRoleType(order, orderRoleType, EntityPermission.READ_ONLY);
    }
    
    public OrderRole getOrderRoleByOrderAndOrderRoleTypeForUpdate(Order order, OrderRoleType orderRoleType) {
        return getOrderRoleByOrderAndOrderRoleType(order, orderRoleType, EntityPermission.READ_WRITE);
    }
    
    public OrderRole getOrderRoleByOrderAndOrderRoleTypeUsingNames(Order order, String orderRoleTypeName) {
        return getOrderRoleByOrderAndOrderRoleType(order, getOrderRoleTypeByName(orderRoleTypeName));
    }
    
    public OrderRole getOrderRoleByOrderAndOrderRoleTypeUsingNamesForUpdate(Order order, String orderRoleTypeName) {
        return getOrderRoleByOrderAndOrderRoleTypeForUpdate(order, getOrderRoleTypeByName(orderRoleTypeName));
    }
    
    private static final Map<EntityPermission, String> getOrderRolesByOrderQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM orderroles, orderroletypes, parties, partydetails " +
                "WHERE ordr_ord_orderid = ? AND ordr_thrutime = ? " +
                "AND ordr_ordrtyp_orderroletypeid = ordrtyp_orderroletypeid " +
                "AND ordr_par_partyid = par_partyid AND par_activedetailid = pardt_partydetailid " +
                "ORDER BY ordrtyp_sortorder, pardt_partyname");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM orderroles " +
                "WHERE ordr_ord_orderid = ? AND ordr_thrutime = ? " +
                "FOR UPDATE");
        getOrderRolesByOrderQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<OrderRole> getOrderRolesByOrder(Order order, EntityPermission entityPermission) {
        return OrderRoleFactory.getInstance().getEntitiesFromQuery(entityPermission, getOrderRolesByOrderQueries,
                order, Session.MAX_TIME);
    }

    public List<OrderRole> getOrderRolesByOrder(Order order) {
        return getOrderRolesByOrder(order, EntityPermission.READ_ONLY);
    }
    
    public List<OrderRole> getOrderRolesByOrderForUpdate(Order order) {
        return getOrderRolesByOrder(order, EntityPermission.READ_WRITE);
    }
    
    public OrderRoleTransfer getOrderRoleTransfer(UserVisit userVisit, OrderRole orderRole) {
        return getOrderTransferCaches(userVisit).getOrderRoleTransferCache().getOrderRoleTransfer(orderRole);
    }

    public List<OrderRoleTransfer> getOrderRoleTransfers(UserVisit userVisit, List<OrderRole> orderRoles) {
        List<OrderRoleTransfer> orderRoleTransfers = new ArrayList<>(orderRoles.size());

        orderRoles.stream().forEach((orderRole) -> {
            orderRoleTransfers.add(getOrderRoleTransfer(userVisit, orderRole));
        });

        return orderRoleTransfers;
    }

    public List<OrderRoleTransfer> getOrderRoleTransfersByOrder(UserVisit userVisit, Order order) {
        return getOrderRoleTransfers(userVisit, getOrderRolesByOrder(order));
    }

    public void deleteOrderRole(OrderRole orderRole, BasePK deletedBy) {
        orderRole.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(orderRole.getOrderPK(), EventTypes.MODIFY.name(), orderRole.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
    }
    
    public void deleteOrderRoles(List<OrderRole> orderRoles, BasePK deletedBy) {
        orderRoles.stream().forEach((orderRole) -> {
            deleteOrderRole(orderRole, deletedBy);
        });
    }
    
    public void deleteOrderRolesByOrder(Order order, BasePK deletedBy) {
        deleteOrderRoles(getOrderRolesByOrderForUpdate(order), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Order Adjustments
    // --------------------------------------------------------------------------------
    
    public OrderAdjustment createOrderAdjustment(Order order, Integer orderAdjustmentSequence, OrderAdjustmentType orderAdjustmentType, Long amount,
            BasePK createdBy) {
        OrderAdjustment orderAdjustment = OrderAdjustmentFactory.getInstance().create();
        OrderAdjustmentDetail orderAdjustmentDetail = OrderAdjustmentDetailFactory.getInstance().create(orderAdjustment,
                order, orderAdjustmentSequence, orderAdjustmentType, amount, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        orderAdjustment = OrderAdjustmentFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                orderAdjustment.getPrimaryKey());
        orderAdjustment.setActiveDetail(orderAdjustmentDetail);
        orderAdjustment.setLastDetail(orderAdjustmentDetail);
        orderAdjustment.store();
        
        sendEventUsingNames(orderAdjustment.getPrimaryKey(), EventTypes.CREATE.name(), null, null, createdBy);
        
        return orderAdjustment;
    }
    
    private List<OrderAdjustment> getOrderAdjustmentsByOrder(Order order, EntityPermission entityPermission) {
        List<OrderAdjustment> orderAdjustments = null;
        
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
            
            PreparedStatement ps = OrderAdjustmentFactory.getInstance().prepareStatement(query);
            
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
        OrderAdjustmentDetail orderAdjustmentDetail = orderAdjustment.getLastDetailForUpdate();
        orderAdjustmentDetail.setThruTime(session.START_TIME_LONG);
        orderAdjustment.setActiveDetail(null);
        orderAdjustment.store();
        
        sendEventUsingNames(orderAdjustmentDetail.getOrderPK(), EventTypes.DELETE.name(), null, null, deletedBy);
    }
    
    public void deleteOrderAdjustmentsByOrder(Order order, BasePK deletedBy) {
        List<OrderAdjustment> orderAdjustments = getOrderAdjustmentsByOrderForUpdate(order);
        
        orderAdjustments.stream().forEach((orderAdjustment) -> {
            deleteOrderAdjustment(orderAdjustment, deletedBy);
        });
    }
    
    // --------------------------------------------------------------------------------
    //   Order User Visits
    // --------------------------------------------------------------------------------
    
    public OrderUserVisit createOrderUserVisit(Order order, UserVisit userVisit) {
        return OrderUserVisitFactory.getInstance().create(order, userVisit, session.START_TIME_LONG, Session.MAX_TIME_LONG);
    }
    
    private static final Map<EntityPermission, String> getOrderUserVisitQueries;
    
    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM orderuservisits "
                + "WHERE orduvis_ord_orderid = ? AND orduvis_uvis_uservisitid = ? AND orduvis_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM orderuservisits "
                + "WHERE orduvis_ord_orderid = ? AND orduvis_uvis_uservisitid = ? AND orduvis_thrutime = ? "
                + "FOR UPDATE");
        getOrderUserVisitQueries = Collections.unmodifiableMap(queryMap);
    }

    private OrderUserVisit getOrderUserVisit(Order order, UserVisit userVisit, EntityPermission entityPermission) {
        return OrderUserVisitFactory.getInstance().getEntityFromQuery(entityPermission, getOrderUserVisitQueries,
                order, userVisit, Session.MAX_TIME);
    }
    
    public OrderUserVisit getOrderUserVisit(Order order, UserVisit userVisit) {
        return getOrderUserVisit(order, userVisit, EntityPermission.READ_ONLY);
    }
    
    public OrderUserVisit getOrderUserVisitForUpdate(Order order, UserVisit userVisit) {
        return getOrderUserVisit(order, userVisit, EntityPermission.READ_WRITE);
    }
    
    public OrderUserVisitValue getOrderUserVisitValue(OrderUserVisit orderUserVisit) {
        return orderUserVisit == null? null: orderUserVisit.getOrderUserVisitValue().clone();
    }
    
    public OrderUserVisitValue getOrderUserVisitValueForUpdate(Order order, UserVisit userVisit) {
        return getOrderUserVisitValue(getOrderUserVisitForUpdate(order, userVisit));
    }
    
    private static final Map<EntityPermission, String> getOrderUserVisitsByOrderQueries;
    
    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM orderuservisits, uservisits "
                + "WHERE orduvis_ord_orderid = ? AND orduvis_thrutime = ? "
                + "AND orduvis_uvis_uservisitid = uvis_uservisitid "
                + "ORDER BY uvis_fromtime");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM orderuservisits "
                + "WHERE orduvis_ord_orderid = ? AND orduvis_thrutime = ? "
                + "FOR UPDATE");
        getOrderUserVisitsByOrderQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<OrderUserVisit> getOrderUserVisitsByOrder(Order order, EntityPermission entityPermission) {
        return OrderUserVisitFactory.getInstance().getEntitiesFromQuery(entityPermission, getOrderUserVisitsByOrderQueries,
                order, Session.MAX_TIME);
    }
    
    public List<OrderUserVisit> getOrderUserVisitsByOrder(Order order) {
        return getOrderUserVisitsByOrder(order, EntityPermission.READ_ONLY);
    }
    
    public List<OrderUserVisit> getOrderUserVisitsByOrderForUpdate(Order order) {
        return getOrderUserVisitsByOrder(order, EntityPermission.READ_WRITE);
    }
    
    private static final Map<EntityPermission, String> getOrderUserVisitsByUserVisitQueries;
    
    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM orderuservisits, orders, orderdetails "
                + "WHERE orduvis_ord_orderid = ? AND orduvis_thrutime = ? "
                + "AND orduvis_ord_orderid = ord_orderid AND ord_lastdetailid = orddt_orderdetailid "
                + "ORDER BY orddt_ordername");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM orderuservisits "
                + "WHERE orduvis_ord_orderid = ? AND orduvis_thrutime = ? "
                + "FOR UPDATE");
        getOrderUserVisitsByUserVisitQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<OrderUserVisit> getOrderUserVisitsByUserVisit(UserVisit userVisit, EntityPermission entityPermission) {
        return OrderUserVisitFactory.getInstance().getEntitiesFromQuery(entityPermission, getOrderUserVisitsByUserVisitQueries,
                userVisit, Session.MAX_TIME);
    }
    
    public List<OrderUserVisit> getOrderUserVisitsByUserVisit(UserVisit userVisit) {
        return getOrderUserVisitsByUserVisit(userVisit, EntityPermission.READ_ONLY);
    }
    
    public List<OrderUserVisit> getOrderUserVisitsByUserVisitForUpdate(UserVisit userVisit) {
        return getOrderUserVisitsByUserVisit(userVisit, EntityPermission.READ_WRITE);
    }
    
    public void deleteOrderUserVisit(OrderUserVisit orderUserVisit) {
        orderUserVisit.setThruTime(session.START_TIME_LONG);
        orderUserVisit.store();
    }
    
    public void deleteOrderUserVisits(List<OrderUserVisit> orderUserVisits) {
        orderUserVisits.stream().forEach((orderUserVisit) -> {
            deleteOrderUserVisit(orderUserVisit);
        });
    }
    
    public void deleteOrderUserVisitsByOrder(Order order) {
        deleteOrderUserVisits(getOrderUserVisitsByOrderForUpdate(order));
    }
    
    public void deleteOrderUserVisitsByUserVisit(UserVisit userVisit) {
        deleteOrderUserVisits(getOrderUserVisitsByUserVisitForUpdate(userVisit));
    }
    
    public void removeOrderUserVisit(OrderUserVisit orderUserVisit) {
        orderUserVisit.remove();
    }
    
    public void removeOrderUserVisits(List<OrderUserVisit> orderUserVisits) {
        orderUserVisits.stream().forEach((orderUserVisit) -> {
            removeOrderUserVisit(orderUserVisit);
        });
    }
    
    public void removeOrderUserVisitsByOrder(Order order) {
        removeOrderUserVisits(getOrderUserVisitsByOrderForUpdate(order));
    }
    
    public void removeOrderUserVisitsByUserVisit(UserVisit userVisit) {
        removeOrderUserVisits(getOrderUserVisitsByUserVisitForUpdate(userVisit));
    }
    
    // --------------------------------------------------------------------------------
    //   Order Shipment Groups
    // --------------------------------------------------------------------------------
    
    public OrderShipmentGroup createOrderShipmentGroup(Order order, Integer orderShipmentGroupSequence, ItemDeliveryType itemDeliveryType, Boolean isDefault,
            PartyContactMechanism partyContactMechanism, ShippingMethod shippingMethod, Boolean holdUntilComplete, BasePK createdBy) {
        OrderShipmentGroup defaultOrderShipmentGroup = getDefaultOrderShipmentGroup(order, itemDeliveryType);
        boolean defaultFound = defaultOrderShipmentGroup != null;

        if(defaultFound && isDefault) {
            OrderShipmentGroupDetailValue defaultOrderShipmentGroupDetailValue = getDefaultOrderShipmentGroupDetailValueForUpdate(order, itemDeliveryType);

            defaultOrderShipmentGroupDetailValue.setIsDefault(Boolean.FALSE);
            updateOrderShipmentGroupFromValue(defaultOrderShipmentGroupDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = Boolean.TRUE;
        }

        OrderShipmentGroup orderShipmentGroup = OrderShipmentGroupFactory.getInstance().create();
        OrderShipmentGroupDetail orderShipmentGroupDetail = OrderShipmentGroupDetailFactory.getInstance().create(orderShipmentGroup, order,
                orderShipmentGroupSequence, itemDeliveryType, isDefault, partyContactMechanism, shippingMethod, holdUntilComplete, session.START_TIME_LONG,
                Session.MAX_TIME_LONG);
        
        // Convert to R/W
        orderShipmentGroup = OrderShipmentGroupFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, orderShipmentGroup.getPrimaryKey());
        orderShipmentGroup.setActiveDetail(orderShipmentGroupDetail);
        orderShipmentGroup.setLastDetail(orderShipmentGroupDetail);
        orderShipmentGroup.store();
        
        sendEventUsingNames(orderShipmentGroup.getPrimaryKey(), EventTypes.CREATE.name(), null, null, createdBy);
        
        return orderShipmentGroup;
    }
    
    private static final Map<EntityPermission, String> getOrderShipmentGroupBySequenceQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM ordershipmentgroups, ordershipmentgroupdetails " +
                "WHERE ordshpgrp_activedetailid = ordshpgrpdt_ordershipmentgroupdetailid " +
                "AND ordshpgrpdt_ord_orderid = ? AND ordshpgrpdt_ordershipmentgroupsequence = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM ordershipmentgroups, ordershipmentgroupdetails " +
                "WHERE ordshpgrp_activedetailid = ordshpgrpdt_ordershipmentgroupdetailid " +
                "AND ordshpgrpdt_ord_orderid = ? AND ordshpgrpdt_ordershipmentgroupsequence = ? " +
                "FOR UPDATE");
        getOrderShipmentGroupBySequenceQueries = Collections.unmodifiableMap(queryMap);
    }

    private OrderShipmentGroup getOrderShipmentGroupBySequence(Order order, Integer orderShipmentGroupSequence, EntityPermission entityPermission) {
        return OrderShipmentGroupFactory.getInstance().getEntityFromQuery(entityPermission, getOrderShipmentGroupBySequenceQueries,
                order, orderShipmentGroupSequence);
    }

    public OrderShipmentGroup getOrderShipmentGroupBySequence(Order order, Integer orderShipmentGroupSequence) {
        return getOrderShipmentGroupBySequence(order, orderShipmentGroupSequence, EntityPermission.READ_ONLY);
    }

    public OrderShipmentGroup getOrderShipmentGroupBySequenceForUpdate(Order order, Integer orderShipmentGroupSequence) {
        return getOrderShipmentGroupBySequence(order, orderShipmentGroupSequence, EntityPermission.READ_WRITE);
    }

    public OrderShipmentGroupDetailValue getOrderShipmentGroupDetailValueForUpdate(OrderShipmentGroup orderShipmentGroup) {
        return orderShipmentGroup == null? null: orderShipmentGroup.getLastDetailForUpdate().getOrderShipmentGroupDetailValue().clone();
    }

    public OrderShipmentGroupDetailValue getOrderShipmentGroupDetailValueBySequenceForUpdate(Order order, Integer orderShipmentGroupSequence) {
        return getOrderShipmentGroupDetailValueForUpdate(getOrderShipmentGroupBySequenceForUpdate(order, orderShipmentGroupSequence));
    }

    private static final Map<EntityPermission, String> getDefaultOrderShipmentGroupQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM ordershipmentgroups, ordershipmentgroupdetails " +
                "WHERE ordshpgrp_activedetailid = ordshpgrpdt_ordershipmentgroupdetailid " +
                "AND ordshpgrpdt_ord_orderid = ? AND ordshpgrpdt_idlvrtyp_itemdeliverytypeid = ? AND ordshpgrpdt_isdefault = 1");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM ordershipmentgroups, ordershipmentgroupdetails " +
                "WHERE ordshpgrp_activedetailid = ordshpgrpdt_ordershipmentgroupdetailid " +
                "AND ordshpgrpdt_ord_orderid = ? AND ordshpgrpdt_idlvrtyp_itemdeliverytypeid = ? AND ordshpgrpdt_isdefault = 1 " +
                "FOR UPDATE");
        getDefaultOrderShipmentGroupQueries = Collections.unmodifiableMap(queryMap);
    }

    private OrderShipmentGroup getDefaultOrderShipmentGroup(Order order, ItemDeliveryType itemDeliveryType, EntityPermission entityPermission) {
        return OrderShipmentGroupFactory.getInstance().getEntityFromQuery(entityPermission, getDefaultOrderShipmentGroupQueries,
                order, itemDeliveryType);
    }

    public OrderShipmentGroup getDefaultOrderShipmentGroup(Order order, ItemDeliveryType itemDeliveryType) {
        return getDefaultOrderShipmentGroup(order, itemDeliveryType, EntityPermission.READ_ONLY);
    }

    public OrderShipmentGroup getDefaultOrderShipmentGroupForUpdate(Order order, ItemDeliveryType itemDeliveryType) {
        return getDefaultOrderShipmentGroup(order, itemDeliveryType, EntityPermission.READ_WRITE);
    }

    public OrderShipmentGroupDetailValue getDefaultOrderShipmentGroupDetailValueForUpdate(Order order, ItemDeliveryType itemDeliveryType) {
        return getDefaultOrderShipmentGroupForUpdate(order, itemDeliveryType).getLastDetailForUpdate().getOrderShipmentGroupDetailValue().clone();
    }

    private static final Map<EntityPermission, String> getOrderShipmentGroupsByOrderQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM ordershipmentgroups, ordershipmentgroupdetails " +
                "WHERE ordshpgrp_activedetailid = ordshpgrpdt_ordershipmentgroupdetailid " +
                "AND ordshpgrpdt_ord_orderid = ? " +
                "ORDER BY ordshpgrpdt_ordershipmentgroupsequence");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM ordershipmentgroups, ordershipmentgroupdetails " +
                "WHERE ordshpgrp_activedetailid = ordshpgrpdt_ordershipmentgroupdetailid " +
                "AND ordshpgrpdt_ord_orderid = ? " +
                "FOR UPDATE");
        getOrderShipmentGroupsByOrderQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<OrderShipmentGroup> getOrderShipmentGroupsByOrder(Order order, EntityPermission entityPermission) {
        return OrderShipmentGroupFactory.getInstance().getEntitiesFromQuery(entityPermission, getOrderShipmentGroupsByOrderQueries,
                order);
    }

    public List<OrderShipmentGroup> getOrderShipmentGroupsByOrder(Order order) {
        return getOrderShipmentGroupsByOrder(order, EntityPermission.READ_ONLY);
    }

    public List<OrderShipmentGroup> getOrderShipmentGroupsByOrderForUpdate(Order order) {
        return getOrderShipmentGroupsByOrder(order, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getOrderShipmentGroupsQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM ordershipmentgroups, ordershipmentgroupdetails " +
                "WHERE ordshpgrp_activedetailid = ordshpgrpdt_ordershipmentgroupdetailid " +
                "AND ordshpgrpdt_ord_orderid = ? AND ordshpgrpdt_idlvrtyp_itemdeliverytypeid = ? " +
                "ORDER BY ordshpgrpdt_ordershipmentgroupsequence");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM ordershipmentgroups, ordershipmentgroupdetails " +
                "WHERE ordshpgrp_activedetailid = ordshpgrpdt_ordershipmentgroupdetailid " +
                "AND ordshpgrpdt_ord_orderid = ? AND ordshpgrpdt_idlvrtyp_itemdeliverytypeid = ? " +
                "FOR UPDATE");
        getOrderShipmentGroupsQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<OrderShipmentGroup> getOrderShipmentGroups(Order order, ItemDeliveryType itemDeliveryType, EntityPermission entityPermission) {
        return OrderShipmentGroupFactory.getInstance().getEntitiesFromQuery(entityPermission, getOrderShipmentGroupsQueries,
                order, itemDeliveryType);
    }

    public List<OrderShipmentGroup> getOrderShipmentGroups(Order order, ItemDeliveryType itemDeliveryType) {
        return getOrderShipmentGroups(order, itemDeliveryType, EntityPermission.READ_ONLY);
    }

    public List<OrderShipmentGroup> getOrderShipmentGroupsForUpdate(Order order, ItemDeliveryType itemDeliveryType) {
        return getOrderShipmentGroups(order, itemDeliveryType, EntityPermission.READ_WRITE);
    }

    public OrderShipmentGroupTransfer getOrderShipmentGroupTransfer(UserVisit userVisit, OrderShipmentGroup orderShipmentGroup) {
        return getOrderTransferCaches(userVisit).getOrderShipmentGroupTransferCache().getOrderShipmentGroupTransfer(orderShipmentGroup);
    }

    public List<OrderShipmentGroupTransfer> getOrderShipmentGroupTransfers(UserVisit userVisit, List<OrderShipmentGroup> orderShipmentGroups) {
        List<OrderShipmentGroupTransfer> orderShipmentGroupTransfers = new ArrayList<>(orderShipmentGroups.size());
        OrderShipmentGroupTransferCache orderShipmentGroupTransferCache = getOrderTransferCaches(userVisit).getOrderShipmentGroupTransferCache();

        orderShipmentGroups.stream().forEach((orderShipmentGroup) -> {
            orderShipmentGroupTransfers.add(orderShipmentGroupTransferCache.getOrderShipmentGroupTransfer(orderShipmentGroup));
        });

        return orderShipmentGroupTransfers;
    }

    public List<OrderShipmentGroupTransfer> getOrderShipmentGroupTransfers(UserVisit userVisit, Order order) {
        return getOrderShipmentGroupTransfers(userVisit, getOrderShipmentGroupsByOrder(order));
    }

    private void updateOrderShipmentGroupFromValue(OrderShipmentGroupDetailValue orderShipmentGroupDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(orderShipmentGroupDetailValue.hasBeenModified()) {
            OrderShipmentGroup orderShipmentGroup = OrderShipmentGroupFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     orderShipmentGroupDetailValue.getOrderShipmentGroupPK());
            OrderShipmentGroupDetail orderShipmentGroupDetail = orderShipmentGroup.getActiveDetailForUpdate();

            orderShipmentGroupDetail.setThruTime(session.START_TIME_LONG);
            orderShipmentGroupDetail.store();

            OrderShipmentGroupPK orderShipmentGroupPK = orderShipmentGroupDetail.getOrderShipmentGroupPK(); // Not updated
            Order order = orderShipmentGroupDetail.getOrder(); // Not updated
            OrderPK orderPK = order.getPrimaryKey(); // Not updated
            Integer orderShipmentGroupSequence = orderShipmentGroupDetail.getOrderShipmentGroupSequence(); // Not updated
            ItemDeliveryType itemDeliveryType = orderShipmentGroupDetail.getItemDeliveryType(); // Not updated
            ItemDeliveryTypePK itemDeliveryTypePK = itemDeliveryType.getPrimaryKey(); // Not updated
            Boolean isDefault = orderShipmentGroupDetailValue.getIsDefault();
            PartyContactMechanismPK partyContactMechanismPK = orderShipmentGroupDetailValue.getPartyContactMechanismPK();
            ShippingMethodPK shippingMethodPK = orderShipmentGroupDetailValue.getShippingMethodPK();
            Boolean holdUntilComplete = orderShipmentGroupDetailValue.getHoldUntilComplete();

            if(checkDefault) {
                OrderShipmentGroup defaultOrderShipmentGroup = getDefaultOrderShipmentGroup(order, itemDeliveryType);
                boolean defaultFound = defaultOrderShipmentGroup != null && !defaultOrderShipmentGroup.equals(orderShipmentGroup);

                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    OrderShipmentGroupDetailValue defaultOrderShipmentGroupDetailValue = getDefaultOrderShipmentGroupDetailValueForUpdate(order, itemDeliveryType);

                    defaultOrderShipmentGroupDetailValue.setIsDefault(Boolean.FALSE);
                    updateOrderShipmentGroupFromValue(defaultOrderShipmentGroupDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = Boolean.TRUE;
                }
            }

            orderShipmentGroupDetail = OrderShipmentGroupDetailFactory.getInstance().create(orderShipmentGroupPK, orderPK, orderShipmentGroupSequence,
                    itemDeliveryTypePK, isDefault, partyContactMechanismPK, shippingMethodPK, holdUntilComplete, session.START_TIME_LONG, Session.MAX_TIME_LONG);

            orderShipmentGroup.setActiveDetail(orderShipmentGroupDetail);
            orderShipmentGroup.setLastDetail(orderShipmentGroupDetail);

            sendEventUsingNames(orderShipmentGroupPK, EventTypes.MODIFY.name(), null, null, updatedBy);
        }
    }

    public void updateOrderShipmentGroupFromValue(OrderShipmentGroupDetailValue orderShipmentGroupDetailValue, BasePK updatedBy) {
        updateOrderShipmentGroupFromValue(orderShipmentGroupDetailValue, true, updatedBy);
    }

    public void deleteOrderShipmentGroup(OrderShipmentGroup orderShipmentGroup, BasePK deletedBy) {
        deleteOrderLinesByOrderShipmentGroup(orderShipmentGroup, deletedBy);

        OrderShipmentGroupDetail orderShipmentGroupDetail = orderShipmentGroup.getLastDetailForUpdate();
        orderShipmentGroupDetail.setThruTime(session.START_TIME_LONG);
        orderShipmentGroup.setActiveDetail(null);
        orderShipmentGroup.store();

        // Check for default, and pick one if necessary
        Order order = orderShipmentGroupDetail.getOrder();
        ItemDeliveryType itemDeliveryType = orderShipmentGroupDetail.getItemDeliveryType();
        OrderShipmentGroup defaultOrderShipmentGroup = getDefaultOrderShipmentGroup(order, itemDeliveryType);
        if(defaultOrderShipmentGroup == null) {
            List<OrderShipmentGroup> orderShipmentGroups = getOrderShipmentGroupsForUpdate(order, itemDeliveryType);

            if(!orderShipmentGroups.isEmpty()) {
                Iterator<OrderShipmentGroup> iter = orderShipmentGroups.iterator();
                if(iter.hasNext()) {
                    defaultOrderShipmentGroup = iter.next();
                }
                OrderShipmentGroupDetailValue orderShipmentGroupDetailValue = defaultOrderShipmentGroup.getLastDetailForUpdate().getOrderShipmentGroupDetailValue().clone();

                orderShipmentGroupDetailValue.setIsDefault(Boolean.TRUE);
                updateOrderShipmentGroupFromValue(orderShipmentGroupDetailValue, false, deletedBy);
            }
        }

        sendEventUsingNames(orderShipmentGroup.getPrimaryKey(), EventTypes.DELETE.name(), null, null, deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Order Lines
    // --------------------------------------------------------------------------------
    
    public OrderLine createOrderLine(Order order, Integer orderLineSequence, OrderLine parentOrderLine, OrderShipmentGroup orderShipmentGroup, Item item,
            InventoryCondition inventoryCondition, UnitOfMeasureType unitOfMeasureType, Long quantity, Long unitAmount, String description,
            CancellationPolicy cancellationPolicy, ReturnPolicy returnPolicy, Boolean taxable, BasePK createdBy) {
        OrderLine orderLine = OrderLineFactory.getInstance().create();
        OrderLineDetail orderLineDetail = OrderLineDetailFactory.getInstance().create(orderLine, order, orderLineSequence, parentOrderLine, orderShipmentGroup,
                item, inventoryCondition, unitOfMeasureType, quantity, unitAmount, description, cancellationPolicy, returnPolicy, taxable,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        orderLine = OrderLineFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                orderLine.getPrimaryKey());
        orderLine.setActiveDetail(orderLineDetail);
        orderLine.setLastDetail(orderLineDetail);
        orderLine.store();
        
        sendEventUsingNames(orderLine.getPrimaryKey(), EventTypes.CREATE.name(), null, null, createdBy);
        
        createOrderLineStatus(orderLine);

        return orderLine;
    }
    
    public long countOrderLinesByCancellationPolicy(CancellationPolicy cancellationPolicy) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM orderlines, orderlinedetails " +
                "WHERE ordl_activedetailid = ordldt_orderlinedetailid AND ordldt_cnclplcy_cancellationpolicyid = ?",
                cancellationPolicy);
    }

    public long countOrderLinesByReturnPolicy(ReturnPolicy returnPolicy) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM orderlines, orderlinedetails " +
                "WHERE ordl_activedetailid = ordldt_orderlinedetailid AND ordldt_rtnplcy_returnpolicyid = ?",
                returnPolicy);
    }

    public long countOrderLinesByInventoryCondition(InventoryCondition inventoryCondition) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM orderlines, orderlinedetails " +
                "WHERE ordl_activedetailid = ordldt_orderlinedetailid AND ordldt_invcon_inventoryconditionid = ?",
                inventoryCondition);
    }

    public long countOrderLinesByUnitOfMeasureType(UnitOfMeasureType unitOfMeasureType) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM orderlines, orderlinedetails " +
                "WHERE ordl_activedetailid = ordldt_orderlinedetailid AND ordldt_uomt_unitofmeasuretypeid = ?",
                unitOfMeasureType);
    }

    public long countOrderLinesByOrderAndCancellationPolicy(Order order, CancellationPolicy cancellationPolicy) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM orderlines, orderlinedetails " +
                "WHERE ordl_activedetailid = ordldt_orderlinedetailid AND ordldt_ord_orderid = ? AND ordldt_cnclplcy_cancellationpolicyid = ?",
                order, cancellationPolicy);
    }

    public long countOrderLinesByOrderAndReturnPolicy(Order order, ReturnPolicy returnPolicy) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM orderlines, orderlinedetails " +
                "WHERE ordl_activedetailid = ordldt_orderlinedetailid AND ordldt_ord_orderid = ? AND ordldt_rtnplcy_returnpolicyid = ?",
                order, returnPolicy);
    }

    public long countOrderLinesByOrderAndShipmentGroup(Order order, OrderShipmentGroup orderShipmentGroup) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM orderlines, orderlinedetails " +
                "WHERE ordl_activedetailid = ordldt_orderlinedetailid AND ordldt_ord_orderid = ? AND ordldt_ordshpgrp_ordershipmentgroupid = ?",
                order, orderShipmentGroup);
    }

    public OrderLine getOrderLineBySequence(Order order, Integer orderLineSequence, EntityPermission entityPermission) {
        OrderLine orderLine = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM orderlines, orderlinedetails " +
                        "WHERE ordl_activedetailid = ordldt_orderlinedetailid AND ordldt_ord_orderid = ? AND ordldt_orderlinesequence = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM orderlines, orderlinedetails " +
                        "WHERE ordl_activedetailid = ordldt_orderlinedetailid AND ordldt_ord_orderid = ? AND ordldt_orderlinesequence = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = OrderLineFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, order.getPrimaryKey().getEntityId());
            ps.setInt(2, orderLineSequence);
            
            orderLine = OrderLineFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return orderLine;
    }
    
    public OrderLine getOrderLineBySequence(Order order, Integer orderLineSequence) {
        return getOrderLineBySequence(order, orderLineSequence, EntityPermission.READ_ONLY);
    }
    
    public OrderLine getOrderLineBySequenceForUpdate(Order order, Integer orderLineSequence) {
        return getOrderLineBySequence(order, orderLineSequence, EntityPermission.READ_WRITE);
    }
    
    public OrderLineDetailValue getOrderLineDetailValueForUpdate(OrderLine orderLine) {
        return orderLine == null? null: orderLine.getLastDetailForUpdate().getOrderLineDetailValue().clone();
    }
    
    public OrderLineDetailValue getOrderLineDetailValueBySequenceForUpdate(Order order, Integer orderLineSequence) {
        return getOrderLineDetailValueForUpdate(getOrderLineBySequenceForUpdate(order, orderLineSequence));
    }
    
    private List<OrderLine> getOrderLinesByOrder(Order order, EntityPermission entityPermission) {
        List<OrderLine> orderLines = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM orderlines, orderlinedetails " +
                        "WHERE ordl_activedetailid = ordldt_orderlinedetailid AND ordldt_ord_orderid = ? " +
                        "ORDER BY ordldt_orderlinesequence";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM orderlines, orderlinedetails " +
                        "WHERE ordl_activedetailid = ordldt_orderlinedetailid AND ordldt_ord_orderid = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = OrderLineFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, order.getPrimaryKey().getEntityId());
            
            orderLines = OrderLineFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return orderLines;
    }
    
    public List<OrderLine> getOrderLinesByOrder(Order order) {
        return getOrderLinesByOrder(order, EntityPermission.READ_ONLY);
    }
    
    public List<OrderLine> getOrderLinesByOrderForUpdate(Order order) {
        return getOrderLinesByOrder(order, EntityPermission.READ_WRITE);
    }
    
    private List<OrderLine> getOrderLinesByOrderShipmentGroup(OrderShipmentGroup orderShipmentGroup, EntityPermission entityPermission) {
        List<OrderLine> orderLines = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM orderlines, orderlinedetails " +
                        "WHERE ordl_activedetailid = ordldt_orderlinedetailid AND ordldt_ordshpgrp_ordershipmentgroupid = ? " +
                        "ORDER BY ordldt_orderlinesequence";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM orderlines, orderlinedetails " +
                        "WHERE ordl_activedetailid = ordldt_orderlinedetailid AND ordldt_ordshpgrp_ordershipmentgroupid = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = OrderLineFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, orderShipmentGroup.getPrimaryKey().getEntityId());
            
            orderLines = OrderLineFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return orderLines;
    }
    
    public List<OrderLine> getOrderLinesByOrderShipmentGroup(OrderShipmentGroup orderShipmentGroup) {
        return getOrderLinesByOrderShipmentGroup(orderShipmentGroup, EntityPermission.READ_ONLY);
    }
    
    public List<OrderLine> getOrderLinesByOrderShipmentGroupForUpdate(OrderShipmentGroup orderShipmentGroup) {
        return getOrderLinesByOrderShipmentGroup(orderShipmentGroup, EntityPermission.READ_WRITE);
    }
    
    public void updateOrderLineFromValue(OrderLineDetailValue orderLineDetailValue, BasePK updatedBy) {
        if(orderLineDetailValue.hasBeenModified()) {
            OrderLine orderLine = OrderLineFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    orderLineDetailValue.getOrderLinePK());
            OrderLineDetail orderLineDetail = orderLine.getActiveDetailForUpdate();
            
            orderLineDetail.setThruTime(session.START_TIME_LONG);
            orderLineDetail.store();
            
            OrderLinePK orderLinePK = orderLineDetail.getOrderLinePK(); // Not updated
            OrderPK orderPK = orderLineDetail.getOrderPK(); // Not updated
            Integer orderLineSequence = orderLineDetail.getOrderLineSequence(); // Not updated
            OrderLinePK parentOrderLinePK = orderLineDetail.getParentOrderLinePK(); // Not updated
            OrderShipmentGroupPK orderShipmentGroupPK = orderLineDetailValue.getOrderShipmentGroupPK();
            ItemPK itemPK = orderLineDetailValue.getItemPK();
            InventoryConditionPK inventoryConditionPK = orderLineDetailValue.getInventoryConditionPK();
            UnitOfMeasureTypePK unitOfMeasureTypePK = orderLineDetailValue.getUnitOfMeasureTypePK();
            Long quantity = orderLineDetailValue.getQuantity();
            Long unitAmount = orderLineDetailValue.getUnitAmount();
            String description = orderLineDetailValue.getDescription();
            CancellationPolicyPK cancellationPolicyPK = orderLineDetailValue.getCancellationPolicyPK();
            ReturnPolicyPK returnPolicyPK = orderLineDetailValue.getReturnPolicyPK();
            Boolean taxable = orderLineDetailValue.getTaxable();
            
            orderLineDetail = OrderLineDetailFactory.getInstance().create(orderLinePK, orderPK, orderLineSequence, parentOrderLinePK, orderShipmentGroupPK,
                    itemPK, inventoryConditionPK, unitOfMeasureTypePK, quantity, unitAmount, description, cancellationPolicyPK, returnPolicyPK, taxable,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            orderLine.setActiveDetail(orderLineDetail);
            orderLine.setLastDetail(orderLineDetail);
            
            sendEventUsingNames(orderLinePK, EventTypes.MODIFY.name(), null, null, updatedBy);
        }
    }
    
    public void deleteOrderLine(OrderLine orderLine, BasePK deletedBy) {
        OrderLineDetail orderLineDetail = orderLine.getLastDetailForUpdate();

        removeOrderLineStatusByOrderLine(orderLine);
        deleteOrderLineAdjustmentsByOrderLine(orderLine, deletedBy);
        
        orderLineDetail.setThruTime(session.START_TIME_LONG);
        orderLine.setActiveDetail(null);
        orderLine.store();
        
        sendEventUsingNames(orderLine.getPrimaryKey(), EventTypes.DELETE.name(), null, null, deletedBy);
    }
    
    public void deleteOrderLinesByOrder(List<OrderLine> orderLines, BasePK deletedBy) {
        orderLines.stream().forEach((orderLine) -> {
            deleteOrderLine(orderLine, deletedBy);
        });
    }
    
    public void deleteOrderLinesByOrder(Order order, BasePK deletedBy) {
        deleteOrderLinesByOrder(getOrderLinesByOrderForUpdate(order), deletedBy);
    }
    
    public void deleteOrderLinesByOrderShipmentGroup(OrderShipmentGroup orderShipmentGroup, BasePK deletedBy) {
        deleteOrderLinesByOrder(getOrderLinesByOrderShipmentGroupForUpdate(orderShipmentGroup), deletedBy);
    }
    
    public void deleteOrderLinesByWishlistTypePriority(WishlistTypePriority wishlistTypePriority, BasePK deletedBy) {
        var wishlistControl = (WishlistControl)Session.getModelController(WishlistControl.class);
        List<WishlistLine> wishlistLines = wishlistControl.getWishlistLinesByWishlistTypePriorityForUpdate(wishlistTypePriority);
        
        wishlistLines.stream().forEach((wishlistLine) -> {
            deleteOrderLine(wishlistLine.getOrderLineForUpdate(), deletedBy);
        });
    }
    
    // --------------------------------------------------------------------------------
    //   Order Line Statuses
    // --------------------------------------------------------------------------------
    
    public OrderLineStatus createOrderLineStatus(OrderLine orderLine) {
        return OrderLineStatusFactory.getInstance().create(orderLine, 0);
    }
    
    private OrderLineStatus getOrderLineStatus(OrderLine orderLine, EntityPermission entityPermission) {
        OrderLineStatus orderLineStatus = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM orderlinestatuses " +
                        "WHERE ordlst_ordl_orderlineid = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM orderlinestatuses " +
                        "WHERE ordlst_ordl_orderlineid = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = OrderLineStatusFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, orderLine.getPrimaryKey().getEntityId());
            
            orderLineStatus = OrderLineStatusFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return orderLineStatus;
    }
    
    public OrderLineStatus getOrderLineStatus(OrderLine orderLine) {
        return getOrderLineStatus(orderLine, EntityPermission.READ_ONLY);
    }
    
    public OrderLineStatus getOrderLineStatusForUpdate(OrderLine orderLine) {
        return getOrderLineStatus(orderLine, EntityPermission.READ_WRITE);
    }
    
    public void removeOrderLineStatusByOrderLine(OrderLine orderLine) {
        OrderLineStatus orderLineStatus = getOrderLineStatusForUpdate(orderLine);
        
        if(orderLineStatus != null) {
            orderLineStatus.remove();
        }
    }
    
    // --------------------------------------------------------------------------------
    //   Order Line Times
    // --------------------------------------------------------------------------------

    public OrderLineTime createOrderLineTime(OrderLine orderLine, OrderTimeType orderTimeType, Long time, BasePK createdBy) {
        OrderLineTime orderLineTime = OrderLineTimeFactory.getInstance().create(orderLine, orderTimeType, time, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEventUsingNames(orderLine.getPrimaryKey(), EventTypes.MODIFY.name(), orderLineTime.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);

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
        return getOrderTransferCaches(userVisit).getOrderLineTimeTransferCache().getOrderLineTimeTransfer(orderLineTime);
    }

    public List<OrderLineTimeTransfer> getOrderLineTimeTransfers(UserVisit userVisit, List<OrderLineTime> orderLineTimes) {
        List<OrderLineTimeTransfer> orderLineTimeTransfers = new ArrayList<>(orderLineTimes.size());
        OrderLineTimeTransferCache orderLineTimeTransferCache = getOrderTransferCaches(userVisit).getOrderLineTimeTransferCache();

        orderLineTimes.stream().forEach((orderLineTime) -> {
            orderLineTimeTransfers.add(orderLineTimeTransferCache.getOrderLineTimeTransfer(orderLineTime));
        });

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
            OrderLineTime orderLineTime = OrderLineTimeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    orderLineTimeValue.getPrimaryKey());

            orderLineTime.setThruTime(session.START_TIME_LONG);
            orderLineTime.store();

            OrderLinePK orderLinePK = orderLineTime.getOrderLinePK(); // Not updated
            OrderTimeTypePK orderTimeTypePK = orderLineTime.getOrderTimeTypePK(); // Not updated
            Long time = orderLineTimeValue.getTime();

            orderLineTime = OrderLineTimeFactory.getInstance().create(orderLinePK, orderTimeTypePK, time, session.START_TIME_LONG, Session.MAX_TIME_LONG);

            sendEventUsingNames(orderLinePK, EventTypes.MODIFY.name(), orderLineTime.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }

    public void deleteOrderLineTime(OrderLineTime orderLineTime, BasePK deletedBy) {
        orderLineTime.setThruTime(session.START_TIME_LONG);

        sendEventUsingNames(orderLineTime.getOrderTimeTypePK(), EventTypes.MODIFY.name(), orderLineTime.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);

    }

    public void deleteOrderLineTimes(List<OrderLineTime> orderLineTimes, BasePK deletedBy) {
        orderLineTimes.stream().forEach((orderLineTime) -> {
            deleteOrderLineTime(orderLineTime, deletedBy);
        });
    }

    public void deleteOrderLineTimesByOrder(OrderLine orderLine, BasePK deletedBy) {
        deleteOrderLineTimes(getOrderLineTimesByOrderForUpdate(orderLine), deletedBy);
    }

    public void deleteOrderLineTimesByOrderTimeType(OrderTimeType orderTimeType, BasePK deletedBy) {
        deleteOrderLineTimes(getOrderLineTimesByOrderTimeTypeForUpdate(orderTimeType), deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Order Line Adjustments
    // --------------------------------------------------------------------------------
    
    public OrderLineAdjustment createOrderLineAdjustment(OrderLine orderLine, Integer orderLineAdjustmentSequence,
            OrderLineAdjustmentType orderLineAdjustmentType, Long amount, BasePK createdBy) {
        OrderLineAdjustment orderLineAdjustment = OrderLineAdjustmentFactory.getInstance().create();
        OrderLineAdjustmentDetail orderLineAdjustmentDetail = OrderLineAdjustmentDetailFactory.getInstance().create(session,
                orderLineAdjustment, orderLine, orderLineAdjustmentSequence, orderLineAdjustmentType, amount,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        orderLineAdjustment = OrderLineAdjustmentFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                orderLineAdjustment.getPrimaryKey());
        orderLineAdjustment.setActiveDetail(orderLineAdjustmentDetail);
        orderLineAdjustment.setLastDetail(orderLineAdjustmentDetail);
        orderLineAdjustment.store();
        
        sendEventUsingNames(orderLineAdjustment.getPrimaryKey(), EventTypes.CREATE.name(), null, null, createdBy);
        
        return orderLineAdjustment;
    }
    
    private List<OrderLineAdjustment> getOrderLineAdjustmentsByOrderLine(OrderLine orderLine, EntityPermission entityPermission) {
        List<OrderLineAdjustment> orderLineAdjustments = null;
        
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
            
            PreparedStatement ps = OrderLineAdjustmentFactory.getInstance().prepareStatement(query);
            
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
        OrderLineAdjustmentDetail orderLineAdjustmentDetail = orderLineAdjustment.getLastDetailForUpdate();
        orderLineAdjustmentDetail.setThruTime(session.START_TIME_LONG);
        orderLineAdjustment.setActiveDetail(null);
        orderLineAdjustment.store();
        
        sendEventUsingNames(orderLineAdjustmentDetail.getOrderLinePK(), EventTypes.DELETE.name(), null, null, deletedBy);
    }
    
    public void deleteOrderLineAdjustmentsByOrderLine(OrderLine orderLine, BasePK deletedBy) {
        List<OrderLineAdjustment> orderLineAdjustments = getOrderLineAdjustmentsByOrderLineForUpdate(orderLine);
        
        orderLineAdjustments.stream().forEach((orderLineAdjustment) -> {
            deleteOrderLineAdjustment(orderLineAdjustment, deletedBy);
        });
    }
    
    // --------------------------------------------------------------------------------
    //   Order Shipment Groups
    // --------------------------------------------------------------------------------
    
    public void deleteOrderShipmentGroupsByPartyContactMechanism(PartyContactMechanism partyContactMechanism, BasePK deletedBy) {
        // TODO
    }
    
}
