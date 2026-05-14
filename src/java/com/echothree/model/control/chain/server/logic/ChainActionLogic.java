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

package com.echothree.model.control.chain.server.logic;

import com.echothree.model.control.chain.common.exception.UnknownChainActionNameException;
import com.echothree.model.control.chain.server.control.ChainControl;
import com.echothree.model.data.chain.server.entity.ChainAction;
import com.echothree.model.data.chain.server.entity.ChainActionSet;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class ChainActionLogic
        extends BaseLogic {

    @Inject
    ChainControl chainControl;

    @Inject
    ChainActionSetLogic chainActionSetLogic;

    protected ChainActionLogic() {
        super();
    }

    public ChainAction getChainActionByName(final ExecutionErrorAccumulator eea, final ChainActionSet chainActionSet, final String chainActionName) {
        var chainAction = chainControl.getChainActionByName(chainActionSet, chainActionName);

        if(chainAction == null) {
            handleExecutionError(UnknownChainActionNameException.class, eea, ExecutionErrors.UnknownChainActionName.name(),
                    chainActionSet.getLastDetail().getChain().getLastDetail().getChainType().getLastDetail().getChainKind().getLastDetail().getChainKindName(),
                    chainActionSet.getLastDetail().getChain().getLastDetail().getChainType().getLastDetail().getChainTypeName(),
                    chainActionSet.getLastDetail().getChain().getLastDetail().getChainName(),
                    chainActionSet.getLastDetail().getChainActionSetName(),
                    chainActionName);
        }

        return chainAction;
    }

    public ChainAction getChainActionByName(final ExecutionErrorAccumulator eea, final String chainKindName,
            final String chainTypeName, final String chainName, final String chainActionSetName, final String chainActionName) {
        var chainActionSet = chainActionSetLogic.getChainActionSetByName(eea, chainKindName, chainTypeName, chainName, chainActionSetName);
        ChainAction chainAction = null;

        if(!hasExecutionErrors(eea)) {
            chainAction = getChainActionByName(eea, chainActionSet, chainActionName);
        }

        return chainAction;
    }

}
