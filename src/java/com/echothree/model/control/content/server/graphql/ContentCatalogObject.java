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

import com.echothree.model.control.content.server.ContentControl;
import com.echothree.model.control.graphql.server.graphql.BaseEntityInstanceObject;
import com.echothree.model.control.graphql.server.util.GraphQlContext;
import com.echothree.model.control.user.server.UserControl;
import com.echothree.model.data.content.server.entity.ContentCatalog;
import com.echothree.model.data.content.server.entity.ContentCatalogDetail;
import com.echothree.model.data.content.server.entity.ContentCatalogItem;
import com.echothree.model.data.content.server.entity.ContentCategory;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.schema.DataFetchingEnvironment;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import java.util.ArrayList;
import java.util.List;

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
        return ContentSecurityUtils.getInstance().getHasContentCollectionAccess(env) ? new ContentCollectionObject(getContentCatalogDetail().getContentCollection()) : null;
    }

    @GraphQLField
    @GraphQLDescription("content catalog name")
    @GraphQLNonNull
    public String getContentCatalogName() {
        return getContentCatalogDetail().getContentCatalogName();
    }

//    @GraphQLField
//    @GraphQLDescription("default offer use")
//    public OfferUseObject getDefaultOfferUse(final DataFetchingEnvironment env) {
//        return ContentSecurityUtils.getInstance().getHasOfferUseAccess(env) ? new OfferUseObject(getContentCollectionDetail().getDefaultOfferUse()) : null;
//    }

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
        var contentControl = (ContentControl)Session.getModelController(ContentControl.class);
        var userControl = (UserControl)Session.getModelController(UserControl.class);
        GraphQlContext context = env.getContext();
        
        return contentControl.getBestContentCatalogDescription(contentCatalog, userControl.getPreferredLanguageFromUserVisit(context.getUserVisit()));
    }
    
    @GraphQLField
    @GraphQLDescription("content categories count")
    public Long getContentCategoriesCount(final DataFetchingEnvironment env) {
        var contentControl = (ContentControl)Session.getModelController(ContentControl.class);
        
        return ContentSecurityUtils.getInstance().getHasContentCategoriesAccess(env) ? contentControl.countContentCategoriesByContentCatalog(contentCatalog) : null;
    }
    
    @GraphQLField
    @GraphQLDescription("content categories")
    public List<ContentCategoryObject> getContentCategories(final DataFetchingEnvironment env) {
        var contentControl = (ContentControl)Session.getModelController(ContentControl.class);
        List<ContentCategory> entities = ContentSecurityUtils.getInstance().getHasContentCategoriesAccess(env) ? contentControl.getContentCategories(contentCatalog) : null;
        List<ContentCategoryObject> contentCategories = entities == null ? null : new ArrayList<>(entities.size());
        
        if(entities != null) {
            entities.forEach((entity) -> {
                contentCategories.add(new ContentCategoryObject(entity));
            });
        }
        
        return contentCategories;
    }
    
    @GraphQLField
    @GraphQLDescription("content catalog items count")
    public Long getContentCatalogItemsCount(final DataFetchingEnvironment env) {
        var contentControl = (ContentControl)Session.getModelController(ContentControl.class);
        
        return ContentSecurityUtils.getInstance().getHasContentCatalogItemsAccess(env) ? contentControl.countContentCatalogItemsByContentCatalog(contentCatalog) : null;
    }
    
    @GraphQLField
    @GraphQLDescription("content catalog items")
    public List<ContentCatalogItemObject> getContentCatalogItems(final DataFetchingEnvironment env) {
        var contentControl = (ContentControl)Session.getModelController(ContentControl.class);
        List<ContentCatalogItem> entities = ContentSecurityUtils.getInstance().getHasContentCatalogItemsAccess(env) ? contentControl.getContentCatalogItemsByContentCatalog(contentCatalog) : null;
        List<ContentCatalogItemObject> contentCatalogItems = entities == null ? null : new ArrayList<>(entities.size());
        
        if(entities != null) {
            entities.forEach((entity) -> {
                contentCatalogItems.add(new ContentCatalogItemObject(entity));
            });
        }
        
        return contentCatalogItems;
    }
    
}
