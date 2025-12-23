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

package com.echothree.ui.web.main.action.configuration.harmonizedtariffschedulecode;

import com.echothree.control.user.core.common.CoreUtil;
import com.echothree.control.user.core.common.result.GetMimeTypeChoicesResult;
import com.echothree.control.user.item.common.ItemUtil;
import com.echothree.control.user.item.common.result.GetHarmonizedTariffScheduleCodeUnitChoicesResult;
import com.echothree.model.control.core.common.MimeTypeUsageTypes;
import com.echothree.model.control.core.common.choice.MimeTypeChoicesBean;
import com.echothree.model.control.item.common.choice.HarmonizedTariffScheduleCodeUnitChoicesBean;
import com.echothree.view.client.web.struts.BaseActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;

@SproutForm(name="HarmonizedTariffScheduleCodeAdd")
public class AddActionForm
        extends BaseActionForm {
    
    private HarmonizedTariffScheduleCodeUnitChoicesBean firstHarmonizedTariffScheduleCodeUnitChoices;
    private HarmonizedTariffScheduleCodeUnitChoicesBean secondHarmonizedTariffScheduleCodeUnitChoices;
    private MimeTypeChoicesBean overviewMimeTypeChoices;

    private String countryName;
    private String harmonizedTariffScheduleCodeName;
    private String firstHarmonizedTariffScheduleCodeUnitChoice;
    private String secondHarmonizedTariffScheduleCodeUnitChoice;
    private Boolean isDefault;
    private String sortOrder;
    private String description;
    private String overviewMimeTypeChoice;
    private String overview;
    
    public void setupFirstHarmonizedTariffScheduleCodeUnitChoices()
            throws NamingException {
        if(firstHarmonizedTariffScheduleCodeUnitChoices == null) {
            var form = ItemUtil.getHome().getGetHarmonizedTariffScheduleCodeUnitChoicesForm();

            form.setDefaultHarmonizedTariffScheduleCodeUnitChoice(firstHarmonizedTariffScheduleCodeUnitChoice);
            form.setAllowNullChoice(String.valueOf(true));

            var commandResult = ItemUtil.getHome().getHarmonizedTariffScheduleCodeUnitChoices(userVisitPK, form);
            var executionResult = commandResult.getExecutionResult();
            var getHarmonizedTariffScheduleCodeUnitChoicesResult = (GetHarmonizedTariffScheduleCodeUnitChoicesResult)executionResult.getResult();
            firstHarmonizedTariffScheduleCodeUnitChoices = getHarmonizedTariffScheduleCodeUnitChoicesResult.getHarmonizedTariffScheduleCodeUnitChoices();

            if(firstHarmonizedTariffScheduleCodeUnitChoice == null) {
                firstHarmonizedTariffScheduleCodeUnitChoice = firstHarmonizedTariffScheduleCodeUnitChoices.getDefaultValue();
            }
        }
    }

    public void setupSecondHarmonizedTariffScheduleCodeUnitChoices()
            throws NamingException {
        if(secondHarmonizedTariffScheduleCodeUnitChoices == null) {
            var form = ItemUtil.getHome().getGetHarmonizedTariffScheduleCodeUnitChoicesForm();

            form.setDefaultHarmonizedTariffScheduleCodeUnitChoice(secondHarmonizedTariffScheduleCodeUnitChoice);
            form.setAllowNullChoice(String.valueOf(true));

            var commandResult = ItemUtil.getHome().getHarmonizedTariffScheduleCodeUnitChoices(userVisitPK, form);
            var executionResult = commandResult.getExecutionResult();
            var getHarmonizedTariffScheduleCodeUnitChoicesResult = (GetHarmonizedTariffScheduleCodeUnitChoicesResult)executionResult.getResult();
            secondHarmonizedTariffScheduleCodeUnitChoices = getHarmonizedTariffScheduleCodeUnitChoicesResult.getHarmonizedTariffScheduleCodeUnitChoices();

            if(secondHarmonizedTariffScheduleCodeUnitChoice == null) {
                secondHarmonizedTariffScheduleCodeUnitChoice = secondHarmonizedTariffScheduleCodeUnitChoices.getDefaultValue();
            }
        }
    }

     private void setupOverviewMimeTypeChoices()
             throws NamingException {
        if(overviewMimeTypeChoices == null) {
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

    public String getFirstHarmonizedTariffScheduleCodeUnitChoice()
            throws NamingException {
        setupFirstHarmonizedTariffScheduleCodeUnitChoices();
        return firstHarmonizedTariffScheduleCodeUnitChoice;
    }

    public void setFirstHarmonizedTariffScheduleCodeUnitChoice(String firstHarmonizedTariffScheduleCodeUnitChoice) {
        this.firstHarmonizedTariffScheduleCodeUnitChoice = firstHarmonizedTariffScheduleCodeUnitChoice;
    }

    public List<LabelValueBean> getFirstHarmonizedTariffScheduleCodeUnitChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;

        setupFirstHarmonizedTariffScheduleCodeUnitChoices();
        if(firstHarmonizedTariffScheduleCodeUnitChoices != null) {
            choices = convertChoices(firstHarmonizedTariffScheduleCodeUnitChoices);
        }

        return choices;
    }

    public String getSecondHarmonizedTariffScheduleCodeUnitChoice()
            throws NamingException {
        setupSecondHarmonizedTariffScheduleCodeUnitChoices();
        return secondHarmonizedTariffScheduleCodeUnitChoice;
    }

    public void setSecondHarmonizedTariffScheduleCodeUnitChoice(String secondHarmonizedTariffScheduleCodeUnitChoice) {
        this.secondHarmonizedTariffScheduleCodeUnitChoice = secondHarmonizedTariffScheduleCodeUnitChoice;
    }

    public List<LabelValueBean> getSecondHarmonizedTariffScheduleCodeUnitChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;

        setupSecondHarmonizedTariffScheduleCodeUnitChoices();
        if(secondHarmonizedTariffScheduleCodeUnitChoices != null) {
            choices = convertChoices(secondHarmonizedTariffScheduleCodeUnitChoices);
        }

        return choices;
    }

    public Boolean getIsDefault() {
        return isDefault;
    }
    
    public void setIsDefault(Boolean isDefault) {
        this.isDefault = isDefault;
    }
    
    public String getSortOrder() {
        return sortOrder;
    }
    
    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
    
    public List<LabelValueBean> getOverviewMimeTypeChoices()
            throws NamingException {
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
    
    public String getOverviewMimeTypeChoice()
            throws NamingException {
        setupOverviewMimeTypeChoices();
        
        return overviewMimeTypeChoice;
    }
    
    public String getOverview() {
        return overview;
    }
    
    public void setOverview(String overview) {
        this.overview = overview;
    }
    
    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        super.reset(mapping, request);
        
        isDefault = false;
    }

}
