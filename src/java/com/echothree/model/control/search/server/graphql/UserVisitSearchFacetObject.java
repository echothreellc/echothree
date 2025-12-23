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
import com.echothree.model.control.core.server.graphql.EntityAttributeObject;
import com.echothree.model.data.core.server.entity.EntityAttribute;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.schema.DataFetchingEnvironment;
import java.util.Collection;

@GraphQLDescription("user visit search facet object")
@GraphQLName("UserVisitSearchFacet")
public class UserVisitSearchFacetObject {

    final private EntityAttribute entityAttribute;
    final private Collection<UserVisitSearchFacetListItemObject> userVisitSearchFacetListItems;
    final private Collection<UserVisitSearchFacetIntegerObject> userVisitSearchFacetIntegers;
    final private Collection<UserVisitSearchFacetLongObject> userVisitSearchFacetLongs;

    /** Creates a new instance of UserVisitSearchFacetIntegerObject */
    public UserVisitSearchFacetObject(EntityAttribute entityAttribute, Collection<UserVisitSearchFacetListItemObject> userVisitSearchFacetListItems,
            Collection<UserVisitSearchFacetIntegerObject> userVisitSearchFacetIntegers, Collection<UserVisitSearchFacetLongObject> userVisitSearchFacetLongs) {
        this.entityAttribute = entityAttribute;
        this.userVisitSearchFacetListItems = userVisitSearchFacetListItems;
        this.userVisitSearchFacetIntegers = userVisitSearchFacetIntegers;
        this.userVisitSearchFacetLongs = userVisitSearchFacetLongs;
    }

    @GraphQLField
    @GraphQLDescription("entity attribute")
    public EntityAttributeObject getEntityAttribute(final DataFetchingEnvironment env) {
        return CoreSecurityUtils.getHasEntityAttributeAccess(env) ? new EntityAttributeObject(entityAttribute, null) : null;
    }

    @GraphQLField
    @GraphQLDescription("entity list items")
    public Collection<UserVisitSearchFacetListItemObject> getEntityListItems() {
        return userVisitSearchFacetListItems;
    }

    @GraphQLField
    @GraphQLDescription("entity integer ranges")
    public Collection<UserVisitSearchFacetIntegerObject> getEntityIntegerRanges() {
        return userVisitSearchFacetIntegers;
    }

    @GraphQLField
    @GraphQLDescription("entity long ranges")
    public Collection<UserVisitSearchFacetLongObject> getEntityLongRanges() {
        return userVisitSearchFacetLongs;
    }

}
