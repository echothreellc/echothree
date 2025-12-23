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
import com.echothree.model.control.order.server.control.OrderRoleControl;
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.data.order.server.entity.OrderRoleType;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;

@GraphQLDescription("order role type object")
@GraphQLName("OrderRoleType")
public class OrderRoleTypeObject
        extends BaseEntityInstanceObject {
    
    private final OrderRoleType orderRoleType; // Always Present

    public OrderRoleTypeObject(final OrderRoleType orderRoleType) {
        super(orderRoleType.getPrimaryKey());

        this.orderRoleType = orderRoleType;
    }

    @GraphQLField
    @GraphQLDescription("order role type name")
    @GraphQLNonNull
    public String getOrderRoleTypeName() {
        return orderRoleType.getOrderRoleTypeName();
    }

    @GraphQLField
    @GraphQLDescription("sort order")
    @GraphQLNonNull
    public int getSortOrder() {
        return orderRoleType.getSortOrder();
    }
    
    @GraphQLField
    @GraphQLDescription("description")
    @GraphQLNonNull
    public String getDescription(final DataFetchingEnvironment env) {
        var orderRoleControl = Session.getModelController(OrderRoleControl.class);
        var userControl = Session.getModelController(UserControl.class);

        return orderRoleControl.getBestOrderRoleTypeDescription(orderRoleType, userControl.getPreferredLanguageFromUserVisit(BaseGraphQl.getUserVisit(env)));
    }

}
