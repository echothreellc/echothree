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
import com.echothree.control.user.content.common.result.GetContentCatalogItemsResult;
import com.echothree.model.data.content.common.ContentCatalogItemConstants;
import com.echothree.util.common.form.TransferProperties;
import com.echothree.util.common.transfer.Limit;
import com.echothree.util.common.transfer.ListWrapper;
import java.util.HashMap;
import java.util.Map;
import javax.naming.NamingException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

public class ContentCatalogItemsTag
        extends BaseTag {

    protected String contentWebAddressName;
    protected String contentCollectionName;
    protected String contentCatalogName;
    protected String associateProgramName;
    protected String associateName;
    protected String associateContactMechanismName;
    protected String options;
    protected TransferProperties transferProperties;
    protected String contentCatalogItemOffset;
    protected String contentCatalogItemCount;
    protected String var;
    protected String countVar;
    protected String contentCatalogVar;
    protected String commandResultVar;
    protected int scope;
    protected boolean logErrors;

    private void init() {
        contentWebAddressName = null;
        contentCollectionName = null;
        contentCatalogName = null;
        associateProgramName = null;
        associateName = null;
        associateContactMechanismName = null;
        options = null;
        transferProperties = null;
        contentCatalogItemOffset = null;
        contentCatalogItemCount = null;
        var = null;
        countVar = null;
        contentCatalogVar = null;
        commandResultVar = null;
        scope = PageContext.PAGE_SCOPE;
        logErrors = true;
    }

    /** Creates a new instance of ContentCatalogItemsTag */
    public ContentCatalogItemsTag() {
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
    
    /**
     * Sets the contentCatalogItemOffset.
     * @param contentCatalogItemOffset the contentCatalogItemOffset to set
     */
    public void setContentCatalogItemOffset(String contentCatalogItemOffset) {
        this.contentCatalogItemOffset = contentCatalogItemOffset;
    }

    /**
     * Sets the contentCatalogItemCount.
     * @param contentCatalogItemCount the contentCatalogItemCount to set
     */
    public void setContentCatalogItemCount(String contentCatalogItemCount) {
        this.contentCatalogItemCount = contentCatalogItemCount;
    }

    public void setVar(String var) {
        this.var = var;
    }

    public void setContentCatalogVar(String contentCatalogVar) {
        this.contentCatalogVar = contentCatalogVar;
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
            var commandForm = ContentUtil.getHome().getGetContentCatalogItemsForm();
            Map<String, Limit> limits = new HashMap<>();

            commandForm.setContentWebAddressName(contentWebAddressName);
            commandForm.setContentCollectionName(contentCollectionName);
            commandForm.setContentCatalogName(contentCatalogName);
            commandForm.setAssociateProgramName(associateProgramName);
            commandForm.setAssociateName(associateName);
            commandForm.setAssociatePartyContactMechanismName(associateContactMechanismName);

            setOptions(options, null, commandForm);

            commandForm.setTransferProperties(transferProperties);

            if(contentCatalogItemCount != null || contentCatalogItemOffset != null) {
                limits.put(ContentCatalogItemConstants.ENTITY_TYPE_NAME, new Limit(contentCatalogItemCount, contentCatalogItemOffset));
            }
            commandForm.setLimits(limits);

            var commandResult = ContentUtil.getHome().getContentCatalogItems(getUserVisitPK(), commandForm);
            
            pageContext.setAttribute(commandResultVar == null ? TagConstants.CommandResultName : commandResultVar, commandResult, scope);
            if(commandResult.hasErrors()) {
                if(logErrors) {
                    getLog().error(commandResult);
                }
            } else {
                var executionResult = commandResult.getExecutionResult();
                var result = (GetContentCatalogItemsResult)executionResult.getResult();

                pageContext.setAttribute(var, new ListWrapper<>(result.getContentCatalogItems()), scope);

                if(countVar != null) {
                    pageContext.setAttribute(countVar, result.getContentCatalogItemCount(), scope);
                }
                
                if(contentCatalogVar != null) {
                    pageContext.setAttribute(contentCatalogVar, result.getContentCatalog(), scope);
                }
            }
        } catch(NamingException ne) {
            throw new JspException(ne);
        }

        return SKIP_BODY;
    }
    
}
