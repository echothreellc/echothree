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

import com.echothree.control.user.content.server.command.GetContentCategoriesCommand;
import com.echothree.control.user.content.server.command.GetContentCollectionCommand;
import com.echothree.control.user.offer.server.command.GetOfferUseCommand;
import com.echothree.model.control.content.server.ContentControl;
import com.echothree.model.control.graphql.server.graphql.BaseEntityInstanceObject;
import com.echothree.model.control.graphql.server.util.GraphQlContext;
import com.echothree.model.control.user.server.UserControl;
import com.echothree.model.data.content.server.entity.ContentCatalog;
import com.echothree.model.data.content.server.entity.ContentCatalogDetail;
import com.echothree.model.data.content.server.entity.ContentCategory;
import com.echothree.util.server.control.BaseMultipleEntitiesCommand;
import com.echothree.util.server.control.BaseSingleEntityCommand;
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

    private Boolean hasContentCollectionAccess;
    
    private boolean getHasContentCollectionAccess(final DataFetchingEnvironment env) {
        if(hasContentCollectionAccess == null) {
            GraphQlContext context = env.getContext();
            BaseSingleEntityCommand baseSingleEntityCommand = new GetContentCollectionCommand(context.getUserVisitPK(), null);
            
            baseSingleEntityCommand.security();
            
            hasContentCollectionAccess = !baseSingleEntityCommand.hasSecurityMessages();
        }
        
        return hasContentCollectionAccess;
    }
    
    private Boolean hasOfferUseAccess;
    
    private boolean getHasOfferUseAccess(final DataFetchingEnvironment env) {
        if(hasOfferUseAccess == null) {
            GraphQlContext context = env.getContext();
            BaseSingleEntityCommand baseSingleEntityCommand = new GetOfferUseCommand(context.getUserVisitPK(), null);
            
            baseSingleEntityCommand.security();
            
            hasOfferUseAccess = !baseSingleEntityCommand.hasSecurityMessages();
        }
        
        return hasOfferUseAccess;
    }
    
    private Boolean hasContentCategoriesAccess;
    
    private boolean getHasContentCategoriesAccess(final DataFetchingEnvironment env) {
        if(hasContentCategoriesAccess == null) {
            GraphQlContext context = env.getContext();
            BaseMultipleEntitiesCommand baseMultipleEntitiesCommand = new GetContentCategoriesCommand(context.getUserVisitPK(), null);
            
            baseMultipleEntitiesCommand.security();
            
            hasContentCategoriesAccess = !baseMultipleEntitiesCommand.hasSecurityMessages();
        }
        
        return hasContentCategoriesAccess;
    }
        
    @GraphQLField
    @GraphQLDescription("content collection")
    public ContentCollectionObject getContentCollection(final DataFetchingEnvironment env) {
        return getHasContentCollectionAccess(env) ? new ContentCollectionObject(getContentCatalogDetail().getContentCollection()) : null;
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
//        return getHasOfferUseAccess(env) ? new OfferUseObject(getContentCollectionDetail().getDefaultOfferUse()) : null;
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
        ContentControl contentControl = (ContentControl)Session.getModelController(ContentControl.class);
        UserControl userControl = (UserControl)Session.getModelController(UserControl.class);
        GraphQlContext context = env.getContext();
        
        return contentControl.getBestContentCatalogDescription(contentCatalog, userControl.getPreferredLanguageFromUserVisit(context.getUserVisit()));
    }
    
    @GraphQLField
    @GraphQLDescription("content categories")
    public List<ContentCategoryObject> getContentCategories(final DataFetchingEnvironment env) {
        ContentControl contentControl = (ContentControl)Session.getModelController(ContentControl.class);
        List<ContentCategory> entities = getHasContentCategoriesAccess(env) ? contentControl.getContentCategories(contentCatalog) : null;
        List<ContentCategoryObject> contentCategories = new ArrayList<>(entities.size());
        
        entities.forEach((entity) -> {
            contentCategories.add(new ContentCategoryObject(entity));
        });
        
        return contentCategories;
    }
    
}
