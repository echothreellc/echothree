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

package com.echothree.ui.web.main.action.configuration.harmonizedtariffschedulecodeuse;

import com.echothree.control.user.item.common.ItemUtil;
import com.echothree.control.user.item.common.result.GetHarmonizedTariffScheduleCodeUseTypeChoicesResult;
import com.echothree.model.control.item.common.choice.HarmonizedTariffScheduleCodeUseTypeChoicesBean;
import com.echothree.view.client.web.struts.BaseActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import java.util.List;
import javax.naming.NamingException;
import org.apache.struts.util.LabelValueBean;

@SproutForm(name="HarmonizedTariffScheduleCodeUseAdd")
public class AddActionForm
        extends BaseActionForm {
    
    private HarmonizedTariffScheduleCodeUseTypeChoicesBean harmonizedTariffScheduleCodeUseTypeChoices;

    private String countryName;
    private String harmonizedTariffScheduleCodeName;
    private String harmonizedTariffScheduleCodeUseTypeChoice;
    
    public void setupHarmonizedTariffScheduleCodeUseTypeChoices()
            throws NamingException {
        if(harmonizedTariffScheduleCodeUseTypeChoices == null) {
            var form = ItemUtil.getHome().getGetHarmonizedTariffScheduleCodeUseTypeChoicesForm();

            form.setDefaultHarmonizedTariffScheduleCodeUseTypeChoice(harmonizedTariffScheduleCodeUseTypeChoice);
            form.setAllowNullChoice(String.valueOf(false));

            var commandResult = ItemUtil.getHome().getHarmonizedTariffScheduleCodeUseTypeChoices(userVisitPK, form);
            var executionResult = commandResult.getExecutionResult();
            var getHarmonizedTariffScheduleCodeUseTypeChoicesResult = (GetHarmonizedTariffScheduleCodeUseTypeChoicesResult)executionResult.getResult();
            harmonizedTariffScheduleCodeUseTypeChoices = getHarmonizedTariffScheduleCodeUseTypeChoicesResult.getHarmonizedTariffScheduleCodeUseTypeChoices();

            if(harmonizedTariffScheduleCodeUseTypeChoice == null) {
                harmonizedTariffScheduleCodeUseTypeChoice = harmonizedTariffScheduleCodeUseTypeChoices.getDefaultValue();
            }
        }
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }
    
    public String getCountryName() {
        return countryName;
    }
    
    public void setHarmonizedTariffScheduleCodeName(String harmonizedTariffScheduleCodeName) {
        this.harmonizedTariffScheduleCodeName = harmonizedTariffScheduleCodeName;
    }
    
    public String getHarmonizedTariffScheduleCodeName() {
        return harmonizedTariffScheduleCodeName;
    }

    public String getHarmonizedTariffScheduleCodeUseTypeChoice()
            throws NamingException {
        setupHarmonizedTariffScheduleCodeUseTypeChoices();
        return harmonizedTariffScheduleCodeUseTypeChoice;
    }

    public void setHarmonizedTariffScheduleCodeUseTypeChoice(String harmonizedTariffScheduleCodeUseTypeChoice) {
        this.harmonizedTariffScheduleCodeUseTypeChoice = harmonizedTariffScheduleCodeUseTypeChoice;
    }

    public List<LabelValueBean> getHarmonizedTariffScheduleCodeUseTypeChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;

        setupHarmonizedTariffScheduleCodeUseTypeChoices();
        if(harmonizedTariffScheduleCodeUseTypeChoices != null) {
            choices = convertChoices(harmonizedTariffScheduleCodeUseTypeChoices);
        }

        return choices;
    }

}
