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

import com.echothree.control.user.inventory.common.spec.InventoryCostingPoolUniversalSpec;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.inventory.common.exception.DuplicateInventoryCostingPoolException;
import com.echothree.model.control.inventory.common.exception.UnknownInventoryCostingPoolException;
import com.echothree.model.control.inventory.server.control.InventoryCostingPoolControl;
import com.echothree.model.control.item.server.logic.ItemLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.party.server.logic.CompanyLogic;
import com.echothree.model.control.party.server.logic.PartyLogic;
import com.echothree.model.data.inventory.server.entity.InventoryCondition;
import com.echothree.model.data.inventory.server.entity.InventoryCostingPool;
import com.echothree.model.data.item.server.entity.Item;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class InventoryCostingPoolLogic
        extends BaseLogic {

    @Inject
    InventoryCostingPoolControl inventoryCostingPoolControl;

    @Inject
    PartyControl partyControl;

    @Inject
    CompanyLogic companyLogic;

    @Inject
    EntityInstanceLogic entityInstanceLogic;

    @Inject
    InventoryConditionLogic inventoryConditionLogic;

    @Inject
    ItemLogic itemLogic;

    @Inject
    PartyLogic partyLogic;

    protected InventoryCostingPoolLogic() {
        super();
    }

    public InventoryCostingPool createInventoryCostingPool(final ExecutionErrorAccumulator eea, final Party companyParty,
            final Item item, final InventoryCondition inventoryCondition, final BasePK createdBy) {
        InventoryCostingPool inventoryCostingPool = null;

        partyLogic.checkPartyType(eea, companyParty, PartyTypes.COMPANY.name());

        if(eea == null || !eea.hasExecutionErrors()) {
            inventoryCostingPool = inventoryCostingPoolControl.getInventoryCostingPool(companyParty, item, inventoryCondition);

            if(inventoryCostingPool == null) {
                inventoryCostingPool = inventoryCostingPoolControl.createInventoryCostingPool(companyParty, item, inventoryCondition, createdBy);
            } else {
                handleExecutionError(DuplicateInventoryCostingPoolException.class, eea, ExecutionErrors.DuplicateInventoryCostingPool.name(),
                        partyControl.getPartyCompany(companyParty).getPartyCompanyName(), item.getLastDetail().getItemName(),
                        inventoryCondition.getLastDetail().getInventoryConditionName());
            }
        }

        return inventoryCostingPool;
    }

    public InventoryCostingPool getInventoryCostingPoolByName(final ExecutionErrorAccumulator eea, final String companyName,
            final String partyName, final String itemName, final String inventoryConditionName, final EntityPermission entityPermission) {
        var company = companyLogic.getPartyCompanyByName(eea, companyName, partyName, null, true);
        var item = itemLogic.getItemByName(eea, itemName);
        var inventoryCondition = inventoryConditionLogic.getInventoryConditionByName(eea, inventoryConditionName);
        InventoryCostingPool inventoryCostingPool = null;

        if(eea == null || !eea.hasExecutionErrors()) {
            inventoryCostingPool = inventoryCostingPoolControl.getInventoryCostingPool(company.getParty(), item,
                    inventoryCondition, entityPermission);

            if(inventoryCostingPool == null) {
                handleExecutionError(UnknownInventoryCostingPoolException.class, eea, ExecutionErrors.UnknownInventoryCostingPool.name(),
                        company.getPartyCompanyName(), itemName, inventoryConditionName);
            }
        }

        return inventoryCostingPool;
    }

    public InventoryCostingPool getInventoryCostingPoolByName(final ExecutionErrorAccumulator eea, final String companyName,
            final String partyName, final String itemName, final String inventoryConditionName) {
        return getInventoryCostingPoolByName(eea, companyName, partyName, itemName, inventoryConditionName, EntityPermission.READ_ONLY);
    }

    public InventoryCostingPool getInventoryCostingPool(final Party companyParty, final Item item,
            final InventoryCondition inventoryCondition, final EntityPermission entityPermission) {
        return inventoryCostingPoolControl.getInventoryCostingPool(companyParty, item, inventoryCondition, entityPermission);
    }

    public InventoryCostingPool getInventoryCostingPool(final Party companyParty, final Item item,
            final InventoryCondition inventoryCondition) {
        return getInventoryCostingPool(companyParty, item, inventoryCondition, EntityPermission.READ_ONLY);
    }

    public InventoryCostingPool getInventoryCostingPoolByUniversalSpec(final ExecutionErrorAccumulator eea,
            final InventoryCostingPoolUniversalSpec universalSpec, final EntityPermission entityPermission) {
        var companyName = universalSpec.getCompanyName();
        var partyName = universalSpec.getPartyName();
        var itemName = universalSpec.getItemName();
        var inventoryConditionName = universalSpec.getInventoryConditionName();
        var companyParameterCount = (companyName == null ? 0 : 1) + (partyName == null ? 0 : 1);
        var hasNameParameters = companyParameterCount != 0 || itemName != null || inventoryConditionName != null;
        var hasCompleteName = companyParameterCount == 1 && itemName != null && inventoryConditionName != null;
        var entitySpecCount = entityInstanceLogic.countPossibleEntitySpecs(universalSpec);
        InventoryCostingPool inventoryCostingPool = null;

        if(hasCompleteName && entitySpecCount == 0) {
            inventoryCostingPool = getInventoryCostingPoolByName(eea, companyName, partyName, itemName, inventoryConditionName, entityPermission);
        } else if(!hasNameParameters && entitySpecCount == 1) {
            var entityInstance = entityInstanceLogic.getEntityInstance(eea, universalSpec,
                    ComponentVendors.ECHO_THREE.name(), EntityTypes.InventoryCostingPool.name());

            if(eea == null || !eea.hasExecutionErrors()) {
                inventoryCostingPool = inventoryCostingPoolControl.getInventoryCostingPoolByEntityInstance(entityInstance, entityPermission);
            }
        } else {
            handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
        }

        return inventoryCostingPool;
    }

    public InventoryCostingPool getInventoryCostingPoolByUniversalSpec(final ExecutionErrorAccumulator eea,
            final InventoryCostingPoolUniversalSpec universalSpec) {
        return getInventoryCostingPoolByUniversalSpec(eea, universalSpec, EntityPermission.READ_ONLY);
    }

    public InventoryCostingPool getInventoryCostingPoolByUniversalSpecForUpdate(final ExecutionErrorAccumulator eea,
            final InventoryCostingPoolUniversalSpec universalSpec) {
        return getInventoryCostingPoolByUniversalSpec(eea, universalSpec, EntityPermission.READ_WRITE);
    }

    public void deleteInventoryCostingPool(final InventoryCostingPool inventoryCostingPool, final BasePK deletedBy) {
        inventoryCostingPoolControl.deleteInventoryCostingPool(inventoryCostingPool, deletedBy);
    }

}
