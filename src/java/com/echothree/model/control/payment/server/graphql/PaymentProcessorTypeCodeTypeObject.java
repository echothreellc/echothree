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
import com.echothree.model.control.payment.server.control.PaymentProcessorTypeCodeTypeControl;
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.data.payment.server.entity.PaymentProcessorTypeCodeType;
import com.echothree.model.data.payment.server.entity.PaymentProcessorTypeCodeTypeDetail;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;

@GraphQLDescription("payment processor type code type object")
@GraphQLName("PaymentProcessorTypeCodeType")
public class PaymentProcessorTypeCodeTypeObject
        extends BaseEntityInstanceObject {
    
    private final PaymentProcessorTypeCodeType paymentProcessorTypeCodeType; // Always Present
    
    public PaymentProcessorTypeCodeTypeObject(PaymentProcessorTypeCodeType paymentProcessorTypeCodeType) {
        super(paymentProcessorTypeCodeType.getPrimaryKey());
        
        this.paymentProcessorTypeCodeType = paymentProcessorTypeCodeType;
    }

    private PaymentProcessorTypeCodeTypeDetail paymentProcessorTypeCodeTypeDetail; // Optional, use getPaymentProcessorTypeCodeTypeDetail()
    
    private PaymentProcessorTypeCodeTypeDetail getPaymentProcessorTypeCodeTypeDetail() {
        if(paymentProcessorTypeCodeTypeDetail == null) {
            paymentProcessorTypeCodeTypeDetail = paymentProcessorTypeCodeType.getLastDetail();
        }
        
        return paymentProcessorTypeCodeTypeDetail;
    }

    @GraphQLField
    @GraphQLDescription("payment processor type")
    public PaymentProcessorTypeObject getPaymentProcessorType(final DataFetchingEnvironment env) {
        return PaymentSecurityUtils.getHasPaymentProcessorTypeAccess(env) ? new PaymentProcessorTypeObject(getPaymentProcessorTypeCodeTypeDetail().getPaymentProcessorType()) : null;
    }

    @GraphQLField
    @GraphQLDescription("payment processor type code type name")
    @GraphQLNonNull
    public String getPaymentProcessorTypeCodeTypeName() {
        return getPaymentProcessorTypeCodeTypeDetail().getPaymentProcessorTypeCodeTypeName();
    }

    @GraphQLField
    @GraphQLDescription("is default")
    @GraphQLNonNull
    public boolean getIsDefault() {
        return getPaymentProcessorTypeCodeTypeDetail().getIsDefault();
    }
    
    @GraphQLField
    @GraphQLDescription("sort order")
    @GraphQLNonNull
    public int getSortOrder() {
        return getPaymentProcessorTypeCodeTypeDetail().getSortOrder();
    }
    
    @GraphQLField
    @GraphQLDescription("description")
    @GraphQLNonNull
    public String getDescription(final DataFetchingEnvironment env) {
        var paymentProcessorTypeCodeTypeControl = Session.getModelController(PaymentProcessorTypeCodeTypeControl.class);
        var userControl = Session.getModelController(UserControl.class);

        return paymentProcessorTypeCodeTypeControl.getBestPaymentProcessorTypeCodeTypeDescription(paymentProcessorTypeCodeType, userControl.getPreferredLanguageFromUserVisit(BaseGraphQl.getUserVisit(env)));
    }
    
}
