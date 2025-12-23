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

package com.echothree.ui.web.main.action.forum.forum;

import com.echothree.control.user.forum.common.ForumUtil;
import com.echothree.control.user.forum.common.edit.ForumEdit;
import com.echothree.control.user.forum.common.form.EditForumForm;
import com.echothree.control.user.forum.common.result.EditForumResult;
import com.echothree.control.user.forum.common.spec.ForumSpec;
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
    path = "/Forum/Forum/Edit",
    mappingClass = SecureActionMapping.class,
    name = "ForumEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Forum/Forum/Main", redirect = true),
        @SproutForward(name = "Form", path = "/forum/forum/edit.jsp")
    }
)
public class EditAction
        extends MainBaseEditAction<EditActionForm, ForumSpec, ForumEdit, EditForumForm, EditForumResult> {

    @Override
    protected ForumSpec getSpec(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        var spec = ForumUtil.getHome().getForumSpec();
        var originalForumName = request.getParameter(ParameterConstants.ORIGINAL_FORUM_NAME);

        if(originalForumName == null) {
            originalForumName = actionForm.getOriginalForumName();
        }

        spec.setForumName(originalForumName);
        
        return spec;
    }
    
    @Override
    protected ForumEdit getEdit(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        var edit = ForumUtil.getHome().getForumEdit();

        edit.setForumName(actionForm.getForumName());
        edit.setIconName(actionForm.getIconChoice());
        edit.setForumThreadSequenceName(actionForm.getForumThreadSequenceChoice());
        edit.setForumMessageSequenceName(actionForm.getForumMessageSequenceChoice());
        edit.setSortOrder(actionForm.getSortOrder());
        edit.setDescription(actionForm.getDescription());

        return edit;
    }
    
    @Override
    protected EditForumForm getForm()
            throws NamingException {
        return ForumUtil.getHome().getEditForumForm();
    }
    
    @Override
    protected void setupActionForm(HttpServletRequest request, EditActionForm actionForm, EditForumResult result, ForumSpec spec, ForumEdit edit) {
        actionForm.setOriginalForumName(spec.getForumName());
        actionForm.setForumName(edit.getForumName());
        actionForm.setIconChoice(edit.getIconName());
        actionForm.setForumThreadSequenceChoice(edit.getForumThreadSequenceName());
        actionForm.setForumMessageSequenceChoice(edit.getForumMessageSequenceName());
        actionForm.setSortOrder(edit.getSortOrder());
        actionForm.setDescription(edit.getDescription());
    }
    
    @Override
    protected CommandResult doEdit(HttpServletRequest request, EditForumForm commandForm)
            throws Exception {
        return ForumUtil.getHome().editForum(getUserVisitPK(request), commandForm);
    }
    
}