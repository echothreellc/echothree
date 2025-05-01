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

import com.echothree.model.control.party.common.PartyOptions;
import com.echothree.model.control.party.common.transfer.PartyAliasTypeTransfer;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.data.party.server.entity.PartyAliasType;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class PartyAliasTypeTransferCache
        extends BasePartyTransferCache<PartyAliasType, PartyAliasTypeTransfer> {

    PartyControl partyControl = Session.getModelController(PartyControl.class);

    /** Creates a new instance of PartyAliasTypeTransferCache */
    public PartyAliasTypeTransferCache(UserVisit userVisit) {
        super(userVisit);

        var options = session.getOptions();
        if(options != null) {
            setIncludeUuid(options.contains(PartyOptions.PartyAliasTypeIncludeUuid));
            setIncludeEntityAttributeGroups(options.contains(PartyOptions.PartyAliasTypeIncludeEntityAttributeGroups));
            setIncludeTagScopes(options.contains(PartyOptions.PartyAliasTypeIncludeTagScopes));
        }

        setIncludeEntityInstance(true);
    }

    @Override
    public PartyAliasTypeTransfer getTransfer(PartyAliasType partyAliasType) {
        var partyAliasTypeTransfer = get(partyAliasType);
        
        if(partyAliasTypeTransfer == null) {
            var partyAliasTypeDetail = partyAliasType.getLastDetail();
            var partyType = partyControl.getPartyTypeTransfer(userVisit, partyAliasTypeDetail.getPartyType());
            var partyAliasTypeName = partyAliasTypeDetail.getPartyAliasTypeName();
            var validationPattern = partyAliasTypeDetail.getValidationPattern();
            var isDefault = partyAliasTypeDetail.getIsDefault();
            var sortOrder = partyAliasTypeDetail.getSortOrder();
            var description = partyControl.getBestPartyAliasTypeDescription(partyAliasType, getLanguage());
            
            partyAliasTypeTransfer = new PartyAliasTypeTransfer(partyType, partyAliasTypeName, validationPattern, isDefault, sortOrder, description);
            put(partyAliasType, partyAliasTypeTransfer);
        }
        
        return partyAliasTypeTransfer;
    }
    
}
