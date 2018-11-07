// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
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

import com.echothree.model.control.chain.common.transfer.ChainTypeTransfer;
import com.echothree.model.control.chain.server.ChainControl;
import com.echothree.model.control.contactlist.common.transfer.ContactListTransfer;
import com.echothree.model.control.contactlist.server.ContactListControl;
import com.echothree.model.control.letter.common.transfer.LetterSourceTransfer;
import com.echothree.model.control.letter.common.transfer.LetterTransfer;
import com.echothree.model.control.letter.server.LetterControl;
import com.echothree.model.data.contactlist.server.entity.ContactList;
import com.echothree.model.data.letter.server.entity.Letter;
import com.echothree.model.data.letter.server.entity.LetterDetail;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class LetterTransferCache
        extends BaseLetterTransferCache<Letter, LetterTransfer> {
    
    ChainControl chainControl = (ChainControl)Session.getModelController(ChainControl.class);
    ContactListControl contactListControl = (ContactListControl)Session.getModelController(ContactListControl.class);
    
    /** Creates a new instance of LetterTransferCache */
    public LetterTransferCache(UserVisit userVisit, LetterControl letterControl) {
        super(userVisit, letterControl);
        
        setIncludeEntityInstance(true);
    }
    
    public LetterTransfer getLetterTransfer(Letter letter) {
        LetterTransfer letterTransfer = get(letter);
        
        if(letterTransfer == null) {
            LetterDetail letterDetail = letter.getLastDetail();
            ChainTypeTransfer chainTypeTransfer = chainControl.getChainTypeTransfer(userVisit, letterDetail.getChainType());
            String letterName = letterDetail.getLetterName();
            LetterSourceTransfer letterSourceTransfer = letterControl.getLetterSourceTransfer(userVisit, letterDetail.getLetterSource());
            ContactList contactList = letterDetail.getContactList();
            ContactListTransfer contactListTransfer = contactList == null? null: contactListControl.getContactListTransfer(userVisit, contactList);
            Boolean isDefault = letterDetail.getIsDefault();
            Integer sortOrder = letterDetail.getSortOrder();
            String description = letterControl.getBestLetterDescription(letter, getLanguage());
            
            letterTransfer = new LetterTransfer(chainTypeTransfer, letterName, letterSourceTransfer, contactListTransfer, isDefault,
                    sortOrder, description);
            put(letter, letterTransfer);
        }
        
        return letterTransfer;
    }
    
}
