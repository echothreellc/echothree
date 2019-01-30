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

import com.echothree.control.user.content.server.command.GetContentCollectionCommand;
import com.echothree.control.user.content.server.command.GetContentPagesCommand;
import com.echothree.control.user.content.server.command.GetContentSectionCommand;
import com.echothree.model.control.content.server.ContentControl;
import com.echothree.model.control.graphql.server.graphql.BaseEntityInstanceObject;
import com.echothree.model.control.graphql.server.util.GraphQlContext;
import com.echothree.model.control.user.server.UserControl;
import com.echothree.model.data.content.server.entity.ContentPage;
import com.echothree.model.data.content.server.entity.ContentSection;
import com.echothree.model.data.content.server.entity.ContentSectionDetail;
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
    
    private Boolean hasContentSectionAccess;
    
    private boolean getHasContentSectionAccess(final DataFetchingEnvironment env) {
        if(hasContentSectionAccess == null) {
            GraphQlContext context = env.getContext();
            BaseSingleEntityCommand baseSingleEntityCommand = new GetContentSectionCommand(context.getUserVisitPK(), null);
            
            baseSingleEntityCommand.security();
            
            hasContentSectionAccess = !baseSingleEntityCommand.hasSecurityMessages();
        }
        
        return hasContentSectionAccess;
    }
        
    private Boolean hasContentPagesAccess;
    
    private boolean getHasContentPagesAccess(final DataFetchingEnvironment env) {
        if(hasContentPagesAccess == null) {
            GraphQlContext context = env.getContext();
            BaseMultipleEntitiesCommand baseMultipleEntitiesCommand = new GetContentPagesCommand(context.getUserVisitPK(), null);
            
            baseMultipleEntitiesCommand.security();
            
            hasContentPagesAccess = !baseMultipleEntitiesCommand.hasSecurityMessages();
        }
        
        return hasContentPagesAccess;
    }
    
    @GraphQLField
    @GraphQLDescription("content collection")
    public ContentCollectionObject getContentCollection(final DataFetchingEnvironment env) {
        return getHasContentCollectionAccess(env) ? new ContentCollectionObject(getContentSectionDetail().getContentCollection()) : null;
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
        return getHasContentSectionAccess(env) ? new ContentSectionObject(getContentSectionDetail().getParentContentSection()) : null;
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
        ContentControl contentControl = (ContentControl)Session.getModelController(ContentControl.class);
        UserControl userControl = (UserControl)Session.getModelController(UserControl.class);
        GraphQlContext context = env.getContext();
        
        return contentControl.getBestContentSectionDescription(contentSection, userControl.getPreferredLanguageFromUserVisit(context.getUserVisit()));
    }
    
//    @GraphQLField
//    @GraphQLDescription("content pages")
//    public List<ContentPageObject> getContentPages(final DataFetchingEnvironment env) {
//        ContentControl contentControl = (ContentControl)Session.getModelController(ContentControl.class);
//        List<ContentPage> entities = getHasContentPagesAccess(env) ? contentControl.getContentPages(contentSection) : null;
//        List<ContentPageObject> contentPages = new ArrayList<>(entities.size());
//        
//        entities.forEach((entity) -> {
//            contentPages.add(new ContentPageObject(entity));
//        });
//        
//        return contentPages;
//    }
    
}
