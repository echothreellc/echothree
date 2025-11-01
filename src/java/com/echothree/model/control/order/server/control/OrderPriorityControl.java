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
import com.echothree.model.control.order.common.choice.OrderPriorityChoicesBean;
import com.echothree.model.control.order.common.transfer.OrderPriorityDescriptionTransfer;
import com.echothree.model.control.order.common.transfer.OrderPriorityTransfer;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.order.common.pk.OrderPriorityPK;
import com.echothree.model.data.order.server.entity.OrderPriority;
import com.echothree.model.data.order.server.entity.OrderPriorityDescription;
import com.echothree.model.data.order.server.entity.OrderType;
import com.echothree.model.data.order.server.factory.OrderPriorityDescriptionFactory;
import com.echothree.model.data.order.server.factory.OrderPriorityDetailFactory;
import com.echothree.model.data.order.server.factory.OrderPriorityFactory;
import com.echothree.model.data.order.server.value.OrderPriorityDescriptionValue;
import com.echothree.model.data.order.server.value.OrderPriorityDetailValue;
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
public class OrderPriorityControl
        extends BaseOrderControl {

    /** Creates a new instance of OrderControl */
    protected OrderPriorityControl() {
        super();
    }

    // --------------------------------------------------------------------------------
    //   Order Priorities
    // --------------------------------------------------------------------------------

    public OrderPriority createOrderPriority(OrderType orderType, String orderPriorityName, Integer priority, Boolean isDefault, Integer sortOrder,
            BasePK createdBy) {
        var defaultOrderPriority = getDefaultOrderPriority(orderType);
        var defaultFound = defaultOrderPriority != null;

        if(defaultFound && isDefault) {
            var defaultOrderPriorityDetailValue = getDefaultOrderPriorityDetailValueForUpdate(orderType);

            defaultOrderPriorityDetailValue.setIsDefault(false);
            updateOrderPriorityFromValue(defaultOrderPriorityDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var orderPriority = OrderPriorityFactory.getInstance().create();
        var orderPriorityDetail = OrderPriorityDetailFactory.getInstance().create(orderPriority, orderType, orderPriorityName, priority,
                isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        // Convert to R/W
        orderPriority = OrderPriorityFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                orderPriority.getPrimaryKey());
        orderPriority.setActiveDetail(orderPriorityDetail);
        orderPriority.setLastDetail(orderPriorityDetail);
        orderPriority.store();

        sendEvent(orderPriority.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return orderPriority;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.OrderPriority */
    public OrderPriority getOrderPriorityByEntityInstance(final EntityInstance entityInstance,
            final EntityPermission entityPermission) {
        var pk = new OrderPriorityPK(entityInstance.getEntityUniqueId());

        return OrderPriorityFactory.getInstance().getEntityFromPK(entityPermission, pk);
    }

    public OrderPriority getOrderPriorityByEntityInstance(final EntityInstance entityInstance) {
        return getOrderPriorityByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public OrderPriority getOrderPriorityByEntityInstanceForUpdate(final EntityInstance entityInstance) {
        return getOrderPriorityByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public OrderPriority getOrderPriorityByPK(OrderPriorityPK pk) {
        return OrderPriorityFactory.getInstance().getEntityFromPK(EntityPermission.READ_ONLY, pk);
    }

    public long countOrderPriorities(OrderType orderType) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM orderpriorities, orderprioritydetails " +
                "WHERE ordpr_activedetailid = ordprdt_orderprioritydetailid " +
                "AND ordprdt_ordtyp_ordertypeid = ?",
                orderType);
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

    public OrderPriority getOrderPriorityByName(OrderType orderType, String orderPriorityName, EntityPermission entityPermission) {
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

    public OrderPriority getDefaultOrderPriority(OrderType orderType, EntityPermission entityPermission) {
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

    public List<OrderPriorityTransfer> getOrderPriorityTransfers(UserVisit userVisit, Collection<OrderPriority> orderPriorities) {
        List<OrderPriorityTransfer> orderPriorityTransfers = new ArrayList<>(orderPriorities.size());
        var orderPriorityTransferCache = getOrderTransferCaches(userVisit).getOrderPriorityTransferCache();

        orderPriorities.forEach((orderPriority) ->
                orderPriorityTransfers.add(orderPriorityTransferCache.getOrderPriorityTransfer(orderPriority))
        );

        return orderPriorityTransfers;
    }

    public List<OrderPriorityTransfer> getOrderPriorityTransfers(UserVisit userVisit, OrderType orderType) {
        return getOrderPriorityTransfers(userVisit, getOrderPriorities(orderType));
    }

    public OrderPriorityChoicesBean getOrderPriorityChoices(String defaultOrderPriorityChoice, Language language, boolean allowNullChoice,
            OrderType orderType) {
        var orderPriorities = getOrderPriorities(orderType);
        var size = orderPriorities.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultOrderPriorityChoice == null) {
                defaultValue = "";
            }
        }

        for(var orderPriority : orderPriorities) {
            var orderPriorityDetail = orderPriority.getLastDetail();

            var label = getBestOrderPriorityDescription(orderPriority, language);
            var value = orderPriorityDetail.getOrderPriorityName();

            labels.add(label == null? value: label);
            values.add(value);

            var usingDefaultChoice = defaultOrderPriorityChoice != null && defaultOrderPriorityChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && orderPriorityDetail.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new OrderPriorityChoicesBean(labels, values, defaultValue);
    }

    private void updateOrderPriorityFromValue(OrderPriorityDetailValue orderPriorityDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(orderPriorityDetailValue.hasBeenModified()) {
            var orderPriority = OrderPriorityFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     orderPriorityDetailValue.getOrderPriorityPK());
            var orderPriorityDetail = orderPriority.getActiveDetailForUpdate();

            orderPriorityDetail.setThruTime(session.START_TIME_LONG);
            orderPriorityDetail.store();

            var orderType = orderPriorityDetail.getOrderType(); // Not updated
            var orderTypePK = orderType.getPrimaryKey(); // Not updated
            var orderPriorityPK = orderPriorityDetail.getOrderPriorityPK(); // Not updated
            var orderPriorityName = orderPriorityDetailValue.getOrderPriorityName();
            var priority = orderPriorityDetailValue.getPriority();
            var isDefault = orderPriorityDetailValue.getIsDefault();
            var sortOrder = orderPriorityDetailValue.getSortOrder();

            if(checkDefault) {
                var defaultOrderPriority = getDefaultOrderPriority(orderType);
                var defaultFound = defaultOrderPriority != null && !defaultOrderPriority.equals(orderPriority);

                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultOrderPriorityDetailValue = getDefaultOrderPriorityDetailValueForUpdate(orderType);

                    defaultOrderPriorityDetailValue.setIsDefault(false);
                    updateOrderPriorityFromValue(defaultOrderPriorityDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }

            orderPriorityDetail = OrderPriorityDetailFactory.getInstance().create(orderPriorityPK, orderTypePK, orderPriorityName, priority, isDefault,
                    sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);

            orderPriority.setActiveDetail(orderPriorityDetail);
            orderPriority.setLastDetail(orderPriorityDetail);

            sendEvent(orderPriorityPK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }

    public void updateOrderPriorityFromValue(OrderPriorityDetailValue orderPriorityDetailValue, BasePK updatedBy) {
        updateOrderPriorityFromValue(orderPriorityDetailValue, true, updatedBy);
    }

    public void deleteOrderPriority(OrderPriority orderPriority, BasePK deletedBy) {
        deleteOrderPriorityDescriptionsByOrderPriority(orderPriority, deletedBy);

        var orderPriorityDetail = orderPriority.getLastDetailForUpdate();
        orderPriorityDetail.setThruTime(session.START_TIME_LONG);
        orderPriority.setActiveDetail(null);
        orderPriority.store();

        // Check for default, and pick one if necessary
        var orderType = orderPriorityDetail.getOrderType();
        var defaultOrderPriority = getDefaultOrderPriority(orderType);
        if(defaultOrderPriority == null) {
            var orderPriorities = getOrderPrioritiesForUpdate(orderType);

            if(!orderPriorities.isEmpty()) {
                var iter = orderPriorities.iterator();
                if(iter.hasNext()) {
                    defaultOrderPriority = iter.next();
                }
                var orderPriorityDetailValue = Objects.requireNonNull(defaultOrderPriority).getLastDetailForUpdate().getOrderPriorityDetailValue().clone();

                orderPriorityDetailValue.setIsDefault(true);
                updateOrderPriorityFromValue(orderPriorityDetailValue, false, deletedBy);
            }
        }

        sendEvent(orderPriority.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Order Priority Descriptions
    // --------------------------------------------------------------------------------

    public OrderPriorityDescription createOrderPriorityDescription(OrderPriority orderPriority, Language language, String description, BasePK createdBy) {
        var orderPriorityDescription = OrderPriorityDescriptionFactory.getInstance().create(orderPriority, language, description,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEvent(orderPriority.getPrimaryKey(), EventTypes.MODIFY, orderPriorityDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);

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
        var orderPriorityDescription = getOrderPriorityDescription(orderPriority, language);

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
        var orderPriorityDescriptions = getOrderPriorityDescriptionsByOrderPriority(orderPriority);
        List<OrderPriorityDescriptionTransfer> orderPriorityDescriptionTransfers = new ArrayList<>(orderPriorityDescriptions.size());
        var orderPriorityDescriptionTransferCache = getOrderTransferCaches(userVisit).getOrderPriorityDescriptionTransferCache();

        orderPriorityDescriptions.forEach((orderPriorityDescription) ->
                orderPriorityDescriptionTransfers.add(orderPriorityDescriptionTransferCache.getOrderPriorityDescriptionTransfer(orderPriorityDescription))
        );

        return orderPriorityDescriptionTransfers;
    }

    public void updateOrderPriorityDescriptionFromValue(OrderPriorityDescriptionValue orderPriorityDescriptionValue, BasePK updatedBy) {
        if(orderPriorityDescriptionValue.hasBeenModified()) {
            var orderPriorityDescription = OrderPriorityDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    orderPriorityDescriptionValue.getPrimaryKey());

            orderPriorityDescription.setThruTime(session.START_TIME_LONG);
            orderPriorityDescription.store();

            var orderPriority = orderPriorityDescription.getOrderPriority();
            var language = orderPriorityDescription.getLanguage();
            var description = orderPriorityDescriptionValue.getDescription();

            orderPriorityDescription = OrderPriorityDescriptionFactory.getInstance().create(orderPriority, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);

            sendEvent(orderPriority.getPrimaryKey(), EventTypes.MODIFY, orderPriorityDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteOrderPriorityDescription(OrderPriorityDescription orderPriorityDescription, BasePK deletedBy) {
        orderPriorityDescription.setThruTime(session.START_TIME_LONG);

        sendEvent(orderPriorityDescription.getOrderPriorityPK(), EventTypes.MODIFY, orderPriorityDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);

    }

    public void deleteOrderPriorityDescriptionsByOrderPriority(OrderPriority orderPriority, BasePK deletedBy) {
        var orderPriorityDescriptions = getOrderPriorityDescriptionsByOrderPriorityForUpdate(orderPriority);

        orderPriorityDescriptions.forEach((orderPriorityDescription) -> 
                deleteOrderPriorityDescription(orderPriorityDescription, deletedBy)
        );
    }

}
