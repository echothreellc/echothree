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

package com.echothree.model.control.vendor.server.graphql;

import com.echothree.model.control.graphql.server.graphql.BaseObject;
import com.echothree.model.control.graphql.server.graphql.UnitCostObject;
import com.echothree.model.control.inventory.server.graphql.InventoryConditionObject;
import com.echothree.model.control.inventory.server.graphql.InventorySecurityUtils;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.uom.server.graphql.UnitOfMeasureTypeObject;
import com.echothree.model.control.uom.server.graphql.UomSecurityUtils;
import com.echothree.model.data.vendor.server.entity.VendorItemCost;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.schema.DataFetchingEnvironment;

@GraphQLDescription("vendor item cost object")
@GraphQLName("VendorItemCost")
public class VendorItemCostObject
        extends BaseObject {
    
    private final VendorItemCost vendorItemCost; // Always Present
    
    public VendorItemCostObject(VendorItemCost vendorItemCost) {
        this.vendorItemCost = vendorItemCost;
    }

    @GraphQLField
    @GraphQLDescription("vendor item")
    public VendorItemObject getVendorItem(final DataFetchingEnvironment env) {
        return VendorSecurityUtils.getHasVendorItemAccess(env) ? new VendorItemObject(vendorItemCost.getVendorItem()) : null;
    }

    @GraphQLField
    @GraphQLDescription("inventory condition")
    public InventoryConditionObject getInventoryCondition(final DataFetchingEnvironment env) {
        return InventorySecurityUtils.getHasInventoryConditionAccess(env) ? new InventoryConditionObject(vendorItemCost.getInventoryCondition()) : null;
    }

    @GraphQLField
    @GraphQLDescription("unit of measure type")
    public UnitOfMeasureTypeObject getUnitOfMeasureType(final DataFetchingEnvironment env) {
        return UomSecurityUtils.getHasUnitOfMeasureTypeAccess(env) ? new UnitOfMeasureTypeObject(vendorItemCost.getUnitOfMeasureType()) : null;
    }

    @GraphQLField
    @GraphQLDescription("unit cost")
    public UnitCostObject getUnitCost() {
        var partyControl = Session.getModelController(PartyControl.class);

        return new UnitCostObject(partyControl.getPreferredCurrency(vendorItemCost.getVendorItem().getLastDetail().getVendorParty()),
                vendorItemCost.getUnitCost());
    }

}
