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
import com.echothree.model.data.contactlist.server.entity.CustomerTypeContactListGroup;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;

@GraphQLDescription("customer type contact list group object")
@GraphQLName("CustomerTypeContactListGroup")
public class CustomerTypeContactListGroupObject
        extends BaseObject {

    private final CustomerTypeContactListGroup customerTypeContactListGroup; // Always Present

    public CustomerTypeContactListGroupObject(CustomerTypeContactListGroup customerTypeContactListGroup) {
        this.customerTypeContactListGroup = customerTypeContactListGroup;
    }

    @GraphQLField
    @GraphQLDescription("customer type")
    public CustomerTypeObject getCustomerType(final DataFetchingEnvironment env) {
        var customerType = customerTypeContactListGroup.getCustomerType();

        return customerType == null ? null : (CustomerSecurityUtils.getHasCustomerTypeAccess(env) ? new CustomerTypeObject(customerType) : null);
    }

    @GraphQLField
    @GraphQLDescription("contact list group")
    public ContactListGroupObject getContactListGroup(final DataFetchingEnvironment env) {
        var contactListGroup = customerTypeContactListGroup.getContactListGroup();

        return contactListGroup == null ? null : (ContactListSecurityUtils.getHasContactListGroupAccess(env) ? new ContactListGroupObject(contactListGroup) : null);
    }

    @GraphQLField
    @GraphQLDescription("add when created")
    @GraphQLNonNull
    public boolean getAddWhenCreated() {
        return customerTypeContactListGroup.getAddWhenCreated();
    }


}
