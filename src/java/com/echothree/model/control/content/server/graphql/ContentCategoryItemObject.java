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

import com.echothree.model.control.graphql.server.graphql.BaseObject;
import com.echothree.model.data.content.server.entity.ContentCategoryItem;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;

@GraphQLDescription("content category item object")
@GraphQLName("ContentCategoryItem")
public class ContentCategoryItemObject
        extends BaseObject {
    
    private final ContentCategoryItem contentCategoryItem; // Always Present
    
    public ContentCategoryItemObject(ContentCategoryItem contentCategoryItem) {
        this.contentCategoryItem = contentCategoryItem;
    }

    @GraphQLField
    @GraphQLDescription("content category")
    public ContentCategoryObject getContentCategory(final DataFetchingEnvironment env) {
        return ContentSecurityUtils.getHasContentCategoryAccess(env) ? new ContentCategoryObject(contentCategoryItem.getContentCategory()) : null;
    }

    @GraphQLField
    @GraphQLDescription("content catalog item")
    public ContentCatalogItemObject getContentCatalogItem(final DataFetchingEnvironment env) {
        return ContentSecurityUtils.getHasContentCatalogItemAccess(env) ? new ContentCatalogItemObject(contentCategoryItem.getContentCatalogItem()) : null;
    }

    @GraphQLField
    @GraphQLDescription("is default")
    @GraphQLNonNull
    public boolean getIsDefault() {
        return contentCategoryItem.getIsDefault();
    }
    
    @GraphQLField
    @GraphQLDescription("sort order")
    @GraphQLNonNull
    public int getSortOrder() {
        return contentCategoryItem.getSortOrder();
    }
    
}
