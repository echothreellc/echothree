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

package com.echothree.ui.web.main.action.configuration.harmonizedtariffschedulecode;

import com.echothree.control.user.core.common.CoreUtil;
import com.echothree.control.user.core.common.result.GetMimeTypeChoicesResult;
import com.echothree.model.control.core.common.MimeTypeUsageTypes;
import com.echothree.model.control.core.common.choice.MimeTypeChoicesBean;
import com.echothree.view.client.web.struts.BaseActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import java.util.List;
import javax.naming.NamingException;
import org.apache.struts.util.LabelValueBean;

@SproutForm(name="HarmonizedTariffScheduleCodeTranslationEdit")
public class TranslationEditActionForm
        extends BaseActionForm {
    
    private MimeTypeChoicesBean overviewMimeTypeChoices;
    
    private String countryName;
    private String harmonizedTariffScheduleCodeName;
    private String languageIsoName;
    private String description;
    private String overviewMimeTypeChoice;
    private String overview;
    
     private void setupOverviewMimeTypeChoices() {
        if(overviewMimeTypeChoices == null) {
            try {
                var commandForm = CoreUtil.getHome().getGetMimeTypeChoicesForm();
                
                commandForm.setDefaultMimeTypeChoice(overviewMimeTypeChoice);
                commandForm.setAllowNullChoice(String.valueOf(true));
                commandForm.setMimeTypeUsageTypeName(MimeTypeUsageTypes.TEXT.name());

                var commandResult = CoreUtil.getHome().getMimeTypeChoices(userVisitPK, commandForm);
                var executionResult = commandResult.getExecutionResult();
                var result = (GetMimeTypeChoicesResult)executionResult.getResult();
                overviewMimeTypeChoices = result.getMimeTypeChoices();
                
                if(overviewMimeTypeChoice == null) {
                    overviewMimeTypeChoice = overviewMimeTypeChoices.getDefaultValue();
                }
            } catch (NamingException ne) {
                // failed, overviewMimeTypeChoices remains null, no default
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
    
    public String getLanguageIsoName() {
        return languageIsoName;
    }
    
    public void setLanguageIsoName(String languageIsoName) {
        this.languageIsoName = languageIsoName;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
    
    public List<LabelValueBean> getOverviewMimeTypeChoices() {
        List<LabelValueBean> choices = null;
        
        setupOverviewMimeTypeChoices();
        if(overviewMimeTypeChoices != null) {
            choices = convertChoices(overviewMimeTypeChoices);
        }
        
        return choices;
    }
    
    public void setOverviewMimeTypeChoice(String overviewMimeTypeChoice) {
        this.overviewMimeTypeChoice = overviewMimeTypeChoice;
    }
    
    public String getOverviewMimeTypeChoice() {
        setupOverviewMimeTypeChoices();
        
        return overviewMimeTypeChoice;
    }
    
    public String getOverview() {
        return overview;
    }
    
    public void setOverview(String overview) {
        this.overview = overview;
    }
    
}
