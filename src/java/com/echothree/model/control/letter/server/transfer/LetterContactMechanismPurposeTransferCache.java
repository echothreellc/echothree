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

import com.echothree.model.control.contact.server.control.ContactControl;
import com.echothree.model.control.letter.common.transfer.LetterContactMechanismPurposeTransfer;
import com.echothree.model.control.letter.server.control.LetterControl;
import com.echothree.model.data.letter.server.entity.LetterContactMechanismPurpose;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class LetterContactMechanismPurposeTransferCache
        extends BaseLetterTransferCache<LetterContactMechanismPurpose, LetterContactMechanismPurposeTransfer> {
    
    ContactControl contactControl;
    
    /** Creates a new instance of LetterContactMechanismPurposeTransferCache */
    public LetterContactMechanismPurposeTransferCache(UserVisit userVisit, LetterControl letterControl) {
        super(userVisit, letterControl);
        
        contactControl = Session.getModelController(ContactControl.class);
    }
    
    public LetterContactMechanismPurposeTransfer getLetterContactMechanismPurposeTransfer(LetterContactMechanismPurpose letterContactMechanismPurpose) {
        var letterContactMechanismPurposeTransfer = get(letterContactMechanismPurpose);
        
        if(letterContactMechanismPurposeTransfer == null) {
            var letterContactMechanismPurposeDetail = letterContactMechanismPurpose.getLastDetail();
            var letter = letterControl.getLetterTransfer(userVisit, letterContactMechanismPurposeDetail.getLetter());
            var contactMechanismPurpose = contactControl.getContactMechanismPurposeTransfer(userVisit, letterContactMechanismPurposeDetail.getContactMechanismPurpose());
            var priority = letterContactMechanismPurposeDetail.getPriority();
            
            letterContactMechanismPurposeTransfer = new LetterContactMechanismPurposeTransfer(letter, contactMechanismPurpose,
                    priority);
            put(letterContactMechanismPurpose, letterContactMechanismPurposeTransfer);
        }
        
        return letterContactMechanismPurposeTransfer;
    }
    
}
