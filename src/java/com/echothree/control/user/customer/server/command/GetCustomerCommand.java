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

package com.echothree.control.user.customer.server.command;

import com.echothree.control.user.core.common.spec.UniversalEntitySpec;
import com.echothree.control.user.customer.common.form.GetCustomerForm;
import com.echothree.control.user.customer.common.result.CustomerResultFactory;
import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.customer.server.control.CustomerControl;
import com.echothree.model.control.customer.server.logic.CustomerLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.logic.PartyLogic;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.wishlist.server.control.WishlistControl;
import com.echothree.model.data.customer.server.entity.Customer;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.command.SecurityResult;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSingleEntityCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.List;
import javax.enterprise.context.Dependent;

@Dependent
public class GetCustomerCommand
        extends BaseSingleEntityCommand<Customer, GetCustomerForm> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.CUSTOMER.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.Customer.name(), SecurityRoles.Review.name())
                ))
        ));

        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("CustomerName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("PartyName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EntityRef", FieldType.ENTITY_REF, false, null, null),
                new FieldDefinition("Uuid", FieldType.UUID, false, null, null)
        );
    }

    /** Creates a new instance of GetCustomerCommand */
    public GetCustomerCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }

    String customerName;
    String partyName;
    UniversalEntitySpec universalEntitySpec;
    int parameterCount;

    @Override
    public SecurityResult security() {
        var securityResult = super.security();

        customerName = form.getCustomerName();
        partyName = form.getPartyName();
        universalEntitySpec = form;
        parameterCount = (customerName == null ? 0 : 1) + (partyName == null ? 0 : 1) +
                EntityInstanceLogic.getInstance().countPossibleEntitySpecs(form);

        if(!canSpecifyParty() && parameterCount != 0) {
            securityResult = getInsufficientSecurityResult();
        }

        return securityResult;
    }

    @Override
    protected Customer getEntity() {
        Customer customer = null;

        if(parameterCount == 0) {
            var party = getParty();

            PartyLogic.getInstance().checkPartyType(this, party, PartyTypes.CUSTOMER.name());

            if(!hasExecutionErrors()) {
                var customerControl = Session.getModelController(CustomerControl.class);

                customer = customerControl.getCustomer(party);
            }
        } else {
            customer = CustomerLogic.getInstance().getCustomerByName(this, customerName, partyName, universalEntitySpec);
        }

        if(customer != null) {
            sendEvent(customer.getPartyPK(), EventTypes.READ, null, null, getPartyPK());
        }

        return customer;
    }

    @Override
    protected BaseResult getResult(Customer customer) {
        var result = CustomerResultFactory.getGetCustomerResult();

        if(customer != null) {
            var customerControl = Session.getModelController(CustomerControl.class);
            var userVisit = getUserVisit();
            var companyParty = getCompanyParty();

            result.setCustomer(customerControl.getCustomerTransfer(userVisit, customer));

            if(companyParty != null) {
                var wishlistControl = Session.getModelController(WishlistControl.class);

                result.setWishlists(wishlistControl.getWishlistTransfers(userVisit, companyParty, customer.getParty()));
            }
        }

        return result;
    }

}
