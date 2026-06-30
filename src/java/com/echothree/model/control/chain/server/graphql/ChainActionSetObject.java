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

import com.echothree.model.control.chain.server.control.ChainControl;
import com.echothree.model.control.graphql.server.graphql.BaseEntityInstanceObject;
import com.echothree.model.control.graphql.server.graphql.count.Connections;
import com.echothree.model.control.graphql.server.graphql.count.CountedObjects;
import com.echothree.model.control.graphql.server.graphql.count.CountingDataConnectionFetcher;
import com.echothree.model.control.graphql.server.graphql.count.CountingPaginatedData;
import com.echothree.model.control.graphql.server.util.BaseGraphQl;
import com.echothree.model.control.graphql.server.util.count.ObjectLimiter;
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.data.chain.common.ChainConstants;
import com.echothree.model.data.chain.server.entity.ChainActionSet;
import com.echothree.model.data.chain.server.entity.ChainActionSetDetail;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.annotations.connection.GraphQLConnection;
import graphql.schema.DataFetchingEnvironment;
import java.util.ArrayList;
import java.util.stream.Collectors;

@GraphQLDescription("chain action set object")
@GraphQLName("ChainActionSet")
public class ChainActionSetObject
        extends BaseEntityInstanceObject {
    
    private final ChainActionSet chainActionSet; // Always Present
    
    public ChainActionSetObject(ChainActionSet chainActionSet) {
        super(chainActionSet.getPrimaryKey());
        
        this.chainActionSet = chainActionSet;
    }

    private ChainActionSetDetail chainActionSetDetail; // Optional, use getChainActionSetDetail()
    
    private ChainActionSetDetail getChainActionSetDetail() {
        if(chainActionSetDetail == null) {
            chainActionSetDetail = chainActionSet.getLastDetail();
        }
        
        return chainActionSetDetail;
    }

    @GraphQLField
    @GraphQLDescription("chain")
    public ChainObject getChain(final DataFetchingEnvironment env) {
        return ChainSecurityUtils.getHasChainAccess(env) ? new ChainObject(getChainActionSetDetail().getChain()) : null;
    }

    @GraphQLField
    @GraphQLDescription("chain action set name")
    @GraphQLNonNull
    public String getChainActionSetName() {
        return getChainActionSetDetail().getChainActionSetName();
    }
    
    @GraphQLField
    @GraphQLDescription("is default")
    @GraphQLNonNull
    public boolean getIsDefault() {
        return getChainActionSetDetail().getIsDefault();
    }
    
    @GraphQLField
    @GraphQLDescription("sort order")
    @GraphQLNonNull
    public int getSortOrder() {
        return getChainActionSetDetail().getSortOrder();
    }
    
    @GraphQLField
    @GraphQLDescription("description")
    @GraphQLNonNull
    public String getDescription(final DataFetchingEnvironment env) {
        var chainControl = Session.getModelController(ChainControl.class);
        var userControl = Session.getModelController(UserControl.class);

        return chainControl.getBestChainActionSetDescription(chainActionSet, userControl.getPreferredLanguageFromUserVisit(BaseGraphQl.getUserVisit(env)));
    }

}
