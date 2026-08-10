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

import com.echothree.model.control.chain.common.ChainActionTypes;
import com.echothree.model.control.chain.server.control.ChainControl;
import com.echothree.model.control.graphql.server.graphql.BaseEntityInstanceObject;
import com.echothree.model.control.graphql.server.util.BaseGraphQl;
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.data.chain.server.entity.ChainAction;
import com.echothree.model.data.chain.server.entity.ChainActionDetail;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;

@GraphQLDescription("chain action object")
@GraphQLName("ChainAction")
public class ChainActionObject
        extends BaseEntityInstanceObject {
    
    private final ChainAction chainAction; // Always Present
    
    public ChainActionObject(ChainAction chainAction) {
        super(chainAction.getPrimaryKey());
        
        this.chainAction = chainAction;
    }

    private ChainActionDetail chainActionDetail; // Optional, use getChainActionDetail()
    
    private ChainActionDetail getChainActionDetail() {
        if(chainActionDetail == null) {
            chainActionDetail = chainAction.getLastDetail();
        }
        
        return chainActionDetail;
    }

    @GraphQLField
    @GraphQLDescription("chain action set")
    public ChainActionSetObject getChainActionSet(final DataFetchingEnvironment env) {
        return ChainSecurityUtils.getHasChainActionSetAccess(env) ? new ChainActionSetObject(getChainActionDetail().getChainActionSet()) : null;
    }

    @GraphQLField
    @GraphQLDescription("chain action name")
    @GraphQLNonNull
    public String getChainActionName() {
        return getChainActionDetail().getChainActionName();
    }

    @GraphQLField
    @GraphQLDescription("chain action type")
    public ChainActionTypeObject getChainActionType(final DataFetchingEnvironment env) {
        return ChainSecurityUtils.getHasChainActionTypeAccess(env) ? new ChainActionTypeObject(getChainActionDetail().getChainActionType()) : null;
    }

    @GraphQLField
    @GraphQLDescription("sort order")
    @GraphQLNonNull
    public int getSortOrder() {
        return getChainActionDetail().getSortOrder();
    }
    
    @GraphQLField
    @GraphQLDescription("description")
    @GraphQLNonNull
    public String getDescription(final DataFetchingEnvironment env) {
        var chainControl = Session.getModelController(ChainControl.class);
        var userControl = Session.getModelController(UserControl.class);

        return chainControl.getBestChainActionDescription(chainAction, userControl.getPreferredLanguageFromUserVisit(BaseGraphQl.getUserVisit(env)));
    }

    private ChainActionTypes chainActionTypeEnum = null; // Optional, use getChainActionTypeEnum()

    protected ChainActionTypes getChainActionTypeEnum() {
        if(chainActionTypeEnum == null) {
            chainActionTypeEnum = ChainActionTypes.valueOf(getChainActionDetail().getChainActionType().getLastDetail().getChainActionTypeName());
        }

        return chainActionTypeEnum;
    }

    @GraphQLField
    @GraphQLDescription("chain action")
    public ChainActionInterface getChainAction(final DataFetchingEnvironment env) {
        var chainControl = Session.getModelController(ChainControl.class);

        return switch(getChainActionTypeEnum()) {
            case LETTER -> null;
            case SURVEY -> null;
            case CHAIN_ACTION_SET -> new ChainActionChainActionSetObject(chainControl.getChainActionChainActionSet(chainAction));
        };
    }
    
}
