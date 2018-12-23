// --------------------------------------------------------------------------------
// Copyright 2002-2019 Echo Three, LLC
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
import com.echothree.model.control.core.server.graphql.EntityInstanceObject;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLNonNull;

public class BaseEntityInstanceObject {
    
    private final BasePK basePrimaryKey;
    
    protected BaseEntityInstanceObject(BasePK basePrimaryKey) {
        this.basePrimaryKey = basePrimaryKey;
    }
    
    private EntityInstance entityInstance; // Optional, use getEntityInstance()
    
    protected EntityInstance getEntityInstanceByBasePK() {
        if(entityInstance == null) {
            CoreControl coreControl = (CoreControl)Session.getModelController(CoreControl.class);

            entityInstance = coreControl.getEntityInstanceByBasePK(basePrimaryKey);
        }
        
        return entityInstance;
    }
    
    @GraphQLField
    @GraphQLDescription("id")
    @GraphQLNonNull
    public String getId() {
        CoreControl coreControl = (CoreControl)Session.getModelController(CoreControl.class);

        return coreControl.generateUlidForEntityInstance(getEntityInstanceByBasePK(), false);
    }
    
    @GraphQLField
    @GraphQLDescription("entity instance")
    @GraphQLNonNull
    public EntityInstanceObject getEntityInstance() {
        return new EntityInstanceObject(getEntityInstanceByBasePK());
    }

}
