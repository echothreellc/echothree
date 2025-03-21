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

package com.echothree.model.control.accounting.server.transfer;

import com.echothree.model.control.accounting.common.transfer.SymbolPositionTransfer;
import com.echothree.model.control.accounting.server.control.AccountingControl;
import com.echothree.model.data.accounting.server.entity.SymbolPosition;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class SymbolPositionTransferCache
        extends BaseAccountingTransferCache<SymbolPosition, SymbolPositionTransfer> {

    AccountingControl accountingControl = Session.getModelController(AccountingControl.class);

    /** Creates a new instance of SymbolPositionTransferCache */
    public SymbolPositionTransferCache(UserVisit userVisit) {
        super(userVisit);
        
        setIncludeEntityInstance(true);
    }
    
    @Override
    public SymbolPositionTransfer getTransfer(SymbolPosition symbolPosition) {
        var symbolPositionTransfer = get(symbolPosition);
        
        if(symbolPositionTransfer == null) {
            var symbolPositionDetail = symbolPosition.getLastDetail();
            var symbolPositionName = symbolPositionDetail.getSymbolPositionName();
            var isDefault = symbolPositionDetail.getIsDefault();
            var sortOrder = symbolPositionDetail.getSortOrder();
            var description = accountingControl.getBestSymbolPositionDescription(symbolPosition, getLanguage());
            
            symbolPositionTransfer = new SymbolPositionTransfer(symbolPositionName, isDefault, sortOrder, description);
            put(symbolPosition, symbolPositionTransfer);
        }
        
        return symbolPositionTransfer;
    }
    
}
