// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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
     * @return the carrier
     */
    public CarrierTransfer getCarrier() {
        return carrier;
    }

    /**
     * @param carrier the carrier to set
     */
    public void setCarrier(CarrierTransfer carrier) {
        this.carrier = carrier;
    }

    /**
     * @return the carrierOptionName
     */
    public String getCarrierOptionName() {
        return carrierOptionName;
    }

    /**
     * @param carrierOptionName the carrierOptionName to set
     */
    public void setCarrierOptionName(String carrierOptionName) {
        this.carrierOptionName = carrierOptionName;
    }

    /**
     * @return the isRecommended
     */
    public Boolean getIsRecommended() {
        return isRecommended;
    }

    /**
     * @param isRecommended the isRecommended to set
     */
    public void setIsRecommended(Boolean isRecommended) {
        this.isRecommended = isRecommended;
    }

    /**
     * @return the isRequired
     */
    public Boolean getIsRequired() {
        return isRequired;
    }

    /**
     * @param isRequired the isRequired to set
     */
    public void setIsRequired(Boolean isRequired) {
        this.isRequired = isRequired;
    }

    /**
     * @return the recommendedGeoCodeSelector
     */
    public SelectorTransfer getRecommendedGeoCodeSelector() {
        return recommendedGeoCodeSelector;
    }

    /**
     * @param recommendedGeoCodeSelector the recommendedGeoCodeSelector to set
     */
    public void setRecommendedGeoCodeSelector(SelectorTransfer recommendedGeoCodeSelector) {
        this.recommendedGeoCodeSelector = recommendedGeoCodeSelector;
    }

    /**
     * @return the requiredGeoCodeSelector
     */
    public SelectorTransfer getRequiredGeoCodeSelector() {
        return requiredGeoCodeSelector;
    }

    /**
     * @param requiredGeoCodeSelector the requiredGeoCodeSelector to set
     */
    public void setRequiredGeoCodeSelector(SelectorTransfer requiredGeoCodeSelector) {
        this.requiredGeoCodeSelector = requiredGeoCodeSelector;
    }

    /**
     * @return the recommendedItemSelector
     */
    public SelectorTransfer getRecommendedItemSelector() {
        return recommendedItemSelector;
    }

    /**
     * @param recommendedItemSelector the recommendedItemSelector to set
     */
    public void setRecommendedItemSelector(SelectorTransfer recommendedItemSelector) {
        this.recommendedItemSelector = recommendedItemSelector;
    }

    /**
     * @return the requiredItemSelector
     */
    public SelectorTransfer getRequiredItemSelector() {
        return requiredItemSelector;
    }

    /**
     * @param requiredItemSelector the requiredItemSelector to set
     */
    public void setRequiredItemSelector(SelectorTransfer requiredItemSelector) {
        this.requiredItemSelector = requiredItemSelector;
    }

    /**
     * @return the recommendedOrderSelector
     */
    public SelectorTransfer getRecommendedOrderSelector() {
        return recommendedOrderSelector;
    }

    /**
     * @param recommendedOrderSelector the recommendedOrderSelector to set
     */
    public void setRecommendedOrderSelector(SelectorTransfer recommendedOrderSelector) {
        this.recommendedOrderSelector = recommendedOrderSelector;
    }

    /**
     * @return the requiredOrderSelector
     */
    public SelectorTransfer getRequiredOrderSelector() {
        return requiredOrderSelector;
    }

    /**
     * @param requiredOrderSelector the requiredOrderSelector to set
     */
    public void setRequiredOrderSelector(SelectorTransfer requiredOrderSelector) {
        this.requiredOrderSelector = requiredOrderSelector;
    }

    /**
     * @return the recommendedShipmentSelector
     */
    public SelectorTransfer getRecommendedShipmentSelector() {
        return recommendedShipmentSelector;
    }

    /**
     * @param recommendedShipmentSelector the recommendedShipmentSelector to set
     */
    public void setRecommendedShipmentSelector(SelectorTransfer recommendedShipmentSelector) {
        this.recommendedShipmentSelector = recommendedShipmentSelector;
    }

    /**
     * @return the requiredShipmentSelector
     */
    public SelectorTransfer getRequiredShipmentSelector() {
        return requiredShipmentSelector;
    }

    /**
     * @param requiredShipmentSelector the requiredShipmentSelector to set
     */
    public void setRequiredShipmentSelector(SelectorTransfer requiredShipmentSelector) {
        this.requiredShipmentSelector = requiredShipmentSelector;
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
    
}
