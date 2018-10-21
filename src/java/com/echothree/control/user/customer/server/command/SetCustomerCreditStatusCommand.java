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

package com.echothree.control.user.customer.server.command;

import com.echothree.control.user.customer.remote.form.SetCustomerCreditStatusForm;
import com.echothree.model.control.customer.server.CustomerControl;
import com.echothree.model.control.party.server.logic.PartyChainLogic;
import com.echothree.model.data.customer.server.entity.Customer;
import com.echothree.model.data.party.remote.pk.PartyPK;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.user.remote.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.remote.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SetCustomerCreditStatusCommand
        extends BaseSimpleCommand<SetCustomerCreditStatusForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("CustomerName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("CustomerCreditStatusChoice", FieldType.ENTITY_NAME, true, null, null)
                ));
    }
    
    /** Creates a new instance of SetCustomerCreditStatusCommand */
    public SetCustomerCreditStatusCommand(UserVisitPK userVisitPK, SetCustomerCreditStatusForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        CustomerControl customerControl = (CustomerControl)Session.getModelController(CustomerControl.class);
        String customerName = form.getCustomerName();
        Customer customer = customerControl.getCustomerByName(customerName);
        
        if(customer != null) {
            String customerCreditStatusChoice = form.getCustomerCreditStatusChoice();
            Party party = customer.getParty();
            PartyPK updatedBy = getPartyPK();
            
            customerControl.setCustomerCreditStatus(this, party, customerCreditStatusChoice, updatedBy);
            
            if(!hasExecutionErrors()) {
                // ExecutionErrorAccumulator is passed in as null so that an Exception will be thrown if there is an error.
                PartyChainLogic.getInstance().createPartyCreditStatusChangedChainInstance(null, party, updatedBy);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownCustomerName.name(), customerName);
        }
        
        return null;
    }
    
}
