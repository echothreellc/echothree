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

package com.echothree.ui.web.main.action.cancellationpolicy.cancellationpolicy;

import com.echothree.control.user.cancellationpolicy.common.CancellationPolicyUtil;
import com.echothree.control.user.cancellationpolicy.common.result.EditCancellationPolicyTranslationResult;
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
    path = "/CancellationPolicy/CancellationPolicy/TranslationEdit",
    mappingClass = SecureActionMapping.class,
    name = "CancellationPolicyTranslationEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/CancellationPolicy/CancellationPolicy/Translation", redirect = true),
        @SproutForward(name = "Form", path = "/cancellationpolicy/cancellationpolicy/translationEdit.jsp")
    }
)
public class TranslationEditAction
        extends MainBaseAction<ActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
        String forwardKey = null;
        var cancellationKindName = request.getParameter(ParameterConstants.CANCELLATION_KIND_NAME);
        var cancellationPolicyName = request.getParameter(ParameterConstants.CANCELLATION_POLICY_NAME);
        var languageIsoName = request.getParameter(ParameterConstants.LANGUAGE_ISO_NAME);
        
        try {
            if(forwardKey == null) {
                var actionForm = (TranslationEditActionForm)form;
                var commandForm = CancellationPolicyUtil.getHome().getEditCancellationPolicyTranslationForm();
                var spec = CancellationPolicyUtil.getHome().getCancellationPolicyTranslationSpec();
                
                if(cancellationKindName == null)
                    cancellationKindName = actionForm.getCancellationKindName();
                if(cancellationPolicyName == null)
                    cancellationPolicyName = actionForm.getCancellationPolicyName();
                if(languageIsoName == null)
                    languageIsoName = actionForm.getLanguageIsoName();
                
                commandForm.setSpec(spec);
                spec.setCancellationKindName(cancellationKindName);
                spec.setCancellationPolicyName(cancellationPolicyName);
                spec.setLanguageIsoName(languageIsoName);
                
                if(wasPost(request)) {
                    var edit = CancellationPolicyUtil.getHome().getCancellationPolicyTranslationEdit();
                    
                    commandForm.setEditMode(EditMode.UPDATE);
                    commandForm.setEdit(edit);
                    edit.setDescription(actionForm.getDescription());
                    edit.setPolicyMimeTypeName(actionForm.getPolicyMimeTypeChoice());
                    edit.setPolicy(actionForm.getPolicy());

                    var commandResult = CancellationPolicyUtil.getHome().editCancellationPolicyTranslation(getUserVisitPK(request), commandForm);
                    
                    if(commandResult.hasErrors()) {
                        var executionResult = commandResult.getExecutionResult();
                        
                        if(executionResult != null) {
                            var result = (EditCancellationPolicyTranslationResult)executionResult.getResult();
                            
                            request.setAttribute(AttributeConstants.ENTITY_LOCK, result.getEntityLock());
                        }
                        
                        setCommandResultAttribute(request, commandResult);
                        
                        forwardKey = ForwardConstants.FORM;
                    } else {
                        forwardKey = ForwardConstants.DISPLAY;
                    }
                } else {
                    commandForm.setEditMode(EditMode.LOCK);

                    var commandResult = CancellationPolicyUtil.getHome().editCancellationPolicyTranslation(getUserVisitPK(request), commandForm);
                    var executionResult = commandResult.getExecutionResult();
                    var result = (EditCancellationPolicyTranslationResult)executionResult.getResult();
                    
                    if(result != null) {
                        var edit = result.getEdit();
                        
                        if(edit != null) {
                            actionForm.setCancellationKindName(cancellationKindName);
                            actionForm.setCancellationPolicyName(cancellationPolicyName);
                            actionForm.setLanguageIsoName(languageIsoName);
                            actionForm.setDescription(edit.getDescription());
                            actionForm.setPolicyMimeTypeChoice(edit.getPolicyMimeTypeName());
                            actionForm.setPolicy(edit.getPolicy());
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
            request.setAttribute(AttributeConstants.CANCELLATION_KIND_NAME, cancellationKindName);
            request.setAttribute(AttributeConstants.CANCELLATION_POLICY_NAME, cancellationPolicyName);
            request.setAttribute(AttributeConstants.LANGUAGE_ISO_NAME, languageIsoName);
        } else if(forwardKey.equals(ForwardConstants.DISPLAY)) {
            Map<String, String> parameters = new HashMap<>(2);
            
            parameters.put(ParameterConstants.CANCELLATION_KIND_NAME, cancellationKindName);
            parameters.put(ParameterConstants.CANCELLATION_POLICY_NAME, cancellationPolicyName);
            customActionForward.setParameters(parameters);
        }
        
        return customActionForward;
    }
    
}