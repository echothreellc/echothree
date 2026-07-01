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
import com.echothree.model.data.chain.server.entity.ChainType;
import com.echothree.model.data.chain.server.entity.ChainTypeDetail;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.annotations.connection.GraphQLConnection;
import graphql.schema.DataFetchingEnvironment;
import java.util.ArrayList;
import java.util.stream.Collectors;

@GraphQLDescription("chain type object")
@GraphQLName("ChainType")
public class ChainTypeObject
        extends BaseEntityInstanceObject {
    
    private final ChainType chainType; // Always Present
    
    public ChainTypeObject(ChainType chainType) {
        super(chainType.getPrimaryKey());
        
        this.chainType = chainType;
    }

    private ChainTypeDetail chainTypeDetail; // Optional, use getChainTypeDetail()
    
    private ChainTypeDetail getChainTypeDetail() {
        if(chainTypeDetail == null) {
            chainTypeDetail = chainType.getLastDetail();
        }
        
        return chainTypeDetail;
    }

    @GraphQLField
    @GraphQLDescription("chain kind")
    public ChainKindObject getChainKind(final DataFetchingEnvironment env) {
        return ChainSecurityUtils.getHasChainKindAccess(env) ? new ChainKindObject(getChainTypeDetail().getChainKind()) : null;
    }

    @GraphQLField
    @GraphQLDescription("chain type name")
    @GraphQLNonNull
    public String getChainTypeName() {
        return getChainTypeDetail().getChainTypeName();
    }
    
    @GraphQLField
    @GraphQLDescription("is default")
    @GraphQLNonNull
    public boolean getIsDefault() {
        return getChainTypeDetail().getIsDefault();
    }
    
    @GraphQLField
    @GraphQLDescription("sort order")
    @GraphQLNonNull
    public int getSortOrder() {
        return getChainTypeDetail().getSortOrder();
    }
    
    @GraphQLField
    @GraphQLDescription("description")
    @GraphQLNonNull
    public String getDescription(final DataFetchingEnvironment env) {
        var chainControl = Session.getModelController(ChainControl.class);
        var userControl = Session.getModelController(UserControl.class);

        return chainControl.getBestChainTypeDescription(chainType, userControl.getPreferredLanguageFromUserVisit(BaseGraphQl.getUserVisit(env)));
    }

    @GraphQLField
    @GraphQLDescription("chains")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    public CountingPaginatedData<ChainObject> getChains(final DataFetchingEnvironment env) {
        if(ChainSecurityUtils.getHasChainsAccess(env)) {
            var chainControl = Session.getModelController(ChainControl.class);
            var totalCount = chainControl.countChainsByChainType(chainType);

            try(var objectLimiter = new ObjectLimiter(env, ChainConstants.COMPONENT_VENDOR_NAME, ChainConstants.ENTITY_TYPE_NAME, totalCount)) {
                var entities = chainControl.getChainsByChainType(chainType);
                var chains = entities.stream().map(ChainObject::new).collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                return new CountedObjects<>(objectLimiter, chains);
            }
        } else {
            return Connections.emptyConnection();
        }
    }

}
