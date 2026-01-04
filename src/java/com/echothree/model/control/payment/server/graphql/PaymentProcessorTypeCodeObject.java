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
import com.echothree.model.control.payment.server.control.PaymentProcessorTypeCodeControl;
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.data.payment.server.entity.PaymentProcessorTypeCode;
import com.echothree.model.data.payment.server.entity.PaymentProcessorTypeCodeDetail;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;

@GraphQLDescription("payment processor type code object")
@GraphQLName("PaymentProcessorTypeCode")
public class PaymentProcessorTypeCodeObject
        extends BaseEntityInstanceObject {
    
    private final PaymentProcessorTypeCode paymentProcessorTypeCode; // Always Present
    
    public PaymentProcessorTypeCodeObject(PaymentProcessorTypeCode paymentProcessorTypeCode) {
        super(paymentProcessorTypeCode.getPrimaryKey());
        
        this.paymentProcessorTypeCode = paymentProcessorTypeCode;
    }

    private PaymentProcessorTypeCodeDetail paymentProcessorTypeCodeDetail; // Optional, use getPaymentProcessorTypeCodeDetail()
    
    private PaymentProcessorTypeCodeDetail getPaymentProcessorTypeCodeDetail() {
        if(paymentProcessorTypeCodeDetail == null) {
            paymentProcessorTypeCodeDetail = paymentProcessorTypeCode.getLastDetail();
        }
        
        return paymentProcessorTypeCodeDetail;
    }

    @GraphQLField
    @GraphQLDescription("payment processor type code type")
    public PaymentProcessorTypeCodeTypeObject getPaymentProcessorTypeCodeType(final DataFetchingEnvironment env) {
        return PaymentSecurityUtils.getHasPaymentProcessorTypeCodeTypeAccess(env) ? new PaymentProcessorTypeCodeTypeObject(getPaymentProcessorTypeCodeDetail().getPaymentProcessorTypeCodeType()) : null;
    }

    @GraphQLField
    @GraphQLDescription("payment processor type code name")
    @GraphQLNonNull
    public String getPaymentProcessorTypeCodeName() {
        return getPaymentProcessorTypeCodeDetail().getPaymentProcessorTypeCodeName();
    }

    @GraphQLField
    @GraphQLDescription("is default")
    @GraphQLNonNull
    public boolean getIsDefault() {
        return getPaymentProcessorTypeCodeDetail().getIsDefault();
    }
    
    @GraphQLField
    @GraphQLDescription("sort order")
    @GraphQLNonNull
    public int getSortOrder() {
        return getPaymentProcessorTypeCodeDetail().getSortOrder();
    }
    
    @GraphQLField
    @GraphQLDescription("description")
    @GraphQLNonNull
    public String getDescription(final DataFetchingEnvironment env) {
        var paymentProcessorTypeCodeControl = Session.getModelController(PaymentProcessorTypeCodeControl.class);
        var userControl = Session.getModelController(UserControl.class);

        return paymentProcessorTypeCodeControl.getBestPaymentProcessorTypeCodeDescription(paymentProcessorTypeCode, userControl.getPreferredLanguageFromUserVisit(BaseGraphQl.getUserVisit(env)));
    }
    
}
