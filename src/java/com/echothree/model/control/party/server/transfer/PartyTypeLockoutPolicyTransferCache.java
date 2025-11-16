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

import com.echothree.model.control.party.common.transfer.PartyTypeLockoutPolicyTransfer;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.uom.common.UomConstants;
import com.echothree.model.control.uom.server.control.UomControl;
import com.echothree.model.data.party.server.entity.PartyTypeLockoutPolicy;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureKind;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class PartyTypeLockoutPolicyTransferCache
        extends BasePartyTransferCache<PartyTypeLockoutPolicy, PartyTypeLockoutPolicyTransfer> {

    PartyControl partyControl = Session.getModelController(PartyControl.class);
    UomControl uomControl = Session.getModelController(UomControl.class);

    UnitOfMeasureKind timeUnitOfMeasureKind = uomControl.getUnitOfMeasureKindByUnitOfMeasureKindUseTypeUsingNames(UomConstants.UnitOfMeasureKindUseType_TIME);

    /** Creates a new instance of PartyTypeLockoutPolicyTransferCache */
    protected PartyTypeLockoutPolicyTransferCache() {
        super();
        
        setIncludeEntityInstance(true);
    }

    @Override
    public PartyTypeLockoutPolicyTransfer getTransfer(UserVisit userVisit, PartyTypeLockoutPolicy partyTypeLockoutPolicy) {
        var partyTypeLockoutPolicyTransfer = get(partyTypeLockoutPolicy);

        if(partyTypeLockoutPolicyTransfer == null) {
            var partyTypeLockoutPolicyDetail = partyTypeLockoutPolicy.getLastDetail();
            var partyTypeTransfer = partyControl.getPartyTypeTransfer(userVisit, partyTypeLockoutPolicyDetail.getPartyType());
            var lockoutFailureCount = partyTypeLockoutPolicyDetail.getLockoutFailureCount();
            var unformattedResetFailureCountTime = partyTypeLockoutPolicyDetail.getResetFailureCountTime();
            var resetFailureCountTime = formatUnitOfMeasure(userVisit, timeUnitOfMeasureKind, unformattedResetFailureCountTime);
            var manualLockoutReset = partyTypeLockoutPolicyDetail.getManualLockoutReset();
            var unformattedLockoutInactiveTime = partyTypeLockoutPolicyDetail.getLockoutInactiveTime();
            var lockoutInactiveTime = formatUnitOfMeasure(userVisit, timeUnitOfMeasureKind, unformattedLockoutInactiveTime);

            partyTypeLockoutPolicyTransfer = new PartyTypeLockoutPolicyTransfer(partyTypeTransfer, lockoutFailureCount, unformattedResetFailureCountTime,
                    resetFailureCountTime, manualLockoutReset, unformattedLockoutInactiveTime, lockoutInactiveTime);
            put(userVisit, partyTypeLockoutPolicy, partyTypeLockoutPolicyTransfer);
        }

        return partyTypeLockoutPolicyTransfer;
    }
}