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

package com.echothree.ui.web.main.action.configuration.geocodelanguage;

import com.echothree.control.user.party.common.PartyUtil;
import com.echothree.control.user.party.common.result.GetLanguageChoicesResult;
import com.echothree.model.control.party.common.choice.LanguageChoicesBean;
import com.echothree.view.client.web.struts.BaseActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;

@SproutForm(name="GeoCodeLanguageAdd")
public class AddActionForm
        extends BaseActionForm {
    
    private LanguageChoicesBean languageChoices;
    
    private String geoCodeName;
    private String languageChoice;
    private Boolean isDefault;
    private String sortOrder;
    
    private void setupLanguageChoices() {
        if(languageChoices == null) {
            try {
                var commandForm = PartyUtil.getHome().getGetLanguageChoicesForm();
                
                commandForm.setDefaultLanguageChoice(languageChoice);
                commandForm.setAllowNullChoice(String.valueOf(false));

                var commandResult = PartyUtil.getHome().getLanguageChoices(userVisitPK, commandForm);
                var executionResult = commandResult.getExecutionResult();
                var getLanguageChoicesResult = (GetLanguageChoicesResult)executionResult.getResult();
                languageChoices = getLanguageChoicesResult.getLanguageChoices();
                
                if(languageChoice == null)
                    languageChoice = languageChoices.getDefaultValue();
            } catch (NamingException ne) {
                // failed, languageChoices remains null, no default
            }
        }
    }
    
    public String getGeoCodeName() {
        return geoCodeName;
    }
    
    public void setGeoCodeName(String geoCodeName) {
        this.geoCodeName = geoCodeName;
    }
    
    public List<LabelValueBean> getLanguageChoices() {
        List<LabelValueBean> choices = null;
        
        setupLanguageChoices();
        if(languageChoices != null) {
            choices = convertChoices(languageChoices);
        }
        
        return choices;
    }
    
    public void setLanguageChoice(String languageChoice) {
        this.languageChoice = languageChoice;
    }
    
    public String getLanguageChoice() {
        setupLanguageChoices();
        
        return languageChoice;
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
