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

import com.echothree.model.control.printer.common.transfer.PrinterGroupDescriptionTransfer;
import com.echothree.model.control.printer.server.control.PrinterControl;
import com.echothree.model.data.printer.server.entity.PrinterGroupDescription;
import com.echothree.model.data.user.server.entity.UserVisit;

public class PrinterGroupDescriptionTransferCache
        extends BasePrinterDescriptionTransferCache<PrinterGroupDescription, PrinterGroupDescriptionTransfer> {
    
    /** Creates a new instance of PrinterGroupDescriptionTransferCache */
    public PrinterGroupDescriptionTransferCache(UserVisit userVisit, PrinterControl printerControl) {
        super(userVisit, printerControl);
    }
    
    public PrinterGroupDescriptionTransfer getPrinterGroupDescriptionTransfer(PrinterGroupDescription printerGroupDescription) {
        var printerGroupDescriptionTransfer = get(printerGroupDescription);
        
        if(printerGroupDescriptionTransfer == null) {
            var printerGroupTransfer = printerControl.getPrinterGroupTransfer(userVisit, printerGroupDescription.getPrinterGroup());
            var languageTransfer = partyControl.getLanguageTransfer(userVisit, printerGroupDescription.getLanguage());
            
            printerGroupDescriptionTransfer = new PrinterGroupDescriptionTransfer(languageTransfer, printerGroupTransfer, printerGroupDescription.getDescription());
            put(userVisit, printerGroupDescription, printerGroupDescriptionTransfer);
        }
        
        return printerGroupDescriptionTransfer;
    }
    
}
