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

import com.echothree.model.control.party.common.transfer.BirthdayFormatTransfer;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.data.party.server.entity.BirthdayFormat;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class BirthdayFormatTransferCache
        extends BasePartyTransferCache<BirthdayFormat, BirthdayFormatTransfer> {

    PartyControl partyControl = Session.getModelController(PartyControl.class);

    /** Creates a new instance of BirthdayFormatTransferCache */
    public BirthdayFormatTransferCache() {
        super();
        
        setIncludeEntityInstance(true);
    }

    @Override
    public BirthdayFormatTransfer getTransfer(BirthdayFormat birthdayFormat) {
        var birthdayFormatTransfer = get(birthdayFormat);
        
        if(birthdayFormatTransfer == null) {
            var birthdayFormatDetail = birthdayFormat.getLastDetail();
            var birthdayFormatName = birthdayFormatDetail.getBirthdayFormatName();
            var isDefault = birthdayFormatDetail.getIsDefault();
            var sortOrder = birthdayFormatDetail.getSortOrder();
            var description = partyControl.getBestBirthdayFormatDescription(birthdayFormat, getLanguage(userVisit));
            
            birthdayFormatTransfer = new BirthdayFormatTransfer(birthdayFormatName, isDefault, sortOrder, description);
            put(userVisit, birthdayFormat, birthdayFormatTransfer);
        }
        
        return birthdayFormatTransfer;
    }
    
}
