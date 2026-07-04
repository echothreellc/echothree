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
import com.echothree.model.control.graphql.server.util.BaseGraphQl;
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.data.contactlist.server.entity.ContactListFrequency;
import com.echothree.model.data.contactlist.server.entity.ContactListFrequencyDetail;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;

@GraphQLDescription("contact list frequency object")
@GraphQLName("ContactListFrequency")
public class ContactListFrequencyObject
        extends BaseEntityInstanceObject {
    
    private final ContactListFrequency contactListFrequency; // Always Present
    
    public ContactListFrequencyObject(ContactListFrequency contactListFrequency) {
        super(contactListFrequency.getPrimaryKey());
        
        this.contactListFrequency = contactListFrequency;
    }

    private ContactListFrequencyDetail contactListFrequencyDetail; // Optional, use getContactListFrequencyDetail()
    
    private ContactListFrequencyDetail getContactListFrequencyDetail() {
        if(contactListFrequencyDetail == null) {
            contactListFrequencyDetail = contactListFrequency.getLastDetail();
        }
        
        return contactListFrequencyDetail;
    }
    
    @GraphQLField
    @GraphQLDescription("contact list frequency name")
    @GraphQLNonNull
    public String getContactListFrequencyName() {
        return getContactListFrequencyDetail().getContactListFrequencyName();
    }
    
    @GraphQLField
    @GraphQLDescription("is default")
    @GraphQLNonNull
    public boolean getIsDefault() {
        return getContactListFrequencyDetail().getIsDefault();
    }
    
    @GraphQLField
    @GraphQLDescription("sort order")
    @GraphQLNonNull
    public int getSortOrder() {
        return getContactListFrequencyDetail().getSortOrder();
    }
    
    @GraphQLField
    @GraphQLDescription("description")
    @GraphQLNonNull
    public String getDescription(final DataFetchingEnvironment env) {
        var contactListControl = Session.getModelController(ContactListControl.class);
        var userControl = Session.getModelController(UserControl.class);

        return contactListControl.getBestContactListFrequencyDescription(contactListFrequency, userControl.getPreferredLanguageFromUserVisit(BaseGraphQl.getUserVisit(env)));
    }

}
