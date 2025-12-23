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

package com.echothree.model.control.geo.server.graphql;

import com.echothree.model.control.geo.server.control.GeoControl;
import com.echothree.model.control.graphql.server.graphql.BaseEntityInstanceObject;
import com.echothree.model.control.graphql.server.util.BaseGraphQl;
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.data.geo.server.entity.GeoCodeScope;
import com.echothree.model.data.geo.server.entity.GeoCodeScopeDetail;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;

@GraphQLDescription("geo code scope object")
@GraphQLName("GeoCodeScope")
public class GeoCodeScopeObject
        extends BaseEntityInstanceObject {
    
    private final GeoCodeScope geoCodeScope; // Always Present
    
    public GeoCodeScopeObject(GeoCodeScope geoCodeScope) {
        super(geoCodeScope.getPrimaryKey());
        
        this.geoCodeScope = geoCodeScope;
    }

    private GeoCodeScopeDetail geoCodeScopeDetail; // Optional, use getGeoCodeScopeDetail()
    
    private GeoCodeScopeDetail getGeoCodeScopeDetail() {
        if(geoCodeScopeDetail == null) {
            geoCodeScopeDetail = geoCodeScope.getLastDetail();
        }
        
        return geoCodeScopeDetail;
    }
    
    @GraphQLField
    @GraphQLDescription("geo code scope name")
    @GraphQLNonNull
    public String getGeoCodeScopeName() {
        return getGeoCodeScopeDetail().getGeoCodeScopeName();
    }

    @GraphQLField
    @GraphQLDescription("is default")
    @GraphQLNonNull
    public boolean getIsDefault() {
        return getGeoCodeScopeDetail().getIsDefault();
    }
    
    @GraphQLField
    @GraphQLDescription("sort order")
    @GraphQLNonNull
    public int getSortOrder() {
        return getGeoCodeScopeDetail().getSortOrder();
    }
    
    @GraphQLField
    @GraphQLDescription("description")
    @GraphQLNonNull
    public String getDescription(final DataFetchingEnvironment env) {
        var geoControl = Session.getModelController(GeoControl.class);
        var userControl = Session.getModelController(UserControl.class);

        return geoControl.getBestGeoCodeScopeDescription(geoCodeScope, userControl.getPreferredLanguageFromUserVisit(BaseGraphQl.getUserVisit(env)));
    }

}
