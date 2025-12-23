// --------------------------------------------------------------------------------
// Copyright 2002-2026 Echo Three, LLC
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

package com.echothree.ui.web.main.action.forum.forummessage;

import com.echothree.control.user.forum.common.ForumUtil;
import com.echothree.control.user.forum.common.result.GetForumMessageResult;
import com.echothree.model.control.forum.common.ForumConstants;
import com.echothree.model.control.forum.common.ForumOptions;
import com.echothree.model.control.forum.common.transfer.ForumMessageTransfer;
import com.echothree.model.control.forum.common.transfer.ForumTransfer;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.ForwardConstants;
import com.echothree.ui.web.main.framework.MainBaseAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.util.common.command.CommandResult;
import com.echothree.view.client.web.struts.CustomActionForward;
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
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

@SproutAction(
    path = "/Forum/ForumMessage/Add",
    mappingClass = SecureActionMapping.class,
    name = "ForumMessageAdd",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Forum/ForumThread/Main", redirect = true),
        @SproutForward(name = "Form", path = "/forum/forummessage/add.jsp")
    }
)
public class AddAction
        extends MainBaseAction<ActionForm> {
    
    private ForumMessageTransfer getForumMessageTransfer(UserVisitPK userVisitPK, String parentForumMessageName)
            throws NamingException {
        var commandForm = ForumUtil.getHome().getGetForumMessageForm();
        
        commandForm.setForumMessageName(parentForumMessageName);
        
        Set<String> options = new HashSet<>();
        options.add(ForumOptions.ForumThreadIncludeForumForumThreads);
        commandForm.setOptions(options);

        var commandResult = ForumUtil.getHome().getForumMessage(userVisitPK, commandForm);
        var executionResult = commandResult.getExecutionResult();
        var result = (GetForumMessageResult)executionResult.getResult();
        
        return result.getForumMessage();
    }
    
    public ForumTransfer getDefaultForum(ForumMessageTransfer forumMessage) {
        var forumThread = forumMessage.getForumThread();
        ForumTransfer forum = null;
        
        if(forumThread != null) {
            for(var forumForumThread: forumThread.getForumForumThreads().getList()) {
                if(forumForumThread.getIsDefault()) {
                    forum = forumForumThread.getForum();
                    break;
                }
            }
        }
        
        return forum;
    }
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String forwardKey;
        var forumName = request.getParameter(ParameterConstants.FORUM_NAME);
        var parentForumMessageName = request.getParameter(ParameterConstants.PARENT_FORUM_MESSAGE_NAME);
        var actionForm = (AddActionForm)form;
        
        if(forumName == null)
            forumName = actionForm.getForumName();
        if(parentForumMessageName == null)
            parentForumMessageName = actionForm.getParentForumMessageName();

        var userVisitPK = getUserVisitPK(request);
        var forumMessage = getForumMessageTransfer(userVisitPK, parentForumMessageName);
        
        if(wasPost(request)) {
            var forumTypeName = getDefaultForum(forumMessage).getForumType().getForumTypeName();
            CommandResult commandResult = null;
            
            if(forumTypeName.equals(ForumConstants.ForumType_BLOG)) {
                var commandForm = ForumUtil.getHome().getCreateBlogCommentForm();
                
                commandForm.setParentForumMessageName(parentForumMessageName);
                commandForm.setLanguageIsoName(actionForm.getLanguageChoice());
                commandForm.setForumMessageIconName(actionForm.getForumMessageIconChoice());
                commandForm.setPostedTime(actionForm.getPostedTime());
                commandForm.setTitle(actionForm.getTitle());
                commandForm.setContentMimeTypeName(actionForm.getContentMimeTypeChoice());
                commandForm.setContent(actionForm.getContent());
                
                commandResult = ForumUtil.getHome().createBlogComment(getUserVisitPK(request), commandForm);
            }
            
            if(commandResult.hasErrors()) {
                setCommandResultAttribute(request, commandResult);
                forwardKey = ForwardConstants.FORM;
            } else {
                forwardKey = ForwardConstants.DISPLAY;
            }
        } else {
            actionForm.setForumName(forumName);
            actionForm.setParentForumMessageName(parentForumMessageName);
            forwardKey = ForwardConstants.FORM;
        }

        var customActionForward = new CustomActionForward(mapping.findForward(forwardKey));
        if(forwardKey.equals(ForwardConstants.FORM)) {
            request.setAttribute(AttributeConstants.FORUM_NAME, forumName);
            request.setAttribute(AttributeConstants.PARENT_FORUM_MESSAGE, forumMessage);
        } else if(forwardKey.equals(ForwardConstants.DISPLAY)) {
            Map<String, String> parameters = new HashMap<>(1);
            
            parameters.put(ParameterConstants.FORUM_NAME, forumName);
            customActionForward.setParameters(parameters);
        }
        
        return customActionForward;
    }
    
}
