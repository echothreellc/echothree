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

package com.echothree.model.control.party.server.graphql;

import com.echothree.model.control.geo.server.control.GeoControl;
import com.echothree.model.control.geo.server.graphql.GeoCodeLanguageObject;
import com.echothree.model.control.geo.server.graphql.GeoSecurityUtils;
import com.echothree.model.control.graphql.server.graphql.BaseEntityInstanceObject;
import com.echothree.model.control.graphql.server.graphql.count.Connections;
import com.echothree.model.control.graphql.server.graphql.count.CountedObjects;
import com.echothree.model.control.graphql.server.graphql.count.CountingDataConnectionFetcher;
import com.echothree.model.control.graphql.server.graphql.count.CountingPaginatedData;
import com.echothree.model.control.graphql.server.util.BaseGraphQl;
import com.echothree.model.control.graphql.server.util.count.ObjectLimiter;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.data.geo.common.GeoCodeLanguageConstants;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.annotations.connection.GraphQLConnection;
import graphql.schema.DataFetchingEnvironment;
import java.util.ArrayList;
import java.util.stream.Collectors;

@GraphQLDescription("language object")
@GraphQLName("Language")
public class LanguageObject
        extends BaseEntityInstanceObject {
    
    private final Language language; // Always Present
    
    public LanguageObject(Language language) {
        super(language.getPrimaryKey());
        
        this.language = language;
    }
    
    @GraphQLField
    @GraphQLDescription("language iso name")
    @GraphQLNonNull
    public String getLanguageIsoName() {
        return language.getLanguageIsoName();
    }

    @GraphQLField
    @GraphQLDescription("is default")
    @GraphQLNonNull
    public Boolean getIsDefault() {
        return language.getIsDefault();
    }

    @GraphQLField
    @GraphQLDescription("sort order")
    @GraphQLNonNull
    public Integer getSortOrder() {
        return language.getSortOrder();
    }

    @GraphQLField
    @GraphQLDescription("description")
    @GraphQLNonNull
    public String getDescription(final DataFetchingEnvironment env) {
        var partyControl = Session.getModelController(PartyControl.class);
        var userControl = Session.getModelController(UserControl.class);

        return partyControl.getBestLanguageDescription(language, userControl.getPreferredLanguageFromUserVisit(BaseGraphQl.getUserVisit(env)));
    }

    @GraphQLField
    @GraphQLDescription("geo code languages")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    public CountingPaginatedData<GeoCodeLanguageObject> getGeoCodeLanguages(final DataFetchingEnvironment env) {
        if(GeoSecurityUtils.getHasGeoCodeLanguagesAccess(env)) {
            var geoControl = Session.getModelController(GeoControl.class);
            var totalCount = geoControl.countGeoCodeLanguagesByLanguage(language);

            try(var objectLimiter = new ObjectLimiter(env, GeoCodeLanguageConstants.COMPONENT_VENDOR_NAME, GeoCodeLanguageConstants.ENTITY_TYPE_NAME, totalCount)) {
                var entities = geoControl.getGeoCodeLanguagesByLanguage(language);
                var items = entities.stream().map(GeoCodeLanguageObject::new).collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                return new CountedObjects<>(objectLimiter, items);
            }
        } else {
            return Connections.emptyConnection();
        }
    }

}
