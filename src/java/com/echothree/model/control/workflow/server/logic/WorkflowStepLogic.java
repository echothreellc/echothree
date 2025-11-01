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

package com.echothree.model.control.workflow.server.logic;

import com.echothree.control.user.workflow.common.spec.WorkflowStepUniversalSpec;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.server.control.EntityInstanceControl;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.workflow.common.exception.MissingRequiredWorkflowNameException;
import com.echothree.model.control.workflow.common.exception.UnknownDefaultWorkflowStepException;
import com.echothree.model.control.workflow.common.exception.UnknownWorkflowStepNameException;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.workflow.server.entity.Workflow;
import com.echothree.model.data.workflow.server.entity.WorkflowEntityStatus;
import com.echothree.model.data.workflow.server.entity.WorkflowStep;
import com.echothree.util.common.exception.BaseException;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.BaseEntity;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import com.echothree.util.server.validation.ParameterUtils;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;

@ApplicationScoped
public class WorkflowStepLogic
        extends BaseLogic {

    protected WorkflowStepLogic() {
        super();
    }

    public static WorkflowStepLogic getInstance() {
        return CDI.current().select(WorkflowStepLogic.class).get();
    }

    public WorkflowStep getWorkflowStepByName(final Class<? extends BaseException> unknownWorkflowException, final ExecutionErrors unknownWorkflowExecutionError,
            final Class<? extends BaseException>  unknownWorkflowStepException, final ExecutionErrors unknownWorkflowStepExecutionError,
            final ExecutionErrorAccumulator eea, final String workflowName, final String workflowStepName) {
        var workflow = WorkflowLogic.getInstance().getWorkflowByName(unknownWorkflowException, unknownWorkflowExecutionError,
                eea, workflowName, EntityPermission.READ_ONLY);
        WorkflowStep workflowStep = null;

        if(eea == null || !eea.hasExecutionErrors()) {
            workflowStep = getWorkflowStepByName(unknownWorkflowStepException, unknownWorkflowStepExecutionError, eea,
                    workflow, workflowStepName);
        }

        return workflowStep;
    }

    public WorkflowStep getWorkflowStepByName(final Class<? extends BaseException> unknownException, final ExecutionErrors unknownExecutionError,
            final ExecutionErrorAccumulator eea, final Workflow workflow, final String workflowStepName, EntityPermission entityPermission) {
        var workflowControl = Session.getModelController(WorkflowControl.class);
        var workflowStep = workflowControl.getWorkflowStepByName(workflow, workflowStepName, entityPermission);

        if(workflowStep == null) {
            handleExecutionError(unknownException, eea, unknownExecutionError.name(), workflow.getLastDetail().getWorkflowName(),
                    workflowStepName);
        }

        return workflowStep;
    }

    public WorkflowStep getWorkflowStepByName(final Class<? extends BaseException> unknownException, final ExecutionErrors unknownExecutionError,
            final ExecutionErrorAccumulator eea, final Workflow workflow, final String workflowStepName) {
        return getWorkflowStepByName(unknownException, unknownExecutionError, eea, workflow, workflowStepName, EntityPermission.READ_ONLY);
    }

    public WorkflowStep getWorkflowStepByNameForUpdate(final Class<? extends BaseException> unknownException, final ExecutionErrors unknownExecutionError,
            final ExecutionErrorAccumulator eea, final Workflow workflow, final String workflowStepName) {
        return getWorkflowStepByName(unknownException, unknownExecutionError, eea, workflow, workflowStepName, EntityPermission.READ_WRITE);
    }

    public WorkflowStep getWorkflowStepByName(final ExecutionErrorAccumulator eea, final Workflow workflow, final String workflowStepName,
            final EntityPermission entityPermission) {
        return getWorkflowStepByName(UnknownWorkflowStepNameException.class, ExecutionErrors.UnknownWorkflowStepName,
                eea, workflow, workflowStepName, entityPermission);
    }

    public WorkflowStep getWorkflowStepByName(final ExecutionErrorAccumulator eea, final Workflow workflow, final String workflowStepName) {
        return getWorkflowStepByName(UnknownWorkflowStepNameException.class, ExecutionErrors.UnknownWorkflowStepName,
                eea, workflow, workflowStepName);
    }

    public WorkflowStep getWorkflowStepByNameForUpdate(final ExecutionErrorAccumulator eea, final Workflow workflow, final String workflowStepName) {
        return getWorkflowStepByNameForUpdate(UnknownWorkflowStepNameException.class, ExecutionErrors.UnknownWorkflowStepName,
                eea, workflow, workflowStepName);
    }

    public WorkflowStep getWorkflowStepByName(final ExecutionErrorAccumulator eea, final String workflowName, final String workflowStepName,
            final EntityPermission entityPermission) {
        var workflow = WorkflowLogic.getInstance().getWorkflowByName(eea, workflowName);
        WorkflowStep workflowStep = null;

        if(eea == null || !eea.hasExecutionErrors()) {
            workflowStep = getWorkflowStepByName(UnknownWorkflowStepNameException.class, ExecutionErrors.UnknownWorkflowStepName,
                    eea, workflow, workflowStepName, entityPermission);
        }

        return workflowStep;
    }

    public WorkflowStep getWorkflowStepByName(final ExecutionErrorAccumulator eea, final String workflowName, final String workflowStepName) {
        return getWorkflowStepByName(eea, workflowName, workflowStepName, EntityPermission.READ_ONLY);
    }

    public WorkflowStep getWorkflowStepByNameForUpdate(final ExecutionErrorAccumulator eea, final String workflowName, final String workflowStepName) {
        return getWorkflowStepByName(eea, workflowName, workflowStepName, EntityPermission.READ_WRITE);
    }

    public WorkflowStep getWorkflowStepByUniversalSpec(final ExecutionErrorAccumulator eea, final WorkflowStepUniversalSpec universalSpec,
            final boolean allowDefault, final EntityPermission entityPermission) {
        var workflowControl = Session.getModelController(WorkflowControl.class);
        var workflowName = universalSpec.getWorkflowName();
        var workflowStepName = universalSpec.getWorkflowStepName();
        var nameParameterCount= ParameterUtils.getInstance().countNonNullParameters(workflowName, workflowStepName);
        var possibleEntitySpecs= EntityInstanceLogic.getInstance().countPossibleEntitySpecs(universalSpec);
        WorkflowStep workflowStep = null;

        if(nameParameterCount < 3 && possibleEntitySpecs == 0) {
            Workflow workflow = null;

            if(workflowName != null) {
                workflow = WorkflowLogic.getInstance().getWorkflowByName(eea, workflowName);
            } else {
                handleExecutionError(MissingRequiredWorkflowNameException.class, eea, ExecutionErrors.MissingRequiredWorkflowName.name());
            }

            if(!eea.hasExecutionErrors()) {
                if(workflowStepName == null) {
                    if(allowDefault) {
                        workflowStep = workflowControl.getDefaultWorkflowStep(workflow, entityPermission);

                        if(workflowStep == null) {
                            handleExecutionError(UnknownDefaultWorkflowStepException.class, eea, ExecutionErrors.UnknownDefaultWorkflowStep.name());
                        }
                    } else {
                        handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
                    }
                } else {
                    workflowStep = getWorkflowStepByName(eea, workflow, workflowStepName, entityPermission);
                }
            }
        } else if(nameParameterCount == 0 && possibleEntitySpecs == 1) {
            var entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(eea, universalSpec,
                    ComponentVendors.ECHO_THREE.name(), EntityTypes.WorkflowStep.name());

            if(!eea.hasExecutionErrors()) {
                workflowStep = workflowControl.getWorkflowStepByEntityInstance(entityInstance, entityPermission);
            }
        } else {
            handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
        }

        return workflowStep;
    }

    public WorkflowStep getWorkflowStepByUniversalSpec(final ExecutionErrorAccumulator eea, final WorkflowStepUniversalSpec universalSpec,
            boolean allowDefault) {
        return getWorkflowStepByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_ONLY);
    }

    public WorkflowStep getWorkflowStepByUniversalSpecForUpdate(final ExecutionErrorAccumulator eea, final WorkflowStepUniversalSpec universalSpec,
            boolean allowDefault) {
        return getWorkflowStepByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_WRITE);
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
        var entityInstanceControl = Session.getModelController(EntityInstanceControl.class);
        var entityInstance = entityInstanceControl.getEntityInstanceByBasePK(pk);
        
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
        var workflow = WorkflowLogic.getInstance().getWorkflowByName(eea, workflowName);
        Set<WorkflowEntityStatus> result = new HashSet<>();
        
        if(!hasExecutionErrors(eea)) {
            var workflowControl = Session.getModelController(WorkflowControl.class);
            var workflowEntityStatuses = workflowControl.getWorkflowEntityStatusesByEntityInstance(workflow, entityInstance, entityPermission);
            Set<String> possibleWorkflowStepNames = new HashSet<>(workflowStepNames.length);
            
            possibleWorkflowStepNames.addAll(Arrays.asList(workflowStepNames));
            
            workflowEntityStatuses.forEach((workflowEntityStatus) -> {
                var workflowStepDetail = workflowEntityStatus.getWorkflowStep().getLastDetail();
                if (workflowStepDetail.getWorkflow().equals(workflow)) {
                    var workflowStepName = workflowStepDetail.getWorkflowStepName();
                    if (possibleWorkflowStepNames.contains(workflowStepName)) {
                        result.add(workflowEntityStatus);
                    }
                }
            });

        }

        return result;
    }
    
    public boolean isWorkflowStepInSet(Set<WorkflowEntityStatus> workflowEntityStatuses, String workflowStepName) {
        var result = false;
        
        for(var workflowEntityStatus : workflowEntityStatuses) {
            if(result |= workflowEntityStatus.getWorkflowStep().getLastDetail().getWorkflowStepName().equals(workflowStepName)) {
                break;
            }
        }
        
        return result;
    }

}
