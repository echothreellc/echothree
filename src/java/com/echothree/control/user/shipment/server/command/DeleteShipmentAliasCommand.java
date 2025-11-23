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

import com.echothree.control.user.shipment.common.form.DeleteShipmentAliasForm;
import com.echothree.control.user.shipment.server.command.util.ShipmentAliasUtil;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.shipment.server.control.ShipmentControl;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
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
public class DeleteShipmentAliasCommand
        extends BaseSimpleCommand<DeleteShipmentAliasForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ShipmentTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ShipmentName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ShipmentAliasTypeName", FieldType.ENTITY_NAME, true, null, null)
                ));
    }
    
    /** Creates a new instance of DeleteShipmentAliasCommand */
    public DeleteShipmentAliasCommand() {
        super(null, FORM_FIELD_DEFINITIONS, false);
    }

    @Override
    protected CommandSecurityDefinition getCommandSecurityDefinition() {
        return new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(ShipmentAliasUtil.getInstance().getSecurityRoleGroupNameByShipmentTypeSpec(form), SecurityRoles.Delete.name())
                )))
        )));
    }

    @Override
    protected BaseResult execute() {
        var shipmentControl = Session.getModelController(ShipmentControl.class);
        var shipmentTypeName = form.getShipmentTypeName();
        var shipmentType = shipmentControl.getShipmentTypeByName(shipmentTypeName);

        if(shipmentType != null) {
            var shipmentName = form.getShipmentName();
            var shipment = shipmentControl.getShipmentByName(shipmentType, shipmentName);

            if(shipment != null) {
                var shipmentAliasTypeName = form.getShipmentAliasTypeName();
                var shipmentAliasType = shipmentControl.getShipmentAliasTypeByName(shipmentType, shipmentAliasTypeName);

                if(shipmentAliasType != null) {
                    var shipmentAlias = shipmentControl.getShipmentAliasForUpdate(shipment, shipmentAliasType);

                    if(shipmentAlias != null) {
                        shipmentControl.deleteShipmentAlias(shipmentAlias, getPartyPK());
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

        return null;
    }
    
}
