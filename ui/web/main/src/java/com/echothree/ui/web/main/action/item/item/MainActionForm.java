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

package com.echothree.ui.web.main.action.item.item;

import com.echothree.control.user.item.common.ItemUtil;
import com.echothree.control.user.item.common.result.GetItemTypeChoicesResult;
import com.echothree.control.user.item.common.result.GetItemUseTypeChoicesResult;
import com.echothree.control.user.search.common.SearchUtil;
import com.echothree.control.user.search.common.result.GetSearchDefaultOperatorChoicesResult;
import com.echothree.control.user.search.common.result.GetSearchSortDirectionChoicesResult;
import com.echothree.control.user.search.common.result.GetSearchSortOrderChoicesResult;
import com.echothree.control.user.workflow.common.WorkflowUtil;
import com.echothree.control.user.workflow.common.result.GetWorkflowStepChoicesResult;
import com.echothree.model.control.item.common.choice.ItemTypeChoicesBean;
import com.echothree.model.control.item.common.choice.ItemUseTypeChoicesBean;
import com.echothree.model.control.item.common.workflow.ItemStatusConstants;
import com.echothree.model.control.search.common.SearchKinds;
import com.echothree.model.control.search.common.SearchTypes;
import com.echothree.model.control.search.common.choice.SearchDefaultOperatorChoicesBean;
import com.echothree.model.control.search.common.choice.SearchSortDirectionChoicesBean;
import com.echothree.model.control.search.common.choice.SearchSortOrderChoicesBean;
import com.echothree.model.control.workflow.common.choice.WorkflowStepChoicesBean;
import com.echothree.view.client.web.struts.BaseActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;

