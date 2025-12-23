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
import com.echothree.model.control.payment.server.control.PaymentProcessorActionTypeControl;
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.data.payment.server.entity.PaymentProcessorActionType;
import com.echothree.model.data.payment.server.entity.PaymentProcessorActionTypeDetail;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;

@GraphQLDescription("payment processor action type object")
@GraphQLName("PaymentProcessorActionType")
public class PaymentProcessorActionTypeObject
        extends BaseEntityInstanceObject {
    
    private final PaymentProcessorActionType paymentProcessorActionType; // Always Present
    
    public PaymentProcessorActionTypeObject(PaymentProcessorActionType paymentProcessorActionType) {
        super(paymentProcessorActionType.getPrimaryKey());
        
        this.paymentProcessorActionType = paymentProcessorActionType;
    }

    private PaymentProcessorActionTypeDetail paymentProcessorActionTypeDetail; // Optional, use getPaymentProcessorActionTypeDetail()
    
    private PaymentProcessorActionTypeDetail getPaymentProcessorActionTypeDetail() {
        if(paymentProcessorActionTypeDetail == null) {
            paymentProcessorActionTypeDetail = paymentProcessorActionType.getLastDetail();
        }
        
        return paymentProcessorActionTypeDetail;
    }
    
    @GraphQLField
    @GraphQLDescription("payment processor action type name")
    @GraphQLNonNull
    public String getPaymentProcessorActionTypeName() {
        return getPaymentProcessorActionTypeDetail().getPaymentProcessorActionTypeName();
    }

    @GraphQLField
    @GraphQLDescription("is default")
    @GraphQLNonNull
    public boolean getIsDefault() {
        return getPaymentProcessorActionTypeDetail().getIsDefault();
    }
    
    @GraphQLField
    @GraphQLDescription("sort order")
    @GraphQLNonNull
    public int getSortOrder() {
        return getPaymentProcessorActionTypeDetail().getSortOrder();
    }
    
    @GraphQLField
    @GraphQLDescription("description")
    @GraphQLNonNull
    public String getDescription(final DataFetchingEnvironment env) {
        var paymentProcessorActionTypeControl = Session.getModelController(PaymentProcessorActionTypeControl.class);
        var userControl = Session.getModelController(UserControl.class);

        return paymentProcessorActionTypeControl.getBestPaymentProcessorActionTypeDescription(paymentProcessorActionType, userControl.getPreferredLanguageFromUserVisit(BaseGraphQl.getUserVisit(env)));
    }
    
}
