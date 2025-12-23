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
import com.echothree.model.control.inventory.server.control.InventoryAdjustmentTypeControl;
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.data.inventory.server.entity.InventoryAdjustmentType;
import com.echothree.model.data.inventory.server.entity.InventoryAdjustmentTypeDetail;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;

@GraphQLDescription("inventory type object")
@GraphQLName("InventoryAdjustmentType")
public class InventoryAdjustmentTypeObject
        extends BaseEntityInstanceObject {
    
    private final InventoryAdjustmentType inventoryAdjustmentType; // Always Present

    public InventoryAdjustmentTypeObject(final InventoryAdjustmentType inventoryAdjustmentType) {
        super(inventoryAdjustmentType.getPrimaryKey());

        this.inventoryAdjustmentType = inventoryAdjustmentType;
    }

    private InventoryAdjustmentTypeDetail inventoryAdjustmentTypeDetail; // Optional, use getInventoryAdjustmentTypeDetail()
    
    private InventoryAdjustmentTypeDetail getInventoryAdjustmentTypeDetail() {
        if(inventoryAdjustmentTypeDetail == null) {
            inventoryAdjustmentTypeDetail = inventoryAdjustmentType.getLastDetail();
        }
        
        return inventoryAdjustmentTypeDetail;
    }

    @GraphQLField
    @GraphQLDescription("inventory type name")
    @GraphQLNonNull
    public String getInventoryAdjustmentTypeName() {
        return getInventoryAdjustmentTypeDetail().getInventoryAdjustmentTypeName();
    }

    @GraphQLField
    @GraphQLDescription("is default")
    @GraphQLNonNull
    public boolean getIsDefault() {
        return getInventoryAdjustmentTypeDetail().getIsDefault();
    }

    @GraphQLField
    @GraphQLDescription("sort inventory")
    @GraphQLNonNull
    public int getSortOrder() {
        return getInventoryAdjustmentTypeDetail().getSortOrder();
    }
    
    @GraphQLField
    @GraphQLDescription("description")
    @GraphQLNonNull
    public String getDescription(final DataFetchingEnvironment env) {
        var inventoryAdjustmentTypeControl = Session.getModelController(InventoryAdjustmentTypeControl.class);
        var userControl = Session.getModelController(UserControl.class);

        return inventoryAdjustmentTypeControl.getBestInventoryAdjustmentTypeDescription(inventoryAdjustmentType, userControl.getPreferredLanguageFromUserVisit(BaseGraphQl.getUserVisit(env)));
    }

}
