// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
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

/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.echothree.view.client.web.taglib;

import com.echothree.control.user.core.common.CoreUtil;
import com.echothree.control.user.core.common.form.GetPartyApplicationEditorUseForm;
import com.echothree.control.user.core.common.result.GetPartyApplicationEditorUseResult;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.ExecutionResult;
import com.echothree.util.common.form.TransferProperties;
import javax.naming.NamingException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

public class PartyApplicationEditorUseTag
        extends BaseTag {
    
    protected String partyName;
    protected String applicationName;
    protected String applicationEditorUseName;
    protected String options;
    protected TransferProperties transferProperties;
    protected String var;
    protected String commandResultVar;
    protected int scope;
    protected boolean logErrors;
    
    private void init() {
        partyName = null;
        applicationName = null;
        applicationEditorUseName = null;
        options = null;
        transferProperties = null;
        var = null;
        commandResultVar = null;
        scope = PageContext.PAGE_SCOPE;
        logErrors = true;
    }
    
    /** Creates a new instance of PartyApplicationEditorUseTag */
    public PartyApplicationEditorUseTag() {
        super();
        init();
    }
    
    @Override
    public void release() {
        super.release();
        init();
    }
    
    public void setPartyName(String partyName) {
        this.partyName = partyName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public void setApplicationEditorUseName(String applicationEditorUseName) {
        this.applicationEditorUseName = applicationEditorUseName;
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
            GetPartyApplicationEditorUseForm commandForm = CoreUtil.getHome().getGetPartyApplicationEditorUseForm();
            
            commandForm.setPartyName(partyName);
            commandForm.setApplicationName(applicationName);
            commandForm.setApplicationEditorUseName(applicationEditorUseName);
            
            setOptions(options, null, commandForm);

            commandForm.setTransferProperties(transferProperties);
            
            CommandResult commandResult = CoreUtil.getHome().getPartyApplicationEditorUse(getUserVisitPK(), commandForm);
            
            pageContext.setAttribute(commandResultVar == null ? TagConstants.CommandResultName : commandResultVar, commandResult, scope);
            if(commandResult.hasErrors()) {
                if(logErrors) {
                    getLog().error(commandResult);
                }
            } else {
                ExecutionResult executionResult = commandResult.getExecutionResult();
                GetPartyApplicationEditorUseResult result = (GetPartyApplicationEditorUseResult)executionResult.getResult();

                pageContext.setAttribute(var, result.getPartyApplicationEditorUse(), scope);
            }
        } catch (NamingException ne) {
            throw new JspException(ne);
        }
        
        return SKIP_BODY;
    }
    
}
