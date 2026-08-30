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
import com.echothree.model.control.inventory.server.control.InventoryDispositionAdjustmentControl;
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.data.inventory.server.entity.InventoryDispositionAdjustment;
import com.echothree.model.data.inventory.server.entity.InventoryDispositionAdjustmentDetail;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;

@GraphQLDescription("inventory disposition adjustment object")
@GraphQLName("InventoryDispositionAdjustment")
public class InventoryDispositionAdjustmentObject
        extends BaseEntityInstanceObject {
    
    private final InventoryDispositionAdjustment inventoryDispositionAdjustment; // Always Present

    public InventoryDispositionAdjustmentObject(final InventoryDispositionAdjustment inventoryDispositionAdjustment) {
        super(inventoryDispositionAdjustment.getPrimaryKey());

        this.inventoryDispositionAdjustment = inventoryDispositionAdjustment;
    }

    private InventoryDispositionAdjustmentDetail inventoryDispositionAdjustmentDetail; // Optional, use getInventoryDispositionAdjustmentDetail()
    
    private InventoryDispositionAdjustmentDetail getInventoryDispositionAdjustmentDetail() {
        if(inventoryDispositionAdjustmentDetail == null) {
            inventoryDispositionAdjustmentDetail = inventoryDispositionAdjustment.getLastDetail();
        }
        
        return inventoryDispositionAdjustmentDetail;
    }

    @GraphQLField
    @GraphQLDescription("inventory disposition adjustment name")
    @GraphQLNonNull
    public String getInventoryDispositionAdjustmentName() {
        return getInventoryDispositionAdjustmentDetail().getInventoryDispositionAdjustmentName();
    }

    @GraphQLField
    @GraphQLDescription("inventory adjustment type")
    @GraphQLNonNull
    public InventoryAdjustmentTypeObject getInventoryAdjustmentType(final DataFetchingEnvironment env) {
        return InventorySecurityUtils.getHasInventoryAdjustmentTypeAccess(env)
                ? new InventoryAdjustmentTypeObject(getInventoryDispositionAdjustmentDetail().getInventoryAdjustmentType()) : null;
    }

    @GraphQLField
    @GraphQLDescription("inventory bucket type")
    @GraphQLNonNull
    public InventoryBucketTypeObject getInventoryBucketType(final DataFetchingEnvironment env) {
        return InventorySecurityUtils.getHasInventoryBucketTypeAccess(env)
                ? new InventoryBucketTypeObject(getInventoryDispositionAdjustmentDetail().getInventoryBucketType()) : null;
    }

    @GraphQLField
    @GraphQLDescription("inventory disposition")
    @GraphQLNonNull
    public InventoryDispositionObject getInventoryDisposition(final DataFetchingEnvironment env) {
        return InventorySecurityUtils.getHasInventoryDispositionAccess(
                env) ? new InventoryDispositionObject(getInventoryDispositionAdjustmentDetail().getInventoryDisposition()) : null;
    }

    @GraphQLField
    @GraphQLDescription("is default")
    @GraphQLNonNull
    public boolean getIsDefault() {
        return getInventoryDispositionAdjustmentDetail().getIsDefault();
    }

    @GraphQLField
    @GraphQLDescription("sort order")
    @GraphQLNonNull
    public int getSortOrder() {
        return getInventoryDispositionAdjustmentDetail().getSortOrder();
    }
    
    @GraphQLField
    @GraphQLDescription("description")
    @GraphQLNonNull
    public String getDescription(final DataFetchingEnvironment env) {
        var inventoryDispositionAdjustmentControl = Session.getModelController(InventoryDispositionAdjustmentControl.class);
        var userControl = Session.getModelController(UserControl.class);

        return inventoryDispositionAdjustmentControl.getBestInventoryDispositionAdjustmentDescription(inventoryDispositionAdjustment,
                userControl.getPreferredLanguageFromUserVisit(BaseGraphQl.getUserVisit(env)));
    }

}
