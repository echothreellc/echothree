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

import com.echothree.model.control.inventory.server.control.InventoryControl;
import com.echothree.model.control.invoice.common.transfer.InvoiceLineItemTransfer;
import com.echothree.model.control.invoice.server.control.InvoiceControl;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.control.uom.server.control.UomControl;
import com.echothree.model.data.invoice.server.entity.InvoiceLineItem;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class InvoiceLineItemTransferCache
        extends BaseInvoiceTransferCache<InvoiceLineItem, InvoiceLineItemTransfer> {

    InventoryControl inventoryControl;
    ItemControl itemControl;
    UomControl uomControl;

    /** Creates a new instance of InvoiceLineItemTransferCache */
    public InvoiceLineItemTransferCache(InvoiceControl invoiceControl) {
        super(invoiceControl);

        inventoryControl = Session.getModelController(InventoryControl.class);
        itemControl = Session.getModelController(ItemControl.class);
        uomControl = Session.getModelController(UomControl.class);
    }

    public InvoiceLineItemTransfer getInvoiceLineItemTransfer(UserVisit userVisit, InvoiceLineItem invoiceLineItem) {
        var invoiceLineItemTransfer = get(invoiceLineItem);

        if(invoiceLineItemTransfer == null) {
            var invoiceLine = invoiceControl.getInvoiceLineTransfer(userVisit, invoiceLineItem.getInvoiceLine());
            var item = itemControl.getItemTransfer(userVisit, invoiceLineItem.getItem());
            var inventoryCondition = inventoryControl.getInventoryConditionTransfer(userVisit, invoiceLineItem.getInventoryCondition());
            var unitOfMeasureType = uomControl.getUnitOfMeasureTypeTransfer(userVisit, invoiceLineItem.getUnitOfMeasureType());
            var quantity = invoiceLineItem.getQuantity();
            
            invoiceLineItemTransfer = new InvoiceLineItemTransfer(invoiceLine, item, inventoryCondition, unitOfMeasureType, quantity);
            put(userVisit, invoiceLineItem, invoiceLineItemTransfer);
        }

        return invoiceLineItemTransfer;
    }
}