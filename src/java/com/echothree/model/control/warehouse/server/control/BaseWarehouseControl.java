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

package com.echothree.model.control.warehouse.server.control;

import com.echothree.model.control.warehouse.server.transfer.WarehouseTransferCaches;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.control.BaseModelControl;
import com.echothree.util.server.persistence.Session;

public abstract class BaseWarehouseControl
        extends BaseModelControl {

    /** Creates a new instance of BaseWarehouseControl */
    protected BaseWarehouseControl() {
        super();
    }
    
    // --------------------------------------------------------------------------------
    //   Warehouse Transfer Caches
    // --------------------------------------------------------------------------------
    
    private WarehouseTransferCaches warehouseTransferCaches;
    
    public WarehouseTransferCaches getWarehouseTransferCaches() {
        if(warehouseTransferCaches == null) {
            var warehouseControl = Session.getModelController(WarehouseControl.class);

            warehouseTransferCaches = new WarehouseTransferCaches(userVisit, warehouseControl);
        }
        
        return warehouseTransferCaches;
    }

}
