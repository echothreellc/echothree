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

package com.echothree.model.control.offer.server.graphql;

import com.echothree.model.control.filter.server.graphql.FilterObject;
import com.echothree.model.control.filter.server.graphql.FilterSecurityUtils;
import com.echothree.model.control.graphql.server.graphql.BaseEntityInstanceObject;
import com.echothree.model.control.graphql.server.graphql.count.Connections;
import com.echothree.model.control.graphql.server.graphql.count.CountedObjects;
import com.echothree.model.control.graphql.server.graphql.count.CountingDataConnectionFetcher;
import com.echothree.model.control.graphql.server.graphql.count.CountingPaginatedData;
import com.echothree.model.control.graphql.server.util.BaseGraphQl;
import com.echothree.model.control.graphql.server.util.count.ObjectLimiter;
import com.echothree.model.control.offer.server.control.OfferControl;
import com.echothree.model.control.offer.server.control.OfferItemControl;
import com.echothree.model.control.party.server.graphql.DepartmentObject;
import com.echothree.model.control.party.server.graphql.PartySecurityUtils;
import com.echothree.model.control.selector.server.graphql.SelectorObject;
import com.echothree.model.control.selector.server.graphql.SelectorSecurityUtils;
import com.echothree.model.control.sequence.server.graphql.SequenceObject;
import com.echothree.model.control.sequence.server.graphql.SequenceSecurityUtils;
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.data.offer.common.OfferItemConstants;
import com.echothree.model.data.offer.server.entity.Offer;
import com.echothree.model.data.offer.server.entity.OfferDetail;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.annotations.connection.GraphQLConnection;
import graphql.schema.DataFetchingEnvironment;
import java.util.ArrayList;
import java.util.stream.Collectors;

@GraphQLDescription("offer object")
@GraphQLName("Offer")
public class OfferObject
        extends BaseEntityInstanceObject {
    
    private final Offer offer; // Always Present
    
    public OfferObject(Offer offer) {
        super(offer.getPrimaryKey());
        
        this.offer = offer;
    }

    private OfferDetail offerDetail; // Optional, offer getOfferDetail()
    
    private OfferDetail getOfferDetail() {
        if(offerDetail == null) {
            offerDetail = offer.getLastDetail();
        }
        
        return offerDetail;
    }
    
    @GraphQLField
    @GraphQLDescription("offer name")
    @GraphQLNonNull
    public String getOfferName() {
        return getOfferDetail().getOfferName();
    }

    @GraphQLField
    @GraphQLDescription("sales order sequence")
    public SequenceObject getSalesOrderSequence(final DataFetchingEnvironment env) {
        if(SequenceSecurityUtils.getHasSequenceAccess(env)) {
            var salesOrderSequence = getOfferDetail().getSalesOrderSequence();

            return salesOrderSequence == null ? null : new SequenceObject(salesOrderSequence);
        } else {
            return null;
        }
    }

    @GraphQLField
    @GraphQLDescription("department")
    public DepartmentObject getDepartment(final DataFetchingEnvironment env) {
        var departmentParty = getOfferDetail().getDepartmentParty();

        return PartySecurityUtils.getHasPartyAccess(env, departmentParty) ? new DepartmentObject(departmentParty) : null;
    }

    @GraphQLField
    @GraphQLDescription("offer item selector")
    public SelectorObject getOfferItemSelector(final DataFetchingEnvironment env) {
        if(SelectorSecurityUtils.getHasSelectorAccess(env)) {
            var offerItemSelector = getOfferDetail().getOfferItemSelector();

            return offerItemSelector == null ? null : new SelectorObject(offerItemSelector);
        } else {
            return null;
        }
    }

    @GraphQLField
    @GraphQLDescription("offer item price filter")
    public FilterObject getOfferItemPriceFilter(final DataFetchingEnvironment env) {
        if(FilterSecurityUtils.getHasFilterAccess(env)) {
            var offerItemPriceFilter = getOfferDetail().getOfferItemPriceFilter();

            return offerItemPriceFilter == null ? null : new FilterObject(offerItemPriceFilter);
        } else {
            return null;
        }
    }

    @GraphQLField
    @GraphQLDescription("is default")
    @GraphQLNonNull
    public boolean getIsDefault() {
        return getOfferDetail().getIsDefault();
    }
    
    @GraphQLField
    @GraphQLDescription("sort order")
    @GraphQLNonNull
    public int getSortOrder() {
        return getOfferDetail().getSortOrder();
    }
    
    @GraphQLField
    @GraphQLDescription("description")
    @GraphQLNonNull
    public String getDescription(final DataFetchingEnvironment env) {
        var offerControl = Session.getModelController(OfferControl.class);
        var userControl = Session.getModelController(UserControl.class);

        return offerControl.getBestOfferDescription(offer, userControl.getPreferredLanguageFromUserVisit(BaseGraphQl.getUserVisit(env)));
    }

    @GraphQLField
    @GraphQLDescription("offer items")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    public CountingPaginatedData<OfferItemObject> getOfferItems(final DataFetchingEnvironment env) {
        if(OfferSecurityUtils.getHasOfferItemsAccess(env)) {
            var offerItemControl = Session.getModelController(OfferItemControl.class);
            var totalCount = offerItemControl.countOfferItemsByOffer(offer);

            try(var objectLimiter = new ObjectLimiter(env, OfferItemConstants.COMPONENT_VENDOR_NAME, OfferItemConstants.ENTITY_TYPE_NAME, totalCount)) {
                var entities = offerItemControl.getOfferItemsByOffer(offer);
                var offerItems = entities.stream().map(OfferItemObject::new).collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                return new CountedObjects<>(objectLimiter, offerItems);
            }
        } else {
            return Connections.emptyConnection();
        }
    }

}
