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

import com.echothree.util.server.transfer.BaseTransferCaches;
import javax.enterprise.inject.spi.CDI;

public class LetterTransferCaches
        extends BaseTransferCaches {
    
    protected LetterTransferCache letterTransferCache;
    protected LetterDescriptionTransferCache letterDescriptionTransferCache;
    protected LetterSourceTransferCache letterSourceTransferCache;
    protected LetterSourceDescriptionTransferCache letterSourceDescriptionTransferCache;
    protected QueuedLetterTransferCache queuedLetterTransferCache;
    protected LetterContactMechanismPurposeTransferCache letterContactMechanismPurposeTransferCache;
    
    /** Creates a new instance of LetterTransferCaches */
    public LetterTransferCaches() {
        super();
    }
    
    public LetterTransferCache getLetterTransferCache() {
        if(letterTransferCache == null)
            letterTransferCache = CDI.current().select(LetterTransferCache.class).get();
        
        return letterTransferCache;
    }
    
    public LetterDescriptionTransferCache getLetterDescriptionTransferCache() {
        if(letterDescriptionTransferCache == null)
            letterDescriptionTransferCache = CDI.current().select(LetterDescriptionTransferCache.class).get();
        
        return letterDescriptionTransferCache;
    }
    
    public LetterSourceTransferCache getLetterSourceTransferCache() {
        if(letterSourceTransferCache == null)
            letterSourceTransferCache = CDI.current().select(LetterSourceTransferCache.class).get();
        
        return letterSourceTransferCache;
    }
    
    public LetterSourceDescriptionTransferCache getLetterSourceDescriptionTransferCache() {
        if(letterSourceDescriptionTransferCache == null)
            letterSourceDescriptionTransferCache = CDI.current().select(LetterSourceDescriptionTransferCache.class).get();
        
        return letterSourceDescriptionTransferCache;
    }
    
    public QueuedLetterTransferCache getQueuedLetterTransferCache() {
        if(queuedLetterTransferCache == null)
            queuedLetterTransferCache = CDI.current().select(QueuedLetterTransferCache.class).get();
        
        return queuedLetterTransferCache;
    }
    
    public LetterContactMechanismPurposeTransferCache getLetterContactMechanismPurposeTransferCache() {
        if(letterContactMechanismPurposeTransferCache == null)
            letterContactMechanismPurposeTransferCache = CDI.current().select(LetterContactMechanismPurposeTransferCache.class).get();
        
        return letterContactMechanismPurposeTransferCache;
    }
    
}
