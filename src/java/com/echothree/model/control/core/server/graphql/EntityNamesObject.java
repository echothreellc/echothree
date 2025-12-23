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

import com.echothree.model.control.graphql.server.util.BaseGraphQl;
import com.echothree.util.common.persistence.EntityNames;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import java.util.ArrayList;
import java.util.List;

@GraphQLDescription("entity names object")
@GraphQLName("EntityNames")
public class EntityNamesObject
        implements BaseGraphQl {

    private final EntityNames entityNames; // Always Present

    public EntityNamesObject(EntityNames entityNames) {
        this.entityNames = entityNames;
    }

    @GraphQLField
    @GraphQLDescription("target")
    @GraphQLNonNull
    public String getTarget() {
        return entityNames.getTarget();
    }

    @GraphQLField
    @GraphQLDescription("names")
    @GraphQLNonNull
    public List<EntityNameObject> getNames() {
        var namesMapWrapper = entityNames.getNames();
        var names = new ArrayList<EntityNameObject>(namesMapWrapper.getSize());

        for(var entry : namesMapWrapper.getMap().entrySet()) {
            names.add(new EntityNameObject(entry.getKey(), entry.getValue()));
        }

        return names;
    }

}
