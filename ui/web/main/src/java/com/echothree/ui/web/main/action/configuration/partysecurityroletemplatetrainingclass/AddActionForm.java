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

package com.echothree.ui.web.main.action.configuration.partysecurityroletemplatetrainingclass;

import com.echothree.control.user.training.common.TrainingUtil;
import com.echothree.control.user.training.common.result.GetTrainingClassChoicesResult;
import com.echothree.model.control.training.common.choice.TrainingClassChoicesBean;
import com.echothree.view.client.web.struts.BaseActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import java.util.List;
import javax.naming.NamingException;
import org.apache.struts.util.LabelValueBean;

@SproutForm(name="PartySecurityRoleTemplateTrainingClassAdd")
public class AddActionForm
        extends BaseActionForm {
    
    private TrainingClassChoicesBean trainingClassChoices;

    private String partySecurityRoleTemplateName;
    private String trainingClassChoice;
    
    public void setupTrainingClassChoices()
            throws NamingException {
        if(trainingClassChoices == null) {
            var form = TrainingUtil.getHome().getGetTrainingClassChoicesForm();

            form.setDefaultTrainingClassChoice(trainingClassChoice);
            form.setAllowNullChoice(String.valueOf(false));

            var commandResult = TrainingUtil.getHome().getTrainingClassChoices(userVisitPK, form);
            var executionResult = commandResult.getExecutionResult();
            var result = (GetTrainingClassChoicesResult)executionResult.getResult();
            trainingClassChoices = result.getTrainingClassChoices();

            if(trainingClassChoice == null) {
                trainingClassChoice = trainingClassChoices.getDefaultValue();
            }
        }
    }

    public void setPartySecurityRoleTemplateName(String partySecurityRoleTemplateName) {
        this.partySecurityRoleTemplateName = partySecurityRoleTemplateName;
    }

    public String getPartySecurityRoleTemplateName() {
        return partySecurityRoleTemplateName;
    }

    public List<LabelValueBean> getTrainingClassChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;

        setupTrainingClassChoices();
        if(trainingClassChoices != null) {
            choices = convertChoices(trainingClassChoices);
        }
        
        return choices;
    }

    public String getTrainingClassChoice()
            throws NamingException {
        setupTrainingClassChoices();

        return trainingClassChoice;
    }

    public void setTrainingClassChoice(String trainingClassChoice) {
        this.trainingClassChoice = trainingClassChoice;
    }

}
