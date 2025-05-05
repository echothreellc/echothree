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

import com.echothree.control.user.workflow.common.edit.WorkflowEdit;
import com.echothree.control.user.workflow.common.edit.WorkflowEditFactory;
import com.echothree.control.user.workflow.common.form.EditWorkflowForm;
import com.echothree.control.user.workflow.common.result.EditWorkflowResult;
import com.echothree.control.user.workflow.common.result.WorkflowResultFactory;
import com.echothree.control.user.workflow.common.spec.WorkflowUniversalSpec;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.security.server.logic.SecurityRoleGroupLogic;
import com.echothree.model.control.selector.server.logic.SelectorTypeLogic;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.control.workflow.server.logic.WorkflowLogic;
import com.echothree.model.data.security.server.entity.SecurityRoleGroup;
import com.echothree.model.data.selector.server.entity.SelectorType;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.model.data.workflow.server.entity.Workflow;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.List;

public class EditWorkflowCommand
        extends BaseAbstractEditCommand<WorkflowUniversalSpec, WorkflowEdit, EditWorkflowResult, Workflow, Workflow> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                    new SecurityRoleDefinition(SecurityRoleGroups.Workflow.name(), SecurityRoles.Edit.name())
            ))
        ));
        
        SPEC_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("WorkflowName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EntityRef", FieldType.ENTITY_REF, false, null, null),
                new FieldDefinition("Uuid", FieldType.UUID, false, null, null)
        );
        
        EDIT_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("WorkflowName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("SelectorKindName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("SelectorTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("SecurityRoleGroupName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
        );
    }
    
    /** Creates a new instance of EditWorkflowCommand */
    public EditWorkflowCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditWorkflowResult getResult() {
        return WorkflowResultFactory.getEditWorkflowResult();
    }

    @Override
    public WorkflowEdit getEdit() {
        return WorkflowEditFactory.getWorkflowEdit();
    }

    @Override
    public Workflow getEntity(EditWorkflowResult result) {
        return WorkflowLogic.getInstance().getWorkflowByUniversalSpec(this, spec, editModeToEntityPermission(editMode));
    }

    @Override
    public Workflow getLockEntity(Workflow freeOnBoard) {
        return freeOnBoard;
    }

    @Override
    public void fillInResult(EditWorkflowResult result, Workflow freeOnBoard) {
        var workflow = Session.getModelController(WorkflowControl.class);

        result.setWorkflow(workflow.getWorkflowTransfer(getUserVisit(), freeOnBoard));
    }

    @Override
    public void doLock(WorkflowEdit edit, Workflow workflow) {
        var workflowControl = Session.getModelController(WorkflowControl.class);
        var workflowDescription = workflowControl.getWorkflowDescription(workflow, getPreferredLanguage());
        var workflowDetail = workflow.getLastDetail();
        var selectorType = workflowDetail.getSelectorType();
        var selectorKind = selectorType == null ? null : selectorType.getLastDetail().getSelectorKind();
        var securityRoleGroup = workflowDetail.getSecurityRoleGroup();

        edit.setWorkflowName(workflowDetail.getWorkflowName());
        edit.setSelectorKindName(selectorKind == null ? null : selectorKind.getLastDetail().getSelectorKindName());
        edit.setSelectorTypeName(selectorType == null ? null : selectorType.getLastDetail().getSelectorTypeName());
        edit.setSecurityRoleGroupName(securityRoleGroup == null ? null : securityRoleGroup.getLastDetail().getSecurityRoleGroupName());
        edit.setSortOrder(workflowDetail.getSortOrder().toString());

        if(workflowDescription != null) {
            edit.setDescription(workflowDescription.getDescription());
        }
    }

    SelectorType selectorType;
    SecurityRoleGroup securityRoleGroup;

    @Override
    public void canUpdate(Workflow workflow) {
        var workflowControl = Session.getModelController(WorkflowControl.class);
        var workflowName = edit.getWorkflowName();
        var duplicateWorkflow = workflowControl.getWorkflowByName(workflowName);

        if(duplicateWorkflow != null && !workflow.equals(duplicateWorkflow)) {
            addExecutionError(ExecutionErrors.DuplicateWorkflowName.name(), workflowName);
        } else {
            var selectorKindName = edit.getSelectorKindName();
            var selectorTypeName = edit.getSelectorTypeName();
            var parameterCount = (selectorKindName == null ? 0 : 1) + (selectorTypeName == null ? 0 : 1);

            if(parameterCount == 0 || parameterCount == 2) {
                selectorType = parameterCount == 0 ? null : SelectorTypeLogic.getInstance().getSelectorTypeByName(this, selectorKindName, selectorTypeName);

                if(!hasExecutionErrors()) {
                    var securityRoleGroupName = edit.getSecurityRoleGroupName();

                    securityRoleGroup = securityRoleGroupName == null ? null : SecurityRoleGroupLogic.getInstance().getSecurityRoleGroupByName(this, securityRoleGroupName);
                }
            } else {
                addExecutionError(ExecutionErrors.InvalidParameterCount.name());
            }
        }
    }

    @Override
    public void doUpdate(Workflow workflow) {
        var workflowControl = Session.getModelController(WorkflowControl.class);
        var partyPK = getPartyPK();
        var workflowDetailValue = workflowControl.getWorkflowDetailValueForUpdate(workflow);
        var workflowDescription = workflowControl.getWorkflowDescriptionForUpdate(workflow, getPreferredLanguage());
        var description = edit.getDescription();

        workflowDetailValue.setWorkflowName(edit.getWorkflowName());
        workflowDetailValue.setSelectorTypePK(selectorType == null ? null : selectorType.getPrimaryKey());
        workflowDetailValue.setSecurityRoleGroupPK(securityRoleGroup == null ? null : securityRoleGroup.getPrimaryKey());
        workflowDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        workflowControl.updateWorkflowFromValue(workflowDetailValue, partyPK);

        if(workflowDescription == null && description != null) {
            workflowControl.createWorkflowDescription(workflow, getPreferredLanguage(), description, partyPK);
        } else if(workflowDescription != null && description == null) {
            workflowControl.deleteWorkflowDescription(workflowDescription, partyPK);
        } else if(workflowDescription != null && description != null) {
            var workflowDescriptionValue = workflowControl.getWorkflowDescriptionValue(workflowDescription);

            workflowDescriptionValue.setDescription(description);
            workflowControl.updateWorkflowDescriptionFromValue(workflowDescriptionValue, partyPK);
        }
    }
    
}
