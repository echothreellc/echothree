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

public class InvoiceTransferCaches
        extends BaseTransferCaches {
    
    protected InvoiceLineUseTypeTransferCache invoiceLineUseTypeTransferCache;
    protected InvoiceRoleTypeTransferCache invoiceRoleTypeTransferCache;
    protected InvoiceRoleTransferCache invoiceRoleTransferCache;
    protected InvoiceTypeTransferCache invoiceTypeTransferCache;
    protected InvoiceTypeDescriptionTransferCache invoiceTypeDescriptionTransferCache;
    protected InvoiceAliasTypeTransferCache invoiceAliasTypeTransferCache;
    protected InvoiceAliasTypeDescriptionTransferCache invoiceAliasTypeDescriptionTransferCache;
    protected InvoiceLineTypeTransferCache invoiceLineTypeTransferCache;
    protected InvoiceLineTypeDescriptionTransferCache invoiceLineTypeDescriptionTransferCache;
    protected InvoiceTransferCache invoiceTransferCache;
    protected InvoiceAliasTransferCache invoiceAliasTransferCache;
    protected InvoiceLineTransferCache invoiceLineTransferCache;
    protected InvoiceLineItemTransferCache invoiceLineItemTransferCache;
    protected InvoiceLineGlAccountTransferCache invoiceLineGlAccountTransferCache;
    protected InvoiceTimeTypeTransferCache invoiceTimeTypeTransferCache;
    protected InvoiceTimeTypeDescriptionTransferCache invoiceTimeTypeDescriptionTransferCache;
    protected InvoiceTimeTransferCache invoiceTimeTransferCache;
    
    /** Creates a new instance of InvoiceTransferCaches */
    public InvoiceTransferCaches() {
        super();
    }
    
    public InvoiceLineUseTypeTransferCache getInvoiceLineUseTypeTransferCache() {
        if(invoiceLineUseTypeTransferCache == null)
            invoiceLineUseTypeTransferCache = new InvoiceLineUseTypeTransferCache();
        
        return invoiceLineUseTypeTransferCache;
    }
    
    public InvoiceRoleTypeTransferCache getInvoiceRoleTypeTransferCache() {
        if(invoiceRoleTypeTransferCache == null)
            invoiceRoleTypeTransferCache = new InvoiceRoleTypeTransferCache();
        
        return invoiceRoleTypeTransferCache;
    }
    
    public InvoiceRoleTransferCache getInvoiceRoleTransferCache() {
        if(invoiceRoleTransferCache == null)
            invoiceRoleTransferCache = new InvoiceRoleTransferCache();
        
        return invoiceRoleTransferCache;
    }
    
    public InvoiceTypeTransferCache getInvoiceTypeTransferCache() {
        if(invoiceTypeTransferCache == null)
            invoiceTypeTransferCache = new InvoiceTypeTransferCache();
        
        return invoiceTypeTransferCache;
    }
    
    public InvoiceTypeDescriptionTransferCache getInvoiceTypeDescriptionTransferCache() {
        if(invoiceTypeDescriptionTransferCache == null)
            invoiceTypeDescriptionTransferCache = new InvoiceTypeDescriptionTransferCache();
        
        return invoiceTypeDescriptionTransferCache;
    }
    
    public InvoiceAliasTypeTransferCache getInvoiceAliasTypeTransferCache() {
        if(invoiceAliasTypeTransferCache == null)
            invoiceAliasTypeTransferCache = new InvoiceAliasTypeTransferCache();
        
        return invoiceAliasTypeTransferCache;
    }
    
    public InvoiceAliasTypeDescriptionTransferCache getInvoiceAliasTypeDescriptionTransferCache() {
        if(invoiceAliasTypeDescriptionTransferCache == null)
            invoiceAliasTypeDescriptionTransferCache = new InvoiceAliasTypeDescriptionTransferCache();
        
        return invoiceAliasTypeDescriptionTransferCache;
    }
    
    public InvoiceLineTypeTransferCache getInvoiceLineTypeTransferCache() {
        if(invoiceLineTypeTransferCache == null)
            invoiceLineTypeTransferCache = new InvoiceLineTypeTransferCache();
        
        return invoiceLineTypeTransferCache;
    }
    
    public InvoiceLineTypeDescriptionTransferCache getInvoiceLineTypeDescriptionTransferCache() {
        if(invoiceLineTypeDescriptionTransferCache == null)
            invoiceLineTypeDescriptionTransferCache = new InvoiceLineTypeDescriptionTransferCache();
        
        return invoiceLineTypeDescriptionTransferCache;
    }
    
    public InvoiceTransferCache getInvoiceTransferCache() {
        if(invoiceTransferCache == null)
            invoiceTransferCache = new InvoiceTransferCache();
        
        return invoiceTransferCache;
    }
    
    public InvoiceAliasTransferCache getInvoiceAliasTransferCache() {
        if(invoiceAliasTransferCache == null)
            invoiceAliasTransferCache = new InvoiceAliasTransferCache();
        
        return invoiceAliasTransferCache;
    }
    
    public InvoiceLineTransferCache getInvoiceLineTransferCache() {
        if(invoiceLineTransferCache == null)
            invoiceLineTransferCache = new InvoiceLineTransferCache();
        
        return invoiceLineTransferCache;
    }
    
    public InvoiceLineGlAccountTransferCache getInvoiceLineGlAccountTransferCache() {
        if(invoiceLineGlAccountTransferCache == null)
            invoiceLineGlAccountTransferCache = new InvoiceLineGlAccountTransferCache();
        
        return invoiceLineGlAccountTransferCache;
    }
    
    public InvoiceLineItemTransferCache getInvoiceLineItemTransferCache() {
        if(invoiceLineItemTransferCache == null)
            invoiceLineItemTransferCache = new InvoiceLineItemTransferCache();
        
        return invoiceLineItemTransferCache;
    }
    
    public InvoiceTimeTypeTransferCache getInvoiceTimeTypeTransferCache() {
        if(invoiceTimeTypeTransferCache == null)
            invoiceTimeTypeTransferCache = new InvoiceTimeTypeTransferCache();

        return invoiceTimeTypeTransferCache;
    }

    public InvoiceTimeTransferCache getInvoiceTimeTransferCache() {
        if(invoiceTimeTransferCache == null)
            invoiceTimeTransferCache = new InvoiceTimeTransferCache();

        return invoiceTimeTransferCache;
    }

    public InvoiceTimeTypeDescriptionTransferCache getInvoiceTimeTypeDescriptionTransferCache() {
        if(invoiceTimeTypeDescriptionTransferCache == null)
            invoiceTimeTypeDescriptionTransferCache = new InvoiceTimeTypeDescriptionTransferCache();

        return invoiceTimeTypeDescriptionTransferCache;
    }

}
