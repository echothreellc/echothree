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
import com.echothree.model.control.workflow.common.transfer.WorkflowDestinationTransfer;
import com.echothree.model.control.workflow.common.transfer.WorkflowEntranceTransfer;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.model.data.workflow.server.entity.WorkflowDestination;
import com.echothree.util.common.form.TransferProperties;

public class WorkflowDestinationTransferCache
        extends BaseWorkflowTransferCache<WorkflowDestination, WorkflowDestinationTransfer> {
    
    TransferProperties transferProperties;
    boolean filterWorkflowStep;
    boolean filterWorkflowDestinationName;
    boolean filterIsDefault;
    boolean filterSortOrder;
    boolean filterDescription;
    boolean filterEntityInstance;
    
    /** Creates a new instance of WorkflowDestinationTransferCache */
    public WorkflowDestinationTransferCache(WorkflowControl workflowControl) {
        super(workflowControl);
        
        transferProperties = session.getTransferProperties();
        if(transferProperties != null) {
            var properties = transferProperties.getProperties(WorkflowEntranceTransfer.class);
            
            if(properties != null) {
                filterWorkflowStep = !properties.contains(WorkflowProperties.WORKFLOW_STEP);
                filterWorkflowDestinationName = !properties.contains(WorkflowProperties.WORKFLOW_DESTINATION_NAME);
                filterIsDefault = !properties.contains(WorkflowProperties.IS_DEFAULT);
                filterSortOrder = !properties.contains(WorkflowProperties.SORT_ORDER);
                filterDescription = !properties.contains(WorkflowProperties.DESCRIPTION);
                filterEntityInstance = !properties.contains(WorkflowProperties.ENTITY_INSTANCE);
            }
        }
        
        setIncludeEntityInstance(!filterEntityInstance);
    }
    
    public WorkflowDestinationTransfer getWorkflowDestinationTransfer(UserVisit userVisit, WorkflowDestination workflowDestination) {
        var workflowDestinationTransfer = get(workflowDestination);
        
        if(workflowDestinationTransfer == null) {
            var workflowDestinationDetail = workflowDestination.getLastDetail();
            var workflowStep = filterWorkflowStep ? null : workflowControl.getWorkflowStepTransfer(userVisit, workflowDestinationDetail.getWorkflowStep());
            var workflowDestinationName = filterWorkflowDestinationName ? null : workflowDestinationDetail.getWorkflowDestinationName();
            var isDefault = filterIsDefault ? null : workflowDestinationDetail.getIsDefault();
            var sortOrder = filterSortOrder ? null : workflowDestinationDetail.getSortOrder();
            var description = filterDescription ? null : workflowControl.getBestWorkflowDestinationDescription(workflowDestination, getLanguage(userVisit));
            
            workflowDestinationTransfer = new WorkflowDestinationTransfer(workflowStep, workflowDestinationName, isDefault,
                    sortOrder, description);
            put(userVisit, workflowDestination, workflowDestinationTransfer);
        }
        
        return workflowDestinationTransfer;
    }
    
}
