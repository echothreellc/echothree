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

package com.echothree.model.control.content.server.graphql;

import com.echothree.model.control.content.server.control.ContentControl;
import com.echothree.model.control.graphql.server.graphql.BaseEntityInstanceObject;
import com.echothree.model.control.graphql.server.util.BaseGraphQl;
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.data.content.server.entity.ContentWebAddress;
import com.echothree.model.data.content.server.entity.ContentWebAddressDetail;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;

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
    
    @GraphQLField
    @GraphQLDescription("content collection name")
    @GraphQLNonNull
    public String getContentWebAddressName() {
        return getContentWebAddressDetail().getContentWebAddressName();
    }

    @GraphQLField
    @GraphQLDescription("content collection")
    public ContentCollectionObject getContentCollection(final DataFetchingEnvironment env) {
        return ContentSecurityUtils.getHasContentCollectionAccess(env) ?
                new ContentCollectionObject(getContentWebAddressDetail().getContentCollection()) :
                null;
    }

    @GraphQLField
    @GraphQLDescription("description")
    @GraphQLNonNull
    public String getDescription(final DataFetchingEnvironment env) {
        var contentControl = Session.getModelController(ContentControl.class);
        var userControl = Session.getModelController(UserControl.class);

        return contentControl.getBestContentWebAddressDescription(contentWebAddress, userControl.getPreferredLanguageFromUserVisit(BaseGraphQl.getUserVisit(env)));
    }
    
}
