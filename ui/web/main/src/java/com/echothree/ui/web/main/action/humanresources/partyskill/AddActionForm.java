// --------------------------------------------------------------------------------
// Copyright 2002-2019 Echo Three, LLC
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

package com.echothree.ui.web.main.action.humanresources.partyskill;

import com.echothree.control.user.employee.common.EmployeeUtil;
import com.echothree.control.user.employee.common.form.GetSkillTypeChoicesForm;
import com.echothree.control.user.employee.common.result.GetSkillTypeChoicesResult;
import com.echothree.model.control.employee.common.choice.SkillTypeChoicesBean;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.ExecutionResult;
import com.echothree.view.client.web.struts.BaseActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import java.util.List;
import javax.naming.NamingException;
import org.apache.struts.util.LabelValueBean;

@SproutForm(name="PartySkillAdd")
public class AddActionForm
        extends BaseActionForm {
    
    private SkillTypeChoicesBean skillTypeChoices;
    
    private String partyName;
    private String skillTypeChoice;
    
    public void setupSkillTypeChoices() {
        if(skillTypeChoices == null) {
            try {
                GetSkillTypeChoicesForm form = EmployeeUtil.getHome().getGetSkillTypeChoicesForm();
                
                form.setDefaultSkillTypeChoice(skillTypeChoice);
                form.setAllowNullChoice(Boolean.FALSE.toString());
                
                CommandResult commandResult = EmployeeUtil.getHome().getSkillTypeChoices(userVisitPK, form);
                ExecutionResult executionResult = commandResult.getExecutionResult();
                GetSkillTypeChoicesResult result = (GetSkillTypeChoicesResult)executionResult.getResult();
                skillTypeChoices = result.getSkillTypeChoices();
                
                if(skillTypeChoice == null)
                    skillTypeChoice = skillTypeChoices.getDefaultValue();
            } catch (NamingException ne) {
                ne.printStackTrace();
                // failed, skillTypeChoices remains null, no default
            }
        }
    }
    
    public void setPartyName(String partyName) {
        this.partyName = partyName;
    }
    
    public String getPartyName() {
        return partyName;
    }
    
    public String getSkillTypeChoice() {
        return skillTypeChoice;
    }
    
    public void setSkillTypeChoice(String skillTypeChoice) {
        this.skillTypeChoice = skillTypeChoice;
    }
    
    public List<LabelValueBean> getSkillTypeChoices() {
        List<LabelValueBean> choices = null;
        
        setupSkillTypeChoices();
        if(skillTypeChoices != null)
            choices = convertChoices(skillTypeChoices);
        
        return choices;
    }
    
}
