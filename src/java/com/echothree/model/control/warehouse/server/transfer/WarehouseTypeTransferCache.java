// --------------------------------------------------------------------------------
// Copyright 2002-2024 Echo Three, LLC
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

package com.echothree.model.control.warehouse.server.transfer;

import com.echothree.model.control.warehouse.common.WarehouseOptions;
import com.echothree.model.control.warehouse.common.transfer.WarehouseTypeTransfer;
import com.echothree.model.control.warehouse.server.control.WarehouseControl;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.model.data.warehouse.server.entity.WarehouseType;
import com.echothree.model.data.warehouse.server.entity.WarehouseTypeDetail;

public class WarehouseTypeTransferCache
        extends BaseWarehouseTransferCache<WarehouseType, WarehouseTypeTransfer> {

    /** Creates a new instance of WarehouseTypeTransferCache */
    public WarehouseTypeTransferCache(UserVisit userVisit, WarehouseControl warehouseControl) {
        super(userVisit, warehouseControl);

        var options = session.getOptions();
        if(options != null) {
            setIncludeKey(options.contains(WarehouseOptions.WarehouseTypeIncludeKey));
            setIncludeGuid(options.contains(WarehouseOptions.WarehouseTypeIncludeGuid));
            setIncludeEntityAttributeGroups(options.contains(WarehouseOptions.WarehouseTypeIncludeEntityAttributeGroups));
            setIncludeTagScopes(options.contains(WarehouseOptions.WarehouseTypeIncludeTagScopes));
        }

        setIncludeEntityInstance(true);
    }

    public WarehouseTypeTransfer getTransfer(WarehouseType warehouseType) {
        WarehouseTypeTransfer warehouseTypeTransfer = get(warehouseType);
        
        if(warehouseTypeTransfer == null) {
            WarehouseTypeDetail warehouseTypeDetail = warehouseType.getLastDetail();
            String warehouseTypeName = warehouseTypeDetail.getWarehouseTypeName();
            Integer priority = warehouseTypeDetail.getPriority();
            Boolean isDefault = warehouseTypeDetail.getIsDefault();
            Integer sortOrder = warehouseTypeDetail.getSortOrder();
            String description = warehouseControl.getBestWarehouseTypeDescription(warehouseType, getLanguage());
            
            warehouseTypeTransfer = new WarehouseTypeTransfer(warehouseTypeName, priority, isDefault, sortOrder,
                    description);
            put(warehouseType, warehouseTypeTransfer);
        }
        
        return warehouseTypeTransfer;
    }
    
}
