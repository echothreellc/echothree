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

package com.echothree.ui.web.main.action.advertising.offercustomertype;

import com.echothree.control.user.customer.common.CustomerUtil;
import com.echothree.control.user.customer.common.result.GetCustomerTypeChoicesResult;
import com.echothree.model.control.customer.common.choice.CustomerTypeChoicesBean;
import com.echothree.view.client.web.struts.BaseActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;

@SproutForm(name="OfferCustomerTypeAdd")
public class AddActionForm
        extends BaseActionForm {
    
    private CustomerTypeChoicesBean customerTypeChoices;
    
    private String offerName;
    private String customerTypeChoice;
    private Boolean isDefault;
    private String sortOrder;
    
    private void setupCustomerTypeChoices()
            throws NamingException {
        if(customerTypeChoices == null) {
            var form = CustomerUtil.getHome().getGetCustomerTypeChoicesForm();

            form.setDefaultCustomerTypeChoice(customerTypeChoice);
            form.setAllowNullChoice(String.valueOf(false));

            var commandResult = CustomerUtil.getHome().getCustomerTypeChoices(userVisitPK, form);
            var executionResult = commandResult.getExecutionResult();
            var result = (GetCustomerTypeChoicesResult)executionResult.getResult();
            customerTypeChoices = result.getCustomerTypeChoices();

            if(customerTypeChoice == null)
                customerTypeChoice = customerTypeChoices.getDefaultValue();
        }
    }
    
    public void setOfferName(String offerName) {
        this.offerName = offerName;
    }
    
    public String getOfferName() {
        return offerName;
    }
    
    public String getCustomerTypeChoice() {
        return customerTypeChoice;
    }
    
    public void setCustomerTypeChoice(String customerTypeChoice) {
        this.customerTypeChoice = customerTypeChoice;
    }
    
    public List<LabelValueBean> getCustomerTypeChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;
        
        setupCustomerTypeChoices();
        if(customerTypeChoices != null)
            choices = convertChoices(customerTypeChoices);
        
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

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        super.reset(mapping, request);

        isDefault = false;
    }

}
