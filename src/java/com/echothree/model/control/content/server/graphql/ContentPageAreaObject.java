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

import com.echothree.model.control.content.common.ContentPageAreaTypes;
import com.echothree.model.control.content.server.control.ContentControl;
import com.echothree.model.control.core.server.graphql.CoreSecurityUtils;
import com.echothree.model.control.core.server.graphql.MimeTypeObject;
import com.echothree.model.control.graphql.server.graphql.BaseEntityInstanceObject;
import com.echothree.model.control.party.server.graphql.LanguageObject;
import com.echothree.model.control.party.server.graphql.PartySecurityUtils;
import com.echothree.model.data.content.server.entity.ContentPageArea;
import com.echothree.model.data.content.server.entity.ContentPageAreaClob;
import com.echothree.model.data.content.server.entity.ContentPageAreaDetail;
import com.echothree.model.data.content.server.entity.ContentPageAreaString;
import com.echothree.model.data.content.server.entity.ContentPageAreaUrl;
import com.echothree.util.server.persistence.Session;
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
    
    String contentPageAreaTypeName;

    private String getContentPageAreaTypeName() {
        if(contentPageAreaTypeName == null) {
            contentPageAreaTypeName = getContentPageAreaDetail().getContentPageLayoutArea().getContentPageAreaType().getContentPageAreaTypeName();
        }

        return contentPageAreaTypeName;
    }
    
    ContentPageAreaClob contentPageAreaClob;

    private ContentPageAreaClob getContentPageAreaClob() {
        if(contentPageAreaClob == null) {
            var contentControl = Session.getModelController(ContentControl.class);

            contentPageAreaClob = contentControl.getContentPageAreaClob(getContentPageAreaDetail());
        }

        return contentPageAreaClob;
    }

    ContentPageAreaString contentPageAreaString;

    private ContentPageAreaString getContentPageAreaString() {
        if(contentPageAreaString == null) {
            var contentControl = Session.getModelController(ContentControl.class);

            contentPageAreaString = contentControl.getContentPageAreaString(getContentPageAreaDetail());
        }

        return contentPageAreaString;
    }

    ContentPageAreaUrl contentPageAreaUrl;

    private ContentPageAreaUrl getContentPageAreaUrl() {
        if(contentPageAreaUrl == null) {
            var contentControl = Session.getModelController(ContentControl.class);

            contentPageAreaUrl = contentControl.getContentPageAreaUrl(getContentPageAreaDetail());
        }

        return contentPageAreaUrl;
    }

    @GraphQLField
    @GraphQLDescription("content page")
    @GraphQLNonNull
    public ContentPageObject getContentPage(final DataFetchingEnvironment env) {
        return ContentSecurityUtils.getHasContentPageAccess(env) ? new ContentPageObject(getContentPageAreaDetail().getContentPage()) : null;
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
        return PartySecurityUtils.getHasLanguageAccess(env) ? new LanguageObject(getContentPageAreaDetail().getLanguage()) : null;
    }

    @GraphQLField
    @GraphQLDescription("mime type")
    @GraphQLNonNull
    public MimeTypeObject getMimeType(final DataFetchingEnvironment env) {
        return CoreSecurityUtils.getHasMimeTypeAccess(env) ? new MimeTypeObject(getContentPageAreaDetail().getMimeType()) : null;
    }

    @GraphQLField
    @GraphQLDescription("clob")
    public String getClob(final DataFetchingEnvironment env) {
        var contentPageAreaClob = getContentPageAreaClob();
        
        return contentPageAreaClob == null ? null : contentPageAreaClob.getClob();
    }

    @GraphQLField
    @GraphQLDescription("string")
    public String getString(final DataFetchingEnvironment env) {
        var contentPageAreaString = getContentPageAreaString();
        
        return contentPageAreaString == null ? null : contentPageAreaString.getString();
    }

    @GraphQLField
    @GraphQLDescription("url")
    public String getUrl(final DataFetchingEnvironment env) {
        var contentPageAreaUrl = getContentPageAreaUrl();
        
        return contentPageAreaUrl == null ? null : contentPageAreaUrl.getUrl();
    }

}
