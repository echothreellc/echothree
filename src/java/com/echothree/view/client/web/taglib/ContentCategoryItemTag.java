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

import com.echothree.control.user.content.common.ContentUtil;
import com.echothree.control.user.content.common.result.GetContentCategoryItemResult;
import com.echothree.util.common.form.TransferProperties;
import javax.naming.NamingException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

public class ContentCategoryItemTag
        extends BaseTag {

    protected String contentWebAddressName;
    protected String contentCollectionName;
    protected String contentCatalogName;
    protected String contentCategoryName;
    protected String itemName;
    protected String inventoryConditionName;
    protected String unitOfMeasureTypeName;
    protected String currencyIsoName;
    protected String associateProgramName;
    protected String associateName;
    protected String associateContactMechanismName;
    protected String options;
    protected TransferProperties transferProperties;
    protected String var;
    protected String commandResultVar;
    protected int scope;
    protected boolean logErrors;

    private void init() {
        contentWebAddressName = null;
        contentCollectionName = null;
        contentCatalogName = null;
        contentCategoryName = null;
        itemName = null;
        inventoryConditionName = null;
        unitOfMeasureTypeName = null;
        currencyIsoName = null;
        associateProgramName = null;
        associateName = null;
        associateContactMechanismName = null;
        options = null;
        transferProperties = null;
        var = null;
        commandResultVar = null;
        scope = PageContext.PAGE_SCOPE;
        logErrors = true;
    }

    /** Creates a new instance of ContentCategoryItemTag */
    public ContentCategoryItemTag() {
        super();
        init();
    }

    @Override
    public void release() {
        super.release();
        init();
    }

    public void setContentWebAddressName(String contentWebAddressName) {
        this.contentWebAddressName = contentWebAddressName;
    }

    public void setContentCollectionName(String contentCollectionName) {
        this.contentCollectionName = contentCollectionName;
    }

    public void setContentCatalogName(String contentCatalogName) {
        this.contentCatalogName = contentCatalogName;
    }

    public void setContentCategoryName(String contentCategoryName) {
        this.contentCategoryName = contentCategoryName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setInventoryConditionName(String inventoryConditionName) {
        this.inventoryConditionName = inventoryConditionName;
    }

    public void setUnitOfMeasureTypeName(String unitOfMeasureTypeName) {
        this.unitOfMeasureTypeName = unitOfMeasureTypeName;
    }

    public void setCurrencyIsoName(String currencyIsoName) {
        this.currencyIsoName = currencyIsoName;
    }
    
    public void setAssociateProgramName(String associateProgramName) {
        this.associateProgramName = associateProgramName;
    }

    public void setAssociateName(String associateName) {
        this.associateName = associateName;
    }

    public void setAssociateContactMechanismName(String associateContactMechanismName) {
        this.associateContactMechanismName = associateContactMechanismName;
    }

    public void setOptions(String options) {
        this.options = options;
    }
    
    public void setTransferProperties(TransferProperties transferProperties) {
        this.transferProperties = transferProperties;
    }
    
    public void setVar(String var) {
        this.var = var;
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
            var commandForm = ContentUtil.getHome().getGetContentCategoryItemForm();

            commandForm.setContentWebAddressName(contentWebAddressName);
            commandForm.setContentCollectionName(contentCollectionName);
            commandForm.setContentCatalogName(contentCatalogName);
            commandForm.setContentCategoryName(contentCategoryName);
            commandForm.setItemName(itemName);
            commandForm.setInventoryConditionName(inventoryConditionName);
            commandForm.setUnitOfMeasureTypeName(unitOfMeasureTypeName);
            commandForm.setCurrencyIsoName(currencyIsoName);
            commandForm.setAssociateProgramName(associateProgramName);
            commandForm.setAssociateName(associateName);
            commandForm.setAssociatePartyContactMechanismName(associateContactMechanismName);

            setOptions(options, null, commandForm);

            commandForm.setTransferProperties(transferProperties);

            var commandResult = ContentUtil.getHome().getContentCategoryItem(getUserVisitPK(), commandForm);
            
            pageContext.setAttribute(commandResultVar == null ? TagConstants.CommandResultName : commandResultVar, commandResult, scope);
            if(commandResult.hasErrors()) {
                if(logErrors) {
                    getLog().error(commandResult);
                }
            } else {
                var executionResult = commandResult.getExecutionResult();
                var result = (GetContentCategoryItemResult)executionResult.getResult();

                pageContext.setAttribute(var, result.getContentCategoryItem(), scope);
            }
        } catch(NamingException ne) {
            throw new JspException(ne);
        }

        return SKIP_BODY;
    }
    
}
