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

package com.echothree.model.control.invoice.server.transfer;

import com.echothree.model.control.invoice.common.transfer.InvoiceTypeDescriptionTransfer;
import com.echothree.model.control.invoice.server.control.InvoiceControl;
import com.echothree.model.data.invoice.server.entity.InvoiceTypeDescription;
import com.echothree.model.data.user.server.entity.UserVisit;

public class InvoiceTypeDescriptionTransferCache
        extends BaseInvoiceDescriptionTransferCache<InvoiceTypeDescription, InvoiceTypeDescriptionTransfer> {
    
    /** Creates a new instance of InvoiceTypeDescriptionTransferCache */
    public InvoiceTypeDescriptionTransferCache(InvoiceControl invoiceControl) {
        super(invoiceControl);
    }
    
    public InvoiceTypeDescriptionTransfer getInvoiceTypeDescriptionTransfer(InvoiceTypeDescription invoiceTypeDescription) {
        var invoiceTypeDescriptionTransfer = get(invoiceTypeDescription);
        
        if(invoiceTypeDescriptionTransfer == null) {
            var invoiceTypeTransfer = invoiceControl.getInvoiceTypeTransfer(userVisit, invoiceTypeDescription.getInvoiceType());
            var languageTransfer = partyControl.getLanguageTransfer(userVisit, invoiceTypeDescription.getLanguage());
            
            invoiceTypeDescriptionTransfer = new InvoiceTypeDescriptionTransfer(languageTransfer, invoiceTypeTransfer, invoiceTypeDescription.getDescription());
            put(userVisit, invoiceTypeDescription, invoiceTypeDescriptionTransfer);
        }
        
        return invoiceTypeDescriptionTransfer;
    }
    
}
