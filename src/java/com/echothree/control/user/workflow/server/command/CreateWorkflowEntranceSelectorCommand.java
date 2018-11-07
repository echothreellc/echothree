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

import com.echothree.control.user.workflow.common.form.CreateWorkflowEntranceSelectorForm;
import com.echothree.model.control.selector.server.SelectorControl;
import com.echothree.model.control.workflow.server.WorkflowControl;
import com.echothree.model.data.selector.server.entity.Selector;
import com.echothree.model.data.selector.server.entity.SelectorType;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.model.data.workflow.server.entity.Workflow;
import com.echothree.model.data.workflow.server.entity.WorkflowEntrance;
import com.echothree.model.data.workflow.server.entity.WorkflowEntranceSelector;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CreateWorkflowEntranceSelectorCommand
        extends BaseSimpleCommand<CreateWorkflowEntranceSelectorForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("WorkflowName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("WorkflowEntranceName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("SelectorName", FieldType.ENTITY_NAME, true, null, null)
                ));
    }
    
    /** Creates a new instance of CreateWorkflowEntranceSelectorCommand */
    public CreateWorkflowEntranceSelectorCommand(UserVisitPK userVisitPK, CreateWorkflowEntranceSelectorForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        WorkflowControl workflowControl = (WorkflowControl)Session.getModelController(WorkflowControl.class);
        String workflowName = form.getWorkflowName();
        Workflow workflow = workflowControl.getWorkflowByName(workflowName);
        
        if(workflow != null) {
            SelectorType selectorType = workflow.getLastDetail().getSelectorType();
            
            if(selectorType != null) {
            String workflowEntranceName = form.getWorkflowEntranceName();
            WorkflowEntrance workflowEntrance = workflowControl.getWorkflowEntranceByName(workflow, workflowEntranceName);
            
            if(workflowEntrance != null) {
                SelectorControl selectorControl = (SelectorControl)Session.getModelController(SelectorControl.class);
                String selectorName = form.getSelectorName();
                    Selector selector = selectorControl.getSelectorByName(workflow.getLastDetail().getSelectorType(), selectorName);
                    
                    if(selector != null) {
                    WorkflowEntranceSelector workflowEntranceSelector = workflowControl.getWorkflowEntranceSelector(workflowEntrance, selector);
                    
                    if(workflowEntranceSelector == null) {                        
                        workflowControl.createWorkflowEntranceSelector(workflowEntrance, selector, getPartyPK());
                    } else {
                        addExecutionError(ExecutionErrors.DuplicateWorkflowEntranceSelector.name());
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownSelectorName.name(), selectorName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownWorkflowEntranceName.name(), workflowEntranceName);
            }
            } else {
                addExecutionError(ExecutionErrors.InvalidWorkflow.name());
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownWorkflowName.name(), workflowName);
        }
        
        return null;
    }
    
}
