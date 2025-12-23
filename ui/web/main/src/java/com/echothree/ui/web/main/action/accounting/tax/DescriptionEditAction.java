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

package com.echothree.ui.web.main.action.accounting.tax;

import com.echothree.control.user.tax.common.TaxUtil;
import com.echothree.control.user.tax.common.result.EditTaxDescriptionResult;
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
    path = "/Accounting/Tax/DescriptionEdit",
    mappingClass = SecureActionMapping.class,
    name = "TaxDescriptionEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Accounting/Tax/Description", redirect = true),
        @SproutForward(name = "Form", path = "/accounting/tax/descriptionEdit.jsp")
    }
)
public class DescriptionEditAction
        extends MainBaseAction<DescriptionEditActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, DescriptionEditActionForm actionForm, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String forwardKey;
        var taxName = request.getParameter(ParameterConstants.TAX_NAME);
        var languageIsoName = request.getParameter(ParameterConstants.LANGUAGE_ISO_NAME);
        var commandForm = TaxUtil.getHome().getEditTaxDescriptionForm();
        var spec = TaxUtil.getHome().getTaxDescriptionSpec();
        
        if(taxName == null)
            taxName = actionForm.getTaxName();
        if(languageIsoName == null)
            languageIsoName = actionForm.getLanguageIsoName();
        
        commandForm.setSpec(spec);
        spec.setTaxName(taxName);
        spec.setLanguageIsoName(languageIsoName);
        
        if(wasPost(request)) {
            var edit = TaxUtil.getHome().getTaxDescriptionEdit();
            
            commandForm.setEditMode(EditMode.UPDATE);
            commandForm.setEdit(edit);
            edit.setDescription(actionForm.getDescription());

            var commandResult = TaxUtil.getHome().editTaxDescription(getUserVisitPK(request), commandForm);
            
            if(commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                
                if(executionResult != null) {
                    var result = (EditTaxDescriptionResult)executionResult.getResult();
                    
                    request.setAttribute(AttributeConstants.ENTITY_LOCK, result.getEntityLock());
                }
                
                setCommandResultAttribute(request, commandResult);
                
                forwardKey = ForwardConstants.FORM;
            } else {
                forwardKey = ForwardConstants.DISPLAY;
            }
        } else {
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = TaxUtil.getHome().editTaxDescription(getUserVisitPK(request), commandForm);
            var executionResult = commandResult.getExecutionResult();
            var result = (EditTaxDescriptionResult)executionResult.getResult();
            
            if(result != null) {
                var edit = result.getEdit();
                
                if(edit != null) {
                    actionForm.setTaxName(taxName);
                    actionForm.setLanguageIsoName(languageIsoName);
                    actionForm.setDescription(edit.getDescription());
                }
                
                request.setAttribute(AttributeConstants.ENTITY_LOCK, result.getEntityLock());
            }
            
            setCommandResultAttribute(request, commandResult);
            
            forwardKey = ForwardConstants.FORM;
        }

        var customActionForward = new CustomActionForward(mapping.findForward(forwardKey));
        if(forwardKey.equals(ForwardConstants.FORM)) {
            request.setAttribute(AttributeConstants.TAX_NAME, taxName);
            request.setAttribute(AttributeConstants.LANGUAGE_ISO_NAME, languageIsoName);
        } else if(forwardKey.equals(ForwardConstants.DISPLAY)) {
            Map<String, String> parameters = new HashMap<>(1);
            
            parameters.put(ParameterConstants.TAX_NAME, taxName);
            customActionForward.setParameters(parameters);
        }
        
        return customActionForward;
    }
    
}