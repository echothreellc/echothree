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

package com.echothree.model.control.party.server.transfer;

import com.echothree.model.control.party.remote.transfer.PartyAliasTypeTransfer;
import com.echothree.model.control.party.remote.transfer.PartyTypeTransfer;
import com.echothree.model.control.party.server.PartyControl;
import com.echothree.model.data.party.server.entity.PartyAliasType;
import com.echothree.model.data.party.server.entity.PartyAliasTypeDetail;
import com.echothree.model.data.user.server.entity.UserVisit;

public class PartyAliasTypeTransferCache
        extends BasePartyTransferCache<PartyAliasType, PartyAliasTypeTransfer> {
    
    /** Creates a new instance of PartyAliasTypeTransferCache */
    public PartyAliasTypeTransferCache(UserVisit userVisit, PartyControl partyControl) {
        super(userVisit, partyControl);
        
        setIncludeEntityInstance(true);
    }
    
    public PartyAliasTypeTransfer getPartyAliasTypeTransfer(PartyAliasType partyAliasType) {
        PartyAliasTypeTransfer partyAliasTypeTransfer = get(partyAliasType);
        
        if(partyAliasTypeTransfer == null) {
            PartyAliasTypeDetail partyAliasTypeDetail = partyAliasType.getLastDetail();
            PartyTypeTransfer partyType = partyControl.getPartyTypeTransfer(userVisit, partyAliasTypeDetail.getPartyType());
            String partyAliasTypeName = partyAliasTypeDetail.getPartyAliasTypeName();
            String validationPattern = partyAliasTypeDetail.getValidationPattern();
            Boolean isDefault = partyAliasTypeDetail.getIsDefault();
            Integer sortOrder = partyAliasTypeDetail.getSortOrder();
            String description = partyControl.getBestPartyAliasTypeDescription(partyAliasType, getLanguage());
            
            partyAliasTypeTransfer = new PartyAliasTypeTransfer(partyType, partyAliasTypeName, validationPattern, isDefault, sortOrder, description);
            put(partyAliasType, partyAliasTypeTransfer);
        }
        
        return partyAliasTypeTransfer;
    }
    
}
