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
import com.echothree.model.control.inventory.server.control.InventoryTransactionRoleControl;
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.data.inventory.server.entity.InventoryTransactionRoleType;
import com.echothree.model.data.inventory.server.entity.InventoryTransactionRoleTypeDetail;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;

@GraphQLDescription("inventory transaction role type object")
@GraphQLName("InventoryTransactionRoleType")
public class InventoryTransactionRoleTypeObject
        extends BaseEntityInstanceObject {
    
    private final InventoryTransactionRoleType inventoryTransactionRoleType; // Always Present

    public InventoryTransactionRoleTypeObject(final InventoryTransactionRoleType inventoryTransactionRoleType) {
        super(inventoryTransactionRoleType.getPrimaryKey());

        this.inventoryTransactionRoleType = inventoryTransactionRoleType;
    }

    private InventoryTransactionRoleTypeDetail inventoryTransactionRoleTypeDetail; // Optional, use getInventoryTransactionRoleTypeDetail()
    
    private InventoryTransactionRoleTypeDetail getInventoryTransactionRoleTypeDetail() {
        if(inventoryTransactionRoleTypeDetail == null) {
            inventoryTransactionRoleTypeDetail = inventoryTransactionRoleType.getLastDetail();
        }
        
        return inventoryTransactionRoleTypeDetail;
    }

    @GraphQLField
    @GraphQLDescription("inventory transaction role type name")
    @GraphQLNonNull
    public String getInventoryTransactionRoleTypeName() {
        return getInventoryTransactionRoleTypeDetail().getInventoryTransactionRoleTypeName();
    }

    @GraphQLField
    @GraphQLDescription("inventory transaction type")
    @GraphQLNonNull
    public InventoryTransactionTypeObject getInventoryTransactionType(final DataFetchingEnvironment env) {
        return InventorySecurityUtils.getHasInventoryTransactionTypeAccess(
                env) ? new InventoryTransactionTypeObject(getInventoryTransactionRoleTypeDetail().getInventoryTransactionType()) : null;
    }

    @GraphQLField
    @GraphQLDescription("is default")
    @GraphQLNonNull
    public boolean getIsDefault() {
        return getInventoryTransactionRoleTypeDetail().getIsDefault();
    }

    @GraphQLField
    @GraphQLDescription("sort order")
    @GraphQLNonNull
    public int getSortOrder() {
        return getInventoryTransactionRoleTypeDetail().getSortOrder();
    }
    
    @GraphQLField
    @GraphQLDescription("description")
    @GraphQLNonNull
    public String getDescription(final DataFetchingEnvironment env) {
        var inventoryTransactionRoleControl = Session.getModelController(InventoryTransactionRoleControl.class);
        var userControl = Session.getModelController(UserControl.class);

        return inventoryTransactionRoleControl.getBestInventoryTransactionRoleTypeDescription(inventoryTransactionRoleType,
                userControl.getPreferredLanguageFromUserVisit(BaseGraphQl.getUserVisit(env)));
    }

}
