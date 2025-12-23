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

package com.echothree.model.control.inventory.common.transfer;

import com.echothree.model.control.accounting.common.transfer.CurrencyTransfer;
import com.echothree.model.control.item.common.transfer.ItemTransfer;
import com.echothree.model.control.party.common.transfer.PartyTransfer;
import com.echothree.model.control.uom.common.transfer.UnitOfMeasureTypeTransfer;
import com.echothree.util.common.transfer.BaseTransfer;
import com.echothree.util.common.transfer.MapWrapper;

public class LotTransfer
        extends BaseTransfer {

    private ItemTransfer item;
    private String lotIdentifier;

    private MapWrapper<LotAliasTransfer> lotAliases;
    private MapWrapper<LotTimeTransfer> lotTimes;

    public LotTransfer(final ItemTransfer item, final String lotIdentifier) {
        this.item = item;
        this.lotIdentifier = lotIdentifier;
    }

    public ItemTransfer getItem() {
        return item;
    }

    public void setItem(final ItemTransfer item) {
        this.item = item;
    }

    public String getLotIdentifier() {
        return lotIdentifier;
    }

    public void setLotIdentifier(final String lotIdentifier) {
        this.lotIdentifier = lotIdentifier;
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
