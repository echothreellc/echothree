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

import com.echothree.control.user.carrier.common.form.CreatePartyCarrierAccountForm;
import com.echothree.model.control.carrier.server.control.CarrierControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.carrier.server.entity.Carrier;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CreatePartyCarrierAccountCommand
        extends BaseSimpleCommand<CreatePartyCarrierAccountForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.PartyCarrierAccount.name(), SecurityRoles.Create.name())
                        )))
                )));

        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("PartyName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("CarrierName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("Account", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("AlwaysUseThirdPartyBilling", FieldType.BOOLEAN, true, null, null)
                ));
    }
    
    /** Creates a new instance of CreatePartyCarrierAccountCommand */
    public CreatePartyCarrierAccountCommand(UserVisitPK userVisitPK, CreatePartyCarrierAccountForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }

    @Override
    protected BaseResult execute() {
        var partyControl = (PartyControl)Session.getModelController(PartyControl.class);
        String partyName = form.getPartyName();
        Party party = partyControl.getPartyByName(partyName);

        if(party != null) {
            var carrierControl = (CarrierControl)Session.getModelController(CarrierControl.class);
            String carrierName = form.getCarrierName();
            Carrier carrier = carrierControl.getCarrierByName(carrierName);

            if(carrier != null) {
                String accountValidationPattern = carrier.getAccountValidationPattern();
                String account = form.getAccount();

                if(accountValidationPattern != null) {
                    Pattern pattern = Pattern.compile(accountValidationPattern);
                    Matcher m = pattern.matcher(account);

                    if(!m.matches()) {
                        addExecutionError(ExecutionErrors.InvalidAccount.name(), account);
                    }
                }

                if(!hasExecutionErrors()) {
                    Party carrierParty = carrier.getParty();

                    if(carrierControl.getPartyCarrierAccount(party, carrierParty) == null) {
                        Boolean alwaysUseThirdPartyBilling = Boolean.valueOf(form.getAlwaysUseThirdPartyBilling());

                        carrierControl.createPartyCarrierAccount(party, carrierParty, account, alwaysUseThirdPartyBilling, getPartyPK());
                    } else {
                        addExecutionError(ExecutionErrors.DuplicatePartyCarrierAccount.name(), partyName, carrierName);
                    }
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownCarrierName.name(), carrierName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownPartyName.name(), partyName);
        }

        return null;
    }
    
}
