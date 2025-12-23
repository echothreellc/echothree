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
import com.echothree.control.user.party.common.result.CreateVendorResult;
import com.echothree.ui.web.main.framework.ForwardConstants;
import com.echothree.ui.web.main.framework.MainBaseAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.view.client.web.struts.CustomActionForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

@SproutAction(
    path = "/Purchasing/Vendor/Add",
    mappingClass = SecureActionMapping.class,
    name = "VendorAdd",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Review", path = "/action/Purchasing/Vendor/Review", redirect = true),
        @SproutForward(name = "Form", path = "/purchasing/vendor/add.jsp")
    }
)
public class AddAction
        extends MainBaseAction<AddActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, AddActionForm actionForm, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String forwardKey;
        String vendorName = null;
        
        if(wasPost(request)) {
            var commandForm = PartyUtil.getHome().getCreateVendorForm();
            
            commandForm.setVendorName(actionForm.getVendorName());
            commandForm.setVendorTypeName(actionForm.getVendorTypeChoice());
            commandForm.setCancellationPolicyName(actionForm.getCancellationPolicyChoice());
            commandForm.setReturnPolicyName(actionForm.getReturnPolicyChoice());
            commandForm.setApGlAccountName(actionForm.getApGlAccountChoice());
            commandForm.setMinimumPurchaseOrderLines(actionForm.getMinimumPurchaseOrderLines());
            commandForm.setMaximumPurchaseOrderLines(actionForm.getMaximumPurchaseOrderLines());
            commandForm.setMinimumPurchaseOrderAmount(actionForm.getMinimumPurchaseOrderAmount());
            commandForm.setMaximumPurchaseOrderAmount(actionForm.getMaximumPurchaseOrderAmount());
            commandForm.setUseItemPurchasingCategories(actionForm.getUseItemPurchasingCategories().toString());
            commandForm.setDefaultItemAliasTypeName(actionForm.getDefaultItemAliasTypeChoice());
            commandForm.setPersonalTitleId(actionForm.getPersonalTitleChoice());
            commandForm.setFirstName(actionForm.getFirstName());
            commandForm.setMiddleName(actionForm.getMiddleName());
            commandForm.setLastName(actionForm.getLastName());
            commandForm.setNameSuffixId(actionForm.getNameSuffixChoice());
            commandForm.setName(actionForm.getName());
            commandForm.setPreferredLanguageIsoName(actionForm.getLanguageChoice());
            commandForm.setPreferredCurrencyIsoName(actionForm.getCurrencyChoice());
            commandForm.setPreferredJavaTimeZoneName(actionForm.getTimeZoneChoice());
            commandForm.setPreferredDateTimeFormatName(actionForm.getDateTimeFormatChoice());
            commandForm.setEmailAddress(actionForm.getEmailAddress());
            commandForm.setAllowSolicitation(actionForm.getAllowSolicitation().toString());

            var commandResult = PartyUtil.getHome().createVendor(getUserVisitPK(request), commandForm);
            
            if(commandResult.hasErrors()) {
                setCommandResultAttribute(request, commandResult);
                forwardKey = ForwardConstants.FORM;
            } else {
                var executionResult = commandResult.getExecutionResult();
                var result = (CreateVendorResult)executionResult.getResult();

                forwardKey = ForwardConstants.REVIEW;
                vendorName = result.getVendorName();
            }
        } else {
            forwardKey = ForwardConstants.FORM;
        }
        
        
        var customActionForward = new CustomActionForward(mapping.findForward(forwardKey));
        if(forwardKey.equals(ForwardConstants.REVIEW)) {
            var parameters = new HashMap<String, String>(1);
            
            parameters.put(ParameterConstants.VENDOR_NAME, vendorName);
            customActionForward.setParameters(parameters);
        }
        
        return customActionForward;
    }
    
}
