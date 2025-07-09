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

package com.echothree.ui.web.main.action.filter.filteradjustment;

import com.echothree.control.user.filter.common.FilterUtil;
import com.echothree.control.user.filter.common.result.GetFilterAdjustmentSourceChoicesResult;
import com.echothree.control.user.filter.common.result.GetFilterAdjustmentTypeChoicesResult;
import com.echothree.model.control.filter.common.choice.FilterAdjustmentSourceChoicesBean;
import com.echothree.model.control.filter.common.choice.FilterAdjustmentTypeChoicesBean;
import com.echothree.view.client.web.struts.BaseActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;

@SproutForm(name="FilterAdjustmentAdd")
public class AddActionForm
        extends BaseActionForm {
    
    private FilterAdjustmentSourceChoicesBean filterAdjustmentSourceChoices;
    private FilterAdjustmentTypeChoicesBean filterAdjustmentTypeChoices;
    
    private String filterKindName;
    private String filterAdjustmentName;
    private String filterAdjustmentSourceChoice;
    private String filterAdjustmentTypeChoice;
    private Boolean isDefault;
    private String sortOrder;
    private String description;
    
    private void setupFilterAdjustmentSourceChoices()
            throws NamingException {
        if(filterAdjustmentSourceChoices == null) {
            var getFilterAdjustmentSourceChoicesForm = FilterUtil.getHome().getGetFilterAdjustmentSourceChoicesForm();

            getFilterAdjustmentSourceChoicesForm.setDefaultFilterAdjustmentSourceChoice(filterAdjustmentSourceChoice);

            var commandResult = FilterUtil.getHome().getFilterAdjustmentSourceChoices(userVisitPK, getFilterAdjustmentSourceChoicesForm);
            var executionResult = commandResult.getExecutionResult();
            var getFilterAdjustmentSourceChoicesResult = (GetFilterAdjustmentSourceChoicesResult)executionResult.getResult();
            filterAdjustmentSourceChoices = getFilterAdjustmentSourceChoicesResult.getFilterAdjustmentSourceChoices();

            if(filterAdjustmentSourceChoice == null)
                filterAdjustmentSourceChoice = filterAdjustmentSourceChoices.getDefaultValue();
        }
    }
    
    private void setupFilterAdjustmentTypeChoices()
            throws NamingException {
        if(filterAdjustmentTypeChoices == null) {
            var getFilterAdjustmentTypeChoicesForm = FilterUtil.getHome().getGetFilterAdjustmentTypeChoicesForm();

            getFilterAdjustmentTypeChoicesForm.setDefaultFilterAdjustmentTypeChoice(filterAdjustmentTypeChoice);

            var commandResult = FilterUtil.getHome().getFilterAdjustmentTypeChoices(userVisitPK, getFilterAdjustmentTypeChoicesForm);
            var executionResult = commandResult.getExecutionResult();
            var getFilterAdjustmentTypeChoicesResult = (GetFilterAdjustmentTypeChoicesResult)executionResult.getResult();
            filterAdjustmentTypeChoices = getFilterAdjustmentTypeChoicesResult.getFilterAdjustmentTypeChoices();

            if(filterAdjustmentTypeChoice == null)
                filterAdjustmentTypeChoice = filterAdjustmentTypeChoices.getDefaultValue();
        }
    }
    
    public void setFilterKindName(String filterKindName) {
        this.filterKindName = filterKindName;
    }
    
    public String getFilterKindName() {
        return filterKindName;
    }
    
    public void setFilterAdjustmentName(String filterAdjustmentName) {
        this.filterAdjustmentName = filterAdjustmentName;
    }
    
    public String getFilterAdjustmentName() {
        return filterAdjustmentName;
    }
    
    public List<LabelValueBean> getFilterAdjustmentSourceChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;
        
        setupFilterAdjustmentSourceChoices();
        if(filterAdjustmentSourceChoices != null)
            choices = convertChoices(filterAdjustmentSourceChoices);
        
        return choices;
    }
    
    public void setFilterAdjustmentSourceChoice(String filterAdjustmentSourceChoice) {
        this.filterAdjustmentSourceChoice = filterAdjustmentSourceChoice;
    }
    
    public String getFilterAdjustmentSourceChoice()
            throws NamingException {
        setupFilterAdjustmentSourceChoices();
        return filterAdjustmentSourceChoice;
    }
    
    public List<LabelValueBean> getFilterAdjustmentTypeChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;
        
        setupFilterAdjustmentTypeChoices();
        if(filterAdjustmentTypeChoices != null)
            choices = convertChoices(filterAdjustmentTypeChoices);
        
        return choices;
    }
    
    public void setFilterAdjustmentTypeChoice(String filterAdjustmentTypeChoice) {
        this.filterAdjustmentTypeChoice = filterAdjustmentTypeChoice;
    }
    
    public String getFilterAdjustmentTypeChoice()
            throws NamingException {
        setupFilterAdjustmentTypeChoices();
        return filterAdjustmentTypeChoice;
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
    
    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        super.reset(mapping, request);
        
        isDefault = false;
    }
    
}
