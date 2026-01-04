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

package com.echothree.model.control.party.server.graphql;

import com.echothree.model.control.graphql.server.util.BaseGraphQl;
import com.echothree.model.data.party.server.entity.PartyRelationship;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;

@GraphQLDescription("party relationship object")
@GraphQLName("PartyRelationship")
public class PartyRelationshipObject
        implements BaseGraphQl {

    private final PartyRelationship partyRelationship; // Always Present
    
    public PartyRelationshipObject(PartyRelationship partyRelationship) {
        this.partyRelationship = partyRelationship;
    }

    @GraphQLField
    @GraphQLDescription("party relationship type")
    @GraphQLNonNull
    public PartyRelationshipTypeObject getPartyRelationshipType() {
        return new PartyRelationshipTypeObject(partyRelationship.getPartyRelationshipType());
    }

    @GraphQLField
    @GraphQLDescription("from party")
    public PartyObject getFromParty(final DataFetchingEnvironment env) {
        var fromParty = partyRelationship.getFromParty();

        return PartySecurityUtils.getHasPartyAccess(env, fromParty) ? new PartyObject(fromParty) : null;
    }

    @GraphQLField
    @GraphQLDescription("from role type")
    @GraphQLNonNull
    public RoleTypeObject getFromRoleType() {
        return new RoleTypeObject(partyRelationship.getFromRoleType());
    }

    @GraphQLField
    @GraphQLDescription("to party")
    public PartyObject getToParty(final DataFetchingEnvironment env) {
        var toParty = partyRelationship.getToParty();

        return PartySecurityUtils.getHasPartyAccess(env, toParty) ? new PartyObject(toParty) : null;
    }

    @GraphQLField
    @GraphQLDescription("to role type")
    @GraphQLNonNull
    public RoleTypeObject getToRoleType() {
        return new RoleTypeObject(partyRelationship.getToRoleType());
    }

}
