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

import com.echothree.control.user.customer.common.form.GetCustomersForm;
import com.echothree.control.user.customer.common.result.CustomerResultFactory;
import com.echothree.model.control.customer.server.control.CustomerControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.customer.server.entity.Customer;
import com.echothree.model.data.customer.server.factory.CustomerFactory;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.server.control.BasePaginatedMultipleEntitiesCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Collection;
import java.util.List;
import javax.enterprise.context.Dependent;

@Dependent
public class GetCustomersCommand
        extends BasePaginatedMultipleEntitiesCommand<Customer, GetCustomersForm> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.Customer.name(), SecurityRoles.List.name())
                ))
        ));

        FORM_FIELD_DEFINITIONS = List.of();
    }

    /** Creates a new instance of GetCustomersCommand */
    public GetCustomersCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }

    @Override
    protected void handleForm() {
        // No form fields.
    }

    @Override
    protected Long getTotalEntities() {
        var customerControl = Session.getModelController(CustomerControl.class);

        return customerControl.countCustomers();
    }

    @Override
    protected Collection<Customer> getEntities() {
        var customerControl = Session.getModelController(CustomerControl.class);

        return customerControl.getCustomers();
    }

    @Override
    protected BaseResult getResult(Collection<Customer> entities) {
        var result = CustomerResultFactory.getGetCustomersResult();

        if(entities != null) {
            var customerControl = Session.getModelController(CustomerControl.class);

            if(session.hasLimit(CustomerFactory.class)) {
                result.setCustomerCount(getTotalEntities());
            }

            result.setCustomers(customerControl.getCustomerTransfers(getUserVisit(), entities));
        }

        return result;
    }

}
