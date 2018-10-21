// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
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

package com.echothree.ui.web.main.action.shipping.shippingmethodcarrierservice;

import com.echothree.control.user.shipping.common.ShippingUtil;
import com.echothree.control.user.shipping.remote.form.GetShippingMethodCarrierServicesForm;
import com.echothree.control.user.shipping.remote.result.GetShippingMethodCarrierServicesResult;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.ForwardConstants;
import com.echothree.ui.web.main.framework.MainBaseAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.util.remote.command.CommandResult;
import com.echothree.util.remote.command.ExecutionResult;
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
    path = "/Shipping/ShippingMethodCarrierService/Main",
    mappingClass = SecureActionMapping.class,
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/shipping/shippingmethodcarrierservice/main.jsp")
    }
)
public class MainAction
        extends MainBaseAction<ActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
        String forwardKey = null;
        
        try {
            String shippingMethodName = request.getParameter(ParameterConstants.SHIPPING_METHOD_NAME);
            String carrierName = request.getParameter(ParameterConstants.CARRIER_NAME);
            String carrierServiceName = request.getParameter(ParameterConstants.CARRIER_SERVICE_NAME);
            GetShippingMethodCarrierServicesForm commandForm = ShippingUtil.getHome().getGetShippingMethodCarrierServicesForm();
            
            commandForm.setShippingMethodName(shippingMethodName);
            commandForm.setCarrierName(carrierName);
            commandForm.setCarrierServiceName(carrierServiceName);
            
            CommandResult commandResult = ShippingUtil.getHome().getShippingMethodCarrierServices(getUserVisitPK(request), commandForm);
            ExecutionResult executionResult = commandResult.getExecutionResult();
            GetShippingMethodCarrierServicesResult result = (GetShippingMethodCarrierServicesResult)executionResult.getResult();
            
            request.setAttribute(AttributeConstants.SHIPPING_METHOD, result.getShippingMethod());
            request.setAttribute(AttributeConstants.CARRIER_SERVICE, result.getCarrierService());
            request.setAttribute(AttributeConstants.SHIPPING_METHOD_CARRIER_SERVICES, result.getShippingMethodCarrierServices());
            forwardKey = ForwardConstants.DISPLAY;
        } catch (NamingException ne) {
            forwardKey = ForwardConstants.ERROR_500;
        }
        
        return mapping.findForward(forwardKey);
    }
    
}