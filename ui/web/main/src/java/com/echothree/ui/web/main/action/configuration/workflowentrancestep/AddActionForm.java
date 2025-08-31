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

package com.echothree.ui.web.main.action.configuration.workflowentrancestep;

import com.echothree.control.user.workflow.common.WorkflowUtil;
import com.echothree.control.user.workflow.common.result.GetWorkflowStepChoicesResult;
import com.echothree.model.control.workflow.common.choice.WorkflowStepChoicesBean;
import com.echothree.view.client.web.struts.BaseActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import java.util.List;
import javax.naming.NamingException;
import org.apache.struts.util.LabelValueBean;

@SproutForm(name="WorkflowEntranceStepAdd")
public class AddActionForm
        extends BaseActionForm {
    
    private WorkflowStepChoicesBean entranceWorkflowStepChoices;

    private String workflowName;
    private String workflowEntranceName;
    private String entranceWorkflowName;
    private String entranceWorkflowStepChoice;

    private void setupEntranceWorkflowStepChoices()
            throws NamingException {
        if(entranceWorkflowStepChoices == null) {
            var form = WorkflowUtil.getHome().getGetWorkflowStepChoicesForm();

            form.setWorkflowName(entranceWorkflowName);
            form.setDefaultWorkflowStepChoice(entranceWorkflowStepChoice);
            form.setAllowNullChoice(String.valueOf(false));

            var commandResult = WorkflowUtil.getHome().getWorkflowStepChoices(userVisitPK, form);
            var executionResult = commandResult.getExecutionResult();
            var result = (GetWorkflowStepChoicesResult)executionResult.getResult();
            entranceWorkflowStepChoices = result.getWorkflowStepChoices();

            if(entranceWorkflowStepChoice == null) {
                entranceWorkflowStepChoice = entranceWorkflowStepChoices.getDefaultValue();
            }
        }
    }

    /**
     * Returns the workflowName.
     * @return the workflowName
     */
    public String getWorkflowName() {
        return workflowName;
    }

    /**
     * Sets the workflowName.
     * @param workflowName the workflowName to set
     */
    public void setWorkflowName(String workflowName) {
        this.workflowName = workflowName;
    }

    /**
     * Returns the workflowEntranceName.
     * @return the workflowEntranceName
     */
    public String getWorkflowEntranceName() {
        return workflowEntranceName;
    }

    /**
     * Sets the workflowEntranceName.
     * @param workflowEntranceName the workflowEntranceName to set
     */
    public void setWorkflowEntranceName(String workflowEntranceName) {
        this.workflowEntranceName = workflowEntranceName;
    }

    /**
     * Returns the entranceWorkflowName.
     * @return the entranceWorkflowName
     */
    public String getEntranceWorkflowName() {
        return entranceWorkflowName;
    }

    /**
     * Sets the entranceWorkflowName.
     * @param entranceWorkflowName the entranceWorkflowName to set
     */
    public void setEntranceWorkflowName(String entranceWorkflowName) {
        this.entranceWorkflowName = entranceWorkflowName;
    }

    public String getEntranceWorkflowStepChoice()
            throws NamingException {
        setupEntranceWorkflowStepChoices();
        
        return entranceWorkflowStepChoice;
    }

    public void setEntranceWorkflowStepChoice(String entranceWorkflowStepChoice) {
        this.entranceWorkflowStepChoice = entranceWorkflowStepChoice;
    }

    public List<LabelValueBean> getEntranceWorkflowStepChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;

        setupEntranceWorkflowStepChoices();
        if(entranceWorkflowStepChoices != null) {
            choices = convertChoices(entranceWorkflowStepChoices);
        }

        return choices;
    }
    
}
