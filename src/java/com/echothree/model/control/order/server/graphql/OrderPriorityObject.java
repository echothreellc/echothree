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

import com.echothree.model.control.graphql.server.graphql.BaseEntityInstanceObject;
import com.echothree.model.control.graphql.server.util.BaseGraphQl;
import com.echothree.model.control.order.server.control.OrderPriorityControl;
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.data.order.server.entity.OrderPriority;
import com.echothree.model.data.order.server.entity.OrderPriorityDetail;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;

@GraphQLDescription("order priority object")
@GraphQLName("OrderPriority")
public class OrderPriorityObject
        extends BaseEntityInstanceObject {
    
    private final OrderPriority orderPriority; // Always Present

    public OrderPriorityObject(final OrderPriority orderPriority) {
        super(orderPriority.getPrimaryKey());

        this.orderPriority = orderPriority;
    }

    private OrderPriorityDetail orderPriorityDetail; // Optional, use getOrderPriorityDetail()
    
    private OrderPriorityDetail getOrderPriorityDetail() {
        if(orderPriorityDetail == null) {
            orderPriorityDetail = orderPriority.getLastDetail();
        }
        
        return orderPriorityDetail;
    }

    @GraphQLField
    @GraphQLDescription("order priority name")
    @GraphQLNonNull
    public String getOrderPriorityName() {
        return getOrderPriorityDetail().getOrderPriorityName();
    }

    @GraphQLField
    @GraphQLDescription("order type")
    @GraphQLNonNull
    public OrderTypeObject getOrderType(final DataFetchingEnvironment env) {
        return OrderSecurityUtils.getHasOrderTypeAccess(env) ? new OrderTypeObject(getOrderPriorityDetail().getOrderType()) : null;
    }

    @GraphQLField
    @GraphQLDescription("priority")
    @GraphQLNonNull
    public int getPriority() {
        return getOrderPriorityDetail().getPriority();
    }

    @GraphQLField
    @GraphQLDescription("is default")
    @GraphQLNonNull
    public boolean getIsDefault() {
        return getOrderPriorityDetail().getIsDefault();
    }

    @GraphQLField
    @GraphQLDescription("sort order")
    @GraphQLNonNull
    public int getSortOrder() {
        return getOrderPriorityDetail().getSortOrder();
    }
    
    @GraphQLField
    @GraphQLDescription("description")
    @GraphQLNonNull
    public String getDescription(final DataFetchingEnvironment env) {
        var orderPriorityControl = Session.getModelController(OrderPriorityControl.class);
        var userControl = Session.getModelController(UserControl.class);

        return orderPriorityControl.getBestOrderPriorityDescription(orderPriority, userControl.getPreferredLanguageFromUserVisit(BaseGraphQl.getUserVisit(env)));
    }

}
