// --------------------------------------------------------------------------------
// Copyright 2002-2024 Echo Three, LLC
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

import com.echothree.model.control.party.common.transfer.NameSuffixTransfer;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.data.party.common.pk.NameSuffixPK;
import com.echothree.model.data.party.server.entity.NameSuffix;
import com.echothree.model.data.party.server.entity.NameSuffixDetail;
import com.echothree.model.data.user.server.entity.UserVisit;

public class NameSuffixTransferCache
        extends BasePartyTransferCache<NameSuffix, NameSuffixTransfer> {
    
    /** Creates a new instance of NameSuffixTransferCache */
    public NameSuffixTransferCache(UserVisit userVisit, PartyControl partyControl) {
        super(userVisit, partyControl);
        
        setIncludeEntityInstance(true);
    }
    
    public NameSuffixTransfer getNameSuffixTransfer(NameSuffix nameSuffix) {
        NameSuffixTransfer nameSuffixTransfer = get(nameSuffix);
        
        if(nameSuffixTransfer == null) {
            NameSuffixDetail nameSuffixDetail = nameSuffix.getLastDetail();
            NameSuffixPK nameSuffixPK = nameSuffix.getPrimaryKey();
            String nameSuffixId = nameSuffixPK.getEntityId().toString();
            String description = nameSuffixDetail.getDescription();
            Boolean isDefault = nameSuffixDetail.getIsDefault();
            Integer sortOrder = nameSuffixDetail.getSortOrder();
            
            nameSuffixTransfer = new NameSuffixTransfer(nameSuffixId, description, isDefault, sortOrder);
            put(nameSuffix, nameSuffixTransfer);
        }
        
        return nameSuffixTransfer;
    }
    
}
