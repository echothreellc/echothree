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

import com.echothree.model.control.core.server.logic.EntityTypeLogic;
import com.echothree.model.control.selector.server.logic.SelectorKindLogic;
import com.echothree.model.control.workflow.common.exception.UnknownWorkflowEntityTypeException;
import com.echothree.model.control.workflow.common.exception.UnknownWorkflowNameException;
import com.echothree.model.control.workflow.common.exception.UnknownWorkflowSelectorKindException;
import com.echothree.model.control.workflow.server.WorkflowControl;
import com.echothree.model.data.workflow.server.entity.Workflow;
import com.echothree.model.data.workflow.server.entity.WorkflowEntityType;
import com.echothree.model.data.workflow.server.entity.WorkflowSelectorKind;
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

    public Workflow getWorkflowByName(final Class unknownException, final ExecutionErrors unknownExecutionError,
            final ExecutionErrorAccumulator eea, final String workflowName) {
        var workflowControl = (WorkflowControl)Session.getModelController(WorkflowControl.class);
        var workflow = workflowControl.getWorkflowByName(workflowName);

        if(workflow == null) {
            handleExecutionError(unknownException, eea, unknownExecutionError.name(), workflowName);
        }

        return workflow;
    }

    public Workflow getWorkflowByName(final ExecutionErrorAccumulator eea, final String workflowName) {
        return getWorkflowByName(UnknownWorkflowNameException.class, ExecutionErrors.UnknownWorkflowName, eea, workflowName);
    }

    public WorkflowEntityType getWorkflowEntityTypeByName(final ExecutionErrorAccumulator eea, final String workflowName,
            final String componentVendorName, final String entityTypeName) {
        var workflow = getWorkflowByName(eea, workflowName);
        var entityType = EntityTypeLogic.getInstance().getEntityTypeByName(eea, componentVendorName, entityTypeName);
        WorkflowEntityType workflowEntityType = null;

        if(eea != null && !eea.hasExecutionErrors()) {
            var workflowControl = (WorkflowControl)Session.getModelController(WorkflowControl.class);

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
            var workflowControl = (WorkflowControl)Session.getModelController(WorkflowControl.class);

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
