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

package com.echothree.model.control.inventory.server.transfer;

import javax.inject.Inject;
import com.echothree.model.control.inventory.common.transfer.InventoryBucketTypeDescriptionTransfer;
import com.echothree.model.control.inventory.server.control.InventoryBucketTypeControl;
import com.echothree.model.data.inventory.server.entity.InventoryBucketTypeDescription;
import com.echothree.model.data.user.server.entity.UserVisit;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class InventoryBucketTypeDescriptionTransferCache
        extends BaseInventoryDescriptionTransferCache<InventoryBucketTypeDescription, InventoryBucketTypeDescriptionTransfer> {

    @Inject
    InventoryBucketTypeControl inventoryBucketTypeControl;

    /** Creates a new instance of InventoryBucketTypeDescriptionTransferCache */
    protected InventoryBucketTypeDescriptionTransferCache() {
        super();
    }

    @Override
    public InventoryBucketTypeDescriptionTransfer getTransfer(UserVisit userVisit, InventoryBucketTypeDescription inventoryBucketTypeDescription) {
        var inventoryBucketTypeDescriptionTransfer = get(inventoryBucketTypeDescription);
        
        if(inventoryBucketTypeDescriptionTransfer == null) {
            var inventoryBucketTypeTransfer = inventoryBucketTypeControl.getInventoryBucketTypeTransfer(userVisit, inventoryBucketTypeDescription.getInventoryBucketType());
            var languageTransfer = partyControl.getLanguageTransfer(userVisit, inventoryBucketTypeDescription.getLanguage());
            
            inventoryBucketTypeDescriptionTransfer = new InventoryBucketTypeDescriptionTransfer(languageTransfer, inventoryBucketTypeTransfer, inventoryBucketTypeDescription.getDescription());
            put(userVisit, inventoryBucketTypeDescription, inventoryBucketTypeDescriptionTransfer);
        }
        
        return inventoryBucketTypeDescriptionTransfer;
    }
    
}
