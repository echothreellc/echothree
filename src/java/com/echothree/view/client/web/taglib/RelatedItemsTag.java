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

import com.echothree.control.user.item.common.ItemUtil;
import com.echothree.control.user.item.common.result.GetRelatedItemsResult;
import com.echothree.model.data.item.common.RelatedItemConstants;
import com.echothree.util.common.form.TransferProperties;
import com.echothree.util.common.transfer.Limit;
import com.echothree.util.common.transfer.ListWrapper;
import java.util.HashMap;
import java.util.Map;
import javax.naming.NamingException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

public class RelatedItemsTag
        extends BaseTag {

    protected String relatedItemTypeName;
    protected String fromItemName;
    protected String toItemName;
    protected String options;
    protected TransferProperties transferProperties;
    protected String relatedItemCount;
    protected String relatedItemOffset;
    protected String var;
    protected String relatedItemTypeVar;
    protected String fromItemVar;
    protected String toItemVar;
    protected String countVar;
    protected String commandResultVar;
    protected int scope;
    protected boolean logErrors;
    
    private void init() {
        relatedItemTypeName = null;
        fromItemName = null;
        toItemName = null;
        options = null;
        transferProperties = null;
        relatedItemCount = null;
        relatedItemOffset = null;
        var = null;
        relatedItemTypeVar = null;
        fromItemVar = null;
        toItemVar = null;
        countVar = null;
        commandResultVar = null;
        scope = PageContext.PAGE_SCOPE;
        logErrors = true;
    }
    
    /** Creates a new instance of RelatedItemsTag */
    public RelatedItemsTag() {
        super();
        init();
    }
    
    @Override
    public void release() {
        super.release();
        init();
    }
    
    public void setRelatedItemTypeName(String relatedItemTypeName) {
        this.relatedItemTypeName = relatedItemTypeName;
    }

    public void setFromItemName(String fromItemName) {
        this.fromItemName = fromItemName;
    }

    public void setToItemName(String toItemName) {
        this.toItemName = toItemName;
    }

    public void setOptions(String options) {
        this.options = options;
    }
    
    public void setTransferProperties(TransferProperties transferProperties) {
        this.transferProperties = transferProperties;
    }

    public void setRelatedItemCount(String relatedItemCount) {
        this.relatedItemCount = relatedItemCount;
    }

    public void setRelatedItemOffset(String relatedItemOffset) {
        this.relatedItemOffset = relatedItemOffset;
    }

    public void setVar(String var) {
        this.var = var;
    }

    public void setRelatedItemTypeVar(String relatedItemTypeVar) {
        this.relatedItemTypeVar = relatedItemTypeVar;
    }

    public void setFromItemVar(String fromItemVar) {
        this.fromItemVar = fromItemVar;
    }

    public void setToItemVar(String toItemVar) {
        this.toItemVar = toItemVar;
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
            var commandForm = ItemUtil.getHome().getGetRelatedItemsForm();
            Map<String, Limit> limits = new HashMap<>();
            
            commandForm.setRelatedItemTypeName(relatedItemTypeName);
            commandForm.setFromItemName(fromItemName);
            commandForm.setToItemName(toItemName);
            
            setOptions(options, null, commandForm);

            commandForm.setTransferProperties(transferProperties);
            
            if(relatedItemCount != null || relatedItemOffset != null) {
                limits.put(RelatedItemConstants.ENTITY_TYPE_NAME, new Limit(relatedItemCount, relatedItemOffset));
            }
            commandForm.setLimits(limits);

            var commandResult = ItemUtil.getHome().getRelatedItems(getUserVisitPK(), commandForm);
            
            pageContext.setAttribute(commandResultVar == null ? TagConstants.CommandResultName : commandResultVar, commandResult, scope);
            if(commandResult.hasErrors()) {
                if(logErrors) {
                    getLog().error(commandResult);
                }
            } else {
                var executionResult = commandResult.getExecutionResult();
                var result = (GetRelatedItemsResult)executionResult.getResult();

                pageContext.setAttribute(var, new ListWrapper<>(result.getRelatedItems()), scope);

                if(relatedItemTypeVar != null) {
                    pageContext.setAttribute(relatedItemTypeVar, result.getRelatedItemType(), scope);
                }

                if(fromItemVar != null) {
                    pageContext.setAttribute(fromItemVar, result.getFromItem(), scope);
                }

                if(toItemVar != null) {
                    pageContext.setAttribute(toItemVar, result.getToItem(), scope);
                }

                if(countVar != null) {
                    pageContext.setAttribute(countVar, result.getRelatedItemCount(), scope);
                }
            }
        } catch (NamingException ne) {
            throw new JspException(ne);
        }
        
        return SKIP_BODY;
    }

}
