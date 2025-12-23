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

package com.echothree.model.control.customer.server.graphql;

import com.echothree.model.control.graphql.server.graphql.BaseObject;
import com.echothree.model.control.shipping.server.graphql.ShippingMethodObject;
import com.echothree.model.control.shipping.server.graphql.ShippingSecurityUtils;
import com.echothree.model.data.customer.server.entity.CustomerTypeShippingMethod;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;

@GraphQLDescription("customer type shipping method object")
@GraphQLName("CustomerTypeShippingMethod")
public class CustomerTypeShippingMethodObject
        extends BaseObject {

    private final CustomerTypeShippingMethod customerTypeShippingMethod; // Always Present

    public CustomerTypeShippingMethodObject(CustomerTypeShippingMethod customerTypeShippingMethod) {
        this.customerTypeShippingMethod = customerTypeShippingMethod;
    }

    @GraphQLField
    @GraphQLDescription("customer type")
    public CustomerTypeObject getCustomerType(final DataFetchingEnvironment env) {
        var customerType = customerTypeShippingMethod.getCustomerType();

        return CustomerSecurityUtils.getHasCustomerTypeAccess(env) ? new CustomerTypeObject(customerType) : null;
    }

    @GraphQLField
    @GraphQLDescription("shipping method")
    public ShippingMethodObject getShippingMethod(final DataFetchingEnvironment env) {
        var shippingMethod = customerTypeShippingMethod.getShippingMethod();

        return ShippingSecurityUtils.getHasShippingMethodAccess(env) ? new ShippingMethodObject(shippingMethod) : null;
    }

    @GraphQLField
    @GraphQLDescription("default selection priority")
    @GraphQLNonNull
    public int getDefaultSelectionPriority() {
        return customerTypeShippingMethod.getDefaultSelectionPriority();
    }


    @GraphQLField
    @GraphQLDescription("is default")
    @GraphQLNonNull
    public boolean getIsDefault() {
        return customerTypeShippingMethod.getIsDefault();
    }

    @GraphQLField
    @GraphQLDescription("sort order")
    @GraphQLNonNull
    public int getSortOrder() {
        return customerTypeShippingMethod.getSortOrder();
    }

}
