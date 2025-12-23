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

package com.echothree.model.control.invoice.common.transfer;

import com.echothree.util.common.transfer.BaseTransfer;

public class InvoiceTimeTypeTransfer
        extends BaseTransfer {
    
    private String invoiceTimeTypeName;
    private Boolean isDefault;
    private Integer sortInvoice;
    private String description;
    
    /** Creates a new instance of InvoiceTimeTypeTransfer */
    public InvoiceTimeTypeTransfer(String invoiceTimeTypeName, Boolean isDefault, Integer sortInvoice, String description) {
        this.invoiceTimeTypeName = invoiceTimeTypeName;
        this.isDefault = isDefault;
        this.sortInvoice = sortInvoice;
        this.description = description;
    }

    public String getInvoiceTimeTypeName() {
        return invoiceTimeTypeName;
    }

    public void setInvoiceTimeTypeName(String invoiceTimeTypeName) {
        this.invoiceTimeTypeName = invoiceTimeTypeName;
    }

    public Boolean getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(Boolean isDefault) {
        this.isDefault = isDefault;
    }

    public Integer getSortInvoice() {
        return sortInvoice;
    }

    public void setSortInvoice(Integer sortInvoice) {
        this.sortInvoice = sortInvoice;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
}
