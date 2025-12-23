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

import com.echothree.control.user.offer.common.OfferUtil;
import com.echothree.control.user.offer.common.result.GetUseTypesResult;
import com.echothree.model.data.offer.common.UseTypeConstants;
import com.echothree.util.common.form.TransferProperties;
import com.echothree.util.common.transfer.Limit;
import com.echothree.util.common.transfer.ListWrapper;
import java.util.HashMap;
import java.util.Map;
import javax.naming.NamingException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

public class UseTypesTag
        extends BaseTag {
    
    protected String options;
    protected TransferProperties transferProperties;
    protected String useTypeCount;
    protected String useTypeOffset;
    protected String var;
    protected String entityAttributeVar;
    protected String countVar;
    protected String commandResultVar;
    protected int scope;
    protected boolean logErrors;
    
    private void init() {
        options = null;
        transferProperties = null;
        useTypeCount = null;
        useTypeOffset = null;
        var = null;
        entityAttributeVar = null;
        countVar = null;
        commandResultVar = null;
        scope = PageContext.PAGE_SCOPE;
        logErrors = true;
    }
    
    /** Creates a new instance of UseTypesTag */
    public UseTypesTag() {
        super();
        init();
    }
    
    @Override
    public void release() {
        super.release();
        init();
    }
    
    public void setOptions(String options) {
        this.options = options;
    }
    
    public void setTransferProperties(TransferProperties transferProperties) {
        this.transferProperties = transferProperties;
    }

    public void setUseTypeCount(String useTypeCount) {
        this.useTypeCount = useTypeCount;
    }

    public void setUseTypeOffset(String useTypeOffset) {
        this.useTypeOffset = useTypeOffset;
    }

    public void setVar(String var) {
        this.var = var;
    }

    public void setEntityAttributeVar(String entityAttributeVar) {
        this.entityAttributeVar = entityAttributeVar;
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
    public int doStartTag()
            throws JspException {
        try {
            var commandForm = OfferUtil.getHome().getGetUseTypesForm();
            Map<String, Limit> limits = new HashMap<>();
            
            setOptions(options, null, commandForm);

            commandForm.setTransferProperties(transferProperties);
            
            if(useTypeCount != null || useTypeOffset != null) {
                limits.put(UseTypeConstants.ENTITY_TYPE_NAME, new Limit(useTypeCount, useTypeOffset));
            }
            commandForm.setLimits(limits);

            var commandResult = OfferUtil.getHome().getUseTypes(getUserVisitPK(), commandForm);
            
            pageContext.setAttribute(commandResultVar == null ? TagConstants.CommandResultName : commandResultVar, commandResult, scope);
            if(commandResult.hasErrors()) {
                if(logErrors) {
                    getLog().error(commandResult);
                }
            } else {
                var executionResult = commandResult.getExecutionResult();
                var result = (GetUseTypesResult)executionResult.getResult();

                pageContext.setAttribute(var, new ListWrapper<>(result.getUseTypes()), scope);

                if(countVar != null) {
                    pageContext.setAttribute(countVar, result.getUseTypeCount(), scope);
                }
            }
        } catch (NamingException ne) {
            throw new JspException(ne);
        }
        
        return SKIP_BODY;
    }
    
}
