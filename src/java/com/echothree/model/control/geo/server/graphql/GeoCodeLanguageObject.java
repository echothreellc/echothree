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
import com.echothree.model.control.party.server.graphql.LanguageObject;
import com.echothree.model.control.party.server.graphql.PartySecurityUtils;
import com.echothree.model.data.geo.server.entity.GeoCodeLanguage;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;

@GraphQLDescription("geo code language object")
@GraphQLName("GeoCodeLanguage")
public class GeoCodeLanguageObject
        extends BaseObject {

    private final GeoCodeLanguage geoCodeLanguage; // Always Present

    public GeoCodeLanguageObject(GeoCodeLanguage geoCodeLanguage) {
        this.geoCodeLanguage = geoCodeLanguage;
    }

    @GraphQLField
    @GraphQLDescription("geo code")
    public GeoCodeObject getGeoCode(final DataFetchingEnvironment env) {
        return GeoSecurityUtils.getHasGeoCodeAccess(env) ? new GeoCodeObject(geoCodeLanguage.getGeoCode()) : null;
    }

    @GraphQLField
    @GraphQLDescription("language")
    public LanguageObject getLanguage(final DataFetchingEnvironment env) {
        return PartySecurityUtils.getHasLanguageAccess(env) ? new LanguageObject(geoCodeLanguage.getLanguage()) : null;
    }

    @GraphQLField
    @GraphQLDescription("is default")
    @GraphQLNonNull
    public boolean getIsDefault() {
        return geoCodeLanguage.getIsDefault();
    }

    @GraphQLField
    @GraphQLDescription("sort order")
    @GraphQLNonNull
    public int getSortOrder() {
        return geoCodeLanguage.getSortOrder();
    }

}
