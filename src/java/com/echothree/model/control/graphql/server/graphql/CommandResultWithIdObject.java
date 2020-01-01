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

package com.echothree.model.control.graphql.server.graphql;

import com.echothree.model.control.core.server.CoreControl;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;

@GraphQLDescription("command result with id object")
@GraphQLName("CommandResultWithId")
public class CommandResultWithIdObject
        extends CommandResultObject{
    
    private EntityInstance entityInstance; // Optional, use getEntityInstance()
    
    public void setEntityInstanceFromEntityRef(String entityRef) {
        var coreControl = (CoreControl)Session.getModelController(CoreControl.class);

        entityInstance = coreControl.getEntityInstanceByEntityRef(entityRef);
    }
    
    public void setEntityInstance(EntityInstance entityInstance) {
        this.entityInstance = entityInstance;
    }
    
    @GraphQLField
    @GraphQLDescription("id")
    public String getId() {
        String id = null;
        
        if(entityInstance != null) {
            var coreControl = (CoreControl)Session.getModelController(CoreControl.class);

            id = coreControl.generateUlidForEntityInstance(entityInstance, false);
        }
        
        return id;
    }
    
}
