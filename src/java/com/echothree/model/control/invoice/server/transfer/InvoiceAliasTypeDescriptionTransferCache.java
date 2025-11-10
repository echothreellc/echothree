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

import com.echothree.model.control.invoice.common.transfer.InvoiceAliasTypeDescriptionTransfer;
import com.echothree.model.control.invoice.server.control.InvoiceControl;
import com.echothree.model.data.invoice.server.entity.InvoiceAliasTypeDescription;
import com.echothree.model.data.user.server.entity.UserVisit;

public class InvoiceAliasTypeDescriptionTransferCache
        extends BaseInvoiceDescriptionTransferCache<InvoiceAliasTypeDescription, InvoiceAliasTypeDescriptionTransfer> {
    
    /** Creates a new instance of InvoiceAliasTypeDescriptionTransferCache */
    public InvoiceAliasTypeDescriptionTransferCache(UserVisit userVisit, InvoiceControl invoiceControl) {
        super(userVisit, invoiceControl);
    }
    
    public InvoiceAliasTypeDescriptionTransfer getInvoiceAliasTypeDescriptionTransfer(InvoiceAliasTypeDescription invoiceAliasTypeDescription) {
        var invoiceAliasTypeDescriptionTransfer = get(invoiceAliasTypeDescription);
        
        if(invoiceAliasTypeDescriptionTransfer == null) {
            var invoiceAliasTypeTransfer = invoiceControl.getInvoiceAliasTypeTransfer(userVisit, invoiceAliasTypeDescription.getInvoiceAliasType());
            var languageTransfer = partyControl.getLanguageTransfer(userVisit, invoiceAliasTypeDescription.getLanguage());
            
            invoiceAliasTypeDescriptionTransfer = new InvoiceAliasTypeDescriptionTransfer(languageTransfer, invoiceAliasTypeTransfer, invoiceAliasTypeDescription.getDescription());
            put(userVisit, invoiceAliasTypeDescription, invoiceAliasTypeDescriptionTransfer);
        }
        
        return invoiceAliasTypeDescriptionTransfer;
    }
    
}
