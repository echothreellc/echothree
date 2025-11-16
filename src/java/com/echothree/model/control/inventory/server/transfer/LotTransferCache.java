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

package com.echothree.model.control.inventory.server.transfer;

import com.echothree.model.control.inventory.common.InventoryOptions;
import com.echothree.model.control.inventory.common.transfer.LotAliasTransfer;
import com.echothree.model.control.inventory.common.transfer.LotTimeTransfer;
import com.echothree.model.control.inventory.common.transfer.LotTransfer;
import com.echothree.model.control.inventory.server.control.LotAliasControl;
import com.echothree.model.control.inventory.server.control.LotTimeControl;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.data.inventory.server.entity.Lot;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.transfer.MapWrapper;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class LotTransferCache
        extends BaseInventoryTransferCache<Lot, LotTransfer> {

    ItemControl itemControl = Session.getModelController(ItemControl.class);
    LotAliasControl lotAliasControl = Session.getModelController(LotAliasControl.class);
    LotTimeControl lotTimeControl = Session.getModelController(LotTimeControl.class);

    boolean includeLotAliases;
    boolean includeLotTimes;

    /** Creates a new instance of LotTransferCache */
    protected LotTransferCache() {
        super();

        var options = session.getOptions();
        if(options != null) {
            setIncludeUuid(options.contains(InventoryOptions.LotIncludeUuid));
            includeLotAliases = options.contains(InventoryOptions.LotIncludeLotAliases);
            includeLotTimes = options.contains(InventoryOptions.LotIncludeLotTimes);
        }

        setIncludeEntityInstance(true);
    }
    
    @Override
    public LotTransfer getTransfer(UserVisit userVisit, Lot lot) {
        var lotTransfer = get(lot);
        
        if(lotTransfer == null) {
            var lotDetail = lot.getLastDetail();

            var item = itemControl.getItemTransfer(userVisit, lotDetail.getItem());
            var lotIdentifier = lotDetail.getLotIdentifier();

            lotTransfer = new LotTransfer(item, lotIdentifier);
            put(userVisit, lot, lotTransfer);

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
