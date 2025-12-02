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

import com.echothree.control.user.shipment.common.edit.ShipmentAliasTypeEdit;
import com.echothree.control.user.shipment.common.edit.ShipmentEditFactory;
import com.echothree.control.user.shipment.common.result.EditShipmentAliasTypeResult;
import com.echothree.control.user.shipment.common.result.ShipmentResultFactory;
import com.echothree.control.user.shipment.common.spec.ShipmentAliasTypeSpec;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.shipment.server.control.ShipmentControl;
import com.echothree.model.data.shipment.server.entity.ShipmentAliasType;
import com.echothree.model.data.shipment.server.entity.ShipmentType;
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
public class EditShipmentAliasTypeCommand
        extends BaseAbstractEditCommand<ShipmentAliasTypeSpec, ShipmentAliasTypeEdit, EditShipmentAliasTypeResult, ShipmentAliasType, ShipmentAliasType> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.ShipmentAliasType.name(), SecurityRoles.Edit.name())
                        )))
                )));

        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ShipmentTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ShipmentAliasTypeName", FieldType.ENTITY_NAME, true, null, null)
                ));

        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ShipmentAliasTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ValidationPattern", FieldType.REGULAR_EXPRESSION, false, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
                ));
    }

    /** Creates a new instance of EditShipmentAliasTypeCommand */
    public EditShipmentAliasTypeCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditShipmentAliasTypeResult getResult() {
        return ShipmentResultFactory.getEditShipmentAliasTypeResult();
    }

    @Override
    public ShipmentAliasTypeEdit getEdit() {
        return ShipmentEditFactory.getShipmentAliasTypeEdit();
    }

    ShipmentType shipmentType;

    @Override
    public ShipmentAliasType getEntity(EditShipmentAliasTypeResult result) {
        var shipmentControl = Session.getModelController(ShipmentControl.class);
        ShipmentAliasType shipmentAliasType = null;
        var shipmentTypeName = spec.getShipmentTypeName();

        shipmentType = shipmentControl.getShipmentTypeByName(shipmentTypeName);

        if(shipmentType != null) {
            var shipmentAliasTypeName = spec.getShipmentAliasTypeName();

            if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                shipmentAliasType = shipmentControl.getShipmentAliasTypeByName(shipmentType, shipmentAliasTypeName);
            } else { // EditMode.UPDATE
                shipmentAliasType = shipmentControl.getShipmentAliasTypeByNameForUpdate(shipmentType, shipmentAliasTypeName);
            }

            if(shipmentAliasType != null) {
                result.setShipmentAliasType(shipmentControl.getShipmentAliasTypeTransfer(getUserVisit(), shipmentAliasType));
            } else {
                addExecutionError(ExecutionErrors.UnknownShipmentAliasTypeName.name(), shipmentTypeName, shipmentAliasTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownShipmentTypeName.name(), shipmentTypeName);
        }

        return shipmentAliasType;
    }

    @Override
    public ShipmentAliasType getLockEntity(ShipmentAliasType shipmentAliasType) {
        return shipmentAliasType;
    }

    @Override
    public void fillInResult(EditShipmentAliasTypeResult result, ShipmentAliasType shipmentAliasType) {
        var shipmentControl = Session.getModelController(ShipmentControl.class);

        result.setShipmentAliasType(shipmentControl.getShipmentAliasTypeTransfer(getUserVisit(), shipmentAliasType));
    }

    @Override
    public void doLock(ShipmentAliasTypeEdit edit, ShipmentAliasType shipmentAliasType) {
        var shipmentControl = Session.getModelController(ShipmentControl.class);
        var shipmentAliasTypeDescription = shipmentControl.getShipmentAliasTypeDescription(shipmentAliasType, getPreferredLanguage());
        var shipmentAliasTypeDetail = shipmentAliasType.getLastDetail();

        edit.setShipmentAliasTypeName(shipmentAliasTypeDetail.getShipmentAliasTypeName());
        edit.setValidationPattern(shipmentAliasTypeDetail.getValidationPattern());
        edit.setIsDefault(shipmentAliasTypeDetail.getIsDefault().toString());
        edit.setSortOrder(shipmentAliasTypeDetail.getSortOrder().toString());

        if(shipmentAliasTypeDescription != null) {
            edit.setDescription(shipmentAliasTypeDescription.getDescription());
        }
    }

    @Override
    public void canUpdate(ShipmentAliasType shipmentAliasType) {
        var shipmentControl = Session.getModelController(ShipmentControl.class);
        var shipmentAliasTypeName = edit.getShipmentAliasTypeName();
        var duplicateShipmentAliasType = shipmentControl.getShipmentAliasTypeByName(shipmentType, shipmentAliasTypeName);

        if(duplicateShipmentAliasType != null && !shipmentAliasType.equals(duplicateShipmentAliasType)) {
            addExecutionError(ExecutionErrors.DuplicateShipmentAliasTypeName.name(), spec.getShipmentTypeName(), shipmentAliasTypeName);
        }
    }

    @Override
    public void doUpdate(ShipmentAliasType shipmentAliasType) {
        var shipmentControl = Session.getModelController(ShipmentControl.class);
        var partyPK = getPartyPK();
        var shipmentAliasTypeDetailValue = shipmentControl.getShipmentAliasTypeDetailValueForUpdate(shipmentAliasType);
        var shipmentAliasTypeDescription = shipmentControl.getShipmentAliasTypeDescriptionForUpdate(shipmentAliasType, getPreferredLanguage());
        var description = edit.getDescription();

        shipmentAliasTypeDetailValue.setShipmentAliasTypeName(edit.getShipmentAliasTypeName());
        shipmentAliasTypeDetailValue.setValidationPattern(edit.getValidationPattern());
        shipmentAliasTypeDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        shipmentAliasTypeDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        shipmentControl.updateShipmentAliasTypeFromValue(shipmentAliasTypeDetailValue, partyPK);

        if(shipmentAliasTypeDescription == null && description != null) {
            shipmentControl.createShipmentAliasTypeDescription(shipmentAliasType, getPreferredLanguage(), description, partyPK);
        } else if(shipmentAliasTypeDescription != null && description == null) {
            shipmentControl.deleteShipmentAliasTypeDescription(shipmentAliasTypeDescription, partyPK);
        } else if(shipmentAliasTypeDescription != null && description != null) {
            var shipmentAliasTypeDescriptionValue = shipmentControl.getShipmentAliasTypeDescriptionValue(shipmentAliasTypeDescription);

            shipmentAliasTypeDescriptionValue.setDescription(description);
            shipmentControl.updateShipmentAliasTypeDescriptionFromValue(shipmentAliasTypeDescriptionValue, partyPK);
        }
    }

}
