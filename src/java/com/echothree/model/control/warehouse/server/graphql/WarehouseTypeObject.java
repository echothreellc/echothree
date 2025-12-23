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

import com.echothree.model.control.graphql.server.graphql.BaseEntityInstanceObject;
import com.echothree.model.control.graphql.server.util.BaseGraphQl;
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.control.warehouse.server.control.WarehouseControl;
import com.echothree.model.data.warehouse.server.entity.WarehouseType;
import com.echothree.model.data.warehouse.server.entity.WarehouseTypeDetail;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;

@GraphQLDescription("warehouse type object")
@GraphQLName("WarehouseType")
public class WarehouseTypeObject
        extends BaseEntityInstanceObject {
    
    private final WarehouseType warehouseType; // Always Present
    
    public WarehouseTypeObject(WarehouseType warehouseType) {
        super(warehouseType.getPrimaryKey());
        
        this.warehouseType = warehouseType;
    }

    private WarehouseTypeDetail warehouseTypeDetail; // Optional, use getWarehouseTypeDetail()
    
    private WarehouseTypeDetail getWarehouseTypeDetail() {
        if(warehouseTypeDetail == null) {
            warehouseTypeDetail = warehouseType.getLastDetail();
        }
        
        return warehouseTypeDetail;
    }
    
    @GraphQLField
    @GraphQLDescription("warehouse type name")
    @GraphQLNonNull
    public String getWarehouseTypeName() {
        return getWarehouseTypeDetail().getWarehouseTypeName();
    }

    @GraphQLField
    @GraphQLDescription("priority")
    @GraphQLNonNull
    public int getPriority() {
        return getWarehouseTypeDetail().getPriority();
    }

    @GraphQLField
    @GraphQLDescription("is default")
    @GraphQLNonNull
    public boolean getIsDefault() {
        return getWarehouseTypeDetail().getIsDefault();
    }
    
    @GraphQLField
    @GraphQLDescription("sort order")
    @GraphQLNonNull
    public int getSortOrder() {
        return getWarehouseTypeDetail().getSortOrder();
    }
    
    @GraphQLField
    @GraphQLDescription("description")
    @GraphQLNonNull
    public String getDescription(final DataFetchingEnvironment env) {
        var warehouseControl = Session.getModelController(WarehouseControl.class);
        var userControl = Session.getModelController(UserControl.class);

        return warehouseControl.getBestWarehouseTypeDescription(warehouseType, userControl.getPreferredLanguageFromUserVisit(BaseGraphQl.getUserVisit(env)));
    }

}
