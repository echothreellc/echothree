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

import com.echothree.model.control.accounting.server.graphql.AccountingSecurityUtils;
import com.echothree.model.control.accounting.server.graphql.CurrencyObject;
import com.echothree.model.control.graphql.server.graphql.BaseObject;
import com.echothree.model.data.geo.server.entity.GeoCodeCurrency;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;

@GraphQLDescription("geo code currency object")
@GraphQLName("GeoCodeCurrency")
public class GeoCodeCurrencyObject
        extends BaseObject {

    private final GeoCodeCurrency geoCodeCurrency; // Always Present

    public GeoCodeCurrencyObject(GeoCodeCurrency geoCodeCurrency) {
        this.geoCodeCurrency = geoCodeCurrency;
    }

    @GraphQLField
    @GraphQLDescription("geo code")
    public GeoCodeObject getGeoCode(final DataFetchingEnvironment env) {
        return GeoSecurityUtils.getHasGeoCodeAccess(env) ? new GeoCodeObject(geoCodeCurrency.getGeoCode()) : null;
    }

    @GraphQLField
    @GraphQLDescription("currency")
    public CurrencyObject getCurrency(final DataFetchingEnvironment env) {
        return AccountingSecurityUtils.getHasCurrencyAccess(env) ? new CurrencyObject(geoCodeCurrency.getCurrency()) : null;
    }

    @GraphQLField
    @GraphQLDescription("is default")
    @GraphQLNonNull
    public boolean getIsDefault() {
        return geoCodeCurrency.getIsDefault();
    }

    @GraphQLField
    @GraphQLDescription("sort order")
    @GraphQLNonNull
    public int getSortOrder() {
        return geoCodeCurrency.getSortOrder();
    }

}
