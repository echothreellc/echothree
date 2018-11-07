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

import com.echothree.model.control.party.common.transfer.PartyTypeLockoutPolicyTransfer;
import com.echothree.model.control.party.common.transfer.PartyTypeTransfer;
import com.echothree.model.control.party.server.PartyControl;
import com.echothree.model.control.uom.common.UomConstants;
import com.echothree.model.control.uom.server.UomControl;
import com.echothree.model.data.party.server.entity.PartyTypeLockoutPolicy;
import com.echothree.model.data.party.server.entity.PartyTypeLockoutPolicyDetail;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureKind;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class PartyTypeLockoutPolicyTransferCache
        extends BasePartyTransferCache<PartyTypeLockoutPolicy, PartyTypeLockoutPolicyTransfer> {

    UomControl uomControl = (UomControl)Session.getModelController(UomControl.class);
    UnitOfMeasureKind timeUnitOfMeasureKind = uomControl.getUnitOfMeasureKindByUnitOfMeasureKindUseTypeUsingNames(UomConstants.UnitOfMeasureKindUseType_TIME);

    /** Creates a new instance of PartyTypeLockoutPolicyTransferCache */
    public PartyTypeLockoutPolicyTransferCache(UserVisit userVisit, PartyControl partyControl) {
        super(userVisit, partyControl);
        
        setIncludeEntityInstance(true);
    }

    public PartyTypeLockoutPolicyTransfer getPartyTypeLockoutPolicyTransfer(PartyTypeLockoutPolicy partyTypeLockoutPolicy) {
        PartyTypeLockoutPolicyTransfer partyTypeLockoutPolicyTransfer = get(partyTypeLockoutPolicy);

        if(partyTypeLockoutPolicyTransfer == null) {
            PartyTypeLockoutPolicyDetail partyTypeLockoutPolicyDetail = partyTypeLockoutPolicy.getLastDetail();
            PartyTypeTransfer partyTypeTransfer = partyControl.getPartyTypeTransfer(userVisit, partyTypeLockoutPolicyDetail.getPartyType());
            Integer lockoutFailureCount = partyTypeLockoutPolicyDetail.getLockoutFailureCount();
            Long unformattedResetFailureCountTime = partyTypeLockoutPolicyDetail.getResetFailureCountTime();
            String resetFailureCountTime = formatUnitOfMeasure(timeUnitOfMeasureKind, unformattedResetFailureCountTime);
            Boolean manualLockoutReset = partyTypeLockoutPolicyDetail.getManualLockoutReset();
            Long unformattedLockoutInactiveTime = partyTypeLockoutPolicyDetail.getLockoutInactiveTime();
            String lockoutInactiveTime = formatUnitOfMeasure(timeUnitOfMeasureKind, unformattedLockoutInactiveTime);

            partyTypeLockoutPolicyTransfer = new PartyTypeLockoutPolicyTransfer(partyTypeTransfer, lockoutFailureCount, unformattedResetFailureCountTime,
                    resetFailureCountTime, manualLockoutReset, unformattedLockoutInactiveTime, lockoutInactiveTime);
            put(partyTypeLockoutPolicy, partyTypeLockoutPolicyTransfer);
        }

        return partyTypeLockoutPolicyTransfer;
    }
}