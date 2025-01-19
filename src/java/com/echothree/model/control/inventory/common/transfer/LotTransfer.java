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

package com.echothree.model.control.inventory.common.transfer;

import com.echothree.model.control.accounting.common.transfer.CurrencyTransfer;
import com.echothree.model.control.item.common.transfer.ItemTransfer;
import com.echothree.model.control.party.common.transfer.PartyTransfer;
import com.echothree.model.control.uom.common.transfer.UnitOfMeasureTypeTransfer;
import com.echothree.util.common.transfer.BaseTransfer;
import com.echothree.util.common.transfer.MapWrapper;

public class LotTransfer
        extends BaseTransfer {

    private String lotName;
    private PartyTransfer ownerParty;
    private ItemTransfer item;
    private InventoryConditionTransfer inventoryCondition;
    private UnitOfMeasureTypeTransfer unitOfMeasureType;
    private Long quantity;
    private CurrencyTransfer currency;
    private Long unformattedUnitCost;
    private String unitCost;

    private MapWrapper<LotAliasTransfer> lotAliases;
    private MapWrapper<LotTimeTransfer> lotTimes;

    public LotTransfer(final String lotName, final PartyTransfer ownerParty, final ItemTransfer item,
            final InventoryConditionTransfer inventoryCondition, final UnitOfMeasureTypeTransfer unitOfMeasureType,
            final Long quantity, final CurrencyTransfer currency, final Long unformattedUnitCost, final String unitCost) {
        this.lotName = lotName;
        this.ownerParty = ownerParty;
        this.item = item;
        this.inventoryCondition = inventoryCondition;
        this.unitOfMeasureType = unitOfMeasureType;
        this.quantity = quantity;
        this.currency = currency;
        this.unformattedUnitCost = unformattedUnitCost;
        this.unitCost = unitCost;
    }

    public String getLotName() {
        return lotName;
    }

    public void setLotName(final String lotName) {
        this.lotName = lotName;
    }

    public PartyTransfer getOwnerParty() {
        return ownerParty;
    }

    public void setOwnerParty(final PartyTransfer ownerParty) {
        this.ownerParty = ownerParty;
    }

    public ItemTransfer getItem() {
        return item;
    }

    public void setItem(final ItemTransfer item) {
        this.item = item;
    }

    public InventoryConditionTransfer getInventoryCondition() {
        return inventoryCondition;
    }

    public void setInventoryCondition(final InventoryConditionTransfer inventoryCondition) {
        this.inventoryCondition = inventoryCondition;
    }

    public UnitOfMeasureTypeTransfer getUnitOfMeasureType() {
        return unitOfMeasureType;
    }

    public void setUnitOfMeasureType(final UnitOfMeasureTypeTransfer unitOfMeasureType) {
        this.unitOfMeasureType = unitOfMeasureType;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(final Long quantity) {
        this.quantity = quantity;
    }

    public CurrencyTransfer getCurrency() {
        return currency;
    }

    public void setCurrency(final CurrencyTransfer currency) {
        this.currency = currency;
    }

    public Long getUnformattedUnitCost() {
        return unformattedUnitCost;
    }

    public void setUnformattedUnitCost(final Long unformattedUnitCost) {
        this.unformattedUnitCost = unformattedUnitCost;
    }

    public String getUnitCost() {
        return unitCost;
    }

    public void setUnitCost(final String unitCost) {
        this.unitCost = unitCost;
    }

    public MapWrapper<LotAliasTransfer> getLotAliases() {
        return lotAliases;
    }

    public void setLotAliases(final MapWrapper<LotAliasTransfer> lotAliases) {
        this.lotAliases = lotAliases;
    }

    public MapWrapper<LotTimeTransfer> getLotTimes() {
        return lotTimes;
    }

    public void setLotTimes(final MapWrapper<LotTimeTransfer> lotTimes) {
        this.lotTimes = lotTimes;
    }

}
