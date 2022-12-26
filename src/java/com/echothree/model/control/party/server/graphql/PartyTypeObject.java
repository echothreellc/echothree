// --------------------------------------------------------------------------------
// Copyright 2002-2023 Echo Three, LLC
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
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.sequence.server.graphql.SequenceSecurityUtils;
import com.echothree.model.control.sequence.server.graphql.SequenceTypeObject;
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.data.party.server.entity.PartyType;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;

@GraphQLDescription("party type object")
@GraphQLName("PartyType")
public class PartyTypeObject
        extends BaseGraphQl {

    private final PartyType partyType; // Always Present
    
    public PartyTypeObject(PartyType partyType) {
        this.partyType = partyType;
    }
    
    @GraphQLField
    @GraphQLDescription("party type name")
    @GraphQLNonNull
    public String getPartyTypeName() {
        return partyType.getPartyTypeName();
    }
    
    public PartyTypeObject getParentPartyType() {
        return new PartyTypeObject(partyType.getParentPartyType());
    }
    
    @GraphQLField
    @GraphQLDescription("billing account sequence type")
    public SequenceTypeObject getBillingAccountSequenceType(final DataFetchingEnvironment env) {
        return SequenceSecurityUtils.getInstance().getHasSequenceTypeAccess(env) ? new SequenceTypeObject(partyType.getBillingAccountSequenceType()) : null;
    }

    @GraphQLField
    @GraphQLDescription("allow user logins")
    @GraphQLNonNull
    public Boolean getAllowUserLogins() {
        return partyType.getAllowUserLogins();
    }

    @GraphQLField
    @GraphQLDescription("allow party aliases")
    @GraphQLNonNull
    public Boolean getAllowPartyAliases() {
        return partyType.getAllowPartyAliases();
    }

    @GraphQLField
    @GraphQLDescription("is default")
    @GraphQLNonNull
    public Boolean getIsDefault() {
        return partyType.getIsDefault();
    }

    @GraphQLField
    @GraphQLDescription("sort order")
    @GraphQLNonNull
    public Integer getSortOrder() {
        return partyType.getSortOrder();
    }

    @GraphQLField
    @GraphQLDescription("description")
    @GraphQLNonNull
    public String getDescription(final DataFetchingEnvironment env) {
        var partyControl = Session.getModelController(PartyControl.class);
        var userControl = Session.getModelController(UserControl.class);

        return partyControl.getBestPartyTypeDescription(partyType, userControl.getPreferredLanguageFromUserVisit(getUserVisit(env)));
    }

}
