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

import com.echothree.model.control.core.server.graphql.CoreSecurityUtils;
import com.echothree.model.control.core.server.graphql.MimeTypeObject;
import com.echothree.model.control.graphql.server.graphql.BaseEntityInstanceObject;
import com.echothree.model.control.party.server.graphql.LanguageObject;
import com.echothree.model.control.party.server.graphql.PartySecurityUtils;
import com.echothree.model.data.content.server.entity.ContentPageArea;
import com.echothree.model.data.content.server.entity.ContentPageAreaDetail;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;

@GraphQLDescription("content page area object")
@GraphQLName("ContentPageArea")
public class ContentPageAreaObject
        extends BaseEntityInstanceObject {
    
    private final ContentPageArea contentPageArea; // Always Present
    
    public ContentPageAreaObject(ContentPageArea contentPageArea) {
        super(contentPageArea.getPrimaryKey());
        
        this.contentPageArea = contentPageArea;
    }

    private ContentPageAreaDetail contentPageAreaDetail; // Optional, use getContentPageAreaDetail()
    
    private ContentPageAreaDetail getContentPageAreaDetail() {
        if(contentPageAreaDetail == null) {
            contentPageAreaDetail = contentPageArea.getLastDetail();
        }
        
        return contentPageAreaDetail;
    }
    
    @GraphQLField
    @GraphQLDescription("content page")
    @GraphQLNonNull
    public ContentPageObject getContentPage(final DataFetchingEnvironment env) {
        return ContentSecurityUtils.getInstance().getHasContentPageAccess(env) ? new ContentPageObject(getContentPageAreaDetail().getContentPage()) : null;
    }

    @GraphQLField
    @GraphQLDescription("content page layout area")
    @GraphQLNonNull
    public ContentPageLayoutAreaObject getContentPageLayoutArea(final DataFetchingEnvironment env) {
        return new ContentPageLayoutAreaObject(getContentPageAreaDetail().getContentPageLayoutArea());
    }

    @GraphQLField
    @GraphQLDescription("language")
    @GraphQLNonNull
    public LanguageObject getLanguage(final DataFetchingEnvironment env) {
        return PartySecurityUtils.getInstance().getHasLanguageAccess(env) ? new LanguageObject(getContentPageAreaDetail().getLanguage()) : null;
    }

    @GraphQLField
    @GraphQLDescription("mime type")
    @GraphQLNonNull
    public MimeTypeObject getMimeType(final DataFetchingEnvironment env) {
        return CoreSecurityUtils.getInstance().getHasMimeTypeAccess(env) ? new MimeTypeObject(getContentPageAreaDetail().getMimeType()) : null;
    }

}
