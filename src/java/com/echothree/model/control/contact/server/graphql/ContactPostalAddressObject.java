// --------------------------------------------------------------------------------
// Copyright 2002-2024 Echo Three, LLC
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

package com.echothree.model.control.contact.server.graphql;

import com.echothree.model.data.contact.server.entity.ContactPostalAddress;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;

@GraphQLDescription("contact postal address object")
@GraphQLName("ContactPostalAddress")
public class ContactPostalAddressObject
        implements ContactMechanismInterface {

    private final ContactPostalAddress contactPostalAddress; // Always Present

    public ContactPostalAddressObject(ContactPostalAddress contactPostalAddress) {
        this.contactPostalAddress = contactPostalAddress;
    }
    
    @GraphQLField
    @GraphQLDescription("address 1")
    @GraphQLNonNull
    public String getAddress1() {
        return contactPostalAddress.getAddress1();
    }

}
