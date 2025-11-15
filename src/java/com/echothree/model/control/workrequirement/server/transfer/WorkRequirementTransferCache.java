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

package com.echothree.model.control.workrequirement.server.transfer;

import com.echothree.model.control.core.server.control.EntityInstanceControl;
import com.echothree.model.control.workeffort.server.control.WorkEffortControl;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.control.workrequirement.common.WorkRequirementOptions;
import com.echothree.model.control.workrequirement.common.transfer.WorkRequirementTransfer;
import com.echothree.model.control.workrequirement.common.workflow.WorkRequirementStatusConstants;
import com.echothree.model.control.workrequirement.server.control.WorkRequirementControl;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.model.data.workrequirement.server.entity.WorkRequirement;
import com.echothree.util.common.transfer.ListWrapper;
import com.echothree.util.server.persistence.Session;

public class WorkRequirementTransferCache
        extends BaseWorkRequirementTransferCache<WorkRequirement, WorkRequirementTransfer> {
    
    EntityInstanceControl entityInstanceControl = Session.getModelController(EntityInstanceControl.class);
    WorkEffortControl workEffortControl = Session.getModelController(WorkEffortControl.class);
    WorkflowControl workflowControl = Session.getModelController(WorkflowControl.class);
    WorkRequirementControl workRequirementControl = Session.getModelController(WorkRequirementControl.class);

    boolean includeWorkAssignments;
    boolean includeWorkTimes;
    
    /** Creates a new instance of WorkRequirementTransferCache */
    public WorkRequirementTransferCache() {
        var options = session.getOptions();
        if(options != null) {
            includeWorkAssignments = options.contains(WorkRequirementOptions.WorkRequirementIncludeWorkAssignments);
            includeWorkTimes = options.contains(WorkRequirementOptions.WorkRequirementIncludeWorkTimes);
        }
        
        setIncludeEntityInstance(true);
    }
    
    public WorkRequirementTransfer getWorkRequirementTransfer(UserVisit userVisit, WorkRequirement workRequirement) {
        var workRequirementTransfer = get(workRequirement);
        
        if(workRequirementTransfer == null) {
            var workRequirementDetail = workRequirement.getLastDetail();
            var workRequirementName = workRequirementDetail.getWorkRequirementName();
            var workEffort = workEffortControl.getWorkEffortTransfer(userVisit, workRequirementDetail.getWorkEffort());
            var workRequirementScope = workRequirementControl.getWorkRequirementScopeTransfer(userVisit, workRequirementDetail.getWorkRequirementScope());
            var unformattedStartTime = workRequirementDetail.getStartTime();
            var startTime = formatTypicalDateTime(userVisit, unformattedStartTime);
            var unformattedRequiredTime = workRequirementDetail.getRequiredTime();
            var requiredTime = formatTypicalDateTime(userVisit, unformattedRequiredTime);

            var entityInstance = entityInstanceControl.getEntityInstanceByBasePK(workRequirement.getPrimaryKey());
            var workRequirementStatus = workflowControl.getWorkflowEntityStatusTransferByEntityInstanceUsingNames(userVisit,
                    WorkRequirementStatusConstants.Workflow_WORK_REQUIREMENT_STATUS, entityInstance);

            workRequirementTransfer = new WorkRequirementTransfer(workRequirementName, workEffort, workRequirementScope, unformattedStartTime, startTime,
                    unformattedRequiredTime, requiredTime, workRequirementStatus);
            put(userVisit, workRequirement, workRequirementTransfer);

            if(includeWorkAssignments) {
                workRequirementTransfer.setWorkAssignments(new ListWrapper<>(workRequirementControl.getWorkAssignmentTransfersByWorkRequirement(userVisit, workRequirement)));
            }

            if(includeWorkTimes) {
                workRequirementTransfer.setWorkTimes(new ListWrapper<>(workRequirementControl.getWorkTimeTransfersByWorkRequirement(userVisit, workRequirement)));
            }
        }
        
        return workRequirementTransfer;
    }
    
}
