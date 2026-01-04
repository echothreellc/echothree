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

import com.echothree.model.control.contact.server.control.ContactControl;
import com.echothree.model.control.graphql.server.graphql.BaseEntityInstanceObject;
import com.echothree.model.control.graphql.server.util.BaseGraphQl;
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.data.contact.server.entity.ContactMechanismPurpose;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;

@GraphQLDescription("contact mechanism purpose object")
@GraphQLName("ContactMechanismPurpose")
public class ContactMechanismPurposeObject
        extends BaseEntityInstanceObject {
    
    private final ContactMechanismPurpose contactMechanismPurpose; // Always Present
    
    public ContactMechanismPurposeObject(ContactMechanismPurpose contactMechanismPurpose) {
        super(contactMechanismPurpose.getPrimaryKey());

        this.contactMechanismPurpose = contactMechanismPurpose;
    }
    
    @GraphQLField
    @GraphQLDescription("contact mechanism type name")
    @GraphQLNonNull
    public String getContactMechanismPurposeName() {
        return contactMechanismPurpose.getContactMechanismPurposeName();
    }

    @GraphQLField
    @GraphQLDescription("event subscriber")
    @GraphQLNonNull
    public Boolean getEventSubscriber() {
        return contactMechanismPurpose.getEventSubscriber();
    }

    @GraphQLField
    @GraphQLDescription("is default")
    @GraphQLNonNull
    public Boolean getIsDefault() {
        return contactMechanismPurpose.getIsDefault();
    }

    @GraphQLField
    @GraphQLDescription("sort order")
    @GraphQLNonNull
    public Integer getSortOrder() {
        return contactMechanismPurpose.getSortOrder();
    }

    @GraphQLField
    @GraphQLDescription("description")
    @GraphQLNonNull
    public String getDescription(final DataFetchingEnvironment env) {
        var contactControl = Session.getModelController(ContactControl.class);
        var userControl = Session.getModelController(UserControl.class);

        return contactControl.getBestContactMechanismPurposeDescription(contactMechanismPurpose, userControl.getPreferredLanguageFromUserVisit(BaseGraphQl.getUserVisit(env)));
    }
    
}
