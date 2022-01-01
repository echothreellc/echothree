// --------------------------------------------------------------------------------
// Copyright 2002-2022 Echo Three, LLC
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

import com.echothree.control.user.core.server.command.GetEntityAttributeCommand;
import com.echothree.model.control.graphql.server.util.BaseGraphQl;
import com.echothree.model.data.core.server.entity.EntityLongAttribute;
import com.echothree.util.server.control.BaseSingleEntityCommand;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;

@GraphQLDescription("entity long attribute object")
@GraphQLName("EntityLongAttribute")
public class EntityLongAttributeObject
        extends BaseGraphQl {
    
    private final EntityLongAttribute entityLongAttribute; // Always Present
    
    public EntityLongAttributeObject(EntityLongAttribute entityLongAttribute) {
        this.entityLongAttribute = entityLongAttribute;
    }

    private Boolean hasEntityAttributeAccess;
    
    private boolean getHasEntityAttributeAccess(final DataFetchingEnvironment env) {
        if(hasEntityAttributeAccess == null) {
            var baseSingleEntityCommand = new GetEntityAttributeCommand(getUserVisitPK(env), null);
            
            baseSingleEntityCommand.security();
            
            hasEntityAttributeAccess = !baseSingleEntityCommand.hasSecurityMessages();
        }
        
        return hasEntityAttributeAccess;
    }
        
    @GraphQLField
    @GraphQLDescription("unformatted long attribute")
    @GraphQLNonNull
    public Long getUnformattedLongAttribute() {
        return entityLongAttribute.getLongAttribute();
    }
    
    @GraphQLField
    @GraphQLDescription("long attribute")
    @GraphQLNonNull
    public String getLongAttribute() {
        return entityLongAttribute.getLongAttribute().toString(); // TODO
    }
    
    @GraphQLField
    @GraphQLDescription("entity attribute")
    public EntityAttributeObject getEntityAttribute(final DataFetchingEnvironment env) {
        return getHasEntityAttributeAccess(env) ? new EntityAttributeObject(entityLongAttribute.getEntityAttribute(), entityLongAttribute.getEntityInstance()) : null;
    }
    
    @GraphQLField
    @GraphQLDescription("entity instance")
    public EntityInstanceObject getEntityInstance(final DataFetchingEnvironment env) {
        return new EntityInstanceObject(entityLongAttribute.getEntityInstance());
    }
    
}
