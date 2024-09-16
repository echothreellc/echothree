// --------------------------------------------------------------------------------
// Copyright 2002-2024 Echo Three, LLC
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

import com.echothree.model.control.accounting.server.control.AccountingControl;
import com.echothree.model.control.inventory.common.InventoryOptions;
import com.echothree.model.control.inventory.common.transfer.LotAliasTransfer;
import com.echothree.model.control.inventory.common.transfer.LotTimeTransfer;
import com.echothree.model.control.inventory.common.transfer.LotTransfer;
import com.echothree.model.control.inventory.server.control.InventoryControl;
import com.echothree.model.control.inventory.server.control.LotAliasControl;
import com.echothree.model.control.inventory.server.control.LotTimeControl;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.uom.server.control.UomControl;
import com.echothree.model.data.inventory.server.entity.Lot;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.transfer.MapWrapper;
import com.echothree.util.server.persistence.Session;
import com.echothree.util.server.string.AmountUtils;

public class LotTransferCache
        extends BaseInventoryTransferCache<Lot, LotTransfer> {

    AccountingControl accountingControl = Session.getModelController(AccountingControl.class);
    ItemControl itemControl = Session.getModelController(ItemControl.class);
    PartyControl partyControl = Session.getModelController(PartyControl.class);
    UomControl uomControl = Session.getModelController(UomControl.class);
    LotAliasControl lotAliasControl = Session.getModelController(LotAliasControl.class);
    LotTimeControl lotTimeControl = Session.getModelController(LotTimeControl.class);

    boolean includeLotAliases;
    boolean includeLotTimes;

    /** Creates a new instance of LotTransferCache */
    public LotTransferCache(UserVisit userVisit, InventoryControl inventoryControl) {
        super(userVisit, inventoryControl);

        var options = session.getOptions();
        if(options != null) {
            setIncludeUuid(options.contains(InventoryOptions.LotIncludeUuid));
            includeLotAliases = options.contains(InventoryOptions.LotIncludeLotAliases);
            includeLotTimes = options.contains(InventoryOptions.LotIncludeLotTimes);
        }

        setIncludeEntityInstance(true);
    }
    
    @Override
    public LotTransfer getTransfer(Lot lot) {
        var lotTransfer = get(lot);
        
        if(lotTransfer == null) {
            var lotDetail = lot.getLastDetail();

            var lotName = lotDetail.getLotName();
            var ownerParty = partyControl.getPartyTransfer(userVisit, lotDetail.getOwnerParty());
            var item = itemControl.getItemTransfer(userVisit, lotDetail.getItem());
            var inventoryCondition = inventoryControl.getInventoryConditionTransfer(userVisit, lotDetail.getInventoryCondition());
            var unitOfMeasureType = uomControl.getUnitOfMeasureTypeTransfer(userVisit, lotDetail.getUnitOfMeasureType());
            var quantity = lotDetail.getQuantity();
            var currency = lotDetail.getCurrency();
            var currencyTransfer = currency == null ? null : accountingControl.getCurrencyTransfer(userVisit, currency);
            var unformattedUnitCost = lotDetail.getUnitCost();
            var unitCost = currency == null || unformattedUnitCost == null ? null : AmountUtils.getInstance().formatCostUnit(currency, unformattedUnitCost);

            lotTransfer = new LotTransfer(lotName, ownerParty, item, inventoryCondition, unitOfMeasureType, quantity,
                    currencyTransfer, unformattedUnitCost, unitCost);
            put(lot, lotTransfer);

            if(includeLotAliases) {
                var lotAliasTransfers = lotAliasControl.getLotAliasTransfersByLot(userVisit, lot);
                var lotAliases = new MapWrapper<LotAliasTransfer>(lotAliasTransfers.size());

                lotAliasTransfers.forEach((lotAliasTransfer) -> {
                    lotAliases.put(lotAliasTransfer.getLotAliasType().getLotAliasTypeName(), lotAliasTransfer);
                });

                lotTransfer.setLotAliases(lotAliases);
            }

            if(includeLotTimes) {
                var lotTimeTransfers = lotTimeControl.getLotTimeTransfersByLot(userVisit, lot);
                var lotTimes = new MapWrapper<LotTimeTransfer>(lotTimeTransfers.size());

                lotTimeTransfers.forEach((lotTimeTransfer) -> {
                    lotTimes.put(lotTimeTransfer.getLotTimeType().getLotTimeTypeName(), lotTimeTransfer);
                });

                lotTransfer.setLotTimes(lotTimes);
            }
        }
        
        return lotTransfer;
    }
    
}
