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

package com.echothree.model.control.uom.common.transfer;

import com.echothree.util.common.transfer.BaseTransfer;

public class UnitOfMeasureTypeVolumeTransfer
        extends BaseTransfer {
    
    private UnitOfMeasureTypeTransfer unitOfMeasureType;
    private String height;
    private String width;
    private String depth;
    
    /** Creates a new instance of UnitOfMeasureTypeVolumeTransfer */
    public UnitOfMeasureTypeVolumeTransfer(UnitOfMeasureTypeTransfer unitOfMeasureType, String height, String width, String depth) {
        this.unitOfMeasureType = unitOfMeasureType;
        this.height = height;
        this.width = width;
        this.depth = depth;
    }
    
    public UnitOfMeasureTypeTransfer getUnitOfMeasureType() {
        return unitOfMeasureType;
    }
    
    public void setUnitOfMeasureType(UnitOfMeasureTypeTransfer unitOfMeasureType) {
        this.unitOfMeasureType = unitOfMeasureType;
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
    
}
