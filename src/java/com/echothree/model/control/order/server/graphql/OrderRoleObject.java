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

import com.echothree.model.control.graphql.server.graphql.BaseObject;
import com.echothree.model.control.party.server.graphql.PartyObject;
import com.echothree.model.control.party.server.graphql.PartySecurityUtils;
import com.echothree.model.data.order.server.entity.OrderRole;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;

@GraphQLDescription("order role type object")
@GraphQLName("OrderRole")
public class OrderRoleObject
        extends BaseObject {
    
    private final OrderRole orderRole; // Always Present

    public OrderRoleObject(final OrderRole orderRole) {
        this.orderRole = orderRole;
    }

    @GraphQLField
    @GraphQLDescription("party")
    public PartyObject getParty(final DataFetchingEnvironment env) {
        var party = orderRole.getParty();

        return PartySecurityUtils.getHasPartyAccess(env, party) ? new PartyObject(party) : null;
    }

    @GraphQLField
    @GraphQLDescription("order role type")
    @GraphQLNonNull
    public OrderRoleTypeObject getOrderRoleType(final DataFetchingEnvironment env) {
        return OrderSecurityUtils.getHasOrderRoleTypeAccess(env) ? new OrderRoleTypeObject(orderRole.getOrderRoleType()) : null;
    }


}
