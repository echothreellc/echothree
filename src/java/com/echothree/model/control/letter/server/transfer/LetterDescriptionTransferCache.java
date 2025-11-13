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

import com.echothree.model.control.letter.common.transfer.LetterDescriptionTransfer;
import com.echothree.model.control.letter.server.control.LetterControl;
import com.echothree.model.data.letter.server.entity.LetterDescription;
import com.echothree.model.data.user.server.entity.UserVisit;

public class LetterDescriptionTransferCache
        extends BaseLetterDescriptionTransferCache<LetterDescription, LetterDescriptionTransfer> {
    
    /** Creates a new instance of LetterDescriptionTransferCache */
    public LetterDescriptionTransferCache(LetterControl letterControl) {
        super(letterControl);
    }
    
    public LetterDescriptionTransfer getLetterDescriptionTransfer(LetterDescription letterDescription) {
        var letterDescriptionTransfer = get(letterDescription);
        
        if(letterDescriptionTransfer == null) {
            var letterTransfer = letterControl.getLetterTransfer(userVisit, letterDescription.getLetter());
            var languageTransfer = partyControl.getLanguageTransfer(userVisit, letterDescription.getLanguage());
            
            letterDescriptionTransfer = new LetterDescriptionTransfer(languageTransfer, letterTransfer, letterDescription.getDescription());
            put(userVisit, letterDescription, letterDescriptionTransfer);
        }
        
        return letterDescriptionTransfer;
    }
    
}
