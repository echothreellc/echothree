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
import com.echothree.control.user.forum.common.result.GetForumThreadResult;
import com.echothree.model.control.core.common.CoreOptions;
import com.echothree.model.control.forum.common.ForumOptions;
import com.echothree.model.control.forum.common.transfer.ForumThreadTransfer;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.ForwardConstants;
import com.echothree.ui.web.main.framework.MainBaseAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import java.util.HashSet;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

@SproutAction(
    path = "/Forum/ForumThread/Review",
    mappingClass = SecureActionMapping.class,
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/forum/forumthread/review.jsp")
    }
)
public class ReviewAction
        extends MainBaseAction<ActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String forwardKey;
        var commandForm = ForumUtil.getHome().getGetForumThreadForm();
        
        commandForm.setForumThreadName(request.getParameter(ParameterConstants.FORUM_THREAD_NAME));
        commandForm.setEntityRef(request.getParameter(ParameterConstants.ENTITY_REF));
        commandForm.setUuid(request.getParameter(ParameterConstants.UUID));
        
        Set<String> options = new HashSet<>();
        options.add(ForumOptions.ForumThreadIncludeEntityAttributeGroups);
        options.add(ForumOptions.ForumThreadIncludeTagScopes);
        options.add(CoreOptions.EntityAttributeGroupIncludeEntityAttributes);
        options.add(CoreOptions.EntityAttributeIncludeValue);
        options.add(CoreOptions.EntityStringAttributeIncludeString);
        options.add(CoreOptions.EntityInstanceIncludeNames);
        commandForm.setOptions(options);

        var commandResult = ForumUtil.getHome().getForumThread(getUserVisitPK(request), commandForm);
        ForumThreadTransfer forumThread = null;
        
        if(!commandResult.hasErrors()) {
            var executionResult = commandResult.getExecutionResult();
            var result = (GetForumThreadResult)executionResult.getResult();
            
            forumThread = result.getForumThread();
        }
        
        if(forumThread == null) {
            forwardKey = ForwardConstants.ERROR_404;
        } else {
            saveToken(request); // Required for ForumThreadIncludeTagScopes and tagScopes.jsp
            request.setAttribute(AttributeConstants.FORUM_THREAD, forumThread);
            forwardKey = ForwardConstants.DISPLAY;
        }
        
        return mapping.findForward(forwardKey);
    }
    
}
