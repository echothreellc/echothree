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

package com.echothree.ui.web.main.action.uom.unitofmeasurekinduse;

import com.echothree.control.user.uom.common.UomUtil;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.ForwardConstants;
import com.echothree.ui.web.main.framework.MainBaseAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.view.client.web.struts.CustomActionForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

@SproutAction(
    path = "/UnitOfMeasure/UnitOfMeasureKindUse/Add",
    mappingClass = SecureActionMapping.class,
    name = "UnitOfMeasureKindUseAdd",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/UnitOfMeasure/UnitOfMeasureKindUse/Main", redirect = true),
        @SproutForward(name = "Form", path = "/uom/unitofmeasurekinduse/add.jsp")
    }
)
public class AddAction
        extends MainBaseAction<AddActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, AddActionForm actionForm, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String forwardKey;
        var unitOfMeasureKindName = request.getParameter(ParameterConstants.UNIT_OF_MEASURE_KIND_NAME);
        var unitOfMeasureKindUseTypeName = request.getParameter(ParameterConstants.UNIT_OF_MEASURE_KIND_USE_TYPE_NAME);
        var forwardParameter = request.getParameter(ParameterConstants.FORWARD_PARAMETER);
        
        if(wasPost(request)) {
            var commandForm = UomUtil.getHome().getCreateUnitOfMeasureKindUseForm();
            
            if(unitOfMeasureKindName == null)
                unitOfMeasureKindName = actionForm.getUnitOfMeasureKindName();
            if(unitOfMeasureKindUseTypeName == null)
                unitOfMeasureKindUseTypeName = actionForm.getUnitOfMeasureKindUseTypeName();
            if(forwardParameter == null)
                forwardParameter = actionForm.getForwardParameter();
            
            if(forwardParameter == null || forwardParameter.equals("UnitOfMeasureKindUseTypeName")) {
                commandForm.setUnitOfMeasureKindName(actionForm.getUnitOfMeasureKindChoice());
                commandForm.setUnitOfMeasureKindUseTypeName(unitOfMeasureKindUseTypeName);
            } else {
                commandForm.setUnitOfMeasureKindName(unitOfMeasureKindName);
                commandForm.setUnitOfMeasureKindUseTypeName(actionForm.getUnitOfMeasureKindUseTypeChoice());
            }
            commandForm.setIsDefault(actionForm.getIsDefault().toString());
            commandForm.setSortOrder(actionForm.getSortOrder());

            var commandResult = UomUtil.getHome().createUnitOfMeasureKindUse(getUserVisitPK(request), commandForm);
            
            if(commandResult.hasErrors()) {
                setCommandResultAttribute(request, commandResult);
                forwardKey = ForwardConstants.FORM;
            } else {
                forwardKey = ForwardConstants.DISPLAY;
            }
        } else {
            actionForm.setUnitOfMeasureKindName(unitOfMeasureKindName);
            actionForm.setUnitOfMeasureKindUseTypeName(unitOfMeasureKindUseTypeName);
            actionForm.setSortOrder("1");
            actionForm.setForwardParameter(forwardParameter);
            forwardKey = ForwardConstants.FORM;
        }

        var customActionForward = new CustomActionForward(mapping.findForward(forwardKey));
        if(forwardKey.equals(ForwardConstants.FORM)) {
            request.setAttribute(AttributeConstants.UNIT_OF_MEASURE_KIND_NAME, unitOfMeasureKindName);
            request.setAttribute(AttributeConstants.UNIT_OF_MEASURE_KIND_USE_TYPE_NAME, unitOfMeasureKindUseTypeName);
            request.setAttribute(AttributeConstants.FORWARD_PARAMETER, forwardParameter);
        } else if(forwardKey.equals(ForwardConstants.DISPLAY)) {
            Map<String, String> parameters = new HashMap<>(2);
            
            if(forwardParameter == null || forwardParameter.equals("UnitOfMeasureKindUseTypeName")) {
                parameters.put(ParameterConstants.UNIT_OF_MEASURE_KIND_USE_TYPE_NAME, unitOfMeasureKindUseTypeName);
            } else {
                parameters.put(ParameterConstants.UNIT_OF_MEASURE_KIND_NAME, unitOfMeasureKindName);
            }
            parameters.put(ParameterConstants.FORWARD_PARAMETER, forwardParameter);
            customActionForward.setParameters(parameters);
        }
        
        return customActionForward;
    }
    
}