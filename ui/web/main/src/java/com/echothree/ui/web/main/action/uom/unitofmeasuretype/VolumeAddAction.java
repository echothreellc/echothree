// --------------------------------------------------------------------------------
// Copyright 2002-2025 Echo Three, LLC
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

package com.echothree.ui.web.main.action.uom.unitofmeasuretype;

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
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

@SproutAction(
    path = "/UnitOfMeasure/UnitOfMeasureType/VolumeAdd",
    mappingClass = SecureActionMapping.class,
    name = "UnitOfMeasureTypeVolumeAdd",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/UnitOfMeasure/UnitOfMeasureType/Main", redirect = true),
        @SproutForward(name = "Form", path = "/uom/unitofmeasuretype/volumeAdd.jsp")
    }
)
public class VolumeAddAction
        extends MainBaseAction<ActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String forwardKey = null;
        var unitOfMeasureKindName = request.getParameter(ParameterConstants.UNIT_OF_MEASURE_KIND_NAME);
        var unitOfMeasureTypeName = request.getParameter(ParameterConstants.UNIT_OF_MEASURE_TYPE_NAME);
        
        try {
            if(forwardKey == null) {
                var actionForm = (VolumeAddActionForm)form;
                
                if(wasPost(request)) {
                    var commandForm = UomUtil.getHome().getCreateUnitOfMeasureTypeVolumeForm();
                    
                    if(unitOfMeasureKindName == null)
                        unitOfMeasureKindName = actionForm.getUnitOfMeasureKindName();
                    if(unitOfMeasureTypeName == null)
                        unitOfMeasureTypeName = actionForm.getUnitOfMeasureTypeName();
                    
                    commandForm.setUnitOfMeasureKindName(unitOfMeasureKindName);
                    commandForm.setUnitOfMeasureTypeName(actionForm.getUnitOfMeasureTypeName());
                    commandForm.setHeight(actionForm.getHeight());
                    commandForm.setHeightUnitOfMeasureTypeName(actionForm.getHeightUnitOfMeasureTypeChoice());
                    commandForm.setWidth(actionForm.getWidth());
                    commandForm.setWidthUnitOfMeasureTypeName(actionForm.getWidthUnitOfMeasureTypeChoice());
                    commandForm.setDepth(actionForm.getDepth());
                    commandForm.setDepthUnitOfMeasureTypeName(actionForm.getDepthUnitOfMeasureTypeChoice());

                    var commandResult = UomUtil.getHome().createUnitOfMeasureTypeVolume(getUserVisitPK(request), commandForm);
                    
                    if(commandResult.hasErrors()) {
                        setCommandResultAttribute(request, commandResult);
                        forwardKey = ForwardConstants.FORM;
                    } else {
                        forwardKey = ForwardConstants.DISPLAY;
                    }
                } else {
                    actionForm.setUnitOfMeasureKindName(unitOfMeasureKindName);
                    actionForm.setUnitOfMeasureTypeName(unitOfMeasureTypeName);
                    forwardKey = ForwardConstants.FORM;
                }
            }
        } catch (NamingException ne) {
            forwardKey = ForwardConstants.ERROR_500;
        }

        var customActionForward = new CustomActionForward(mapping.findForward(forwardKey));
        if(forwardKey.equals(ForwardConstants.FORM)) {
            request.setAttribute(AttributeConstants.UNIT_OF_MEASURE_KIND_NAME, unitOfMeasureKindName);
            request.setAttribute(AttributeConstants.UNIT_OF_MEASURE_TYPE_NAME, unitOfMeasureTypeName);
        } else if(forwardKey.equals(ForwardConstants.DISPLAY)) {
            Map<String, String> parameters = new HashMap<>(2);
            
            parameters.put(ParameterConstants.UNIT_OF_MEASURE_KIND_NAME, unitOfMeasureKindName);
            parameters.put(ParameterConstants.UNIT_OF_MEASURE_TYPE_NAME, unitOfMeasureTypeName);
            customActionForward.setParameters(parameters);
        }
        
        return customActionForward;
    }
    
}
