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

package com.echothree.model.control.carrier.remote.transfer;

import com.echothree.model.control.accounting.remote.transfer.CurrencyTransfer;
import com.echothree.model.control.party.remote.transfer.DateTimeFormatTransfer;
import com.echothree.model.control.party.remote.transfer.LanguageTransfer;
import com.echothree.model.control.party.remote.transfer.PartyGroupTransfer;
import com.echothree.model.control.party.remote.transfer.PartyTransfer;
import com.echothree.model.control.party.remote.transfer.PartyTypeTransfer;
import com.echothree.model.control.party.remote.transfer.PersonTransfer;
import com.echothree.model.control.party.remote.transfer.TimeZoneTransfer;
import com.echothree.model.control.selector.remote.transfer.SelectorTransfer;

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
     * @return the carrierName
     */
    public String getCarrierName() {
        return carrierName;
    }

    /**
     * @param carrierName the carrierName to set
     */
    public void setCarrierName(String carrierName) {
        this.carrierName = carrierName;
    }

    /**
     * @return the carrierType
     */
    public CarrierTypeTransfer getCarrierType() {
        return carrierType;
    }

    /**
     * @param carrierType the carrierType to set
     */
    public void setCarrierType(CarrierTypeTransfer carrierType) {
        this.carrierType = carrierType;
    }

    /**
     * @return the geoCodeSelector
     */
    public SelectorTransfer getGeoCodeSelector() {
        return geoCodeSelector;
    }

    /**
     * @param geoCodeSelector the geoCodeSelector to set
     */
    public void setGeoCodeSelector(SelectorTransfer geoCodeSelector) {
        this.geoCodeSelector = geoCodeSelector;
    }

    /**
     * @return the itemSelector
     */
    public SelectorTransfer getItemSelector() {
        return itemSelector;
    }

    /**
     * @param itemSelector the itemSelector to set
     */
    public void setItemSelector(SelectorTransfer itemSelector) {
        this.itemSelector = itemSelector;
    }

    /**
     * @return the accountValidationPattern
     */
    public String getAccountValidationPattern() {
        return accountValidationPattern;
    }

    /**
     * @param accountValidationPattern the accountValidationPattern to set
     */
    public void setAccountValidationPattern(String accountValidationPattern) {
        this.accountValidationPattern = accountValidationPattern;
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

}
