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

package com.echothree.ui.web.main.action.filter.filterentrancestep;

import com.echothree.control.user.filter.common.FilterUtil;
import com.echothree.control.user.filter.common.result.GetFilterStepChoicesResult;
import com.echothree.model.control.filter.common.choice.FilterStepChoicesBean;
import com.echothree.view.client.web.struts.BaseActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import java.util.List;
import javax.naming.NamingException;
import org.apache.struts.util.LabelValueBean;

@SproutForm(name="FilterEntranceStepAdd")
public class AddActionForm
        extends BaseActionForm {
    
    private FilterStepChoicesBean filterStepChoices;
    
    private String filterKindName;
    private String filterTypeName;
    private String filterName;
    private String filterStepChoice;
    
    private void setupFilterStepChoices()
            throws NamingException {
        if(filterStepChoices == null) {
            var form = FilterUtil.getHome().getGetFilterStepChoicesForm();

            form.setFilterKindName(filterKindName);
            form.setFilterTypeName(filterTypeName);
            form.setFilterName(filterName);
            form.setDefaultFilterStepChoice(filterStepChoice);
            form.setAllowNullChoice(String.valueOf(false));

            var commandResult = FilterUtil.getHome().getFilterStepChoices(userVisitPK, form);
            var executionResult = commandResult.getExecutionResult();
            var result = (GetFilterStepChoicesResult)executionResult.getResult();
            filterStepChoices = result.getFilterStepChoices();

            if(filterStepChoice == null)
                filterStepChoice = filterStepChoices.getDefaultValue();
        }
    }
    
    public void setFilterKindName(String filterKindName) {
        this.filterKindName = filterKindName;
    }
    
    public String getFilterKindName() {
        return filterKindName;
    }
    
    public void setFilterTypeName(String filterTypeName) {
        this.filterTypeName = filterTypeName;
    }
    
    public String getFilterTypeName() {
        return filterTypeName;
    }
    
    public void setFilterName(String filterName) {
        this.filterName = filterName;
    }
    
    public String getFilterName() {
        return filterName;
    }
    
    public List<LabelValueBean> getFilterStepChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;
        
        setupFilterStepChoices();
        if(filterStepChoices != null)
            choices = convertChoices(filterStepChoices);
        
        return choices;
    }
    
    public void setFilterStepChoice(String filterStepChoice) {
        this.filterStepChoice = filterStepChoice;
    }
    
    public String getFilterStepChoice()
            throws NamingException {
        setupFilterStepChoices();
        return filterStepChoice;
    }
    
}
