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
import com.echothree.model.control.order.common.transfer.OrderRoleTransfer;
import com.echothree.model.control.order.common.transfer.OrderRoleTypeTransfer;
import com.echothree.model.control.order.server.transfer.OrderRoleTransferCache;
import com.echothree.model.control.order.server.transfer.OrderRoleTypeTransferCache;
import com.echothree.model.data.order.server.entity.Order;
import com.echothree.model.data.order.server.entity.OrderRole;
import com.echothree.model.data.order.server.entity.OrderRoleType;
import com.echothree.model.data.order.server.entity.OrderRoleTypeDescription;
import com.echothree.model.data.order.server.factory.OrderRoleFactory;
import com.echothree.model.data.order.server.factory.OrderRoleTypeDescriptionFactory;
import com.echothree.model.data.order.server.factory.OrderRoleTypeFactory;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.user.server.entity.UserVisit;
import static com.echothree.model.jooq.server.keys.order.OrderForeignKeys.ORDER_ROLES_ORDER_ROLE_TYPE_FK;
import static com.echothree.model.jooq.server.keys.order.OrderForeignKeys.ORDER_ROLES_PARTY_FK;
import static com.echothree.model.jooq.server.keys.party.PartyForeignKeys.PARTIES_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.tables.order.OrderRoleTypeDescriptions.OrderRoleTypeDescriptions;
import static com.echothree.model.jooq.server.tables.order.OrderRoleTypes.OrderRoleTypes;
import static com.echothree.model.jooq.server.tables.order.OrderRoles.OrderRoles;
import static com.echothree.model.jooq.server.tables.party.Parties.Parties;
import static com.echothree.model.jooq.server.tables.party.PartyDetails.PartyDetails;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.cdi.CommandScope;
import com.echothree.util.server.control.BaseModelControl;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.inject.Inject;

