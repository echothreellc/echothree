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
import com.echothree.model.control.inventory.server.control.InventoryTransactionTimeControl;
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.data.inventory.server.entity.InventoryTransactionTimeType;
import com.echothree.model.data.inventory.server.entity.InventoryTransactionTimeTypeDetail;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;

@GraphQLDescription("inventory transaction time type object")
@GraphQLName("InventoryTransactionTimeType")
public class InventoryTransactionTimeTypeObject
        extends BaseEntityInstanceObject {
    
    private final InventoryTransactionTimeType inventoryTransactionTimeType; // Always Present

    public InventoryTransactionTimeTypeObject(final InventoryTransactionTimeType inventoryTransactionTimeType) {
        super(inventoryTransactionTimeType.getPrimaryKey());

        this.inventoryTransactionTimeType = inventoryTransactionTimeType;
    }

    private InventoryTransactionTimeTypeDetail inventoryTransactionTimeTypeDetail; // Optional, use getInventoryTransactionTimeTypeDetail()
    
    private InventoryTransactionTimeTypeDetail getInventoryTransactionTimeTypeDetail() {
        if(inventoryTransactionTimeTypeDetail == null) {
            inventoryTransactionTimeTypeDetail = inventoryTransactionTimeType.getLastDetail();
        }
        
        return inventoryTransactionTimeTypeDetail;
    }

    @GraphQLField
    @GraphQLDescription("inventory transaction time type name")
    @GraphQLNonNull
    public String getInventoryTransactionTimeTypeName() {
        return getInventoryTransactionTimeTypeDetail().getInventoryTransactionTimeTypeName();
    }

    @GraphQLField
    @GraphQLDescription("inventory transaction type")
    @GraphQLNonNull
    public InventoryTransactionTypeObject getInventoryTransactionType(final DataFetchingEnvironment env) {
        return InventorySecurityUtils.getHasInventoryTransactionTypeAccess(
                env) ? new InventoryTransactionTypeObject(getInventoryTransactionTimeTypeDetail().getInventoryTransactionType()) : null;
    }

    @GraphQLField
    @GraphQLDescription("is default")
    @GraphQLNonNull
    public boolean getIsDefault() {
        return getInventoryTransactionTimeTypeDetail().getIsDefault();
    }

    @GraphQLField
    @GraphQLDescription("sort order")
    @GraphQLNonNull
    public int getSortOrder() {
        return getInventoryTransactionTimeTypeDetail().getSortOrder();
    }
    
    @GraphQLField
    @GraphQLDescription("description")
    @GraphQLNonNull
    public String getDescription(final DataFetchingEnvironment env) {
        var inventoryTransactionTimeControl = Session.getModelController(InventoryTransactionTimeControl.class);
        var userControl = Session.getModelController(UserControl.class);

        return inventoryTransactionTimeControl.getBestInventoryTransactionTimeTypeDescription(inventoryTransactionTimeType,
                userControl.getPreferredLanguageFromUserVisit(BaseGraphQl.getUserVisit(env)));
    }

}
