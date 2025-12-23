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

package com.echothree.model.control.contact.server.graphql;

import com.echothree.model.control.contact.common.workflow.PostalAddressStatusConstants;
import com.echothree.model.control.geo.server.control.GeoControl;
import com.echothree.model.control.geo.server.graphql.GeoCodeObject;
import com.echothree.model.control.geo.server.graphql.GeoSecurityUtils;
import com.echothree.model.control.graphql.server.graphql.BaseObject;
import com.echothree.model.control.graphql.server.util.BaseGraphQl;
import com.echothree.model.control.party.server.graphql.NameSuffixObject;
import com.echothree.model.control.party.server.graphql.PersonalTitleObject;
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.control.workflow.server.graphql.WorkflowEntityStatusObject;
import com.echothree.model.data.contact.server.entity.ContactPostalAddress;
import com.echothree.model.data.geo.server.entity.GeoCode;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;

@GraphQLDescription("contact postal address object")
@GraphQLName("ContactPostalAddress")
public class ContactPostalAddressObject
        extends BaseObject
        implements ContactMechanismInterface {

    private final BasePK basePrimaryKey; // Always Present
    private final ContactPostalAddress contactPostalAddress; // Always Present

    public ContactPostalAddressObject(BasePK basePrimaryKey, ContactPostalAddress contactPostalAddress) {
        this.basePrimaryKey = basePrimaryKey;
        this.contactPostalAddress = contactPostalAddress;
    }

    @GraphQLField
    @GraphQLDescription("personal title")
    public PersonalTitleObject getPersonalTitle() {
        var personalTitle = contactPostalAddress.getPersonalTitle();

        return personalTitle == null ? null : new PersonalTitleObject(personalTitle);
    }

    @GraphQLField
    @GraphQLDescription("first name")
    public String getFirstName() {
        return contactPostalAddress.getFirstName();
    }

    @GraphQLField
    @GraphQLDescription("middle name")
    public String getMiddleName() {
        return contactPostalAddress.getMiddleName();
    }

    @GraphQLField
    @GraphQLDescription("last name")
    public String getLastName() {
        return contactPostalAddress.getLastName();
    }

    @GraphQLField
    @GraphQLDescription("name suffix")
    public NameSuffixObject getNameSuffix() {
        var nameSuffix = contactPostalAddress.getNameSuffix();

        return nameSuffix == null ? null : new NameSuffixObject(nameSuffix);
    }

    @GraphQLField
    @GraphQLDescription("company name")
    public String getCompanyName() {
        return contactPostalAddress.getCompanyName();
    }

    @GraphQLField
    @GraphQLDescription("attention")
    public String getAttention() {
        return contactPostalAddress.getAttention();
    }

    @GraphQLField
    @GraphQLDescription("address 1")
    @GraphQLNonNull
    public String getAddress1() {
        return contactPostalAddress.getAddress1();
    }

    @GraphQLField
    @GraphQLDescription("address 2")
    public String getAddress2() {
        return contactPostalAddress.getAddress2();
    }

    @GraphQLField
    @GraphQLDescription("address 3")
    public String getAddress3() {
        return contactPostalAddress.getAddress3();
    }

    private GeoCode getCityGeoCode() {
        return contactPostalAddress.getCityGeoCode();
    }

    @GraphQLField
    @GraphQLDescription("city")
    public String getCity(final DataFetchingEnvironment env) {
        var description = contactPostalAddress.getCity();
        var cityGeoCode = getCityGeoCode();

        if(description == null) {
            var geoControl = Session.getModelController(GeoControl.class);
            var userControl = Session.getModelController(UserControl.class);

            description = geoControl.getBestGeoCodeDescription(cityGeoCode, userControl.getPreferredLanguageFromUserVisit(BaseGraphQl.getUserVisit(env)));

            if(description == null) {
                description = geoControl.getAliasForCity(cityGeoCode);
            }
        }

        return description;
    }

    @GraphQLField
    @GraphQLDescription("city geo code")
    public GeoCodeObject getCityGeoCode(final DataFetchingEnvironment env) {
        if(GeoSecurityUtils.getHasGeoCodeAccess(env)) {
            var cityGeoCode = getCityGeoCode();

            return cityGeoCode == null ? null : new GeoCodeObject(cityGeoCode);
        } else {
            return null;
        }
    }

    @GraphQLField
    @GraphQLDescription("county geo code")
    public GeoCodeObject getCountyGeoCode(final DataFetchingEnvironment env) {
        if(GeoSecurityUtils.getHasGeoCodeAccess(env)) {
            var countyGeoCode = contactPostalAddress.getCountyGeoCode();

            return countyGeoCode == null ? null : new GeoCodeObject(countyGeoCode);
        } else {
            return null;
        }
    }

    private GeoCode getStateGeoCode() {
        return contactPostalAddress.getStateGeoCode();
    }

    @GraphQLField
    @GraphQLDescription("state")
    public String getState(final DataFetchingEnvironment env) {
        var description = contactPostalAddress.getState();
        var stateGeoCode = getStateGeoCode();

        if(description == null) {
            var geoControl = Session.getModelController(GeoControl.class);
            var userControl = Session.getModelController(UserControl.class);

            description = geoControl.getBestGeoCodeDescription(stateGeoCode, userControl.getPreferredLanguageFromUserVisit(BaseGraphQl.getUserVisit(env)));

            if(description == null) {
                description = geoControl.getAliasForState(stateGeoCode);
            }
        }

        return description;
    }

    @GraphQLField
    @GraphQLDescription("state geo code")
    public GeoCodeObject getStateGeoCode(final DataFetchingEnvironment env) {
        if(GeoSecurityUtils.getHasGeoCodeAccess(env)) {
            var stateGeoCode = getStateGeoCode();

            return stateGeoCode == null ? null : new GeoCodeObject(stateGeoCode);
        } else {
            return null;
        }
    }

    private GeoCode getPostalCodeGeoCode() {
        return contactPostalAddress.getPostalCodeGeoCode();
    }

    @GraphQLField
    @GraphQLDescription("postal code")
    public String getPostalCode(final DataFetchingEnvironment env) {
        var description = contactPostalAddress.getPostalCode();
        var postalCodeGeoCode = getPostalCodeGeoCode();

        if(description == null) {
            var geoControl = Session.getModelController(GeoControl.class);
            var userControl = Session.getModelController(UserControl.class);

            description = geoControl.getBestGeoCodeDescription(postalCodeGeoCode, userControl.getPreferredLanguageFromUserVisit(BaseGraphQl.getUserVisit(env)));

            if(description == null) {
                description = geoControl.getAliasForPostalCode(postalCodeGeoCode);
            }
        }

        return description;
    }

    @GraphQLField
    @GraphQLDescription("postal code geo code")
    public GeoCodeObject getPostalCodeGeoCode(final DataFetchingEnvironment env) {
        if(GeoSecurityUtils.getHasGeoCodeAccess(env)) {
            var postalCodeGeoCode = getPostalCodeGeoCode();

            return postalCodeGeoCode == null ? null : new GeoCodeObject(postalCodeGeoCode);
        } else {
            return null;
        }
    }

    @GraphQLField
    @GraphQLDescription("country geo code")
    @GraphQLNonNull
    public GeoCodeObject getCountryGeoCode(final DataFetchingEnvironment env) {
        if(GeoSecurityUtils.getHasGeoCodeAccess(env)) {
            return new GeoCodeObject(contactPostalAddress.getCountryGeoCode());
        } else {
            return null;
        }
    }

    @GraphQLField
    @GraphQLDescription("is commercial")
    @GraphQLNonNull
    public boolean getIsCommercial() {
        return contactPostalAddress.getIsCommercial();
    }

    @GraphQLField
    @GraphQLDescription("postal address status")
    public WorkflowEntityStatusObject getPostalAddressStatus(final DataFetchingEnvironment env) {
        return getWorkflowEntityStatusObject(env, basePrimaryKey, PostalAddressStatusConstants.Workflow_POSTAL_ADDRESS_STATUS);
    }

}
