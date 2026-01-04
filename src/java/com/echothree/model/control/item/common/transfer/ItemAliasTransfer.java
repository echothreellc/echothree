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

package com.echothree.model.control.item.common.transfer;

import com.echothree.model.control.uom.common.transfer.UnitOfMeasureTypeTransfer;
import com.echothree.util.common.transfer.BaseTransfer;

public class ItemAliasTransfer
        extends BaseTransfer {
    
    private ItemTransfer item;
    private UnitOfMeasureTypeTransfer unitOfMeasureType;
    private ItemAliasTypeTransfer itemAliasType;
    private String alias;
    
    /** Creates a new instance of ItemAliasTransfer */
    public ItemAliasTransfer(ItemTransfer item, UnitOfMeasureTypeTransfer unitOfMeasureType, ItemAliasTypeTransfer itemAliasType,
            String alias) {
        this.item = item;
        this.unitOfMeasureType = unitOfMeasureType;
        this.itemAliasType = itemAliasType;
        this.alias = alias;
    }
    
    public ItemTransfer getItem() {
        return item;
    }
    
    public void setItem(ItemTransfer item) {
        this.item = item;
    }
    
    public UnitOfMeasureTypeTransfer getUnitOfMeasureType() {
        return unitOfMeasureType;
    }
    
    public void setUnitOfMeasureType(UnitOfMeasureTypeTransfer unitOfMeasureType) {
        this.unitOfMeasureType = unitOfMeasureType;
    }
    
    public ItemAliasTypeTransfer getItemAliasType() {
        return itemAliasType;
    }
    
    public void setItemAliasType(ItemAliasTypeTransfer itemAliasType) {
        this.itemAliasType = itemAliasType;
    }
    
    public String getAlias() {
        return alias;
    }
    
    public void setAlias(String alias) {
        this.alias = alias;
    }
    
}
