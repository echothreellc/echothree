// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
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

package com.echothree.control.user.inventory.remote.result;

import com.echothree.model.control.inventory.remote.transfer.PartyInventoryLevelTransfer;
import com.echothree.model.control.item.remote.transfer.ItemTransfer;
import com.echothree.model.control.party.remote.transfer.CompanyTransfer;
import com.echothree.model.control.party.remote.transfer.PartyTransfer;
import com.echothree.model.control.warehouse.remote.transfer.WarehouseTransfer;
import com.echothree.util.remote.command.BaseResult;

public interface GetPartyInventoryLevelResult
        extends BaseResult {
    
    PartyTransfer getParty();
    void setParty(PartyTransfer party);
    
    WarehouseTransfer getWarehouse();
    void setWarehouse(WarehouseTransfer warehouse);
    
    CompanyTransfer getCompany();
    void setCompany(CompanyTransfer company);
    
    ItemTransfer getItem();
    void setItem(ItemTransfer item);
    
    PartyInventoryLevelTransfer getPartyInventoryLevel();
    void setPartyInventoryLevel(PartyInventoryLevelTransfer partyInventoryLevel);
    
}
