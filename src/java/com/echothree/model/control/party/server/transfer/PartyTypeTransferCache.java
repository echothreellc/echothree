// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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
import com.echothree.model.control.party.common.transfer.PartyTypeTransfer;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.sequence.common.transfer.SequenceTypeTransfer;
import com.echothree.model.control.sequence.server.control.SequenceControl;
import com.echothree.model.data.party.server.entity.PartyType;
import com.echothree.model.data.party.server.entity.PartyTypeAuditPolicy;
import com.echothree.model.data.party.server.entity.PartyTypeLockoutPolicy;
import com.echothree.model.data.party.server.entity.PartyTypePasswordStringPolicy;
import com.echothree.model.data.sequence.server.entity.SequenceType;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.transfer.ListWrapper;
import com.echothree.util.server.persistence.Session;
import java.util.Set;

public class PartyTypeTransferCache
        extends BasePartyTransferCache<PartyType, PartyTypeTransfer> {
    
    SequenceControl sequenceControl;
    boolean includeAuditPolicy;
    boolean includeLockoutPolicy;
    boolean includePasswordStringPolicy;
    boolean includePartyAliasTypes;
    
    /** Creates a new instance of PartyTypeTransferCache */
    public PartyTypeTransferCache(UserVisit userVisit, PartyControl partyControl) {
        super(userVisit, partyControl);
        
        sequenceControl = Session.getModelController(SequenceControl.class);
        
        Set<String> options = session.getOptions();
        if(options != null) {
            includeAuditPolicy = options.contains(PartyOptions.PartyTypeIncludeAuditPolicy);
            includeLockoutPolicy = options.contains(PartyOptions.PartyTypeIncludeLockoutPolicy);
            includePasswordStringPolicy = options.contains(PartyOptions.PartyTypeIncludePasswordStringPolicy);
            includePartyAliasTypes = options.contains(PartyOptions.PartyTypeIncludePartyAliasTypes);
        }
    }
    
    public PartyTypeTransfer getPartyTypeTransfer(PartyType partyType) {
        PartyTypeTransfer partyTypeTransfer = get(partyType);
        
        if(partyTypeTransfer == null) {
            String partyTypeName = partyType.getPartyTypeName();
            PartyType parentPartyType = partyType.getParentPartyType();
            PartyTypeTransfer parentPartyTypeTransfer = parentPartyType == null? null: getPartyTypeTransfer(parentPartyType);
            SequenceType billingAccountSequenceType = partyType.getBillingAccountSequenceType();
            SequenceTypeTransfer billingAccountSequenceTypeTransfer = billingAccountSequenceType == null? null: sequenceControl.getSequenceTypeTransfer(userVisit, billingAccountSequenceType);
            Boolean allowUserLogins = partyType.getAllowUserLogins();
            Boolean allowPartyAliases = partyType.getAllowPartyAliases();
            Boolean isDefault = partyType.getIsDefault();
            Integer sortOrder = partyType.getSortOrder();
            String description = partyControl.getBestPartyTypeDescription(partyType, getLanguage());
            
            partyTypeTransfer = new PartyTypeTransfer(partyTypeName, parentPartyTypeTransfer, billingAccountSequenceTypeTransfer, allowUserLogins,
                    allowPartyAliases, isDefault, sortOrder, description);
            put(partyType, partyTypeTransfer);
            
            if(includeAuditPolicy) {
                PartyTypeAuditPolicy partyTypeAuditPolicy = partyControl.getPartyTypeAuditPolicy(partyType);
                
                if(partyTypeAuditPolicy != null) {
                    partyTypeTransfer.setPartyTypeAuditPolicy(partyControl.getPartyTypeAuditPolicyTransfer(userVisit, partyTypeAuditPolicy));
                }
            }
            
            if(includeLockoutPolicy) {
                PartyTypeLockoutPolicy partyTypeLockoutPolicy = partyControl.getPartyTypeLockoutPolicy(partyType);
                
                if(partyTypeLockoutPolicy != null) {
                    partyTypeTransfer.setPartyTypeLockoutPolicy(partyControl.getPartyTypeLockoutPolicyTransfer(userVisit, partyTypeLockoutPolicy));
                }
            }
            
            if(includePasswordStringPolicy) {
                PartyTypePasswordStringPolicy partyTypePasswordStringPolicy = partyControl.getPartyTypePasswordStringPolicy(partyType);
                
                if(partyTypePasswordStringPolicy != null) {
                    partyTypeTransfer.setPartyTypePasswordStringPolicy(partyControl.getPartyTypePasswordStringPolicyTransfer(userVisit, partyTypePasswordStringPolicy));
                }
            }

            if(includePartyAliasTypes) {
                partyTypeTransfer.setPartyAliasTypes(new ListWrapper<>(partyControl.getPartyAliasTypeTransfers(userVisit, partyType)));
            }
        }
        
        return partyTypeTransfer;
    }
    
}
