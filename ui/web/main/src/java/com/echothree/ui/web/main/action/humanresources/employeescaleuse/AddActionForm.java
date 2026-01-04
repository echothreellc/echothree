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

package com.echothree.ui.web.main.action.humanresources.employeescaleuse;

import com.echothree.control.user.scale.common.ScaleUtil;
import com.echothree.control.user.scale.common.result.GetScaleChoicesResult;
import com.echothree.control.user.scale.common.result.GetScaleUseTypeChoicesResult;
import com.echothree.model.control.scale.common.choice.ScaleChoicesBean;
import com.echothree.model.control.scale.common.choice.ScaleUseTypeChoicesBean;
import com.echothree.view.client.web.struts.BaseLanguageActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import java.util.List;
import javax.naming.NamingException;
import org.apache.struts.util.LabelValueBean;

@SproutForm(name="EmployeeScaleUseAdd")
public class AddActionForm
        extends BaseLanguageActionForm {

    private ScaleUseTypeChoicesBean scaleUseTypeChoices;
    private ScaleChoicesBean scaleChoices;

    private String partyName;
    private String scaleUseTypeChoice;
    private String scaleChoice;

    private void setupScaleUseTypeChoices() {
        if(scaleUseTypeChoices == null) {
            try {
                var commandForm = ScaleUtil.getHome().getGetScaleUseTypeChoicesForm();

                commandForm.setDefaultScaleUseTypeChoice(scaleUseTypeChoice);
                commandForm.setAllowNullChoice(String.valueOf(false));

                var commandResult = ScaleUtil.getHome().getScaleUseTypeChoices(userVisitPK, commandForm);
                var executionResult = commandResult.getExecutionResult();
                var result = (GetScaleUseTypeChoicesResult)executionResult.getResult();
                scaleUseTypeChoices = result.getScaleUseTypeChoices();

                if(scaleUseTypeChoice == null) {
                    scaleUseTypeChoice = scaleUseTypeChoices.getDefaultValue();
                }
            } catch (NamingException ne) {
                // failed, scaleUseTypeChoices remains null, no default
            }
        }
    }

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

    public List<LabelValueBean> getScaleUseTypeChoices() {
        List<LabelValueBean> choices = null;

        setupScaleUseTypeChoices();
        if(scaleUseTypeChoices != null) {
            choices = convertChoices(scaleUseTypeChoices);
        }

        return choices;
    }

    public void setScaleUseTypeChoice(String scaleUseTypeChoice) {
        this.scaleUseTypeChoice = scaleUseTypeChoice;
    }

    public String getScaleUseTypeChoice() {
        setupScaleUseTypeChoices();

        return scaleUseTypeChoice;
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
