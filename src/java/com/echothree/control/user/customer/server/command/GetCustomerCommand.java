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

package com.echothree.control.user.customer.server.command;

import com.echothree.control.user.customer.common.form.GetCustomerForm;
import com.echothree.control.user.customer.common.result.CustomerResultFactory;
import com.echothree.control.user.customer.common.result.GetCustomerResult;
import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.customer.server.control.CustomerControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.wishlist.server.control.WishlistControl;
import com.echothree.model.data.customer.server.entity.Customer;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GetCustomerCommand
        extends BaseSimpleCommand<GetCustomerForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("CustomerName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("PartyName", FieldType.ENTITY_NAME, false, null, null)
                ));
    }
    
    /** Creates a new instance of GetCustomerCommand */
    public GetCustomerCommand(UserVisitPK userVisitPK, GetCustomerForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected BaseResult execute() {
        GetCustomerResult result = CustomerResultFactory.getGetCustomerResult();
        String customerName = form.getCustomerName();
        String partyName = form.getPartyName();
        int parameterCount = (customerName == null? 0: 1) + (partyName == null? 0: 1);
        
        if(parameterCount == 1) {
            var customerControl = (CustomerControl)Session.getModelController(CustomerControl.class);
            Customer customer = null;
            Party party = null;
            
            if(customerName != null) {
                customer = customerControl.getCustomerByName(customerName);
                
                if(customer != null) {
                    party = customer.getParty();
                } else {
                    addExecutionError(ExecutionErrors.UnknownCustomerName.name(), customerName);
                }
            } else if(partyName != null) {
                var partyControl = (PartyControl)Session.getModelController(PartyControl.class);
                
                party = partyControl.getPartyByName(partyName);
                
                if(party != null) {
                    if(party.getLastDetail().getPartyType().getPartyTypeName().equals(PartyTypes.CUSTOMER.name())) {
                    customer = customerControl.getCustomer(party);
                    } else {
                        addExecutionError(ExecutionErrors.InvalidPartyType.name());
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownPartyName.name(), partyName);
                }
            }
            
            if(customer != null) {
                UserVisit userVisit = getUserVisit();
                Party companyParty = getCompanyParty();

                result.setCustomer(customerControl.getCustomerTransfer(userVisit, customer));

                if(companyParty != null) {
                    var wishlistControl = (WishlistControl)Session.getModelController(WishlistControl.class);

                    result.setWishlists(wishlistControl.getWishlistTransfers(userVisit, companyParty, party));
                }

                sendEventUsingNames(party.getPrimaryKey(), EventTypes.READ.name(), null, null, getPartyPK());
            }
        } else {
            addExecutionError(ExecutionErrors.InvalidParameterCount.name());
        }
        
        return result;
    }
    
}
