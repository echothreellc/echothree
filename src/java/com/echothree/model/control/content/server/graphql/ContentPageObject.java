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

import com.echothree.control.user.content.server.command.GetContentPageLayoutCommand;
import com.echothree.control.user.content.server.command.GetContentSectionCommand;
import com.echothree.model.control.content.server.ContentControl;
import com.echothree.model.control.graphql.server.graphql.BaseEntityInstanceObject;
import com.echothree.model.control.graphql.server.util.GraphQlContext;
import com.echothree.model.control.user.server.UserControl;
import com.echothree.model.data.content.server.entity.ContentPage;
import com.echothree.model.data.content.server.entity.ContentPageDetail;
import com.echothree.util.server.control.BaseSingleEntityCommand;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.schema.DataFetchingEnvironment;
import graphql.annotations.annotationTypes.GraphQLNonNull;

@GraphQLDescription("content page object")
@GraphQLName("ContentPage")
public class ContentPageObject
        extends BaseEntityInstanceObject {
    
    private final ContentPage contentPage; // Always Present
    
    public ContentPageObject(ContentPage contentPage) {
        super(contentPage.getPrimaryKey());
        
        this.contentPage = contentPage;
    }

    private ContentPageDetail contentPageDetail; // Optional, use getContentPageDetail()
    
    private ContentPageDetail getContentPageDetail() {
        if(contentPageDetail == null) {
            contentPageDetail = contentPage.getLastDetail();
        }
        
        return contentPageDetail;
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
    
    private Boolean hasContentPageLayoutAccess;
    
    private boolean getHasContentPageLayoutAccess(final DataFetchingEnvironment env) {
        if(hasContentPageLayoutAccess == null) {
            GraphQlContext context = env.getContext();
            BaseSingleEntityCommand baseSingleEntityCommand = new GetContentPageLayoutCommand(context.getUserVisitPK(), null);
            
            baseSingleEntityCommand.security();
            
            hasContentPageLayoutAccess = !baseSingleEntityCommand.hasSecurityMessages();
        }
        
        return hasContentPageLayoutAccess;
    }
    
    @GraphQLField
    @GraphQLDescription("content section")
    public ContentSectionObject getContentSection(final DataFetchingEnvironment env) {
        return getHasContentSectionAccess(env) ? new ContentSectionObject(getContentPageDetail().getContentSection()) : null;
    }

    @GraphQLField
    @GraphQLDescription("content page name")
    @GraphQLNonNull
    public String getContentPageName() {
        return getContentPageDetail().getContentPageName();
    }

    @GraphQLField
    @GraphQLDescription("content page layout")
    public ContentPageLayoutObject getContentPageLayout(final DataFetchingEnvironment env) {
        return getHasContentPageLayoutAccess(env) ? new ContentPageLayoutObject(getContentPageDetail().getContentPageLayout()) : null;
    }

    @GraphQLField
    @GraphQLDescription("is default")
    @GraphQLNonNull
    public boolean getIsDefault() {
        return getContentPageDetail().getIsDefault();
    }
    
    @GraphQLField
    @GraphQLDescription("sort order")
    @GraphQLNonNull
    public int getSortOrder() {
        return getContentPageDetail().getSortOrder();
    }
    
    @GraphQLField
    @GraphQLDescription("description")
    @GraphQLNonNull
    public String getDescription(final DataFetchingEnvironment env) {
        ContentControl contentControl = (ContentControl)Session.getModelController(ContentControl.class);
        UserControl userControl = (UserControl)Session.getModelController(UserControl.class);
        GraphQlContext context = env.getContext();
        
        return contentControl.getBestContentPageDescription(contentPage, userControl.getPreferredLanguageFromUserVisit(context.getUserVisit()));
    }
    
}
