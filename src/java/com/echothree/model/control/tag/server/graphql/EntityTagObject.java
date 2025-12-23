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
import com.echothree.model.control.core.server.graphql.EntityInstanceObject;
import com.echothree.model.control.graphql.server.graphql.BaseObject;
import com.echothree.model.data.tag.server.entity.EntityTag;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;

@GraphQLDescription("entity tag object")
@GraphQLName("EntityTag")
public class EntityTagObject
        extends BaseObject {

    private final EntityTag entityTag; // Always Present

    public EntityTagObject(EntityTag entityTag) {
        this.entityTag = entityTag;
    }

    @GraphQLField
    @GraphQLDescription("tagged entity instance")
    @GraphQLNonNull
    public EntityInstanceObject getTaggedEntityInstance(final DataFetchingEnvironment env) {
        return CoreSecurityUtils.getHasEntityInstanceAccess(env) ? new EntityInstanceObject(entityTag.getTaggedEntityInstance()): null;
    }

    @GraphQLField
    @GraphQLDescription("tag")
    @GraphQLNonNull
    public TagObject getTag(final DataFetchingEnvironment env) {
        return TagSecurityUtils.getHasTagAccess(env) ? new TagObject(entityTag.getTag()): null;
    }

}
