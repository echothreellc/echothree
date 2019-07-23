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
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.Session;

public class WorkflowLogic
        extends BaseLogic {
    
    private WorkflowLogic() {
        super();
    }
    
    private static class WorkflowLogicHolder {
        static WorkflowLogic instance = new WorkflowLogic();
    }
    
    public static WorkflowLogic getInstance() {
        return WorkflowLogicHolder.instance;
    }

    public Workflow getWorkflowByName(final ExecutionErrorAccumulator eea, final String workflowName) {
        var workflowControl = (WorkflowControl)Session.getModelController(WorkflowControl.class);
        Workflow workflow = workflowControl.getWorkflowByName(workflowName);

        if(workflow == null) {
            handleExecutionError(UnknownWorkflowNameException.class, eea, ExecutionErrors.UnknownWorkflowName.name(), workflowName);
        }

        return workflow;
    }

}
