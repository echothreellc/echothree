// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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

import com.echothree.model.control.core.common.transfer.EntityInstanceTransfer;
import com.echothree.model.control.core.server.CoreControl;
import com.echothree.model.control.workeffort.common.transfer.WorkEffortScopeTransfer;
import com.echothree.model.control.workeffort.server.WorkEffortControl;
import com.echothree.model.control.workflow.common.WorkflowOptions;
import com.echothree.model.control.workflow.common.transfer.WorkflowEntityStatusTransfer;
import com.echothree.model.control.workflow.common.transfer.WorkflowStepTransfer;
import com.echothree.model.control.workflow.server.WorkflowControl;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.model.data.workeffort.server.entity.WorkEffortScope;
import com.echothree.model.data.workflow.server.entity.WorkflowEntityStatus;
import com.echothree.model.data.workflow.server.entity.WorkflowTrigger;
import com.echothree.util.server.persistence.Session;
import java.util.Set;

public class WorkflowEntityStatusTransferCache
        extends BaseWorkflowTransferCache<WorkflowEntityStatus, WorkflowEntityStatusTransfer> {

    WorkEffortControl workEffortControl;
    boolean includeTriggerTime;

    /** Creates a new instance of WorkflowEntityStatusTransferCache */
    public WorkflowEntityStatusTransferCache(UserVisit userVisit, WorkflowControl workflowControl) {
        super(userVisit, workflowControl);

        workEffortControl = (WorkEffortControl)Session.getModelController(WorkEffortControl.class);

        Set<String> options = session.getOptions();
        if(options != null) {
            includeTriggerTime = options.contains(WorkflowOptions.WorkflowEntityStatusIncludeTriggerTime);
        }
    }

    public WorkflowEntityStatusTransfer getWorkflowEntityStatusTransfer(WorkflowEntityStatus workflowEntityStatus) {
        WorkflowEntityStatusTransfer workflowEntityStatusTransfer = get(workflowEntityStatus);

        if(workflowEntityStatusTransfer == null) {
            CoreControl coreControl = (CoreControl)Session.getModelController(CoreControl.class);
            EntityInstanceTransfer entityInstanceTransfer = coreControl.getEntityInstanceTransfer(userVisit, workflowEntityStatus.getEntityInstance(), false, false, false, false, false);
            WorkflowStepTransfer workflowStepTransfer = workflowControl.getWorkflowStepTransfer(userVisit, workflowEntityStatus.getWorkflowStep());
            WorkEffortScope workEffortScope = workflowEntityStatus.getWorkEffortScope();
            WorkEffortScopeTransfer workEffortScopeTransfer = workEffortScope == null ? null : workEffortControl.getWorkEffortScopeTransfer(userVisit, workEffortScope);
            Long unformattedFromTime = workflowEntityStatus.getFromTime();
            String fromTime = formatTypicalDateTime(unformattedFromTime);
            Long unformattedThruTime = workflowEntityStatus.getThruTime();
            String thruTime = formatTypicalDateTime(unformattedThruTime);
            Long unformattedTriggerTime = null;
            String triggerTime = null;

            if(includeTriggerTime) {
                WorkflowTrigger workflowTrigger = workflowControl.getWorkflowTrigger(workflowEntityStatus);

                unformattedTriggerTime = workflowTrigger == null ? null : workflowTrigger.getTriggerTime();
                triggerTime = unformattedTriggerTime == null ? null : formatTypicalDateTime(unformattedTriggerTime);
            }

            workflowEntityStatusTransfer = new WorkflowEntityStatusTransfer(entityInstanceTransfer, workflowStepTransfer, workEffortScopeTransfer,
                    unformattedFromTime, fromTime, unformattedThruTime, thruTime, unformattedThruTime, triggerTime);
            put(workflowEntityStatus, workflowEntityStatusTransfer);
        }

        return workflowEntityStatusTransfer;
    }
}