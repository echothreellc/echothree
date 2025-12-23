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

package com.echothree.model.control.user.server.graphql;

import com.echothree.model.control.graphql.server.graphql.BaseObject;
import com.echothree.model.control.party.server.graphql.PartyObject;
import com.echothree.model.control.party.server.graphql.PartyRelationshipObject;
import com.echothree.model.control.party.server.graphql.PartySecurityUtils;
import com.echothree.model.data.user.server.entity.UserKey;
import com.echothree.model.data.user.server.entity.UserKeyDetail;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;

@GraphQLDescription("user key object")
@GraphQLName("UserKey")
public class UserKeyObject
        extends BaseObject {
    
    private final UserKey userKey; // Always Present
    
    public UserKeyObject(UserKey userKey) {
        this.userKey = userKey;
    }

    private UserKeyDetail userKeyDetail; // Optional, use getUserKeyDetail()
    
    private UserKeyDetail getUserKeyDetail() {
        if(userKeyDetail == null) {
            userKeyDetail = userKey.getLastDetail();
        }
        
        return userKeyDetail;
    }
    
    @GraphQLField
    @GraphQLDescription("user visit group name")
    @GraphQLNonNull
    public String getUserKeyName() {
        return getUserKeyDetail().getUserKeyName();
    }

    @GraphQLField
    @GraphQLDescription("party")
    public PartyObject getParty(final DataFetchingEnvironment env) {
        var party = getUserKeyDetail().getParty();

        return party != null && PartySecurityUtils.getHasPartyAccess(env, party) ?
                new PartyObject(party) : null;
    }

    @GraphQLField
    @GraphQLDescription("party relationship")
    public PartyRelationshipObject getPartyRelationship(final DataFetchingEnvironment env) {
        var partyRelationship = getUserKeyDetail().getPartyRelationship();

        return partyRelationship != null && PartySecurityUtils.getHasPartyRelationshipAccess(env) ?
                new PartyRelationshipObject(partyRelationship) : null;
    }


}
