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

package com.echothree.ui.web.main.action.humanresources.employeescaleuse;

import com.echothree.control.user.scale.common.ScaleUtil;
import com.echothree.control.user.scale.common.result.GetScaleChoicesResult;
import com.echothree.model.control.scale.common.choice.ScaleChoicesBean;
import com.echothree.view.client.web.struts.BaseLanguageActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import java.util.List;
import javax.naming.NamingException;
import org.apache.struts.util.LabelValueBean;

@SproutForm(name="EmployeeScaleUseEdit")
public class EditActionForm
        extends BaseLanguageActionForm {

    private ScaleChoicesBean scaleChoices;

    private String partyName;
    private String scaleUseTypeName;
    private String scaleChoice;

    private void setupScaleChoices() {
        if(scaleChoices == null) {
            try {
                var commandForm = ScaleUtil.getHome().getGetScaleChoicesForm();

                commandForm.setDefaultScaleChoice(scaleChoice);
                commandForm.setAllowNullChoice(String.valueOf(false));

                var commandResult = ScaleUtil.getHome().getScaleChoices(userVisitPK, commandForm);
                var executionResult = commandResult.getExecutionResult();
                var result = (GetScaleChoicesResult)executionResult.getResult();
                scaleChoices = result.getScaleChoices();

                if(scaleChoice == null) {
                    scaleChoice = scaleChoices.getDefaultValue();
                }
            } catch (NamingException ne) {
                // failed, scaleChoices remains null, no default
            }
        }
    }

    public String getPartyName() {
        return partyName;
    }

    public void setPartyName(String partyName) {
        this.partyName = partyName;
    }

    public void setScaleUseTypeName(String scaleUseTypeName) {
        this.scaleUseTypeName = scaleUseTypeName;
    }

    public String getScaleUseTypeName() {
        return scaleUseTypeName;
    }

    public List<LabelValueBean> getScaleChoices() {
        List<LabelValueBean> choices = null;

        setupScaleChoices();
        if(scaleChoices != null) {
            choices = convertChoices(scaleChoices);
        }

        return choices;
    }

    public void setScaleChoice(String scaleChoice) {
        this.scaleChoice = scaleChoice;
    }

    public String getScaleChoice() {
        setupScaleChoices();

        return scaleChoice;
    }

}
