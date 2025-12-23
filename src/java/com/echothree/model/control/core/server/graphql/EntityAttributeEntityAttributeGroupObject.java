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

package com.echothree.model.control.core.server.graphql;

import com.echothree.model.control.graphql.server.util.BaseGraphQl;
import com.echothree.model.data.core.server.entity.EntityAttributeEntityAttributeGroup;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;

@GraphQLDescription("entity attribute entity attribute group object")
@GraphQLName("EntityAttributeEntityAttributeGroup")
public class EntityAttributeEntityAttributeGroupObject
        implements BaseGraphQl {
    
    private final EntityAttributeEntityAttributeGroup entityAttributeEntityAttributeGroup; // Always Present
    
    public EntityAttributeEntityAttributeGroupObject(EntityAttributeEntityAttributeGroup entityAttributeEntityAttributeGroup) {
        this.entityAttributeEntityAttributeGroup = entityAttributeEntityAttributeGroup;
    }

    @GraphQLField
    @GraphQLDescription("entity instance")
    public EntityAttributeObject getEntityAttribute(final DataFetchingEnvironment env) {
        return CoreSecurityUtils.getHasEntityAttributeAccess(env) ? new EntityAttributeObject(entityAttributeEntityAttributeGroup.getEntityAttribute(), null) : null;
    }

    @GraphQLField
    @GraphQLDescription("entity instance")
    public EntityAttributeGroupObject getEntityAttributeGroup(final DataFetchingEnvironment env) {
        return CoreSecurityUtils.getHasEntityAttributeGroupAccess(env) ? new EntityAttributeGroupObject(entityAttributeEntityAttributeGroup.getEntityAttributeGroup(), null) : null;
    }

    @GraphQLField
    @GraphQLDescription("sort order")
    @GraphQLNonNull
    public int getSortOrder() {
        return entityAttributeEntityAttributeGroup.getSortOrder();
    }

}
