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

import com.echothree.model.control.graphql.server.graphql.BaseEntityInstanceObject;
import com.echothree.model.control.sequence.server.graphql.SequenceObject;
import com.echothree.model.control.sequence.server.graphql.SequenceSecurityUtils;
import com.echothree.model.data.offer.server.entity.OfferUse;
import com.echothree.model.data.offer.server.entity.OfferUseDetail;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.schema.DataFetchingEnvironment;

@GraphQLDescription("offer use object")
@GraphQLName("OfferUse")
public class OfferUseObject
        extends BaseEntityInstanceObject {

    private final OfferUse offerUse; // Always Present

    public OfferUseObject(OfferUse offerUse) {
        super(offerUse.getPrimaryKey());
        
        this.offerUse = offerUse;
    }

    private OfferUseDetail offerUseDetail; // Optional, offerUse getOfferUseDetail()
    
    private OfferUseDetail getOfferUseDetail() {
        if(offerUseDetail == null) {
            offerUseDetail = offerUse.getLastDetail();
        }
        
        return offerUseDetail;
    }

    @GraphQLField
    @GraphQLDescription("offer")
    public OfferObject getOffer(final DataFetchingEnvironment env) {
        return OfferSecurityUtils.getHasUseAccess(env) ? new OfferObject(getOfferUseDetail().getOffer()) : null;
    }

    @GraphQLField
    @GraphQLDescription("use")
    public UseObject getUse(final DataFetchingEnvironment env) {
        return OfferSecurityUtils.getHasUseAccess(env) ? new UseObject(getOfferUseDetail().getUse()) : null;
    }
    
    @GraphQLField
    @GraphQLDescription("sales order sequence")
    public SequenceObject getSalesOrderSequence(final DataFetchingEnvironment env) {
        if(SequenceSecurityUtils.getHasSequenceAccess(env)) {
            var salesOrderSequence = getOfferUseDetail().getSalesOrderSequence();

            return salesOrderSequence == null ? null : new SequenceObject(salesOrderSequence);
        } else {
            return null;
        }
    }

}
