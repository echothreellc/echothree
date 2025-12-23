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

package com.echothree.ui.web.main.action.core.entitytag;

import com.echothree.control.user.tag.common.TagUtil;
import com.echothree.control.user.tag.common.result.GetEntityTagResult;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.ForwardConstants;
import com.echothree.ui.web.main.framework.MainBaseDeleteAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.util.common.command.CommandResult;
import com.echothree.view.client.web.struts.CustomActionForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

@SproutAction(
    path = "/Core/EntityTag/Delete",
    mappingClass = SecureActionMapping.class,
    name = "EntityTagDelete",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Form", path = "/core/entitytag/delete.jsp")
    }
)
public class DeleteAction
        extends MainBaseDeleteAction<DeleteActionForm> {

    @Override
    public String getEntityTypeName() {
        return EntityTypes.EntityTag.name();
    }

    @Override
    public void setupParameters(DeleteActionForm actionForm, HttpServletRequest request) {
        actionForm.setTagName(findParameter(request, ParameterConstants.TAG_NAME, actionForm.getTagName()));
        actionForm.setReturnUrl(findParameter(request, ParameterConstants.RETURN_URL, actionForm.getReturnUrl()));
        actionForm.setEntityRef(findParameter(request, ParameterConstants.ENTITY_REF, actionForm.getEntityRef()));
        actionForm.setTagScopeName(findParameter(request, ParameterConstants.TAG_SCOPE_NAME, actionForm.getTagScopeName()));
    }

    @Override
    public void setupTransfer(DeleteActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        var commandForm = TagUtil.getHome().getGetEntityTagForm();

        commandForm.setTagScopeName(actionForm.getTagScopeName());
        commandForm.setEntityRef(actionForm.getEntityRef());
        commandForm.setTagName(actionForm.getTagName());

        var commandResult = TagUtil.getHome().getEntityTag(getUserVisitPK(request), commandForm);
        var executionResult = commandResult.getExecutionResult();
        var result = (GetEntityTagResult)executionResult.getResult();

        request.setAttribute(AttributeConstants.ENTITY_TAG, result.getEntityTag());
    }

    @Override
    public CommandResult doDelete(DeleteActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        var commandForm = TagUtil.getHome().getDeleteEntityTagForm();

        commandForm.setTagScopeName(actionForm.getTagScopeName());
        commandForm.setEntityRef(actionForm.getEntityRef());
        commandForm.setTagName(actionForm.getTagName());

        return TagUtil.getHome().deleteEntityTag(getUserVisitPK(request), commandForm);
    }

    @Override
    protected ActionForward getActionForward(ActionMapping mapping, String forwardKey, DeleteActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        ActionForward actionForward;

        if(forwardKey == null || forwardKey.equals(ForwardConstants.CANCEL)) {
            actionForward = new ActionForward(actionForm.getReturnUrl(), true);
        } else {
            // Sending them to 'Form'
            actionForward = new CustomActionForward(mapping.findForward(forwardKey == null ? getDisplayForward(actionForm, request) : forwardKey));
            setupTransfer(actionForm, request);
        }

        return actionForward;
    }

}
