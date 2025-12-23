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
import com.echothree.model.control.graphql.server.util.BaseGraphQl;
import com.echothree.model.control.payment.server.control.PaymentProcessorControl;
import com.echothree.model.control.payment.server.control.PaymentProcessorTransactionControl;
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.data.payment.server.entity.PaymentProcessor;
import com.echothree.model.data.payment.server.entity.PaymentProcessorDetail;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;
import java.util.ArrayList;
import java.util.List;

@GraphQLDescription("payment processor object")
@GraphQLName("PaymentProcessor")
public class PaymentProcessorObject
        extends BaseEntityInstanceObject {
    
    private final PaymentProcessor paymentProcessor; // Always Present
    
    public PaymentProcessorObject(PaymentProcessor paymentProcessor) {
        super(paymentProcessor.getPrimaryKey());
        
        this.paymentProcessor = paymentProcessor;
    }

    private PaymentProcessorDetail paymentProcessorDetail; // Optional, use getPaymentProcessorDetail()
    
    private PaymentProcessorDetail getPaymentProcessorDetail() {
        if(paymentProcessorDetail == null) {
            paymentProcessorDetail = paymentProcessor.getLastDetail();
        }
        
        return paymentProcessorDetail;
    }

    @GraphQLField
    @GraphQLDescription("payment processor name")
    @GraphQLNonNull
    public String getPaymentProcessorName() {
        return getPaymentProcessorDetail().getPaymentProcessorName();
    }

    @GraphQLField
    @GraphQLDescription("payment processor type")
    public PaymentProcessorTypeObject getPaymentProcessorType(final DataFetchingEnvironment env) {
        return PaymentSecurityUtils.getHasPaymentProcessorTypeAccess(env) ? new PaymentProcessorTypeObject(getPaymentProcessorDetail().getPaymentProcessorType()) : null;
    }
    
    @GraphQLField
    @GraphQLDescription("is default")
    @GraphQLNonNull
    public boolean getIsDefault() {
        return getPaymentProcessorDetail().getIsDefault();
    }
    
    @GraphQLField
    @GraphQLDescription("sort order")
    @GraphQLNonNull
    public int getSortOrder() {
        return getPaymentProcessorDetail().getSortOrder();
    }
    
    @GraphQLField
    @GraphQLDescription("description")
    @GraphQLNonNull
    public String getDescription(final DataFetchingEnvironment env) {
        var paymentProcessorControl = Session.getModelController(PaymentProcessorControl.class);
        var userControl = Session.getModelController(UserControl.class);

        return paymentProcessorControl.getBestPaymentProcessorDescription(paymentProcessor, userControl.getPreferredLanguageFromUserVisit(BaseGraphQl.getUserVisit(env)));
    }

    @GraphQLField
    @GraphQLDescription("payment processor transactions")
    @GraphQLNonNull
    public List<PaymentProcessorTransactionObject> getPaymentProcessorTransactions(final DataFetchingEnvironment env) {
        List<PaymentProcessorTransactionObject> paymentProcessorTransactions = null;

        if(PaymentSecurityUtils.getHasPaymentProcessorTransactionsAccess(env)) {
            var paymentProcessorTransactionControl = Session.getModelController(PaymentProcessorTransactionControl.class);
            var entities = paymentProcessorTransactionControl.getPaymentProcessorTransactionsByPaymentProcessor(paymentProcessor);
            var objects = new ArrayList<PaymentProcessorTransactionObject>(entities.size());

            entities.forEach((entity) -> objects.add(new PaymentProcessorTransactionObject(entity)));

            paymentProcessorTransactions = objects;
        }

        return paymentProcessorTransactions;
    }

}
