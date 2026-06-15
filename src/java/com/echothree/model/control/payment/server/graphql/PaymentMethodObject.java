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
import com.echothree.model.control.payment.common.PaymentMethodTypes;
import com.echothree.model.control.payment.server.control.PaymentMethodControl;
import com.echothree.model.control.selector.server.graphql.SelectorObject;
import com.echothree.model.control.selector.server.graphql.SelectorSecurityUtils;
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.data.payment.server.entity.PaymentMethod;
import com.echothree.model.data.payment.server.entity.PaymentMethodDetail;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;

@GraphQLDescription("payment method object")
@GraphQLName("PaymentMethod")
public class PaymentMethodObject
        extends BaseEntityInstanceObject {
    
    private final PaymentMethod paymentMethod; // Always Present
    
    public PaymentMethodObject(PaymentMethod paymentMethod) {
        super(paymentMethod.getPrimaryKey());
        
        this.paymentMethod = paymentMethod;
    }

    private PaymentMethodDetail paymentMethodDetail; // Optional, use getPaymentMethodDetail()
    
    private PaymentMethodDetail getPaymentMethodDetail() {
        if(paymentMethodDetail == null) {
            paymentMethodDetail = paymentMethod.getLastDetail();
        }
        
        return paymentMethodDetail;
    }
    
    @GraphQLField
    @GraphQLDescription("payment method name")
    @GraphQLNonNull
    public String getPaymentMethodName() {
        return getPaymentMethodDetail().getPaymentMethodName();
    }

    @GraphQLField
    @GraphQLDescription("payment method type")
    public PaymentMethodTypeObject getPaymentMethodType(final DataFetchingEnvironment env) {
        return PaymentSecurityUtils.getHasPaymentMethodTypeAccess(env) ? new PaymentMethodTypeObject(getPaymentMethodDetail().getPaymentMethodType()) : null;
    }

    @GraphQLField
    @GraphQLDescription("payment processor")
    public PaymentProcessorObject getPaymentProcessor(final DataFetchingEnvironment env) {
        if(PaymentSecurityUtils.getHasPaymentProcessorAccess(env)) {
            var paymentProcessor = getPaymentMethodDetail().getPaymentProcessor();

            return paymentProcessor == null ? null : new PaymentProcessorObject(paymentProcessor);
        }

        return null;
    }

    @GraphQLField
    @GraphQLDescription("item selector")
    public SelectorObject getItemSelector(final DataFetchingEnvironment env) {
        if(SelectorSecurityUtils.getHasSelectorAccess(env)) {
            var itemSelector = getPaymentMethodDetail().getItemSelector();

            return itemSelector == null ? null : new SelectorObject(itemSelector);
        }

        return null;
    }

    @GraphQLField
    @GraphQLDescription("sales order item selector")
    public SelectorObject getSalesOrderItemSelector(final DataFetchingEnvironment env) {
        if(SelectorSecurityUtils.getHasSelectorAccess(env)) {
            var salesOrderItemSelector = getPaymentMethodDetail().getSalesOrderItemSelector();

            return salesOrderItemSelector == null ? null : new SelectorObject(salesOrderItemSelector);
        }

        return null;
    }

    @GraphQLField
    @GraphQLDescription("is default")
    @GraphQLNonNull
    public boolean getIsDefault() {
        return getPaymentMethodDetail().getIsDefault();
    }
    
    @GraphQLField
    @GraphQLDescription("sort order")
    @GraphQLNonNull
    public int getSortOrder() {
        return getPaymentMethodDetail().getSortOrder();
    }
    
    @GraphQLField
    @GraphQLDescription("description")
    @GraphQLNonNull
    public String getDescription(final DataFetchingEnvironment env) {
        var paymentMethodControl = Session.getModelController(PaymentMethodControl.class);
        var userControl = Session.getModelController(UserControl.class);

        return paymentMethodControl.getBestPaymentMethodDescription(paymentMethod, userControl.getPreferredLanguageFromUserVisit(BaseGraphQl.getUserVisit(env)));
    }

    private PaymentMethodTypes paymentMethodTypeEnum = null; // Optional, use getPaymentMethodTypeEnum()

    protected PaymentMethodTypes getPaymentMethodTypeEnum() {
        if(paymentMethodTypeEnum == null) {
            paymentMethodTypeEnum = PaymentMethodTypes.valueOf(getPaymentMethodDetail().getPaymentMethodType().getLastDetail().getPaymentMethodTypeName());
        }

        return paymentMethodTypeEnum;
    }

    @GraphQLField
    @GraphQLDescription("payment method")
    public PaymentMethodInterface getPaymentMethod(final DataFetchingEnvironment env) {
        var paymentMethodControl = Session.getModelController(PaymentMethodControl.class);

        return switch(getPaymentMethodTypeEnum()) {
            case ACCOUNT -> null;
            case CHECK -> new PaymentMethodCheckObject(paymentMethodControl.getPaymentMethodCheck(paymentMethod));
            case COD -> null;
            case CREDIT_CARD -> new PaymentMethodCreditCardObject(paymentMethodControl.getPaymentMethodCreditCard(paymentMethod));
            case PREPAID -> null;
            case GIFT_CARD -> null;
            case GIFT_CERTIFICATE -> null;
        };
    }

}
