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

package com.echothree.model.control.payment.server.graphql;

import com.echothree.model.control.graphql.server.graphql.BaseEntityInstanceObject;
import com.echothree.model.control.payment.server.control.PaymentProcessorTransactionCodeControl;
import com.echothree.model.data.payment.server.entity.PaymentProcessorTransactionCode;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;
import java.util.ArrayList;
import java.util.List;

@GraphQLDescription("payment processor transaction code object")
@GraphQLName("PaymentProcessorTransactionCode")
public class PaymentProcessorTransactionCodeObject
        extends BaseEntityInstanceObject {
    
    private final PaymentProcessorTransactionCode paymentProcessorTransactionCode; // Always Present
    
    public PaymentProcessorTransactionCodeObject(PaymentProcessorTransactionCode paymentProcessorTransactionCode) {
        super(paymentProcessorTransactionCode.getPrimaryKey());
        
        this.paymentProcessorTransactionCode = paymentProcessorTransactionCode;
    }

    @GraphQLField
    @GraphQLDescription("payment processor transaction")
    public PaymentProcessorTransactionObject getPaymentProcessorTransaction(final DataFetchingEnvironment env) {
        return PaymentSecurityUtils.getHasPaymentProcessorTransactionAccess(env) ? new PaymentProcessorTransactionObject(paymentProcessorTransactionCode.getPaymentProcessorTransaction()) : null;
    }

    @GraphQLField
    @GraphQLDescription("payment processor transaction codes")
    @GraphQLNonNull
    public List<PaymentProcessorTransactionCodeObject> getPaymentProcessorTransactionCodes(final DataFetchingEnvironment env) {
        List<PaymentProcessorTransactionCodeObject> paymentProcessorTransactionCodes = null;

        if(PaymentSecurityUtils.getHasPaymentProcessorTransactionCodesAccess(env)) {
            var paymentProcessorTransactionCodeControl = Session.getModelController(PaymentProcessorTransactionCodeControl.class);
            var entities = paymentProcessorTransactionCodeControl.getPaymentProcessorTransactionCodesByPaymentProcessorTransaction(paymentProcessorTransactionCode.getPaymentProcessorTransaction());
            var objects = new ArrayList<PaymentProcessorTransactionCodeObject>(entities.size());

            entities.forEach((entity) -> objects.add(new PaymentProcessorTransactionCodeObject(entity)));

            paymentProcessorTransactionCodes = objects;
        }

        return paymentProcessorTransactionCodes;
    }

}
