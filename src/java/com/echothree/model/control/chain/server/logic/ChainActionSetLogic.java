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

import com.echothree.model.control.chain.common.exception.UnknownChainActionSetNameException;
import com.echothree.model.control.chain.server.control.ChainControl;
import com.echothree.model.data.chain.server.entity.Chain;
import com.echothree.model.data.chain.server.entity.ChainActionSet;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class ChainActionSetLogic
        extends BaseLogic {

    @Inject
    ChainControl chainControl;

    @Inject
    ChainLogic chainLogic;

    protected ChainActionSetLogic() {
        super();
    }

    public ChainActionSet getChainActionSetByName(final ExecutionErrorAccumulator eea, final Chain chain, final String chainActionSetName) {
        var chainActionSet = chainControl.getChainActionSetByName(chain, chainActionSetName);

        if(chainActionSet == null) {
            handleExecutionError(UnknownChainActionSetNameException.class, eea, ExecutionErrors.UnknownChainActionSetName.name(),
                    chain.getLastDetail().getChainType().getLastDetail().getChainKind().getLastDetail().getChainKindName(),
                    chain.getLastDetail().getChainType().getLastDetail().getChainTypeName(),
                    chain.getLastDetail().getChainName(),
                    chainActionSetName);
        }

        return chainActionSet;
    }

    public ChainActionSet getChainActionSetByName(final ExecutionErrorAccumulator eea, final String chainKindName,
            final String chainTypeName, final String chainName, final String chainActionSetName) {
        var chain = chainLogic.getChainByName(eea, chainKindName, chainTypeName, chainName);
        ChainActionSet chainActionSet = null;

        if(!hasExecutionErrors(eea)) {
            chainActionSet = getChainActionSetByName(eea, chain, chainActionSetName);
        }

        return chainActionSet;
    }

}
