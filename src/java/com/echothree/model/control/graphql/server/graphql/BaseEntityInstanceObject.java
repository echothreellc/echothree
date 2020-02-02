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

import com.echothree.control.user.core.server.command.GetEntityInstanceCommand;
import com.echothree.model.control.core.server.CoreControl;
import com.echothree.model.control.core.server.graphql.EntityInstanceObject;
import com.echothree.model.control.graphql.server.util.GraphQlContext;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;

public abstract class BaseEntityInstanceObject {
    
    private final BasePK basePrimaryKey;
    
    protected BaseEntityInstanceObject(BasePK basePrimaryKey) {
        this.basePrimaryKey = basePrimaryKey;
    }
    
    private EntityInstance entityInstance; // Optional, use getEntityInstance()
    
    protected EntityInstance getEntityInstanceByBasePK() {
        if(entityInstance == null) {
            var coreControl = (CoreControl)Session.getModelController(CoreControl.class);

            entityInstance = coreControl.getEntityInstanceByBasePK(basePrimaryKey);
        }
        
        return entityInstance;
    }

    private Boolean hasEntityInstanceAccess;

    private boolean getHasEntityInstanceAccess(final DataFetchingEnvironment env) {
        if(hasEntityInstanceAccess == null) {
            GraphQlContext context = env.getContext();
            var baseSingleEntityCommand = new GetEntityInstanceCommand(context.getUserVisitPK(), null);

            baseSingleEntityCommand.security();

            hasEntityInstanceAccess = !baseSingleEntityCommand.hasSecurityMessages();
        }

        return hasEntityInstanceAccess;
    }

    @GraphQLField
    @GraphQLDescription("id")
    @GraphQLNonNull
    public String getId() {
        var coreControl = (CoreControl)Session.getModelController(CoreControl.class);

        return coreControl.generateUlidForEntityInstance(getEntityInstanceByBasePK(), false);
    }
    
    @GraphQLField
    @GraphQLDescription("entity instance")
    @GraphQLNonNull
    public EntityInstanceObject getEntityInstance(final DataFetchingEnvironment env) {
        if(getHasEntityInstanceAccess(env)) {
            return new EntityInstanceObject(getEntityInstanceByBasePK());
        } else {
            return null;
        }
    }

}
