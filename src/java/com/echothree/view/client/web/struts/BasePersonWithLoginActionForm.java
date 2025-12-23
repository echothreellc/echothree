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

package com.echothree.view.client.web.struts;

import com.echothree.control.user.user.common.UserUtil;
import com.echothree.control.user.user.common.result.GetRecoveryQuestionChoicesResult;
import com.echothree.model.control.user.common.choice.RecoveryQuestionChoicesBean;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;

public class BasePersonWithLoginActionForm
        extends BasePersonActionForm {
    
    private RecoveryQuestionChoicesBean recoveryQuestionChoices = null;
    
    private String emailAddress = null;
    private String username = null;
    private String password1 = null;
    private String password2 = null;
    private boolean allowSolicitation;
    private String recoveryQuestionChoice = null;
    private String answer = null;
    
    private void setupRecoveryQuestionChoices() {
        if(recoveryQuestionChoices == null) {
            try {
                var form = UserUtil.getHome().getGetRecoveryQuestionChoicesForm();
                
                form.setDefaultRecoveryQuestionChoice(recoveryQuestionChoice);
                form.setAllowNullChoice(String.valueOf(true));

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
    
    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }
    
    public String getEmailAddress() {
        return emailAddress;
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
    
    public void setAllowSolicitation(boolean allowSolicitation) {
        this.allowSolicitation = allowSolicitation;
    }
    
    public boolean getAllowSolicitation() {
        return allowSolicitation;
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
    
    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        super.reset(mapping, request);
        
        allowSolicitation = false;
    }
    
}
