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

import com.echothree.model.control.contactlist.server.control.ContactListControl;
import com.echothree.model.control.graphql.server.graphql.BaseEntityInstanceObject;
import com.echothree.model.control.graphql.server.graphql.count.Connections;
import com.echothree.model.control.graphql.server.graphql.count.CountedObjects;
import com.echothree.model.control.graphql.server.graphql.count.CountingDataConnectionFetcher;
import com.echothree.model.control.graphql.server.graphql.count.CountingPaginatedData;
import com.echothree.model.control.graphql.server.util.BaseGraphQl;
import com.echothree.model.control.graphql.server.util.count.ObjectLimiter;
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.control.workflow.server.graphql.WorkflowEntranceObject;
import com.echothree.model.control.workflow.server.graphql.WorkflowSecurityUtils;
import com.echothree.model.data.contactlist.common.CustomerTypeContactListConstants;
import com.echothree.model.data.contactlist.common.PartyTypeContactListConstants;
import com.echothree.model.data.contactlist.server.entity.ContactList;
import com.echothree.model.data.contactlist.server.entity.ContactListDetail;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.annotations.connection.GraphQLConnection;
import graphql.schema.DataFetchingEnvironment;
import java.util.ArrayList;
import java.util.stream.Collectors;

@GraphQLDescription("contact list object")
@GraphQLName("ContactList")
public class ContactListObject
        extends BaseEntityInstanceObject {
    
    private final ContactList contactList; // Always Present
    
    public ContactListObject(ContactList contactList) {
        super(contactList.getPrimaryKey());
        
        this.contactList = contactList;
    }

    private ContactListDetail contactListDetail; // Optional, use getContactListDetail()
    
    private ContactListDetail getContactListDetail() {
        if(contactListDetail == null) {
            contactListDetail = contactList.getLastDetail();
        }
        
        return contactListDetail;
    }
    
    @GraphQLField
    @GraphQLDescription("contact list name")
    @GraphQLNonNull
    public String getContactListName() {
        return getContactListDetail().getContactListName();
    }
    
    @GraphQLField
    @GraphQLDescription("contact list group")
    public ContactListGroupObject getContactListGroup(final DataFetchingEnvironment env) {
        var contactListGroup = getContactListDetail().getContactListGroup();

        return ContactListSecurityUtils.getHasContactListGroupAccess(env) ? new ContactListGroupObject(contactListGroup) : null;
    }
    
    @GraphQLField
    @GraphQLDescription("contact list type")
    public ContactListTypeObject getContactListType(final DataFetchingEnvironment env) {
        var contactListType = getContactListDetail().getContactListType();

        return ContactListSecurityUtils.getHasContactListTypeAccess(env) ? new ContactListTypeObject(contactListType) : null;
    }
    
    @GraphQLField
    @GraphQLDescription("contact list frequency")
    public ContactListFrequencyObject getContactListFrequency(final DataFetchingEnvironment env) {
        var contactListFrequency = getContactListDetail().getContactListFrequency();

        return contactListFrequency == null ? null : (ContactListSecurityUtils.getHasContactListFrequencyAccess(env) ? new ContactListFrequencyObject(contactListFrequency) : null);
    }
    
    @GraphQLField
    @GraphQLDescription("default party contact list status")
    public WorkflowEntranceObject getDefaultPartyContactListStatus(final DataFetchingEnvironment env) {
        var defaultPartyContactListStatus = getContactListDetail().getDefaultPartyContactListStatus();

        return WorkflowSecurityUtils.getHasWorkflowEntranceAccess(env) ? new WorkflowEntranceObject(defaultPartyContactListStatus) : null;
    }
    
    @GraphQLField
    @GraphQLDescription("is default")
    @GraphQLNonNull
    public boolean getIsDefault() {
        return getContactListDetail().getIsDefault();
    }
    
    @GraphQLField
    @GraphQLDescription("sort order")
    @GraphQLNonNull
    public int getSortOrder() {
        return getContactListDetail().getSortOrder();
    }
    
    @GraphQLField
    @GraphQLDescription("description")
    @GraphQLNonNull
    public String getDescription(final DataFetchingEnvironment env) {
        var contactListControl = Session.getModelController(ContactListControl.class);
        var userControl = Session.getModelController(UserControl.class);

        return contactListControl.getBestContactListDescription(contactList, userControl.getPreferredLanguageFromUserVisit(BaseGraphQl.getUserVisit(env)));
    }

    @GraphQLField
    @GraphQLDescription("customer type contact lists")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    public CountingPaginatedData<CustomerTypeContactListObject> getCustomerTypeContactLists(final DataFetchingEnvironment env) {
        if(ContactListSecurityUtils.getHasCustomerTypeContactListsAccess(env)) {
            var contactListControl = Session.getModelController(ContactListControl.class);
            var totalCount = contactListControl.countCustomerTypeContactListsByContactList(contactList);

            try(var objectLimiter = new ObjectLimiter(env, CustomerTypeContactListConstants.COMPONENT_VENDOR_NAME, CustomerTypeContactListConstants.ENTITY_TYPE_NAME, totalCount)) {
                var entities = contactListControl.getCustomerTypeContactListsByContactList(contactList);
                var customerTypeContactLists = entities.stream().map(CustomerTypeContactListObject::new).collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                return new CountedObjects<>(objectLimiter, customerTypeContactLists);
            }
        } else {
            return Connections.emptyConnection();
        }
    }

    @GraphQLField
    @GraphQLDescription("party type contact lists")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    public CountingPaginatedData<PartyTypeContactListObject> getPartyTypeContactLists(final DataFetchingEnvironment env) {
        if(ContactListSecurityUtils.getHasPartyTypeContactListsAccess(env)) {
            var contactListControl = Session.getModelController(ContactListControl.class);
            var totalCount = contactListControl.countPartyTypeContactListsByContactList(contactList);

            try(var objectLimiter = new ObjectLimiter(env, PartyTypeContactListConstants.COMPONENT_VENDOR_NAME, PartyTypeContactListConstants.ENTITY_TYPE_NAME, totalCount)) {
                var entities = contactListControl.getPartyTypeContactListsByContactList(contactList);
                var partyTypeContactLists = entities.stream().map(PartyTypeContactListObject::new).collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                return new CountedObjects<>(objectLimiter, partyTypeContactLists);
            }
        } else {
            return Connections.emptyConnection();
        }
    }

}
