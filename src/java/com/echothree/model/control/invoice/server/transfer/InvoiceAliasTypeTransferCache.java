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

import com.echothree.model.control.invoice.common.transfer.InvoiceAliasTypeTransfer;
import com.echothree.model.control.invoice.server.control.InvoiceControl;
import com.echothree.model.data.invoice.server.entity.InvoiceAliasType;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class InvoiceAliasTypeTransferCache
        extends BaseInvoiceTransferCache<InvoiceAliasType, InvoiceAliasTypeTransfer> {

    InvoiceControl invoiceControl = Session.getModelController(InvoiceControl.class);

    /** Creates a new instance of InvoiceAliasTypeTransferCache */
    public InvoiceAliasTypeTransferCache() {
        super();
        
        setIncludeEntityInstance(true);
    }
    
    public InvoiceAliasTypeTransfer getInvoiceAliasTypeTransfer(UserVisit userVisit, InvoiceAliasType invoiceAliasType) {
        var invoiceAliasTypeTransfer = get(invoiceAliasType);
        
        if(invoiceAliasTypeTransfer == null) {
            var invoiceAliasTypeDetail = invoiceAliasType.getLastDetail();
            var invoiceType = invoiceControl.getInvoiceTypeTransfer(userVisit, invoiceAliasTypeDetail.getInvoiceType());
            var invoiceAliasTypeName = invoiceAliasTypeDetail.getInvoiceAliasTypeName();
            var validationPattern = invoiceAliasTypeDetail.getValidationPattern();
            var isDefault = invoiceAliasTypeDetail.getIsDefault();
            var sortOrder = invoiceAliasTypeDetail.getSortOrder();
            var description = invoiceControl.getBestInvoiceAliasTypeDescription(invoiceAliasType, getLanguage(userVisit));
            
            invoiceAliasTypeTransfer = new InvoiceAliasTypeTransfer(invoiceType, invoiceAliasTypeName, validationPattern, isDefault, sortOrder, description);
            put(userVisit, invoiceAliasType, invoiceAliasTypeTransfer);
        }
        
        return invoiceAliasTypeTransfer;
    }
    
}
