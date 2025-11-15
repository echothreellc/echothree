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

package com.echothree.model.control.printer.server.transfer;

import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.printer.common.transfer.PartyPrinterGroupUseTransfer;
import com.echothree.model.control.printer.server.control.PrinterControl;
import com.echothree.model.data.printer.server.entity.PartyPrinterGroupUse;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class PartyPrinterGroupUseTransferCache
        extends BasePrinterTransferCache<PartyPrinterGroupUse, PartyPrinterGroupUseTransfer> {
    
    PartyControl partyControl = Session.getModelController(PartyControl.class);
    PrinterControl printerControl = Session.getModelController(PrinterControl.class);

    /** Creates a new instance of PartyPrinterGroupUseTransferCache */
    public PartyPrinterGroupUseTransferCache() {
        super();
    }
    
    public PartyPrinterGroupUseTransfer getPartyPrinterGroupUseTransfer(UserVisit userVisit, PartyPrinterGroupUse partyPrinterGroupUse) {
        var partyPrinterGroupUseTransfer = get(partyPrinterGroupUse);
        
        if(partyPrinterGroupUseTransfer == null) {
            var party = partyControl.getPartyTransfer(userVisit, partyPrinterGroupUse.getParty());
            var printerGroupUseType = printerControl.getPrinterGroupUseTypeTransfer(userVisit,
                    partyPrinterGroupUse.getPrinterGroupUseType());
            var printerGroup = printerControl.getPrinterGroupTransfer(userVisit,
                    partyPrinterGroupUse.getPrinterGroup());
            
            partyPrinterGroupUseTransfer = new PartyPrinterGroupUseTransfer(party, printerGroupUseType, printerGroup);
            put(userVisit, partyPrinterGroupUse, partyPrinterGroupUseTransfer);
        }
        
        return partyPrinterGroupUseTransfer;
    }
    
}
