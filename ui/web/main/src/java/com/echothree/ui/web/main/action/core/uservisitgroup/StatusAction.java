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

package com.echothree.ui.web.main.action.core.uservisitgroup;

import com.echothree.control.user.user.common.UserUtil;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.ForwardConstants;
import com.echothree.ui.web.main.framework.MainBaseAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.view.client.web.struts.CustomActionForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

@SproutAction(
    path = "/Core/UserVisitGroup/Status",
    mappingClass = SecureActionMapping.class,
    name = "UserVisitGroupStatus",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Core/UserVisitGroup/Main", redirect = true),
        @SproutForward(name = "Form", path = "/core/uservisitgroup/status.jsp")
    }
)
public class StatusAction
        extends MainBaseAction<StatusActionForm> {

    @Override
    public ActionForward executeAction(ActionMapping mapping, StatusActionForm actionForm, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String forwardKey;
        var userVisitGroupName = request.getParameter(ParameterConstants.USER_VISIT_GROUP_NAME);
        var commandForm = UserUtil.getHome().getSetUserVisitGroupStatusForm();

        if(userVisitGroupName == null) {
            userVisitGroupName = actionForm.getUserVisitGroupName();
        }
        if(wasPost(request)) {
            commandForm.setUserVisitGroupName(userVisitGroupName);
            commandForm.setUserVisitGroupStatusChoice(actionForm.getUserVisitGroupStatusChoice());

            var commandResult = UserUtil.getHome().setUserVisitGroupStatus(getUserVisitPK(request), commandForm);

            if(commandResult.hasErrors()) {
                setCommandResultAttribute(request, commandResult);
                forwardKey = ForwardConstants.FORM;
            } else {
                forwardKey = ForwardConstants.DISPLAY;
            }
        } else {
            actionForm.setUserVisitGroupName(userVisitGroupName);
            actionForm.setupUserVisitGroupStatusChoices();
            forwardKey = ForwardConstants.FORM;
        }

        var customActionForward = new CustomActionForward(mapping.findForward(forwardKey));
        if(forwardKey.equals(ForwardConstants.FORM)) {
            request.setAttribute(AttributeConstants.USER_VISIT_GROUP_NAME, userVisitGroupName);
        }

        return customActionForward;
    }
}