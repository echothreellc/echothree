// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
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
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.payment.server.PaymentControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.payment.server.entity.PartyPaymentMethod;
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

public class DeletePartyPaymentMethodCommand
        extends BaseSimpleCommand<DeletePartyPaymentMethodForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_UTILITY, null),
                new PartyTypeDefinition(PartyConstants.PartyType_CUSTOMER, null),
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.PartyPaymentMethod.name(), SecurityRoles.Delete.name())
                        )))
                )));

        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("PartyPaymentMethodName", FieldType.ENTITY_NAME, true, null, null)
                ));
    }
    
    /** Creates a new instance of DeletePartyPaymentMethodCommand */
    public DeletePartyPaymentMethodCommand(UserVisitPK userVisitPK, DeletePartyPaymentMethodForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        PaymentControl paymentControl = (PaymentControl)Session.getModelController(PaymentControl.class);
        String partyPaymentMethodName = form.getPartyPaymentMethodName();
        PartyPaymentMethod partyPaymentMethod = paymentControl.getPartyPaymentMethodByNameForUpdate(partyPaymentMethodName);
        
        if(partyPaymentMethod != null) {
            Party party = getParty();
            String partyTypeName = party.getLastDetail().getPartyType().getPartyTypeName();

            // If the executing Party is a CUSTOMER, and the PartyPaymentMethod isn't for the executing Party,
            // return a UnknownPartyPaymentMethodName error.
            if(partyTypeName.equals(PartyConstants.PartyType_CUSTOMER)) {
                if(!partyPaymentMethod.getLastDetail().getParty().equals(party)) {
                    partyPaymentMethod = null;
                }
            }
        }

        if(partyPaymentMethod == null) {
            addExecutionError(ExecutionErrors.UnknownPartyPaymentMethodName.name(), partyPaymentMethodName);
        } else {
            // TODO: Check to see if this paymemnt method is in use on any open orders,
            // or orders that currently are allowing returns to be made against them.
            // If that's the case, the PPM shouldn't be deleted.

            paymentControl.deletePartyPaymentMethod(partyPaymentMethod, getPartyPK());
        }
        
        return null;
    }
    
}
