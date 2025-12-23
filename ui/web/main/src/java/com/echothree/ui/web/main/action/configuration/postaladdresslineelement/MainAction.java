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

package com.echothree.ui.web.main.action.configuration.postaladdresslineelement;

import com.echothree.control.user.contact.common.ContactUtil;
import com.echothree.control.user.contact.common.result.GetPostalAddressLineElementsResult;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.ForwardConstants;
import com.echothree.ui.web.main.framework.MainBaseAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
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
    path = "/Configuration/PostalAddressLineElement/Main",
    mappingClass = SecureActionMapping.class,
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/configuration/postaladdresslineelement/main.jsp")
    }
)
public class MainAction
        extends MainBaseAction<ActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
        String forwardKey;
        
        try {
            var postalAddressFormatName = request.getParameter(ParameterConstants.POSTAL_ADDRESS_FORMAT_NAME);
            var postalAddressLineSortOrder = request.getParameter(ParameterConstants.POSTAL_ADDRESS_LINE_SORT_ORDER);
            var commandForm = ContactUtil.getHome().getGetPostalAddressLineElementsForm();
            
            commandForm.setPostalAddressFormatName(postalAddressFormatName);
            commandForm.setPostalAddressLineSortOrder(postalAddressLineSortOrder);

            var commandResult = ContactUtil.getHome().getPostalAddressLineElements(getUserVisitPK(request), commandForm);
            var executionResult = commandResult.getExecutionResult();
            var result = (GetPostalAddressLineElementsResult)executionResult.getResult();
            var postalAddressFormatTransfer = result.getPostalAddressFormat();
            var postalAddressLineTransfer = result.getPostalAddressLine();
            
            request.setAttribute(AttributeConstants.POSTAL_ADDRESS_FORMAT, postalAddressFormatTransfer);
            request.setAttribute(AttributeConstants.POSTAL_ADDRESS_LINE, postalAddressLineTransfer);
            request.setAttribute(AttributeConstants.POSTAL_ADDRESS_LINE_ELEMENTS, result.getPostalAddressLineElements());
            forwardKey = ForwardConstants.DISPLAY;
        } catch (NamingException ne) {
            forwardKey = ForwardConstants.ERROR_500;
        }
        
        return mapping.findForward(forwardKey);
    }
    
}