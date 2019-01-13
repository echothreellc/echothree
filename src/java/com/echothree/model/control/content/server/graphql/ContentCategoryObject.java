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

import com.echothree.control.user.content.server.command.GetContentCatalogCommand;
import com.echothree.control.user.content.server.command.GetContentCategoryCommand;
import com.echothree.control.user.offer.server.command.GetOfferUseCommand;
import com.echothree.model.control.content.server.ContentControl;
import com.echothree.model.control.graphql.server.graphql.BaseEntityInstanceObject;
import com.echothree.model.control.graphql.server.util.GraphQlContext;
import com.echothree.model.control.user.server.UserControl;
import com.echothree.model.data.content.server.entity.ContentCategory;
import com.echothree.model.data.content.server.entity.ContentCategoryDetail;
import com.echothree.util.server.control.BaseSingleEntityCommand;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.schema.DataFetchingEnvironment;
import graphql.annotations.annotationTypes.GraphQLNonNull;

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

    private Boolean hasContentCatalogAccess;
    
    private boolean getHasContentCatalogAccess(final DataFetchingEnvironment env) {
        if(hasContentCatalogAccess == null) {
            GraphQlContext context = env.getContext();
            BaseSingleEntityCommand baseSingleEntityCommand = new GetContentCatalogCommand(context.getUserVisitPK(), null);
            
            baseSingleEntityCommand.security();
            
            hasContentCatalogAccess = !baseSingleEntityCommand.hasSecurityMessages();
        }
        
        return hasContentCatalogAccess;
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
    
//    private Boolean hasSelectorAccess;
//    
//    private boolean getHasSelectorAccess(final DataFetchingEnvironment env) {
//        if(hasSelectorAccess == null) {
//            GraphQlContext context = env.getContext();
//            BaseSingleEntityCommand baseSingleEntityCommand = new GetSelectorCommand(context.getUserVisitPK(), null);
//            
//            baseSingleEntityCommand.security();
//            
//            hasSelectorAccess = !baseSingleEntityCommand.hasSecurityMessages();
//        }
//        
//        return hasSelectorAccess;
//    }
    
    @GraphQLField
    @GraphQLDescription("content catalog")
    public ContentCatalogObject getContentCatalog(final DataFetchingEnvironment env) {
        return getHasContentCatalogAccess(env) ? new ContentCatalogObject(getContentCategoryDetail().getContentCatalog()) : null;
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
        return getHasContentCategoryAccess(env) ? new ContentCategoryObject(getContentCategoryDetail().getParentContentCategory()) : null;
    }

//    @GraphQLField
//    @GraphQLDescription("default offer use")
//    public OfferUseObject getDefaultOfferUse(final DataFetchingEnvironment env) {
//        return getHasOfferUseAccess(env) ? new OfferUseObject(getContentCategoryDetail().getDefaultOfferUse()) : null;
//    }

//    @GraphQLField
//    @GraphQLDescription("content category item selector")
//    public SelectorObject getContentCategoryItemSelector(final DataFetchingEnvironment env) {
//        return getHasSelectorAccess(env) ? new SelectorObject(getContentCategoryDetail().getContentCategoryItemSelector()) : null;
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
        ContentControl contentControl = (ContentControl)Session.getModelController(ContentControl.class);
        UserControl userControl = (UserControl)Session.getModelController(UserControl.class);
        GraphQlContext context = env.getContext();
        
        return contentControl.getBestContentCategoryDescription(contentCategory, userControl.getPreferredLanguageFromUserVisit(context.getUserVisit()));
    }
    
}
