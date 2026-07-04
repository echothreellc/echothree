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

import com.echothree.model.control.chain.server.graphql.ChainObject;
import com.echothree.model.control.chain.server.graphql.ChainSecurityUtils;
import com.echothree.model.control.contactlist.server.control.ContactListControl;
import com.echothree.model.control.graphql.server.graphql.BaseEntityInstanceObject;
import com.echothree.model.control.graphql.server.util.BaseGraphQl;
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.data.contactlist.server.entity.ContactListType;
import com.echothree.model.data.contactlist.server.entity.ContactListTypeDetail;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;

@GraphQLDescription("contact list type object")
@GraphQLName("ContactListType")
public class ContactListTypeObject
        extends BaseEntityInstanceObject {
    
    private final ContactListType contactListType; // Always Present
    
    public ContactListTypeObject(ContactListType contactListType) {
        super(contactListType.getPrimaryKey());
        
        this.contactListType = contactListType;
    }

    private ContactListTypeDetail contactListTypeDetail; // Optional, use getContactListTypeDetail()
    
    private ContactListTypeDetail getContactListTypeDetail() {
        if(contactListTypeDetail == null) {
            contactListTypeDetail = contactListType.getLastDetail();
        }
        
        return contactListTypeDetail;
    }
    
    @GraphQLField
    @GraphQLDescription("contact list type name")
    @GraphQLNonNull
    public String getContactListTypeName() {
        return getContactListTypeDetail().getContactListTypeName();
    }
    
    @GraphQLField
    @GraphQLDescription("confirmation request chain")
    public ChainObject getConfirmationRequestChain(final DataFetchingEnvironment env) {
        var confirmationRequestChain = getContactListTypeDetail().getConfirmationRequestChain();

        return confirmationRequestChain == null ? null : (ChainSecurityUtils.getHasChainAccess(env) ? new ChainObject(confirmationRequestChain) : null);
    }
    
    @GraphQLField
    @GraphQLDescription("subscribe chain")
    public ChainObject getSubscribeChain(final DataFetchingEnvironment env) {
        var subscribeChain = getContactListTypeDetail().getSubscribeChain();

        return subscribeChain == null ? null : (ChainSecurityUtils.getHasChainAccess(env) ? new ChainObject(subscribeChain) : null);
    }
    
    @GraphQLField
    @GraphQLDescription("unsubscribe chain")
    public ChainObject getUnsubscribeChain(final DataFetchingEnvironment env) {
        var unsubscribeChain = getContactListTypeDetail().getUnsubscribeChain();

        return unsubscribeChain == null ? null : (ChainSecurityUtils.getHasChainAccess(env) ? new ChainObject(unsubscribeChain) : null);
    }
    
    @GraphQLField
    @GraphQLDescription("used for solicitation")
    @GraphQLNonNull
    public boolean getUsedForSolicitation() {
        return getContactListTypeDetail().getUsedForSolicitation();
    }
    
    @GraphQLField
    @GraphQLDescription("is default")
    @GraphQLNonNull
    public boolean getIsDefault() {
        return getContactListTypeDetail().getIsDefault();
    }
    
    @GraphQLField
    @GraphQLDescription("sort order")
    @GraphQLNonNull
    public int getSortOrder() {
        return getContactListTypeDetail().getSortOrder();
    }
    
    @GraphQLField
    @GraphQLDescription("description")
    @GraphQLNonNull
    public String getDescription(final DataFetchingEnvironment env) {
        var contactListControl = Session.getModelController(ContactListControl.class);
        var userControl = Session.getModelController(UserControl.class);

        return contactListControl.getBestContactListTypeDescription(contactListType, userControl.getPreferredLanguageFromUserVisit(BaseGraphQl.getUserVisit(env)));
    }

}
