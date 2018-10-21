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

import com.echothree.control.user.workflow.remote.form.GetWorkflowSelectorKindForm;
import com.echothree.control.user.workflow.remote.result.GetWorkflowSelectorKindResult;
import com.echothree.control.user.workflow.remote.result.WorkflowResultFactory;
import com.echothree.model.control.selector.server.SelectorControl;
import com.echothree.model.control.workflow.server.WorkflowControl;
import com.echothree.model.data.selector.server.entity.SelectorKind;
import com.echothree.model.data.user.remote.pk.UserVisitPK;
import com.echothree.model.data.workflow.server.entity.Workflow;
import com.echothree.model.data.workflow.server.entity.WorkflowSelectorKind;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.remote.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GetWorkflowSelectorKindCommand
        extends BaseSimpleCommand<GetWorkflowSelectorKindForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
            new FieldDefinition("WorkflowName", FieldType.ENTITY_NAME, true, null, null),
            new FieldDefinition("WorkflowSelectorKindName", FieldType.ENTITY_NAME, true, null, null)
        ));
    }
    
    /** Creates a new instance of GetWorkflowSelectorKindCommand */
    public GetWorkflowSelectorKindCommand(UserVisitPK userVisitPK, GetWorkflowSelectorKindForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected BaseResult execute() {
        WorkflowControl workflowControl = (WorkflowControl)Session.getModelController(WorkflowControl.class);
        GetWorkflowSelectorKindResult result = WorkflowResultFactory.getGetWorkflowSelectorKindResult();
        String workflowName = form.getWorkflowName();
        Workflow workflow = workflowControl.getWorkflowByName(workflowName);
        
        if(workflow != null) {
            SelectorControl selectorControl = (SelectorControl)Session.getModelController(SelectorControl.class);
            String selectorKindName = form.getSelectorKindName();
            SelectorKind selectorKind = selectorControl.getSelectorKindByName(selectorKindName);
            
            if(selectorKind != null) {
                WorkflowSelectorKind workflowSelectorKind = workflowControl.getWorkflowSelectorKind(workflow, selectorKind);
                
                if(workflowSelectorKind != null) {
                    result.setWorkflowSelectorKind(workflowControl.getWorkflowSelectorKindTransfer(getUserVisit(), workflowSelectorKind));
                } else {
                    addExecutionError(ExecutionErrors.UnknownWorkflowSelectorKind.name(), workflowName, selectorKindName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownSelectorKindName.name(), selectorKindName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownWorkflowName.name(), workflowName);
        }
        
        return result;
    }
    
}
