// --------------------------------------------------------------------------------
// Copyright 2002-2021 Echo Three, LLC
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

package com.echothree.ui.web.main.action.shipping.carrieroption;

import com.echothree.control.user.carrier.common.CarrierUtil;
import com.echothree.control.user.carrier.common.edit.CarrierOptionDescriptionEdit;
import com.echothree.control.user.carrier.common.form.EditCarrierOptionDescriptionForm;
import com.echothree.control.user.carrier.common.result.EditCarrierOptionDescriptionResult;
import com.echothree.control.user.carrier.common.spec.CarrierOptionDescriptionSpec;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.ForwardConstants;
import com.echothree.ui.web.main.framework.MainBaseAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.common.command.ExecutionResult;
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
    path = "/Shipping/CarrierOption/DescriptionEdit",
    mappingClass = SecureActionMapping.class,
    name = "CarrierOptionDescriptionEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Shipping/CarrierOption/Description", redirect = true),
        @SproutForward(name = "Form", path = "/shipping/carrieroption/descriptionEdit.jsp")
    }
)
public class DescriptionEditAction
        extends MainBaseAction<ActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
        String forwardKey = null;
        String carrierName = request.getParameter(ParameterConstants.CARRIER_NAME);
        String carrierOptionName = request.getParameter(ParameterConstants.CARRIER_OPTION_NAME);
        String languageIsoName = request.getParameter(ParameterConstants.LANGUAGE_ISO_NAME);
        
        try {
            if(forwardKey == null) {
                DescriptionEditActionForm actionForm = (DescriptionEditActionForm)form;
                EditCarrierOptionDescriptionForm commandForm = CarrierUtil.getHome().getEditCarrierOptionDescriptionForm();
                CarrierOptionDescriptionSpec spec = CarrierUtil.getHome().getCarrierOptionDescriptionSpec();
                
                if(carrierName == null)
                    carrierName = actionForm.getCarrierName();
                if(carrierOptionName == null)
                    carrierOptionName = actionForm.getCarrierOptionName();
                if(languageIsoName == null)
                    languageIsoName = actionForm.getLanguageIsoName();
                
                commandForm.setSpec(spec);
                spec.setCarrierName(carrierName);
                spec.setCarrierOptionName(carrierOptionName);
                spec.setLanguageIsoName(languageIsoName);
                
                if(wasPost(request)) {
                    CarrierOptionDescriptionEdit edit = CarrierUtil.getHome().getCarrierOptionDescriptionEdit();
                    
                    commandForm.setEditMode(EditMode.UPDATE);
                    commandForm.setEdit(edit);
                    edit.setDescription(actionForm.getDescription());
                    
                    CommandResult commandResult = CarrierUtil.getHome().editCarrierOptionDescription(getUserVisitPK(request), commandForm);
                    
                    if(commandResult.hasErrors()) {
                        ExecutionResult executionResult = commandResult.getExecutionResult();
                        
                        if(executionResult != null) {
                            EditCarrierOptionDescriptionResult result = (EditCarrierOptionDescriptionResult)executionResult.getResult();
                            
                            request.setAttribute(AttributeConstants.ENTITY_LOCK, result.getEntityLock());
                        }
                        
                        setCommandResultAttribute(request, commandResult);
                        
                        forwardKey = ForwardConstants.FORM;
                    } else {
                        forwardKey = ForwardConstants.DISPLAY;
                    }
                } else {
                    commandForm.setEditMode(EditMode.LOCK);
                    
                    CommandResult commandResult = CarrierUtil.getHome().editCarrierOptionDescription(getUserVisitPK(request), commandForm);
                    ExecutionResult executionResult = commandResult.getExecutionResult();
                    EditCarrierOptionDescriptionResult result = (EditCarrierOptionDescriptionResult)executionResult.getResult();
                    
                    if(result != null) {
                        CarrierOptionDescriptionEdit edit = result.getEdit();
                        
                        if(edit != null) {
                            actionForm.setCarrierName(carrierName);
                            actionForm.setCarrierOptionName(carrierOptionName);
                            actionForm.setLanguageIsoName(languageIsoName);
                            actionForm.setDescription(edit.getDescription());
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
        
        CustomActionForward customActionForward = new CustomActionForward(mapping.findForward(forwardKey));
        if(forwardKey.equals(ForwardConstants.FORM)) {
            request.setAttribute(AttributeConstants.CARRIER_NAME, carrierName);
            request.setAttribute(AttributeConstants.CARRIER_OPTION_NAME, carrierOptionName);
            request.setAttribute(AttributeConstants.LANGUAGE_ISO_NAME, languageIsoName);
        } else if(forwardKey.equals(ForwardConstants.DISPLAY)) {
            Map<String, String> parameters = new HashMap<>(2);
            
            parameters.put(ParameterConstants.CARRIER_NAME, carrierName);
            parameters.put(ParameterConstants.CARRIER_OPTION_NAME, carrierOptionName);
            customActionForward.setParameters(parameters);
        }
        
        return customActionForward;
    }
    
}