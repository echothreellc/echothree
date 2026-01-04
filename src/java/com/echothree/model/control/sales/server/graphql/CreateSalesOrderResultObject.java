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

package com.echothree.model.control.sales.server.graphql;

import com.echothree.control.user.sales.common.result.CreateSalesOrderResult;
import com.echothree.model.control.graphql.server.graphql.MutationResultWithIdObject;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;

@GraphQLDescription("create sales order result object")
@GraphQLName("CreateSalesOrderResult")
public class CreateSalesOrderResultObject
        extends MutationResultWithIdObject {

    String orderName;

    public void setCreateSalesOrderResult(final CreateSalesOrderResult result) {
        setEntityInstanceFromEntityRef(result.getEntityRef());

        this.orderName = result.getOrderName();
    }

    @GraphQLField
    @GraphQLDescription("order name")
    public String getOrderName() {
        return orderName;
    }

}
