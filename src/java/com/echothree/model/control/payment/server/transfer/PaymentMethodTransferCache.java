// --------------------------------------------------------------------------------
// Copyright 2002-2025 Echo Three, LLC
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

package com.echothree.model.control.payment.server.transfer;

import com.echothree.model.control.comment.common.CommentConstants;
import com.echothree.model.control.payment.common.PaymentMethodTypes;
import com.echothree.model.control.payment.common.PaymentOptions;
import com.echothree.model.control.payment.common.transfer.PaymentMethodTransfer;
import com.echothree.model.control.payment.server.control.PaymentMethodControl;
import com.echothree.model.control.payment.server.control.PaymentMethodTypeControl;
import com.echothree.model.control.payment.server.control.PaymentProcessorControl;
import com.echothree.model.data.payment.server.entity.PaymentMethod;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class PaymentMethodTransferCache
        extends BasePaymentTransferCache<PaymentMethod, PaymentMethodTransfer> {

    PaymentMethodControl paymentMethodControl = Session.getModelController(PaymentMethodControl.class);
    PaymentMethodTypeControl paymentMethodTypeControl = Session.getModelController(PaymentMethodTypeControl.class);
    PaymentProcessorControl paymentProcessorControl = Session.getModelController(PaymentProcessorControl.class);

    boolean includeComments;

    /** Creates a new instance of PaymentMethodTransferCache */
    public PaymentMethodTransferCache(UserVisit userVisit) {
        super(userVisit);

        var options = session.getOptions();
        if(options != null) {
            setIncludeUuid(options.contains(PaymentOptions.PaymentMethodIncludeUuid));
            includeComments = options.contains(PaymentOptions.PaymentMethodIncludeComments);
            setIncludeEntityAttributeGroups(options.contains(PaymentOptions.PaymentMethodIncludeEntityAttributeGroups));
        }
        
        setIncludeEntityInstance(true);
    }

    @Override
    public PaymentMethodTransfer getTransfer(PaymentMethod paymentMethod) {
        var paymentMethodTransfer = get(paymentMethod);
        
        if(paymentMethodTransfer == null) {
            var paymentMethodDetail = paymentMethod.getLastDetail();
            var paymentMethodName = paymentMethodDetail.getPaymentMethodName();
            var paymentMethodTypeTransfer = paymentMethodTypeControl.getPaymentMethodTypeTransfer(userVisit, paymentMethodDetail.getPaymentMethodType());
            var paymentMethodTypeName = paymentMethodTypeTransfer.getPaymentMethodTypeName();
            var paymentProcessor = paymentMethodDetail.getPaymentProcessor();
            var paymentProcessorTransfer = paymentProcessor == null ? null : paymentProcessorControl.getPaymentProcessorTransfer(userVisit, paymentProcessor);
            var isDefault = paymentMethodDetail.getIsDefault();
            var sortOrder = paymentMethodDetail.getSortOrder();
            var description = paymentMethodControl.getBestPaymentMethodDescription(paymentMethod, getLanguage(userVisit));
            Boolean requestNameOnCard = null;
            Boolean requireNameOnCard = null;
            Boolean checkCardNumber = null;
            Boolean requestExpirationDate = null;
            Boolean requireExpirationDate = null;
            Boolean checkExpirationDate = null;
            Boolean requestSecurityCode = null;
            Boolean requireSecurityCode = null;
            String cardNumberValidationPattern = null;
            String securityCodeValidationPattern = null;
            Boolean retainCreditCard = null;
            Boolean retainSecurityCode = null;
            Boolean requestBilling = null;
            Boolean requireBilling = null;
            Boolean requestIssuer = null;
            Boolean requireIssuer = null;
            Integer holdDays = null;
            
            if(paymentMethodTypeName.equals(PaymentMethodTypes.CHECK.name())) {
                var paymentMethodCheck = paymentMethodControl.getPaymentMethodCheck(paymentMethod);
                
                holdDays = paymentMethodCheck.getHoldDays();
            } else if(paymentMethodTypeName.equals(PaymentMethodTypes.CREDIT_CARD.name())) {
                var paymentMethodCreditCard = paymentMethodControl.getPaymentMethodCreditCard(paymentMethod);
                
                requestNameOnCard = paymentMethodCreditCard.getRequestNameOnCard();
                requireNameOnCard = paymentMethodCreditCard.getRequireNameOnCard();
                checkCardNumber = paymentMethodCreditCard.getCheckCardNumber();
                requestExpirationDate = paymentMethodCreditCard.getRequestExpirationDate();
                requireExpirationDate = paymentMethodCreditCard.getRequireExpirationDate();
                checkExpirationDate = paymentMethodCreditCard.getCheckExpirationDate();
                requestSecurityCode = paymentMethodCreditCard.getRequestSecurityCode();
                requireSecurityCode = paymentMethodCreditCard.getRequireSecurityCode();
                cardNumberValidationPattern = paymentMethodCreditCard.getCardNumberValidationPattern();
                securityCodeValidationPattern = paymentMethodCreditCard.getSecurityCodeValidationPattern();
                retainCreditCard = paymentMethodCreditCard.getRetainCreditCard();
                retainSecurityCode = paymentMethodCreditCard.getRetainSecurityCode();
                requestBilling = paymentMethodCreditCard.getRequestBilling();
                requireBilling = paymentMethodCreditCard.getRequireBilling();
                requestIssuer = paymentMethodCreditCard.getRequestIssuer();
                requireIssuer = paymentMethodCreditCard.getRequireIssuer();
            }
            
            paymentMethodTransfer = new PaymentMethodTransfer(paymentMethodName, paymentMethodTypeTransfer, paymentProcessorTransfer,
                    isDefault, sortOrder, description, requestNameOnCard, requireNameOnCard, checkCardNumber,
                    requestExpirationDate, requireExpirationDate, checkExpirationDate, requestSecurityCode, requireSecurityCode, cardNumberValidationPattern,
                    securityCodeValidationPattern, retainCreditCard, retainSecurityCode, requestBilling, requireBilling, requestIssuer, requireIssuer, holdDays);
            put(userVisit, paymentMethod, paymentMethodTransfer);

            if(includeComments) {
                setupComments(userVisit, paymentMethod, null, paymentMethodTransfer, CommentConstants.CommentType_PAYMENT_METHOD);
            }
        }

        return paymentMethodTransfer;
    }
    
}
