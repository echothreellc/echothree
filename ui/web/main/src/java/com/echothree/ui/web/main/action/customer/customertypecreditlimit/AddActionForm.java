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

package com.echothree.ui.web.main.action.customer.customertypecreditlimit;

import com.echothree.control.user.accounting.common.AccountingUtil;
import com.echothree.control.user.accounting.common.result.GetCurrencyChoicesResult;
import com.echothree.model.control.accounting.common.choice.CurrencyChoicesBean;
import com.echothree.view.client.web.struts.BaseActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import java.util.List;
import javax.naming.NamingException;
import org.apache.struts.util.LabelValueBean;

@SproutForm(name="CustomerTypeCreditLimitAdd")
public class AddActionForm
        extends BaseActionForm {
    
    private CurrencyChoicesBean currencyChoices;
    
    private String customerTypeName;
    private String currencyChoice;
    private String creditLimit;
    private String potentialCreditLimit;
    
    public void setupCurrencyChoices()
            throws NamingException {
        if(currencyChoices == null) {
            var form = AccountingUtil.getHome().getGetCurrencyChoicesForm();

            form.setDefaultCurrencyChoice(currencyChoice);
            form.setAllowNullChoice(String.valueOf(false));

            var commandResult = AccountingUtil.getHome().getCurrencyChoices(userVisitPK, form);
            var executionResult = commandResult.getExecutionResult();
            var getCurrencyChoicesResult = (GetCurrencyChoicesResult)executionResult.getResult();
            currencyChoices = getCurrencyChoicesResult.getCurrencyChoices();

            if(currencyChoice == null)
                currencyChoice = currencyChoices.getDefaultValue();
        }
    }
    
    public String getCustomerTypeName() {
        return customerTypeName;
    }
    
    public void setCustomerTypeName(String customerTypeName) {
        this.customerTypeName = customerTypeName;
    }
    
    public List<LabelValueBean> getCurrencyChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;
        
        setupCurrencyChoices();
        if(currencyChoices != null)
            choices = convertChoices(currencyChoices);
        
        return choices;
    }
    
    public void setCurrencyChoice(String currencyChoice) {
        this.currencyChoice = currencyChoice;
    }
    
    public String getCurrencyChoice()
            throws NamingException {
        setupCurrencyChoices();
        return currencyChoice;
    }
    
    public String getCreditLimit() {
        return creditLimit;
    }
    
    public void setCreditLimit(String creditLimit) {
        this.creditLimit = creditLimit;
    }
    
    public String getPotentialCreditLimit() {
        return potentialCreditLimit;
    }
    
    public void setPotentialCreditLimit(String potentialCreditLimit) {
        this.potentialCreditLimit = potentialCreditLimit;
    }
    
}
