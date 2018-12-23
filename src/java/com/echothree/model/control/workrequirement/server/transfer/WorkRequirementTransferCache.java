// --------------------------------------------------------------------------------
// Copyright 2002-2019 Echo Three, LLC
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

import com.echothree.model.control.core.server.CoreControl;
import com.echothree.model.control.uom.server.UomControl;
import com.echothree.model.control.workeffort.common.transfer.WorkEffortTransfer;
import com.echothree.model.control.workeffort.server.WorkEffortControl;
import com.echothree.model.control.workrequirement.common.workflow.WorkRequirementStatusConstants;
import com.echothree.model.control.workflow.common.transfer.WorkflowEntityStatusTransfer;
import com.echothree.model.control.workflow.server.WorkflowControl;
import com.echothree.model.control.workrequirement.common.WorkRequirementOptions;
import com.echothree.model.control.workrequirement.common.transfer.WorkRequirementScopeTransfer;
import com.echothree.model.control.workrequirement.common.transfer.WorkRequirementTransfer;
import com.echothree.model.control.workrequirement.server.WorkRequirementControl;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.model.data.workrequirement.server.entity.WorkRequirement;
import com.echothree.model.data.workrequirement.server.entity.WorkRequirementDetail;
import com.echothree.util.common.transfer.ListWrapper;
import com.echothree.util.server.persistence.Session;
import java.util.Set;

public class WorkRequirementTransferCache
        extends BaseWorkRequirementTransferCache<WorkRequirement, WorkRequirementTransfer> {
    
    CoreControl coreControl = (CoreControl)Session.getModelController(CoreControl.class);
    UomControl uomControl = (UomControl)Session.getModelController(UomControl.class);
    WorkEffortControl workEffortControl = (WorkEffortControl)Session.getModelController(WorkEffortControl.class);
    WorkflowControl workflowControl = (WorkflowControl)Session.getModelController(WorkflowControl.class);
    boolean includeWorkAssignments;
    boolean includeWorkTimes;
    
    /** Creates a new instance of WorkRequirementTransferCache */
    public WorkRequirementTransferCache(UserVisit userVisit, WorkRequirementControl workRequirementControl) {
        super(userVisit, workRequirementControl);

        Set<String> options = session.getOptions();
        if(options != null) {
            includeWorkAssignments = options.contains(WorkRequirementOptions.WorkRequirementIncludeWorkAssignments);
            includeWorkTimes = options.contains(WorkRequirementOptions.WorkRequirementIncludeWorkTimes);
        }
        
        setIncludeEntityInstance(true);
    }
    
    public WorkRequirementTransfer getWorkRequirementTransfer(WorkRequirement workRequirement) {
        WorkRequirementTransfer workRequirementTransfer = get(workRequirement);
        
        if(workRequirementTransfer == null) {
            WorkRequirementDetail workRequirementDetail = workRequirement.getLastDetail();
            String workRequirementName = workRequirementDetail.getWorkRequirementName();
            WorkEffortTransfer workEffort = workEffortControl.getWorkEffortTransfer(userVisit, workRequirementDetail.getWorkEffort());
            WorkRequirementScopeTransfer workRequirementScope = workRequirementControl.getWorkRequirementScopeTransfer(userVisit, workRequirementDetail.getWorkRequirementScope());
            Long unformattedStartTime = workRequirementDetail.getStartTime();
            String startTime = formatTypicalDateTime(unformattedStartTime);
            Long unformattedRequiredTime = workRequirementDetail.getRequiredTime();
            String requiredTime = formatTypicalDateTime(unformattedRequiredTime);

            EntityInstance entityInstance = coreControl.getEntityInstanceByBasePK(workRequirement.getPrimaryKey());
            WorkflowEntityStatusTransfer workRequirementStatus = workflowControl.getWorkflowEntityStatusTransferByEntityInstanceUsingNames(userVisit,
                    WorkRequirementStatusConstants.Workflow_WORK_REQUIREMENT_STATUS, entityInstance);

            workRequirementTransfer = new WorkRequirementTransfer(workRequirementName, workEffort, workRequirementScope, unformattedStartTime, startTime,
                    unformattedRequiredTime, requiredTime, workRequirementStatus);
            put(workRequirement, workRequirementTransfer);

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