@CommandScope
public class OrderRoleControl
        extends BaseModelControl {

    /** Creates a new instance of OrderControl */
    protected OrderRoleControl() {
        super();
    }
    
    // --------------------------------------------------------------------------------
    //   Order Role Types
    // --------------------------------------------------------------------------------

    @Inject
    private OrderRoleTypeFactory orderRoleTypeFactory;

    @Inject
    private OrderRoleTypeTransferCache orderRoleTypeTransferCache;

    public OrderRoleType createOrderRoleType(String orderRoleTypeName, Integer sortOrder) {
        return orderRoleTypeFactory.create(orderRoleTypeName, sortOrder);
    }
    
    public OrderRoleType getOrderRoleTypeByName(String orderRoleTypeName) {
        var query = session.getDslContext()
                .select(OrderRoleTypes.fields())
                .from(OrderRoleTypes)
                .where(OrderRoleTypes.ORDER_ROLE_TYPE_NAME.eq(orderRoleTypeName));

        return orderRoleTypeFactory.getEntityFromQuery(EntityPermission.READ_ONLY, query);
    }
    
    public OrderRoleTypeTransfer getOrderRoleTypeTransfer(UserVisit userVisit, OrderRoleType orderRoleType) {
        return orderRoleTypeTransferCache.getOrderRoleTypeTransfer(userVisit, orderRoleType);
    }

    // --------------------------------------------------------------------------------
    //   Order Role Type Descriptions
    // --------------------------------------------------------------------------------

    @Inject
    private OrderRoleTypeDescriptionFactory orderRoleTypeDescriptionFactory;

    public OrderRoleTypeDescription createOrderRoleTypeDescription(OrderRoleType orderRoleType, Language language,
            String description) {
        return orderRoleTypeDescriptionFactory.create(orderRoleType, language, description);
    }
    
    public OrderRoleTypeDescription getOrderRoleTypeDescription(OrderRoleType orderRoleType, Language language) {
        var query = session.getDslContext()
                .select(OrderRoleTypeDescriptions.fields())
                .from(OrderRoleTypeDescriptions)
                .where(OrderRoleTypeDescriptions.ORDER_ROLE_TYPE.eq(orderRoleType.getPrimaryKey()),
                        OrderRoleTypeDescriptions.LANGUAGE.eq(language.getPrimaryKey()));

        return orderRoleTypeDescriptionFactory.getEntityFromQuery(EntityPermission.READ_ONLY, query);
    }
    
    public String getBestOrderRoleTypeDescription(OrderRoleType orderRoleType, Language language) {
        String description;
        var orderRoleTypeDescription = getOrderRoleTypeDescription(orderRoleType, language);

        if(orderRoleTypeDescription == null && !language.getIsDefault()) {
            orderRoleTypeDescription = getOrderRoleTypeDescription(orderRoleType, partyControl.getDefaultLanguage());
        }

        if(orderRoleTypeDescription == null) {
            description = orderRoleType.getOrderRoleTypeName();
        } else {
            description = orderRoleTypeDescription.getDescription();
        }

        return description;
    }

    // --------------------------------------------------------------------------------
    //   Order Roles
    // --------------------------------------------------------------------------------

    @Inject
    private OrderRoleFactory orderRoleFactory;

    @Inject
    private OrderRoleTransferCache orderRoleTransferCache;

    public OrderRole createOrderRoleUsingNames(Order order, Party party, String orderRoleTypeName, BasePK createdBy) {
        return createOrderRole(order, party, getOrderRoleTypeByName(orderRoleTypeName), createdBy);
    }

    public OrderRole createOrderRole(Order order, Party party, OrderRoleType orderRoleType, BasePK createdBy) {
        var orderRole = orderRoleFactory.create(order, party, orderRoleType, session.getStartTime(), Session.MAX_TIME);

        sendEvent(order.getPrimaryKey(), EventTypes.MODIFY, orderRole.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return orderRole;
    }

    public long countOrderRolesByOrderAndOrderRoleType(Order order, OrderRoleType orderRoleType) {
        return session.getDslContext()
                .selectCount()
                .from(OrderRoles)
                .where(OrderRoles.ORDER.eq(order.getPrimaryKey()),
                        OrderRoles.ORDER_ROLE_TYPE.eq(orderRoleType.getPrimaryKey()),
                        OrderRoles.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private List<OrderRole> getOrderRolesByOrderAndOrderRoleType(Order order, OrderRoleType orderRoleType, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(OrderRoles.fields())
                    .from(OrderRoles)
                    .join(OrderRoleTypes).onKey(ORDER_ROLES_ORDER_ROLE_TYPE_FK)
                    .join(Parties).onKey(ORDER_ROLES_PARTY_FK)
                    .join(PartyDetails).onKey(PARTIES_LAST_DETAIL_FK)
                    .where(OrderRoles.ORDER.eq(order.getPrimaryKey()),
                            OrderRoles.ORDER_ROLE_TYPE.eq(orderRoleType.getPrimaryKey()),
                            OrderRoles.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(OrderRoleTypes.SORT_ORDER, PartyDetails.PARTY_NAME),
                    OrderRoleFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(OrderRoles.fields())
                    .from(OrderRoles)
                    .where(OrderRoles.ORDER.eq(order.getPrimaryKey()),
                            OrderRoles.ORDER_ROLE_TYPE.eq(orderRoleType.getPrimaryKey()),
                            OrderRoles.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return orderRoleFactory.getEntitiesFromQuery(entityPermission, query);
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
            var orderRoles = getOrderRolesByOrderAndOrderRoleType(order, orderRoleType, entityPermission);
            var size = orderRoles.size();

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

    private List<OrderRole> getOrderRolesByOrder(Order order, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(OrderRoles.fields())
                    .from(OrderRoles)
                    .join(OrderRoleTypes).onKey(ORDER_ROLES_ORDER_ROLE_TYPE_FK)
                    .join(Parties).onKey(ORDER_ROLES_PARTY_FK)
                    .join(PartyDetails).onKey(PARTIES_LAST_DETAIL_FK)
                    .where(OrderRoles.ORDER.eq(order.getPrimaryKey()),
                            OrderRoles.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(OrderRoleTypes.SORT_ORDER, PartyDetails.PARTY_NAME),
                    OrderRoleFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(OrderRoles.fields())
                    .from(OrderRoles)
                    .where(OrderRoles.ORDER.eq(order.getPrimaryKey()),
                            OrderRoles.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return orderRoleFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<OrderRole> getOrderRolesByOrder(Order order) {
        return getOrderRolesByOrder(order, EntityPermission.READ_ONLY);
    }

    public List<OrderRole> getOrderRolesByOrderForUpdate(Order order) {
        return getOrderRolesByOrder(order, EntityPermission.READ_WRITE);
    }

    public OrderRoleTransfer getOrderRoleTransfer(UserVisit userVisit, OrderRole orderRole) {
        return orderRoleTransferCache.getOrderRoleTransfer(userVisit, orderRole);
    }

    public List<OrderRoleTransfer> getOrderRoleTransfers(UserVisit userVisit, Collection<OrderRole> orderRoles) {
        List<OrderRoleTransfer> orderRoleTransfers = new ArrayList<>(orderRoles.size());

        orderRoles.forEach((orderRole) -> {
            orderRoleTransfers.add(getOrderRoleTransfer(userVisit, orderRole));
        });

        return orderRoleTransfers;
    }

    public List<OrderRoleTransfer> getOrderRoleTransfersByOrder(UserVisit userVisit, Order order) {
        return getOrderRoleTransfers(userVisit, getOrderRolesByOrder(order));
    }

    public void deleteOrderRole(OrderRole orderRole, BasePK deletedBy) {
        orderRole.setThruTime(session.getStartTime());

        sendEvent(orderRole.getOrderPK(), EventTypes.MODIFY, orderRole.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }

    public void deleteOrderRoles(List<OrderRole> orderRoles, BasePK deletedBy) {
        orderRoles.forEach((orderRole) -> 
                deleteOrderRole(orderRole, deletedBy)
        );
    }

    public void deleteOrderRolesByOrder(Order order, BasePK deletedBy) {
        deleteOrderRoles(getOrderRolesByOrderForUpdate(order), deletedBy);
    }

}
