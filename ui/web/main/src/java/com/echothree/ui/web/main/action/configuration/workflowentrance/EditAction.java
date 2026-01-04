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

package com.echothree.ui.web.main.action.configuration.workflowentrance;

import com.echothree.control.user.workflow.common.WorkflowUtil;
import com.echothree.control.user.workflow.common.edit.WorkflowEntranceEdit;
import com.echothree.control.user.workflow.common.form.EditWorkflowEntranceForm;
import com.echothree.control.user.workflow.common.result.EditWorkflowEntranceResult;
import com.echothree.control.user.workflow.common.spec.WorkflowEntranceUniversalSpec;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.MainBaseEditAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.util.common.command.CommandResult;
import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import java.util.Map;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;

@SproutAction(
    path = "/Configuration/WorkflowEntrance/Edit",
    mappingClass = SecureActionMapping.class,
    name = "WorkflowEntranceEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Configuration/WorkflowEntrance/Main", redirect = true),
        @SproutForward(name = "Form", path = "/configuration/workflowentrance/edit.jsp")
    }
)
public class EditAction
        extends MainBaseEditAction<EditActionForm, WorkflowEntranceUniversalSpec, WorkflowEntranceEdit, EditWorkflowEntranceForm, EditWorkflowEntranceResult> {
    
    @Override
    protected WorkflowEntranceUniversalSpec getSpec(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        var spec = WorkflowUtil.getHome().getWorkflowEntranceUniversalSpec();
        
        spec.setWorkflowName(findParameter(request, ParameterConstants.WORKFLOW_NAME, actionForm.getWorkflowName()));
        spec.setWorkflowEntranceName(findParameter(request, ParameterConstants.ORIGINAL_WORKFLOW_ENTRANCE_NAME, actionForm.getOriginalWorkflowEntranceName()));
        
        return spec;
    }
    
    @Override
    protected WorkflowEntranceEdit getEdit(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        var edit = WorkflowUtil.getHome().getWorkflowEntranceEdit();

        edit.setWorkflowEntranceName(actionForm.getWorkflowEntranceName());
        edit.setIsDefault(actionForm.getIsDefault().toString());
        edit.setSortOrder(actionForm.getSortOrder());
        edit.setDescription(actionForm.getDescription());

        return edit;
    }
    
    @Override
    protected EditWorkflowEntranceForm getForm()
            throws NamingException {
        return WorkflowUtil.getHome().getEditWorkflowEntranceForm();
    }
    
    @Override
    protected void setupActionForm(HttpServletRequest request, EditActionForm actionForm, EditWorkflowEntranceResult result, WorkflowEntranceUniversalSpec spec, WorkflowEntranceEdit edit) {
        actionForm.setWorkflowName(spec.getWorkflowName());
        actionForm.setOriginalWorkflowEntranceName(spec.getWorkflowEntranceName());
        actionForm.setWorkflowEntranceName(edit.getWorkflowEntranceName());
        actionForm.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        actionForm.setSortOrder(edit.getSortOrder());
        actionForm.setDescription(edit.getDescription());
    }
    
    @Override
    protected CommandResult doEdit(HttpServletRequest request, EditWorkflowEntranceForm commandForm)
            throws Exception {
        return WorkflowUtil.getHome().editWorkflowEntrance(getUserVisitPK(request), commandForm);
    }
    
    @Override
    public void setupForwardParameters(EditActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.WORKFLOW_NAME, actionForm.getWorkflowName());
    }
    
    @Override
    protected void setupTransferForForm(HttpServletRequest request, EditActionForm actionForm, EditWorkflowEntranceResult result) {
        request.setAttribute(AttributeConstants.WORKFLOW_ENTRANCE, result.getWorkflowEntrance());
    }

}
