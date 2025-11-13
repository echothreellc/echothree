// --------------------------------------------------------------------------------
// Copyright 2002-2025 Echo Three, LLC
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

import com.echothree.model.control.chain.common.transfer.ChainInstanceStatusTransfer;
import com.echothree.model.control.chain.server.control.ChainControl;
import com.echothree.model.control.core.server.control.CoreControl;
import com.echothree.model.data.chain.server.entity.ChainInstanceStatus;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class ChainInstanceStatusTransferCache
        extends BaseChainTransferCache<ChainInstanceStatus, ChainInstanceStatusTransfer> {
    
    CoreControl coreControl = Session.getModelController(CoreControl.class);
    
    /** Creates a new instance of ChainInstanceStatusTransferCache */
    public ChainInstanceStatusTransferCache(ChainControl chainControl) {
        super(chainControl);
    }
    
    public ChainInstanceStatusTransfer getChainInstanceStatusTransfer(UserVisit userVisit, ChainInstanceStatus chainInstanceStatus) {
        var chainInstanceStatusTransfer = get(chainInstanceStatus);
        
        if(chainInstanceStatusTransfer == null) {
            var chainInstance = chainControl.getChainInstanceTransfer(userVisit, chainInstanceStatus.getChainInstance());
            var nextChainActionSet = chainControl.getChainActionSetTransfer(userVisit, chainInstanceStatus.getNextChainActionSet());
            var unformattedNextChainActionSetTime = chainInstanceStatus.getNextChainActionSetTime();
            var nextChainActionSetTime = formatTypicalDateTime(userVisit, unformattedNextChainActionSetTime);
            var queuedLetterSequence = chainInstanceStatus.getQueuedLetterSequence();
            
            chainInstanceStatusTransfer = new ChainInstanceStatusTransfer(chainInstance, nextChainActionSet, unformattedNextChainActionSetTime,
                    nextChainActionSetTime, queuedLetterSequence);
            put(userVisit, chainInstanceStatus, chainInstanceStatusTransfer);
        }
        
        return chainInstanceStatusTransfer;
    }
    
}
