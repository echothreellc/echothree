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

package com.echothree.model.control.item.server.transfer;

import com.echothree.model.control.item.common.transfer.ItemWeightTransfer;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.control.uom.common.UomConstants;
import com.echothree.model.control.uom.server.control.UomControl;
import com.echothree.model.data.item.server.entity.ItemWeight;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class ItemWeightTransferCache
        extends BaseItemTransferCache<ItemWeight, ItemWeightTransfer> {

    ItemControl itemControl = Session.getModelController(ItemControl.class);
    UomControl uomControl = Session.getModelController(UomControl.class);
    
    /** Creates a new instance of ItemWeightTransferCache */
    protected ItemWeightTransferCache() {
        super();
    }
    
    @Override
    public ItemWeightTransfer getTransfer(UserVisit userVisit, ItemWeight itemWeight) {
        var itemWeightTransfer = get(itemWeight);
        
        if(itemWeightTransfer == null) {
            var itemTransfer = itemControl.getItemTransfer(userVisit, itemWeight.getItem());
            var unitOfMeasureTypeTransfer = uomControl.getUnitOfMeasureTypeTransfer(userVisit, itemWeight.getUnitOfMeasureType());
            var itemWeightType = itemControl.getItemWeightTypeTransfer(userVisit, itemWeight.getItemWeightType());
            var weightUnitOfMeasureKind = uomControl.getUnitOfMeasureKindByUnitOfMeasureKindUseTypeUsingNames(UomConstants.UnitOfMeasureKindUseType_WEIGHT);
            var weight = formatUnitOfMeasure(userVisit, weightUnitOfMeasureKind, itemWeight.getWeight());
            
            itemWeightTransfer = new ItemWeightTransfer(itemTransfer, unitOfMeasureTypeTransfer, itemWeightType, weight);
            put(userVisit, itemWeight, itemWeightTransfer);
        }
        
        return itemWeightTransfer;
    }
    
}
