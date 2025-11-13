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

package com.echothree.model.control.party.server.transfer;

import com.echothree.model.control.party.common.transfer.BirthdayFormatDescriptionTransfer;
import com.echothree.model.data.party.server.entity.BirthdayFormatDescription;
import com.echothree.model.data.user.server.entity.UserVisit;

public class BirthdayFormatDescriptionTransferCache
        extends BasePartyDescriptionTransferCache<BirthdayFormatDescription, BirthdayFormatDescriptionTransfer> {
    
    /** Creates a new instance of BirthdayFormatDescriptionTransferCache */
    public BirthdayFormatDescriptionTransferCache() {
        super();
    }

    @Override
    public BirthdayFormatDescriptionTransfer getTransfer(BirthdayFormatDescription birthdayFormatDescription) {
        var birthdayFormatDescriptionTransfer = get(birthdayFormatDescription);
        
        if(birthdayFormatDescriptionTransfer == null) {
            var birthdayFormatTransfer = partyControl.getBirthdayFormatTransfer(userVisit, birthdayFormatDescription.getBirthdayFormat());
            var languageTransfer = partyControl.getLanguageTransfer(userVisit, birthdayFormatDescription.getLanguage());
            
            birthdayFormatDescriptionTransfer = new BirthdayFormatDescriptionTransfer(languageTransfer, birthdayFormatTransfer, birthdayFormatDescription.getDescription());
            put(userVisit, birthdayFormatDescription, birthdayFormatDescriptionTransfer);
        }
        
        return birthdayFormatDescriptionTransfer;
    }
    
}
