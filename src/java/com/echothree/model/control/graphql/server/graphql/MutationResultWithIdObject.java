// --------------------------------------------------------------------------------
// Copyright 2002-2024 Echo Three, LLC
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

import com.echothree.model.control.core.common.transfer.EntityInstanceTransfer;
import com.echothree.model.control.core.server.control.CoreControl;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;

@GraphQLDescription("mutation result with id object")
@GraphQLName("MutationResultWithIdO")
public class MutationResultWithIdObject
        extends MutationResultObject {
    
    private EntityInstance entityInstance; // Optional, use getEntityInstance()
    
    public void setEntityInstance(final EntityInstance entityInstance) {
        if(this.entityInstance == null) {
            this.entityInstance = entityInstance;
        } else {
            throw new RuntimeException("Cannot call setEntityInstance(EntityInstance) multiple times");
        }
    }

    public void setEntityInstanceFromEntityRef(final String entityRef) {
        var coreControl = Session.getModelController(CoreControl.class);

        setEntityInstance(coreControl.getEntityInstanceByEntityRef(entityRef));
    }

    public void setEntityInstance(final EntityInstanceTransfer entityInstanceTransfer) {
        setEntityInstanceFromEntityRef(entityInstanceTransfer.getEntityRef());
    }

    @GraphQLField
    @GraphQLDescription("id")
    public String getId() {
        String id = null;
        
        if(entityInstance != null) {
            var coreControl = Session.getModelController(CoreControl.class);

            entityInstance = coreControl.ensureUlidForEntityInstance(entityInstance, false);
            id = entityInstance.getUlid();
        }
        
        return id;
    }
    
}
