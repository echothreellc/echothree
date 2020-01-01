// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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

import com.echothree.model.control.graphql.server.util.GraphQlContext;
import com.echothree.model.control.party.server.graphql.PartyObject;
import com.echothree.model.data.user.server.entity.UserSession;
import com.echothree.util.server.string.DateUtils;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.schema.DataFetchingEnvironment;
import graphql.annotations.annotationTypes.GraphQLNonNull;

@GraphQLDescription("user session object")
@GraphQLName("UserSession")
public class UserSessionObject  {
    
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

//    @GraphQLField
//    @GraphQLDescription("party relationship")
//    public PartyRelationshipObject getPartyRelationship() {
//        return new PartyRelationshipObject(userSession.getPartyRelationship());
//    }

    @GraphQLField
    @GraphQLDescription("unformatted password verified time")
    public Long getUnformattedPasswordVerifiedTime() {
        Long passwordVerifiedTime = userSession.getPasswordVerifiedTime();
        
        return passwordVerifiedTime == null ? null : passwordVerifiedTime;
    }
    
    @GraphQLField
    @GraphQLDescription("password verified time")
    public String getPasswordVerifiedTime(final DataFetchingEnvironment env) {
        GraphQlContext context = env.getContext();
        Long passwordVerifiedTime = userSession.getPasswordVerifiedTime();

        return passwordVerifiedTime == null ? null : DateUtils.getInstance().formatTypicalDateTime(context.getUserVisit(), passwordVerifiedTime);
    }
    
}
