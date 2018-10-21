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

package com.echothree.model.control.workflow.server.logic;

import com.echothree.model.control.core.server.CoreControl;
import com.echothree.model.control.workflow.common.exception.UnknownWorkflowNameException;
import com.echothree.model.control.workflow.server.WorkflowControl;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.workflow.server.entity.Workflow;
import com.echothree.model.data.workflow.server.entity.WorkflowDestination;
import com.echothree.model.data.workflow.server.entity.WorkflowDestinationStep;
import com.echothree.model.data.workflow.server.entity.WorkflowEntityStatus;
import com.echothree.model.data.workflow.server.entity.WorkflowStep;
import com.echothree.model.data.workflow.server.entity.WorkflowStepDetail;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.remote.persistence.BasePK;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.BaseEntity;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class WorkflowLogic
        extends BaseLogic {
    
    private WorkflowLogic() {
        super();
    }
    
    private static class WorkflowTriggerLogicHolder {
        static WorkflowLogic instance = new WorkflowLogic();
    }
    
    public static WorkflowLogic getInstance() {
        return WorkflowTriggerLogicHolder.instance;
    }
    
    public Set<WorkflowEntityStatus> isEntityInWorkflowSteps(final ExecutionErrorAccumulator eea, final String workflowName, final BaseEntity baseEntity,
            String... workflowStepNames) {
        return isEntityInWorkflowSteps(eea, workflowName, baseEntity, EntityPermission.READ_ONLY, workflowStepNames);
    }
    
    public Set<WorkflowEntityStatus> isEntityInWorkflowStepsForUpdate(final ExecutionErrorAccumulator eea, final String workflowName, final BaseEntity baseEntity,
            String... workflowStepNames) {
        return isEntityInWorkflowSteps(eea, workflowName, baseEntity, EntityPermission.READ_WRITE, workflowStepNames);
    }
    
    public Set<WorkflowEntityStatus> isEntityInWorkflowSteps(final ExecutionErrorAccumulator eea, final String workflowName, final BaseEntity baseEntity,
            EntityPermission entityPermission, String... workflowStepNames) {
        return isEntityInWorkflowSteps(eea, workflowName, baseEntity.getPrimaryKey(), entityPermission, workflowStepNames);
    }

    public Set<WorkflowEntityStatus> isEntityInWorkflowSteps(final ExecutionErrorAccumulator eea, final String workflowName, final BasePK pk,
            String... workflowStepNames) {
        return isEntityInWorkflowSteps(eea, workflowName, pk, EntityPermission.READ_ONLY, workflowStepNames);
    }
    
    public Set<WorkflowEntityStatus> isEntityInWorkflowStepsForUpdate(final ExecutionErrorAccumulator eea, final String workflowName, final BasePK pk,
            String... workflowStepNames) {
        return isEntityInWorkflowSteps(eea, workflowName, pk, EntityPermission.READ_WRITE, workflowStepNames);
    }
    
    public Set<WorkflowEntityStatus> isEntityInWorkflowSteps(final ExecutionErrorAccumulator eea, final String workflowName, final BasePK pk,
            EntityPermission entityPermission, String... workflowStepNames) {
        CoreControl coreControl = (CoreControl)Session.getModelController(CoreControl.class);
        EntityInstance entityInstance = coreControl.getEntityInstanceByBasePK(pk);
        
        return isEntityInWorkflowSteps(eea, workflowName, entityInstance, entityPermission, workflowStepNames);
    }

    public Set<WorkflowEntityStatus> isEntityInWorkflowSteps(final ExecutionErrorAccumulator eea, final String workflowName, final EntityInstance entityInstance,
            final String... workflowStepNames) {
        return isEntityInWorkflowSteps(eea, workflowName, entityInstance, EntityPermission.READ_ONLY, workflowStepNames);
    }
    
    public Set<WorkflowEntityStatus> isEntityInWorkflowStepsForUpdate(final ExecutionErrorAccumulator eea, final String workflowName, final EntityInstance entityInstance,
            final String... workflowStepNames) {
        return isEntityInWorkflowSteps(eea, workflowName, entityInstance, EntityPermission.READ_WRITE, workflowStepNames);
    }
    
    private Set<WorkflowEntityStatus> isEntityInWorkflowSteps(final ExecutionErrorAccumulator eea, final String workflowName, final EntityInstance entityInstance,
            final EntityPermission entityPermission, final String... workflowStepNames) {
        Workflow workflow = getWorkflowByName(eea, workflowName);
        Set<WorkflowEntityStatus> result = new HashSet<>();
        
        if(!hasExecutionErrors(eea)) {
            WorkflowControl workflowControl = (WorkflowControl)Session.getModelController(WorkflowControl.class);
            List<WorkflowEntityStatus> workflowEntityStatuses = workflowControl.getWorkflowEntityStatusesByEntityInstance(workflow, entityInstance, entityPermission);
            Set<String> possibleWorkflowStepNames = new HashSet<>(workflowStepNames.length);
            
            possibleWorkflowStepNames.addAll(Arrays.asList(workflowStepNames));
            
            workflowEntityStatuses.stream().forEach((workflowEntityStatus) -> {
                WorkflowStepDetail workflowStepDetail = workflowEntityStatus.getWorkflowStep().getLastDetail();
                if (workflowStepDetail.getWorkflow().equals(workflow)) {
                    String workflowStepName = workflowStepDetail.getWorkflowStepName();
                    if (possibleWorkflowStepNames.contains(workflowStepName)) {
                        result.add(workflowEntityStatus);
                    }
                }
            });

        }

        return result;
    }
    
    public boolean isWorkflowStepInSet(Set<WorkflowEntityStatus> workflowEntityStatuses, String workflowStepName) {
        boolean result = false;
        
        for(WorkflowEntityStatus workflowEntityStatus : workflowEntityStatuses) {
            if(result |= workflowEntityStatus.getWorkflowStep().getLastDetail().getWorkflowStepName().equals(workflowStepName)) {
                break;
            }
        }
        
        return result;
    }

    public Workflow getWorkflowByName(final ExecutionErrorAccumulator eea, final String workflowName) {
        WorkflowControl workflowControl = (WorkflowControl)Session.getModelController(WorkflowControl.class);
        Workflow workflow = workflowControl.getWorkflowByName(workflowName);

        if(workflow == null) {
            handleExecutionError(UnknownWorkflowNameException.class, eea, ExecutionErrors.UnknownWorkflowName.name(), workflowName);
        }

        return workflow;
    }

    public WorkflowStep getWorkflowStepByName(final ExecutionErrorAccumulator eea, final Workflow workflow, final String workflowStepName) {
        WorkflowControl workflowControl = (WorkflowControl)Session.getModelController(WorkflowControl.class);
        WorkflowStep workflowStep = workflowControl.getWorkflowStepByName(workflow, workflowStepName);

        if(workflowStep == null) {
            handleExecutionError(UnknownWorkflowNameException.class, eea, ExecutionErrors.UnknownWorkflowStepName.name(), workflow.getLastDetail().getWorkflowName(),
                    workflowStepName);
        }

        return workflowStep;
    }

    public WorkflowStep getWorkflowStepByName(final ExecutionErrorAccumulator eea, final String workflowName, final String workflowStepName) {
        Workflow workflow = getWorkflowByName(eea, workflowName);
        WorkflowStep workflowStep = null;
        
        if(eea == null || !eea.hasExecutionErrors()) {
            workflowStep = getWorkflowStepByName(eea, workflow, workflowStepName);
        }
        
        return workflowStep;
    }

    public WorkflowDestination getWorkflowDestinationByName(final ExecutionErrorAccumulator eea, final WorkflowStep workflowStep, final String workflowDestinationName) {
        WorkflowControl workflowControl = (WorkflowControl)Session.getModelController(WorkflowControl.class);
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
        WorkflowStep workflowStep = getWorkflowStepByName(eea, workflow, workflowStepName);
        WorkflowDestination workflowDestination = null;
        
        if(eea == null || !eea.hasExecutionErrors()) {
            workflowDestination = getWorkflowDestinationByName(eea, workflowStep, workflowDestinationName);
        }
        
        return workflowDestination;
    }

    public WorkflowDestination getWorkflowDestinationByName(final ExecutionErrorAccumulator eea, final String workflowName, final String workflowStepName,
            final String workflowDestinationName) {
        WorkflowStep workflowStep = getWorkflowStepByName(eea, workflowName, workflowStepName);
        WorkflowDestination workflowDestination = null;
        
        if(eea == null || !eea.hasExecutionErrors()) {
            workflowDestination = getWorkflowDestinationByName(eea, workflowStep, workflowDestinationName);
        }
        
        return workflowDestination;
    }

    public Set<WorkflowStep> getWorkflowDestinationStepsAsSet(final WorkflowDestination workflowDestination) {
        WorkflowControl workflowControl = (WorkflowControl)Session.getModelController(WorkflowControl.class);
        List<WorkflowDestinationStep> workflowDestinationSteps = workflowControl.getWorkflowDestinationStepsByWorkflowDestination(workflowDestination);
        Set<WorkflowStep> workflowSteps = new HashSet<>(workflowDestinationSteps.size());
        
        workflowDestinationSteps.stream().forEach((workflowDestinationStep) -> {
            workflowSteps.add(workflowDestinationStep.getWorkflowStep());
        });
        
        return workflowSteps;
    }
    
    public Map<String, Set<String>> getWorkflowDestinationsAsMap(final WorkflowDestination workflowDestination) {
        WorkflowControl workflowControl = (WorkflowControl)Session.getModelController(WorkflowControl.class);
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
