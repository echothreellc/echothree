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

import java.util.stream.Collectors;
import java.util.ArrayList;
import graphql.annotations.connection.GraphQLConnection;
import com.echothree.model.control.graphql.server.util.count.ObjectLimiter;
import com.echothree.model.control.graphql.server.graphql.count.CountingPaginatedData;
import com.echothree.model.control.graphql.server.graphql.count.CountingDataConnectionFetcher;
import com.echothree.model.control.graphql.server.graphql.count.CountedObjects;
import com.echothree.model.control.graphql.server.graphql.count.Connections;
import com.echothree.model.data.inventory.common.InventoryCostingPoolConstants;
import com.echothree.model.control.inventory.server.control.InventoryCostingPoolControl;
import com.echothree.model.control.graphql.server.graphql.BaseEntityInstanceObject;
import com.echothree.model.control.graphql.server.util.BaseGraphQl;
import com.echothree.model.control.inventory.server.control.InventoryConditionControl;
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.data.inventory.server.entity.InventoryCondition;
import com.echothree.model.data.inventory.server.entity.InventoryConditionDetail;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;

@GraphQLDescription("inventory condition object")
@GraphQLName("InventoryCondition")
public class InventoryConditionObject
        extends BaseEntityInstanceObject {
    
    private final InventoryCondition inventoryCondition; // Always Present
    
    public InventoryConditionObject(InventoryCondition inventoryCondition) {
        super(inventoryCondition.getPrimaryKey());
        
        this.inventoryCondition = inventoryCondition;
    }

    private InventoryConditionDetail inventoryConditionDetail; // Optional, use getInventoryConditionDetail()
    
    private InventoryConditionDetail getInventoryConditionDetail() {
        if(inventoryConditionDetail == null) {
            inventoryConditionDetail = inventoryCondition.getLastDetail();
        }
        
        return inventoryConditionDetail;
    }
    
    @GraphQLField
    @GraphQLDescription("inventory condition name")
    @GraphQLNonNull
    public String getInventoryConditionName() {
        return getInventoryConditionDetail().getInventoryConditionName();
    }

    @GraphQLField
    @GraphQLDescription("is default")
    @GraphQLNonNull
    public boolean getIsDefault() {
        return getInventoryConditionDetail().getIsDefault();
    }
    
    @GraphQLField
    @GraphQLDescription("sort order")
    @GraphQLNonNull
    public int getSortOrder() {
        return getInventoryConditionDetail().getSortOrder();
    }
    
    @GraphQLField
    @GraphQLDescription("description")
    @GraphQLNonNull
    public String getDescription(final DataFetchingEnvironment env) {
        var inventoryConditionControl = Session.getModelController(InventoryConditionControl.class);
        var userControl = Session.getModelController(UserControl.class);

        return inventoryConditionControl.getBestInventoryConditionDescription(inventoryCondition, userControl.getPreferredLanguageFromUserVisit(BaseGraphQl.getUserVisit(env)));
    }
    
    @GraphQLField
    @GraphQLDescription("inventory costing pools")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    public CountingPaginatedData<InventoryCostingPoolObject> getInventoryCostingPools(final DataFetchingEnvironment env) {
        if(InventorySecurityUtils.getHasInventoryCostingPoolsAccess(env)) {
            var inventoryCostingPoolControl = Session.getModelController(InventoryCostingPoolControl.class);
            var totalCount = inventoryCostingPoolControl.countInventoryCostingPoolsByInventoryCondition(inventoryCondition);

            try(var objectLimiter = new ObjectLimiter(env, InventoryCostingPoolConstants.COMPONENT_VENDOR_NAME, InventoryCostingPoolConstants.ENTITY_TYPE_NAME, totalCount)) {
                var entities = inventoryCostingPoolControl.getInventoryCostingPoolsByInventoryCondition(inventoryCondition);
                var inventoryCostingPools = entities.stream()
                        .map(InventoryCostingPoolObject::new)
                        .collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                return new CountedObjects<>(objectLimiter, inventoryCostingPools);
            }
        } else {
            return Connections.emptyConnection();
        }
    }

}
