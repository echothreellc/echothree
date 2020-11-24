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

package com.echothree.control.user.workflow.server.command;

import com.echothree.control.user.workflow.common.form.GetWorkflowEntranceForm;
import com.echothree.control.user.workflow.common.result.WorkflowResultFactory;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.control.workflow.server.logic.WorkflowEntranceLogic;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.model.data.workflow.server.entity.WorkflowEntrance;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSingleEntityCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GetWorkflowEntranceCommand
        extends BaseSingleEntityCommand<WorkflowEntrance, GetWorkflowEntranceForm> {

    // No COMMAND_SECURITY_DEFINITION, anyone may execute this command.
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("WorkflowName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("WorkflowEntranceName", FieldType.ENTITY_NAME, true, null, null)
                ));
    }
    
    /** Creates a new instance of GetWorkflowEntranceCommand */
    public GetWorkflowEntranceCommand(UserVisitPK userVisitPK, GetWorkflowEntranceForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, true);
    }

    @Override
    protected WorkflowEntrance getEntity() {
        var workflowName = form.getWorkflowName();
        var workflowEntranceName = form.getWorkflowEntranceName();

        return WorkflowEntranceLogic.getInstance().getWorkflowEntranceByName(this, workflowName, workflowEntranceName);
    }

    @Override
    protected BaseResult getTransfer(WorkflowEntrance workflowEntrance) {
        var workflowControl = Session.getModelController(WorkflowControl.class);
        var result = WorkflowResultFactory.getGetWorkflowEntranceResult();

        if(workflowEntrance != null) {
            result.setWorkflowEntrance(workflowControl.getWorkflowEntranceTransfer(getUserVisit(), workflowEntrance));
        }

        return result;
    }

}
