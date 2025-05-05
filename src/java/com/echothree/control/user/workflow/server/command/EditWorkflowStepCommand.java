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

import com.echothree.control.user.workflow.common.edit.WorkflowEditFactory;
import com.echothree.control.user.workflow.common.edit.WorkflowStepEdit;
import com.echothree.control.user.workflow.common.form.EditWorkflowStepForm;
import com.echothree.control.user.workflow.common.result.EditWorkflowStepResult;
import com.echothree.control.user.workflow.common.result.WorkflowResultFactory;
import com.echothree.control.user.workflow.common.spec.WorkflowStepUniversalSpec;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.control.workflow.server.logic.WorkflowStepLogic;
import com.echothree.model.control.workflow.server.logic.WorkflowStepTypeLogic;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.model.data.workflow.server.entity.WorkflowStep;
import com.echothree.model.data.workflow.server.entity.WorkflowStepType;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.List;

public class EditWorkflowStepCommand
        extends BaseAbstractEditCommand<WorkflowStepUniversalSpec, WorkflowStepEdit, EditWorkflowStepResult, WorkflowStep, WorkflowStep> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.WorkflowStep.name(), SecurityRoles.Edit.name())
                ))
        ));
        
        SPEC_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("WorkflowName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("WorkflowStepName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EntityRef", FieldType.ENTITY_REF, false, null, null),
                new FieldDefinition("Uuid", FieldType.UUID, false, null, null)
        );
        
        EDIT_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("WorkflowStepName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("WorkflowStepTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
        );
    }
    
    /** Creates a new instance of EditWorkflowStepCommand */
    public EditWorkflowStepCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditWorkflowStepResult getResult() {
        return WorkflowResultFactory.getEditWorkflowStepResult();
    }

    @Override
    public WorkflowStepEdit getEdit() {
        return WorkflowEditFactory.getWorkflowStepEdit();
    }

    @Override
    public WorkflowStep getEntity(EditWorkflowStepResult result) {
        return WorkflowStepLogic.getInstance().getWorkflowStepByUniversalSpec(this, spec, false, editModeToEntityPermission(editMode));
    }

    @Override
    public WorkflowStep getLockEntity(WorkflowStep freeOnBoard) {
        return freeOnBoard;
    }

    @Override
    public void fillInResult(EditWorkflowStepResult result, WorkflowStep freeOnBoard) {
        var workflow = Session.getModelController(WorkflowControl.class);

        result.setWorkflowStep(workflow.getWorkflowStepTransfer(getUserVisit(), freeOnBoard));
    }

    @Override
    public void doLock(WorkflowStepEdit edit, WorkflowStep workflowStep) {
        var workflowControl = Session.getModelController(WorkflowControl.class);
        var workflowStepDescription = workflowControl.getWorkflowStepDescription(workflowStep, getPreferredLanguage());
        var workflowStepDetail = workflowStep.getLastDetail();

        edit.setWorkflowStepName(workflowStepDetail.getWorkflowStepName());
        edit.setWorkflowStepTypeName(workflowStepDetail.getWorkflowStepType().getWorkflowStepTypeName());
        edit.setIsDefault(workflowStepDetail.getIsDefault().toString());
        edit.setSortOrder(workflowStepDetail.getSortOrder().toString());

        if(workflowStepDescription != null) {
            edit.setDescription(workflowStepDescription.getDescription());
        }
    }

    WorkflowStepType workflowStepType;

    @Override
    public void canUpdate(WorkflowStep workflowStep) {
        var workflowControl = Session.getModelController(WorkflowControl.class);
        var workflow = workflowStep.getLastDetail().getWorkflow();
        var workflowStepName = edit.getWorkflowStepName();
        var duplicateWorkflowStep = workflowControl.getWorkflowStepByName(workflow, workflowStepName);

        if(duplicateWorkflowStep != null && !workflowStep.equals(duplicateWorkflowStep)) {
            addExecutionError(ExecutionErrors.DuplicateWorkflowStepName.name(), workflowStepName);
        } else {
            var workflowStepTypeName = edit.getWorkflowStepTypeName();

            workflowStepType = WorkflowStepTypeLogic.getInstance().getWorkflowStepTypeByName(this, workflowStepTypeName);
        }
    }

    @Override
    public void doUpdate(WorkflowStep workflowStep) {
        var workflowControl = Session.getModelController(WorkflowControl.class);
        var partyPK = getPartyPK();
        var workflowStepDetailValue = workflowControl.getWorkflowStepDetailValueForUpdate(workflowStep);
        var workflowStepDescription = workflowControl.getWorkflowStepDescriptionForUpdate(workflowStep, getPreferredLanguage());
        var description = edit.getDescription();

        workflowStepDetailValue.setWorkflowStepName(edit.getWorkflowStepName());
        workflowStepDetailValue.setWorkflowStepTypePK(workflowStepType.getPrimaryKey());
        workflowStepDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        workflowStepDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        workflowControl.updateWorkflowStepFromValue(workflowStepDetailValue, partyPK);

        if(workflowStepDescription == null && description != null) {
            workflowControl.createWorkflowStepDescription(workflowStep, getPreferredLanguage(), description, partyPK);
        } else if(workflowStepDescription != null && description == null) {
            workflowControl.deleteWorkflowStepDescription(workflowStepDescription, partyPK);
        } else if(workflowStepDescription != null && description != null) {
            var workflowStepDescriptionValue = workflowControl.getWorkflowStepDescriptionValue(workflowStepDescription);

            workflowStepDescriptionValue.setDescription(description);
            workflowControl.updateWorkflowStepDescriptionFromValue(workflowStepDescriptionValue, partyPK);
        }
    }
    
}
