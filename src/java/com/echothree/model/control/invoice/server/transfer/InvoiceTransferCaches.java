// --------------------------------------------------------------------------------
// Copyright 2002-2019 Echo Three, LLC
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

import com.echothree.model.control.invoice.server.InvoiceControl;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.transfer.BaseTransferCaches;

public class InvoiceTransferCaches
        extends BaseTransferCaches {
    
    protected InvoiceControl invoiceControl;
    
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
    public InvoiceTransferCaches(UserVisit userVisit, InvoiceControl invoiceControl) {
        super(userVisit);
        
        this.invoiceControl = invoiceControl;
    }
    
    public InvoiceLineUseTypeTransferCache getInvoiceLineUseTypeTransferCache() {
        if(invoiceLineUseTypeTransferCache == null)
            invoiceLineUseTypeTransferCache = new InvoiceLineUseTypeTransferCache(userVisit, invoiceControl);
        
        return invoiceLineUseTypeTransferCache;
    }
    
    public InvoiceRoleTypeTransferCache getInvoiceRoleTypeTransferCache() {
        if(invoiceRoleTypeTransferCache == null)
            invoiceRoleTypeTransferCache = new InvoiceRoleTypeTransferCache(userVisit, invoiceControl);
        
        return invoiceRoleTypeTransferCache;
    }
    
    public InvoiceRoleTransferCache getInvoiceRoleTransferCache() {
        if(invoiceRoleTransferCache == null)
            invoiceRoleTransferCache = new InvoiceRoleTransferCache(userVisit, invoiceControl);
        
        return invoiceRoleTransferCache;
    }
    
    public InvoiceTypeTransferCache getInvoiceTypeTransferCache() {
        if(invoiceTypeTransferCache == null)
            invoiceTypeTransferCache = new InvoiceTypeTransferCache(userVisit, invoiceControl);
        
        return invoiceTypeTransferCache;
    }
    
    public InvoiceTypeDescriptionTransferCache getInvoiceTypeDescriptionTransferCache() {
        if(invoiceTypeDescriptionTransferCache == null)
            invoiceTypeDescriptionTransferCache = new InvoiceTypeDescriptionTransferCache(userVisit, invoiceControl);
        
        return invoiceTypeDescriptionTransferCache;
    }
    
    public InvoiceAliasTypeTransferCache getInvoiceAliasTypeTransferCache() {
        if(invoiceAliasTypeTransferCache == null)
            invoiceAliasTypeTransferCache = new InvoiceAliasTypeTransferCache(userVisit, invoiceControl);
        
        return invoiceAliasTypeTransferCache;
    }
    
    public InvoiceAliasTypeDescriptionTransferCache getInvoiceAliasTypeDescriptionTransferCache() {
        if(invoiceAliasTypeDescriptionTransferCache == null)
            invoiceAliasTypeDescriptionTransferCache = new InvoiceAliasTypeDescriptionTransferCache(userVisit, invoiceControl);
        
        return invoiceAliasTypeDescriptionTransferCache;
    }
    
    public InvoiceLineTypeTransferCache getInvoiceLineTypeTransferCache() {
        if(invoiceLineTypeTransferCache == null)
            invoiceLineTypeTransferCache = new InvoiceLineTypeTransferCache(userVisit, invoiceControl);
        
        return invoiceLineTypeTransferCache;
    }
    
    public InvoiceLineTypeDescriptionTransferCache getInvoiceLineTypeDescriptionTransferCache() {
        if(invoiceLineTypeDescriptionTransferCache == null)
            invoiceLineTypeDescriptionTransferCache = new InvoiceLineTypeDescriptionTransferCache(userVisit, invoiceControl);
        
        return invoiceLineTypeDescriptionTransferCache;
    }
    
    public InvoiceTransferCache getInvoiceTransferCache() {
        if(invoiceTransferCache == null)
            invoiceTransferCache = new InvoiceTransferCache(userVisit, invoiceControl);
        
        return invoiceTransferCache;
    }
    
    public InvoiceAliasTransferCache getInvoiceAliasTransferCache() {
        if(invoiceAliasTransferCache == null)
            invoiceAliasTransferCache = new InvoiceAliasTransferCache(userVisit, invoiceControl);
        
        return invoiceAliasTransferCache;
    }
    
    public InvoiceLineTransferCache getInvoiceLineTransferCache() {
        if(invoiceLineTransferCache == null)
            invoiceLineTransferCache = new InvoiceLineTransferCache(userVisit, invoiceControl);
        
        return invoiceLineTransferCache;
    }
    
    public InvoiceLineGlAccountTransferCache getInvoiceLineGlAccountTransferCache() {
        if(invoiceLineGlAccountTransferCache == null)
            invoiceLineGlAccountTransferCache = new InvoiceLineGlAccountTransferCache(userVisit, invoiceControl);
        
        return invoiceLineGlAccountTransferCache;
    }
    
    public InvoiceLineItemTransferCache getInvoiceLineItemTransferCache() {
        if(invoiceLineItemTransferCache == null)
            invoiceLineItemTransferCache = new InvoiceLineItemTransferCache(userVisit, invoiceControl);
        
        return invoiceLineItemTransferCache;
    }
    
    public InvoiceTimeTypeTransferCache getInvoiceTimeTypeTransferCache() {
        if(invoiceTimeTypeTransferCache == null)
            invoiceTimeTypeTransferCache = new InvoiceTimeTypeTransferCache(userVisit, invoiceControl);

        return invoiceTimeTypeTransferCache;
    }

    public InvoiceTimeTransferCache getInvoiceTimeTransferCache() {
        if(invoiceTimeTransferCache == null)
            invoiceTimeTransferCache = new InvoiceTimeTransferCache(userVisit, invoiceControl);

        return invoiceTimeTransferCache;
    }

    public InvoiceTimeTypeDescriptionTransferCache getInvoiceTimeTypeDescriptionTransferCache() {
        if(invoiceTimeTypeDescriptionTransferCache == null)
            invoiceTimeTypeDescriptionTransferCache = new InvoiceTimeTypeDescriptionTransferCache(userVisit, invoiceControl);

        return invoiceTimeTypeDescriptionTransferCache;
    }

}
