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
import com.echothree.model.control.sequence.server.graphql.SequenceObject;
import com.echothree.model.control.sequence.server.graphql.SequenceSecurityUtils;
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.data.chain.server.entity.Chain;
import com.echothree.model.data.chain.server.entity.ChainDetail;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;

@GraphQLDescription("chain object")
@GraphQLName("Chain")
public class ChainObject
        extends BaseEntityInstanceObject {
    
    private final Chain chain; // Always Present
    
    public ChainObject(Chain chain) {
        super(chain.getPrimaryKey());
        
        this.chain = chain;
    }

    private ChainDetail chainDetail; // Optional, use getChainDetail()
    
    private ChainDetail getChainDetail() {
        if(chainDetail == null) {
            chainDetail = chain.getLastDetail();
        }
        
        return chainDetail;
    }

    @GraphQLField
    @GraphQLDescription("chain type")
    public ChainTypeObject getChainType(final DataFetchingEnvironment env) {
        return ChainSecurityUtils.getHasChainTypeAccess(env) ? new ChainTypeObject(getChainDetail().getChainType()) : null;
    }

    @GraphQLField
    @GraphQLDescription("chain name")
    @GraphQLNonNull
    public String getChainName() {
        return getChainDetail().getChainName();
    }

    @GraphQLField
    @GraphQLDescription("chain instance sequence")
    public SequenceObject getChainItemSequence(final DataFetchingEnvironment env) {
        var sequence = getChainDetail().getChainInstanceSequence();

        return sequence == null ? null : SequenceSecurityUtils.getHasSequenceAccess(env) ? new SequenceObject(sequence): null;
    }

    @GraphQLField
    @GraphQLDescription("is default")
    @GraphQLNonNull
    public boolean getIsDefault() {
        return getChainDetail().getIsDefault();
    }
    
    @GraphQLField
    @GraphQLDescription("sort order")
    @GraphQLNonNull
    public int getSortOrder() {
        return getChainDetail().getSortOrder();
    }
    
    @GraphQLField
    @GraphQLDescription("description")
    @GraphQLNonNull
    public String getDescription(final DataFetchingEnvironment env) {
        var chainControl = Session.getModelController(ChainControl.class);
        var userControl = Session.getModelController(UserControl.class);

        return chainControl.getBestChainDescription(chain, userControl.getPreferredLanguageFromUserVisit(BaseGraphQl.getUserVisit(env)));
    }

}
