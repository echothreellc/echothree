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

import com.echothree.model.control.invoice.common.transfer.InvoiceTypeTransfer;
import com.echothree.model.control.invoice.server.control.InvoiceControl;
import com.echothree.model.data.invoice.server.entity.InvoiceType;
import com.echothree.model.data.user.server.entity.UserVisit;

public class InvoiceTypeTransferCache
        extends BaseInvoiceTransferCache<InvoiceType, InvoiceTypeTransfer> {
    
    /** Creates a new instance of InvoiceTypeTransferCache */
    public InvoiceTypeTransferCache(InvoiceControl invoiceControl) {
        super(invoiceControl);
        
        setIncludeEntityInstance(true);
    }
    
    public InvoiceTypeTransfer getInvoiceTypeTransfer(InvoiceType invoiceType) {
        var invoiceTypeTransfer = get(invoiceType);
        
        if(invoiceTypeTransfer == null) {
            var invoiceTypeDetail = invoiceType.getLastDetail();
            var invoiceTypeName = invoiceTypeDetail.getInvoiceTypeName();
            var parentInvoiceType = invoiceTypeDetail.getParentInvoiceType();
            var parentInvoiceTypeTransfer = parentInvoiceType == null? null: getInvoiceTypeTransfer(parentInvoiceType);
            var isDefault = invoiceTypeDetail.getIsDefault();
            var sortOrder = invoiceTypeDetail.getSortOrder();
            var description = invoiceControl.getBestInvoiceTypeDescription(invoiceType, getLanguage(userVisit));
            
            invoiceTypeTransfer = new InvoiceTypeTransfer(invoiceTypeName, parentInvoiceTypeTransfer, isDefault, sortOrder,
                    description);
            put(userVisit, invoiceType, invoiceTypeTransfer);
        }
        
        return invoiceTypeTransfer;
    }
    
}
