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

package com.echothree.ui.web.main.action.configuration.workflowentrancestep;

import com.echothree.ui.web.main.framework.MainBaseDeleteActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;

@SproutForm(name="WorkflowEntranceStepDelete")
public class DeleteActionForm
        extends MainBaseDeleteActionForm {
    
    private String workflowName;
    private String workflowEntranceName;
    private String entranceWorkflowName;
    private String entranceWorkflowStepName;

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

    /**
     * Returns the entranceWorkflowStepName.
     * @return the entranceWorkflowStepName
     */
    public String getEntranceWorkflowStepName() {
        return entranceWorkflowStepName;
    }

    /**
     * Sets the entranceWorkflowStepName.
     * @param entranceWorkflowStepName the entranceWorkflowStepName to set
     */
    public void setEntranceWorkflowStepName(String entranceWorkflowStepName) {
        this.entranceWorkflowStepName = entranceWorkflowStepName;
    }

}
