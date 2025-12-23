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

package com.echothree.ui.web.main.action.configuration.workflowdestination;

import com.echothree.control.user.workflow.common.WorkflowUtil;
import com.echothree.control.user.workflow.common.result.EditWorkflowDestinationDescriptionResult;
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
    path = "/Configuration/WorkflowDestination/DescriptionEdit",
    mappingClass = SecureActionMapping.class,
    name = "WorkflowDestinationDescriptionEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Configuration/WorkflowDestination/Description", redirect = true),
        @SproutForward(name = "Form", path = "/configuration/workflowdestination/descriptionEdit.jsp")
    }
)
public class DescriptionEditAction
        extends MainBaseAction<ActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
        String forwardKey = null;
        var workflowName = request.getParameter(ParameterConstants.WORKFLOW_NAME);
        var workflowStepName = request.getParameter(ParameterConstants.WORKFLOW_STEP_NAME);
        var workflowDestinationName = request.getParameter(ParameterConstants.WORKFLOW_DESTINATION_NAME);
        var languageIsoName = request.getParameter(ParameterConstants.LANGUAGE_ISO_NAME);
        
        try {
            if(forwardKey == null) {
                var actionForm = (DescriptionEditActionForm)form;
                var commandForm = WorkflowUtil.getHome().getEditWorkflowDestinationDescriptionForm();
                var spec = WorkflowUtil.getHome().getWorkflowDestinationDescriptionSpec();
                
                if(workflowName == null)
                    workflowName = actionForm.getWorkflowName();
                if(workflowStepName == null)
                    workflowStepName = actionForm.getWorkflowStepName();
                if(workflowDestinationName == null)
                    workflowDestinationName = actionForm.getWorkflowDestinationName();
                if(languageIsoName == null)
                    languageIsoName = actionForm.getLanguageIsoName();
                
                commandForm.setSpec(spec);
                spec.setWorkflowName(workflowName);
                spec.setWorkflowStepName(workflowStepName);
                spec.setWorkflowDestinationName(workflowDestinationName);
                spec.setLanguageIsoName(languageIsoName);
                
                if(wasPost(request)) {
                    var edit = WorkflowUtil.getHome().getWorkflowDestinationDescriptionEdit();
                    
                    commandForm.setEditMode(EditMode.UPDATE);
                    commandForm.setEdit(edit);
                    edit.setDescription(actionForm.getDescription());

                    var commandResult = WorkflowUtil.getHome().editWorkflowDestinationDescription(getUserVisitPK(request), commandForm);
                    
                    if(commandResult.hasErrors()) {
                        var executionResult = commandResult.getExecutionResult();
                        
                        if(executionResult != null) {
                            var result = (EditWorkflowDestinationDescriptionResult)executionResult.getResult();
                            
                            request.setAttribute(AttributeConstants.ENTITY_LOCK, result.getEntityLock());
                        }
                        
                        setCommandResultAttribute(request, commandResult);
                        
                        forwardKey = ForwardConstants.FORM;
                    } else {
                        forwardKey = ForwardConstants.DISPLAY;
                    }
                } else {
                    commandForm.setEditMode(EditMode.LOCK);

                    var commandResult = WorkflowUtil.getHome().editWorkflowDestinationDescription(getUserVisitPK(request), commandForm);
                    var executionResult = commandResult.getExecutionResult();
                    var result = (EditWorkflowDestinationDescriptionResult)executionResult.getResult();
                    
                    if(result != null) {
                        var edit = result.getEdit();
                        
                        if(edit != null) {
                            actionForm.setWorkflowName(workflowName);
                            actionForm.setWorkflowStepName(workflowStepName);
                            actionForm.setWorkflowDestinationName(workflowDestinationName);
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

        var customActionForward = new CustomActionForward(mapping.findForward(forwardKey));
        if(forwardKey.equals(ForwardConstants.FORM)) {
            request.setAttribute(AttributeConstants.WORKFLOW_NAME, workflowName);
            request.setAttribute(AttributeConstants.WORKFLOW_STEP_NAME, workflowStepName);
            request.setAttribute(AttributeConstants.WORKFLOW_DESTINATION_NAME, workflowDestinationName);
            request.setAttribute(AttributeConstants.LANGUAGE_ISO_NAME, languageIsoName);
        } else if(forwardKey.equals(ForwardConstants.DISPLAY)) {
            Map<String, String> parameters = new HashMap<>(3);
            
            parameters.put(ParameterConstants.WORKFLOW_DESTINATION_NAME, workflowDestinationName);
            parameters.put(ParameterConstants.WORKFLOW_STEP_NAME, workflowStepName);
            parameters.put(ParameterConstants.WORKFLOW_NAME, workflowName);
            customActionForward.setParameters(parameters);
        }
        
        return customActionForward;
    }
    
}