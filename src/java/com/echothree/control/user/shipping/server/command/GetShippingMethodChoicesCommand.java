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

package com.echothree.control.user.shipping.server.command;

import com.echothree.control.user.shipping.common.form.GetShippingMethodChoicesForm;
import com.echothree.control.user.shipping.common.result.ShippingResultFactory;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.shipment.server.control.ShipmentControl;
import com.echothree.model.control.shipping.server.control.ShippingControl;
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
public class GetShippingMethodChoicesCommand
        extends BaseSimpleCommand<GetShippingMethodChoicesForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.ShippingMethod.name(), SecurityRoles.Choices.name())
                        )))
                )));

        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ShipmentTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("DefaultShippingMethodChoice", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("AllowNullChoice", FieldType.BOOLEAN, true, null, null)
                ));
    }
    
    /** Creates a new instance of GetShippingMethodChoicesCommand */
    public GetShippingMethodChoicesCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var shipmentControl = Session.getModelController(ShipmentControl.class);
        var shippingControl = Session.getModelController(ShippingControl.class);
        var result = ShippingResultFactory.getGetShippingMethodChoicesResult();
        var shipmentTypeName = form.getShipmentTypeName();
        var shipmentType = shipmentTypeName == null? null: shipmentControl.getShipmentTypeByName(shipmentTypeName);
        
        if(shipmentTypeName == null || shipmentType != null) {
            var defaultShippingMethodChoice = form.getDefaultShippingMethodChoice();
            var allowNullChoice = Boolean.parseBoolean(form.getAllowNullChoice());
            
            if(shipmentType == null) {
                result.setShippingMethodChoices(shippingControl.getShippingMethodChoices(defaultShippingMethodChoice,
                        getPreferredLanguage(), allowNullChoice));
            } else {
                result.setShippingMethodChoices(shippingControl.getShippingMethodChoices(defaultShippingMethodChoice,
                        getPreferredLanguage(), allowNullChoice, shipmentType));
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownShipmentTypeName.name(), shipmentTypeName);
        }
        
        return result;
    }
    
}
