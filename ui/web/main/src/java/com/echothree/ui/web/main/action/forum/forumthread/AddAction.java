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

package com.echothree.ui.web.main.action.forum.forumthread;

import com.echothree.control.user.forum.common.ForumUtil;
import com.echothree.control.user.forum.common.form.CreateBlogEntryForm;
import com.echothree.control.user.forum.common.form.GetForumForm;
import com.echothree.control.user.forum.common.result.GetForumResult;
import com.echothree.model.control.forum.common.ForumConstants;
import com.echothree.model.control.forum.common.transfer.ForumTransfer;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.ForwardConstants;
import com.echothree.ui.web.main.framework.MainBaseAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.ExecutionResult;
import com.echothree.view.client.web.struts.CustomActionForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import java.util.HashMap;
import java.util.Map;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

@SproutAction(
    path = "/Forum/ForumThread/Add",
    mappingClass = SecureActionMapping.class,
    name = "ForumThreadAdd",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Forum/ForumThread/Main", redirect = true),
        @SproutForward(name = "Form", path = "/forum/forumthread/add.jsp")
    }
)
public class AddAction
        extends MainBaseAction<ActionForm> {
    
    private ForumTransfer getForumTransfer(UserVisitPK userVisitPK, String forumName)
            throws NamingException {
        GetForumForm commandForm = ForumUtil.getHome().getGetForumForm();
        
        commandForm.setForumName(forumName);
        
        CommandResult commandResult = ForumUtil.getHome().getForum(userVisitPK, commandForm);
        ExecutionResult executionResult = commandResult.getExecutionResult();
        GetForumResult result = (GetForumResult)executionResult.getResult();
        
        return result.getForum();
    }
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String forwardKey = null;
        String forumName = request.getParameter(ParameterConstants.FORUM_NAME);
        AddActionForm actionForm = (AddActionForm)form;
        
        if(forumName == null)
            forumName = actionForm.getForumName();
        
        UserVisitPK userVisitPK = getUserVisitPK(request);
        ForumTransfer forum = getForumTransfer(userVisitPK, forumName);
        
        if(wasPost(request)) {
            String forumTypeName = forum.getForumType().getForumTypeName();
            CommandResult commandResult = null;
            
            if(forumTypeName.equals(ForumConstants.ForumType_BLOG)) {
                CreateBlogEntryForm commandForm = ForumUtil.getHome().getCreateBlogEntryForm();
                
                commandForm.setForumName(forumName);
                commandForm.setLanguageIsoName(actionForm.getLanguageChoice());
                commandForm.setForumThreadIconName(actionForm.getForumThreadIconChoice());
                commandForm.setPostedTime(actionForm.getPostedTime());
                commandForm.setSortOrder(actionForm.getSortOrder());
                commandForm.setForumMessageIconName(actionForm.getForumMessageIconChoice());
                commandForm.setTitle(actionForm.getTitle());
                commandForm.setFeedSummaryMimeTypeName(actionForm.getFeedSummaryMimeTypeChoice());
                commandForm.setFeedSummary(actionForm.getFeedSummary());
                commandForm.setSummaryMimeTypeName(actionForm.getSummaryMimeTypeChoice());
                commandForm.setSummary(actionForm.getSummary());
                commandForm.setContentMimeTypeName(actionForm.getContentMimeTypeChoice());
                commandForm.setContent(actionForm.getContent());
                
                commandResult = ForumUtil.getHome().createBlogEntry(getUserVisitPK(request), commandForm);
            }
            
            if(commandResult.hasErrors()) {
                setCommandResultAttribute(request, commandResult);
                forwardKey = ForwardConstants.FORM;
            } else {
                forwardKey = ForwardConstants.DISPLAY;
            }
        } else {
            actionForm.setForumName(forumName);
            actionForm.setSortOrder("1");
            forwardKey = ForwardConstants.FORM;
        }
        
        CustomActionForward customActionForward = new CustomActionForward(mapping.findForward(forwardKey));
        if(forwardKey.equals(ForwardConstants.FORM)) {
            request.setAttribute(AttributeConstants.FORUM_NAME, forumName);
            request.setAttribute(AttributeConstants.FORUM, forum);
        } else if(forwardKey.equals(ForwardConstants.DISPLAY)) {
            Map<String, String> parameters = new HashMap<>(1);
            
            parameters.put(ParameterConstants.FORUM_NAME, forumName);
            customActionForward.setParameters(parameters);
        }
        
        return customActionForward;
    }
    
}