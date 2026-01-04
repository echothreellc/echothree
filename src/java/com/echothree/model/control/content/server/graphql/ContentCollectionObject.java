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

import com.echothree.model.control.content.server.control.ContentControl;
import com.echothree.model.control.graphql.server.graphql.BaseEntityInstanceObject;
import com.echothree.model.control.graphql.server.graphql.count.Connections;
import com.echothree.model.control.graphql.server.graphql.count.CountedObjects;
import com.echothree.model.control.graphql.server.graphql.count.CountingDataConnectionFetcher;
import com.echothree.model.control.graphql.server.graphql.count.CountingPaginatedData;
import com.echothree.model.control.graphql.server.util.BaseGraphQl;
import com.echothree.model.control.graphql.server.util.count.ObjectLimiter;
import com.echothree.model.control.offer.server.graphql.OfferSecurityUtils;
import com.echothree.model.control.offer.server.graphql.OfferUseObject;
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.data.content.common.ContentCatalogConstants;
import com.echothree.model.data.content.common.ContentSectionConstants;
import com.echothree.model.data.content.server.entity.ContentCollection;
import com.echothree.model.data.content.server.entity.ContentCollectionDetail;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.annotations.connection.GraphQLConnection;
import graphql.schema.DataFetchingEnvironment;
import java.util.ArrayList;
import java.util.stream.Collectors;

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
    @GraphQLDescription("contentCatalogs")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    public CountingPaginatedData<ContentCatalogObject> getContentCatalogs(final DataFetchingEnvironment env) {
        if(ContentSecurityUtils.getHasContentCatalogsAccess(env)) {
            var contentControl = Session.getModelController(ContentControl.class);
            var totalCount = contentControl.countContentCatalogsByContentCollection(contentCollection);

            try(var objectLimiter = new ObjectLimiter(env, ContentCatalogConstants.COMPONENT_VENDOR_NAME, ContentCatalogConstants.ENTITY_TYPE_NAME, totalCount)) {
                var entities = contentControl.getContentCatalogs(contentCollection);
                var contentCatalogs = entities.stream().map(ContentCatalogObject::new).collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                return new CountedObjects<>(objectLimiter, contentCatalogs);
            }
        } else {
            return Connections.emptyConnection();
        }
    }

    @GraphQLField
    @GraphQLDescription("contentSections")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    public CountingPaginatedData<ContentSectionObject> getContentSections(final DataFetchingEnvironment env) {
        if(ContentSecurityUtils.getHasContentSectionsAccess(env)) {
            var contentControl = Session.getModelController(ContentControl.class);
            var totalCount = contentControl.countContentSectionsByContentCollection(contentCollection);

            try(var objectLimiter = new ObjectLimiter(env, ContentSectionConstants.COMPONENT_VENDOR_NAME, ContentSectionConstants.ENTITY_TYPE_NAME, totalCount)) {
                var entities = contentControl.getContentSections(contentCollection);
                var contentSections = entities.stream().map(ContentSectionObject::new).collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                return new CountedObjects<>(objectLimiter, contentSections);
            }
        } else {
            return Connections.emptyConnection();
        }
    }

}
