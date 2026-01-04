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

import com.echothree.model.control.graphql.server.util.count.ObjectLimiter;
import com.echothree.model.control.graphql.server.graphql.count.Connections;
import com.echothree.model.control.graphql.server.graphql.count.CountedObjects;
import com.echothree.model.control.graphql.server.graphql.count.CountingDataConnectionFetcher;
import com.echothree.model.control.graphql.server.graphql.count.CountingPaginatedData;
import com.echothree.model.control.offer.server.control.OfferControl;
import com.echothree.model.control.offer.server.graphql.OfferObject;
import com.echothree.model.control.offer.server.graphql.OfferSecurityUtils;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.data.offer.common.OfferConstants;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.party.server.entity.PartyDepartment;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.annotations.connection.GraphQLConnection;
import graphql.schema.DataFetchingEnvironment;
import java.util.ArrayList;
import java.util.stream.Collectors;

@GraphQLDescription("department object")
@GraphQLName("Department")
public class DepartmentObject
        extends BasePartyObject {

    public DepartmentObject(Party party) {
        super(party);
    }

    public DepartmentObject(PartyDepartment partyDepartment) {
        super(partyDepartment.getParty());

        this.partyDepartment = partyDepartment;
    }

    private PartyDepartment partyDepartment;  // Optional, use getPartyDepartment()

    protected PartyDepartment getPartyDepartment() {
        if(partyDepartment == null) {
            var partyControl = Session.getModelController(PartyControl.class);

            partyDepartment = partyControl.getPartyDepartment(party);
        }

        return partyDepartment;
    }
    @GraphQLField
    @GraphQLDescription("division")
    public DivisionObject getDivision(final DataFetchingEnvironment env) {
        var divisionParty = getPartyDepartment().getDivisionParty();

        return PartySecurityUtils.getHasPartyAccess(env, divisionParty) ? new DivisionObject(divisionParty) : null;
    }

    @GraphQLField
    @GraphQLDescription("department name")
    @GraphQLNonNull
    public String getDepartmentName() {
        return getPartyDepartment().getPartyDepartmentName();
    }

    @GraphQLField
    @GraphQLDescription("is default")
    @GraphQLNonNull
    public boolean getIsDefault() {
        return getPartyDepartment().getIsDefault();
    }

    @GraphQLField
    @GraphQLDescription("sort order")
    @GraphQLNonNull
    public int getSortOrder() {
        return getPartyDepartment().getSortOrder();
    }

    @GraphQLField
    @GraphQLDescription("offers")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    public CountingPaginatedData<OfferObject> getOffers(final DataFetchingEnvironment env) {
        if(OfferSecurityUtils.getHasOffersAccess(env)) {
            var offerControl = Session.getModelController(OfferControl.class);
            var totalCount = offerControl.countOffersByDepartmentParty(party);

            try(var objectLimiter = new ObjectLimiter(env, OfferConstants.COMPONENT_VENDOR_NAME, OfferConstants.ENTITY_TYPE_NAME, totalCount)) {
                var entities = offerControl.getOffersByDepartmentParty(party);
                var departments = entities.stream().map(OfferObject::new).collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                return new CountedObjects<>(objectLimiter, departments);
            }
        } else {
            return Connections.emptyConnection();
        }
    }

}
