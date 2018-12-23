// --------------------------------------------------------------------------------
// Copyright 2002-2019 Echo Three, LLC
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

package com.echothree.ui.web.main.action.forum.forumthread;

import com.echothree.control.user.forum.common.ForumUtil;
import com.echothree.control.user.forum.common.form.DeleteForumThreadForm;
import com.echothree.control.user.forum.common.form.GetForumForm;
import com.echothree.control.user.forum.common.form.GetForumThreadForm;
import com.echothree.control.user.forum.common.result.GetForumResult;
import com.echothree.control.user.forum.common.result.GetForumThreadResult;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.forum.common.ForumOptions;
import com.echothree.model.data.forum.common.ForumMessageConstants;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.MainBaseDeleteAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.ExecutionResult;
import com.echothree.util.common.transfer.Limit;
import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;

@SproutAction(
    path = "/Forum/ForumThread/Delete",
    mappingClass = SecureActionMapping.class,
    name = "ForumThreadDelete",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Forum/ForumThread/Main", redirect = true),
        @SproutForward(name = "Form", path = "/forum/forumthread/delete.jsp")
    }
)
public class DeleteAction
        extends MainBaseDeleteAction<DeleteActionForm> {
    
    @Override
    public String getEntityTypeName() {
        return EntityTypes.ForumThread.name();
    }
    
    @Override
    public void setupParameters(DeleteActionForm actionForm, HttpServletRequest request) {
        actionForm.setForumName(findParameter(request, ParameterConstants.FORUM_NAME, actionForm.getForumName()));
        actionForm.setForumThreadName(findParameter(request, ParameterConstants.FORUM_THREAD_NAME, actionForm.getForumThreadName()));
    }
    
    public void setupForumTransfer(DeleteActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        GetForumForm commandForm = ForumUtil.getHome().getGetForumForm();
        
        commandForm.setForumName(actionForm.getForumName());
        
        CommandResult commandResult = ForumUtil.getHome().getForum(getUserVisitPK(request), commandForm);
        ExecutionResult executionResult = commandResult.getExecutionResult();
        GetForumResult result = (GetForumResult)executionResult.getResult();
        
        request.setAttribute(AttributeConstants.FORUM, result.getForum());
    }
    
    public void setupForumThreadTransfer(DeleteActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        GetForumThreadForm commandForm = ForumUtil.getHome().getGetForumThreadForm();
        Set<String> commandOptions = new HashSet<>();
        Map<String, Limit> limits = new HashMap<>();
        
        commandForm.setForumThreadName(actionForm.getForumThreadName());

        commandOptions.add(ForumOptions.ForumThreadIncludeForumMessages);
        commandOptions.add(ForumOptions.ForumMessageIncludeForumMessageParts);
        commandOptions.add(ForumOptions.ForumMessagePartIncludeString);
        commandForm.setOptions(commandOptions);
        
        limits.put(ForumMessageConstants.ENTITY_TYPE_NAME, new Limit("1", null));
        commandForm.setLimits(limits);

        CommandResult commandResult = ForumUtil.getHome().getForumThread(getUserVisitPK(request), commandForm);
        ExecutionResult executionResult = commandResult.getExecutionResult();
        GetForumThreadResult result = (GetForumThreadResult)executionResult.getResult();
        
        request.setAttribute(AttributeConstants.FORUM_THREAD, result.getForumThread());
    }
    
    @Override
    public void setupTransfer(DeleteActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        setupForumTransfer(actionForm, request);
        setupForumThreadTransfer(actionForm, request);
    }
    
    @Override
    public CommandResult doDelete(DeleteActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        DeleteForumThreadForm commandForm = ForumUtil.getHome().getDeleteForumThreadForm();

        commandForm.setForumThreadName(actionForm.getForumThreadName());

        return ForumUtil.getHome().deleteForumThread(getUserVisitPK(request), commandForm);
    }
    
    @Override
    public void setupForwardParameters(DeleteActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.FORUM_NAME, actionForm.getForumName());
    }
    
}