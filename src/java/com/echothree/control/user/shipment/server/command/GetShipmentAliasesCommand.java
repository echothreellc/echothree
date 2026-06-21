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

import com.echothree.control.user.shipment.common.form.GetShipmentAliasesForm;
import com.echothree.control.user.shipment.common.result.ShipmentResultFactory;
import com.echothree.control.user.shipment.server.command.util.ShipmentAliasUtil;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.shipment.server.control.ShipmentControl;
import com.echothree.model.data.shipment.server.entity.Shipment;
import com.echothree.model.data.shipment.server.entity.ShipmentAlias;
import com.echothree.model.data.shipment.server.entity.ShipmentType;
import com.echothree.model.data.shipment.server.factory.ShipmentAliasFactory;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BasePaginatedMultipleEntitiesCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import java.util.Collection;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class GetShipmentAliasesCommand
        extends BasePaginatedMultipleEntitiesCommand<ShipmentAlias, GetShipmentAliasesForm> {

    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("ShipmentTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ShipmentName", FieldType.ENTITY_NAME, true, null, null)
        );
    }

    @Inject
    ShipmentControl shipmentControl;

    /** Creates a new instance of GetShipmentAliasesCommand */
    public GetShipmentAliasesCommand() {
        super(null, FORM_FIELD_DEFINITIONS, true);
    }

    @Override
    protected CommandSecurityDefinition getCommandSecurityDefinition() {
        return new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(ShipmentAliasUtil.getInstance().getSecurityRoleGroupNameByShipmentTypeSpec(form), SecurityRoles.List.name())
                ))
        ));
    }

    private Shipment shipment;

    @Override
    protected void handleForm() {
        var shipmentTypeName = form.getShipmentTypeName();
        var shipmentType = shipmentControl.getShipmentTypeByName(shipmentTypeName);

        if(shipmentType != null) {
            var shipmentName = form.getShipmentName();

            shipment = shipmentControl.getShipmentByName(shipmentType, shipmentName);

            if(shipment == null) {
                addExecutionError(ExecutionErrors.UnknownShipmentName.name(), shipmentTypeName, shipmentName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownShipmentTypeName.name(), shipmentTypeName);
        }
    }

    @Override
    protected Long getTotalEntities() {
        return hasExecutionErrors() ? null : shipmentControl.countShipmentAliasesByShipment(shipment);
    }

    @Override
    protected Collection<ShipmentAlias> getEntities() {
        return hasExecutionErrors() ? null : shipmentControl.getShipmentAliasesByShipment(shipment);
    }

    @Override
    protected BaseResult getResult(Collection<ShipmentAlias> entities) {
        var result = ShipmentResultFactory.getGetShipmentAliasesResult();

        if(entities != null) {
            var userVisit = getUserVisit();

            result.setShipment(shipmentControl.getShipmentTransfer(userVisit, shipment));

            if(session.hasLimit(ShipmentAliasFactory.class)) {
                result.setShipmentAliasCount(getTotalEntities());
            }

            result.setShipmentAliases(shipmentControl.getShipmentAliasTransfers(userVisit, entities));
        }

        return result;
    }

}
