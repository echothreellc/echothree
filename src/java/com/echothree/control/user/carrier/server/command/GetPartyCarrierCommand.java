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

package com.echothree.control.user.carrier.server.command;

import com.echothree.control.user.carrier.common.form.GetPartyCarrierForm;
import com.echothree.control.user.carrier.common.result.CarrierResultFactory;
import com.echothree.model.control.carrier.server.control.CarrierControl;
import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.logic.PartyLogic;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.carrier.server.entity.PartyCarrier;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSingleEntityCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class GetPartyCarrierCommand
        extends BaseSingleEntityCommand<PartyCarrier, GetPartyCarrierForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.PartyCarrier.name(), SecurityRoles.Review.name())
                ))
        ));
        
        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("PartyName", FieldType.HOST_NAME, true, null, null),
                new FieldDefinition("CarrierName", FieldType.ENTITY_NAME, true, null, null)
        );
    }
    
    @Inject
    CarrierControl carrierControl;

    @Inject
    PartyLogic partyLogic;

    /** Creates a new instance of GetPartyCarrierCommand */
    public GetPartyCarrierCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected PartyCarrier getEntity() {
        PartyCarrier partyCarrier = null;
        var partyName = form.getPartyName();
        var party = partyLogic.getPartyByName(this, partyName);

        if(!hasExecutionErrors()) {
            var carrierName = form.getCarrierName();
            var carrier = carrierControl.getCarrierByName(carrierName);

            if(carrier != null) {
                partyCarrier = carrierControl.getPartyCarrier(party, carrier.getParty());

                if(partyCarrier == null) {
                    addExecutionError(ExecutionErrors.UnknownPartyCarrier.name(), partyName, carrierName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownCarrierName.name(), carrierName);
            }
        }

        return partyCarrier;
    }

    @Override
    protected BaseResult getResult(PartyCarrier partyCarrier) {
        var result = CarrierResultFactory.getGetPartyCarrierResult();

        if(partyCarrier != null) {
            result.setPartyCarrier(carrierControl.getPartyCarrierTransfer(getUserVisit(), partyCarrier));
        }

        return result;
    }
    
}
