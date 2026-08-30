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
import com.echothree.model.control.graphql.server.util.BaseGraphQl;
import com.echothree.model.control.graphql.server.util.count.ObjectLimiter;
import com.echothree.model.control.inventory.server.control.InventoryDispositionControl;
import com.echothree.model.control.inventory.server.control.InventoryTransactionReasonControl;
import com.echothree.model.control.inventory.server.control.InventoryDispositionAdjustmentControl;
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.data.inventory.common.InventoryTransactionReasonConstants;
import com.echothree.model.data.inventory.common.InventoryDispositionAdjustmentConstants;
import com.echothree.model.data.inventory.server.entity.InventoryDisposition;
import com.echothree.model.data.inventory.server.entity.InventoryDispositionDetail;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.annotations.connection.GraphQLConnection;
import graphql.schema.DataFetchingEnvironment;
import java.util.ArrayList;
import java.util.stream.Collectors;

@GraphQLDescription("inventory disposition object")
@GraphQLName("InventoryDisposition")
public class InventoryDispositionObject
        extends BaseEntityInstanceObject {
    
    private final InventoryDisposition inventoryDisposition; // Always Present

    public InventoryDispositionObject(final InventoryDisposition inventoryDisposition) {
        super(inventoryDisposition.getPrimaryKey());

        this.inventoryDisposition = inventoryDisposition;
    }

    private InventoryDispositionDetail inventoryDispositionDetail; // Optional, use getInventoryDispositionDetail()
    
    private InventoryDispositionDetail getInventoryDispositionDetail() {
        if(inventoryDispositionDetail == null) {
            inventoryDispositionDetail = inventoryDisposition.getLastDetail();
        }
        
        return inventoryDispositionDetail;
    }

    @GraphQLField
    @GraphQLDescription("inventory disposition name")
    @GraphQLNonNull
    public String getInventoryDispositionName() {
        return getInventoryDispositionDetail().getInventoryDispositionName();
    }

    @GraphQLField
    @GraphQLDescription("inventory transaction type")
    @GraphQLNonNull
    public InventoryTransactionTypeObject getInventoryTransactionType(final DataFetchingEnvironment env) {
        return InventorySecurityUtils.getHasInventoryTransactionTypeAccess(
                env) ? new InventoryTransactionTypeObject(getInventoryDispositionDetail().getInventoryTransactionType()) : null;
    }

    @GraphQLField
    @GraphQLDescription("is default")
    @GraphQLNonNull
    public boolean getIsDefault() {
        return getInventoryDispositionDetail().getIsDefault();
    }

    @GraphQLField
    @GraphQLDescription("sort order")
    @GraphQLNonNull
    public int getSortOrder() {
        return getInventoryDispositionDetail().getSortOrder();
    }
    
    @GraphQLField
    @GraphQLDescription("description")
    @GraphQLNonNull
    public String getDescription(final DataFetchingEnvironment env) {
        var inventoryDispositionControl = Session.getModelController(InventoryDispositionControl.class);
        var userControl = Session.getModelController(UserControl.class);

        return inventoryDispositionControl.getBestInventoryDispositionDescription(inventoryDisposition,
                userControl.getPreferredLanguageFromUserVisit(BaseGraphQl.getUserVisit(env)));
    }

    @GraphQLField
    @GraphQLDescription("inventory transaction reasons")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    public CountingPaginatedData<InventoryTransactionReasonObject> getInventoryTransactionReasons(final DataFetchingEnvironment env) {
        if(InventorySecurityUtils.getHasInventoryTransactionReasonsAccess(env)) {
            var inventoryTransactionReasonControl = Session.getModelController(InventoryTransactionReasonControl.class);
            var totalCount = inventoryTransactionReasonControl.countInventoryTransactionReasonsByInventoryDisposition(inventoryDisposition);

            try(var objectLimiter = new ObjectLimiter(env, InventoryTransactionReasonConstants.COMPONENT_VENDOR_NAME,
                    InventoryTransactionReasonConstants.ENTITY_TYPE_NAME, totalCount)) {
                var entities = inventoryTransactionReasonControl.getInventoryTransactionReasonsByInventoryDisposition(inventoryDisposition);
                var reasons = entities.stream()
                        .map(InventoryTransactionReasonObject::new)
                        .collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                return new CountedObjects<>(objectLimiter, reasons);
            }
        } else {
            return Connections.emptyConnection();
        }
    }

    @GraphQLField
    @GraphQLDescription("inventory disposition adjustments")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    public CountingPaginatedData<InventoryDispositionAdjustmentObject> getInventoryDispositionAdjustments(
            final DataFetchingEnvironment env) {
        if(InventorySecurityUtils.getHasInventoryDispositionAdjustmentsAccess(env)) {
            var control = Session.getModelController(InventoryDispositionAdjustmentControl.class);
            var totalCount = control.countInventoryDispositionAdjustmentsByInventoryDisposition(inventoryDisposition);
            try(var objectLimiter = new ObjectLimiter(env, InventoryDispositionAdjustmentConstants.COMPONENT_VENDOR_NAME,
                    InventoryDispositionAdjustmentConstants.ENTITY_TYPE_NAME, totalCount)) {
                var entities = control.getInventoryDispositionAdjustments(inventoryDisposition);
                var objects = entities.stream().map(InventoryDispositionAdjustmentObject::new)
                        .collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));
                return new CountedObjects<>(objectLimiter, objects);
            }
        } else {
            return Connections.emptyConnection();
        }
    }

}
