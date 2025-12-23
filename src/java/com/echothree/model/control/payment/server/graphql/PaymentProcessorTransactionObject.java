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
import com.echothree.model.data.payment.server.entity.PaymentProcessorTransaction;
import com.echothree.model.data.payment.server.entity.PaymentProcessorTransactionDetail;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;

@GraphQLDescription("payment processor transaction object")
@GraphQLName("PaymentProcessorTransaction")
public class PaymentProcessorTransactionObject
        extends BaseEntityInstanceObject {
    
    private final PaymentProcessorTransaction paymentProcessorTransaction; // Always Present
    
    public PaymentProcessorTransactionObject(PaymentProcessorTransaction paymentProcessorTransaction) {
        super(paymentProcessorTransaction.getPrimaryKey());
        
        this.paymentProcessorTransaction = paymentProcessorTransaction;
    }

    private PaymentProcessorTransactionDetail paymentProcessorTransactionDetail; // Optional, use getPaymentProcessorTransactionDetail()
    
    private PaymentProcessorTransactionDetail getPaymentProcessorTransactionDetail() {
        if(paymentProcessorTransactionDetail == null) {
            paymentProcessorTransactionDetail = paymentProcessorTransaction.getLastDetail();
        }
        
        return paymentProcessorTransactionDetail;
    }

    @GraphQLField
    @GraphQLDescription("payment processor transaction name")
    @GraphQLNonNull
    public String getPaymentProcessorTransactionName() {
        return getPaymentProcessorTransactionDetail().getPaymentProcessorTransactionName();
    }
    
    @GraphQLField
    @GraphQLDescription("payment processor")
    public PaymentProcessorObject getPaymentProcessor(final DataFetchingEnvironment env) {
        return PaymentSecurityUtils.getHasPaymentProcessorAccess(env) ? new PaymentProcessorObject(getPaymentProcessorTransactionDetail().getPaymentProcessor()) : null;
    }

    @GraphQLField
    @GraphQLDescription("payment processor action type")
    public PaymentProcessorActionTypeObject getPaymentProcessorActionType(final DataFetchingEnvironment env) {
        return PaymentSecurityUtils.getHasPaymentProcessorActionTypeAccess(env) ? new PaymentProcessorActionTypeObject(getPaymentProcessorTransactionDetail().getPaymentProcessorActionType()) : null;
    }

    @GraphQLField
    @GraphQLDescription("payment processor result code")
    public PaymentProcessorResultCodeObject getPaymentProcessorResultCode(final DataFetchingEnvironment env) {
        return PaymentSecurityUtils.getHasPaymentProcessorResultCodeAccess(env) ? new PaymentProcessorResultCodeObject(getPaymentProcessorTransactionDetail().getPaymentProcessorResultCode()) : null;
    }
    
}
