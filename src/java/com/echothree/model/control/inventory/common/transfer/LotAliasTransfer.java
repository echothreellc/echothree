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

import com.echothree.util.common.transfer.BaseTransfer;

public class LotAliasTransfer
        extends BaseTransfer {
    
    private LotTransfer lot;
    private LotAliasTypeTransfer lotAliasType;
    private String alias;
    
    /** Creates a new instance of LotAliasTransfer */
    public LotAliasTransfer(LotTransfer lot, LotAliasTypeTransfer lotAliasType, String alias) {
        this.lot = lot;
        this.lotAliasType = lotAliasType;
        this.alias = alias;
    }

    public LotTransfer getLot() {
        return lot;
    }

    public void setLot(LotTransfer lot) {
        this.lot = lot;
    }

    public LotAliasTypeTransfer getLotAliasType() {
        return lotAliasType;
    }

    public void setLotAliasType(LotAliasTypeTransfer lotAliasType) {
        this.lotAliasType = lotAliasType;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

}
