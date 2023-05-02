// --------------------------------------------------------------------------------
// Copyright 2002-2023 Echo Three, LLC
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

package com.echothree.model.control.warehouse.server.logic;

import com.echothree.control.user.core.common.spec.UniversalEntitySpec;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.common.exception.UnknownPartyNameException;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.party.server.logic.PartyLogic;
import com.echothree.model.control.warehouse.common.exception.UnknownWarehouseNameException;
import com.echothree.model.control.warehouse.server.control.WarehouseControl;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.warehouse.server.entity.Warehouse;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.Session;

public class WarehouseLogic
        extends BaseLogic {
    
    private WarehouseLogic() {
        super();
    }
    
    private static class WarehouseLogicHolder {
        static WarehouseLogic instance = new WarehouseLogic();
    }
    
    public static WarehouseLogic getInstance() {
        return WarehouseLogicHolder.instance;
    }

    public Warehouse getWarehouseByName(final ExecutionErrorAccumulator eea, final String warehouseName,
            final String partyName, final UniversalEntitySpec universalEntitySpec, final boolean required) {
        var parameterCount = (warehouseName == null ? 0 : 1) + (partyName == null ? 0 : 1) +
                EntityInstanceLogic.getInstance().countPossibleEntitySpecs(universalEntitySpec);
        Warehouse warehouse = null;

        if(parameterCount == 1) {
            var warehouseControl = Session.getModelController(WarehouseControl.class);

            if(warehouseName != null) {
                warehouse = warehouseControl.getWarehouseByName(warehouseName);

                if(warehouse == null) {
                    handleExecutionError(UnknownWarehouseNameException.class, eea, ExecutionErrors.UnknownWarehouseName.name(), warehouseName);
                }
            } else if(partyName != null) {
                var partyControl = Session.getModelController(PartyControl.class);
                Party party = partyControl.getPartyByName(partyName);

                if(party != null) {
                    PartyLogic.getInstance().checkPartyType(eea, party, PartyTypes.COMPANY.name());

                    warehouse = warehouseControl.getWarehouse(party);
                } else {
                    handleExecutionError(UnknownPartyNameException.class, eea, ExecutionErrors.UnknownPartyName.name(), partyName);
                }
            } else if(universalEntitySpec != null) {
                var partyControl = Session.getModelController(PartyControl.class);
                var entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(eea, universalEntitySpec,
                        ComponentVendors.ECHO_THREE.name(), EntityTypes.Party.name());

                if(!eea.hasExecutionErrors()) {
                    var party = partyControl.getPartyByEntityInstance(entityInstance);

                    PartyLogic.getInstance().checkPartyType(eea, party, PartyTypes.WAREHOUSE.name());

                    warehouse = warehouseControl.getWarehouse(party);
                }
            }
        } else {
            if(required) {
                handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
            }
        }

        return warehouse;
    }

}
