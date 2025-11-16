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

import com.echothree.model.control.party.common.transfer.PartyTypePasswordStringPolicyTransfer;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.uom.common.UomConstants;
import com.echothree.model.control.uom.server.control.UomControl;
import com.echothree.model.data.party.server.entity.PartyTypePasswordStringPolicy;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureKind;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class PartyTypePasswordStringPolicyTransferCache
        extends BasePartyTransferCache<PartyTypePasswordStringPolicy, PartyTypePasswordStringPolicyTransfer> {

    PartyControl partyControl = Session.getModelController(PartyControl.class);
    UomControl uomControl = Session.getModelController(UomControl.class);

    UnitOfMeasureKind timeUnitOfMeasureKind = uomControl.getUnitOfMeasureKindByUnitOfMeasureKindUseTypeUsingNames(UomConstants.UnitOfMeasureKindUseType_TIME);
    
    /** Creates a new instance of PartyTypePasswordStringPolicyTransferCache */
    protected PartyTypePasswordStringPolicyTransferCache() {
        super();
        
        setIncludeEntityInstance(true);
    }

    @Override
    public PartyTypePasswordStringPolicyTransfer getTransfer(UserVisit userVisit, PartyTypePasswordStringPolicy partyTypePasswordStringPolicy) {
        var partyTypePasswordStringPolicyTransfer = get(partyTypePasswordStringPolicy);
        
        if(partyTypePasswordStringPolicyTransfer == null) {
            var partyTypePasswordStringPolicyDetail = partyTypePasswordStringPolicy.getLastDetail();
            var partyTypeTransfer = partyControl.getPartyTypeTransfer(userVisit, partyTypePasswordStringPolicyDetail.getPartyType());
            var forceChangeAfterCreate = partyTypePasswordStringPolicyDetail.getForceChangeAfterCreate();
            var forceChangeAfterReset = partyTypePasswordStringPolicyDetail.getForceChangeAfterReset();
            var allowChange = partyTypePasswordStringPolicyDetail.getAllowChange();
            var passwordHistory = partyTypePasswordStringPolicyDetail.getPasswordHistory();
            var unformattedMinimumPasswordLifetime = partyTypePasswordStringPolicyDetail.getMinimumPasswordLifetime();
            var minimumPasswordLifetime = formatUnitOfMeasure(userVisit, timeUnitOfMeasureKind, unformattedMinimumPasswordLifetime);
            var unformattedMaximumPasswordLifetime = partyTypePasswordStringPolicyDetail.getMaximumPasswordLifetime();
            var maximumPasswordLifetime = formatUnitOfMeasure(userVisit, timeUnitOfMeasureKind, unformattedMaximumPasswordLifetime);
            var unformattedExpirationWarningTime = partyTypePasswordStringPolicyDetail.getExpirationWarningTime();
            var expirationWarningTime = formatUnitOfMeasure(userVisit, timeUnitOfMeasureKind, unformattedExpirationWarningTime);
            var expiredLoginsPermitted = partyTypePasswordStringPolicyDetail.getExpiredLoginsPermitted();
            var minimumLength = partyTypePasswordStringPolicyDetail.getMinimumLength();
            var maximumLength = partyTypePasswordStringPolicyDetail.getMaximumLength();
            var requiredDigitCount = partyTypePasswordStringPolicyDetail.getRequiredDigitCount();
            var requiredLetterCount = partyTypePasswordStringPolicyDetail.getRequiredLetterCount();
            var requiredUpperCaseCount = partyTypePasswordStringPolicyDetail.getRequiredUpperCaseCount();
            var requiredLowerCaseCount = partyTypePasswordStringPolicyDetail.getRequiredLowerCaseCount();
            var maximumRepeated = partyTypePasswordStringPolicyDetail.getMaximumRepeated();
            var minimumCharacterTypes = partyTypePasswordStringPolicyDetail.getMinimumCharacterTypes();
            
            partyTypePasswordStringPolicyTransfer = new PartyTypePasswordStringPolicyTransfer(partyTypeTransfer, forceChangeAfterCreate,
                    forceChangeAfterReset, allowChange, passwordHistory, unformattedMinimumPasswordLifetime, minimumPasswordLifetime,
                    unformattedMaximumPasswordLifetime, maximumPasswordLifetime, unformattedExpirationWarningTime, expirationWarningTime,
                    expiredLoginsPermitted, minimumLength, maximumLength, requiredDigitCount, requiredLetterCount, requiredUpperCaseCount,
                    requiredLowerCaseCount, maximumRepeated, minimumCharacterTypes);
            put(userVisit, partyTypePasswordStringPolicy, partyTypePasswordStringPolicyTransfer);
        }
        
        return partyTypePasswordStringPolicyTransfer;
    }
    
}
