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

import com.echothree.model.control.invoice.common.transfer.InvoiceTimeTypeDescriptionTransfer;
import com.echothree.model.control.invoice.server.control.InvoiceControl;
import com.echothree.model.data.invoice.server.entity.InvoiceTimeTypeDescription;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class InvoiceTimeTypeDescriptionTransferCache
        extends BaseInvoiceDescriptionTransferCache<InvoiceTimeTypeDescription, InvoiceTimeTypeDescriptionTransfer> {

    InvoiceControl invoiceControl = Session.getModelController(InvoiceControl.class);

    /** Creates a new instance of InvoiceTimeTypeDescriptionTransferCache */
    public InvoiceTimeTypeDescriptionTransferCache() {
        super();
    }
    
    public InvoiceTimeTypeDescriptionTransfer getInvoiceTimeTypeDescriptionTransfer(UserVisit userVisit, InvoiceTimeTypeDescription invoiceTimeTypeDescription) {
        var invoiceTimeTypeDescriptionTransfer = get(invoiceTimeTypeDescription);
        
        if(invoiceTimeTypeDescriptionTransfer == null) {
            var invoiceTimeTypeTransfer = invoiceControl.getInvoiceTimeTypeTransfer(userVisit, invoiceTimeTypeDescription.getInvoiceTimeType());
            var languageTransfer = partyControl.getLanguageTransfer(userVisit, invoiceTimeTypeDescription.getLanguage());
            
            invoiceTimeTypeDescriptionTransfer = new InvoiceTimeTypeDescriptionTransfer(languageTransfer, invoiceTimeTypeTransfer, invoiceTimeTypeDescription.getDescription());
            put(userVisit, invoiceTimeTypeDescription, invoiceTimeTypeDescriptionTransfer);
        }
        
        return invoiceTimeTypeDescriptionTransfer;
    }
    
}
