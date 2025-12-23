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

package com.echothree.model.control.filter.server.evaluator;

import com.echothree.model.data.accounting.server.entity.Currency;
import com.echothree.model.data.item.server.entity.Item;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureType;

public class FilteredItemPrice
        implements Cloneable {
    
    protected Item item;
    protected UnitOfMeasureType unitOfMeasureType;
    protected Currency currency;
    
    /** Creates a new instance of FilteredItemPrice */
    public FilteredItemPrice(Item item, UnitOfMeasureType unitOfMeasureType, Currency currency) {
        this.item = item;
        this.unitOfMeasureType = unitOfMeasureType;
        this.currency = currency;
    }
    
    public void setItem(Item item) {
        this.item = item;
    }
    
    public Item getItem() {
        return item;
    }
    
    public void setUnitOfMeasureType(UnitOfMeasureType unitOfMeasureType) {
        this.unitOfMeasureType = unitOfMeasureType;
    }
    
    public UnitOfMeasureType getUnitOfMeasureType() {
        return unitOfMeasureType;
    }
    
    public void setCurrency(Currency currency) {
        this.currency = currency;
    }
    
    public Currency getCurrency() {
        return currency;
    }
    
    @Override
    public FilteredItemPrice clone()
            throws CloneNotSupportedException {
        return (FilteredItemPrice)super.clone();
    }
    
}
