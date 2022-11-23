// --------------------------------------------------------------------------------
// Copyright 2002-2022 Echo Three, LLC
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

package com.echothree.model.control.tag.server.graphql;

import com.echothree.model.control.graphql.server.graphql.BaseEntityInstanceObject;
import com.echothree.model.control.graphql.server.graphql.count.Connections;
import com.echothree.model.control.graphql.server.graphql.count.CountedObjects;
import com.echothree.model.control.graphql.server.graphql.count.CountingDataConnectionFetcher;
import com.echothree.model.control.graphql.server.graphql.count.CountingPaginatedData;
import com.echothree.model.control.graphql.server.util.count.ObjectLimiter;
import com.echothree.model.control.tag.server.control.TagControl;
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.data.tag.common.TagScopeEntityTypeConstants;
import com.echothree.model.data.tag.server.entity.TagScope;
import com.echothree.model.data.tag.server.entity.TagScopeDetail;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.annotations.connection.GraphQLConnection;
import graphql.schema.DataFetchingEnvironment;
import java.util.ArrayList;
import java.util.stream.Collectors;

@GraphQLDescription("tag scope object")
@GraphQLName("TagScope")
public class TagScopeObject
        extends BaseEntityInstanceObject {
    
    private final TagScope tagScope; // Always Present
    
    public TagScopeObject(TagScope tagScope) {
        super(tagScope.getPrimaryKey());
        
        this.tagScope = tagScope;
    }

    private TagScopeDetail tagScopeDetail; // Optional, use getTagScopeDetail()
    
    private TagScopeDetail getTagScopeDetail() {
        if(tagScopeDetail == null) {
            tagScopeDetail = tagScope.getLastDetail();
        }
        
        return tagScopeDetail;
    }
    
    @GraphQLField
    @GraphQLDescription("tag scope name")
    @GraphQLNonNull
    public String getTagScopeName() {
        return getTagScopeDetail().getTagScopeName();
    }

    @GraphQLField
    @GraphQLDescription("is default")
    @GraphQLNonNull
    public boolean getIsDefault() {
        return getTagScopeDetail().getIsDefault();
    }
    
    @GraphQLField
    @GraphQLDescription("sort order")
    @GraphQLNonNull
    public int getSortOrder() {
        return getTagScopeDetail().getSortOrder();
    }
    
    @GraphQLField
    @GraphQLDescription("description")
    @GraphQLNonNull
    public String getDescription(final DataFetchingEnvironment env) {
        var tagControl = Session.getModelController(TagControl.class);
        var userControl = Session.getModelController(UserControl.class);

        return tagControl.getBestTagScopeDescription(tagScope, userControl.getPreferredLanguageFromUserVisit(getUserVisit(env)));
    }

    @GraphQLField
    @GraphQLDescription("tag scope entity types")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    public CountingPaginatedData<TagScopeEntityTypeObject> getTagScopeEntityTypes(final DataFetchingEnvironment env) {
        if(TagSecurityUtils.getInstance().getHasTagScopeEntityTypesAccess(env)) {
            var tagControl = Session.getModelController(TagControl.class);
            var totalCount = tagControl.countTagScopeEntityTypesByTagScope(tagScope);

            try(var objectLimiter = new ObjectLimiter(env, TagScopeEntityTypeConstants.COMPONENT_VENDOR_NAME, TagScopeEntityTypeConstants.ENTITY_TYPE_NAME, totalCount)) {
                var entities = tagControl.getTagScopeEntityTypesByTagScope(tagScope);
                var entityTypes = entities.stream().map(TagScopeEntityTypeObject::new).collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                return new CountedObjects<>(objectLimiter, entityTypes);
            }
        } else {
            return Connections.emptyConnection();
        }
    }

}
