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

import com.echothree.model.control.contact.common.workflow.EmailAddressStatusConstants;
import com.echothree.model.control.contact.common.workflow.EmailAddressVerificationConstants;
import com.echothree.model.control.graphql.server.graphql.BaseObject;
import com.echothree.model.control.workflow.server.graphql.WorkflowEntityStatusObject;
import com.echothree.model.data.contact.server.entity.ContactEmailAddress;
import com.echothree.util.common.persistence.BasePK;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.schema.DataFetchingEnvironment;

@GraphQLDescription("contact email address object")
@GraphQLName("ContactEmailAddress")
public class ContactEmailAddressObject
        extends BaseObject
        implements ContactMechanismInterface {

    private final BasePK basePrimaryKey; // Always Present
    private final ContactEmailAddress contactEmailAddress; // Always Present

    public ContactEmailAddressObject(BasePK basePrimaryKey, ContactEmailAddress contactEmailAddress) {
        this.basePrimaryKey = basePrimaryKey;
        this.contactEmailAddress = contactEmailAddress;
    }

    @GraphQLField
    @GraphQLDescription("email address")
    public String getEmailAddress() {
        return contactEmailAddress.getEmailAddress();
    }

    @GraphQLField
    @GraphQLDescription("email address status")
    public WorkflowEntityStatusObject getEmailAddressStatus(final DataFetchingEnvironment env) {
        return getWorkflowEntityStatusObject(env, basePrimaryKey, EmailAddressStatusConstants.Workflow_EMAIL_ADDRESS_STATUS);
    }

    @GraphQLField
    @GraphQLDescription("email address verification")
    public WorkflowEntityStatusObject getEmailAddressVerification(final DataFetchingEnvironment env) {
        return getWorkflowEntityStatusObject(env, basePrimaryKey, EmailAddressVerificationConstants.Workflow_EMAIL_ADDRESS_VERIFICATION);
    }

}
