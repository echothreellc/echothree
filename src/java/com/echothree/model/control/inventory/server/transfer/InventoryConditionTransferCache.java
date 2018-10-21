// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
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

package com.echothree.model.control.inventory.server.transfer;

import com.echothree.model.control.inventory.common.InventoryProperties;
import com.echothree.model.control.inventory.remote.transfer.InventoryConditionTransfer;
import com.echothree.model.control.inventory.server.InventoryControl;
import com.echothree.model.data.inventory.server.entity.InventoryCondition;
import com.echothree.model.data.inventory.server.entity.InventoryConditionDetail;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.remote.form.TransferProperties;
import java.util.Set;

public class InventoryConditionTransferCache
        extends BaseInventoryTransferCache<InventoryCondition, InventoryConditionTransfer> {
    
    TransferProperties transferProperties;
    boolean filterInventoryConditionName;
    boolean filterIsDefault;
    boolean filterSortOrder;
    boolean filterDescription;
    boolean filterEntityInstance;
    
    /** Creates a new instance of InventoryConditionTransferCache */
    public InventoryConditionTransferCache(UserVisit userVisit, InventoryControl inventoryControl) {
        super(userVisit, inventoryControl);

        transferProperties = session.getTransferProperties();
        if(transferProperties != null) {
            Set<String> properties = transferProperties.getProperties(InventoryConditionTransfer.class);
            
            if(properties != null) {
                filterInventoryConditionName = !properties.contains(InventoryProperties.INVENTORY_CONDITION_NAME);
                filterIsDefault = !properties.contains(InventoryProperties.IS_DEFAULT);
                filterSortOrder = !properties.contains(InventoryProperties.SORT_ORDER);
                filterDescription = !properties.contains(InventoryProperties.DESCRIPTION);
                filterEntityInstance = !properties.contains(InventoryProperties.ENTITY_INSTANCE);
            }
        }
        
        setIncludeEntityInstance(!filterEntityInstance);
    }
    
    public InventoryConditionTransfer getInventoryConditionTransfer(InventoryCondition inventoryCondition) {
        InventoryConditionTransfer inventoryConditionTransfer = get(inventoryCondition);
        
        if(inventoryConditionTransfer == null) {
            InventoryConditionDetail inventoryConditionDetail = inventoryCondition.getLastDetail();
            String inventoryConditionName = filterInventoryConditionName ? null : inventoryConditionDetail.getInventoryConditionName();
            Boolean isDefault = filterIsDefault ? null : inventoryConditionDetail.getIsDefault();
            Integer sortOrder = filterSortOrder ? null : inventoryConditionDetail.getSortOrder();
            String description = filterDescription ? null : inventoryControl.getBestInventoryConditionDescription(inventoryCondition, getLanguage());
            
            inventoryConditionTransfer = new InventoryConditionTransfer(inventoryConditionName, isDefault, sortOrder, description);
            put(inventoryCondition, inventoryConditionTransfer);
        }
        
        return inventoryConditionTransfer;
    }
    
}
