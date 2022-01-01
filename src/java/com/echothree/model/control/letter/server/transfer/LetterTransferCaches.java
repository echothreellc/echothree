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

package com.echothree.model.control.letter.server.transfer;

import com.echothree.model.control.letter.server.control.LetterControl;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.transfer.BaseTransferCaches;

public class LetterTransferCaches
        extends BaseTransferCaches {
    
    protected LetterControl letterControl;
    
    protected LetterTransferCache letterTransferCache;
    protected LetterDescriptionTransferCache letterDescriptionTransferCache;
    protected LetterSourceTransferCache letterSourceTransferCache;
    protected LetterSourceDescriptionTransferCache letterSourceDescriptionTransferCache;
    protected QueuedLetterTransferCache queuedLetterTransferCache;
    protected LetterContactMechanismPurposeTransferCache letterContactMechanismPurposeTransferCache;
    
    /** Creates a new instance of LetterTransferCaches */
    public LetterTransferCaches(UserVisit userVisit, LetterControl letterControl) {
        super(userVisit);
        
        this.letterControl = letterControl;
    }
    
    public LetterTransferCache getLetterTransferCache() {
        if(letterTransferCache == null)
            letterTransferCache = new LetterTransferCache(userVisit, letterControl);
        
        return letterTransferCache;
    }
    
    public LetterDescriptionTransferCache getLetterDescriptionTransferCache() {
        if(letterDescriptionTransferCache == null)
            letterDescriptionTransferCache = new LetterDescriptionTransferCache(userVisit, letterControl);
        
        return letterDescriptionTransferCache;
    }
    
    public LetterSourceTransferCache getLetterSourceTransferCache() {
        if(letterSourceTransferCache == null)
            letterSourceTransferCache = new LetterSourceTransferCache(userVisit, letterControl);
        
        return letterSourceTransferCache;
    }
    
    public LetterSourceDescriptionTransferCache getLetterSourceDescriptionTransferCache() {
        if(letterSourceDescriptionTransferCache == null)
            letterSourceDescriptionTransferCache = new LetterSourceDescriptionTransferCache(userVisit, letterControl);
        
        return letterSourceDescriptionTransferCache;
    }
    
    public QueuedLetterTransferCache getQueuedLetterTransferCache() {
        if(queuedLetterTransferCache == null)
            queuedLetterTransferCache = new QueuedLetterTransferCache(userVisit, letterControl);
        
        return queuedLetterTransferCache;
    }
    
    public LetterContactMechanismPurposeTransferCache getLetterContactMechanismPurposeTransferCache() {
        if(letterContactMechanismPurposeTransferCache == null)
            letterContactMechanismPurposeTransferCache = new LetterContactMechanismPurposeTransferCache(userVisit, letterControl);
        
        return letterContactMechanismPurposeTransferCache;
    }
    
}
