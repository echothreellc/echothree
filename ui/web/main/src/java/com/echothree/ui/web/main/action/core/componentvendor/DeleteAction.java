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

package com.echothree.ui.web.main.action.core.componentvendor;

import com.echothree.control.user.core.common.CoreUtil;
import com.echothree.control.user.core.common.result.GetComponentVendorResult;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.MainBaseDeleteAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.util.common.command.CommandResult;
import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;

@SproutAction(
    path = "/Core/ComponentVendor/Delete",
    mappingClass = SecureActionMapping.class,
    name = "ComponentVendorDelete",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Core/ComponentVendor/Main", redirect = true),
        @SproutForward(name = "Form", path = "/core/componentvendor/delete.jsp")
    }
)
public class DeleteAction
        extends MainBaseDeleteAction<DeleteActionForm> {

    @Override
    public String getEntityTypeName(final DeleteActionForm actionForm) {
        return EntityTypes.ComponentVendor.name();
    }

    @Override
    public void setupParameters(DeleteActionForm actionForm, HttpServletRequest request) {
        actionForm.setComponentVendorName(findParameter(request, ParameterConstants.COMPONENT_VENDOR_NAME, actionForm.getComponentVendorName()));
    }

    @Override
    public void setupTransfer(DeleteActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        var commandForm = CoreUtil.getHome().getGetComponentVendorForm();

        commandForm.setComponentVendorName(actionForm.getComponentVendorName());

        var commandResult = CoreUtil.getHome().getComponentVendor(getUserVisitPK(request), commandForm);
        var executionResult = commandResult.getExecutionResult();
        var result = (GetComponentVendorResult)executionResult.getResult();

        request.setAttribute(AttributeConstants.COMPONENT_VENDOR, result.getComponentVendor());
    }

    @Override
    public CommandResult doDelete(DeleteActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        var commandForm = CoreUtil.getHome().getDeleteComponentVendorForm();

        commandForm.setComponentVendorName(actionForm.getComponentVendorName());

        return CoreUtil.getHome().deleteComponentVendor(getUserVisitPK(request), commandForm);
    }

}
