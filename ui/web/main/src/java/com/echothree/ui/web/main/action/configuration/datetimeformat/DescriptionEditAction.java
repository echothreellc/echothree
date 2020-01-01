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

package com.echothree.ui.web.main.action.configuration.datetimeformat;

import com.echothree.control.user.party.common.PartyUtil;
import com.echothree.control.user.party.common.edit.DateTimeFormatDescriptionEdit;
import com.echothree.control.user.party.common.form.EditDateTimeFormatDescriptionForm;
import com.echothree.control.user.party.common.result.EditDateTimeFormatDescriptionResult;
import com.echothree.control.user.party.common.spec.DateTimeFormatDescriptionSpec;
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
    path = "/Configuration/DateTimeFormat/DescriptionEdit",
    mappingClass = SecureActionMapping.class,
    name = "DateTimeFormatDescriptionEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Configuration/DateTimeFormat/Description", redirect = true),
        @SproutForward(name = "Form", path = "/configuration/datetimeformat/descriptionEdit.jsp")
    }
)
public class DescriptionEditAction
        extends MainBaseAction<ActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
        String forwardKey = null;
        String dateTimeFormatName = request.getParameter(ParameterConstants.DATE_TIME_FORMAT_NAME);
        String languageIsoName = request.getParameter(ParameterConstants.LANGUAGE_ISO_NAME);
        
        try {
            if(forwardKey == null) {
                DescriptionEditActionForm actionForm = (DescriptionEditActionForm)form;
                EditDateTimeFormatDescriptionForm commandForm = PartyUtil.getHome().getEditDateTimeFormatDescriptionForm();
                DateTimeFormatDescriptionSpec spec = PartyUtil.getHome().getDateTimeFormatDescriptionSpec();
                
                if(dateTimeFormatName == null)
                    dateTimeFormatName = actionForm.getDateTimeFormatName();
                if(languageIsoName == null)
                    languageIsoName = actionForm.getLanguageIsoName();
                
                commandForm.setSpec(spec);
                spec.setDateTimeFormatName(dateTimeFormatName);
                spec.setLanguageIsoName(languageIsoName);
                
                if(wasPost(request)) {
                    DateTimeFormatDescriptionEdit edit = PartyUtil.getHome().getDateTimeFormatDescriptionEdit();
                    
                    commandForm.setEditMode(EditMode.UPDATE);
                    commandForm.setEdit(edit);
                    edit.setDescription(actionForm.getDescription());
                    
                    CommandResult commandResult = PartyUtil.getHome().editDateTimeFormatDescription(getUserVisitPK(request), commandForm);
                    
                    if(commandResult.hasErrors()) {
                        ExecutionResult executionResult = commandResult.getExecutionResult();
                        
                        if(executionResult != null) {
                            EditDateTimeFormatDescriptionResult result = (EditDateTimeFormatDescriptionResult)executionResult.getResult();
                            
                            request.setAttribute(AttributeConstants.ENTITY_LOCK, result.getEntityLock());
                        }
                        
                        setCommandResultAttribute(request, commandResult);
                        
                        forwardKey = ForwardConstants.FORM;
                    } else {
                        forwardKey = ForwardConstants.DISPLAY;
                    }
                } else {
                    commandForm.setEditMode(EditMode.LOCK);
                    
                    CommandResult commandResult = PartyUtil.getHome().editDateTimeFormatDescription(getUserVisitPK(request), commandForm);
                    ExecutionResult executionResult = commandResult.getExecutionResult();
                    EditDateTimeFormatDescriptionResult result = (EditDateTimeFormatDescriptionResult)executionResult.getResult();
                    
                    if(result != null) {
                        DateTimeFormatDescriptionEdit edit = result.getEdit();
                        
                        if(edit != null) {
                            actionForm.setDateTimeFormatName(dateTimeFormatName);
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
            request.setAttribute(AttributeConstants.DATE_TIME_FORMAT_NAME, dateTimeFormatName);
            request.setAttribute(AttributeConstants.LANGUAGE_ISO_NAME, languageIsoName);
        } else if(forwardKey.equals(ForwardConstants.DISPLAY)) {
            Map<String, String> parameters = new HashMap<>(1);
            
            parameters.put(ParameterConstants.DATE_TIME_FORMAT_NAME, dateTimeFormatName);
            customActionForward.setParameters(parameters);
        }
        
        return customActionForward;
    }
    
}