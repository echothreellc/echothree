// --------------------------------------------------------------------------------
// Copyright 2002-2021 Echo Three, LLC
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
import com.echothree.control.user.item.common.form.GetItemTypeChoicesForm;
import com.echothree.control.user.item.common.form.GetItemUseTypeChoicesForm;
import com.echothree.control.user.item.common.result.GetItemTypeChoicesResult;
import com.echothree.control.user.item.common.result.GetItemUseTypeChoicesResult;
import com.echothree.control.user.search.common.SearchUtil;
import com.echothree.control.user.search.common.form.GetSearchDefaultOperatorChoicesForm;
import com.echothree.control.user.search.common.form.GetSearchSortDirectionChoicesForm;
import com.echothree.control.user.search.common.form.GetSearchSortOrderChoicesForm;
import com.echothree.control.user.search.common.result.GetSearchDefaultOperatorChoicesResult;
import com.echothree.control.user.search.common.result.GetSearchSortDirectionChoicesResult;
import com.echothree.control.user.search.common.result.GetSearchSortOrderChoicesResult;
import com.echothree.control.user.workflow.common.WorkflowUtil;
import com.echothree.control.user.workflow.common.form.GetWorkflowStepChoicesForm;
import com.echothree.control.user.workflow.common.result.GetWorkflowStepChoicesResult;
import com.echothree.model.control.item.common.choice.ItemTypeChoicesBean;
import com.echothree.model.control.item.common.choice.ItemUseTypeChoicesBean;
import com.echothree.model.control.search.common.SearchConstants;
import com.echothree.model.control.search.common.choice.SearchDefaultOperatorChoicesBean;
import com.echothree.model.control.search.common.choice.SearchSortDirectionChoicesBean;
import com.echothree.model.control.search.common.choice.SearchSortOrderChoicesBean;
import com.echothree.model.control.item.common.workflow.ItemStatusConstants;
import com.echothree.model.control.workflow.common.choice.WorkflowStepChoicesBean;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.ExecutionResult;
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
    
    private void setupItemTypeChoices() {
        if(itemTypeChoices == null) {
            try {
                GetItemTypeChoicesForm form = ItemUtil.getHome().getGetItemTypeChoicesForm();
                
                form.setDefaultItemTypeChoice(itemTypeChoice);
                form.setAllowNullChoice(Boolean.TRUE.toString());
                
                CommandResult commandResult = ItemUtil.getHome().getItemTypeChoices(userVisitPK, form);
                ExecutionResult executionResult = commandResult.getExecutionResult();
                GetItemTypeChoicesResult result = (GetItemTypeChoicesResult)executionResult.getResult();
                itemTypeChoices = result.getItemTypeChoices();
                
                if(itemTypeChoice == null) {
                    itemTypeChoice = itemTypeChoices.getDefaultValue();
                }
            } catch (NamingException ne) {
                ne.printStackTrace();
                // failed, itemTypeChoices remains null, no default
            }
        }
    }
    
    private void setupItemUseTypeChoices() {
        if(itemUseTypeChoices == null) {
            try {
                GetItemUseTypeChoicesForm form = ItemUtil.getHome().getGetItemUseTypeChoicesForm();
                
                form.setDefaultItemUseTypeChoice(itemUseTypeChoice);
                form.setAllowNullChoice(Boolean.TRUE.toString());
                
                CommandResult commandResult = ItemUtil.getHome().getItemUseTypeChoices(userVisitPK, form);
                ExecutionResult executionResult = commandResult.getExecutionResult();
                GetItemUseTypeChoicesResult result = (GetItemUseTypeChoicesResult)executionResult.getResult();
                itemUseTypeChoices = result.getItemUseTypeChoices();
                
                if(itemUseTypeChoice == null) {
                    itemUseTypeChoice = itemUseTypeChoices.getDefaultValue();
                }
            } catch (NamingException ne) {
                ne.printStackTrace();
                // failed, itemUseTypeChoices remains null, no default
            }
        }
    }
    
    private void setupItemStatusChoices() {
        if(itemStatusChoices == null) {
            try {
                GetWorkflowStepChoicesForm form = WorkflowUtil.getHome().getGetWorkflowStepChoicesForm();
                
                form.setWorkflowName(ItemStatusConstants.Workflow_ITEM_STATUS);
                form.setDefaultWorkflowStepChoice(itemStatusChoice);
                form.setAllowNullChoice(Boolean.TRUE.toString());
                
                CommandResult commandResult = WorkflowUtil.getHome().getWorkflowStepChoices(userVisitPK, form);
                ExecutionResult executionResult = commandResult.getExecutionResult();
                GetWorkflowStepChoicesResult result = (GetWorkflowStepChoicesResult)executionResult.getResult();
                itemStatusChoices = result.getWorkflowStepChoices();
                
                if(itemStatusChoice == null) {
                    itemStatusChoice = ItemStatusConstants.WorkflowStep_ITEM_STATUS_ACTIVE;
                }
            } catch (NamingException ne) {
                ne.printStackTrace();
                // failed, itemStatusChoices remains null, no default
            }
        }
    }
    
    private void setupSearchDefaultOperatorChoices() {
        if(searchDefaultOperatorChoices == null) {
            try {
                GetSearchDefaultOperatorChoicesForm form = SearchUtil.getHome().getGetSearchDefaultOperatorChoicesForm();
                
                form.setSearchKindName(SearchConstants.SearchKind_ITEM);
                form.setSearchTypeName(SearchConstants.SearchType_ITEM_MAINTENANCE);
                form.setDefaultSearchDefaultOperatorChoice(searchDefaultOperatorChoice);
                form.setAllowNullChoice(Boolean.TRUE.toString());
                
                CommandResult commandResult = SearchUtil.getHome().getSearchDefaultOperatorChoices(userVisitPK, form);
                ExecutionResult executionResult = commandResult.getExecutionResult();
                GetSearchDefaultOperatorChoicesResult result = (GetSearchDefaultOperatorChoicesResult)executionResult.getResult();
                searchDefaultOperatorChoices = result.getSearchDefaultOperatorChoices();
                
                if(searchDefaultOperatorChoice == null) {
                    searchDefaultOperatorChoice = searchDefaultOperatorChoices.getDefaultValue();
                }
            } catch (NamingException ne) {
                ne.printStackTrace();
                // failed, searchDefaultOperatorChoices remains null, no default
            }
        }
    }
    
    private void setupSearchSortOrderChoices() {
        if(searchSortOrderChoices == null) {
            try {
                GetSearchSortOrderChoicesForm form = SearchUtil.getHome().getGetSearchSortOrderChoicesForm();
                
                form.setSearchKindName(SearchConstants.SearchKind_ITEM);
                form.setSearchTypeName(SearchConstants.SearchType_ITEM_MAINTENANCE);
                form.setDefaultSearchSortOrderChoice(searchSortOrderChoice);
                form.setAllowNullChoice(Boolean.TRUE.toString());
                
                CommandResult commandResult = SearchUtil.getHome().getSearchSortOrderChoices(userVisitPK, form);
                ExecutionResult executionResult = commandResult.getExecutionResult();
                GetSearchSortOrderChoicesResult result = (GetSearchSortOrderChoicesResult)executionResult.getResult();
                searchSortOrderChoices = result.getSearchSortOrderChoices();
                
                if(searchSortOrderChoice == null) {
                    searchSortOrderChoice = searchSortOrderChoices.getDefaultValue();
                }
            } catch (NamingException ne) {
                ne.printStackTrace();
                // failed, searchSortOrderChoices remains null, no default
            }
        }
    }
    
    private void setupSearchSortDirectionChoices() {
        if(searchSortDirectionChoices == null) {
            try {
                GetSearchSortDirectionChoicesForm form = SearchUtil.getHome().getGetSearchSortDirectionChoicesForm();
                
                form.setSearchKindName(SearchConstants.SearchKind_ITEM);
                form.setSearchTypeName(SearchConstants.SearchType_ITEM_MAINTENANCE);
                form.setDefaultSearchSortDirectionChoice(searchSortDirectionChoice);
                form.setAllowNullChoice(Boolean.TRUE.toString());
                
                CommandResult commandResult = SearchUtil.getHome().getSearchSortDirectionChoices(userVisitPK, form);
                ExecutionResult executionResult = commandResult.getExecutionResult();
                GetSearchSortDirectionChoicesResult result = (GetSearchSortDirectionChoicesResult)executionResult.getResult();
                searchSortDirectionChoices = result.getSearchSortDirectionChoices();
                
                if(searchSortDirectionChoice == null) {
                    searchSortDirectionChoice = searchSortDirectionChoices.getDefaultValue();
                }
            } catch (NamingException ne) {
                ne.printStackTrace();
                // failed, searchSortDirectionChoices remains null, no default
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
    
    public String getItemTypeChoice() {
        setupItemTypeChoices();
        return itemTypeChoice;
    }
    
    public void setItemTypeChoice(String itemTypeChoice) {
        this.itemTypeChoice = itemTypeChoice;
    }
    
    public List<LabelValueBean> getItemTypeChoices() {
        List<LabelValueBean> choices = null;
        
        setupItemTypeChoices();
        if(itemTypeChoices != null)
            choices = convertChoices(itemTypeChoices);
        
        return choices;
    }
    
    public String getItemUseTypeChoice() {
        setupItemUseTypeChoices();
        return itemUseTypeChoice;
    }
    
    public void setItemUseTypeChoice(String itemUseTypeChoice) {
        this.itemUseTypeChoice = itemUseTypeChoice;
    }
    
    public List<LabelValueBean> getItemUseTypeChoices() {
        List<LabelValueBean> choices = null;
        
        setupItemUseTypeChoices();
        if(itemUseTypeChoices != null)
            choices = convertChoices(itemUseTypeChoices);
        
        return choices;
    }
    
    public String getItemStatusChoice() {
        setupItemStatusChoices();
        return itemStatusChoice;
    }
    
    public void setItemStatusChoice(String itemStatusChoice) {
        this.itemStatusChoice = itemStatusChoice;
    }
    
    public List<LabelValueBean> getItemStatusChoices() {
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
    
    public String getSearchDefaultOperatorChoice() {
        setupSearchDefaultOperatorChoices();
        return searchDefaultOperatorChoice;
    }
    
    public void setSearchDefaultOperatorChoice(String searchDefaultOperatorChoice) {
        this.searchDefaultOperatorChoice = searchDefaultOperatorChoice;
    }
    
    public List<LabelValueBean> getSearchDefaultOperatorChoices() {
        List<LabelValueBean> choices = null;
        
        setupSearchDefaultOperatorChoices();
        if(searchDefaultOperatorChoices != null)
            choices = convertChoices(searchDefaultOperatorChoices);
        
        return choices;
    }
    
    public String getSearchSortOrderChoice() {
        setupSearchSortOrderChoices();
        return searchSortOrderChoice;
    }
    
    public void setSearchSortOrderChoice(String searchSortOrderChoice) {
        this.searchSortOrderChoice = searchSortOrderChoice;
    }
    
    public List<LabelValueBean> getSearchSortOrderChoices() {
        List<LabelValueBean> choices = null;
        
        setupSearchSortOrderChoices();
        if(searchSortOrderChoices != null)
            choices = convertChoices(searchSortOrderChoices);
        
        return choices;
    }
    
    public String getSearchSortDirectionChoice() {
        setupSearchSortDirectionChoices();
        return searchSortDirectionChoice;
    }
    
    public void setSearchSortDirectionChoice(String searchSortDirectionChoice) {
        this.searchSortDirectionChoice = searchSortDirectionChoice;
    }
    
    public List<LabelValueBean> getSearchSortDirectionChoices() {
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
        
        setRememberPreferences(Boolean.FALSE);
    }
    
}
