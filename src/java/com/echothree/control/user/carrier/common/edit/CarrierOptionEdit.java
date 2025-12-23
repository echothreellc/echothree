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

package com.echothree.control.user.carrier.common.edit;

public interface CarrierOptionEdit
        extends CarrierOptionDescriptionEdit {
    
    String getCarrierOptionName();
    void setCarrierOptionName(String carrierOptionName);
        
    String getIsRecommended();
    void setIsRecommended(String isRecommended);
    
    String getIsRequired();
    void setIsRequired(String isRequired);
    
    String getRecommendedGeoCodeSelectorName();
    void setRecommendedGeoCodeSelectorName(String recommendedGeoCodeSelectorName);

    String getRequiredGeoCodeSelectorName();
    void setRequiredGeoCodeSelectorName(String requiredGeoCodeSelectorName);

    String getRecommendedItemSelectorName();
    void setRecommendedItemSelectorName(String recommendedItemSelectorName);

    String getRequiredItemSelectorName();
    void setRequiredItemSelectorName(String requiredItemSelectorName);

    String getRecommendedOrderSelectorName();
    void setRecommendedOrderSelectorName(String recommendedOrderSelectorName);
    
    String getRequiredOrderSelectorName();
    void setRequiredOrderSelectorName(String requiredOrderSelectorName);
    
    String getRecommendedShipmentSelectorName();
    void setRecommendedShipmentSelectorName(String recommendedShipmentSelectorName);
    
    String getRequiredShipmentSelectorName();
    void setRequiredShipmentSelectorName(String requiredShipmentSelectorName);
    
    String getIsDefault();
    void setIsDefault(String isDefault);
    
    String getSortOrder();
    void setSortOrder(String sortOrder);
    
}
