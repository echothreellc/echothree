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
import com.echothree.model.control.graphql.server.graphql.count.Connections;
import com.echothree.model.control.graphql.server.graphql.count.CountedObjects;
import com.echothree.model.control.graphql.server.graphql.count.CountingDataConnectionFetcher;
import com.echothree.model.control.graphql.server.graphql.count.CountingPaginatedData;
import com.echothree.model.control.graphql.server.util.BaseGraphQl;
import com.echothree.model.control.graphql.server.util.count.ObjectLimiter;
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.data.geo.common.GeoCodeAliasTypeConstants;
import com.echothree.model.data.geo.server.entity.GeoCodeType;
import com.echothree.model.data.geo.server.entity.GeoCodeTypeDetail;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.annotations.connection.GraphQLConnection;
import graphql.schema.DataFetchingEnvironment;
import java.util.ArrayList;
import java.util.stream.Collectors;

@GraphQLDescription("geo code type object")
@GraphQLName("GeoCodeType")
public class GeoCodeTypeObject
        extends BaseEntityInstanceObject {
    
    private final GeoCodeType geoCodeType; // Always Present
    
    public GeoCodeTypeObject(GeoCodeType geoCodeType) {
        super(geoCodeType.getPrimaryKey());
        
        this.geoCodeType = geoCodeType;
    }

    private GeoCodeTypeDetail geoCodeTypeDetail; // Optional, use getGeoCodeTypeDetail()
    
    private GeoCodeTypeDetail getGeoCodeTypeDetail() {
        if(geoCodeTypeDetail == null) {
            geoCodeTypeDetail = geoCodeType.getLastDetail();
        }
        
        return geoCodeTypeDetail;
    }
    
    @GraphQLField
    @GraphQLDescription("geo code type name")
    @GraphQLNonNull
    public String getGeoCodeTypeName() {
        return getGeoCodeTypeDetail().getGeoCodeTypeName();
    }

    @GraphQLField
    @GraphQLDescription("parent geo code type")
    public GeoCodeTypeObject getParentGeoCodeType() {
        var parentGeoCodeType = getGeoCodeTypeDetail().getParentGeoCodeType();
        
        return parentGeoCodeType == null ? null : new GeoCodeTypeObject(parentGeoCodeType);
    }

    @GraphQLField
    @GraphQLDescription("is default")
    @GraphQLNonNull
    public boolean getIsDefault() {
        return getGeoCodeTypeDetail().getIsDefault();
    }
    
    @GraphQLField
    @GraphQLDescription("sort order")
    @GraphQLNonNull
    public int getSortOrder() {
        return getGeoCodeTypeDetail().getSortOrder();
    }
    
    @GraphQLField
    @GraphQLDescription("description")
    @GraphQLNonNull
    public String getDescription(final DataFetchingEnvironment env) {
        var geoControl = Session.getModelController(GeoControl.class);
        var userControl = Session.getModelController(UserControl.class);

        return geoControl.getBestGeoCodeTypeDescription(geoCodeType, userControl.getPreferredLanguageFromUserVisit(BaseGraphQl.getUserVisit(env)));
    }

    @GraphQLField
    @GraphQLDescription("geo code languages")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    public CountingPaginatedData<GeoCodeAliasTypeObject> getGeoCodeAliasTypes(final DataFetchingEnvironment env) {
        if(GeoSecurityUtils.getHasGeoCodeAliasTypesAccess(env)) {
            var geoControl = Session.getModelController(GeoControl.class);
            var totalCount = geoControl.countGeoCodeAliasTypesByGeoCodeType(geoCodeType);

            try(var objectLimiter = new ObjectLimiter(env, GeoCodeAliasTypeConstants.COMPONENT_VENDOR_NAME, GeoCodeAliasTypeConstants.ENTITY_TYPE_NAME, totalCount)) {
                var entities = geoControl.getGeoCodeAliasTypes(geoCodeType);
                var items = entities.stream().map(GeoCodeAliasTypeObject::new).collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                return new CountedObjects<>(objectLimiter, items);
            }
        } else {
            return Connections.emptyConnection();
        }
    }

}
