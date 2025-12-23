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

import com.echothree.model.control.accounting.common.transfer.CurrencyTransfer;
import com.echothree.model.control.party.common.transfer.DateTimeFormatTransfer;
import com.echothree.model.control.party.common.transfer.LanguageTransfer;
import com.echothree.model.control.party.common.transfer.PartyGroupTransfer;
import com.echothree.model.control.party.common.transfer.PartyTransfer;
import com.echothree.model.control.party.common.transfer.PartyTypeTransfer;
import com.echothree.model.control.party.common.transfer.PersonTransfer;
import com.echothree.model.control.party.common.transfer.TimeZoneTransfer;
import com.echothree.model.control.selector.common.transfer.SelectorTransfer;

public class CarrierTransfer
        extends PartyTransfer {
    
    private String carrierName;
    private CarrierTypeTransfer carrierType;
    private SelectorTransfer geoCodeSelector;
    private SelectorTransfer itemSelector;
    private String accountValidationPattern;
    private Boolean isDefault;
    private Integer sortOrder;
    
    /** Creates a new instance of CarrierTransfer */
    public CarrierTransfer(String partyName, PartyTypeTransfer partyType, LanguageTransfer preferredLanguage, CurrencyTransfer preferredCurrency,
            TimeZoneTransfer preferredTimeZone, DateTimeFormatTransfer preferredDateTimeFormat, PersonTransfer person, PartyGroupTransfer partyGroup,
            String carrierName, CarrierTypeTransfer carrierType, SelectorTransfer geoCodeSelector, SelectorTransfer itemSelector,
            String accountValidationPattern, Boolean isDefault, Integer sortOrder) {
        super(partyName, partyType, preferredLanguage, preferredCurrency, preferredTimeZone, preferredDateTimeFormat, person, partyGroup, null);
        
        this.carrierName = carrierName;
        this.carrierType = carrierType;
        this.geoCodeSelector = geoCodeSelector;
        this.itemSelector = itemSelector;
        this.accountValidationPattern = accountValidationPattern;
        this.isDefault = isDefault;
        this.sortOrder = sortOrder;
    }

    /**
     * Returns the carrierName.
     * @return the carrierName
     */
    public String getCarrierName() {
        return carrierName;
    }

    /**
     * Sets the carrierName.
     * @param carrierName the carrierName to set
     */
    public void setCarrierName(String carrierName) {
        this.carrierName = carrierName;
    }

    /**
     * Returns the carrierType.
     * @return the carrierType
     */
    public CarrierTypeTransfer getCarrierType() {
        return carrierType;
    }

    /**
     * Sets the carrierType.
     * @param carrierType the carrierType to set
     */
    public void setCarrierType(CarrierTypeTransfer carrierType) {
        this.carrierType = carrierType;
    }

    /**
     * Returns the geoCodeSelector.
     * @return the geoCodeSelector
     */
    public SelectorTransfer getGeoCodeSelector() {
        return geoCodeSelector;
    }

    /**
     * Sets the geoCodeSelector.
     * @param geoCodeSelector the geoCodeSelector to set
     */
    public void setGeoCodeSelector(SelectorTransfer geoCodeSelector) {
        this.geoCodeSelector = geoCodeSelector;
    }

    /**
     * Returns the itemSelector.
     * @return the itemSelector
     */
    public SelectorTransfer getItemSelector() {
        return itemSelector;
    }

    /**
     * Sets the itemSelector.
     * @param itemSelector the itemSelector to set
     */
    public void setItemSelector(SelectorTransfer itemSelector) {
        this.itemSelector = itemSelector;
    }

    /**
     * Returns the accountValidationPattern.
     * @return the accountValidationPattern
     */
    public String getAccountValidationPattern() {
        return accountValidationPattern;
    }

    /**
     * Sets the accountValidationPattern.
     * @param accountValidationPattern the accountValidationPattern to set
     */
    public void setAccountValidationPattern(String accountValidationPattern) {
        this.accountValidationPattern = accountValidationPattern;
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

}
