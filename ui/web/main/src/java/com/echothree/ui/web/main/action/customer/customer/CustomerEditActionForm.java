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

package com.echothree.ui.web.main.action.customer.customer;

import com.echothree.control.user.accounting.common.AccountingUtil;
import com.echothree.control.user.accounting.common.result.GetGlAccountChoicesResult;
import com.echothree.control.user.cancellationpolicy.common.CancellationPolicyUtil;
import com.echothree.control.user.cancellationpolicy.common.result.GetCancellationPolicyChoicesResult;
import com.echothree.control.user.customer.common.CustomerUtil;
import com.echothree.control.user.customer.common.result.GetCustomerTypeChoicesResult;
import com.echothree.control.user.returnpolicy.common.ReturnPolicyUtil;
import com.echothree.control.user.returnpolicy.common.result.GetReturnPolicyChoicesResult;
import com.echothree.model.control.accounting.common.AccountingConstants;
import com.echothree.model.control.accounting.common.choice.GlAccountChoicesBean;
import com.echothree.model.control.cancellationpolicy.common.CancellationKinds;
import com.echothree.model.control.cancellationpolicy.common.choice.CancellationPolicyChoicesBean;
import com.echothree.model.control.customer.common.choice.CustomerTypeChoicesBean;
import com.echothree.model.control.returnpolicy.common.ReturnKinds;
import com.echothree.model.control.returnpolicy.common.choice.ReturnPolicyChoicesBean;
import com.echothree.view.client.web.struts.BasePersonActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;

