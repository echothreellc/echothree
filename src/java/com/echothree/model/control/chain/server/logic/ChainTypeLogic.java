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

import com.echothree.model.control.chain.common.exception.UnknownChainTypeNameException;
import com.echothree.model.control.chain.server.control.ChainControl;
import com.echothree.model.data.chain.server.entity.ChainKind;
import com.echothree.model.data.chain.server.entity.ChainType;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class ChainTypeLogic
        extends BaseLogic {

    @Inject
    ChainControl chainControl;

    @Inject
    ChainKindLogic chainKindLogic;

    protected ChainTypeLogic() {
        super();
    }

    private static class ChainTypeLogicHolder {
        static ChainTypeLogic instance = new ChainTypeLogic();
    }

    public static ChainTypeLogic getInstance() {
        return ChainTypeLogicHolder.instance;
    }

    public ChainType getChainTypeByName(final ExecutionErrorAccumulator eea, final ChainKind chainKind, final String chainTypeName) {
        var chainType = chainControl.getChainTypeByName(chainKind, chainTypeName);

        if(chainType == null) {
            handleExecutionError(UnknownChainTypeNameException.class, eea, ExecutionErrors.UnknownChainTypeName.name(), chainKind.getLastDetail().getChainKindName(),
                    chainTypeName);
        }

        return chainType;
    }

    public ChainType getChainTypeByName(final ExecutionErrorAccumulator eea, final String chainKindName, final String chainTypeName) {
        var chainKind = chainKindLogic.getChainKindByName(eea, chainKindName);
        ChainType chainType = null;

        if(chainKind != null) {
            chainType = getChainTypeByName(eea, chainKind, chainTypeName);
        }

        return chainType;
    }

}
