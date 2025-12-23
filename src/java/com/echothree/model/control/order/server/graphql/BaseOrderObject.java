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

package com.echothree.model.control.order.server.graphql;

import com.echothree.model.control.accounting.server.graphql.AccountingSecurityUtils;
import com.echothree.model.control.accounting.server.graphql.CurrencyObject;
import com.echothree.model.control.cancellationpolicy.server.graphql.CancellationPolicyObject;
import com.echothree.model.control.cancellationpolicy.server.graphql.CancellationPolicySecurityUtils;
import com.echothree.model.control.graphql.server.graphql.BaseEntityInstanceObject;
import com.echothree.model.control.returnpolicy.server.graphql.ReturnPolicyObject;
import com.echothree.model.control.returnpolicy.server.graphql.ReturnPolicySecurityUtils;
import com.echothree.model.control.shipment.server.graphql.FreeOnBoardObject;
import com.echothree.model.control.shipment.server.graphql.ShipmentSecurityUtils;
import com.echothree.model.control.term.server.graphql.TermObject;
import com.echothree.model.control.term.server.graphql.TermSecurityUtils;
import com.echothree.model.data.order.server.entity.Order;
import com.echothree.model.data.order.server.entity.OrderDetail;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;

public abstract class BaseOrderObject
        extends BaseEntityInstanceObject {

    private final Order order; // Always Present

    protected BaseOrderObject(final Order order) {
        super(order.getPrimaryKey());

        this.order = order;
    }

    private OrderDetail orderDetail; // Optional, use getOrderDetail()
    
    private OrderDetail getOrderDetail() {
        if(orderDetail == null) {
            orderDetail = order.getLastDetail();
        }
        
        return orderDetail;
    }

    @GraphQLField
    @GraphQLDescription("order type")
    @GraphQLNonNull
    public OrderTypeObject getOrderType(final DataFetchingEnvironment env) {
        return OrderSecurityUtils.getHasOrderTypeAccess(env) ? new OrderTypeObject(getOrderDetail().getOrderType()) : null;
    }

    @GraphQLField
    @GraphQLDescription("order type name")
    @GraphQLNonNull
    public String getOrderName() {
        return getOrderDetail().getOrderName();
    }

    @GraphQLField
    @GraphQLDescription("order priority")
    public OrderPriorityObject getOrderPriority(final DataFetchingEnvironment env) {
        var orderPriority = getOrderDetail().getOrderPriority();

        return orderPriority == null ? null : OrderSecurityUtils.getHasOrderPriorityAccess(env) ?
                new OrderPriorityObject(orderPriority) : null;
    }

    @GraphQLField
    @GraphQLDescription("currency")
    @GraphQLNonNull
    public CurrencyObject getCurrency(final DataFetchingEnvironment env) {
        return AccountingSecurityUtils.getHasCurrencyAccess(env) ?
                new CurrencyObject(getOrderDetail().getCurrency()) : null;
    }

    @GraphQLField
    @GraphQLDescription("hold until complete")
    public Boolean getHoldUntilComplete() {
        return getOrderDetail().getHoldUntilComplete();
    }

    @GraphQLField
    @GraphQLDescription("allow backorders")
    public Boolean getAllowBackorders() {
        return getOrderDetail().getAllowBackorders();
    }

    @GraphQLField
    @GraphQLDescription("allow substitutions")
    public Boolean getAllowSubstitutions() {
        return getOrderDetail().getAllowSubstitutions();
    }

    @GraphQLField
    @GraphQLDescription("allow combining shipments")
    public Boolean getAllowCombiningShipments() {
        return getOrderDetail().getAllowCombiningShipments();
    }

    @GraphQLField
    @GraphQLDescription("term")
    public TermObject getTerm(final DataFetchingEnvironment env) {
        var term = getOrderDetail().getTerm();

        return term == null ? null : (TermSecurityUtils.getHasTermAccess(env) ?
                new TermObject(getOrderDetail().getTerm()) : null);
    }

    @GraphQLField
    @GraphQLDescription("freeOnBoard")
    public FreeOnBoardObject getFreeOnBoard(final DataFetchingEnvironment env) {
        var freeOnBoard = getOrderDetail().getFreeOnBoard();

        return freeOnBoard == null ? null : ShipmentSecurityUtils.getHasFreeOnBoardAccess(env) ?
                new FreeOnBoardObject(getOrderDetail().getFreeOnBoard()) : null;
    }

    @GraphQLField
    @GraphQLDescription("reference")
    public String getReference() {
        return getOrderDetail().getReference();
    }

    @GraphQLField
    @GraphQLDescription("description")
    public String getDescription() {
        return getOrderDetail().getDescription();
    }

    @GraphQLField
    @GraphQLDescription("cancellation policy")
    public CancellationPolicyObject getCancellationPolicy(final DataFetchingEnvironment env) {
        var cancellationPolicy = getOrderDetail().getCancellationPolicy();

        return cancellationPolicy == null ? null : CancellationPolicySecurityUtils.getHasCancellationPolicyAccess(env) ?
                new CancellationPolicyObject(cancellationPolicy) : null;
    }

    @GraphQLField
    @GraphQLDescription("return policy")
    public ReturnPolicyObject getReturnPolicy(final DataFetchingEnvironment env) {
        var returnPolicy = getOrderDetail().getReturnPolicy();

        return returnPolicy == null ? null : ReturnPolicySecurityUtils.getHasReturnPolicyAccess(env) ?
                new ReturnPolicyObject(returnPolicy) : null;
    }

    @GraphQLField
    @GraphQLDescription("taxable")
    public Boolean getTaxable() {
        return getOrderDetail().getTaxable();
    }

}
