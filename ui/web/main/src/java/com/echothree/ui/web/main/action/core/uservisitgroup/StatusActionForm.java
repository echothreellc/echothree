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

package com.echothree.ui.web.main.action.core.uservisitgroup;

import com.echothree.control.user.user.common.UserUtil;
import com.echothree.control.user.user.common.result.GetUserVisitGroupStatusChoicesResult;
import com.echothree.model.control.user.common.choice.UserVisitGroupStatusChoicesBean;
import com.echothree.view.client.web.struts.BaseActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import java.util.List;
import javax.naming.NamingException;
import org.apache.struts.util.LabelValueBean;

@SproutForm(name="UserVisitGroupStatus")
public class StatusActionForm
        extends BaseActionForm {
    
    private UserVisitGroupStatusChoicesBean userVisitGroupStatusChoices;
    
    private String userVisitGroupName;
    private String userVisitGroupStatusChoice;
    
    public void setupUserVisitGroupStatusChoices()
            throws NamingException {
        if(userVisitGroupStatusChoices == null) {
            var form = UserUtil.getHome().getGetUserVisitGroupStatusChoicesForm();

            form.setUserVisitGroupName(userVisitGroupName);
            form.setDefaultUserVisitGroupStatusChoice(userVisitGroupStatusChoice);
            form.setAllowNullChoice(String.valueOf(false));

            var commandResult = UserUtil.getHome().getUserVisitGroupStatusChoices(userVisitPK, form);
            var executionResult = commandResult.getExecutionResult();
            var result = (GetUserVisitGroupStatusChoicesResult)executionResult.getResult();
            userVisitGroupStatusChoices = result.getUserVisitGroupStatusChoices();

            if(userVisitGroupStatusChoice == null) {
                userVisitGroupStatusChoice = userVisitGroupStatusChoices.getDefaultValue();
            }
        }
    }
    
    public String getUserVisitGroupName() {
        return userVisitGroupName;
    }
    
    public void setUserVisitGroupName(String userVisitGroupName) {
        this.userVisitGroupName = userVisitGroupName;
    }
    
    public String getUserVisitGroupStatusChoice() {
        return userVisitGroupStatusChoice;
    }
    
    public void setUserVisitGroupStatusChoice(String userVisitGroupStatusChoice) {
        this.userVisitGroupStatusChoice = userVisitGroupStatusChoice;
    }
    
    public List<LabelValueBean> getUserVisitGroupStatusChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;
        
        setupUserVisitGroupStatusChoices();
        if(userVisitGroupStatusChoices != null) {
            choices = convertChoices(userVisitGroupStatusChoices);
        }
        
        return choices;
    }
    
}
