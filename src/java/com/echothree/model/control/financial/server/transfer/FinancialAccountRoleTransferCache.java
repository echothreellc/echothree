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

package com.echothree.model.control.financial.server.transfer;

import com.echothree.model.control.financial.common.transfer.FinancialAccountRoleTransfer;
import com.echothree.model.control.financial.server.control.FinancialControl;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.data.financial.server.entity.FinancialAccountRole;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class FinancialAccountRoleTransferCache
        extends BaseFinancialTransferCache<FinancialAccountRole, FinancialAccountRoleTransfer> {

    FinancialControl financialControl = Session.getModelController(FinancialControl.class);
    PartyControl partyControl = Session.getModelController(PartyControl.class);

    /** Creates a new instance of FinancialAccountRoleTransferCache */
    public FinancialAccountRoleTransferCache() {
        super();
    }

    public FinancialAccountRoleTransfer getFinancialAccountRoleTransfer(UserVisit userVisit, FinancialAccountRole financialAccountRole) {
        var financialAccountRoleTransfer = get(financialAccountRole);

        if(financialAccountRoleTransfer == null) {
            var financialAccount = financialControl.getFinancialAccountTransfer(userVisit, financialAccountRole.getFinancialAccount());
            var party = partyControl.getPartyTransfer(userVisit, financialAccountRole.getParty());
            var financialAccountRoleType = financialControl.getFinancialAccountRoleTypeTransfer(userVisit, financialAccountRole.getFinancialAccountRoleType());

            financialAccountRoleTransfer = new FinancialAccountRoleTransfer(financialAccount, party, financialAccountRoleType);
            put(userVisit, financialAccountRole, financialAccountRoleTransfer);
        }

        return financialAccountRoleTransfer;
    }
}