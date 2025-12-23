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
import com.echothree.model.control.order.server.control.OrderTimeControl;
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.data.order.server.entity.OrderTimeType;
import com.echothree.model.data.order.server.entity.OrderTimeTypeDetail;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;

@GraphQLDescription("order time type object")
@GraphQLName("OrderTimeType")
public class OrderTimeTypeObject
        extends BaseEntityInstanceObject {
    
    private final OrderTimeType orderTimeType; // Always Present

    public OrderTimeTypeObject(final OrderTimeType orderTimeType) {
        super(orderTimeType.getPrimaryKey());

        this.orderTimeType = orderTimeType;
    }

    private OrderTimeTypeDetail orderTimeTypeDetail; // Optional, use getOrderTimeTypeDetail()
    
    private OrderTimeTypeDetail getOrderTimeTypeDetail() {
        if(orderTimeTypeDetail == null) {
            orderTimeTypeDetail = orderTimeType.getLastDetail();
        }
        
        return orderTimeTypeDetail;
    }

    @GraphQLField
    @GraphQLDescription("order time type name")
    @GraphQLNonNull
    public String getOrderTimeTypeName() {
        return getOrderTimeTypeDetail().getOrderTimeTypeName();
    }

    @GraphQLField
    @GraphQLDescription("order type")
    @GraphQLNonNull
    public OrderTypeObject getOrderType(final DataFetchingEnvironment env) {
        return OrderSecurityUtils.getHasOrderTypeAccess(env) ? new OrderTypeObject(getOrderTimeTypeDetail().getOrderType()) : null;
    }

    @GraphQLField
    @GraphQLDescription("is default")
    @GraphQLNonNull
    public boolean getIsDefault() {
        return getOrderTimeTypeDetail().getIsDefault();
    }

    @GraphQLField
    @GraphQLDescription("sort order")
    @GraphQLNonNull
    public int getSortOrder() {
        return getOrderTimeTypeDetail().getSortOrder();
    }
    
    @GraphQLField
    @GraphQLDescription("description")
    @GraphQLNonNull
    public String getDescription(final DataFetchingEnvironment env) {
        var orderTimeControl = Session.getModelController(OrderTimeControl.class);
        var userControl = Session.getModelController(UserControl.class);

        return orderTimeControl.getBestOrderTimeTypeDescription(orderTimeType, userControl.getPreferredLanguageFromUserVisit(BaseGraphQl.getUserVisit(env)));
    }

}
