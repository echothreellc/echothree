// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
// Copyright 1999-2004 The Apache Software Foundation.
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

package com.echothree.view.client.web.taglib;

import com.echothree.control.user.search.common.SearchUtil;
import com.echothree.control.user.search.common.form.SearchItemsForm;
import com.echothree.control.user.search.common.result.SearchItemsResult;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.ExecutionResult;
import javax.naming.NamingException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

public class SearchItemsTag
        extends BaseTag {
    
    protected String languageIsoName;
    protected String searchTypeName;
    protected String searchDefaultOperatorName;
    protected String searchSortOrderName;
    protected String searchSortDirectionName;
    protected String rememberPreferences;
    protected String itemNameOrAlias;
    protected String description;
    protected String itemTypeName;
    protected String itemUseTypeName;
    protected String itemStatusChoice;
    protected String itemStatusChoices;
    protected String createdSince;
    protected String modifiedSince;
    protected String fields;
    protected String searchUseTypeName;
    protected String countVar;
    protected String commandResultVar;
    protected int scope;
    protected boolean logErrors;
    
    private void init() {
        languageIsoName = null;
        searchTypeName = null;
        searchDefaultOperatorName = null;
        searchSortOrderName = null;
        searchSortDirectionName = null;
        rememberPreferences = null;
        itemNameOrAlias = null;
        description = null;
        itemTypeName = null;
        itemUseTypeName = null;
        itemStatusChoice = null;
        itemStatusChoices = null;
        createdSince = null;
        modifiedSince = null;
        fields = null;
        searchUseTypeName = null;
        countVar = null;
        commandResultVar = null;
        scope = PageContext.PAGE_SCOPE;
        logErrors = true;
    }
    
    /** Creates a new instance of SearchItemsTag */
    public SearchItemsTag() {
        super();
        init();
    }
    
    @Override
    public void release() {
        super.release();
        init();
    }
    
    /**
     * @param languageIsoName the languageIsoName to set
     */
    public void setLanguageIsoName(String languageIsoName) {
        this.languageIsoName = languageIsoName;
    }

    /**
     * @param searchTypeName the searchTypeName to set
     */
    public void setSearchTypeName(String searchTypeName) {
        this.searchTypeName = searchTypeName;
    }

    /**
     * @param searchDefaultOperatorName the searchDefaultOperatorName to set
     */
    public void setSearchDefaultOperatorName(String searchDefaultOperatorName) {
        this.searchDefaultOperatorName = searchDefaultOperatorName;
    }

    /**
     * @param searchSortOrderName the searchSortOrderName to set
     */
    public void setSearchSortOrderName(String searchSortOrderName) {
        this.searchSortOrderName = searchSortOrderName;
    }

    /**
     * @param searchSortDirectionName the searchSortDirectionName to set
     */
    public void setSearchSortDirectionName(String searchSortDirectionName) {
        this.searchSortDirectionName = searchSortDirectionName;
    }

    /**
     * @param rememberPreferences the rememberPreferences to set
     */
    public void setRememberPreferences(String rememberPreferences) {
        this.rememberPreferences = rememberPreferences;
    }

    /**
     * @param itemNameOrAlias the itemNameOrAlias to set
     */
    public void setItemNameOrAlias(String itemNameOrAlias) {
        this.itemNameOrAlias = itemNameOrAlias;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @param itemTypeName the itemTypeName to set
     */
    public void setItemTypeName(String itemTypeName) {
        this.itemTypeName = itemTypeName;
    }

    /**
     * @param itemUseTypeName the itemUseTypeName to set
     */
    public void setItemUseTypeName(String itemUseTypeName) {
        this.itemUseTypeName = itemUseTypeName;
    }

    /**
     * @param itemStatusChoice the itemStatusChoice to set
     */
    public void setItemStatusChoice(String itemStatusChoice) {
        this.itemStatusChoice = itemStatusChoice;
    }

    /**
     * @param itemStatusChoices the itemStatusChoices to set
     */
    public void setItemStatusChoices(String itemStatusChoices) {
        this.itemStatusChoices = itemStatusChoices;
    }

    /**
     * @param createdSince the createdSince to set
     */
    public void setCreatedSince(String createdSince) {
        this.createdSince = createdSince;
    }

    /**
     * @param modifiedSince the modifiedSince to set
     */
    public void setModifiedSince(String modifiedSince) {
        this.modifiedSince = modifiedSince;
    }

    /**
     * @param fields the fields to set
     */
    public void setFields(String fields) {
        this.fields = fields;
    }

    /**
     * @param searchUseTypeName the searchUseTypeName to set
     */
    public void setSearchUseTypeName(String searchUseTypeName) {
        this.searchUseTypeName = searchUseTypeName;
    }

    /**
     * @param countVar the countVar to set
     */
    public void setCountVar(String countVar) {
        this.countVar = countVar;
    }

    public void setCommandResultVar(String commandResultVar) {
        this.commandResultVar = commandResultVar;
    }

    /**
     * @param scope the scope to set
     */
    public void setScope(String scope) {
        this.scope = translateScope(scope);
    }
    
    public void setLogErrors(Boolean logErrors) {
        this.logErrors = logErrors;
    }
    
    @Override
    public int doStartTag()
            throws JspException {
        try {
            SearchItemsForm commandForm = SearchUtil.getHome().getSearchItemsForm();
            
            commandForm.setLanguageIsoName(languageIsoName);
            commandForm.setSearchTypeName(searchTypeName);
            commandForm.setSearchDefaultOperatorName(searchDefaultOperatorName);
            commandForm.setSearchSortOrderName(searchSortOrderName);
            commandForm.setSearchSortDirectionName(searchSortDirectionName);
            commandForm.setRememberPreferences(rememberPreferences);
            commandForm.setItemNameOrAlias(itemNameOrAlias);
            commandForm.setDescription(description);
            commandForm.setItemTypeName(itemTypeName);
            commandForm.setItemUseTypeName(itemUseTypeName);
            commandForm.setItemStatusChoice(itemStatusChoice);
            commandForm.setItemStatusChoices(itemStatusChoices);
            commandForm.setCreatedSince(createdSince);
            commandForm.setModifiedSince(modifiedSince);
            commandForm.setFields(fields);
            commandForm.setSearchUseTypeName(searchUseTypeName);

            CommandResult commandResult = SearchUtil.getHome().searchItems(getUserVisitPK(), commandForm);
            
            pageContext.setAttribute(commandResultVar == null ? TagConstants.CommandResultName : commandResultVar, commandResult, scope);
            if(commandResult.hasErrors()) {
                if(logErrors) {
                    getLog().error(commandResult);
                }
            } else {
                ExecutionResult executionResult = commandResult.getExecutionResult();
                SearchItemsResult result = (SearchItemsResult)executionResult.getResult();

                if(countVar != null) {
                    pageContext.setAttribute(countVar, result.getCount(), scope);
                }
            }
        } catch (NamingException ne) {
            throw new JspException(ne);
        }
        
        return SKIP_BODY;
    }

}
