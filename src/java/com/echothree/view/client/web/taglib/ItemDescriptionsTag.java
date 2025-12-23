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
import com.echothree.control.user.item.common.result.GetItemDescriptionsResult;
import com.echothree.model.data.item.common.ItemDescriptionConstants;
import com.echothree.util.common.form.TransferProperties;
import com.echothree.util.common.transfer.Limit;
import com.echothree.util.common.transfer.ListWrapper;
import java.util.HashMap;
import java.util.Map;
import javax.naming.NamingException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

public class ItemDescriptionsTag
        extends BaseTag {
    
    protected String itemName;
    protected String itemDescriptionTypeUseTypeName;
    protected String languageIsoName;
    protected String options;
    protected TransferProperties transferProperties;
    protected String itemDescriptionCount;
    protected String itemDescriptionOffset;
    protected String var;
    protected String itemVar;
    protected String countVar;
    protected String commandResultVar;
    protected int scope;
    protected boolean logErrors;
    
    private void init() {
        itemName = null;
        itemDescriptionTypeUseTypeName = null;
        languageIsoName = null;
        options = null;
        transferProperties = null;
        itemDescriptionCount = null;
        itemDescriptionOffset = null;
        var = null;
        itemVar = null;
        countVar = null;
        commandResultVar = null;
        scope = PageContext.PAGE_SCOPE;
        logErrors = true;
    }
    
    /** Creates a new instance of ItemDescriptionsTag */
    public ItemDescriptionsTag() {
        super();
        init();
    }
    
    @Override
    public void release() {
        super.release();
        init();
    }
    
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setItemDescriptionTypeUseTypeName(String itemDescriptionTypeUseTypeName) {
        this.itemDescriptionTypeUseTypeName = itemDescriptionTypeUseTypeName;
    }

    public void setLanguageIsoName(String languageIsoName) {
        this.languageIsoName = languageIsoName;
    }

    public void setOptions(String options) {
        this.options = options;
    }
    
    public void setTransferProperties(TransferProperties transferProperties) {
        this.transferProperties = transferProperties;
    }

    public void setItemDescriptionCount(String itemDescriptionCount) {
        this.itemDescriptionCount = itemDescriptionCount;
    }

    public void setItemDescriptionOffset(String itemDescriptionOffset) {
        this.itemDescriptionOffset = itemDescriptionOffset;
    }

    public void setVar(String var) {
        this.var = var;
    }

    public void setItemVar(String itemVar) {
        this.itemVar = itemVar;
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
            var commandForm = ItemUtil.getHome().getGetItemDescriptionsForm();
            Map<String, Limit> limits = new HashMap<>();
            
            commandForm.setItemName(itemName);
            commandForm.setItemDescriptionTypeUseTypeName(itemDescriptionTypeUseTypeName);
            commandForm.setLanguageIsoName(languageIsoName);
            
            setOptions(options, null, commandForm);

            commandForm.setTransferProperties(transferProperties);
            
            if(itemDescriptionCount != null || itemDescriptionOffset != null) {
                limits.put(ItemDescriptionConstants.ENTITY_TYPE_NAME, new Limit(itemDescriptionCount, itemDescriptionOffset));
            }
            commandForm.setLimits(limits);

            var commandResult = ItemUtil.getHome().getItemDescriptions(getUserVisitPK(), commandForm);
            
            pageContext.setAttribute(commandResultVar == null ? TagConstants.CommandResultName : commandResultVar, commandResult, scope);
            if(commandResult.hasErrors()) {
                if(logErrors) {
                    getLog().error(commandResult);
                }
            } else {
                var executionResult = commandResult.getExecutionResult();
                var result = (GetItemDescriptionsResult)executionResult.getResult();

                pageContext.setAttribute(var, new ListWrapper<>(result.getItemDescriptions()), scope);

                if(itemVar != null) {
                    pageContext.setAttribute(itemVar, result.getItem(), scope);
                }

                if(countVar != null) {
                    pageContext.setAttribute(countVar, result.getItemDescriptionCount(), scope);
                }
            }
        } catch (NamingException ne) {
            throw new JspException(ne);
        }
        
        return SKIP_BODY;
    }
    
}
