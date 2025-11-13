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

import com.echothree.model.control.invoice.common.transfer.InvoiceLineTypeTransfer;
import com.echothree.model.control.invoice.server.control.InvoiceControl;
import com.echothree.model.data.invoice.server.entity.InvoiceLineType;
import com.echothree.model.data.user.server.entity.UserVisit;

public class InvoiceLineTypeTransferCache
        extends BaseInvoiceTransferCache<InvoiceLineType, InvoiceLineTypeTransfer> {
    
    /** Creates a new instance of InvoiceLineTypeTransferCache */
    public InvoiceLineTypeTransferCache(InvoiceControl invoiceControl) {
        super(invoiceControl);
        
        setIncludeEntityInstance(true);
    }
    
    public InvoiceLineTypeTransfer getInvoiceLineTypeTransfer(UserVisit userVisit, InvoiceLineType invoiceLineType) {
        var invoiceLineTypeTransfer = get(invoiceLineType);
        
        if(invoiceLineTypeTransfer == null) {
            var invoiceLineTypeDetail = invoiceLineType.getLastDetail();
            var invoiceType = invoiceControl.getInvoiceTypeTransfer(userVisit, invoiceLineTypeDetail.getInvoiceType());
            var invoiceLineTypeName = invoiceLineTypeDetail.getInvoiceLineTypeName();
            var parentInvoiceLineType = invoiceLineTypeDetail.getParentInvoiceLineType();
            var parentInvoiceLineTypeTransfer = parentInvoiceLineType == null? null: getInvoiceLineTypeTransfer(parentInvoiceLineType);
            var isDefault = invoiceLineTypeDetail.getIsDefault();
            var sortOrder = invoiceLineTypeDetail.getSortOrder();
            var description = invoiceControl.getBestInvoiceLineTypeDescription(invoiceLineType, getLanguage(userVisit));
            
            invoiceLineTypeTransfer = new InvoiceLineTypeTransfer(invoiceType, invoiceLineTypeName, parentInvoiceLineTypeTransfer,
                    isDefault, sortOrder, description);
            put(userVisit, invoiceLineType, invoiceLineTypeTransfer);
        }
        
        return invoiceLineTypeTransfer;
    }
    
}
