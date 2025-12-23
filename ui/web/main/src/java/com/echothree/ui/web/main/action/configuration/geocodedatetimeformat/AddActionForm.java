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

package com.echothree.ui.web.main.action.configuration.geocodedatetimeformat;

import com.echothree.control.user.party.common.PartyUtil;
import com.echothree.control.user.party.common.result.GetDateTimeFormatChoicesResult;
import com.echothree.model.control.party.common.choice.DateTimeFormatChoicesBean;
import com.echothree.view.client.web.struts.BaseActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;

@SproutForm(name="GeoCodeDateTimeFormatAdd")
public class AddActionForm
        extends BaseActionForm {
    
    private DateTimeFormatChoicesBean dateTimeFormatChoices;
    
    private String geoCodeName;
    private String dateTimeFormatChoice;
    private Boolean isDefault;
    private String sortOrder;
    
    private void setupDateTimeFormatChoices() {
        if(dateTimeFormatChoices == null) {
            try {
                var commandForm = PartyUtil.getHome().getGetDateTimeFormatChoicesForm();
                
                commandForm.setDefaultDateTimeFormatChoice(dateTimeFormatChoice);
                commandForm.setAllowNullChoice(String.valueOf(false));

                var commandResult = PartyUtil.getHome().getDateTimeFormatChoices(userVisitPK, commandForm);
                var executionResult = commandResult.getExecutionResult();
                var getDateTimeFormatChoicesResult = (GetDateTimeFormatChoicesResult)executionResult.getResult();
                dateTimeFormatChoices = getDateTimeFormatChoicesResult.getDateTimeFormatChoices();
                
                if(dateTimeFormatChoice == null)
                    dateTimeFormatChoice = dateTimeFormatChoices.getDefaultValue();
            } catch (NamingException ne) {
                // failed, dateTimeFormatChoices remains null, no default
            }
        }
    }
    
    public String getGeoCodeName() {
        return geoCodeName;
    }
    
    public void setGeoCodeName(String geoCodeName) {
        this.geoCodeName = geoCodeName;
    }
    
    public List<LabelValueBean> getDateTimeFormatChoices() {
        List<LabelValueBean> choices = null;
        
        setupDateTimeFormatChoices();
        if(dateTimeFormatChoices != null) {
            choices = convertChoices(dateTimeFormatChoices);
        }
        
        return choices;
    }
    
    public void setDateTimeFormatChoice(String dateTimeFormatChoice) {
        this.dateTimeFormatChoice = dateTimeFormatChoice;
    }
    
    public String getDateTimeFormatChoice() {
        setupDateTimeFormatChoices();
        
        return dateTimeFormatChoice;
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
    
    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        super.reset(mapping, request);
        
        isDefault = false;
    }
    
}
