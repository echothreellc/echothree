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

package com.echothree.model.control.warehouse.common.transfer;

import com.echothree.model.control.uom.common.transfer.UnitOfMeasureTypeTransfer;
import com.echothree.util.common.transfer.BaseTransfer;

public class LocationCapacityTransfer
        extends BaseTransfer {
    
    private LocationTransfer location;
    private UnitOfMeasureTypeTransfer unitOfMeasureType;
    private Long capacity;
    
    /** Creates a new instance of LocationCapacityTransfer */
    public LocationCapacityTransfer(LocationTransfer location, UnitOfMeasureTypeTransfer unitOfMeasureType, Long capacity) {
        this.location = location;
        this.unitOfMeasureType = unitOfMeasureType;
        this.capacity = capacity;
    }
    
    public LocationTransfer getLocation() {
        return location;
    }
    
    public void setLocation(LocationTransfer location) {
        this.location = location;
    }
    
    public UnitOfMeasureTypeTransfer getUnitOfMeasureType() {
        return unitOfMeasureType;
    }
    
    public void setUnitOfMeasureType(UnitOfMeasureTypeTransfer unitOfMeasureType) {
        this.unitOfMeasureType = unitOfMeasureType;
    }
    
    public Long getCapacity() {
        return capacity;
    }
    
    public void setCapacity(Long capacity) {
        this.capacity = capacity;
    }
    
}
