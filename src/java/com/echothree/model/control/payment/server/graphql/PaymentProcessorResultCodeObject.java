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
import com.echothree.model.control.payment.server.control.PaymentProcessorResultCodeControl;
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.data.payment.server.entity.PaymentProcessorResultCode;
import com.echothree.model.data.payment.server.entity.PaymentProcessorResultCodeDetail;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;

@GraphQLDescription("payment processor result code object")
@GraphQLName("PaymentProcessorResultCode")
public class PaymentProcessorResultCodeObject
        extends BaseEntityInstanceObject {
    
    private final PaymentProcessorResultCode paymentProcessorResultCode; // Always Present
    
    public PaymentProcessorResultCodeObject(PaymentProcessorResultCode paymentProcessorResultCode) {
        super(paymentProcessorResultCode.getPrimaryKey());
        
        this.paymentProcessorResultCode = paymentProcessorResultCode;
    }

    private PaymentProcessorResultCodeDetail paymentProcessorResultCodeDetail; // Optional, use getPaymentProcessorResultCodeDetail()
    
    private PaymentProcessorResultCodeDetail getPaymentProcessorResultCodeDetail() {
        if(paymentProcessorResultCodeDetail == null) {
            paymentProcessorResultCodeDetail = paymentProcessorResultCode.getLastDetail();
        }
        
        return paymentProcessorResultCodeDetail;
    }
    
    @GraphQLField
    @GraphQLDescription("payment processor result code name")
    @GraphQLNonNull
    public String getPaymentProcessorResultCodeName() {
        return getPaymentProcessorResultCodeDetail().getPaymentProcessorResultCodeName();
    }

    @GraphQLField
    @GraphQLDescription("is default")
    @GraphQLNonNull
    public boolean getIsDefault() {
        return getPaymentProcessorResultCodeDetail().getIsDefault();
    }
    
    @GraphQLField
    @GraphQLDescription("sort order")
    @GraphQLNonNull
    public int getSortOrder() {
        return getPaymentProcessorResultCodeDetail().getSortOrder();
    }
    
    @GraphQLField
    @GraphQLDescription("description")
    @GraphQLNonNull
    public String getDescription(final DataFetchingEnvironment env) {
        var paymentProcessorResultCodeControl = Session.getModelController(PaymentProcessorResultCodeControl.class);
        var userControl = Session.getModelController(UserControl.class);

        return paymentProcessorResultCodeControl.getBestPaymentProcessorResultCodeDescription(paymentProcessorResultCode, userControl.getPreferredLanguageFromUserVisit(BaseGraphQl.getUserVisit(env)));
    }
    
}
