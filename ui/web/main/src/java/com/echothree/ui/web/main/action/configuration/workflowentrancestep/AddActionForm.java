// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
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
import com.echothree.control.user.workflow.common.form.GetWorkflowStepChoicesForm;
import com.echothree.control.user.workflow.common.result.GetWorkflowStepChoicesResult;
import com.echothree.model.control.workflow.common.choice.WorkflowStepChoicesBean;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.ExecutionResult;
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

    private void setupEntranceWorkflowStepChoices() {
        if(entranceWorkflowStepChoices == null) {
            try {
                GetWorkflowStepChoicesForm form = WorkflowUtil.getHome().getGetWorkflowStepChoicesForm();

                form.setWorkflowName(entranceWorkflowName);
                form.setDefaultWorkflowStepChoice(entranceWorkflowStepChoice);
                form.setAllowNullChoice(Boolean.FALSE.toString());

                CommandResult commandResult = WorkflowUtil.getHome().getWorkflowStepChoices(userVisitPK, form);
                ExecutionResult executionResult = commandResult.getExecutionResult();
                GetWorkflowStepChoicesResult result = (GetWorkflowStepChoicesResult)executionResult.getResult();
                entranceWorkflowStepChoices = result.getWorkflowStepChoices();

                if(entranceWorkflowStepChoice == null) {
                    entranceWorkflowStepChoice = entranceWorkflowStepChoices.getDefaultValue();
                }
            } catch (NamingException ne) {
                ne.printStackTrace();
                // failed, entranceWorkflowStepChoices remains null, no default
            }
        }
    }

    /**
     * @return the workflowName
     */
    public String getWorkflowName() {
        return workflowName;
    }

    /**
     * @param workflowName the workflowName to set
     */
    public void setWorkflowName(String workflowName) {
        this.workflowName = workflowName;
    }

    /**
     * @return the workflowEntranceName
     */
    public String getWorkflowEntranceName() {
        return workflowEntranceName;
    }

    /**
     * @param workflowEntranceName the workflowEntranceName to set
     */
    public void setWorkflowEntranceName(String workflowEntranceName) {
        this.workflowEntranceName = workflowEntranceName;
    }

    /**
     * @return the entranceWorkflowName
     */
    public String getEntranceWorkflowName() {
        return entranceWorkflowName;
    }

    /**
     * @param entranceWorkflowName the entranceWorkflowName to set
     */
    public void setEntranceWorkflowName(String entranceWorkflowName) {
        this.entranceWorkflowName = entranceWorkflowName;
    }

    public String getEntranceWorkflowStepChoice() {
        setupEntranceWorkflowStepChoices();
        
        return entranceWorkflowStepChoice;
    }

    public void setEntranceWorkflowStepChoice(String entranceWorkflowStepChoice) {
        this.entranceWorkflowStepChoice = entranceWorkflowStepChoice;
    }

    public List<LabelValueBean> getEntranceWorkflowStepChoices() {
        List<LabelValueBean> choices = null;

        setupEntranceWorkflowStepChoices();
        if(entranceWorkflowStepChoices != null) {
            choices = convertChoices(entranceWorkflowStepChoices);
        }

        return choices;
    }
    
}
