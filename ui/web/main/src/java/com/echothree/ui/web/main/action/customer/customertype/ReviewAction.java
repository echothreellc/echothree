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

package com.echothree.ui.web.main.action.customer.customertype;

import com.echothree.control.user.customer.common.CustomerUtil;
import com.echothree.control.user.customer.common.result.GetCustomerTypeResult;
import com.echothree.control.user.offer.common.OfferUtil;
import com.echothree.control.user.offer.common.result.GetOfferCustomerTypesResult;
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
    path = "/Customer/CustomerType/Review",
    mappingClass = SecureActionMapping.class,
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/customer/customertype/review.jsp")
    }
)
public class ReviewAction
        extends MainBaseAction<ActionForm> {

    private void setCustomerType(HttpServletRequest request)
            throws NamingException {
        var commandForm = CustomerUtil.getHome().getGetCustomerTypeForm();
        var customerTypeName = request.getParameter(ParameterConstants.CUSTOMER_TYPE_NAME);

        commandForm.setCustomerTypeName(customerTypeName);

        var commandResult = CustomerUtil.getHome().getCustomerType(getUserVisitPK(request), commandForm);
        var executionResult = commandResult.getExecutionResult();
        var result = (GetCustomerTypeResult)executionResult.getResult();

        request.setAttribute(AttributeConstants.CUSTOMER_TYPE, result.getCustomerType());
    }

    private void setOfferCustomerTypes(HttpServletRequest request)
            throws NamingException {
        var commandForm = OfferUtil.getHome().getGetOfferCustomerTypesForm();
        var customerTypeName = request.getParameter(ParameterConstants.CUSTOMER_TYPE_NAME);

        commandForm.setCustomerTypeName(customerTypeName);

        var commandResult = OfferUtil.getHome().getOfferCustomerTypes(getUserVisitPK(request), commandForm);
        var executionResult = commandResult.getExecutionResult();
        var result = (GetOfferCustomerTypesResult)executionResult.getResult();
        var offerCustomerTypes = result.getOfferCustomerTypes();

        request.setAttribute(AttributeConstants.OFFER_CUSTOMER_TYPES, offerCustomerTypes.isEmpty()? null: offerCustomerTypes);
    }

    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String forwardKey;
        
        try {
            setCustomerType(request);
            setOfferCustomerTypes(request);

            forwardKey = ForwardConstants.DISPLAY;
        } catch (NamingException ne) {
            forwardKey = ForwardConstants.ERROR_500;
        }
        
        return mapping.findForward(forwardKey);
    }
    
}