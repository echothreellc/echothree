// --------------------------------------------------------------------------------
// Copyright 2002-2021 Echo Three, LLC
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
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.data.content.server.entity.ContentCategory;
import com.echothree.model.data.content.server.entity.ContentCategoryDetail;
import com.echothree.model.data.content.server.entity.ContentCategoryItem;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.schema.DataFetchingEnvironment;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import java.util.ArrayList;
import java.util.List;

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
        return ContentSecurityUtils.getInstance().getHasContentCatalogAccess(env) ? new ContentCatalogObject(getContentCategoryDetail().getContentCatalog()) : null;
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
        return ContentSecurityUtils.getInstance().getHasContentCategoryAccess(env) ? new ContentCategoryObject(getContentCategoryDetail().getParentContentCategory()) : null;
    }

//    @GraphQLField
//    @GraphQLDescription("default offer use")
//    public OfferUseObject getDefaultOfferUse(final DataFetchingEnvironment env) {
//        return ContentSecurityUtils.getInstance().getHasOfferUseAccess(env) ? new OfferUseObject(getContentCategoryDetail().getDefaultOfferUse()) : null;
//    }

//    @GraphQLField
//    @GraphQLDescription("content category item selector")
//    public SelectorObject getContentCategoryItemSelector(final DataFetchingEnvironment env) {
//        return SelectorSecurityUtils.getInstance().getHasSelectorAccess(env) ? new SelectorObject(getContentCategoryDetail().getContentCategoryItemSelector()) : null;
//    }

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
        GraphQlContext context = env.getContext();
        
        return contentControl.getBestContentCategoryDescription(contentCategory, userControl.getPreferredLanguageFromUserVisit(context.getUserVisit()));
    }
    
    @GraphQLField
    @GraphQLDescription("content category items count")
    public Long getContentCategoryItemsCount(final DataFetchingEnvironment env) {
        var contentControl = Session.getModelController(ContentControl.class);
        
        return ContentSecurityUtils.getInstance().getHasContentCategoryItemsAccess(env) ? contentControl.countContentCategoryItemsByContentCategory(contentCategory) : null;
    }
    
    @GraphQLField
    @GraphQLDescription("content category items")
    public List<ContentCategoryItemObject> getContentCategoryItems(final DataFetchingEnvironment env) {
        var contentControl = Session.getModelController(ContentControl.class);
        List<ContentCategoryItem> entities = ContentSecurityUtils.getInstance().getHasContentCategoryItemsAccess(env) ? contentControl.getContentCategoryItemsByContentCategory(contentCategory) : null;
        List<ContentCategoryItemObject> contentCategoryItems = entities == null ? null : new ArrayList<>(entities.size());
                
        if(entities != null) {
            entities.forEach((entity) -> {
                contentCategoryItems.add(new ContentCategoryItemObject(entity));
            });
        }
        
        return contentCategoryItems;
    }
    
}
