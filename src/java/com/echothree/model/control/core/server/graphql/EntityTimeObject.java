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

import com.echothree.model.control.graphql.server.graphql.TimeObject;
import com.echothree.model.control.graphql.server.util.BaseGraphQl;
import com.echothree.model.data.core.server.entity.EntityTime;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;

@GraphQLDescription("entity time object")
@GraphQLName("EntityTime")
public class EntityTimeObject
        implements BaseGraphQl {
    
    private final EntityTime entityTime; // Always Present
    
    public EntityTimeObject(EntityTime entityTime) {
        this.entityTime = entityTime;
    }
    
    @GraphQLField
    @GraphQLDescription("entity instance")
    public EntityInstanceObject getEntityInstance(final DataFetchingEnvironment env) {
        return CoreSecurityUtils.getHasEntityInstanceAccess(env) ? new EntityInstanceObject(entityTime.getEntityInstance()) : null;
    }
    
    @GraphQLField
    @GraphQLDescription("created time")
    @GraphQLNonNull
    public TimeObject getCreatedTime(final DataFetchingEnvironment env) {
        return new TimeObject(entityTime.getCreatedTime());
    }
    
    @GraphQLField
    @GraphQLDescription("modified time")
    public TimeObject getModifiedTime(final DataFetchingEnvironment env) {
        var modifiedTime = entityTime.getModifiedTime();
        
        return modifiedTime == null ? null : new TimeObject(modifiedTime);
    }
    
    @GraphQLField
    @GraphQLDescription("deleted time")
    public TimeObject getDeletedTime(final DataFetchingEnvironment env) {
        var deletedTime = entityTime.getDeletedTime();
        
        return deletedTime == null ? null : new TimeObject(deletedTime);
    }
    
}
