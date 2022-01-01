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
import com.echothree.control.user.core.server.command.GetEntityListItemCommand;
import com.echothree.model.control.graphql.server.util.BaseGraphQl;
import com.echothree.model.data.core.server.entity.EntityMultipleListItemAttribute;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.schema.DataFetchingEnvironment;

@GraphQLDescription("entity multiple list item attribute object")
@GraphQLName("EntityMultipleListItemAttribute")
public class EntityMultipleListItemAttributeObject
        extends BaseGraphQl {
    
    private final EntityMultipleListItemAttribute entityMultipleListItemAttribute; // Always Present
    
    public EntityMultipleListItemAttributeObject(EntityMultipleListItemAttribute entityListItemAttribute) {
        this.entityMultipleListItemAttribute = entityListItemAttribute;
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
        
    private Boolean hasEntityListItemAccess;
    
    private boolean getHasEntityListItemAccess(final DataFetchingEnvironment env) {
        if(hasEntityListItemAccess == null) {
            var baseSingleEntityCommand = new GetEntityListItemCommand(getUserVisitPK(env), null);
            
            baseSingleEntityCommand.security();
            
            hasEntityListItemAccess = !baseSingleEntityCommand.hasSecurityMessages();
        }
        
        return hasEntityListItemAccess;
    }
        
    @GraphQLField
    @GraphQLDescription("entity list item")
    public EntityListItemObject getEntityListItem(final DataFetchingEnvironment env) {
        // TODO: return getHasEntityListItemAccess(env) ? new EntityListItemObject(entityMultipleListItemAttribute.getEntityListItem()) : null;
        return new EntityListItemObject(entityMultipleListItemAttribute.getEntityListItem());
    }
    
    @GraphQLField
    @GraphQLDescription("entity attribute")
    public EntityAttributeObject getEntityAttribute(final DataFetchingEnvironment env) {
        return getHasEntityAttributeAccess(env) ? new EntityAttributeObject(entityMultipleListItemAttribute.getEntityAttribute(), entityMultipleListItemAttribute.getEntityInstance()) : null;
    }
    
    @GraphQLField
    @GraphQLDescription("entity instance")
    public EntityInstanceObject getEntityInstance(final DataFetchingEnvironment env) {
        return new EntityInstanceObject(entityMultipleListItemAttribute.getEntityInstance());
    }
    
}
