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

package com.echothree.model.control.chain.server.graphql;

import com.echothree.model.control.graphql.server.graphql.BaseObject;
import com.echothree.model.control.graphql.server.graphql.UnitOfMeasureKindUseTypeTimeObject;
import com.echothree.model.data.chain.server.entity.ChainActionChainActionSet;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;

@GraphQLDescription("chain action chain action set object")
@GraphQLName("ChainActionChainActionSet")
public class ChainActionChainActionSetObject
        extends BaseObject
        implements ChainActionInterface {

    private final ChainActionChainActionSet chainActionChainActionSet; // Always Present

    public ChainActionChainActionSetObject(ChainActionChainActionSet chainActionChainActionSet) {
        this.chainActionChainActionSet = chainActionChainActionSet;
    }

    @GraphQLField
    @GraphQLDescription("chain action")
    @GraphQLNonNull
    public ChainActionObject getChainAction(final DataFetchingEnvironment env) {
        return ChainSecurityUtils.getHasChainActionAccess(env) ? new ChainActionObject(chainActionChainActionSet.getChainAction()) : null;
    }

    @GraphQLField
    @GraphQLDescription("next chain action set")
    @GraphQLNonNull
    public ChainActionSetObject getNextChainActionSet(final DataFetchingEnvironment env) {
        return ChainSecurityUtils.getHasChainActionSetAccess(env) ? new ChainActionSetObject(chainActionChainActionSet.getNextChainActionSet()) : null;
    }

    @GraphQLField
    @GraphQLDescription("delay time")
    @GraphQLNonNull
    public UnitOfMeasureKindUseTypeTimeObject getDelayTime(final DataFetchingEnvironment env) {
        return new UnitOfMeasureKindUseTypeTimeObject(chainActionChainActionSet.getDelayTime());
    }

}
