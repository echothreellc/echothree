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

package com.echothree.ui.web.main.action.configuration.workflowdestinationstep;

import com.echothree.ui.web.main.framework.MainBaseDeleteActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;

@SproutForm(name="WorkflowDestinationStepDelete")
public class DeleteActionForm
        extends MainBaseDeleteActionForm {
    
    private String workflowName;
    private String workflowStepName;
    private String workflowDestinationName;
    private String destinationWorkflowName;
    private String destinationWorkflowStepName;

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
     * Returns the workflowStepName.
     * @return the workflowStepName
     */
    public String getWorkflowStepName() {
        return workflowStepName;
    }

    /**
     * Sets the workflowStepName.
     * @param workflowStepName the workflowStepName to set
     */
    public void setWorkflowStepName(String workflowStepName) {
        this.workflowStepName = workflowStepName;
    }

    /**
     * Returns the workflowDestinationName.
     * @return the workflowDestinationName
     */
    public String getWorkflowDestinationName() {
        return workflowDestinationName;
    }

    /**
     * Sets the workflowDestinationName.
     * @param workflowDestinationName the workflowDestinationName to set
     */
    public void setWorkflowDestinationName(String workflowDestinationName) {
        this.workflowDestinationName = workflowDestinationName;
    }

    /**
     * Returns the destinationWorkflowName.
     * @return the destinationWorkflowName
     */
    public String getDestinationWorkflowName() {
        return destinationWorkflowName;
    }

    /**
     * Sets the destinationWorkflowName.
     * @param destinationWorkflowName the destinationWorkflowName to set
     */
    public void setDestinationWorkflowName(String destinationWorkflowName) {
        this.destinationWorkflowName = destinationWorkflowName;
    }

    /**
     * Returns the destinationWorkflowStepName.
     * @return the destinationWorkflowStepName
     */
    public String getDestinationWorkflowStepName() {
        return destinationWorkflowStepName;
    }

    /**
     * Sets the destinationWorkflowStepName.
     * @param destinationWorkflowStepName the destinationWorkflowStepName to set
     */
    public void setDestinationWorkflowStepName(String destinationWorkflowStepName) {
        this.destinationWorkflowStepName = destinationWorkflowStepName;
    }
    
}
