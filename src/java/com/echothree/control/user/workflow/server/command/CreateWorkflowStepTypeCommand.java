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

package com.echothree.control.user.workflow.server.command;

import com.echothree.control.user.workflow.common.form.CreateWorkflowStepTypeForm;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.workflow.server.logic.WorkflowStepTypeLogic;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import java.util.List;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class CreateWorkflowStepTypeCommand
        extends BaseSimpleCommand<CreateWorkflowStepTypeForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null)
        ));

        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("WorkflowStepTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null)
        );
    }
    
    /** Creates a new instance of CreateWorkflowStepTypeCommand */
    public CreateWorkflowStepTypeCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var workflowStepTypeName = form.getWorkflowStepTypeName();
        var isDefault = Boolean.valueOf(form.getIsDefault());
        var sortOrder = Integer.valueOf(form.getSortOrder());

        WorkflowStepTypeLogic.getInstance().createWorkflowStepType(this, workflowStepTypeName, isDefault, sortOrder, null,
                null, getPartyPK());

        return null;
    }
    
}
