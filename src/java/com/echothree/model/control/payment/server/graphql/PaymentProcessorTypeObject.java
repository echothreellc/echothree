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
import com.echothree.model.control.payment.server.control.PaymentProcessorTypeControl;
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.data.payment.server.entity.PaymentProcessorType;
import com.echothree.model.data.payment.server.entity.PaymentProcessorTypeDetail;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;

@GraphQLDescription("payment processor type object")
@GraphQLName("PaymentProcessorType")
public class PaymentProcessorTypeObject
        extends BaseEntityInstanceObject {
    
    private final PaymentProcessorType paymentProcessorType; // Always Present
    
    public PaymentProcessorTypeObject(PaymentProcessorType paymentProcessorType) {
        super(paymentProcessorType.getPrimaryKey());
        
        this.paymentProcessorType = paymentProcessorType;
    }

    private PaymentProcessorTypeDetail paymentProcessorTypeDetail; // Optional, use getPaymentProcessorTypeDetail()
    
    private PaymentProcessorTypeDetail getPaymentProcessorTypeDetail() {
        if(paymentProcessorTypeDetail == null) {
            paymentProcessorTypeDetail = paymentProcessorType.getLastDetail();
        }
        
        return paymentProcessorTypeDetail;
    }
    
    @GraphQLField
    @GraphQLDescription("payment processor type name")
    @GraphQLNonNull
    public String getPaymentProcessorTypeName() {
        return getPaymentProcessorTypeDetail().getPaymentProcessorTypeName();
    }

    @GraphQLField
    @GraphQLDescription("is default")
    @GraphQLNonNull
    public boolean getIsDefault() {
        return getPaymentProcessorTypeDetail().getIsDefault();
    }
    
    @GraphQLField
    @GraphQLDescription("sort order")
    @GraphQLNonNull
    public int getSortOrder() {
        return getPaymentProcessorTypeDetail().getSortOrder();
    }
    
    @GraphQLField
    @GraphQLDescription("description")
    @GraphQLNonNull
    public String getDescription(final DataFetchingEnvironment env) {
        var paymentProcessorTypeControl = Session.getModelController(PaymentProcessorTypeControl.class);
        var userControl = Session.getModelController(UserControl.class);

        return paymentProcessorTypeControl.getBestPaymentProcessorTypeDescription(paymentProcessorType, userControl.getPreferredLanguageFromUserVisit(BaseGraphQl.getUserVisit(env)));
    }
    
}
