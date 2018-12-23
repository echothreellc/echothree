// --------------------------------------------------------------------------------
// Copyright 2002-2019 Echo Three, LLC
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

import com.echothree.model.control.chain.common.transfer.ChainActionChainActionSetTransfer;
import com.echothree.model.control.chain.common.transfer.ChainActionSetTransfer;
import com.echothree.model.control.chain.common.transfer.ChainActionTransfer;
import com.echothree.model.control.chain.server.ChainControl;
import com.echothree.model.control.uom.common.UomConstants;
import com.echothree.model.control.uom.server.UomControl;
import com.echothree.model.data.chain.server.entity.ChainActionChainActionSet;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureKind;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class ChainActionChainActionSetTransferCache
        extends BaseChainTransferCache<ChainActionChainActionSet, ChainActionChainActionSetTransfer> {
    
    UomControl uomControl = (UomControl)Session.getModelController(UomControl.class);
    
    /** Creates a new instance of ChainActionChainActionSetTransferCache */
    public ChainActionChainActionSetTransferCache(UserVisit userVisit, ChainControl chainControl) {
        super(userVisit, chainControl);
    }
    
    public ChainActionChainActionSetTransfer getChainActionChainActionSetTransfer(ChainActionChainActionSet chainActionChainActionSet) {
        ChainActionChainActionSetTransfer chainActionChainActionSetTransfer = get(chainActionChainActionSet);
        
        if(chainActionChainActionSetTransfer == null) {
            ChainActionTransfer chainAction = chainControl.getChainActionTransfer(userVisit, chainActionChainActionSet.getChainAction());
            ChainActionSetTransfer nextChainActionSet = chainControl.getChainActionSetTransfer(userVisit, chainActionChainActionSet.getNextChainActionSet());
            Long unformattedDelayTime = chainActionChainActionSet.getDelayTime();
            UnitOfMeasureKind timeUnitOfMeasureKind = uomControl.getUnitOfMeasureKindByUnitOfMeasureKindUseTypeUsingNames(UomConstants.UnitOfMeasureKindUseType_TIME);
            String delayTime = formatUnitOfMeasure(timeUnitOfMeasureKind, unformattedDelayTime);
            
            chainActionChainActionSetTransfer = new ChainActionChainActionSetTransfer(chainAction, nextChainActionSet, unformattedDelayTime, delayTime);
            put(chainActionChainActionSet, chainActionChainActionSetTransfer);
        }
        
        return chainActionChainActionSetTransfer;
    }
    
}
