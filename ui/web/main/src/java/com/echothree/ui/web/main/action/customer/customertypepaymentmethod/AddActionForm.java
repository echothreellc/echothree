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

package com.echothree.ui.web.main.action.customer.customertypepaymentmethod;

import com.echothree.control.user.payment.common.PaymentUtil;
import com.echothree.control.user.payment.common.result.GetPaymentMethodChoicesResult;
import com.echothree.model.control.payment.common.choice.PaymentMethodChoicesBean;
import com.echothree.view.client.web.struts.BaseActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;

@SproutForm(name="CustomerTypePaymentMethodAdd")
public class AddActionForm
        extends BaseActionForm {
    
    private PaymentMethodChoicesBean paymentMethodChoices;
    
    private String customerTypeName;
    private String paymentMethodChoice;
    private String defaultSelectionPriority;
    private Boolean isDefault;
    private String sortOrder;
    
    private void setupPaymentMethodChoices()
            throws NamingException {
        if(paymentMethodChoices == null) {
            var form = PaymentUtil.getHome().getGetPaymentMethodChoicesForm();
                
                form.setDefaultPaymentMethodChoice(paymentMethodChoice);
                form.setAllowNullChoice(String.valueOf(false));

            var commandResult = PaymentUtil.getHome().getPaymentMethodChoices(userVisitPK, form);
            var executionResult = commandResult.getExecutionResult();
            var getPaymentMethodChoicesResult = (GetPaymentMethodChoicesResult)executionResult.getResult();
                paymentMethodChoices = getPaymentMethodChoicesResult.getPaymentMethodChoices();
                
                if(paymentMethodChoice == null)
                    paymentMethodChoice = paymentMethodChoices.getDefaultValue();
        }
    }
    
    public void setCustomerTypeName(String customerTypeName) {
        this.customerTypeName = customerTypeName;
    }
    
    public String getCustomerTypeName() {
        return customerTypeName;
    }
    
    public String getPaymentMethodChoice() {
        return paymentMethodChoice;
    }
    
    public void setPaymentMethodChoice(String paymentMethodChoice) {
        this.paymentMethodChoice = paymentMethodChoice;
    }
    
    public List<LabelValueBean> getPaymentMethodChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;
        
        setupPaymentMethodChoices();
        if(paymentMethodChoices != null)
            choices = convertChoices(paymentMethodChoices);
        
        return choices;
    }
    
    public String getDefaultSelectionPriority() {
        return defaultSelectionPriority;
    }
    
    public void setDefaultSelectionPriority(String defaultSelectionPriority) {
        this.defaultSelectionPriority = defaultSelectionPriority;
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
