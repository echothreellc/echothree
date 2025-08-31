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

package com.echothree.view.client.web.struts;

import com.echothree.control.user.party.common.PartyUtil;
import com.echothree.control.user.party.common.result.GetLanguageChoicesResult;
import com.echothree.model.control.party.common.choice.LanguageChoicesBean;
import java.util.List;
import javax.naming.NamingException;
import org.apache.struts.util.LabelValueBean;

public class BaseLanguageActionForm
        extends BaseActionForm {
    
    private LanguageChoicesBean languageChoices = null;
    
    private String languageChoice = null;
    
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
                
                if(languageChoice == null) {
                    languageChoice = languageChoices.getDefaultValue();
                }
            } catch (NamingException ne) {
                // failed, languageChoices remains null, no default
            }
        }
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
    
}
