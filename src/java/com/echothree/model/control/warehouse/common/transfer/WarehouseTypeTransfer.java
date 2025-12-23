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

public class WarehouseTypeTransfer
        extends BaseTransfer {
    
    private String warehouseTypeName;
    private Integer priority;
    private Boolean isDefault;
    private Integer sortOrder;
    private String description;
    
    /** Creates a new instance of WarehouseTypeTransfer */
    public WarehouseTypeTransfer(String warehouseTypeName, Integer priority, Boolean isDefault, Integer sortOrder,
            String description) {
        this.warehouseTypeName = warehouseTypeName;
        this.priority = priority;
        this.isDefault = isDefault;
        this.sortOrder = sortOrder;
        this.description = description;
    }

    public String getWarehouseTypeName() {
        return warehouseTypeName;
    }

    public void setWarehouseTypeName(final String warehouseTypeName) {
        this.warehouseTypeName = warehouseTypeName;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(final Integer priority) {
        this.priority = priority;
    }

    public Boolean getIsDefault() {
        return isDefault;
    }

    public void getIsDefault(final Boolean isDefault) {
        this.isDefault = isDefault;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(final Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

}
