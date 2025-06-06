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

package com.echothree.control.user.warehouse.common.result;

import com.echothree.model.control.warehouse.common.transfer.LocationCapacityTransfer;
import com.echothree.model.control.warehouse.common.transfer.LocationNameElementTransfer;
import com.echothree.model.control.warehouse.common.transfer.LocationTransfer;
import com.echothree.model.control.warehouse.common.transfer.LocationVolumeTransfer;
import com.echothree.util.common.command.BaseResult;
import java.util.List;

public interface GetLocationResult
        extends BaseResult {
    
    LocationTransfer getLocation();
    void setLocation(LocationTransfer location);
    
    List<LocationNameElementTransfer> getLocationNameElements();
    void setLocationNameElements(List<LocationNameElementTransfer> locationNameElements);
    
    LocationVolumeTransfer getLocationVolume();
    void setLocationVolume(LocationVolumeTransfer locationVolume);
    
    List<LocationCapacityTransfer> getLocationCapacities();
    void setLocationCapacities(List<LocationCapacityTransfer> locationCapacities);
    
}
