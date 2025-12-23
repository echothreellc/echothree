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
import com.echothree.control.user.search.common.result.GetUseTypeResultsResult;
import com.echothree.model.data.search.common.SearchResultConstants;
import com.echothree.util.common.form.TransferProperties;
import com.echothree.util.common.transfer.Limit;
import com.echothree.util.common.transfer.ListWrapper;
import java.util.HashMap;
import java.util.Map;
import javax.naming.NamingException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

public class GetUseTypeResultsTag
        extends BaseTag {
    
    protected String searchTypeName;
    protected String options;
    protected TransferProperties transferProperties;
    protected String useTypeResultCount;
    protected String useTypeResultOffset;
    protected String var;
    protected String countVar;
    protected String commandResultVar;
    protected int scope;
    protected boolean logErrors;
    
    private void init() {
        searchTypeName = null;
        options = null;
        transferProperties = null;
        useTypeResultCount = null;
        useTypeResultOffset = null;
        var = null;
        countVar = null;
        commandResultVar = null;
        scope = PageContext.PAGE_SCOPE;
        logErrors = true;
    }
    
    /** Creates a new instance of GetUseTypeResultsTag */
    public GetUseTypeResultsTag() {
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
    
    public void setUseTypeResultCount(String useTypeResultCount) {
        this.useTypeResultCount = useTypeResultCount;
    }
    
    public void setUseTypeResultOffset(String useTypeResultOffset) {
        this.useTypeResultOffset = useTypeResultOffset;
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
    
    public void setLogErrors(Boolean logErrors) {
        this.logErrors = logErrors;
    }
    
    @Override
    public int doStartTag() throws JspException {
        try {
            var commandForm = SearchUtil.getHome().getGetUseTypeResultsForm();
            Map<String, Limit> limits = new HashMap<>();
            
            commandForm.setSearchTypeName(searchTypeName);
            
            setOptions(options, null, commandForm);

            commandForm.setTransferProperties(transferProperties);
            
            if(useTypeResultCount != null || useTypeResultOffset != null) {
                limits.put(SearchResultConstants.ENTITY_TYPE_NAME, new Limit(useTypeResultCount, useTypeResultOffset));
            }
            commandForm.setLimits(limits);

            var commandResult = SearchUtil.getHome().getUseTypeResults(getUserVisitPK(), commandForm);
            
            pageContext.setAttribute(commandResultVar == null ? TagConstants.CommandResultName : commandResultVar, commandResult, scope);
            if(commandResult.hasErrors()) {
                if(logErrors) {
                    getLog().error(commandResult);
                }
            } else {
                var executionResult = commandResult.getExecutionResult();
                var result = (GetUseTypeResultsResult)executionResult.getResult();

                pageContext.setAttribute(var, new ListWrapper<>(result.getUseTypeResults()), scope);

                if(countVar != null) {
                    pageContext.setAttribute(countVar, result.getUseTypeResultCount(), scope);
                }
            }
        } catch (NamingException ne) {
            throw new JspException(ne);
        }
        
        return SKIP_BODY;
    }
    
}
