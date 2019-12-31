// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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

package com.echothree.ui.web.main.action.humanresources.partyresponsibility;

import com.echothree.control.user.employee.common.EmployeeUtil;
import com.echothree.control.user.employee.common.form.GetResponsibilityTypeChoicesForm;
import com.echothree.control.user.employee.common.result.GetResponsibilityTypeChoicesResult;
import com.echothree.model.control.employee.common.choice.ResponsibilityTypeChoicesBean;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.ExecutionResult;
import com.echothree.view.client.web.struts.BaseActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import java.util.List;
import javax.naming.NamingException;
import org.apache.struts.util.LabelValueBean;

@SproutForm(name="PartyResponsibilityAdd")
public class AddActionForm
        extends BaseActionForm {
    
    private ResponsibilityTypeChoicesBean responsibilityTypeChoices;
    
    private String partyName;
    private String responsibilityTypeChoice;
    
    public void setupResponsibilityTypeChoices() {
        if(responsibilityTypeChoices == null) {
            try {
                GetResponsibilityTypeChoicesForm form = EmployeeUtil.getHome().getGetResponsibilityTypeChoicesForm();
                
                form.setDefaultResponsibilityTypeChoice(responsibilityTypeChoice);
                form.setAllowNullChoice(Boolean.FALSE.toString());
                
                CommandResult commandResult = EmployeeUtil.getHome().getResponsibilityTypeChoices(userVisitPK, form);
                ExecutionResult executionResult = commandResult.getExecutionResult();
                GetResponsibilityTypeChoicesResult result = (GetResponsibilityTypeChoicesResult)executionResult.getResult();
                responsibilityTypeChoices = result.getResponsibilityTypeChoices();
                
                if(responsibilityTypeChoice == null)
                    responsibilityTypeChoice = responsibilityTypeChoices.getDefaultValue();
            } catch (NamingException ne) {
                ne.printStackTrace();
                // failed, responsibilityTypeChoices remains null, no default
            }
        }
    }
    
    public void setPartyName(String partyName) {
        this.partyName = partyName;
    }
    
    public String getPartyName() {
        return partyName;
    }
    
    public String getResponsibilityTypeChoice() {
        return responsibilityTypeChoice;
    }
    
    public void setResponsibilityTypeChoice(String responsibilityTypeChoice) {
        this.responsibilityTypeChoice = responsibilityTypeChoice;
    }
    
    public List<LabelValueBean> getResponsibilityTypeChoices() {
        List<LabelValueBean> choices = null;
        
        setupResponsibilityTypeChoices();
        if(responsibilityTypeChoices != null)
            choices = convertChoices(responsibilityTypeChoices);
        
        return choices;
    }
    
}
