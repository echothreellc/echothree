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
import com.echothree.model.data.workflow.server.entity.WorkflowDestination;
import com.echothree.model.data.workflow.server.entity.WorkflowDestinationStep;
import com.echothree.model.data.workflow.server.entity.WorkflowStep;
import com.echothree.model.data.workflow.server.entity.WorkflowStepDetail;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.Session;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class WorkflowDestinationLogic
        extends BaseLogic {

    private WorkflowDestinationLogic() {
        super();
    }
    
    private static class WorkflowDestinationLogicHolder {
        static WorkflowDestinationLogic instance = new WorkflowDestinationLogic();
    }
    
    public static WorkflowDestinationLogic getInstance() {
        return WorkflowDestinationLogicHolder.instance;
    }
    
    public WorkflowDestination getWorkflowDestinationByName(final ExecutionErrorAccumulator eea, final WorkflowStep workflowStep, final String workflowDestinationName) {
        var workflowControl = (WorkflowControl)Session.getModelController(WorkflowControl.class);
        WorkflowDestination workflowDestination = workflowControl.getWorkflowDestinationByName(workflowStep, workflowDestinationName);

        if(workflowDestination == null) {
            WorkflowStepDetail workflowStepDetail = workflowStep.getLastDetail();
            
            handleExecutionError(UnknownWorkflowNameException.class, eea, ExecutionErrors.UnknownWorkflowDestinationName.name(),
                    workflowStepDetail.getWorkflow().getLastDetail().getWorkflowName(), workflowStepDetail.getWorkflowStepName(), workflowDestinationName);
        }

        return workflowDestination;
    }

    public WorkflowDestination getWorkflowDestinationByName(final ExecutionErrorAccumulator eea, final Workflow workflow, final String workflowStepName,
            final String workflowDestinationName) {
        WorkflowStep workflowStep = WorkflowStepLogic.getInstance().getWorkflowStepByName(eea, workflow, workflowStepName);
        WorkflowDestination workflowDestination = null;
        
        if(eea == null || !eea.hasExecutionErrors()) {
            workflowDestination = getWorkflowDestinationByName(eea, workflowStep, workflowDestinationName);
        }
        
        return workflowDestination;
    }

    public WorkflowDestination getWorkflowDestinationByName(final ExecutionErrorAccumulator eea, final String workflowName, final String workflowStepName,
            final String workflowDestinationName) {
        WorkflowStep workflowStep = WorkflowStepLogic.getInstance().getWorkflowStepByName(eea, workflowName, workflowStepName);
        WorkflowDestination workflowDestination = null;
        
        if(eea == null || !eea.hasExecutionErrors()) {
            workflowDestination = getWorkflowDestinationByName(eea, workflowStep, workflowDestinationName);
        }
        
        return workflowDestination;
    }

    public Set<WorkflowStep> getWorkflowDestinationStepsAsSet(final WorkflowDestination workflowDestination) {
        var workflowControl = (WorkflowControl)Session.getModelController(WorkflowControl.class);
        List<WorkflowDestinationStep> workflowDestinationSteps = workflowControl.getWorkflowDestinationStepsByWorkflowDestination(workflowDestination);
        Set<WorkflowStep> workflowSteps = new HashSet<>(workflowDestinationSteps.size());
        
        workflowDestinationSteps.stream().forEach((workflowDestinationStep) -> {
            workflowSteps.add(workflowDestinationStep.getWorkflowStep());
        });
        
        return workflowSteps;
    }
    
    public Map<String, Set<String>> getWorkflowDestinationsAsMap(final WorkflowDestination workflowDestination) {
        var workflowControl = (WorkflowControl)Session.getModelController(WorkflowControl.class);
        List<WorkflowDestinationStep> workflowDestinationSteps = workflowControl.getWorkflowDestinationStepsByWorkflowDestination(workflowDestination);
        Map<String, Set<String>> map = new HashMap<>();
        
        workflowDestinationSteps.stream().map((workflowDestinationStep) -> workflowDestinationStep.getWorkflowStep().getLastDetail()).forEach((workflowStepDetail) -> {
            String workflowStepName = workflowStepDetail.getWorkflowStepName();
            String workflowName = workflowStepDetail.getWorkflow().getLastDetail().getWorkflowName();
            
            workflowDestinationMapContainsStep(map, workflowName, workflowStepName, true);
        });
        
        return map;
    }
    
    private boolean workflowDestinationMapContainsStep(final Map<String, Set<String>> map, final String workflowName, final String workflowStepName, final boolean addIt) {
        Set<String> workflowSteps = map.get(workflowName);

        if(workflowSteps == null && addIt) {
            workflowSteps = new HashSet<>();
            map.put(workflowName, workflowSteps);
        }
        
        boolean found = workflowSteps == null ? false : workflowSteps.contains(workflowStepName);

        if(!found && addIt) {
            workflowSteps.add(workflowStepName);
        }
        
        return found;
    }

    public boolean workflowDestinationMapContainsStep(final Map<String, Set<String>> map, String workflowName, String workflowStepName) {
       return workflowDestinationMapContainsStep(map, workflowName, workflowStepName, false);
    }

}
