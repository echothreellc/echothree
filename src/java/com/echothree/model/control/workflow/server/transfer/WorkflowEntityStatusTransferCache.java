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

package com.echothree.model.control.workflow.server.transfer;

import com.echothree.model.control.core.server.control.EntityInstanceControl;
import com.echothree.model.control.workeffort.server.control.WorkEffortControl;
import com.echothree.model.control.workflow.common.WorkflowOptions;
import com.echothree.model.control.workflow.common.transfer.WorkflowEntityStatusTransfer;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.model.data.workflow.server.entity.WorkflowEntityStatus;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class WorkflowEntityStatusTransferCache
        extends BaseWorkflowTransferCache<WorkflowEntityStatus, WorkflowEntityStatusTransfer> {

    WorkEffortControl workEffortControl = Session.getModelController(WorkEffortControl.class);
    WorkflowControl workflowControl = Session.getModelController(WorkflowControl.class);

    boolean includeTriggerTime;

    /** Creates a new instance of WorkflowEntityStatusTransferCache */
    protected WorkflowEntityStatusTransferCache() {
        super();

        var options = session.getOptions();
        if(options != null) {
            includeTriggerTime = options.contains(WorkflowOptions.WorkflowEntityStatusIncludeTriggerTime);
        }
    }

    public WorkflowEntityStatusTransfer getWorkflowEntityStatusTransfer(UserVisit userVisit, WorkflowEntityStatus workflowEntityStatus) {
        var workflowEntityStatusTransfer = get(workflowEntityStatus);

        if(workflowEntityStatusTransfer == null) {
            var entityInstanceControl = Session.getModelController(EntityInstanceControl.class);
            var entityInstanceTransfer = entityInstanceControl.getEntityInstanceTransfer(userVisit, workflowEntityStatus.getEntityInstance(), false, false, false, false);
            var workflowStepTransfer = workflowControl.getWorkflowStepTransfer(userVisit, workflowEntityStatus.getWorkflowStep());
            var workEffortScope = workflowEntityStatus.getWorkEffortScope();
            var workEffortScopeTransfer = workEffortScope == null ? null : workEffortControl.getWorkEffortScopeTransfer(userVisit, workEffortScope);
            var unformattedFromTime = workflowEntityStatus.getFromTime();
            var fromTime = formatTypicalDateTime(userVisit, unformattedFromTime);
            var unformattedThruTime = workflowEntityStatus.getThruTime();
            var thruTime = formatTypicalDateTime(userVisit, unformattedThruTime);
            Long unformattedTriggerTime;
            String triggerTime = null;

            if(includeTriggerTime) {
                var workflowTrigger = workflowControl.getWorkflowTrigger(workflowEntityStatus);

                unformattedTriggerTime = workflowTrigger == null ? null : workflowTrigger.getTriggerTime();
                triggerTime = unformattedTriggerTime == null ? null : formatTypicalDateTime(userVisit, unformattedTriggerTime);
            }

            workflowEntityStatusTransfer = new WorkflowEntityStatusTransfer(entityInstanceTransfer, workflowStepTransfer, workEffortScopeTransfer,
                    unformattedFromTime, fromTime, unformattedThruTime, thruTime, unformattedThruTime, triggerTime);
            put(userVisit, workflowEntityStatus, workflowEntityStatusTransfer);
        }

        return workflowEntityStatusTransfer;
    }
}