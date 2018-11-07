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

package com.echothree.control.user.inventory.server.command;

import com.echothree.control.user.inventory.common.edit.InventoryEditFactory;
import com.echothree.control.user.inventory.common.edit.LotTypeEdit;
import com.echothree.control.user.inventory.common.form.EditLotTypeForm;
import com.echothree.control.user.inventory.common.result.EditLotTypeResult;
import com.echothree.control.user.inventory.common.result.InventoryResultFactory;
import com.echothree.control.user.inventory.common.spec.LotTypeSpec;
import com.echothree.model.control.inventory.server.InventoryControl;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.sequence.server.SequenceControl;
import com.echothree.model.control.workflow.server.WorkflowControl;
import com.echothree.model.data.inventory.server.entity.LotType;
import com.echothree.model.data.inventory.server.entity.LotTypeDescription;
import com.echothree.model.data.inventory.server.entity.LotTypeDetail;
import com.echothree.model.data.inventory.server.value.LotTypeDescriptionValue;
import com.echothree.model.data.inventory.server.value.LotTypeDetailValue;
import com.echothree.model.data.party.common.pk.PartyPK;
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

public class EditLotTypeCommand
        extends BaseAbstractEditCommand<LotTypeSpec, LotTypeEdit, EditLotTypeResult, LotType, LotType> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_UTILITY, null),
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
                    new SecurityRoleDefinition(SecurityRoleGroups.LotType.name(), SecurityRoles.Edit.name())
                    )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("LotTypeName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("LotTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ParentLotTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("LotSequenceTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("LotWorkflowName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("LotWorkflowEntranceName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 80L)
                ));
    }
    
    /** Creates a new instance of EditLotTypeCommand */
    public EditLotTypeCommand(UserVisitPK userVisitPK, EditLotTypeForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditLotTypeResult getResult() {
        return InventoryResultFactory.getEditLotTypeResult();
    }

    @Override
    public LotTypeEdit getEdit() {
        return InventoryEditFactory.getLotTypeEdit();
    }

    @Override
    public LotType getEntity(EditLotTypeResult result) {
        InventoryControl inventoryControl = (InventoryControl)Session.getModelController(InventoryControl.class);
        LotType lotType = null;
        String lotTypeName = spec.getLotTypeName();

        if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
            lotType = inventoryControl.getLotTypeByName(lotTypeName);
        } else { // EditMode.UPDATE
            lotType = inventoryControl.getLotTypeByNameForUpdate(lotTypeName);
        }

        if(lotType != null) {
            result.setLotType(inventoryControl.getLotTypeTransfer(getUserVisit(), lotType));
        } else {
            addExecutionError(ExecutionErrors.UnknownLotTypeName.name(), lotTypeName);
        }

        return lotType;
    }

    @Override
    public LotType getLockEntity(LotType lotType) {
        return lotType;
    }

    @Override
    public void fillInResult(EditLotTypeResult result, LotType lotType) {
        InventoryControl inventoryControl = (InventoryControl)Session.getModelController(InventoryControl.class);

        result.setLotType(inventoryControl.getLotTypeTransfer(getUserVisit(), lotType));
    }

    LotType parentLotType = null;
    SequenceType lotSequenceType = null;
    Workflow lotWorkflow = null;
    WorkflowEntrance lotWorkflowEntrance = null;

    @Override
    public void doLock(LotTypeEdit edit, LotType lotType) {
        InventoryControl inventoryControl = (InventoryControl)Session.getModelController(InventoryControl.class);
        LotTypeDescription lotTypeDescription = inventoryControl.getLotTypeDescription(lotType, getPreferredLanguage());
        LotTypeDetail lotTypeDetail = lotType.getLastDetail();

        parentLotType = lotTypeDetail.getParentLotType();
        lotSequenceType = lotTypeDetail.getLotSequenceType();
        lotWorkflow = lotTypeDetail.getLotWorkflow();
        lotWorkflowEntrance = lotTypeDetail.getLotWorkflowEntrance();

        edit.setLotTypeName(lotTypeDetail.getLotTypeName());
        edit.setParentLotTypeName(parentLotType == null ? null : parentLotType.getLastDetail().getLotTypeName());
        edit.setLotSequenceTypeName(lotSequenceType == null ? null : lotSequenceType.getLastDetail().getSequenceTypeName());
        edit.setLotWorkflowName(lotWorkflow == null ? null : lotWorkflow.getLastDetail().getWorkflowName());
        edit.setLotWorkflowEntranceName(lotWorkflowEntrance == null ? null : lotWorkflowEntrance.getLastDetail().getWorkflowEntranceName());
        edit.setIsDefault(lotTypeDetail.getIsDefault().toString());
        edit.setSortOrder(lotTypeDetail.getSortOrder().toString());

        if(lotTypeDescription != null) {
            edit.setDescription(lotTypeDescription.getDescription());
        }
    }

    @Override
    public void canUpdate(LotType lotType) {
        InventoryControl inventoryControl = (InventoryControl)Session.getModelController(InventoryControl.class);
        String lotTypeName = edit.getLotTypeName();
        LotType duplicateLotType = inventoryControl.getLotTypeByName(lotTypeName);

        if(duplicateLotType == null || lotType.equals(duplicateLotType)) {
            String parentLotTypeName = edit.getParentLotTypeName();

            if(parentLotTypeName != null) {
                parentLotType = inventoryControl.getLotTypeByName(parentLotTypeName);
            }

            if(parentLotTypeName == null || parentLotType != null) {
                if(inventoryControl.isParentLotTypeSafe(lotType, parentLotType)) {
                    SequenceControl sequenceControl = (SequenceControl)Session.getModelController(SequenceControl.class);
                    String lotSequenceTypeName = edit.getLotSequenceTypeName();

                    lotSequenceType = sequenceControl.getSequenceTypeByName(lotSequenceTypeName);

                    if(lotSequenceTypeName == null || lotSequenceType != null) {
                        WorkflowControl workflowControl = (WorkflowControl)Session.getModelController(WorkflowControl.class);
                        String lotWorkflowName = edit.getLotWorkflowName();

                        lotWorkflow = lotWorkflowName == null ? null : workflowControl.getWorkflowByName(lotWorkflowName);

                        if(lotWorkflowName == null || lotWorkflow != null) {
                            String lotWorkflowEntranceName = edit.getLotWorkflowEntranceName();

                            if(lotWorkflowEntranceName == null || (lotWorkflow != null && lotWorkflowEntranceName != null)) {
                                lotWorkflowEntrance = lotWorkflowEntranceName == null ? null : workflowControl.getWorkflowEntranceByName(lotWorkflow, lotWorkflowEntranceName);

                                if(lotWorkflowEntranceName != null && lotWorkflowEntrance == null) {
                                    addExecutionError(ExecutionErrors.UnknownLotWorkflowEntranceName.name(), lotWorkflowName, lotWorkflowEntranceName);
                                }
                            } else {
                                addExecutionError(ExecutionErrors.MissingRequiredLotWorkflowName.name());
                            }
                        } else {
                            addExecutionError(ExecutionErrors.UnknownLotWorkflowName.name(), lotWorkflowName);
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownLotSequenceTypeName.name(), lotSequenceTypeName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.InvalidParentLotType.name());
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownParentLotTypeName.name(), parentLotTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.DuplicateLotTypeName.name(), lotTypeName);
        }
    }

    @Override
    public void doUpdate(LotType lotType) {
        InventoryControl inventoryControl = (InventoryControl)Session.getModelController(InventoryControl.class);
        PartyPK partyPK = getPartyPK();
        LotTypeDetailValue lotTypeDetailValue = inventoryControl.getLotTypeDetailValueForUpdate(lotType);
        LotTypeDescription lotTypeDescription = inventoryControl.getLotTypeDescriptionForUpdate(lotType, getPreferredLanguage());
        String description = edit.getDescription();

        lotTypeDetailValue.setLotTypeName(edit.getLotTypeName());
        lotTypeDetailValue.setParentLotTypePK(parentLotType == null ? null : parentLotType.getPrimaryKey());
        lotTypeDetailValue.setLotSequenceTypePK(lotSequenceType == null ? null : lotSequenceType.getPrimaryKey());
        lotTypeDetailValue.setLotWorkflowPK(lotWorkflow == null ? null : lotWorkflow.getPrimaryKey());
        lotTypeDetailValue.setLotWorkflowEntrancePK(lotWorkflow == null ? null : lotWorkflowEntrance.getPrimaryKey());
        lotTypeDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        lotTypeDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        inventoryControl.updateLotTypeFromValue(lotTypeDetailValue, partyPK);

        if(lotTypeDescription == null && description != null) {
            inventoryControl.createLotTypeDescription(lotType, getPreferredLanguage(), description, partyPK);
        } else {
            if(lotTypeDescription != null && description == null) {
                inventoryControl.deleteLotTypeDescription(lotTypeDescription, partyPK);
            } else {
                if(lotTypeDescription != null && description != null) {
                    LotTypeDescriptionValue lotTypeDescriptionValue = inventoryControl.getLotTypeDescriptionValue(lotTypeDescription);

                    lotTypeDescriptionValue.setDescription(description);
                    inventoryControl.updateLotTypeDescriptionFromValue(lotTypeDescriptionValue, partyPK);
                }
            }
        }
    }

}
