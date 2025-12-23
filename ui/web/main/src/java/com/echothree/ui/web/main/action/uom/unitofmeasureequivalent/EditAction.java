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

package com.echothree.ui.web.main.action.uom.unitofmeasureequivalent;

import com.echothree.control.user.uom.common.UomUtil;
import com.echothree.control.user.uom.common.result.EditUnitOfMeasureEquivalentResult;
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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

@SproutAction(
    path = "/UnitOfMeasure/UnitOfMeasureEquivalent/Edit",
    mappingClass = SecureActionMapping.class,
    name = "UnitOfMeasureEquivalentEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/UnitOfMeasure/UnitOfMeasureEquivalent/Main", redirect = true),
        @SproutForward(name = "Form", path = "/uom/unitofmeasureequivalent/edit.jsp")
    }
)
public class EditAction
        extends MainBaseAction<EditActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, EditActionForm actionForm, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String forwardKey;
        var unitOfMeasureKindName = request.getParameter(ParameterConstants.UNIT_OF_MEASURE_KIND_NAME);
        var fromUnitOfMeasureTypeName = request.getParameter(ParameterConstants.FROM_UNIT_OF_MEASURE_TYPE_NAME);
        var toUnitOfMeasureTypeName = request.getParameter(ParameterConstants.TO_UNIT_OF_MEASURE_TYPE_NAME);
        var commandForm = UomUtil.getHome().getEditUnitOfMeasureEquivalentForm();
        var spec = UomUtil.getHome().getUnitOfMeasureEquivalentSpec();
        
        if(unitOfMeasureKindName == null)
            unitOfMeasureKindName = actionForm.getUnitOfMeasureKindName();
        if(fromUnitOfMeasureTypeName == null)
            fromUnitOfMeasureTypeName = actionForm.getFromUnitOfMeasureTypeName();
        if(toUnitOfMeasureTypeName == null)
            toUnitOfMeasureTypeName = actionForm.getToUnitOfMeasureTypeName();
        
        commandForm.setSpec(spec);
        spec.setUnitOfMeasureKindName(unitOfMeasureKindName);
        spec.setFromUnitOfMeasureTypeName(fromUnitOfMeasureTypeName);
        spec.setToUnitOfMeasureTypeName(toUnitOfMeasureTypeName);
        
        if(wasPost(request)) {
            var edit = UomUtil.getHome().getUnitOfMeasureEquivalentEdit();
            
            commandForm.setEditMode(EditMode.UPDATE);
            commandForm.setEdit(edit);
            
            edit.setToQuantity(actionForm.getToQuantity());

            var commandResult = UomUtil.getHome().editUnitOfMeasureEquivalent(getUserVisitPK(request), commandForm);
            
            if(commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                
                if(executionResult != null) {
                    var result = (EditUnitOfMeasureEquivalentResult)executionResult.getResult();
                    
                    request.setAttribute(AttributeConstants.ENTITY_LOCK, result.getEntityLock());
                }
                
                setCommandResultAttribute(request, commandResult);
                
                forwardKey = ForwardConstants.FORM;
            } else {
                forwardKey = ForwardConstants.DISPLAY;
            }
        } else {
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = UomUtil.getHome().editUnitOfMeasureEquivalent(getUserVisitPK(request), commandForm);
            var executionResult = commandResult.getExecutionResult();
            var result = (EditUnitOfMeasureEquivalentResult)executionResult.getResult();
            
            if(result != null) {
                var edit = result.getEdit();
                
                if(edit != null) {
                    actionForm.setUnitOfMeasureKindName(unitOfMeasureKindName);
                    actionForm.setFromUnitOfMeasureTypeName(fromUnitOfMeasureTypeName);
                    actionForm.setToUnitOfMeasureTypeName(toUnitOfMeasureTypeName);
                    actionForm.setToQuantity(edit.getToQuantity());
                }
                
                request.setAttribute(AttributeConstants.ENTITY_LOCK, result.getEntityLock());
            }
            
            setCommandResultAttribute(request, commandResult);
            
            forwardKey = ForwardConstants.FORM;
        }

        var customActionForward = new CustomActionForward(mapping.findForward(forwardKey));
        if(forwardKey.equals(ForwardConstants.FORM)) {
            request.setAttribute(AttributeConstants.UNIT_OF_MEASURE_KIND_NAME, unitOfMeasureKindName);
            request.setAttribute(AttributeConstants.FROM_UNIT_OF_MEASURE_TYPE_NAME, fromUnitOfMeasureTypeName);
            request.setAttribute(AttributeConstants.TO_UNIT_OF_MEASURE_TYPE_NAME, toUnitOfMeasureTypeName);
        } else if(forwardKey.equals(ForwardConstants.DISPLAY)) {
            Map<String, String> parameters = new HashMap<>(1);
            
            parameters.put(ParameterConstants.UNIT_OF_MEASURE_KIND_NAME, unitOfMeasureKindName);
            customActionForward.setParameters(parameters);
        }
        
        return customActionForward;
    }
    
}