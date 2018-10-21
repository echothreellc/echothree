// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
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

package com.echothree.model.control.uom.remote.transfer;

import com.echothree.model.control.accounting.remote.transfer.SymbolPositionTransfer;
import com.echothree.util.remote.transfer.BaseTransfer;

public class UnitOfMeasureTypeTransfer
        extends BaseTransfer {
    
    private UnitOfMeasureKindTransfer unitOfMeasureKind;
    private String unitOfMeasureTypeName;
    private SymbolPositionTransfer symbolPosition;
    private Boolean suppressSymbolSeparator;
    private Boolean isDefault;
    private Integer sortOrder;
    private String description;
    
    private UnitOfMeasureTypeVolumeTransfer unitOfMeasureTypeVolume;
    private UnitOfMeasureTypeWeightTransfer unitOfMeasureTypeWeight;
    
    /** Creates a new instance of UnitOfMeasureTypeTransfer */
    public UnitOfMeasureTypeTransfer(UnitOfMeasureKindTransfer unitOfMeasureKind, String unitOfMeasureTypeName,
            SymbolPositionTransfer symbolPosition, Boolean suppressSymbolSeparator, Boolean isDefault, Integer sortOrder,
            String description) {
        this.unitOfMeasureKind = unitOfMeasureKind;
        this.unitOfMeasureTypeName = unitOfMeasureTypeName;
        this.symbolPosition = symbolPosition;
        this.suppressSymbolSeparator = suppressSymbolSeparator;
        this.isDefault = isDefault;
        this.sortOrder = sortOrder;
        this.description = description;
    }

    /**
     * @return the unitOfMeasureKind
     */
    public UnitOfMeasureKindTransfer getUnitOfMeasureKind() {
        return unitOfMeasureKind;
    }

    /**
     * @param unitOfMeasureKind the unitOfMeasureKind to set
     */
    public void setUnitOfMeasureKind(UnitOfMeasureKindTransfer unitOfMeasureKind) {
        this.unitOfMeasureKind = unitOfMeasureKind;
    }

    /**
     * @return the unitOfMeasureTypeName
     */
    public String getUnitOfMeasureTypeName() {
        return unitOfMeasureTypeName;
    }

    /**
     * @param unitOfMeasureTypeName the unitOfMeasureTypeName to set
     */
    public void setUnitOfMeasureTypeName(String unitOfMeasureTypeName) {
        this.unitOfMeasureTypeName = unitOfMeasureTypeName;
    }

    /**
     * @return the symbolPosition
     */
    public SymbolPositionTransfer getSymbolPosition() {
        return symbolPosition;
    }

    /**
     * @param symbolPosition the symbolPosition to set
     */
    public void setSymbolPosition(SymbolPositionTransfer symbolPosition) {
        this.symbolPosition = symbolPosition;
    }

    /**
     * @return the suppressSymbolSeparator
     */
    public Boolean getSuppressSymbolSeparator() {
        return suppressSymbolSeparator;
    }

    /**
     * @param suppressSymbolSeparator the suppressSymbolSeparator to set
     */
    public void setSuppressSymbolSeparator(Boolean suppressSymbolSeparator) {
        this.suppressSymbolSeparator = suppressSymbolSeparator;
    }

    /**
     * @return the isDefault
     */
    public Boolean getIsDefault() {
        return isDefault;
    }

    /**
     * @param isDefault the isDefault to set
     */
    public void setIsDefault(Boolean isDefault) {
        this.isDefault = isDefault;
    }

    /**
     * @return the sortOrder
     */
    public Integer getSortOrder() {
        return sortOrder;
    }

    /**
     * @param sortOrder the sortOrder to set
     */
    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the unitOfMeasureTypeVolume
     */
    public UnitOfMeasureTypeVolumeTransfer getUnitOfMeasureTypeVolume() {
        return unitOfMeasureTypeVolume;
    }

    /**
     * @param unitOfMeasureTypeVolume the unitOfMeasureTypeVolume to set
     */
    public void setUnitOfMeasureTypeVolume(UnitOfMeasureTypeVolumeTransfer unitOfMeasureTypeVolume) {
        this.unitOfMeasureTypeVolume = unitOfMeasureTypeVolume;
    }

    /**
     * @return the unitOfMeasureTypeWeight
     */
    public UnitOfMeasureTypeWeightTransfer getUnitOfMeasureTypeWeight() {
        return unitOfMeasureTypeWeight;
    }

    /**
     * @param unitOfMeasureTypeWeight the unitOfMeasureTypeWeight to set
     */
    public void setUnitOfMeasureTypeWeight(UnitOfMeasureTypeWeightTransfer unitOfMeasureTypeWeight) {
        this.unitOfMeasureTypeWeight = unitOfMeasureTypeWeight;
    }
    
}
