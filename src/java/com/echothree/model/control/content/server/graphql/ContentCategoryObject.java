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
import com.echothree.model.control.offer.server.graphql.OfferSecurityUtils;
import com.echothree.model.control.offer.server.graphql.OfferUseObject;
import com.echothree.model.control.selector.server.graphql.SelectorObject;
import com.echothree.model.control.selector.server.graphql.SelectorSecurityUtils;
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.data.content.common.ContentCategoryItemConstants;
import com.echothree.model.data.content.server.entity.ContentCategory;
import com.echothree.model.data.content.server.entity.ContentCategoryDetail;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.annotations.connection.GraphQLConnection;
import graphql.schema.DataFetchingEnvironment;
import java.util.ArrayList;
import java.util.stream.Collectors;

@GraphQLDescription("content category object")
@GraphQLName("ContentCategory")
public class ContentCategoryObject
        extends BaseEntityInstanceObject {
    
    private final ContentCategory contentCategory; // Always Present
    
    public ContentCategoryObject(ContentCategory contentCategory) {
        super(contentCategory.getPrimaryKey());
        
        this.contentCategory = contentCategory;
    }

    private ContentCategoryDetail contentCategoryDetail; // Optional, use getContentCategoryDetail()
    
    private ContentCategoryDetail getContentCategoryDetail() {
        if(contentCategoryDetail == null) {
            contentCategoryDetail = contentCategory.getLastDetail();
        }
        
        return contentCategoryDetail;
    }

    @GraphQLField
    @GraphQLDescription("content catalog")
    public ContentCatalogObject getContentCatalog(final DataFetchingEnvironment env) {
        return ContentSecurityUtils.getHasContentCatalogAccess(env) ? new ContentCatalogObject(getContentCategoryDetail().getContentCatalog()) : null;
    }

    @GraphQLField
    @GraphQLDescription("content category name")
    @GraphQLNonNull
    public String getContentCategoryName() {
        return getContentCategoryDetail().getContentCategoryName();
    }

    @GraphQLField
    @GraphQLDescription("parent content category")
    public ContentCategoryObject getParentContentCategory(final DataFetchingEnvironment env) {
        var parentContentCategory = getContentCategoryDetail().getParentContentCategory();

        return parentContentCategory == null ? null :
                ContentSecurityUtils.getHasContentCategoryAccess(env) ?
                        new ContentCategoryObject(parentContentCategory) : null;
    }

    @GraphQLField
    @GraphQLDescription("default offer use")
    public OfferUseObject getDefaultOfferUse(final DataFetchingEnvironment env) {
        var defaultOfferUse = getContentCategoryDetail().getDefaultOfferUse();

        return defaultOfferUse == null ? null :
                OfferSecurityUtils.getHasOfferUseAccess(env) ?
                        new OfferUseObject(defaultOfferUse) : null;
    }

    @GraphQLField
    @GraphQLDescription("content category item selector")
    public SelectorObject getContentCategoryItemSelector(final DataFetchingEnvironment env) {
        var contentCategoryItemSelector = getContentCategoryDetail().getContentCategoryItemSelector();

        return contentCategoryItemSelector == null ? null :
                SelectorSecurityUtils.getHasSelectorAccess(env) ?
                        new SelectorObject(contentCategoryItemSelector) : null;
    }

    @GraphQLField
    @GraphQLDescription("is default")
    @GraphQLNonNull
    public boolean getIsDefault() {
        return getContentCategoryDetail().getIsDefault();
    }
    
    @GraphQLField
    @GraphQLDescription("sort order")
    @GraphQLNonNull
    public int getSortOrder() {
        return getContentCategoryDetail().getSortOrder();
    }
    
    @GraphQLField
    @GraphQLDescription("description")
    @GraphQLNonNull
    public String getDescription(final DataFetchingEnvironment env) {
        var contentControl = Session.getModelController(ContentControl.class);
        var userControl = Session.getModelController(UserControl.class);

        return contentControl.getBestContentCategoryDescription(contentCategory, userControl.getPreferredLanguageFromUserVisit(BaseGraphQl.getUserVisit(env)));
    }
    
    @GraphQLField
    @GraphQLDescription("content category items")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    public CountingPaginatedData<ContentCategoryItemObject> getContentCategoryItems(final DataFetchingEnvironment env) {
        if(ContentSecurityUtils.getHasContentCategoryItemsAccess(env)) {
            var contentControl = Session.getModelController(ContentControl.class);
            var totalCount = contentControl.countContentCategoryItemsByContentCategory(contentCategory);

            try(var objectLimiter = new ObjectLimiter(env, ContentCategoryItemConstants.COMPONENT_VENDOR_NAME, ContentCategoryItemConstants.ENTITY_TYPE_NAME, totalCount)) {
                var entities = contentControl.getContentCategoryItemsByContentCategory(contentCategory);
                var contentCategoryItems = entities.stream().map(ContentCategoryItemObject::new).collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                return new CountedObjects<>(objectLimiter, contentCategoryItems);
            }
        } else {
            return Connections.emptyConnection();
        }
    }
    
}
