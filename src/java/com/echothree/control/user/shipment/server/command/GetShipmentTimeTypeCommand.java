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

import com.echothree.control.user.shipment.common.form.GetShipmentTimeTypeForm;
import com.echothree.control.user.shipment.common.result.ShipmentResultFactory;
import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.shipment.server.ShipmentControl;
import com.echothree.model.data.user.common.pk.UserVisitPK;
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
public class GetShipmentTimeTypeCommand
        extends BaseSimpleCommand<GetShipmentTimeTypeForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.ShipmentTimeType.name(), SecurityRoles.Review.name())
                        )))
                )));
        
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ShipmentTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ShipmentTimeTypeName", FieldType.ENTITY_NAME, true, null, null)
                ));
    }
    
    /** Creates a new instance of GetShipmentTimeTypeCommand */
    public GetShipmentTimeTypeCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected BaseResult execute() {
        var shipmentControl = Session.getModelController(ShipmentControl.class);
        var result = ShipmentResultFactory.getGetShipmentTimeTypeResult();
        var shipmentTypeName = form.getShipmentTypeName();
        var shipmentType = shipmentControl.getShipmentTypeByName(shipmentTypeName);

        if(shipmentType != null) {
            var shipmentTimeTypeName = form.getShipmentTimeTypeName();
            var shipmentTimeType = shipmentControl.getShipmentTimeTypeByName(shipmentType, shipmentTimeTypeName);

            if(shipmentTimeType != null) {
                result.setShipmentTimeType(shipmentControl.getShipmentTimeTypeTransfer(getUserVisit(), shipmentTimeType));

                sendEvent(shipmentTimeType.getPrimaryKey(), EventTypes.READ, null, null, getPartyPK());
            } else {
                addExecutionError(ExecutionErrors.UnknownShipmentTimeTypeName.name(), shipmentTimeTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownShipmentTypeName.name(), shipmentTypeName);
        }

        return result;
    }
    
}
