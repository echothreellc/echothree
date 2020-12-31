// --------------------------------------------------------------------------------
// Copyright 2002-2021 Echo Three, LLC
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

package com.echothree.ui.web.main.action.salesorder.salesorderbatch;

import com.echothree.control.user.accounting.common.AccountingUtil;
import com.echothree.control.user.accounting.common.form.GetCurrencyChoicesForm;
import com.echothree.control.user.accounting.common.result.GetCurrencyChoicesResult;
import com.echothree.control.user.payment.common.PaymentUtil;
import com.echothree.control.user.payment.common.form.GetPaymentMethodChoicesForm;
import com.echothree.control.user.payment.common.result.GetPaymentMethodChoicesResult;
import com.echothree.model.control.accounting.common.choice.CurrencyChoicesBean;
import com.echothree.model.control.payment.common.choice.PaymentMethodChoicesBean;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.ExecutionResult;
import com.echothree.view.client.web.struts.BaseActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;

@SproutForm(name="SalesOrderBatchAdd")
public class AddActionForm
        extends BaseActionForm {
    
    private CurrencyChoicesBean currencyChoices;
    private PaymentMethodChoicesBean paymentMethodChoices;
    
    private String currencyChoice;
    private String paymentMethodChoice;
    private String count;
    private String amount;
    
    public void setupCurrencyChoices() {
        if(currencyChoices == null) {
            try {
                GetCurrencyChoicesForm form = AccountingUtil.getHome().getGetCurrencyChoicesForm();
                
                form.setDefaultCurrencyChoice(currencyChoice);
                form.setAllowNullChoice(Boolean.FALSE.toString());
                
                CommandResult commandResult = AccountingUtil.getHome().getCurrencyChoices(userVisitPK, form);
                ExecutionResult executionResult = commandResult.getExecutionResult();
                GetCurrencyChoicesResult getCurrencyChoicesResult = (GetCurrencyChoicesResult)executionResult.getResult();
                currencyChoices = getCurrencyChoicesResult.getCurrencyChoices();
                
                if(currencyChoice == null)
                    currencyChoice = currencyChoices.getDefaultValue();
            } catch (NamingException ne) {
                ne.printStackTrace();
                // failed, currencyChoices remains null, no default
            }
        }
    }
    
    public void setupPaymentMethodChoices() {
        if(paymentMethodChoices == null) {
            try {
                GetPaymentMethodChoicesForm form = PaymentUtil.getHome().getGetPaymentMethodChoicesForm();
                
                form.setDefaultPaymentMethodChoice(paymentMethodChoice);
                form.setAllowNullChoice(Boolean.FALSE.toString());
                
                CommandResult commandResult = PaymentUtil.getHome().getPaymentMethodChoices(userVisitPK, form);
                ExecutionResult executionResult = commandResult.getExecutionResult();
                GetPaymentMethodChoicesResult getPaymentMethodChoicesResult = (GetPaymentMethodChoicesResult)executionResult.getResult();
                paymentMethodChoices = getPaymentMethodChoicesResult.getPaymentMethodChoices();
                
                if(paymentMethodChoice == null)
                    paymentMethodChoice = paymentMethodChoices.getDefaultValue();
            } catch (NamingException ne) {
                ne.printStackTrace();
                // failed, paymentMethodChoices remains null, no default
            }
        }
    }
    
    public List<LabelValueBean> getCurrencyChoices() {
        List<LabelValueBean> choices = null;
        
        setupCurrencyChoices();
        if(currencyChoices != null)
            choices = convertChoices(currencyChoices);
        
        return choices;
    }
    
    public void setCurrencyChoice(String currencyChoice) {
        this.currencyChoice = currencyChoice;
    }
    
    public String getCurrencyChoice() {
        setupCurrencyChoices();
        return currencyChoice;
    }
    
    public List<LabelValueBean> getPaymentMethodChoices() {
        List<LabelValueBean> choices = null;
        
        setupPaymentMethodChoices();
        if(paymentMethodChoices != null)
            choices = convertChoices(paymentMethodChoices);
        
        return choices;
    }
    
    public void setPaymentMethodChoice(String paymentMethodChoice) {
        this.paymentMethodChoice = paymentMethodChoice;
    }
    
    public String getPaymentMethodChoice() {
        setupPaymentMethodChoices();
        return paymentMethodChoice;
    }
    
    public void setCount(String count) {
        this.count = count;
    }

    public String getCount() {
        return count;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getAmount() {
        return amount;
    }

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        super.reset(mapping, request);
    }
    
}
