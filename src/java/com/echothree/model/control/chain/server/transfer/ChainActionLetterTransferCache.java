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

import com.echothree.model.control.chain.common.transfer.ChainActionLetterTransfer;
import com.echothree.model.control.chain.server.control.ChainControl;
import com.echothree.model.control.letter.server.control.LetterControl;
import com.echothree.model.data.chain.server.entity.ChainActionLetter;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class ChainActionLetterTransferCache
        extends BaseChainTransferCache<ChainActionLetter, ChainActionLetterTransfer> {
    
    LetterControl letterControl = Session.getModelController(LetterControl.class);
    
    /** Creates a new instance of ChainActionLetterTransferCache */
    public ChainActionLetterTransferCache(ChainControl chainControl) {
        super(chainControl);
    }
    
    public ChainActionLetterTransfer getChainActionLetterTransfer(ChainActionLetter chainActionLetter) {
        var chainActionLetterTransfer = get(chainActionLetter);
        
        if(chainActionLetterTransfer == null) {
            var chainAction = chainControl.getChainActionTransfer(userVisit, chainActionLetter.getChainAction());
            var letter = letterControl.getLetterTransfer(userVisit, chainActionLetter.getLetter());
            
            chainActionLetterTransfer = new ChainActionLetterTransfer(chainAction, letter);
            put(userVisit, chainActionLetter, chainActionLetterTransfer);
        }
        
        return chainActionLetterTransfer;
    }
    
}
