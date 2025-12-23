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
import com.echothree.model.control.warehouse.server.control.LocationUseTypeControl;
import com.echothree.model.data.warehouse.server.entity.LocationUseType;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;

@GraphQLDescription("inventory condition object")
@GraphQLName("LocationUseType")
public class LocationUseTypeObject
        extends BaseEntityInstanceObject {
    
    private final LocationUseType locationUseType; // Always Present
    
    public LocationUseTypeObject(LocationUseType locationUseType) {
        super(locationUseType.getPrimaryKey());
        
        this.locationUseType = locationUseType;
    }

    @GraphQLField
    @GraphQLDescription("inventory condition name")
    @GraphQLNonNull
    public String getLocationUseTypeName() {
        return locationUseType.getLocationUseTypeName();
    }

    @GraphQLField
    @GraphQLDescription("allow multiple")
    @GraphQLNonNull
    public boolean getAllowMultiple() {
        return locationUseType.getAllowMultiple();
    }

    @GraphQLField
    @GraphQLDescription("is default")
    @GraphQLNonNull
    public boolean getIsDefault() {
        return locationUseType.getIsDefault();
    }

    @GraphQLField
    @GraphQLDescription("sort order")
    @GraphQLNonNull
    public int getSortOrder() {
        return locationUseType.getSortOrder();
    }
    
    @GraphQLField
    @GraphQLDescription("description")
    @GraphQLNonNull
    public String getDescription(final DataFetchingEnvironment env) {
        var locationUseTypeControl = Session.getModelController(LocationUseTypeControl.class);
        var userControl = Session.getModelController(UserControl.class);

        return locationUseTypeControl.getBestLocationUseTypeDescription(locationUseType, userControl.getPreferredLanguageFromUserVisit(BaseGraphQl.getUserVisit(env)));
    }
    
}
