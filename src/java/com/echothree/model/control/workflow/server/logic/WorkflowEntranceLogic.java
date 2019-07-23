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

package com.echothree.model.control.workflow.server.logic;

import com.echothree.model.control.workflow.common.exception.UnknownWorkflowNameException;
import com.echothree.model.control.workflow.server.WorkflowControl;
import com.echothree.model.data.workflow.server.entity.Workflow;
import com.echothree.model.data.workflow.server.entity.WorkflowEntrance;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.Session;

public class WorkflowEntranceLogic
        extends BaseLogic {

    private WorkflowEntranceLogic() {
        super();
    }
    
    private static class WorkflowEntranceLogicHolder {
        static WorkflowEntranceLogic instance = new WorkflowEntranceLogic();
    }
    
    public static WorkflowEntranceLogic getInstance() {
        return WorkflowEntranceLogicHolder.instance;
    }
    
    public WorkflowEntrance getWorkflowEntranceByName(final ExecutionErrorAccumulator eea, final Workflow workflow,
            final String workflowEntranceName) {
        var workflowControl = (WorkflowControl)Session.getModelController(WorkflowControl.class);
        WorkflowEntrance workflowEntrance = workflowControl.getWorkflowEntranceByName(workflow, workflowEntranceName);

        if(workflowEntrance == null) {
            handleExecutionError(UnknownWorkflowNameException.class, eea, ExecutionErrors.UnknownWorkflowEntranceName.name(),
                    workflow.getLastDetail().getWorkflowName(), workflowEntranceName);
        }

        return workflowEntrance;
    }

    public WorkflowEntrance getWorkflowEntranceByName(final ExecutionErrorAccumulator eea, final String workflowName,
            final String workflowEntranceName) {
        Workflow workflow = WorkflowLogic.getInstance().getWorkflowByName(eea, workflowName);
        WorkflowEntrance workflowEntrance = null;
        
        if(eea == null || !eea.hasExecutionErrors()) {
            workflowEntrance = getWorkflowEntranceByName(eea, workflow, workflowEntranceName);
        }
        
        return workflowEntrance;
    }

}
