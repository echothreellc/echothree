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

package com.echothree.control.user.carrier.server.command;

import com.echothree.control.user.carrier.common.form.DeleteCarrierServiceOptionForm;
import com.echothree.model.control.carrier.server.control.CarrierControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
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
public class DeleteCarrierServiceOptionCommand
        extends BaseSimpleCommand<DeleteCarrierServiceOptionForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.CarrierServiceOption.name(), SecurityRoles.Delete.name())
                        )))
                )));

        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("CarrierName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("CarrierServiceName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("CarrierOptionName", FieldType.ENTITY_NAME, true, null, null)
                ));
    }
    
    /** Creates a new instance of DeleteCarrierServiceOptionCommand */
    public DeleteCarrierServiceOptionCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var carrierControl = Session.getModelController(CarrierControl.class);
        var carrierName = form.getCarrierName();
        var carrier = carrierControl.getCarrierByName(carrierName);
        
        if(carrier != null) {
            var carrierParty = carrier.getParty();
            var carrierServiceName = form.getCarrierServiceName();
            var carrierService = carrierControl.getCarrierServiceByName(carrierParty, carrierServiceName);
            
            if(carrierService != null) {
                var carrierOptionName = form.getCarrierOptionName();
                var carrierOption = carrierControl.getCarrierOptionByName(carrierParty, carrierOptionName);
                
                if(carrierOption != null) {
                    var carrierServiceOption = carrierControl.getCarrierServiceOptionForUpdate(carrierService, carrierOption);
                    
                    if(carrierServiceOption != null) {
                        carrierControl.deleteCarrierServiceOption(carrierServiceOption, getPartyPK());
                    } else {
                        addExecutionError(ExecutionErrors.UnknownCarrierServiceOption.name(), carrierName, carrierServiceName, carrierOptionName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownCarrierOptionName.name(), carrierName, carrierOptionName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownCarrierServiceName.name(), carrierName, carrierServiceName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownCarrierName.name(), carrierName);
        }
        
        return null;
    }
    
}
