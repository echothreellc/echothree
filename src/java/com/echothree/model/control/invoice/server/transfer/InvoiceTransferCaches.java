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

import com.echothree.util.server.transfer.BaseTransferCaches;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

@RequestScoped
public class InvoiceTransferCaches
        extends BaseTransferCaches {
    
    @Inject
    InvoiceLineUseTypeTransferCache invoiceLineUseTypeTransferCache;
    
    @Inject
    InvoiceRoleTypeTransferCache invoiceRoleTypeTransferCache;
    
    @Inject
    InvoiceRoleTransferCache invoiceRoleTransferCache;
    
    @Inject
    InvoiceTypeTransferCache invoiceTypeTransferCache;
    
    @Inject
    InvoiceTypeDescriptionTransferCache invoiceTypeDescriptionTransferCache;
    
    @Inject
    InvoiceAliasTypeTransferCache invoiceAliasTypeTransferCache;
    
    @Inject
    InvoiceAliasTypeDescriptionTransferCache invoiceAliasTypeDescriptionTransferCache;
    
    @Inject
    InvoiceLineTypeTransferCache invoiceLineTypeTransferCache;
    
    @Inject
    InvoiceLineTypeDescriptionTransferCache invoiceLineTypeDescriptionTransferCache;
    
    @Inject
    InvoiceTransferCache invoiceTransferCache;
    
    @Inject
    InvoiceAliasTransferCache invoiceAliasTransferCache;
    
    @Inject
    InvoiceLineTransferCache invoiceLineTransferCache;
    
    @Inject
    InvoiceLineItemTransferCache invoiceLineItemTransferCache;
    
    @Inject
    InvoiceLineGlAccountTransferCache invoiceLineGlAccountTransferCache;
    
    @Inject
    InvoiceTimeTypeTransferCache invoiceTimeTypeTransferCache;
    
    @Inject
    InvoiceTimeTypeDescriptionTransferCache invoiceTimeTypeDescriptionTransferCache;
    
    @Inject
    InvoiceTimeTransferCache invoiceTimeTransferCache;

    /** Creates a new instance of InvoiceTransferCaches */
    protected InvoiceTransferCaches() {
        super();
    }
    
    public InvoiceLineUseTypeTransferCache getInvoiceLineUseTypeTransferCache() {
        return invoiceLineUseTypeTransferCache;
    }
    
    public InvoiceRoleTypeTransferCache getInvoiceRoleTypeTransferCache() {
        return invoiceRoleTypeTransferCache;
    }
    
    public InvoiceRoleTransferCache getInvoiceRoleTransferCache() {
        return invoiceRoleTransferCache;
    }
    
    public InvoiceTypeTransferCache getInvoiceTypeTransferCache() {
        return invoiceTypeTransferCache;
    }
    
    public InvoiceTypeDescriptionTransferCache getInvoiceTypeDescriptionTransferCache() {
        return invoiceTypeDescriptionTransferCache;
    }
    
    public InvoiceAliasTypeTransferCache getInvoiceAliasTypeTransferCache() {
        return invoiceAliasTypeTransferCache;
    }
    
    public InvoiceAliasTypeDescriptionTransferCache getInvoiceAliasTypeDescriptionTransferCache() {
        return invoiceAliasTypeDescriptionTransferCache;
    }
    
    public InvoiceLineTypeTransferCache getInvoiceLineTypeTransferCache() {
        return invoiceLineTypeTransferCache;
    }
    
    public InvoiceLineTypeDescriptionTransferCache getInvoiceLineTypeDescriptionTransferCache() {
        return invoiceLineTypeDescriptionTransferCache;
    }
    
    public InvoiceTransferCache getInvoiceTransferCache() {
        return invoiceTransferCache;
    }
    
    public InvoiceAliasTransferCache getInvoiceAliasTransferCache() {
        return invoiceAliasTransferCache;
    }
    
    public InvoiceLineTransferCache getInvoiceLineTransferCache() {
        return invoiceLineTransferCache;
    }
    
    public InvoiceLineGlAccountTransferCache getInvoiceLineGlAccountTransferCache() {
        return invoiceLineGlAccountTransferCache;
    }
    
    public InvoiceLineItemTransferCache getInvoiceLineItemTransferCache() {
        return invoiceLineItemTransferCache;
    }
    
    public InvoiceTimeTypeTransferCache getInvoiceTimeTypeTransferCache() {
        return invoiceTimeTypeTransferCache;
    }

    public InvoiceTimeTransferCache getInvoiceTimeTransferCache() {
        return invoiceTimeTransferCache;
    }

    public InvoiceTimeTypeDescriptionTransferCache getInvoiceTimeTypeDescriptionTransferCache() {
        return invoiceTimeTypeDescriptionTransferCache;
    }

}
