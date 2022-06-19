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

package com.echothree.model.control.core.server.graphql;

import com.echothree.model.control.graphql.server.util.BaseGraphQl;
import com.echothree.model.data.core.server.entity.EntityDateAttribute;
import com.echothree.util.server.string.DateUtils;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;

@GraphQLDescription("entity date attribute object")
@GraphQLName("EntityDateAttribute")
public class EntityDateAttributeObject
        extends BaseGraphQl {
    
    private final EntityDateAttribute entityDateAttribute; // Always Present
    
    public EntityDateAttributeObject(EntityDateAttribute entityDateAttribute) {
        this.entityDateAttribute = entityDateAttribute;
    }

    @GraphQLField
    @GraphQLDescription("unformatted date attribute")
    @GraphQLNonNull
    public int getUnformattedDateAttribute() {
        return entityDateAttribute.getDateAttribute();
    }

    @GraphQLField
    @GraphQLDescription("date attribute")
    @GraphQLNonNull
    public String getDateAttribute(final DataFetchingEnvironment env) {
        return DateUtils.getInstance().formatDate(getUserVisit(env), entityDateAttribute.getDateAttribute());
    }

    @GraphQLField
    @GraphQLDescription("entity attribute")
    public EntityAttributeObject getEntityAttribute(final DataFetchingEnvironment env) {
        return CoreSecurityUtils.getInstance().getHasEntityAttributeAccess(env) ? new EntityAttributeObject(entityDateAttribute.getEntityAttribute(), entityDateAttribute.getEntityInstance()) : null;
    }
    
    @GraphQLField
    @GraphQLDescription("entity instance")
    public EntityInstanceObject getEntityInstance(final DataFetchingEnvironment env) {
        return CoreSecurityUtils.getInstance().getHasEntityInstanceAccess(env) ? new EntityInstanceObject(entityDateAttribute.getEntityInstance()) : null;
    }
    
}
