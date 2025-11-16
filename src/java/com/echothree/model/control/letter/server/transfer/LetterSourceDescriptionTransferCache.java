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

import com.echothree.model.control.letter.common.transfer.LetterSourceDescriptionTransfer;
import com.echothree.model.control.letter.server.control.LetterControl;
import com.echothree.model.data.letter.server.entity.LetterSourceDescription;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class LetterSourceDescriptionTransferCache
        extends BaseLetterDescriptionTransferCache<LetterSourceDescription, LetterSourceDescriptionTransfer> {

    LetterControl letterControl = Session.getModelController(LetterControl.class);

    /** Creates a new instance of LetterSourceDescriptionTransferCache */
    public LetterSourceDescriptionTransferCache() {
        super();
    }
    
    public LetterSourceDescriptionTransfer getLetterSourceDescriptionTransfer(UserVisit userVisit, LetterSourceDescription letterSourceDescription) {
        var letterSourceDescriptionTransfer = get(letterSourceDescription);
        
        if(letterSourceDescriptionTransfer == null) {
            var letterSourceTransfer = letterControl.getLetterSourceTransfer(userVisit, letterSourceDescription.getLetterSource());
            var languageTransfer = partyControl.getLanguageTransfer(userVisit, letterSourceDescription.getLanguage());
            
            letterSourceDescriptionTransfer = new LetterSourceDescriptionTransfer(languageTransfer, letterSourceTransfer, letterSourceDescription.getDescription());
            put(userVisit, letterSourceDescription, letterSourceDescriptionTransfer);
        }
        
        return letterSourceDescriptionTransfer;
    }
    
}
