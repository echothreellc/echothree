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
import com.echothree.model.control.graphql.server.graphql.TimeObject;
import com.echothree.model.data.chain.server.entity.ChainInstance;
import com.echothree.model.data.chain.server.entity.ChainInstanceDetail;
import com.echothree.model.data.chain.server.entity.ChainInstanceStatus;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;

@GraphQLDescription("chain instance object")
@GraphQLName("ChainInstance")
public class ChainInstanceObject
        extends BaseEntityInstanceObject {
    
    private final ChainInstance chainInstance; // Always Present
    
    public ChainInstanceObject(ChainInstance chainInstance) {
        super(chainInstance.getPrimaryKey());
        
        this.chainInstance = chainInstance;
    }

    private ChainInstanceDetail chainInstanceDetail; // Optional, use getChainInstanceDetail()
    
    private ChainInstanceDetail getChainInstanceDetail() {
        if(chainInstanceDetail == null) {
            chainInstanceDetail = chainInstance.getLastDetail();
        }
        
        return chainInstanceDetail;
    }
    
    private ChainInstanceStatus chainInstanceStatus; // Optional, use getChainInstanceStatus()
    private boolean chainInstanceStatusLoaded = false;

    private ChainInstanceStatus getChainInstanceStatus() {
        if(!chainInstanceStatusLoaded) {
            var chainControl = Session.getModelController(ChainControl.class);
            
            chainInstanceStatus = chainControl.getChainInstanceStatus(chainInstance);  // May be null
            chainInstanceStatusLoaded = true;
        }

        return chainInstanceStatus;
    }

    @GraphQLField
    @GraphQLDescription("chain instance name")
    @GraphQLNonNull
    public String getChainInstanceName() {
        return getChainInstanceDetail().getChainInstanceName();
    }

    @GraphQLField
    @GraphQLDescription("chain")
    public ChainObject getChain(final DataFetchingEnvironment env) {
        return ChainSecurityUtils.getHasChainAccess(env) ? new ChainObject(getChainInstanceDetail().getChain()) : null;
    }

    @GraphQLField
    @GraphQLDescription("next chain action set")
    public ChainActionSetObject getNextChainActionSet(final DataFetchingEnvironment env) {
        var chainInstanceStatus = getChainInstanceStatus();
        ChainActionSetObject nextChainActionSet = null;

        if(chainInstanceStatus != null) {
            var chainActionSet = chainInstanceStatus.getNextChainActionSet();

            if(chainActionSet != null) {
                nextChainActionSet = ChainSecurityUtils.getHasChainActionSetAccess(env) ? new ChainActionSetObject(chainActionSet) : null;
            }
        }

        return nextChainActionSet;
    }

    @GraphQLField
    @GraphQLDescription("next chain action set time")
    public TimeObject getNextChainActionSetTime() {
        var chainInstanceStatus = getChainInstanceStatus();
        TimeObject nextChainActionSetTime = null;

        if(chainInstanceStatus != null) {
            var time = chainInstanceStatus.getNextChainActionSetTime();

            if(time != null) {
                nextChainActionSetTime = new TimeObject(time);
            }
        }

        return nextChainActionSetTime;
    }

}
