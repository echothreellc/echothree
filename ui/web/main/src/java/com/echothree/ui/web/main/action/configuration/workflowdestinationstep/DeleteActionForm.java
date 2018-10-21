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
     * @return the workflowStepName
     */
    public String getWorkflowStepName() {
        return workflowStepName;
    }

    /**
     * @param workflowStepName the workflowStepName to set
     */
    public void setWorkflowStepName(String workflowStepName) {
        this.workflowStepName = workflowStepName;
    }

    /**
     * @return the workflowDestinationName
     */
    public String getWorkflowDestinationName() {
        return workflowDestinationName;
    }

    /**
     * @param workflowDestinationName the workflowDestinationName to set
     */
    public void setWorkflowDestinationName(String workflowDestinationName) {
        this.workflowDestinationName = workflowDestinationName;
    }

    /**
     * @return the destinationWorkflowName
     */
    public String getDestinationWorkflowName() {
        return destinationWorkflowName;
    }

    /**
     * @param destinationWorkflowName the destinationWorkflowName to set
     */
    public void setDestinationWorkflowName(String destinationWorkflowName) {
        this.destinationWorkflowName = destinationWorkflowName;
    }

    /**
     * @return the destinationWorkflowStepName
     */
    public String getDestinationWorkflowStepName() {
        return destinationWorkflowStepName;
    }

    /**
     * @param destinationWorkflowStepName the destinationWorkflowStepName to set
     */
    public void setDestinationWorkflowStepName(String destinationWorkflowStepName) {
        this.destinationWorkflowStepName = destinationWorkflowStepName;
    }
    
}
