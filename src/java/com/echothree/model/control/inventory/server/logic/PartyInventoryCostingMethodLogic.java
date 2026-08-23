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

package com.echothree.model.control.inventory.server.logic;

import com.echothree.model.control.inventory.server.control.InventoryCostingMethodControl;
import com.echothree.model.data.inventory.server.entity.InventoryCostingMethod;
import com.echothree.model.data.inventory.server.entity.PartyInventoryCostingMethod;
import com.echothree.model.data.inventory.server.value.PartyInventoryCostingMethodValue;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;
import javax.inject.Inject;

@ApplicationScoped
public class PartyInventoryCostingMethodLogic
        extends BaseLogic {

    @Inject
    InventoryCostingMethodControl inventoryCostingMethodControl;

    @Inject
    InventoryCostingMethodLogic inventoryCostingMethodLogic;

    protected PartyInventoryCostingMethodLogic() {
        super();
    }

    public PartyInventoryCostingMethod createPartyInventoryCostingMethod(final Party party,
            final InventoryCostingMethod inventoryCostingMethod, final BasePK createdBy) {
        return inventoryCostingMethodControl.createPartyInventoryCostingMethod(party, inventoryCostingMethod, createdBy);
    }

    public PartyInventoryCostingMethod getPartyInventoryCostingMethod(final ExecutionErrorAccumulator eea,
            final Party party, final boolean allowDefault, final BasePK createdBy) {
        var partyInventoryCostingMethod = inventoryCostingMethodControl.getPartyInventoryCostingMethod(party);

        if(partyInventoryCostingMethod == null && allowDefault) {
            var inventoryCostingMethod = inventoryCostingMethodLogic.getDefaultInventoryCostingMethod(eea);

            if(inventoryCostingMethod != null) {
                partyInventoryCostingMethod = createPartyInventoryCostingMethod(party, inventoryCostingMethod, createdBy);
            }
        }

        return partyInventoryCostingMethod;
    }

    public void updatePartyInventoryCostingMethodFromValue(
            final PartyInventoryCostingMethodValue partyInventoryCostingMethodValue, final BasePK updatedBy) {
        inventoryCostingMethodControl.updatePartyInventoryCostingMethodFromValue(partyInventoryCostingMethodValue, updatedBy);
    }

    public void deletePartyInventoryCostingMethod(final PartyInventoryCostingMethod partyInventoryCostingMethod,
            final BasePK deletedBy) {
        inventoryCostingMethodControl.deletePartyInventoryCostingMethod(partyInventoryCostingMethod, deletedBy);
    }
}
