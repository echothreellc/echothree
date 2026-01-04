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
import com.echothree.model.control.graphql.server.graphql.count.Connections;
import com.echothree.model.control.graphql.server.graphql.count.CountedObjects;
import com.echothree.model.control.graphql.server.graphql.count.CountingDataConnectionFetcher;
import com.echothree.model.control.graphql.server.graphql.count.CountingPaginatedData;
import com.echothree.model.control.graphql.server.util.BaseGraphQl;
import com.echothree.model.control.graphql.server.util.count.ObjectLimiter;
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.data.content.common.ContentPageConstants;
import com.echothree.model.data.content.common.ContentSectionConstants;
import com.echothree.model.data.content.server.entity.ContentSection;
import com.echothree.model.data.content.server.entity.ContentSectionDetail;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.annotations.connection.GraphQLConnection;
import graphql.schema.DataFetchingEnvironment;
import java.util.ArrayList;
import java.util.stream.Collectors;

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
        return ContentSecurityUtils.getHasContentCollectionAccess(env) ? new ContentCollectionObject(getContentSectionDetail().getContentCollection()) : null;
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
        var parentContentSection = getContentSectionDetail().getParentContentSection();

        return parentContentSection == null ? null :
                ContentSecurityUtils.getHasContentSectionAccess(env) ?
                        new ContentSectionObject(parentContentSection) : null;
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
        var contentControl = Session.getModelController(ContentControl.class);
        var userControl = Session.getModelController(UserControl.class);

        return contentControl.getBestContentSectionDescription(contentSection, userControl.getPreferredLanguageFromUserVisit(BaseGraphQl.getUserVisit(env)));
    }

    @GraphQLField
    @GraphQLDescription("contentPages")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    public CountingPaginatedData<ContentPageObject> getContentPages(final DataFetchingEnvironment env) {
        if(ContentSecurityUtils.getHasContentPagesAccess(env)) {
            var contentControl = Session.getModelController(ContentControl.class);
            var totalCount = contentControl.countContentPagesByContentSection(contentSection);

            try(var objectLimiter = new ObjectLimiter(env, ContentPageConstants.COMPONENT_VENDOR_NAME, ContentPageConstants.ENTITY_TYPE_NAME, totalCount)) {
                var entities = contentControl.getContentPagesByContentSection(contentSection);
                var contentPages = entities.stream().map(ContentPageObject::new).collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                return new CountedObjects<>(objectLimiter, contentPages);
            }
        } else {
            return Connections.emptyConnection();
        }
    }

}
