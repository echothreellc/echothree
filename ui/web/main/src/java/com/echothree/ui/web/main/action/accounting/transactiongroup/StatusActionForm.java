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

package com.echothree.ui.web.main.action.accounting.transactiongroup;

import com.echothree.control.user.accounting.common.AccountingUtil;
import com.echothree.control.user.accounting.common.form.GetTransactionGroupStatusChoicesForm;
import com.echothree.control.user.accounting.common.result.GetTransactionGroupStatusChoicesResult;
import com.echothree.model.control.accounting.common.choice.TransactionGroupStatusChoicesBean;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.ExecutionResult;
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
    
    public void setupTransactionGroupStatusChoices() {
        if(transactionGroupStatusChoices == null) {
            try {
                GetTransactionGroupStatusChoicesForm form = AccountingUtil.getHome().getGetTransactionGroupStatusChoicesForm();
                
                form.setTransactionGroupName(transactionGroupName);
                form.setDefaultTransactionGroupStatusChoice(transactionGroupStatusChoice);
                form.setAllowNullChoice(Boolean.FALSE.toString());
                
                CommandResult commandResult = AccountingUtil.getHome().getTransactionGroupStatusChoices(userVisitPK, form);
                ExecutionResult executionResult = commandResult.getExecutionResult();
                GetTransactionGroupStatusChoicesResult result = (GetTransactionGroupStatusChoicesResult)executionResult.getResult();
                transactionGroupStatusChoices = result.getTransactionGroupStatusChoices();
                
                if(transactionGroupStatusChoice == null) {
                    transactionGroupStatusChoice = transactionGroupStatusChoices.getDefaultValue();
                }
            } catch (NamingException ne) {
                ne.printStackTrace();
                // failed, transactionGroupStatusChoices remains null, no default
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
    
    public List<LabelValueBean> getTransactionGroupStatusChoices() {
        List<LabelValueBean> choices = null;
        
        setupTransactionGroupStatusChoices();
        if(transactionGroupStatusChoices != null) {
            choices = convertChoices(transactionGroupStatusChoices);
        }
        
        return choices;
    }
    
}
