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

package com.echothree.control.user.workflow.server.command;

import com.echothree.control.user.workflow.common.edit.WorkflowDescriptionEdit;
import com.echothree.control.user.workflow.common.edit.WorkflowEditFactory;
import com.echothree.control.user.workflow.common.result.EditWorkflowDescriptionResult;
import com.echothree.control.user.workflow.common.result.WorkflowResultFactory;
import com.echothree.control.user.workflow.common.spec.WorkflowDescriptionSpec;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.data.workflow.server.entity.Workflow;
import com.echothree.model.data.workflow.server.entity.WorkflowDescription;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class EditWorkflowDescriptionCommand
        extends BaseAbstractEditCommand<WorkflowDescriptionSpec, WorkflowDescriptionEdit, EditWorkflowDescriptionResult, WorkflowDescription, Workflow> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.Workflow.name(), SecurityRoles.Description.name())
                ))
        ));
        
        SPEC_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("WorkflowName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
        );
        
        EDIT_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L)
        );
    }

    @Inject
    PartyControl partyControl;

    @Inject
    WorkflowControl workflowControl;

    /** Creates a new instance of EditWorkflowDescriptionCommand */
    public EditWorkflowDescriptionCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditWorkflowDescriptionResult getResult() {
        return WorkflowResultFactory.getEditWorkflowDescriptionResult();
    }

    @Override
    public WorkflowDescriptionEdit getEdit() {
        return WorkflowEditFactory.getWorkflowDescriptionEdit();
    }

    @Override
    public WorkflowDescription getEntity(EditWorkflowDescriptionResult result) {
        WorkflowDescription workflowDescription = null;
        var workflowName = spec.getWorkflowName();
        var workflow = workflowControl.getWorkflowByName(workflowName);

        if(workflow != null) {
            var languageIsoName = spec.getLanguageIsoName();
            var language = partyControl.getLanguageByIsoName(languageIsoName);

            if(language != null) {
                if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                    workflowDescription = workflowControl.getWorkflowDescription(workflow, language);
                } else { // EditMode.UPDATE
                    workflowDescription = workflowControl.getWorkflowDescriptionForUpdate(workflow, language);
                }

                if(workflowDescription == null) {
                    addExecutionError(ExecutionErrors.UnknownWorkflowDescription.name(), workflowName, languageIsoName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownWorkflowName.name(), workflowName);
        }

        return workflowDescription;
    }

    @Override
    public Workflow getLockEntity(WorkflowDescription workflowDescription) {
        return workflowDescription.getWorkflow();
    }

    @Override
    public void fillInResult(EditWorkflowDescriptionResult result, WorkflowDescription workflowDescription) {
        result.setWorkflowDescription(workflowControl.getWorkflowDescriptionTransfer(getUserVisit(), workflowDescription));
    }

    @Override
    public void doLock(WorkflowDescriptionEdit edit, WorkflowDescription workflowDescription) {
        edit.setDescription(workflowDescription.getDescription());
    }

    @Override
    public void doUpdate(WorkflowDescription workflowDescription) {
        var workflowDescriptionValue = workflowControl.getWorkflowDescriptionValue(workflowDescription);

        workflowDescriptionValue.setDescription(edit.getDescription());

        workflowControl.updateWorkflowDescriptionFromValue(workflowDescriptionValue, getPartyPK());
    }
    
}
