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

package com.echothree.model.control.tag.server.graphql;

import com.echothree.model.control.core.server.graphql.CoreSecurityUtils;
import com.echothree.model.control.core.server.graphql.EntityTypeObject;
import com.echothree.model.control.graphql.server.graphql.BaseObject;
import com.echothree.model.data.tag.server.entity.TagScopeEntityType;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;

@GraphQLDescription("tag scope entity type object")
@GraphQLName("TagScopeEntityType")
public class TagScopeEntityTypeObject
        extends BaseObject {

    private final TagScopeEntityType tagScopeEntityType; // Always Present

    public TagScopeEntityTypeObject(TagScopeEntityType tagScopeEntityType) {
        this.tagScopeEntityType = tagScopeEntityType;
    }

    @GraphQLField
    @GraphQLDescription("tag scope")
    @GraphQLNonNull
    public TagScopeObject getTagScope(final DataFetchingEnvironment env) {
        return TagSecurityUtils.getHasTagScopeAccess(env) ? new TagScopeObject(tagScopeEntityType.getTagScope(), null): null;
    }

    @GraphQLField
    @GraphQLDescription("entity type")
    @GraphQLNonNull
    public EntityTypeObject getEntityType(final DataFetchingEnvironment env) {
        return CoreSecurityUtils.getHasEntityTypeAccess(env) ? new EntityTypeObject(tagScopeEntityType.getEntityType()): null;
    }

}
