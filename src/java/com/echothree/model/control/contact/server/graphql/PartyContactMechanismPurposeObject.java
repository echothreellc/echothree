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

package com.echothree.model.control.contact.server.graphql;

import com.echothree.model.control.graphql.server.graphql.BaseEntityInstanceObject;
import com.echothree.model.data.contact.server.entity.PartyContactMechanismPurpose;
import com.echothree.model.data.contact.server.entity.PartyContactMechanismPurposeDetail;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;

@GraphQLDescription("party contact mechanism purpose object")
@GraphQLName("PartyContactMechanismPurpose")
public class PartyContactMechanismPurposeObject
        extends BaseEntityInstanceObject {

    private final PartyContactMechanismPurpose partyContactMechanismPurpose; // Always Present

    public PartyContactMechanismPurposeObject(PartyContactMechanismPurpose partyContactMechanismPurpose) {
        super(partyContactMechanismPurpose.getPrimaryKey());
        
        this.partyContactMechanismPurpose = partyContactMechanismPurpose;
    }

    private PartyContactMechanismPurposeDetail partyContactMechanismPurposeDetail; // Optional, use getContactMechanismDetail()
    
    private PartyContactMechanismPurposeDetail getPartyContactMechanismPurposeDetail() {
        if(partyContactMechanismPurposeDetail == null) {
            partyContactMechanismPurposeDetail = partyContactMechanismPurpose.getLastDetail();
        }
        
        return partyContactMechanismPurposeDetail;
    }

    @GraphQLField
    @GraphQLDescription("party contact mechanism")
    @GraphQLNonNull
    public PartyContactMechanismObject getPartyContactMechanism() {
        return new PartyContactMechanismObject(getPartyContactMechanismPurposeDetail().getPartyContactMechanism());
    }

    @GraphQLField
    @GraphQLDescription("contact mechanism purpose")
    @GraphQLNonNull
    public ContactMechanismPurposeObject getContactMechanismPurpose() {
        return new ContactMechanismPurposeObject(getPartyContactMechanismPurposeDetail().getContactMechanismPurpose());
    }

    @GraphQLField
    @GraphQLDescription("is default")
    @GraphQLNonNull
    public Boolean getIsDefault() {
        return getPartyContactMechanismPurposeDetail().getIsDefault();
    }

    @GraphQLField
    @GraphQLDescription("sort order")
    @GraphQLNonNull
    public Integer getSortOrder() {
        return getPartyContactMechanismPurposeDetail().getSortOrder();
    }

}
