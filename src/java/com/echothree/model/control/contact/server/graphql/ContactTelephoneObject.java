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

import com.echothree.model.control.contact.common.workflow.TelephoneStatusConstants;
import com.echothree.model.control.geo.server.graphql.GeoCodeObject;
import com.echothree.model.control.geo.server.graphql.GeoSecurityUtils;
import com.echothree.model.control.graphql.server.graphql.BaseObject;
import com.echothree.model.control.workflow.server.graphql.WorkflowEntityStatusObject;
import com.echothree.model.data.contact.server.entity.ContactTelephone;
import com.echothree.util.common.persistence.BasePK;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;

@GraphQLDescription("contact telephone object")
@GraphQLName("ContactTelephone")
public class ContactTelephoneObject
        extends BaseObject
        implements ContactMechanismInterface {

    private final BasePK basePrimaryKey; // Always Present
    private final ContactTelephone contactTelephone; // Always Present

    public ContactTelephoneObject(BasePK basePrimaryKey, ContactTelephone contactTelephone) {
        this.basePrimaryKey = basePrimaryKey;
        this.contactTelephone = contactTelephone;
    }

    @GraphQLField
    @GraphQLDescription("country geo code")
    @GraphQLNonNull
    public GeoCodeObject getCountryGeoCode(final DataFetchingEnvironment env) {
        if(GeoSecurityUtils.getHasGeoCodeAccess(env)) {
            return new GeoCodeObject(contactTelephone.getCountryGeoCode());
        } else {
            return null;
        }
    }

    @GraphQLField
    @GraphQLDescription("area code")
    public String getAreaCode() {
        return contactTelephone.getAreaCode();
    }

    @GraphQLField
    @GraphQLDescription("telephone number")
    public String getTelephoneNumber() {
        return contactTelephone.getTelephoneNumber();
    }

    @GraphQLField
    @GraphQLDescription("telephone extension")
    public String getTelephoneExtension() {
        return contactTelephone.getTelephoneExtension();
    }

    @GraphQLField
    @GraphQLDescription("telephone status")
    public WorkflowEntityStatusObject getTelephoneStatus(final DataFetchingEnvironment env) {
        return getWorkflowEntityStatusObject(env, basePrimaryKey, TelephoneStatusConstants.Workflow_TELEPHONE_STATUS);
    }

}
