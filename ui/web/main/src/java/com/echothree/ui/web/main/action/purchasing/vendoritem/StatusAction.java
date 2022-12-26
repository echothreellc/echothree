// --------------------------------------------------------------------------------
// Copyright 2002-2023 Echo Three, LLC
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

package com.echothree.ui.web.main.action.purchasing.vendoritem;

import com.echothree.control.user.vendor.common.VendorUtil;
import com.echothree.control.user.vendor.common.form.GetVendorItemForm;
import com.echothree.control.user.vendor.common.form.SetVendorItemStatusForm;
import com.echothree.control.user.vendor.common.result.GetVendorItemResult;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.ForwardConstants;
import com.echothree.ui.web.main.framework.MainBaseAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.ExecutionResult;
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
    path = "/Purchasing/VendorItem/Status",
    mappingClass = SecureActionMapping.class,
    name = "VendorItemStatus",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Purchasing/VendorItem/Main", redirect = true),
        @SproutForward(name = "Form", path = "/purchasing/vendoritem/status.jsp")
    }
)
public class StatusAction
        extends MainBaseAction<ActionForm> {
    
    public void setupVendorItem(HttpServletRequest request, String vendorName, String vendorItemName)
            throws NamingException {
        GetVendorItemForm commandForm = VendorUtil.getHome().getGetVendorItemForm();

        commandForm.setVendorName(vendorName);
        commandForm.setVendorItemName(vendorItemName);

        CommandResult commandResult = VendorUtil.getHome().getVendorItem(getUserVisitPK(request), commandForm);
        ExecutionResult executionResult = commandResult.getExecutionResult();
        GetVendorItemResult result = (GetVendorItemResult)executionResult.getResult();

        request.setAttribute(AttributeConstants.VENDOR_ITEM, result.getVendorItem());
    }

    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String forwardKey;
        StatusActionForm actionForm = (StatusActionForm)form;
        String returnUrl = request.getParameter(ParameterConstants.RETURN_URL);
        String vendorName = request.getParameter(ParameterConstants.VENDOR_NAME);
        String vendorItemName = request.getParameter(ParameterConstants.VENDOR_ITEM_NAME);

        if(returnUrl == null) {
            returnUrl = actionForm.getReturnUrl();
        }
        if(vendorName == null) {
            vendorName = actionForm.getVendorName();
        }
        if(vendorItemName == null) {
            vendorItemName = actionForm.getVendorItemName();
        }

        if(wasPost(request)) {
            CommandResult commandResult = null;

            if(!wasCanceled(request)) {
                SetVendorItemStatusForm commandForm = VendorUtil.getHome().getSetVendorItemStatusForm();

                commandForm.setVendorName(vendorName);
                commandForm.setVendorItemName(vendorItemName);
                commandForm.setVendorItemStatusChoice(actionForm.getVendorItemStatusChoice());

                commandResult = VendorUtil.getHome().setVendorItemStatus(getUserVisitPK(request), commandForm);
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
            actionForm.setVendorItemName(vendorItemName);
            forwardKey = ForwardConstants.FORM;
        }

        if(forwardKey.equals(ForwardConstants.FORM)) {
            setupVendorItem(request, vendorName, vendorItemName);
        }

        return forwardKey.equals(ForwardConstants.DISPLAY)? new ActionForward(returnUrl, true): mapping.findForward(forwardKey);
    }
    
}