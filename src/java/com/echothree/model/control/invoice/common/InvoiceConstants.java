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

package com.echothree.model.control.invoice.common;

public interface InvoiceConstants {
    
    String InvoiceType_PURCHASE_INVOICE = "PURCHASE_INVOICE";
    String InvoiceType_SALES_INVOICE    = "SALES_INVOICE";

    String InvoiceTimeType_INVOICED = "INVOICED";
    String InvoiceTimeType_DUE = "DUE";
    String InvoiceTimeType_PAID = "PAID";
    
    String InvoiceRoleType_INVOICE_FROM = "INVOICE_FROM";
    String InvoiceRoleType_INVOICE_TO   = "INVOICE_TO";
    
    String InvoiceLineUseType_ITEM       = "ITEM";
    String InvoiceLineUseType_GL_ACCOUNT = "GL_ACCOUNT";

}
