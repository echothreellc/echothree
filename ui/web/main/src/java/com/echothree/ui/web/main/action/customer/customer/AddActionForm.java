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
import com.echothree.control.user.customer.common.result.GetCustomerCreditStatusChoicesResult;
import com.echothree.control.user.customer.common.result.GetCustomerStatusChoicesResult;
import com.echothree.control.user.customer.common.result.GetCustomerTypeChoicesResult;
import com.echothree.control.user.offer.common.OfferUtil;
import com.echothree.control.user.offer.common.result.GetSourceChoicesResult;
import com.echothree.control.user.returnpolicy.common.ReturnPolicyUtil;
import com.echothree.control.user.returnpolicy.common.result.GetReturnPolicyChoicesResult;
import com.echothree.model.control.accounting.common.AccountingConstants;
import com.echothree.model.control.accounting.common.choice.GlAccountChoicesBean;
import com.echothree.model.control.cancellationpolicy.common.CancellationKinds;
import com.echothree.model.control.cancellationpolicy.common.choice.CancellationPolicyChoicesBean;
import com.echothree.model.control.customer.common.choice.CustomerCreditStatusChoicesBean;
import com.echothree.model.control.customer.common.choice.CustomerStatusChoicesBean;
import com.echothree.model.control.customer.common.choice.CustomerTypeChoicesBean;
import com.echothree.model.control.offer.common.choice.SourceChoicesBean;
import com.echothree.model.control.returnpolicy.common.ReturnKinds;
import com.echothree.model.control.returnpolicy.common.choice.ReturnPolicyChoicesBean;
import com.echothree.view.client.web.struts.BasePersonActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;

@SproutForm(name="CustomerAdd")
public class AddActionForm
        extends BasePersonActionForm {
    
    private CustomerTypeChoicesBean customerTypeChoices;
    private CancellationPolicyChoicesBean cancellationPolicyChoices;
    private ReturnPolicyChoicesBean returnPolicyChoices;
    private GlAccountChoicesBean arGlAccountChoices;
    private SourceChoicesBean initialSourceChoices;
    private CustomerStatusChoicesBean customerStatusChoices;
    private CustomerCreditStatusChoicesBean customerCreditStatusChoices;
    
    private String customerTypeChoice;
    private String cancellationPolicyChoice;
    private String returnPolicyChoice;
    private String arGlAccountChoice;
    private String initialSourceChoice;
    private String name;
    private String emailAddress;
    private Boolean allowSolicitation;
    private String customerStatusChoice;
    private String customerCreditStatusChoice;
    
    /** Creates a new instance of AddActionForm */
    public AddActionForm() {
        super();
    }
    
    private void setupCustomerTypeChoices()
            throws NamingException {
        if(customerTypeChoices == null) {
            var form = CustomerUtil.getHome().getGetCustomerTypeChoicesForm();

            form.setDefaultCustomerTypeChoice(customerTypeChoice);
            form.setAllowNullChoice(String.valueOf(true));

            var commandResult = CustomerUtil.getHome().getCustomerTypeChoices(userVisitPK, form);
            var executionResult = commandResult.getExecutionResult();
            var result = (GetCustomerTypeChoicesResult)executionResult.getResult();
            customerTypeChoices = result.getCustomerTypeChoices();

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

    public void setupInitialSourceChoices()
            throws NamingException {
        if(initialSourceChoices == null) {
            var form = OfferUtil.getHome().getGetSourceChoicesForm();

            form.setDefaultSourceChoice(initialSourceChoice);
            form.setAllowNullChoice(String.valueOf(true));

            var commandResult = OfferUtil.getHome().getSourceChoices(userVisitPK, form);
            var executionResult = commandResult.getExecutionResult();
            var result = (GetSourceChoicesResult)executionResult.getResult();
            initialSourceChoices = result.getSourceChoices();

            if(initialSourceChoice == null) {
                initialSourceChoice = initialSourceChoices.getDefaultValue();
            }
        }
    }
    
    public void setupCustomerStatusChoices()
            throws NamingException {
        if(customerStatusChoices == null) {
            var form = CustomerUtil.getHome().getGetCustomerStatusChoicesForm();

            form.setDefaultCustomerStatusChoice(customerStatusChoice);
            form.setAllowNullChoice(String.valueOf(true));

            var commandResult = CustomerUtil.getHome().getCustomerStatusChoices(userVisitPK, form);
            var executionResult = commandResult.getExecutionResult();
            var result = (GetCustomerStatusChoicesResult)executionResult.getResult();
            customerStatusChoices = result.getCustomerStatusChoices();

            if(customerStatusChoice == null) {
                customerStatusChoice = customerStatusChoices.getDefaultValue();
            }
        }
    }
    
    public void setupCustomerCreditStatusChoices()
            throws NamingException {
        if(customerCreditStatusChoices == null) {
            var form = CustomerUtil.getHome().getGetCustomerCreditStatusChoicesForm();

            form.setDefaultCustomerCreditStatusChoice(customerCreditStatusChoice);
            form.setAllowNullChoice(String.valueOf(true));

            var commandResult = CustomerUtil.getHome().getCustomerCreditStatusChoices(userVisitPK, form);
            var executionResult = commandResult.getExecutionResult();
            var result = (GetCustomerCreditStatusChoicesResult)executionResult.getResult();
            customerCreditStatusChoices = result.getCustomerCreditStatusChoices();

            if(customerCreditStatusChoice == null) {
                customerCreditStatusChoice = customerCreditStatusChoices.getDefaultValue();
            }
        }
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

    public List<LabelValueBean> getInitialSourceChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;
        
        setupInitialSourceChoices();
        if(initialSourceChoices != null) {
            choices = convertChoices(initialSourceChoices);
        }
        
        return choices;
    }
    
    public void setInitialSourceChoice(String initialSourceChoice) {
        this.initialSourceChoice = initialSourceChoice;
    }
    
    public String getInitialSourceChoice()
            throws NamingException {
        setupInitialSourceChoices();
        
        return initialSourceChoice;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getEmailAddress() {
        return emailAddress;
    }
    
    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }
    
    public Boolean getAllowSolicitation() {
        return allowSolicitation;
    }
    
    public void setAllowSolicitation(Boolean allowSolicitation) {
        this.allowSolicitation = allowSolicitation;
    }
    
    public String getCustomerStatusChoice() {
        return customerStatusChoice;
    }
    
    public void setCustomerStatusChoice(String customerStatusChoice) {
        this.customerStatusChoice = customerStatusChoice;
    }
    
    public List<LabelValueBean> getCustomerStatusChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;
        
        setupCustomerStatusChoices();
        if(customerStatusChoices != null)
            choices = convertChoices(customerStatusChoices);
        
        return choices;
    }
    
    public String getCustomerCreditStatusChoice() {
        return customerCreditStatusChoice;
    }
    
    public void setCustomerCreditStatusChoice(String customerCreditStatusChoice) {
        this.customerCreditStatusChoice = customerCreditStatusChoice;
    }
    
    public List<LabelValueBean> getCustomerCreditStatusChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;
        
        setupCustomerCreditStatusChoices();
        if(customerCreditStatusChoices != null)
            choices = convertChoices(customerCreditStatusChoices);
        
        return choices;
    }
    
    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        super.reset(mapping, request);
        
        setAllowSolicitation(false);
    }
    
}
