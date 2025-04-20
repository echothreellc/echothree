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

package com.echothree.model.control.order.server.search;

import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.server.control.EntityInstanceControl;
import com.echothree.model.control.order.server.control.OrderAliasControl;
import com.echothree.model.control.order.server.control.OrderControl;
import com.echothree.model.control.search.server.search.BaseSearchEvaluator;
import com.echothree.model.control.search.server.search.EntityInstancePKHolder;
import com.echothree.model.data.accounting.server.entity.Currency;
import com.echothree.model.data.cancellationpolicy.server.entity.CancellationPolicy;
import com.echothree.model.data.core.server.factory.EntityInstanceFactory;
import com.echothree.model.data.order.server.entity.Order;
import com.echothree.model.data.order.server.entity.OrderAliasType;
import com.echothree.model.data.order.server.entity.OrderPriority;
import com.echothree.model.data.order.server.entity.OrderType;
import com.echothree.model.data.returnpolicy.server.entity.ReturnPolicy;
import com.echothree.model.data.search.server.entity.SearchDefaultOperator;
import com.echothree.model.data.search.server.entity.SearchSortDirection;
import com.echothree.model.data.search.server.entity.SearchSortOrder;
import com.echothree.model.data.search.server.entity.SearchType;
import com.echothree.model.data.term.server.entity.Term;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.model.data.workflow.server.entity.WorkflowStep;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.Session;

