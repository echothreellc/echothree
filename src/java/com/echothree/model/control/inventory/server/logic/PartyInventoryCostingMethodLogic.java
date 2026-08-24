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

import com.echothree.model.control.inventory.common.exception.DuplicatePartyInventoryCostingMethodException;
import com.echothree.model.control.inventory.common.exception.UnknownPartyInventoryCostingMethodException;
import com.echothree.model.control.inventory.server.control.InventoryCostingMethodControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.logic.PartyLogic;
import com.echothree.model.data.inventory.server.entity.InventoryCostingMethod;
import com.echothree.model.data.inventory.server.entity.PartyInventoryCostingMethod;
import com.echothree.model.data.inventory.server.value.PartyInventoryCostingMethodValue;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
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

    @Inject
    PartyLogic partyLogic;

    protected PartyInventoryCostingMethodLogic() {
        super();
    }

    public PartyInventoryCostingMethod createPartyInventoryCostingMethod(final ExecutionErrorAccumulator eea,
            final Party party, final InventoryCostingMethod inventoryCostingMethod, final BasePK createdBy) {
        partyLogic.checkPartyType(eea, party, PartyTypes.COMPANY.name());

        var partyInventoryCostingMethod = eea == null || !eea.hasExecutionErrors()
                ? inventoryCostingMethodControl.getPartyInventoryCostingMethod(party) : null;

        if(partyInventoryCostingMethod == null) {
            if(eea == null || !eea.hasExecutionErrors()) {
                partyInventoryCostingMethod = inventoryCostingMethodControl.createPartyInventoryCostingMethod(party,
                        inventoryCostingMethod, createdBy);
            }
        } else {
            handleExecutionError(DuplicatePartyInventoryCostingMethodException.class, eea,
                    ExecutionErrors.DuplicatePartyInventoryCostingMethod.name(), party.getLastDetail().getPartyName());
        }

        return partyInventoryCostingMethod;
    }

    public PartyInventoryCostingMethod getPartyInventoryCostingMethod(final ExecutionErrorAccumulator eea,
            final Party party, final boolean allowDefault, final BasePK createdBy,
            final EntityPermission entityPermission) {
        var partyInventoryCostingMethod = inventoryCostingMethodControl.getPartyInventoryCostingMethod(party,
                entityPermission);

        if(partyInventoryCostingMethod == null) {
            if(allowDefault) {
                partyLogic.checkPartyType(eea, party, PartyTypes.COMPANY.name());

                if(eea == null || !eea.hasExecutionErrors()) {
                    var inventoryCostingMethod = inventoryCostingMethodLogic.getDefaultInventoryCostingMethod(eea);

                    if(inventoryCostingMethod != null) {
                        partyInventoryCostingMethod = createPartyInventoryCostingMethod(eea, party,
                                inventoryCostingMethod, createdBy);
                    }
                }
            } else {
                handleExecutionError(UnknownPartyInventoryCostingMethodException.class, eea,
                        ExecutionErrors.UnknownPartyInventoryCostingMethod.name(), party.getLastDetail().getPartyName());
            }
        }

        return partyInventoryCostingMethod;
    }

    public PartyInventoryCostingMethod getPartyInventoryCostingMethod(final ExecutionErrorAccumulator eea,
            final Party party, final boolean allowDefault, final BasePK createdBy) {
        return getPartyInventoryCostingMethod(eea, party, allowDefault, createdBy, EntityPermission.READ_ONLY);
    }

    public PartyInventoryCostingMethod getPartyInventoryCostingMethodForUpdate(final ExecutionErrorAccumulator eea,
            final Party party, final boolean allowDefault, final BasePK createdBy) {
        return getPartyInventoryCostingMethod(eea, party, allowDefault, createdBy, EntityPermission.READ_WRITE);
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
