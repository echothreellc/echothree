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

import com.echothree.control.user.customer.common.CustomerUtil;
import com.echothree.control.user.customer.common.result.GetCustomerCreditStatusChoicesResult;
import com.echothree.model.control.customer.common.choice.CustomerCreditStatusChoicesBean;
import com.echothree.view.client.web.struts.BaseActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import java.util.List;
import javax.naming.NamingException;
import org.apache.struts.util.LabelValueBean;

@SproutForm(name="CustomerCreditStatus")
public class CustomerCreditStatusActionForm
        extends BaseActionForm {
    
    private CustomerCreditStatusChoicesBean customerCreditStatusChoices;
    
    private String customerName;
    private String customerCreditStatusChoice;
    
    public void setupCustomerCreditStatusChoices()
            throws NamingException {
        if(customerCreditStatusChoices == null) {
            var form = CustomerUtil.getHome().getGetCustomerCreditStatusChoicesForm();

            form.setCustomerName(customerName);
            form.setDefaultCustomerCreditStatusChoice(customerCreditStatusChoice);
            form.setAllowNullChoice(String.valueOf(false));

            var commandResult = CustomerUtil.getHome().getCustomerCreditStatusChoices(userVisitPK, form);
            var executionResult = commandResult.getExecutionResult();
            var result = (GetCustomerCreditStatusChoicesResult)executionResult.getResult();
            customerCreditStatusChoices = result.getCustomerCreditStatusChoices();

            if(customerCreditStatusChoice == null)
                customerCreditStatusChoice = customerCreditStatusChoices.getDefaultValue();
        }
    }
    
    public String getCustomerName() {
        return customerName;
    }
    
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
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
    
}
