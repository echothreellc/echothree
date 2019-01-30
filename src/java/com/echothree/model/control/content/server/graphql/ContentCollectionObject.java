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

import com.echothree.control.user.content.server.command.GetContentCatalogsCommand;
import com.echothree.control.user.content.server.command.GetContentSectionsCommand;
import com.echothree.control.user.offer.server.command.GetOfferUseCommand;
import com.echothree.model.control.content.server.ContentControl;
import com.echothree.model.control.graphql.server.graphql.BaseEntityInstanceObject;
import com.echothree.model.control.graphql.server.util.GraphQlContext;
import com.echothree.model.control.user.server.UserControl;
import com.echothree.model.data.content.server.entity.ContentCatalog;
import com.echothree.model.data.content.server.entity.ContentCollection;
import com.echothree.model.data.content.server.entity.ContentCollectionDetail;
import com.echothree.model.data.content.server.entity.ContentSection;
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

@GraphQLDescription("content collection object")
@GraphQLName("ContentCollection")
public class ContentCollectionObject
        extends BaseEntityInstanceObject {
    
    private final ContentCollection contentCollection; // Always Present
    
    public ContentCollectionObject(ContentCollection contentCollection) {
        super(contentCollection.getPrimaryKey());
        
        this.contentCollection = contentCollection;
    }

    private ContentCollectionDetail contentCollectionDetail; // Optional, use getContentCollectionDetail()
    
    private ContentCollectionDetail getContentCollectionDetail() {
        if(contentCollectionDetail == null) {
            contentCollectionDetail = contentCollection.getLastDetail();
        }
        
        return contentCollectionDetail;
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
        
    private Boolean hasContentCatalogsAccess;
    
    private boolean getHasContentCatalogsAccess(final DataFetchingEnvironment env) {
        if(hasContentCatalogsAccess == null) {
            GraphQlContext context = env.getContext();
            BaseMultipleEntitiesCommand baseMultipleEntitiesCommand = new GetContentCatalogsCommand(context.getUserVisitPK(), null);
            
            baseMultipleEntitiesCommand.security();
            
            hasContentCatalogsAccess = !baseMultipleEntitiesCommand.hasSecurityMessages();
        }
        
        return hasContentCatalogsAccess;
    }
        
    private Boolean hasContentSectionsAccess;
    
    private boolean getHasContentSectionsAccess(final DataFetchingEnvironment env) {
        if(hasContentSectionsAccess == null) {
            GraphQlContext context = env.getContext();
            BaseMultipleEntitiesCommand baseMultipleEntitiesCommand = new GetContentSectionsCommand(context.getUserVisitPK(), null);
            
            baseMultipleEntitiesCommand.security();
            
            hasContentSectionsAccess = !baseMultipleEntitiesCommand.hasSecurityMessages();
        }
        
        return hasContentSectionsAccess;
    }
        
    @GraphQLField
    @GraphQLDescription("content collection name")
    @GraphQLNonNull
    public String getContentCollectionName() {
        return getContentCollectionDetail().getContentCollectionName();
    }

//    @GraphQLField
//    @GraphQLDescription("default offer use")
//    public OfferUseObject getDefaultOfferUse(final DataFetchingEnvironment env) {
//        return getHasOfferUseAccess(env) ? new OfferUseObject(getContentCollectionDetail().getDefaultOfferUse()) : null;
//    }

    @GraphQLField
    @GraphQLDescription("description")
    @GraphQLNonNull
    public String getDescription(final DataFetchingEnvironment env) {
        ContentControl contentControl = (ContentControl)Session.getModelController(ContentControl.class);
        UserControl userControl = (UserControl)Session.getModelController(UserControl.class);
        GraphQlContext context = env.getContext();
        
        return contentControl.getBestContentCollectionDescription(contentCollection, userControl.getPreferredLanguageFromUserVisit(context.getUserVisit()));
    }
    
    @GraphQLField
    @GraphQLDescription("content catalogs")
    public List<ContentCatalogObject> getContentCatalogs(final DataFetchingEnvironment env) {
        ContentControl contentControl = (ContentControl)Session.getModelController(ContentControl.class);
        List<ContentCatalog> entities = getHasContentCatalogsAccess(env) ? contentControl.getContentCatalogs(contentCollection) : null;
        List<ContentCatalogObject> contentCatalogs = new ArrayList<>(entities.size());
        
        entities.forEach((entity) -> {
            contentCatalogs.add(new ContentCatalogObject(entity));
        });
        
        return contentCatalogs;
    }
    
    @GraphQLField
    @GraphQLDescription("content sections")
    public List<ContentSectionObject> getContentSections(final DataFetchingEnvironment env) {
        ContentControl contentControl = (ContentControl)Session.getModelController(ContentControl.class);
        List<ContentSection> entities = getHasContentSectionsAccess(env) ? contentControl.getContentSections(contentCollection) : null;
        List<ContentSectionObject> contentSections = new ArrayList<>(entities.size());
        
        entities.forEach((entity) -> {
            contentSections.add(new ContentSectionObject(entity));
        });
        
        return contentSections;
    }
    
}
