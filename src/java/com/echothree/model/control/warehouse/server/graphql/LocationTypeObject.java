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
import com.echothree.model.data.warehouse.server.entity.LocationType;
import com.echothree.model.data.warehouse.server.entity.LocationTypeDetail;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;

@GraphQLDescription("location type object")
@GraphQLName("LocationType")
public class LocationTypeObject
        extends BaseEntityInstanceObject {
    
    private final LocationType locationType; // Always Present
    
    public LocationTypeObject(LocationType locationType) {
        super(locationType.getPrimaryKey());
        
        this.locationType = locationType;
    }

    private LocationTypeDetail locationTypeDetail; // Optional, use getLocationTypeDetail()
    
    private LocationTypeDetail getLocationTypeDetail() {
        if(locationTypeDetail == null) {
            locationTypeDetail = locationType.getLastDetail();
        }
        
        return locationTypeDetail;
    }

    @GraphQLField
    @GraphQLDescription("warehouse")
    public WarehouseObject getWarehouse(final DataFetchingEnvironment env) {
        return WarehouseSecurityUtils.getHasWarehouseAccess(env) ?
                new WarehouseObject(getLocationTypeDetail().getWarehouseParty()) : null;
    }

    @GraphQLField
    @GraphQLDescription("location type name")
    @GraphQLNonNull
    public String getLocationTypeName() {
        return getLocationTypeDetail().getLocationTypeName();
    }

    @GraphQLField
    @GraphQLDescription("is default")
    @GraphQLNonNull
    public boolean getIsDefault() {
        return getLocationTypeDetail().getIsDefault();
    }
    
    @GraphQLField
    @GraphQLDescription("sort order")
    @GraphQLNonNull
    public int getSortOrder() {
        return getLocationTypeDetail().getSortOrder();
    }
    
    @GraphQLField
    @GraphQLDescription("description")
    @GraphQLNonNull
    public String getDescription(final DataFetchingEnvironment env) {
        var warehouseControl = Session.getModelController(WarehouseControl.class);
        var userControl = Session.getModelController(UserControl.class);

        return warehouseControl.getBestLocationTypeDescription(locationType, userControl.getPreferredLanguageFromUserVisit(BaseGraphQl.getUserVisit(env)));
    }
    
}
