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

package com.echothree.model.control.inventory.server.logic;

import com.echothree.control.user.inventory.common.spec.LotUniversalSpec;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.inventory.common.exception.DuplicateLotIdentifierException;
import com.echothree.model.control.inventory.common.exception.UnknownLotIdentifierException;
import com.echothree.model.control.inventory.server.control.LotControl;
import com.echothree.model.control.item.server.logic.ItemLogic;
import com.echothree.model.control.sequence.common.SequenceTypes;
import com.echothree.model.control.sequence.server.logic.SequenceGeneratorLogic;
import com.echothree.model.data.inventory.server.entity.Lot;
import com.echothree.model.data.item.server.entity.Item;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;

@ApplicationScoped
public class LotLogic
        extends BaseLogic {

    protected LotLogic() {
        super();
    }

    public static LotLogic getInstance() {
        return CDI.current().select(LotLogic.class).get();
    }

    public Lot createLot(final ExecutionErrorAccumulator eea, final Item item, String lotIdentifier,
            final BasePK createdBy) {
        Lot lot = null;

        if(lotIdentifier == null) {
            lotIdentifier = SequenceGeneratorLogic.getInstance().getNextSequenceValue(eea, SequenceTypes.LOT.name());
        }

        if(!eea.hasExecutionErrors()) {
            var lotControl = Session.getModelController(LotControl.class);

            lot = lotControl.getLotByIdentifier(item, lotIdentifier);
            if(lot == null) {
                lot = lotControl.createLot(item, lotIdentifier, createdBy);
            } else {
                handleExecutionError(DuplicateLotIdentifierException.class, eea, ExecutionErrors.DuplicateLotIdentifier.name(),
                        item.getLastDetail().getItemName(), lotIdentifier);
            }
        }

        return lot;
    }

    public Lot getLotByIdentifier(final ExecutionErrorAccumulator eea, final Item item, final String lotIdentifier,
            final EntityPermission entityPermission) {
        var lotControl = Session.getModelController(LotControl.class);
        var lot = lotControl.getLotByIdentifier(item, lotIdentifier, entityPermission);

        if(lot == null) {
            handleExecutionError(UnknownLotIdentifierException.class, eea, ExecutionErrors.UnknownLotIdentifier.name(),
                    item.getLastDetail().getItemName(), lotIdentifier);
        }

        return lot;
    }

    public Lot getLotByIdentifier(final ExecutionErrorAccumulator eea, final Item item, final String lotIdentifier) {
        return getLotByIdentifier(eea, item, lotIdentifier, EntityPermission.READ_ONLY);
    }

    public Lot getLotByIdentifierForUpdate(final ExecutionErrorAccumulator eea, final Item item, final String lotIdentifier) {
        return getLotByIdentifier(eea, item, lotIdentifier, EntityPermission.READ_WRITE);
    }

    public Lot getLotByUniversalSpec(final ExecutionErrorAccumulator eea, final LotUniversalSpec universalSpec,
            final EntityPermission entityPermission) {
        Lot lot = null;
        var lotControl = Session.getModelController(LotControl.class);
        var itemName = universalSpec.getItemName();
        var lotIdentifier = universalSpec.getLotIdentifier();
        var parameterCount = (itemName != null && lotIdentifier != null ? 1 : 0)
                + EntityInstanceLogic.getInstance().countPossibleEntitySpecs(universalSpec);

        switch(parameterCount) {
            case 1 -> {
                if(lotIdentifier == null) {
                    var entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(eea, universalSpec,
                            ComponentVendors.ECHO_THREE.name(), EntityTypes.Lot.name());

                    if(!eea.hasExecutionErrors()) {
                        lot = lotControl.getLotByEntityInstance(entityInstance, entityPermission);
                    }
                } else {
                    var item = ItemLogic.getInstance().getItemByName(eea, itemName);

                    if(!eea.hasExecutionErrors()) {
                        lot = getLotByIdentifier(eea, item, lotIdentifier, entityPermission);
                    }
                }
            }
            default ->
                    handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
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
