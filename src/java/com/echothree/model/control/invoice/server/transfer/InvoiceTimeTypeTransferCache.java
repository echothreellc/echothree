// --------------------------------------------------------------------------------
// Copyright 2002-2026 Echo Three, LLC
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

import com.echothree.model.control.invoice.common.transfer.InvoiceTimeTypeTransfer;
import com.echothree.model.control.invoice.server.control.InvoiceControl;
import com.echothree.model.data.invoice.server.entity.InvoiceTimeType;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class InvoiceTimeTypeTransferCache
        extends BaseInvoiceTransferCache<InvoiceTimeType, InvoiceTimeTypeTransfer> {

    InvoiceControl invoiceControl = Session.getModelController(InvoiceControl.class);

    /** Creates a new instance of InvoiceTimeTypeTransferCache */
    protected InvoiceTimeTypeTransferCache() {
        super();
        
        setIncludeEntityInstance(true);
    }
    
    public InvoiceTimeTypeTransfer getInvoiceTimeTypeTransfer(UserVisit userVisit, InvoiceTimeType invoiceTimeType) {
        var invoiceTimeTypeTransfer = get(invoiceTimeType);
        
        if(invoiceTimeTypeTransfer == null) {
            var invoiceTimeTypeDetail = invoiceTimeType.getLastDetail();
            var invoiceTimeTypeName = invoiceTimeTypeDetail.getInvoiceTimeTypeName();
            var isDefault = invoiceTimeTypeDetail.getIsDefault();
            var sortOrder = invoiceTimeTypeDetail.getSortOrder();
            var description = invoiceControl.getBestInvoiceTimeTypeDescription(invoiceTimeType, getLanguage(userVisit));
            
            invoiceTimeTypeTransfer = new InvoiceTimeTypeTransfer(invoiceTimeTypeName, isDefault, sortOrder, description);
            put(userVisit, invoiceTimeType, invoiceTimeTypeTransfer);
        }
        
        return invoiceTimeTypeTransfer;
    }
    
}
