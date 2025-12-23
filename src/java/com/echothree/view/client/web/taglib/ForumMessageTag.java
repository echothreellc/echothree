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

import com.echothree.control.user.forum.common.ForumUtil;
import com.echothree.control.user.forum.common.result.GetForumMessageResult;
import com.echothree.model.control.forum.common.ForumOptions;
import com.echothree.util.common.form.TransferProperties;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import javax.naming.NamingException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

public class ForumMessageTag
        extends BaseTag {
    
    protected String forumMessageName;
    protected String options;
    protected TransferProperties transferProperties;
    protected String var;
    protected String commandResultVar;
    protected int scope;
    protected boolean logErrors;
    
    private void init() {
        forumMessageName = null;
        options = null;
        transferProperties = null;
        var = null;
        commandResultVar = null;
        scope = PageContext.PAGE_SCOPE;
        logErrors = true;
    }
    
    /** Creates a new instance of ForumMessageTag */
    public ForumMessageTag() {
        super();
        init();
    }
    
    @Override
    public void release() {
        super.release();
        init();
    }
    
    public void setForumMessageName(String forumMessageName) {
        this.forumMessageName = forumMessageName;
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
                ForumOptions.ForumMessageIncludeForumMessageRoles,
                ForumOptions.ForumMessageIncludeForumMessageParts,
                ForumOptions.ForumMessagePartIncludeString,
                ForumOptions.ForumMessagePartIncludeClob
                )));
    }
    
    @Override
    public int doStartTag() throws JspException {
        try {
            var commandForm = ForumUtil.getHome().getGetForumMessageForm();

            commandForm.setForumMessageName(forumMessageName);
            
            setOptions(options, defaultOptions, commandForm);

            commandForm.setTransferProperties(transferProperties);

            var commandResult = ForumUtil.getHome().getForumMessage(getUserVisitPK(), commandForm);
            
            pageContext.setAttribute(commandResultVar == null ? TagConstants.CommandResultName : commandResultVar, commandResult, scope);
            if(commandResult.hasErrors()) {
                if(logErrors) {
                    getLog().error(commandResult);
                }
            } else {
                var executionResult = commandResult.getExecutionResult();
                var result = (GetForumMessageResult)executionResult.getResult();

                pageContext.setAttribute(var, result.getForumMessage(), scope);
            }
        } catch (NamingException ne) {
            throw new JspException(ne);
        }
        
        return SKIP_BODY;
    }
    
}
