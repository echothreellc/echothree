// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
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
import com.echothree.control.user.core.remote.form.GetMimeTypeChoicesForm;
import com.echothree.control.user.core.remote.result.GetMimeTypeChoicesResult;
import com.echothree.control.user.item.common.ItemUtil;
import com.echothree.control.user.item.remote.form.GetHarmonizedTariffScheduleCodeUnitChoicesForm;
import com.echothree.control.user.item.remote.result.GetHarmonizedTariffScheduleCodeUnitChoicesResult;
import com.echothree.model.control.core.common.MimeTypeUsageTypes;
import com.echothree.model.control.core.remote.choice.MimeTypeChoicesBean;
import com.echothree.model.control.item.remote.choice.HarmonizedTariffScheduleCodeUnitChoicesBean;
import com.echothree.util.remote.command.CommandResult;
import com.echothree.util.remote.command.ExecutionResult;
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
    
    public void setupFirstHarmonizedTariffScheduleCodeUnitChoices() {
        if(firstHarmonizedTariffScheduleCodeUnitChoices == null) {
            try {
                GetHarmonizedTariffScheduleCodeUnitChoicesForm form = ItemUtil.getHome().getGetHarmonizedTariffScheduleCodeUnitChoicesForm();

                form.setDefaultHarmonizedTariffScheduleCodeUnitChoice(firstHarmonizedTariffScheduleCodeUnitChoice);
                form.setAllowNullChoice(Boolean.TRUE.toString());

                CommandResult commandResult = ItemUtil.getHome().getHarmonizedTariffScheduleCodeUnitChoices(userVisitPK, form);
                ExecutionResult executionResult = commandResult.getExecutionResult();
                GetHarmonizedTariffScheduleCodeUnitChoicesResult getHarmonizedTariffScheduleCodeUnitChoicesResult = (GetHarmonizedTariffScheduleCodeUnitChoicesResult)executionResult.getResult();
                firstHarmonizedTariffScheduleCodeUnitChoices = getHarmonizedTariffScheduleCodeUnitChoicesResult.getHarmonizedTariffScheduleCodeUnitChoices();

                if(firstHarmonizedTariffScheduleCodeUnitChoice == null) {
                    firstHarmonizedTariffScheduleCodeUnitChoice = firstHarmonizedTariffScheduleCodeUnitChoices.getDefaultValue();
                }
            } catch (NamingException ne) {
                ne.printStackTrace();
                // failed, firstHarmonizedTariffScheduleCodeUnitChoices remains null, no default
            }
        }
    }

    public void setupSecondHarmonizedTariffScheduleCodeUnitChoices() {
        if(secondHarmonizedTariffScheduleCodeUnitChoices == null) {
            try {
                GetHarmonizedTariffScheduleCodeUnitChoicesForm form = ItemUtil.getHome().getGetHarmonizedTariffScheduleCodeUnitChoicesForm();

                form.setDefaultHarmonizedTariffScheduleCodeUnitChoice(secondHarmonizedTariffScheduleCodeUnitChoice);
                form.setAllowNullChoice(Boolean.TRUE.toString());

                CommandResult commandResult = ItemUtil.getHome().getHarmonizedTariffScheduleCodeUnitChoices(userVisitPK, form);
                ExecutionResult executionResult = commandResult.getExecutionResult();
                GetHarmonizedTariffScheduleCodeUnitChoicesResult getHarmonizedTariffScheduleCodeUnitChoicesResult = (GetHarmonizedTariffScheduleCodeUnitChoicesResult)executionResult.getResult();
                secondHarmonizedTariffScheduleCodeUnitChoices = getHarmonizedTariffScheduleCodeUnitChoicesResult.getHarmonizedTariffScheduleCodeUnitChoices();

                if(secondHarmonizedTariffScheduleCodeUnitChoice == null) {
                    secondHarmonizedTariffScheduleCodeUnitChoice = secondHarmonizedTariffScheduleCodeUnitChoices.getDefaultValue();
                }
            } catch (NamingException ne) {
                ne.printStackTrace();
                // failed, secondHarmonizedTariffScheduleCodeUnitChoices remains null, no default
            }
        }
    }

     private void setupOverviewMimeTypeChoices() {
        if(overviewMimeTypeChoices == null) {
            try {
                GetMimeTypeChoicesForm commandForm = CoreUtil.getHome().getGetMimeTypeChoicesForm();
                
                commandForm.setDefaultMimeTypeChoice(overviewMimeTypeChoice);
                commandForm.setAllowNullChoice(Boolean.TRUE.toString());
                commandForm.setMimeTypeUsageTypeName(MimeTypeUsageTypes.TEXT.name());
                
                CommandResult commandResult = CoreUtil.getHome().getMimeTypeChoices(userVisitPK, commandForm);
                ExecutionResult executionResult = commandResult.getExecutionResult();
                GetMimeTypeChoicesResult result = (GetMimeTypeChoicesResult)executionResult.getResult();
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

    public String getFirstHarmonizedTariffScheduleCodeUnitChoice() {
        setupFirstHarmonizedTariffScheduleCodeUnitChoices();
        return firstHarmonizedTariffScheduleCodeUnitChoice;
    }

    public void setFirstHarmonizedTariffScheduleCodeUnitChoice(String firstHarmonizedTariffScheduleCodeUnitChoice) {
        this.firstHarmonizedTariffScheduleCodeUnitChoice = firstHarmonizedTariffScheduleCodeUnitChoice;
    }

    public List<LabelValueBean> getFirstHarmonizedTariffScheduleCodeUnitChoices() {
        List<LabelValueBean> choices = null;

        setupFirstHarmonizedTariffScheduleCodeUnitChoices();
        if(firstHarmonizedTariffScheduleCodeUnitChoices != null) {
            choices = convertChoices(firstHarmonizedTariffScheduleCodeUnitChoices);
        }

        return choices;
    }

    public String getSecondHarmonizedTariffScheduleCodeUnitChoice() {
        setupSecondHarmonizedTariffScheduleCodeUnitChoices();
        return secondHarmonizedTariffScheduleCodeUnitChoice;
    }

    public void setSecondHarmonizedTariffScheduleCodeUnitChoice(String secondHarmonizedTariffScheduleCodeUnitChoice) {
        this.secondHarmonizedTariffScheduleCodeUnitChoice = secondHarmonizedTariffScheduleCodeUnitChoice;
    }

    public List<LabelValueBean> getSecondHarmonizedTariffScheduleCodeUnitChoices() {
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
    
    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        super.reset(mapping, request);
        
        isDefault = Boolean.FALSE;
    }

}
