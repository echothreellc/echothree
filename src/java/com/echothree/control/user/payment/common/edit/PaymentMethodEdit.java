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

package com.echothree.control.user.payment.common.edit;

import com.echothree.control.user.payment.common.spec.PaymentMethodSpec;
import com.echothree.control.user.payment.common.spec.PaymentProcessorSpec;

public interface PaymentMethodEdit
        extends PaymentMethodSpec, PaymentProcessorSpec, PaymentMethodDescriptionEdit {
    
    String getItemSelectorName();
    void setItemSelectorName(String itemSelectorName);

    String getSalesOrderItemSelectorName();
    void setSalesOrderItemSelectorName(String salesOrderItemSelectorName);

    String getIsDefault();
    void setIsDefault(String isDefault);
    
    String getSortOrder();
    void setSortOrder(String sortOrder);
    
    String getHoldDays();
    void setHoldDays(String holdDays);
    
    String getRequestNameOnCard();
    void setRequestNameOnCard(String RequestNameOnCard);
    
    String getRequireNameOnCard();
    void setRequireNameOnCard(String RequireNameOnCard);
    
    String getCheckCardNumber();
    void setCheckCardNumber(String CheckCardNumber);
    
    String getRequestExpirationDate();
    void setRequestExpirationDate(String RequestExpirationDate);
    
    String getRequireExpirationDate();
    void setRequireExpirationDate(String RequireExpirationDate);
    
    String getCheckExpirationDate();
    void setCheckExpirationDate(String CheckExpirationDate);
    
    String getRequestSecurityCode();
    void setRequestSecurityCode(String RequestSecurityCode);
    
    String getRequireSecurityCode();
    void setRequireSecurityCode(String RequireSecurityCode);
    
    String getCardNumberValidationPattern();
    void setCardNumberValidationPattern(String CardNumberValidationPattern);
    
    String getSecurityCodeValidationPattern();
    void setSecurityCodeValidationPattern(String SecurityCodeValidationPattern);
    
    String getRetainCreditCard();
    void setRetainCreditCard(String RetainCreditCard);
    
    String getRetainSecurityCode();
    void setRetainSecurityCode(String RetainSecurityCode);

    String getRequestBilling();
    void setRequestBilling(String requestBilling);

    String getRequireBilling();
    void setRequireBilling(String requireBilling);

    String getRequestIssuer();
    void setRequestIssuer(String requestIssuer);

    String getRequireIssuer();
    void setRequireIssuer(String requireIssuer);
    
}
