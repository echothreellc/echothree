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

import com.echothree.model.control.core.server.graphql.EntityAttributeObject;
import com.echothree.model.data.core.server.entity.EntityAttribute;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;

@GraphQLDescription("entity attribute result object")
@GraphQLName("EntityAttributeResult")
public class EntityAttributeResultObject {
    
    private final EntityAttribute entityAttribute;
    
    public EntityAttributeResultObject(EntityAttribute entityAttribute) {
        this.entityAttribute = entityAttribute;
    }
    
    @GraphQLField
    @GraphQLDescription("entity attribute")
    @GraphQLNonNull
    public EntityAttributeObject getEntityAttribute() {
        return new EntityAttributeObject(entityAttribute, null);
    }
    
}
