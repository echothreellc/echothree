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
import com.echothree.model.control.graphql.server.util.BaseGraphQl;
import com.echothree.model.control.inventory.server.control.InventoryControl;
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.control.warehouse.server.graphql.WarehouseObject;
import com.echothree.model.control.warehouse.server.graphql.WarehouseSecurityUtils;
import com.echothree.model.data.inventory.server.entity.InventoryLocationGroup;
import com.echothree.model.data.inventory.server.entity.InventoryLocationGroupDetail;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;

@GraphQLDescription("inventory location group object")
@GraphQLName("InventoryLocationGroup")
public class InventoryLocationGroupObject
        extends BaseEntityInstanceObject {
    
    private final InventoryLocationGroup inventoryLocationGroup; // Always Present
    
    public InventoryLocationGroupObject(InventoryLocationGroup inventoryLocationGroup) {
        super(inventoryLocationGroup.getPrimaryKey());
        
        this.inventoryLocationGroup = inventoryLocationGroup;
    }

    private InventoryLocationGroupDetail inventoryLocationGroupDetail; // Optional, use getInventoryLocationGroupDetail()
    
    private InventoryLocationGroupDetail getInventoryLocationGroupDetail() {
        if(inventoryLocationGroupDetail == null) {
            inventoryLocationGroupDetail = inventoryLocationGroup.getLastDetail();
        }
        
        return inventoryLocationGroupDetail;
    }

    @GraphQLField
    @GraphQLDescription("warehouse")
    public WarehouseObject getWarehouse(final DataFetchingEnvironment env) {
        return WarehouseSecurityUtils.getHasWarehouseAccess(env) ?
                new WarehouseObject(getInventoryLocationGroupDetail().getWarehouseParty()) : null;
    }

    @GraphQLField
    @GraphQLDescription("inventory location group name")
    @GraphQLNonNull
    public String getInventoryLocationGroupName() {
        return getInventoryLocationGroupDetail().getInventoryLocationGroupName();
    }

    @GraphQLField
    @GraphQLDescription("is default")
    @GraphQLNonNull
    public boolean getIsDefault() {
        return getInventoryLocationGroupDetail().getIsDefault();
    }
    
    @GraphQLField
    @GraphQLDescription("sort order")
    @GraphQLNonNull
    public int getSortOrder() {
        return getInventoryLocationGroupDetail().getSortOrder();
    }
    
    @GraphQLField
    @GraphQLDescription("description")
    @GraphQLNonNull
    public String getDescription(final DataFetchingEnvironment env) {
        var inventoryControl = Session.getModelController(InventoryControl.class);
        var userControl = Session.getModelController(UserControl.class);

        return inventoryControl.getBestInventoryLocationGroupDescription(inventoryLocationGroup, userControl.getPreferredLanguageFromUserVisit(BaseGraphQl.getUserVisit(env)));
    }
    
}
