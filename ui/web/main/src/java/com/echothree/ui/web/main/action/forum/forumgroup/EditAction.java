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

package com.echothree.ui.web.main.action.forum.forumgroup;

import com.echothree.control.user.forum.common.ForumUtil;
import com.echothree.control.user.forum.common.edit.ForumGroupEdit;
import com.echothree.control.user.forum.common.form.EditForumGroupForm;
import com.echothree.control.user.forum.common.result.EditForumGroupResult;
import com.echothree.control.user.forum.common.spec.ForumGroupSpec;
import com.echothree.ui.web.main.framework.MainBaseEditAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.util.common.command.CommandResult;
import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;

@SproutAction(
    path = "/Forum/ForumGroup/Edit",
    mappingClass = SecureActionMapping.class,
    name = "ForumGroupEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Forum/ForumGroup/Main", redirect = true),
        @SproutForward(name = "Form", path = "/forum/forumgroup/edit.jsp")
    }
)
public class EditAction
        extends MainBaseEditAction<EditActionForm, ForumGroupSpec, ForumGroupEdit, EditForumGroupForm, EditForumGroupResult> {
    
    @Override
    protected ForumGroupSpec getSpec(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        var spec = ForumUtil.getHome().getForumGroupSpec();
        var originalForumGroupName = request.getParameter(ParameterConstants.ORIGINAL_FORUM_GROUP_NAME);

        if(originalForumGroupName == null) {
            originalForumGroupName = actionForm.getOriginalForumGroupName();
        }

        spec.setForumGroupName(originalForumGroupName);
        
        return spec;
    }
    
    @Override
    protected ForumGroupEdit getEdit(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        var edit = ForumUtil.getHome().getForumGroupEdit();

        edit.setForumGroupName(actionForm.getForumGroupName());
        edit.setIconName(actionForm.getIconChoice());
        edit.setSortOrder(actionForm.getSortOrder());
        edit.setDescription(actionForm.getDescription());

        return edit;
    }
    
    @Override
    protected EditForumGroupForm getForm()
            throws NamingException {
        return ForumUtil.getHome().getEditForumGroupForm();
    }
    
    @Override
    protected void setupActionForm(HttpServletRequest request, EditActionForm actionForm, EditForumGroupResult result, ForumGroupSpec spec, ForumGroupEdit edit) {
        actionForm.setOriginalForumGroupName(spec.getForumGroupName());
        actionForm.setForumGroupName(edit.getForumGroupName());
        actionForm.setIconChoice(edit.getIconName());
        actionForm.setSortOrder(edit.getSortOrder());
        actionForm.setDescription(edit.getDescription());
    }
    
    @Override
    protected CommandResult doEdit(HttpServletRequest request, EditForumGroupForm commandForm)
            throws Exception {
        return ForumUtil.getHome().editForumGroup(getUserVisitPK(request), commandForm);
    }
    
}