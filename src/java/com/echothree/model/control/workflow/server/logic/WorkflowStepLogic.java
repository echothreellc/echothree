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

package com.echothree.model.control.workflow.server.logic;

import com.echothree.model.control.core.server.control.CoreControl;
import com.echothree.model.control.workflow.common.exception.UnknownWorkflowNameException;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.workflow.server.entity.Workflow;
import com.echothree.model.data.workflow.server.entity.WorkflowEntityStatus;
import com.echothree.model.data.workflow.server.entity.WorkflowStep;
import com.echothree.model.data.workflow.server.entity.WorkflowStepDetail;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.BaseEntity;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class WorkflowStepLogic
        extends BaseLogic {

    private WorkflowStepLogic() {
        super();
    }
    
    private static class WorkflowStepLogicHolder {
        static WorkflowStepLogic instance = new WorkflowStepLogic();
    }
    
    public static WorkflowStepLogic getInstance() {
        return WorkflowStepLogicHolder.instance;
    }
    
    public WorkflowStep getWorkflowStepByName(final Class unknownException, final ExecutionErrors unknownExecutionError,
            final ExecutionErrorAccumulator eea, final Workflow workflow, final String workflowStepName) {
        var workflowControl = Session.getModelController(WorkflowControl.class);
        WorkflowStep workflowStep = workflowControl.getWorkflowStepByName(workflow, workflowStepName);

        if(workflowStep == null) {
            handleExecutionError(unknownException, eea, unknownExecutionError.name(), workflow.getLastDetail().getWorkflowName(),
                    workflowStepName);
        }

        return workflowStep;
    }

    public WorkflowStep getWorkflowStepByName(final ExecutionErrorAccumulator eea, final Workflow workflow, final String workflowStepName) {
        return getWorkflowStepByName(UnknownWorkflowNameException.class, ExecutionErrors.UnknownWorkflowStepName, eea,
                workflow, workflowStepName);
    }

    public WorkflowStep getWorkflowStepByName(final Class unknownWorkflowException, final ExecutionErrors unknownWorkflowExecutionError,
            final Class unknownWorkflowStepException, final ExecutionErrors unknownWorkflowStepExecutionError,
            final ExecutionErrorAccumulator eea, final String workflowName, final String workflowStepName) {
        Workflow workflow = WorkflowLogic.getInstance().getWorkflowByName(unknownWorkflowException, unknownWorkflowExecutionError,
                eea, workflowName);
        WorkflowStep workflowStep = null;

        if(eea == null || !eea.hasExecutionErrors()) {
            workflowStep = getWorkflowStepByName(unknownWorkflowStepException, unknownWorkflowStepExecutionError, eea,
                    workflow, workflowStepName);
        }

        return workflowStep;
    }

    public WorkflowStep getWorkflowStepByName(final ExecutionErrorAccumulator eea, final String workflowName, final String workflowStepName) {
        Workflow workflow = WorkflowLogic.getInstance().getWorkflowByName(eea, workflowName);
        WorkflowStep workflowStep = null;

        if(eea == null || !eea.hasExecutionErrors()) {
            workflowStep = getWorkflowStepByName(eea, workflow, workflowStepName);
        }

        return workflowStep;
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
        var coreControl = Session.getModelController(CoreControl.class);
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
        Workflow workflow = WorkflowLogic.getInstance().getWorkflowByName(eea, workflowName);
        Set<WorkflowEntityStatus> result = new HashSet<>();
        
        if(!hasExecutionErrors(eea)) {
            var workflowControl = Session.getModelController(WorkflowControl.class);
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

}
