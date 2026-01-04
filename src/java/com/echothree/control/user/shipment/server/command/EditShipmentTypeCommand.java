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

package com.echothree.control.user.shipment.server.command;

import com.echothree.control.user.shipment.common.edit.ShipmentEditFactory;
import com.echothree.control.user.shipment.common.edit.ShipmentTypeEdit;
import com.echothree.control.user.shipment.common.result.EditShipmentTypeResult;
import com.echothree.control.user.shipment.common.result.ShipmentResultFactory;
import com.echothree.control.user.shipment.common.spec.ShipmentTypeSpec;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.sequence.server.control.SequenceControl;
import com.echothree.model.control.shipment.server.control.ShipmentControl;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.data.sequence.server.entity.SequenceType;
import com.echothree.model.data.shipment.server.entity.ShipmentType;
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
import javax.enterprise.context.Dependent;

@Dependent
public class EditShipmentTypeCommand
        extends BaseAbstractEditCommand<ShipmentTypeSpec, ShipmentTypeEdit, EditShipmentTypeResult, ShipmentType, ShipmentType> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                    new SecurityRoleDefinition(SecurityRoleGroups.ShipmentType.name(), SecurityRoles.Edit.name())
                    )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ShipmentTypeName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ShipmentTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ParentShipmentTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("ShipmentSequenceTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("ShipmentPackageSequenceTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("ShipmentWorkflowName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("ShipmentWorkflowEntranceName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of EditShipmentTypeCommand */
    public EditShipmentTypeCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditShipmentTypeResult getResult() {
        return ShipmentResultFactory.getEditShipmentTypeResult();
    }

    @Override
    public ShipmentTypeEdit getEdit() {
        return ShipmentEditFactory.getShipmentTypeEdit();
    }

    @Override
    public ShipmentType getEntity(EditShipmentTypeResult result) {
        var shipmentControl = Session.getModelController(ShipmentControl.class);
        ShipmentType shipmentType;
        var shipmentTypeName = spec.getShipmentTypeName();

        if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
            shipmentType = shipmentControl.getShipmentTypeByName(shipmentTypeName);
        } else { // EditMode.UPDATE
            shipmentType = shipmentControl.getShipmentTypeByNameForUpdate(shipmentTypeName);
        }

        if(shipmentType != null) {
            result.setShipmentType(shipmentControl.getShipmentTypeTransfer(getUserVisit(), shipmentType));
        } else {
            addExecutionError(ExecutionErrors.UnknownShipmentTypeName.name(), shipmentTypeName);
        }

        return shipmentType;
    }

    @Override
    public ShipmentType getLockEntity(ShipmentType shipmentType) {
        return shipmentType;
    }

    @Override
    public void fillInResult(EditShipmentTypeResult result, ShipmentType shipmentType) {
        var shipmentControl = Session.getModelController(ShipmentControl.class);

        result.setShipmentType(shipmentControl.getShipmentTypeTransfer(getUserVisit(), shipmentType));
    }

    ShipmentType parentShipmentType = null;
    SequenceType shipmentSequenceType = null;
    SequenceType shipmentPackageSequenceType = null;
    Workflow shipmentWorkflow = null;
    WorkflowEntrance shipmentWorkflowEntrance = null;

    @Override
    public void doLock(ShipmentTypeEdit edit, ShipmentType shipmentType) {
        var shipmentControl = Session.getModelController(ShipmentControl.class);
        var shipmentTypeDescription = shipmentControl.getShipmentTypeDescription(shipmentType, getPreferredLanguage());
        var shipmentTypeDetail = shipmentType.getLastDetail();

        parentShipmentType = shipmentTypeDetail.getParentShipmentType();
        shipmentSequenceType = shipmentTypeDetail.getShipmentSequenceType();
        shipmentPackageSequenceType = shipmentTypeDetail.getShipmentPackageSequenceType();
        shipmentWorkflow = shipmentTypeDetail.getShipmentWorkflow();
        shipmentWorkflowEntrance = shipmentTypeDetail.getShipmentWorkflowEntrance();

        edit.setShipmentTypeName(shipmentTypeDetail.getShipmentTypeName());
        edit.setParentShipmentTypeName(parentShipmentType == null ? null : parentShipmentType.getLastDetail().getShipmentTypeName());
        edit.setShipmentSequenceTypeName(shipmentSequenceType == null ? null : shipmentSequenceType.getLastDetail().getSequenceTypeName());
        edit.setShipmentPackageSequenceTypeName(shipmentPackageSequenceType == null ? null : shipmentPackageSequenceType.getLastDetail().getSequenceTypeName());
        edit.setShipmentWorkflowName(shipmentWorkflow == null ? null : shipmentWorkflow.getLastDetail().getWorkflowName());
        edit.setShipmentWorkflowEntranceName(shipmentWorkflowEntrance == null ? null : shipmentWorkflowEntrance.getLastDetail().getWorkflowEntranceName());
        edit.setIsDefault(shipmentTypeDetail.getIsDefault().toString());
        edit.setSortOrder(shipmentTypeDetail.getSortOrder().toString());

        if(shipmentTypeDescription != null) {
            edit.setDescription(shipmentTypeDescription.getDescription());
        }
    }

    @Override
    public void canUpdate(ShipmentType shipmentType) {
        var shipmentControl = Session.getModelController(ShipmentControl.class);
        var shipmentTypeName = edit.getShipmentTypeName();
        var duplicateShipmentType = shipmentControl.getShipmentTypeByName(shipmentTypeName);

        if(duplicateShipmentType == null || shipmentType.equals(duplicateShipmentType)) {
            var parentShipmentTypeName = edit.getParentShipmentTypeName();

            if(parentShipmentTypeName != null) {
                parentShipmentType = shipmentControl.getShipmentTypeByName(parentShipmentTypeName);
            }

            if(parentShipmentTypeName == null || parentShipmentType != null) {
                if(shipmentControl.isParentShipmentTypeSafe(shipmentType, parentShipmentType)) {
                    var sequenceControl = Session.getModelController(SequenceControl.class);
                    var shipmentSequenceTypeName = edit.getShipmentSequenceTypeName();

                    shipmentSequenceType = sequenceControl.getSequenceTypeByName(shipmentSequenceTypeName);

                    if(shipmentSequenceTypeName == null || shipmentSequenceType != null) {
                        var shipmentPackageSequenceTypeName = edit.getShipmentPackageSequenceTypeName();

                        shipmentPackageSequenceType = sequenceControl.getSequenceTypeByName(shipmentPackageSequenceTypeName);

                        if(shipmentPackageSequenceTypeName == null || shipmentPackageSequenceType != null) {
                            var workflowControl = Session.getModelController(WorkflowControl.class);
                            var shipmentWorkflowName = edit.getShipmentWorkflowName();

                            shipmentWorkflow = shipmentWorkflowName == null ? null : workflowControl.getWorkflowByName(shipmentWorkflowName);

                            if(shipmentWorkflowName == null || shipmentWorkflow != null) {
                                var shipmentWorkflowEntranceName = edit.getShipmentWorkflowEntranceName();

                                if(shipmentWorkflowEntranceName == null || (shipmentWorkflow != null && shipmentWorkflowEntranceName != null)) {
                                    shipmentWorkflowEntrance = shipmentWorkflowEntranceName == null ? null : workflowControl.getWorkflowEntranceByName(shipmentWorkflow, shipmentWorkflowEntranceName);

                                    if(shipmentWorkflowEntranceName != null && shipmentWorkflowEntrance == null) {
                                        addExecutionError(ExecutionErrors.UnknownShipmentWorkflowEntranceName.name(), shipmentWorkflowName, shipmentWorkflowEntranceName);
                                    }
                                } else {
                                    addExecutionError(ExecutionErrors.MissingRequiredShipmentWorkflowName.name());
                                }
                            } else {
                                addExecutionError(ExecutionErrors.UnknownShipmentWorkflowName.name(), shipmentWorkflowName);
                            }
                        } else {
                            addExecutionError(ExecutionErrors.UnknownShipmentPackageSequenceTypeName.name(), shipmentPackageSequenceTypeName);
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownShipmentSequenceTypeName.name(), shipmentSequenceTypeName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.InvalidParentShipmentType.name());
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownParentShipmentTypeName.name(), parentShipmentTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.DuplicateShipmentTypeName.name(), shipmentTypeName);
        }
    }

    @Override
    public void doUpdate(ShipmentType shipmentType) {
        var shipmentControl = Session.getModelController(ShipmentControl.class);
        var partyPK = getPartyPK();
        var shipmentTypeDetailValue = shipmentControl.getShipmentTypeDetailValueForUpdate(shipmentType);
        var shipmentTypeDescription = shipmentControl.getShipmentTypeDescriptionForUpdate(shipmentType, getPreferredLanguage());
        var description = edit.getDescription();

        shipmentTypeDetailValue.setShipmentTypeName(edit.getShipmentTypeName());
        shipmentTypeDetailValue.setParentShipmentTypePK(parentShipmentType == null ? null : parentShipmentType.getPrimaryKey());
        shipmentTypeDetailValue.setShipmentSequenceTypePK(shipmentSequenceType == null ? null : shipmentSequenceType.getPrimaryKey());
        shipmentTypeDetailValue.setShipmentPackageSequenceTypePK(shipmentPackageSequenceType == null ? null : shipmentPackageSequenceType.getPrimaryKey());
        shipmentTypeDetailValue.setShipmentWorkflowPK(shipmentWorkflow == null ? null : shipmentWorkflow.getPrimaryKey());
        shipmentTypeDetailValue.setShipmentWorkflowEntrancePK(shipmentWorkflow == null ? null : shipmentWorkflowEntrance.getPrimaryKey());
        shipmentTypeDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        shipmentTypeDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        shipmentControl.updateShipmentTypeFromValue(shipmentTypeDetailValue, partyPK);

        if(shipmentTypeDescription == null && description != null) {
            shipmentControl.createShipmentTypeDescription(shipmentType, getPreferredLanguage(), description, partyPK);
        } else {
            if(shipmentTypeDescription != null && description == null) {
                shipmentControl.deleteShipmentTypeDescription(shipmentTypeDescription, partyPK);
            } else {
                if(shipmentTypeDescription != null && description != null) {
                    var shipmentTypeDescriptionValue = shipmentControl.getShipmentTypeDescriptionValue(shipmentTypeDescription);

                    shipmentTypeDescriptionValue.setDescription(description);
                    shipmentControl.updateShipmentTypeDescriptionFromValue(shipmentTypeDescriptionValue, partyPK);
                }
            }
        }
    }

}
