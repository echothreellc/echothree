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
import com.echothree.control.user.uom.common.result.EditUnitOfMeasureTypeDescriptionResult;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.ForwardConstants;
import com.echothree.ui.web.main.framework.MainBaseAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.util.common.command.EditMode;
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
    path = "/UnitOfMeasure/UnitOfMeasureType/DescriptionEdit",
    mappingClass = SecureActionMapping.class,
    name = "UnitOfMeasureTypeDescriptionEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/UnitOfMeasure/UnitOfMeasureType/Description", redirect = true),
        @SproutForward(name = "Form", path = "/uom/unitofmeasuretype/descriptionEdit.jsp")
    }
)
public class DescriptionEditAction
        extends MainBaseAction<ActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
        String forwardKey = null;
        var unitOfMeasureKindName = request.getParameter(ParameterConstants.UNIT_OF_MEASURE_KIND_NAME);
        var unitOfMeasureTypeName = request.getParameter(ParameterConstants.UNIT_OF_MEASURE_TYPE_NAME);
        var languageIsoName = request.getParameter(ParameterConstants.LANGUAGE_ISO_NAME);
        
        try {
            if(forwardKey == null) {
                var actionForm = (DescriptionEditActionForm)form;
                var commandForm = UomUtil.getHome().getEditUnitOfMeasureTypeDescriptionForm();
                var spec = UomUtil.getHome().getUnitOfMeasureTypeDescriptionSpec();
                
                if(unitOfMeasureKindName == null)
                    unitOfMeasureKindName = actionForm.getUnitOfMeasureKindName();
                if(unitOfMeasureTypeName == null)
                    unitOfMeasureTypeName = actionForm.getUnitOfMeasureTypeName();
                if(languageIsoName == null)
                    languageIsoName = actionForm.getLanguageIsoName();
                
                commandForm.setSpec(spec);
                spec.setUnitOfMeasureKindName(unitOfMeasureKindName);
                spec.setUnitOfMeasureTypeName(unitOfMeasureTypeName);
                spec.setLanguageIsoName(languageIsoName);
                
                if(wasPost(request)) {
                    var edit = UomUtil.getHome().getUnitOfMeasureTypeDescriptionEdit();
                    
                    commandForm.setEditMode(EditMode.UPDATE);
                    commandForm.setEdit(edit);
                    edit.setSingularDescription(actionForm.getSingularDescription());
                    edit.setPluralDescription(actionForm.getPluralDescription());
                    edit.setSymbol(actionForm.getSymbol());

                    var commandResult = UomUtil.getHome().editUnitOfMeasureTypeDescription(getUserVisitPK(request), commandForm);
                    
                    if(commandResult.hasErrors()) {
                        var executionResult = commandResult.getExecutionResult();
                        
                        if(executionResult != null) {
                            var result = (EditUnitOfMeasureTypeDescriptionResult)executionResult.getResult();
                            
                            request.setAttribute(AttributeConstants.ENTITY_LOCK, result.getEntityLock());
                        }
                        
                        setCommandResultAttribute(request, commandResult);
                        
                        forwardKey = ForwardConstants.FORM;
                    } else {
                        forwardKey = ForwardConstants.DISPLAY;
                    }
                } else {
                    commandForm.setEditMode(EditMode.LOCK);

                    var commandResult = UomUtil.getHome().editUnitOfMeasureTypeDescription(getUserVisitPK(request), commandForm);
                    var executionResult = commandResult.getExecutionResult();
                    var result = (EditUnitOfMeasureTypeDescriptionResult)executionResult.getResult();
                    
                    if(result != null) {
                        var edit = result.getEdit();
                        
                        if(edit != null) {
                            actionForm.setUnitOfMeasureKindName(unitOfMeasureKindName);
                            actionForm.setUnitOfMeasureTypeName(unitOfMeasureTypeName);
                            actionForm.setLanguageIsoName(languageIsoName);
                            actionForm.setSingularDescription(edit.getSingularDescription());
                            actionForm.setPluralDescription(edit.getPluralDescription());
                            actionForm.setSymbol(edit.getSymbol());
                        }
                        
                        request.setAttribute(AttributeConstants.ENTITY_LOCK, result.getEntityLock());
                    }
                    
                    setCommandResultAttribute(request, commandResult);
                    
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
            request.setAttribute(AttributeConstants.LANGUAGE_ISO_NAME, languageIsoName);
        } else if(forwardKey.equals(ForwardConstants.DISPLAY)) {
            Map<String, String> parameters = new HashMap<>(2);
            
            parameters.put(ParameterConstants.UNIT_OF_MEASURE_KIND_NAME, unitOfMeasureKindName);
            parameters.put(ParameterConstants.UNIT_OF_MEASURE_TYPE_NAME, unitOfMeasureTypeName);
            customActionForward.setParameters(parameters);
        }
        
        return customActionForward;
    }
    
}