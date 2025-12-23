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
import com.echothree.model.control.graphql.server.graphql.TimeObject;
import com.echothree.model.data.order.server.entity.OrderLineTime;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;

@GraphQLDescription("order line time object")
@GraphQLName("OrderLineTime")
public class OrderLineTimeObject
        extends BaseEntityInstanceObject {
    
    private final OrderLineTime orderLineTime; // Always Present

    public OrderLineTimeObject(final OrderLineTime orderLineTime) {
        super(orderLineTime.getPrimaryKey());

        this.orderLineTime = orderLineTime;
    }

    @GraphQLField
    @GraphQLDescription("order time type")
    @GraphQLNonNull
    public OrderTimeTypeObject getOrderTimeType(final DataFetchingEnvironment env) {
        return OrderSecurityUtils.getHasOrderTimeTypeAccess(env) ? new OrderTimeTypeObject(orderLineTime.getOrderTimeType()) : null;
    }

    @GraphQLField
    @GraphQLDescription("time")
    @GraphQLNonNull
    public TimeObject getTime(final DataFetchingEnvironment env) {
        return new TimeObject(orderLineTime.getTime());
    }

}
