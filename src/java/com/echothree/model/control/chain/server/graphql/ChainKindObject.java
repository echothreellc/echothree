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
import com.echothree.model.control.graphql.server.util.BaseGraphQl;
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.data.chain.server.entity.ChainKind;
import com.echothree.model.data.chain.server.entity.ChainKindDetail;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;

@GraphQLDescription("chain kind object")
@GraphQLName("ChainKind")
public class ChainKindObject
        extends BaseEntityInstanceObject {
    
    private final ChainKind chainKind; // Always Present
    
    public ChainKindObject(ChainKind chainKind) {
        super(chainKind.getPrimaryKey());
        
        this.chainKind = chainKind;
    }

    private ChainKindDetail chainKindDetail; // Optional, use getChainKindDetail()
    
    private ChainKindDetail getChainKindDetail() {
        if(chainKindDetail == null) {
            chainKindDetail = chainKind.getLastDetail();
        }
        
        return chainKindDetail;
    }
    
    @GraphQLField
    @GraphQLDescription("chain kind name")
    @GraphQLNonNull
    public String getChainKindName() {
        return getChainKindDetail().getChainKindName();
    }
    
    @GraphQLField
    @GraphQLDescription("is default")
    @GraphQLNonNull
    public boolean getIsDefault() {
        return getChainKindDetail().getIsDefault();
    }
    
    @GraphQLField
    @GraphQLDescription("sort order")
    @GraphQLNonNull
    public int getSortOrder() {
        return getChainKindDetail().getSortOrder();
    }
    
    @GraphQLField
    @GraphQLDescription("description")
    @GraphQLNonNull
    public String getDescription(final DataFetchingEnvironment env) {
        var chainControl = Session.getModelController(ChainControl.class);
        var userControl = Session.getModelController(UserControl.class);

        return chainControl.getBestChainKindDescription(chainKind, userControl.getPreferredLanguageFromUserVisit(BaseGraphQl.getUserVisit(env)));
    }

//    @GraphQLField
//    @GraphQLDescription("chain types")
//    @GraphQLNonNull
//    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
//    public CountingPaginatedData<ChainTypeObject> getChainTypes(final DataFetchingEnvironment env) {
//        if(ChainSecurityUtils.getHasChainTypesAccess(env)) {
//            var chainControl = Session.getModelController(ChainControl.class);
//            var totalCount = chainControl.countChainTypesByChainKind(chainKind);
//
//            try(var objectLimiter = new ObjectLimiter(env, ChainTypeConstants.COMPONENT_VENDOR_NAME, ChainTypeConstants.ENTITY_TYPE_NAME, totalCount)) {
//                var entities = chainControl.getChainTypes(chainKind);
//                var chainTypes = entities.stream().map(ChainTypeObject::new).collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));
//
//                return new CountedObjects<>(objectLimiter, chainTypes);
//            }
//        } else {
//            return Connections.emptyConnection();
//        }
//    }
    
}
