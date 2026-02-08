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
import com.echothree.model.data.geo.server.entity.GeoCodeRelationship;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.schema.DataFetchingEnvironment;

@GraphQLDescription("geo code alias object")
@GraphQLName("GeoCodeRelationship")
public class GeoCodeRelationshipObject
        extends BaseObject {
    
    private final GeoCodeRelationship geoCodeRelationship; // Always Present
    
    public GeoCodeRelationshipObject(GeoCodeRelationship geoCodeRelationship) {
        this.geoCodeRelationship = geoCodeRelationship;
    }

    @GraphQLField
    @GraphQLDescription("from geo code")
    public GeoCodeObject getFromGeoCode(final DataFetchingEnvironment env) {
        return GeoSecurityUtils.getHasGeoCodeAccess(env) ? new GeoCodeObject(geoCodeRelationship.getFromGeoCode()) : null;
    }

    @GraphQLField
    @GraphQLDescription("to geo code")
    public GeoCodeObject getToGeoCode(final DataFetchingEnvironment env) {
        return GeoSecurityUtils.getHasGeoCodeAccess(env) ? new GeoCodeObject(geoCodeRelationship.getToGeoCode()) : null;
    }

}
