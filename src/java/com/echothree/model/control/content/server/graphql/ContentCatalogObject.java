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
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.data.content.common.ContentCatalogItemConstants;
import com.echothree.model.data.content.common.ContentCategoryConstants;
import com.echothree.model.data.content.server.entity.ContentCatalog;
import com.echothree.model.data.content.server.entity.ContentCatalogDetail;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.annotations.connection.GraphQLConnection;
import graphql.schema.DataFetchingEnvironment;
import java.util.ArrayList;
import java.util.stream.Collectors;

@GraphQLDescription("content catalog object")
@GraphQLName("ContentCatalog")
public class ContentCatalogObject
        extends BaseEntityInstanceObject {
    
    private final ContentCatalog contentCatalog; // Always Present
    
    public ContentCatalogObject(ContentCatalog contentCatalog) {
        super(contentCatalog.getPrimaryKey());
        
        this.contentCatalog = contentCatalog;
    }

    private ContentCatalogDetail contentCatalogDetail; // Optional, use getContentCatalogDetail()
    
    private ContentCatalogDetail getContentCatalogDetail() {
        if(contentCatalogDetail == null) {
            contentCatalogDetail = contentCatalog.getLastDetail();
        }
        
        return contentCatalogDetail;
    }

    @GraphQLField
    @GraphQLDescription("content collection")
    public ContentCollectionObject getContentCollection(final DataFetchingEnvironment env) {
        return ContentSecurityUtils.getHasContentCollectionAccess(env) ? new ContentCollectionObject(getContentCatalogDetail().getContentCollection()) : null;
    }

    @GraphQLField
    @GraphQLDescription("content catalog name")
    @GraphQLNonNull
    public String getContentCatalogName() {
        return getContentCatalogDetail().getContentCatalogName();
    }

    @GraphQLField
    @GraphQLDescription("default offer use")
    @GraphQLNonNull
    public OfferUseObject getDefaultOfferUse(final DataFetchingEnvironment env) {
        return OfferSecurityUtils.getHasOfferUseAccess(env) ?
                new OfferUseObject(getContentCatalogDetail().getDefaultOfferUse()) : null;
    }

    @GraphQLField
    @GraphQLDescription("is default")
    @GraphQLNonNull
    public boolean getIsDefault() {
        return getContentCatalogDetail().getIsDefault();
    }
    
    @GraphQLField
    @GraphQLDescription("sort order")
    @GraphQLNonNull
    public int getSortOrder() {
        return getContentCatalogDetail().getSortOrder();
    }
    
    @GraphQLField
    @GraphQLDescription("description")
    @GraphQLNonNull
    public String getDescription(final DataFetchingEnvironment env) {
        var contentControl = Session.getModelController(ContentControl.class);
        var userControl = Session.getModelController(UserControl.class);

        return contentControl.getBestContentCatalogDescription(contentCatalog, userControl.getPreferredLanguageFromUserVisit(BaseGraphQl.getUserVisit(env)));
    }
    
    @GraphQLField
    @GraphQLDescription("content categories")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    public CountingPaginatedData<ContentCategoryObject> getContentCategories(final DataFetchingEnvironment env) {
        if(ContentSecurityUtils.getHasContentCategoriesAccess(env)) {
            var contentControl = Session.getModelController(ContentControl.class);
            var totalCount = contentControl.countContentCategoriesByContentCatalog(contentCatalog);

            try(var objectLimiter = new ObjectLimiter(env, ContentCategoryConstants.COMPONENT_VENDOR_NAME, ContentCategoryConstants.ENTITY_TYPE_NAME, totalCount)) {
                var entities = contentControl.getContentCategories(contentCatalog);
                var contentCategories = entities.stream().map(ContentCategoryObject::new).collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                return new CountedObjects<>(objectLimiter, contentCategories);
            }
        } else {
            return Connections.emptyConnection();
        }
    }

    @GraphQLField
    @GraphQLDescription("content catalog items")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    public CountingPaginatedData<ContentCatalogItemObject> getContentCatalogItems(final DataFetchingEnvironment env) {
        if(ContentSecurityUtils.getHasContentCatalogItemsAccess(env)) {
            var contentControl = Session.getModelController(ContentControl.class);
            var totalCount = contentControl.countContentCatalogItemsByContentCatalog(contentCatalog);

            try(var objectLimiter = new ObjectLimiter(env, ContentCatalogItemConstants.COMPONENT_VENDOR_NAME, ContentCatalogItemConstants.ENTITY_TYPE_NAME, totalCount)) {
                var entities = contentControl.getContentCatalogItemsByContentCatalog(contentCatalog);
                var contentCatalogItems = entities.stream().map(ContentCatalogItemObject::new).collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                return new CountedObjects<>(objectLimiter, contentCatalogItems);
            }
        } else {
            return Connections.emptyConnection();
        }
    }

}
