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

package com.echothree.ui.web.main.action.humanresources.partytrainingclass;

import com.echothree.control.user.training.common.TrainingUtil;
import com.echothree.control.user.training.common.result.GetPartyTrainingClassStatusChoicesResult;
import com.echothree.model.control.training.common.choice.PartyTrainingClassStatusChoicesBean;
import com.echothree.view.client.web.struts.BaseActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import java.util.List;
import javax.naming.NamingException;
import org.apache.struts.util.LabelValueBean;

@SproutForm(name="PartyTrainingClassStatus")
public class StatusActionForm
        extends BaseActionForm {
    
    private PartyTrainingClassStatusChoicesBean partyTrainingClassStatusChoices;
    
    private String partyName;
    private String partyTrainingClassName;
    private String partyTrainingClassStatusChoice;
    
    public void setupPartyTrainingClassStatusChoices()
            throws NamingException {
        if(partyTrainingClassStatusChoices == null) {
            var form = TrainingUtil.getHome().getGetPartyTrainingClassStatusChoicesForm();

            form.setPartyTrainingClassName(partyTrainingClassName);
            form.setDefaultPartyTrainingClassStatusChoice(partyTrainingClassStatusChoice);
            form.setAllowNullChoice(String.valueOf(false));

            var commandResult = TrainingUtil.getHome().getPartyTrainingClassStatusChoices(userVisitPK, form);
            var executionResult = commandResult.getExecutionResult();
            var result = (GetPartyTrainingClassStatusChoicesResult)executionResult.getResult();
            partyTrainingClassStatusChoices = result.getPartyTrainingClassStatusChoices();

            if(partyTrainingClassStatusChoice == null) {
                partyTrainingClassStatusChoice = partyTrainingClassStatusChoices.getDefaultValue();
            }
        }
    }
    
    public String getPartyName() {
        return partyName;
    }

    public void setPartyName(String partyName) {
        this.partyName = partyName;
    }

    public String getPartyTrainingClassName() {
        return partyTrainingClassName;
    }

    public void setPartyTrainingClassName(String partyTrainingClassName) {
        this.partyTrainingClassName = partyTrainingClassName;
    }

    public String getPartyTrainingClassStatusChoice() {
        return partyTrainingClassStatusChoice;
    }
    
    public void setPartyTrainingClassStatusChoice(String partyTrainingClassStatusChoice) {
        this.partyTrainingClassStatusChoice = partyTrainingClassStatusChoice;
    }
    
    public List<LabelValueBean> getPartyTrainingClassStatusChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;
        
        setupPartyTrainingClassStatusChoices();
        if(partyTrainingClassStatusChoices != null) {
            choices = convertChoices(partyTrainingClassStatusChoices);
        }
        
        return choices;
    }
    
}
