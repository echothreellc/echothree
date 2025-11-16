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

package com.echothree.model.control.letter.server.transfer;

import com.echothree.model.control.chain.server.control.ChainControl;
import com.echothree.model.control.letter.common.transfer.QueuedLetterTransfer;
import com.echothree.model.control.letter.server.control.LetterControl;
import com.echothree.model.data.letter.server.entity.QueuedLetter;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class QueuedLetterTransferCache
        extends BaseLetterTransferCache<QueuedLetter, QueuedLetterTransfer> {
    
    ChainControl chainControl = Session.getModelController(ChainControl.class);
    LetterControl letterControl = Session.getModelController(LetterControl.class);

    /** Creates a new instance of QueuedLetterTransferCache */
    protected QueuedLetterTransferCache() {
        super();
    }
    
    public QueuedLetterTransfer getQueuedLetterTransfer(UserVisit userVisit, QueuedLetter queuedLetter) {
        var queuedLetterTransfer = get(queuedLetter);
        
        if(queuedLetterTransfer == null) {
            var chainInstance = chainControl.getChainInstanceTransfer(userVisit, queuedLetter.getChainInstance());
            var letter = letterControl.getLetterTransfer(userVisit, queuedLetter.getLetter());
            
            queuedLetterTransfer = new QueuedLetterTransfer(chainInstance, letter);
            put(userVisit, queuedLetter, queuedLetterTransfer);
        }
        
        return queuedLetterTransfer;
    }
    
}
