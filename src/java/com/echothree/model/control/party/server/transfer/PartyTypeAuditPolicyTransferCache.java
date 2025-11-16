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

import com.echothree.model.control.party.common.transfer.PartyTypeAuditPolicyTransfer;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.uom.common.UomConstants;
import com.echothree.model.control.uom.server.control.UomControl;
import com.echothree.model.data.party.server.entity.PartyTypeAuditPolicy;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureKind;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class PartyTypeAuditPolicyTransferCache
        extends BasePartyTransferCache<PartyTypeAuditPolicy, PartyTypeAuditPolicyTransfer> {

    PartyControl partyControl = Session.getModelController(PartyControl.class);
    UomControl uomControl = Session.getModelController(UomControl.class);

    UnitOfMeasureKind timeUnitOfMeasureKind = uomControl.getUnitOfMeasureKindByUnitOfMeasureKindUseTypeUsingNames(UomConstants.UnitOfMeasureKindUseType_TIME);

    /** Creates a new instance of PartyTypeAuditPolicyTransferCache */
    protected PartyTypeAuditPolicyTransferCache() {
        super();

        setIncludeEntityInstance(true);
    }

    @Override
    public PartyTypeAuditPolicyTransfer getTransfer(UserVisit userVisit, PartyTypeAuditPolicy partyTypeAuditPolicy) {
        var partyTypeAuditPolicyTransfer = get(partyTypeAuditPolicy);

        if(partyTypeAuditPolicyTransfer == null) {
            var partyTypeAuditPolicyDetail = partyTypeAuditPolicy.getLastDetail();
            var partyTypeTransfer = partyControl.getPartyTypeTransfer(userVisit, partyTypeAuditPolicyDetail.getPartyType());
            var auditCommands = partyTypeAuditPolicyDetail.getAuditCommands();
            var unformattedRetainUserVisitsTime = partyTypeAuditPolicyDetail.getRetainUserVisitsTime();
            var retainUserVisitsTime = formatUnitOfMeasure(userVisit, timeUnitOfMeasureKind, unformattedRetainUserVisitsTime);

            partyTypeAuditPolicyTransfer = new PartyTypeAuditPolicyTransfer(partyTypeTransfer, auditCommands, unformattedRetainUserVisitsTime,
                    retainUserVisitsTime);
            put(userVisit, partyTypeAuditPolicy, partyTypeAuditPolicyTransfer);
        }

        return partyTypeAuditPolicyTransfer;
    }
    
}