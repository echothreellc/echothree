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

package com.echothree.control.user.shipment.server.command;

import com.echothree.control.user.shipment.common.form.CreateShipmentTypeForm;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.sequence.server.control.SequenceControl;
import com.echothree.model.control.shipment.server.control.ShipmentControl;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.data.shipment.server.entity.ShipmentType;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class CreateShipmentTypeCommand
        extends BaseSimpleCommand<CreateShipmentTypeForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                    new SecurityRoleDefinition(SecurityRoleGroups.ShipmentType.name(), SecurityRoles.Create.name())
                    )))
                )));
        
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
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
    
    /** Creates a new instance of CreateShipmentTypeCommand */
    public CreateShipmentTypeCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var shipmentControl = Session.getModelController(ShipmentControl.class);
        var shipmentTypeName = form.getShipmentTypeName();
        var shipmentType = shipmentControl.getShipmentTypeByName(shipmentTypeName);

        if(shipmentType == null) {
            var parentShipmentTypeName = form.getParentShipmentTypeName();
            ShipmentType parentShipmentType = null;

            if(parentShipmentTypeName != null) {
                parentShipmentType = shipmentControl.getShipmentTypeByName(parentShipmentTypeName);
            }

            if(parentShipmentTypeName == null || parentShipmentType != null) {
                var sequenceControl = Session.getModelController(SequenceControl.class);
                var shipmentSequenceTypeName = form.getShipmentSequenceTypeName();
                var shipmentSequenceType = sequenceControl.getSequenceTypeByName(shipmentSequenceTypeName);

                if(shipmentSequenceTypeName == null || shipmentSequenceType != null) {
                    var shipmentPackageSequenceTypeName = form.getShipmentPackageSequenceTypeName();
                    var shipmentPackageSequenceType = sequenceControl.getSequenceTypeByName(shipmentPackageSequenceTypeName);

                    if(shipmentPackageSequenceTypeName == null || shipmentPackageSequenceType != null) {
                        var workflowControl = Session.getModelController(WorkflowControl.class);
                        var shipmentWorkflowName = form.getShipmentWorkflowName();
                        var shipmentWorkflow = shipmentWorkflowName == null ? null : workflowControl.getWorkflowByName(shipmentWorkflowName);

                        if(shipmentWorkflowName == null || shipmentWorkflow != null) {
                            var shipmentWorkflowEntranceName = form.getShipmentWorkflowEntranceName();

                            if(shipmentWorkflowEntranceName == null || (shipmentWorkflow != null && shipmentWorkflowEntranceName != null)) {
                                var shipmentWorkflowEntrance = shipmentWorkflowEntranceName == null ? null : workflowControl.getWorkflowEntranceByName(shipmentWorkflow, shipmentWorkflowEntranceName);

                                if(shipmentWorkflowEntranceName == null || shipmentWorkflowEntrance != null) {
                                    var partyPK = getPartyPK();
                                    var isDefault = Boolean.valueOf(form.getIsDefault());
                                    var sortOrder = Integer.valueOf(form.getSortOrder());
                                    var description = form.getDescription();

                                    shipmentType = shipmentControl.createShipmentType(shipmentTypeName, parentShipmentType, shipmentSequenceType,
                                            shipmentPackageSequenceType, shipmentWorkflow, shipmentWorkflowEntrance, isDefault, sortOrder, partyPK);

                                    if(description != null) {
                                        shipmentControl.createShipmentTypeDescription(shipmentType, getPreferredLanguage(), description, partyPK);
                                    }
                                } else {
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
                addExecutionError(ExecutionErrors.UnknownParentShipmentTypeName.name(), parentShipmentTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.DuplicateShipmentTypeName.name(), shipmentTypeName);
        }

        return null;
    }
    
}
