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

package com.echothree.model.control.item.common.transfer;

import com.echothree.model.control.uom.common.transfer.UnitOfMeasureTypeTransfer;
import com.echothree.util.common.transfer.BaseTransfer;

public class ItemWeightTransfer
        extends BaseTransfer {
    
    private ItemTransfer item;
    private UnitOfMeasureTypeTransfer unitOfMeasureType;
    private ItemWeightTypeTransfer itemWeightTypeTransfer;
    private String weight;
    
    /** Creates a new instance of ItemWeightTransfer */
    public ItemWeightTransfer(ItemTransfer item, UnitOfMeasureTypeTransfer unitOfMeasureType,
            ItemWeightTypeTransfer itemWeightTypeTransfer, String weight) {
        this.item = item;
        this.unitOfMeasureType = unitOfMeasureType;
        this.itemWeightTypeTransfer = itemWeightTypeTransfer;
        this.weight = weight;
    }
    
    public ItemTransfer getItem() {
        return item;
    }
    
    public void setItem(final ItemTransfer item) {
        this.item = item;
    }
    
    public UnitOfMeasureTypeTransfer getUnitOfMeasureType() {
        return unitOfMeasureType;
    }
    
    public void setUnitOfMeasureType(final UnitOfMeasureTypeTransfer unitOfMeasureType) {
        this.unitOfMeasureType = unitOfMeasureType;
    }

    public ItemWeightTypeTransfer getItemWeightTypeTransfer() {
        return itemWeightTypeTransfer;
    }

    public void setItemWeightTypeTransfer(final ItemWeightTypeTransfer itemWeightTypeTransfer) {
        this.itemWeightTypeTransfer = itemWeightTypeTransfer;
    }

    public String getWeight() {
        return weight;
    }
    
    public void setWeight(final String weight) {
        this.weight = weight;
    }
    
}
