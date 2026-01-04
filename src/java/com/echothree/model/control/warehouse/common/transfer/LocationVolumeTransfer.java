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

import com.echothree.util.common.transfer.BaseTransfer;

public class LocationVolumeTransfer
        extends BaseTransfer {
    
    private LocationTransfer location;
    private String height;
    private String width;
    private String depth;
    private Long cubicVolume;
    
    /** Creates a new instance of LocationVolumeTransfer */
    public LocationVolumeTransfer(LocationTransfer location, String height, String width, String depth, Long cubicVolume) {
        this.location = location;
        this.height = height;
        this.width = width;
        this.depth = depth;
        this.cubicVolume = cubicVolume;
    }
    
    public LocationTransfer getLocation() {
        return location;
    }
    
    public void setLocation(LocationTransfer location) {
        this.location = location;
    }
    
    public String getHeight() {
        return height;
    }
    
    public void setHeight(String height) {
        this.height = height;
    }
    
    public String getWidth() {
        return width;
    }
    
    public void setWidth(String width) {
        this.width = width;
    }
    
    public String getDepth() {
        return depth;
    }
    
    public void setDepth(String depth) {
        this.depth = depth;
    }
    
    public Long getCubicVolume() {
        return cubicVolume;
    }
    
    public void setCubicVolume(Long cubicVolume) {
        this.cubicVolume = cubicVolume;
    }
    
}
