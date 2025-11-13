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
import com.echothree.model.control.letter.common.transfer.LetterSourceTransfer;
import com.echothree.model.control.letter.server.control.LetterControl;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.data.letter.server.entity.LetterSource;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class LetterSourceTransferCache
        extends BaseLetterTransferCache<LetterSource, LetterSourceTransfer> {
    
    ContactControl contactControl = Session.getModelController(ContactControl.class);
    PartyControl partyControl = Session.getModelController(PartyControl.class);
    
    /** Creates a new instance of LetterSourceTransferCache */
    public LetterSourceTransferCache(LetterControl letterControl) {
        super(letterControl);
        
        setIncludeEntityInstance(true);
    }
    
    public LetterSourceTransfer getLetterSourceTransfer(UserVisit userVisit, LetterSource letterSource) {
        var letterSourceTransfer = get(letterSource);
        
        if(letterSourceTransfer == null) {
            var letterSourceDetail = letterSource.getLastDetail();
            var letterSourceName = letterSourceDetail.getLetterSourceName();
            var companyTransfer = partyControl.getCompanyTransfer(userVisit, letterSourceDetail.getCompanyParty());
            var emailAddressPartyContactMechanismTransfer = contactControl.getPartyContactMechanismTransfer(userVisit, letterSourceDetail.getEmailAddressPartyContactMechanism());
            var postalAddressPartyContactMechanismTransfer = contactControl.getPartyContactMechanismTransfer(userVisit, letterSourceDetail.getPostalAddressPartyContactMechanism());
            var letterSourcePartyContactMechanismTransfer = contactControl.getPartyContactMechanismTransfer(userVisit, letterSourceDetail.getLetterSourcePartyContactMechanism());
            var isDefault = letterSourceDetail.getIsDefault();
            var sortOrder = letterSourceDetail.getSortOrder();
            var description = letterControl.getBestLetterSourceDescription(letterSource, getLanguage(userVisit));
            
            letterSourceTransfer = new LetterSourceTransfer(letterSourceName, companyTransfer,
                    emailAddressPartyContactMechanismTransfer, postalAddressPartyContactMechanismTransfer,
                    letterSourcePartyContactMechanismTransfer, isDefault, sortOrder, description);
            put(userVisit, letterSource, letterSourceTransfer);
        }
        
        return letterSourceTransfer;
    }
    
}
