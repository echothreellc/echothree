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

package com.echothree.model.control.warehouse.server.logic;

import com.echothree.control.user.core.common.spec.UniversalEntitySpec;
import com.echothree.control.user.warehouse.common.spec.WarehouseUniversalSpec;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.common.exception.UnknownPartyNameException;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.party.server.logic.PartyLogic;
import com.echothree.model.control.warehouse.common.exception.DuplicateWarehouseNameException;
import com.echothree.model.control.warehouse.common.exception.UnknownDefaultWarehouseException;
import com.echothree.model.control.warehouse.common.exception.UnknownWarehouseNameException;
import com.echothree.model.control.warehouse.server.control.WarehouseControl;
import com.echothree.model.data.accounting.server.entity.Currency;
import com.echothree.model.data.party.server.entity.DateTimeFormat;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.party.server.entity.TimeZone;
import com.echothree.model.data.warehouse.server.entity.Warehouse;
import com.echothree.model.data.warehouse.server.entity.WarehouseType;
import com.echothree.model.data.warehouse.server.value.WarehouseValue;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;

@ApplicationScoped
public class WarehouseLogic
        extends BaseLogic {

    protected WarehouseLogic() {
        super();
    }

    public static WarehouseLogic getInstance() {
        return CDI.current().select(WarehouseLogic.class).get();
    }

    public Warehouse createWarehouse(final ExecutionErrorAccumulator eea, final String warehouseName, final WarehouseType warehouseType,
            final Language preferredLanguage, final Currency preferredCurrency, final TimeZone preferredTimeZone,
            final DateTimeFormat preferredDateTimeFormat, final String name, final Boolean isDefault, final Integer sortOrder,
            final BasePK createdBy) {
        var warehouseControl = Session.getModelController(WarehouseControl.class);
        var warehouse = warehouseControl.getWarehouseByName(warehouseName);

        if(warehouse == null) {
            var partyControl = Session.getModelController(PartyControl.class);
            var partyType = partyControl.getPartyTypeByName(PartyTypes.WAREHOUSE.name());
            var party = partyControl.createParty(null, partyType, preferredLanguage, preferredCurrency,
                    preferredTimeZone, preferredDateTimeFormat, createdBy);

            if(name != null) {
                partyControl.createPartyGroup(party, name, createdBy);
            }

            warehouse = warehouseControl.createWarehouse(party, warehouseName, warehouseType, isDefault, sortOrder, createdBy);
        } else {
            handleExecutionError(DuplicateWarehouseNameException.class, eea, ExecutionErrors.DuplicateWarehouseName.name(), warehouseName);
        }

        return warehouse;
    }

    public Warehouse getWarehouseByName(final ExecutionErrorAccumulator eea, final String warehouseName, final String partyName,
            final UniversalEntitySpec universalEntitySpec, final boolean allowDefault, final EntityPermission entityPermission) {
        Warehouse warehouse = null;
        var warehouseControl = Session.getModelController(WarehouseControl.class);
        var parameterCount = (warehouseName == null ? 0 : 1) + (partyName == null ? 0 : 1) +
                EntityInstanceLogic.getInstance().countPossibleEntitySpecs(universalEntitySpec);

        switch(parameterCount) {
            case 0 -> {
                if(allowDefault) {
                    warehouse = warehouseControl.getDefaultWarehouse(entityPermission);

                    if(warehouse == null) {
                        handleExecutionError(UnknownDefaultWarehouseException.class, eea, ExecutionErrors.UnknownDefaultWarehouse.name());
                    }
                } else {
                    handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
                }
            }
            case 1 -> {
                var partyControl = Session.getModelController(PartyControl.class);

                if(warehouseName != null) {
                    warehouse = warehouseControl.getWarehouseByName(warehouseName, entityPermission);

                    if(warehouse == null) {
                        handleExecutionError(UnknownWarehouseNameException.class, eea, ExecutionErrors.UnknownWarehouseName.name(), warehouseName);
                    }
                } else if(partyName != null) {
                    var party = partyControl.getPartyByName(partyName);

                    if(party != null) {
                        PartyLogic.getInstance().checkPartyType(eea, party, PartyTypes.WAREHOUSE.name());

                        warehouse = warehouseControl.getWarehouse(party, entityPermission);
                    } else {
                        handleExecutionError(UnknownPartyNameException.class, eea, ExecutionErrors.UnknownPartyName.name(), partyName);
                    }
                } else if(universalEntitySpec != null){
                    var entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(eea, universalEntitySpec,
                            ComponentVendors.ECHO_THREE.name(), EntityTypes.Party.name());

                    if(!eea.hasExecutionErrors()) {
                        var party = partyControl.getPartyByEntityInstance(entityInstance);

                        PartyLogic.getInstance().checkPartyType(eea, party, PartyTypes.WAREHOUSE.name());

                        warehouse = warehouseControl.getWarehouse(party, entityPermission);
                    }
                }
            }
            default ->
                    handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
        }

        return warehouse;
    }

    public Warehouse getWarehouseByName(final ExecutionErrorAccumulator eea, final String warehouseName, final String partyName,
            final UniversalEntitySpec universalEntitySpec, final boolean allowDefault) {
        return getWarehouseByName(eea, warehouseName, partyName, universalEntitySpec, allowDefault, EntityPermission.READ_ONLY);
    }

    public Warehouse getWarehouseByNameForUpdate(final ExecutionErrorAccumulator eea, final String warehouseName, final String partyName,
            final UniversalEntitySpec universalEntitySpec, final boolean allowDefault) {
        return getWarehouseByName(eea, warehouseName, partyName, universalEntitySpec, allowDefault, EntityPermission.READ_WRITE);
    }

    public Warehouse getWarehouseByUniversalSpec(final ExecutionErrorAccumulator eea, final WarehouseUniversalSpec universalSpec,
            final boolean allowDefault, final EntityPermission entityPermission) {
        return getWarehouseByName(eea, universalSpec.getWarehouseName(), universalSpec.getPartyName(), universalSpec, allowDefault, entityPermission);
    }

    public Warehouse getWarehouseByUniversalSpec(final ExecutionErrorAccumulator eea, final WarehouseUniversalSpec universalSpec,
            final boolean allowDefault) {
        return getWarehouseByName(eea, universalSpec.getWarehouseName(), universalSpec.getPartyName(), universalSpec, allowDefault);
    }

    public Warehouse getWarehouseByUniversalSpecForUpdate(final ExecutionErrorAccumulator eea, final WarehouseUniversalSpec universalSpec,
            final boolean allowDefault) {
        return getWarehouseByNameForUpdate(eea, universalSpec.getWarehouseName(), universalSpec.getPartyName(), universalSpec, allowDefault);
    }

    public void updateWarehouseFromValue(final ExecutionErrorAccumulator eea, final WarehouseValue warehouseValue,
            final BasePK updatedBy) {
        var warehouseControl = Session.getModelController(WarehouseControl.class);

        warehouseControl.updateWarehouseFromValue(warehouseValue, updatedBy);
    }

    public void deleteWarehouse(final ExecutionErrorAccumulator eea, final Warehouse warehouse, final BasePK deletedBy) {
        var warehouseControl = Session.getModelController(WarehouseControl.class);

        // TODO: Verify warehouse is empty

        warehouseControl.deleteWarehouse(warehouse, deletedBy);
    }

}
