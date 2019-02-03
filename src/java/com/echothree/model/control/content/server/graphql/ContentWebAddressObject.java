// --------------------------------------------------------------------------------
// Copyright 2002-2019 Echo Three, LLC
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

package com.echothree.model.control.content.server.graphql;

import com.echothree.control.user.content.server.command.GetContentCollectionCommand;
import com.echothree.model.control.content.server.ContentControl;
import com.echothree.model.control.graphql.server.graphql.BaseEntityInstanceObject;
import com.echothree.model.control.graphql.server.util.GraphQlContext;
import com.echothree.model.control.graphql.server.util.GraphQlSecurityUtils;
import com.echothree.model.control.user.server.UserControl;
import com.echothree.model.data.content.server.entity.ContentWebAddress;
import com.echothree.model.data.content.server.entity.ContentWebAddressDetail;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.schema.DataFetchingEnvironment;
import graphql.annotations.annotationTypes.GraphQLNonNull;

@GraphQLDescription("content web address object")
@GraphQLName("ContentWebAddress")
public class ContentWebAddressObject
        extends BaseEntityInstanceObject {
    
    private final ContentWebAddress contentWebAddress; // Always Present
    
    public ContentWebAddressObject(ContentWebAddress contentWebAddress) {
        super(contentWebAddress.getPrimaryKey());
        
        this.contentWebAddress = contentWebAddress;
    }

    private ContentWebAddressDetail contentWebAddressDetail; // Optional, use getContentWebAddressDetail()
    
    private ContentWebAddressDetail getContentWebAddressDetail() {
        if(contentWebAddressDetail == null) {
            contentWebAddressDetail = contentWebAddress.getLastDetail();
        }
        
        return contentWebAddressDetail;
    }
    
    private Boolean hasContentCollectionAccess;
    
    private boolean getHasContentCollectionAccess(final DataFetchingEnvironment env) {
        if(hasContentCollectionAccess == null) {
            hasContentCollectionAccess = GraphQlSecurityUtils.getInstance().hasAccess(env, GetContentCollectionCommand.class);
        }
        
        return hasContentCollectionAccess;
    }
        
    @GraphQLField
    @GraphQLDescription("content collection name")
    @GraphQLNonNull
    public String getContentWebAddressName() {
        return getContentWebAddressDetail().getContentWebAddressName();
    }

    @GraphQLField
    @GraphQLDescription("content collection")
    public ContentCollectionObject getContentCollection(final DataFetchingEnvironment env) {
        return getHasContentCollectionAccess(env) ? new ContentCollectionObject(getContentWebAddressDetail().getContentCollection()) : null;
    }

    @GraphQLField
    @GraphQLDescription("description")
    @GraphQLNonNull
    public String getDescription(final DataFetchingEnvironment env) {
        ContentControl contentControl = (ContentControl)Session.getModelController(ContentControl.class);
        UserControl userControl = (UserControl)Session.getModelController(UserControl.class);
        GraphQlContext context = env.getContext();
        
        return contentControl.getBestContentWebAddressDescription(contentWebAddress, userControl.getPreferredLanguageFromUserVisit(context.getUserVisit()));
    }
    
}
