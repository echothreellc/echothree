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

import com.echothree.model.control.graphql.server.graphql.BaseObject;
import com.echothree.model.control.party.server.graphql.PartySecurityUtils;
import com.echothree.model.control.party.server.graphql.PartyTypeObject;
import com.echothree.model.data.contactlist.server.entity.PartyTypeContactList;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;

@GraphQLDescription("party type contact list object")
@GraphQLName("PartyTypeContactList")
public class PartyTypeContactListObject
        extends BaseObject {

    private final PartyTypeContactList partyTypeContactList; // Always Present

    public PartyTypeContactListObject(PartyTypeContactList partyTypeContactList) {
        this.partyTypeContactList = partyTypeContactList;
    }

    @GraphQLField
    @GraphQLDescription("party type")
    public PartyTypeObject getPartyType(final DataFetchingEnvironment env) {
        var partyType = partyTypeContactList.getPartyType();

        return PartySecurityUtils.getHasPartyTypeAccess(env) ? new PartyTypeObject(partyType) : null;
    }

    @GraphQLField
    @GraphQLDescription("contact list")
    public ContactListObject getContactList(final DataFetchingEnvironment env) {
        var contactList = partyTypeContactList.getContactList();

        return ContactListSecurityUtils.getHasContactListAccess(env) ? new ContactListObject(contactList) : null;
    }

    @GraphQLField
    @GraphQLDescription("add when created")
    @GraphQLNonNull
    public boolean getAddWhenCreated() {
        return partyTypeContactList.getAddWhenCreated();
    }

}
