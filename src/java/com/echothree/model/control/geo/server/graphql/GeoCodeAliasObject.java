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

import com.echothree.model.control.graphql.server.graphql.BaseObject;
import com.echothree.model.data.geo.server.entity.GeoCodeAlias;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;

@GraphQLDescription("geo code alias object")
@GraphQLName("GeoCodeAlias")
public class GeoCodeAliasObject
        extends BaseObject {
    
    private final GeoCodeAlias geoCodeAlias; // Always Present
    
    public GeoCodeAliasObject(GeoCodeAlias geoCodeAlias) {
        this.geoCodeAlias = geoCodeAlias;
    }

    @GraphQLField
    @GraphQLDescription("geo code")
    public GeoCodeObject getGeoCode(final DataFetchingEnvironment env) {
        return GeoSecurityUtils.getHasGeoCodeAccess(env) ? new GeoCodeObject(geoCodeAlias.getGeoCode()) : null;
    }

    @GraphQLField
    @GraphQLDescription("geo code scope")
    public GeoCodeScopeObject getGeoCodeScope(final DataFetchingEnvironment env) {
        return GeoSecurityUtils.getHasGeoCodeScopeAccess(env) ? new GeoCodeScopeObject(geoCodeAlias.getGeoCodeScope()) : null;
    }

    @GraphQLField
    @GraphQLDescription("geo code alias type")
    public GeoCodeAliasTypeObject getGeoCodeAliasType(final DataFetchingEnvironment env) {
        return GeoSecurityUtils.getHasGeoCodeAliasTypeAccess(env) ? new GeoCodeAliasTypeObject(geoCodeAlias.getGeoCodeAliasType()) : null;
    }

    @GraphQLField
    @GraphQLDescription("alias")
    @GraphQLNonNull
    public String getAlias(final DataFetchingEnvironment env) {
        return geoCodeAlias.getAlias();
    }

}
