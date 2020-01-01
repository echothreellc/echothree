// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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

import com.echothree.model.control.graphql.server.util.GraphQlContext;
import com.echothree.model.data.core.server.entity.EntityVisit;
import com.echothree.util.server.string.DateUtils;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.schema.DataFetchingEnvironment;
import graphql.annotations.annotationTypes.GraphQLNonNull;

@GraphQLDescription("entity visit object")
@GraphQLName("EntityVisit")
public class EntityVisitObject {
    
    private final EntityVisit entityVisit; // Always Present
    
    public EntityVisitObject(EntityVisit entityVisit) {
        this.entityVisit = entityVisit;
    }
    
    @GraphQLField
    @GraphQLDescription("visited entity instance")
    @GraphQLNonNull
    public EntityInstanceObject getVisitedEntityInstance() {
        return new EntityInstanceObject(entityVisit.getVisitedEntityInstance());
    }
    
    @GraphQLField
    @GraphQLDescription("entity instance")
    @GraphQLNonNull
    public EntityInstanceObject getEntityInstance() {
        return new EntityInstanceObject(entityVisit.getEntityInstance());
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
        GraphQlContext context = env.getContext();

        return DateUtils.getInstance().formatTypicalDateTime(context.getUserVisit(), entityVisit.getVisitedTime());
    }
        
}
