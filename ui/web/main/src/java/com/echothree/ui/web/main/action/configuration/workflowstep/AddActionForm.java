// --------------------------------------------------------------------------------
// Copyright 2002-2025 Echo Three, LLC
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

package com.echothree.ui.web.main.action.configuration.workflowstep;

import com.echothree.control.user.workflow.common.WorkflowUtil;
import com.echothree.control.user.workflow.common.result.GetWorkflowStepTypeChoicesResult;
import com.echothree.model.control.workflow.common.choice.WorkflowStepTypeChoicesBean;
import com.echothree.view.client.web.struts.BaseActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;

@SproutForm(name="WorkflowStepAdd")
public class AddActionForm
        extends BaseActionForm {
    
    private WorkflowStepTypeChoicesBean workflowStepTypeChoices;
    
    private String workflowName;
    private String workflowStepName;
    private String workflowStepTypeChoice;
    private Boolean isDefault;
    private String sortOrder;
    private String description;
    
    public void setupWorkflowStepTypeChoices()
            throws NamingException {
        if(workflowStepTypeChoices == null) {
            var form = WorkflowUtil.getHome().getGetWorkflowStepTypeChoicesForm();

            form.setDefaultWorkflowStepTypeChoice(workflowStepTypeChoice);
            form.setAllowNullChoice(String.valueOf(false));

            var commandResult = WorkflowUtil.getHome().getWorkflowStepTypeChoices(userVisitPK, form);
            var executionResult = commandResult.getExecutionResult();
            var result = (GetWorkflowStepTypeChoicesResult)executionResult.getResult();
            workflowStepTypeChoices = result.getWorkflowStepTypeChoices();

            if(workflowStepTypeChoice == null) {
                workflowStepTypeChoice = workflowStepTypeChoices.getDefaultValue();
            }
        }
    }
    
    public void setWorkflowName(String workflowName) {
        this.workflowName = workflowName;
    }
    
    public String getWorkflowName() {
        return workflowName;
    }
    
    public void setWorkflowStepName(String workflowStepName) {
        this.workflowStepName = workflowStepName;
    }
    
    public String getWorkflowStepName() {
        return workflowStepName;
    }
    
    public List<LabelValueBean> getWorkflowStepTypeChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;
        
        setupWorkflowStepTypeChoices();
        if(workflowStepTypeChoices != null) {
            choices = convertChoices(workflowStepTypeChoices);
        }
        
        return choices;
    }
    
    public void setWorkflowStepTypeChoice(String workflowStepTypeChoice) {
        this.workflowStepTypeChoice = workflowStepTypeChoice;
    }
    
    public String getWorkflowStepTypeChoice()
            throws NamingException {
        setupWorkflowStepTypeChoices();
        
        return workflowStepTypeChoice;
    }
    
    public Boolean getIsDefault() {
        return isDefault;
    }
    
    public void setIsDefault(Boolean isDefault) {
        this.isDefault = isDefault;
    }
    
    public String getSortOrder() {
        return sortOrder;
    }
    
    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
    
    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        super.reset(mapping, request);
        
        isDefault = false;
    }
    
}
