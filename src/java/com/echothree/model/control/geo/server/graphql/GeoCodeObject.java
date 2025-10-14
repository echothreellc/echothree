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

import com.echothree.model.control.geo.common.GeoCodeTypes;
import com.echothree.model.control.geo.server.control.GeoControl;
import com.echothree.model.control.graphql.server.graphql.BaseEntityInstanceObject;
import com.echothree.model.control.graphql.server.util.BaseGraphQl;
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.data.geo.server.entity.GeoCode;
import com.echothree.model.data.geo.server.entity.GeoCodeDetail;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;

@GraphQLDescription("geo code object")
@GraphQLName("GeoCode")
public class GeoCodeObject
        extends BaseEntityInstanceObject {

    private final GeoCode geoCode; // Always Present

    public GeoCodeObject(GeoCode geoCode) {
        super(geoCode.getPrimaryKey());

        this.geoCode = geoCode;
    }

    private GeoCodeDetail geoCodeDetail; // Optional, use getGeoCodeDetail()

    private GeoCodeDetail getGeoCodeDetail() {
        if(geoCodeDetail == null) {
            geoCodeDetail = geoCode.getLastDetail();
        }

        return geoCodeDetail;
    }

    @GraphQLField
    @GraphQLDescription("geo code type name")
    @GraphQLNonNull
    public String getGeoCodeName() {
        return getGeoCodeDetail().getGeoCodeName();
    }

    @GraphQLField
    @GraphQLDescription("geo code type")
    public GeoCodeTypeObject getGeoCodeType(final DataFetchingEnvironment env) {
        return GeoSecurityUtils.getHasGeoCodeTypeAccess(env) ? new GeoCodeTypeObject(getGeoCodeDetail().getGeoCodeType()) : null;
    }

    @GraphQLField
    @GraphQLDescription("geo code scope")
    public GeoCodeScopeObject getGeoCodeScope(final DataFetchingEnvironment env) {
        return GeoSecurityUtils.getHasGeoCodeScopeAccess(env) ? new GeoCodeScopeObject(getGeoCodeDetail().getGeoCodeScope()) : null;
    }

    @GraphQLField
    @GraphQLDescription("is default")
    @GraphQLNonNull
    public boolean getIsDefault() {
        return getGeoCodeDetail().getIsDefault();
    }

    @GraphQLField
    @GraphQLDescription("sort order")
    @GraphQLNonNull
    public int getSortOrder() {
        return getGeoCodeDetail().getSortOrder();
    }

    @GraphQLField
    @GraphQLDescription("description")
    @GraphQLNonNull
    public String getDescription(final DataFetchingEnvironment env) {
        var geoControl = Session.getModelController(GeoControl.class);
        var userControl = Session.getModelController(UserControl.class);

        return geoControl.getBestGeoCodeDescription(geoCode, userControl.getPreferredLanguageFromUserVisit(BaseGraphQl.getUserVisit(env)));
    }

    private GeoCodeTypes geoCodeTypeEnum = null; // Optional, use getGeoCodeTypeEnum()

    protected GeoCodeTypes getGeoCodeTypeEnum() {
        if(geoCodeTypeEnum == null) {
            geoCodeTypeEnum = GeoCodeTypes.valueOf(getGeoCodeDetail().getGeoCodeType().getLastDetail().getGeoCodeTypeName());
        }

        return geoCodeTypeEnum;
    }

    @GraphQLField
    @GraphQLDescription("geo code")
    public GeoCodeInterface getGeoCode(final DataFetchingEnvironment env) {
        var geoControl = Session.getModelController(GeoControl.class);

        return switch(getGeoCodeTypeEnum()) {
            case ROOT -> null;
            case COUNTRY -> new GeoCodeCountryObject(geoControl.getGeoCodeCountry(geoCode));
            case STATE -> null;
            case COUNTY -> null;
            case CITY -> null;
            case ZIP_CODE -> null;
        };
    }

}
