// --------------------------------------------------------------------------------
// Copyright 2002-2023 Echo Three, LLC
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
import com.echothree.model.control.graphql.server.util.BaseGraphQl;
import com.echothree.model.control.offer.server.graphql.OfferSecurityUtils;
import com.echothree.model.control.offer.server.graphql.OfferUseObject;
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.data.content.server.entity.ContentCatalog;
import com.echothree.model.data.content.server.entity.ContentCollection;
import com.echothree.model.data.content.server.entity.ContentCollectionDetail;
import com.echothree.model.data.content.server.entity.ContentSection;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;
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
    
    @GraphQLField
    @GraphQLDescription("content collection name")
    @GraphQLNonNull
    public String getContentCollectionName() {
        return getContentCollectionDetail().getContentCollectionName();
    }

    @GraphQLField
    @GraphQLDescription("default offer use")
    @GraphQLNonNull
    public OfferUseObject getDefaultOfferUse(final DataFetchingEnvironment env) {
        return OfferSecurityUtils.getHasOfferUseAccess(env) ?
                new OfferUseObject(getContentCollectionDetail().getDefaultOfferUse()) : null;
    }

    @GraphQLField
    @GraphQLDescription("description")
    @GraphQLNonNull
    public String getDescription(final DataFetchingEnvironment env) {
        var contentControl = Session.getModelController(ContentControl.class);
        var userControl = Session.getModelController(UserControl.class);

        return contentControl.getBestContentCollectionDescription(contentCollection, userControl.getPreferredLanguageFromUserVisit(BaseGraphQl.getUserVisit(env)));
    }
    
    @GraphQLField
    @GraphQLDescription("content catalogs count")
    public Long getContentCatalogsCount(final DataFetchingEnvironment env) {
        var contentControl = Session.getModelController(ContentControl.class);
        
        return ContentSecurityUtils.getHasContentCatalogsAccess(env) ? contentControl.countContentCatalogsByContentCollection(contentCollection) : null;
    }
    
    @GraphQLField
    @GraphQLDescription("content catalogs")
    public List<ContentCatalogObject> getContentCatalogs(final DataFetchingEnvironment env) {
        var contentControl = Session.getModelController(ContentControl.class);
        List<ContentCatalog> entities = ContentSecurityUtils.getHasContentCatalogsAccess(env) ? contentControl.getContentCatalogs(contentCollection) : null;
        List<ContentCatalogObject> contentCatalogs = entities == null ? null : new ArrayList<>(entities.size());
        
        if(entities != null) {
            entities.forEach((entity) -> {
                contentCatalogs.add(new ContentCatalogObject(entity));
            });
        }
        
        return contentCatalogs;
    }
    
    @GraphQLField
    @GraphQLDescription("content sections count")
    public Long getContentSectionsCount(final DataFetchingEnvironment env) {
        var contentControl = Session.getModelController(ContentControl.class);
        
        return ContentSecurityUtils.getHasContentSectionsAccess(env) ? contentControl.countContentSectionsByContentCollection(contentCollection) : null;
    }
    
    @GraphQLField
    @GraphQLDescription("content sections")
    public List<ContentSectionObject> getContentSections(final DataFetchingEnvironment env) {
        var contentControl = Session.getModelController(ContentControl.class);
        List<ContentSection> entities = ContentSecurityUtils.getHasContentSectionsAccess(env) ? contentControl.getContentSections(contentCollection) : null;
        List<ContentSectionObject> contentSections = entities == null ? null : new ArrayList<>(entities.size());
        
        if(entities != null) {
            entities.forEach((entity) -> {
                contentSections.add(new ContentSectionObject(entity));
            });
        }
        
        return contentSections;
    }
    
}
