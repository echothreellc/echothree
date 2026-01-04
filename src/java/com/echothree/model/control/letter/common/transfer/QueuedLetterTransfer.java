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

package com.echothree.model.control.letter.common.transfer;

import com.echothree.model.control.chain.common.transfer.ChainInstanceTransfer;
import com.echothree.util.common.transfer.BaseTransfer;

public class QueuedLetterTransfer
        extends BaseTransfer {
    
    private ChainInstanceTransfer chainInstance;
    private LetterTransfer letter;
    
    /** Creates a new instance of QueuedLetterTransfer */
    public QueuedLetterTransfer(ChainInstanceTransfer chainInstance, LetterTransfer letter) {
        this.chainInstance = chainInstance;
        this.letter = letter;
    }
    
    public ChainInstanceTransfer getChainInstance() {
        return chainInstance;
    }
    
    public void setChainInstance(ChainInstanceTransfer chainInstance) {
        this.chainInstance = chainInstance;
    }
    
    public LetterTransfer getLetter() {
        return letter;
    }
    
    public void setLetter(LetterTransfer letter) {
        this.letter = letter;
    }
    
}
