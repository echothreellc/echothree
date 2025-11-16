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
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

@RequestScoped
public class LetterTransferCaches
        extends BaseTransferCaches {
    
    @Inject
    LetterTransferCache letterTransferCache;
    
    @Inject
    LetterDescriptionTransferCache letterDescriptionTransferCache;
    
    @Inject
    LetterSourceTransferCache letterSourceTransferCache;
    
    @Inject
    LetterSourceDescriptionTransferCache letterSourceDescriptionTransferCache;
    
    @Inject
    QueuedLetterTransferCache queuedLetterTransferCache;
    
    @Inject
    LetterContactMechanismPurposeTransferCache letterContactMechanismPurposeTransferCache;

    /** Creates a new instance of LetterTransferCaches */
    protected LetterTransferCaches() {
        super();
    }
    
    public LetterTransferCache getLetterTransferCache() {
        return letterTransferCache;
    }
    
    public LetterDescriptionTransferCache getLetterDescriptionTransferCache() {
        return letterDescriptionTransferCache;
    }
    
    public LetterSourceTransferCache getLetterSourceTransferCache() {
        return letterSourceTransferCache;
    }
    
    public LetterSourceDescriptionTransferCache getLetterSourceDescriptionTransferCache() {
        return letterSourceDescriptionTransferCache;
    }
    
    public QueuedLetterTransferCache getQueuedLetterTransferCache() {
        return queuedLetterTransferCache;
    }
    
    public LetterContactMechanismPurposeTransferCache getLetterContactMechanismPurposeTransferCache() {
        return letterContactMechanismPurposeTransferCache;
    }
    
}
