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

public class PrinterTransferCaches
        extends BaseTransferCaches {
    
    protected PrinterGroupTransferCache printerGroupTransferCache;
    protected PrinterGroupDescriptionTransferCache printerGroupDescriptionTransferCache;
    protected PrinterGroupUseTypeTransferCache printerGroupUseTypeTransferCache;
    protected PrinterGroupUseTypeDescriptionTransferCache printerGroupUseTypeDescriptionTransferCache;
    protected PartyPrinterGroupUseTransferCache partyPrinterGroupUseTransferCache;
    protected PrinterTransferCache printerTransferCache;
    protected PrinterDescriptionTransferCache printerDescriptionTransferCache;
    protected PrinterGroupJobTransferCache printerGroupJobTransferCache;
    
    /** Creates a new instance of PrinterTransferCaches */
    public PrinterTransferCaches() {
        super();
    }
    
    public PrinterGroupTransferCache getPrinterGroupTransferCache() {
        if(printerGroupTransferCache == null)
            printerGroupTransferCache = new PrinterGroupTransferCache();
        
        return printerGroupTransferCache;
    }
    
    public PrinterGroupDescriptionTransferCache getPrinterGroupDescriptionTransferCache() {
        if(printerGroupDescriptionTransferCache == null)
            printerGroupDescriptionTransferCache = new PrinterGroupDescriptionTransferCache();
        
        return printerGroupDescriptionTransferCache;
    }
    
    public PrinterGroupUseTypeTransferCache getPrinterGroupUseTypeTransferCache() {
        if(printerGroupUseTypeTransferCache == null)
            printerGroupUseTypeTransferCache = new PrinterGroupUseTypeTransferCache();

        return printerGroupUseTypeTransferCache;
    }

    public PrinterGroupUseTypeDescriptionTransferCache getPrinterGroupUseTypeDescriptionTransferCache() {
        if(printerGroupUseTypeDescriptionTransferCache == null)
            printerGroupUseTypeDescriptionTransferCache = new PrinterGroupUseTypeDescriptionTransferCache();

        return printerGroupUseTypeDescriptionTransferCache;
    }

    public PartyPrinterGroupUseTransferCache getPartyPrinterGroupUseTransferCache() {
        if(partyPrinterGroupUseTransferCache == null)
            partyPrinterGroupUseTransferCache = new PartyPrinterGroupUseTransferCache();
        
        return partyPrinterGroupUseTransferCache;
    }
    
    public PrinterTransferCache getPrinterTransferCache() {
        if(printerTransferCache == null)
            printerTransferCache = new PrinterTransferCache();
        
        return printerTransferCache;
    }
    
    public PrinterDescriptionTransferCache getPrinterDescriptionTransferCache() {
        if(printerDescriptionTransferCache == null)
            printerDescriptionTransferCache = new PrinterDescriptionTransferCache();
        
        return printerDescriptionTransferCache;
    }
    
    public PrinterGroupJobTransferCache getPrinterGroupJobTransferCache() {
        if(printerGroupJobTransferCache == null)
            printerGroupJobTransferCache = new PrinterGroupJobTransferCache();
        
        return printerGroupJobTransferCache;
    }
    
}
