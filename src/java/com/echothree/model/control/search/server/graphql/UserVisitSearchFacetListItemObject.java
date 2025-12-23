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

import com.echothree.model.control.core.server.graphql.CoreSecurityUtils;
import com.echothree.model.control.core.server.graphql.EntityListItemObject;
import com.echothree.model.data.core.server.entity.EntityListItem;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;

@GraphQLDescription("user visit search facet list item object")
@GraphQLName("UserVisitSearchFacetListItem")
public class UserVisitSearchFacetListItemObject  {

    final private EntityListItem entityListItem;
    final private Long count;

    /** Creates a new instance of UserVisitSearchFacetListItemObject */
    public UserVisitSearchFacetListItemObject(EntityListItem entityListItem, Long count) {
        this.entityListItem = entityListItem;
        this.count = count;
    }

    @GraphQLField
    @GraphQLDescription("entity list item")
    @GraphQLNonNull
    public EntityListItemObject getEntityListItem(final DataFetchingEnvironment env) {
        return CoreSecurityUtils.getHasEntityListItemAccess(env) ? new EntityListItemObject(entityListItem) : null;
    }

    @GraphQLField
    @GraphQLDescription("count")
    @GraphQLNonNull
    public Long getCount() {
        return count;
    }

}
