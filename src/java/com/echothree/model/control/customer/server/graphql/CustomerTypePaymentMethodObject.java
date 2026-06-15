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
import com.echothree.model.control.payment.server.graphql.PaymentMethodObject;
import com.echothree.model.control.payment.server.graphql.PaymentSecurityUtils;
import com.echothree.model.data.customer.server.entity.CustomerTypePaymentMethod;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;

@GraphQLDescription("customer type payment method object")
@GraphQLName("CustomerTypePaymentMethod")
public class CustomerTypePaymentMethodObject
        extends BaseObject {

    private final CustomerTypePaymentMethod customerTypePaymentMethod; // Always Present

    public CustomerTypePaymentMethodObject(CustomerTypePaymentMethod customerTypePaymentMethod) {
        this.customerTypePaymentMethod = customerTypePaymentMethod;
    }

    @GraphQLField
    @GraphQLDescription("customer type")
    public CustomerTypeObject getCustomerType(final DataFetchingEnvironment env) {
        var customerType = customerTypePaymentMethod.getCustomerType();

        return CustomerSecurityUtils.getHasCustomerTypeAccess(env) ? new CustomerTypeObject(customerType) : null;
    }

    @GraphQLField
    @GraphQLDescription("payment method")
    public PaymentMethodObject getPaymentMethod(final DataFetchingEnvironment env) {
        var paymentMethod = customerTypePaymentMethod.getPaymentMethod();

        return PaymentSecurityUtils.getHasPaymentMethodAccess(env) ? new PaymentMethodObject(paymentMethod) : null;
    }

    @GraphQLField
    @GraphQLDescription("default selection priority")
    @GraphQLNonNull
    public int getDefaultSelectionPriority() {
        return customerTypePaymentMethod.getDefaultSelectionPriority();
    }


    @GraphQLField
    @GraphQLDescription("is default")
    @GraphQLNonNull
    public boolean getIsDefault() {
        return customerTypePaymentMethod.getIsDefault();
    }

    @GraphQLField
    @GraphQLDescription("sort order")
    @GraphQLNonNull
    public int getSortOrder() {
        return customerTypePaymentMethod.getSortOrder();
    }

}
