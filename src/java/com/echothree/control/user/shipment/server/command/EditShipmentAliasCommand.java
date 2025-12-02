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

import com.echothree.control.user.shipment.common.edit.ShipmentAliasEdit;
import com.echothree.control.user.shipment.common.edit.ShipmentEditFactory;
import com.echothree.control.user.shipment.common.result.EditShipmentAliasResult;
import com.echothree.control.user.shipment.common.result.ShipmentResultFactory;
import com.echothree.control.user.shipment.common.spec.ShipmentAliasSpec;
import com.echothree.control.user.shipment.server.command.util.ShipmentAliasUtil;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.shipment.server.control.ShipmentControl;
import com.echothree.model.data.shipment.server.entity.ShipmentAlias;
import com.echothree.model.data.shipment.server.entity.ShipmentAliasType;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
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
public class EditShipmentAliasCommand
        extends BaseAbstractEditCommand<ShipmentAliasSpec, ShipmentAliasEdit, EditShipmentAliasResult, ShipmentAlias, ShipmentAlias> {
    
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ShipmentTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ShipmentName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ShipmentAliasTypeName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Alias", FieldType.ENTITY_NAME, true, null, null)
                ));
    }
    
    /** Creates a new instance of EditShipmentAliasCommand */
    public EditShipmentAliasCommand() {
        super(null, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    protected CommandSecurityDefinition getCommandSecurityDefinition() {
        return new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(ShipmentAliasUtil.getInstance().getSecurityRoleGroupNameByShipmentTypeSpec(spec == null ? null : spec), SecurityRoles.Edit.name())
                )))
        )));
    }

    @Override
    public EditShipmentAliasResult getResult() {
        return ShipmentResultFactory.getEditShipmentAliasResult();
    }

    @Override
    public ShipmentAliasEdit getEdit() {
        return ShipmentEditFactory.getShipmentAliasEdit();
    }

    ShipmentAliasType shipmentAliasType;
    
    @Override
    public ShipmentAlias getEntity(EditShipmentAliasResult result) {
        var shipmentControl = Session.getModelController(ShipmentControl.class);
        ShipmentAlias shipmentAlias = null;
        var shipmentTypeName = spec.getShipmentTypeName();
        var shipmentType = shipmentControl.getShipmentTypeByName(shipmentTypeName);

        if(shipmentType != null) {
            var shipmentName = spec.getShipmentName();
            var shipment = shipmentControl.getShipmentByName(shipmentType, shipmentName);

            if(shipment != null) {
                var shipmentAliasTypeName = spec.getShipmentAliasTypeName();

                shipmentAliasType = shipmentControl.getShipmentAliasTypeByName(shipmentType, shipmentAliasTypeName);

                if(shipmentAliasType != null) {
                    if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                        shipmentAlias = shipmentControl.getShipmentAlias(shipment, shipmentAliasType);
                    } else { // EditMode.UPDATE
                        shipmentAlias = shipmentControl.getShipmentAliasForUpdate(shipment, shipmentAliasType);
                    }

                    if(shipmentAlias != null) {
                        result.setShipmentAlias(shipmentControl.getShipmentAliasTransfer(getUserVisit(), shipmentAlias));
                    } else {
                        addExecutionError(ExecutionErrors.UnknownShipmentAlias.name(), shipmentTypeName, shipmentName, shipmentAliasTypeName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownShipmentAliasTypeName.name(), shipmentTypeName, shipmentAliasTypeName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownShipmentName.name(), shipmentTypeName, shipmentName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownShipmentTypeName.name(), shipmentTypeName);
        }

        return shipmentAlias;
    }

    @Override
    public ShipmentAlias getLockEntity(ShipmentAlias shipmentAlias) {
        return shipmentAlias;
    }

    @Override
    public void fillInResult(EditShipmentAliasResult result, ShipmentAlias shipmentAlias) {
        var shipmentControl = Session.getModelController(ShipmentControl.class);

        result.setShipmentAlias(shipmentControl.getShipmentAliasTransfer(getUserVisit(), shipmentAlias));
    }

    @Override
    public void doLock(ShipmentAliasEdit edit, ShipmentAlias shipmentAlias) {
        edit.setAlias(shipmentAlias.getAlias());
    }

    @Override
    public void canUpdate(ShipmentAlias shipmentAlias) {
        var shipmentControl = Session.getModelController(ShipmentControl.class);
        var alias = edit.getAlias();
        var duplicateShipmentAlias = shipmentControl.getShipmentAliasByAlias(shipmentAliasType, alias);

        if(duplicateShipmentAlias != null && !shipmentAlias.equals(duplicateShipmentAlias)) {
            var shipmentAliasTypeDetail = shipmentAlias.getShipmentAliasType().getLastDetail();

            addExecutionError(ExecutionErrors.DuplicateShipmentAlias.name(), shipmentAliasTypeDetail.getShipmentType().getLastDetail().getShipmentTypeName(),
                    shipmentAliasTypeDetail.getShipmentAliasTypeName(), alias);
        }
    }

    @Override
    public void doUpdate(ShipmentAlias shipmentAlias) {
        var shipmentControl = Session.getModelController(ShipmentControl.class);
        var shipmentAliasValue = shipmentControl.getShipmentAliasValue(shipmentAlias);

        shipmentAliasValue.setAlias(edit.getAlias());

        shipmentControl.updateShipmentAliasFromValue(shipmentAliasValue, getPartyPK());
    }

}
