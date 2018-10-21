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

import com.echothree.model.control.party.remote.transfer.PartyTypePasswordStringPolicyTransfer;
import com.echothree.model.control.party.remote.transfer.PartyTypeTransfer;
import com.echothree.model.control.party.server.PartyControl;
import com.echothree.model.control.uom.common.UomConstants;
import com.echothree.model.control.uom.server.UomControl;
import com.echothree.model.data.party.server.entity.PartyTypePasswordStringPolicy;
import com.echothree.model.data.party.server.entity.PartyTypePasswordStringPolicyDetail;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureKind;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class PartyTypePasswordStringPolicyTransferCache
        extends BasePartyTransferCache<PartyTypePasswordStringPolicy, PartyTypePasswordStringPolicyTransfer> {
    
    UomControl uomControl = (UomControl)Session.getModelController(UomControl.class);
    UnitOfMeasureKind timeUnitOfMeasureKind = uomControl.getUnitOfMeasureKindByUnitOfMeasureKindUseTypeUsingNames(UomConstants.UnitOfMeasureKindUseType_TIME);
    
    /** Creates a new instance of PartyTypePasswordStringPolicyTransferCache */
    public PartyTypePasswordStringPolicyTransferCache(UserVisit userVisit, PartyControl partyControl) {
        super(userVisit, partyControl);
        
        setIncludeEntityInstance(true);
    }
    
    public PartyTypePasswordStringPolicyTransfer getPartyTypePasswordStringPolicyTransfer(PartyTypePasswordStringPolicy partyTypePasswordStringPolicy) {
        PartyTypePasswordStringPolicyTransfer partyTypePasswordStringPolicyTransfer = get(partyTypePasswordStringPolicy);
        
        if(partyTypePasswordStringPolicyTransfer == null) {
            PartyTypePasswordStringPolicyDetail partyTypePasswordStringPolicyDetail = partyTypePasswordStringPolicy.getLastDetail();
            PartyTypeTransfer partyTypeTransfer = partyControl.getPartyTypeTransfer(userVisit, partyTypePasswordStringPolicyDetail.getPartyType());
            Boolean forceChangeAfterCreate = partyTypePasswordStringPolicyDetail.getForceChangeAfterCreate();
            Boolean forceChangeAfterReset = partyTypePasswordStringPolicyDetail.getForceChangeAfterReset();
            Boolean allowChange = partyTypePasswordStringPolicyDetail.getAllowChange();
            Integer passwordHistory = partyTypePasswordStringPolicyDetail.getPasswordHistory();
            Long unformattedMinimumPasswordLifetime = partyTypePasswordStringPolicyDetail.getMinimumPasswordLifetime();
            String minimumPasswordLifetime = formatUnitOfMeasure(timeUnitOfMeasureKind, unformattedMinimumPasswordLifetime);
            Long unformattedMaximumPasswordLifetime = partyTypePasswordStringPolicyDetail.getMaximumPasswordLifetime();
            String maximumPasswordLifetime = formatUnitOfMeasure(timeUnitOfMeasureKind, unformattedMaximumPasswordLifetime);
            Long unformattedExpirationWarningTime = partyTypePasswordStringPolicyDetail.getExpirationWarningTime();
            String expirationWarningTime = formatUnitOfMeasure(timeUnitOfMeasureKind, unformattedExpirationWarningTime);
            Integer expiredLoginsPermitted = partyTypePasswordStringPolicyDetail.getExpiredLoginsPermitted();
            Integer minimumLength = partyTypePasswordStringPolicyDetail.getMinimumLength();
            Integer maximumLength = partyTypePasswordStringPolicyDetail.getMaximumLength();
            Integer requiredDigitCount = partyTypePasswordStringPolicyDetail.getRequiredDigitCount();
            Integer requiredLetterCount = partyTypePasswordStringPolicyDetail.getRequiredLetterCount();
            Integer requiredUpperCaseCount = partyTypePasswordStringPolicyDetail.getRequiredUpperCaseCount();
            Integer requiredLowerCaseCount = partyTypePasswordStringPolicyDetail.getRequiredLowerCaseCount();
            Integer maximumRepeated = partyTypePasswordStringPolicyDetail.getMaximumRepeated();
            Integer minimumCharacterTypes = partyTypePasswordStringPolicyDetail.getMinimumCharacterTypes();
            
            partyTypePasswordStringPolicyTransfer = new PartyTypePasswordStringPolicyTransfer(partyTypeTransfer, forceChangeAfterCreate,
                    forceChangeAfterReset, allowChange, passwordHistory, unformattedMinimumPasswordLifetime, minimumPasswordLifetime,
                    unformattedMaximumPasswordLifetime, maximumPasswordLifetime, unformattedExpirationWarningTime, expirationWarningTime,
                    expiredLoginsPermitted, minimumLength, maximumLength, requiredDigitCount, requiredLetterCount, requiredUpperCaseCount,
                    requiredLowerCaseCount, maximumRepeated, minimumCharacterTypes);
            put(partyTypePasswordStringPolicy, partyTypePasswordStringPolicyTransfer);
        }
        
        return partyTypePasswordStringPolicyTransfer;
    }
    
}
