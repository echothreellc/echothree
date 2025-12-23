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

import com.echothree.model.control.contact.common.transfer.ContactMechanismPurposeTransfer;
import com.echothree.util.common.transfer.BaseTransfer;

public class LetterContactMechanismPurposeTransfer
        extends BaseTransfer {
    
    private LetterTransfer letter;
    private ContactMechanismPurposeTransfer contactMechanismPurpose;
    private Integer priority;
    
    /** Creates a new instance of LetterContactMechanismPurposeTransfer */
    public LetterContactMechanismPurposeTransfer(LetterTransfer letter,
            ContactMechanismPurposeTransfer contactMechanismPurpose, Integer priority) {
        this.letter = letter;
        this.contactMechanismPurpose = contactMechanismPurpose;
        this.priority = priority;
    }
    
    public LetterTransfer getLetter() {
        return letter;
    }
    
    public void setLetter(LetterTransfer letter) {
        this.letter = letter;
    }
    
    public ContactMechanismPurposeTransfer getContactMechanismPurpose() {
        return contactMechanismPurpose;
    }
    
    public void setContactMechanismPurpose(ContactMechanismPurposeTransfer contactMechanismPurpose) {
        this.contactMechanismPurpose = contactMechanismPurpose;
    }
    
    public Integer getPriority() {
        return priority;
    }
    
    public void setPriority(Integer priority) {
        this.priority = priority;
    }
    
}
