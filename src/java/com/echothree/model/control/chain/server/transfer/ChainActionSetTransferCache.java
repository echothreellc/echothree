// --------------------------------------------------------------------------------
// Copyright 2002-2022 Echo Three, LLC
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

package com.echothree.model.control.chain.server.transfer;

import com.echothree.model.control.chain.common.transfer.ChainActionSetTransfer;
import com.echothree.model.control.chain.common.transfer.ChainTransfer;
import com.echothree.model.control.chain.server.control.ChainControl;
import com.echothree.model.data.chain.server.entity.ChainActionSet;
import com.echothree.model.data.chain.server.entity.ChainActionSetDetail;
import com.echothree.model.data.user.server.entity.UserVisit;

public class ChainActionSetTransferCache
        extends BaseChainTransferCache<ChainActionSet, ChainActionSetTransfer> {

    /** Creates a new instance of ChainActionSetTransferCache */
    public ChainActionSetTransferCache(UserVisit userVisit, ChainControl chainControl) {
        super(userVisit, chainControl);
        
        setIncludeEntityInstance(true);
    }

    public ChainActionSetTransfer getChainActionSetTransfer(ChainActionSet chainActionSet) {
        ChainActionSetTransfer chainActionSetTransfer = get(chainActionSet);

        if(chainActionSetTransfer == null) {
            ChainActionSetDetail chainActionSetDetail = chainActionSet.getLastDetail();
            ChainTransfer chainTransfer = chainControl.getChainTransfer(userVisit, chainActionSetDetail.getChain());
            String chainActionSetName = chainActionSetDetail.getChainActionSetName();
            Boolean isDefault = chainActionSetDetail.getIsDefault();
            Integer sortOrder = chainActionSetDetail.getSortOrder();
            String description = chainControl.getBestChainActionSetDescription(chainActionSet, getLanguage());

            chainActionSetTransfer = new ChainActionSetTransfer(chainTransfer, chainActionSetName, isDefault, sortOrder, description);
            put(chainActionSet, chainActionSetTransfer);
        }

        return chainActionSetTransfer;
    }

}
