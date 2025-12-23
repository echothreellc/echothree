// --------------------------------------------------------------------------------
// Copyright 2002-2026 Echo Three, LLC
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
import com.echothree.control.user.search.common.result.SearchContentCategoriesResult;
import javax.naming.NamingException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

public class SearchContentCategoriesTag
        extends BaseTag {
    
    protected String languageIsoName;
    protected String searchTypeName;
    protected String searchDefaultOperatorName;
    protected String searchSortOrderName;
    protected String searchSortDirectionName;
    protected String rememberPreferences;
    protected String q;
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
        q = null;
        createdSince = null;
        modifiedSince = null;
        fields = null;
        searchUseTypeName = null;
        countVar = null;
        commandResultVar = null;
        scope = PageContext.PAGE_SCOPE;
        logErrors = true;
    }
    
    /** Creates a new instance of SearchContentCategoriesTag */
    public SearchContentCategoriesTag() {
        super();
        init();
    }
    
    @Override
    public void release() {
        super.release();
        init();
    }
    
    /**
     * Sets the languageIsoName.
     * @param languageIsoName the languageIsoName to set
     */
    public void setLanguageIsoName(String languageIsoName) {
        this.languageIsoName = languageIsoName;
    }

    /**
     * Sets the searchTypeName.
     * @param searchTypeName the searchTypeName to set
     */
    public void setSearchTypeName(String searchTypeName) {
        this.searchTypeName = searchTypeName;
    }

    /**
     * Sets the searchDefaultOperatorName.
     * @param searchDefaultOperatorName the searchDefaultOperatorName to set
     */
    public void setSearchDefaultOperatorName(String searchDefaultOperatorName) {
        this.searchDefaultOperatorName = searchDefaultOperatorName;
    }

    /**
     * Sets the searchSortOrderName.
     * @param searchSortOrderName the searchSortOrderName to set
     */
    public void setSearchSortOrderName(String searchSortOrderName) {
        this.searchSortOrderName = searchSortOrderName;
    }

    /**
     * Sets the searchSortDirectionName.
     * @param searchSortDirectionName the searchSortDirectionName to set
     */
    public void setSearchSortDirectionName(String searchSortDirectionName) {
        this.searchSortDirectionName = searchSortDirectionName;
    }

    /**
     * Sets the rememberPreferences.
     * @param rememberPreferences the rememberPreferences to set
     */
    public void setRememberPreferences(String rememberPreferences) {
        this.rememberPreferences = rememberPreferences;
    }

    /**
     * Sets the q.
     * @param q the q to set
     */
    public void setQ(String q) {
        this.q = q;
    }

    /**
     * Sets the createdSince.
     * @param createdSince the createdSince to set
     */
    public void setCreatedSince(String createdSince) {
        this.createdSince = createdSince;
    }

    /**
     * Sets the modifiedSince.
     * @param modifiedSince the modifiedSince to set
     */
    public void setModifiedSince(String modifiedSince) {
        this.modifiedSince = modifiedSince;
    }

    /**
     * Sets the fields.
     * @param fields the fields to set
     */
    public void setFields(String fields) {
        this.fields = fields;
    }

    /**
     * Sets the searchUseTypeName.
     * @param searchUseTypeName the searchUseTypeName to set
     */
    public void setSearchUseTypeName(String searchUseTypeName) {
        this.searchUseTypeName = searchUseTypeName;
    }

    /**
     * Sets the countVar.
     * @param countVar the countVar to set
     */
    public void setCountVar(String countVar) {
        this.countVar = countVar;
    }

    public void setCommandResultVar(String commandResultVar) {
        this.commandResultVar = commandResultVar;
    }

    /**
     * Sets the scope.
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
            var commandForm = SearchUtil.getHome().getSearchContentCategoriesForm();
            
            commandForm.setLanguageIsoName(languageIsoName);
            commandForm.setSearchTypeName(searchTypeName);
            commandForm.setSearchDefaultOperatorName(searchDefaultOperatorName);
            commandForm.setSearchSortOrderName(searchSortOrderName);
            commandForm.setSearchSortDirectionName(searchSortDirectionName);
            commandForm.setRememberPreferences(rememberPreferences);
            commandForm.setQ(q);
            commandForm.setCreatedSince(createdSince);
            commandForm.setModifiedSince(modifiedSince);
            commandForm.setFields(fields);
            commandForm.setSearchUseTypeName(searchUseTypeName);

            var commandResult = SearchUtil.getHome().searchContentCategories(getUserVisitPK(), commandForm);
            
            pageContext.setAttribute(commandResultVar == null ? TagConstants.CommandResultName : commandResultVar, commandResult, scope);
            if(commandResult.hasErrors()) {
                if(logErrors) {
                    getLog().error(commandResult);
                }
            } else {
                var executionResult = commandResult.getExecutionResult();
                var result = (SearchContentCategoriesResult)executionResult.getResult();

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
