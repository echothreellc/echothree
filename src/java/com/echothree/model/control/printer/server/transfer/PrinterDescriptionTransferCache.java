// --------------------------------------------------------------------------------
// Copyright 2002-2021 Echo Three, LLC
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

import com.echothree.model.control.party.common.transfer.LanguageTransfer;
import com.echothree.model.control.printer.common.transfer.PrinterDescriptionTransfer;
import com.echothree.model.control.printer.common.transfer.PrinterTransfer;
import com.echothree.model.control.printer.server.control.PrinterControl;
import com.echothree.model.data.printer.server.entity.PrinterDescription;
import com.echothree.model.data.user.server.entity.UserVisit;

public class PrinterDescriptionTransferCache
        extends BasePrinterDescriptionTransferCache<PrinterDescription, PrinterDescriptionTransfer> {
    
    /** Creates a new instance of PrinterDescriptionTransferCache */
    public PrinterDescriptionTransferCache(UserVisit userVisit, PrinterControl printerControl) {
        super(userVisit, printerControl);
    }
    
    public PrinterDescriptionTransfer getPrinterDescriptionTransfer(PrinterDescription printerDescription) {
        PrinterDescriptionTransfer printerDescriptionTransfer = get(printerDescription);
        
        if(printerDescriptionTransfer == null) {
            PrinterTransfer printerTransfer = printerControl.getPrinterTransfer(userVisit, printerDescription.getPrinter());
            LanguageTransfer languageTransfer = partyControl.getLanguageTransfer(userVisit, printerDescription.getLanguage());
            
            printerDescriptionTransfer = new PrinterDescriptionTransfer(languageTransfer, printerTransfer, printerDescription.getDescription());
            put(printerDescription, printerDescriptionTransfer);
        }
        
        return printerDescriptionTransfer;
    }
    
}
