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
import com.echothree.model.control.inventory.server.control.InventoryTransactionReasonControl;
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.data.inventory.server.entity.InventoryTransactionReason;
import com.echothree.model.data.inventory.server.entity.InventoryTransactionReasonDetail;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;

@GraphQLDescription("inventory transaction reason object")
@GraphQLName("InventoryTransactionReason")
public class InventoryTransactionReasonObject
        extends BaseEntityInstanceObject {
    
    private final InventoryTransactionReason inventoryTransactionReason; // Always Present

    public InventoryTransactionReasonObject(final InventoryTransactionReason inventoryTransactionReason) {
        super(inventoryTransactionReason.getPrimaryKey());

        this.inventoryTransactionReason = inventoryTransactionReason;
    }

    private InventoryTransactionReasonDetail inventoryTransactionReasonDetail; // Optional, use getInventoryTransactionReasonDetail()
    
    private InventoryTransactionReasonDetail getInventoryTransactionReasonDetail() {
        if(inventoryTransactionReasonDetail == null) {
            inventoryTransactionReasonDetail = inventoryTransactionReason.getLastDetail();
        }
        
        return inventoryTransactionReasonDetail;
    }

    @GraphQLField
    @GraphQLDescription("inventory transaction reason name")
    @GraphQLNonNull
    public String getInventoryTransactionReasonName() {
        return getInventoryTransactionReasonDetail().getInventoryTransactionReasonName();
    }

    @GraphQLField
    @GraphQLDescription("inventory transaction type")
    @GraphQLNonNull
    public InventoryTransactionTypeObject getInventoryTransactionType(final DataFetchingEnvironment env) {
        return InventorySecurityUtils.getHasInventoryTransactionTypeAccess(
                env) ? new InventoryTransactionTypeObject(getInventoryTransactionReasonDetail().getInventoryTransactionType()) : null;
    }

    @GraphQLField
    @GraphQLDescription("inventory disposition")
    @GraphQLNonNull
    public InventoryDispositionObject getInventoryDisposition(final DataFetchingEnvironment env) {
        return InventorySecurityUtils.getHasInventoryDispositionAccess(
                env) ? new InventoryDispositionObject(getInventoryTransactionReasonDetail().getInventoryDisposition()) : null;
    }

    @GraphQLField
    @GraphQLDescription("is default")
    @GraphQLNonNull
    public boolean getIsDefault() {
        return getInventoryTransactionReasonDetail().getIsDefault();
    }

    @GraphQLField
    @GraphQLDescription("sort order")
    @GraphQLNonNull
    public int getSortOrder() {
        return getInventoryTransactionReasonDetail().getSortOrder();
    }
    
    @GraphQLField
    @GraphQLDescription("description")
    @GraphQLNonNull
    public String getDescription(final DataFetchingEnvironment env) {
        var inventoryTransactionReasonControl = Session.getModelController(InventoryTransactionReasonControl.class);
        var userControl = Session.getModelController(UserControl.class);

        return inventoryTransactionReasonControl.getBestInventoryTransactionReasonDescription(inventoryTransactionReason,
                userControl.getPreferredLanguageFromUserVisit(BaseGraphQl.getUserVisit(env)));
    }

}
