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

package com.echothree.model.control.search.server.graphql;

import com.echothree.model.control.core.server.graphql.EntityTypeObject;
import com.echothree.model.data.core.server.entity.EntityType;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;

@GraphQLDescription("entity type result object")
@GraphQLName("EntityTypeResult")
public class EntityTypeResultObject {
    
    private final EntityType entityType;
    
    public EntityTypeResultObject(EntityType entityType) {
        this.entityType = entityType;
    }
    
    @GraphQLField
    @GraphQLDescription("entity type")
    @GraphQLNonNull
    public EntityTypeObject getEntityType() {
        return new EntityTypeObject(entityType);
    }
    
}
