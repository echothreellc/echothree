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

import com.echothree.model.control.contact.common.workflow.WebAddressStatusConstants;
import com.echothree.model.control.graphql.server.graphql.BaseObject;
import com.echothree.model.control.workflow.server.graphql.WorkflowEntityStatusObject;
import com.echothree.model.data.contact.server.entity.ContactWebAddress;
import com.echothree.util.common.persistence.BasePK;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.schema.DataFetchingEnvironment;

@GraphQLDescription("contact web address object")
@GraphQLName("ContactWebAddress")
public class ContactWebAddressObject
        extends BaseObject
        implements ContactMechanismInterface {

    private final BasePK basePrimaryKey; // Always Present
    private final ContactWebAddress contactWebAddress; // Always Present

    public ContactWebAddressObject(BasePK basePrimaryKey, ContactWebAddress contactWebAddress) {
        this.basePrimaryKey = basePrimaryKey;
        this.contactWebAddress = contactWebAddress;
    }

    @GraphQLField
    @GraphQLDescription("url")
    public String getUrl() {
        return contactWebAddress.getUrl();
    }

    @GraphQLField
    @GraphQLDescription("web address status")
    public WorkflowEntityStatusObject getWebAddressStatus(final DataFetchingEnvironment env) {
        return getWorkflowEntityStatusObject(env, basePrimaryKey, WebAddressStatusConstants.Workflow_WEB_ADDRESS_STATUS);
    }

}
