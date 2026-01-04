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

package com.echothree.model.control.payment.common.transfer;

import com.echothree.util.common.transfer.BaseTransfer;

public class PaymentMethodTransfer
        extends BaseTransfer {
    
    private String paymentMethodName;
    private PaymentMethodTypeTransfer paymentMethodType;
    private PaymentProcessorTransfer paymentProcessor;
    private Boolean isDefault;
    private Integer sortOrder;
    private String description;
    
    // CREDIT_CARD
    private Boolean requestNameOnCard;
    private Boolean requireNameOnCard;
    private Boolean checkCardNumber;
    private Boolean requestExpirationDate;
    private Boolean requireExpirationDate;
    private Boolean checkExpirationDate;
    private Boolean requestSecurityCode;
    private Boolean requireSecurityCode;
    private String cardNumberValidationPattern;
    private String securityCodeValidationPattern;
    private Boolean retainCreditCard;
    private Boolean retainSecurityCode;
    private Boolean requestBilling;
    private Boolean requireBilling;
    private Boolean requestIssuer;
    private Boolean requireIssuer;
    
    // CHECK
    private Integer holdDays;
    
    /** Creates a new instance of PaymentMethodTransfer */
    public PaymentMethodTransfer(String paymentMethodName, PaymentMethodTypeTransfer paymentMethodType,
            PaymentProcessorTransfer paymentProcessor, Boolean isDefault, Integer sortOrder, String description, Boolean requestNameOnCard,
            Boolean requireNameOnCard, Boolean checkCardNumber, Boolean requestExpirationDate, Boolean requireExpirationDate,
            Boolean checkExpirationDate, Boolean requestSecurityCode, Boolean requireSecurityCode,
            String cardNumberValidationPattern, String securityCodeValidationPattern, Boolean retainCreditCard,
            Boolean retainSecurityCode, Boolean requestBilling, Boolean requireBilling, Boolean requestIssuer, Boolean requireIssuer, Integer holdDays) {
        this.paymentMethodName = paymentMethodName;
        this.paymentMethodType = paymentMethodType;
        this.paymentProcessor = paymentProcessor;
        this.isDefault = isDefault;
        this.sortOrder = sortOrder;
        this.description = description;
        this.requestNameOnCard = requestNameOnCard;
        this.requireNameOnCard = requireNameOnCard;
        this.checkCardNumber = checkCardNumber;
        this.requestExpirationDate = requestExpirationDate;
        this.requireExpirationDate = requireExpirationDate;
        this.checkExpirationDate = checkExpirationDate;
        this.requestSecurityCode = requestSecurityCode;
        this.requireSecurityCode = requireSecurityCode;
        this.cardNumberValidationPattern = cardNumberValidationPattern;
        this.securityCodeValidationPattern = securityCodeValidationPattern;
        this.retainCreditCard = retainCreditCard;
        this.retainSecurityCode = retainSecurityCode;
        this.requestBilling = requestBilling;
        this.requireBilling = requireBilling;
        this.requestIssuer = requestIssuer;
        this.requireIssuer = requireIssuer;
        this.holdDays = holdDays;
    }
    
    public String getPaymentMethodName() {
        return paymentMethodName;
    }
    
    public void setPaymentMethodName(String paymentMethodName) {
        this.paymentMethodName = paymentMethodName;
    }
    
    public PaymentMethodTypeTransfer getPaymentMethodType() {
        return paymentMethodType;
    }
    
    public void setPaymentMethodType(PaymentMethodTypeTransfer paymentMethodType) {
        this.paymentMethodType = paymentMethodType;
    }
    
    public PaymentProcessorTransfer getPaymentProcessorTransfer() {
        return getPaymentProcessor();
    }
    
    public void setPaymentProcessorTransfer(PaymentProcessorTransfer paymentProcessor) {
        this.paymentProcessor = paymentProcessor;
    }
    
    public Boolean getIsDefault() {
        return isDefault;
    }
    
    public void setIsDefault(Boolean isDefault) {
        this.isDefault = isDefault;
    }
    
    public Integer getSortOrder() {
        return sortOrder;
    }
    
    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public PaymentProcessorTransfer getPaymentProcessor() {
        return paymentProcessor;
    }
    
    public void setPaymentProcessor(PaymentProcessorTransfer paymentProcessor) {
        this.paymentProcessor = paymentProcessor;
    }
    
    public Boolean getRequestNameOnCard() {
        return requestNameOnCard;
    }
    
    public void setRequestNameOnCard(Boolean requestNameOnCard) {
        this.requestNameOnCard = requestNameOnCard;
    }
    
    public Boolean getRequireNameOnCard() {
        return requireNameOnCard;
    }
    
    public void setRequireNameOnCard(Boolean requireNameOnCard) {
        this.requireNameOnCard = requireNameOnCard;
    }
    
    public Boolean getCheckCardNumber() {
        return checkCardNumber;
    }
    
    public void setCheckCardNumber(Boolean checkCardNumber) {
        this.checkCardNumber = checkCardNumber;
    }
    
    public Boolean getRequestExpirationDate() {
        return requestExpirationDate;
    }
    
    public void setRequestExpirationDate(Boolean requestExpirationDate) {
        this.requestExpirationDate = requestExpirationDate;
    }
    
    public Boolean getRequireExpirationDate() {
        return requireExpirationDate;
    }
    
    public void setRequireExpirationDate(Boolean requireExpirationDate) {
        this.requireExpirationDate = requireExpirationDate;
    }
    
    public Boolean getCheckExpirationDate() {
        return checkExpirationDate;
    }
    
    public void setCheckExpirationDate(Boolean checkExpirationDate) {
        this.checkExpirationDate = checkExpirationDate;
    }
    
    public Boolean getRequestSecurityCode() {
        return requestSecurityCode;
    }
    
    public void setRequestSecurityCode(Boolean requestSecurityCode) {
        this.requestSecurityCode = requestSecurityCode;
    }
    
    public Boolean getRequireSecurityCode() {
        return requireSecurityCode;
    }
    
    public void setRequireSecurityCode(Boolean requireSecurityCode) {
        this.requireSecurityCode = requireSecurityCode;
    }
    
    public String getCardNumberValidationPattern() {
        return cardNumberValidationPattern;
    }
    
    public void setCardNumberValidationPattern(String cardNumberValidationPattern) {
        this.cardNumberValidationPattern = cardNumberValidationPattern;
    }
    
    public String getSecurityCodeValidationPattern() {
        return securityCodeValidationPattern;
    }
    
    public void setSecurityCodeValidationPattern(String securityCodeValidationPattern) {
        this.securityCodeValidationPattern = securityCodeValidationPattern;
    }
    
    public Boolean getRetainCreditCard() {
        return retainCreditCard;
    }
    
    public void setRetainCreditCard(Boolean retainCreditCard) {
        this.retainCreditCard = retainCreditCard;
    }
    
    public Boolean getRetainSecurityCode() {
        return retainSecurityCode;
    }
    
    public void setRetainSecurityCode(Boolean retainSecurityCode) {
        this.retainSecurityCode = retainSecurityCode;
    }
    
    public Boolean getRequestBilling() {
        return requestBilling;
    }

    public void setRequestBilling(Boolean requestBilling) {
        this.requestBilling = requestBilling;
    }

    public Boolean getRequireBilling() {
        return requireBilling;
    }

    public void setRequireBilling(Boolean requireBilling) {
        this.requireBilling = requireBilling;
    }

    public Boolean getRequestIssuer() {
        return requestIssuer;
    }

    public void setRequestIssuer(Boolean requestIssuer) {
        this.requestIssuer = requestIssuer;
    }

    public Boolean getRequireIssuer() {
        return requireIssuer;
    }

    public void setRequireIssuer(Boolean requireIssuer) {
        this.requireIssuer = requireIssuer;
    }

    public Integer getHoldDays() {
        return holdDays;
    }
    
    public void setHoldDays(Integer holdDays) {
        this.holdDays = holdDays;
    }
    
}
