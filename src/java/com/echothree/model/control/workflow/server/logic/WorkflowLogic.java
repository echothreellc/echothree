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

import com.echothree.control.user.workflow.common.spec.WorkflowUniversalSpec;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.core.server.logic.EntityTypeLogic;
import com.echothree.model.control.selector.server.logic.SelectorKindLogic;
import com.echothree.model.control.workflow.common.exception.UnknownWorkflowEntityTypeException;
import com.echothree.model.control.workflow.common.exception.UnknownWorkflowNameException;
import com.echothree.model.control.workflow.common.exception.UnknownWorkflowSelectorKindException;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.data.workflow.server.entity.Workflow;
import com.echothree.model.data.workflow.server.entity.WorkflowEntityType;
import com.echothree.model.data.workflow.server.entity.WorkflowSelectorKind;
import com.echothree.util.common.exception.BaseException;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;

@ApplicationScoped
public class WorkflowLogic
        extends BaseLogic {

    protected WorkflowLogic() {
        super();
    }

    public static WorkflowLogic getInstance() {
        return CDI.current().select(WorkflowLogic.class).get();
    }

    public Workflow getWorkflowByName(final Class<? extends BaseException> unknownException, final ExecutionErrors unknownExecutionError,
            final ExecutionErrorAccumulator eea, final String workflowName, final EntityPermission entityPermission) {
        var workflowControl = Session.getModelController(WorkflowControl.class);
        var workflow = workflowControl.getWorkflowByName(workflowName, entityPermission);

        if(workflow == null) {
            handleExecutionError(unknownException, eea, unknownExecutionError.name(), workflowName);
        }

        return workflow;
    }

    public Workflow getWorkflowByName(final ExecutionErrorAccumulator eea, final String workflowName,
            final EntityPermission entityPermission) {
        return getWorkflowByName(UnknownWorkflowNameException.class, ExecutionErrors.UnknownWorkflowName, eea,
                workflowName, entityPermission);
    }

    public Workflow getWorkflowByName(final ExecutionErrorAccumulator eea, final String workflowName) {
        return getWorkflowByName(eea, workflowName, EntityPermission.READ_ONLY);
    }

    public Workflow getWorkflowByNameForUpdate(final ExecutionErrorAccumulator eea, final String workflowName) {
        return getWorkflowByName(eea, workflowName, EntityPermission.READ_WRITE);
    }

    public Workflow getWorkflowByUniversalSpec(final ExecutionErrorAccumulator eea,
            final WorkflowUniversalSpec universalSpec, final EntityPermission entityPermission) {
        Workflow workflow = null;
        var workflowControl = Session.getModelController(WorkflowControl.class);
        var workflowName = universalSpec.getWorkflowName();
        var parameterCount = (workflowName == null ? 0 : 1) + EntityInstanceLogic.getInstance().countPossibleEntitySpecs(universalSpec);

        switch(parameterCount) {
            case 1:
                if(workflowName == null) {
                    var entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(eea, universalSpec,
                            ComponentVendors.ECHO_THREE.name(), EntityTypes.Workflow.name());

                    if(!eea.hasExecutionErrors()) {
                        workflow = workflowControl.getWorkflowByEntityInstance(entityInstance, entityPermission);
                    }
                } else {
                    workflow = getWorkflowByName(eea, workflowName, entityPermission);
                }
                break;
            default:
                handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
                break;
        }

        return workflow;
    }

    public Workflow getWorkflowByUniversalSpec(final ExecutionErrorAccumulator eea,
            final WorkflowUniversalSpec universalSpec) {
        return getWorkflowByUniversalSpec(eea, universalSpec, EntityPermission.READ_ONLY);
    }

    public Workflow getWorkflowByUniversalSpecForUpdate(final ExecutionErrorAccumulator eea,
            final WorkflowUniversalSpec universalSpec) {
        return getWorkflowByUniversalSpec(eea, universalSpec, EntityPermission.READ_WRITE);
    }
    
    public WorkflowEntityType getWorkflowEntityTypeByName(final ExecutionErrorAccumulator eea, final String workflowName,
            final String componentVendorName, final String entityTypeName) {
        var workflow = getWorkflowByName(eea, workflowName);
        var entityType = EntityTypeLogic.getInstance().getEntityTypeByName(eea, componentVendorName, entityTypeName);
        WorkflowEntityType workflowEntityType = null;

        if(eea != null && !eea.hasExecutionErrors()) {
            var workflowControl = Session.getModelController(WorkflowControl.class);

            workflowEntityType = workflowControl.getWorkflowEntityType(workflow, entityType);

            if(workflowEntityType == null) {
                var entityTypeDetail = entityType.getLastDetail();

                handleExecutionError(UnknownWorkflowEntityTypeException.class, eea, ExecutionErrors.UnknownWorkflowEntityType.name(),
                        workflow.getLastDetail(),
                        entityTypeDetail.getComponentVendor().getLastDetail().getComponentVendorName(),
                        entityTypeDetail.getEntityTypeName());
            }
        }

        return workflowEntityType;
    }

    public WorkflowSelectorKind getWorkflowSelectorKindByName(final ExecutionErrorAccumulator eea, final String workflowName,
            final String selectorKindName) {
        var workflow = getWorkflowByName(eea, workflowName);
        var selectorKind = SelectorKindLogic.getInstance().getSelectorKindByName(eea, selectorKindName);
        WorkflowSelectorKind workflowSelectorKind = null;

        if(eea != null && !eea.hasExecutionErrors()) {
            var workflowControl = Session.getModelController(WorkflowControl.class);

            workflowSelectorKind = workflowControl.getWorkflowSelectorKind(workflow, selectorKind);

            if(workflowSelectorKind == null) {
                handleExecutionError(UnknownWorkflowSelectorKindException.class, eea, ExecutionErrors.UnknownWorkflowSelectorKind.name(),
                        workflow.getLastDetail(),
                        selectorKind.getLastDetail().getSelectorKindName());
            }
        }

        return workflowSelectorKind;
    }

}
