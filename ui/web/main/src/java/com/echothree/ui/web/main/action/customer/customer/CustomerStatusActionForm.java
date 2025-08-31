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
import com.echothree.control.user.customer.common.result.GetCustomerStatusChoicesResult;
import com.echothree.model.control.customer.common.choice.CustomerStatusChoicesBean;
import com.echothree.view.client.web.struts.BaseActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import java.util.List;
import javax.naming.NamingException;
import org.apache.struts.util.LabelValueBean;

@SproutForm(name="CustomerStatus")
public class CustomerStatusActionForm
        extends BaseActionForm {
    
    private CustomerStatusChoicesBean customerStatusChoices;
    
    private String returnUrl;
    private String customerName;
    private String customerStatusChoice;
    
    public void setupCustomerStatusChoices()
            throws NamingException {
        if(customerStatusChoices == null) {
            var form = CustomerUtil.getHome().getGetCustomerStatusChoicesForm();

            form.setCustomerName(customerName);
            form.setDefaultCustomerStatusChoice(customerStatusChoice);
            form.setAllowNullChoice(String.valueOf(false));

            var commandResult = CustomerUtil.getHome().getCustomerStatusChoices(userVisitPK, form);
            var executionResult = commandResult.getExecutionResult();
            var result = (GetCustomerStatusChoicesResult)executionResult.getResult();
            customerStatusChoices = result.getCustomerStatusChoices();

            if(customerStatusChoice == null) {
                customerStatusChoice = customerStatusChoices.getDefaultValue();
            }
        }
    }
    
    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }

    public String getCustomerName() {
        return customerName;
    }
    
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
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
        if(customerStatusChoices != null) {
            choices = convertChoices(customerStatusChoices);
        }

        return choices;
    }
    
}
