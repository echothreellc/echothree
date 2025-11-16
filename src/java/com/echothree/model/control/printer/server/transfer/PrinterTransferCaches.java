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

import com.echothree.util.server.transfer.BaseTransferCaches;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

@RequestScoped
public class PrinterTransferCaches
        extends BaseTransferCaches {
    
    @Inject
    PrinterGroupTransferCache printerGroupTransferCache;
    
    @Inject
    PrinterGroupDescriptionTransferCache printerGroupDescriptionTransferCache;
    
    @Inject
    PrinterGroupUseTypeTransferCache printerGroupUseTypeTransferCache;
    
    @Inject
    PrinterGroupUseTypeDescriptionTransferCache printerGroupUseTypeDescriptionTransferCache;
    
    @Inject
    PartyPrinterGroupUseTransferCache partyPrinterGroupUseTransferCache;
    
    @Inject
    PrinterTransferCache printerTransferCache;
    
    @Inject
    PrinterDescriptionTransferCache printerDescriptionTransferCache;
    
    @Inject
    PrinterGroupJobTransferCache printerGroupJobTransferCache;

    /** Creates a new instance of PrinterTransferCaches */
    protected PrinterTransferCaches() {
        super();
    }
    
    public PrinterGroupTransferCache getPrinterGroupTransferCache() {
        return printerGroupTransferCache;
    }
    
    public PrinterGroupDescriptionTransferCache getPrinterGroupDescriptionTransferCache() {
        return printerGroupDescriptionTransferCache;
    }
    
    public PrinterGroupUseTypeTransferCache getPrinterGroupUseTypeTransferCache() {
        return printerGroupUseTypeTransferCache;
    }

    public PrinterGroupUseTypeDescriptionTransferCache getPrinterGroupUseTypeDescriptionTransferCache() {
        return printerGroupUseTypeDescriptionTransferCache;
    }

    public PartyPrinterGroupUseTransferCache getPartyPrinterGroupUseTransferCache() {
        return partyPrinterGroupUseTransferCache;
    }
    
    public PrinterTransferCache getPrinterTransferCache() {
        return printerTransferCache;
    }
    
    public PrinterDescriptionTransferCache getPrinterDescriptionTransferCache() {
        return printerDescriptionTransferCache;
    }
    
    public PrinterGroupJobTransferCache getPrinterGroupJobTransferCache() {
        return printerGroupJobTransferCache;
    }
    
}
