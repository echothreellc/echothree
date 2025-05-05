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

import com.echothree.control.user.shipping.common.form.DeleteShippingMethodCarrierServiceForm;
import com.echothree.model.control.carrier.server.control.CarrierControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.shipping.server.control.ShippingControl;
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

public class DeleteShippingMethodCarrierServiceCommand
        extends BaseSimpleCommand<DeleteShippingMethodCarrierServiceForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.ShippingMethodCarrierService.name(), SecurityRoles.Delete.name())
                        )))
                )));

        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ShippingMethodName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("CarrierName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("CarrierServiceName", FieldType.ENTITY_NAME, true, null, null)
                ));
    }
    
    /** Creates a new instance of DeleteShippingMethodCarrierServiceCommand */
    public DeleteShippingMethodCarrierServiceCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var shippingControl = Session.getModelController(ShippingControl.class);
        var shippingMethodName = form.getShippingMethodName();
        var shippingMethod = shippingControl.getShippingMethodByName(shippingMethodName);
        
        if(shippingMethod != null) {
            var carrierControl = Session.getModelController(CarrierControl.class);
            var carrierName = form.getCarrierName();
            var carrier = carrierControl.getCarrierByName(carrierName);
            
            if(carrier != null) {
                var carrierParty = carrier.getParty();
                var carrierServiceName = form.getCarrierServiceName();
                var carrierService = carrierControl.getCarrierServiceByNameForUpdate(carrierParty, carrierServiceName);
                
                if(carrierService != null) {
                    var shippingMethodCarrierService = shippingControl.getShippingMethodCarrierServiceForUpdate(shippingMethod, carrierService);
                    
                    if(shippingMethodCarrierService != null) {
                        shippingControl.deleteShippingMethodCarrierService(shippingMethodCarrierService, getPartyPK());
                    } else {
                        addExecutionError(ExecutionErrors.UnknownShippingMethodCarrierService.name(), shippingMethodName, carrierName, carrierServiceName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownCarrierServiceName.name(), carrierName, carrierServiceName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownCarrierName.name(), carrierName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownShippingMethodName.name(), shippingMethodName);
        }
        
        return null;
    }
    
}
