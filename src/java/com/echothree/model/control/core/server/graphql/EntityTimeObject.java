// --------------------------------------------------------------------------------
// Copyright 2002-2021 Echo Three, LLC
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
import com.echothree.model.data.core.server.entity.EntityTime;
import com.echothree.util.server.string.DateUtils;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;

@GraphQLDescription("entity time object")
@GraphQLName("EntityTime")
public class EntityTimeObject
        extends BaseGraphQl {
    
    private final EntityTime entityTime; // Always Present
    
    public EntityTimeObject(EntityTime entityTime) {
        this.entityTime = entityTime;
    }
    
    @GraphQLField
    @GraphQLDescription("entity instance")
    @GraphQLNonNull
    public EntityInstanceObject getEntityInstance() {
        return new EntityInstanceObject(entityTime.getEntityInstance());
    }
    
    @GraphQLField
    @GraphQLDescription("unformatted created time")
    @GraphQLNonNull
    public Long getUnformattedCreatedTime() {
        return entityTime.getCreatedTime();
    }
    
    @GraphQLField
    @GraphQLDescription("created time")
    @GraphQLNonNull
    public String getCreatedTime(final DataFetchingEnvironment env) {
        return DateUtils.getInstance().formatTypicalDateTime(getUserVisit(env), entityTime.getCreatedTime());
    }
    
    @GraphQLField
    @GraphQLDescription("unformatted modified time")
    public Long getUnformattedModifiedTime() {
        return entityTime.getModifiedTime();
    }
    
    @GraphQLField
    @GraphQLDescription("modified time")
    public String getModifiedTime(final DataFetchingEnvironment env) {
        Long modifiedTime = entityTime.getModifiedTime();
        
        return modifiedTime == null ? null : DateUtils.getInstance().formatTypicalDateTime(getUserVisit(env), modifiedTime);
    }
    
    @GraphQLField
    @GraphQLDescription("unformatted deleted time")
    public Long getUnformattedDeletedTime() {
        return entityTime.getDeletedTime();
    }
    
    @GraphQLField
    @GraphQLDescription("deleted time")
    public String getDeletedTime(final DataFetchingEnvironment env) {
        Long deletedTime = entityTime.getDeletedTime();
        
        return deletedTime == null ? null : DateUtils.getInstance().formatTypicalDateTime(getUserVisit(env), deletedTime);
    }
    
}
