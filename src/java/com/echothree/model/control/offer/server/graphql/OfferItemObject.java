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
import com.echothree.model.control.graphql.server.util.count.ObjectLimiter;
import com.echothree.model.control.graphql.server.graphql.count.Connections;
import com.echothree.model.control.graphql.server.graphql.count.CountedObjects;
import com.echothree.model.control.graphql.server.graphql.count.CountingDataConnectionFetcher;
import com.echothree.model.control.graphql.server.graphql.count.CountingPaginatedData;
import com.echothree.model.control.item.server.graphql.ItemObject;
import com.echothree.model.control.item.server.graphql.ItemSecurityUtils;
import com.echothree.model.control.offer.server.control.OfferItemControl;
import com.echothree.model.data.offer.common.OfferItemPriceConstants;
import com.echothree.model.data.offer.server.entity.OfferItem;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.annotations.connection.GraphQLConnection;
import graphql.schema.DataFetchingEnvironment;
import java.util.ArrayList;
import java.util.stream.Collectors;

@GraphQLDescription("offer item price object")
@GraphQLName("OfferItem")
public class OfferItemObject
        extends BaseEntityInstanceObject {
    
    private final OfferItem offerItem; // Always Present
    
    public OfferItemObject(OfferItem offerItem) {
        super(offerItem.getPrimaryKey());
        
        this.offerItem = offerItem;
    }

    @GraphQLField
    @GraphQLDescription("offer")
    public OfferObject getOffer(final DataFetchingEnvironment env) {
        return OfferSecurityUtils.getHasOfferAccess(env) ? new OfferObject(offerItem.getOffer()) : null;
    }

    @GraphQLField
    @GraphQLDescription("item")
    public ItemObject getItem(final DataFetchingEnvironment env) {
        return ItemSecurityUtils.getHasItemAccess(env) ? new ItemObject(offerItem.getItem()) : null;
    }

    @GraphQLField
    @GraphQLDescription("offer item prices")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    public CountingPaginatedData<OfferItemPriceObject> getOfferItemPrices(final DataFetchingEnvironment env) {
        if(OfferSecurityUtils.getHasOfferItemPricesAccess(env)) {
            var offerItemControl = Session.getModelController(OfferItemControl.class);
            var totalCount = offerItemControl.countOfferItemPricesByOfferItem(offerItem);

            try(var objectLimiter = new ObjectLimiter(env, OfferItemPriceConstants.COMPONENT_VENDOR_NAME, OfferItemPriceConstants.ENTITY_TYPE_NAME, totalCount)) {
                var entities = offerItemControl.getOfferItemPricesByOfferItem(offerItem);
                var offerItemPrices = entities.stream().map(OfferItemPriceObject::new).collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                return new CountedObjects<>(objectLimiter, offerItemPrices);
            }
        } else {
            return Connections.emptyConnection();
        }
    }

}
