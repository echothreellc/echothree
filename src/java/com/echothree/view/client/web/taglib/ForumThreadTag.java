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
import com.echothree.control.user.forum.common.result.GetForumThreadResult;
import com.echothree.model.control.forum.common.ForumOptions;
import com.echothree.model.data.forum.common.ForumMessageConstants;
import com.echothree.util.common.form.TransferProperties;
import com.echothree.util.common.transfer.Limit;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.naming.NamingException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

public class ForumThreadTag
        extends BaseTag {
    
    protected String forumThreadName;
    protected String options;
    protected TransferProperties transferProperties;
    protected String forumMessageCount;
    protected String forumMessageOffset;
    protected String var;
    protected String commandResultVar;
    protected int scope;
    protected boolean logErrors;
    
    private void init() {
        forumThreadName = null;
        options = null;
        transferProperties = null;
        forumMessageCount = null;
        forumMessageOffset = null;
        var = null;
        commandResultVar = null;
        scope = PageContext.PAGE_SCOPE;
        logErrors = true;
    }
    
    /** Creates a new instance of ForumThreadTag */
    public ForumThreadTag() {
        super();
        init();
    }
    
    @Override
    public void release() {
        super.release();
        init();
    }
    
    public void setForumThreadName(String forumThreadName) {
        this.forumThreadName = forumThreadName;
    }
    
    public void setOptions(String options) {
        this.options = options;
    }
    
    public void setTransferProperties(TransferProperties transferProperties) {
        this.transferProperties = transferProperties;
    }
    
    public void setForumMessageCount(String forumMessageCount) {
        this.forumMessageCount = forumMessageCount;
    }
    
    public void setForumMessageOffset(String forumMessageOffset) {
        this.forumMessageOffset = forumMessageOffset;
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
                ForumOptions.ForumThreadIncludeForumMessages,
                ForumOptions.ForumMessageIncludeForumMessageRoles,
                ForumOptions.ForumMessageIncludeForumMessageParts,
                ForumOptions.ForumMessagePartIncludeString,
                ForumOptions.ForumMessagePartIncludeClob
                )));
    }
    
    @Override
    public int doStartTag() throws JspException {
        try {
            var commandForm = ForumUtil.getHome().getGetForumThreadForm();
            Map<String, Limit> limits = new HashMap<>();
            
            commandForm.setForumThreadName(forumThreadName);
            
            setOptions(options, defaultOptions, commandForm);

            commandForm.setTransferProperties(transferProperties);
            
            if(forumMessageCount != null || forumMessageOffset != null) {
                limits.put(ForumMessageConstants.ENTITY_TYPE_NAME, new Limit(forumMessageCount, forumMessageOffset));
            }
            commandForm.setLimits(limits);

            var commandResult = ForumUtil.getHome().getForumThread(getUserVisitPK(), commandForm);
            
            pageContext.setAttribute(commandResultVar == null ? TagConstants.CommandResultName : commandResultVar, commandResult, scope);
            if(commandResult.hasErrors()) {
                if(logErrors) {
                    getLog().error(commandResult);
                }
            } else {
                var executionResult = commandResult.getExecutionResult();
                var result = (GetForumThreadResult)executionResult.getResult();

                pageContext.setAttribute(var, result.getForumThread(), scope);
            }
        } catch (NamingException ne) {
            throw new JspException(ne);
        }
        
        return SKIP_BODY;
    }
    
}
