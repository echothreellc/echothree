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
import com.echothree.model.data.contactlist.common.ContactListConstants;
import com.echothree.model.data.contactlist.common.CustomerTypeContactListGroupConstants;
import com.echothree.model.data.contactlist.common.PartyTypeContactListGroupConstants;
import com.echothree.model.data.contactlist.server.entity.ContactListGroup;
import com.echothree.model.data.contactlist.server.entity.ContactListGroupDetail;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.annotations.connection.GraphQLConnection;
import graphql.schema.DataFetchingEnvironment;
import java.util.ArrayList;
import java.util.stream.Collectors;

@GraphQLDescription("contact list group object")
@GraphQLName("ContactListGroup")
public class ContactListGroupObject
        extends BaseEntityInstanceObject {
    
    private final ContactListGroup contactListGroup; // Always Present
    
    public ContactListGroupObject(ContactListGroup contactListGroup) {
        super(contactListGroup.getPrimaryKey());
        
        this.contactListGroup = contactListGroup;
    }

    private ContactListGroupDetail contactListGroupDetail; // Optional, use getContactListGroupDetail()
    
    private ContactListGroupDetail getContactListGroupDetail() {
        if(contactListGroupDetail == null) {
            contactListGroupDetail = contactListGroup.getLastDetail();
        }
        
        return contactListGroupDetail;
    }
    
    @GraphQLField
    @GraphQLDescription("contact list group name")
    @GraphQLNonNull
    public String getContactListGroupName() {
        return getContactListGroupDetail().getContactListGroupName();
    }
    
    @GraphQLField
    @GraphQLDescription("is default")
    @GraphQLNonNull
    public boolean getIsDefault() {
        return getContactListGroupDetail().getIsDefault();
    }
    
    @GraphQLField
    @GraphQLDescription("sort order")
    @GraphQLNonNull
    public int getSortOrder() {
        return getContactListGroupDetail().getSortOrder();
    }
    
    @GraphQLField
    @GraphQLDescription("description")
    @GraphQLNonNull
    public String getDescription(final DataFetchingEnvironment env) {
        var contactListControl = Session.getModelController(ContactListControl.class);
        var userControl = Session.getModelController(UserControl.class);

        return contactListControl.getBestContactListGroupDescription(contactListGroup, userControl.getPreferredLanguageFromUserVisit(BaseGraphQl.getUserVisit(env)));
    }

    @GraphQLField
    @GraphQLDescription("contact lists")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    public CountingPaginatedData<ContactListObject> getContactLists(final DataFetchingEnvironment env) {
        if(ContactListSecurityUtils.getHasContactListsAccess(env)) {
            var chainControl = Session.getModelController(ContactListControl.class);
            var totalCount = chainControl.countContactListsByContactListGroup(contactListGroup);

            try(var objectLimiter = new ObjectLimiter(env, ContactListConstants.COMPONENT_VENDOR_NAME, ContactListConstants.ENTITY_TYPE_NAME, totalCount)) {
                var entities = chainControl.getContactListsByContactListGroup(contactListGroup);
                var contactLists = entities.stream().map(ContactListObject::new).collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                return new CountedObjects<>(objectLimiter, contactLists);
            }
        } else {
            return Connections.emptyConnection();
        }
    }

    @GraphQLField
    @GraphQLDescription("customer type contact list groups")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    public CountingPaginatedData<CustomerTypeContactListGroupObject> getCustomerTypeContactListGroups(final DataFetchingEnvironment env) {
        if(ContactListSecurityUtils.getHasCustomerTypeContactListGroupsAccess(env)) {
            var contactListControl = Session.getModelController(ContactListControl.class);
            var totalCount = contactListControl.countCustomerTypeContactListGroupsByContactListGroup(contactListGroup);

            try(var objectLimiter = new ObjectLimiter(env, CustomerTypeContactListGroupConstants.COMPONENT_VENDOR_NAME, CustomerTypeContactListGroupConstants.ENTITY_TYPE_NAME, totalCount)) {
                var entities = contactListControl.getCustomerTypeContactListGroupsByContactListGroup(contactListGroup);
                var customerTypeContactListGroups = entities.stream().map(CustomerTypeContactListGroupObject::new).collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                return new CountedObjects<>(objectLimiter, customerTypeContactListGroups);
            }
        } else {
            return Connections.emptyConnection();
        }
    }

    @GraphQLField
    @GraphQLDescription("party type contact list groups")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    public CountingPaginatedData<PartyTypeContactListGroupObject> getPartyTypeContactListGroups(final DataFetchingEnvironment env) {
        if(ContactListSecurityUtils.getHasPartyTypeContactListGroupsAccess(env)) {
            var contactListControl = Session.getModelController(ContactListControl.class);
            var totalCount = contactListControl.countPartyTypeContactListGroupsByContactListGroup(contactListGroup);

            try(var objectLimiter = new ObjectLimiter(env, PartyTypeContactListGroupConstants.COMPONENT_VENDOR_NAME, PartyTypeContactListGroupConstants.ENTITY_TYPE_NAME, totalCount)) {
                var entities = contactListControl.getPartyTypeContactListGroupsByContactListGroup(contactListGroup);
                var partyTypeContactListGroups = entities.stream().map(PartyTypeContactListGroupObject::new).collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                return new CountedObjects<>(objectLimiter, partyTypeContactListGroups);
            }
        } else {
            return Connections.emptyConnection();
        }
    }

}
