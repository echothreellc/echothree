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

package com.echothree.control.user.inventory.server.command.common;

import com.echothree.control.user.inventory.common.spec.PartyInventoryLevelSpec;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.warehouse.server.control.WarehouseControl;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class PartyInventoryLevelUtil {

    @Inject
    PartyControl partyControl;
    
    @Inject
    WarehouseControl warehouseControl;

    protected PartyInventoryLevelUtil() {
        super();
    }

    public String getPartyTypeName(final Party party) {
        return party.getLastDetail().getPartyType().getPartyTypeName();
    }

    public Party getParty(final ExecutionErrorAccumulator eea, final String partyName, final String companyName, final String warehouseName) {
        Party party = null;
        
        if(partyName != null || companyName != null) {
            if(partyName != null) {
                party = partyControl.getPartyByName(partyName);
                
                if(party != null) {
                    var partyTypeName = getPartyTypeName(party);
                    
                    if(!partyTypeName.equals(PartyTypes.COMPANY.name())
                    && !partyTypeName.equals(PartyTypes.WAREHOUSE.name())) {
                        party = null;
                        eea.addExecutionError(ExecutionErrors.InvalidPartyType.name());
                    }
                }  else {
                    eea.addExecutionError(ExecutionErrors.UnknownPartyName.name(), partyName);
                }
            } else if(companyName != null) {
                var partyCompany = partyControl.getPartyCompanyByName(companyName);
                
                if(partyCompany != null) {
                    party = partyCompany.getParty();
                } else {
                    eea.addExecutionError(ExecutionErrors.UnknownCompanyName.name(), companyName);
                }
            }
        } else if(warehouseName != null) {
            var warehouse = warehouseControl.getWarehouseByName(warehouseName);
            
            if(warehouse != null) {
                party = warehouse.getParty();
            } else {
                eea.addExecutionError(ExecutionErrors.UnknownWarehouseName.name(), warehouseName);
            }
        }
        return party;
    }

    public Party getParty(final ExecutionErrorAccumulator eea, PartyInventoryLevelSpec spec) {
        var partyName = spec.getPartyName();
        var companyName = spec.getCompanyName();
        var warehouseName = spec.getWarehouseName();
        var parameterCount = (partyName == null ? 0 : 1) + (companyName == null ? 0 : 1) + (warehouseName == null ? 0 : 1);
        Party party = null;
        
        if(parameterCount == 1) {
            party = getParty(eea, partyName, companyName, warehouseName);
        }  else {
            eea.addExecutionError(ExecutionErrors.InvalidParameterCount.name());
        }
        
        return party;
    }

}
