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

package com.echothree.model.control.carrier.common.transfer;

import com.echothree.model.control.selector.common.transfer.SelectorTransfer;
import com.echothree.util.common.transfer.BaseTransfer;

public class CarrierOptionTransfer
        extends BaseTransfer {
    
    private CarrierTransfer carrier;
    private String carrierOptionName;
    private Boolean isRecommended;
    private Boolean isRequired;
    private SelectorTransfer recommendedGeoCodeSelector;
    private SelectorTransfer requiredGeoCodeSelector;
    private SelectorTransfer recommendedItemSelector;
    private SelectorTransfer requiredItemSelector;
    private SelectorTransfer recommendedOrderSelector;
    private SelectorTransfer requiredOrderSelector;
    private SelectorTransfer recommendedShipmentSelector;
    private SelectorTransfer requiredShipmentSelector;
    private Boolean isDefault;
    private Integer sortOrder;
    private String description;
    
    /** Creates a new instance of CarrierOptionTransfer */
    public CarrierOptionTransfer(CarrierTransfer carrier, String carrierOptionName, Boolean isRecommended, Boolean isRequired,
            SelectorTransfer recommendedGeoCodeSelector, SelectorTransfer requiredGeoCodeSelector, SelectorTransfer recommendedItemSelector,
            SelectorTransfer requiredItemSelector, SelectorTransfer recommendedOrderSelector, SelectorTransfer requiredOrderSelector,
            SelectorTransfer recommendedShipmentSelector, SelectorTransfer requiredShipmentSelector, Boolean isDefault, Integer sortOrder, String description) {
        this.carrier = carrier;
        this.carrierOptionName = carrierOptionName;
        this.isRecommended = isRecommended;
        this.isRequired = isRequired;
        this.recommendedGeoCodeSelector = recommendedGeoCodeSelector;
        this.requiredGeoCodeSelector = requiredGeoCodeSelector;
        this.recommendedItemSelector = recommendedItemSelector;
        this.requiredItemSelector = requiredItemSelector;
        this.recommendedOrderSelector = recommendedOrderSelector;
        this.requiredOrderSelector = requiredOrderSelector;
        this.recommendedShipmentSelector = recommendedShipmentSelector;
        this.requiredShipmentSelector = requiredShipmentSelector;
        this.isDefault = isDefault;
        this.sortOrder = sortOrder;
        this.description = description;
    }

    /**
     * Returns the carrier.
     * @return the carrier
     */
    public CarrierTransfer getCarrier() {
        return carrier;
    }

    /**
     * Sets the carrier.
     * @param carrier the carrier to set
     */
    public void setCarrier(CarrierTransfer carrier) {
        this.carrier = carrier;
    }

    /**
     * Returns the carrierOptionName.
     * @return the carrierOptionName
     */
    public String getCarrierOptionName() {
        return carrierOptionName;
    }

    /**
     * Sets the carrierOptionName.
     * @param carrierOptionName the carrierOptionName to set
     */
    public void setCarrierOptionName(String carrierOptionName) {
        this.carrierOptionName = carrierOptionName;
    }

    /**
     * Returns the isRecommended.
     * @return the isRecommended
     */
    public Boolean getIsRecommended() {
        return isRecommended;
    }

    /**
     * Sets the isRecommended.
     * @param isRecommended the isRecommended to set
     */
    public void setIsRecommended(Boolean isRecommended) {
        this.isRecommended = isRecommended;
    }

    /**
     * Returns the isRequired.
     * @return the isRequired
     */
    public Boolean getIsRequired() {
        return isRequired;
    }

    /**
     * Sets the isRequired.
     * @param isRequired the isRequired to set
     */
    public void setIsRequired(Boolean isRequired) {
        this.isRequired = isRequired;
    }

    /**
     * Returns the recommendedGeoCodeSelector.
     * @return the recommendedGeoCodeSelector
     */
    public SelectorTransfer getRecommendedGeoCodeSelector() {
        return recommendedGeoCodeSelector;
    }

    /**
     * Sets the recommendedGeoCodeSelector.
     * @param recommendedGeoCodeSelector the recommendedGeoCodeSelector to set
     */
    public void setRecommendedGeoCodeSelector(SelectorTransfer recommendedGeoCodeSelector) {
        this.recommendedGeoCodeSelector = recommendedGeoCodeSelector;
    }

    /**
     * Returns the requiredGeoCodeSelector.
     * @return the requiredGeoCodeSelector
     */
    public SelectorTransfer getRequiredGeoCodeSelector() {
        return requiredGeoCodeSelector;
    }

    /**
     * Sets the requiredGeoCodeSelector.
     * @param requiredGeoCodeSelector the requiredGeoCodeSelector to set
     */
    public void setRequiredGeoCodeSelector(SelectorTransfer requiredGeoCodeSelector) {
        this.requiredGeoCodeSelector = requiredGeoCodeSelector;
    }

    /**
     * Returns the recommendedItemSelector.
     * @return the recommendedItemSelector
     */
    public SelectorTransfer getRecommendedItemSelector() {
        return recommendedItemSelector;
    }

    /**
     * Sets the recommendedItemSelector.
     * @param recommendedItemSelector the recommendedItemSelector to set
     */
    public void setRecommendedItemSelector(SelectorTransfer recommendedItemSelector) {
        this.recommendedItemSelector = recommendedItemSelector;
    }

    /**
     * Returns the requiredItemSelector.
     * @return the requiredItemSelector
     */
    public SelectorTransfer getRequiredItemSelector() {
        return requiredItemSelector;
    }

    /**
     * Sets the requiredItemSelector.
     * @param requiredItemSelector the requiredItemSelector to set
     */
    public void setRequiredItemSelector(SelectorTransfer requiredItemSelector) {
        this.requiredItemSelector = requiredItemSelector;
    }

    /**
     * Returns the recommendedOrderSelector.
     * @return the recommendedOrderSelector
     */
    public SelectorTransfer getRecommendedOrderSelector() {
        return recommendedOrderSelector;
    }

    /**
     * Sets the recommendedOrderSelector.
     * @param recommendedOrderSelector the recommendedOrderSelector to set
     */
    public void setRecommendedOrderSelector(SelectorTransfer recommendedOrderSelector) {
        this.recommendedOrderSelector = recommendedOrderSelector;
    }

    /**
     * Returns the requiredOrderSelector.
     * @return the requiredOrderSelector
     */
    public SelectorTransfer getRequiredOrderSelector() {
        return requiredOrderSelector;
    }

    /**
     * Sets the requiredOrderSelector.
     * @param requiredOrderSelector the requiredOrderSelector to set
     */
    public void setRequiredOrderSelector(SelectorTransfer requiredOrderSelector) {
        this.requiredOrderSelector = requiredOrderSelector;
    }

    /**
     * Returns the recommendedShipmentSelector.
     * @return the recommendedShipmentSelector
     */
    public SelectorTransfer getRecommendedShipmentSelector() {
        return recommendedShipmentSelector;
    }

    /**
     * Sets the recommendedShipmentSelector.
     * @param recommendedShipmentSelector the recommendedShipmentSelector to set
     */
    public void setRecommendedShipmentSelector(SelectorTransfer recommendedShipmentSelector) {
        this.recommendedShipmentSelector = recommendedShipmentSelector;
    }

    /**
     * Returns the requiredShipmentSelector.
     * @return the requiredShipmentSelector
     */
    public SelectorTransfer getRequiredShipmentSelector() {
        return requiredShipmentSelector;
    }

    /**
     * Sets the requiredShipmentSelector.
     * @param requiredShipmentSelector the requiredShipmentSelector to set
     */
    public void setRequiredShipmentSelector(SelectorTransfer requiredShipmentSelector) {
        this.requiredShipmentSelector = requiredShipmentSelector;
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
    
}
