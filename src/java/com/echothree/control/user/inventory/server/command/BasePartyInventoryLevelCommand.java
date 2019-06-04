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

package com.echothree.control.user.inventory.server.command;

import com.echothree.control.user.inventory.common.spec.PartyInventoryLevelSpec;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.party.server.PartyControl;
import com.echothree.model.control.warehouse.server.WarehouseControl;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.party.server.entity.PartyCompany;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.model.data.warehouse.server.entity.Warehouse;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.persistence.Session;
import java.util.List;

public abstract class BasePartyInventoryLevelCommand<F
        extends PartyInventoryLevelSpec> extends BaseSimpleCommand<F> {
    
    protected BasePartyInventoryLevelCommand(UserVisitPK userVisitPK, F form, List<FieldDefinition> FORM_FIELD_DEFINITIONS) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, false);
    }
    
    protected String getPartyTypeName(final Party party) {
        return party.getLastDetail().getPartyType().getPartyTypeName();
    }
    
    protected Party getParty(final String partyName, final String companyName, final String warehouseName) {
        Party party = null;
        
        if(partyName != null || companyName != null) {
            var partyControl = (PartyControl)Session.getModelController(PartyControl.class);
            
            if(partyName != null) {
                party = partyControl.getPartyByName(partyName);
                
                if(party != null) {
                    String partyTypeName = getPartyTypeName(party);
                    
                    if(!partyTypeName.equals(PartyConstants.PartyType_COMPANY)
                    && !partyTypeName.equals(PartyConstants.PartyType_WAREHOUSE)) {
                        party = null;
                        addExecutionError(ExecutionErrors.InvalidPartyType.name());
                    }
                }  else {
                    addExecutionError(ExecutionErrors.UnknownPartyName.name(), partyName);
                }
            } else if(companyName != null) {
                PartyCompany partyCompany = partyControl.getPartyCompanyByName(companyName);
                
                if(partyCompany != null) {
                    party = partyCompany.getParty();
                } else {
                    addExecutionError(ExecutionErrors.UnknownCompanyName.name(), companyName);
                }
            }
        } else if(warehouseName != null) {
            var warehouseControl = (WarehouseControl)Session.getModelController(WarehouseControl.class);
            Warehouse warehouse = warehouseControl.getWarehouseByName(warehouseName);
            
            if(warehouse != null) {
                party = warehouse.getParty();
            } else {
                addExecutionError(ExecutionErrors.UnknownWarehouseName.name(), warehouseName);
            }
        }
        return party;
    }
    
    protected Party getParty(PartyInventoryLevelSpec spec) {
        String partyName = spec.getPartyName();
        String companyName = spec.getCompanyName();
        String warehouseName = spec.getWarehouseName();
        int parameterCount = (partyName == null? 0: 1) + (companyName == null? 0: 1) + (warehouseName == null? 0: 1);
        Party party = null;
        
        if(parameterCount == 1) {
            party = getParty(partyName, companyName, warehouseName);
        }  else {
            addExecutionError(ExecutionErrors.InvalidParameterCount.name());
        }
        
        return party;
    }

}
