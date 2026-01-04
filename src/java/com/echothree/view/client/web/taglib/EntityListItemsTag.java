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

import com.echothree.control.user.core.common.CoreUtil;
import com.echothree.control.user.core.common.result.GetEntityListItemsResult;
import com.echothree.model.data.core.common.EntityListItemConstants;
import com.echothree.util.common.form.TransferProperties;
import com.echothree.util.common.transfer.Limit;
import com.echothree.util.common.transfer.ListWrapper;
import java.util.HashMap;
import java.util.Map;
import javax.naming.NamingException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

public class EntityListItemsTag
        extends BaseTag {
    
    protected String componentVendorName;
    protected String entityTypeName;
    protected String entityAttributeName;
    protected String options;
    protected TransferProperties transferProperties;
    protected String entityListItemCount;
    protected String entityListItemOffset;
    protected String var;
    protected String entityAttributeVar;
    protected String countVar;
    protected String commandResultVar;
    protected int scope;
    protected boolean logErrors;
    
    private void init() {
        componentVendorName = null;
        entityTypeName = null;
        entityAttributeName = null;
        options = null;
        transferProperties = null;
        entityListItemCount = null;
        entityListItemOffset = null;
        var = null;
        entityAttributeVar = null;
        countVar = null;
        commandResultVar = null;
        scope = PageContext.PAGE_SCOPE;
        logErrors = true;
    }
    
    /** Creates a new instance of EntityListItemsTag */
    public EntityListItemsTag() {
        super();
        init();
    }
    
    @Override
    public void release() {
        super.release();
        init();
    }
    
    public void setComponentVendorName(String componentVendorName) {
        this.componentVendorName = componentVendorName;
    }

    public void setEntityTypeName(String entityTypeName) {
        this.entityTypeName = entityTypeName;
    }

    public void setEntityAttributeName(String entityAttributeName) {
        this.entityAttributeName = entityAttributeName;
    }

    public void setOptions(String options) {
        this.options = options;
    }
    
    public void setTransferProperties(TransferProperties transferProperties) {
        this.transferProperties = transferProperties;
    }

    public void setEntityListItemCount(String entityListItemCount) {
        this.entityListItemCount = entityListItemCount;
    }

    public void setEntityListItemOffset(String entityListItemOffset) {
        this.entityListItemOffset = entityListItemOffset;
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
            var commandForm = CoreUtil.getHome().getGetEntityListItemsForm();
            Map<String, Limit> limits = new HashMap<>();
            
            commandForm.setComponentVendorName(componentVendorName);
            commandForm.setEntityTypeName(entityTypeName);
            commandForm.setEntityAttributeName(entityAttributeName);
            
            setOptions(options, null, commandForm);

            commandForm.setTransferProperties(transferProperties);
            
            if(entityListItemCount != null || entityListItemOffset != null) {
                limits.put(EntityListItemConstants.ENTITY_TYPE_NAME, new Limit(entityListItemCount, entityListItemOffset));
            }
            commandForm.setLimits(limits);

            var commandResult = CoreUtil.getHome().getEntityListItems(getUserVisitPK(), commandForm);
            
            pageContext.setAttribute(commandResultVar == null ? TagConstants.CommandResultName : commandResultVar, commandResult, scope);
            if(commandResult.hasErrors()) {
                if(logErrors) {
                    getLog().error(commandResult);
                }
            } else {
                var executionResult = commandResult.getExecutionResult();
                var result = (GetEntityListItemsResult)executionResult.getResult();

                pageContext.setAttribute(var, new ListWrapper<>(result.getEntityListItems()), scope);

                if(entityAttributeVar != null) {
                    pageContext.setAttribute(entityAttributeVar, result.getEntityAttribute(), scope);
                }

                if(countVar != null) {
                    pageContext.setAttribute(countVar, result.getEntityListItemCount(), scope);
                }
            }
        } catch (NamingException ne) {
            throw new JspException(ne);
        }
        
        return SKIP_BODY;
    }
    
}
