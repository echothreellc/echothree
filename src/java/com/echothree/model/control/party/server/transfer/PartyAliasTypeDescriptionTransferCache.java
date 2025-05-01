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

import com.echothree.model.control.party.common.transfer.PartyAliasTypeDescriptionTransfer;
import com.echothree.model.data.party.server.entity.PartyAliasTypeDescription;
import com.echothree.model.data.user.server.entity.UserVisit;

public class PartyAliasTypeDescriptionTransferCache
        extends BasePartyDescriptionTransferCache<PartyAliasTypeDescription, PartyAliasTypeDescriptionTransfer> {
    
    /** Creates a new instance of PartyAliasTypeDescriptionTransferCache */
    public PartyAliasTypeDescriptionTransferCache(UserVisit userVisit) {
        super(userVisit);
    }

    @Override
    public PartyAliasTypeDescriptionTransfer getTransfer(PartyAliasTypeDescription partyAliasTypeDescription) {
        var partyAliasTypeDescriptionTransfer = get(partyAliasTypeDescription);
        
        if(partyAliasTypeDescriptionTransfer == null) {
            var partyAliasTypeTransfer = partyControl.getPartyAliasTypeTransfer(userVisit, partyAliasTypeDescription.getPartyAliasType());
            var languageTransfer = partyControl.getLanguageTransfer(userVisit, partyAliasTypeDescription.getLanguage());
            
            partyAliasTypeDescriptionTransfer = new PartyAliasTypeDescriptionTransfer(languageTransfer, partyAliasTypeTransfer, partyAliasTypeDescription.getDescription());
            put(partyAliasTypeDescription, partyAliasTypeDescriptionTransfer);
        }
        
        return partyAliasTypeDescriptionTransfer;
    }
    
}
