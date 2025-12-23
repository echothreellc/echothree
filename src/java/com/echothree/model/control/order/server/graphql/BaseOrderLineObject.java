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

import com.echothree.model.control.cancellationpolicy.server.graphql.CancellationPolicyObject;
import com.echothree.model.control.cancellationpolicy.server.graphql.CancellationPolicySecurityUtils;
import com.echothree.model.control.graphql.server.graphql.BaseEntityInstanceObject;
import com.echothree.model.control.inventory.server.graphql.InventoryConditionObject;
import com.echothree.model.control.inventory.server.graphql.InventorySecurityUtils;
import com.echothree.model.control.item.server.graphql.ItemObject;
import com.echothree.model.control.item.server.graphql.ItemSecurityUtils;
import com.echothree.model.control.returnpolicy.server.graphql.ReturnPolicyObject;
import com.echothree.model.control.returnpolicy.server.graphql.ReturnPolicySecurityUtils;
import com.echothree.model.control.uom.server.graphql.UnitOfMeasureTypeObject;
import com.echothree.model.control.uom.server.graphql.UomSecurityUtils;
import com.echothree.model.data.order.server.entity.OrderLine;
import com.echothree.model.data.order.server.entity.OrderLineDetail;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.schema.DataFetchingEnvironment;

public abstract class BaseOrderLineObject
        extends BaseEntityInstanceObject {

    private final OrderLine orderLine; // Always Present

    protected BaseOrderLineObject(final OrderLine orderLine) {
        super(orderLine.getPrimaryKey());

        this.orderLine = orderLine;
    }

    private OrderLineDetail orderLineDetail; // Optional, use getOrderDetail()
    
    private OrderLineDetail getOrderLineDetail() {
        if(orderLineDetail == null) {
            orderLineDetail = orderLine.getLastDetail();
        }
        
        return orderLineDetail;
    }

    @GraphQLField
    @GraphQLDescription("order line sequence")
    public Integer getOrderLineSequence() {
        return getOrderLineDetail().getOrderLineSequence();
    }

    @GraphQLField
    @GraphQLDescription("parent order line")
    public OrderLineObject getParentOrderLine(final DataFetchingEnvironment env) {
        var parentOrderLine = getOrderLineDetail().getParentOrderLine();

        return parentOrderLine == null ? null : new OrderLineObject(parentOrderLine);
    }

    // TODO: Order Shipment Group

    @GraphQLField
    @GraphQLDescription("item")
    public ItemObject getItem(final DataFetchingEnvironment env) {
        return ItemSecurityUtils.getHasItemAccess(env) ? new ItemObject(getOrderLineDetail().getItem()) : null;
    }

    @GraphQLField
    @GraphQLDescription("inventory condition")
    public InventoryConditionObject getInventoryCondition(final DataFetchingEnvironment env) {
        return InventorySecurityUtils.getHasInventoryConditionAccess(env) ? new InventoryConditionObject(getOrderLineDetail().getInventoryCondition()) : null;
    }

    @GraphQLField
    @GraphQLDescription("unit of measure type")
    public UnitOfMeasureTypeObject getUnitOfMeasureType(final DataFetchingEnvironment env) {
        return UomSecurityUtils.getHasUnitOfMeasureTypeAccess(env) ? new UnitOfMeasureTypeObject(getOrderLineDetail().getUnitOfMeasureType()) : null;
    }

    @GraphQLField
    @GraphQLDescription("quantity")
    public Long getQuantity() {
        return getOrderLineDetail().getQuantity();
    }

    @GraphQLField
    @GraphQLDescription("unformattedUnitAmount")
    public Long getUnformattedUnitAmount(final DataFetchingEnvironment env) {
        return getOrderLineDetail().getUnitAmount();
    }

    // Unit Amount must be formatted in subclass - it could be a Cost or a Price.

    @GraphQLField
    @GraphQLDescription("description")
    public String getDescription() {
        return getOrderLineDetail().getDescription();
    }

    @GraphQLField
    @GraphQLDescription("cancellation policy")
    public CancellationPolicyObject getCancellationPolicy(final DataFetchingEnvironment env) {
        var cancellationPolicy = getOrderLineDetail().getCancellationPolicy();

        return cancellationPolicy == null ? null : CancellationPolicySecurityUtils.getHasCancellationPolicyAccess(env) ?
                new CancellationPolicyObject(cancellationPolicy) : null;
    }

    @GraphQLField
    @GraphQLDescription("return policy")
    public ReturnPolicyObject getReturnPolicy(final DataFetchingEnvironment env) {
        var returnPolicy = getOrderLineDetail().getReturnPolicy();

        return returnPolicy == null ? null : ReturnPolicySecurityUtils.getHasReturnPolicyAccess(env) ?
                new ReturnPolicyObject(returnPolicy) : null;
    }

    @GraphQLField
    @GraphQLDescription("taxable")
    public Boolean getTaxable() {
        return getOrderLineDetail().getTaxable();
    }

}
