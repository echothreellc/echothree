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
import com.echothree.model.control.contactlist.server.control.ContactListControl;
import com.echothree.model.control.letter.common.transfer.LetterTransfer;
import com.echothree.model.control.letter.server.control.LetterControl;
import com.echothree.model.data.letter.server.entity.Letter;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class LetterTransferCache
        extends BaseLetterTransferCache<Letter, LetterTransfer> {
    
    ChainControl chainControl = Session.getModelController(ChainControl.class);
    ContactListControl contactListControl = Session.getModelController(ContactListControl.class);
    LetterControl letterControl = Session.getModelController(LetterControl.class);

    /** Creates a new instance of LetterTransferCache */
    protected LetterTransferCache() {
        super();
        
        setIncludeEntityInstance(true);
    }
    
    public LetterTransfer getLetterTransfer(UserVisit userVisit, Letter letter) {
        var letterTransfer = get(letter);
        
        if(letterTransfer == null) {
            var letterDetail = letter.getLastDetail();
            var chainTypeTransfer = chainControl.getChainTypeTransfer(userVisit, letterDetail.getChainType());
            var letterName = letterDetail.getLetterName();
            var letterSourceTransfer = letterControl.getLetterSourceTransfer(userVisit, letterDetail.getLetterSource());
            var contactList = letterDetail.getContactList();
            var contactListTransfer = contactList == null? null: contactListControl.getContactListTransfer(userVisit, contactList);
            var isDefault = letterDetail.getIsDefault();
            var sortOrder = letterDetail.getSortOrder();
            var description = letterControl.getBestLetterDescription(letter, getLanguage(userVisit));
            
            letterTransfer = new LetterTransfer(chainTypeTransfer, letterName, letterSourceTransfer, contactListTransfer, isDefault,
                    sortOrder, description);
            put(userVisit, letter, letterTransfer);
        }
        
        return letterTransfer;
    }
    
}
