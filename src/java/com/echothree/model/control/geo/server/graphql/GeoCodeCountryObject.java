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
import com.echothree.model.data.geo.server.entity.GeoCodeCountry;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;

@GraphQLDescription("geo code country object")
@GraphQLName("GeoCodeCountry")
public class GeoCodeCountryObject
        extends BaseObject
        implements GeoCodeInterface {

    private final GeoCodeCountry geoCodeCountry; // Always Present

    public GeoCodeCountryObject(GeoCodeCountry geoCodeCountry) {
        this.geoCodeCountry = geoCodeCountry;
    }

    @GraphQLField
    @GraphQLDescription("telephone code")
    public String getTelephoneCode() {
        return geoCodeCountry.getTelephoneCode();
    }

    @GraphQLField
    @GraphQLDescription("area code pattern")
    public String getAreaCodePattern() {
        return geoCodeCountry.getAreaCodePattern();
    }

    @GraphQLField
    @GraphQLDescription("area code required")
    @GraphQLNonNull
    public boolean getAreaCodeRequired() {
        return geoCodeCountry.getAreaCodeRequired();
    }

    @GraphQLField
    @GraphQLDescription("area code example")
    public String getAreaCodeExample() {
        return geoCodeCountry.getAreaCodeExample();
    }

    @GraphQLField
    @GraphQLDescription("telephone number pattern")
    public String getTelephoneNumberPattern() {
        return geoCodeCountry.getTelephoneNumberPattern();
    }

    @GraphQLField
    @GraphQLDescription("telephone number example")
    public String getTelephoneNumberExample() {
        return geoCodeCountry.getTelephoneNumberExample();
    }

    //| geoc_pstafmt_postaladdressformatid | bigint       | NO   | MUL | NULL    |       |

    @GraphQLField
    @GraphQLDescription("city required")
    @GraphQLNonNull
    public boolean getCityRequired() {
        return geoCodeCountry.getCityRequired();
    }

    @GraphQLField
    @GraphQLDescription("city geo code required")
    @GraphQLNonNull
    public boolean getCityGeoCodeRequired() {
        return geoCodeCountry.getCityGeoCodeRequired();
    }

    @GraphQLField
    @GraphQLDescription("state required")
    @GraphQLNonNull
    public boolean getStateRequired() {
        return geoCodeCountry.getStateRequired();
    }

    @GraphQLField
    @GraphQLDescription("state geo code required")
    @GraphQLNonNull
    public boolean getStateGeoCodeRequired() {
        return geoCodeCountry.getStateGeoCodeRequired();
    }

    @GraphQLField
    @GraphQLDescription("postal code pattern")
    public String getPostalCodePattern() {
        return geoCodeCountry.getPostalCodePattern();
    }

    @GraphQLField
    @GraphQLDescription("postal code required")
    @GraphQLNonNull
    public boolean getPostalCodeRequired() {
        return geoCodeCountry.getPostalCodeRequired();
    }

    @GraphQLField
    @GraphQLDescription("postal code geo code required")
    @GraphQLNonNull
    public boolean getPostalCodeGeoCodeRequired() {
        return geoCodeCountry.getPostalCodeGeoCodeRequired();
    }

    @GraphQLField
    @GraphQLDescription("postal code length")
    @GraphQLNonNull
    public int getPostalCodeLength() {
        return geoCodeCountry.getPostalCodeLength();
    }

    @GraphQLField
    @GraphQLDescription("postal code geo code length")
    @GraphQLNonNull
    public int getPostalCodeGeoCodeLength() {
        return geoCodeCountry.getPostalCodeGeoCodeLength();
    }

    @GraphQLField
    @GraphQLDescription("postal code example")
    public String getPostalCodeExample() {
        return geoCodeCountry.getPostalCodeExample();
    }

}
