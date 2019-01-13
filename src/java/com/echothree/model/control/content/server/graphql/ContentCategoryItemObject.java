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

import com.echothree.control.user.content.server.command.GetContentCatalogItemCommand;
import com.echothree.control.user.content.server.command.GetContentCategoryCommand;
import com.echothree.model.control.graphql.server.graphql.BaseEntityInstanceObject;
import com.echothree.model.control.graphql.server.util.GraphQlContext;
import com.echothree.model.data.content.server.entity.ContentCategoryItem;
import com.echothree.util.server.control.BaseSingleEntityCommand;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;

@GraphQLDescription("content category item object")
@GraphQLName("ContentCategoryItem")
public class ContentCategoryItemObject
        extends BaseEntityInstanceObject {
    
    private final ContentCategoryItem contentCategoryItem; // Always Present
    
    public ContentCategoryItemObject(ContentCategoryItem contentCategoryItem) {
        super(contentCategoryItem.getPrimaryKey());
        
        this.contentCategoryItem = contentCategoryItem;
    }

    private Boolean hasContentCategoryAccess;
    
    private boolean getHasContentCategoryAccess(final DataFetchingEnvironment env) {
        if(hasContentCategoryAccess == null) {
            GraphQlContext context = env.getContext();
            BaseSingleEntityCommand baseSingleEntityCommand = new GetContentCategoryCommand(context.getUserVisitPK(), null);
            
            baseSingleEntityCommand.security();
            
            hasContentCategoryAccess = !baseSingleEntityCommand.hasSecurityMessages();
        }
        
        return hasContentCategoryAccess;
    }
    
    private Boolean hasContentCatalogItemAccess;
    
    private boolean getHasContentCatalogItemAccess(final DataFetchingEnvironment env) {
        if(hasContentCatalogItemAccess == null) {
            GraphQlContext context = env.getContext();
            BaseSingleEntityCommand baseSingleEntityCommand = new GetContentCatalogItemCommand(context.getUserVisitPK(), null);
            
            baseSingleEntityCommand.security();
            
            hasContentCatalogItemAccess = !baseSingleEntityCommand.hasSecurityMessages();
        }
        
        return hasContentCatalogItemAccess;
    }
    
    @GraphQLField
    @GraphQLDescription("content category")
    public ContentCategoryObject getContentCategory(final DataFetchingEnvironment env) {
        return getHasContentCategoryAccess(env) ? new ContentCategoryObject(contentCategoryItem.getContentCategory()) : null;
    }

    @GraphQLField
    @GraphQLDescription("content catalog item")
    public ContentCatalogItemObject getContentCatalogItem(final DataFetchingEnvironment env) {
        return getHasContentCatalogItemAccess(env) ? new ContentCatalogItemObject(contentCategoryItem.getContentCatalogItem()) : null;
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
