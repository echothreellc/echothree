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

package com.echothree.ui.web.main.action.payment.paymentmethod.add;

import com.echothree.control.user.payment.common.PaymentUtil;
import com.echothree.control.user.payment.common.result.GetPaymentProcessorChoicesResult;
import com.echothree.control.user.selector.common.SelectorUtil;
import com.echothree.control.user.selector.common.result.GetSelectorChoicesResult;
import com.echothree.model.control.payment.common.choice.PaymentProcessorChoicesBean;
import com.echothree.model.control.selector.common.SelectorKinds;
import com.echothree.model.control.selector.common.SelectorTypes;
import com.echothree.model.control.selector.common.choice.SelectorChoicesBean;
import com.echothree.view.client.web.struts.BaseActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;

@SproutForm(name="PaymentMethodAddStep2")
public class Step2ActionForm
        extends BaseActionForm {
    
    private PaymentProcessorChoicesBean paymentProcessorChoices;
    private SelectorChoicesBean itemSelectorChoices;
    private SelectorChoicesBean salesOrderItemSelectorChoices;
    
    private String paymentMethodName;
    private String paymentMethodTypeName;
    private String paymentProcessorChoice;
    private String itemSelectorChoice;
    private String salesOrderItemSelectorChoice;
    private Boolean isDefault;
    private String sortOrder;
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
    private String holdDays;
    
    private void setupPaymentProcessorChoices()
            throws NamingException {
        if(paymentProcessorChoices == null) {
            var form = PaymentUtil.getHome().getGetPaymentProcessorChoicesForm();

            form.setDefaultPaymentProcessorChoice(paymentProcessorChoice);
            form.setAllowNullChoice(String.valueOf(true));

            var commandResult = PaymentUtil.getHome().getPaymentProcessorChoices(userVisitPK, form);
            var executionResult = commandResult.getExecutionResult();
            var getPaymentProcessorChoicesResult = (GetPaymentProcessorChoicesResult)executionResult.getResult();
            paymentProcessorChoices = getPaymentProcessorChoicesResult.getPaymentProcessorChoices();

            if(paymentProcessorChoice == null) {
                paymentProcessorChoice = paymentProcessorChoices.getDefaultValue();
            }
        }
    }
    
    public void setupItemSelectorChoices()
            throws NamingException {
        if(itemSelectorChoices == null) {
            var form = SelectorUtil.getHome().getGetSelectorChoicesForm();

            form.setSelectorKindName(SelectorKinds.ITEM.name());
            form.setSelectorTypeName(SelectorTypes.PAYMENT_METHOD.name());
            form.setDefaultSelectorChoice(itemSelectorChoice);
            form.setAllowNullChoice(String.valueOf(true));

            var commandResult = SelectorUtil.getHome().getSelectorChoices(userVisitPK, form);
            var executionResult = commandResult.getExecutionResult();
            var result = (GetSelectorChoicesResult)executionResult.getResult();
            itemSelectorChoices = result.getSelectorChoices();

            if(itemSelectorChoice == null) {
                itemSelectorChoice = itemSelectorChoices.getDefaultValue();
            }
        }
    }

    public void setupSalesOrderItemSelectorChoices()
            throws NamingException {
        if(salesOrderItemSelectorChoices == null) {
            var form = SelectorUtil.getHome().getGetSelectorChoicesForm();

            form.setSelectorKindName(SelectorKinds.SALES_ORDER_ITEM.name());
            form.setSelectorTypeName(SelectorTypes.PAYMENT_METHOD.name());
            form.setDefaultSelectorChoice(salesOrderItemSelectorChoice);
            form.setAllowNullChoice(String.valueOf(true));

            var commandResult = SelectorUtil.getHome().getSelectorChoices(userVisitPK, form);
            var executionResult = commandResult.getExecutionResult();
            var result = (GetSelectorChoicesResult)executionResult.getResult();
            salesOrderItemSelectorChoices = result.getSelectorChoices();

            if(salesOrderItemSelectorChoice == null) {
                salesOrderItemSelectorChoice = salesOrderItemSelectorChoices.getDefaultValue();
            }
        }
    }

    public void setPaymentMethodName(String paymentMethodName) {
        this.paymentMethodName = paymentMethodName;
    }
    
    public String getPaymentMethodName() {
        return paymentMethodName;
    }
    
    public String getPaymentMethodTypeName() {
        return paymentMethodTypeName;
    }
    
    public void setPaymentMethodTypeName(String paymentMethodTypeName) {
        this.paymentMethodTypeName = paymentMethodTypeName;
    }
    
    public String getPaymentProcessorChoice() {
        return paymentProcessorChoice;
    }
    
    public void setPaymentProcessorChoice(String paymentProcessorChoice) {
        this.paymentProcessorChoice = paymentProcessorChoice;
    }
    
    public List<LabelValueBean> getPaymentProcessorChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;
        
        setupPaymentProcessorChoices();
        if(paymentProcessorChoices != null) {
            choices = convertChoices(paymentProcessorChoices);
        }
        
        return choices;
    }
    
    public void setItemSelectorChoice(String itemSelectorChoice) {
        this.itemSelectorChoice = itemSelectorChoice;
    }

    public String getItemSelectorChoice()
            throws NamingException {
        setupItemSelectorChoices();

        return itemSelectorChoice;
    }

    public List<LabelValueBean> getItemSelectorChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;

        setupItemSelectorChoices();
        if(itemSelectorChoices != null) {
            choices = convertChoices(itemSelectorChoices);
        }

        return choices;
    }

    public void setSalesOrderItemSelectorChoice(String salesOrderItemSelectorChoice) {
        this.salesOrderItemSelectorChoice = salesOrderItemSelectorChoice;
    }

    public String getSalesOrderItemSelectorChoice()
            throws NamingException {
        setupSalesOrderItemSelectorChoices();

        return salesOrderItemSelectorChoice;
    }

    public List<LabelValueBean> getSalesOrderItemSelectorChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;

        setupSalesOrderItemSelectorChoices();
        if(salesOrderItemSelectorChoices != null) {
            choices = convertChoices(salesOrderItemSelectorChoices);
        }

        return choices;
    }

    public Boolean getIsDefault() {
        return isDefault;
    }
    
    public void setIsDefault(Boolean isDefault) {
        this.isDefault = isDefault;
    }
    
    public String getSortOrder() {
        return sortOrder;
    }
    
    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
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

    public String getHoldDays() {
        return holdDays;
    }
    
    public void setHoldDays(String holdDays) {
        this.holdDays = holdDays;
    }
    
    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        super.reset(mapping, request);
        
        setIsDefault(false);
        setRequestNameOnCard(false);
        setRequireNameOnCard(false);
        setCheckCardNumber(false);
        setRequestExpirationDate(false);
        setRequireExpirationDate(false);
        setCheckExpirationDate(false);
        setRequestSecurityCode(false);
        setRequireSecurityCode(false);
        setRetainCreditCard(false);
        setRetainSecurityCode(false);
        setRequestBilling(false);
        setRequireBilling(false);
        setRequestIssuer(false);
        setRequireIssuer(false);
    }
    
}
