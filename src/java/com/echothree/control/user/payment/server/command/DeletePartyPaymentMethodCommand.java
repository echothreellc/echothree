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

package com.echothree.control.user.payment.server.command;

import com.echothree.control.user.payment.common.form.DeletePartyPaymentMethodForm;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.payment.server.control.PartyPaymentMethodControl;
import com.echothree.model.control.payment.server.logic.PartyPaymentMethodLogic;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.command.SecurityResult;
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

public class DeletePartyPaymentMethodCommand
        extends BaseSimpleCommand<DeletePartyPaymentMethodForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.CUSTOMER.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.PartyPaymentMethod.name(), SecurityRoles.Delete.name())
                        )))
                )));

        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("PartyPaymentMethodName", FieldType.ENTITY_NAME, true, null, null)
                ));
    }
    
    /** Creates a new instance of DeletePartyPaymentMethodCommand */
    public DeletePartyPaymentMethodCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }

    @Override
    protected SecurityResult security() {
        // Execute the standard security check using COMMAND_SECURITY_DEFINITION.
        var securityResult = super.security();

        // If that passed, continue checking the executing Party vs. the Party owning the
        // PartyPaymentMethod.
        if(securityResult == null) {
            var party = getParty();
            var partyTypeName = party.getLastDetail().getPartyType().getPartyTypeName();

            // If the executing Party is a CUSTOMER...
            if(partyTypeName.equals(PartyTypes.CUSTOMER.name())) {
                var partyPaymentMethodControl = Session.getModelController(PartyPaymentMethodControl.class);
                var partyPaymentMethodName = form.getPartyPaymentMethodName();
                var partyPaymentMethod = partyPaymentMethodControl.getPartyPaymentMethodByNameForUpdate(partyPaymentMethodName);

                if(partyPaymentMethod != null) {
                    // ...and the PartyPaymentMethod isn't for the executing Party, return an
                    // InsufficientSecurity error.
                    if(!partyPaymentMethod.getLastDetail().getParty().equals(party)) {
                        securityResult = getInsufficientSecurityResult();
                    }
                }
            }
        }

        return securityResult;
    }

    @Override
    protected BaseResult execute() {
        var partyPaymentMethodName = form.getPartyPaymentMethodName();

        PartyPaymentMethodLogic.getInstance().deletePartyPaymentMethod(this, partyPaymentMethodName, getPartyPK());

        return null;
    }
    
}
