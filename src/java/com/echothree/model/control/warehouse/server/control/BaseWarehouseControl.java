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

import com.echothree.model.control.warehouse.server.transfer.LocationCapacityTransferCache;
import com.echothree.model.control.warehouse.server.transfer.LocationDescriptionTransferCache;
import com.echothree.model.control.warehouse.server.transfer.LocationNameElementDescriptionTransferCache;
import com.echothree.model.control.warehouse.server.transfer.LocationNameElementTransferCache;
import com.echothree.model.control.warehouse.server.transfer.LocationTransferCache;
import com.echothree.model.control.warehouse.server.transfer.LocationTypeDescriptionTransferCache;
import com.echothree.model.control.warehouse.server.transfer.LocationTypeTransferCache;
import com.echothree.model.control.warehouse.server.transfer.LocationUseTypeTransferCache;
import com.echothree.model.control.warehouse.server.transfer.LocationVolumeTransferCache;
import com.echothree.model.control.warehouse.server.transfer.WarehouseTransferCache;
import com.echothree.model.control.warehouse.server.transfer.WarehouseTypeDescriptionTransferCache;
import com.echothree.model.control.warehouse.server.transfer.WarehouseTypeTransferCache;
import com.echothree.util.server.control.BaseModelControl;
import javax.inject.Inject;

public abstract class BaseWarehouseControl
        extends BaseModelControl {

    /** Creates a new instance of BaseWarehouseControl */
    protected BaseWarehouseControl() {
        super();
    }
    
    // --------------------------------------------------------------------------------
    //   Warehouse Transfer Caches
    // --------------------------------------------------------------------------------

    @Inject
    LocationDescriptionTransferCache locationDescriptionTransferCache;

    @Inject
    LocationNameElementDescriptionTransferCache locationNameElementDescriptionTransferCache;

    @Inject
    LocationNameElementTransferCache locationNameElementTransferCache;

    @Inject
    LocationTransferCache locationTransferCache;

    @Inject
    LocationTypeDescriptionTransferCache locationTypeDescriptionTransferCache;

    @Inject
    LocationTypeTransferCache locationTypeTransferCache;

    @Inject
    LocationUseTypeTransferCache locationUseTypeTransferCache;

    @Inject
    WarehouseTransferCache warehouseTransferCache;

    @Inject
    LocationVolumeTransferCache locationVolumeTransferCache;

    @Inject
    LocationCapacityTransferCache locationCapacityTransferCache;

    @Inject
    WarehouseTypeTransferCache warehouseTypeTransferCache;

    @Inject
    WarehouseTypeDescriptionTransferCache warehouseTypeDescriptionTransferCache;

}
