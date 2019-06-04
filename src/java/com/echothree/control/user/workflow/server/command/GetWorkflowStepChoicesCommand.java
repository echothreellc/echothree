// --------------------------------------------------------------------------------
// Copyright 2002-2019 Echo Three, LLC
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

import com.echothree.control.user.workflow.common.form.GetWorkflowStepChoicesForm;
import com.echothree.control.user.workflow.common.result.GetWorkflowStepChoicesResult;
import com.echothree.control.user.workflow.common.result.WorkflowResultFactory;
import com.echothree.model.control.workflow.server.WorkflowControl;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.model.data.workflow.server.entity.Workflow;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GetWorkflowStepChoicesCommand
        extends BaseSimpleCommand<GetWorkflowStepChoicesForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
            new FieldDefinition("WorkflowName", FieldType.ENTITY_NAME, true, null, null),
            new FieldDefinition("DefaultWorkflowStepChoice", FieldType.ENTITY_NAME, false, null, null),
            new FieldDefinition("AllowNullChoice", FieldType.BOOLEAN, true, null, null)
        ));
    }
    
    /** Creates a new instance of GetWorkflowStepChoicesCommand */
    public GetWorkflowStepChoicesCommand(UserVisitPK userVisitPK, GetWorkflowStepChoicesForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var workflowControl = (WorkflowControl)Session.getModelController(WorkflowControl.class);
        GetWorkflowStepChoicesResult result = WorkflowResultFactory.getGetWorkflowStepChoicesResult();
        String workflowName = form.getWorkflowName();
        Workflow workflow = workflowControl.getWorkflowByName(workflowName);
        
        if(workflow != null) {
            String defaultWorkflowStepChoice = form.getDefaultWorkflowStepChoice();
            boolean allowNullChoice = Boolean.parseBoolean(form.getAllowNullChoice());
            
            result.setWorkflowStepChoices(workflowControl.getWorkflowStepChoices(defaultWorkflowStepChoice,
                    getPreferredLanguage(), allowNullChoice, workflow));
        } else {
            addExecutionError(ExecutionErrors.UnknownWorkflowName.name(), workflowName);
        }
        
        return result;
    }
    
}
