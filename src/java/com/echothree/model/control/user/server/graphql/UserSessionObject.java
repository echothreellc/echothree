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

import com.echothree.model.control.graphql.server.graphql.TimeObject;
import com.echothree.model.control.graphql.server.util.BaseGraphQl;
import com.echothree.model.control.party.server.graphql.PartyObject;
import com.echothree.model.control.party.server.graphql.PartyRelationshipObject;
import com.echothree.model.control.party.server.graphql.PartySecurityUtils;
import com.echothree.model.data.user.server.entity.UserSession;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;

@GraphQLDescription("user session object")
@GraphQLName("UserSession")
public class UserSessionObject
        implements BaseGraphQl {
    
    private final UserSession userSession; // Always Present
    
    public UserSessionObject(UserSession userSession) {
        this.userSession = userSession;
    }

    @GraphQLField
    @GraphQLDescription("user visit")
    @GraphQLNonNull
    public UserVisitObject getUserVisit() {
        return new UserVisitObject(userSession.getUserVisit());
    }

    @GraphQLField
    @GraphQLDescription("party")
    @GraphQLNonNull
    public PartyObject getParty() {
        return new PartyObject(userSession.getParty());
    }

    @GraphQLField
    @GraphQLDescription("party relationship")
    public PartyRelationshipObject getPartyRelationship(final DataFetchingEnvironment env) {
        return PartySecurityUtils.getHasPartyRelationshipAccess(env) ? new PartyRelationshipObject(userSession.getPartyRelationship()) : null;
    }

    @GraphQLField
    @GraphQLDescription("password verified time")
    public TimeObject getIdentityVerifiedTime(final DataFetchingEnvironment env) {
        var identityVerifiedTime = userSession.getIdentityVerifiedTime();

        return identityVerifiedTime == null ? null : new TimeObject(identityVerifiedTime);
    }
    
}
