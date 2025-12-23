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

package com.echothree.ui.web.main.action.uom.unitofmeasuretype;

import com.echothree.control.user.uom.common.UomUtil;
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
    path = "/UnitOfMeasure/UnitOfMeasureType/DescriptionAdd",
    mappingClass = SecureActionMapping.class,
    name = "UnitOfMeasureTypeDescriptionAdd",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/UnitOfMeasure/UnitOfMeasureType/Description", redirect = true),
        @SproutForward(name = "Form", path = "/uom/unitofmeasuretype/descriptionAdd.jsp")
    }
)
public class DescriptionAddAction
        extends MainBaseAction<ActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
        String forwardKey = null;
        var unitOfMeasureKindName = request.getParameter(ParameterConstants.UNIT_OF_MEASURE_KIND_NAME);
        var unitOfMeasureTypeName = request.getParameter(ParameterConstants.UNIT_OF_MEASURE_TYPE_NAME);
        
        try {
            if(forwardKey == null) {
                if(wasPost(request)) {
                    var descriptionAddActionForm = (DescriptionAddActionForm)form;

                    var commandForm = UomUtil.getHome().getCreateUnitOfMeasureTypeDescriptionForm();
                    
                    if(unitOfMeasureKindName == null)
                        unitOfMeasureKindName = descriptionAddActionForm.getUnitOfMeasureKindName();
                    if(unitOfMeasureTypeName == null)
                        unitOfMeasureTypeName = descriptionAddActionForm.getUnitOfMeasureTypeName();
                    
                    commandForm.setUnitOfMeasureKindName(unitOfMeasureKindName);
                    commandForm.setUnitOfMeasureTypeName(unitOfMeasureTypeName);
                    commandForm.setLanguageIsoName(descriptionAddActionForm.getLanguageChoice());
                    commandForm.setSingularDescription(descriptionAddActionForm.getSingularDescription());
                    commandForm.setPluralDescription(descriptionAddActionForm.getPluralDescription());
                    commandForm.setSymbol(descriptionAddActionForm.getSymbol());

                    var commandResult = UomUtil.getHome().createUnitOfMeasureTypeDescription(getUserVisitPK(request), commandForm);
                    
                    if(commandResult.hasErrors()) {
                        setCommandResultAttribute(request, commandResult);
                        forwardKey = ForwardConstants.FORM;
                    } else {
                        forwardKey = ForwardConstants.DISPLAY;
                    }
                } else
                    forwardKey = ForwardConstants.FORM;
            }
        } catch (NamingException ne) {
            forwardKey = ForwardConstants.ERROR_500;
        }

        var customActionForward = new CustomActionForward(mapping.findForward(forwardKey));
        if(forwardKey.equals(ForwardConstants.FORM) || forwardKey.equals(ForwardConstants.DISPLAY)) {
            Map<String, String> parameters = new HashMap<>(2);
            parameters.put(ParameterConstants.UNIT_OF_MEASURE_KIND_NAME, unitOfMeasureKindName);
            parameters.put(ParameterConstants.UNIT_OF_MEASURE_TYPE_NAME, unitOfMeasureTypeName);
            customActionForward.setParameters(parameters);
            
            request.setAttribute("unitOfMeasureKindName", unitOfMeasureKindName); // TODO: not encoded
            request.setAttribute("unitOfMeasureTypeName", unitOfMeasureTypeName); // TODO: not encoded
        }
        
        return customActionForward;
    }
    
}
