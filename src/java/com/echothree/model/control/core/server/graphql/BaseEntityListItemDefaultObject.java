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
import com.echothree.model.data.core.server.entity.EntityListItemDefault;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.schema.DataFetchingEnvironment;

public class BaseEntityListItemDefaultObject
        implements BaseGraphQl {

    protected final EntityListItemDefault entityListItemDefault; // Always Present

    protected BaseEntityListItemDefaultObject(EntityListItemDefault entityListItemDefault) {
        this.entityListItemDefault = entityListItemDefault;
    }

    @GraphQLField
    @GraphQLDescription("entity attribute")
    public EntityAttributeObject getEntityAttribute(final DataFetchingEnvironment env) {
        return CoreSecurityUtils.getHasEntityAttributeAccess(env) ? new EntityAttributeObject(entityListItemDefault.getEntityAttribute(), null) : null;
    }

    @GraphQLField
    @GraphQLDescription("entity list item")
    public EntityListItemObject getEntityListItem(final DataFetchingEnvironment env) {
        return CoreSecurityUtils.getHasEntityListItemAccess(env) ? new EntityListItemObject(entityListItemDefault.getEntityListItem()) : null;
    }

}
