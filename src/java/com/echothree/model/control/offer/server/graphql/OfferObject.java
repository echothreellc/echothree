// --------------------------------------------------------------------------------
// Copyright 2002-2021 Echo Three, LLC
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
import com.echothree.model.control.graphql.server.util.GraphQlContext;
import com.echothree.model.control.offer.server.control.OfferControl;
import com.echothree.model.control.party.server.graphql.CompanyObject;
import com.echothree.model.control.party.server.graphql.DepartmentObject;
import com.echothree.model.control.party.server.graphql.PartySecurityUtils;
import com.echothree.model.control.sequence.server.graphql.SequenceObject;
import com.echothree.model.control.sequence.server.graphql.SequenceSecurityUtils;
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.data.offer.server.entity.Offer;
import com.echothree.model.data.offer.server.entity.OfferDetail;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;

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
        if(SequenceSecurityUtils.getInstance().getHasSequenceAccess(env)) {
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

        return PartySecurityUtils.getInstance().getHasPartyAccess(env, departmentParty) ? new DepartmentObject(departmentParty) : null;
    }

    // TODO: OfferItemSelector

    @GraphQLField
    @GraphQLDescription("offer item price filter")
    public FilterObject getOfferItemPriceFilter(final DataFetchingEnvironment env) {
        if(FilterSecurityUtils.getInstance().getHasFilterAccess(env)) {
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
        GraphQlContext context = env.getContext();
        
        return offerControl.getBestOfferDescription(offer, userControl.getPreferredLanguageFromUserVisit(context.getUserVisit()));
    }
    
}
