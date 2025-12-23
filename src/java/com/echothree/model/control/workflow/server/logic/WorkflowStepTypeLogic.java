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

package com.echothree.model.control.workflow.server.logic;

import com.echothree.control.user.workflow.common.spec.WorkflowStepTypeUniversalSpec;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.workflow.common.exception.DuplicateWorkflowStepTypeNameException;
import com.echothree.model.control.workflow.common.exception.UnknownDefaultWorkflowStepTypeException;
import com.echothree.model.control.workflow.common.exception.UnknownWorkflowStepTypeNameException;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.workflow.server.entity.WorkflowStepType;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;

@ApplicationScoped
public class WorkflowStepTypeLogic
        extends BaseLogic {

    protected WorkflowStepTypeLogic() {
        super();
    }

    public static WorkflowStepTypeLogic getInstance() {
        return CDI.current().select(WorkflowStepTypeLogic.class).get();
    }

    public WorkflowStepType createWorkflowStepType(final ExecutionErrorAccumulator eea, final String workflowStepTypeName,
            final Boolean isDefault, final Integer sortOrder, final Language language, final String description,
            final BasePK createdBy) {
        var workflowControl = Session.getModelController(WorkflowControl.class);
        var workflowStepType = workflowControl.getWorkflowStepTypeByName(workflowStepTypeName);

        if(workflowStepType == null) {
            workflowStepType = workflowControl.createWorkflowStepType(workflowStepTypeName, isDefault, sortOrder, createdBy);

            if(description != null) {
                workflowControl.createWorkflowStepTypeDescription(workflowStepType, language, description, createdBy);
            }
        } else {
            handleExecutionError(DuplicateWorkflowStepTypeNameException.class, eea, ExecutionErrors.DuplicateWorkflowStepTypeName.name(), workflowStepTypeName);
        }

        return workflowStepType;
    }

    public WorkflowStepType getWorkflowStepTypeByName(final ExecutionErrorAccumulator eea, final String workflowStepTypeName,
            final EntityPermission entityPermission) {
        var workflowControl = Session.getModelController(WorkflowControl.class);
        var workflowStepType = workflowControl.getWorkflowStepTypeByName(workflowStepTypeName, entityPermission);

        if(workflowStepType == null) {
            handleExecutionError(UnknownWorkflowStepTypeNameException.class, eea, ExecutionErrors.UnknownWorkflowStepTypeName.name(), workflowStepTypeName);
        }

        return workflowStepType;
    }

    public WorkflowStepType getWorkflowStepTypeByName(final ExecutionErrorAccumulator eea, final String workflowStepTypeName) {
        return getWorkflowStepTypeByName(eea, workflowStepTypeName, EntityPermission.READ_ONLY);
    }

    public WorkflowStepType getWorkflowStepTypeByNameForUpdate(final ExecutionErrorAccumulator eea, final String workflowStepTypeName) {
        return getWorkflowStepTypeByName(eea, workflowStepTypeName, EntityPermission.READ_WRITE);
    }

    public WorkflowStepType getWorkflowStepTypeByUniversalSpec(final ExecutionErrorAccumulator eea,
            final WorkflowStepTypeUniversalSpec universalSpec, boolean allowDefault, final EntityPermission entityPermission) {
        WorkflowStepType workflowStepType = null;
        var workflowControl = Session.getModelController(WorkflowControl.class);
        var workflowStepTypeName = universalSpec.getWorkflowStepTypeName();
        var parameterCount = (workflowStepTypeName == null ? 0 : 1) + EntityInstanceLogic.getInstance().countPossibleEntitySpecs(universalSpec);

        if(parameterCount == 0) {
            if(allowDefault) {
                workflowStepType = workflowControl.getDefaultWorkflowStepType(entityPermission);

                if(workflowStepType == null) {
                    handleExecutionError(UnknownDefaultWorkflowStepTypeException.class, eea, ExecutionErrors.UnknownDefaultWorkflowStepType.name());
                }
            } else {
                handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
            }
        } else if(parameterCount == 1) {
            if(workflowStepTypeName == null) {
                var entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(eea, universalSpec,
                        ComponentVendors.ECHO_THREE.name(), EntityTypes.WorkflowStepType.name());

                if(!eea.hasExecutionErrors()) {
                    workflowStepType = workflowControl.getWorkflowStepTypeByEntityInstance(entityInstance, entityPermission);
                }
            } else {
                workflowStepType = getWorkflowStepTypeByName(eea, workflowStepTypeName, entityPermission);
            }
        } else {
            handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
        }

        return workflowStepType;
    }

    public WorkflowStepType getWorkflowStepTypeByUniversalSpec(final ExecutionErrorAccumulator eea,
            final WorkflowStepTypeUniversalSpec universalSpec, boolean allowDefault) {
        return getWorkflowStepTypeByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_ONLY);
    }

    public WorkflowStepType getWorkflowStepTypeByUniversalSpecForUpdate(final ExecutionErrorAccumulator eea,
            final WorkflowStepTypeUniversalSpec universalSpec, boolean allowDefault) {
        return getWorkflowStepTypeByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_WRITE);
    }

}
