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

package com.echothree.model.control.item.common.transfer;

import com.echothree.model.control.uom.common.transfer.UnitOfMeasureTypeTransfer;
import com.echothree.util.common.transfer.BaseTransfer;

public class ItemVolumeTransfer
        extends BaseTransfer {
    
    private ItemTransfer item;
    private UnitOfMeasureTypeTransfer unitOfMeasureType;
    private ItemVolumeTypeTransfer itemVolumeType;
    private String height;
    private String width;
    private String depth;

    /** Creates a new instance of ItemVolumeTransfer */
    public ItemVolumeTransfer(final ItemTransfer item, final UnitOfMeasureTypeTransfer unitOfMeasureType,
            final ItemVolumeTypeTransfer itemVolumeType, final String height, final String width, final String depth) {
        this.item = item;
        this.unitOfMeasureType = unitOfMeasureType;
        this.itemVolumeType = itemVolumeType;
        this.height = height;
        this.width = width;
        this.depth = depth;
    }

    public ItemTransfer getItem() {
        return item;
    }

    public void setItem(final ItemTransfer item) {
        this.item = item;
    }

    public UnitOfMeasureTypeTransfer getUnitOfMeasureType() {
        return unitOfMeasureType;
    }

    public void setUnitOfMeasureType(final UnitOfMeasureTypeTransfer unitOfMeasureType) {
        this.unitOfMeasureType = unitOfMeasureType;
    }

    public ItemVolumeTypeTransfer getItemVolumeType() {
        return itemVolumeType;
    }

    public void setItemVolumeType(final ItemVolumeTypeTransfer itemVolumeType) {
        this.itemVolumeType = itemVolumeType;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(final String height) {
        this.height = height;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(final String width) {
        this.width = width;
    }

    public String getDepth() {
        return depth;
    }

    public void setDepth(final String depth) {
        this.depth = depth;
    }

}
