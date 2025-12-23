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

package com.echothree.ui.web.main.action.core.baseencryptionkey;

import com.echothree.control.user.core.common.CoreUtil;
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
    path = "/Core/BaseEncryptionKey/Status",
    mappingClass = SecureActionMapping.class,
    name = "BaseEncryptionKeyStatus",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Core/BaseEncryptionKey/Main", redirect = true),
        @SproutForward(name = "Form", path = "/core/baseencryptionkey/status.jsp")
    }
)
public class StatusAction
        extends MainBaseAction<StatusActionForm> {

    @Override
    public ActionForward executeAction(ActionMapping mapping, StatusActionForm actionForm, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String forwardKey;
        var baseEncryptionKeyName = request.getParameter(ParameterConstants.BASE_ENCRYPTION_KEY_NAME);
        var commandForm = CoreUtil.getHome().getSetBaseEncryptionKeyStatusForm();

        if(baseEncryptionKeyName == null) {
            baseEncryptionKeyName = actionForm.getBaseEncryptionKeyName();
        }
        
        if(wasPost(request)) {
            commandForm.setBaseEncryptionKeyName(baseEncryptionKeyName);
            commandForm.setBaseEncryptionKeyStatusChoice(actionForm.getBaseEncryptionKeyStatusChoice());

            var commandResult = CoreUtil.getHome().setBaseEncryptionKeyStatus(getUserVisitPK(request), commandForm);

            if(commandResult.hasErrors()) {
                setCommandResultAttribute(request, commandResult);
                forwardKey = ForwardConstants.FORM;
            } else {
                forwardKey = ForwardConstants.DISPLAY;
            }
        } else {
            actionForm.setBaseEncryptionKeyName(baseEncryptionKeyName);
            forwardKey = ForwardConstants.FORM;
        }

        var customActionForward = new CustomActionForward(mapping.findForward(forwardKey));
        if(forwardKey.equals(ForwardConstants.FORM)) {
            request.setAttribute(AttributeConstants.BASE_ENCRYPTION_KEY_NAME, baseEncryptionKeyName);
        }

        return customActionForward;
    }
}