public class OrderSearchEvaluator
        extends BaseSearchEvaluator {
    
    protected OrderType orderType;
    private WorkflowStep orderStatusWorkflowStep;
    private String orderName;
    private OrderAliasType orderAliasType;
    private String alias;
    private OrderPriority orderPriority;
    private Currency currency;
    private Term term;
    private String reference;
    private CancellationPolicy cancellationPolicy;
    private ReturnPolicy returnPolicy;
    
    protected OrderSearchEvaluator(UserVisit userVisit, SearchType searchType, SearchDefaultOperator searchDefaultOperator, SearchSortOrder searchSortOrder,
            SearchSortDirection searchSortDirection, OrderType orderType) {
        super(userVisit, searchDefaultOperator, searchType, searchSortOrder, searchSortDirection, null, ComponentVendors.ECHO_THREE.name(),
                EntityTypes.Order.name(), null, null, null);
        
        this.orderType = orderType;
    }
    
    public WorkflowStep getOrderStatusWorkflowStep() {
        return orderStatusWorkflowStep;
    }

    public void setOrderStatusWorkflowStep(WorkflowStep orderStatusWorkflowStep) {
        this.orderStatusWorkflowStep = orderStatusWorkflowStep;
    }
    
    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public OrderAliasType getOrderAliasType() {
        return orderAliasType;
    }

    public void setOrderAliasType(OrderAliasType orderAliasType) {
        this.orderAliasType = orderAliasType;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }
    
    public OrderPriority getOrderPriority() {
        return orderPriority;
    }

    public void setOrderPriority(OrderPriority orderPriority) {
        this.orderPriority = orderPriority;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Term getTerm() {
        return term;
    }

    public void setTerm(Term term) {
        this.term = term;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public CancellationPolicy getCancellationPolicy() {
        return cancellationPolicy;
    }

    public void setCancellationPolicy(CancellationPolicy cancellationPolicy) {
        this.cancellationPolicy = cancellationPolicy;
    }

    public ReturnPolicy getReturnPolicy() {
        return returnPolicy;
    }

    public void setReturnPolicy(ReturnPolicy returnPolicy) {
        this.returnPolicy = returnPolicy;
    }

    /** Counts the number of search parameters, not including orderType. */
    @Override
    protected int countParameters() {
        return super.countParameters() + (orderStatusWorkflowStep == null ? 0 : 1) + (orderName == null ? 0 : 1) + (orderAliasType == null ? 0 : 1)
                + (alias == null ? 0 : 1) + (orderPriority == null ? 0 : 1) + (currency == null ? 0 : 1) + (term == null ? 0 : 1) + (reference == null ? 0 : 1)
                + (cancellationPolicy == null ? 0 : 1) + (returnPolicy == null ? 0 : 1);
    }

    public EntityInstancePKHolder getEntityInstancePKHolderByOrderType(OrderType orderType) {
        return getEntityInstancePKHolderFromQuery(EntityInstanceFactory.getInstance().prepareStatement(
                "SELECT _PK_ "
                + "FROM entityinstances, orderes, orderdetails "
                + "WHERE ord_activedetailid = orddt_orderdetailid "
                + "AND orddt_ordtyp_ordertypeid = ? "
                + "AND eni_ent_entitytypeid = ? AND ord_orderid = eni_entityuniqueid"),
                orderType, entityType);
    }
    
    public EntityInstancePKHolder getEntityInstancePKHolderByOrderStatusWorkflowStep(WorkflowStep orderStatusWorkflowStep) {
        return getEntityInstancePKHolderFromQuery(EntityInstanceFactory.getInstance().prepareStatement(
                "SELECT _PK_ "
                + "FROM entityinstances, orderes, orderdetails, workflowentitystatuses "
                + "WHERE ord_activedetailid = orddt_orderdetailid "
                + "AND orddt_ordtyp_ordertypeid = ? "
                + "AND eni_ent_entitytypeid = ? AND ord_orderid = eni_entityuniqueid "
                + "AND eni_entityinstanceid = wkfles_eni_entityinstanceid AND wkfles_wkfls_workflowstepid = ? AND wkfles_thrutime = ?"),
                orderType, entityType, orderStatusWorkflowStep, Session.MAX_TIME);
    }
 
    public EntityInstancePKHolder getEntityInstancePKHolderByOrderPriority(OrderPriority orderPriority) {
        return getEntityInstancePKHolderFromQuery(EntityInstanceFactory.getInstance().prepareStatement(
                "SELECT _PK_ "
                + "FROM entityinstances, orderes, orderdetails "
                + "WHERE ord_activedetailid = orddt_orderdetailid "
                + "AND orddt_ordtyp_ordertypeid = ? AND orddt_ordpr_orderpriorityid = ? "
                + "AND eni_ent_entitytypeid = ? AND ord_orderid = eni_entityuniqueid"),
                orderType, orderPriority, entityType);
    }
    
    public EntityInstancePKHolder getEntityInstancePKHolderByCurrency(Currency currency) {
        return getEntityInstancePKHolderFromQuery(EntityInstanceFactory.getInstance().prepareStatement(
                "SELECT _PK_ "
                + "FROM entityinstances, orderes, orderdetails "
                + "WHERE ord_activedetailid = orddt_orderdetailid "
                + "AND orddt_ordtyp_ordertypeid = ? AND orddt_cur_currencyid = ? "
                + "AND eni_ent_entitytypeid = ? AND ord_orderid = eni_entityuniqueid"),
                orderType, currency, entityType);
    }
    
    public EntityInstancePKHolder getEntityInstancePKHolderByTerm(Term term) {
        return getEntityInstancePKHolderFromQuery(EntityInstanceFactory.getInstance().prepareStatement(
                "SELECT _PK_ "
                + "FROM entityinstances, orderes, orderdetails "
                + "WHERE ord_activedetailid = orddt_orderdetailid "
                + "AND orddt_ordtyp_ordertypeid = ? AND orddt_trm_termid = ? "
                + "AND eni_ent_entitytypeid = ? AND ord_orderid = eni_entityuniqueid"),
                orderType, term, entityType);
    }
    
    public EntityInstancePKHolder getEntityInstancePKHolderByReference(String reference) {
        return getEntityInstancePKHolderFromQuery(EntityInstanceFactory.getInstance().prepareStatement(
                "SELECT _PK_ "
                + "FROM entityinstances, orderes, orderdetails "
                + "WHERE ord_activedetailid = orddt_orderdetailid "
                + "AND orddt_ordtyp_ordertypeid = ? AND orddt_reference = ? "
                + "AND eni_ent_entitytypeid = ? AND ord_orderid = eni_entityuniqueid"),
                orderType, reference, entityType);
    }
    
    public EntityInstancePKHolder getEntityInstancePKHolderByCancellationPolicy(CancellationPolicy cancellationPolicy) {
        return getEntityInstancePKHolderFromQuery(EntityInstanceFactory.getInstance().prepareStatement(
                "SELECT _PK_ "
                + "FROM entityinstances, orderes, orderdetails "
                + "WHERE ord_activedetailid = orddt_orderdetailid "
                + "AND orddt_ordtyp_ordertypeid = ? AND orddt_cnclplcy_cancellationpolicyid = ? "
                + "AND eni_ent_entitytypeid = ? AND ord_orderid = eni_entityuniqueid"),
                orderType, cancellationPolicy, entityType);
    }
    
    public EntityInstancePKHolder getEntityInstancePKHolderByReturnPolicy(ReturnPolicy returnPolicy) {
        return getEntityInstancePKHolderFromQuery(EntityInstanceFactory.getInstance().prepareStatement(
                "SELECT _PK_ "
                + "FROM entityinstances, orderes, orderdetails "
                + "WHERE ord_activedetailid = orddt_orderdetailid "
                + "AND orddt_ordtyp_ordertypeid = ? AND orddt_rtnplcy_returnpolicyid = ? "
                + "AND eni_ent_entitytypeid = ? AND ord_orderid = eni_entityuniqueid"),
                orderType, returnPolicy, entityType);
    }
    
    /** Subclasses should override and always call their super's executeSearch() */
    @Override
    protected EntityInstancePKHolder executeSearch(final ExecutionErrorAccumulator eea) {
        EntityInstancePKHolder resultSet = null;
        var parameterCount = (orderName == null ? 0 : 1) + (alias == null ? 0 : 1);

        if(parameterCount == 0) {
            resultSet = super.executeSearch(eea);
            
            if(orderStatusWorkflowStep != null && (resultSet == null || resultSet.size() > 0)) {
                var entityInstancePKHolder = getEntityInstancePKHolderByOrderStatusWorkflowStep(orderStatusWorkflowStep);

                if(resultSet == null) {
                    resultSet = entityInstancePKHolder;
                } else {
                    resultSet.retainAll(entityInstancePKHolder);
                }
            }
            
            if(orderPriority != null && (resultSet == null || resultSet.size() > 0)) {
                var entityInstancePKHolder = getEntityInstancePKHolderByOrderPriority(orderPriority);

                if(resultSet == null) {
                    resultSet = entityInstancePKHolder;
                } else {
                    resultSet.retainAll(entityInstancePKHolder);
                }
            }
            
            if(currency != null && (resultSet == null || resultSet.size() > 0)) {
                var entityInstancePKHolder = getEntityInstancePKHolderByCurrency(currency);

                if(resultSet == null) {
                    resultSet = entityInstancePKHolder;
                } else {
                    resultSet.retainAll(entityInstancePKHolder);
                }
            }
            
            if(term != null && (resultSet == null || resultSet.size() > 0)) {
                var entityInstancePKHolder = getEntityInstancePKHolderByTerm(term);

                if(resultSet == null) {
                    resultSet = entityInstancePKHolder;
                } else {
                    resultSet.retainAll(entityInstancePKHolder);
                }
            }
            
            if(reference != null && (resultSet == null || resultSet.size() > 0)) {
                var entityInstancePKHolder = getEntityInstancePKHolderByReference(reference);

                if(resultSet == null) {
                    resultSet = entityInstancePKHolder;
                } else {
                    resultSet.retainAll(entityInstancePKHolder);
                }
            }
            
            if(cancellationPolicy != null && (resultSet == null || resultSet.size() > 0)) {
                var entityInstancePKHolder = getEntityInstancePKHolderByCancellationPolicy(cancellationPolicy);

                if(resultSet == null) {
                    resultSet = entityInstancePKHolder;
                } else {
                    resultSet.retainAll(entityInstancePKHolder);
                }
            }
            
            if(returnPolicy != null && (resultSet == null || resultSet.size() > 0)) {
                var entityInstancePKHolder = getEntityInstancePKHolderByReturnPolicy(returnPolicy);

                if(resultSet == null) {
                    resultSet = entityInstancePKHolder;
                } else {
                    resultSet.retainAll(entityInstancePKHolder);
                }
            }
            
            if(countParameters() == 0 && (resultSet == null || resultSet.size() > 0)) {
                var entityInstancePKHolder = getEntityInstancePKHolderByOrderType(orderType);

                if(resultSet == null) {
                    resultSet = entityInstancePKHolder;
                } else {
                    resultSet.retainAll(entityInstancePKHolder);
                }
            }
        } else {
            Order order = null;

            if(parameterCount == 1) {
                var orderAliasControl = Session.getModelController(OrderAliasControl.class);

                if(orderAliasType == null) {
                    orderAliasType = orderAliasControl.getDefaultOrderAliasType(orderType);
                }

                if(orderAliasType != null) {
                    var orderAlias = orderAliasControl.getOrderAliasByAlias(orderAliasType, alias);
                    
                    order = orderAlias == null ? null : orderAlias.getOrder();
                }

                if(orderName != null) {
                    var orderControl = Session.getModelController(OrderControl.class);

                    order = orderControl.getOrderByName(orderType, orderName);
                }

                if(order != null) {
                    var entityInstanceControl = Session.getModelController(EntityInstanceControl.class);

                    resultSet = new EntityInstancePKHolder(1);
                    resultSet.add(entityInstanceControl.getEntityInstanceByBasePK(order.getPrimaryKey()).getPrimaryKey(), null);
                }
            }
        }
        
        return resultSet;
    }

}
