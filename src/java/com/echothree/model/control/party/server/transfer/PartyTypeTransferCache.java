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
import com.echothree.model.control.party.common.transfer.PartyTypeTransfer;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.sequence.server.control.SequenceControl;
import com.echothree.model.data.party.server.entity.PartyType;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.transfer.ListWrapper;
import com.echothree.util.server.persistence.Session;

public class PartyTypeTransferCache
        extends BasePartyTransferCache<PartyType, PartyTypeTransfer> {

    PartyControl partyControl = Session.getModelController(PartyControl.class);
    SequenceControl sequenceControl = Session.getModelController(SequenceControl.class);

    boolean includeAuditPolicy;
    boolean includeLockoutPolicy;
    boolean includePasswordStringPolicy;
    boolean includePartyAliasTypes;
    
    /** Creates a new instance of PartyTypeTransferCache */
    public PartyTypeTransferCache() {
        super();
        
        var options = session.getOptions();
        if(options != null) {
            includeAuditPolicy = options.contains(PartyOptions.PartyTypeIncludeAuditPolicy);
            includeLockoutPolicy = options.contains(PartyOptions.PartyTypeIncludeLockoutPolicy);
            includePasswordStringPolicy = options.contains(PartyOptions.PartyTypeIncludePasswordStringPolicy);
            includePartyAliasTypes = options.contains(PartyOptions.PartyTypeIncludePartyAliasTypes);
        }
    }

    @Override
    public PartyTypeTransfer getTransfer(UserVisit userVisit, PartyType partyType) {
        var partyTypeTransfer = get(partyType);
        
        if(partyTypeTransfer == null) {
            var partyTypeName = partyType.getPartyTypeName();
            var parentPartyType = partyType.getParentPartyType();
            var parentPartyTypeTransfer = parentPartyType == null? null: getTransfer(parentPartyType);
            var billingAccountSequenceType = partyType.getBillingAccountSequenceType();
            var billingAccountSequenceTypeTransfer = billingAccountSequenceType == null? null: sequenceControl.getSequenceTypeTransfer(userVisit, billingAccountSequenceType);
            var allowUserLogins = partyType.getAllowUserLogins();
            var allowPartyAliases = partyType.getAllowPartyAliases();
            var isDefault = partyType.getIsDefault();
            var sortOrder = partyType.getSortOrder();
            var description = partyControl.getBestPartyTypeDescription(partyType, getLanguage(userVisit));
            
            partyTypeTransfer = new PartyTypeTransfer(partyTypeName, parentPartyTypeTransfer, billingAccountSequenceTypeTransfer, allowUserLogins,
                    allowPartyAliases, isDefault, sortOrder, description);
            put(userVisit, partyType, partyTypeTransfer);
            
            if(includeAuditPolicy) {
                var partyTypeAuditPolicy = partyControl.getPartyTypeAuditPolicy(partyType);
                
                if(partyTypeAuditPolicy != null) {
                    partyTypeTransfer.setPartyTypeAuditPolicy(partyControl.getPartyTypeAuditPolicyTransfer(userVisit, partyTypeAuditPolicy));
                }
            }
            
            if(includeLockoutPolicy) {
                var partyTypeLockoutPolicy = partyControl.getPartyTypeLockoutPolicy(partyType);
                
                if(partyTypeLockoutPolicy != null) {
                    partyTypeTransfer.setPartyTypeLockoutPolicy(partyControl.getPartyTypeLockoutPolicyTransfer(userVisit, partyTypeLockoutPolicy));
                }
            }
            
            if(includePasswordStringPolicy) {
                var partyTypePasswordStringPolicy = partyControl.getPartyTypePasswordStringPolicy(partyType);
                
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
