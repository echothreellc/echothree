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

package com.echothree.control.user.workflow.server.command;

import com.echothree.control.user.workflow.remote.form.GetWorkflowEntityTypesForm;
import com.echothree.control.user.workflow.remote.result.GetWorkflowEntityTypesResult;
import com.echothree.control.user.workflow.remote.result.WorkflowResultFactory;
import com.echothree.model.control.core.server.CoreControl;
import com.echothree.model.control.workflow.server.WorkflowControl;
import com.echothree.model.data.core.server.entity.ComponentVendor;
import com.echothree.model.data.core.server.entity.EntityType;
import com.echothree.model.data.user.remote.pk.UserVisitPK;
import com.echothree.model.data.workflow.server.entity.Workflow;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.remote.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GetWorkflowEntityTypesCommand
        extends BaseSimpleCommand<GetWorkflowEntityTypesForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("WorkflowName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("ComponentVendorName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EntityTypeName", FieldType.ENTITY_TYPE_NAME, false, null, null)
                ));
    }
    
    /** Creates a new instance of GetWorkflowEntityTypesCommand */
    public GetWorkflowEntityTypesCommand(UserVisitPK userVisitPK, GetWorkflowEntityTypesForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected BaseResult execute() {
        GetWorkflowEntityTypesResult result = WorkflowResultFactory.getGetWorkflowEntityTypesResult();
        String workflowName = form.getWorkflowName();
        String componentVendorName = form.getComponentVendorName();
        String entityTypeName = form.getEntityTypeName();
        int parameterCount = (workflowName == null? 0: 1) + (componentVendorName == null && entityTypeName == null? 0: 1);

        if(parameterCount == 1) {
            WorkflowControl workflowControl = (WorkflowControl)Session.getModelController(WorkflowControl.class);

            if(workflowName != null) {
                Workflow workflow = workflowControl.getWorkflowByName(workflowName);

                if(workflow != null) {
                    result.setWorkflow(workflowControl.getWorkflowTransfer(getUserVisit(), workflow));
                    result.setWorkflowEntityTypes(workflowControl.getWorkflowEntityTypeTransfersByWorkflow(getUserVisit(), workflow));
                } else {
                    addExecutionError(ExecutionErrors.UnknownWorkflowName.name(), workflowName);
                }
            } else {
                CoreControl coreControl = getCoreControl();
                ComponentVendor componentVendor = coreControl.getComponentVendorByName(componentVendorName);

                if(componentVendor != null) {
                    EntityType entityType = coreControl.getEntityTypeByName(componentVendor, entityTypeName);

                    if(entityType != null) {
                        result.setEntityType(coreControl.getEntityTypeTransfer(getUserVisit(), entityType));
                        result.setWorkflowEntityTypes(workflowControl.getWorkflowEntityTypeTransfersByEntityType(getUserVisit(), entityType));
                    } else {
                        addExecutionError(ExecutionErrors.UnknownEntityTypeName.name(), componentVendorName, entityTypeName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownComponentVendorName.name(), componentVendorName);
                }
            }
        } else {
            addExecutionError(ExecutionErrors.InvalidParameterCount.name());
        }
        
        return result;
    }
    
}
