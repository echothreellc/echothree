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
import com.echothree.model.control.order.common.transfer.OrderRoleTransfer;
import com.echothree.model.control.order.common.transfer.OrderRoleTypeTransfer;
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
import com.echothree.util.common.exception.PersistenceDatabaseException;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class OrderRoleControl
        extends BaseOrderControl {

    /** Creates a new instance of OrderControl */
    protected OrderRoleControl() {
        super();
    }
    
    // --------------------------------------------------------------------------------
    //   Order Role Types
    // --------------------------------------------------------------------------------
    
    public OrderRoleType createOrderRoleType(String orderRoleTypeName, Integer sortOrder) {
        return OrderRoleTypeFactory.getInstance().create(orderRoleTypeName, sortOrder);
    }
    
    public OrderRoleType getOrderRoleTypeByName(String orderRoleTypeName) {
        OrderRoleType orderRoleType;
        
        try {
            var ps = OrderRoleTypeFactory.getInstance().prepareStatement(
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
        OrderRoleTypeDescription orderRoleTypeDescription;
        
        try {
            var ps = OrderRoleTypeDescriptionFactory.getInstance().prepareStatement(
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
        var orderRoleTypeDescription = getOrderRoleTypeDescription(orderRoleType, language);

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
    //   Order Roles
    // --------------------------------------------------------------------------------

    public OrderRole createOrderRoleUsingNames(Order order, Party party, String orderRoleTypeName, BasePK createdBy) {
        return createOrderRole(order, party, getOrderRoleTypeByName(orderRoleTypeName), createdBy);
    }

    public OrderRole createOrderRole(Order order, Party party, OrderRoleType orderRoleType, BasePK createdBy) {
        var orderRole = OrderRoleFactory.getInstance().create(order, party, orderRoleType, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEvent(order.getPrimaryKey(), EventTypes.MODIFY, orderRole.getPrimaryKey(), EventTypes.CREATE, createdBy);

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
        orderRole.setThruTime(session.START_TIME_LONG);

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
