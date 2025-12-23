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

package com.echothree.ui.web.main.action.chain.lettersource;

import com.echothree.control.user.letter.common.LetterUtil;
import com.echothree.control.user.letter.common.result.EditLetterSourceDescriptionResult;
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
    path = "/Chain/LetterSource/DescriptionEdit",
    mappingClass = SecureActionMapping.class,
    name = "LetterSourceDescriptionEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Chain/LetterSource/Description", redirect = true),
        @SproutForward(name = "Form", path = "/chain/lettersource/descriptionEdit.jsp")
    }
)
public class DescriptionEditAction
        extends MainBaseAction<DescriptionEditActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, DescriptionEditActionForm actionForm, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String forwardKey;
        var letterSourceName = request.getParameter(ParameterConstants.LETTER_SOURCE_NAME);
        var languageIsoName = request.getParameter(ParameterConstants.LANGUAGE_ISO_NAME);
        var commandForm = LetterUtil.getHome().getEditLetterSourceDescriptionForm();
        var spec = LetterUtil.getHome().getLetterSourceDescriptionSpec();
        
        if(letterSourceName == null) {
            letterSourceName = actionForm.getLetterSourceName();
        }
        if(languageIsoName == null){
            languageIsoName = actionForm.getLanguageIsoName();
        }
        
        commandForm.setSpec(spec);
        spec.setLetterSourceName(letterSourceName);
        spec.setLanguageIsoName(languageIsoName);
        
        if(wasPost(request)) {
            var edit = LetterUtil.getHome().getLetterSourceDescriptionEdit();
            
            commandForm.setEditMode(EditMode.UPDATE);
            commandForm.setEdit(edit);
            edit.setDescription(actionForm.getDescription());

            var commandResult = LetterUtil.getHome().editLetterSourceDescription(getUserVisitPK(request), commandForm);
            
            if(commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                
                if(executionResult != null) {
                    var result = (EditLetterSourceDescriptionResult)executionResult.getResult();
                    
                    request.setAttribute(AttributeConstants.ENTITY_LOCK, result.getEntityLock());
                }
                
                setCommandResultAttribute(request, commandResult);
                
                forwardKey = ForwardConstants.FORM;
            } else {
                forwardKey = ForwardConstants.DISPLAY;
            }
        } else {
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = LetterUtil.getHome().editLetterSourceDescription(getUserVisitPK(request), commandForm);
            var executionResult = commandResult.getExecutionResult();
            var result = (EditLetterSourceDescriptionResult)executionResult.getResult();
            
            if(result != null) {
                var edit = result.getEdit();
                
                if(edit != null) {
                    actionForm.setLetterSourceName(letterSourceName);
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
            request.setAttribute(AttributeConstants.LETTER_SOURCE_NAME, letterSourceName);
            request.setAttribute(AttributeConstants.LANGUAGE_ISO_NAME, languageIsoName);
        } else if(forwardKey.equals(ForwardConstants.DISPLAY)) {
            Map<String, String> parameters = new HashMap<>(1);
            
            parameters.put(ParameterConstants.LETTER_SOURCE_NAME, letterSourceName);
            customActionForward.setParameters(parameters);
        }
        
        return customActionForward;
    }
    
}