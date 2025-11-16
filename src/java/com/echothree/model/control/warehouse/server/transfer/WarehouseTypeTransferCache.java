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

package com.echothree.model.control.warehouse.server.transfer;

import com.echothree.model.control.warehouse.common.WarehouseOptions;
import com.echothree.model.control.warehouse.common.transfer.WarehouseTypeTransfer;
import com.echothree.model.control.warehouse.server.control.WarehouseControl;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.model.data.warehouse.server.entity.WarehouseType;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class WarehouseTypeTransferCache
        extends BaseWarehouseTransferCache<WarehouseType, WarehouseTypeTransfer> {

    WarehouseControl warehouseControl = Session.getModelController(WarehouseControl.class);

    /** Creates a new instance of WarehouseTypeTransferCache */
    public WarehouseTypeTransferCache() {
        super();

        var options = session.getOptions();
        if(options != null) {
            setIncludeUuid(options.contains(WarehouseOptions.WarehouseTypeIncludeUuid));
            setIncludeEntityAttributeGroups(options.contains(WarehouseOptions.WarehouseTypeIncludeEntityAttributeGroups));
            setIncludeTagScopes(options.contains(WarehouseOptions.WarehouseTypeIncludeTagScopes));
        }

        setIncludeEntityInstance(true);
    }

    public WarehouseTypeTransfer getTransfer(UserVisit userVisit, WarehouseType warehouseType) {
        var warehouseTypeTransfer = get(warehouseType);
        
        if(warehouseTypeTransfer == null) {
            var warehouseTypeDetail = warehouseType.getLastDetail();
            var warehouseTypeName = warehouseTypeDetail.getWarehouseTypeName();
            var priority = warehouseTypeDetail.getPriority();
            var isDefault = warehouseTypeDetail.getIsDefault();
            var sortOrder = warehouseTypeDetail.getSortOrder();
            var description = warehouseControl.getBestWarehouseTypeDescription(warehouseType, getLanguage(userVisit));
            
            warehouseTypeTransfer = new WarehouseTypeTransfer(warehouseTypeName, priority, isDefault, sortOrder,
                    description);
            put(userVisit, warehouseType, warehouseTypeTransfer);
        }
        
        return warehouseTypeTransfer;
    }
    
}
