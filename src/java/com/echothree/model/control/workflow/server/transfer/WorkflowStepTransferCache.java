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

package com.echothree.model.control.workflow.server.transfer;

import com.echothree.model.control.workflow.common.WorkflowProperties;
import com.echothree.model.control.workflow.common.transfer.WorkflowStepTransfer;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.model.data.workflow.server.entity.WorkflowStep;
import com.echothree.util.common.form.TransferProperties;

public class WorkflowStepTransferCache
        extends BaseWorkflowTransferCache<WorkflowStep, WorkflowStepTransfer> {
    
    TransferProperties transferProperties;
    boolean filterWorkflow;
    boolean filterWorkflowStepName;
    boolean filterWorkflowStepType;
    boolean filterIsDefault;
    boolean filterSortOrder;
    boolean filterHasDestinations;
    boolean filterDescription;
    boolean filterEntityInstance;
    
    /** Creates a new instance of WorkflowStepTransferCache */
    public WorkflowStepTransferCache(WorkflowControl workflowControl) {
        super(workflowControl);
        
        transferProperties = session.getTransferProperties();
        if(transferProperties != null) {
            var properties = transferProperties.getProperties(WorkflowStepTransfer.class);
            
            if(properties != null) {
                filterWorkflow = !properties.contains(WorkflowProperties.WORKFLOW);
                filterWorkflowStepName = !properties.contains(WorkflowProperties.WORKFLOW_STEP_NAME);
                filterWorkflowStepType = !properties.contains(WorkflowProperties.WORKFLOW_STEP_TYPE);
                filterIsDefault = !properties.contains(WorkflowProperties.IS_DEFAULT);
                filterSortOrder = !properties.contains(WorkflowProperties.SORT_ORDER);
                filterDescription = !properties.contains(WorkflowProperties.DESCRIPTION);
                filterEntityInstance = !properties.contains(WorkflowProperties.ENTITY_INSTANCE);
            }
        }
        
        setIncludeEntityInstance(!filterEntityInstance);
    }
    
    public WorkflowStepTransfer getWorkflowStepTransfer(WorkflowStep workflowStep) {
        var workflowStepTransfer = get(workflowStep);
        
        if(workflowStepTransfer == null) {
            var workflowStepDetail = workflowStep.getLastDetail();
            var workflow = filterWorkflow ? null : workflowControl.getWorkflowTransfer(userVisit, workflowStepDetail.getWorkflow());
            var workflowStepName = filterWorkflowStepName ? null : workflowStepDetail.getWorkflowStepName();
            var workflowStepType = filterWorkflowStepType ? null : workflowControl.getWorkflowStepTypeTransfer(userVisit, workflowStepDetail.getWorkflowStepType());
            var isDefault = filterIsDefault ? null : workflowStepDetail.getIsDefault();
            var sortOrder = filterSortOrder ? null : workflowStepDetail.getSortOrder();
            var hasDestinations = filterHasDestinations ? null : workflowControl.countWorkflowDestinationsByWorkflowStep(workflowStep) > 0;
            var description = filterDescription ? null : workflowControl.getBestWorkflowStepDescription(workflowStep, getLanguage(userVisit));
            
            workflowStepTransfer = new WorkflowStepTransfer(workflow, workflowStepName, workflowStepType, isDefault, sortOrder,
                    hasDestinations, description);
            put(userVisit, workflowStep, workflowStepTransfer);
        }
        
        return workflowStepTransfer;
    }
    
}
