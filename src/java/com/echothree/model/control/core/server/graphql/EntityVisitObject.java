// --------------------------------------------------------------------------------
// Copyright 2002-2023 Echo Three, LLC
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
import com.echothree.model.data.core.server.entity.EntityVisit;
import com.echothree.util.server.string.DateUtils;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;

@GraphQLDescription("entity visit object")
@GraphQLName("EntityVisit")
public class EntityVisitObject
        extends BaseGraphQl {
    
    private final EntityVisit entityVisit; // Always Present
    
    public EntityVisitObject(EntityVisit entityVisit) {
        this.entityVisit = entityVisit;
    }
    
    @GraphQLField
    @GraphQLDescription("visited entity instance")
    public EntityInstanceObject getVisitedEntityInstance(final DataFetchingEnvironment env) {
        return CoreSecurityUtils.getInstance().getHasEntityInstanceAccess(env) ? new EntityInstanceObject(entityVisit.getVisitedEntityInstance()) : null;
    }
    
    @GraphQLField
    @GraphQLDescription("entity instance")
    public EntityInstanceObject getEntityInstance(final DataFetchingEnvironment env) {
        return CoreSecurityUtils.getInstance().getHasEntityInstanceAccess(env) ? new EntityInstanceObject(entityVisit.getEntityInstance()) : null;
    }
    
    @GraphQLField
    @GraphQLDescription("unformatted visited time")
    @GraphQLNonNull
    public Long getUnformattedVisitedTime() {
        return entityVisit.getVisitedTime();
    }
    
    @GraphQLField
    @GraphQLDescription("visited time")
    @GraphQLNonNull
    public String getVisitedTime(final DataFetchingEnvironment env) {
        return DateUtils.getInstance().formatTypicalDateTime(getUserVisit(env), entityVisit.getVisitedTime());
    }
        
}
