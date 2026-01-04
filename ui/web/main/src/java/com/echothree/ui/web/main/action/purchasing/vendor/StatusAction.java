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

package com.echothree.ui.web.main.action.purchasing.vendor;

import com.echothree.control.user.party.common.PartyUtil;
import com.echothree.control.user.vendor.common.VendorUtil;
import com.echothree.control.user.vendor.common.result.GetVendorResult;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.ForwardConstants;
import com.echothree.ui.web.main.framework.MainBaseAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.util.common.command.CommandResult;
import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

@SproutAction(
    path = "/Purchasing/Vendor/Status",
    mappingClass = SecureActionMapping.class,
    name = "VendorStatus",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Purchasing/Vendor/Main", redirect = true),
        @SproutForward(name = "Form", path = "/purchasing/vendor/status.jsp")
    }
)
public class StatusAction
        extends MainBaseAction<ActionForm> {
    
    public void setupVendor(HttpServletRequest request, String vendorName)
            throws NamingException {
        var commandForm = VendorUtil.getHome().getGetVendorForm();

        commandForm.setVendorName(vendorName);

        var commandResult = VendorUtil.getHome().getVendor(getUserVisitPK(request), commandForm);
        var executionResult = commandResult.getExecutionResult();
        var result = (GetVendorResult)executionResult.getResult();

        request.setAttribute(AttributeConstants.VENDOR, result.getVendor());
    }

    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String forwardKey;
        var actionForm = (StatusActionForm)form;
        var returnUrl = request.getParameter(ParameterConstants.RETURN_URL);
        var vendorName = request.getParameter(ParameterConstants.VENDOR_NAME);

        if(returnUrl == null) {
            returnUrl = actionForm.getReturnUrl();
        }
        if(vendorName == null) {
            vendorName = actionForm.getVendorName();
        }

        if(wasPost(request)) {
            CommandResult commandResult = null;

            if(!wasCanceled(request)) {
                var commandForm = PartyUtil.getHome().getSetVendorStatusForm();

                commandForm.setVendorName(vendorName);
                commandForm.setVendorStatusChoice(actionForm.getVendorStatusChoice());

                commandResult = PartyUtil.getHome().setVendorStatus(getUserVisitPK(request), commandForm);
            }

            if(commandResult != null && commandResult.hasErrors()) {
                setCommandResultAttribute(request, commandResult);
                forwardKey = ForwardConstants.FORM;
            } else {
                forwardKey = ForwardConstants.DISPLAY;
            }
        } else {
            actionForm.setReturnUrl(returnUrl);
            actionForm.setVendorName(vendorName);
            forwardKey = ForwardConstants.FORM;
        }

        if(forwardKey.equals(ForwardConstants.FORM)) {
            setupVendor(request, vendorName);
        }

        return forwardKey.equals(ForwardConstants.DISPLAY)? new ActionForward(returnUrl, true): mapping.findForward(forwardKey);
    }
    
}