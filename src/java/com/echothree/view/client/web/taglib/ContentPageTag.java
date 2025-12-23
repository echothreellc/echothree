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
import com.echothree.control.user.content.common.result.GetContentPageResult;
import com.echothree.model.control.content.common.ContentOptions;
import com.echothree.util.common.form.TransferProperties;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import javax.naming.NamingException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

public class ContentPageTag
        extends BaseTag {

    protected String contentWebAddressName;
    protected String contentCollectionName;
    protected String contentSectionName;
    protected String contentPageName;
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
        contentSectionName = null;
        contentPageName = null;
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

    /** Creates a new instance of ContentPageTag */
    public ContentPageTag() {
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

    public void setContentSectionName(String contentSectionName) {
        this.contentSectionName = contentSectionName;
    }

    public void setContentPageName(String contentPageName) {
        this.contentPageName = contentPageName;
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

    protected final static Set<String> defaultOptions;
    
    static {
        defaultOptions = Collections.unmodifiableSet(new HashSet<>(Arrays.asList(
                ContentOptions.ContentPageIncludeContentPageAreas,
                ContentOptions.ContentPageAreaIncludeClob,
                ContentOptions.ContentPageAreaIncludeString,
                ContentOptions.ContentPageAreaIncludeUrl
                )));
    }
    
    @Override
    public int doStartTag()
            throws JspException {
        try {
            var commandForm = ContentUtil.getHome().getGetContentPageForm();

            commandForm.setContentWebAddressName(contentWebAddressName);
            commandForm.setContentCollectionName(contentCollectionName);
            commandForm.setContentSectionName(contentSectionName);
            commandForm.setContentPageName(contentPageName);
            commandForm.setAssociateProgramName(associateProgramName);
            commandForm.setAssociateName(associateName);
            commandForm.setAssociatePartyContactMechanismName(associateContactMechanismName);

            setOptions(options, defaultOptions, commandForm);

            commandForm.setTransferProperties(transferProperties);

            var commandResult = ContentUtil.getHome().getContentPage(getUserVisitPK(), commandForm);
            
            pageContext.setAttribute(commandResultVar == null ? TagConstants.CommandResultName : commandResultVar, commandResult, scope);
            if(commandResult.hasErrors()) {
                if(logErrors) {
                    getLog().error(commandResult);
                }
            } else {
                var executionResult = commandResult.getExecutionResult();
                var result = (GetContentPageResult)executionResult.getResult();

                pageContext.setAttribute(var, result.getContentPage(), scope);
            }
        } catch(NamingException ne) {
            throw new JspException(ne);
        }

        return SKIP_BODY;
    }

}