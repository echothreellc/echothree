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

package com.echothree.model.control.workflow.server.transfer;

import com.echothree.model.control.workflow.common.WorkflowProperties;
import com.echothree.model.control.workflow.common.transfer.WorkflowDestinationTransfer;
import com.echothree.model.control.workflow.common.transfer.WorkflowEntranceTransfer;
import com.echothree.model.control.workflow.common.transfer.WorkflowStepTransfer;
import com.echothree.model.control.workflow.server.WorkflowControl;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.model.data.workflow.server.entity.WorkflowDestination;
import com.echothree.model.data.workflow.server.entity.WorkflowDestinationDetail;
import com.echothree.util.common.form.TransferProperties;
import java.util.Set;

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
    public WorkflowDestinationTransferCache(UserVisit userVisit, WorkflowControl workflowControl) {
        super(userVisit, workflowControl);
        
        transferProperties = session.getTransferProperties();
        if(transferProperties != null) {
            Set<String> properties = transferProperties.getProperties(WorkflowEntranceTransfer.class);
            
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
    
    public WorkflowDestinationTransfer getWorkflowDestinationTransfer(WorkflowDestination workflowDestination) {
        WorkflowDestinationTransfer workflowDestinationTransfer = get(workflowDestination);
        
        if(workflowDestinationTransfer == null) {
            WorkflowDestinationDetail workflowDestinationDetail = workflowDestination.getLastDetail();
            WorkflowStepTransfer workflowStep = filterWorkflowStep ? null : workflowControl.getWorkflowStepTransfer(userVisit, workflowDestinationDetail.getWorkflowStep());
            String workflowDestinationName = filterWorkflowDestinationName ? null : workflowDestinationDetail.getWorkflowDestinationName();
            Boolean isDefault = filterIsDefault ? null : workflowDestinationDetail.getIsDefault();
            Integer sortOrder = filterSortOrder ? null : workflowDestinationDetail.getSortOrder();
            String description = filterDescription ? null : workflowControl.getBestWorkflowDestinationDescription(workflowDestination, getLanguage());
            
            workflowDestinationTransfer = new WorkflowDestinationTransfer(workflowStep, workflowDestinationName, isDefault,
                    sortOrder, description);
            put(workflowDestination, workflowDestinationTransfer);
        }
        
        return workflowDestinationTransfer;
    }
    
}
