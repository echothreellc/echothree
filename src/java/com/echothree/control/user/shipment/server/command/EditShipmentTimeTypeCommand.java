// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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
import com.echothree.control.user.shipment.common.edit.ShipmentTimeTypeEdit;
import com.echothree.control.user.shipment.common.form.EditShipmentTimeTypeForm;
import com.echothree.control.user.shipment.common.result.EditShipmentTimeTypeResult;
import com.echothree.control.user.shipment.common.result.ShipmentResultFactory;
import com.echothree.control.user.shipment.common.spec.ShipmentTimeTypeSpec;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.shipment.server.ShipmentControl;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.shipment.server.entity.ShipmentTimeType;
import com.echothree.model.data.shipment.server.entity.ShipmentTimeTypeDescription;
import com.echothree.model.data.shipment.server.entity.ShipmentTimeTypeDetail;
import com.echothree.model.data.shipment.server.entity.ShipmentType;
import com.echothree.model.data.shipment.server.value.ShipmentTimeTypeDescriptionValue;
import com.echothree.model.data.shipment.server.value.ShipmentTimeTypeDetailValue;
import com.echothree.model.data.user.common.pk.UserVisitPK;
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

public class EditShipmentTimeTypeCommand
        extends BaseAbstractEditCommand<ShipmentTimeTypeSpec, ShipmentTimeTypeEdit, EditShipmentTimeTypeResult, ShipmentTimeType, ShipmentTimeType> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                    new SecurityRoleDefinition(SecurityRoleGroups.ShipmentTimeType.name(), SecurityRoles.Edit.name())
                    )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ShipmentTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ShipmentTimeTypeName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ShipmentTimeTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 80L)
                ));
    }
    
    /** Creates a new instance of EditShipmentTimeTypeCommand */
    public EditShipmentTimeTypeCommand(UserVisitPK userVisitPK, EditShipmentTimeTypeForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditShipmentTimeTypeResult getResult() {
        return ShipmentResultFactory.getEditShipmentTimeTypeResult();
    }

    @Override
    public ShipmentTimeTypeEdit getEdit() {
        return ShipmentEditFactory.getShipmentTimeTypeEdit();
    }

    @Override
    public ShipmentTimeType getEntity(EditShipmentTimeTypeResult result) {
        var shipmentControl = (ShipmentControl)Session.getModelController(ShipmentControl.class);
        ShipmentTimeType shipmentTimeType = null;
        String shipmentTypeName = spec.getShipmentTypeName();
        ShipmentType shipmentType = shipmentControl.getShipmentTypeByName(shipmentTypeName);

        if(shipmentType != null) {
            String shipmentTimeTypeName = spec.getShipmentTimeTypeName();

            if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                shipmentTimeType = shipmentControl.getShipmentTimeTypeByName(shipmentType, shipmentTimeTypeName);
            } else { // EditMode.UPDATE
                shipmentTimeType = shipmentControl.getShipmentTimeTypeByNameForUpdate(shipmentType, shipmentTimeTypeName);
            }

            if(shipmentTimeType != null) {
                result.setShipmentTimeType(shipmentControl.getShipmentTimeTypeTransfer(getUserVisit(), shipmentTimeType));
            } else {
                addExecutionError(ExecutionErrors.UnknownShipmentTimeTypeName.name(), shipmentTypeName, shipmentTimeTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownShipmentTypeName.name(), shipmentTypeName);
        }

        return shipmentTimeType;
    }

    @Override
    public ShipmentTimeType getLockEntity(ShipmentTimeType shipmentTimeType) {
        return shipmentTimeType;
    }

    @Override
    public void fillInResult(EditShipmentTimeTypeResult result, ShipmentTimeType shipmentTimeType) {
        var shipmentControl = (ShipmentControl)Session.getModelController(ShipmentControl.class);

        result.setShipmentTimeType(shipmentControl.getShipmentTimeTypeTransfer(getUserVisit(), shipmentTimeType));
    }

    @Override
    public void doLock(ShipmentTimeTypeEdit edit, ShipmentTimeType shipmentTimeType) {
        var shipmentControl = (ShipmentControl)Session.getModelController(ShipmentControl.class);
        ShipmentTimeTypeDescription shipmentTimeTypeDescription = shipmentControl.getShipmentTimeTypeDescription(shipmentTimeType, getPreferredLanguage());
        ShipmentTimeTypeDetail shipmentTimeTypeDetail = shipmentTimeType.getLastDetail();

        edit.setShipmentTimeTypeName(shipmentTimeTypeDetail.getShipmentTimeTypeName());
        edit.setIsDefault(shipmentTimeTypeDetail.getIsDefault().toString());
        edit.setSortOrder(shipmentTimeTypeDetail.getSortOrder().toString());

        if(shipmentTimeTypeDescription != null) {
            edit.setDescription(shipmentTimeTypeDescription.getDescription());
        }
    }

    @Override
    public void canUpdate(ShipmentTimeType shipmentTimeType) {
        var shipmentControl = (ShipmentControl)Session.getModelController(ShipmentControl.class);
        String shipmentTypeName = spec.getShipmentTypeName();
        ShipmentType shipmentType = shipmentControl.getShipmentTypeByName(shipmentTypeName);

        if(shipmentType != null) {
            String shipmentTimeTypeName = edit.getShipmentTimeTypeName();
            ShipmentTimeType duplicateShipmentTimeType = shipmentControl.getShipmentTimeTypeByName(shipmentType, shipmentTimeTypeName);

            if(duplicateShipmentTimeType != null && !shipmentTimeType.equals(duplicateShipmentTimeType)) {
                addExecutionError(ExecutionErrors.DuplicateShipmentTimeTypeName.name(), shipmentTypeName, shipmentTimeTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownShipmentTypeName.name(), shipmentTypeName);
        }
    }

    @Override
    public void doUpdate(ShipmentTimeType shipmentTimeType) {
        var shipmentControl = (ShipmentControl)Session.getModelController(ShipmentControl.class);
        var partyPK = getPartyPK();
        ShipmentTimeTypeDetailValue shipmentTimeTypeDetailValue = shipmentControl.getShipmentTimeTypeDetailValueForUpdate(shipmentTimeType);
        ShipmentTimeTypeDescription shipmentTimeTypeDescription = shipmentControl.getShipmentTimeTypeDescriptionForUpdate(shipmentTimeType, getPreferredLanguage());
        String description = edit.getDescription();

        shipmentTimeTypeDetailValue.setShipmentTimeTypeName(edit.getShipmentTimeTypeName());
        shipmentTimeTypeDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        shipmentTimeTypeDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        shipmentControl.updateShipmentTimeTypeFromValue(shipmentTimeTypeDetailValue, partyPK);

        if(shipmentTimeTypeDescription == null && description != null) {
            shipmentControl.createShipmentTimeTypeDescription(shipmentTimeType, getPreferredLanguage(), description, partyPK);
        } else {
            if(shipmentTimeTypeDescription != null && description == null) {
                shipmentControl.deleteShipmentTimeTypeDescription(shipmentTimeTypeDescription, partyPK);
            } else {
                if(shipmentTimeTypeDescription != null && description != null) {
                    ShipmentTimeTypeDescriptionValue shipmentTimeTypeDescriptionValue = shipmentControl.getShipmentTimeTypeDescriptionValue(shipmentTimeTypeDescription);

                    shipmentTimeTypeDescriptionValue.setDescription(description);
                    shipmentControl.updateShipmentTimeTypeDescriptionFromValue(shipmentTimeTypeDescriptionValue, partyPK);
                }
            }
        }
    }

}
