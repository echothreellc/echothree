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

package com.echothree.control.user.carrier.server.command;

import com.echothree.control.user.carrier.common.form.GetPartyCarrierAccountForm;
import com.echothree.control.user.carrier.common.result.CarrierResultFactory;
import com.echothree.control.user.carrier.common.result.GetPartyCarrierAccountResult;
import com.echothree.model.control.carrier.server.CarrierControl;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.party.server.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.carrier.server.entity.Carrier;
import com.echothree.model.data.carrier.server.entity.PartyCarrierAccount;
import com.echothree.model.data.party.server.entity.Party;
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

public class GetPartyCarrierAccountCommand
        extends BaseSimpleCommand<GetPartyCarrierAccountForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_UTILITY, null),
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.PartyCarrierAccount.name(), SecurityRoles.Review.name())
                        )))
                )));

        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("PartyName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("CarrierName", FieldType.ENTITY_NAME, true, null, null)
                ));
    }
    
    /** Creates a new instance of GetPartyCarrierAccountCommand */
    public GetPartyCarrierAccountCommand(UserVisitPK userVisitPK, GetPartyCarrierAccountForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var partyControl = (PartyControl)Session.getModelController(PartyControl.class);
        GetPartyCarrierAccountResult result = CarrierResultFactory.getGetPartyCarrierAccountResult();
        String partyName = form.getPartyName();
        Party party = partyControl.getPartyByName(partyName);

        if(party != null) {
            var carrierControl = (CarrierControl)Session.getModelController(CarrierControl.class);
            String carrierName = form.getCarrierName();
            Carrier carrier = carrierControl.getCarrierByName(carrierName);

            if(carrier != null) {
                PartyCarrierAccount partyCarrierAccount = carrierControl.getPartyCarrierAccount(party, carrier.getParty());

                if(partyCarrierAccount != null) {
                    result.setPartyCarrierAccount(carrierControl.getPartyCarrierAccountTransfer(getUserVisit(), partyCarrierAccount));
                } else {
                    addExecutionError(ExecutionErrors.UnknownPartyCarrierAccount.name(), partyName, carrierName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownCarrierName.name(), carrierName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownPartyName.name(), partyName);
        }

        return result;
    }
    
}
