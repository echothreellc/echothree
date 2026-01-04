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
import com.echothree.model.data.core.server.entity.EntityLongAttribute;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;

@GraphQLDescription("entity long attribute object")
@GraphQLName("EntityLongAttribute")
public class BaseEntityLongAttributeObject
        implements BaseGraphQl {

    protected final EntityLongAttribute entityLongAttribute; // Always Present

    public BaseEntityLongAttributeObject(EntityLongAttribute entityLongAttribute) {
        this.entityLongAttribute = entityLongAttribute;
    }

    @GraphQLField
    @GraphQLDescription("entity attribute")
    public EntityAttributeObject getEntityAttribute(final DataFetchingEnvironment env) {
        return CoreSecurityUtils.getHasEntityAttributeAccess(env) ? new EntityAttributeObject(entityLongAttribute.getEntityAttribute(), entityLongAttribute.getEntityInstance()) : null;
    }

    @GraphQLField
    @GraphQLDescription("entity instance")
    public EntityInstanceObject getEntityInstance(final DataFetchingEnvironment env) {
        return CoreSecurityUtils.getHasEntityInstanceAccess(env) ? new EntityInstanceObject(entityLongAttribute.getEntityInstance()) : null;
    }

    @GraphQLField
    @GraphQLDescription("unformatted long attribute")
    @GraphQLNonNull
    public Long getUnformattedLongAttribute() {
        return entityLongAttribute.getLongAttribute();
    }

    @GraphQLField
    @GraphQLDescription("long attribute")
    @GraphQLNonNull
    public String getLongAttribute() {
        return entityLongAttribute.getLongAttribute().toString(); // TODO
    }

}
