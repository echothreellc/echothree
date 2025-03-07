// --------------------------------------------------------------------------------
// Copyright 2002-2025 Echo Three, LLC
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
import com.echothree.control.user.search.common.result.GetItemResultsResult;
import com.echothree.model.data.search.common.SearchResultConstants;
import com.echothree.util.common.form.TransferProperties;
import com.echothree.util.common.transfer.Limit;
import com.echothree.util.common.transfer.ListWrapper;
import java.util.HashMap;
import java.util.Map;
import javax.naming.NamingException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

public class GetItemResultsTag
        extends BaseTag {
    
    protected String searchTypeName;
    protected String options;
    protected TransferProperties transferProperties;
    protected String itemResultCount;
    protected String itemResultOffset;
    protected String var;
    protected String countVar;
    protected String commandResultVar;
    protected int scope;
    protected boolean logError;
    
    private void init() {
        searchTypeName = null;
        options = null;
        transferProperties = null;
        itemResultCount = null;
        itemResultOffset = null;
        var = null;
        countVar = null;
        commandResultVar = null;
        scope = PageContext.PAGE_SCOPE;
        logError = true;
    }
    
    /** Creates a new instance of GetItemResultsTag */
    public GetItemResultsTag() {
        super();
        init();
    }
    
    @Override
    public void release() {
        super.release();
        init();
    }
    
    public void setSearchTypeName(String searchTypeName) {
        this.searchTypeName = searchTypeName;
    }
    
    public void setOptions(String options) {
        this.options = options;
    }
    
    public void setTransferProperties(TransferProperties transferProperties) {
        this.transferProperties = transferProperties;
    }
    
    public void setItemResultCount(String itemResultCount) {
        this.itemResultCount = itemResultCount;
    }
    
    public void setItemResultOffset(String itemResultOffset) {
        this.itemResultOffset = itemResultOffset;
    }
    
    public void setVar(String var) {
        this.var = var;
    }
    
    public void setCountVar(String countVar) {
        this.countVar = countVar;
    }
    
    public void setCommandResultVar(String commandResultVar) {
        this.commandResultVar = commandResultVar;
    }

    public void setScope(String scope) {
        this.scope = translateScope(scope);
    }
    
    public void setLogError(Boolean logError) {
        this.logError = logError;
    }
    
    @Override
    public int doStartTag() throws JspException {
        try {
            var commandForm = SearchUtil.getHome().getGetItemResultsForm();
            Map<String, Limit> limits = new HashMap<>();
            
            commandForm.setSearchTypeName(searchTypeName);
            
            setOptions(options, null, commandForm);

            commandForm.setTransferProperties(transferProperties);
            
            if(itemResultCount != null || itemResultOffset != null) {
                limits.put(SearchResultConstants.ENTITY_TYPE_NAME, new Limit(itemResultCount, itemResultOffset));
            }
            commandForm.setLimits(limits);

            var commandResult = SearchUtil.getHome().getItemResults(getUserVisitPK(), commandForm);
            
            pageContext.setAttribute(commandResultVar == null ? TagConstants.CommandResultName : commandResultVar, commandResult, scope);
            if(commandResult.hasErrors()) {
                if(logError) {
                    getLog().error(commandResult);
                }
            } else {
                var executionResult = commandResult.getExecutionResult();
                var result = (GetItemResultsResult)executionResult.getResult();

                pageContext.setAttribute(var, new ListWrapper<>(result.getItemResults()), scope);

                if(countVar != null) {
                    pageContext.setAttribute(countVar, result.getItemResultCount(), scope);
                }
            }
        } catch (NamingException ne) {
            throw new JspException(ne);
        }
        
        return SKIP_BODY;
    }
    
}
