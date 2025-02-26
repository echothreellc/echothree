// --------------------------------------------------------------------------------
// Copyright 2002-2025 Echo Three, LLC
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
import com.echothree.model.control.graphql.server.graphql.VersionInterface;
import com.echothree.model.data.core.server.entity.EntityDateDefault;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;

@GraphQLDescription("entity date default history object")
@GraphQLName("EntityDateDefaultHistory")
public class EntityDateDefaultHistoryObject
        extends BaseEntityDateDefaultObject
        implements VersionInterface {

    public EntityDateDefaultHistoryObject(EntityDateDefault entityDateDefault) {
        super(entityDateDefault);
    }

    @Override
    @GraphQLField
    @GraphQLDescription("from time")
    @GraphQLNonNull
    public TimeObject getFromTime(final DataFetchingEnvironment env) {
        return new TimeObject(entityDateDefault.getFromTime());
    }

    @Override
    @GraphQLField
    @GraphQLDescription("thru time")
    @GraphQLNonNull
    public TimeObject getThruTime(final DataFetchingEnvironment env) {
        return new TimeObject(entityDateDefault.getThruTime());
    }

}
