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

package com.echothree.model.control.accounting.server.transfer;

import com.echothree.model.control.accounting.common.transfer.SymbolPositionDescriptionTransfer;
import com.echothree.model.control.accounting.common.transfer.SymbolPositionTransfer;
import com.echothree.model.control.accounting.server.AccountingControl;
import com.echothree.model.control.party.common.transfer.LanguageTransfer;
import com.echothree.model.data.accounting.server.entity.SymbolPositionDescription;
import com.echothree.model.data.user.server.entity.UserVisit;

public class SymbolPositionDescriptionTransferCache
        extends BaseAccountingDescriptionTransferCache<SymbolPositionDescription, SymbolPositionDescriptionTransfer> {
    
    /** Creates a new instance of SymbolPositionDescriptionTransferCache */
    public SymbolPositionDescriptionTransferCache(UserVisit userVisit, AccountingControl accountingControl) {
        super(userVisit, accountingControl);
    }
    
    @Override
    public SymbolPositionDescriptionTransfer getTransfer(SymbolPositionDescription symbolPositionDescription) {
        SymbolPositionDescriptionTransfer symbolPositionDescriptionTransfer = get(symbolPositionDescription);
        
        if(symbolPositionDescriptionTransfer == null) {
            SymbolPositionTransferCache symbolPositionTransferCache = accountingControl.getAccountingTransferCaches(userVisit).getSymbolPositionTransferCache();
            SymbolPositionTransfer symbolPositionTransfer = symbolPositionTransferCache.getTransfer(symbolPositionDescription.getSymbolPosition());
            LanguageTransfer languageTransfer = partyControl.getLanguageTransfer(userVisit, symbolPositionDescription.getLanguage());
            
            symbolPositionDescriptionTransfer = new SymbolPositionDescriptionTransfer(languageTransfer, symbolPositionTransfer, symbolPositionDescription.getDescription());
            put(symbolPositionDescription, symbolPositionDescriptionTransfer);
        }
        
        return symbolPositionDescriptionTransfer;
    }
    
}
