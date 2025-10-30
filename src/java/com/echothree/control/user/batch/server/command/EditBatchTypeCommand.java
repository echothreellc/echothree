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

package com.echothree.control.user.batch.server.command;

import com.echothree.control.user.batch.common.edit.BatchEditFactory;
import com.echothree.control.user.batch.common.edit.BatchTypeEdit;
import com.echothree.control.user.batch.common.form.EditBatchTypeForm;
import com.echothree.control.user.batch.common.result.BatchResultFactory;
import com.echothree.control.user.batch.common.result.EditBatchTypeResult;
import com.echothree.control.user.batch.common.spec.BatchTypeSpec;
import com.echothree.model.control.batch.server.control.BatchControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.sequence.server.control.SequenceControl;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.data.batch.server.entity.BatchType;
import com.echothree.model.data.sequence.server.entity.SequenceType;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.model.data.workflow.server.entity.Workflow;
import com.echothree.model.data.workflow.server.entity.WorkflowEntrance;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class EditBatchTypeCommand
        extends BaseAbstractEditCommand<BatchTypeSpec, BatchTypeEdit, EditBatchTypeResult, BatchType, BatchType> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                    new SecurityRoleDefinition(SecurityRoleGroups.BatchType.name(), SecurityRoles.Edit.name())
                    )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("BatchTypeName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("BatchTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ParentBatchTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("BatchSequenceTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("BatchWorkflowName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("BatchWorkflowEntranceName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of EditBatchTypeCommand */
    public EditBatchTypeCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditBatchTypeResult getResult() {
        return BatchResultFactory.getEditBatchTypeResult();
    }

    @Override
    public BatchTypeEdit getEdit() {
        return BatchEditFactory.getBatchTypeEdit();
    }

    @Override
    public BatchType getEntity(EditBatchTypeResult result) {
        var batchControl = Session.getModelController(BatchControl.class);
        BatchType batchType;
        var batchTypeName = spec.getBatchTypeName();

        if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
            batchType = batchControl.getBatchTypeByName(batchTypeName);
        } else { // EditMode.UPDATE
            batchType = batchControl.getBatchTypeByNameForUpdate(batchTypeName);
        }

        if(batchType != null) {
            result.setBatchType(batchControl.getBatchTypeTransfer(getUserVisit(), batchType));
        } else {
            addExecutionError(ExecutionErrors.UnknownBatchTypeName.name(), batchTypeName);
        }

        return batchType;
    }

    @Override
    public BatchType getLockEntity(BatchType batchType) {
        return batchType;
    }

    @Override
    public void fillInResult(EditBatchTypeResult result, BatchType batchType) {
        var batchControl = Session.getModelController(BatchControl.class);

        result.setBatchType(batchControl.getBatchTypeTransfer(getUserVisit(), batchType));
    }

    BatchType parentBatchType = null;
    SequenceType batchSequenceType = null;
    Workflow batchWorkflow = null;
    WorkflowEntrance batchWorkflowEntrance = null;

    @Override
    public void doLock(BatchTypeEdit edit, BatchType batchType) {
        var batchControl = Session.getModelController(BatchControl.class);
        var batchTypeDescription = batchControl.getBatchTypeDescription(batchType, getPreferredLanguage());
        var batchTypeDetail = batchType.getLastDetail();

        parentBatchType = batchTypeDetail.getParentBatchType();
        batchSequenceType = batchTypeDetail.getBatchSequenceType();
        batchWorkflow = batchTypeDetail.getBatchWorkflow();
        batchWorkflowEntrance = batchTypeDetail.getBatchWorkflowEntrance();

        edit.setBatchTypeName(batchTypeDetail.getBatchTypeName());
        edit.setParentBatchTypeName(parentBatchType == null ? null : parentBatchType.getLastDetail().getBatchTypeName());
        edit.setBatchSequenceTypeName(batchSequenceType == null ? null : batchSequenceType.getLastDetail().getSequenceTypeName());
        edit.setBatchWorkflowName(batchWorkflow == null ? null : batchWorkflow.getLastDetail().getWorkflowName());
        edit.setBatchWorkflowEntranceName(batchWorkflowEntrance == null ? null : batchWorkflowEntrance.getLastDetail().getWorkflowEntranceName());
        edit.setIsDefault(batchTypeDetail.getIsDefault().toString());
        edit.setSortOrder(batchTypeDetail.getSortOrder().toString());

        if(batchTypeDescription != null) {
            edit.setDescription(batchTypeDescription.getDescription());
        }
    }

    @Override
    public void canUpdate(BatchType batchType) {
        var batchControl = Session.getModelController(BatchControl.class);
        var batchTypeName = edit.getBatchTypeName();
        var duplicateBatchType = batchControl.getBatchTypeByName(batchTypeName);

        if(duplicateBatchType == null || batchType.equals(duplicateBatchType)) {
            var parentBatchTypeName = edit.getParentBatchTypeName();

            if(parentBatchTypeName != null) {
                parentBatchType = batchControl.getBatchTypeByName(parentBatchTypeName);
            }

            if(parentBatchTypeName == null || parentBatchType != null) {
                if(batchControl.isParentBatchTypeSafe(batchType, parentBatchType)) {
                    var sequenceControl = Session.getModelController(SequenceControl.class);
                    var batchSequenceTypeName = edit.getBatchSequenceTypeName();

                    batchSequenceType = sequenceControl.getSequenceTypeByName(batchSequenceTypeName);

                    if(batchSequenceTypeName == null || batchSequenceType != null) {
                        var workflowControl = Session.getModelController(WorkflowControl.class);
                        var batchWorkflowName = edit.getBatchWorkflowName();

                        batchWorkflow = batchWorkflowName == null ? null : workflowControl.getWorkflowByName(batchWorkflowName);

                        if(batchWorkflowName == null || batchWorkflow != null) {
                            var batchWorkflowEntranceName = edit.getBatchWorkflowEntranceName();

                            if(batchWorkflowEntranceName == null || (batchWorkflow != null && batchWorkflowEntranceName != null)) {
                                batchWorkflowEntrance = batchWorkflowEntranceName == null ? null : workflowControl.getWorkflowEntranceByName(batchWorkflow, batchWorkflowEntranceName);

                                if(batchWorkflowEntranceName != null && batchWorkflowEntrance == null) {
                                    addExecutionError(ExecutionErrors.UnknownBatchWorkflowEntranceName.name(), batchWorkflowName, batchWorkflowEntranceName);
                                }
                            } else {
                                addExecutionError(ExecutionErrors.MissingRequiredBatchWorkflowName.name());
                            }
                        } else {
                            addExecutionError(ExecutionErrors.UnknownBatchWorkflowName.name(), batchWorkflowName);
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownBatchSequenceTypeName.name(), batchSequenceTypeName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.InvalidParentBatchType.name());
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownParentBatchTypeName.name(), parentBatchTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.DuplicateBatchTypeName.name(), batchTypeName);
        }
    }

    @Override
    public void doUpdate(BatchType batchType) {
        var batchControl = Session.getModelController(BatchControl.class);
        var partyPK = getPartyPK();
        var batchTypeDetailValue = batchControl.getBatchTypeDetailValueForUpdate(batchType);
        var batchTypeDescription = batchControl.getBatchTypeDescriptionForUpdate(batchType, getPreferredLanguage());
        var description = edit.getDescription();

        batchTypeDetailValue.setBatchTypeName(edit.getBatchTypeName());
        batchTypeDetailValue.setParentBatchTypePK(parentBatchType == null ? null : parentBatchType.getPrimaryKey());
        batchTypeDetailValue.setBatchSequenceTypePK(batchSequenceType == null ? null : batchSequenceType.getPrimaryKey());
        batchTypeDetailValue.setBatchWorkflowPK(batchWorkflow == null ? null : batchWorkflow.getPrimaryKey());
        batchTypeDetailValue.setBatchWorkflowEntrancePK(batchWorkflow == null ? null : batchWorkflowEntrance.getPrimaryKey());
        batchTypeDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        batchTypeDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        batchControl.updateBatchTypeFromValue(batchTypeDetailValue, partyPK);

        if(batchTypeDescription == null && description != null) {
            batchControl.createBatchTypeDescription(batchType, getPreferredLanguage(), description, partyPK);
        } else {
            if(batchTypeDescription != null && description == null) {
                batchControl.deleteBatchTypeDescription(batchTypeDescription, partyPK);
            } else {
                if(batchTypeDescription != null && description != null) {
                    var batchTypeDescriptionValue = batchControl.getBatchTypeDescriptionValue(batchTypeDescription);

                    batchTypeDescriptionValue.setDescription(description);
                    batchControl.updateBatchTypeDescriptionFromValue(batchTypeDescriptionValue, partyPK);
                }
            }
        }
    }

}
