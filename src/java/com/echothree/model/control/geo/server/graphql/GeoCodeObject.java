// --------------------------------------------------------------------------------
// Copyright 2002-2025 Echo Three, LLC
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
// -------------------------------------------------------------------------------

package com.echothree.model.control.geo.server.graphql;

import com.echothree.model.data.geo.server.entity.GeoCode;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLName;

@GraphQLDescription("geo code object")
@GraphQLName("GeoCode")
public class GeoCodeObject
        extends BaseGeoCodeObject {
    
    public GeoCodeObject(GeoCode geoCode) {
        super(geoCode);
    }

}
