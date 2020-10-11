// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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
import com.echothree.model.control.graphql.server.util.GraphQlContext;
import com.echothree.model.control.user.server.UserControl;
import com.echothree.model.data.content.server.entity.ContentPage;
import com.echothree.model.data.content.server.entity.ContentSection;
import com.echothree.model.data.content.server.entity.ContentSectionDetail;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.schema.DataFetchingEnvironment;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import java.util.ArrayList;
import java.util.List;

@GraphQLDescription("content section object")
@GraphQLName("ContentSection")
public class ContentSectionObject
        extends BaseEntityInstanceObject {
    
    private final ContentSection contentSection; // Always Present
    
    public ContentSectionObject(ContentSection contentSection) {
        super(contentSection.getPrimaryKey());
        
        this.contentSection = contentSection;
    }

    private ContentSectionDetail contentSectionDetail; // Optional, use getContentSectionDetail()
    
    private ContentSectionDetail getContentSectionDetail() {
        if(contentSectionDetail == null) {
            contentSectionDetail = contentSection.getLastDetail();
        }
        
        return contentSectionDetail;
    }

    @GraphQLField
    @GraphQLDescription("content collection")
    public ContentCollectionObject getContentCollection(final DataFetchingEnvironment env) {
        return ContentSecurityUtils.getInstance().getHasContentCollectionAccess(env) ? new ContentCollectionObject(getContentSectionDetail().getContentCollection()) : null;
    }

    @GraphQLField
    @GraphQLDescription("content section name")
    @GraphQLNonNull
    public String getContentSectionName() {
        return getContentSectionDetail().getContentSectionName();
    }

    @GraphQLField
    @GraphQLDescription("parent content section")
    public ContentSectionObject getParentContentSection(final DataFetchingEnvironment env) {
        return ContentSecurityUtils.getInstance().getHasContentSectionAccess(env) ? new ContentSectionObject(getContentSectionDetail().getParentContentSection()) : null;
    }

    @GraphQLField
    @GraphQLDescription("is default")
    @GraphQLNonNull
    public boolean getIsDefault() {
        return getContentSectionDetail().getIsDefault();
    }
    
    @GraphQLField
    @GraphQLDescription("sort order")
    @GraphQLNonNull
    public int getSortOrder() {
        return getContentSectionDetail().getSortOrder();
    }
    
    @GraphQLField
    @GraphQLDescription("description")
    @GraphQLNonNull
    public String getDescription(final DataFetchingEnvironment env) {
        var contentControl = (ContentControl)Session.getModelController(ContentControl.class);
        var userControl = (UserControl)Session.getModelController(UserControl.class);
        GraphQlContext context = env.getContext();
        
        return contentControl.getBestContentSectionDescription(contentSection, userControl.getPreferredLanguageFromUserVisit(context.getUserVisit()));
    }
    
    @GraphQLField
    @GraphQLDescription("content pages count")
    public Long getContentPagesCount(final DataFetchingEnvironment env) {
        var contentControl = (ContentControl)Session.getModelController(ContentControl.class);
        
        return ContentSecurityUtils.getInstance().getHasContentPagesAccess(env) ? contentControl.countContentPagesByContentSection(contentSection) : null;
    }
    
    @GraphQLField
    @GraphQLDescription("content pages")
    public List<ContentPageObject> getContentPages(final DataFetchingEnvironment env) {
        var contentControl = (ContentControl)Session.getModelController(ContentControl.class);
        List<ContentPage> entities = ContentSecurityUtils.getInstance().getHasContentPagesAccess(env) ? contentControl.getContentPagesByContentSection(contentSection) : null;
        List<ContentPageObject> contentPages = entities == null ? null : new ArrayList<>(entities.size());
        
        if(entities != null) {
            entities.forEach((entity) -> {
                contentPages.add(new ContentPageObject(entity));
            });
        }
        
        return contentPages;
    }
    
}
