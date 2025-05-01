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

import com.echothree.model.control.party.common.transfer.PersonalTitleTransfer;
import com.echothree.model.data.party.server.entity.PersonalTitle;
import com.echothree.model.data.user.server.entity.UserVisit;

public class PersonalTitleTransferCache
        extends BasePartyTransferCache<PersonalTitle, PersonalTitleTransfer> {
    
    /** Creates a new instance of PersonalTitleTransferCache */
    public PersonalTitleTransferCache(UserVisit userVisit) {
        super(userVisit);
        
        setIncludeEntityInstance(true);
    }

    @Override
    public PersonalTitleTransfer getTransfer(PersonalTitle personalTitle) {
        var personalTitleTransfer = get(personalTitle);
        
        if(personalTitleTransfer == null) {
            var personalTitleDetail = personalTitle.getLastDetail();
            var personalTitlePK = personalTitle.getPrimaryKey();
            var personalTitleId = personalTitlePK.getEntityId().toString();
            var description = personalTitleDetail.getDescription();
            var isDefault = personalTitleDetail.getIsDefault();
            var sortOrder = personalTitleDetail.getSortOrder();
            
            personalTitleTransfer = new PersonalTitleTransfer(personalTitleId, description, isDefault, sortOrder);
            put(personalTitle, personalTitleTransfer);
        }
        
        return personalTitleTransfer;
    }
    
}
