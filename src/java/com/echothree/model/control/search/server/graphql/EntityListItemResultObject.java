// --------------------------------------------------------------------------------
// Copyright 2002-2025 Echo Three, LLC
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

import com.echothree.model.control.core.server.graphql.EntityListItemObject;
import com.echothree.model.data.core.server.entity.EntityListItem;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;

@GraphQLDescription("entity list item result object")
@GraphQLName("EntityListItemResult")
public class EntityListItemResultObject {
    
    private final EntityListItem entityListItem;
    
    public EntityListItemResultObject(EntityListItem entityListItem) {
        this.entityListItem = entityListItem;
    }
    
    @GraphQLField
    @GraphQLDescription("entity list item")
    @GraphQLNonNull
    public EntityListItemObject getEntityListItem() {
        return new EntityListItemObject(entityListItem);
    }
    
}
