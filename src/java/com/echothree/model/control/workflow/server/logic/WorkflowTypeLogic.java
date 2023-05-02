// --------------------------------------------------------------------------------
// Copyright 2002-2023 Echo Three, LLC
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

import com.echothree.control.user.workflow.common.spec.WorkflowTypeUniversalSpec;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.workflow.common.exception.DuplicateWorkflowTypeNameException;
import com.echothree.model.control.workflow.common.exception.UnknownDefaultWorkflowTypeException;
import com.echothree.model.control.workflow.common.exception.UnknownWorkflowTypeNameException;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.workflow.server.entity.WorkflowType;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;

public class WorkflowTypeLogic
        extends BaseLogic {

    private WorkflowTypeLogic() {
        super();
    }

    private static class WorkflowTypeLogicHolder {
        static WorkflowTypeLogic instance = new WorkflowTypeLogic();
    }

    public static WorkflowTypeLogic getInstance() {
        return WorkflowTypeLogic.WorkflowTypeLogicHolder.instance;
    }

    public WorkflowType createWorkflowType(final ExecutionErrorAccumulator eea, final String workflowTypeName,
            final Boolean isDefault, final Integer sortOrder, final Language language, final String description,
            final BasePK createdBy) {
        var workflowControl = Session.getModelController(WorkflowControl.class);
        var workflowType = workflowControl.getWorkflowTypeByName(workflowTypeName);

        if(workflowType == null) {
            workflowType = workflowControl.createWorkflowType(workflowTypeName, isDefault, sortOrder, createdBy);

            if(description != null) {
                workflowControl.createWorkflowTypeDescription(workflowType, language, description, createdBy);
            }
        } else {
            handleExecutionError(DuplicateWorkflowTypeNameException.class, eea, ExecutionErrors.DuplicateWorkflowTypeName.name(), workflowTypeName);
        }

        return workflowType;
    }

    public WorkflowType getWorkflowTypeByName(final ExecutionErrorAccumulator eea, final String workflowTypeName,
            final EntityPermission entityPermission) {
        var workflowControl = Session.getModelController(WorkflowControl.class);
        var workflowType = workflowControl.getWorkflowTypeByName(workflowTypeName, entityPermission);

        if(workflowType == null) {
            handleExecutionError(UnknownWorkflowTypeNameException.class, eea, ExecutionErrors.UnknownWorkflowTypeName.name(), workflowTypeName);
        }

        return workflowType;
    }

    public WorkflowType getWorkflowTypeByName(final ExecutionErrorAccumulator eea, final String workflowTypeName) {
        return getWorkflowTypeByName(eea, workflowTypeName, EntityPermission.READ_ONLY);
    }

    public WorkflowType getWorkflowTypeByNameForUpdate(final ExecutionErrorAccumulator eea, final String workflowTypeName) {
        return getWorkflowTypeByName(eea, workflowTypeName, EntityPermission.READ_WRITE);
    }

    public WorkflowType getWorkflowTypeByUniversalSpec(final ExecutionErrorAccumulator eea,
            final WorkflowTypeUniversalSpec universalSpec, boolean allowDefault, final EntityPermission entityPermission) {
        WorkflowType workflowType = null;
        var workflowControl = Session.getModelController(WorkflowControl.class);
        var workflowTypeName = universalSpec.getWorkflowTypeName();
        var parameterCount = (workflowTypeName == null ? 0 : 1) + EntityInstanceLogic.getInstance().countPossibleEntitySpecs(universalSpec);

        switch(parameterCount) {
            case 0:
                if(allowDefault) {
                    workflowType = workflowControl.getDefaultWorkflowType(entityPermission);

                    if(workflowType == null) {
                        handleExecutionError(UnknownDefaultWorkflowTypeException.class, eea, ExecutionErrors.UnknownDefaultWorkflowType.name());
                    }
                } else {
                    handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
                }
                break;
            case 1:
                if(workflowTypeName == null) {
                    var entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(eea, universalSpec,
                            ComponentVendors.ECHO_THREE.name(), EntityTypes.WorkflowType.name());

                    if(!eea.hasExecutionErrors()) {
                        workflowType = workflowControl.getWorkflowTypeByEntityInstance(entityInstance, entityPermission);
                    }
                } else {
                    workflowType = getWorkflowTypeByName(eea, workflowTypeName, entityPermission);
                }
                break;
            default:
                handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
                break;
        }

        return workflowType;
    }

    public WorkflowType getWorkflowTypeByUniversalSpec(final ExecutionErrorAccumulator eea,
            final WorkflowTypeUniversalSpec universalSpec, boolean allowDefault) {
        return getWorkflowTypeByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_ONLY);
    }

    public WorkflowType getWorkflowTypeByUniversalSpecForUpdate(final ExecutionErrorAccumulator eea,
            final WorkflowTypeUniversalSpec universalSpec, boolean allowDefault) {
        return getWorkflowTypeByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_WRITE);
    }

}
