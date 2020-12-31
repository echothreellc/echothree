// --------------------------------------------------------------------------------
// Copyright 2002-2021 Echo Three, LLC
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

import com.echothree.control.user.inventory.common.spec.LotUniversalSpec;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.inventory.common.exception.DuplicateLotNameException;
import com.echothree.model.control.inventory.common.exception.UnknownLotNameException;
import com.echothree.model.control.inventory.server.control.LotControl;
import com.echothree.model.control.sequence.common.SequenceTypes;
import com.echothree.model.control.sequence.server.logic.SequenceGeneratorLogic;
import com.echothree.model.data.accounting.server.entity.Currency;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.inventory.server.entity.InventoryCondition;
import com.echothree.model.data.inventory.server.entity.Lot;
import com.echothree.model.data.item.server.entity.Item;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureType;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;

public class LotLogic
        extends BaseLogic {

    private LotLogic() {
        super();
    }

    private static class LotTimeLogicHolder {
        static LotLogic instance = new LotLogic();
    }

    public static LotLogic getInstance() {
        return LotTimeLogicHolder.instance;
    }

    public Lot createLot(final ExecutionErrorAccumulator eea, String lotName, final Party ownerParty, final Item item,
            final UnitOfMeasureType unitOfMeasureType, final InventoryCondition inventoryCondition, final Long quantity,
            final Currency currency, final Long unitCost, final BasePK createdBy) {
        Lot lot = null;

        if(lotName == null) {
            lotName = SequenceGeneratorLogic.getInstance().getNextSequenceValue(eea, SequenceTypes.LOT.name());
        }

        if(!eea.hasExecutionErrors()) {
            var lotControl = Session.getModelController(LotControl.class);

            lot = lotControl.getLotByName(lotName);
            if(lot == null) {
                lot = lotControl.createLot(lotName, ownerParty, item, unitOfMeasureType, inventoryCondition, quantity,
                        currency, unitCost, createdBy);
            } else {
                handleExecutionError(DuplicateLotNameException.class, eea, ExecutionErrors.DuplicateLotName.name(), lotName);
            }
        }

        return lot;
    }

    public Lot getLotByName(final ExecutionErrorAccumulator eea, final String lotName,
            final EntityPermission entityPermission) {
        var lotControl = Session.getModelController(LotControl.class);
        Lot lot = lotControl.getLotByName(lotName, entityPermission);

        if(lot == null) {
            handleExecutionError(UnknownLotNameException.class, eea, ExecutionErrors.UnknownLotName.name(), lotName);
        }

        return lot;
    }

    public Lot getLotByName(final ExecutionErrorAccumulator eea, final String lotName) {
        return getLotByName(eea, lotName, EntityPermission.READ_ONLY);
    }

    public Lot getLotByNameForUpdate(final ExecutionErrorAccumulator eea, final String lotName) {
        return getLotByName(eea, lotName, EntityPermission.READ_WRITE);
    }

    public Lot getLotByUniversalSpec(final ExecutionErrorAccumulator eea, final LotUniversalSpec universalSpec,
            final EntityPermission entityPermission) {
        Lot lot = null;
        var lotControl = Session.getModelController(LotControl.class);
        String lotName = universalSpec.getLotName();
        int parameterCount = (lotName == null ? 0 : 1) + EntityInstanceLogic.getInstance().countPossibleEntitySpecs(universalSpec);

        switch(parameterCount) {
            case 1:
                if(lotName == null) {
                    EntityInstance entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(eea, universalSpec,
                            ComponentVendors.ECHOTHREE.name(), EntityTypes.Lot.name());

                    if(!eea.hasExecutionErrors()) {
                        lot = lotControl.getLotByEntityInstance(entityInstance, entityPermission);
                    }
                } else {
                    lot = getLotByName(eea, lotName, entityPermission);
                }
                break;
            default:
                handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
                break;
        }

        return lot;
    }

    public Lot getLotByUniversalSpec(final ExecutionErrorAccumulator eea,
            final LotUniversalSpec universalSpec) {
        return getLotByUniversalSpec(eea, universalSpec, EntityPermission.READ_ONLY);
    }

    public Lot getLotByUniversalSpecForUpdate(final ExecutionErrorAccumulator eea,
            final LotUniversalSpec universalSpec) {
        return getLotByUniversalSpec(eea, universalSpec, EntityPermission.READ_WRITE);
    }

    public void deleteLot(final ExecutionErrorAccumulator eea, final Lot lot,
            final BasePK deletedBy) {
        var lotControl = Session.getModelController(LotControl.class);

        lotControl.deleteLot(lot, deletedBy);
    }

}
