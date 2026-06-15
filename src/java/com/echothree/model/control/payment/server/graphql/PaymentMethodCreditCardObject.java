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

import com.echothree.model.control.graphql.server.graphql.BaseObject;
import com.echothree.model.data.payment.server.entity.PaymentMethodCreditCard;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;

@GraphQLDescription("payment method creditCard object")
@GraphQLName("PaymentMethodCreditCard")
public class PaymentMethodCreditCardObject
        extends BaseObject
        implements PaymentMethodInterface {

    private final PaymentMethodCreditCard paymentMethodCreditCard; // Always Present

    public PaymentMethodCreditCardObject(PaymentMethodCreditCard paymentMethodCreditCard) {
        this.paymentMethodCreditCard = paymentMethodCreditCard;
    }

    @GraphQLField
    @GraphQLDescription("request name on card")
    @GraphQLNonNull
    public boolean getRequestNameOnCard() {
        return paymentMethodCreditCard.getRequestNameOnCard();
    }

    @GraphQLField
    @GraphQLDescription("require name on card")
    @GraphQLNonNull
    public boolean getRequireNameOnCard() {
        return paymentMethodCreditCard.getRequireNameOnCard();
    }

    @GraphQLField
    @GraphQLDescription("check card number")
    @GraphQLNonNull
    public boolean getCheckCardNumber() {
        return paymentMethodCreditCard.getCheckCardNumber();
    }

    @GraphQLField
    @GraphQLDescription("request expiration date")
    @GraphQLNonNull
    public boolean getRequestExpirationDate() {
        return paymentMethodCreditCard.getRequestExpirationDate();
    }

    @GraphQLField
    @GraphQLDescription("require expiration date")
    @GraphQLNonNull
    public boolean getRequireExpirationDate() {
        return paymentMethodCreditCard.getRequireExpirationDate();
    }

    @GraphQLField
    @GraphQLDescription("check expiration date")
    @GraphQLNonNull
    public boolean getCheckExpirationDate() {
        return paymentMethodCreditCard.getCheckExpirationDate();
    }

    @GraphQLField
    @GraphQLDescription("request security code")
    @GraphQLNonNull
    public boolean getRequestSecurityCode() {
        return paymentMethodCreditCard.getRequestSecurityCode();
    }

    @GraphQLField
    @GraphQLDescription("require security code")
    @GraphQLNonNull
    public boolean getRequireSecurityCode() {
        return paymentMethodCreditCard.getRequireSecurityCode();
    }

    @GraphQLField
    @GraphQLDescription("card number validation pattern")
    public String getCardNumberValidationPattern() {
        return paymentMethodCreditCard.getCardNumberValidationPattern();
    }

    @GraphQLField
    @GraphQLDescription("security code validation pattern")
    public String getSecurityCodeValidationPattern() {
        return paymentMethodCreditCard.getSecurityCodeValidationPattern();
    }

    @GraphQLField
    @GraphQLDescription("retain credit card")
    @GraphQLNonNull
    public boolean getRetainCreditCard() {
        return paymentMethodCreditCard.getRetainCreditCard();
    }

    @GraphQLField
    @GraphQLDescription("retain security code")
    @GraphQLNonNull
    public boolean getRetainSecurityCode() {
        return paymentMethodCreditCard.getRetainSecurityCode();
    }

    @GraphQLField
    @GraphQLDescription("request billing")
    @GraphQLNonNull
    public boolean getRequestBilling() {
        return paymentMethodCreditCard.getRequestBilling();
    }

    @GraphQLField
    @GraphQLDescription("require billing")
    @GraphQLNonNull
    public boolean getRequireBilling() {
        return paymentMethodCreditCard.getRequireBilling();
    }

    @GraphQLField
    @GraphQLDescription("request issuer")
    @GraphQLNonNull
    public boolean getRequestIssuer() {
        return paymentMethodCreditCard.getRequestIssuer();
    }

    @GraphQLField
    @GraphQLDescription("require issuer")
    @GraphQLNonNull
    public boolean getRequireIssuer() {
        return paymentMethodCreditCard.getRequireIssuer();
    }

}
