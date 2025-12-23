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
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.data.content.common.ContentPageLayoutAreaConstants;
import com.echothree.model.data.content.server.entity.ContentPageLayout;
import com.echothree.model.data.content.server.entity.ContentPageLayoutDetail;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.annotations.connection.GraphQLConnection;
import graphql.schema.DataFetchingEnvironment;
import java.util.ArrayList;
import java.util.stream.Collectors;

@GraphQLDescription("content page layout object")
@GraphQLName("ContentPageLayout")
public class ContentPageLayoutObject
        extends BaseEntityInstanceObject {
    
    private final ContentPageLayout contentPageLayout; // Always Present
    
    public ContentPageLayoutObject(ContentPageLayout contentPageLayout) {
        super(contentPageLayout.getPrimaryKey());
        
        this.contentPageLayout = contentPageLayout;
    }

    private ContentPageLayoutDetail contentPageLayoutDetail; // Optional, use getContentPageLayoutDetail()
    
    private ContentPageLayoutDetail getContentPageLayoutDetail() {
        if(contentPageLayoutDetail == null) {
            contentPageLayoutDetail = contentPageLayout.getLastDetail();
        }
        
        return contentPageLayoutDetail;
    }
    
    @GraphQLField
    @GraphQLDescription("content page layout name")
    @GraphQLNonNull
    public String getContentPageLayoutName() {
        return getContentPageLayoutDetail().getContentPageLayoutName();
    }

    @GraphQLField
    @GraphQLDescription("is default")
    @GraphQLNonNull
    public boolean getIsDefault() {
        return getContentPageLayoutDetail().getIsDefault();
    }
    
    @GraphQLField
    @GraphQLDescription("sort order")
    @GraphQLNonNull
    public int getSortOrder() {
        return getContentPageLayoutDetail().getSortOrder();
    }
    
    @GraphQLField
    @GraphQLDescription("description")
    @GraphQLNonNull
    public String getDescription(final DataFetchingEnvironment env) {
        var contentControl = Session.getModelController(ContentControl.class);
        var userControl = Session.getModelController(UserControl.class);

        return contentControl.getBestContentPageLayoutDescription(contentPageLayout, userControl.getPreferredLanguageFromUserVisit(BaseGraphQl.getUserVisit(env)));
    }

    @GraphQLField
    @GraphQLDescription("contentPageLayoutAreas")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    public CountingPaginatedData<ContentPageLayoutAreaObject> getContentPageLayoutAreas(final DataFetchingEnvironment env) {
        if(ContentSecurityUtils.getHasContentPageLayoutAreasAccess(env)) {
            var contentControl = Session.getModelController(ContentControl.class);
            var totalCount = contentControl.countContentPageLayoutAreasByContentPageLayout(contentPageLayout);

            try(var objectLimiter = new ObjectLimiter(env, ContentPageLayoutAreaConstants.COMPONENT_VENDOR_NAME, ContentPageLayoutAreaConstants.ENTITY_TYPE_NAME, totalCount)) {
                var entities = contentControl.getContentPageLayoutAreasByContentPageLayout(contentPageLayout);
                var contentPageLayoutAreas = entities.stream().map(ContentPageLayoutAreaObject::new).collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                return new CountedObjects<>(objectLimiter, contentPageLayoutAreas);
            }
        } else {
            return Connections.emptyConnection();
        }
    }

}
