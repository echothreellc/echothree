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

public class FilteredItemFixedPrice
        extends FilteredItemPrice implements Comparable<FilteredItemFixedPrice>, Cloneable {
    
    protected Long unitPrice;
    
    /** Creates a new instance of FilteredItemFixedPrice */
    public FilteredItemFixedPrice(Item item, UnitOfMeasureType unitOfMeasureType, Currency currency, Long unitPrice) {
        super(item, unitOfMeasureType, currency);
        
        this.unitPrice = unitPrice;
    }
    
    public void setUnitPrice(Long unitPrice) {
        this.unitPrice = unitPrice;
    }
    
    public Long getUnitPrice() {
        return unitPrice;
    }
    
    @Override
    public int compareTo(FilteredItemFixedPrice obj) {
        return unitPrice.compareTo(obj.unitPrice);
    }
    
    @Override
    public FilteredItemFixedPrice clone()
            throws CloneNotSupportedException {
        return (FilteredItemFixedPrice)super.clone();
    }
    
}
