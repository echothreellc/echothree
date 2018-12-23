// --------------------------------------------------------------------------------
// Copyright 2002-2019 Echo Three, LLC
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

package com.echothree.model.control.financial.server.transfer;

import com.echothree.model.control.financial.common.transfer.FinancialAccountRoleTransfer;
import com.echothree.model.control.financial.common.transfer.FinancialAccountRoleTypeTransfer;
import com.echothree.model.control.financial.common.transfer.FinancialAccountTransfer;
import com.echothree.model.control.financial.server.FinancialControl;
import com.echothree.model.control.party.common.transfer.PartyTransfer;
import com.echothree.model.control.party.server.PartyControl;
import com.echothree.model.data.financial.server.entity.FinancialAccountRole;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class FinancialAccountRoleTransferCache
        extends BaseFinancialTransferCache<FinancialAccountRole, FinancialAccountRoleTransfer> {

    PartyControl partyControl;

    /** Creates a new instance of FinancialAccountRoleTransferCache */
    public FinancialAccountRoleTransferCache(UserVisit userVisit, FinancialControl financialControl) {
        super(userVisit, financialControl);

        partyControl = (PartyControl)Session.getModelController(PartyControl.class);
    }

    public FinancialAccountRoleTransfer getFinancialAccountRoleTransfer(FinancialAccountRole financialAccountRole) {
        FinancialAccountRoleTransfer financialAccountRoleTransfer = get(financialAccountRole);

        if(financialAccountRoleTransfer == null) {
            FinancialAccountTransfer financialAccount = financialControl.getFinancialAccountTransfer(userVisit, financialAccountRole.getFinancialAccount());
            PartyTransfer party = partyControl.getPartyTransfer(userVisit, financialAccountRole.getParty());
            FinancialAccountRoleTypeTransfer financialAccountRoleType = financialControl.getFinancialAccountRoleTypeTransfer(userVisit, financialAccountRole.getFinancialAccountRoleType());

            financialAccountRoleTransfer = new FinancialAccountRoleTransfer(financialAccount, party, financialAccountRoleType);
            put(financialAccountRole, financialAccountRoleTransfer);
        }

        return financialAccountRoleTransfer;
    }
}