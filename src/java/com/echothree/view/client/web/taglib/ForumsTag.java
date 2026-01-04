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
import com.echothree.control.user.forum.common.result.GetForumsResult;
import com.echothree.model.data.forum.common.ForumConstants;
import com.echothree.util.common.form.TransferProperties;
import com.echothree.util.common.transfer.Limit;
import com.echothree.util.common.transfer.ListWrapper;
import java.util.HashMap;
import java.util.Map;
import javax.naming.NamingException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

public class ForumsTag
        extends BaseTag {
    
    protected String forumGroupName;
    protected String options;
    protected TransferProperties transferProperties;
    protected String forumCount;
    protected String forumOffset;
    protected String var;
    protected String countVar;
    protected String commandResultVar;
    protected int scope;
    protected boolean logErrors;
    
    private void init() {
        forumGroupName = null;
        options = null;
        transferProperties = null;
        forumCount = null;
        forumOffset = null;
        var = null;
        countVar = null;
        commandResultVar = null;
        scope = PageContext.PAGE_SCOPE;
        logErrors = true;
    }
    
    /** Creates a new instance of ForumsTag */
    public ForumsTag() {
        super();
        init();
    }
    
    @Override
    public void release() {
        super.release();
        init();
    }
    
    public void setForumGroupName(String forumGroupName) {
        this.forumGroupName = forumGroupName;
    }
    
    public void setOptions(String options) {
        this.options = options;
    }
    
    public void setTransferProperties(TransferProperties transferProperties) {
        this.transferProperties = transferProperties;
    }
    
    public void setForumCount(String forumCount) {
        this.forumCount = forumCount;
    }
    
    public void setForumOffset(String forumOffset) {
        this.forumOffset = forumOffset;
    }
    
    public void setVar(String var) {
        this.var = var;
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
            var commandForm = ForumUtil.getHome().getGetForumsForm();
            Map<String, Limit> limits = new HashMap<>();

            commandForm.setForumGroupName(forumGroupName);

            setOptions(options, null, commandForm);

            commandForm.setTransferProperties(transferProperties);
            
            if(forumCount != null || forumOffset != null) {
                limits.put(ForumConstants.ENTITY_TYPE_NAME, new Limit(forumCount, forumOffset));
            }
            commandForm.setLimits(limits);

            var commandResult = ForumUtil.getHome().getForums(getUserVisitPK(), commandForm);
            
            pageContext.setAttribute(commandResultVar == null ? TagConstants.CommandResultName : commandResultVar, commandResult, scope);
            if(commandResult.hasErrors()) {
                if(logErrors) {
                    getLog().error(commandResult);
                }
            } else {
                var executionResult = commandResult.getExecutionResult();
                var result = (GetForumsResult)executionResult.getResult();

                pageContext.setAttribute(var, new ListWrapper<>(result.getForums()), scope);

                if(countVar != null) {
                    pageContext.setAttribute(countVar, result.getForumCount(), scope);
                }
            }
        } catch (NamingException ne) {
            throw new JspException(ne);
        }
        
        return SKIP_BODY;
    }
    
}
