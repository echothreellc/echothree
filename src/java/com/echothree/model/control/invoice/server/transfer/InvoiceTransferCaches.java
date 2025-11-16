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
import javax.enterprise.inject.spi.CDI;
import javax.enterprise.context.RequestScoped;

@RequestScoped
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
    protected InvoiceTransferCaches() {
        super();
    }
    
    public InvoiceLineUseTypeTransferCache getInvoiceLineUseTypeTransferCache() {
        if(invoiceLineUseTypeTransferCache == null)
            invoiceLineUseTypeTransferCache = CDI.current().select(InvoiceLineUseTypeTransferCache.class).get();
        
        return invoiceLineUseTypeTransferCache;
    }
    
    public InvoiceRoleTypeTransferCache getInvoiceRoleTypeTransferCache() {
        if(invoiceRoleTypeTransferCache == null)
            invoiceRoleTypeTransferCache = CDI.current().select(InvoiceRoleTypeTransferCache.class).get();
        
        return invoiceRoleTypeTransferCache;
    }
    
    public InvoiceRoleTransferCache getInvoiceRoleTransferCache() {
        if(invoiceRoleTransferCache == null)
            invoiceRoleTransferCache = CDI.current().select(InvoiceRoleTransferCache.class).get();
        
        return invoiceRoleTransferCache;
    }
    
    public InvoiceTypeTransferCache getInvoiceTypeTransferCache() {
        if(invoiceTypeTransferCache == null)
            invoiceTypeTransferCache = CDI.current().select(InvoiceTypeTransferCache.class).get();
        
        return invoiceTypeTransferCache;
    }
    
    public InvoiceTypeDescriptionTransferCache getInvoiceTypeDescriptionTransferCache() {
        if(invoiceTypeDescriptionTransferCache == null)
            invoiceTypeDescriptionTransferCache = CDI.current().select(InvoiceTypeDescriptionTransferCache.class).get();
        
        return invoiceTypeDescriptionTransferCache;
    }
    
    public InvoiceAliasTypeTransferCache getInvoiceAliasTypeTransferCache() {
        if(invoiceAliasTypeTransferCache == null)
            invoiceAliasTypeTransferCache = CDI.current().select(InvoiceAliasTypeTransferCache.class).get();
        
        return invoiceAliasTypeTransferCache;
    }
    
    public InvoiceAliasTypeDescriptionTransferCache getInvoiceAliasTypeDescriptionTransferCache() {
        if(invoiceAliasTypeDescriptionTransferCache == null)
            invoiceAliasTypeDescriptionTransferCache = CDI.current().select(InvoiceAliasTypeDescriptionTransferCache.class).get();
        
        return invoiceAliasTypeDescriptionTransferCache;
    }
    
    public InvoiceLineTypeTransferCache getInvoiceLineTypeTransferCache() {
        if(invoiceLineTypeTransferCache == null)
            invoiceLineTypeTransferCache = CDI.current().select(InvoiceLineTypeTransferCache.class).get();
        
        return invoiceLineTypeTransferCache;
    }
    
    public InvoiceLineTypeDescriptionTransferCache getInvoiceLineTypeDescriptionTransferCache() {
        if(invoiceLineTypeDescriptionTransferCache == null)
            invoiceLineTypeDescriptionTransferCache = CDI.current().select(InvoiceLineTypeDescriptionTransferCache.class).get();
        
        return invoiceLineTypeDescriptionTransferCache;
    }
    
    public InvoiceTransferCache getInvoiceTransferCache() {
        if(invoiceTransferCache == null)
            invoiceTransferCache = CDI.current().select(InvoiceTransferCache.class).get();
        
        return invoiceTransferCache;
    }
    
    public InvoiceAliasTransferCache getInvoiceAliasTransferCache() {
        if(invoiceAliasTransferCache == null)
            invoiceAliasTransferCache = CDI.current().select(InvoiceAliasTransferCache.class).get();
        
        return invoiceAliasTransferCache;
    }
    
    public InvoiceLineTransferCache getInvoiceLineTransferCache() {
        if(invoiceLineTransferCache == null)
            invoiceLineTransferCache = CDI.current().select(InvoiceLineTransferCache.class).get();
        
        return invoiceLineTransferCache;
    }
    
    public InvoiceLineGlAccountTransferCache getInvoiceLineGlAccountTransferCache() {
        if(invoiceLineGlAccountTransferCache == null)
            invoiceLineGlAccountTransferCache = CDI.current().select(InvoiceLineGlAccountTransferCache.class).get();
        
        return invoiceLineGlAccountTransferCache;
    }
    
    public InvoiceLineItemTransferCache getInvoiceLineItemTransferCache() {
        if(invoiceLineItemTransferCache == null)
            invoiceLineItemTransferCache = CDI.current().select(InvoiceLineItemTransferCache.class).get();
        
        return invoiceLineItemTransferCache;
    }
    
    public InvoiceTimeTypeTransferCache getInvoiceTimeTypeTransferCache() {
        if(invoiceTimeTypeTransferCache == null)
            invoiceTimeTypeTransferCache = CDI.current().select(InvoiceTimeTypeTransferCache.class).get();

        return invoiceTimeTypeTransferCache;
    }

    public InvoiceTimeTransferCache getInvoiceTimeTransferCache() {
        if(invoiceTimeTransferCache == null)
            invoiceTimeTransferCache = CDI.current().select(InvoiceTimeTransferCache.class).get();

        return invoiceTimeTransferCache;
    }

    public InvoiceTimeTypeDescriptionTransferCache getInvoiceTimeTypeDescriptionTransferCache() {
        if(invoiceTimeTypeDescriptionTransferCache == null)
            invoiceTimeTypeDescriptionTransferCache = CDI.current().select(InvoiceTimeTypeDescriptionTransferCache.class).get();

        return invoiceTimeTypeDescriptionTransferCache;
    }

}
