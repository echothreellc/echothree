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
import com.echothree.model.control.inventory.server.control.InventoryCostingMethodControl;
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.data.inventory.server.entity.InventoryCostingMethod;
import com.echothree.model.data.inventory.server.entity.InventoryCostingMethodDetail;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;

@GraphQLDescription("inventory type object")
@GraphQLName("InventoryCostingMethod")
public class InventoryCostingMethodObject
        extends BaseEntityInstanceObject {
    
    private final InventoryCostingMethod inventoryCostingMethod; // Always Present

    public InventoryCostingMethodObject(final InventoryCostingMethod inventoryCostingMethod) {
        super(inventoryCostingMethod.getPrimaryKey());

        this.inventoryCostingMethod = inventoryCostingMethod;
    }

    private InventoryCostingMethodDetail inventoryCostingMethodDetail; // Optional, use getInventoryCostingMethodDetail()
    
    private InventoryCostingMethodDetail getInventoryCostingMethodDetail() {
        if(inventoryCostingMethodDetail == null) {
            inventoryCostingMethodDetail = inventoryCostingMethod.getLastDetail();
        }
        
        return inventoryCostingMethodDetail;
    }

    @GraphQLField
    @GraphQLDescription("inventory type name")
    @GraphQLNonNull
    public String getInventoryCostingMethodName() {
        return getInventoryCostingMethodDetail().getInventoryCostingMethodName();
    }

    @GraphQLField
    @GraphQLDescription("is default")
    @GraphQLNonNull
    public boolean getIsDefault() {
        return getInventoryCostingMethodDetail().getIsDefault();
    }

    @GraphQLField
    @GraphQLDescription("sort inventory")
    @GraphQLNonNull
    public int getSortOrder() {
        return getInventoryCostingMethodDetail().getSortOrder();
    }
    
    @GraphQLField
    @GraphQLDescription("description")
    @GraphQLNonNull
    public String getDescription(final DataFetchingEnvironment env) {
        var inventoryCostingMethodControl = Session.getModelController(InventoryCostingMethodControl.class);
        var userControl = Session.getModelController(UserControl.class);

        return inventoryCostingMethodControl.getBestInventoryCostingMethodDescription(inventoryCostingMethod, userControl.getPreferredLanguageFromUserVisit(BaseGraphQl.getUserVisit(env)));
    }

}
