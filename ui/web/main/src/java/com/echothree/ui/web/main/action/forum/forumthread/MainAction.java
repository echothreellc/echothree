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

package com.echothree.ui.web.main.action.forum.forumthread;

import com.echothree.control.user.forum.common.ForumUtil;
import com.echothree.control.user.forum.common.result.GetForumThreadsResult;
import com.echothree.model.control.forum.common.ForumOptions;
import com.echothree.model.data.forum.common.ForumThreadConstants;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.ForwardConstants;
import com.echothree.ui.web.main.framework.MainBaseAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.util.common.transfer.Limit;
import com.echothree.util.common.transfer.ListWrapper;
import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import static java.lang.Math.toIntExact;
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
import org.displaytag.tags.TableTagParameters;
import org.displaytag.util.ParamEncoder;

@SproutAction(
    path = "/Forum/ForumThread/Main",
    mappingClass = SecureActionMapping.class,
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/forum/forumthread/main.jsp")
    }
)
public class MainAction
        extends MainBaseAction<ActionForm> {

    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws NamingException {
        String forwardKey;
        var commandForm = ForumUtil.getHome().getGetForumThreadsForm();
        var forumName = request.getParameter(ParameterConstants.FORUM_NAME);

        commandForm.setForumName(forumName);
        commandForm.setIncludeFutureForumThreads(String.valueOf(true));

        Set<String> options = new HashSet<>();
        options.add(ForumOptions.ForumThreadIncludeForumMessages);
        options.add(ForumOptions.ForumMessageIncludeForumMessageRoles);
        options.add(ForumOptions.ForumMessageIncludeForumMessageParts);
        options.add(ForumOptions.ForumMessagePartIncludeString);
        commandForm.setOptions(options);

        var offsetParameter = request.getParameter(new ParamEncoder("forumThread").encodeParameterName(TableTagParameters.PARAMETER_PAGE));
        var offset = offsetParameter == null ? null : (Integer.parseInt(offsetParameter) - 1) * 5;

        Map<String, Limit> limits = new HashMap<>();
        limits.put(ForumThreadConstants.ENTITY_TYPE_NAME, new Limit("5", offset == null ? null : offset.toString()));
        commandForm.setLimits(limits);

        var commandResult = ForumUtil.getHome().getForumThreads(getUserVisitPK(request), commandForm);
        var executionResult = commandResult.getExecutionResult();
        var result = (GetForumThreadsResult)executionResult.getResult();
        var forum = result.getForum();

        if(forum == null) {
            forwardKey = ForwardConstants.ERROR_404;
        } else {
            request.setAttribute(AttributeConstants.FORUM, forum);
            request.setAttribute(AttributeConstants.FORUM_THREAD_COUNT, toIntExact(result.getForumThreadCount()));
            request.setAttribute(AttributeConstants.FORUM_THREADS, new ListWrapper<>(result.getForumThreads()));
            setupDtAttributes(request, "forumThread");
            forwardKey = ForwardConstants.DISPLAY;
        }

        return mapping.findForward(forwardKey);
    }

}
