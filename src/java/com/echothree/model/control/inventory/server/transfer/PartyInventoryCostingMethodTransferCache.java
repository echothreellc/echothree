// --------------------------------------------------------------------------------
// Copyright 2002-2026 Echo Three, LLC
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

package com.echothree.model.control.inventory.server.transfer;

import com.echothree.model.control.inventory.common.InventoryOptions;
import com.echothree.model.control.inventory.common.transfer.PartyInventoryCostingMethodTransfer;
import com.echothree.model.control.inventory.server.control.InventoryCostingMethodControl;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.data.inventory.server.entity.PartyInventoryCostingMethod;
import com.echothree.model.data.user.server.entity.UserVisit;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

@RequestScoped
public class PartyInventoryCostingMethodTransferCache
        extends BaseInventoryTransferCache<PartyInventoryCostingMethod, PartyInventoryCostingMethodTransfer> {

    @Inject
    InventoryCostingMethodControl inventoryCostingMethodControl;

    @Inject
    PartyControl partyControl;

    boolean includeParty;

    protected PartyInventoryCostingMethodTransferCache() {
        super();

        var options = session.getOptions();
        if(options != null) {
            includeParty = options.contains(InventoryOptions.PartyInventoryCostingMethodIncludeParty);
        }
    }

    @Override
    public PartyInventoryCostingMethodTransfer getTransfer(UserVisit userVisit,
            PartyInventoryCostingMethod partyInventoryCostingMethod) {
        var transfer = get(partyInventoryCostingMethod);

        if(transfer == null) {
            var party = includeParty ? partyControl.getPartyTransfer(userVisit, partyInventoryCostingMethod.getParty()) : null;
            var inventoryCostingMethod = inventoryCostingMethodControl.getInventoryCostingMethodTransfer(userVisit,
                    partyInventoryCostingMethod.getInventoryCostingMethod());

            transfer = new PartyInventoryCostingMethodTransfer(party, inventoryCostingMethod);
            put(userVisit, partyInventoryCostingMethod, transfer);
        }

        return transfer;
    }
}
