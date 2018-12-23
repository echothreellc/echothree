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

package com.echothree.ui.web.main.action.forum.forummessage;

import com.echothree.control.user.forum.common.ForumUtil;
import com.echothree.control.user.forum.common.edit.BlogCommentEdit;
import com.echothree.control.user.forum.common.edit.BlogEntryEdit;
import com.echothree.control.user.forum.common.form.EditBlogCommentForm;
import com.echothree.control.user.forum.common.form.EditBlogEntryForm;
import com.echothree.control.user.forum.common.form.GetForumMessageForm;
import com.echothree.control.user.forum.common.result.EditBlogCommentResult;
import com.echothree.control.user.forum.common.result.EditBlogEntryResult;
import com.echothree.control.user.forum.common.result.GetForumMessageResult;
import com.echothree.control.user.forum.common.spec.ForumMessageSpec;
import com.echothree.model.control.forum.common.ForumConstants;
import com.echothree.model.control.forum.common.transfer.ForumMessageTransfer;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.ForwardConstants;
import com.echothree.ui.web.main.framework.MainBaseAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.common.command.ExecutionResult;
import com.echothree.view.client.web.struts.CustomActionForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

@SproutAction(
    path = "/Forum/ForumMessage/Edit",
    mappingClass = SecureActionMapping.class,
    name = "ForumMessageEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Forum/ForumThread/Main", redirect = true),
        @SproutForward(name = "Form", path = "/forum/forummessage/edit.jsp")
    }
)
public class EditAction
        extends MainBaseAction<EditActionForm> {

    private String editBlogEntry(ActionMapping mapping, EditActionForm actionForm, HttpServletRequest request, String forumName, String forumMessageName)
            throws Exception {
        String forwardKey = null;
        EditBlogEntryForm commandForm = ForumUtil.getHome().getEditBlogEntryForm();
        ForumMessageSpec spec = ForumUtil.getHome().getForumMessageSpec();

        commandForm.setSpec(spec);
        spec.setForumMessageName(forumMessageName);

        if(wasPost(request)) {
            boolean wasCancelled = wasCancelled(request);
            
            if(wasCancelled) {
                commandForm.setEditMode(EditMode.ABANDON);
            } else {
                BlogEntryEdit edit = ForumUtil.getHome().getBlogEntryEdit();

                commandForm.setEditMode(EditMode.UPDATE);
                commandForm.setEdit(edit);

                edit.setForumThreadIconName(actionForm.getForumThreadIconChoice());
                edit.setPostedTime(actionForm.getPostedTime());
                edit.setSortOrder(actionForm.getSortOrder());
                edit.setForumMessageIconName(actionForm.getForumMessageIconChoice());
                edit.setTitle(actionForm.getTitle());
                edit.setFeedSummaryMimeTypeName(actionForm.getFeedSummaryMimeTypeChoice());
                edit.setFeedSummary(actionForm.getFeedSummary());
                edit.setSummaryMimeTypeName(actionForm.getSummaryMimeTypeChoice());
                edit.setSummary(actionForm.getSummary());
                edit.setContentMimeTypeName(actionForm.getContentMimeTypeChoice());
                edit.setContent(actionForm.getContent());
            }
            
            CommandResult commandResult = ForumUtil.getHome().editBlogEntry(getUserVisitPK(request), commandForm);

            if(commandResult.hasErrors() && !wasCancelled) {
                ExecutionResult executionResult = commandResult.getExecutionResult();

                if(executionResult != null) {
                    EditBlogEntryResult result = (EditBlogEntryResult)executionResult.getResult();

                    request.setAttribute(AttributeConstants.ENTITY_LOCK, result.getEntityLock());
                }

                setCommandResultAttribute(request, commandResult);

                forwardKey = ForwardConstants.FORM;
            } else {
                forwardKey = ForwardConstants.DISPLAY;
            }
        } else {
            commandForm.setEditMode(EditMode.LOCK);

            CommandResult commandResult = ForumUtil.getHome().editBlogEntry(getUserVisitPK(request), commandForm);
            ExecutionResult executionResult = commandResult.getExecutionResult();
            EditBlogEntryResult result = (EditBlogEntryResult)executionResult.getResult();

            if(result != null) {
                BlogEntryEdit edit = result.getEdit();

                if(edit != null) {
                    actionForm.setForumName(forumName);
                    actionForm.setForumMessageName(forumMessageName);
                    actionForm.setForumThreadIconChoice(edit.getForumThreadIconName());
                    actionForm.setPostedTime(edit.getPostedTime());
                    actionForm.setSortOrder(edit.getSortOrder());
                    actionForm.setForumMessageIconChoice(edit.getForumMessageIconName());
                    actionForm.setTitle(edit.getTitle());
                    actionForm.setFeedSummaryMimeTypeChoice(edit.getFeedSummaryMimeTypeName());
                    actionForm.setFeedSummary(edit.getFeedSummary());
                    actionForm.setSummaryMimeTypeChoice(edit.getSummaryMimeTypeName());
                    actionForm.setSummary(edit.getSummary());
                    actionForm.setContentMimeTypeChoice(edit.getContentMimeTypeName());
                    actionForm.setContent(edit.getContent());
                    actionForm.saveDtParameters(request);
                }

                request.setAttribute(AttributeConstants.ENTITY_LOCK, result.getEntityLock());
            }

            setCommandResultAttribute(request, commandResult);

            forwardKey = ForwardConstants.FORM;
        }

        return forwardKey;
    }

    private String editBlogComment(ActionMapping mapping, EditActionForm actionForm, HttpServletRequest request, String forumName, String forumMessageName)
            throws Exception {
        String forwardKey = null;
        EditBlogCommentForm commandForm = ForumUtil.getHome().getEditBlogCommentForm();
        ForumMessageSpec spec = ForumUtil.getHome().getForumMessageSpec();

        commandForm.setSpec(spec);
        spec.setForumMessageName(forumMessageName);

        if(wasPost(request)) {
            boolean wasCancelled = wasCancelled(request);
            
            if(wasCancelled) {
                commandForm.setEditMode(EditMode.ABANDON);
            } else {
                BlogCommentEdit edit = ForumUtil.getHome().getBlogCommentEdit();

                commandForm.setEditMode(EditMode.UPDATE);
                commandForm.setEdit(edit);

                edit.setForumMessageIconName(actionForm.getForumMessageIconChoice());
                edit.setPostedTime(actionForm.getPostedTime());
                edit.setTitle(actionForm.getTitle());
                edit.setContentMimeTypeName(actionForm.getContentMimeTypeChoice());
                edit.setContent(actionForm.getContent());
            }
            
            CommandResult commandResult = ForumUtil.getHome().editBlogComment(getUserVisitPK(request), commandForm);

            if(commandResult.hasErrors() && !wasCancelled) {
                ExecutionResult executionResult = commandResult.getExecutionResult();

                if(executionResult != null) {
                    EditBlogCommentResult result = (EditBlogCommentResult)executionResult.getResult();

                    request.setAttribute(AttributeConstants.ENTITY_LOCK, result.getEntityLock());
                }

                setCommandResultAttribute(request, commandResult);

                forwardKey = ForwardConstants.FORM;
            } else {
                forwardKey = ForwardConstants.DISPLAY;
            }
        } else {
            commandForm.setEditMode(EditMode.LOCK);

            CommandResult commandResult = ForumUtil.getHome().editBlogComment(getUserVisitPK(request), commandForm);
            ExecutionResult executionResult = commandResult.getExecutionResult();
            EditBlogCommentResult result = (EditBlogCommentResult)executionResult.getResult();

            if(result != null) {
                BlogCommentEdit edit = result.getEdit();

                if(edit != null) {
                    actionForm.setForumName(forumName);
                    actionForm.setForumMessageName(forumMessageName);
                    actionForm.setForumMessageIconChoice(edit.getForumMessageIconName());
                    actionForm.setPostedTime(edit.getPostedTime());
                    actionForm.setTitle(edit.getTitle());
                    actionForm.setContentMimeTypeChoice(edit.getContentMimeTypeName());
                    actionForm.setContent(edit.getContent());
                    actionForm.saveDtParameters(request);
                }

                request.setAttribute(AttributeConstants.ENTITY_LOCK, result.getEntityLock());
            }

            setCommandResultAttribute(request, commandResult);

            forwardKey = ForwardConstants.FORM;
        }

        return forwardKey;
    }

    @Override
    public ActionForward executeAction(ActionMapping mapping, EditActionForm actionForm, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String forwardKey = null;
        String forumName = request.getParameter(ParameterConstants.FORUM_NAME);
        String forumMessageName = request.getParameter(ParameterConstants.FORUM_MESSAGE_NAME);
        ForumMessageTransfer forumMessage = null;

        if(forumName == null) {
            forumName = actionForm.getForumName();
        }

        if(forumMessageName == null) {
            forumMessageName = actionForm.getForumMessageName();
        }

        if(forumName != null && forumMessageName != null) {
            GetForumMessageForm commandForm = ForumUtil.getHome().getGetForumMessageForm();

            commandForm.setForumMessageName(forumMessageName);

            CommandResult commandResult = ForumUtil.getHome().getForumMessage(getUserVisitPK(request), commandForm);
            ExecutionResult executionResult = commandResult.getExecutionResult();
            GetForumMessageResult result = (GetForumMessageResult)executionResult.getResult();

            forumMessage = result.getForumMessage();

            if(forumMessage != null) {
                String forumMessageTypeName = forumMessage.getForumMessageType().getForumMessageTypeName();

                if(forumMessageTypeName.equals(ForumConstants.ForumMessageType_BLOG_ENTRY)) {
                    forwardKey = editBlogEntry(mapping, actionForm, request, forumName, forumMessageName);
                } else if(forumMessageTypeName.equals(ForumConstants.ForumMessageType_BLOG_COMMENT)) {
                    forwardKey = editBlogComment(mapping, actionForm, request, forumName, forumMessageName);
                }
            }
        }

        if(forwardKey == null) {
            forwardKey = ForwardConstants.ERROR_404;
        }

        CustomActionForward customActionForward = new CustomActionForward(mapping.findForward(forwardKey));
        if(forwardKey.equals(ForwardConstants.DISPLAY)) {
            Map<String, String> parameters = new HashMap<>();

            parameters.put(ParameterConstants.FORUM_NAME, forumName);
            setupDtParameters(parameters, actionForm);
            customActionForward.setParameters(parameters);
        } else if(forwardKey.equals(ForwardConstants.FORM) && forumMessage != null) {
            request.setAttribute(AttributeConstants.FORUM_NAME, forumName);
            request.setAttribute(AttributeConstants.FORUM_MESSAGE, forumMessage);
        }

        return customActionForward;
    }
}