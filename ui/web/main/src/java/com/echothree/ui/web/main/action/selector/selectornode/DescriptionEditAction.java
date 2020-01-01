// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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

package com.echothree.ui.web.main.action.selector.selectornode;

import com.echothree.control.user.selector.common.SelectorUtil;
import com.echothree.control.user.selector.common.edit.SelectorNodeDescriptionEdit;
import com.echothree.control.user.selector.common.form.EditSelectorNodeDescriptionForm;
import com.echothree.control.user.selector.common.result.EditSelectorNodeDescriptionResult;
import com.echothree.control.user.selector.common.spec.SelectorNodeDescriptionSpec;
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
    path = "/Selector/SelectorNode/DescriptionEdit",
    mappingClass = SecureActionMapping.class,
    name = "SelectorNodeDescriptionEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Selector/SelectorNode/Description", redirect = true),
        @SproutForward(name = "Form", path = "/selector/selectornode/descriptionEdit.jsp")
    }
)
public class DescriptionEditAction
        extends MainBaseAction<ActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
        String forwardKey = null;
        String selectorKindName = request.getParameter(ParameterConstants.SELECTOR_KIND_NAME);
        String selectorTypeName = request.getParameter(ParameterConstants.SELECTOR_TYPE_NAME);
        String selectorName = request.getParameter(ParameterConstants.SELECTOR_NAME);
        String selectorNodeName = request.getParameter(ParameterConstants.SELECTOR_NODE_NAME);
        String languageIsoName = request.getParameter(ParameterConstants.LANGUAGE_ISO_NAME);
        
        try {
            if(forwardKey == null) {
                DescriptionEditActionForm actionForm = (DescriptionEditActionForm)form;
                EditSelectorNodeDescriptionForm commandForm = SelectorUtil.getHome().getEditSelectorNodeDescriptionForm();
                SelectorNodeDescriptionSpec spec = SelectorUtil.getHome().getSelectorNodeDescriptionSpec();
                
                if(selectorKindName == null)
                    selectorKindName = actionForm.getSelectorKindName();
                if(selectorTypeName == null)
                    selectorTypeName = actionForm.getSelectorTypeName();
                if(selectorName == null)
                    selectorName = actionForm.getSelectorName();
                if(selectorNodeName == null)
                    selectorNodeName = actionForm.getSelectorNodeName();
                if(languageIsoName == null)
                    languageIsoName = actionForm.getLanguageIsoName();
                
                commandForm.setSpec(spec);
                spec.setSelectorKindName(selectorKindName);
                spec.setSelectorTypeName(selectorTypeName);
                spec.setSelectorName(selectorName);
                spec.setSelectorNodeName(selectorNodeName);
                spec.setLanguageIsoName(languageIsoName);
                
                if(wasPost(request)) {
                    SelectorNodeDescriptionEdit edit = SelectorUtil.getHome().getSelectorNodeDescriptionEdit();
                    
                    commandForm.setEditMode(EditMode.UPDATE);
                    commandForm.setEdit(edit);
                    edit.setDescription(actionForm.getDescription());
                    
                    CommandResult commandResult = SelectorUtil.getHome().editSelectorNodeDescription(getUserVisitPK(request), commandForm);
                    
                    if(commandResult.hasErrors()) {
                        ExecutionResult executionResult = commandResult.getExecutionResult();
                        
                        if(executionResult != null) {
                            EditSelectorNodeDescriptionResult result = (EditSelectorNodeDescriptionResult)executionResult.getResult();
                            
                            request.setAttribute(AttributeConstants.ENTITY_LOCK, result.getEntityLock());
                        }
                        
                        setCommandResultAttribute(request, commandResult);
                        
                        forwardKey = ForwardConstants.FORM;
                    } else {
                        forwardKey = ForwardConstants.DISPLAY;
                    }
                } else {
                    commandForm.setEditMode(EditMode.LOCK);
                    
                    CommandResult commandResult = SelectorUtil.getHome().editSelectorNodeDescription(getUserVisitPK(request), commandForm);
                    ExecutionResult executionResult = commandResult.getExecutionResult();
                    EditSelectorNodeDescriptionResult result = (EditSelectorNodeDescriptionResult)executionResult.getResult();
                    
                    if(result != null) {
                        SelectorNodeDescriptionEdit edit = result.getEdit();
                        
                        if(edit != null) {
                            actionForm.setSelectorKindName(selectorKindName);
                            actionForm.setSelectorTypeName(selectorTypeName);
                            actionForm.setSelectorName(selectorName);
                            actionForm.setSelectorNodeName(selectorNodeName);
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
            request.setAttribute(AttributeConstants.SELECTOR_KIND_NAME, selectorKindName);
            request.setAttribute(AttributeConstants.SELECTOR_TYPE_NAME, selectorTypeName);
            request.setAttribute(AttributeConstants.SELECTOR_TYPE_NAME, selectorName);
            request.setAttribute(AttributeConstants.SELECTOR_NODE_NAME, selectorNodeName);
            request.setAttribute(AttributeConstants.LANGUAGE_ISO_NAME, languageIsoName);
        } else if(forwardKey.equals(ForwardConstants.DISPLAY)) {
            Map<String, String> parameters = new HashMap<>(4);
            
            parameters.put(ParameterConstants.SELECTOR_KIND_NAME, selectorKindName);
            parameters.put(ParameterConstants.SELECTOR_TYPE_NAME, selectorTypeName);
            parameters.put(ParameterConstants.SELECTOR_NAME, selectorName);
            parameters.put(ParameterConstants.SELECTOR_NODE_NAME, selectorNodeName);
            customActionForward.setParameters(parameters);
        }
        
        return customActionForward;
    }
    
}