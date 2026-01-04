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

public class InvoiceTimeTransfer
        extends BaseTransfer {

    private InvoiceTimeTypeTransfer invoiceTimeType;
    private Long unformattedTime;
    private String time;
    
    /** Creates a new instance of InvoiceTimeTransfer */
    public InvoiceTimeTransfer(InvoiceTimeTypeTransfer invoiceTimeType, Long unformattedTime, String time) {
        this.invoiceTimeType = invoiceTimeType;
        this.unformattedTime = unformattedTime;
        this.time = time;
    }

    public InvoiceTimeTypeTransfer getInvoiceTimeType() {
        return invoiceTimeType;
    }

    public void setInvoiceTimeType(InvoiceTimeTypeTransfer invoiceTimeType) {
        this.invoiceTimeType = invoiceTimeType;
    }

    public Long getUnformattedTime() {
        return unformattedTime;
    }

    public void setUnformattedTime(Long unformattedTime) {
        this.unformattedTime = unformattedTime;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
    
}
