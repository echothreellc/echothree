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

import com.echothree.control.user.search.common.SearchUtil;
import com.echothree.control.user.search.common.result.GetVendorResultsResult;
import com.echothree.control.user.search.common.result.SearchVendorsResult;
import com.echothree.model.control.search.common.SearchTypes;
import com.echothree.model.control.vendor.common.transfer.VendorResultTransfer;
import com.echothree.ui.web.main.framework.ForwardConstants;
import com.echothree.ui.web.main.framework.MainBaseAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.view.client.web.struts.CustomActionForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

@SproutAction(
    path = "/Purchasing/Vendor/Main",
    mappingClass = SecureActionMapping.class,
    name = "VendorMain",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Purchasing/Vendor/Result", redirect = true),
        @SproutForward(name = "Review", path = "/action/Purchasing/Vendor/Review", redirect = true),
        @SproutForward(name = "Form", path = "/purchasing/vendor/main.jsp")
    }
)
public class MainAction
        extends MainBaseAction<MainActionForm> {
    
    private String getPartyName(HttpServletRequest request)
            throws NamingException {
        var commandForm = SearchUtil.getHome().getGetVendorResultsForm();
        String partyName = null;
        
        commandForm.setSearchTypeName(SearchTypes.VENDOR_REVIEW.name());

        var commandResult = SearchUtil.getHome().getVendorResults(getUserVisitPK(request), commandForm);
        var executionResult = commandResult.getExecutionResult();
        var result = (GetVendorResultsResult)executionResult.getResult();
        Collection vendorResults = result.getVendorResults();
        var iter = vendorResults.iterator();
        if(iter.hasNext()) {
            partyName = ((VendorResultTransfer)iter.next()).getPartyName();
        }
        
        return partyName;
    }
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, MainActionForm actionForm, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String forwardKey;
        String partyName = null;

        if(wasPost(request)) {
            var commandForm = SearchUtil.getHome().getSearchVendorsForm();

            commandForm.setSearchTypeName(SearchTypes.VENDOR_REVIEW.name());
            commandForm.setFirstName(actionForm.getFirstName());
            commandForm.setFirstNameSoundex(actionForm.getFirstNameSoundex().toString());
            commandForm.setMiddleName(actionForm.getMiddleName());
            commandForm.setMiddleNameSoundex(actionForm.getMiddleNameSoundex().toString());
            commandForm.setLastName(actionForm.getLastName());
            commandForm.setLastNameSoundex(actionForm.getLastNameSoundex().toString());
            commandForm.setName(actionForm.getName());
            commandForm.setVendorName(actionForm.getVendorName());
            commandForm.setCreatedSince(actionForm.getCreatedSince());
            commandForm.setModifiedSince(actionForm.getModifiedSince());

            var commandResult = SearchUtil.getHome().searchVendors(getUserVisitPK(request), commandForm);

            if(commandResult.hasErrors()) {
                setCommandResultAttribute(request, commandResult);
                forwardKey = ForwardConstants.FORM;
            } else {
                var executionResult = commandResult.getExecutionResult();
                var result = (SearchVendorsResult)executionResult.getResult();
                var count = result.getCount();

                if(count == 0 || count > 1) {
                    forwardKey = ForwardConstants.DISPLAY;
                } else {
                    partyName = getPartyName(request);
                    forwardKey = ForwardConstants.REVIEW;
                }
            }
        } else {
            var firstName = request.getParameter(ParameterConstants.FIRST_NAME);
            var lastName = request.getParameter(ParameterConstants.LAST_NAME);

            actionForm.setFirstName(firstName);
            actionForm.setLastName(lastName);
            forwardKey = ForwardConstants.FORM;
        }

        var customActionForward = new CustomActionForward(mapping.findForward(forwardKey));
        if(forwardKey.equals(ForwardConstants.REVIEW)) {
            Map<String, String> parameters = new HashMap<>(1);
            
            parameters.put(ParameterConstants.PARTY_NAME, partyName);
            customActionForward.setParameters(parameters);
        }
        
        return customActionForward;
    }
    
}