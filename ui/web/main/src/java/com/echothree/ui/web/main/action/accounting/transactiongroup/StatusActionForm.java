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

package com.echothree.ui.web.main.action.accounting.transactiongroup;

import com.echothree.control.user.accounting.common.AccountingUtil;
import com.echothree.control.user.accounting.common.result.GetTransactionGroupStatusChoicesResult;
import com.echothree.model.control.accounting.common.choice.TransactionGroupStatusChoicesBean;
import com.echothree.view.client.web.struts.BaseActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import java.util.List;
import javax.naming.NamingException;
import org.apache.struts.util.LabelValueBean;

@SproutForm(name="TransactionGroupStatus")
public class StatusActionForm
        extends BaseActionForm {
    
    private TransactionGroupStatusChoicesBean transactionGroupStatusChoices;
    
    private String transactionGroupName;
    private String transactionGroupStatusChoice;
    
    public void setupTransactionGroupStatusChoices()
            throws NamingException {
        if(transactionGroupStatusChoices == null) {
            var form = AccountingUtil.getHome().getGetTransactionGroupStatusChoicesForm();

            form.setTransactionGroupName(transactionGroupName);
            form.setDefaultTransactionGroupStatusChoice(transactionGroupStatusChoice);
            form.setAllowNullChoice(String.valueOf(false));

            var commandResult = AccountingUtil.getHome().getTransactionGroupStatusChoices(userVisitPK, form);
            var executionResult = commandResult.getExecutionResult();
            var result = (GetTransactionGroupStatusChoicesResult)executionResult.getResult();
            transactionGroupStatusChoices = result.getTransactionGroupStatusChoices();

            if(transactionGroupStatusChoice == null) {
                transactionGroupStatusChoice = transactionGroupStatusChoices.getDefaultValue();
            }
        }
    }
    
    public String getTransactionGroupName() {
        return transactionGroupName;
    }
    
    public void setTransactionGroupName(String transactionGroupName) {
        this.transactionGroupName = transactionGroupName;
    }
    
    public String getTransactionGroupStatusChoice() {
        return transactionGroupStatusChoice;
    }
    
    public void setTransactionGroupStatusChoice(String transactionGroupStatusChoice) {
        this.transactionGroupStatusChoice = transactionGroupStatusChoice;
    }
    
    public List<LabelValueBean> getTransactionGroupStatusChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;
        
        setupTransactionGroupStatusChoices();
        if(transactionGroupStatusChoices != null) {
            choices = convertChoices(transactionGroupStatusChoices);
        }
        
        return choices;
    }
    
}
