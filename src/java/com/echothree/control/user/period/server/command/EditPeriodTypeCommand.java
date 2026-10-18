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

package com.echothree.control.user.period.server.command;

import com.echothree.control.user.period.common.edit.PeriodEditFactory;
import com.echothree.control.user.period.common.edit.PeriodTypeEdit;
import com.echothree.control.user.period.common.result.EditPeriodTypeResult;
import com.echothree.control.user.period.common.result.PeriodResultFactory;
import com.echothree.control.user.period.common.spec.PeriodTypeSpec;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.period.server.control.PeriodControl;
import com.echothree.model.control.period.server.logic.PeriodKindLogic;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.workflow.server.logic.WorkflowEntranceLogic;
import com.echothree.model.data.period.server.entity.PeriodType;
import com.echothree.model.data.workflow.server.entity.WorkflowEntrance;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class EditPeriodTypeCommand
        extends BaseAbstractEditCommand<PeriodTypeSpec, PeriodTypeEdit, EditPeriodTypeResult, PeriodType, PeriodType> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.PeriodType.name(), SecurityRoles.Edit.name())
                ))
        ));

        SPEC_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("PeriodKindName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("PeriodTypeName", FieldType.ENTITY_NAME, true, null, null)
        );

        EDIT_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("PeriodTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ParentPeriodTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("WorkflowName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("WorkflowEntranceName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
        );
    }

    @Inject
    PeriodControl periodControl;

    @Inject
    PeriodKindLogic periodKindLogic;

    @Inject
    WorkflowEntranceLogic workflowEntranceLogic;

    /** Creates a new instance of EditPeriodTypeCommand */
    public EditPeriodTypeCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditPeriodTypeResult getResult() {
        return PeriodResultFactory.getEditPeriodTypeResult();
    }

    @Override
    public PeriodTypeEdit getEdit() {
        return PeriodEditFactory.getPeriodTypeEdit();
    }

    @Override
    public PeriodType getEntity(EditPeriodTypeResult result) {
        PeriodType periodType = null;
        var periodKindName = spec.getPeriodKindName();
        var periodKind = periodKindLogic.getPeriodKindByName(this, periodKindName);

        if(!hasExecutionErrors()) {
            var periodTypeName = spec.getPeriodTypeName();

            periodType = periodControl.getPeriodTypeByName(periodKind, periodTypeName, editModeToEntityPermission(editMode));

            if(periodType == null) {
                addExecutionError(ExecutionErrors.UnknownPeriodTypeName.name(), periodKindName, periodTypeName);
            }
        }

        return periodType;
    }

    @Override
    public PeriodType getLockEntity(PeriodType periodType) {
        return periodType;
    }

    @Override
    public void fillInResult(EditPeriodTypeResult result, PeriodType periodType) {
        result.setPeriodType(periodControl.getPeriodTypeTransfer(getUserVisit(), periodType));
    }

    private PeriodType parentPeriodType;
    private WorkflowEntrance workflowEntrance;

    @Override
    public void doLock(PeriodTypeEdit edit, PeriodType periodType) {
        var periodTypeDescription = periodControl.getPeriodTypeDescription(periodType, getPreferredLanguage());
        var periodTypeDetail = periodType.getLastDetail();
        var parentPeriodType = periodTypeDetail.getParentPeriodType();
        var workflowEntrance = periodTypeDetail.getWorkflowEntrance();
        var workflow = workflowEntrance == null? null: workflowEntrance.getLastDetail().getWorkflow();

        edit.setPeriodTypeName(periodTypeDetail.getPeriodTypeName());
        edit.setParentPeriodType(parentPeriodType == null? null: parentPeriodType.getLastDetail().getPeriodTypeName());
        edit.setWorkflowName(workflow == null? null: workflow.getLastDetail().getWorkflowName());
        edit.setWorkflowEntranceName(workflowEntrance == null? null: workflowEntrance.getLastDetail().getWorkflowEntranceName());
        edit.setIsDefault(periodTypeDetail.getIsDefault().toString());
        edit.setSortOrder(periodTypeDetail.getSortOrder().toString());

        if(periodTypeDescription != null) {
            edit.setDescription(periodTypeDescription.getDescription());
        }
    }

    @Override
    public void canUpdate(PeriodType periodType) {
        var periodKind = periodType.getLastDetail().getPeriodKind();
        var periodKindName = periodKind.getLastDetail().getPeriodKindName();
        var periodTypeName = edit.getPeriodTypeName();
        var duplicatePeriodType = periodControl.getPeriodTypeByName(periodKind, periodTypeName);

        if(duplicatePeriodType == null || periodType.equals(duplicatePeriodType)) {
            var parentPeriodTypeName = edit.getParentPeriodTypeName();

            parentPeriodType = parentPeriodTypeName == null? null: periodControl.getPeriodTypeByName(periodKind, parentPeriodTypeName);

            if(parentPeriodTypeName == null || parentPeriodType != null) {
                if(periodControl.isParentPeriodTypeSafe(periodType, parentPeriodType)) {
                    var workflowName = edit.getWorkflowName();
                    var workflowEntranceName = edit.getWorkflowEntranceName();
                    var parameterCount = (workflowName == null ? 0 : 1) + (workflowEntranceName == null ? 0 : 1);

                    if(parameterCount == 0 || parameterCount == 2) {
                        workflowEntrance = parameterCount == 0? null: workflowEntranceLogic.getWorkflowEntranceByName(this, workflowName, workflowEntranceName);
                    } else {
                        addExecutionError(ExecutionErrors.InvalidParameterCount.name());
                    }
                } else {
                    addExecutionError(ExecutionErrors.InvalidParentPeriodType.name());
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownParentPeriodTypeName.name(), periodKindName, parentPeriodTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.DuplicatePeriodTypeName.name(), periodKindName, periodTypeName);
        }
    }

    @Override
    public void doUpdate(PeriodType periodType) {
        var partyPK = getPartyPK();
        var periodTypeDetailValue = periodControl.getPeriodTypeDetailValueForUpdate(periodType);
        var periodTypeDescription = periodControl.getPeriodTypeDescriptionForUpdate(periodType, getPreferredLanguage());
        var description = edit.getDescription();

        periodTypeDetailValue.setPeriodTypeName(edit.getPeriodTypeName());
        periodTypeDetailValue.setParentPeriodTypePK(parentPeriodType == null? null: parentPeriodType.getPrimaryKey());
        periodTypeDetailValue.setWorkflowEntrancePK(workflowEntrance == null? null: workflowEntrance.getPrimaryKey());
        periodTypeDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        periodTypeDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        periodControl.updatePeriodTypeFromValue(periodTypeDetailValue, partyPK);

        if(periodTypeDescription == null && description != null) {
            periodControl.createPeriodTypeDescription(periodType, getPreferredLanguage(), description, partyPK);
        } else if(periodTypeDescription != null && description == null) {
            periodControl.deletePeriodTypeDescription(periodTypeDescription, partyPK);
        } else if(periodTypeDescription != null && description != null) {
            var periodTypeDescriptionValue = periodControl.getPeriodTypeDescriptionValue(periodTypeDescription);

            periodTypeDescriptionValue.setDescription(description);
            periodControl.updatePeriodTypeDescriptionFromValue(periodTypeDescriptionValue, partyPK);
        }
    }

}
