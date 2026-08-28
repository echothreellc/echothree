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

package com.echothree.model.control.inventory.server.graphql;

import com.echothree.model.control.graphql.server.graphql.BaseEntityInstanceObject;
import com.echothree.model.control.graphql.server.graphql.count.Connections;
import com.echothree.model.control.graphql.server.graphql.count.CountedObjects;
import com.echothree.model.control.graphql.server.graphql.count.CountingDataConnectionFetcher;
import com.echothree.model.control.graphql.server.graphql.count.CountingPaginatedData;
import com.echothree.model.control.graphql.server.util.count.ObjectLimiter;
import com.echothree.model.control.graphql.server.util.BaseGraphQl;
import com.echothree.model.control.inventory.server.control.InventoryBucketTypeControl;
import com.echothree.model.control.inventory.server.control.BucketControl;
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.data.inventory.server.entity.InventoryBucketType;
import com.echothree.model.data.inventory.server.entity.InventoryBucketTypeDetail;
import com.echothree.model.data.inventory.common.InventoryLocationBucketConstants;
import com.echothree.model.data.inventory.common.PartyBucketConstants;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.annotations.connection.GraphQLConnection;
import graphql.schema.DataFetchingEnvironment;
import java.util.ArrayList;
import java.util.stream.Collectors;

@GraphQLDescription("inventory type object")
@GraphQLName("InventoryBucketType")
public class InventoryBucketTypeObject
        extends BaseEntityInstanceObject {
    
    private final InventoryBucketType inventoryBucketType; // Always Present

    public InventoryBucketTypeObject(final InventoryBucketType inventoryBucketType) {
        super(inventoryBucketType.getPrimaryKey());

        this.inventoryBucketType = inventoryBucketType;
    }

    private InventoryBucketTypeDetail inventoryBucketTypeDetail; // Optional, use getInventoryBucketTypeDetail()
    
    private InventoryBucketTypeDetail getInventoryBucketTypeDetail() {
        if(inventoryBucketTypeDetail == null) {
            inventoryBucketTypeDetail = inventoryBucketType.getLastDetail();
        }
        
        return inventoryBucketTypeDetail;
    }

    @GraphQLField
    @GraphQLDescription("inventory type name")
    @GraphQLNonNull
    public String getInventoryBucketTypeName() {
        return getInventoryBucketTypeDetail().getInventoryBucketTypeName();
    }

    @GraphQLField
    @GraphQLDescription("is default")
    @GraphQLNonNull
    public boolean getIsDefault() {
        return getInventoryBucketTypeDetail().getIsDefault();
    }

    @GraphQLField
    @GraphQLDescription("sort inventory")
    @GraphQLNonNull
    public int getSortOrder() {
        return getInventoryBucketTypeDetail().getSortOrder();
    }
    
    @GraphQLField
    @GraphQLDescription("description")
    @GraphQLNonNull
    public String getDescription(final DataFetchingEnvironment env) {
        var inventoryBucketTypeControl = Session.getModelController(InventoryBucketTypeControl.class);
        var userControl = Session.getModelController(UserControl.class);

        return inventoryBucketTypeControl.getBestInventoryBucketTypeDescription(inventoryBucketType, userControl.getPreferredLanguageFromUserVisit(BaseGraphQl.getUserVisit(env)));
    }

    @GraphQLField
    @GraphQLDescription("inventory location buckets")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    public CountingPaginatedData<InventoryLocationBucketObject> getInventoryLocationBuckets(final DataFetchingEnvironment env) {
        if(InventorySecurityUtils.getHasInventoryLocationBucketsAccess(env)) {
            var bucketControl = Session.getModelController(BucketControl.class);
            var totalCount = bucketControl.countInventoryLocationBucketsByInventoryBucketType(inventoryBucketType);

            try(var objectLimiter = new ObjectLimiter(env, InventoryLocationBucketConstants.COMPONENT_VENDOR_NAME,
                    InventoryLocationBucketConstants.ENTITY_TYPE_NAME, totalCount)) {
                var entities = bucketControl.getInventoryLocationBucketsByInventoryBucketType(inventoryBucketType);
                var inventoryLocationBuckets = entities.stream().map(InventoryLocationBucketObject::new)
                        .collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                return new CountedObjects<>(objectLimiter, inventoryLocationBuckets);
            }
        } else {
            return Connections.emptyConnection();
        }
    }

    @GraphQLField
    @GraphQLDescription("party buckets")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    public CountingPaginatedData<PartyBucketObject> getPartyBuckets(final DataFetchingEnvironment env) {
        if(InventorySecurityUtils.getHasPartyBucketsAccess(env)) {
            var bucketControl = Session.getModelController(BucketControl.class);
            var totalCount = bucketControl.countPartyBucketsByInventoryBucketType(inventoryBucketType);

            try(var objectLimiter = new ObjectLimiter(env, PartyBucketConstants.COMPONENT_VENDOR_NAME,
                    PartyBucketConstants.ENTITY_TYPE_NAME, totalCount)) {
                var entities = bucketControl.getPartyBucketsByInventoryBucketType(inventoryBucketType);
                var partyBuckets = entities.stream().map(PartyBucketObject::new)
                        .collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                return new CountedObjects<>(objectLimiter, partyBuckets);
            }
        } else {
            return Connections.emptyConnection();
        }
    }

}
