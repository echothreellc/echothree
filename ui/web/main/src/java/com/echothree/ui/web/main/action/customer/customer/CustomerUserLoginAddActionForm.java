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

package com.echothree.ui.web.main.action.customer.customer;

import com.echothree.control.user.user.common.UserUtil;
import com.echothree.control.user.user.common.result.GetRecoveryQuestionChoicesResult;
import com.echothree.model.control.user.common.choice.RecoveryQuestionChoicesBean;
import com.echothree.view.client.web.struts.BaseActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import java.util.List;
import javax.naming.NamingException;
import org.apache.struts.util.LabelValueBean;

@SproutForm(name="CustomerUserLoginAdd")
public class CustomerUserLoginAddActionForm
        extends BaseActionForm {
    
    private RecoveryQuestionChoicesBean recoveryQuestionChoices;
    
    private String partyName;
    private String username;
    private String password1;
    private String password2;
    private String recoveryQuestionChoice;
    private String answer;
    
    private void setupRecoveryQuestionChoices() {
        if(recoveryQuestionChoices == null) {
            try {
                var form = UserUtil.getHome().getGetRecoveryQuestionChoicesForm();
                
                form.setDefaultRecoveryQuestionChoice(recoveryQuestionChoice);
                form.setAllowNullChoice(String.valueOf(false));

                var commandResult = UserUtil.getHome().getRecoveryQuestionChoices(userVisitPK, form);
                var executionResult = commandResult.getExecutionResult();
                var getRecoveryQuestionChoicesResult = (GetRecoveryQuestionChoicesResult)executionResult.getResult();
                recoveryQuestionChoices = getRecoveryQuestionChoicesResult.getRecoveryQuestionChoices();
                
                if(recoveryQuestionChoice == null) {
                    recoveryQuestionChoice = recoveryQuestionChoices.getDefaultValue();
                }
            } catch (NamingException ne) {
                // failed, recoveryQuestionChoices remains null, no default
            }
        }
    }
    
    public String getPartyName() {
        return partyName;
    }
    
    public void setPartyName(String partyName) {
        this.partyName = partyName;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setPassword1(String password1) {
        this.password1 = password1;
    }
    
    public String getPassword1() {
        return password1;
    }
    
    public void setPassword2(String password2) {
        this.password2 = password2;
    }
    
    public String getPassword2() {
        return password2;
    }
    
    public List<LabelValueBean> getRecoveryQuestionChoices() {
        List<LabelValueBean> choices = null;
        
        setupRecoveryQuestionChoices();
        if(recoveryQuestionChoices != null) {
            choices = convertChoices(recoveryQuestionChoices);
        }
        
        return choices;
    }
    
    public void setRecoveryQuestionChoice(String recoveryQuestionChoice) {
        this.recoveryQuestionChoice = recoveryQuestionChoice;
    }
    
    public String getRecoveryQuestionChoice() {
        setupRecoveryQuestionChoices();
        
        return recoveryQuestionChoice;
    }
    
    public void setAnswer(String answer) {
        this.answer = answer;
    }
    
    public String getAnswer() {
        return answer;
    }
    
    
}
