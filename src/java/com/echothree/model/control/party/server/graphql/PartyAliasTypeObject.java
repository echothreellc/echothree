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

import com.echothree.model.control.graphql.server.graphql.BaseEntityInstanceObject;
import com.echothree.model.control.graphql.server.util.BaseGraphQl;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.data.party.server.entity.PartyAliasType;
import com.echothree.model.data.party.server.entity.PartyAliasTypeDetail;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;

@GraphQLDescription("party alias type object")
@GraphQLName("PartyAliasType")
public class PartyAliasTypeObject
        extends BaseEntityInstanceObject {
    
    private final PartyAliasType partyAliasType; // Always Present
    
    public PartyAliasTypeObject(PartyAliasType partyAliasType) {
        super(partyAliasType.getPrimaryKey());
        
        this.partyAliasType = partyAliasType;
    }

    private PartyAliasTypeDetail partyAliasTypeDetail; // Optional, use getPartyAliasTypeDetail()
    
    private PartyAliasTypeDetail getPartyAliasTypeDetail() {
        if(partyAliasTypeDetail == null) {
            partyAliasTypeDetail = partyAliasType.getLastDetail();
        }
        
        return partyAliasTypeDetail;
    }

    @GraphQLField
    @GraphQLDescription("party type")
    public PartyTypeObject getPartyType(final DataFetchingEnvironment env) {
        return PartySecurityUtils.getHasPartyTypeAccess(env) ? new PartyTypeObject(getPartyAliasTypeDetail().getPartyType()) : null;
    }

    @GraphQLField
    @GraphQLDescription("party alias type name")
    @GraphQLNonNull
    public String getPartyAliasTypeName() {
        return getPartyAliasTypeDetail().getPartyAliasTypeName();
    }

    @GraphQLField
    @GraphQLDescription("validation pattern")
    public String getValidationPattern() {
        return getPartyAliasTypeDetail().getValidationPattern();
    }

    @GraphQLField
    @GraphQLDescription("is default")
    @GraphQLNonNull
    public boolean getIsDefault() {
        return getPartyAliasTypeDetail().getIsDefault();
    }
    
    @GraphQLField
    @GraphQLDescription("sort order")
    @GraphQLNonNull
    public int getSortOrder() {
        return getPartyAliasTypeDetail().getSortOrder();
    }
    
    @GraphQLField
    @GraphQLDescription("description")
    @GraphQLNonNull
    public String getDescription(final DataFetchingEnvironment env) {
        var partyControl = Session.getModelController(PartyControl.class);
        var userControl = Session.getModelController(UserControl.class);

        return partyControl.getBestPartyAliasTypeDescription(partyAliasType, userControl.getPreferredLanguageFromUserVisit(BaseGraphQl.getUserVisit(env)));
    }

}
