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

import com.echothree.model.control.contact.server.control.ContactControl;
import com.echothree.model.control.graphql.server.graphql.BaseEntityInstanceObject;
import com.echothree.model.control.graphql.server.graphql.count.CountedObjects;
import com.echothree.model.control.graphql.server.graphql.count.CountingDataConnectionFetcher;
import com.echothree.model.control.graphql.server.graphql.count.CountingPaginatedData;
import com.echothree.model.control.graphql.server.util.count.ObjectLimiter;
import com.echothree.model.control.party.server.graphql.PartyObject;
import com.echothree.model.control.party.server.graphql.PartySecurityUtils;
import com.echothree.model.data.contact.common.PartyContactMechanismPurposeConstants;
import com.echothree.model.data.contact.server.entity.PartyContactMechanism;
import com.echothree.model.data.contact.server.entity.PartyContactMechanismDetail;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.annotations.connection.GraphQLConnection;
import graphql.schema.DataFetchingEnvironment;
import java.util.ArrayList;
import java.util.stream.Collectors;

@GraphQLDescription("partyContactMechanism object")
@GraphQLName("PartyContactMechanism")
public class PartyContactMechanismObject
        extends BaseEntityInstanceObject {

    private final PartyContactMechanism partyContactMechanism; // Always Present

    public PartyContactMechanismObject(PartyContactMechanism partyContactMechanism) {
        super(partyContactMechanism.getPrimaryKey());
        
        this.partyContactMechanism = partyContactMechanism;
    }

    private PartyContactMechanismDetail partyContactMechanismDetail; // Optional, use getPartyContactMechanismDetail()
    
    private PartyContactMechanismDetail getPartyContactMechanismDetail() {
        if(partyContactMechanismDetail == null) {
            partyContactMechanismDetail = partyContactMechanism.getLastDetail();
        }
        
        return partyContactMechanismDetail;
    }

    @GraphQLField
    @GraphQLDescription("party")
    @GraphQLNonNull
    public PartyObject getParty(final DataFetchingEnvironment env) {
        var party = partyContactMechanismDetail.getParty();

        return PartySecurityUtils.getHasPartyAccess(env, party) ? new PartyObject(party) : null;
    }

    @GraphQLField
    @GraphQLDescription("contact mechanism")
    @GraphQLNonNull
    public ContactMechanismObject getContactMechanism() {
        return new ContactMechanismObject(getPartyContactMechanismDetail().getContactMechanism());
    }

    @GraphQLField
    @GraphQLDescription("description")
    public String getDescription(final DataFetchingEnvironment env) {
        return getPartyContactMechanismDetail().getDescription();
    }

    @GraphQLField
    @GraphQLDescription("is default")
    @GraphQLNonNull
    public boolean getIsDefault() {
        return getPartyContactMechanismDetail().getIsDefault();
    }
    
    @GraphQLField
    @GraphQLDescription("sort order")
    @GraphQLNonNull
    public int getSortOrder() {
        return getPartyContactMechanismDetail().getSortOrder();
    }

    @GraphQLField
    @GraphQLDescription("party contact mechanism purposes")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    public CountingPaginatedData<PartyContactMechanismPurposeObject> getPartyContactMechanismPurposes(final DataFetchingEnvironment env) {
//        if(ContactSecurityUtils.getHasPartyContactMechanismPurposesAccess(env, party)) {
        var contactControl = Session.getModelController(ContactControl.class);
        var totalCount = contactControl.countPartyContactMechanismPurposesByPartyContactMechanism(partyContactMechanism);

        try(var objectLimiter = new ObjectLimiter(env, PartyContactMechanismPurposeConstants.COMPONENT_VENDOR_NAME, PartyContactMechanismPurposeConstants.ENTITY_TYPE_NAME, totalCount)) {
            var entities = contactControl.getPartyContactMechanismPurposesByPartyContactMechanism(partyContactMechanism);
            var partyContactMechanismPurposees = entities.stream().map(PartyContactMechanismPurposeObject::new).collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

            return new CountedObjects<>(objectLimiter, partyContactMechanismPurposees);
        }
//        } else {
//            return Connections.emptyConnection();
//        }
    }

}
