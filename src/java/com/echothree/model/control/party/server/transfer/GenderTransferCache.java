// --------------------------------------------------------------------------------
// Copyright 2002-2022 Echo Three, LLC
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

import com.echothree.model.control.party.common.transfer.GenderTransfer;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.data.party.server.entity.Gender;
import com.echothree.model.data.party.server.entity.GenderDetail;
import com.echothree.model.data.user.server.entity.UserVisit;

public class GenderTransferCache
        extends BasePartyTransferCache<Gender, GenderTransfer> {
    
    /** Creates a new instance of GenderTransferCache */
    public GenderTransferCache(UserVisit userVisit, PartyControl partyControl) {
        super(userVisit, partyControl);
        
        setIncludeEntityInstance(true);
    }
    
    public GenderTransfer getGenderTransfer(Gender gender) {
        GenderTransfer genderTransfer = get(gender);
        
        if(genderTransfer == null) {
            GenderDetail genderDetail = gender.getLastDetail();
            String genderName = genderDetail.getGenderName();
            Boolean isDefault = genderDetail.getIsDefault();
            Integer sortOrder = genderDetail.getSortOrder();
            String description = partyControl.getBestGenderDescription(gender, getLanguage());
            
            genderTransfer = new GenderTransfer(genderName, isDefault, sortOrder, description);
            put(gender, genderTransfer);
        }
        
        return genderTransfer;
    }
    
}
