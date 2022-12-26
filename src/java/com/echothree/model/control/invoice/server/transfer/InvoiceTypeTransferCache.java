// --------------------------------------------------------------------------------
// Copyright 2002-2023 Echo Three, LLC
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
import com.echothree.model.data.invoice.server.entity.InvoiceTypeDetail;
import com.echothree.model.data.user.server.entity.UserVisit;

public class InvoiceTypeTransferCache
        extends BaseInvoiceTransferCache<InvoiceType, InvoiceTypeTransfer> {
    
    /** Creates a new instance of InvoiceTypeTransferCache */
    public InvoiceTypeTransferCache(UserVisit userVisit, InvoiceControl invoiceControl) {
        super(userVisit, invoiceControl);
        
        setIncludeEntityInstance(true);
    }
    
    public InvoiceTypeTransfer getInvoiceTypeTransfer(InvoiceType invoiceType) {
        InvoiceTypeTransfer invoiceTypeTransfer = get(invoiceType);
        
        if(invoiceTypeTransfer == null) {
            InvoiceTypeDetail invoiceTypeDetail = invoiceType.getLastDetail();
            String invoiceTypeName = invoiceTypeDetail.getInvoiceTypeName();
            InvoiceType parentInvoiceType = invoiceTypeDetail.getParentInvoiceType();
            InvoiceTypeTransfer parentInvoiceTypeTransfer = parentInvoiceType == null? null: getInvoiceTypeTransfer(parentInvoiceType);
            Boolean isDefault = invoiceTypeDetail.getIsDefault();
            Integer sortOrder = invoiceTypeDetail.getSortOrder();
            String description = invoiceControl.getBestInvoiceTypeDescription(invoiceType, getLanguage());
            
            invoiceTypeTransfer = new InvoiceTypeTransfer(invoiceTypeName, parentInvoiceTypeTransfer, isDefault, sortOrder,
                    description);
            put(invoiceType, invoiceTypeTransfer);
        }
        
        return invoiceTypeTransfer;
    }
    
}
