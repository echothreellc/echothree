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

package com.echothree.model.control.warehouse.server.graphql;

import com.echothree.model.control.party.server.graphql.BasePartyObject;
import com.echothree.model.control.warehouse.server.control.WarehouseControl;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.warehouse.server.entity.Warehouse;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;

@GraphQLDescription("warehouse object")
@GraphQLName("Warehouse")
public class WarehouseObject
        extends BasePartyObject {

    public WarehouseObject(Party party) {
        super(party);
    }

    public WarehouseObject(Warehouse warehouse) {
        super(warehouse.getParty());

        this.warehouse = warehouse;
    }

    private Warehouse warehouse;  // Optional, use getWarehouse()

    protected Warehouse getWarehouse() {
        if(warehouse == null) {
            var warehouseControl = Session.getModelController(WarehouseControl.class);

            warehouse = warehouseControl.getWarehouse(party);
        }

        return warehouse;
    }

    @GraphQLField
    @GraphQLDescription("warehouse name")
    @GraphQLNonNull
    public String getWarehouseName() {
        return getWarehouse().getWarehouseName();
    }

    @GraphQLField
    @GraphQLDescription("warehouse type")
    @GraphQLNonNull
    public WarehouseTypeObject getWarehouseType(final DataFetchingEnvironment env) {
        return WarehouseSecurityUtils.getHasWarehouseTypeAccess(env) ?
                new WarehouseTypeObject(getWarehouse().getWarehouseType()) : null;
    }

    @GraphQLField
    @GraphQLDescription("is default")
    @GraphQLNonNull
    public boolean getIsDefault() {
        return getWarehouse().getIsDefault();
    }

    @GraphQLField
    @GraphQLDescription("sort order")
    @GraphQLNonNull
    public int getSortOrder() {
        return getWarehouse().getSortOrder();
    }

}
