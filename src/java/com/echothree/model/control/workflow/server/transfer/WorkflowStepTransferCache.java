// --------------------------------------------------------------------------------
// Copyright 2002-2022 Echo Three, LLC
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
import com.echothree.model.control.workflow.common.transfer.WorkflowStepTypeTransfer;
import com.echothree.model.control.workflow.common.transfer.WorkflowTransfer;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.model.data.workflow.server.entity.WorkflowStep;
import com.echothree.model.data.workflow.server.entity.WorkflowStepDetail;
import com.echothree.util.common.form.TransferProperties;
import java.util.Set;

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
    public WorkflowStepTransferCache(UserVisit userVisit, WorkflowControl workflowControl) {
        super(userVisit, workflowControl);
        
        transferProperties = session.getTransferProperties();
        if(transferProperties != null) {
            Set<String> properties = transferProperties.getProperties(WorkflowStepTransfer.class);
            
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
        WorkflowStepTransfer workflowStepTransfer = get(workflowStep);
        
        if(workflowStepTransfer == null) {
            WorkflowStepDetail workflowStepDetail = workflowStep.getLastDetail();
            WorkflowTransfer workflow = filterWorkflow ? null : workflowControl.getWorkflowTransfer(userVisit, workflowStepDetail.getWorkflow());
            String workflowStepName = filterWorkflowStepName ? null : workflowStepDetail.getWorkflowStepName();
            WorkflowStepTypeTransfer workflowStepType = filterWorkflowStepType ? null : workflowControl.getWorkflowStepTypeTransfer(userVisit, workflowStepDetail.getWorkflowStepType());
            Boolean isDefault = filterIsDefault ? null : workflowStepDetail.getIsDefault();
            Integer sortOrder = filterSortOrder ? null : workflowStepDetail.getSortOrder();
            Boolean hasDestinations = filterHasDestinations ? null : workflowControl.countWorkflowDestinationsByWorkflowStep(workflowStep) > 0;
            String description = filterDescription ? null : workflowControl.getBestWorkflowStepDescription(workflowStep, getLanguage());
            
            workflowStepTransfer = new WorkflowStepTransfer(workflow, workflowStepName, workflowStepType, isDefault, sortOrder,
                    hasDestinations, description);
            put(workflowStep, workflowStepTransfer);
        }
        
        return workflowStepTransfer;
    }
    
}
