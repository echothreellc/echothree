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

import com.echothree.model.control.inventory.common.transfer.InventoryConditionTransfer;
import com.echothree.model.control.item.common.transfer.ItemTransfer;
import com.echothree.model.control.uom.common.transfer.UnitOfMeasureTypeTransfer;
import com.echothree.util.common.transfer.BaseTransfer;

public class InvoiceLineItemTransfer
        extends BaseTransfer {
    
    private InvoiceLineTransfer invoiceLine;
    private ItemTransfer item;
    private InventoryConditionTransfer inventoryCondition;
    private UnitOfMeasureTypeTransfer unitOfMeasureType;
    private Integer quantity;
    
    /** Creates a new instance of InvoiceLineItemTransfer */
    public InvoiceLineItemTransfer(InvoiceLineTransfer invoiceLine, ItemTransfer item, InventoryConditionTransfer inventoryCondition, UnitOfMeasureTypeTransfer unitOfMeasureType, Integer quantity) {
        this.invoiceLine = invoiceLine;
        this.item = item;
        this.inventoryCondition = inventoryCondition;
        this.unitOfMeasureType = unitOfMeasureType;
        this.quantity = quantity;
    }

    public InvoiceLineTransfer getInvoiceLine() {
        return invoiceLine;
    }

    public void setInvoiceLine(InvoiceLineTransfer invoiceLine) {
        this.invoiceLine = invoiceLine;
    }

    public ItemTransfer getItem() {
        return item;
    }

    public void setItem(ItemTransfer item) {
        this.item = item;
    }

    public InventoryConditionTransfer getInventoryCondition() {
        return inventoryCondition;
    }

    public void setInventoryCondition(InventoryConditionTransfer inventoryCondition) {
        this.inventoryCondition = inventoryCondition;
    }

    public UnitOfMeasureTypeTransfer getUnitOfMeasureType() {
        return unitOfMeasureType;
    }

    public void setUnitOfMeasureType(UnitOfMeasureTypeTransfer unitOfMeasureType) {
        this.unitOfMeasureType = unitOfMeasureType;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
    
}