@SproutForm(name="ItemMain")
public class MainActionForm
        extends BaseActionForm {
    
    private ItemTypeChoicesBean itemTypeChoices;
    private ItemUseTypeChoicesBean itemUseTypeChoices;
    private WorkflowStepChoicesBean itemStatusChoices;
    private SearchDefaultOperatorChoicesBean searchDefaultOperatorChoices;
    private SearchSortOrderChoicesBean searchSortOrderChoices;
    private SearchSortDirectionChoicesBean searchSortDirectionChoices;
    
    private String itemNameOrAlias;
    private String description;
    private String itemTypeChoice;
    private String itemUseTypeChoice;
    private String itemStatusChoice;
    private String createdSince;
    private String modifiedSince;
    private String searchDefaultOperatorChoice;
    private String searchSortOrderChoice;
    private String searchSortDirectionChoice;
    private Boolean rememberPreferences;
    
    private void setupItemTypeChoices()
            throws NamingException {
        if(itemTypeChoices == null) {
            var form = ItemUtil.getHome().getGetItemTypeChoicesForm();

            form.setDefaultItemTypeChoice(itemTypeChoice);
            form.setAllowNullChoice(String.valueOf(true));

            var commandResult = ItemUtil.getHome().getItemTypeChoices(userVisitPK, form);
            var executionResult = commandResult.getExecutionResult();
            var result = (GetItemTypeChoicesResult)executionResult.getResult();
            itemTypeChoices = result.getItemTypeChoices();

            if(itemTypeChoice == null) {
                itemTypeChoice = itemTypeChoices.getDefaultValue();
            }
        }
    }
    
    private void setupItemUseTypeChoices()
            throws NamingException {
        if(itemUseTypeChoices == null) {
            var form = ItemUtil.getHome().getGetItemUseTypeChoicesForm();

            form.setDefaultItemUseTypeChoice(itemUseTypeChoice);
            form.setAllowNullChoice(String.valueOf(true));

            var commandResult = ItemUtil.getHome().getItemUseTypeChoices(userVisitPK, form);
            var executionResult = commandResult.getExecutionResult();
            var result = (GetItemUseTypeChoicesResult)executionResult.getResult();
            itemUseTypeChoices = result.getItemUseTypeChoices();

            if(itemUseTypeChoice == null) {
                itemUseTypeChoice = itemUseTypeChoices.getDefaultValue();
            }
        }
    }
    
    private void setupItemStatusChoices()
            throws NamingException {
        if(itemStatusChoices == null) {
            var form = WorkflowUtil.getHome().getGetWorkflowStepChoicesForm();

            form.setWorkflowName(ItemStatusConstants.Workflow_ITEM_STATUS);
            form.setDefaultWorkflowStepChoice(itemStatusChoice);
            form.setAllowNullChoice(String.valueOf(true));

            var commandResult = WorkflowUtil.getHome().getWorkflowStepChoices(userVisitPK, form);
            var executionResult = commandResult.getExecutionResult();
            var result = (GetWorkflowStepChoicesResult)executionResult.getResult();
            itemStatusChoices = result.getWorkflowStepChoices();

            if(itemStatusChoice == null) {
                itemStatusChoice = ItemStatusConstants.WorkflowStep_ITEM_STATUS_ACTIVE;
            }
        }
    }
    
    private void setupSearchDefaultOperatorChoices()
            throws NamingException {
        if(searchDefaultOperatorChoices == null) {
            var form = SearchUtil.getHome().getGetSearchDefaultOperatorChoicesForm();

            form.setSearchKindName(SearchKinds.ITEM.name());
            form.setSearchTypeName(SearchTypes.ITEM_MAINTENANCE.name());
            form.setDefaultSearchDefaultOperatorChoice(searchDefaultOperatorChoice);
            form.setAllowNullChoice(String.valueOf(true));

            var commandResult = SearchUtil.getHome().getSearchDefaultOperatorChoices(userVisitPK, form);
            var executionResult = commandResult.getExecutionResult();
            var result = (GetSearchDefaultOperatorChoicesResult)executionResult.getResult();
            searchDefaultOperatorChoices = result.getSearchDefaultOperatorChoices();

            if(searchDefaultOperatorChoice == null) {
                searchDefaultOperatorChoice = searchDefaultOperatorChoices.getDefaultValue();
            }
        }
    }
    
    private void setupSearchSortOrderChoices()
            throws NamingException {
        if(searchSortOrderChoices == null) {
            var form = SearchUtil.getHome().getGetSearchSortOrderChoicesForm();

            form.setSearchKindName(SearchKinds.ITEM.name());
            form.setSearchTypeName(SearchTypes.ITEM_MAINTENANCE.name());
            form.setDefaultSearchSortOrderChoice(searchSortOrderChoice);
            form.setAllowNullChoice(String.valueOf(true));

            var commandResult = SearchUtil.getHome().getSearchSortOrderChoices(userVisitPK, form);
            var executionResult = commandResult.getExecutionResult();
            var result = (GetSearchSortOrderChoicesResult)executionResult.getResult();
            searchSortOrderChoices = result.getSearchSortOrderChoices();

            if(searchSortOrderChoice == null) {
                searchSortOrderChoice = searchSortOrderChoices.getDefaultValue();
            }
        }
    }
    
    private void setupSearchSortDirectionChoices()
            throws NamingException {
        if(searchSortDirectionChoices == null) {
            var form = SearchUtil.getHome().getGetSearchSortDirectionChoicesForm();

            form.setSearchKindName(SearchKinds.ITEM.name());
            form.setSearchTypeName(SearchTypes.ITEM_MAINTENANCE.name());
            form.setDefaultSearchSortDirectionChoice(searchSortDirectionChoice);
            form.setAllowNullChoice(String.valueOf(true));

            var commandResult = SearchUtil.getHome().getSearchSortDirectionChoices(userVisitPK, form);
            var executionResult = commandResult.getExecutionResult();
            var result = (GetSearchSortDirectionChoicesResult)executionResult.getResult();
            searchSortDirectionChoices = result.getSearchSortDirectionChoices();

            if(searchSortDirectionChoice == null) {
                searchSortDirectionChoice = searchSortDirectionChoices.getDefaultValue();
            }
        }
    }
    
    public String getItemNameOrAlias() {
        return itemNameOrAlias;
    }
    
    public void setItemNameOrAlias(String itemNameOrAlias) {
        this.itemNameOrAlias = itemNameOrAlias;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getItemTypeChoice()
            throws NamingException {
        setupItemTypeChoices();
        return itemTypeChoice;
    }
    
    public void setItemTypeChoice(String itemTypeChoice) {
        this.itemTypeChoice = itemTypeChoice;
    }
    
    public List<LabelValueBean> getItemTypeChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;
        
        setupItemTypeChoices();
        if(itemTypeChoices != null)
            choices = convertChoices(itemTypeChoices);
        
        return choices;
    }
    
    public String getItemUseTypeChoice()
            throws NamingException {
        setupItemUseTypeChoices();
        return itemUseTypeChoice;
    }
    
    public void setItemUseTypeChoice(String itemUseTypeChoice) {
        this.itemUseTypeChoice = itemUseTypeChoice;
    }
    
    public List<LabelValueBean> getItemUseTypeChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;
        
        setupItemUseTypeChoices();
        if(itemUseTypeChoices != null)
            choices = convertChoices(itemUseTypeChoices);
        
        return choices;
    }
    
    public String getItemStatusChoice()
            throws NamingException {
        setupItemStatusChoices();
        return itemStatusChoice;
    }
    
    public void setItemStatusChoice(String itemStatusChoice) {
        this.itemStatusChoice = itemStatusChoice;
    }
    
    public List<LabelValueBean> getItemStatusChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;
        
        setupItemStatusChoices();
        if(itemStatusChoices != null)
            choices = convertChoices(itemStatusChoices);
        
        return choices;
    }

    public String getCreatedSince() {
        return createdSince;
    }

    public void setCreatedSince(String createdSince) {
        this.createdSince = createdSince;
    }

    public String getModifiedSince() {
        return modifiedSince;
    }

    public void setModifiedSince(String modifiedSince) {
        this.modifiedSince = modifiedSince;
    }
    
    public String getSearchDefaultOperatorChoice()
            throws NamingException {
        setupSearchDefaultOperatorChoices();
        return searchDefaultOperatorChoice;
    }
    
    public void setSearchDefaultOperatorChoice(String searchDefaultOperatorChoice) {
        this.searchDefaultOperatorChoice = searchDefaultOperatorChoice;
    }
    
    public List<LabelValueBean> getSearchDefaultOperatorChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;
        
        setupSearchDefaultOperatorChoices();
        if(searchDefaultOperatorChoices != null)
            choices = convertChoices(searchDefaultOperatorChoices);
        
        return choices;
    }
    
    public String getSearchSortOrderChoice()
            throws NamingException {
        setupSearchSortOrderChoices();
        return searchSortOrderChoice;
    }
    
    public void setSearchSortOrderChoice(String searchSortOrderChoice) {
        this.searchSortOrderChoice = searchSortOrderChoice;
    }
    
    public List<LabelValueBean> getSearchSortOrderChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;
        
        setupSearchSortOrderChoices();
        if(searchSortOrderChoices != null)
            choices = convertChoices(searchSortOrderChoices);
        
        return choices;
    }
    
    public String getSearchSortDirectionChoice()
            throws NamingException {
        setupSearchSortDirectionChoices();
        return searchSortDirectionChoice;
    }
    
    public void setSearchSortDirectionChoice(String searchSortDirectionChoice) {
        this.searchSortDirectionChoice = searchSortDirectionChoice;
    }
    
    public List<LabelValueBean> getSearchSortDirectionChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;
        
        setupSearchSortDirectionChoices();
        if(searchSortDirectionChoices != null)
            choices = convertChoices(searchSortDirectionChoices);
        
        return choices;
    }
    
    public Boolean getRememberPreferences() {
        return rememberPreferences;
    }
    
    public void setRememberPreferences(Boolean rememberPreferences) {
        this.rememberPreferences = rememberPreferences;
    }
    
    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        super.reset(mapping, request);
        
        setRememberPreferences(false);
    }
    
}
