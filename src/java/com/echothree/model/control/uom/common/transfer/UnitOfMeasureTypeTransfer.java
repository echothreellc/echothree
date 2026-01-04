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

import com.echothree.model.control.accounting.common.transfer.SymbolPositionTransfer;
import com.echothree.util.common.transfer.BaseTransfer;

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
     * Returns the unitOfMeasureKind.
     * @return the unitOfMeasureKind
     */
    public UnitOfMeasureKindTransfer getUnitOfMeasureKind() {
        return unitOfMeasureKind;
    }

    /**
     * Sets the unitOfMeasureKind.
     * @param unitOfMeasureKind the unitOfMeasureKind to set
     */
    public void setUnitOfMeasureKind(UnitOfMeasureKindTransfer unitOfMeasureKind) {
        this.unitOfMeasureKind = unitOfMeasureKind;
    }

    /**
     * Returns the unitOfMeasureTypeName.
     * @return the unitOfMeasureTypeName
     */
    public String getUnitOfMeasureTypeName() {
        return unitOfMeasureTypeName;
    }

    /**
     * Sets the unitOfMeasureTypeName.
     * @param unitOfMeasureTypeName the unitOfMeasureTypeName to set
     */
    public void setUnitOfMeasureTypeName(String unitOfMeasureTypeName) {
        this.unitOfMeasureTypeName = unitOfMeasureTypeName;
    }

    /**
     * Returns the symbolPosition.
     * @return the symbolPosition
     */
    public SymbolPositionTransfer getSymbolPosition() {
        return symbolPosition;
    }

    /**
     * Sets the symbolPosition.
     * @param symbolPosition the symbolPosition to set
     */
    public void setSymbolPosition(SymbolPositionTransfer symbolPosition) {
        this.symbolPosition = symbolPosition;
    }

    /**
     * Returns the suppressSymbolSeparator.
     * @return the suppressSymbolSeparator
     */
    public Boolean getSuppressSymbolSeparator() {
        return suppressSymbolSeparator;
    }

    /**
     * Sets the suppressSymbolSeparator.
     * @param suppressSymbolSeparator the suppressSymbolSeparator to set
     */
    public void setSuppressSymbolSeparator(Boolean suppressSymbolSeparator) {
        this.suppressSymbolSeparator = suppressSymbolSeparator;
    }

    /**
     * Returns the isDefault.
     * @return the isDefault
     */
    public Boolean getIsDefault() {
        return isDefault;
    }

    /**
     * Sets the isDefault.
     * @param isDefault the isDefault to set
     */
    public void setIsDefault(Boolean isDefault) {
        this.isDefault = isDefault;
    }

    /**
     * Returns the sortOrder.
     * @return the sortOrder
     */
    public Integer getSortOrder() {
        return sortOrder;
    }

    /**
     * Sets the sortOrder.
     * @param sortOrder the sortOrder to set
     */
    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    /**
     * Returns the description.
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description.
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns the unitOfMeasureTypeVolume.
     * @return the unitOfMeasureTypeVolume
     */
    public UnitOfMeasureTypeVolumeTransfer getUnitOfMeasureTypeVolume() {
        return unitOfMeasureTypeVolume;
    }

    /**
     * Sets the unitOfMeasureTypeVolume.
     * @param unitOfMeasureTypeVolume the unitOfMeasureTypeVolume to set
     */
    public void setUnitOfMeasureTypeVolume(UnitOfMeasureTypeVolumeTransfer unitOfMeasureTypeVolume) {
        this.unitOfMeasureTypeVolume = unitOfMeasureTypeVolume;
    }

    /**
     * Returns the unitOfMeasureTypeWeight.
     * @return the unitOfMeasureTypeWeight
     */
    public UnitOfMeasureTypeWeightTransfer getUnitOfMeasureTypeWeight() {
        return unitOfMeasureTypeWeight;
    }

    /**
     * Sets the unitOfMeasureTypeWeight.
     * @param unitOfMeasureTypeWeight the unitOfMeasureTypeWeight to set
     */
    public void setUnitOfMeasureTypeWeight(UnitOfMeasureTypeWeightTransfer unitOfMeasureTypeWeight) {
        this.unitOfMeasureTypeWeight = unitOfMeasureTypeWeight;
    }
    
}
