// --------------------------------------------------------------------------------
// Copyright 2002-2021 Echo Three, LLC
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

package com.echothree.model.control.customer.server.graphql;

import com.echothree.model.control.customer.server.control.CustomerControl;
import com.echothree.model.control.party.server.graphql.BasePartyObject;
import com.echothree.model.data.customer.server.entity.Customer;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;

@GraphQLDescription("customer object")
@GraphQLName("Customer")
public class CustomerObject
        extends BasePartyObject {

    public CustomerObject(Party party) {
        super(party);
    }

    private Customer customer;  // Optional, use getCustomer()

    protected Customer getCustomer() {
        if(customer == null) {
            var customerControl = Session.getModelController(CustomerControl.class);

            customer = customerControl.getCustomer(party);
        }

        return customer;
    }

    @GraphQLField
    @GraphQLDescription("customer name")
    @GraphQLNonNull
    public String getCustomerName() {
        return getCustomer().getCustomerName();
    }

}
