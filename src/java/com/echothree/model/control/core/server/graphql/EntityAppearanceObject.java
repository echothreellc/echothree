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

import com.echothree.control.user.core.server.command.GetAppearanceCommand;
import com.echothree.control.user.core.server.command.GetEntityInstanceCommand;
import com.echothree.model.control.graphql.server.util.BaseGraphQl;
import com.echothree.model.data.core.server.entity.EntityAppearance;
import com.echothree.util.server.control.BaseSingleEntityCommand;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;

@GraphQLDescription("entity appearance object")
@GraphQLName("EntityAppearance")
public class EntityAppearanceObject
        extends BaseGraphQl {
    
    private final EntityAppearance entityAppearance; // Always Present
    
    public EntityAppearanceObject(EntityAppearance entityAppearance) {
        this.entityAppearance = entityAppearance;
    }

    private Boolean hasEntityInstanceAccess;

    private boolean getHasEntityInstanceAccess(final DataFetchingEnvironment env) {
        if(hasEntityInstanceAccess == null) {
            var baseSingleEntityCommand = new GetEntityInstanceCommand(getUserVisitPK(env), null);

            baseSingleEntityCommand.security();

            hasEntityInstanceAccess = !baseSingleEntityCommand.hasSecurityMessages();
        }

        return hasEntityInstanceAccess;
    }

    private Boolean hasAppearanceAccess;

    private boolean getHasAppearanceAccess(final DataFetchingEnvironment env) {
        if(hasAppearanceAccess == null) {
            var baseSingleEntityCommand = new GetAppearanceCommand(getUserVisitPK(env), null);

            baseSingleEntityCommand.security();

            hasAppearanceAccess = !baseSingleEntityCommand.hasSecurityMessages();
        }

        return hasAppearanceAccess;
    }

    @GraphQLField
    @GraphQLDescription("entity instance")
    @GraphQLNonNull
    public EntityInstanceObject getEntityInstance(final DataFetchingEnvironment env) {
        return getHasEntityInstanceAccess(env) ? new EntityInstanceObject(entityAppearance.getEntityInstance()) : null;
    }
    
    @GraphQLField
    @GraphQLDescription("appearance")
    @GraphQLNonNull
    public AppearanceObject getAppearance(final DataFetchingEnvironment env) {
        return getHasAppearanceAccess(env) ? new AppearanceObject(entityAppearance.getAppearance()) : null;
    }
    
}