@SproutForm(name="CustomerEdit")
public class CustomerEditActionForm
        extends BasePersonActionForm {
    
    private CustomerTypeChoicesBean customerTypeChoices;
    private CancellationPolicyChoicesBean cancellationPolicyChoices;
    private ReturnPolicyChoicesBean returnPolicyChoices;
    private GlAccountChoicesBean arGlAccountChoices;
    
    private String customerName;
    private String customerTypeChoice;
    private String cancellationPolicyChoice;
    private String returnPolicyChoice;
    private String arGlAccountChoice;
    private String name;
    private Boolean holdUntilComplete;
    private Boolean allowBackorders;
    private Boolean allowSubstitutions;
    private Boolean allowCombiningShipments;
    private Boolean requireReference;
    private Boolean allowReferenceDuplicates;
    private String referenceValidationPattern;
    
    public void setupCustomerTypeChoices()
            throws NamingException {
        if(customerTypeChoices == null) {
            var form = CustomerUtil.getHome().getGetCustomerTypeChoicesForm();

            form.setDefaultCustomerTypeChoice(customerTypeChoice);
            form.setAllowNullChoice(String.valueOf(false));

            var commandResult = CustomerUtil.getHome().getCustomerTypeChoices(userVisitPK, form);
            var executionResult = commandResult.getExecutionResult();
            var getCustomerTypeChoicesResult = (GetCustomerTypeChoicesResult)executionResult.getResult();
            customerTypeChoices = getCustomerTypeChoicesResult.getCustomerTypeChoices();

            if(customerTypeChoice == null) {
                customerTypeChoice = customerTypeChoices.getDefaultValue();
            }
        }
    }
    
    public void setupCancellationPolicyChoices()
            throws NamingException {
        if(cancellationPolicyChoices == null) {
            var form = CancellationPolicyUtil.getHome().getGetCancellationPolicyChoicesForm();

            form.setCancellationKindName(CancellationKinds.CUSTOMER_CANCELLATION.name());
            form.setDefaultCancellationPolicyChoice(cancellationPolicyChoice);
            form.setAllowNullChoice(String.valueOf(true));

            var commandResult = CancellationPolicyUtil.getHome().getCancellationPolicyChoices(userVisitPK, form);
            var executionResult = commandResult.getExecutionResult();
            var result = (GetCancellationPolicyChoicesResult)executionResult.getResult();
            cancellationPolicyChoices = result.getCancellationPolicyChoices();

            if(cancellationPolicyChoice == null)
                cancellationPolicyChoice = cancellationPolicyChoices.getDefaultValue();
        }
    }

    public void setupReturnPolicyChoices()
            throws NamingException {
        if(returnPolicyChoices == null) {
            var form = ReturnPolicyUtil.getHome().getGetReturnPolicyChoicesForm();

            form.setReturnKindName(ReturnKinds.CUSTOMER_RETURN.name());
            form.setDefaultReturnPolicyChoice(returnPolicyChoice);
            form.setAllowNullChoice(String.valueOf(true));

            var commandResult = ReturnPolicyUtil.getHome().getReturnPolicyChoices(userVisitPK, form);
            var executionResult = commandResult.getExecutionResult();
            var result = (GetReturnPolicyChoicesResult)executionResult.getResult();
            returnPolicyChoices = result.getReturnPolicyChoices();

            if(returnPolicyChoice == null)
                returnPolicyChoice = returnPolicyChoices.getDefaultValue();
        }
    }

    public void setupArGlAccountChoices()
            throws NamingException {
        if(arGlAccountChoices == null) {
            var form = AccountingUtil.getHome().getGetGlAccountChoicesForm();

            form.setGlAccountCategoryName(AccountingConstants.GlAccountCategory_ACCOUNTS_RECEIVABLE);
            form.setDefaultGlAccountChoice(arGlAccountChoice);
            form.setAllowNullChoice(String.valueOf(true));

            var commandResult = AccountingUtil.getHome().getGlAccountChoices(userVisitPK, form);
            var executionResult = commandResult.getExecutionResult();
            var getGlAccountChoicesResult = (GetGlAccountChoicesResult)executionResult.getResult();
            arGlAccountChoices = getGlAccountChoicesResult.getGlAccountChoices();

            if(arGlAccountChoice == null) {
                arGlAccountChoice = arGlAccountChoices.getDefaultValue();
            }
        }
    }
    
    public String getCustomerName() {
        return customerName;
    }
    
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    
    public List<LabelValueBean> getCustomerTypeChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;
        
        setupCustomerTypeChoices();
        if(customerTypeChoices != null) {
            choices = convertChoices(customerTypeChoices);
        }
        
        return choices;
    }
    
    public void setCustomerTypeChoice(String customerTypeChoice) {
        this.customerTypeChoice = customerTypeChoice;
    }
    
    public String getCustomerTypeChoice()
            throws NamingException {
        setupCustomerTypeChoices();
        
        return customerTypeChoice;
    }
    
    public List<LabelValueBean> getCancellationPolicyChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;

        setupCancellationPolicyChoices();
        if(cancellationPolicyChoices != null)
            choices = convertChoices(cancellationPolicyChoices);

        return choices;
    }

    public void setCancellationPolicyChoice(String cancellationPolicyChoice) {
        this.cancellationPolicyChoice = cancellationPolicyChoice;
    }

    public String getCancellationPolicyChoice()
            throws NamingException {
        setupCancellationPolicyChoices();

        return cancellationPolicyChoice;
    }

    public List<LabelValueBean> getReturnPolicyChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;

        setupReturnPolicyChoices();
        if(returnPolicyChoices != null)
            choices = convertChoices(returnPolicyChoices);

        return choices;
    }

    public void setReturnPolicyChoice(String returnPolicyChoice) {
        this.returnPolicyChoice = returnPolicyChoice;
    }

    public String getReturnPolicyChoice()
            throws NamingException {
        setupReturnPolicyChoices();

        return returnPolicyChoice;
    }

    public List<LabelValueBean> getArGlAccountChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;

        setupArGlAccountChoices();
        if(arGlAccountChoices != null) {
            choices = convertChoices(arGlAccountChoices);
        }

        return choices;
    }

    public void setArGlAccountChoice(String arGlAccountChoice) {
        this.arGlAccountChoice = arGlAccountChoice;
    }

    public String getArGlAccountChoice()
            throws NamingException {
        setupArGlAccountChoices();

        return arGlAccountChoice;
    }

    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public Boolean getHoldUntilComplete() {
        return holdUntilComplete;
    }

    public void setHoldUntilComplete(Boolean holdUntilComplete) {
        this.holdUntilComplete = holdUntilComplete;
    }

    public Boolean getAllowBackorders() {
        return allowBackorders;
    }

    public void setAllowBackorders(Boolean allowBackorders) {
        this.allowBackorders = allowBackorders;
    }

    public Boolean getAllowSubstitutions() {
        return allowSubstitutions;
    }

    public void setAllowSubstitutions(Boolean allowSubstitutions) {
        this.allowSubstitutions = allowSubstitutions;
    }

    public Boolean getAllowCombiningShipments() {
        return allowCombiningShipments;
    }

    public void setAllowCombiningShipments(Boolean allowCombiningShipments) {
        this.allowCombiningShipments = allowCombiningShipments;
    }

    public Boolean getRequireReference() {
        return requireReference;
    }

    public void setRequireReference(Boolean requireReference) {
        this.requireReference = requireReference;
    }

    public Boolean getAllowReferenceDuplicates() {
        return allowReferenceDuplicates;
    }

    public void setAllowReferenceDuplicates(Boolean allowReferenceDuplicates) {
        this.allowReferenceDuplicates = allowReferenceDuplicates;
    }
    
    public String getReferenceValidationPattern() {
        return referenceValidationPattern;
    }

    public void setReferenceValidationPattern(String referenceValidationPattern) {
        this.referenceValidationPattern = referenceValidationPattern;
    }
    
    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        super.reset(mapping, request);
        
        setHoldUntilComplete(false);
        setAllowBackorders(false);
        setAllowSubstitutions(false);
        setAllowCombiningShipments(false);
        setRequireReference(false);
        setAllowReferenceDuplicates(false);
    }

}
