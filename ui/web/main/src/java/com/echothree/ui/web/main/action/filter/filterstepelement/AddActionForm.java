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

package com.echothree.ui.web.main.action.filter.filterstepelement;

import com.echothree.control.user.filter.common.FilterUtil;
import com.echothree.control.user.filter.common.result.GetFilterAdjustmentChoicesResult;
import com.echothree.control.user.selector.common.SelectorUtil;
import com.echothree.control.user.selector.common.result.GetSelectorChoicesResult;
import com.echothree.model.control.filter.common.choice.FilterAdjustmentChoicesBean;
import com.echothree.model.control.selector.common.SelectorKinds;
import com.echothree.model.control.selector.common.SelectorTypes;
import com.echothree.model.control.selector.common.choice.SelectorChoicesBean;
import com.echothree.view.client.web.struts.BaseActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import java.util.List;
import javax.naming.NamingException;
import org.apache.struts.util.LabelValueBean;

@SproutForm(name="FilterStepElementAdd")
public class AddActionForm
        extends BaseActionForm {
    
    private FilterAdjustmentChoicesBean filterAdjustmentChoices;
    private SelectorChoicesBean filterItemSelectorChoices;
    
    private String filterKindName;
    private String filterTypeName;
    private String filterName;
    private String filterStepName;
    private String filterStepElementName;
    private String filterAdjustmentChoice;
    private String filterItemSelectorChoice;
    private String description;
    
    private void setupFilterAdjustmentChoices()
            throws NamingException {
        if(filterAdjustmentChoices == null) {
            var form = FilterUtil.getHome().getGetFilterAdjustmentChoicesForm();

            form.setFilterKindName(filterKindName);
            form.setDefaultFilterAdjustmentChoice(filterAdjustmentChoice);
            form.setInitialAdjustmentsOnly(String.valueOf(false));

            var commandResult = FilterUtil.getHome().getFilterAdjustmentChoices(userVisitPK, form);
            var executionResult = commandResult.getExecutionResult();
            var result = (GetFilterAdjustmentChoicesResult)executionResult.getResult();
            filterAdjustmentChoices = result.getFilterAdjustmentChoices();

            if(filterAdjustmentChoice == null)
                filterAdjustmentChoice = filterAdjustmentChoices.getDefaultValue();
        }
    }
    
    private void setupFilterItemSelectorChoices()
            throws NamingException {
        if(filterItemSelectorChoices == null) {
            var form = SelectorUtil.getHome().getGetSelectorChoicesForm();

            form.setSelectorKindName(SelectorKinds.ITEM.name());
            form.setSelectorTypeName(SelectorTypes.FILTER.name());
            form.setDefaultSelectorChoice(filterItemSelectorChoice);
            form.setAllowNullChoice(String.valueOf(true));

            var commandResult = SelectorUtil.getHome().getSelectorChoices(userVisitPK, form);
            var executionResult = commandResult.getExecutionResult();
            var result = (GetSelectorChoicesResult)executionResult.getResult();
            filterItemSelectorChoices = result.getSelectorChoices();

            if(filterItemSelectorChoice == null)
                filterItemSelectorChoice = filterItemSelectorChoices.getDefaultValue();
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
    
    public String getFilterStepName() {
        return filterStepName;
    }
    
    public void setFilterStepName(String filterStepName) {
        this.filterStepName = filterStepName;
    }
    
    public String getFilterStepElementName() {
        return filterStepElementName;
    }
    
    public void setFilterStepElementName(String filterStepElementName) {
        this.filterStepElementName = filterStepElementName;
    }
    
    public List<LabelValueBean> getFilterAdjustmentChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;
        
        setupFilterAdjustmentChoices();
        if(filterAdjustmentChoices != null)
            choices = convertChoices(filterAdjustmentChoices);
        
        return choices;
    }
    
    public void setFilterAdjustmentChoice(String filterAdjustmentChoice) {
        this.filterAdjustmentChoice = filterAdjustmentChoice;
    }
    
    public String getFilterAdjustmentChoice()
            throws NamingException {
        setupFilterAdjustmentChoices();
        return filterAdjustmentChoice;
    }
    
    public List<LabelValueBean> getFilterItemSelectorChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;
        
        setupFilterItemSelectorChoices();
        if(filterItemSelectorChoices != null)
            choices = convertChoices(filterItemSelectorChoices);
        
        return choices;
    }
    
    public void setFilterItemSelectorChoice(String filterItemSelectorChoice) {
        this.filterItemSelectorChoice = filterItemSelectorChoice;
    }
    
    public String getFilterItemSelectorChoice()
            throws NamingException {
        setupFilterItemSelectorChoices();
        return filterItemSelectorChoice;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
    
}
