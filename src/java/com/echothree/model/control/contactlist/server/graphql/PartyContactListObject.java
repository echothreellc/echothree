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

package com.echothree.model.control.contactlist.server.graphql;

import com.echothree.model.control.contactlist.common.workflow.PartyContactListStatusConstants;
import com.echothree.model.control.graphql.server.graphql.BaseEntityInstanceObject;
import com.echothree.model.control.party.server.graphql.PartyObject;
import com.echothree.model.control.party.server.graphql.PartySecurityUtils;
import com.echothree.model.control.workflow.server.graphql.WorkflowEntityStatusObject;
import com.echothree.model.data.contactlist.server.entity.PartyContactList;
import com.echothree.model.data.contactlist.server.entity.PartyContactListDetail;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.schema.DataFetchingEnvironment;

@GraphQLDescription("party contact list object")
@GraphQLName("PartyContactList")
public class PartyContactListObject
        extends BaseEntityInstanceObject {

    private final PartyContactList partyContactList; // Always Present

    public PartyContactListObject(PartyContactList partyContactList) {
        super(partyContactList.getPrimaryKey());

        this.partyContactList = partyContactList;
    }

    private PartyContactListDetail partyContactListDetail; // Optional, use getPartyContactListDetail()

    private PartyContactListDetail getPartyContactListDetail() {
        if(partyContactListDetail == null) {
            partyContactListDetail = partyContactList.getLastDetail();
        }

        return partyContactListDetail;
    }

    @GraphQLField
    @GraphQLDescription("party")
    public PartyObject getParty(final DataFetchingEnvironment env) {
        var party = getPartyContactListDetail().getParty();

        return PartySecurityUtils.getHasPartyAccess(env, party) ? new PartyObject(party) : null;
    }

    @GraphQLField
    @GraphQLDescription("contact list")
    public ContactListObject getContactList(final DataFetchingEnvironment env) {
        var contactList = getPartyContactListDetail().getContactList();

        return ContactListSecurityUtils.getHasContactListAccess(env) ? new ContactListObject(contactList) : null;
    }

    @GraphQLField
    @GraphQLDescription("preferred contact list contact mechanism purpose")
    public ContactListContactMechanismPurposeObject getPreferredContactListContactMechanismPurpose(final DataFetchingEnvironment env) {
        var preferredContactListContactMechanismPurpose = getPartyContactListDetail().getPreferredContactListContactMechanismPurpose();

        return preferredContactListContactMechanismPurpose == null ? null : (ContactListSecurityUtils.getHasContactListContactMechanismPurposeAccess(env) ? new ContactListContactMechanismPurposeObject(preferredContactListContactMechanismPurpose) : null);
    }

    @GraphQLField
    @GraphQLDescription("party contact list status")
    public WorkflowEntityStatusObject getPartyContactListStatus(final DataFetchingEnvironment env) {
        return getWorkflowEntityStatusObject(env, PartyContactListStatusConstants.Workflow_PARTY_CONTACT_LIST_STATUS);
    }

}
