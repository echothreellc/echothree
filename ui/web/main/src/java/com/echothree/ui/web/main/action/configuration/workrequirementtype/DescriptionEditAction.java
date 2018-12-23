// --------------------------------------------------------------------------------
// Copyright 2002-2019 Echo Three, LLC
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

package com.echothree.ui.web.main.action.configuration.workrequirementtype;

import com.echothree.control.user.workrequirement.common.WorkRequirementUtil;
import com.echothree.control.user.workrequirement.common.edit.WorkRequirementTypeDescriptionEdit;
import com.echothree.control.user.workrequirement.common.form.EditWorkRequirementTypeDescriptionForm;
import com.echothree.control.user.workrequirement.common.result.EditWorkRequirementTypeDescriptionResult;
import com.echothree.control.user.workrequirement.common.spec.WorkRequirementTypeDescriptionSpec;
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
    path = "/Configuration/WorkRequirementType/DescriptionEdit",
    mappingClass = SecureActionMapping.class,
    name = "WorkRequirementTypeDescriptionEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Configuration/WorkRequirementType/Description", redirect = true),
        @SproutForward(name = "Form", path = "/configuration/workrequirementtype/descriptionEdit.jsp")
    }
)
public class DescriptionEditAction
        extends MainBaseAction<ActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
        String forwardKey = null;
        String workEffortTypeName = request.getParameter(ParameterConstants.WORK_EFFORT_TYPE_NAME);
        String workRequirementTypeName = request.getParameter(ParameterConstants.WORK_REQUIREMENT_TYPE_NAME);
        String languageIsoName = request.getParameter(ParameterConstants.LANGUAGE_ISO_NAME);
        
        try {
            if(forwardKey == null) {
                DescriptionEditActionForm actionForm = (DescriptionEditActionForm)form;
                EditWorkRequirementTypeDescriptionForm commandForm = WorkRequirementUtil.getHome().getEditWorkRequirementTypeDescriptionForm();
                WorkRequirementTypeDescriptionSpec spec = WorkRequirementUtil.getHome().getWorkRequirementTypeDescriptionSpec();
                
                if(workEffortTypeName == null)
                    workEffortTypeName = actionForm.getWorkEffortTypeName();
                if(workRequirementTypeName == null)
                    workRequirementTypeName = actionForm.getWorkRequirementTypeName();
                if(languageIsoName == null)
                    languageIsoName = actionForm.getLanguageIsoName();
                
                commandForm.setSpec(spec);
                spec.setWorkEffortTypeName(workEffortTypeName);
                spec.setWorkRequirementTypeName(workRequirementTypeName);
                spec.setLanguageIsoName(languageIsoName);
                
                if(wasPost(request)) {
                    WorkRequirementTypeDescriptionEdit edit = WorkRequirementUtil.getHome().getWorkRequirementTypeDescriptionEdit();
                    
                    commandForm.setEditMode(EditMode.UPDATE);
                    commandForm.setEdit(edit);
                    edit.setDescription(actionForm.getDescription());
                    
                    CommandResult commandResult = WorkRequirementUtil.getHome().editWorkRequirementTypeDescription(getUserVisitPK(request), commandForm);
                    
                    if(commandResult.hasErrors()) {
                        ExecutionResult executionResult = commandResult.getExecutionResult();
                        
                        if(executionResult != null) {
                            EditWorkRequirementTypeDescriptionResult result = (EditWorkRequirementTypeDescriptionResult)executionResult.getResult();
                            
                            request.setAttribute(AttributeConstants.ENTITY_LOCK, result.getEntityLock());
                        }
                        
                        setCommandResultAttribute(request, commandResult);
                        
                        forwardKey = ForwardConstants.FORM;
                    } else {
                        forwardKey = ForwardConstants.DISPLAY;
                    }
                } else {
                    commandForm.setEditMode(EditMode.LOCK);
                    
                    CommandResult commandResult = WorkRequirementUtil.getHome().editWorkRequirementTypeDescription(getUserVisitPK(request), commandForm);
                    ExecutionResult executionResult = commandResult.getExecutionResult();
                    EditWorkRequirementTypeDescriptionResult result = (EditWorkRequirementTypeDescriptionResult)executionResult.getResult();
                    
                    if(result != null) {
                        WorkRequirementTypeDescriptionEdit edit = result.getEdit();
                        
                        if(edit != null) {
                            actionForm.setWorkEffortTypeName(workEffortTypeName);
                            actionForm.setWorkRequirementTypeName(workRequirementTypeName);
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
            request.setAttribute(AttributeConstants.WORK_EFFORT_TYPE_NAME, workEffortTypeName);
            request.setAttribute(AttributeConstants.WORK_REQUIREMENT_TYPE_NAME, workRequirementTypeName);
            request.setAttribute(AttributeConstants.LANGUAGE_ISO_NAME, languageIsoName);
        } else if(forwardKey.equals(ForwardConstants.DISPLAY)) {
            Map<String, String> parameters = new HashMap<>(2);
            
            parameters.put(ParameterConstants.WORK_EFFORT_TYPE_NAME, workEffortTypeName);
            parameters.put(ParameterConstants.WORK_REQUIREMENT_TYPE_NAME, workRequirementTypeName);
            customActionForward.setParameters(parameters);
        }
        
        return customActionForward;
    }
    
}