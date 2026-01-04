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
import com.echothree.model.control.core.server.graphql.EntityLongRangeObject;
import com.echothree.model.data.core.server.entity.EntityLongRange;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;

@GraphQLDescription("user visit search facet long object")
@GraphQLName("UserVisitSearchFacetLong")
public class UserVisitSearchFacetLongObject {

    final private EntityLongRange entityLongRange;
    private Long count;

    /** Creates a new instance of UserVisitSearchFacetLongObject */
    public UserVisitSearchFacetLongObject(EntityLongRange entityLongRange, Long count) {
        this.entityLongRange = entityLongRange;
        this.count = count;
    }

    public EntityLongRange getEntityLongRange() {
        return entityLongRange;
    }

    @GraphQLField
    @GraphQLDescription("entity long range")
    @GraphQLNonNull
    public EntityLongRangeObject getEntityLongRange(final DataFetchingEnvironment env) {
        return CoreSecurityUtils.getHasEntityLongRangeAccess(env) ? new EntityLongRangeObject(entityLongRange) : null;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    @GraphQLField
    @GraphQLDescription("count")
    @GraphQLNonNull
    public Long getCount() {
        return count;
    }

}
