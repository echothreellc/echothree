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

package com.echothree.model.control.contactlist.server.graphql;

import com.echothree.model.control.customer.server.graphql.CustomerSecurityUtils;
import com.echothree.model.control.customer.server.graphql.CustomerTypeObject;
import com.echothree.model.control.graphql.server.graphql.BaseObject;
import com.echothree.model.data.contactlist.server.entity.CustomerTypeContactList;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;

@GraphQLDescription("customer type contact list object")
@GraphQLName("CustomerTypeContactList")
public class CustomerTypeContactListObject
        extends BaseObject {

    private final CustomerTypeContactList customerTypeContactList; // Always Present

    public CustomerTypeContactListObject(CustomerTypeContactList customerTypeContactList) {
        this.customerTypeContactList = customerTypeContactList;
    }

    @GraphQLField
    @GraphQLDescription("customer type")
    public CustomerTypeObject getCustomerType(final DataFetchingEnvironment env) {
        var customerType = customerTypeContactList.getCustomerType();

        return CustomerSecurityUtils.getHasCustomerTypeAccess(env) ? new CustomerTypeObject(customerType) : null;
    }

    @GraphQLField
    @GraphQLDescription("contact list")
    public ContactListObject getContactList(final DataFetchingEnvironment env) {
        var contactList = customerTypeContactList.getContactList();

        return ContactListSecurityUtils.getHasContactListAccess(env) ? new ContactListObject(contactList) : null;
    }

    @GraphQLField
    @GraphQLDescription("add when created")
    @GraphQLNonNull
    public boolean getAddWhenCreated() {
        return customerTypeContactList.getAddWhenCreated();
    }

}
