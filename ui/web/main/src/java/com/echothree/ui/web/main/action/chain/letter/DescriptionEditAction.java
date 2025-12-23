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

package com.echothree.ui.web.main.action.chain.letter;

import com.echothree.control.user.letter.common.LetterUtil;
import com.echothree.control.user.letter.common.result.EditLetterDescriptionResult;
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
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

@SproutAction(
    path = "/Chain/Letter/DescriptionEdit",
    mappingClass = SecureActionMapping.class,
    name = "LetterDescriptionEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Chain/Letter/Description", redirect = true),
        @SproutForward(name = "Form", path = "/chain/letter/descriptionEdit.jsp")
    }
)
public class DescriptionEditAction
        extends MainBaseAction<ActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String forwardKey;
        var chainKindName = request.getParameter(ParameterConstants.CHAIN_KIND_NAME);
        var chainTypeName = request.getParameter(ParameterConstants.CHAIN_TYPE_NAME);
        var letterName = request.getParameter(ParameterConstants.LETTER_NAME);
        var languageIsoName = request.getParameter(ParameterConstants.LANGUAGE_ISO_NAME);
        var actionForm = (DescriptionEditActionForm)form;
        var commandForm = LetterUtil.getHome().getEditLetterDescriptionForm();
        var spec = LetterUtil.getHome().getLetterDescriptionSpec();
        
        if(chainKindName == null)
            chainKindName = actionForm.getChainKindName();
        if(chainTypeName == null)
            chainTypeName = actionForm.getChainTypeName();
        if(letterName == null)
            letterName = actionForm.getLetterName();
        if(languageIsoName == null)
            languageIsoName = actionForm.getLanguageIsoName();
        
        commandForm.setSpec(spec);
        spec.setChainKindName(chainKindName);
        spec.setChainTypeName(chainTypeName);
        spec.setLetterName(letterName);
        spec.setLanguageIsoName(languageIsoName);
        
        if(wasPost(request)) {
            var edit = LetterUtil.getHome().getLetterDescriptionEdit();
            
            commandForm.setEditMode(EditMode.UPDATE);
            commandForm.setEdit(edit);
            edit.setDescription(actionForm.getDescription());

            var commandResult = LetterUtil.getHome().editLetterDescription(getUserVisitPK(request), commandForm);
            
            if(commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                
                if(executionResult != null) {
                    var result = (EditLetterDescriptionResult)executionResult.getResult();
                    
                    request.setAttribute(AttributeConstants.ENTITY_LOCK, result.getEntityLock());
                }
                
                setCommandResultAttribute(request, commandResult);
                
                forwardKey = ForwardConstants.FORM;
            } else {
                forwardKey = ForwardConstants.DISPLAY;
            }
        } else {
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = LetterUtil.getHome().editLetterDescription(getUserVisitPK(request), commandForm);
            var executionResult = commandResult.getExecutionResult();
            var result = (EditLetterDescriptionResult)executionResult.getResult();
            
            if(result != null) {
                var edit = result.getEdit();
                
                if(edit != null) {
                    actionForm.setChainKindName(chainKindName);
                    actionForm.setChainTypeName(chainTypeName);
                    actionForm.setLetterName(letterName);
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
            request.setAttribute(AttributeConstants.CHAIN_KIND_NAME, chainKindName);
            request.setAttribute(AttributeConstants.CHAIN_TYPE_NAME, chainTypeName);
            request.setAttribute(AttributeConstants.LETTER_NAME, letterName);
            request.setAttribute(AttributeConstants.LANGUAGE_ISO_NAME, languageIsoName);
        } else if(forwardKey.equals(ForwardConstants.DISPLAY)) {
            Map<String, String> parameters = new HashMap<>(3);
            
            parameters.put(ParameterConstants.CHAIN_KIND_NAME, chainKindName);
            parameters.put(ParameterConstants.CHAIN_TYPE_NAME, chainTypeName);
            parameters.put(ParameterConstants.LETTER_NAME, letterName);
            customActionForward.setParameters(parameters);
        }
        
        return customActionForward;
    }
    
}