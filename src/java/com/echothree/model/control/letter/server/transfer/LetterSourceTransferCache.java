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

import com.echothree.model.control.contact.remote.transfer.PartyContactMechanismTransfer;
import com.echothree.model.control.contact.server.ContactControl;
import com.echothree.model.control.letter.remote.transfer.LetterSourceTransfer;
import com.echothree.model.control.letter.server.LetterControl;
import com.echothree.model.control.party.remote.transfer.CompanyTransfer;
import com.echothree.model.control.party.server.PartyControl;
import com.echothree.model.data.letter.server.entity.LetterSource;
import com.echothree.model.data.letter.server.entity.LetterSourceDetail;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class LetterSourceTransferCache
        extends BaseLetterTransferCache<LetterSource, LetterSourceTransfer> {
    
    ContactControl contactControl = (ContactControl)Session.getModelController(ContactControl.class);
    PartyControl partyControl = (PartyControl)Session.getModelController(PartyControl.class);
    
    /** Creates a new instance of LetterSourceTransferCache */
    public LetterSourceTransferCache(UserVisit userVisit, LetterControl letterControl) {
        super(userVisit, letterControl);
        
        setIncludeEntityInstance(true);
    }
    
    public LetterSourceTransfer getLetterSourceTransfer(LetterSource letterSource) {
        LetterSourceTransfer letterSourceTransfer = get(letterSource);
        
        if(letterSourceTransfer == null) {
            LetterSourceDetail letterSourceDetail = letterSource.getLastDetail();
            String letterSourceName = letterSourceDetail.getLetterSourceName();
            CompanyTransfer companyTransfer = partyControl.getCompanyTransfer(userVisit, letterSourceDetail.getCompanyParty());
            PartyContactMechanismTransfer emailAddressPartyContactMechanismTransfer = contactControl.getPartyContactMechanismTransfer(userVisit, letterSourceDetail.getEmailAddressPartyContactMechanism());
            PartyContactMechanismTransfer postalAddressPartyContactMechanismTransfer = contactControl.getPartyContactMechanismTransfer(userVisit, letterSourceDetail.getPostalAddressPartyContactMechanism());
            PartyContactMechanismTransfer letterSourcePartyContactMechanismTransfer = contactControl.getPartyContactMechanismTransfer(userVisit, letterSourceDetail.getLetterSourcePartyContactMechanism());
            Boolean isDefault = letterSourceDetail.getIsDefault();
            Integer sortOrder = letterSourceDetail.getSortOrder();
            String description = letterControl.getBestLetterSourceDescription(letterSource, getLanguage());
            
            letterSourceTransfer = new LetterSourceTransfer(letterSourceName, companyTransfer,
                    emailAddressPartyContactMechanismTransfer, postalAddressPartyContactMechanismTransfer,
                    letterSourcePartyContactMechanismTransfer, isDefault, sortOrder, description);
            put(letterSource, letterSourceTransfer);
        }
        
        return letterSourceTransfer;
    }
    
}
